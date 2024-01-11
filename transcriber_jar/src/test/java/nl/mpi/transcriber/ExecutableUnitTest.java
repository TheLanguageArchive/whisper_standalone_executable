package nl.mpi.transcriber;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;

import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static nl.mpi.transcriber.WhisperLanguage.EN;
import static nl.mpi.transcriber.WhisperModels.LARGE_V3;
import static nl.mpi.transcriber.WhisperModels.MEDIUM;
import static nl.mpi.transcriber.WhisperResponseFormats.JSON;
import static org.junit.jupiter.api.condition.OS.*;

public class ExecutableUnitTest {

    @Test
    @EnabledOnOs({LINUX})
    public void testGetTranscribedLinux() throws Exception {
        checkTranscriptionResults(new WhisperArguments(
                resolvePath("src/main/resources/releases/transcriber_cli_ubuntu_001202312211131"),
                resolvePath("src/test/resources/videos/testFile202312061352.mp4"),
                MEDIUM,
                Optional.of(EN),
                Optional.empty(),
                Optional.of(JSON),
                Optional.empty(),
                new URI("./target/"),
                Optional.of(" --fp16=False ")
        ), "src/test/resources/testFile202312061352Expected_ubuntu.json");
    }

    @Test
    @EnabledOnOs({MAC})
    public void testGetTranscribedMac() throws Exception {
        checkTranscriptionResults(new WhisperArguments(
                resolvePath("src/main/resources/releases/transcriber_cli_mac_001202312211131"),
                resolvePath("src/test/resources/videos/testFile202312061352.mp4"),
                LARGE_V3,
                Optional.of(EN),
                Optional.empty(),
                Optional.of(JSON),
                Optional.empty(),
                new URI("./target/"),
                Optional.of(" --fp16=False ")
        ), "src/test/resources/testFile202312061352Expected_mac.json");
    }

    @Test
    @EnabledOnOs({WINDOWS})
    public void testGetTranscribedWin() throws Exception {
        checkTranscriptionResults(new WhisperArguments(
                resolvePath("src/main/resources/releases/transcriber_cli_win_001202401021637.exe"),
                resolvePath("src/test/resources/videos/testFile202312061352.mp4"),
                LARGE_V3,
                Optional.of(EN),
                Optional.empty(),
                Optional.of(JSON),
                Optional.empty(),
                new URI("target/"),
                Optional.of(" --fp16=False ")
        ), "src/test/resources/testFile202312061352Expected_win.json");
    }

    private void checkTranscriptionResults(WhisperArguments whisperArgs, String expectedResultsFilePath) throws IOException, InterruptedException {
        String[] commandLine = CommandCreator.getInstance().toCommandLineFormat(whisperArgs);
        ProcessBuilder processBuilder = new ProcessBuilder(commandLine);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        List<String> results = readProcessOutput(process.getInputStream());

        Assertions.assertTrue(results.stream().anyMatch(string -> string.startsWith("Whisper cli started at :")), "Results should contain output of script");
        Assertions.assertTrue(results.stream().anyMatch(string -> string.startsWith("Whisper cli returned which was started at :")), "Results should contain output of script");
        compareResultingTranscription(expectedResultsFilePath);
        Assertions.assertEquals(0, process.waitFor(), "No errors should be detected");
    }

    private void compareResultingTranscription(String expectedResultsFilePath) throws IOException {
        String actualResults = "target/testFile202312061352.json";

        String expectedResultsContents = new String(Files.readAllBytes(Paths.get(expectedResultsFilePath)));
        String actualResultsContents = new String(Files.readAllBytes(Paths.get(resolvePath(actualResults))));

        JSONObject expectedJson = new JSONObject(expectedResultsContents);
        JSONObject actualJson = new JSONObject(actualResultsContents);

        Assertions.assertTrue(expectedJson.similar(actualJson), "The transcription should be similar");

    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

    private String resolvePath(String filename) {
        File file = new File(filename);
        return file.getAbsolutePath();
    }
}

package nl.mpi.transcriber;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.condition.OS.MAC;
import static org.junit.jupiter.api.condition.OS.WINDOWS;

public class JavaPythonInteropUnitTest {

    @Test
    @EnabledOnOs({MAC, WINDOWS})
    public void givenPythonScript_whenPythonProcessInvoked_thenSuccess() throws Exception {
        ProcessBuilder processBuilder = new ProcessBuilder("python", resolvePythonScriptPath());
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();
        List<String> results = readProcessOutput(process.getInputStream());

        Assertions.assertTrue(results.stream().anyMatch(string -> string.startsWith("Hello Researchers!!")), "Results should contain output of script");
        int exitCode = process.waitFor();
        Assertions.assertEquals(0, exitCode, "No errors should be detected");
    }

    private List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }

    private String resolvePythonScriptPath() {
        File file = new File("src/test/resources/" + "hello.py");
        return file.getAbsolutePath();
    }

}

package nl.mpi.transcriber;

import java.net.URI;
import java.util.Optional;

public record WhisperArguments(
        String executablePath,
        String inputAudioVideoFilePath,
        WhisperModels whisperModelToUse,
        Optional<WhisperLanguage> expectedLanguageOfInputFile,
        Optional<String> prompt,
        Optional<WhisperResponseFormats> responseFormat,
        Optional<String> temperature,
        Optional<Boolean> wordTimestamps,
        URI outputDirectory,
        Optional<String> misc
) {
}

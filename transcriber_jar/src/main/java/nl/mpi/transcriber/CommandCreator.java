package nl.mpi.transcriber;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandCreator {
    private static class InstanceHolder {
        public static CommandCreator instance = new CommandCreator();
    }

    private CommandCreator() {

    }

    public static CommandCreator getInstance() {
        return CommandCreator.InstanceHolder.instance;
    }

    public String[] toCommandLineFormat(WhisperArguments whisperArguments) {
        List<String> command = new ArrayList<>();

        command.add(whisperArguments.executablePath());
        command.add(whisperArguments.inputAudioVideoFilePath());
        add(command, "model", whisperArguments.whisperModelToUse());

        optionalAdd(command, "language", whisperArguments.expectedLanguageOfInputFile());
        optionalAdd(command, "prompt", whisperArguments.prompt());
        optionalAdd(command, "output_format", whisperArguments.responseFormat());
        optionalAdd(command, "temperature", whisperArguments.temperature());

        add(command, "output_dir", whisperArguments.outputDirectory());

        return command.toArray(new String[0]);
    }

    public <T> List<String> add(List<String> command, String key, T value) {
        command.add("--" + key + "=" + value);
        return command;
    }

    <T> void optionalAdd(List<String> command, String key, Optional<T> optional) {
        optional.map(optionalValue -> add(command, key, optionalValue.toString()));
    }

}
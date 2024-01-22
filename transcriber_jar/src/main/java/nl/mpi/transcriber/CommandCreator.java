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
        optionalAdd(command, "word_timestamps", toPythonBooleanString(whisperArguments.wordTimestamps()));

        add(command, "output_dir", whisperArguments.outputDirectory());

        return command.toArray(new String[0]);
    }

    /**
     * Use this function to convert the string to correct capitalization case. Java stringifies the boolean values in small letters. Python has first letter capital for boolean literals.
     * Java : true & false
     * Python : True & False
     *
     * @param maybeBool is optionally available param with boolean literals.
     *
     * @return python acceptable capitalized stringified value of boolean literals.
     */
    private Optional<String> toPythonBooleanString(Optional<Boolean> maybeBool) {
        Optional<String> maybeString = Optional.empty();
        if (maybeBool.isPresent()) {
            String boolString = maybeBool.get().toString();
            maybeString = Optional.of(Character.toString(boolString.charAt(0)).toUpperCase().concat(boolString.substring(1)));
        }
        return maybeString;
    }

    public <T> List<String> add(List<String> command, String key, T value) {
        command.add("--" + key + "=" + value);
        return command;
    }

    <T> void optionalAdd(List<String> command, String key, Optional<T> optional) {
        optional.map(optionalValue -> add(command, key, optionalValue.toString()));
    }

}
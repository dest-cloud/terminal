package cloud.dest.terminal.utils;

import cloud.dest.terminal.command.Command;
import cloud.dest.terminal.variable.Variable;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ParseJsonFile {

    private ParseJsonFile() {
    }

    public static List<Command> readCommands(String absoluteFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = new URL("file:" + absoluteFile);
        return Arrays.asList(objectMapper.readValue(url, Command[].class).clone());
    }

    public static List<Variable> readVariables(String absoluteFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = new URL("file:" + absoluteFile);
        return Arrays.asList(objectMapper.readValue(url, Variable[].class).clone());
    }
}

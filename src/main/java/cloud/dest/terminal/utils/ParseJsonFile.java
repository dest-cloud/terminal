package cloud.dest.terminal.utils;

import cloud.dest.terminal.command.Command;
import cloud.dest.terminal.variable.Variable;
import cloud.dest.terminal.workspace.Workspace;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

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

    public static Workspace readWorkspace(String absoluteFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        URL url = new URL("file:" + absoluteFile);
        return objectMapper.readValue(url, Workspace.class);
    }

}

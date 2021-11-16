package com.bgconsole.desktop.utils;

import com.bgconsole.desktop.command.Command;
import com.bgconsole.desktop.location.Location;
import com.bgconsole.desktop.variable.Variable;
import com.bgconsole.desktop.workspace.Workspace;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ParseYAMLFile {

    private ParseYAMLFile() {
    }

    public static List<Command> readCommands(String absoluteFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        URL url = new URL("file:" + absoluteFile);
        return Arrays.asList(objectMapper.readValue(url, Command[].class).clone());
    }

    public static List<Variable> readVariables(String absoluteFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        URL url = new URL("file:" + absoluteFile);
        return Arrays.asList(objectMapper.readValue(url, Variable[].class).clone());
    }

    public static Workspace readWorkspace(String absoluteFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        URL url = new URL("file:" + absoluteFile);
        return objectMapper.readValue(url, Workspace.class);
    }

    public static List<Location> readLocations(String absoluteFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        URL url = new URL("file:" + absoluteFile);
        return Arrays.asList(objectMapper.readValue(url, Location[].class).clone());
    }

}

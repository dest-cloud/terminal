package cloud.dest.terminal.environment;

import cloud.dest.terminal.Config;
import cloud.dest.terminal.command.CommandList;
import cloud.dest.terminal.terminal.TerminalList;
import cloud.dest.terminal.variable.VariableList;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Environment {

    private String id;
    private Path dir;
    private TerminalList terminalList;
    private List<Config> configs;
    private List<VariableList> variableLists;
    private List<CommandList> commandLists;

    public Environment(String id, Path dir) {
        this.id = id;
        this.dir = dir;
        this.terminalList = new TerminalList();
        configs = new ArrayList<>();
        variableLists = new ArrayList<>();
        commandLists = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Path getDir() {
        return dir;
    }

    public void setDir(Path dir) {
        this.dir = dir;
    }

    public TerminalList getTerminalList() {
        return terminalList;
    }

    public void setTerminalList(TerminalList terminalList) {
        this.terminalList = terminalList;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }

    public List<VariableList> getVariableLists() {
        return variableLists;
    }

    public void setVariableLists(List<VariableList> variableLists) {
        this.variableLists = variableLists;
    }

    public List<CommandList> getCommandLists() {
        return commandLists;
    }

    public void setCommandLists(List<CommandList> commandLists) {
        this.commandLists = commandLists;
    }
}

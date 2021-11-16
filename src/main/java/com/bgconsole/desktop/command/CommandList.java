package com.bgconsole.desktop.command;

import java.util.ArrayList;
import java.util.List;

public class CommandList {

    private String absolutePath;

    private List<Command> commands;

    public CommandList(String absolutePath) {
        this.absolutePath = absolutePath;
        commands = new ArrayList<>();
    }

    public void setNewList(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }
}

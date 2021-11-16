package com.bgconsole.desktop.variable;

import com.bgconsole.desktop.Config;

import java.util.ArrayList;
import java.util.List;

public class VariableList {

    private Config config;

    private List<Variable> variables;

    public VariableList(Config config) {
        this.config = config;
        variables = new ArrayList<>();
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setNewVariables(List<Variable> variables) {
        this.variables = variables;
    }

    public Config getConfig() {
        return config;
    }
}

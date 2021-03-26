package cloud.dest.terminal.variable;

import cloud.dest.terminal.Config;

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

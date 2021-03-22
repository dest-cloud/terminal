package cloud.dest.terminal.variable;

import java.util.ArrayList;
import java.util.List;

public class VariableList {

    private List<Variable> variables;

    public VariableList() {
        variables = new ArrayList<>();
    }

    public List<Variable> getVariables() {
        return variables;
    }

    public void setNewVariables(List<Variable> variables) {
        this.variables = variables;
    }
}

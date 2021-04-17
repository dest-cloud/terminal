package cloud.dest.terminal.workspace;

import java.util.List;

public class Workspace {

    private String id;
    private String name;

    private List<WorkspaceCommand> commands;
    private List<WorkspaceVariable> variables;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<WorkspaceCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<WorkspaceCommand> commands) {
        this.commands = commands;
    }

    public List<WorkspaceVariable> getVariables() {
        return variables;
    }

    public void setVariables(List<WorkspaceVariable> variables) {
        this.variables = variables;
    }
}

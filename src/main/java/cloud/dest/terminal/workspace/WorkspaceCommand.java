package cloud.dest.terminal.workspace;

public class WorkspaceCommand {

    private String id;
    private String name;
    private String namespace;
    private String path;

    public WorkspaceCommand() {
    }

    public WorkspaceCommand(String id, String name, String namespace, String path) {
        this.id = id;
        this.name = name;
        this.namespace = namespace;
        this.path = path;
    }

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

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

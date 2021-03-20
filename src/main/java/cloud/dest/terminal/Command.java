package cloud.dest.terminal;

public class Command {

    private String name;
    private String alias;
    private String command;
    private String shellType;
    private String consoleId;
    private String execBefore;
    private String execAfter;

    public Command() {

    }

    public Command(String name, String alias, String command, String shellType, String consoleId, String execBefore, String execAfter) {
        this.name = name;
        this.alias = alias;
        this.command = command;
        this.shellType = shellType;
        this.consoleId = consoleId;
        this.execBefore = execBefore;
        this.execAfter = execAfter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getShellType() {
        return shellType;
    }

    public void setShellType(String shellType) {
        this.shellType = shellType;
    }

    public String getConsoleId() {
        return consoleId;
    }

    public void setConsoleId(String consoleId) {
        this.consoleId = consoleId;
    }

    public String getExecBefore() {
        return execBefore;
    }

    public void setExecBefore(String execBefore) {
        this.execBefore = execBefore;
    }

    public String getExecAfter() {
        return execAfter;
    }

    public void setExecAfter(String execAfter) {
        this.execAfter = execAfter;
    }
}

package cloud.dest.terminal;

import java.util.ArrayList;
import java.util.List;

public class CommandList {

    private List<Command> commands;

    public CommandList() {
//        commands = new ArrayList<>(){{
//            add(new Command("Java version", "java v", "java -version", "term1", null, null));
//            add(new Command("Current dir", "pwd", "pwd", "term1", null, null));
//            add(new Command("Docker list", "dock ps", "docker ps", "term2", null, null));
//            add(new Command("Karaf client", "karaf client", "docker exec -it stemys_karaf /opt/karaf/bin/client", "term3", null, null));
//            add(new Command("Karaf list", "karaf list", "list", "term3", null, null));
//            add(new Command("Karaf log", "karaf log", "log:tail", "term3", null, null));
//        }};
        commands = new ArrayList<>();
    }

    public void setNewList(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return commands;
    }
}

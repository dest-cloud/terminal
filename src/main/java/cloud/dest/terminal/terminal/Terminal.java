package cloud.dest.terminal.terminalmanager;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TerminalConfig;
import javafx.scene.paint.Color;

public class Terminal {

    private String name;
    private String id;
    private TerminalTab terminal;

    public Terminal(String id) {
        this(id, id);
    }

    public Terminal(String id, String name) {
        this.id = id;
        this.name = name;

        TerminalConfig darkConfig = new TerminalConfig();
        darkConfig.setBackgroundColor(Color.rgb(16, 16, 16));
        darkConfig.setForegroundColor(Color.rgb(240, 240, 240));
        darkConfig.setCursorColor(Color.rgb(255, 0, 0, 0.5));

        TerminalBuilder terminalBuilder = new TerminalBuilder(darkConfig);
        terminal = terminalBuilder.newTerminal();
        terminal.setId(id);
        terminal.setText(name != null ? name : id);
        terminal.setClosable(true);
        terminal.setContextMenu(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TerminalTab getTerminalTab() {
        return terminal;
    }

    public void setTerminal(TerminalTab terminal) {
        this.terminal = terminal;
    }
}

module terminal {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.kodedu.terminalfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens cloud.dest.terminal to javafx.fxml;
    exports cloud.dest.terminal;
}
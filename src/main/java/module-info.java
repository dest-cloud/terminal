module terminal {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.kodedu.terminalfx;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
//    requires jnativehook;
    requires java.logging;
    requires  simplenativehooks;

    opens cloud.dest.terminal to javafx.fxml;
    opens cloud.dest.terminal.ui.vareditor to javafx.fxml;
    opens cloud.dest.terminal.ui.commandeditor to javafx.fxml;
    exports cloud.dest.terminal;
    exports cloud.dest.terminal.variable;
    exports cloud.dest.terminal.command;
    exports cloud.dest.terminal.config;
    exports cloud.dest.terminal.terminal;
    exports cloud.dest.terminal.environment;
}
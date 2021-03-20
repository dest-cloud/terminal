module terminal {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.kodedu.terminalfx;
//    requires pty4j;
//    requires org.apache.commons.lang3;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    opens cloud.dest.terminal to javafx.fxml;
    exports cloud.dest.terminal;
}
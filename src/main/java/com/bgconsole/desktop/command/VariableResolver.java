package com.bgconsole.desktop.command;

import java.util.Optional;

public interface VariableResolver {
    Optional<String> resolve(String variable);
}

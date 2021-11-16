package com.bgconsole.desktop.variable;

import com.bgconsole.desktop.Config;
import com.bgconsole.desktop.environment.Environment;
import com.bgconsole.desktop.utils.ParseYAMLFile;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VariableServiceImpl implements VariableService {

    private static final List<Variable> systemVariables = new ArrayList<>() {{
        add(new Variable(SYS_HOME_DIR, System.getProperty("user.home")));
        add(new Variable(SYS_FILE_SEPARATOR, FileSystems.getDefault().getSeparator()));
        add(new Variable(SYS_NEW_LINE, System.getProperty("line.separator")));
    }};

    @Override
    public List<Variable> getSystemVariable() {
        return systemVariables;
    }

    @Override
    public Optional<Variable> findVar(Environment environment, String var) {
        for (VariableList variables : environment.getVariableLists()) {
            for (Variable variable : variables.getVariables()) {
                if (variable.getVariable().equals(var)) {
                    return Optional.of(variable);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Variable> findVars(Environment environment, String var) {
        List<Variable> variables = new ArrayList<>();
        for (VariableList variableList : environment.getVariableLists()) {
            for (Variable variable : variableList.getVariables()) {
                if (variable.getVariable().equals(var)) {
                    variables.add(variable);
                }
            }
        }
        return variables;
    }

    @Override
    public VariableList loadVariables(Config config) {
        VariableList variables = new VariableList(config);
        try {
            variables.setNewVariables(ParseYAMLFile.readVariables(config.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return variables;
    }

}

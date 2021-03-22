package cloud.dest.terminal.variable;

import cloud.dest.terminal.Config;
import cloud.dest.terminal.environment.Environment;
import cloud.dest.terminal.utils.ParseJsonFile;

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

    public VariableList loadVariables(Config config) {
        VariableList variables = new VariableList();
        try {
            variables.setNewVariables(ParseJsonFile.readVariables(config.getAbsolutePath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return variables;
    }

}

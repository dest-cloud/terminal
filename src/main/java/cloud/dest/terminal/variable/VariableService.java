package cloud.dest.terminal.variable;

import cloud.dest.terminal.Config;
import cloud.dest.terminal.environment.Environment;

import java.util.List;
import java.util.Optional;

public interface VariableService {

    String SYS_HOME_DIR = "SYS_HOME_DIR";

    String SYS_FILE_SEPARATOR = "SYS_FILE_SEP";

    String SYS_NEW_LINE = "SYS_RET";

    List<Variable> getSystemVariable();

    Optional<Variable> findVar(Environment environment, String var);

    VariableList loadVariables(Config config);
}

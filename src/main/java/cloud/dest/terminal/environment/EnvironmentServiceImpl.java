package cloud.dest.terminal.environment;

import cloud.dest.terminal.Config;
import cloud.dest.terminal.variable.VariableList;
import cloud.dest.terminal.variable.VariableService;

import java.nio.file.Path;

public class EnvironmentServiceImpl implements EnvironmentService {

    private final VariableService variableService;

    public EnvironmentServiceImpl(VariableService variableService) {
        this.variableService = variableService;
    }

    @Override
    public Environment initEnv(String id, Path dir) {
        Environment environment = new Environment(id, dir);
        VariableList sysVar = new VariableList(new Config("file", "SYS_VAR", ""));
        sysVar.setNewVariables(variableService.getSystemVariable());
        environment.getVariableLists().add(sysVar);
        return environment;
    }

}

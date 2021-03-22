package cloud.dest.terminal.environment;

import java.nio.file.Path;

public interface EnvironmentService {

    Environment initEnv(String id, Path dir);

}

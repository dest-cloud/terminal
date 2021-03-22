package cloud.dest.terminal.config;

import cloud.dest.terminal.Config;
import cloud.dest.terminal.environment.Environment;

public interface ConfigService {
    Environment loadConfig(Environment environment, Config config);
}

package com.bgconsole.desktop.config;

import com.bgconsole.desktop.Config;
import com.bgconsole.desktop.environment.Environment;

public interface ConfigService {
    Environment loadConfig(Environment environment, Config config);
}

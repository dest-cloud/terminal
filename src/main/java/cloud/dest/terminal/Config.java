package cloud.dest.terminal;

import java.util.ArrayList;
import java.util.List;

public class Config {

    private String type;
    private String config;
    private String absolutePath;
    private List<Config> configs;

    public Config(String type, String config, String absolutePath) {
        this.type = type;
        this.config = config;
        this.absolutePath = absolutePath;
        this.configs = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public List<Config> getConfigs() {
        return configs;
    }

    public void setConfigs(List<Config> configs) {
        this.configs = configs;
    }
}

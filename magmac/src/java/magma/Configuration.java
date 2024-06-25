package magma;

import magma.api.collect.Map;
import magma.java.Set;

import java.nio.file.Path;

public final class Configuration {
    private final Map<String, Build> builds;

    public Configuration(Map<String, Build> builds) {
        this.builds = builds;
    }

    public Path sourceDirectory() {
        return builds.streamValues().head().orElsePanic().sourceDirectory();
    }

    public Path targetDirectory() {
        return builds.streamValues().head().orElsePanic().targetDirectory();
    }

    public Path debugDirectory() {
        return builds.streamValues().head().orElsePanic().debugDirectory();
    }
}
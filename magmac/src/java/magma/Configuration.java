package magma;

import magma.api.collect.List;
import magma.api.collect.stream.Stream;

public final class Configuration {
    private final List<Build> builds;

    public Configuration(List<Build> builds) {
        this.builds = builds;
    }

    public Stream<Build> streamBuilds() {
        return builds.stream();
    }
}
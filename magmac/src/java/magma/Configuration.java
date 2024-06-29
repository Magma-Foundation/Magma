package magma;

import magma.api.contain.List;
import magma.api.contain.stream.Stream;

public final class Configuration {
    private final List<Build> builds;

    public Configuration(List<Build> builds) {
        this.builds = builds;
    }

    public Stream<Build> streamBuilds() {
        return builds.stream();
    }
}
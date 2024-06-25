package magma;

import magma.api.Tuple;
import magma.api.collect.Map;
import magma.api.collect.stream.Stream;

public final class Configuration {
    private final Map<String, Build> builds;

    public Configuration(Map<String, Build> builds) {
        this.builds = builds;
    }

    public Stream<Tuple<String, Build>> streamBuilds() {
        return builds.streamEntries();
    }
}
package magma.app.compile;

import magma.api.collect.Iter;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.text.Strings;
import magma.app.Location;
import magma.app.Platform;
import magma.app.io.Source;

public record Context(Platform platform, Option<Location> maybeLocation, List<Source> sources) {
    public Iter<Source> iterSources() {
        return this.sources.iter();
    }

    public boolean hasPlatform(Platform platform) {
        return this.platform() == platform;
    }

    public   Option<Source> findSource(String name) {
        return this.iterSources()
                .filter((Source source) -> Strings.equalsTo(source.createLocation().name(), name))
                .next();
    }
}
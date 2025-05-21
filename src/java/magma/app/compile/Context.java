package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.Iter;
import magma.api.collect.list.List;
import magma.api.option.Option;
import magma.api.option.Some;
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

    public Option<Source> findSource(String name) {
        return this.iterSources()
                .filter((Source source) -> Strings.equalsTo(source.createLocation().name(), name))
                .next();
    }

    public Context withLocation(Location location) {
        return new Context(this.platform(), new Some<Location>(location), this.sources());
    }

    public Context addSource(Source source) {
        return new Context(this.platform(), this.maybeLocation(), this.sources().addLast(source));
    }

    public List<String> findNamespaceOrEmpty() {
        return this.maybeLocation()
                .map(Location::namespace)
                .orElse(Lists.empty());
    }

    public String findNameOrEmpty() {
        return this.maybeLocation().map(Location::name).orElse("");
    }

    public Context withPlatform(Platform platform) {
        return new Context(platform, this.maybeLocation(), this.sources());
    }
}
package magma.app.compile;

import jvm.api.collect.list.Lists;
import magma.api.collect.Iter;
import magma.api.collect.list.List;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.text.Strings;
import magma.app.Location;
import magma.app.Platform;
import magma.app.io.Source;

public record ImmutableContext(
        Option<Platform> maybePlatform,
        Option<Location> maybeLocation,
        List<Source> sources
) implements Context {
    static Context createEmpty() {
        return new ImmutableContext(new None<Platform>(), new None<Location>(), Lists.empty());
    }

    @Override
    public Iter<Source> iterSources() {
        return this.sources.iter();
    }

    @Override
    public boolean hasPlatform(Platform platform) {
        return this.maybePlatform
                .filter((Platform thisPlatform) -> {
                    return thisPlatform == platform;
                })
                .isPresent();
    }

    @Override
    public Option<Source> findSource(String name) {
        return this.iterSources()
                .filter((Source source) -> {
                    return Strings.equalsTo(source.createLocation().name(), name);
                })
                .next();
    }

    @Override
    public Context withLocation(Location location) {
        return new ImmutableContext(this.maybePlatform, new Some<Location>(location), this.sources());
    }

    @Override
    public Context addSource(Source source) {
        return new ImmutableContext(this.maybePlatform, this.maybeLocation(), this.sources().addLast(source));
    }

    @Override
    public List<String> findNamespaceOrEmpty() {
        return this.maybeLocation()
                .map(Location::namespace)
                .orElse(Lists.empty());
    }

    @Override
    public String findNameOrEmpty() {
        return this.maybeLocation().map(Location::name).orElse("");
    }

    @Override
    public Context withPlatform(Platform platform) {
        return new ImmutableContext(new Some<Platform>(platform), this.maybeLocation(), this.sources());
    }
}
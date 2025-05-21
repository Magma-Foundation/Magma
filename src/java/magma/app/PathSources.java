package magma.app;

import magma.api.collect.list.Iterable;
import magma.api.collect.list.ListCollector;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.result.Result;
import magma.app.io.PathSource;
import magma.app.io.Source;

public record PathSources(Path sourceDirectory) implements Sources {
    @Override
    public Result<Iterable<Source>, IOError> listSources() {
        return this.sourceDirectory()
                .walk()
                .mapValue((Iterable<Path> children) -> this.retainSources(children));
    }

    @Override
    public Iterable<Source> retainSources(Iterable<Path> children) {
        return children.iter()
                .filter((Path source) -> source.endsWith(".java"))
                .<Source>map((Path child) -> new PathSource(this.sourceDirectory, child))
                .collect(new ListCollector<>());
    }
}
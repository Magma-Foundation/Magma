package magma.app;

import magma.api.collect.list.List;
import magma.api.collect.list.ListCollector;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.result.Result;
import magma.app.io.PathSource;
import magma.app.io.Source;

public record Sources(Path sourceDirectory) {
    Result<List<Source>, IOError> listSources() {
        return this.sourceDirectory()
                .walk()
                .mapValue((List<Path> children) -> this.retainSources(children));
    }

    private List<Source> retainSources(List<Path> children) {
        return children.query()
                .filter((Path source) -> source.endsWith(".java"))
                .<Source>map((Path child) -> new PathSource(this.sourceDirectory, child))
                .collect(new ListCollector<>());
    }
}
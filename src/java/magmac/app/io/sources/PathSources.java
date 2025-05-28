package magmac.app.io.sources;

import magmac.api.collect.list.List;
import magmac.api.iter.Iter;
import magmac.api.iter.collect.ListCollector;
import magmac.api.iter.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.io.IOResult;
import magmac.app.io.InlineIOResult;
import magmac.app.io.SafeFiles;
import magmac.app.stage.Unit;
import magmac.app.stage.UnitSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record PathSources(Path root) implements Sources {
    private static IOResult<Unit<String>> getTuple2IOResult(PathSource source) {
        return source.read().mapValue((String input) -> new Unit<>(source.computeLocation(), input));
    }

    @Override
    public IOResult<UnitSet<String>> readAll() {
        return SafeFiles.walk(this.root)
                .flatMapValue((Iter<Path> sources) -> this.apply(sources));
    }

    private IOResult<UnitSet<String>> apply(Iter<Path> sources) {
        return new InlineIOResult<>(this.getCollect(sources));
    }

    private Result<UnitSet<String>, IOException> getCollect(Iter<Path> sources) {
        return this.getCollected(sources)
                .iter()
                .map((PathSource source) -> PathSources.getTuple2IOResult(source))
                .map((IOResult<Unit<String>> tuple2IOResult) -> tuple2IOResult.result())
                .collect(new ResultCollector<>(new UnitSetCollector<>()));
    }

    private List<PathSource> getCollected(Iter<Path> sources) {
        return sources.filter((Path path1) -> Files.isRegularFile(path1))
                .filter((Path file) -> file.toString().endsWith(".java"))
                .map((Path path) -> new PathSource(this.root, path))
                .collect(new ListCollector<>());
    }
}
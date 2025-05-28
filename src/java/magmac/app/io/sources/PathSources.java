package magmac.app.io.sources;

import magmac.api.Tuple2;
import magmac.api.collect.list.List;
import magmac.api.collect.map.Map;
import magmac.api.collect.map.MapCollector;
import magmac.api.iter.Iter;
import magmac.api.iter.collect.ListCollector;
import magmac.api.iter.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.io.IOResult;
import magmac.app.io.InlineIOResult;
import magmac.app.io.Location;
import magmac.app.io.SafeFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record PathSources(Path root) implements Sources {
    @Override
    public IOResult<Map<Location, String>> readAll() {
        return SafeFiles.walk(this.root).flatMapValue((Iter<Path> sources) -> this.apply(sources));
    }

    private IOResult<Map<Location, String>> apply(Iter<Path> sources) {
        return new InlineIOResult<>(this.getCollect(sources));
    }

    private Result<Map<Location, String>, IOException> getCollect(Iter<Path> sources) {
        return this.getCollected(sources)
                .iter()
                .map((PathSource source) -> this.getTuple2IOResult(source))
                .map((IOResult<Tuple2<Location, String>> tuple2IOResult) -> tuple2IOResult.result())
                .collect(new ResultCollector<>(new MapCollector<>()));
    }

    private IOResult<Tuple2<Location, String>> getTuple2IOResult(PathSource source) {
        return source.read().mapValue((String input) -> new Tuple2<>(source.computeLocation(), input));
    }

    private List<PathSource> getCollected(Iter<Path> sources) {
        return sources.filter((Path path1) -> Files.isRegularFile(path1))
                .filter((Path file) -> file.toString().endsWith(".java"))
                .map((Path path) -> new PathSource(this.root, path))
                .collect(new ListCollector<>());
    }
}
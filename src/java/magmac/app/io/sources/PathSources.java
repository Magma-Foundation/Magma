package magmac.app.io.sources;

import magmac.api.Tuple2;
import magmac.api.collect.map.MapCollector;
import magmac.api.iter.collect.ResultCollector;
import magmac.api.iter.Iter;
import magmac.app.io.IOResult;
import magmac.app.io.InlineIOResult;
import magmac.app.io.Location;
import magmac.app.io.SafeFiles;

import java.nio.file.Files;
import java.nio.file.Path;
import magmac.api.collect.map.Map;

public record PathSources(Path root) implements Sources {
    @Override
    public IOResult<Map<Location, String>> readAll() {
        return SafeFiles.walk(this.root).flatMapValue(sources -> this.apply(sources));
    }

    private IOResult<Map<Location, String>> apply(Iter<Path> sources) {
        return new InlineIOResult<>(sources.filter(Files::isRegularFile)
                .filter(file -> file.toString().endsWith(".java"))
                .map(path -> new PathSource(this.root, path))
                .map(source -> source.read().mapValue(input -> new Tuple2<>(source.computeLocation(), input)))
                .map(IOResult::result)
                .collect(new ResultCollector<>(new MapCollector<>())));
    }
}
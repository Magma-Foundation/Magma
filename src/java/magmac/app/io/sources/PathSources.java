package magmac.app.io.sources;

import magmac.api.Tuple2;
import magmac.api.collect.collect.MapCollector;
import magmac.api.collect.collect.ResultCollector;
import magmac.api.result.Result;
import magmac.app.io.Location;
import magmac.app.io.SafeFiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public record PathSources(Path root) implements Sources {
    @Override
    public Result<Map<Location, String>, IOException> readAll() {
        return SafeFiles.walk(this.root).flatMapValue(sources -> sources.filter(Files::isRegularFile)
                .filter(file -> file.toString().endsWith(".java"))
                .map(path -> new PathSource(this.root, path))
                .map(source -> source.read().mapValue(input -> new Tuple2<>(source.computeLocation(), input)))
                .collect(new ResultCollector<>(new MapCollector<>())));
    }
}
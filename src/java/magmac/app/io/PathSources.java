package magmac.app.io;

import jvm.io.SafeFiles;
import magmac.api.Tuple2;
import magmac.api.collect.Iters;
import magmac.api.collect.MapCollector;
import magmac.api.collect.ResultCollector;
import magmac.api.result.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record PathSources(Path root) implements Sources {
    public Set<Source> collectUnits(Set<Path> sources) {
        return sources.stream()
                .filter(Files::isRegularFile)
                .filter(file -> file.toString().endsWith(".java"))
                .map(path -> new PathSource(this.root, path))
                .collect(Collectors.toSet());
    }

    private Result<Set<Source>, IOException> collect() {
        return SafeFiles.walk(this.root).mapValue(this::collectUnits);
    }

    @Override
    public Result<Map<Location, String>, IOException> readAll() {
        return collect().flatMapValue(sources1 -> Iters.fromSet(sources1)
                .map(source -> source.read().mapValue(input -> new Tuple2<>(source.computeLocation(), input)))
                .collect(new ResultCollector<>(new MapCollector<>())));
    }
}
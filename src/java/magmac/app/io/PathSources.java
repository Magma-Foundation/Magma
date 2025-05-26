package magmac.app.io;

import jvm.io.SafeFiles;
import magmac.api.result.Result;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public record PathSources(Path root) implements Sources {
    public static Set<Source> collectUnits(Set<Path> sources) {
        return sources.stream()
                .filter(Files::isRegularFile)
                .filter(file -> file.toString().endsWith(".java"))
                .map(path -> new Source(path))
                .collect(Collectors.toSet());
    }

    @Override
    public Result<Set<Source>, IOException> collect() {
        return SafeFiles.walk(this.root).mapValue(PathSources::collectUnits);
    }
}
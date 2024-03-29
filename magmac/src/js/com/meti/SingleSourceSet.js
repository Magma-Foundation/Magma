

import java.nio.file.Files
import java.nio.file.Path
import java.util.HashSet
import java.util.Set
import java.util.stream.Collectors

public record SingleSourceSet(Path source) implements SourceSet {
    private Set<Path> collect1() {
        var set = new HashSet<Path>();
        if (Files.exists(source())) {
            set.add(source());
        }
        return set;
    }

    @Override
    public String findExtension() {
        return ".java";
    }

    @Override
    public Set<PathSource> collect() {
        return collect1()
                .stream()
                .map((Path root) -> new PathSource(root.getParent(), root))
                .collect(Collectors.toSet());
    }
}
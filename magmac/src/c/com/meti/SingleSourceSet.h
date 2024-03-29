#ifndef SingleSourceSet_H
#define SingleSourceSet_H
#include <java.nio.file.h>
#include <java.nio.file.h>
#include <java.util.h>
#include <java.util.h>
#include <java.util.stream.h>public record SingleSourceSet(Path source) implements SourceSet {
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
#endif
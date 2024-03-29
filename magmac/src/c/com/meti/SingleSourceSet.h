import { Files } from java.nio.fileimport { Path } from java.nio.fileimport { HashSet } from java.utilimport { Set } from java.utilimport { Collectors } from java.util.streampublic record SingleSourceSet(Path source) implements SourceSet {
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
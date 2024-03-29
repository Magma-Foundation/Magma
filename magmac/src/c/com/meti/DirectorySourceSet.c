#include "DirectorySourceSet.c"
String findExtension(struct DirectorySourceSet implements SourceSet* __self__){
        return extension;
    }
Set<Path> collect1(struct DirectorySourceSet implements SourceSet* __self__){
        try (var stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(extension))
                    .collect(Collectors.toSet());
        }
    }
Set<PathSource> collect(struct DirectorySourceSet implements SourceSet* __self__){
        return collect1().stream()
                .map((Path child) -> new PathSource(root, child))
                .collect(Collectors.toSet());
    }
struct DirectorySourceSet implements SourceSet DirectorySourceSet implements SourceSet(){
    private final Path root
    private final String extension

    public DirectorySourceSet(Path root, String extension) {
        this.root = root;
        this.extension = extension;
    }
	struct DirectorySourceSet implements SourceSet this = {};
	return this;
}

#include <java.io.h>
#include <java.nio.file.h>
#include <java.nio.file.h>
#include <java.util.h>
#include <java.util.stream.h>
function DirectorySourceSet implements SourceSet(){
    private final Path root;
    private final String extension;

    public DirectorySourceSet(Path root, String extension) {
        this.root = root;
        this.extension = extension;
    }

    @Override
    public String findExtension() {
        return extension;
    }

    private Set<Path> collect1() throws IOException {
        try (var stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(extension))
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public Set<PathSource> collect() throws IOException {
        return collect1().stream()
                .map((Path child) -> new PathSource(root, child))
                .collect(Collectors.toSet());
    }
}
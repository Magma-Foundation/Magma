import { IOException }import { Files }import { Path }import { Set }import { Collectors }from java.iofrom java.nio.filefrom java.nio.filefrom java.utilfrom java.util.stream
function DirectorySourceSet implements SourceSet(){private final Path root
    private final String extension

    public DirectorySourceSet(Path root, String extension) {
        this.root = root;
        this.extension = extension;
    }
	pub def findExtension() : String => {
        return extension;
    }
	def collect1() : Set<Path> => {
        try (var stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(extension))
                    .collect(Collectors.toSet());
        }
    }
	pub def collect() : Set<PathSource> => {
        return collect1().stream()
                .map((Path child) -> new PathSource(root, child))
                .collect(Collectors.toSet());
    }
	return {};
}
module.exports = {
	DirectorySourceSet implements SourceSet
};
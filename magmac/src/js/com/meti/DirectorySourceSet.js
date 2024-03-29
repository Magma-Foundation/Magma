const { IOException } = require("java.io");
const { Files } = require("java.nio.file");
const { Path } = require("java.nio.file");
const { Set } = require("java.util");
const { Collectors } = require("java.util.stream");
function DirectorySourceSet implements SourceSet(){
    private final Path root
    private final String extension

    public DirectorySourceSet(Path root, String extension) {
        this.root = root;
        this.extension = extension;
    }
	function findExtension(){
        return extension;
    }
	function collect1(){
        try (var stream = Files.walk(root)) {
            return stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(extension))
                    .collect(Collectors.toSet());
        }
    }
	function collect(){
        return collect1().stream()
                .map((Path child) -> new PathSource(root, child))
                .collect(Collectors.toSet());
    }
	return {findExtensioncollect};
}
module.exports = {
	DirectorySourceSet implements SourceSet
};
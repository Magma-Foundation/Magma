
const { IOException } = require("java.io");
const { Files } = require("java.nio.file");
const { Path } = require("java.nio.file");
const { ArrayList } = require("java.util");
const { Arrays } = require("java.util");
const { Objects } = require("java.util");
const { Collectors } = require("java.util.stream");public final class Application {
    private final SourceSet sourceSet;
    private final Path targetRoot;
    private final String[] targetExtensions;

    public Application(SourceSet sourceSet, Path targetRoot, String... targetExtensions) {
        this.sourceSet = sourceSet;
        this.targetRoot = targetRoot;
        this.targetExtensions = targetExtensions;
    }

    private static String compile(String input, String extension) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        for (int i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        lines.removeIf(String::isBlank);

        if (extension.equals(".java")) {
            lines.removeIf(line -> line.startsWith("package"));
        }

        return lines.stream()
                .map(String::strip)
                .filter(value -> !value.isEmpty())
                .map(line -> {
                    if (line.startsWith("import ")) {
                        var segmentString = line.substring("import ".length()).strip();

                        var childStart = segmentString.indexOf('{');
                        var childEnd = segmentString.indexOf('}');

                        if (extension.equals(".java")) {
                            var arrays = Arrays.asList(segmentString.split("\\."));
                            if (arrays.isEmpty()) return line;
                            if (arrays.size() == 1) return "import " + arrays.get(0);

                            var child = arrays.get(arrays.size() - 1);
                            var parent = String.join(".", arrays.subList(0, arrays.size() - 1));
                            return "\nimport { %s } from %s;".formatted(child, parent);
                        } else {
                            var child = segmentString.substring(childStart + 1, childEnd).strip();
                            var fromIndex = segmentString.indexOf("from ", childEnd);
                            if(fromIndex == -1) return line;

                            var parent = segmentString.substring(fromIndex + "from ".length()).strip();
                            return "\nconst { " + child + " } = require(\"" + parent + "\");";
                        }
                    } else {
                        return line;
                    }
                })
                .collect(Collectors.joining());
    }

    void run() throws IOException {
        var set = this.sourceSet().collect();

        for (var path : set) {
            var input = path.read();
            var package_ = path.findPackage();
            var name = path.findName();

            for (String targetExtension : targetExtensions) {
                var target = package_.stream()
                        .reduce(targetRoot, Path::resolve, (path1, path2) -> path2)
                        .resolve(name + targetExtension);

                var parent = target.getParent();
                if (!Files.exists(parent)) Files.createDirectories(parent);
                String output;
                output = compile(input, sourceSet.findExtension());
                Files.writeString(target, output);
            }
        }
    }

    public SourceSet sourceSet() {
        return sourceSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Application) obj;
        return Objects.equals(this.sourceSet, that.sourceSet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceSet);
    }

    @Override
    public String toString() {
        return "Application[" +
               "sourceSet=" + sourceSet + ']';
    }

}
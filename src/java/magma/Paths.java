package magma;

import java.nio.file.Path;

public class Paths {
    private record NIOPath(Path path) implements Main.Path_ {
        @Override
        public Main.Result<String, IOError> readString() {
            return Files.readString(path);
        }

        @Override
        public Main.Path_ resolveSibling(String sibling) {
            return new NIOPath(path.resolveSibling(sibling));
        }

        @Override
        public Main.Option<IOError> writeString(String output) {
            return Files.writeString(path, output);
        }
    }

    public static Main.Path_ get(String first, String... elements) {
        return new NIOPath(java.nio.file.Paths.get(first, elements));
    }
}

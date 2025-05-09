package magma;

import java.io.IOException;
import java.nio.file.Files;

class Main {
    private interface Path {
        void writeString(String input) throws IOException;

        String readString() throws IOException;

        Path resolve(String child);
    }

    private record JVMPath(java.nio.file.Path path) implements Path {
        @Override
        public void writeString(String input) throws IOException {
            Files.writeString(this.path(), input);
        }

        @Override
        public String readString() throws IOException {
            return Files.readString(this.path());
        }

        @Override
        public Path resolve(String child) {
            return new JVMPath(this.path.resolve(child));
        }
    }

    private static class Paths {
        private static Path resolveCurrentWorkingDirectory() {
            return new JVMPath(java.nio.file.Paths.get("."));
        }
    }

    void main() {
        var parent = Paths.resolveCurrentWorkingDirectory()
                .resolve("src")
                .resolve("java")
                .resolve("magma");

        var source = parent.resolve("main.mgs");
        var target = parent.resolve("main.c");

        try {
            var input = source.readString();
            target.writeString(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
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
        public static Path get(String first, String... more) {
            return new JVMPath(java.nio.file.Paths.get(first, more));
        }
    }

    void main() {
        var parent = Paths.get(".", "src", "java", "magma");
        var jvmPath = parent.resolve("main.mgs");
        var jvmPath1 = parent.resolve("main.c");
        try {
            var input = jvmPath.readString();
            jvmPath1.writeString(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
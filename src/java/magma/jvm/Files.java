package magma.jvm;

import magma.Actual;
import magma.api.io.Path;
import magma.app.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class Files {
    @Actual
    private record JVMPath(java.nio.file.Path path) implements Path {
        private record JVMIOError(IOException error) implements Main.IOError {
            @Override
            public String display() {
                var writer = new StringWriter();
                this.error.printStackTrace(new PrintWriter(writer));
                return writer.toString();
            }
        }

        @Override
        public Main.Option<Main.IOError> writeString(String output) {
            try {
                java.nio.file.Files.writeString(this.path, output);
                return new Main.None<Main.IOError>();
            } catch (IOException e) {
                return new Main.Some<Main.IOError>(new JVMPath.JVMIOError(e));
            }
        }

        @Override
        public Main.Result<String, Main.IOError> readString() {
            try {
                return new Main.Ok<String, Main.IOError>(java.nio.file.Files.readString(this.path));
            } catch (IOException e) {
                return new Main.Err<String, Main.IOError>(new JVMPath.JVMIOError(e));
            }
        }

        @Override
        public magma.api.io.Path resolveSibling(String siblingName) {
            return new JVMPath(this.path.resolveSibling(siblingName));
        }

        @Override
        public Main.Result<Main.List<magma.api.io.Path>, Main.IOError> walk() {
            try (Stream<java.nio.file.Path> stream = java.nio.file.Files.walk(this.path)) {
                return new Main.Ok<>(new Main.Lists.JVMList<>(stream.<magma.api.io.Path>map(JVMPath::new).toList()));
            } catch (IOException e) {
                return new Main.Err<>(new JVMPath.JVMIOError(e));
            }
        }

        @Override
        public String findFileName() {
            return this.path.getFileName().toString();
        }

        @Override
        public boolean endsWith(String suffix) {
            return this.path.toString().endsWith(suffix);
        }
    }

    @Actual
    public static Path get(String first, String... more) {
        return new JVMPath(Paths.get(first, more));
    }
}

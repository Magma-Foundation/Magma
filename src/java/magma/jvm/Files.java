package magma.jvm;

import magma.annotate.Actual;
import magma.api.collect.List;
import magma.api.collect.Lists;
import magma.api.collect.RangeHead;
import magma.api.collect.query.HeadedQuery;
import magma.api.collect.query.Query;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.option.Option;
import magma.api.result.Result;
import magma.app.Main;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class Files {
    @Actual
    private record JVMPath(java.nio.file.Path path) implements Path {
        private record JVMIOError(IOException error) implements IOError {
            @Override
            public String display() {
                var writer = new StringWriter();
                this.error.printStackTrace(new PrintWriter(writer));
                return writer.toString();
            }
        }

        private static java.nio.file.Path fromPath(Path path) {
            return path.query()
                    .foldWithMapper(Paths::get, java.nio.file.Path::resolve)
                    .orElse(Paths.get("."));
        }

        @Override
        public Option<IOError> writeString(String output) {
            try {
                java.nio.file.Files.writeString(this.path, output);
                return new Main.None<IOError>();
            } catch (IOException e) {
                return new Main.Some<IOError>(new JVMPath.JVMIOError(e));
            }
        }

        @Override
        public Result<String, IOError> readString() {
            try {
                return new Main.Ok<String, IOError>(java.nio.file.Files.readString(this.path));
            } catch (IOException e) {
                return new Main.Err<String, IOError>(new JVMPath.JVMIOError(e));
            }
        }

        @Override
        public magma.api.io.Path resolveSibling(String siblingName) {
            return new JVMPath(this.path.resolveSibling(siblingName));
        }

        @Override
        public Result<List<Path>, IOError> walk() {
            try (Stream<java.nio.file.Path> stream = java.nio.file.Files.walk(this.path)) {
                return new Main.Ok<>(new Lists.JVMList<>(stream.<magma.api.io.Path>map(JVMPath::new).toList()));
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

        @Override
        public Path relativize(Path source) {
            return new JVMPath(this.path.relativize(JVMPath.fromPath(source)));
        }

        @Override
        public Path getParent() {
            return new JVMPath(this.path.getParent());
        }

        @Override
        public Query<String> query() {
            return new HeadedQuery<>(new RangeHead(this.path.getNameCount()))
                    .map((Integer index) -> this.path.getName(index).toString());
        }

        @Override
        public Path resolveChildSegments(List<String> children) {
            return children.query().foldWithInitial(this, Path::resolveChild);
        }

        @Override
        public Path resolveChild(String name) {
            return new JVMPath(this.path.resolve(name));
        }

        @Override
        public boolean exists() {
            return java.nio.file.Files.exists(this.path);
        }

        @Override
        public Option<IOException> createDirectories() {
            try {
                java.nio.file.Files.createDirectories(this.path);
                return new Main.None<>();
            } catch (IOException e) {
                return new Main.Some<>(e);
            }
        }
    }

    @Actual
    public static Path get(String first, String... more) {
        return new JVMPath(Paths.get(first, more));
    }
}

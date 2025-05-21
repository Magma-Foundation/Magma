package jvm.api.io;

import magma.annotate.Actual;
import magma.annotate.Namespace;
import jvm.api.collect.list.JVMList;
import magma.api.collect.list.List;
import magma.api.collect.head.RangeHead;
import magma.api.collect.head.HeadedIter;
import magma.api.collect.Iter;
import magma.api.io.IOError;
import magma.api.io.Path;
import magma.api.option.None;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Namespace
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
                return new None<IOError>();
            } catch (IOException e) {
                return new Some<IOError>(new JVMPath.JVMIOError(e));
            }
        }

        @Override
        public Result<String, IOError> readString() {
            try {
                return new Ok<String, IOError>(java.nio.file.Files.readString(this.path));
            } catch (IOException e) {
                return new Err<String, IOError>(new JVMPath.JVMIOError(e));
            }
        }

        @Override
        public magma.api.io.Path resolveSibling(String siblingName) {
            return new JVMPath(this.path.resolveSibling(siblingName));
        }

        @Override
        public Result<List<Path>, IOError> walk() {
            try (Stream<java.nio.file.Path> stream = java.nio.file.Files.walk(this.path)) {
                return new Ok<List<Path>, IOError>(new JVMList<Path>(stream.<magma.api.io.Path>map(JVMPath::new).toList()));
            } catch (IOException e) {
                return new Err<List<Path>, IOError>(new JVMPath.JVMIOError(e));
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
        public Iter<String> query() {
            return new HeadedIter<Integer>(new RangeHead(this.path.getNameCount()))
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
        public Option<IOError> createDirectories() {
            try {
                java.nio.file.Files.createDirectories(this.path);
                return new None<IOError>();
            } catch (IOException e) {
                return new Some<IOError>(new JVMIOError(e));
            }
        }
    }

    @Actual
    public static Path get(String first, String... more) {
        return new JVMPath(Paths.get(first, more));
    }
}

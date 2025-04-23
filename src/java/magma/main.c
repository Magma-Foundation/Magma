/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Paths; *//* 
import java.util.function.BiFunction; *//* 
import java.util.function.Function; *//* 

 */struct Main {/* sealed interface Option<T> permits Some, None {
        <R> Option<R> map(Function<T, R> mapper);
    } *//* interface Head<T> {
        Option<T> next();
    } *//* interface List<T> {
        void add(T element);

        Iterator<T> iter();
    } *//* public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            }
            else {
                return new None<>();
            }
        }
    } *//* record Iterator<T>(Head<T> head) {
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new Iterator<>(() -> this.head.next().map(mapper));
        }

        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                switch (this.head.next()) {
                    case Some<T>(var value) -> {
                        current = folder.apply(current, value);
                    }
                    case None<T> _ -> {
                        return current;
                    }
                }
            }
        }
    } *//* private static class State {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(Lists.emptyList(), new StringBuilder(), 0);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public State enter() {
            this.depth++;
            return this;
        }

        public State exit() {
            this.depth--;
            return this;
        }

        private State append(char c) {
            this.buffer.append(c);
            return this;
        }

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }
    } *//* private record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }
    } *//* private static final class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }
    } *//* void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var input = Files.readString(source);

            var target = source.resolveSibling("main.c");
            Files.writeString(target, this.compileRoot(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    } *//* private String compileRoot(String input) {
        return this.compileAll(input, this::compileRootSegment);
    } *//* private String compileAll(String input, Function<String, String> compiler) {
        return this.divide(input, new State())
                .segments
                .iter()
                .map(compiler::apply)
                .fold(new StringBuilder(), (builder, element) -> builder.append(element))
                .toString();
    } *//* private String compileRootSegment(String input) {
        var classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            var left = input.substring(0, classIndex);
            var right = input.substring(classIndex + "class ".length());
            var contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                var name = right.substring(0, contentStart).strip();
                var withEnd = right.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var inputContent = withEnd.substring(0, withEnd.length() - 1);
                    var outputContent = this.compileAll(inputContent, this::compileClassSegment);
                    return this.generatePlaceholder(left) + "struct " + name + " {" + outputContent + "};\n";
                }
            }
        }

        return this.generatePlaceholder(input);
    }

    private String compileClassSegment(String input) {
        var stripped = input.strip();
        if (stripped.endsWith(";")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            var nameSeparator = withoutEnd.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                var beforeName = withoutEnd.substring(0, nameSeparator).strip();
                var typeSeparator = beforeName.lastIndexOf(" ");
                if (typeSeparator >= 0) {
                    var beforeType = beforeName.substring(0, typeSeparator).strip();
                    var type = beforeName.substring(typeSeparator + " ".length()).strip();
                    var name = withoutEnd.substring(nameSeparator + " ".length()).strip();
                    return "\n\t" + this.generatePlaceholder(beforeType) + " " + this.compileType(type) + " " + name + ";";
                }
            }
        }

        return this.generatePlaceholder(stripped);
    }

    private String compileType(String type) {
        return this.generatePlaceholder(type);
    }

    private State divide(String input, State state) {
        var current = state;
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = this.foldStatementChar(current, c);
        }
        return current.advance();
    }

    private State foldStatementChar(State state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        if (c == '{') {
            return appended.enter();
        }
        if (c == '}') {
            return appended.exit();
        }
        else {
            return appended;
        }
    } *//* private String generatePlaceholder(String input) {
        return "/* " + input + " */";
    } *//*  */};
/*  */
/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Path; *//* 
import java.nio.file.Paths; *//* 
import java.util.function.BiFunction; *//* 
import java.util.function.Function; *//* class Main {
    sealed */struct Result<T, X> permits Ok, Err {
	/* Iterator<T> */ iter();
	/* add(T */ element);
	/* R> */ mapper);
	/* R> */ folder);
	/* Iterator<R>> */ mapper);
	/* concat(Iterator<T> */ other);
	/* Option<T> */ next();
	/* Option<T> */ next();
	/* List<String> */ segments;
	/* StringBuilder */ buffer;
	int depth;/* 

        private State(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } *//* 

        public State() {
            this(Lists.empty(), new StringBuilder(), 0);
        } *//* 

        private State append(char c) {
            this.buffer.append(c);
            return this;
        } *//* 

        private boolean isLevel() {
            return this.depth == 0;
        } *//* 

        private boolean isShallow() {
            return this.depth == 1;
        } *//* 

        private State enter() {
            this.depth = this.depth + 1;
            return this;
        } *//* 

        private State exit() {
            this.depth = this.depth - 1;
            return this;
        } *//* 

        private State advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } *//* 
    }

    private static class EmptyHead<T> implements Head<T> {
        @Override
        public Option<T> next() {
            return new None<>();
        } *//* 
    }

    public record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> switch (this.head.next()) {
                case None<T> _ -> new None<>();
                case Some<T>(T value) -> new Some<>(mapper.apply(value));
            });
        } *//* 

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                switch (this.head.next()) {
                    case None<T> _ -> {
                        return current;
                    }
                    case Some(var value) -> {
                        current = folder.apply(current, value);
                    }
                }
            }
        } *//* 

        @Override
        public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
            return this.map(mapper).fold(Iterators.empty(), Iterator::concat);
        } *//* 

        @Override
        public Iterator<T> concat(Iterator<T> other) {
            return new HeadedIterator<>(() -> switch (this.head.next()) {
                case Some<T> option -> option;
                case None<T> _ -> other.next();
            });
        } *//* 

        @Override
        public Option<T> next() {
            return this.head.next();
        } */
	int length;
	/* = */ 0;/* 

        public RangeHead(int length) {
            this.length = length;
        } *//* 

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
        } */
	/* T */ value;
	/* = */ false;/* 

        private SingleHead(T value) {
            this.value = value;
        } *//* 

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            }

            this.retrieved = true;
            return new Some<>(this.value);
        } *//* 
    }

    static class Iterators {

        public static <T> Iterator<T> empty() {
            return new HeadedIterator<>(new EmptyHead<>());
        } *//* 

        public static <T> Iterator<T> fromOption(Option<T> option) {
            return new HeadedIterator<>(switch (option) {
                case None<T> _ -> new EmptyHead<T>();
                case Some<T>(var value) -> new SingleHead<T>(value);
            });
        } */
	/* "magma", */ "Main.java");
	/* = */ source.resolveSibling("main.c");/* 

        if (this.run(source, target) instanceof Some(var error)) {
            //noinspection CallToPrintStackTrace
            error.printStackTrace();
        } *//* 
    }

    private Option<IOException> run(Path source, Path target) {
        return switch (this.readString(source)) {
            case Err(var error) -> new Some<>(error);
            case Ok(var input) -> this.writeString(target, this.compile(input));
        } */
	/*  */;
	/* this::compileRootSegment, */ this::mergeStatements);
	/*  */ .toString();
	/* return */ output.append(compiled);
	/* = */ state;
	/* = */ 0;
	/* < */ input.length();/*  i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        } */
	/* current.advance() */;
	/* return */ current.segments;
	/*  */ state.append(c);
	/* == */ ';/* ' && state.isLevel()) {
            state.advance();
        } */
	/*  */ state.advance();
	/* state.exit() */;/* 
        }
        else if (c == '{') {
            state.enter();
        } */
	/*  */ state.exit();
	/* return */ state;
}
/* private String compileRootSegment(String input0) {
        var rules = Lists.<Function<String, Option<String>>>of(
                input -> this.compileStructured(input, " */struct "),
                input -> this.compileStructured(input, "record "),
                input -> this.compileStructured(input, "class "),
                this::compileMethod);

        var result = rules
                .iter()
                .map(rule -> rule.apply(input0))
                .flatMap(Iterators::fromOption)
                .next();

        return switch (result) {
	/* -> */ this.generatePlaceholder(input0);
	/* -> */ value;
	/* } */;
}
/* Option<String> */ compileDefinitionStatement(/* String input */){
	/* var */ stripped = input.strip();/* 
        if (stripped.endsWith("; *//* ")) {
            var content = stripped.substring(0, stripped.length() - ";".length());
            return new Some<>("\n\t" + this.compileDefinition(content) + ";");
        } *//* 
        else {
            return new None<>();
        } *//* 
     */
}
/* Option<String> */ compileMethod(/* String input */){
	/* var */ paramStart = input.indexOf("(");/* 
        if (paramStart >= 0) {
            var definition = input.substring(0, paramStart).strip();
            var withParams = input.substring(paramStart + "(".length());

            var paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                var params = withParams.substring(0, paramEnd).strip();
                var withBraces = withParams.substring(paramEnd + ")".length()).strip();
                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    var inputContent = withBraces.substring(1, withBraces.length() - 1);
                    var outputContent = this.compileAll(inputContent, this::foldStatementChar, this::compileStatementOrBlock, this::mergeStatements);
                    return new Some<>(this.compileDefinition(definition) + "(" + this.generatePlaceholder(params) + "){" + outputContent + "\n}\n");
                }
            }
        } *//* 

        return new None<>(); *//* 
     */
}
/* String */ compileDefinition(/* String input */){
	/* var */ stripped = input.strip();
	/* var */ space = stripped.lastIndexOf(" ");/* 
        if (space >= 0) {
            var beforeName = stripped.substring(0, space);
            var typeSeparator = beforeName.lastIndexOf(" ");
            var type = typeSeparator >= 0
                    ? beforeName.substring(typeSeparator + " ".length())
                    : beforeName;

            var name = stripped.substring(space + " ".length());
            return this.compileType(type) + " " + name;
        } *//* 

        return this.generatePlaceholder(stripped); *//* 
     */
}
/* String */ compileType(/* String input */){
	/* var */ stripped = input.strip();/* 
        if (stripped.equals("void")) {
            return "void";
        } *//* 

        if (stripped.equals("int")) {
            return "int";
        } *//* 

        return this.generatePlaceholder(input); *//* 
     */
}
/* String */ compileStatementOrBlock(/* String input */){
	/* var */ stripped = input.strip();/* 
        if (stripped.endsWith("; *//* ")) {
            var statement = stripped.substring(0, stripped.length() - ";".length());
            var valueSeparator = statement.indexOf("=");
            if (valueSeparator >= 0) {
                var definition = statement.substring(0, valueSeparator).strip();
                var value = statement.substring(valueSeparator + "=".length());
                return "\n\t" + this.compileDefinition(definition) + " = " + this.compileValue(value) + ";";
            }
        } *//* 

        return this.generatePlaceholder(input); *//* 
     */
}
/* String */ compileValue(/* String input */){
	/* var */ stripped = input.strip();/* 
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        } *//* 

        if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
            var paramStart = withoutEnd.indexOf("(");
            if (paramStart >= 0) {
                var caller = withoutEnd.substring(0, paramStart).strip();
                var arguments = withoutEnd.substring(paramStart + "(".length());
                return this.compileValue(caller) + "(" + this.compileAll(arguments, this::foldValueChar, this::compileValue, this::mergeValues) + ")";
            }
        } */
	/* var */ separator = stripped.lastIndexOf(".");/* 
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var child = stripped.substring(separator + ".".length());

            return this.compileValue(parent) + "." + child;
        } *//* 

        if (this.isSymbol(stripped)) {
            return stripped;
        } *//* 

        return this.generatePlaceholder(input); *//* 
     */
}
/* State */ foldValueChar(/* State state, char c */){/* 
        if (c == ',' && state.isLevel()) {
            return state.advance();
        } *//* 

        return state.append(c); *//* 
     */
}
/* StringBuilder */ mergeValues(/* StringBuilder cache, String element */){/* 
        if (cache.isEmpty()) {
            return cache.append(element);
        } *//* 
        return cache.append(", ").append(element); *//* 
     */
}
/* boolean */ isSymbol(/* String input */){
	/* (var */ i = /*  0 */;/*  i < input.length(); *//*  i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* 
        return true; *//* 
     */
}
/* Option<String> */ compileStructured(/* String input, String infix */){
	/* var */ interfaceIndex = input.indexOf(infix);/* 
        if (interfaceIndex < 0) {
            return new None<>();
        } */
	/* var */ beforeKeyword = input.substring(/* 0 */, /* interfaceIndex) */.strip();
	/* var */ afterKeyword = input.substring(/* interfaceIndex + infix */.length());/* 
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return new None<>();
        }

        var beforeContent = afterKeyword.substring(0, contentStart).strip();
        var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return new None<>();
        }

        var content = withEnd.substring(0, withEnd.length() - "} *//* ".length()); *//* 
        return new Some<>(this.generatePlaceholder(beforeKeyword) + "struct " +
                beforeContent + " {" + this.compileAll(content, this::foldStatementChar, this::compileClassMember, this::mergeStatements) + "\n} *//* \n"); *//* 
     */
}
/* String */ compileClassMember(/* String input */){
	/* var */ field = this.compileDefinitionStatement(input);/* 
        if (field instanceof Some(var value)) {
            return value;
        } *//* 

        return this.generatePlaceholder(input); *//* 
     */
}
/* String */ generatePlaceholder(/* String input */){/* 
        return "/* " + input + " */"; *//* 
     */
}
/* Option<IOException> */ writeString(/* Path target, String output */){/* 
        try {
            Files.writeString(target, output);
            return new None<>();
        } *//*  catch (IOException e) {
            return new Some<>(e);
        } *//* 
     */
}
/* IOException> */ readString(/* Path source */){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } *//* 
     */
}
/* 
} */
package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public interface List<T> {
        Stream<T> stream();

        List<T> add(T element);

        Option<Tuple<T, List<T>>> pop();

        T last();

        List<T> setLast(T element);

        List<T> sort(BiFunction<T, T, Integer> comparator);
    }

    public interface Stream<T> {
        Stream<T> concat(Stream<T> other);

        <C> C collect(Collector<T, C> collector);

        <R> R fold(R initial, BiFunction<R, T, R> folder);

        <R> Stream<R> map(Function<T, R> mapper);

        Option<T> next();
    }

    public interface Option<T> {

        <R> Option<R> map(Function<T, R> mapper);

        Option<T> or(Supplier<Option<T>> other);

        T orElse(T other);

        boolean isPresent();

        T orElseGet(Supplier<T> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        void ifPresent(Consumer<T> consumer);
    }

    public interface Collector<T, C> {
        C createInitial();

        C fold(C current, T element);
    }

    private interface Head<T> {
        Option<T> next();
    }

    private interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> Result<R, X> mapValue(Function<T, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);
    }

    interface Error {
        String display();
    }

    record Some<T>(T value) implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }
    }

    static class None<T> implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return other.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public void ifPresent(Consumer<T> consumer) {
        }
    }

    private static class DivideState {
        private final List<String> segments;
        private final String buffer;
        private final int depth;
        private final List<Character> queue;

        private DivideState(List<Character> queue, List<String> segments, String buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.queue = queue;
        }

        public DivideState(List<Character> queue) {
            this(queue, Lists.empty(), "", 0);
        }

        private Option<DivideState> popAndAppend() {
            return this.pop().map(popped -> popped.right.append(popped.left));
        }

        private Stream<String> stream() {
            return this.segments.stream();
        }

        private DivideState advance() {
            return new DivideState(this.queue, this.segments.add(this.buffer), "", this.depth);
        }

        private DivideState append(char c) {
            return new DivideState(this.queue, this.segments, this.buffer + c, this.depth);
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            return new DivideState(this.queue, this.segments, this.buffer, this.depth + 1);
        }

        public DivideState exit() {
            return new DivideState(this.queue, this.segments, this.buffer, this.depth - 1);
        }

        public boolean isShallow() {
            return this.depth == 1;
        }

        public Option<Tuple<Character, DivideState>> pop() {
            return this.queue.pop().map(tuple -> new Tuple<>(tuple.left, new DivideState(tuple.right, this.segments, this.buffer, this.depth)));
        }
    }

    public record Tuple<A, B>(A left, B right) {
    }

    private record CompilerState(List<String> structs, List<String> methods, List<List<String>> frames) {
        public CompilerState() {
            this(Lists.empty(), Lists.empty(), Lists.of(Lists.empty()));
        }

        public CompilerState addStruct(String element) {
            return new CompilerState(this.structs.add(element), this.methods, Lists.empty());
        }

        public CompilerState addMethod(String element) {
            return new CompilerState(this.structs, this.methods.add(element), Lists.empty());
        }

        public CompilerState defineType(String name) {
            return new CompilerState(this.structs, this.methods, this.frames.setLast(this.frames.last().add(name)));
        }

        public CompilerState enter() {
            return new CompilerState(this.structs, this.methods, this.frames.add(Lists.empty()));
        }

        public CompilerState exit() {
            return new CompilerState(this.structs, this.methods, this.frames.pop().map(Tuple::right).orElse(null));
        }
    }

    private static class Joiner implements Collector<String, Option<String>> {
        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<String>(current.map(inner -> inner + element).orElse(element));
        }
    }

    public static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        public RangeHead(int length) {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                int value = this.counter;
                this.counter++;
                return new Some<Integer>(value);
            }
            else {
                return new None<>();
            }
        }
    }

    public record HeadedStream<T>(Head<T> head) implements Stream<T> {
        @Override
        public Stream<T> concat(Stream<T> other) {
            return new HeadedStream<>(() -> this.head.next().or(other::next));
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Option<R> result = this.next().map(next -> folder.apply(finalCurrent, next));
                if (result.isPresent()) {
                    current = result.orElse(null);
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Stream<R> map(Function<T, R> mapper) {
            return new HeadedStream<>(() -> this.head.next().map(mapper));
        }

        @Override
        public Option<T> next() {
            return this.head.next();
        }
    }

    private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public List<T> createInitial() {
            return Lists.empty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        }
    }

    private static class Max implements Collector<Integer, Option<Integer>> {
        @Override
        public Option<Integer> createInitial() {
            return new None<>();
        }

        @Override
        public Option<Integer> fold(Option<Integer> current, Integer element) {
            return new Some<>(current.map(inner -> inner > element ? inner : element).orElse(element));
        }
    }

    private record CompileError(String message, String context, List<CompileError> errors) implements Error {
        private CompileError(String message, String context) {
            this(message, context, Lists.empty());
        }

        @Override
        public String display() {
            return this.format(0);
        }

        private String format(int depth) {
            String joined = this.errors
                    .sort((first, second) -> first.computeMaxDepth() - second.computeMaxDepth())
                    .stream()
                    .map(error -> error.format(depth + 1))
                    .map(display -> "\n" + "\t".repeat(depth) + display)
                    .collect(new Joiner())
                    .orElse("");

            return this.message + ": " + this.context + joined;
        }

        private int computeMaxDepth() {
            return 1 + this.errors.stream()
                    .map(CompileError::computeMaxDepth)
                    .collect(new Max())
                    .orElse(0);
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
        }
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }
    }

    private record OrState(Option<Tuple<CompilerState, String>> maybeValue, List<CompileError> errors) {
        public OrState() {
            this(new None<>(), Lists.empty());
        }

        public OrState withValue(Tuple<CompilerState, String> value) {
            return new OrState(new Some<>(value), this.errors);
        }

        public OrState withError(CompileError error) {
            return new OrState(this.maybeValue, this.errors.add(error));
        }

        public Result<Tuple<CompilerState, String>, List<CompileError>> toResult() {
            return this.maybeValue.<Result<Tuple<CompilerState, String>, List<CompileError>>>map(Ok::new).orElseGet(() -> new Err<>(this.errors));
        }
    }

    private record ThrowableError(Throwable throwable) implements Error {
        @Override
        public String display() {
            StringWriter writer = new StringWriter();
            this.throwable.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    }

    private record ApplicationError(Error error) implements Error {
        @Override
        public String display() {
            return this.error.display();
        }
    }

    public static void main(String[] args) {
        Path source = Paths.get(".", "src", "java", "magma", "Main.java");
        readString(source)
                .mapErr(ThrowableError::new)
                .mapErr(ApplicationError::new)
                .match(input -> compileWithInput(source, input), Some::new)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Option<ApplicationError> compileWithInput(Path source, String input) {
        Path target = source.resolveSibling("main.c");
        return compile(input).mapErr(ApplicationError::new).match(output -> writeString(output, target)
                .map(ThrowableError::new)
                .map(ApplicationError::new), Some::new);
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Option<IOException> writeString(String output, Path target) {
        try {
            Files.writeString(target, output);
            return new None<>();
        } catch (IOException e) {
            return new Some<>(e);
        }
    }

    private static Result<String, CompileError> compile(String input) {
        return compileStatements(input, new CompilerState(), Main::compileRootSegment).mapValue(tuple -> {
            CompilerState elements = tuple.left.addStruct(tuple.right);

            Stream<String> left = elements.structs.stream();
            String joined = left.concat(elements.methods.stream())
                    .collect(new Joiner())
                    .orElse("");

            return joined + "int main(){\n\treturn 0;\n}\n";
        });
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileStatements(String input, CompilerState structs, BiFunction<CompilerState, String, Result<Tuple<CompilerState, String>, CompileError>> compiler) {
        return divideStatements(input).<Result<Tuple<CompilerState, String>, CompileError>>fold(new Ok<Tuple<CompilerState, String>, CompileError>(new Tuple<>(structs, "")),
                (current, element) -> current.flatMapValue(inner -> foldSegment(inner, element, compiler)));
    }

    private static Result<Tuple<CompilerState, String>, CompileError> foldSegment(Tuple<CompilerState, String> tuple, String element, BiFunction<CompilerState, String, Result<Tuple<CompilerState, String>, CompileError>> compiler) {
        CompilerState currentStructs = tuple.left;
        String currentOutput = tuple.right;

        return compiler.apply(currentStructs, element).mapValue(compiledStruct -> {
            CompilerState compiledStructs = compiledStruct.left;
            String compiledElement = compiledStruct.right;

            return new Tuple<>(compiledStructs, currentOutput + compiledElement);
        });
    }

    private static Stream<String> divideStatements(String input) {
        List<Character> queue = new HeadedStream<>(new RangeHead(input.length()))
                .map(input::charAt)
                .collect(new ListCollector<>());

        DivideState current = new DivideState(queue);
        while (true) {
            Option<Tuple<Character, DivideState>> maybeNext = current.pop();
            if (!maybeNext.isPresent()) {
                break;
            }

            Tuple<Character, DivideState> tuple = maybeNext.orElse(new Tuple<>('\0', current));
            char next = tuple.left;
            DivideState finalCurrent = tuple.right;
            current = divideSingleQuotes(finalCurrent, next)
                    .orElseGet(() -> divideStatementChar(finalCurrent, next));
        }
        return current.advance().stream();
    }

    private static Option<DivideState> divideSingleQuotes(DivideState current, char c) {
        if (c != '\'') {
            return new None<>();
        }

        return current.append(c).pop().flatMap(maybeSlashTuple -> {
            char maybeSlash = maybeSlashTuple.left;
            DivideState withMaybeSlash = maybeSlashTuple.right.append(maybeSlash);
            Option<DivideState> divideState = maybeSlash == '\\' ? withMaybeSlash.popAndAppend() : new None<>();
            return divideState.flatMap(DivideState::popAndAppend);
        });
    }

    private static DivideState divideStatementChar(DivideState divideState, char c) {
        DivideState appended = divideState.append(c);
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
        return appended;
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileRootSegment(CompilerState state, String input) {
        return compileOr(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compilePackage,
                Main::compileImport,
                Main::compileClass
        ));
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileOr(CompilerState state, String input, List<BiFunction<CompilerState, String, Result<Tuple<CompilerState, String>, CompileError>>> rules) {
        return rules.stream()
                .fold(new OrState(), (orState, rule) -> rule.apply(state, input).match(orState::withValue, orState::withError))
                .toResult()
                .mapErr(errors -> new CompileError("No valid combination present", input, errors));
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileImport(CompilerState state2, String input2) {
        if (input2.strip().startsWith("import ")) {
            return new Ok<>(new Tuple<>(state2, "// #include <temp.h>\n"));
        }
        else {
            return createPrefixError(input2, "import ");
        }
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compilePackage(CompilerState state, String input) {
        if (input.strip().startsWith("package ")) {
            return new Ok<>(new Tuple<>(state, ""));
        }
        else {
            return createPrefixError(input, "package ");
        }
    }

    private static Result<Tuple<CompilerState, String>, CompileError> createPrefixError(String input, String prefix) {
        return new Err<>(new CompileError("Prefix '" + prefix + "' not present", input));
    }

    private static String generatePlaceholder(String stripped) {
        return "/* " + stripped + " */";
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileClass(CompilerState state, String stripped) {
        return compileToStruct(state, stripped, "class ");
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileToStruct(CompilerState state, String input, String infix) {
        String stripped = input.strip();
        int classIndex = stripped.indexOf(infix);
        if (classIndex < 0) {
            return createInfixErr(infix, infix);
        }

        String afterKeyword = stripped.substring(classIndex + infix.length());
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return createInfixErr(afterKeyword, "{");
        }

        String withoutTypeParams = removeTypeParams(afterKeyword, contentStart);

        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return createSuffixErr(withEnd, "}");
        }

        String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
        CompilerState defined = state.enter().defineType(withoutTypeParams);
        return compileStatements(inputContent, defined, Main::compileClassSegment).mapValue(outputTuple -> {
            CompilerState outputStructs = outputTuple.left.exit();
            String outputContent = outputTuple.right;

            String generated = "struct %s {%s\n};\n".formatted(withoutTypeParams, outputContent);
            CompilerState withGenerated = outputStructs.addStruct(generated);
            return new Tuple<CompilerState, String>(withGenerated, "");
        });
    }

    private static String removeTypeParams(String afterKeyword, int contentStart) {
        String beforeContent = afterKeyword.substring(0, contentStart).strip();

        int implementsIndex = beforeContent.indexOf(" implements ");
        String withoutImplements = implementsIndex >= 0
                ? beforeContent.substring(0, implementsIndex).strip()
                : beforeContent.strip();

        int paramStart = withoutImplements.indexOf("(");
        String withoutParams = paramStart >= 0
                ? withoutImplements.substring(0, withoutImplements.indexOf("(")).strip()
                : withoutImplements.strip();

        int typeParamStart = withoutParams.indexOf("<");
        return typeParamStart >= 0
                ? withoutParams.substring(0, typeParamStart).strip()
                : withoutParams.strip();
    }

    private static Err<Tuple<CompilerState, String>, CompileError> createSuffixErr(String input, String suffix) {
        return new Err<>(new CompileError("Suffix '" + suffix + "' not present", input));
    }

    private static Err<Tuple<CompilerState, String>, CompileError> createInfixErr(String afterKeyword, String infix) {
        return new Err<>(new CompileError("Infix '" + infix + "' not present", afterKeyword));
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileClassSegment(CompilerState state0, String input0) {
        return compileOr(state0, input0, Lists.of(
                Main::compileWhitespace,
                Main::compileClass,
                (state, input) -> compileToStruct(state, input, "interface "),
                (state, input) -> compileToStruct(state, input, "record "),
                Main::compileMethod,
                (state, input) -> compileStatement(state, input, Main::compileDefinition),
                (state, input) -> compileStatement(state, input, Main::compileInitialization)
        ));
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileWhitespace(CompilerState state, String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) {
            return new Ok<>(new Tuple<CompilerState, String>(state, stripped));
        }
        else {
            return new Err<>(new CompileError("Not empty", stripped));
        }
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileInitialization(CompilerState state, String input) {
        int valueSeparator = input.indexOf("=");
        if (valueSeparator < 0) {
            return createInfixErr(input, "{");
        }

        String definition = input.substring(0, valueSeparator).strip();
        String value = input.substring(valueSeparator + "=".length()).strip();
        return compileDefinition(state, definition).mapValue(outputDefinition -> new Tuple<>(outputDefinition.left, outputDefinition.right + " = " + compileValue(value)));
    }

    private static String compileValue(String value) {
        if (isNumber(value.strip())) {
            return value;
        }
        return generatePlaceholder(value);
    }

    private static boolean isNumber(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileMethod(CompilerState state, String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String inputDefinition = input.substring(0, paramStart).strip();
            return compileDefinition(state, inputDefinition).mapValue(definitionTuple -> new Tuple<CompilerState, String>(definitionTuple.left.addMethod(definitionTuple.right + "(){\n}\n"), ""));
        }
        else {
            return createInfixErr(input, "(");
        }
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileStatement(CompilerState state, String input, BiFunction<CompilerState, String, Result<Tuple<CompilerState, String>, CompileError>> compileDefinition) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return createSuffixErr(stripped, ";");
        }

        String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition.apply(state, withoutEnd).mapValue(tuple -> new Tuple<>(tuple.left, "\n\t" + tuple.right + ";"));
    }

    private static Result<Tuple<CompilerState, String>, CompileError> compileDefinition(CompilerState state, String input) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return createInfixErr(stripped, " ");
        }

        String beforeName = stripped.substring(0, nameSeparator).strip();
        Result<String, CompileError> outputBeforeString;
        int typeSeparator = findTypeSeparator(beforeName);
        if (typeSeparator >= 0) {
            String beforeType = beforeName.substring(0, typeSeparator).strip();
            String type1 = beforeName.substring(typeSeparator + " ".length()).strip();
            outputBeforeString = compileType(type1).mapValue(outputType -> generatePlaceholder(beforeType) + " " + outputType);
        }
        else {
            outputBeforeString = compileType(beforeName);
        }

        String oldName = stripped.substring(nameSeparator + " ".length()).strip();
        if (isSymbol(oldName)) {
            String newName = oldName.equals("main") ? "__main__" : oldName;
            return outputBeforeString.mapValue(type -> new Tuple<>(state, type + " " + newName));
        }
        else {
            return new Err<>(new CompileError("Not a symbol", oldName));
        }
    }

    private static int findTypeSeparator(String beforeName) {
        int typeSeparator = -1;
        int depth = 0;
        for (int i = beforeName.length() - 1; i >= 0; i--) {
            char c = beforeName.charAt(i);
            if (c == ' ' && depth == 0) {
                typeSeparator = i;
                break;
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        }
        return typeSeparator;
    }

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }

        return true;
    }

    private static Result<String, CompileError> compileType(String input) {
        String stripped = input.strip();
        switch (stripped) {
            case "public", "private" -> {
                return new Err<>(new CompileError("This is a reserved keyword", stripped));
            }
            case "int", "boolean" -> {
                return new Ok<>("int");
            }
            case "void" -> {
                return new Ok<>("void");
            }
            case "char" -> {
                return new Ok<>("char");
            }
            case "String" -> {
                return new Ok<>("char*");
            }
        }

        int typeParamStart = stripped.indexOf("<");
        if (typeParamStart >= 0) {
            return new Ok<>("struct " + stripped.substring(0, typeParamStart).strip());
        }

        if (isSymbol(stripped)) {
            return new Ok<>("struct " + stripped);
        }

        return new Ok<>(generatePlaceholder(stripped));
    }
}

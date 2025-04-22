package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    private interface DivideState {
        DivideState advance();

        DivideState append(char c);

        Stream<String> stream();

        boolean isLevel();

        DivideState enter();

        DivideState exit();

        boolean isShallow();
    }

    private interface Result<T, X> {
        Optional<T> findValue();

        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);

        <R> Result<T, R> mapErr(Function<X, R> mapper);

        <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper);

        <R> Result<R, X> mapValue(Function<T, R> mapper);
    }

    private interface Rule {
        Result<String, CompileError> compile(String input);
    }

    private interface Locator {
        Optional<Integer> locate(String input, String infix);
    }

    private interface Folder {
        DivideState fold(DivideState state, char c);
    }

    private interface Error {
        String display();
    }

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public Optional<T> findValue() {
            return Optional.of(this.value);
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Ok<>(this.value);
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Ok<>(mapper.apply(this.value));
        }
    }

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public Optional<T> findValue() {
            return Optional.empty();
        }

        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }

        @Override
        public <R> Result<T, R> mapErr(Function<X, R> mapper) {
            return new Err<>(mapper.apply(this.error));
        }

        @Override
        public <R> Result<R, X> flatMapValue(Function<T, Result<R, X>> mapper) {
            return new Err<>(this.error);
        }

        @Override
        public <R> Result<R, X> mapValue(Function<T, R> mapper) {
            return new Err<>(this.error);
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

    private record CompileError(String message, String context, List<CompileError> errors) implements Error {
        public CompileError(String message, String context) {
            this(message, context, Collections.emptyList());
        }

        @Override
        public String display() {
            return this.message + ": " + this.context + this.errors.stream()
                    .map(CompileError::display)
                    .collect(Collectors.joining());
        }
    }

    private static class MutableDivideState implements DivideState {
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        private MutableDivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public MutableDivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        @Override
        public DivideState advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        @Override
        public DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        @Override
        public Stream<String> stream() {
            return this.segments.stream();
        }

        @Override
        public boolean isLevel() {
            return this.depth == 0;
        }

        @Override
        public DivideState enter() {
            this.depth++;
            return this;
        }

        @Override
        public DivideState exit() {
            this.depth--;
            return this;
        }

        @Override
        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private record InfixRule(
            Rule leftRule,
            String infix,
            Locator locator,
            Rule rightRule
    ) implements Rule {
        private InfixRule(Rule leftRule, String infix, Rule rightRule) {
            this(leftRule, infix, new FirstLocator(), rightRule);
        }

        @Override
        public Result<String, CompileError> compile(String input) {
            return this.locator().locate(input, this.infix()).map(index -> {
                String left = input.substring(0, index);
                String right = input.substring(index + this.infix().length());

                return this.leftRule().compile(left).flatMapValue(compiledLeft -> {
                    return this.rightRule().compile(right).mapValue(compiledRight -> {
                        return compiledLeft + this.infix() + compiledRight;
                    });
                });
            }).orElseGet(() -> new Err<>(new CompileError("Infix '" '+ this.infix + "' n'ot present", input)));
        }
    }

    public static class FirstLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {
            int index = input.indexOf(infix);
            return index == -1
                    ? Optional.empty()
                    : Optional.of(index);
        }
    }

    private record PrefixRule(String prefix, Rule childRule) implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            return new InfixRule(new StringRule(), this.prefix(), this.childRule()).compile(input);
        }
    }

    private record SuffixRule(Rule childRule, String suffix) implements Rule {
        @Override
        public Result<String, CompileError> compile(String input) {
            return new InfixRule(this.childRule(), this.suffix(), new LastLocator(), new StringRule()).compile(input);
        }
    }

    private static class StringRule implements Rule {
        private Optional<String> compileToOptional(String name) {
            return Optional.of(name);
        }

        @Override
        public Result<String, CompileError> compile(String input) {
            return this.compileToOptional(input)
                    .<Result<String, CompileError>>map(Ok::new)
                    .orElseGet(() -> new Err<>(new CompileError("Invalid value for " + this.getClass(), input)));
        }
    }

    private record DivideRule(Folder folder, Rule rule) implements Rule {
        private static Stream<String> divide(DivideState state, String input, Folder folder) {
            LinkedList<Character> queue = IntStream.range(0, input.length())
                    .mapToObj(input::charAt)
                    .collect(Collectors.toCollection(LinkedList::new));

            DivideState current = state;
            while (!queue.isEmpty()) {
                char c = queue.pop();
                if (c == '\''') {
                    DivideState appended = current.append(c);

                    char popped = queue.pop();
                    DivideState withMaybeSlash = appended.append(popped);

                    DivideState withEscaped = popped == '\\'' ? withMaybeSlash.append(queue.pop()) : withMaybeSlash;
                    current = withEscaped.append(queue.pop());
                }

                current = folder.fold(current, c);
            }

            return current.advance().stream();
        }

        @Override
        public Result<String, CompileError> compile(String input) {
            return divide(new MutableDivideState(), input, this.folder)
                    .<Result<String, CompileError>>reduce(new Ok<>(""),
                            (maybeCurrent, element) -> maybeCurrent.flatMapValue(
                                    current -> this.rule.compile(element).mapValue(compiled -> current + compiled)),
                            (_, next) -> next);
        }
    }

    private record OrRule(List<Rule> rules) implements Rule {
        private record OrState(Optional<String> maybeValue, List<CompileError> errors) {
            public OrState() {
                this(Optional.empty(), new ArrayList<>());
            }

            public OrState withValue(String value) {
                return new OrState(Optional.of(value), this.errors);
            }

            public OrState withError(CompileError error) {
                ArrayList<CompileError> copy = new ArrayList<>(this.errors);
                copy.add(error);
                return new OrState(this.maybeValue, copy);
            }

            public Result<String, List<CompileError>> toResult() {
                return this.maybeValue.<Result<String, List<CompileError>>>map(Ok::new).orElseGet(() -> new Err<>(this.errors));
            }
        }

        @Override
        public Result<String, CompileError> compile(String input) {
            return this.rules.stream()
                    .reduce(new OrState(), (orState, rule) -> this.foldRule(input, orState, rule), (_, next) -> next)
                    .toResult()
                    .mapErr(errs -> new CompileError("No valid rule present", input, errs));
        }

        private OrState foldRule(String input, OrState orState, Rule rule) {
            if (orState.maybeValue.isPresent()) {
                return orState;
            }
            return rule.compile(input).match(orState::withValue, orState::withError);
        }
    }

    private static class LazyRule implements Rule {
        private Optional<Rule> childRule = Optional.empty();

        public void set(Rule childRule) {
            this.childRule = Optional.of(childRule);
        }

        @Override
        public Result<String, CompileError> compile(String input) {
            return this.childRule.map(internal -> internal.compile(input))
                    .orElseGet(() -> new Err<>(new CompileError("Child not set", input)));
        }
    }

    private static class LastLocator implements Locator {
        @Override
        public Optional<Integer> locate(String input, String infix) {
            int index = input.lastIndexOf(infix);
            return index == -1
                    ? Optional.empty()
                    : Optional.of(index);
        }
    }

    private static class StatementFolder implements Folder {
        @Override
        public DivideState fold(DivideState state, char c) {
            DivideState appended = state.append(c);
            if (c == ';'' && appended.isLevel()) {
                return appended.advance();
            }
            if (c == '}'' && appended.isShallow()) {
                return appended.advance().exit();
            }
            if (c == '{'') {
                return appended.enter();
            }
            if (c == '}'') {
                return appended.exit();
            }
            return appended;
        }
    }

    private static class ValueFolder implements Folder {
        @Override
        public DivideState fold(DivideState state, char c) {
            if (c == ','') {
                return state.advance();
            }

            return state.append(c);
        }
    }

    private static class BlankRule implements Rule {
        private Optional<String> compileToOptional(String input) {
            if (input.isBlank()) {
                return Optional.of("");
            }
            else {
                return Optional.empty();
            }
        }

        @Override
        public Result<String, CompileError> compile(String input) {
            return this.compileToOptional(input)
                    .<Result<String, CompileError>>map(Ok::new)
                    .orElseGet(() -> new Err<>(new CompileError("Invalid value for " + this.getClass(), input)));
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
                .match(input -> runWithSource(source, input), Optional::of)
                .ifPresent(error -> System.err.println(error.display()));
    }

    private static Optional<ApplicationError> runWithSource(Path source, String input) {
        Path target = source.resolveSibling("main.c");
        return compile(input)
                .mapErr(ApplicationError::new)
                .match(output -> {
                    return writeString(target, output).map(ThrowableError::new).map(ApplicationError::new);
                }, Optional::of);
    }

    private static Optional<IOException> writeString(Path target, String output) {
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } catch (IOException e) {
            return Optional.of(e);
        }
    }

    private static Result<String, IOException> readString(Path source) {
        try {
            return new Ok<>(Files.readString(source));
        } catch (IOException e) {
            return new Err<>(e);
        }
    }

    private static Result<String, CompileError> compile(String input) {
        return Statements(createRootSegmentRule()).compile(input);
    }

    private static OrRule createRootSegmentRule() {
        return new OrRule(List.of(
                new BlankRule(),
                createNamespaceRule("package "),
                createNamespaceRule("import "),
                createClassRule(createClassSegmentRule())
        ));
    }

    private static Rule createNamespaceRule(String infix) {
        return new PrefixRule(infix, new SuffixRule(new StringRule(), ";"));
    }

    private static Rule createClassRule(Rule classSegment) {
        return createStructuredRule("class ", classSegment);
    }

    private static Rule createStructuredRule(String infix, Rule classSegment) {
        return new PrefixRule(infix, new InfixRule(new StringRule(), "{",
                new SuffixRule(Statements(classSegment), "}")));
    }

    private static DivideRule Statements(Rule classSegment) {
        return new DivideRule(new StatementFolder(), classSegment);
    }

    private static Rule createClassSegmentRule() {
        LazyRule classSegment = new LazyRule();
        classSegment.set(new OrRule(List.of(
                new BlankRule(),
                createClassRule(classSegment),
                createStructuredRule("interface ", classSegment),
                createStructuredRule("record ", classSegment),
                createMethodRule()
        )));
        return classSegment;
    }

    private static SuffixRule createMethodRule() {
        InfixRule definition = createDefinitionRule();
        Rule params = new DivideRule(new ValueFolder(), new OrRule(List.of(
                new BlankRule(),
                definition
        )));

        return new SuffixRule(new InfixRule(definition, "(", new SuffixRule(params, ")")), ";");
    }

    private static InfixRule createDefinitionRule() {
        return new InfixRule(new StringRule(), " ", new LastLocator(), new StringRule());
    }
}
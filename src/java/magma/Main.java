package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class Main {
    private interface MethodHeader {
        String generateWithAfterName(String afterName);
    }

    private static class DivideState {
        private final List<String> segments;
        private final String input;
        private int index;
        private StringBuilder buffer;
        private int depth;

        private DivideState(List<String> segments, StringBuilder buffer, int depth, String input, int index) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.input = input;
            this.index = index;
        }

        public DivideState(String input) {
            this(new ArrayList<>(), new StringBuilder(), 0, input, 0);
        }

        private DivideState advance() {
            this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public DivideState enter() {
            this.depth++;
            return this;
        }

        public DivideState exit() {
            this.depth--;
            return this;
        }

        public boolean isShallow() {
            return this.depth == 1;
        }

        public Optional<Tuple<DivideState, Character>> pop() {
            if (this.index >= this.input.length()) {
                return Optional.empty();
            }

            var c = this.input.charAt(this.index);
            this.index++;
            return Optional.of(new Tuple<>(this, c));
        }

        public Optional<Tuple<DivideState, Character>> popAndAppendToTuple() {
            return this.pop().map(inner -> new Tuple<>(inner.left.append(inner.right), inner.right));
        }

        public Optional<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map(Tuple::left);
        }

        public char peek() {
            return this.input.charAt(this.index);
        }
    }

    private record Tuple<A, B>(A left, B right) {
    }

    private record CompileState(String output, Optional<String> structureName, int depth) {
        public CompileState() {
            this("", Optional.empty(), 0);
        }

        public CompileState append(String element) {
            return new CompileState(this.output + element, this.structureName, this.depth);
        }

        public CompileState withStructureName(String name) {
            return new CompileState(this.output, Optional.of(name), this.depth);
        }

        public int depth() {
            return this.depth;
        }

        public CompileState enterDepth() {
            return new CompileState(this.output, this.structureName, this.depth + 1);
        }

        public CompileState exitDepth() {
            return new CompileState(this.output, this.structureName, this.depth - 1);
        }
    }

    private record Definition(Optional<String> maybeBeforeType, String name, String type) implements MethodHeader {
        private String generate() {
            return this.generateWithAfterName(" ");
        }

        @Override
        public String generateWithAfterName(String afterName) {
            var beforeTypeString = this.maybeBeforeType().map(Main::generatePlaceholder).orElse("");
            return beforeTypeString + this.name + afterName + ": " + this.type();
        }
    }

    private static class ConstructorHeader implements MethodHeader {
        @Override
        public String generateWithAfterName(String afterName) {
            return "constructor " + afterName;
        }
    }

    public static void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var target = source.resolveSibling("main.ts");
        try {
            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compileRoot(String input) {
        var compiled = compileStatements(new CompileState(), input, Main::compileRootSegment);
        return compiled.left.output + compiled.right;
    }

    private static Tuple<CompileState, String> compileStatements(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldStatements, mapper, Main::mergeStatements);
    }

    private static Tuple<CompileState, String> compileAll(CompileState state, String input, BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper, BiFunction<StringBuilder, String, StringBuilder> merger) {
        var divisions = divide(input, folder);

        var current = new Tuple<>(state, new StringBuilder());
        for (var segment : divisions) {
            var currentState = current.left;
            var currentElement = current.right;

            var mappedTuple = mapper.apply(currentState, segment);
            var mappedState = mappedTuple.left;
            var mappedElement = mappedTuple.right;

            current = new Tuple<>(mappedState, merger.apply(currentElement, mappedElement));
        }

        return new Tuple<>(current.left, current.right.toString());
    }

    private static StringBuilder mergeStatements(StringBuilder cache, String element) {
        return cache.append(element);
    }

    private static List<String> divide(String input, BiFunction<DivideState, Character, DivideState> folder) {
        var current = new DivideState(input);

        while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var poppedTuple = maybePopped.get();
            var poppedState = poppedTuple.left;
            var popped = poppedTuple.right;

            current = foldSingleQuotes(poppedState, popped)
                    .or(() -> foldDoubleQuotes(poppedState, popped))
                    .orElseGet(() -> folder.apply(poppedState, popped));
        }

        return current.advance().segments;
    }

    private static Optional<DivideState> foldDoubleQuotes(DivideState state, char c) {
        if (c != '\"') {
            return Optional.empty();
        }

        var appended = state.append(c);
        while (true) {
            var maybeTuple = appended.popAndAppendToTuple();
            if (maybeTuple.isEmpty()) {
                break;
            }

            var tuple = maybeTuple.get();
            appended = tuple.left;

            if (tuple.right == '\\') {
                appended = appended.popAndAppendToOption().orElse(appended);
            }
            if (tuple.right == '\"') {
                break;
            }
        }
        return Optional.of(appended);
    }

    private static Optional<DivideState> foldSingleQuotes(DivideState state, char c) {
        if (c != '\'') {
            return Optional.empty();
        }

        return state.append(c)
                .popAndAppendToTuple()
                .flatMap(Main::foldEscaped)
                .flatMap(DivideState::popAndAppendToOption);
    }

    private static Optional<DivideState> foldEscaped(Tuple<DivideState, Character> tuple) {
        var state = tuple.left;
        var c = tuple.right;

        if (c == '\\') {
            return state.popAndAppendToOption();
        }

        return Optional.of(state);
    }

    private static DivideState foldStatements(DivideState state, char c) {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }

        if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }

        if (c == '{' || c == '(') {
            return appended.enter();
        }

        if (c == '}' || c == ')') {
            return appended.exit();
        }

        return appended;
    }

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileNamespaced,
                createStructureRule("class ", "class ")
        ));
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createStructureRule(String sourceInfix, String targetInfix) {
        return (state1, input1) -> compileFirst(input1, sourceInfix, (beforeKeyword, right1) -> {
            return compileFirst(right1, "{", (beforeContent, withEnd) -> {
                return compileSuffix(withEnd.strip(), "}", inputContent -> {
                    var strippedBeforeContent = beforeContent.strip();
                    return compileFirst(strippedBeforeContent, "(", (rawName, s2) -> {
                        var name = rawName.strip();
                        return assembleStructure(targetInfix, state1, beforeKeyword, inputContent, name);
                    }).or(() -> {
                        return assembleStructure(targetInfix, state1, beforeKeyword, inputContent, strippedBeforeContent);
                    });
                });
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> assembleStructure(String targetInfix, CompileState state1, String beforeKeyword, String inputContent, String name) {
        var outputContentTuple = compileStatements(state1.withStructureName(name), inputContent, Main::compileClassSegment);
        var outputContentState = outputContentTuple.left;
        var outputContent = outputContentTuple.right;

        var generated = generatePlaceholder(beforeKeyword.strip()) + targetInfix + name + " {" + outputContent + "\n}\n";
        return Optional.of(new Tuple<>(outputContentState.append(generated), ""));
    }

    private static Optional<Tuple<CompileState, String>> compileNamespaced(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return Optional.of(new Tuple<>(state, ""));
        }
        else {
            return Optional.empty();
        }
    }

    private static Tuple<CompileState, String> compileOrPlaceholder(
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules
    ) {
        return compileOr(state, input, rules).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> compileOr(CompileState state, String input, List<BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>>> rules) {
        for (var rule : rules) {
            var maybeTuple = rule.apply(state, input);
            if (maybeTuple.isPresent()) {
                return maybeTuple;
            }
        }

        return Optional.empty();
    }

    private static Tuple<CompileState, String> compileClassSegment(CompileState state1, String input1) {
        return compileOrPlaceholder(state1, input1, List.of(
                Main::compileWhitespace,
                createStructureRule("class ", "class "),
                createStructureRule("interface ", "interface "),
                createStructureRule("record ", "class "),
                Main::compileMethod,
                Main::compileFieldDefinition
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        return compileFirst(input, "(", (beforeParams, withParams) -> {
            return compileLast(beforeParams.strip(), " ", (_, name) -> {
                if (state.structureName.filter(name::equals).isPresent()) {
                    return compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
                }

                return compileDefinition(state, beforeParams)
                        .flatMap(tuple -> compileMethodWithBeforeParams(tuple.left, tuple.right, withParams));
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> compileMethodWithBeforeParams(CompileState state, MethodHeader header, String withParams) {
        return compileFirst(withParams, ")", (params, afterParams) -> {
            var parametersTuple = compileValues(state, params, Main::compileParameter);
            var parametersState = parametersTuple.left;
            var parameters = parametersTuple.right;

            var headerGenerated = header.generateWithAfterName("(" + parameters + ")");
            return compilePrefix(afterParams.strip(), "{", withoutContentStart -> {
                return compileSuffix(withoutContentStart.strip(), "}", withoutContentEnd -> {
                    var statementsTuple = compileFunctionStatements(parametersState.enterDepth().enterDepth(), withoutContentEnd);

                    return Optional.of(new Tuple<>(statementsTuple.left.exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right + "\n\t}"));
                });
            }).or(() -> {
                if (afterParams.strip().equals(";")) {
                    return Optional.of(new Tuple<>(parametersState, "\n\t" + headerGenerated + ";"));
                }

                return Optional.empty();
            });
        });
    }

    private static Tuple<CompileState, String> compileFunctionStatements(CompileState state, String input) {
        return compileStatements(state, input, Main::compileFunctionSegment);
    }

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileWhitespace,
                Main::compileBlock,
                Main::compileFunctionStatement
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileBlock(CompileState state, String input) {
        return compileSuffix(input.strip(), "}", withoutEnd -> {
            return compileFirst(withoutEnd, "{", (beforeContent, content) -> {
                var headerTuple = compileBlockHeader(state, beforeContent);
                var contentTuple = compileFunctionStatements(headerTuple.left.enterDepth(), content);

                var indent = generateIndent(state.depth());
                return Optional.of(new Tuple<>(contentTuple.left.exitDepth(), indent + headerTuple.right + "{" + contentTuple.right + indent + "}"));
            });
        });
    }

    private static Tuple<CompileState, String> compileBlockHeader(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileIf
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileIf(CompileState state, String input) {
        return compilePrefix(input.strip(), "if", withoutPrefix -> {
            var strippedCondition = withoutPrefix.strip();
            return compilePrefix(strippedCondition, "(", withoutConditionStart -> {
                return compileSuffix(withoutConditionStart, ")", withoutConditionEnd -> {
                    var tuple = compileValueOrPlaceholder(state, withoutConditionEnd);
                    return Optional.of(new Tuple<>(tuple.left, "if (" + tuple.right + ")"));
                });
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> compileFunctionStatement(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            var valueTuple = compileFunctionStatementValue(state, withoutEnd);
            return Optional.of(new Tuple<>(valueTuple.left, generateIndent(state.depth()) + valueTuple.right + ";"));
        });
    }

    private static String generateIndent(int indent) {
        return "\n" + "\t".repeat(indent);
    }

    private static Tuple<CompileState, String> compileFunctionStatementValue(CompileState state, String withoutEnd) {
        return compileOrPlaceholder(state, withoutEnd, List.of(
                Main::compileReturn,
                Main::compileAssignment,
                Main::compileInvokable,
                createPostRule("++"),
                createPostRule("--")
        ));
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createPostRule(String suffix) {
        return (state1, input) -> compileSuffix(input.strip(), suffix, child -> {
            var tuple = compileValueOrPlaceholder(state1, child);
            return Optional.of(new Tuple<>(tuple.left, tuple.right + suffix));
        });
    }

    private static Optional<Tuple<CompileState, String>> compileReturn(CompileState state, String input) {
        return compilePrefix(input.strip(), "return ", value -> {
            var tuple = compileValueOrPlaceholder(state, value);
            return Optional.of(new Tuple<>(tuple.left, "return " + tuple.right));
        });
    }

    private static Optional<Tuple<CompileState, String>> compileInvokable(CompileState state, String input) {
        return compileSuffix(input.strip(), ")", withoutEnd -> {
            return compileSplit(splitFolded(withoutEnd, "", Main::foldInvocationStarts), (callerWithArgStart, arguments) -> {
                return compileSuffix(callerWithArgStart, "(", caller -> compilePrefix(caller.strip(), "new ", type -> {
                    var callerTuple = compileTypeOrPlaceholder(state, type);
                    return assembleInvokable(callerTuple.left, "new " + callerTuple.right, arguments);
                }).or(() -> {
                    var callerTuple = compileValueOrPlaceholder(state, caller);
                    return assembleInvokable(callerTuple.left, callerTuple.right, arguments);
                }));
            });
        });
    }

    private static Optional<Tuple<String, String>> splitFolded(String input, String delimiter, BiFunction<DivideState, Character, DivideState> folder) {
        var divisions = divide(input, folder);
        if (divisions.size() < 2) {
            return Optional.empty();
        }

        var beforeLast = divisions.subList(0, divisions.size() - 1);
        var last = divisions.getLast();

        var joined = String.join(delimiter, beforeLast);
        return Optional.of(new Tuple<>(joined, last));
    }

    private static DivideState foldInvocationStarts(DivideState state, char c) {
        var appended = state.append(c);
        if (c == '(') {
            var entered = appended.enter();
            if (entered.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }

        if (c == ')') {
            return appended.exit();
        }

        return appended;
    }

    private static Optional<Tuple<CompileState, String>> assembleInvokable(CompileState state, String caller, String arguments) {
        var argumentsTuple = compileValues(state, arguments, Main::compileValueOrPlaceholder);
        return Optional.of(new Tuple<>(argumentsTuple.left, caller + "(" + argumentsTuple.right + ")"));
    }

    private static Optional<Tuple<CompileState, String>> compileAssignment(CompileState state, String input) {
        return compileFirst(input, "=", (destination, source) -> {
            var sourceTuple = compileValueOrPlaceholder(state, source);

            var destinationTuple = compileValue(sourceTuple.left, destination)
                    .or(() -> compileDefinition(sourceTuple.left, destination).map(tuple -> new Tuple<>(tuple.left, "let " + tuple.right.generate())))
                    .orElseGet(() -> new Tuple<>(sourceTuple.left, generatePlaceholder(destination)));

            return Optional.of(new Tuple<>(destinationTuple.left, destinationTuple.right + " = " + sourceTuple.right));
        });
    }

    private static Tuple<CompileState, String> compileValueOrPlaceholder(CompileState state, String input) {
        return compileValue(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, String>> compileValue(CompileState state, String input) {
        return compileOr(state, input, List.of(
                createAccessRule("."),
                createAccessRule("::"),
                Main::compileSymbol,
                Main::compileLambda,
                Main::compileNot,
                Main::compileInvokable,
                Main::compileNumber,
                createOperatorRuleWithDifferentInfixes("==", "==="),
                createOperatorRuleWithDifferentInfixes("!=", "!=="),
                createTextRule("\""),
                createTextRule("'"),
                createOperatorRule("+"),
                createOperatorRule("-"),
                createOperatorRule("<"),
                createOperatorRule("&&"),
                createOperatorRule("||")
        ));
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createTextRule(String slice) {
        return (state1, input1) -> {
            var stripped = input1.strip();
            if (!stripped.startsWith(slice) || !stripped.endsWith(slice) || stripped.length() <= slice.length()) {
                return Optional.empty();
            }

            var value = stripped.substring(slice.length(), stripped.length() - slice.length());
            return Optional.of(new Tuple<>(state1, "\"" + value + "\""));
        };
    }

    private static Optional<Tuple<CompileState, String>> compileNot(CompileState state, String input) {
        return compilePrefix(input.strip(), "!", withoutPrefix -> {
            var tuple = compileValueOrPlaceholder(state, withoutPrefix);
            return Optional.of(new Tuple<>(tuple.left, "!" + tuple.right));
        });
    }

    private static Optional<Tuple<CompileState, String>> compileLambda(CompileState state, String input) {
        return compileFirst(input, "->", (beforeArrow, afterArrow) -> {
            var strippedBeforeArrow = beforeArrow.strip();
            if (isSymbol(strippedBeforeArrow)) {
                return getCompileStateStringTuple(state, List.of(strippedBeforeArrow), afterArrow);
            }

            return compilePrefix(strippedBeforeArrow, "(", withoutStart -> {
                return compileSuffix(withoutStart, ")", withoutEnd -> {
                    var paramNames = divide(withoutEnd, Main::foldValues)
                            .stream()
                            .map(String::strip)
                            .filter(value -> !value.isEmpty())
                            .toList();

                    if (paramNames.stream().allMatch(Main::isSymbol)) {
                        return getCompileStateStringTuple(state, paramNames, afterArrow);
                    }
                    else {
                        return Optional.empty();
                    }
                });
            });
        });
    }

    private static Optional<Tuple<CompileState, String>> getCompileStateStringTuple(CompileState state, List<String> paramNames, String afterArrow) {
        var strippedAfterArrow = afterArrow.strip();
        return compilePrefix(strippedAfterArrow, "{", withoutContentStart -> {
            return compileSuffix(withoutContentStart, "}", withoutContentEnd -> {
                var statementsTuple = compileFunctionStatements(state.enterDepth(), withoutContentEnd);
                var statementsState = statementsTuple.left;
                var statements = statementsTuple.right;

                var exited = statementsState.exitDepth();
                return assembleLambda(exited, paramNames, "{" + statements + generateIndent(exited.depth) + "}");
            });
        }).or(() -> {
            var tuple = compileValueOrPlaceholder(state, strippedAfterArrow);
            return assembleLambda(tuple.left, paramNames, tuple.right);
        });
    }

    private static Optional<Tuple<CompileState, String>> assembleLambda(CompileState exited, List<String> paramNames, String content) {
        return Optional.of(new Tuple<>(exited, "(" + String.join(", ", paramNames) + ")" + " => " + content));
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createOperatorRule(String infix) {
        return createOperatorRuleWithDifferentInfixes(infix, infix);
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createAccessRule(String infix) {
        return (state, input) -> compileLast(input, infix, (child, rawProperty) -> {
            var tuple = compileValueOrPlaceholder(state, child);
            var property = rawProperty.strip();
            if (isSymbol(property)) {
                return Optional.of(new Tuple<>(tuple.left, tuple.right + "." + property));
            }
            else {
                return Optional.empty();
            }
        });
    }

    private static BiFunction<CompileState, String, Optional<Tuple<CompileState, String>>> createOperatorRuleWithDifferentInfixes(String sourceInfix, String targetInfix) {
        return (state1, input1) -> compileFirst(input1, sourceInfix, (left, right) -> {
            var leftTuple = compileValueOrPlaceholder(state1, left);
            var rightTuple = compileValueOrPlaceholder(leftTuple.left, right);
            return Optional.of(new Tuple<>(rightTuple.left, leftTuple.right + " " + targetInfix + " " + rightTuple.right));
        });
    }

    private static Optional<Tuple<CompileState, String>> compileNumber(CompileState state, String input) {
        var stripped = input.strip();
        if (isNumber(stripped)) {
            return Optional.of(new Tuple<>(state, stripped));
        }
        else {
            return Optional.empty();
        }
    }

    private static boolean isNumber(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        }
        return true;
    }

    private static Optional<Tuple<CompileState, String>> compileSymbol(CompileState state, String input) {
        var stripped = input.strip();
        if (isSymbol(stripped)) {
            return Optional.of(new Tuple<>(state, stripped));
        }
        else {
            return Optional.empty();
        }
    }

    private static boolean isSymbol(String input) {
        return IntStream.range(0, input.length())
                .allMatch(index -> isSymbolChar(index, input.charAt(index)));
    }

    private static boolean isSymbolChar(int index, char c) {
        return Character.isLetter(c) || (index != 0 && Character.isDigit(c));
    }

    private static Optional<Tuple<CompileState, String>> compilePrefix(String input, String infix, Function<String, Optional<Tuple<CompileState, String>>> mapper) {
        if (!input.startsWith(infix)) {
            return Optional.empty();
        }

        var slice = input.substring(infix.length());
        return mapper.apply(slice);
    }

    private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        return compileOrPlaceholder(state, input, List.of(
                Main::compileWhitespace,
                (state1, input1) -> {
                    return compileDefinition(state1, input1).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
                }
        ));
    }

    private static Optional<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return Optional.of(new Tuple<>(state, ""));
        }
        return Optional.empty();
    }

    private static Optional<Tuple<CompileState, String>> compileFieldDefinition(CompileState state, String input) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            var definitionTuple = compileDefinitionOrPlaceholder(state, withoutEnd);
            return Optional.of(new Tuple<>(definitionTuple.left, "\n\t" + definitionTuple.right + ";"));
        });
    }

    private static Tuple<CompileState, String> compileDefinitionOrPlaceholder(CompileState state, String input) {
        return compileDefinition(state, input)
                .map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    }

    private static Optional<Tuple<CompileState, Definition>> compileDefinition(CompileState state, String input) {
        return compileLast(input.strip(), " ", (beforeName, name) -> {
            var splits = splitFolded(beforeName.strip(), " ", Main::foldTypeSeparators);
            return compileSplit(splits, (beforeType, type) -> {
                return assembleDefinition(state, Optional.of(beforeType), name, type);
            }).or(() -> {
                return assembleDefinition(state, Optional.empty(), name, beforeName);
            });
        });
    }

    private static DivideState foldTypeSeparators(DivideState state, char c) {
        if (c == ' ' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    }

    private static Optional<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, Optional<String> maybeBeforeType, String name, String type) {
        var typeTuple = compileTypeOrPlaceholder(state, type);

        var generated = new Definition(maybeBeforeType, name, typeTuple.right);
        return Optional.of(new Tuple<>(typeTuple.left, generated));
    }

    private static Tuple<CompileState, String> compileTypeOrPlaceholder(CompileState state, String type) {
        return compileType(state, type).orElseGet(() -> new Tuple<>(state, generatePlaceholder(type)));
    }

    private static Optional<Tuple<CompileState, String>> compileType(CompileState state, String type) {
        return compileOr(state, type, List.of(
                Main::compileGeneric,
                Main::compilePrimitive
        ));
    }

    private static Optional<Tuple<CompileState, String>> compilePrimitive(CompileState state, String input) {
        return findPrimitiveValue(input.strip()).map(result -> new Tuple<>(state, result));
    }

    private static Optional<String> findPrimitiveValue(String input) {
        return switch (input.strip()) {
            case "char", "Character", "String" -> Optional.of("string");
            case "int", "Integer" -> Optional.of("number");
            case "boolean" -> Optional.of("boolean");
            case "var" -> Optional.of("unknown");
            case "void" -> Optional.of("void");
            default -> Optional.empty();
        };
    }

    private static Optional<Tuple<CompileState, String>> compileGeneric(CompileState state, String input) {
        return compileSuffix(input.strip(), ">", withoutEnd -> {
            return compileFirst(withoutEnd, "<", (baseString, argumentsString) -> {
                var argumentsTuple = compileValues(state, argumentsString, Main::compileTypeArgument);
                return Optional.of(new Tuple<>(argumentsTuple.left, generatePlaceholder(baseString) + "<" + argumentsTuple.right + ">"));
            });
        });
    }

    private static Tuple<CompileState, String> compileTypeArgument(CompileState state, String s) {
        return compileOrPlaceholder(state, s, List.of(
                Main::compileWhitespace,
                Main::compileType
        ));
    }

    private static Tuple<CompileState, String> compileValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return compileAll(state, input, Main::foldValues, mapper, Main::mergeValues);
    }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    }

    private static DivideState foldValues(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '-') {
            var peeked = appended.peek();
            if (peeked == '>') {
                return appended.popAndAppendToOption().orElse(appended);
            }
            else {
                return appended;
            }
        }

        if (c == '<' || c == '(') {
            return appended.enter();
        }

        if (c == '>' || c == ')') {
            return appended.exit();
        }

        return appended;
    }

    private static <T> Optional<T> compileLast(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findLast, mapper);
    }

    private static int findLast(String input, String infix) {
        return input.lastIndexOf(infix);
    }

    private static <T> Optional<T> compileSuffix(String input, String suffix, Function<String, Optional<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var content = input.substring(0, input.length() - suffix.length());
        return mapper.apply(content);
    }

    private static <T> Optional<T> compileFirst(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findFirst, mapper);
    }

    private static <T> Optional<T> compileInfix(String input, String infix, BiFunction<String, String, Integer> locator, BiFunction<String, String, Optional<T>> mapper) {
        return compileSplit(split(input, infix, locator), mapper);
    }

    private static <T> Optional<T> compileSplit(Optional<Tuple<String, String>> splitter, BiFunction<String, String, Optional<T>> mapper) {
        return splitter.flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
    }

    private static Optional<Tuple<String, String>> split(String input, String infix, BiFunction<String, String, Integer> locator) {
        var index = locator.apply(input, infix);
        if (index < 0) {
            return Optional.empty();
        }

        var left = input.substring(0, index);
        var right = input.substring(index + infix.length());
        return Optional.of(new Tuple<>(left, right));
    }

    private static int findFirst(String input, String infix) {
        return input.indexOf(infix);
    }

    private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("/*", "start")
                .replace("*/", "end");

        return "/*" + replaced + "*/";
    }
}
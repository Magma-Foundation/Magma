package magma;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class Main {
	private static class State {
		private final List<String> segments;
		private final Deque<Character> queue;
		private StringBuilder buffer;
		private int depth;
		private State(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth){
			this.segments = segments;
			this.buffer = buffer;
			this.depth = depth;
			this.queue = queue;
		}
		public State(Deque<Character> queue){
			this(queue, new ArrayList<>(), new StringBuilder(), 0);
		}
		private State enter(){
			this.depth = this.depth + 1;
			return this;
		}
		private State append(char c){
			this.buffer.append(c);
			return this;
		}
		private State exit(){
			this.depth = this.depth - 1;
			return this;
		}
		private boolean isShallow(){
			return this.depth == 1;
		}
		private boolean isLevel(){
			return this.depth == 0;
		}
		private State advance(){
			this.segments.add(this.buffer.toString());
			this.buffer = new StringBuilder();
			return this;
		}
		public boolean hasNext(){
			return !this.queue.isEmpty();
		}
		public char pop(){
			return this.queue.pop();
		}
		public char peek(){
			return this.queue.peek();
		}
	}
	public static void main(String[] args){
		try {
			Path source = Paths.get(".", "src", "java", "magma", "Main.java");
			String input = Files.readString(source);
			Path target = source.resolveSibling("main.c");
			Files.writeString(target, compile(input));
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	private static String compile(String input){
		return compileStatementValues(input, Main::compileRootSegment);
	}
	private static String compileStatementValues(String input, Function<String, String> compiler){
		return compileAll(input, Main::foldStatementChar, compiler, Main::mergeStatements);
	}
	private static String compileAll(String input, BiFunction<State, Character, State> folder, Function<String, String> compiler, BiFunction<StringBuilder, String, StringBuilder> merger){
		LinkedList<Character> queue = IntStream.range(0, input.length()).mapToObj(input::charAt).collect(Collectors.toCollection(LinkedList::new));
		State current = new State(queue);
		while (current.hasNext()) {
			char c = current.pop();
			State finalCurrent = current;
			current = foldDoubleQuotes(finalCurrent, c).orElseGet(() -> folder.apply(finalCurrent, c));
		}
		List<String> segments = current.advance().segments;
		StringBuilder output = new StringBuilder();/* 
        for (String segment : segments) {
            String compiled = compiler.apply(segment);
            output = merger.apply(output, compiled);
        } */
		return output.toString();
	}
	private static Optional<State> foldDoubleQuotes(State state, char c){/* 
        if (c != '"') {
            return Optional.empty();
        }

        State appended = state.append(c);
        while (appended.hasNext()) {
            char next = appended.pop();
            appended = appended.append(next);

            if (next == '\\') {
                appended = appended.append(appended.pop());
            }
            if (next == '"') {
                break;
            } */
	}
		return Optional.of(appended);}/* private static StringBuilder mergeStatements(StringBuilder output, String compiled) {
        return output.append(compiled);
    } *//* private static State foldStatementChar(State state, char c) {
        State appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} *//* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//* }

    private static String compileRootSegment(String segment) {
        String stripped = segment.strip(); *//* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        } *//* return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped)); *//* }

    private static Optional<String> compileClass(String stripped, int depth) {
        int classIndex = stripped.indexOf("class "); */if (classIndex >= 0) {            String modifiers = compileModifiers(stripped.substring(0, classIndex));            String afterKeyword = stripped.substring(classIndex + " class ".length());
            int contentStart = afterKeyword.indexOf(" {
	"); if(/* contentStart >= 0 */){
		String className = afterKeyword.substring(0, contentStart).strip();
		String withEnd = afterKeyword.substring(/* contentStart + "{" */.length()).strip();/* 
                if (withEnd.endsWith(" */
	}
	")) {                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());                    String outputContent = compileStatementValues(inputContent, input -> compileClassSegment(input, depth + 1));                    String beforeNode = depth == 0 ? "" : "\n\t";                    String afterChildren = depth == 0 ? "" : "\n" + "\t".repeat(depth);                    return Optional.of(beforeNode + modifiers + " class " + className + " {/* " + outputContent + afterChildren + "}");
                }
             */
	}}/* return Optional.empty(); *//* }

    private static String compileModifiers(String input) {
        return Arrays.stream(input.strip().split(" "))
                .map(String::strip)
                .collect(Collectors.joining(" ")); *//* }

    private static String compileClassSegment(String input, int depth) {
        return compileWhitespace(input)
                .or(() -> compileClass(input, depth))
                .or(() -> compileDefinitionStatement(input))
                .or(() -> compileMethod(input, depth))
                .orElseGet(() -> generatePlaceholder(input)); *//* }

    private static @NotNull Optional<? extends String> compileMethod(String input, int depth) {
        int paramStart = input.indexOf("("); *//* if (paramStart < 0) {
            return Optional.empty();
        } *//* String beforeParams = input.substring(0, paramStart).strip(); *//* return compileDefinition(beforeParams)
                .or(() -> compileConstructorHeader(beforeParams))
                .flatMap(outputDefinition -> {
                    String withParams = input.substring(paramStart + "(".length()).strip();
                    int paramEnd = withParams.indexOf(")");
                    if (paramEnd < 0) {
                        return Optional.empty();
                    }

                    String params = withParams.substring(0, paramEnd).strip();
                    String outputParams = compileValueSegments(params, Main::compileParam);

                    String withBraces = withParams.substring(paramEnd + ")".length()).strip();
                    if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                        String inputContent = withBraces.substring(1, withBraces.length() - 1);
                        String outputContent = compileStatements(inputContent, depth + 1);
                        String indent = "\n" + "\t".repeat(depth);
                        return Optional.of(indent + outputDefinition + "(" + outputParams + "){" + outputContent + indent + "}");
                    }
                    else {
                        return Optional.empty();
                    }
                } *//* ); *//* }

    private static String compileStatements(String inputContent, int depth) {
        return compileStatementValues(inputContent, input -> compileStatementOrBlock(input, depth)); *//* }

    private static String compileParam(String param) {
        return compileWhitespace(param)
                .or(() -> compileDefinition(param))
                .orElseGet(() -> generatePlaceholder(param)); *//* }

    private static String compileStatementOrBlock(String input, int depth) {
        return compileWhitespace(input)
                .or(() -> compileStatement(input, depth))
                .or(() -> compileBlock(input, depth))
                .orElseGet(() -> generatePlaceholder(input)); *//* }

    private static @NotNull Optional<? extends String> compileBlock(String input, int depth) {
        String stripped = input.strip(); *//* if (stripped.endsWith("}")) {
            String withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            int contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                String beforeContent = withoutEnd.substring(0, contentStart).strip();
                String inputContent = withoutEnd.substring(contentStart + "{".length());
                String outputContent = compileStatements(inputContent, depth + 1);

                String indent = "\n" + "\t".repeat(depth);
                return compileBeforeBlock(beforeContent)
                        .map(value -> indent + value + " {" + outputContent + indent + "}");
            }
        } *//* return Optional.empty(); *//* }

    private static Optional<String> compileBeforeBlock(String input) {
        String stripped = input.strip(); *//* if (stripped.equals("try")) {
            return Optional.of("try");
        } *//* return compileBeforeBlockWithQuantity(stripped, "catch", Main::compileDefinition)
                .or(() -> compileBeforeBlockWithQuantity(stripped, "while", value -> Optional.of(compileValue(value)))); *//* }

    private static Optional<String> compileBeforeBlockWithQuantity(String input, String keyword, Function<String, Optional<String>> compileDefinition) {
        String stripped = input.strip(); *//* String prefix = " ("; *//* if (!stripped.startsWith(keyword + prefix)) {
            return Optional.empty();
        } *//* String withoutPrefix = stripped.substring((keyword + prefix).length()).strip(); *//* if (!withoutPrefix.endsWith(")")) {
            return Optional.empty();
        } *//* String inputDefinition = withoutPrefix.substring(0, withoutPrefix.length() - ")".length()); *//* return compileDefinition.apply(inputDefinition).map(outputDefinition -> keyword + prefix + outputDefinition + ")"); *//* }

    private static Optional<String> compileStatement(String input, int depth) {
        String stripped = input.strip(); *//* if (!stripped.endsWith(";")) {
            return Optional.empty();
        } *//* String withoutEnd = stripped.substring(0, stripped.length() - ";".length()); *//* return compileStatementValue(withoutEnd).map(destination -> "\n" + "\t".repeat(depth) + destination + ";"); *//* }

    private static Optional<String> compileStatementValue(String input) {
        String stripped = input.strip(); *//* if (stripped.startsWith("throw ")) {
            String slice = stripped.substring("throw ".length());
            return Optional.of("throw " + compileValue(slice));
        } *//* if (stripped.startsWith("return ")) {
            return Optional.of("return " + compileValue(stripped.substring("return ".length())));
        } *//* int valueSeparator = stripped.indexOf("="); *//* if (valueSeparator >= 0) {
            String inputDestination = stripped.substring(0, valueSeparator).strip();
            String outputDestination = compileDefinition(inputDestination).orElseGet(() -> compileValue(inputDestination));

            String source = stripped.substring(valueSeparator + "=".length()).strip();
            return Optional.of(outputDestination + " = " + compileValue(source));
        } *//* return compileInvocation(stripped); *//* }

    private static Optional<String> compileInvocation(String input) {
        String stripped = input.strip(); *//* if (!stripped.endsWith(")")) {
            return Optional.empty();
        } *//* String withoutArgumentsEnd = stripped.substring(0, stripped.length() - ")".length()); *//* return findArgumentsStart(withoutArgumentsEnd).flatMap(argumentsStart -> {
            String caller = withoutArgumentsEnd.substring(0, argumentsStart);
            String inputArguments = withoutArgumentsEnd.substring(argumentsStart + "(".length());
            String outputArguments = compileValues(inputArguments);
            return Optional.of(compileValue(caller) + "(" + outputArguments + ")");
        } *//* ); *//* }

    private static Optional<Integer> findArgumentsStart(String input) {
        int depth = 0; *//* for (int i = input.length() - 1; *//* i >= 0; *//* i--) {
            char c = input.charAt(i);
            if (c == '(' && depth == 0) {
                return Optional.of(i);
            }
            if (c == ')') {
                depth++;
            }
            if (c == '(') {
                depth--;
            }
        } *//* return Optional.empty(); *//* }

    private static String compileValues(String inputArguments) {
        return compileValueSegments(inputArguments, Main::compileValue); *//* }

    private static Optional<String> compileWhitespace(String input) {
        if (input.isBlank()) {
            return Optional.of("");
        } *//* return Optional.empty(); *//* }

    private static String compileValue(String input) {
        String stripped = input.strip(); *//* if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return stripped;
        } *//* if (stripped.startsWith("new ")) {
            String withoutPrefix = stripped.substring("new ".length()).strip();
            if (withoutPrefix.endsWith(")")) {
                String withoutSuffix = withoutPrefix.substring(0, withoutPrefix.length() - ")".length());
                int argumentStart = withoutSuffix.indexOf("(");
                if (argumentStart >= 0) {
                    String type = withoutSuffix.substring(0, argumentStart).strip();
                    String arguments = withoutSuffix.substring(argumentStart + "(".length()).strip();
                    return "new " + compileTypeOrPlaceholder(type) + "(" + compileValues(arguments) + ")";
                }
            }
        } *//* if (stripped.startsWith("!")) {
            String slice = stripped.substring(1);
            return "!" + compileValue(slice);
        } *//* int arrowIndex = stripped.indexOf("->"); *//* if (arrowIndex >= 0) {
            String beforeArrow = stripped.substring(0, arrowIndex).strip();
            String afterArrow = stripped.substring(arrowIndex + "->".length());
            if (beforeArrow.equals("()") || isSymbol(beforeArrow)) {
                return beforeArrow + " -> " + compileValue(afterArrow);
            }
        } *//* Optional<String> maybeInvocation = compileInvocation(stripped); *//* if (maybeInvocation.isPresent()) {
            return maybeInvocation.get();
        } *//* Optional<String> dataAccess = compileAccess(stripped, "."); *//* if (dataAccess.isPresent()) {
            return dataAccess.get();
        } *//* Optional<String> methodAccess = compileAccess(stripped, "::"); *//* if (methodAccess.isPresent()) {
            return methodAccess.get();
        } *//* if (isSymbol(stripped)) {
            return stripped;
        } *//* if (isNumber(stripped)) {
            return stripped;
        } *//* return generatePlaceholder(stripped); *//* }

    private static Optional<String> compileAccess(String stripped, String separator) {
        int index = stripped.lastIndexOf(separator); *//* if (index < 0) {
            return Optional.empty();
        } *//* String parent = stripped.substring(0, index); *//* String property = stripped.substring(index + separator.length()); *//* return Optional.of(compileValue(parent) + separator + property); *//* }

    private static boolean isNumber(String input) {
        for (int i = 0; *//* i < input.length(); *//* i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                continue;
            }
            return false;
        } *//* return true; *//* }

    private static String compileTypeOrPlaceholder(String type) {
        return compileType(type).orElseGet(() -> generatePlaceholder(type)); *//* }

    private static Optional<String> compileConstructorHeader(String beforeParams) {
        int nameSeparator = beforeParams.lastIndexOf(" "); *//* if (nameSeparator >= 0) {
            String beforeName = beforeParams.substring(0, nameSeparator).strip();
            String name = beforeParams.substring(nameSeparator + " ".length()).strip();
            return Optional.of(compileModifiers(beforeName) + " " + name);
        } *//* if (isSymbol(beforeParams)) {
            return Optional.of(beforeParams);
        } *//* return Optional.empty(); *//* }

    private static @NotNull Optional<? extends String> compileDefinitionStatement(String input) {
        String stripped = input.strip(); *//* if (!stripped.endsWith(";")) {
            return Optional.empty();
        } *//* String slice = stripped.substring(0, stripped.length() - ";".length()).strip(); *//* return compileDefinition(slice).flatMap(definition -> Optional.of("\n\t\t" + definition + ";")); *//* }

    private static Optional<String> compileDefinition(String input) {
        int nameSeparator = input.lastIndexOf(" "); *//* if (nameSeparator >= 0) {
            String beforeName = input.substring(0, nameSeparator).strip();
            String name = input.substring(nameSeparator + " ".length()).strip();
            return findTypeSeparator(beforeName).flatMap(typeSeparator -> {
                String beforeType = beforeName.substring(0, typeSeparator).strip();
                String inputType = beforeName.substring(typeSeparator + " ".length()).strip();
                Optional<String> maybeOutputType = compileType(inputType);
                if (maybeOutputType.isEmpty()) {
                    return Optional.empty();
                }
                String withBeforeType = compileModifiers(beforeType) + " " + maybeOutputType.get();
                return Optional.of(withBeforeType + " " + name);
            }).or(() -> compileType(beforeName).map(type -> type + " " + name));
        } *//* return Optional.empty(); *//* }

    private static Optional<Integer> findTypeSeparator(String input) {
        int depth = 0; *//* for (int i = input.length() - 1; *//* i >= 0; *//* i--) {
            char c = input.charAt(i);
            if (c == ' ' && depth == 0) {
                return Optional.of(i);
            }
            if (c == '>') {
                depth++;
            }
            if (c == '<') {
                depth--;
            }
        } *//* return Optional.empty(); *//* }

    private static Optional<String> compileType(String type) {
        String stripped = type.strip(); *//* if (stripped.endsWith("[]")) {
            return compileType(stripped.substring(0, stripped.length() - "[]".length()))
                    .map(inner -> inner + "[]");
        } *//* if (stripped.endsWith(">")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ">".length());
            int paramStart = withoutEnd.indexOf("<");
            if (paramStart >= 0) {
                String base = withoutEnd.substring(0, paramStart).strip();
                String oldArguments = withoutEnd.substring(paramStart + "<".length());
                String newArguments = compileValueSegments(oldArguments, Main::compileTypeOrPlaceholder);
                return Optional.of(base + "<" + newArguments + ">");
            }
        } *//* if (isSymbol(stripped)) {
            return Optional.of(stripped);
        } *//* return Optional.empty(); *//* }

    private static String compileValueSegments(String input, Function<String, String> compiler) {
        return compileAll(input, Main::foldValueChar, compiler, Main::mergeValues); *//* }

    private static boolean isSymbol(String input) {
        if (input.equals("private") || input.equals("public")) {
            return false;
        } *//* for (int i = 0; *//* i < input.length(); *//* i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* return true; *//* }

    private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        } *//* return cache.append(", ").append(element); *//* }

    private static State foldValueChar(State state, Character c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        } *//* State appended = state.append(c); *//* if (c == '-') {
            if (state.peek() == '>') {
                return state.append(state.pop());
            }
        } *//* if (c == '<' || c == '(') {
            return appended.enter();
        } *//* if (c == '>' || c == ')') {
            return appended.exit();
        } *//* return appended; *//* }

    private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("<content-start>", "<content-start>")
                .replace("<content-end>", "<content-end>"); *//* return "<content-start> " + replaced + " <content-end>"; *//* }
} */
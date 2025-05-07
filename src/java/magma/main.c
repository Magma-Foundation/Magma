package magma;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
public class Main {
	private interface Collector<T, C> {
		/* C */ createInitial(/*  */)/* ; */
		/* C */ fold(/* C */ current, /* T */ element)/* ; *//* 
     */}
	private interface Iterator<T> {
		<R> /* Iterator<R> */ map(Function<T, /* R> */ mapper)/* ; */
		<C> /* C */ collect(Collector<T, /* C> */ collector)/* ; */
		<C> /* C */ fold(/* C */ initial, BiFunction<C, T, /* C> */ folder)/* ; *//* 
     */}
	private interface List<T> {
		/* List<T> */ add(/* T */ element)/* ; */
		/* Iterator<T> */ iterate(/*  */)/* ; *//* 
     */}
	private interface Head<T> {
		/* Optional<T> */ next(/*  */)/* ; *//* 
     */}
	private static class RangeHead implements Head<Integer> {
		private final /* int */ length;/* 
        private int counter = 0; */
		/* public */ RangeHead(/* int */ length)/*  {
            this.length = length;
        } */
		@Override
        public /* Optional<Integer> */ next(/*  */)/*  {
            if (this.counter >= this.length) {
                return Optional.empty();
            }

            var value = this.counter;
            this.counter++;
            return Optional.of(value);
        } *//* 
     */}/* 

    private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <C> C fold(C initial, BiFunction<C, T, C> folder) {
            var current = initial;
            while (true) {
                C finalCurrent = current;
                var folded = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (folded.isPresent()) {
                    current = folded.get();
                }
                else {
                    return current;
                }
            }
        }
    } */
	private static class Lists {/* 
        private record MutableList<T>(java.util.List<T> elements) implements List<T> {
            public MutableList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                this.elements.add(element);
                return this;
            }

            @Override
            public Iterator<T> iterate() {
                return new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
            }
        } */
		public static <T> /* List<T> */ empty(/*  */)/*  {
            return new MutableList<>();
        } *//* 
     */}
	private static class State {
		private /* List<String> */ segments;
		private /* int */ depth;
		private /* StringBuilder */ buffer;
		/* private */ State(/* List<String> */ segments, /* StringBuilder */ buffer, /* int */ depth)/*  {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
		/* public */ State(/*  */)/*  {
            this(Lists.empty(), new StringBuilder(), 0);
        } */
		private /* State */ append(/* char */ c)/*  {
            this.buffer.append(c);
            return this;
        } */
		private /* State */ advance(/*  */)/*  {
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } */
		public /* boolean */ isLevel(/*  */)/*  {
            return this.depth == 0;
        } */
		public /* State */ enter(/*  */)/*  {
            this.depth++;
            return this;
        } */
		public /* State */ exit(/*  */)/*  {
            this.depth--;
            return this;
        } */
		public /* boolean */ isShallow(/*  */)/*  {
            return this.depth == 1;
        } *//* 
     */}
	private /* record */ Joiner(/* String */ delimiter)/*  implements Collector<String, Optional<String>> {
        @Override
        public Optional<String> createInitial() {
            return Optional.empty();
        }

        @Override
        public Optional<String> fold(Optional<String> maybeCurrent, String element) {
            return Optional.of(maybeCurrent.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    } */
	public static /* void */ main(/*  */)/*  {
        var root = Paths.get(".", "src", "java", "magma");
        var source = root.resolve("Main.java");
        var target = root.resolve("main.c");

        try {
            var input = Files.readString(source);
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */
	private static /* String */ compile(/* String */ input)/*  {
        return compileStatements(input, Main::compileRootSegment);
    } */
	private static /* String */ compileStatements(/* String */ input, Function<String, /* String> */ mapper)/*  {
        return compileAll(input, Main::fold, mapper, "");
    } */
	private static /* String */ compileAll(/* String */ input, BiFunction<State, Character, /* State> */ folder, Function<String, /* String> */ mapper, /* String */ delimiter)/*  {
        return divide(input, folder)
                .iterate()
                .map(mapper)
                .collect(new Joiner(delimiter))
                .orElse("");
    } */
	private static /* List<String> */ divide(/* String */ input, BiFunction<State, Character, /* State> */ folder)/*  {
        State state = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            state = folder.apply(state, c);
        }

        return state.advance().segments;
    } */
	private static /* State */ fold(/* State */ state, /* char */ c)/*  {
        var appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} *//* ' && appended.isShallow()) {
            return appended.advance().exit();
        } */
	/* if */ (/* c == '{' */)/*  {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } */
	/* return */ appended;/* 
     */}
/* 

    private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return stripped + "\n";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(input));
    } *//* 

    private static Optional<String> compileClass(String input, int depth) {
        return compileStructure(input, depth, "class ");
    } *//* 

    private static Optional<String> compileStructure(String input, int depth, String infix) {
        var stripped = input.strip();
        if (stripped.endsWith("} *//* ")) {
            var withoutEnd = stripped.substring(0, stripped.length() - "} *//* ".length()); *//* 
            var contentStart = withoutEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = withoutEnd.substring(0, contentStart);
                var content = withoutEnd.substring(contentStart + "{".length());
                var keywordIndex = beforeContent.indexOf(infix);
                if (keywordIndex >= 0) {
                    var left = beforeContent.substring(0, keywordIndex);
                    var right = beforeContent.substring(keywordIndex + infix.length()).strip();
                    var generated = left + infix + right + " {" + compileStatements(content, segment -> compileClassStatement(segment, depth + 1)) + "}";
                    return Optional.of(depth == 0 ? generated + "\n" : (createIndent(depth) + generated));
                }
            }
        }

        return Optional.empty();
    } *//* 

    private static String compileClassStatement(String input, int depth) {
        return compileClass(input, depth)
                .or(() -> compileStructure(input, depth, "interface "))
                .or(() -> compileDefinitionStatement(input, depth))
                .or(() -> compileMethod(input, depth))
                .orElseGet(() -> generatePlaceholder(input));
    } *//* 

    private static Optional<String> compileMethod(String input, int depth) {
        var stripped = input.strip();
        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var left = stripped.substring(0, paramStart);
            var withParams = stripped.substring(paramStart + "(".length());
            return compileDefinition(left).flatMap(definition -> {
                var paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    var params = withParams.substring(0, paramEnd);
                    var content = withParams.substring(paramEnd + ")".length());
                    return Optional.of(createIndent(depth) + definition + "(" + compileAll(params, Main::foldValueChar, Main::compileParameter, ", ") + ")" + generatePlaceholder(content));
                }
                return Optional.empty();
            });
        }

        return Optional.empty();
    } *//* 

    private static State foldValueChar(State state, char c) {
        if (c == ',' && state.isLevel()) {
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
    } *//* 

    private static String compileParameter(String input) {
        return compileDefinition(input).orElseGet(() -> generatePlaceholder(input));
    } *//* 

    private static Optional<String> compileDefinitionStatement(String input, int depth) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }

        var definition = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition(definition).map(generated -> generateStatement(generated, depth));
    } *//* 

    private static Optional<String> compileDefinition(String input) {
        var nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var beforeName = input.substring(0, nameSeparator).strip();
            var name = input.substring(nameSeparator + " ".length()).strip();
            if (isSymbol(name)) {
                var typeSeparator = beforeName.lastIndexOf(" ");
                if (typeSeparator >= 0) {
                    var beforeType = beforeName.substring(0, typeSeparator);
                    var type = beforeName.substring(typeSeparator + " ".length());
                    return Optional.of(generateDefinition(Optional.of(beforeType), type, name));
                }
                else {
                    return Optional.of(generateDefinition(Optional.empty(), beforeName, name));
                }
            }
        }
        return Optional.empty();
    } *//* 

    private static String generateDefinition(Optional<String> maybeBeforeType, String type, String name) {
        var beforeTypeString = maybeBeforeType.map(beforeType -> beforeType + " ").orElse("");
        return beforeTypeString + compileType(type) + " " + name;
    } *//* 

    private static String generateStatement(String content, int depth) {
        return createIndent(depth) + content + ";";
    } *//* 

    private static String compileType(String type) {
        return generatePlaceholder(type);
    } *//* 

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } *//* 

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    } *//* 

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    } *//* 
}
 */
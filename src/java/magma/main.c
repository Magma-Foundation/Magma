// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
/* public */ struct Collector<T, C> {
	/* C */ createInitial(/* ); */
	/* C */ fold(/* C current, T element); *//* 
     */
};
/* public */ struct Iterator<T> {
	/* <C> */ /* C */ collect(/* Collector<T, C> collector); */
	/* <R> */ /* R */ fold(/* R initial, BiFunction<R, T, R> folder); */
	/* <R> */ /* Iterator<R> */ map(/* Function<T, R> mapper); *//* 
     */
};
/* public */ struct List<T> {
	/* Iterator<T> */ iter(/* ); */
	/* List<T> */ add(/* T element); *//* 
     */
};
/* private */ struct Head<T> {
	/* Optional<T> */ next(/* ); *//* 
     */
};
/* private static */ struct DivideState {
	/* private final */ /* List<String> */ segments;
	/* private */ /* StringBuilder */ buffer;
	/* private */ /* int */ depth;
	/* private */ DivideState(/* List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
	/* public */ DivideState(/* ) {
            this(Lists.empty(), new StringBuilder(), 0);
        } */
	/* private */ /* DivideState */ exit(/* ) {
            this.depth = this.depth - 1;
            return this;
        } */
	/* private */ /* DivideState */ enter(/* ) {
            this.depth = this.depth + 1;
            return this;
        } */
	/* private */ /* DivideState */ advance(/* ) {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } */
	/* private */ /* boolean */ isShallow(/* ) {
            return this.depth == 1;
        } */
	/* private */ /* DivideState */ append(/* char c) {
            this.buffer.append(c);
            return this;
        } */
	/* private */ /* boolean */ isLevel(/* ) {
            return this.depth == 0;
        } */
	/* public */ /* List<String> */ segments(/* ) {
            return this.segments;
        } *//* 
     */
};
/* private static */ struct Joiner implements Collector<String, Optional<String>> {
	/* @Override
        public */ /* Optional<String> */ createInitial(/* ) {
            return Optional.empty();
        } */
	/* @Override
        public */ /* Optional<String> */ fold(/* Optional<String> current, String element) {
            return Optional.of(current.map(inner -> inner + element).orElse(element));
        } *//* 
     */
};
/* public static final */ struct RangeHead implements Head<Integer> {
	/* private final */ /* int */ length;
	/* private int counter */ /* = */ 0;
	/* public */ RangeHead(/* int length) {
            this.length = length;
        } */
	/* @Override
        public */ /* Optional<Integer> */ next(/* ) {
            if (this.counter >= this.length) {
                return Optional.empty();
            }

            int value = this.counter;
            this.counter++;
            return Optional.of(value);
        } *//* 
     */
};
/* public */ struct Main {
	/* record */ /* Tuple<A, */ B>(/* A left, B right) {
    } */
	/* record */ CompileState(/* List<String> imports, List<String> structs) {
        public CompileState() {
            this(Lists.empty(), Lists.empty());
        }

        public CompileState addStruct(String struct) {
            this.structs.add(struct);
            return this;
        }

        public CompileState addImport(String imports) {
            this.imports.add(imports);
            return this;
        }
    } */
	/* public */ /* record */ HeadedIterator<T>(/* Head<T> head) implements Iterator<T> {
        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Optional<R> optional = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (optional.isEmpty()) {
                    return current;
                }
                current = optional.get();
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }
    } */
	/* public static */ /* void */ main(/* String[] args) {
        try {
            Path path = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(path);

            Path target = path.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    } */
	/* private static */ /* String */ compile(/* String input) {
        CompileState oldState = new CompileState();
        Tuple<CompileState, String> output = compileStatements(oldState, input, Main::compileRootSegment);
        CompileState newState = output.left;

        String joinedImports = join(newState.imports);
        String joinedStructs = join(newState.structs);
        return joinedImports + joinedStructs + output.right;
    } */
	/* private static */ /* String */ join(/* List<String> list) {
        return list.iter()
                .collect(new Joiner())
                .orElse("");
    } */
	/* private static Tuple<CompileState, */ /* String> */ compileStatements(/* CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> compiler) {
        List<String> segments = divide(input);

        Tuple<CompileState, StringBuilder> fold = segments.iter().fold(new Tuple<CompileState, StringBuilder>(state, new StringBuilder()),
                (current, element) -> compileSegment(compiler, current, element));

        return new Tuple<>(fold.left, fold.right.toString());
    } */
	/* private static Tuple<CompileState, */ /* StringBuilder> */ compileSegment(/* BiFunction<CompileState, String, Tuple<CompileState, String>> compiler, Tuple<CompileState, StringBuilder> current, String element) {
        CompileState currentState = current.left;
        StringBuilder currentCache = current.right;

        Tuple<CompileState, String> compiled = compiler.apply(currentState, element);
        CompileState newState = compiled.left;
        StringBuilder newCache = currentCache.append(compiled.right);
        return new Tuple<>(newState, newCache);
    } */
	/* private static */ /* List<String> */ divide(/* String input) {
        DivideState current = new DivideState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().segments;
    } */
	/* private static */ /* DivideState */ foldStatementChar(/* DivideState state, char c) {
        DivideState appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} */
	/* ' */ /* && */ appended.isShallow(/* )) {
            return appended.advance().exit();
        } *//* 
        if (c == '{') {
            return appended.enter();
        }
        if (c == '} */
	/* ') { */ /* return */ appended.exit(/* );
        } */
	/* return */ appended;/* 
     */
};
/* 

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        if (input.startsWith("package ")) {
            return new Tuple<>(state, "");
        }
        if (input.strip().startsWith("import ")) {
            return new Tuple<>(state.addImport("// #include <temp.h>\n"), "");
        }

        return compileClass(state, input).orElseGet(() -> compileContent(state, input));
    } *//* 

    private static Tuple<CompileState, String> compileContent(CompileState state, String input) {
        return new Tuple<>(state, generatePlaceholder(input));
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileClass(CompileState state, String input) {
        return compileStructured("class ", state, input);
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileStructured(String infix, CompileState state, String input) {
        int classIndex = input.indexOf(infix);
        if (classIndex >= 0) {
            String modifiers = input.substring(0, classIndex).strip();
            String afterKeyword = input.substring(classIndex + infix.length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    Tuple<CompileState, String> content = compileStatements(state, inputContent, Main::compileClassSegment);

                    String format = "%s struct %s {%s\n};\n";
                    String message = format.formatted(generatePlaceholder(modifiers), name, content.right);
                    return Optional.of(new Tuple<>(content.left.addStruct(message), ""));
                }
            }
        }
        return Optional.empty();
    } *//* 

    private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input) {
        return compileClass(state, input)
                .or(() -> compileStructured("interface ", state, input))
                .or(() -> compileMethod(state, input))
                .or(() -> compileDefinitionStatement(state, input))
                .orElseGet(() -> compileContent(state, input));
    } *//* 

    private static @NotNull Optional<? extends Tuple<CompileState, String>> compileMethod(CompileState state, String input) {
        int paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            String definition = input.substring(0, paramStart);
            String withParams = input.substring(paramStart + "(".length());
            return compileDefinition(state, definition).map(outputDefinition -> {
                return new Tuple<>(outputDefinition.left, "\n\t" + outputDefinition.right + "(" + generatePlaceholder(withParams));
            });
        }
        return Optional.empty();
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileDefinitionStatement(CompileState state, String input) {
        String stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return Optional.empty();
        }
        String inputDefinition = stripped.substring(0, stripped.length() - ";".length());
        return compileDefinition(state, inputDefinition).map(outputDefinition -> {
            return new Tuple<>(outputDefinition.left, "\n\t" + outputDefinition.right + ";");
        });
    } *//* 

    private static Optional<Tuple<CompileState, String>> compileDefinition(CompileState state, String definition) {
        String withoutEnd = definition.strip();
        int nameSeparator = withoutEnd.lastIndexOf(" ");
        if (nameSeparator < 0) {
            return Optional.empty();
        }
        String beforeName = withoutEnd.substring(0, nameSeparator).strip();
        String name = withoutEnd.substring(nameSeparator + " ".length());

        int typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator < 0) {
            return generateDefinition(state, generatePlaceholder(beforeName), name);
        }
        String beforeType = beforeName.substring(0, typeSeparator).strip();
        String type = beforeName.substring(typeSeparator + " ".length());
        String outputBeforeName = generatePlaceholder(beforeType) + " " + generatePlaceholder(type);
        return generateDefinition(state, outputBeforeName, name);
    } *//* 

    private static Optional<Tuple<CompileState, String>> generateDefinition(
            CompileState state,
            String outputBeforeName,
            String name
    ) {
        String generated = outputBeforeName + " " + name;
        return Optional.of(new Tuple<>(state, generated));
    } *//* 

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    } *//* 
}
 */
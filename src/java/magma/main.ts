/* private static */class Lists {
	/* private */ JVMList<T>(elements : java.util.List<T>) : record/* implements List<T> {


            public JVMList() {
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

            @Override
            public Option<Tuple<List<T>, T>> removeLast() {
                if (this.elements.isEmpty()) {
                    return new None<>();
                }

                var slice = this.elements.subList(0, this.elements.size() - 1);
                var last = this.elements.getLast();
                return new Some<>(new Tuple<List<T>, T>(new JVMList<>(slice), last));
            }

            @Override
            public T get(int index) {
                return this.elements.get(index);
            }
        } */
	/* public static  */ empty<T>() : List<T>/* {
            return new JVMList<>();
        } */
	/* public static  */ of<T>(elements : /* T... */) : List<T>/* {
            return new JVMList<>(new ArrayList<>(Arrays.asList(elements)));
        } */
}
/* private static */class DivideState {
	/* private */ segments : List<String>;
	/* private */ buffer : StringBuilder;
	/* private */ depth : number;
	DivideState(segments : List<String>, buffer : StringBuilder, depth : number) : public/* {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
	DivideState() : public/* {
            this(Lists.empty(), new StringBuilder(), 0);
        } */
	/* private */ advance() : DivideState/* {
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } */
	/* private */ append(c : char) : DivideState/* {
            this.buffer.append(c);
            return this;
        } */
	/* public */ enter() : DivideState/* {
            this.depth++;
            return this;
        } */
	/* public */ isLevel() : boolean/* {
            return this.depth == 0;
        } */
	/* public */ exit() : DivideState/* {
            this.depth--;
            return this;
        } */
	/* public */ isShallow() : boolean/* {
            return this.depth == 1;
        } */
}
/* public */class Main {
	/* private interface Option */ map<T> {
        <R>(mapper : (T) => R) : Option<R>/* ;

        boolean isPresent();

        T orElse(T other);

        Option<T> filter(Predicate<T> predicate);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);
    } */
	/* private interface Collector<T, C> {
        */ createInitial() : C/* ;

        C fold(C current, T element);
    } */
	/* private interface Iterator */ fold<T> {
        <R>(initial : R, folder : (R, T) => R) : R/* ;

        <R> Iterator<R> map(Function<T, R> mapper);

        <R> R collect(Collector<T, R> collector);
    } */
	/* private interface List<T> {
        */ add(element : T) : List<T>/* ;

        Iterator<T> iterate();

        Option<Tuple<List<T>, T>> removeLast();

        T get(int index);
    } */
	/* private interface Head<T> {
        */ next() : Option<T>/* ;
    } */
	/* private */ Some<T>(value : T) : record/* implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            return predicate.test(this.value) ? this : new None<>();
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }
    } */
	/* private static class None */ map<T> implements Option<T> {
        @Override
        public <R>(mapper : (T) => R) : Option<R>/* {
            return new None<>();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public T orElse(T other) {
            return other;
        }

        @Override
        public Option<T> filter(Predicate<T> predicate) {
            return new None<>();
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }
    } */
	/* private */ HeadedIterator<T>(head : Head<T>) : record/* implements Iterator<T> {
        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.orElse(null);
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }

        @Override
        public <R> R collect(Collector<T, R> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }
    } */
	/* private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        */ RangeHead(length : number) : public/* {
            this.length = length;
        }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            }

            return new None<>();
        }
    } */
	/* private */ CompileState(structures : List<String>) : record/* {
        public CompileState() {
            this(Lists.empty());
        }

        public CompileState addStructure(String structure) {
            return new CompileState(this.structures.add(structure));
        }
    } */
	/* private */ Joiner(delimiter : String) : record/* implements Collector<String, Option<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    } */
	/* private */ Definition(maybeBefore : Option<String>, type : String, name : String, typeParams : List<String>) : record/* {
        private String generate() {
            return this.generateWithParams("");
        }

        public String generateWithParams(String params) {
            var joined = this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            var before = this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");

            return before + this.name + joined + params + " : " + this.type;
        }
    } */
	/* private static class ListCollector<T> implements Collector<T, List<T>> {
        @Override
        public */ createInitial() : List<T>/* {
            return Lists.empty();
        }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        }
    } */
	/* private record */ B>(left : A, right : B) : /* Tuple<A, *//* {
    } */
	/* public static */ main() : void/* {
        try {
            var parent = Paths.get(".", "src", "java", "magma");
            var source = parent.resolve("Main.java");
            var target = parent.resolve("main.ts");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    } */
	/* private static */ compile(input : String) : String/* {
        var tuple = compileStatements(new CompileState(), input, Main::compileRootSegment);
        var joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("");
        return joined + tuple.right;
    } */
	/* private static */ compileStatements(state : CompileState, input : String, mapper : (CompileState, String) => [CompileState, String]) : [CompileState, String]/* {
        return compileAll(state, input, Main::foldStatementChar, mapper, Main::mergeStatements);
    } */
	/* private static */ compileAll(state : CompileState, input : String, folder : (DivideState, Character) => DivideState, mapper : (CompileState, String) => [CompileState, String], merger : (StringBuilder, String) => StringBuilder) : [CompileState, String]/* {
        var parsed = parseAll(state, input, folder, mapper);
        var generated = generateAll(merger, parsed.right);
        return new Tuple<>(parsed.left, generated);
    } */
	/* private static */ generateAll(merger : (StringBuilder, String) => StringBuilder, elements : List<String>) : String/* {
        return elements
                .iterate()
                .fold(new StringBuilder(), merger)
                .toString();
    } */
	/* private static */ parseAll(state : CompileState, input : String, folder : (DivideState, Character) => DivideState, mapper : (CompileState, String) => [CompileState, String]) : [CompileState, List<String>]/* {
        return divideAll(input, folder).iterate().fold(new Tuple<>(state, Lists.empty()), (tuple, element) -> {
            var state1 = tuple.left;
            var right = tuple.right;

            var applied = mapper.apply(state1, element);
            return new Tuple<>(applied.left, right.add(applied.right));
        });
    } */
	/* private static */ mergeStatements(stringBuilder : StringBuilder, str : String) : StringBuilder/* {
        return stringBuilder.append(str);
    } */
	/* private static */ divideAll(input : String, folder : (DivideState, Character) => DivideState) : List<String>/* {
        var current = new DivideState();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
	/* private static */ foldStatementChar(state : DivideState, c : char) : DivideState/* {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '} */
	/* ' */ append.isShallow() : /* && *//* ) {
            return append.advance().exit();
        } */
	/* 
        if  *//* {
            return append.enter();
        }
        if (c == '} */
	/* ') {
            */ append.exit() : return/* ;
        } */
	append : return;
}
/* private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return compileClass(stripped, 0, state)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
    } *//* private static Option<Tuple<CompileState, String>> compileClass(String stripped, int depth, CompileState state) {
        return compileStructure(stripped, "class ", "class ", state);
    } *//* private static Option<Tuple<CompileState, String>> compileStructure(String stripped, String sourceInfix, String targetInfix, CompileState state) {
        return first(stripped, sourceInfix, (beforeInfix, right) -> {
            return first(right, "{", (name, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", content1 -> {
                    var strippedName = name.strip();
                    if (!isSymbol(strippedName)) {
                        return new None<>();
                    }

                    var statements = compileStatements(state, content1, (state0, input) -> compileClassSegment(state0, input, 1));
                    var generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + strippedName + " {" + statements.right + "\n}\n";
                    return new Some<>(new Tuple<>(statements.left.addStructure(generated), ""));
                });
            });
        });
    } *//* private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } *//* private static <T> Option<T> suffix(String input, String suffix, Function<String, Option<T>> mapper) {
        if (!input.endsWith(suffix)) {
            return new None<>();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    } *//* private static Tuple<CompileState, String> compileClassSegment(CompileState state, String input, int depth) {
        return compileWhitespace(input, state)
                .or(() -> compileClass(input, depth, state))
                .or(() -> compileStructure(input, "interface ", "interface ", state))
                .or(() -> compileStructure(input, "record ", "class ", state))
                .or(() -> compileMethod(input, depth, state))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } *//* private static Option<Tuple<CompileState, String>> compileWhitespace(String input, CompileState state) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, ""));
        }
        return new None<>();
    } *//* private static Option<Tuple<CompileState, String>> compileMethod(String input, int depth, CompileState state) {
        return first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var content = rawContent.strip();
                var newContent = content.equals(";") ? ";" : generatePlaceholder(content);

                var tuple = parseDefinition(state, definition)
                        .map(definition1 -> {
                            var paramsTuple = compileValues(state, params, Main::compileParameter);
                            var generated = definition1.right.generateWithParams("(" + paramsTuple.right + ")");
                            return new Tuple<>(paramsTuple.left, generated);
                        })
                        .orElseGet(() -> new Tuple<>(state, generatePlaceholder(definition)));

                var s = createIndent(depth) + tuple.right + newContent;
                return new Some<>(new Tuple<>(tuple.left, s));
            });
        });
    } *//* private static Tuple<CompileState, String> compileValues(CompileState state, String params, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var parsed = parseValues(state, params, mapper);
        var generated = generateValues(parsed.right);
        return new Tuple<>(parsed.left, generated);
    } *//* private static String generateValues(List<String> elements) {
        return generateAll(Main::mergeValues, elements);
    } *//* private static Tuple<CompileState, List<String>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return parseAll(state, input, Main::foldValueChar, mapper);
    } *//* private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        if (input.isBlank()) {
            return new Tuple<>(state, "");
        }

        return parseDefinition(state, input)
                .map((Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } *//* private static StringBuilder mergeValues(StringBuilder cache, String element) {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    } *//* private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    } *//* private static Option<Tuple<CompileState, String>> compileDefinitionStatement(String input, int depth, CompileState state) {
        return suffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd).map(result -> {
                var generated = createIndent(depth) + result.right.generate() + ";";
                return new Tuple<>(result.left, generated);
            });
        });
    } *//* private static Option<Tuple<CompileState, Definition>> parseDefinition(CompileState state, String input) {
        return last(input.strip(), " ", (beforeName, name) -> {
            return split(() -> getStringStringTuple(beforeName), (beforeType, type) -> {
                return suffix(beforeType.strip(), ">", withoutTypeParamStart -> {
                    return first(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) -> {
                        var typeParams = parseValues(state, typeParamsString, (state1, s) -> new Tuple<>(state1, s.strip()));
                        return assembleDefinition(typeParams.left, new Some<String>(beforeTypeParams), name, typeParams.right, type);
                    });
                }).or(() -> {
                    return assembleDefinition(state, new Some<String>(beforeType), name, Lists.empty(), type);
                });
            }).or(() -> {
                return assembleDefinition(state, new None<String>(), name, Lists.empty(), beforeName);
            });
        });
    } *//* private static Option<Tuple<String, String>> getStringStringTuple(String beforeName) {
        var divisions = divideAll(beforeName, Main::foldTypeSeparator);
        return divisions.removeLast().map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        });
    } *//* private static DivideState foldTypeSeparator(DivideState state, Character c) {
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
    } *//* private static Option<Tuple<CompileState, Definition>> assembleDefinition(CompileState state, Option<String> beforeTypeParams, String name, List<String> typeParams, String type) {
        var type1 = type(state, type);
        var node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
        return new Some<>(new Tuple<>(type1.left, node));
    } *//* private static DivideState foldValueChar(DivideState state, char c) {
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
    } *//* private static Tuple<CompileState, String> type(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.equals("int")) {
            return new Tuple<>(state, "number");
        }

        if (isSymbol(stripped)) {
            return new Tuple<>(state, stripped);
        }

        return template(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
    } *//* private static Option<Tuple<CompileState, String>> template(CompileState state, String input) {
        return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                var strippedBase = base.strip();
                var argumentsTuple = parseValues(state, argumentsString, Main::type);
                var argumentsState = argumentsTuple.left;
                var arguments = argumentsTuple.right;

                if (base.equals("BiFunction")) {
                    return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0), arguments.get(1)), arguments.get(2))));
                }

                if (base.equals("Function")) {
                    return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0)), arguments.get(1))));
                }

                if (base.equals("Predicate")) {
                    return new Some<>(new Tuple<>(argumentsState, generate(Lists.of(arguments.get(0)), "boolean")));
                }

                if (base.equals("Supplier")) {
                    return new Some<>(new Tuple<>(argumentsState, generate(Lists.empty(), arguments.get(0))));
                }

                if (base.equals("Tuple")) {
                    return new Some<>(new Tuple<>(argumentsState, "[" + arguments.get(0) + ", " + arguments.get(1) + "]"));
                }

                return new Some<>(new Tuple<>(argumentsState, strippedBase + "<" + generateValues(arguments) + ">"));
            });
        });
    } *//* private static String generate(List<String> arguments, String returns) {
        var joined = arguments.iterate()
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + joined + ") => " + returns;
    } *//* private static <T> Option<T> last(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return infix(input, infix, Main::findLast, mapper);
    } *//* private static Option<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    } *//* private static <T> Option<T> first(String input, String infix, BiFunction<String, String, Option<T>> mapper) {
        return infix(input, infix, Main::findFirst, mapper);
    } *//* private static <T> Option<T> infix(
            String input,
            String infix,
            BiFunction<String, String, Option<Integer>> locator,
            BiFunction<String, String, Option<T>> mapper
    ) {
        return split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple<>(left, right);
        }), mapper);
    } *//* private static <T> Option<T> split(Supplier<Option<Tuple<String, String>>> splitter, BiFunction<String, String, Option<T>> mapper) {
        return splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
    } *//* private static Option<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    } *//* private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start " + replaced + " content-end";
    } *//* } */
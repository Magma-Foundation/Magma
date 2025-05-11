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
	/* private final */ input : String;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : List<String>;
	/* private */ buffer : StringBuilder;
	DivideState(input : String, index : number, segments : List<String>, buffer : StringBuilder, depth : number) : public/* {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
            this.input = input;
            this.index = index;
        } */
	DivideState(input : String) : public/* {
            this(input, 0, Lists.empty(), new StringBuilder(), 0);
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
	/* public */ pop() : Option<[Character, DivideState]>/* {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<>(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
            }

            return new None<>();
        } */
	/* public */ popAndAppendToTuple() : Option<[Character, DivideState]>/* {
            return this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left)));
        } */
	/* public */ popAndAppendToOption() : Option<DivideState>/* {
            return popAndAppendToTuple().map(Tuple::right);
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
        var current = new DivideState(input);
        while (true) {
            var maybePopped = current.pop().map(tuple -> {
                return divideSingleQuotes(tuple)
                        .orElseGet(() -> folder.apply(tuple.right, tuple.left));
            });

            if (maybePopped.isPresent()) {
                current = maybePopped.orElse(current);
            }
            else {
                break;
            }
        }

        return current.advance().segments;
    } */
	/* private static */ divideSingleQuotes(tuple : [Character, DivideState]) : Option<DivideState>/* {
        if (tuple.left == '\'') {
            var appended = tuple.right.append(tuple.left);
            return appended.popAndAppendToTuple()
                    .map(escaped -> escaped.left == '\\' ? escaped.right.popAndAppendToOption().orElse(escaped.right) : escaped.right)
                    .flatMap(DivideState::popAndAppendToOption);
        }

        return new None<>();

    } */
	/* private static */ foldStatementChar(state : DivideState, c : char) : DivideState/* {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '}' && append.isShallow()) {
            return append.advance().exit();
        }
        if (c == '{') {
            return append.enter();
        }
        if (c == '}') {
            return append.exit();
        }
        return append;
    } */
	/* private static */ compileRootSegment(state : CompileState, input : String) : [CompileState, String]/* {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        }

        return compileClass(stripped, 0, state)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
    } */
	/* private static */ compileClass(stripped : String, depth : number, state : CompileState) : Option<[CompileState, String]>/* {
        return compileStructure(stripped, "class ", "class ", state);
    } */
	/* private static */ compileStructure(stripped : String, sourceInfix : String, targetInfix : String, state : CompileState) : Option<[CompileState, String]>/* {
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
    } */
	/* private static */ isSymbol(input : String) : boolean/* {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } */
	/* private static  */ suffix<T>(input : String, suffix : String, mapper : (String) => Option<T>) : Option<T>/* {
        if (!input.endsWith(suffix)) {
            return new None<>();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    } */
	/* private static */ compileClassSegment(state : CompileState, input : String, depth : number) : [CompileState, String]/* {
        return compileWhitespace(input, state)
                .or(() -> compileClass(input, depth, state))
                .or(() -> compileStructure(input, "interface ", "interface ", state))
                .or(() -> compileStructure(input, "record ", "class ", state))
                .or(() -> compileMethod(input, depth, state))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } */
	/* private static */ compileWhitespace(input : String, state : CompileState) : Option<[CompileState, String]>/* {
        if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, ""));
        }
        return new None<>();
    } */
	/* private static */ compileMethod(input : String, depth : number, state : CompileState) : Option<[CompileState, String]>/* {
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
    } */
	/* private static */ compileValues(state : CompileState, params : String, mapper : (CompileState, String) => [CompileState, String]) : [CompileState, String]/* {
        var parsed = parseValues(state, params, mapper);
        var generated = generateValues(parsed.right);
        return new Tuple<>(parsed.left, generated);
    } */
	/* private static */ generateValues(elements : List<String>) : String/* {
        return generateAll(Main::mergeValues, elements);
    } */
	/* private static */ parseValues(state : CompileState, input : String, mapper : (CompileState, String) => [CompileState, String]) : [CompileState, List<String>]/* {
        return parseAll(state, input, Main::foldValueChar, mapper);
    } */
	/* private static */ compileParameter(state : CompileState, input : String) : [CompileState, String]/* {
        if (input.isBlank()) {
            return new Tuple<>(state, "");
        }

        return parseDefinition(state, input)
                .map((Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
    } */
	/* private static */ mergeValues(cache : StringBuilder, element : String) : StringBuilder/* {
        if (cache.isEmpty()) {
            return cache.append(element);
        }
        return cache.append(", ").append(element);
    } */
	/* private static */ createIndent(depth : number) : String/* {
        return "\n" + "\t".repeat(depth);
    } */
	/* private static */ compileDefinitionStatement(input : String, depth : number, state : CompileState) : Option<[CompileState, String]>/* {
        return suffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(state, withoutEnd).map(result -> {
                var generated = createIndent(depth) + result.right.generate() + ";";
                return new Tuple<>(result.left, generated);
            });
        });
    } */
	/* private static */ parseDefinition(state : CompileState, input : String) : Option<[CompileState, Definition]>/* {
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
    } */
	/* private static */ getStringStringTuple(beforeName : String) : Option<[String, String]>/* {
        var divisions = divideAll(beforeName, Main::foldTypeSeparator);
        return divisions.removeLast().map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        });
    } */
	/* private static */ foldTypeSeparator(state : DivideState, c : Character) : DivideState/* {
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
    } */
	/* private static */ assembleDefinition(state : CompileState, beforeTypeParams : Option<String>, name : String, typeParams : List<String>, type : String) : Option<[CompileState, Definition]>/* {
        var type1 = type(state, type);
        var node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
        return new Some<>(new Tuple<>(type1.left, node));
    } */
	/* private static */ foldValueChar(state : DivideState, c : char) : DivideState/* {
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
    } */
	/* private static */ type(state : CompileState, input : String) : [CompileState, String]/* {
        var stripped = input.strip();
        if (stripped.equals("int")) {
            return new Tuple<>(state, "number");
        }

        if (isSymbol(stripped)) {
            return new Tuple<>(state, stripped);
        }

        return template(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
    } */
	/* private static */ template(state : CompileState, input : String) : Option<[CompileState, String]>/* {
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
    } */
	/* private static */ generate(arguments : List<String>, returns : String) : String/* {
        var joined = arguments.iterate()
                .collect(new Joiner(", "))
                .orElse("");

        return "(" + joined + ") => " + returns;
    } */
	/* private static  */ last<T>(input : String, infix : String, mapper : (String, String) => Option<T>) : Option<T>/* {
        return infix(input, infix, Main::findLast, mapper);
    } */
	/* private static */ findLast(input : String, infix : String) : Option<Integer>/* {
        var index = input.lastIndexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    } */
	/* private static  */ first<T>(input : String, infix : String, mapper : (String, String) => Option<T>) : Option<T>/* {
        return infix(input, infix, Main::findFirst, mapper);
    } */
	/* private static  */ infix<T>(input : String, infix : String, locator : (String, String) => Option<Integer>, mapper : (String, String) => Option<T>) : Option<T>/* {
        return split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return new Tuple<>(left, right);
        }), mapper);
    } */
	/* private static  */ split<T>(splitter : () => Option<[String, String]>, mapper : (String, String) => Option<T>) : Option<T>/* {
        return splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
    } */
	/* private static */ findFirst(input : String, infix : String) : Option<Integer>/* {
        var index = input.indexOf(infix);
        return index == -1 ? new None<Integer>() : new Some<>(index);
    } */
	/* private static */ generatePlaceholder(input : String) : String/* {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start " + replaced + " content-end";
    } */
}
/*  */
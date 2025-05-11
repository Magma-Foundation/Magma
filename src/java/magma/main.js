"use strict";
/* private static */ class Lists {
    /* private static final class JVMList<T> implements List<T> {
            private final java.util.List<T> elements;

            */ JVMList(elements) {
    }
    /* public static  */ empty() {
    }
    /* public static  */ of(elements /* T... */) {
    }
}
/* private static */ class DivideState {
    DivideState(input, index, segments, buffer, depth) {
    }
    DivideState(input) {
    }
    /* private */ advance() {
    }
    /* private */ append(c) {
    }
    /* public */ enter() {
    }
    /* public */ isLevel() {
    }
    /* public */ exit() {
    }
    /* public */ isShallow() {
    }
    /* public */ pop() {
    }
    /* public */ popAndAppendToTuple() {
    }
    /* public */ popAndAppendToOption() {
    }
}
/* public */ class Main {
    /* private interface Option */ map() {
        (mapper) => {
            /*  */ ;
            /* boolean isPresent() */ ;
            /* T orElse(T other) */ ;
            /* Option<T> filter(Predicate<T> predicate) */ ;
            /* T orElseGet(Supplier<T> supplier) */ ;
            /* Option<T> or(Supplier<Option<T>> other) */ ;
            /* <R> Option<R> flatMap(Function<T, Option<R>> mapper) */ ;
            /* boolean isEmpty() */ ; /* } */
        };
        /* private interface Collector<T, C> {
            */ createInitial();
        C;
        {
            /*  */ ;
            /* C fold(C current, T element) */ ; /* } */
        }
        /* private interface Iterator */ fold < T > {}(initial, R, folder, (R, T) => R);
        R;
        {
            /*  */ ;
            /* <R> Iterator<R> map(Function<T, R> mapper) */ ;
            /* <R> R collect(Collector<T, R> collector) */ ; /* } */
        }
        /* private interface List<T> {
            */ add(element, T);
        List < T > {};
        /* private interface Head<T> {
            */ next();
        Option < T > {};
        /* private */ Some(value, T);
        record;
        { /* implements Option<T> {
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
    
            @Override
            public boolean isEmpty() {
                return false;
            }
        } */
        }
        /* private static class None */ map < T > implements;
        Option < T > {};
        public(mapper, (T) => R);
        Option < R > { /* {
            return new None<>();
        } */ /* @Override
        public boolean isPresent() {
            return false;
        } */ /* @Override
        public T orElse(T other) {
            return other;
        } */ /* @Override
        public Option<T> filter(Predicate<T> predicate) {
            return new None<>();
        } */ /* @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        } */ /* @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        } */ /* @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        } */ /* @Override
        public boolean isEmpty() {
            return true;
        } */ /* } */};
        /* private */ HeadedIterator(head, (Head));
        record;
        { /* implements Iterator<T> {
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
        }
        /* private static class RangeHead implements Head<Integer> {
            private final int length;
            private int counter;
    
            */ RangeHead(length, number);
        public;
        { /* {
            this.length = length;
        } */ /* @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            }

            return new None<>();
        } */ /* } */
        }
        /* private */ CompileState(structures, (List));
        record;
        { /* {
            public CompileState() {
                this(Lists.empty());
            }
    
            public CompileState addStructure(String structure) {
                return new CompileState(this.structures.add(structure));
            }
        } */
        }
        /* private */ Joiner(delimiter, string);
        record;
        { /* implements Collector<String, Option<String>> {
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
        }
        /* private */ Definition(maybeBefore, (Option), type, string, name, string, typeParams, (List));
        record;
        { /* {
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
        }
        /* private static class ListCollector<T> implements Collector<T, List<T>> {
            @Override
            public */ createInitial();
        List < T > { /* {
            return Lists.empty();
        } */ /* @Override
        public List<T> fold(List<T> current, T element) {
            return current.add(element);
        } */ /* } */};
        /* private record */ B > (left);
        A, right;
        B;
        { /* {
        } */
        }
        /* public static */ main();
        void { /* {
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
        } */};
        /* private static */ compile(input, string);
        string;
        { /* {
            var tuple = compileStatements(new CompileState(), input, Main::compileRootSegment);
            var joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("");
            return joined + tuple.right;
        } */
        }
        /* private static */ compileStatements(state, CompileState, input, string, mapper, (CompileState, string) => [CompileState, string]);
        [CompileState, string];
        { /* {
            return compileAll(state, input, Main::foldStatementChar, mapper, Main::mergeStatements);
        } */
        }
        /* private static */ compileAll(state, CompileState, input, string, folder, (DivideState, Character) => DivideState, mapper, (CompileState, string) => [CompileState, string], merger, (StringBuilder, string) => StringBuilder);
        [CompileState, string];
        { /* {
            var parsed = parseAll(state, input, folder, mapper);
            var generated = generateAll(merger, parsed.right);
            return new Tuple<>(parsed.left, generated);
        } */
        }
        /* private static */ generateAll(merger, (StringBuilder, string) => StringBuilder, elements, (List));
        string;
        { /* {
            return elements
                    .iterate()
                    .fold(new StringBuilder(), merger)
                    .toString();
        } */
        }
        /* private static */ parseAll(state, CompileState, input, string, folder, (DivideState, Character) => DivideState, mapper, (CompileState, string) => [CompileState, string]);
        [CompileState, (List)];
        { /* {
            return divideAll(input, folder).iterate().fold(new Tuple<>(state, Lists.empty()), (tuple, element) -> {
                var state1 = tuple.left;
                var right = tuple.right;
    
                var applied = mapper.apply(state1, element);
                return new Tuple<>(applied.left, right.add(applied.right));
            });
        } */
        }
        /* private static */ mergeStatements(stringBuilder, StringBuilder, str, string);
        StringBuilder;
        { /* {
            return stringBuilder.append(str);
        } */
        }
        /* private static */ divideAll(input, string, folder, (DivideState, Character) => DivideState);
        List < string > { /* {
            var current = new DivideState(input);
            while (true) {
                var maybePopped = current.pop().map(tuple -> {
                    return foldSingleQuotes(tuple)
                            .or(() -> foldDoubleQuotes(tuple))
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
        } */};
        /* private static */ foldDoubleQuotes(tuple, [Character, DivideState]);
        Option < DivideState > { /* {
            if (tuple.left == '\"') {
                var current = tuple.right.append(tuple.left);
                while (true) {
                    var maybePopped = current.popAndAppendToTuple();
                    if (maybePopped.isEmpty()) {
                        break;
                    }
    
                    var popped = maybePopped.orElse(null);
                    current = popped.right;
    
                    if (popped.left == '\\') {
                        current = current.popAndAppendToOption().orElse(current);
                    }
                    if (popped.left == '\"') {
                        break;
                    }
                }
    
                return new Some<>(current);
            }
    
            return new None<>();
        } */};
        /* private static */ foldSingleQuotes(tuple, [Character, DivideState]);
        Option < DivideState > { /* {
            if (tuple.left == '\'') {
                var appended = tuple.right.append(tuple.left);
                return appended.popAndAppendToTuple()
                        .map(escaped -> escaped.left == '\\' ? escaped.right.popAndAppendToOption().orElse(escaped.right) : escaped.right)
                        .flatMap(DivideState::popAndAppendToOption);
            }
    
            return new None<>();
    
        } */};
        /* private static */ foldStatementChar(state, DivideState, c, char);
        DivideState;
        { /* {
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
        }
        /* private static */ compileRootSegment(state, CompileState, input, string);
        [CompileState, string];
        { /* {
            var stripped = input.strip();
            if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
                return new Tuple<>(state, "");
            }
    
            return compileClass(stripped, 0, state)
                    .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
        } */
        }
        /* private static */ compileClass(stripped, string, depth, number, state, CompileState);
        Option < [CompileState, string] > { /* {
            return compileStructure(stripped, "class ", "class ", state);
        } */};
        /* private static */ compileStructure(stripped, string, sourceInfix, string, targetInfix, string, state, CompileState);
        Option < [CompileState, string] > { /* {
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
        } */};
        /* private static */ isSymbol(input, string);
        boolean;
        { /* {
            for (var i = 0; i < input.length(); i++) {
                var c = input.charAt(i);
                if (Character.isLetter(c)) {
                    continue;
                }
                return false;
            }
            return true;
        } */
        }
        /* private static  */ suffix(input, string, suffix, string, mapper, (string) => Option);
        Option < T > { /* {
            if (!input.endsWith(suffix)) {
                return new None<>();
            }
    
            var slice = input.substring(0, input.length() - suffix.length());
            return mapper.apply(slice);
        } */};
        /* private static */ compileClassSegment(state, CompileState, input, string, depth, number);
        [CompileState, string];
        { /* {
            return compileWhitespace(input, state)
                    .or(() -> compileClass(input, depth, state))
                    .or(() -> compileStructure(input, "interface ", "interface ", state))
                    .or(() -> compileStructure(input, "record ", "class ", state))
                    .or(() -> method(state, input, depth))
                    .or(() -> compileDefinitionStatement(input, depth, state))
                    .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
        } */
        }
        /* private static */ compileWhitespace(input, string, state, CompileState);
        Option < [CompileState, string] > { /* {
            if (input.isBlank()) {
                return new Some<>(new Tuple<>(state, ""));
            }
            return new None<>();
        } */};
        /* private static */ method(state, CompileState, input, string, depth, number);
        Option < [CompileState, string] > { /* {
            return first(input, "(", (definition, withParams) -> {
                return first(withParams, ")", (params, rawContent) -> {
                    var definitionTuple = parseDefinition(state, definition)
                            .map(definition1 -> {
                                var paramsTuple = compileValues(state, params, Main::compileParameter);
                                var generated = definition1.right.generateWithParams("(" + paramsTuple.right + ")");
                                return new Tuple<>(paramsTuple.left, generated);
                            })
                            .orElseGet(() -> new Tuple<>(state, generatePlaceholder(definition)));
    
                    var content = rawContent.strip();
                    if (content.equals(";")) {
                        var s = createIndent(depth) + definitionTuple.right + ";";
                        return new Some<>(new Tuple<>(definitionTuple.left, s));
                    }
    
                    var statementsTuple = compileStatements(definitionTuple.left, content, Main::compileFunctionalSegment);
                    var generated = createIndent(depth) + definitionTuple.right + "{" + statementsTuple.right + "}";
                    return new Some<>(new Tuple<>(statementsTuple.left, generated));
                });
            });
        } */};
        /* private static */ compileFunctionalSegment(state, CompileState, input, string);
        [CompileState, string];
        { /* {
            var stripped = input.strip();
            if (stripped.isEmpty()) {
                return new Tuple<>(state, "");
            }
    
            return suffix(stripped, ";", s -> {
                return new Some<>(new Tuple<>(state, "\n\t" + generatePlaceholder(s) + ";"));
            }).orElseGet(() -> {
                return new Tuple<>(state, generatePlaceholder(stripped));
            });
        } */
        }
        /* private static */ compileValues(state, CompileState, params, string, mapper, (CompileState, string) => [CompileState, string]);
        [CompileState, string];
        { /* {
            var parsed = parseValues(state, params, mapper);
            var generated = generateValues(parsed.right);
            return new Tuple<>(parsed.left, generated);
        } */
        }
        /* private static */ generateValues(elements, (List));
        string;
        { /* {
            return generateAll(Main::mergeValues, elements);
        } */
        }
        /* private static */ parseValues(state, CompileState, input, string, mapper, (CompileState, string) => [CompileState, string]);
        [CompileState, (List)];
        { /* {
            return parseAll(state, input, Main::foldValueChar, mapper);
        } */
        }
        /* private static */ compileParameter(state, CompileState, input, string);
        [CompileState, string];
        { /* {
            if (input.isBlank()) {
                return new Tuple<>(state, "");
            }
    
            return parseDefinition(state, input)
                    .map((Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
                    .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input)));
        } */
        }
        /* private static */ mergeValues(cache, StringBuilder, element, string);
        StringBuilder;
        { /* {
            if (cache.isEmpty()) {
                return cache.append(element);
            }
            return cache.append(", ").append(element);
        } */
        }
        /* private static */ createIndent(depth, number);
        string;
        { /* {
            return "\n" + "\t".repeat(depth);
        } */
        }
        /* private static */ compileDefinitionStatement(input, string, depth, number, state, CompileState);
        Option < [CompileState, string] > { /* {
            return suffix(input.strip(), ";", withoutEnd -> {
                return parseDefinition(state, withoutEnd).map(result -> {
                    var generated = createIndent(depth) + result.right.generate() + ";";
                    return new Tuple<>(result.left, generated);
                });
            });
        } */};
        /* private static */ parseDefinition(state, CompileState, input, string);
        Option < [CompileState, Definition] > { /* {
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
        } */};
        /* private static */ getStringStringTuple(beforeName, string);
        Option < [string, string] > { /* {
            var divisions = divideAll(beforeName, Main::foldTypeSeparator);
            return divisions.removeLast().map(removed -> {
                var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
                var right = removed.right;
    
                return new Tuple<>(left, right);
            });
        } */};
        /* private static */ foldTypeSeparator(state, DivideState, c, Character);
        DivideState;
        { /* {
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
        }
        /* private static */ assembleDefinition(state, CompileState, beforeTypeParams, (Option), name, string, typeParams, (List), type, string);
        Option < [CompileState, Definition] > { /* {
            var type1 = type(state, type);
            var node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
            return new Some<>(new Tuple<>(type1.left, node));
        } */};
        /* private static */ foldValueChar(state, DivideState, c, char);
        DivideState;
        { /* {
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
        }
        /* private static */ type(state, CompileState, input, string);
        [CompileState, string];
        { /* {
            var stripped = input.strip();
            if (stripped.equals("int")) {
                return new Tuple<>(state, "number");
            }
    
            if (stripped.equals("String")) {
                return new Tuple<>(state, "string");
            }
    
            if (isSymbol(stripped)) {
                return new Tuple<>(state, stripped);
            }
    
            return template(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped)));
        } */
        }
        /* private static */ template(state, CompileState, input, string);
        Option < [CompileState, string] > { /* {
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
        } */};
        /* private static */ generate(arguments, (List), returns, string);
        string;
        { /* {
            var joined = arguments.iterate()
                    .collect(new Joiner(", "))
                    .orElse("");
    
            return "(" + joined + ") => " + returns;
        } */
        }
        /* private static  */ last(input, string, infix, string, mapper, (string, string) => Option);
        Option < T > { /* {
            return infix(input, infix, Main::findLast, mapper);
        } */};
        /* private static */ findLast(input, string, infix, string);
        Option < Integer > { /* {
            var index = input.lastIndexOf(infix);
            return index == -1 ? new None<Integer>() : new Some<>(index);
        } */};
        /* private static  */ first(input, string, infix, string, mapper, (string, string) => Option);
        Option < T > { /* {
            return infix(input, infix, Main::findFirst, mapper);
        } */};
        /* private static  */ infix(input, string, infix, string, locator, (string, string) => Option, mapper, (string, string) => Option);
        Option < T > { /* {
            return split(() -> locator.apply(input, infix).map(index -> {
                var left = input.substring(0, index);
                var right = input.substring(index + infix.length());
                return new Tuple<>(left, right);
            }), mapper);
        } */};
        /* private static  */ split(splitter, () => Option, mapper, (string, string) => Option);
        Option < T > { /* {
            return splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right));
        } */};
        /* private static */ findFirst(input, string, infix, string);
        Option < Integer > { /* {
            var index = input.indexOf(infix);
            return index == -1 ? new None<Integer>() : new Some<>(index);
        } */};
        /* private static */ generatePlaceholder(input, string);
        string;
        { /* {
            var replaced = input
                    .replace("content-start", "content-start")
                    .replace("content-end", "content-end");
    
            return "content-start " + replaced + " content-end";
        } */
        }
    }
}
/*  */ 

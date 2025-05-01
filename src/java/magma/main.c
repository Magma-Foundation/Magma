/* private */struct Option<T> {/* 

        <R> Option<R> map(Function<T, R> mapper); *//* 

        boolean isEmpty(); *//* 

        T orElse(T other); *//* 

        Option<T> or(Supplier<Option<T>> other); *//* 

        T orElseGet(Supplier<T> other); *//* 

        <R> Option<R> flatMap(Function<T, Option<R>> mapper); */
};
/* private */struct Head<T> {/* Option<T> next(); */
};
/* private */struct List<T> {/* List<T> addLast(T element); *//* 

        Iterator<T> iterate(); */
};
/* private */struct Collector<T, C> {/* C createInitial(); *//* 

        C fold(C current, T element); */
};
/* private @ */struct External {
};
/* private */struct Some<T>(T value) implements Option<T> {
};
/* private */struct None<T>() implements Option<T> {
};
/* private */struct Iterator<T> {
	/* Head<T> */ head;
};
/* private */struct CompileState {
	/* List<String> */ structs;
	/* List<String> */ functions;
};
/* private */struct DivideState {
	/* String */ input;
	/* List<String> */ segments;
	/* StringBuilder */ buffer;
	/* int */ index;
	/* int */ depth;
};
/* private */struct Tuple<A, B> {
	/* A */ left;
	/* B */ right;
};
/* public */struct Main {/* 

    @External
    private record JavaList<T>(java.util.List<T> list) implements List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public List<T> addLast(T element) {
            var copy = new ArrayList<>(this.list);
            copy.add(element);
            return new JavaList<>(copy);
        }

        @Override
        public Iterator<T> iterate() {
            return new Iterator<>(new RangeHead(this.list.size())).map(this.list::get);
        }
    } */
};
/* static <T> */ /* Option<T> */ of(/* T value */){/* return new Some<>(value); *//*  */
}
/* @Override
        public <R> */ /* Option<R> */ map(/* Function<T, R> mapper */){/* return new Some<>(mapper.apply(this.value)); *//*  */
}
/* @Override
        public */ /* boolean */ isEmpty(/*  */){/* return false; *//*  */
}
/* @Override
        public */ /* T */ orElse(/* T other */){/* return this.value; *//*  */
}
/* @Override
        public */ /* Option<T> */ or(/* Supplier<Option<T>> other */){/* return this; *//*  */
}
/* @Override
        public */ /* T */ orElseGet(/* Supplier<T> other */){/* return this.value; *//*  */
}
/* @Override
        public <R> */ /* Option<R> */ flatMap(/* Function<T, Option<R>> mapper */){/* return mapper.apply(this.value); *//*  */
}
/* public */ /* T */ get(/*  */){/* return this.value; *//*  */
}
/* @Override
        public <R> */ /* Option<R> */ map(/* Function<T, R> mapper */){/* return new None<>(); *//*  */
}
/* @Override
        public */ /* boolean */ isEmpty(/*  */){/* return true; *//*  */
}
/* @Override
        public */ /* T */ orElse(/* T other */){/* return other; *//*  */
}
/* @Override
        public */ /* Option<T> */ or(/* Supplier<Option<T>> other */){/* return other.get(); *//*  */
}
/* @Override
        public */ /* T */ orElseGet(/* Supplier<T> other */){/* return other.get(); *//*  */
}
/* @Override
        public <R> */ /* Option<R> */ flatMap(/* Function<T, Option<R>> mapper */){/* return new None<>(); *//*  */
}
/* public <C> */ /* C */ collect(/* Collector<T, C> collector */){/* return this.fold(collector.createInitial(), collector::fold); *//*  */
}
/* private <C> */ /* C */ fold(/* C initial, BiFunction<C, T, C> folder */){/* var current = initial; *//* while (true) {
                C finalCurrent = current;
                var maybeNext = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (maybeNext.isEmpty()) {
                    return current;
                }
                else {
                    current = maybeNext.orElse(null);
                }
            } *//*  */
}
/* public <R> */ /* Iterator<R> */ flatMap(/* Function<T, Iterator<R>> mapper */){/* return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat); *//*  */
}
/* public <R> */ /* Iterator<R> */ map(/* Function<T, R> mapper */){/* return new Iterator<>(() -> this.head.next().map(mapper)); *//*  */
}
/* private */ /* Iterator<T> */ concat(/* Iterator<T> other */){/* return new Iterator<>(() -> this.head.next().or(other::next)); *//*  */
}
/* public */ /* Option<T> */ next(/*  */){/* return this.head.next(); *//*  */
}
/* private static final class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0;

        */ /* private */ RangeHead(/* int length */){/* this.length = length; *//* }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<>();
            } *//* var value = this.counter; *//* this.counter++; *//* return Option.of(value); *//* } */
}
/* private static class Lists {
        public static <T> */ /* List<T> */ of(/* T... elements */){/* return new JavaList<>(Arrays.asList(elements)); *//* }

        public static <T> List<T> empty() {
            return new JavaList<>(new ArrayList<>()); *//* } */
}
/* private static class EmptyHead<T> implements Head<T> {
        @Override
        public */ /* Option<T> */ next(/*  */){/* return new None<>(); *//* } */
}
/* private static class Joiner implements Collector<String, Option<String>> {
        @Override
        public */ /* Option<String> */ createInitial(/*  */){/* return new None<>(); *//* }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return Option.of(current.map(inner -> inner + element).orElse(element)); *//* } */
}
/* public */ CompileState(/*  */){/* this(Lists.empty(), Lists.empty()); *//*  */
}
/* private */ /* String */ generate(/*  */){/* return this.getJoin(this.structs) + this.getJoin(this.functions); *//*  */
}
/* private */ /* String */ getJoin(/* List<String> lists */){/* return lists.iterate().collect(new Joiner()).orElse(""); *//*  */
}
/* public */ /* CompileState */ addStruct(/* String struct */){/* return new CompileState(this.structs.addLast(struct), this.functions); *//*  */
}
/* public */ /* CompileState */ addFunction(/* String function */){/* return new CompileState(this.structs, this.functions.addLast(function)); *//*  */
}
/* public */ DivideState(/* String input */){/* this(input, new JavaList<>(), new StringBuilder(), 0, 0); *//*  */
}
/* private */ /* Option<DivideState> */ popAndAppend(/*  */){/* return this.popAndAppendToTuple().map(Tuple::right); *//*  */
}
/* private Option<Tuple<Character, */ /* DivideState>> */ popAndAppendToTuple(/*  */){/* return this.pop().map(tuple -> {
                var c = tuple.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            } *//* ); *//*  */
}
/* private */ /* DivideState */ append(/* char c */){/* return new DivideState(this.input, this.segments, this.buffer.append(c), this.index, this.depth); *//*  */
}
/* public Option<Tuple<Character, */ /* DivideState>> */ pop(/*  */){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return Option.of(new Tuple<>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } *//*  */
}
/* private */ /* DivideState */ advance(/*  */){/* return new DivideState(this.input, this.segments.addLast(this.buffer.toString()), new StringBuilder(), this.index, this.depth); *//*  */
}
/* public */ /* DivideState */ exit(/*  */){/* return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth - 1); *//*  */
}
/* public */ /* boolean */ isLevel(/*  */){/* return this.depth == 0; *//*  */
}
/* public */ /* DivideState */ enter(/*  */){/* return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth + 1); *//*  */
}
/* public */ /* boolean */ isShallow(/*  */){/* return this.depth == 1; *//*  */
}
/* private static class Iterators {
        public static <T> */ /* Iterator<T> */ fromOptions(/* Option<T> option */){/* return new Iterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new)); *//* } */
}
/* private static class SingleHead<T> implements Head<T> {
        private final T value;
        private boolean retrieved = false;

        */ /* public */ SingleHead(/* T value */){/* this.value = value; *//* }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            } *//* this.retrieved = true; *//* return Option.of(this.value); *//* } */
}
/* public static */ /* void */ main(/*  */){/* try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } *//* catch (IOException e) {
            e.printStackTrace();
        } *//*  */
}
/* private static */ /* String */ compileRoot(/* String input */){/* var state = new CompileState(); *//* var tuple = compileAll(state, input, Main::compileRootSegment)
                .orElse(new Tuple<>(state, "")); *//* return tuple.right + tuple.left.generate(); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileAll(/* 
            CompileState initial,
            String input,
            BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper
     */){/* return compileAll(initial, input, Main::foldStatementChar, mapper, Main::mergeStatements); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileAll(/* 
            CompileState initial,
            String input,
            BiFunction<DivideState, Character, DivideState> folder, BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper,
            BiFunction<StringBuilder, String, StringBuilder> merger
     */){/* var segments = divide(input, folder); *//* return segments.iterate()
                .fold(Option.of(new Tuple<>(initial, new StringBuilder())), (maybeCurrent, segment) -> maybeCurrent.flatMap(state -> foldElement(state, segment, mapper, merger)))
                .map(result -> new Tuple<>(result.left, result.right.toString())); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* StringBuilder>> */ foldElement(/* 
            Tuple<CompileState, StringBuilder> state,
            String segment,
            BiFunction<CompileState, String, Option<Tuple<CompileState, String>>> mapper,
            BiFunction<StringBuilder, String, StringBuilder> merger
     */){/* var oldState = state.left; *//* var oldCache = state.right; *//* return mapper.apply(oldState, segment).map(result -> {
            var newState = result.left;
            var newElement = result.right;
            return new Tuple<>(newState, merger.apply(oldCache, newElement));
        } *//* ); *//*  */
}
/* private static */ /* StringBuilder */ mergeStatements(/* StringBuilder output, String right */){/* return output.append(right); *//*  */
}
/* private static */ /* List<String> */ divide(/* String input, BiFunction<DivideState, Character, DivideState> folder */){/* DivideState current = new DivideState(input); *//* while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var popped = maybePopped.orElse(null);
            var c = popped.left;
            var state = popped.right;
            current = foldSingleQuotes(state, c)
                    .or(() -> foldDoubleQuotes(state, c))
                    .orElseGet(() -> folder.apply(state, c));
        } *//* return current.advance().segments; *//*  */
}
/* private static */ /* Option<DivideState> */ foldDoubleQuotes(/* DivideState state, char c */){/* if (c != '\"') {
            return new None<>();
        } *//* var appended = state.append(c); *//* while (true) {
            var maybeTuple = appended.popAndAppendToTuple();
            if (maybeTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeTuple.orElse(null);
            var next = nextTuple.left;
            appended = nextTuple.right;

            if (next == '\\') {
                appended = appended.popAndAppend().orElse(appended);
            }
            if (next == '\"') {
                break;
            }
        } *//* return Option.of(appended); *//*  */
}
/* private static */ /* Option<DivideState> */ foldSingleQuotes(/* DivideState state, char c */){/* if (c != '\'') {
            return new None<>();
        } *//* return state.append(c).pop().map(maybeNextTuple -> {
            var nextChar = maybeNextTuple.left;
            var nextState = maybeNextTuple.right.append(nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState.popAndAppend().orElse(nextState)
                    : nextState;

            return withEscaped.popAndAppend().orElse(withEscaped);
        } *//* ); *//*  */
}
/* private static */ /* DivideState */ foldStatementChar(/* DivideState state, char c */){/* var appended = state.append(c); *//* if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } *//* if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        } *//* if (c == '}') {
            return appended.exit();
        } *//* return appended; *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileRootSegment(/* CompileState state, String input */){/* return or(state, input, Lists.of(
                Main::compileWhitespace,
                Main::compileNamespaced,
                structure("class "),
                Main::compileContent
        )); *//*  */
}
/* private static BiFunction<CompileState, String, Option<Tuple<CompileState, */ /* String>>> */ structure(/* String infix */){/* return (state, input) -> first(input, infix, (beforeKeyword, afterKeyword) -> {
            var slices = Arrays.stream(beforeKeyword.split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            if (slices.contains("@External")) {
                return new None<>();
            }

            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return or(state, beforeContent, Lists.of(
                        (instance, before) -> structureWithParams(beforeKeyword, withEnd, instance, before),
                        (instance, before) -> structureWithName(beforeKeyword, withEnd, before.strip(), instance, "")
                ));
            });
        } *//* ); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ structureWithParams(/* String beforeKeyword, String withEnd, CompileState instance, String before */){/* return suffix(before.strip(), ")", withoutEnd -> first(withoutEnd, "(", (name, paramString) -> {
            return compileAll(instance, paramString, Main::foldValueChar, Main::compileParameter, Main::mergeStatements).flatMap(params -> {
                return structureWithName(beforeKeyword, withEnd, name, params.left, params.right);
            });
        } *//* )); *//*  */
}
/* private static */ /* StringBuilder */ mergeValues(/* StringBuilder buffer, String element */){/* if (buffer.isEmpty()) {
            return buffer.append(element);
        } *//* return buffer.append(", ").append(element); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileParameter(/* CompileState instance, String paramString */){/* return or(instance, paramString, Lists.of(
                Main::compileDefinition,
                Main::compileContent
        )).map(value -> new Tuple<>(value.left, "\n\t" + value.right + ";")); *//*  */
}
/* private static */ /* DivideState */ foldValueChar(/* DivideState state, char c */){/* if (c == ',') {
            return state.advance();
        } *//* return state.append(c); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ structureWithName(/* String beforeKeyword, String withEnd, String name, CompileState state, String params */){/* return suffix(withEnd.strip(), "}", content -> {
            return compileAll(state, content, Main::compileStructSegment).flatMap(tuple -> {
                var generated = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + " {" + params + tuple.right + "\n};\n";
                return Option.of(new Tuple<>(tuple.left.addStruct(generated), ""));
            });
        } *//* ); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ or(/* 
            CompileState state,
            String input,
            List<BiFunction<CompileState, String, Option<Tuple<CompileState, String>>>> actions
     */){/* return actions.iterate()
                .map(action -> action.apply(state, input))
                .flatMap(Iterators::fromOptions)
                .next(); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileNamespaced(/* CompileState state, String input */){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return Option.of(new Tuple<>(state, ""));
        } *//* return new None<>(); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileStructSegment(/* CompileState state, String input */){/* return or(state, input, Lists.of(
                Main::compileWhitespace,
                structure("record "),
                structure("interface "),
                Main::compileMethod,
                Main::compileContent
        )); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileContent(/* CompileState state, String input */){/* return Option.of(new Tuple<>(state, generatePlaceholder(input))); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileWhitespace(/* CompileState state, String input */){/* if (input.isBlank()) {
            return Option.of(new Tuple<>(state, ""));
        } *//* return new None<>(); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileMethod(/* CompileState state, String input */){/* return first(input, "(", (inputDefinition, withParams) -> {
            return first(withParams, ")", (params, withBraces) -> {
                return prefix(withBraces.strip(), withoutStart1 -> {
                    return suffix(withoutStart1, "}", content -> {
                        return compileAll(state, content, Main::compileFunctionSegment).flatMap(tuple -> {
                            return compileMethodHeader(state, inputDefinition).flatMap(outputDefinition -> {
                                var generated = outputDefinition.right + "(" + generatePlaceholder(params) + "){" + tuple.right + "\n}\n";
                                return Option.of(new Tuple<>(outputDefinition.left.addFunction(generated), ""));
                            });
                        });
                    });
                });
            });
        } *//* ); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileMethodHeader(/* CompileState state, String definition */){/* return or(state, definition, Lists.of(
                Main::compileDefinition,
                Main::compileContent
        )); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileFunctionSegment(/* CompileState state, String input */){/* return or(state, input.strip(), Lists.of(
                Main::compileContent
        )); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileDefinition(/* CompileState state, String input */){/* return infix(input.strip(), " ", Main::lastIndexOfSlice, (beforeName, name) -> {
            return or(state, beforeName.strip(), Lists.of(
                    (instance, beforeName0) -> compileDefinitionWithTypeSeparator(instance, beforeName0, name),
                    (instance, beforeName0) -> compileDefinitionWithoutTypeSeparator(instance, beforeName0, name)
            ));
        } *//* ); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileDefinitionWithoutTypeSeparator(/* CompileState instance, String type, String name */){/* var generated = generatePlaceholder(type) + " " + name.strip(); *//* return Option.of(new Tuple<>(instance, generated)); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ compileDefinitionWithTypeSeparator(/* CompileState instance, String beforeName, String name */){/* return infix(beforeName, " ", Main::lastIndexOfSlice, (beforeType, typeString) -> {
            var generated = generatePlaceholder(beforeType) + " " + generatePlaceholder(typeString) + " " + name.strip();
            return Option.of(new Tuple<>(instance, generated));
        } *//* ); *//*  */
}
/* private static */ /* Option<Integer> */ lastIndexOfSlice(/* String input, String infix */){/* var index = input.lastIndexOf(infix); *//* return index == -1 ? new None<Integer>() : Option.of(index); *//*  */
}
/* private static Option<Tuple<CompileState, */ /* String>> */ prefix(/* String input, Function<String, Option<Tuple<CompileState, String>>> mapper */){/* if (!input.startsWith("{")) {
            return new None<>();
        } *//* var slice = input.substring("{".length()); *//* return mapper.apply(slice); *//*  */
}
/* private static <T> */ /* Option<T> */ suffix(/* String input, String suffix, Function<String, Option<T>> mapper */){/* if (!input.endsWith(suffix)) {
            return new None<>();
        } *//* var content = input.substring(0, input.length() - suffix.length()); *//* return mapper.apply(content); *//*  */
}
/* private static */ /* String */ generatePlaceholder(/* String input */){/* return "/* " + input + " */"; *//*  */
}
/* private static <T> */ /* Option<T> */ first(/* String input, String infix, BiFunction<String, String, Option<T>> mapper */){/* return infix(input, infix, Main::firstIndexOfSlice, mapper); *//*  */
}
/* private static <T> */ /* Option<T> */ infix(/* String input, String infix, BiFunction<String, String, Option<Integer>> locator, BiFunction<String, String, Option<T>> mapper */){/* return locator.apply(input, infix).flatMap(classIndex -> {
            var beforeKeyword = input.substring(0, classIndex);
            var afterKeyword = input.substring(classIndex + infix.length());
            return mapper.apply(beforeKeyword, afterKeyword);
        } *//* ); *//*  */
}
/* private static */ /* Option<Integer> */ firstIndexOfSlice(/* String input, String infix */){/* var index = input.indexOf(infix); *//* return index == -1 ? new None<Integer>() : Option.of(index); *//*  */
}

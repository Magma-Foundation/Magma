enum OptionVariant {
	Some,
	None
};
/* private sealed */struct Option<T> {
	OptionVariant _variant;
	/* <R> */ template Option</* R */> map(/*  R */ (*)(/* T */) mapper);
	int isEmpty();
	/* T */ orElse(/* T */ other);
	template Option</* T */> or(template Option</* T */> (*)() other);
	/* T */ orElseGet(/* T */ (*)() other);
	/* <R> */ template Option</* R */> flatMap(template Option</* R */> (*)(/* T */) mapper);
};
/* private */struct Head<T> {
	template Option</* T */> next();
};
/* private */struct List<T> {
	template List</* T */> addLast(/* T */ element);
	template Iterator</* T */> iterate();
	template Option<template Tuple<template List</* T */>, /*  T */>> removeLast();
	int isEmpty();
	/* T */ get(/* int */ index);
};
/* private */struct Collector<T, C> {
	/* C */ createInitial();
	/* C */ fold(/* C */ current, /* T */ element);
};
/* private @ */struct External {
};
/* private */struct Splitter {
	template Option<template Tuple</* String */, /*  String */>> split(/* String */ input);
};
/* private */struct Some<T>(T value) implements Option<T> {
};
/* private */struct None<T>() implements Option<T> {
};
/* private */struct Iterator<T> {template Head</* T */> head
};
/* private */struct Joiner(String delimiter) implements Collector<String, Option<String>> {
};
/* private */struct CompileState {template List</* String */> structstemplate List</* String */> functions
};
/* private */struct DivideState {/* String */ inputtemplate List</* String */> segments/* StringBuilder */ buffer/* int */ index/* int */ depth
};
/* private */struct Tuple<A, B> {/* A */ left/* B */ right
};
/* private */struct InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter {
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

        @Override
        public Option<Tuple<List<T>, T>> removeLast() {
            if (this.list.isEmpty()) {
                return new None<>();
            }

            var slice = this.list.subList(0, this.list.size() - 1);
            var last = this.list.getLast();
            return new Some<>(new Tuple<>(new JavaList<>(new ArrayList<>(slice)), last));
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public T get(int index) {
            return this.list.get(index);
        }
    } */
};
/* @Override
 public <R> */ template Option</* R */> map(/*  R */ (*)(/* T */) mapper){/* return new Some<>(mapper.apply(this.value)); *//*  */
}
/* @Override
 public */ int isEmpty(){/* return false; *//*  */
}
/* @Override
 public */ /* T */ orElse(/* T */ other){/* return this.value; *//*  */
}
/* @Override
 public */ template Option</* T */> or(template Option</* T */> (*)() other){/* return this; *//*  */
}
/* @Override
 public */ /* T */ orElseGet(/* T */ (*)() other){/* return this.value; *//*  */
}
/* @Override
 public <R> */ template Option</* R */> flatMap(template Option</* R */> (*)(/* T */) mapper){/* return mapper.apply(this.value); *//*  */
}
/* public */ /* T */ get(){/* return this.value; *//*  */
}
/* @Override
 public <R> */ template Option</* R */> map(/*  R */ (*)(/* T */) mapper){/* return new None<>(); *//*  */
}
/* @Override
 public */ int isEmpty(){/* return true; *//*  */
}
/* @Override
 public */ /* T */ orElse(/* T */ other){/* return other; *//*  */
}
/* @Override
 public */ template Option</* T */> or(template Option</* T */> (*)() other){/* return other.get(); *//*  */
}
/* @Override
 public */ /* T */ orElseGet(/* T */ (*)() other){/* return other.get(); *//*  */
}
/* @Override
 public <R> */ template Option</* R */> flatMap(template Option</* R */> (*)(/* T */) mapper){/* return new None<>(); *//*  */
}
/* public <C> */ /* C */ collect(template Collector</* T */, /*  C */> collector){/* return this.fold(collector.createInitial(), collector::fold); *//*  */
}
/* private <C> */ /* C */ fold(/* C */ initial, template BiFunction</* C */, /*  T */, /*  C */> folder){/* var current = initial; *//* while (true) {
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
/* public <R> */ template Iterator</* R */> flatMap(template Iterator</* R */> (*)(/* T */) mapper){/* return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat); *//*  */
}
/* public <R> */ template Iterator</* R */> map(/*  R */ (*)(/* T */) mapper){/* return new Iterator<>(() -> this.head.next().map(mapper)); *//*  */
}
/* private */ template Iterator</* T */> concat(template Iterator</* T */> other){/* return new Iterator<>(() -> this.head.next().or(other::next)); *//*  */
}
/* public */ template Option</* T */> next(){/* return this.head.next(); *//*  */
}
/* private static final class RangeHead implements Head<Integer> {
 private final int length;
 private int counter = 0;

 */ /* private */ RangeHead(/* int */ length){/* this.length = length; *//* }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<>();
            } *//* var value = this.counter; *//* this.counter++; *//* return new Some<>(value); *//* } */
}
/* private static class Lists {
 public static <T> */ template List</* T */> of(/* T... */ elements){/* return new JavaList<>(Arrays.asList(elements)); *//* }

        public static <T> List<T> empty() {
            return new JavaList<>(new ArrayList<>()); *//* } */
}
/* private static class EmptyHead<T> implements Head<T> {
 @Override
 public */ template Option</* T */> next(){/* return new None<>(); *//* } */
}
/* public */ Joiner(){/* this(""); *//*  */
}
/* @Override
 public */ template Option</* String */> createInitial(){/* return new None<>(); *//*  */
}
/* @Override
 public */ template Option</* String */> fold(template Option</* String */> current, /* String */ element){/* return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element)); *//*  */
}
/* public */ CompileState(){/* this(Lists.empty(), Lists.empty()); *//*  */
}
/* private */ /* String */ generate(){/* return this.getJoin(this.structs) + this.getJoin(this.functions); *//*  */
}
/* private */ /* String */ getJoin(template List</* String */> lists){/* return lists.iterate().collect(new Joiner()).orElse(""); *//*  */
}
/* public */ /* CompileState */ addStruct(/* String */ struct){/* return new CompileState(this.structs.addLast(struct), this.functions); *//*  */
}
/* public */ /* CompileState */ addFunction(/* String */ function){/* return new CompileState(this.structs, this.functions.addLast(function)); *//*  */
}
/* public */ DivideState(/* String */ input){/* this(input, new JavaList<>(), new StringBuilder(), 0, 0); *//*  */
}
/* private */ template Option</* DivideState */> popAndAppend(){/* return this.popAndAppendToTuple().map(Tuple::right); *//*  */
}
/* private */ template Option<template Tuple</* Character */, /*  DivideState */>> popAndAppendToTuple(){/* return this.pop().map(tuple -> {
                var c = tuple.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            } *//* ); *//*  */
}
/* private */ /* DivideState */ append(/* char */ c){/* return new DivideState(this.input, this.segments, this.buffer.append(c), this.index, this.depth); *//*  */
}
/* public */ template Option<template Tuple</* Character */, /*  DivideState */>> pop(){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } *//*  */
}
/* private */ /* DivideState */ advance(){/* var withBuffer = this.buffer.isEmpty() ? this.segments : this.segments.addLast(this.buffer.toString()); *//* return new DivideState(this.input, withBuffer, new StringBuilder(), this.index, this.depth); *//*  */
}
/* public */ /* DivideState */ exit(){/* return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth - 1); *//*  */
}
/* public */ int isLevel(){/* return this.depth == 0; *//*  */
}
/* public */ /* DivideState */ enter(){/* return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth + 1); *//*  */
}
/* public */ int isShallow(){/* return this.depth == 1; *//*  */
}
/* private static class Iterators {
 public static <T> */ template Iterator</* T */> fromOptions(template Option</* T */> option){/* return new Iterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new)); *//* } */
}
/* private static class SingleHead<T> implements Head<T> {
 private final T value;
 private boolean retrieved = false;

 */ /* public */ SingleHead(/* T */ value){/* this.value = value; *//* }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            } *//* this.retrieved = true; *//* return new Some<>(this.value); *//* } */
}
/* @Override
 public */ template Option<template Tuple</* String */, /*  String */>> split(/* String */ input){/* return this.apply(input).map(classIndex -> {
                var beforeKeyword = input.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            } *//* ); *//*  */
}
/* private */ /* int */ length(){/* return this.infix.length(); *//*  */
}
/* private */ template Option</* Integer */> apply(/* String */ input){/* return this.locator().apply(input, this.infix); *//*  */
}
/* private static class TypeSeparatorSplitter implements Splitter {
 @Override
 public */ template Option<template Tuple</* String */, /*  String */>> split(/* String */ input){/* return divide(input, TypeSeparatorSplitter::fold).removeLast().flatMap(segments -> {
                var left = segments.left;
                if (left.isEmpty()) {
                    return new None<>();
                }

                var beforeType = left.iterate().collect(new Joiner(" ")).orElse("");
                var type = segments.right;
                return new Some<>(new Tuple<>(beforeType, type));
            } *//* ); *//* }

        private static DivideState fold(DivideState state, char c) {
            if (c == ' ' && state.isLevel()) {
                return state.advance();
            } *//* var appended = state.append(c); *//* if (c == '<') {
                return appended.enter();
            } *//* if (c == '>') {
                return appended.exit();
            } *//* return appended; *//* } */
}
/* public static */ /* void */ main(){/* try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } *//* catch (IOException e) {
            e.printStackTrace();
        } *//*  */
}
/* private static */ /* String */ compileRoot(/* String */ input){/* var state = new CompileState(); *//* var tuple = compileAll(state, input, Main::compileRootSegment)
                .orElse(new Tuple<>(state, "")); *//* return tuple.right + tuple.left.generate(); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> compileAll(/* CompileState */ initial, /* String */ input, template BiFunction</* CompileState */, /*  String */, template Option<template Tuple</* CompileState */, /*  String */>>> mapper){/* return all(initial, input, Main::foldStatementChar, mapper, Main::mergeStatements); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> all(/* CompileState */ initial, /* String */ input, template BiFunction</* DivideState */, /*  Character */, /*  DivideState */> folder, template BiFunction</* CompileState */, /*  String */, template Option<template Tuple</* CompileState */, /*  String */>>> mapper, template BiFunction</* StringBuilder */, /*  String */, /*  StringBuilder */> merger){/* return parseAll(initial, input, folder, mapper, merger).map(tuple -> new Tuple<>(tuple.left, generateAll(merger, tuple.right))); *//*  */
}
/* private static */ /* String */ generateAll(template BiFunction</* StringBuilder */, /*  String */, /*  StringBuilder */> merger, template List</* String */> right){/* return right.iterate().fold(new StringBuilder(), merger).toString(); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, template List</* String */>>> parseAll(/* CompileState */ initial, /* String */ input, template BiFunction</* DivideState */, /*  Character */, /*  DivideState */> folder, template BiFunction</* CompileState */, /*  String */, template Option<template Tuple</* CompileState */, /*  String */>>> mapper, template BiFunction</* StringBuilder */, /*  String */, /*  StringBuilder */> merger){/* return divide(input, folder)
                .iterate()
                .<Option<Tuple<CompileState, List<String>>>>fold(new Some<>(new Tuple<CompileState, List<String>>(initial, Lists.empty())),
                        (maybeCurrent, segment) -> maybeCurrent.flatMap(
                                state -> foldElement(state, segment, mapper, merger))); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, template List</* String */>>> foldElement(template Tuple</* CompileState */, template List</* String */>> state, /* String */ segment, template BiFunction</* CompileState */, /*  String */, template Option<template Tuple</* CompileState */, /*  String */>>> mapper, template BiFunction</* StringBuilder */, /*  String */, /*  StringBuilder */> merger){/* var oldState = state.left; *//* var oldCache = state.right; *//* return mapper.apply(oldState, segment).map(result -> {
            var newState = result.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        } *//* ); *//*  */
}
/* private static */ /* StringBuilder */ mergeStatements(/* StringBuilder */ output, /* String */ right){/* return output.append(right); *//*  */
}
/* private static */ template List</* String */> divide(/* String */ input, template BiFunction</* DivideState */, /*  Character */, /*  DivideState */> folder){/* DivideState current = new DivideState(input); *//* while (true) {
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
/* private static */ template Option</* DivideState */> foldDoubleQuotes(/* DivideState */ state, /* char */ c){/* if (c != '\"') {
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
        } *//* return new Some<>(appended); *//*  */
}
/* private static */ template Option</* DivideState */> foldSingleQuotes(/* DivideState */ state, /* char */ c){/* if (c != '\'') {
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
/* private static */ /* DivideState */ foldStatementChar(/* DivideState */ state, /* char */ c){/* var appended = state.append(c); *//* if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } *//* if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        } *//* if (c == '}') {
            return appended.exit();
        } *//* return appended; *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> compileRootSegment(/* CompileState */ state, /* String */ input){/* return or(state, input, Lists.of(
                Main::whitespace,
                Main::compileNamespaced,
                structure("class "),
                Main::content
        )); *//*  */
}
/* private static */ template BiFunction</* CompileState */, /*  String */, template Option<template Tuple</* CompileState */, /*  String */>>> structure(/* String */ infix){/* return (state, input) -> first(input, infix, (beforeKeyword, afterKeyword) -> {
            var slices = Arrays.stream(beforeKeyword.split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            if (slices.contains("@External")) {
                return new None<>();
            }

            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return or(state, beforeContent, Lists.of(
                        (state3, beforeContent0) -> structureWithVariants(beforeKeyword, withEnd, state3, beforeContent0),
                        (state1, s) -> structureWithoutVariants(state1, beforeKeyword, s, Lists.empty(), withEnd)
                ));
            });
        } *//* ); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> structureWithVariants(/* String */ beforeKeyword, /* String */ withEnd, /*  CompileState state3 */, /*  String beforeContent0 */){/* return first(beforeContent0, " permits ", (beforePermits, variantsString) -> {
            return parseValues(state3, variantsString, Main::symbol).flatMap(params -> {
                return structureWithoutVariants(params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        } *//* ); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> symbol(/* CompileState */ state, /* String */ value){/* return new Some<>(new Tuple<>(state, value.strip())); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> structureWithoutVariants(/* CompileState */ state, /* String */ beforeKeyword, /* String */ beforeContent, template List</* String */> variants, /* String */ withEnd){/* return or(state, beforeContent, Lists.of(
                (instance, before) -> structureWithParams(instance, beforeKeyword, before, variants, withEnd),
                (instance, before) -> structureWithMaybeTypeParams(instance, beforeKeyword, before.strip(), "", variants, withEnd)
        )); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> structureWithParams(/* CompileState */ instance, /* String */ beforeKeyword, /* String */ beforeContent, template List</* String */> variants, /* String */ withEnd){/* return suffix(beforeContent.strip(), ")", withoutEnd -> first(withoutEnd, "(", (name, paramString) -> {
            return all(instance, paramString, Main::foldValueChar, Main::compileParameter, Main::mergeStatements).flatMap(params -> {
                return structureWithMaybeTypeParams(params.left, beforeKeyword, name, params.right, variants, withEnd);
            });
        } *//* )); *//*  */
}
/* private static */ /* StringBuilder */ mergeValues(/* StringBuilder */ buffer, /* String */ element){/* if (buffer.isEmpty()) {
            return buffer.append(element);
        } *//* return buffer.append(", ").append(element); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> compileParameter(/* CompileState */ instance, /* String */ paramString){/* return or(instance, paramString, Lists.of(
                Main::definition,
                Main::content
        )); *//*  */
}
/* private static */ /* DivideState */ foldValueChar(/* DivideState */ state, /* char */ c){/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } *//* var appended = state.append(c); *//* if (c == '<') {
            return appended.enter();
        } *//* if (c == '>') {
            return appended.exit();
        } *//* return appended; *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> structureWithMaybeTypeParams(/* CompileState */ state, /* String */ beforeKeyword, /* String */ beforeParams, /* String */ params, template List</* String */> variants, /* String */ withEnd){/* return or(state, beforeParams, Lists.of(
                (state0, beforeParams0) -> structureWithTypeParams(state0, beforeParams0, beforeKeyword, params, variants, withEnd),
                (state0, name) -> structureWithName(state0, beforeKeyword, name, Lists.empty(), params, variants, withEnd)
        )); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> structureWithTypeParams(/* CompileState */ state, /* 
            String beforeParams0 */, /* String */ beforeKeyword, /* String */ params, template List</* String */> variants, /* String */ withEnd){/* return suffix(beforeParams0.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (name, typeParamString) -> {
                return parseValues(state, typeParamString, Main::symbol).flatMap(values -> {
                    return structureWithName(values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        } *//* ); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> structureWithName(/* CompileState */ state, /* String */ beforeKeyword, /* String */ name, template List</* String */> typeParams, /* String */ params, template List</* String */> variants, /* String */ withEnd){/* return suffix(withEnd.strip(), "}", content -> {
            return compileAll(state, content, Main::structSegment).flatMap(tuple -> {
                return new Some<>(assembleStruct(tuple.left, beforeKeyword, name, typeParams, params, variants, tuple.right));
            });
        } *//* ); *//*  */
}
/* private static */ template Tuple</* CompileState */, /*  String */> assembleStruct(/* CompileState */ state, /* String */ beforeKeyword, /* String */ name, template List</* String */> typeParams, /* String */ params, template List</* String */> variants, /* String */ content){/* if (variants.isEmpty()) {
            return generateStruct(state, beforeKeyword, name, typeParams, params, content);
        } *//* var enumName = name + "Variant"; *//* var variantsString = variants.iterate()
                .map(variant -> "\n\t" + variant)
                .collect(new Joiner(","))
                .orElse(""); *//* var generatedEnum = "enum " + enumName + " {" + variantsString + "\n};\n"; *//* var compileState = state.addStruct(generatedEnum); *//* return generateStruct(compileState, beforeKeyword, name, typeParams, params, "\n\t" + enumName + " _variant;" + content); *//*  */
}
/* private static */ template Tuple</* CompileState */, /*  String */> generateStruct(/* CompileState */ state, /* String */ beforeKeyword, /* String */ name, template List</* String */> typeParams, /* String */ params, /* String */ content){/* String typeParamString; *//* if (typeParams.isEmpty()) {
            typeParamString = "";
        } *//* else {
            typeParamString = "<" + typeParams.iterate().collect(new Joiner(", ")).orElse("") + ">";
        } *//* var generatedStruct = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + typeParamString + " {" + params + content + "\n};\n"; *//* return new Tuple<CompileState, String>(state.addStruct(generatedStruct), ""); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> or(/* CompileState */ state, /* String */ input, template List<template BiFunction</* CompileState */, /*  String */, template Option<template Tuple</* CompileState */, /*  String */>>>> actions){/* return actions.iterate()
                .map(action -> action.apply(state, input))
                .flatMap(Iterators::fromOptions)
                .next(); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> compileNamespaced(/* CompileState */ state, /* String */ input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } *//* return new None<>(); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> structSegment(/* CompileState */ state, /* String */ input){/* return or(state, input, Lists.of(
                Main::whitespace,
                structure("record "),
                structure("interface "),
                Main::method,
                Main::definitionStatement,
                Main::content
        )); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> definitionStatement(/* CompileState */ state, /* String */ input){/* return suffix(input.strip(), ";", withoutEnd -> definition(state, withoutEnd).map(value -> {
            var generated = "\n\t" + value.right + ";";
            return new Tuple<>(value.left, generated);
        } *//* )); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> content(/* CompileState */ state, /* String */ input){/* return new Some<>(new Tuple<CompileState, String>(state, generatePlaceholder(input))); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> whitespace(/* CompileState */ state, /* String */ input){/* if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } *//* return new None<>(); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> method(/* CompileState */ state, /* String */ input){/* return first(input, "(", (inputDefinition, withParams) -> {
            return first(withParams, ")", (params, withBraces) -> {
                return compileMethodHeader(state, inputDefinition).flatMap(outputDefinition -> {
                    return values(outputDefinition.left, params, Main::compileParameter).flatMap(outputParams -> {
                        return or(outputParams.left, withBraces, Lists.of(
                                (state0, element) -> methodWithoutContent(state0, outputDefinition.right, outputParams.right, element),
                                (state0, element) -> methodWithContent(state0, outputDefinition.right, outputParams.right, element)));
                    });
                });
            });
        } *//* ); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> values(/* CompileState */ state, /* String */ input, template BiFunction</* CompileState */, /*  String */, template Option<template Tuple</* CompileState */, /*  String */>>> compiler){/* return parseValues(state, input, compiler).map(tuple -> new Tuple<>(tuple.left, generateValues(tuple.right))); *//*  */
}
/* private static */ /* String */ generateValues(template List</* String */> values){/* return generateAll(Main::mergeValues, values); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, template List</* String */>>> parseValues(/* CompileState */ state, /* String */ input, template BiFunction</* CompileState */, /*  String */, template Option<template Tuple</* CompileState */, /*  String */>>> compiler){/* return parseAll(state, input, Main::foldValueChar, compiler, Main::mergeValues); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> methodWithoutContent(/* CompileState */ state, /* String */ definition, /* String */ params, /* String */ content){/* if (content.equals(";")) {
            var generated = "\n\t" + definition + "(" + params + ");";
            return new Some<>(new Tuple<CompileState, String>(state, generated));
        } *//* else {
            return new None<>();
        } *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> methodWithContent(/* CompileState */ state, /* String */ outputDefinition, /* String */ params, /* String */ withBraces){/* return prefix(withBraces.strip(), "{", withoutStart1 -> {
            return suffix(withoutStart1, "}", content -> {
                return compileAll(state, content, Main::compileFunctionSegment).flatMap(tuple -> {
                    var generated = outputDefinition + "(" + params + "){" + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(state.addFunction(generated), ""));
                });
            });
        } *//* ); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> compileMethodHeader(/* CompileState */ state, /* String */ definition){/* return or(state, definition, Lists.of(
                Main::definition,
                Main::content
        )); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> compileFunctionSegment(/* CompileState */ state, /* String */ input){/* return or(state, input.strip(), Lists.of(
                Main::content
        )); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> definition(/* CompileState */ state, /* String */ input){/* return infix(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (beforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            return or(state, beforeName.strip(), Lists.of(
                    (instance, beforeName0) -> definitionWithTypeSeparator(instance, beforeName0, name),
                    (instance, beforeName0) -> definitionWithoutTypeSeparator(instance, beforeName0, name)
            ));
        } *//* ); *//*  */
}
/* private static */ int isSymbol(/* String */ value){/* for (var i = 0; *//* i < value.length(); *//* i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* return true; *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> definitionWithoutTypeSeparator(/* CompileState */ state, /* String */ type, /* String */ name){/* return type(state, type).flatMap(typeTuple -> {
            var generated = typeTuple.right + " " + name.strip();
            return new Some<>(new Tuple<CompileState, String>(typeTuple.left, generated));
        } *//* ); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> definitionWithTypeSeparator(/* CompileState */ state, /* String */ beforeName, /* String */ name){/* return infix(beforeName, new TypeSeparatorSplitter(), (beforeType, typeString) -> {
            return type(state, typeString).flatMap(typeTuple -> {
                var generated = generatePlaceholder(beforeType) + " " + typeTuple.right + " " + name.strip();
                return new Some<>(new Tuple<CompileState, String>(typeTuple.left, generated));
            });
        } *//* ); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> type(/* CompileState */ state, /* String */ input){/* return or(state, input, Lists.of(
                Main::primitive,
                Main::template,
                Main::content
        )); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> primitive(/* CompileState */ state, /* String */ input){/* var stripped = input.strip(); *//* if (stripped.equals("boolean")) {
            return new Some<>(new Tuple<>(state, "int"));
        } *//* return new None<>(); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> template(/* CompileState */ state, /* String */ input){/* return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                return parseValues(state, argumentsString, Main::type).flatMap(argumentsTuple -> {
                    var arguments = argumentsTuple.right;

                    String generated;
                    if (base.equals("Function")) {
                        generated = generateFunctionalType(Lists.of(arguments.get(0)), arguments.get(1));
                    }
                    else if (base.equals("Supplier")) {
                        generated = generateFunctionalType(Lists.empty(), arguments.get(0));
                    }
                    else {
                        var generatedTuple = generateValues(arguments);
                        generated = "template " + base + "<" + generatedTuple + ">";
                    }

                    return new Some<>(new Tuple<>(argumentsTuple.left, generated));
                });
            });
        } *//* ); *//*  */
}
/* private static */ /* String */ generateFunctionalType(template List</* String */> arguments, /* String */ returns){/* return returns + " (*)(" + generateValues(arguments) + ")"; *//*  */
}
/* private static */ template Option</* Integer */> lastIndexOfSlice(/* String */ input, /* String */ infix){/* var index = input.lastIndexOf(infix); *//* return index == -1 ? new None<Integer>() : new Some<>(index); *//*  */
}
/* private static */ template Option<template Tuple</* CompileState */, /*  String */>> prefix(/* String */ input, /* String */ prefix, template Option<template Tuple</* CompileState */, /*  String */>> (*)(/* String */) mapper){/* if (!input.startsWith(prefix)) {
            return new None<>();
        } *//* var slice = input.substring(prefix.length()); *//* return mapper.apply(slice); *//*  */
}
/* private static <T> */ template Option</* T */> suffix(/* String */ input, /* String */ suffix, template Option</* T */> (*)(/* String */) mapper){/* if (!input.endsWith(suffix)) {
            return new None<>();
        } *//* var slice = input.substring(0, input.length() - suffix.length()); *//* return mapper.apply(slice); *//*  */
}
/* private static */ /* String */ generatePlaceholder(/* String */ input){/* return "/* " + input + " */"; *//*  */
}
/* private static <T> */ template Option</* T */> first(/* String */ input, /* String */ infix, template BiFunction</* String */, /*  String */, template Option</* T */>> mapper){/* return infix(input, new InfixSplitter(infix, Main::firstIndexOfSlice), mapper); *//*  */
}
/* private static <T> */ template Option</* T */> infix(/* String */ input, /* Splitter */ splitter, template BiFunction</* String */, /*  String */, template Option</* T */>> mapper){/* return splitter.split(input).flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        } *//* ); *//*  */
}
/* private static */ template Option</* Integer */> firstIndexOfSlice(/* String */ input, /* String */ infix){/* var index = input.indexOf(infix); *//* return index == -1 ? new None<Integer>() : new Some<>(index); *//*  */
}

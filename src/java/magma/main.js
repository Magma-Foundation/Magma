"use strict";
/* private */ class Some {
}
(T);
value;
implements;
Option < T > {
    /* @Override
        public  */ map(mapper) {
        return /* new Some<>(mapper.apply(this.value)) */;
    }
    /* @Override
        public */ ,
    /* @Override
        public */ isPresent() {
        return /* true */;
    }
    /* @Override
        public */ ,
    /* @Override
        public */ orElse(other) {
        return /* this.value */;
    }
    /* @Override
        public */ ,
    /* @Override
        public */ filter(predicate) {
        return /* predicate.test(this.value) ? this : new None<>() */;
    }
    /* @Override
        public */ ,
    /* @Override
        public */ orElseGet(supplier) {
        return /* this.value */;
    }
    /* @Override
        public */ ,
    /* @Override
        public */ or(other) {
        return /* this */;
    }
    /* @Override
        public  */ ,
    /* @Override
        public  */ flatMap(mapper) {
        return /* mapper.apply(this.value) */;
    }
    /* @Override
        public */ ,
    /* @Override
        public */ isEmpty() {
        return /* false */;
    }
};
/* private static */ class None {
    /* @Override
        public  */ map(mapper) {
        return /* new None<>() */;
    }
    /* @Override
        public */ isPresent() {
        return /* false */;
    }
    /* @Override
        public */ orElse(other) {
        return /* other */;
    }
    /* @Override
        public */ filter(predicate) {
        return /* new None<>() */;
    }
    /* @Override
        public */ orElseGet(supplier) {
        return /* supplier.get() */;
    }
    /* @Override
        public */ or(other) {
        return /* other.get() */;
    }
    /* @Override
        public  */ flatMap(mapper) {
        return /* new None<>() */;
    }
    /* @Override
        public */ isEmpty() {
        return /* true */;
    }
}
/* private */ class HeadedIterator {
}
(Head < T > head);
implements;
Iterator < T > {
    /* @Override
        public  */ fold(initial, folder) {
        /* var current = initial */ ; /* while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.orElse(null);
                }
                else {
                    return current;
                }
            } */
    }
    /* @Override
        public  */ ,
    /* @Override
        public  */ map(mapper) {
        return /* new HeadedIterator<>(() -> this.head.next().map(mapper)) */;
    }
    /* @Override
        public  */ ,
    /* @Override
        public  */ collect(collector) {
        return /* this.fold(collector.createInitial(), collector::fold) */;
    }
};
/* private static */ class RangeHead {
    RangeHead(length) {
        /* this.length = length */ ;
    }
    /* @Override
        public */ next() {
        return /* new None<>() */;
    }
}
/* private static final */ class JVMList {
    JVMList(elements) {
        /* this.elements = elements */ ;
    }
    JVMList() {
        /* this(new ArrayList<>()) */ ;
    }
    /* @Override
            public */ add(element) {
        /* this.elements.add(element) */ ;
        return /* this */;
    }
    /* @Override
            public */ iterate() {
        return /* new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get) */;
    }
    /* @Override
            public */ removeLast() {
        /* var slice = this.elements.subList(0, this.elements.size() - 1) */ ;
        /* var last = this.elements.getLast() */ ;
        return /* new Some<>(new Tuple<List<T>, T>(new JVMList<>(slice), last)) */;
    }
    /* @Override
            public */ get(index) {
        return /* this.elements.get(index) */;
    }
}
/* private static */ class Lists {
    /* public static  */ empty() {
        return /* new JVMList<>() */;
    }
    /* public static  */ of(elements /* T... */) {
        return /* new JVMList<>(new ArrayList<>(Arrays.asList(elements))) */;
    }
}
/* private */ class CompileState {
}
(List < String > structures);
{
    CompileState();
    public;
    {
        /* this(Lists.empty()) */ ;
    }
    /* public */ addStructure(structure, string);
    CompileState;
    {
        return /* new CompileState(this.structures.add(structure)) */;
    }
}
/* private static */ class DivideState {
    DivideState(input, index, segments, buffer, depth) {
        /* this.segments = segments */ ;
        /* this.buffer = buffer */ ;
        /* this.depth = depth */ ;
        /* this.input = input */ ;
        /* this.index = index */ ;
    }
    DivideState(input) {
        /* this(input, 0, Lists.empty(), new StringBuilder(), 0) */ ;
    }
    /* private */ advance() {
        /* this.segments = this.segments.add(this.buffer.toString()) */ ;
        /* this.buffer = new StringBuilder() */ ;
        return /* this */;
    }
    /* private */ append(c) {
        /* this.buffer.append(c) */ ;
        return /* this */;
    }
    /* public */ enter() {
        /* this.depth++ */ ;
        return /* this */;
    }
    /* public */ isLevel() {
        return /* this.depth == 0 */;
    }
    /* public */ exit() {
        /* this.depth-- */ ;
        return /* this */;
    }
    /* public */ isShallow() {
        return /* this.depth == 1 */;
    }
    /* public */ pop() {
        return /* new None<>() */;
    }
    /* public */ popAndAppendToTuple() {
        return /* this.pop().map(tuple -> new Tuple<>(tuple.left, tuple.right.append(tuple.left))) */;
    }
    /* public */ popAndAppendToOption() {
        return /* this.popAndAppendToTuple().map(Tuple::right) */;
    }
}
/* private */ class Joiner {
}
(String);
delimiter;
implements;
Collector < String, Option < String >> {
    Joiner() {
        /* this("") */ ;
    }
    /* @Override
        public */ ,
    /* @Override
        public */ createInitial() {
        return /* new None<>() */;
    }
    /* @Override
        public */ ,
    /* @Override
        public */ fold(current, element) {
        return /* new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element)) */;
    }
};
/* private */ class Definition {
}
(Option < String > maybeBefore, String);
type, String;
name, List < String > typeParams;
{
    /* private */ generate();
    string;
    {
        return /* this.generateWithParams("") */;
    }
    /* public */ generateWithParams(params, string);
    string;
    {
        /* var joined = this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("") */ ;
        /* var before = this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("") */ ;
        return /* before + this.name + joined + params + " : " + this.type */;
    }
}
/* private static */ class ListCollector {
    /* @Override
        public */ createInitial() {
        return /* Lists.empty() */;
    }
    /* @Override
        public */ fold(current, element) {
        return /* current.add(element) */;
    }
}
/* private */ class Tuple {
}
(A);
left, B;
right;
{
}
/* public */ class Main {
    /* public static */ main() {
    }
    /* private static */ compile(input) {
        /* var tuple = compileStatements(new CompileState(), input, Main::compileRootSegment) */ ;
        /* var joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("") */ ;
        return /* joined + tuple.right */;
    }
    /* private static */ compileStatements(state, input, mapper) {
        return /* compileAll(state, input, Main::foldStatementChar, mapper, Main::mergeStatements) */;
    }
    /* private static */ compileAll(state, input, folder, mapper, merger) {
        /* var parsed = parseAll(state, input, folder, mapper) */ ;
        /* var generated = generateAll(merger, parsed.right) */ ;
        return /* new Tuple<>(parsed.left, generated) */;
    }
    /* private static */ generateAll(merger, elements) {
        return /* elements
                .iterate()
                .fold(new StringBuilder(), merger)
                .toString() */;
    }
    /* private static */ parseAll(state, input, folder, mapper) {
        /* ) */ ;
    }
    /* private static */ mergeStatements(stringBuilder, str) {
        return /* stringBuilder.append(str) */;
    }
    /* private static */ divideAll(input, folder) {
        /* var current = new DivideState(input) */ ; /* while (true) {
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
        } */
        return /* current.advance().segments */;
    }
    /* private static */ foldDoubleQuotes(tuple) {
        return /* new None<>() */;
    }
    /* private static */ foldSingleQuotes(tuple) {
        return /* new None<>() */;
    }
    /* private static */ foldStatementChar(state, c) {
        /* var append = state.append(c) */ ; /* if (c == ';' && append.isLevel()) {
            return append.advance();
        } */ /* if (c == '}' && append.isShallow()) {
            return append.advance().exit();
        } */ /* if (c == '{') {
            return append.enter();
        } */ /* if (c == '}') {
            return append.exit();
        } */
        return /* append */;
    }
    /* private static */ compileRootSegment(state, input) {
        /* var stripped = input.strip() */ ; /* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        } */
        return /* compileClass(stripped, 0, state)
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped))) */;
    }
    /* private static */ compileClass(stripped, depth, state) {
        return /* compileStructure(stripped, "class ", "class ", state) */;
    }
    /* private static */ compileStructure(stripped, sourceInfix, targetInfix, state) {
        /* ) */ ;
    }
    /* private static */ isSymbol(input) {
        /* for (var i = 0 */ ;
        /* i < input.length() */ ; /* i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
        return /* true */;
    }
    /* private static  */ suffix(input, suffix, mapper) {
        /* var slice = input.substring(0, input.length() - suffix.length()) */ ;
        return /* mapper.apply(slice) */;
    }
    /* private static */ compileClassSegment(state, input, depth) {
        return /* compileWhitespace(input, state)
                .or(() -> compileClass(input, depth, state))
                .or(() -> compileStructure(input, "interface ", "interface ", state))
                .or(() -> compileStructure(input, "record ", "class ", state))
                .or(() -> method(state, input, depth))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input))) */;
    }
    /* private static */ compileWhitespace(input, state) {
        return /* new None<>() */;
    }
    /* private static */ method(state, input, depth) {
        /* ) */ ;
    }
    /* private static */ compileFunctionalSegment(state, input, depth) {
        /* var stripped = input.strip() */ ; /* if (stripped.isEmpty()) {
            return new Tuple<>(state, "");
        } */ /* return suffix(stripped, ";", s -> {
            var tuple = statementValue(state, s);
            return new Some<>(new Tuple<>(tuple.left, createIndent(depth) + tuple.right + ";"));
        } */ /* ).orElseGet(() -> {
            return new Tuple<>(state, generatePlaceholder(stripped));
        } */
        /* ) */ ;
    }
    /* private static */ statementValue(state, input) {
        /* var stripped = input.strip() */ ; /* if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            var tuple = compileValue(state, value);
            return new Tuple<>(tuple.left, "return " + tuple.right);
        } */
        return /* new Tuple<>(state, generatePlaceholder(stripped)) */;
    }
    /* private static */ compileValue(state, value) {
        return /* new Tuple<CompileState, String>(state, generatePlaceholder(value)) */;
    }
    /* private static */ compileValues(state, params, mapper) {
        /* var parsed = parseValues(state, params, mapper) */ ;
        /* var generated = generateValues(parsed.right) */ ;
        return /* new Tuple<>(parsed.left, generated) */;
    }
    /* private static */ generateValues(elements) {
        return /* generateAll(Main::mergeValues, elements) */;
    }
    /* private static */ parseValues(state, input, mapper) {
        return /* parseAll(state, input, Main::foldValueChar, mapper) */;
    }
    /* private static */ compileParameter(state, input) {
        return /* parseDefinition(state, input)
                .map((Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input))) */;
    }
    /* private static */ mergeValues(cache, element) {
        return /* cache.append(", ").append(element) */;
    }
    /* private static */ createIndent(depth) {
        return /* "\n" + "\t".repeat(depth) */;
    }
    /* private static */ compileDefinitionStatement(input, depth, state) {
        /* ) */ ;
    }
    /* private static */ parseDefinition(state, input) {
        /* ) */ ;
    }
    /* private static */ getStringStringTuple(beforeName) {
        /* var divisions = divideAll(beforeName, Main::foldTypeSeparator) */ ; /* return divisions.removeLast().map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        } */
        /* ) */ ;
    }
    /* private static */ foldTypeSeparator(state, c) {
        /* var appended = state.append(c) */ ; /* if (c == '<') {
            return appended.enter();
        } */ /* if (c == '>') {
            return appended.exit();
        } */
        return /* appended */;
    }
    /* private static */ assembleDefinition(state, beforeTypeParams, name, typeParams, type) {
        /* var type1 = type(state, type) */ ;
        /* var node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams) */ ;
        return /* new Some<>(new Tuple<>(type1.left, node)) */;
    }
    /* private static */ foldValueChar(state, c) {
        /* var appended = state.append(c) */ ; /* if (c == '<') {
            return appended.enter();
        } */ /* if (c == '>') {
            return appended.exit();
        } */
        return /* appended */;
    }
    /* private static */ type(state, input) {
        /* var stripped = input.strip() */ ; /* if (stripped.equals("int")) {
            return new Tuple<>(state, "number");
        } */ /* if (stripped.equals("String")) {
            return new Tuple<>(state, "string");
        } */ /* if (isSymbol(stripped)) {
            return new Tuple<>(state, stripped);
        } */
        return /* template(state, input).orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped))) */;
    }
    /* private static */ template(state, input) {
        /* ) */ ;
    }
    /* private static */ generate(arguments, returns) {
        /* var joined = arguments.iterate()
                .collect(new Joiner(", "))
                .orElse("") */ ;
        return /* "(" + joined + ") => " + returns */;
    }
    /* private static  */ last(input, infix, mapper) {
        return /* infix(input, infix, Main::findLast, mapper) */;
    }
    /* private static */ findLast(input, infix) {
        /* var index = input.lastIndexOf(infix) */ ;
        return /* index == -1 ? new None<Integer>() : new Some<>(index) */;
    }
    /* private static  */ first(input, infix, mapper) {
        return /* infix(input, infix, Main::findFirst, mapper) */;
    }
    /* private static  */ infix(input, infix, locator, mapper) {
        /* ), mapper) */ ;
    }
    /* private static  */ split(splitter, mapper) {
        return /* splitter.get().flatMap(tuple -> mapper.apply(tuple.left, tuple.right)) */;
    }
    /* private static */ findFirst(input, infix) {
        /* var index = input.indexOf(infix) */ ;
        return /* index == -1 ? new None<Integer>() : new Some<>(index) */;
    }
    /* private static */ generatePlaceholder(input) {
        /* var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end") */ ;
        return /* "content-start " + replaced + " content-end" */;
    }
}
/*  */ 

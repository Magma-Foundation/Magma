/* private */interface Option<T>/*   */ {
	map<R>(mapper : (T) => R) : Option<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (T) => boolean) : Option<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => Option<T>) : Option<T>;
	flatMap<R>(mapper : (T) => Option<R>) : Option<R>;
	isEmpty() : boolean;
}
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C;
	fold(current : C, element : T) : C;
}
/* private */interface Iterator<T>/*   */ {
	fold<R>(initial : R, folder : (R, T) => R) : R;
	map<R>(mapper : (T) => R) : Iterator<R>;
	collect<R>(collector : Collector<T, R>) : R;
}
/* private */interface List<T>/*   */ {
	add(element : T) : List<T>;
	iterate() : Iterator<T>;
	removeLast() : Option<[List<T>, T]>;
	get(index : number) : T;
}
/* private */interface Head<T>/*   */ {
	next() : Option<T>;
}
/* private */class Some<T>/* (T value) implements Option<T>  */ {
	/* @Override
        public  */ map<R>(mapper : (T) => R) : Option<R> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](this.value));
	}
	/* @Override
        public */ isPresent() : boolean {
		return true;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return this.value;
	}
	/* @Override
        public */ filter(predicate : (T) => boolean) : Option<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* this.value) ? this : new None<>( */);
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return this.value;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return this;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (T) => Option<R>) : Option<R> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](this.value);
	}
	/* @Override
        public */ isEmpty() : boolean {
		return false;
	}
}
/* private static */class None<T>/*  implements Option<T>  */ {
	/* @Override
        public  */ map<R>(mapper : (T) => R) : Option<R> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* @Override
        public */ isPresent() : boolean {
		return false;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return other;
	}
	/* @Override
        public */ filter(predicate : (T) => boolean) : Option<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* @Override
        public  */ flatMap<R>(mapper : (T) => Option<R>) : Option<R> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* @Override
        public */ isEmpty() : boolean {
		return true;
	}
}
/* private */class HeadedIterator<T>/* (Head<T> head) implements Iterator<T>  */ {
	/* @Override
        public  */ fold<R>(initial : R, folder : (R, T) => R) : R {
		let current : var = initial;/* while (true) {
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
        public  */ map<R>(mapper : (T) => R) : Iterator<R> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) -> this.head.next().map(mapper */));
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](), /*  collector::fold */);
	}
}
/* private static final */class JVMList<T>/*  implements List<T>  */ {
	/* private final */ elements : java.util.List<T>;
	JVMList(elements : java.util.List<T>) : private {
		let /* this.elements  */ = elements;
	}
	JVMList() : public {
		/* this(new ArrayList<>()) */;
	}
	/* @Override
            public */ add(element : T) : List<T> {
		/* this.elements.add(element) */;
		return this;
	}
	/* @Override
            public */ iterate() : Iterator<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* new RangeHead(this.elements.size())).map(this.elements::get */);
	}
	/* @Override
            public */ removeLast() : Option<[List<T>, T]> {/* if (this.elements.isEmpty()) {
                    return new None<>();
                } */
		let slice : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* 0 */, /*  this.elements.size() - 1 */);
		let last : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* new JVMList<>(slice */), /*  last) */);
	}
	/* @Override
            public */ get(index : number) : T {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](index);
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : List<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* public static  */ of<T>(elements : T[]) : List<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](elements)));
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : List<string>;
	/* private */ buffer : StringBuilder;
	DivideState(input : string, index : number, segments : List<string>, buffer : StringBuilder, depth : number) : public {
		let /* this.segments  */ = segments;
		let /* this.buffer  */ = buffer;
		let /* this.depth  */ = depth;
		let /* this.input  */ = input;
		let /* this.index  */ = index;
	}
	DivideState(input : string) : public {
		/* this(input, 0, Lists.empty(), new StringBuilder(), 0) */;
	}
	/* private */ advance() : DivideState {
		let /* this.segments  */ = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]());
		let /* this.buffer  */ = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
		return this;
	}
	/* private */ append(c : char) : DivideState {
		/* this.buffer.append(c) */;
		return this;
	}
	/* public */ enter() : DivideState {
		/* this.depth++ */;
		return this;
	}
	/* public */ isLevel() : boolean {
		return /* this.depth == 0 */;
	}
	/* public */ exit() : DivideState {
		/* this.depth-- */;
		return this;
	}
	/* public */ isShallow() : boolean {
		return /* this.depth == 1 */;
	}
	/* public */ pop() : Option<[Character, DivideState]> {/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<>(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
            } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* public */ popAndAppendToTuple() : Option<[Character, DivideState]> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](tuple.left, /* tuple.right.append(tuple */.left)));
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ).map(Tuple::right */);
	}
}
/* private static */class ListCollector<T>/*  implements Collector<T, List<T>>  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](element);
	}
}
/* private */class Tuple<A, B>/* (A left, B right)  */ {
}
/* public */class Main/*  */ {
	/* private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter;

        */ RangeHead(length : number) : public {
		let /* this.length  */ = length;/* }

        @Override
        public Option<Integer> next() {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return new Some<>(value);
            } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();/* } */
	}
	/* private */ CompileState(structures : List<string>) : record {/* public CompileState() {
            this(Lists.empty());
        } *//* public CompileState addStructure(String structure) {
            return new CompileState(this.structures.add(structure));
        } */
	}/* 

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
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
	/* private */ Definition(maybeBefore : Option<string>, type : string, name : string, typeParams : List<string>) : record {/* private String generate() {
            return this.generateWithParams("");
        } *//* public String generateWithParams(String params) {
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
        } */
	}
	/* public static */ main() : void {/* try {
            var parent = Paths.get(".", "src", "java", "magma");
            var source = parent.resolve("Main.java");
            var target = parent.resolve("main.ts");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
        } *//* catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } */
	}
	/* private static */ compile(input : string) : string {
		let tuple : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](), input, /*  Main::compileRootSegment */);
		let joined : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ).collect(new Joiner()).orElse("" */);
		return joined + tuple.right;
	}
	/* private static */ compileStatements(state : CompileState, input : string, mapper : (CompileState, string) => [CompileState, string]) : [CompileState, string] {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, input, /*  Main::foldStatementChar */, mapper, /*  Main::mergeStatements */);
	}
	/* private static */ compileAll(state : CompileState, input : string, folder : (DivideState, Character) => DivideState, mapper : (CompileState, string) => [CompileState, string], merger : (StringBuilder, string) => StringBuilder) : [CompileState, string] {
		let parsed : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, input, folder, mapper);
		let generated : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](merger, parsed.right);
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](parsed.left, generated);
	}
	/* private static */ generateAll(merger : (StringBuilder, string) => StringBuilder, elements : List<string>) : string {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* new StringBuilder( */), /*  merger)
                .toString( */);
	}
	/* private static */ parseAll(state : CompileState, input : string, folder : (DivideState, Character) => DivideState, mapper : (CompileState, string) => [CompileState, string]) : [CompileState, List<string>] {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](input, /*  folder).iterate().fold(new Tuple<>(state */, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) */), /*  (tuple */, /*  element) -> {
            var state1 = tuple.left;
            var right = tuple.right;

            var applied = mapper.apply(state1, element);
            return new Tuple<>(applied.left, right.add(applied.right));
        } */);
	}
	/* private static */ mergeStatements(stringBuilder : StringBuilder, str : string) : StringBuilder {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](str);
	}
	/* private static */ divideAll(input : string, folder : (DivideState, Character) => DivideState) : List<string> {
		let current : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](input);/* while (true) {
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
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]().segments;
	}
	/* private static */ foldDoubleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {/* if (tuple.left == '\"') {
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
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* private static */ foldSingleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {/* if (tuple.left == '\'') {
            var appended = tuple.right.append(tuple.left);
            return appended.popAndAppendToTuple()
                    .map(escaped -> escaped.left == '\\' ? escaped.right.popAndAppendToOption().orElse(escaped.right) : escaped.right)
                    .flatMap(DivideState::popAndAppendToOption);
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* private static */ foldStatementChar(state : DivideState, c : char) : DivideState {
		let append : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](c);/* if (c == ';' && append.isLevel()) {
            return append.advance();
        } *//* if (c == '}' && append.isShallow()) {
            return append.advance().exit();
        } *//* if (c == '{' || c == '(') {
            return append.enter();
        } *//* if (c == '}' || c == ')') {
            return append.exit();
        } */
		return append;
	}
	/* private static */ compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();/* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, "");
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](stripped, /*  0 */, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) -> new Tuple<>(state, generatePlaceholder(stripped */)));
	}
	/* private static */ compileClass(stripped : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](stripped, "class ", "class ", state);
	}
	/* private static */ structure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, string]> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](stripped, sourceInfix, /*  (beforeInfix */, /*  right) -> {
            return first(right, "{", (beforeContent, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return suffix(strippedWithEnd, "}", content1 -> {
                    return first(beforeContent, "<", new BiFunction<String, String, Option<Tuple<CompileState */, /*  String>>>() {
                        @Override
                        public Option<Tuple<CompileState, String>> apply(String name, String withTypeParams) {
                            return first(withTypeParams, ">", new BiFunction<String, String, Option<Tuple<CompileState */, /*  String>>>() {
                                @Override
                                public Option<Tuple<CompileState, String>> apply(String typeParamsString, String afterTypeParams) {
                                    var typeParams = parseValues(state, typeParamsString, (state1, s) -> new Tuple<>(state1, s.strip()));
                                    return assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams);
                                }
                            });
                        }
                    }).or(() -> {
                        return assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "");
                    });
                });
            });
        } */);
	}
	/* private static */ assemble(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string) : Option<[CompileState, string]> {
		let name : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();/* if (!isSymbol(name)) {
            return new None<>();
        } */
		let joinedTypeParams : var = /*  typeParams.iterate().collect(new Joiner(", ")).map(inner -> "<"  */ + inner + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]("");
		let statements : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, content, /*  (state0 */, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* state0 */, input, /*  1 */));
		let generated : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]()) + targetInfix + name + joinedTypeParams + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](afterTypeParams) + " {" + statements.right + "\n}\n";
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* statements.left.addStructure(generated */), /*  "") */);
	}
	/* private static */ isSymbol(input : string) : boolean {/* for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } */
		return true;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (string) => Option<T>) : Option<T> {/* if (!input.endsWith(suffix)) {
            return new None<>();
        } */
		let slice : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* 0 */, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) - suffix.length( */));
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](slice);
	}
	/* private static */ compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](input, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) -> compileClass(input, depth, state))
                .or(() -> structure(input, "interface ", "interface ", state))
                .or(() -> structure(input, "record ", "class ", state))
                .or(() -> method(state, input, depth))
                .or(() -> compileDefinitionStatement(input, depth, state))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input */)));
	}
	/* private static */ compileWhitespace(input : string, state : CompileState) : Option<[CompileState, string]> {/* if (input.isBlank()) {
            return new Some<>(new Tuple<>(state, ""));
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* private static */ method(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return /* first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var definitionTuple = parseDefinition(state, definition)
                        .map(definition1 -> {
                            var paramsTuple = compileValues(state, params, Main::compileParameter);
                            var generated = definition1.right.generateWithParams("("  */ + paramsTuple.right + /*  ")");
                            return new Tuple<>(paramsTuple.left, generated);
                        })
                        .orElseGet(() -> new Tuple<>(state, generatePlaceholder(definition)));

                var content = rawContent.strip();
                var indent = createIndent(depth);
                if (content.equals(";")) {
                    var s = indent  */ + definitionTuple.right + /*  ";";
                    return new Some<>(new Tuple<>(definitionTuple.left, s));
                }

                if (content.startsWith("{") && content.endsWith("}")) {
                    var statementsTuple = compileStatements(definitionTuple.left, content.substring(1, content.length() - 1), (state1, input1) -> compileFunctionalSegment(state1, input1, depth  */ + /*  1));
                    var generated = indent  */ + definitionTuple.right + " {" + statementsTuple.right + indent + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* new Tuple<>(statementsTuple */.left, /*  generated));
                }

                return new None<>();
            });
        } */);
	}
	/* private static */ compileFunctionalSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();/* if (stripped.isEmpty()) {
            return new Tuple<>(state, "");
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](stripped, ";", /*  s -> {
            var tuple = statementValue(state, s);
            return new Some<>(new Tuple<>(tuple.left, createIndent(depth */) + tuple.right + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* () -> {
            return new Tuple<>(state, generatePlaceholder(stripped));
        } */);
	}
	/* private static */ statementValue(state : CompileState, input : string) : [CompileState, string] {
		let stripped : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();/* if (stripped.startsWith("return ")) {
            var value = stripped.substring("return ".length());
            var tuple = value(state, value);
            return new Tuple<>(tuple.left, "return " + tuple.right);
        } */
		return /* first(stripped, "=", (s, s2) -> {
            var definitionTuple = compileDefinition(state, s);
            var valueTuple = value(definitionTuple.left, s2);
            return new Some<>(new Tuple<>(valueTuple.left, "let "  */ + definitionTuple.right + " = " + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* () -> {
            return new Tuple<>(state, generatePlaceholder(stripped));
        } */);
	}
	/* private static */ value(state : CompileState, input : string) : [CompileState, string] {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) -> symbolValue(state, input))
                .or(() -> stringValue(state, input))
                .or(() -> dataAccess(state, input))
                .or(() -> invocation(state, input))
                .orElseGet(() -> new Tuple<CompileState, String>(state, generatePlaceholder(input */)));
	}
	/* private static */ invocation(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* suffix(input.strip(), ")", withoutEnd -> {
            return first(withoutEnd, "(", (callerString, argumentsString) -> {
                var callerTuple = value(state, callerString);
                var argumentsTuple = compileValues(callerTuple.left, argumentsString, Main::value);

                return new Some<>(new Tuple<>(argumentsTuple.left, callerTuple */.left + "(" + argumentsTuple.right + /*  ")"));
            });
        }) */;
	}
	/* private static */ dataAccess(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* last(input.strip(), ".", (parent, property) -> {
            var value = value(state, parent);
            if (!isSymbol(property)) {
                return new None<>();
            }
            return new Some<>(new Tuple<>(value.left, value */.right + "." + /*  property));
        }) */;
	}
	/* private static */ stringValue(state : CompileState, input : string) : Option<[CompileState, string]> {
		let stripped : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();/* if (stripped.startsWith("\"") && stripped.endsWith("\"")) {
            return new Some<>(new Tuple<>(state, stripped));
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* private static */ symbolValue(state : CompileState, value : string) : Option<[CompileState, string]> {
		let stripped : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();/* if (isSymbol(stripped)) {
            return new Some<>(new Tuple<>(state, stripped));
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();
	}
	/* private static */ operation(state : CompileState, value : string) : Option<[CompileState, string]> {
		return /* first(value, " */ + /* ", (s, s2) -> {
            var leftTuple = value(state, s);
            var rightTuple = value(leftTuple.left, s2);
            return new Some<>(new Tuple<>(rightTuple.left, leftTuple */.right + " + " + /*  rightTuple.right));
        }) */;
	}
	/* private static */ compileValues(state : CompileState, params : string, mapper : (CompileState, string) => [CompileState, string]) : [CompileState, string] {
		let parsed : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, params, mapper);
		let generated : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](parsed.right);
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](parsed.left, generated);
	}
	/* private static */ generateValues(elements : List<string>) : string {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* Main::mergeValues */, elements);
	}
	/* private static */ parseValues(state : CompileState, input : string, mapper : (CompileState, string) => [CompileState, string]) : [CompileState, List<string>] {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, input, /*  Main::foldValueChar */, mapper);
	}
	/* private static */ compileParameter(state : CompileState, input : string) : [CompileState, string] {/* if (input.isBlank()) {
            return new Tuple<>(state, "");
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, input);
	}
	/* private static */ compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* Tuple<CompileState, Definition> tuple) -> new Tuple<>(tuple.left, tuple.right.generate()))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(input */)));
	}
	/* private static */ mergeValues(cache : StringBuilder, element : string) : StringBuilder {/* if (cache.isEmpty()) {
            return cache.append(element);
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ", ").append(element */);
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](depth);
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](), ";", /*  withoutEnd -> {
            return parseDefinition(state, withoutEnd).map(result -> {
                var generated = createIndent(depth */) + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]() + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](result.left, /*  generated);
            });
        } */);
	}
	/* private static */ parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](), " ", /*  (beforeName */, /*  name) -> {
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
        } */);
	}
	/* private static */ getStringStringTuple(beforeName : string) : Option<[string, string]> {
		let divisions : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](beforeName, /*  Main::foldTypeSeparator */);
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ).map(removed -> {
            var left = removed.left.iterate().collect(new Joiner(" ")).orElse("");
            var right = removed.right;

            return new Tuple<>(left, right);
        } */);
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : Character) : DivideState {/* if (c == ' ' && state.isLevel()) {
            return state.advance();
        } */
		let appended : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](c);/* if (c == '<') {
            return appended.enter();
        } *//* if (c == '>') {
            return appended.exit();
        } */
		return appended;
	}
	/* private static */ assembleDefinition(state : CompileState, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		let type1 : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, type);
		let node : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](beforeTypeParams, /* type1 */.right, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](), typeParams);
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* new Tuple<>(type1 */.left, /*  node) */);
	}
	/* private static */ foldValueChar(state : DivideState, c : char) : DivideState {/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } */
		let appended : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](c);/* if (c == '<') {
            return appended.enter();
        } *//* if (c == '>') {
            return appended.exit();
        } */
		return appended;
	}
	/* private static */ type(state : CompileState, input : string) : [CompileState, string] {
		let stripped : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]();/* if (stripped.equals("int")) {
            return new Tuple<>(state, "number");
        } *//* if (stripped.equals("String")) {
            return new Tuple<>(state, "string");
        } *//* if (isSymbol(stripped)) {
            return new Tuple<>(state, stripped);
        } */
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](state, CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) -> varArgs(state, input))
                .orElseGet(() -> new Tuple<>(state, generatePlaceholder(stripped */)));
	}
	/* private static */ varArgs(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* suffix(input, "...", s -> {
            var inner = type(state, s);
            return new Some<>(new Tuple<>(inner.left, inner */.right + /*  "[]"));
        }) */;
	}
	/* private static */ template(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* suffix(input.strip(), ">", withoutEnd -> {
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
                    return new Some<>(new Tuple<>(argumentsState, "["  */ + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* 0 */) + ", " + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* 1 */) + /*  "]"));
                }

                return new Some<>(new Tuple<>(argumentsState, strippedBase  */ + "<" + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](arguments) + /*  ">"));
            });
        }) */;
	}
	/* private static */ generate(arguments : List<string>, returns : string) : string {
		let joined : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* )
                .collect(new Joiner(", "))
                .orElse("" */);
		return "(" + joined + ") => " + returns;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (string, string) => Option<T>) : Option<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](input, infix, /*  Main::findLast */, mapper);
	}
	/* private static */ findLast(input : string, infix : string) : Option<Integer> {
		let index : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](infix);
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) : new Some<>(index */);
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (string, string) => Option<T>) : Option<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](input, infix, /*  Main::findFirst */, mapper);
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (string, string) => Option<Integer>, mapper : (string, string) => Option<T>) : Option<T> {
		return /* split(() -> locator.apply(input, infix).map(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index  */ + CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ));
            return new Tuple<>(left */, /*  right);
        }) */, mapper);
	}
	/* private static  */ split<T>(splitter : () => Option<[string, string]>, mapper : (string, string) => Option<T>) : Option<T> {
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* tuple -> mapper.apply(tuple.left, tuple */.right));
	}
	/* private static */ findFirst(input : string, infix : string) : Option<Integer> {
		let index : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](infix);
		return CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc](/* ) : new Some<>(index */);
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced : var = CompileState[structures=magma.Main$Lists$JVMList@73a8dfcc]("/*", "content-start")
                .replace("*/", "content-end");
		return "/* " + replaced + " */";
	}
}
/*  */
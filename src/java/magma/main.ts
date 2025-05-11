/* private */interface Option<T>/*   */ {
	map<R>(mapper : (arg0 : Symbol[input=T]) => Symbol[input=R]) : Option<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (arg0 : Symbol[input=T]) => Boolean) : Option<T>;
	orElseGet(supplier : () => Symbol[input=T]) : T;
	or(other : () => Template[base=Option, arguments=magma.Main$Lists$JVMList@ea30797]) : Option<T>;
	flatMap<R>(mapper : (arg0 : Symbol[input=T]) => Template[base=Option, arguments=magma.Main$Lists$JVMList@7e774085]) : Option<R>;
	isEmpty() : boolean;
}
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C;
	fold(current : C, element : T) : C;
}
/* private */interface Iterator<T>/*   */ {
	fold<R>(initial : R, folder : (arg0 : Symbol[input=R], arg1 : Symbol[input=T]) => Symbol[input=R]) : R;
	map<R>(mapper : (arg0 : Symbol[input=T]) => Symbol[input=R]) : Iterator<R>;
	collect<R>(collector : Collector<T, R>) : R;
	filter(predicate : (arg0 : Symbol[input=T]) => Boolean) : Iterator<T>;
	next() : Option<T>;
	flatMap<R>(f : (arg0 : Symbol[input=T]) => Template[base=Iterator, arguments=magma.Main$Lists$JVMList@1ee0005]) : Iterator<R>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T>;
	iterate() : Iterator<T>;
	removeLast() : Option<[List<T>, T]>;
	get(index : number) : T;
	size() : number;
	addAll(other : List<T>) : List<T>;
	isEmpty() : boolean;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : Iterator<[number, T]>;
}
/* private */interface Head<T>/*   */ {
	next() : Option<T>;
}
/* private */interface Argument/*  */ {
}
/* private */class Some<T>/*  */ {
	constructor (value : T) {
	}

	/* @Override
        public  */ map<R>(mapper : (arg0 : Symbol[input=T]) => Symbol[input=R]) : Option<R> {
		return new Some(mapper.apply(this.value));
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
        public */ filter(predicate : (arg0 : Symbol[input=T]) => Boolean) : Option<T> {
		/* if (predicate.test(this.value))  */{
			return this;
		}
		return new None();
	}
	/* @Override
        public */ orElseGet(supplier : () => Symbol[input=T]) : T {
		return this.value;
	}
	/* @Override
        public */ or(other : () => Template[base=Option, arguments=magma.Main$Lists$JVMList@782830e]) : Option<T> {
		return this;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : Symbol[input=T]) => Template[base=Option, arguments=magma.Main$Lists$JVMList@470e2030]) : Option<R> {
		return mapper.apply(this.value);
	}
	/* @Override
        public */ isEmpty() : boolean {
		return false;
	}
}
/* private static */class None<T>/*  */ {
	/* @Override
        public  */ map<R>(mapper : (arg0 : Symbol[input=T]) => Symbol[input=R]) : Option<R> {
		return new None();
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
        public */ filter(predicate : (arg0 : Symbol[input=T]) => Boolean) : Option<T> {
		return new None();
	}
	/* @Override
        public */ orElseGet(supplier : () => Symbol[input=T]) : T {
		return supplier.get();
	}
	/* @Override
        public */ or(other : () => Template[base=Option, arguments=magma.Main$Lists$JVMList@3fb4f649]) : Option<T> {
		return other.get();
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : Symbol[input=T]) => Template[base=Option, arguments=magma.Main$Lists$JVMList@33833882]) : Option<R> {
		return new None();
	}
	/* @Override
        public */ isEmpty() : boolean {
		return true;
	}
}
/* private static */class SingleHead<T>/*  */ {
	/* private final */ value : T;
	/* private */ retrieved : boolean;
	SingleHead(value : T) : public {
		let /* this.value  */ = value;
		let /* this.retrieved  */ = false;
	}
	/* @Override
        public */ next() : Option<T> {
		/* if (this.retrieved)  */{
			return new None();
		}
		let /* this.retrieved  */ = true;
		return new Some(this.value);
	}
}
/* private static */class EmptyHead<T>/*  */ {
	/* @Override
        public */ next() : Option<T> {
		return new None();
	}
}
/* private */class HeadedIterator<T>/*  */ {
	constructor (head : Head<T>) {
	}

	/* @Override
        public  */ fold<R>(initial : R, folder : (arg0 : Symbol[input=R], arg1 : Symbol[input=T]) => Symbol[input=R]) : R {
		let current = initial;
		/* while (true) {{
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {{
                    current = optional.orElse(null);
                }
                else  */{
			return current;/* } */
		}
	}
	/* @Override
        public  */ map<R>(mapper : (arg0 : Symbol[input=T]) => Symbol[input=R]) : Iterator<R> {
		return new HeadedIterator(() => this.head.next().map(mapper));
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return this.fold(collector.createInitial(), collector.fold);
	}
	/* @Override
        public */ filter(predicate : (arg0 : Symbol[input=T]) => Boolean) : Iterator<T> {
		return this.flatMap((element) => {
			/* if (predicate.test(element))  */{
				return new HeadedIterator(new SingleHead(element));
			}
			return new HeadedIterator(new EmptyHead());
		});
	}
	/* @Override
        public */ next() : Option<T> {
		return this.head.next();
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : Symbol[input=T]) => Template[base=Iterator, arguments=magma.Main$Lists$JVMList@4d591d15]) : Iterator<R> {
		return new HeadedIterator(new FlatMapHead(this.head, f));
	}
}
/* private static */class RangeHead/*  */ {
	/* private final */ length : number;
	/* private */ counter : number;
	RangeHead(length : number) : public {
		let /* this.length  */ = length;
	}
	/* @Override
        public */ next() : Option<number> {
		/* if (this.counter < this.length)  */{
			let value = this.counter;
			/* this.counter++ */;
			return new Some(value);
		}
		return new None();
	}
}
/* private static final */class JVMList<T>/*  */ {
	/* private final */ elements : java.util.List<T>;
	JVMList(elements : java.util.List<T>) : private {
		let /* this.elements  */ = elements;
	}
	JVMList() : public {
		/* this(new ArrayList<>()) */;
	}
	/* @Override
            public */ addLast(element : T) : List<T> {
		/* this.elements.add(element) */;
		return this;
	}
	/* @Override
            public */ iterate() : Iterator<T> {
		return this.iterateWithIndices().map(Tuple.right);
	}
	/* @Override
            public */ removeLast() : Option<[List<T>, T]> {
		/* if (this.elements.isEmpty())  */{
			return new None();
		}
		let slice = this.elements.subList(0, this.elements.size() - 1);
		let last = this.elements.getLast();
		return new Some(new [List<T>, T](new JVMList(slice), last));
	}
	/* @Override
            public */ get(index : number) : T {
		return this.elements.get(index);
	}
	/* @Override
            public */ size() : number {
		return this.elements.size();
	}
	/* @Override
            public */ addAll(other : List<T>) : List<T> {
		let initial : List<T> = this;
		return other.iterate().fold(initial, List.addLast);
	}
	/* @Override
            public */ isEmpty() : boolean {
		return this.elements.isEmpty();
	}
	/* @Override
            public */ addFirst(element : T) : List<T> {
		/* this.elements.addFirst(element) */;
		return this;
	}
	/* @Override
            public */ iterateWithIndices() : Iterator<[number, T]> {
		return new HeadedIterator(new RangeHead(this.elements.size())).map((index) => new Tuple(index, this.elements.get(index)));
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : List<T> {
		return new JVMList();
	}
	/* public static  */ of<T>(elements : T[]) : List<T> {
		return new JVMList(new ArrayList(Arrays.asList(elements)));
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
		let /* this.segments  */ = this.segments.addLast(this.buffer.toString());
		let /* this.buffer  */ = new StringBuilder();
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
	/* public */ pop() : Option<[Character, DivideState]> {
		/* if (this.index < this.input.length())  */{
			let c = this.input.charAt(this.index);
			return new Some(new Tuple(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
		}
		return new None();
	}
	/* public */ popAndAppendToTuple() : Option<[Character, DivideState]> {
		return this.pop().map((tuple) => new Tuple(tuple.left, tuple.right.append(tuple.left)));
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return this.popAndAppendToTuple().map(Tuple.right);
	}
	/* public */ peek() : char {
		return this.input.charAt(this.index);
	}
}
/* private */class Joiner/*  */ {
	constructor (delimiter : string) {
	}

	Joiner() : private {
		/* this("") */;
	}
	/* @Override
        public */ createInitial() : Option<string> {
		return new None();
	}
	/* @Override
        public */ fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current.map((inner) => inner + this.delimiter + element).orElse(element));
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return Lists.empty();
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return current.addLast(element);
	}
}
/* private */class Tuple<A, B>/* (A left, B right)  */ {
}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : Symbol[input=T]) => Template[base=Iterator, arguments=magma.Main$Lists$JVMList@5ccd43c2];
	/* private final */ head : Head<T>;
	/* private */ current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : Symbol[input=T]) => Template[base=Iterator, arguments=magma.Main$Lists$JVMList@4aa8f0b4]) : public {
		let /* this.mapper  */ = mapper;
		let /* this.current  */ = new None();
		let /* this.head  */ = head;
	}
	/* @Override
        public */ next() : Option<R> {
		/* while (true) {{
                if (this.current.isPresent()) {{
                    Iterator<R> inner = this.current.orElse(null);
                    Option<R> maybe = inner.next();
                    if (maybe.isPresent()) {{
                        return maybe;
                    }
                    else {{
                        this.current = new None<>();
                    }
                }

                Option<T> outer = this.head.next();
                if (outer.isPresent()) {{
                    this.current = outer.map(this.mapper);
                }
                else  */{
			return new None();/* } */
		}
	}
}
/* private */class Symbol/*  */ {
	constructor (input : string) {
	}

	/* @Override
        public */ generate() : string {
		return this.input;
	}
}
/* private static */class ArrayType/*  */ {
	/* private final */ right : Type;
	ArrayType(right : Type) : public {
		let /* this.right  */ = right;
	}
	/* @Override
        public */ generate() : string {
		return this.right.generate() + "[]";
	}
}
/* private static */class Whitespace/*  */ {
}
/* private static */class Iterators/*  */ {
	/* public static  */ fromOption<T>(option : Option<T>) : Iterator<T> {
		return new HeadedIterator(/* option.<Head<T>>map */(SingleHead.new).orElseGet(EmptyHead.new));
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List<Type>, returns : Type) {
	}

	/* @Override
        public */ generate() : string {
		let joined = this.arguments().iterateWithIndices().map((pair) => "arg" + pair.left + " : " + pair.right).collect(new Joiner(", ")).orElse("");
		return "(" + joined + ") => " + this.returns();
	}
}
/* private */class TupleType/*  */ {
	constructor (arguments : List<Type>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).orElse("");
		return "[" + joinedArguments + "]";
	}
}
/* private */class Template/*  */ {
	constructor (base : string, arguments : List<Type>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		return this.base + joinedArguments;
	}
}
/* public */class Main/*  */ {/* 

    private interface Type extends Argument {
        String generate();
    } */
	/* private */ CompileState(structures : List<string>) : record {
		/* public CompileState()  */{
			/* this(Lists.empty()) */;
		}
		/* public CompileState addStructure(String structure)  */{
			return new CompileState(this.structures.addLast(structure));
		}
	}
	/* private */ Definition(maybeBefore : Option<string>, type : string, name : string, typeParams : List<string>) : record {
		/* private String generate()  */{
			return this.generateWithParams("");
		}
		/* public String generateWithParams(String params) {{
            var joined = this.typeParams.iterate()
                    .collect(new Joiner())
                    .map(inner -> "<" + inner + ">")
                    .orElse("");

            var before = this.maybeBefore
                    .filter(value -> !value.isEmpty())
                    .map(Main::generatePlaceholder)
                    .map(inner -> inner + " ")
                    .orElse("");

            var s = before + this.name + joined + params;
            if (this.type.equals("var"))  */{
			return s;
			/* }
            return s + " : " + this.type */;
		}
	}
	/* public static */ main() : void {
		/* try  */{
			let parent = Paths.get(".", "src", "java", "magma");
			let source = parent.resolve("Main.java");
			let target = parent.resolve("main.ts");
			let input = Files.readString(source);
			/* Files.writeString(target, compile(input)) */;
			/* new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor() */;
		}
		/* catch (IOException | InterruptedException e)  */{
			/* throw new RuntimeException(e) */;
		}
	}
	/* private static */ compile(input : string) : string {
		let tuple = compileStatements(new CompileState(), input, Main.compileRootSegment);
		let joined = tuple.left.structures.iterate().collect(new Joiner()).orElse("");
		return joined + tuple.right;
	}
	/* private static */ compileStatements(state : CompileState, input : string, mapper : (arg0 : Symbol[input=CompileState], arg1 : String) => TupleType[arguments=magma.Main$Lists$JVMList@7960847b]) : [CompileState, string] {
		let parsed = parseStatements(state, input, mapper);
		return new Tuple(parsed.left, generateStatements(parsed.right));
	}
	/* private static */ generateStatements(statements : List<string>) : string {
		return generateAll(Main.mergeStatements, statements);
	}
	/* private static */ parseStatements(state : CompileState, input : string, mapper : (arg0 : Symbol[input=CompileState], arg1 : String) => TupleType[arguments=magma.Main$Lists$JVMList@6a6824be]) : [CompileState, List<string>] {
		return parseAll(state, input, Main.foldStatementChar, mapper);
	}
	/* private static */ generateAll(merger : (arg0 : Symbol[input=StringBuilder], arg1 : String) => Symbol[input=StringBuilder], elements : List<string>) : string {
		return elements.iterate().fold(new StringBuilder(), merger).toString();
	}
	/* private static  */ parseAll<T>(state : CompileState, input : string, folder : (arg0 : Symbol[input=DivideState], arg1 : Symbol[input=Character]) => Symbol[input=DivideState], mapper : (arg0 : Symbol[input=CompileState], arg1 : String) => TupleType[arguments=magma.Main$Lists$JVMList@5c8da962]) : [CompileState, List<T>] {
		return getCompileStateListTuple(state, input, folder, (state1,  s) => new Some(mapper.apply(state1, s))).orElseGet(() => new Tuple(state, Lists.empty()));
	}
	/* private static  */ getCompileStateListTuple<T>(state : CompileState, input : string, folder : (arg0 : Symbol[input=DivideState], arg1 : Symbol[input=Character]) => Symbol[input=DivideState], mapper : (arg0 : Symbol[input=CompileState], arg1 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@512ddf17]) : Option<[CompileState, List<T>]> {
		let initial : Option<[CompileState, List<T>]> = new Some(new Tuple(state, Lists.empty()));
		return divideAll(input, folder).iterate().fold(initial, (tuple,  element) => {
			return tuple.flatMap((inner) => {
				let state1 = inner.left;
				let right = inner.right;
				return mapper.apply(state1, element).map((applied) => {
					return new Tuple(applied.left, right.addLast(applied.right));
				});
			});
		});
	}
	/* private static */ mergeStatements(stringBuilder : StringBuilder, str : string) : StringBuilder {
		return stringBuilder.append(str);
	}
	/* private static */ divideAll(input : string, folder : (arg0 : Symbol[input=DivideState], arg1 : Symbol[input=Character]) => Symbol[input=DivideState]) : List<string> {
		let current = new DivideState(input);
		/* while (true) {{
            var maybePopped = current.pop().map(tuple -> {{
                return foldSingleQuotes(tuple)
                        .or(() -> foldDoubleQuotes(tuple))
                        .orElseGet(() -> folder.apply(tuple.right, tuple.left));
            });

            if (maybePopped.isPresent()) {{
                current = maybePopped.orElse(current);
            }
            else  */{
			/* break */;/* } */
		}
		return current.advance().segments;
	}
	/* private static */ foldDoubleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {
		/* if (tuple.left == '\"') {{
            var current = tuple.right.append(tuple.left);
            while (true) {{
                var maybePopped = current.popAndAppendToTuple();
                if (maybePopped.isEmpty()) {{
                    break;
                }

                var popped = maybePopped.orElse(null);
                current = popped.right;

                if (popped.left == '\\') {{
                    current = current.popAndAppendToOption().orElse(current);
                }
                if (popped.left == '\"')  */{
			/* break */;
			/* }
            }

            return new Some<>(current) */;
		}
		return new None();
	}
	/* private static */ foldSingleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {
		/* if (tuple.left != '\'')  */{
			return new None();
		}
		let appended = tuple.right.append(tuple.left);
		return appended.popAndAppendToTuple().map(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	/* private static */ foldEscaped(escaped : [Character, DivideState]) : DivideState {
		/* if (escaped.left == '\\')  */{
			return escaped.right.popAndAppendToOption().orElse(escaped.right);
		}
		return escaped.right;
	}
	/* private static */ foldStatementChar(state : DivideState, c : char) : DivideState {
		let append = state.append(c);
		/* if (c == ';' && append.isLevel())  */{
			return append.advance();
		}
		/* if (c == '}' && append.isShallow())  */{
			return append.advance().exit();
		}
		/* if (c == '{' || c == '(')  */{
			return append.enter();
		}
		/* if (c == '}' || c == ')')  */{
			return append.exit();
		}
		return append;
	}
	/* private static */ compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input.strip();
		/* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */{
			return new Tuple(state, "");
		}
		return compileClass(stripped, 0, state).orElseGet(() => new Tuple(state, generatePlaceholder(stripped)));
	}
	/* private static */ compileClass(stripped : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return structure(stripped, "class ", "class ", state);
	}
	/* private static */ structure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, string]> {
		return first(stripped, sourceInfix, (beforeInfix,  right) => {
			return first(right, "{", (beforeContent,  withEnd) => {
				let strippedWithEnd = withEnd.strip();
				return suffix(strippedWithEnd, "}", (content1) => {
					return first(beforeContent, " implements ", (s,  s2) => {
						return structureWithMaybeParams(targetInfix, state, beforeInfix, s, content1);
					}).or(() => {
						return structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1);
					});
				});
			});
		});
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return suffix(beforeContent, ")", (s) => {
			return first(s, "(", (s1,  s2) => {
				let parsed = parseParameters(state, s2);
				return getOred(targetInfix, parsed.left, beforeInfix, s1, content1, parsed.right);
			});
		}).or(() => {
			return getOred(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
		});
	}
	/* private static */ getOred(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<string>) : Option<[CompileState, string]> {
		return first(beforeContent, "<", (name,  withTypeParams) => {
			return first(withTypeParams, ">", (typeParamsString,  afterTypeParams) => {
				let /* final */ compileStateStringTupleBiFunction : (arg0 : Symbol[input=CompileState], arg1 : String) => TupleType[arguments=magma.Main$Lists$JVMList@2c13da15] = (state1,  s) => new Tuple(state1, s.strip());
				let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1,  s) => new Some(compileStateStringTupleBiFunction.apply(state1, s)));
				return assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams, params);
			});
		}).or(() => {
			return assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "", params);
		});
	}
	/* private static */ assemble(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string, params : List<string>) : Option<[CompileState, string]> {
		let name = rawName.strip();
		/* if (!isSymbol(name))  */{
			return new None();
		}
		let joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		let parsed = parseStatements(state, content, (state0,  input) => compileClassSegment(state0, input, 1));
		/* List<String> parsed1 */;
		/* if (params.isEmpty())  */{
			let /* parsed1  */ = parsed.right;
		}
		/* else  */{
			let joined = params.iterate().collect(new Joiner(", ")).orElse("");
			let constructorIndent = createIndent(1);
			let /* parsed1  */ = parsed.right.addFirst(constructorIndent + "constructor (" + joined + ") {" + constructorIndent + "}\n");
		}
		let parsed2 = parsed1.iterate().collect(new Joiner()).orElse("");
		let generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + parsed2 + "\n}\n";
		return new Some(new Tuple(parsed.left.addStructure(generated), ""));
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) {{
            var c = input.charAt(i);
            if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */{
			/* continue */;
			/* }
            return false */;
		}
		return true;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@77556fd]) : Option<T> {
		/* if (!input.endsWith(suffix))  */{
			return new None();
		}
		let slice = input.substring(0, input.length() - suffix.length());
		return mapper.apply(slice);
	}
	/* private static */ compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return compileWhitespace(input, state).or(() => compileClass(input, depth, state)).or(() => structure(input, "interface ", "interface ", state)).or(() => structure(input, "record ", "class ", state)).or(() => method(state, input, depth)).or(() => compileDefinitionStatement(input, depth, state)).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
	}
	/* private static */ compileWhitespace(input : string, state : CompileState) : Option<[CompileState, string]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple(state, ""));
		}
		return new None();
	}
	/* private static */ method(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return first(input, "(", (definition,  withParams) => {
			return first(withParams, ")", (params,  rawContent) => {
				let definitionTuple = parseDefinition(state, definition).map((definition1) => {
					let paramsTuple = compileParameters(state, params);
					let generated = definition1.right.generateWithParams("(" + paramsTuple.right + ")");
					return new Tuple(paramsTuple.left, generated);
				}).orElseGet(() => new Tuple(state, generatePlaceholder(definition)));
				let content = rawContent.strip();
				let indent = createIndent(depth);
				/* if (content.equals(";"))  */{
					let s = indent + definitionTuple.right + ";";
					return new Some(new Tuple(definitionTuple.left, s));
				}
				/* if (content.startsWith("{") && content.endsWith("}"))  */{
					let substring = content.substring(1, content.length() - 1);
					let statementsTuple = compileFunctionSegments(definitionTuple.left, substring, depth);
					let generated = indent + definitionTuple.right + " {" + statementsTuple.right + indent + "}";
					return new Some(new Tuple(statementsTuple.left, generated));
				}
				return new None();
			});
		});
	}
	/* private static */ compileParameters(state : CompileState, params : string) : [CompileState, string] {
		let parsed = parseParameters(state, params);
		let generated = generateValues(parsed.right);
		return new Tuple(parsed.left, generated);
	}
	/* private static */ parseParameters(state : CompileState, params : string) : [CompileState, List<string>] {
		return parseValuesOrEmpty(state, params, (state1,  s) => new Some(compileParameter(state1, s)));
	}
	/* private static */ compileFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return compileStatements(state, input, (state1,  input1) => compileFunctionSegment(state1, input1, depth + 1));
	}
	/* private static */ compileFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input.strip();
		/* if (stripped.isEmpty())  */{
			return new Tuple(state, "");
		}
		return suffix(stripped, ";", (s) => {
			let tuple = statementValue(state, s, depth);
			return new Some(new Tuple(tuple.left, createIndent(depth) + tuple.right + ";"));
		}).or(() => {
			return block(state, depth, stripped);
		}).orElseGet(() => {
			return new Tuple(state, generatePlaceholder(stripped));
		});
	}
	/* private static */ block(state : CompileState, depth : number, stripped : string) : Option<[CompileState, string]> {
		return suffix(stripped, "}", (withoutEnd) => {
			return split(() => {
				return toLast(withoutEnd, "{", Main.foldBlockStart);
			}, (beforeContent,  content) => {
				return suffix(beforeContent, "{", (s) => {
					let compiled = compileFunctionSegments(state, content, depth);
					let indent = createIndent(depth);
					return new Some(new Tuple(compiled.left, indent + generatePlaceholder(s) + "{" + compiled.right + indent + "}"));
				});
			});
		});
	}
	/* private static */ foldBlockStart(state : DivideState, c : Character) : DivideState {
		let appended = state.append(c);
		/* if (c == '{')  */{
			return appended.advance();
		}
		return appended;
	}
	/* private static */ statementValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input.strip();
		/* if (stripped.startsWith("return "))  */{
			let value = stripped.substring("return ".length());
			let tuple = value(state, value, depth);
			return new Tuple(tuple.left, "return " + tuple.right);
		}
		return first(stripped, "=", (s,  s2) => {
			let definitionTuple = compileDefinition(state, s);
			let valueTuple = value(definitionTuple.left, s2, depth);
			return new Some(new Tuple(valueTuple.left, "let " + definitionTuple.right + " = " + valueTuple.right));
		}).orElseGet(() => {
			return new Tuple(state, generatePlaceholder(stripped));
		});
	}
	/* private static */ value(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return lambda(state, input, depth).or(() => stringValue(state, input)).or(() => dataAccess(state, input, depth)).or(() => symbolValue(state, input)).or(() => invocation(state, input, depth)).or(() => operation(state, input, depth, "+")).or(() => operation(state, input, depth, "-")).or(() => digits(state, input)).or(() => not(state, input, depth)).or(() => methodReference(state, input, depth)).orElseGet(() => new [CompileState, string](state, generatePlaceholder(input)));
	}
	/* private static */ methodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return last(input, "::", (s,  s2) => {
			let value = value(state, s, depth);
			return new Some(new Tuple(value.left, value.right + "." + s2));
		});
	}
	/* private static */ not(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		let stripped = input.strip();
		/* if (stripped.startsWith("!"))  */{
			let slice = stripped.substring(1);
			let value = value(state, slice, depth);
			return new Some(new Tuple(value.left, "!" + value.right));
		}
		return new None();
	}
	/* private static */ lambda(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return first(input, "->", (beforeArrow,  valueString) => {
			let strippedBeforeArrow = beforeArrow.strip();
			/* if (isSymbol(strippedBeforeArrow))  */{
				return assembleLambda(state, Lists.of(strippedBeforeArrow), valueString, depth);
			}
			/* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */{
				let parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main.foldValueChar);
				return assembleLambda(state, parameterNames, valueString, depth);
			}
			return new None();
		});
	}
	/* private static */ assembleLambda(state : CompileState, paramNames : List<string>, valueString : string, depth : number) : Some<[CompileState, string]> {
		/* Tuple<CompileState, String> value */;
		let strippedValueString = valueString.strip();
		/* String s */;
		/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */{
			let /* value  */ = compileFunctionSegments(state, strippedValueString.substring(1, strippedValueString.length() - 1), depth);
			let /* s  */ = "{" + value.right + createIndent(depth) + "}";
		}
		/* else  */{
			let /* value  */ = value(state, strippedValueString, depth);
			let /* s  */ = value.right;
		}
		let joined = paramNames.iterate().collect(new Joiner(", ")).orElse("");
		return new Some(new Tuple(value.left, "(" + joined + ") => " + s));
	}
	/* private static */ digits(state : CompileState, input : string) : Option<[CompileState, string]> {
		let stripped = input.strip();
		/* if (isNumber(stripped))  */{
			return new Some(new Tuple(state, stripped));
		}
		return new None();
	}
	/* private static */ isNumber(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++) {{
            var c = input.charAt(i);
            if (Character.isDigit(c))  */{
			/* continue */;
			/* }
            return false */;
		}
		return true;
	}
	/* private static */ invocation(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return suffix(input.strip(), ")", (withoutEnd) => {
			return split(() => toLast(withoutEnd, "", Main.foldInvocationStart), (callerWithEnd,  argumentsString) => {
				return suffix(callerWithEnd, "(", (callerString) => {
					let callerString1 = callerString.strip();
					let callerTuple = invocationHeader(state, depth, callerString1);
					let argumentsTuple = compileValues(callerTuple.left, argumentsString, (state1,  input1) => value(state1, input1, depth));
					return new Some(new Tuple(argumentsTuple.left, callerTuple.right + "(" + argumentsTuple.right + ")"));
				});
			});
		});
	}
	/* private static */ invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, string] {
		/* if (callerString1.startsWith("new ")) {{
            String input1 = callerString1.substring("new ".length());
            var map = compileType(state, input1).map(type -> {{
                return new Tuple<>(type.left, "new " + type.right);
            });

            if (map.isPresent())  */{
			return map.orElse(null);/* } */
		}
		return value(state, callerString1, depth);
	}
	/* private static */ foldInvocationStart(state : DivideState, c : char) : DivideState {
		let appended = state.append(c);
		/* if (c == '(') {{
            var enter = appended.enter();
            if (enter.isShallow())  */{
			return enter.advance();
			/* }
            return enter */;
		}
		/* if (c == ')')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ dataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return last(input.strip(), ".", (parent,  property) => {
			/* if (!isSymbol(property))  */{
				return new None();
			}
			let value = value(state, parent, depth);
			return new Some(new Tuple(value.left, value.right + "." + property));
		});
	}
	/* private static */ stringValue(state : CompileState, input : string) : Option<[CompileState, string]> {
		let stripped = input.strip();
		/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
			return new Some(new Tuple(state, stripped));
		}
		return new None();
	}
	/* private static */ symbolValue(state : CompileState, value : string) : Option<[CompileState, string]> {
		let stripped = value.strip();
		/* if (isSymbol(stripped))  */{
			return new Some(new Tuple(state, stripped));
		}
		return new None();
	}
	/* private static */ operation(state : CompileState, value : string, depth : number, infix : string) : Option<[CompileState, string]> {
		return first(value, infix, (s,  s2) => {
			let leftTuple = value(state, s, depth);
			let rightTuple = value(leftTuple.left, s2, depth);
			return new Some(new Tuple(rightTuple.left, leftTuple.right + " " + infix + " " + rightTuple.right));
		});
	}
	/* private static */ compileValues(state : CompileState, params : string, mapper : (arg0 : Symbol[input=CompileState], arg1 : String) => TupleType[arguments=magma.Main$Lists$JVMList@368239c8]) : [CompileState, string] {
		let parsed = parseValuesOrEmpty(state, params, (state1,  s) => new Some(mapper.apply(state1, s)));
		let generated = generateValues(parsed.right);
		return new Tuple(parsed.left, generated);
	}
	/* private static */ generateValues(elements : List<string>) : string {
		return generateAll(Main.mergeValues, elements);
	}
	/* private static  */ parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : Symbol[input=CompileState], arg1 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@9e89d68]) : [CompileState, List<T>] {
		return parseValues(state, input, mapper).orElseGet(() => new Tuple(state, Lists.empty()));
	}
	/* private static  */ parseValues<T>(state : CompileState, input : string, mapper : (arg0 : Symbol[input=CompileState], arg1 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@3b192d32]) : Option<[CompileState, List<T>]> {
		return getCompileStateListTuple(state, input, Main.foldValueChar, mapper);
	}
	/* private static */ compileParameter(state : CompileState, input : string) : [CompileState, string] {
		/* if (input.isBlank())  */{
			return new Tuple(state, "");
		}
		return compileDefinition(state, input);
	}
	/* private static */ compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return parseDefinition(state, input).map((tuple) => new Tuple(tuple.left, tuple.right.generate())).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
	}
	/* private static */ mergeValues(cache : StringBuilder, element : string) : StringBuilder {
		/* if (cache.isEmpty())  */{
			return cache.append(element);
		}
		return cache.append(", ").append(element);
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat(depth);
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return suffix(input.strip(), ";", (withoutEnd) => {
			return parseDefinition(state, withoutEnd).map((result) => {
				let generated = createIndent(depth) + result.right.generate() + ";";
				return new Tuple(result.left, generated);
			});
		});
	}
	/* private static */ parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return last(input.strip(), " ", (beforeName,  name) => {
			return split(() => toLast(beforeName, " ", Main.foldTypeSeparator), (beforeType,  type) => {
				return suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
					return first(withoutTypeParamStart, "<", (beforeTypeParams,  typeParamsString) => {
						let /* final */ compileStateStringTupleBiFunction : (arg0 : Symbol[input=CompileState], arg1 : String) => TupleType[arguments=magma.Main$Lists$JVMList@16f65612] = (state1,  s) => new Tuple(state1, s.strip());
						let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1,  s) => new Some(compileStateStringTupleBiFunction.apply(state1, s)));
						return assembleDefinition(typeParams.left, new Some<string>(beforeTypeParams), name, typeParams.right, type);
					});
				}).or(() => {
					return assembleDefinition(state, new Some<string>(beforeType), name, Lists.empty(), type);
				});
			}).or(() => {
				return assembleDefinition(state, new None<string>(), name, Lists.empty(), beforeName);
			});
		});
	}
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : Symbol[input=DivideState], arg1 : Symbol[input=Character]) => Symbol[input=DivideState]) : Option<[string, string]> {
		let divisions = divideAll(input, folder);
		return divisions.removeLast().map((removed) => {
			let left = removed.left.iterate().collect(new Joiner(separator)).orElse("");
			let right = removed.right;
			return new Tuple(left, right);
		});
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : Character) : DivideState {
		/* if (c == ' ' && state.isLevel())  */{
			return state.advance();
		}
		let appended = state.append(c);
		/* if (c == '<')  */{
			return appended.enter();
		}
		/* if (c == '>')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ assembleDefinition(state : CompileState, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		let type1 = typeOrPlaceholder(state, type);
		let node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
		return new Some(new Tuple(type1.left, node));
	}
	/* private static */ foldValueChar(state : DivideState, c : char) : DivideState {
		/* if (c == ',' && state.isLevel())  */{
			return state.advance();
		}
		let appended = state.append(c);
		/* if (c == '-') {{
            var peeked = appended.peek();
            if (peeked == '>') {{
                return appended.popAndAppendToOption().orElse(appended);
            }
            else  */{
			return appended;/* } */
		}
		/* if (c == '<' || c == '(' || c == '{')  */{
			return appended.enter();
		}
		/* if (c == '>' || c == ')' || c == '}')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ typeOrPlaceholder(state : CompileState, input : string) : [CompileState, string] {
		return compileType(state, input).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
	}
	/* private static */ compileType(state : CompileState, input : string) : Option<[CompileState, string]> {
		return type(state, input).map((tuple) => new Tuple(tuple.left, tuple.right.generate()));
	}
	/* private static */ type(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input.strip();
		/* if (stripped.equals("int") || stripped.equals("Integer"))  */{
			return new Some(new Tuple(state, Primitive.Int));
		}
		/* if (stripped.equals("String"))  */{
			return new Some(new Tuple(state, Primitive.String));
		}
		/* if (isSymbol(stripped))  */{
			return new Some(new Tuple(state, new Symbol(stripped)));
		}
		return template(state, input).or(() => varArgs(state, input));
	}
	/* private static */ varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix(input, "...", (s) => {
			return type(state, s).map((inner) => {
				let newState = inner.left;
				let child = inner.right;
				return new Tuple(newState, new ArrayType(child));
			});
		});
	}
	/* private static */ template(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix(input.strip(), ">", (withoutEnd) => {
			return first(withoutEnd, "<", (base,  argumentsString) => {
				let strippedBase = base.strip();
				return parseValues(state, argumentsString, Main.argument).map((argumentsTuple) => {
					return assembleTemplate(base, strippedBase, argumentsTuple.left, argumentsTuple.right);
				});
			});
		});
	}
	/* private static */ assembleTemplate(base : string, strippedBase : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children = arguments.iterate().map(Main.retainType).flatMap(Iterators.fromOption).collect(new ListCollector());
		/* if (base.equals("BiFunction"))  */{
			return new Tuple(state, new FunctionType(Lists.of(children.get(0), children.get(1)), children.get(2)));
		}
		/* if (base.equals("Function"))  */{
			return new Tuple(state, new FunctionType(Lists.of(children.get(0)), children.get(1)));
		}
		/* if (base.equals("Predicate"))  */{
			return new Tuple(state, new FunctionType(Lists.of(children.get(0)), Primitive.Boolean));
		}
		/* if (base.equals("Supplier"))  */{
			return new Tuple(state, new FunctionType(Lists.empty(), children.get(0)));
		}
		/* if (base.equals("Tuple") && children.size() >= 2)  */{
			return new Tuple(state, new TupleType(children));
		}
		return new Tuple(state, new Template(strippedBase, children));
	}
	/* private static */ retainType(argument : Argument) : Option<Type> {
		/* if (argument instanceof Type type)  */{
			return new Some(type);
		}
		/* else  */{
			return new None<Type>();
		}
	}
	/* private static */ argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple(state, new Whitespace()));
		}
		return type(state, input).map((tuple) => new Tuple(tuple.left, tuple.right));
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : String, arg1 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@311d617d]) : Option<T> {
		return infix(input, infix, Main.findLast, mapper);
	}
	/* private static */ findLast(input : string, infix : string) : Option<number> {
		let index = input.lastIndexOf(infix);
		/* if (index == -1)  */{
			return new None<number>();
		}
		return new Some(index);
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : String, arg1 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@7c53a9eb]) : Option<T> {
		return infix(input, infix, Main.findFirst, mapper);
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : String, arg1 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@ed17bee], mapper : (arg0 : String, arg1 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@2a33fae0]) : Option<T> {
		return split(() => locator.apply(input, infix).map((index) => {
			let left = input.substring(0, index);
			let right = input.substring(index + infix.length());
			return new Tuple(left, right);
		}), mapper);
	}
	/* private static  */ split<T>(splitter : () => Template[base=Option, arguments=magma.Main$Lists$JVMList@707f7052], mapper : (arg0 : String, arg1 : String) => Template[base=Option, arguments=magma.Main$Lists$JVMList@11028347]) : Option<T> {
		return splitter.get().flatMap((tuple) => mapper.apply(tuple.left, tuple.right));
	}
	/* private static */ findFirst(input : string, infix : string) : Option<number> {
		let index = input.indexOf(infix);
		/* if (index == -1)  */{
			return new None<number>();
		}
		return new Some(index);
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
		return "/* " + replaced + " */";
	}/* 

    private enum Primitive implements Type {
        Int("number"),
        String("string"),
        Boolean("boolean");

        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String generate() {
            return this.value;
        }
    } */
}
/*  */
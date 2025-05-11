/* private */interface Option<T>/*   */ {
	map<R>(mapper : (arg0 : T) => R) : Option<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (arg0 : T) => boolean) : Option<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => Option<T>) : Option<T>;
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R>;
	isEmpty() : boolean;
}
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C;
	fold(current : C, element : T) : C;
}
/* private */interface Iterator<T>/*   */ {
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R;
	map<R>(mapper : (arg0 : T) => R) : Iterator<R>;
	collect<R>(collector : Collector<T, R>) : R;
	filter(predicate : (arg0 : T) => boolean) : Iterator<T>;
	next() : Option<T>;
	flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T>;
	iterate() : Iterator<T>;
	removeLast() : Option<[List<T>, T]>;
	get(index : number) : T;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : Iterator<[number, T]>;
	removeFirst() : Option<[T, List<T>]>;
}
/* private */interface Head<T>/*   */ {
	next() : Option<T>;
}
/* private */interface Argument/*  */ {
}
/* private */interface Parameter/*  */ {
}
/* private */interface LambdaValue/*  */ {
	generate() : string;
}
/* private */interface Caller/*  */ {
	generate() : string;
}
/* private */class Some<T>/*  */ {
	constructor (value : T) {
	}

	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper(this.value));
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
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		/* if (predicate.test(this.value))  */{
			return this;
		}
		return new None();
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
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper(this.value);
	}
	/* @Override
        public */ isEmpty() : boolean {
		return false;
	}
}
/* private static */class None<T>/*  */ {
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
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
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new None();
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return supplier();
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return other();
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
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
        public  */ fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current = initial;
		/* while (true)  */{
			let finalCurrent : R = current;
			let optional = this.head.next().map((inner : unknown) => folder(finalCurrent, inner));
			/* if (optional.isPresent())  */{
				let /* current  */ = optional.orElse(null);
			}
			/* else  */{
				return current;
			}
		}
	}
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Iterator<R> {
		return new HeadedIterator(() => this.head.next().map(mapper));
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return this.fold(collector.createInitial(), collector.fold);
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return this.flatMap((element : unknown) => {
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
        public  */ flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
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
		return new HeadedIterator(new RangeHead(this.elements.size())).map((index : unknown) => new Tuple(index, this.elements.get(index)));
	}
	/* @Override
            public */ removeFirst() : Option<[T, List<T>]> {
		/* if (this.elements.isEmpty())  */{
			return new None();
		}
		let first = this.elements.getFirst();
		let slice = this.elements.subList(1, this.elements.size());
		return new Some(new [T, List<T>](first, new JVMList(slice)));
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
		return this.depth == 0;
	}
	/* public */ exit() : DivideState {
		/* this.depth-- */;
		return this;
	}
	/* public */ isShallow() : boolean {
		return this.depth == 1;
	}
	/* public */ pop() : Option<[Character, DivideState]> {
		/* if (this.index < this.input.length())  */{
			let c = this.input.charAt(this.index);
			return new Some(new Tuple(c, new DivideState(this.input, this.index + 1, this.segments, this.buffer, this.depth)));
		}
		return new None();
	}
	/* public */ popAndAppendToTuple() : Option<[Character, DivideState]> {
		return this.pop().map((tuple : unknown) => new Tuple(tuple.left, tuple.right.append(tuple.left)));
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
		return new Some(current.map((inner : unknown) => inner + this.delimiter + element).orElse(element));
	}
}
/* private */class Definition/*  */ {
	constructor (maybeBefore : Option<string>, type : Type, name : string, typeParams : List<string>) {
	}

	/* private */ generate() : string {
		return this.generateWithParams("");
	}
	/* public */ generateWithParams(params : string) : string {
		let joined = this.joinTypeParams();
		let before = this.joinBefore();
		let typeString = this.generateType();
		return before + this.name + joined + params + typeString;
	}
	/* private */ generateType() : string {
		/* if (this.type.equals(Primitive.Var))  */{
			return "";
		}
		return " : " + this.type.generate();
	}
	/* private */ joinBefore() : string {
		return this.maybeBefore.filter((value : unknown) => !value.isEmpty()).map(Main.generatePlaceholder).map((inner : unknown) => inner + " ").orElse("");
	}
	/* private */ joinTypeParams() : string {
		return this.typeParams.iterate().collect(new Joiner()).map((inner : unknown) => "<" + inner + ">").orElse("");
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
	/* private final */ mapper : (arg0 : T) => Iterator<R>;
	/* private final */ head : Head<T>;
	/* private */ current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>) : public {
		let /* this.mapper  */ = mapper;
		let /* this.current  */ = new None();
		let /* this.head  */ = head;
	}
	/* @Override
        public */ next() : Option<R> {
		/* while (true)  */{
			/* if (this.current.isPresent())  */{
				let inner : Iterator<R> = this.current.orElse(null);
				let maybe : Option<R> = inner.next();
				/* if (maybe.isPresent())  */{
					return maybe;
				}
				/* else  */{
					let /* this.current  */ = new None();
				}
			}
			let outer : Option<T> = this.head.next();
			/* if (outer.isPresent())  */{
				let /* this.current  */ = outer.map(this.mapper);
			}
			/* else  */{
				return new None();
			}
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
		let single : Option<Head<T>> = option.map(SingleHead.new);
		return new HeadedIterator(single.orElseGet(EmptyHead.new));
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List<Type>, returns : Type) {
	}

	/* @Override
        public */ generate() : string {
		let joined = this.arguments().iterateWithIndices().map((pair : unknown) => "arg" + pair.left + " : " + pair.right.generate()).collect(new Joiner(", ")).orElse("");
		return "(" + joined + ") => " + this.returns.generate();
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
		let joinedArguments = this.arguments.iterate().map(Type.generate).collect(new Joiner(", ")).map((inner : unknown) => "<" + inner + ">").orElse("");
		return this.base + joinedArguments;
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}

	/* @Override
        public */ generate() : string {
		return this.input;
	}
}
/* private */class StringValue/*  */ {
	constructor (stripped : string) {
	}

	/* @Override
        public */ generate() : string {
		return this.stripped;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : Value, property : string) {
	}

	/* @Override
        public */ generate() : string {
		return this.parent.generate() + "." + this.property;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (value : string) {
	}

	/* @Override
        public */ generate() : string {
		return this.value;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (right : Type) {
	}

	/* @Override
        public */ generate() : string {
		return "new " + this.right.generate();
	}
}
/* private */class Operation/*  */ {
	constructor (left : Value, infix : string, right : Value) {
	}

	/* @Override
        public */ generate() : string {
		return this.left.generate() + " " + this.infix + " " + this.right.generate();
	}
}
/* private */class Not/*  */ {
	constructor (value : Value) {
	}

	/* @Override
        public */ generate() : string {
		return "!" + this.value.generate();
	}
}
/* private */class BlockLambdaValue/*  */ {
	constructor (right : string, depth : number) {
	}

	/* @Override
        public */ generate() : string {
		return "{" + this.right + createIndent(this.depth) + "}";
	}
}
/* private */class Lambda/*  */ {
	constructor (parameterNames : List<string>, body : LambdaValue) {
	}

	/* @Override
        public */ generate() : string {
		let joined = this.parameterNames.iterate().map((inner : unknown) => inner + " : unknown").collect(new Joiner(", ")).orElse("");
		return "(" + joined + ") => " + this.body.generate();
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : Caller, arguments : List<Value>) {
	}

	/* @Override
        public */ generate() : string {
		let joined = this.arguments.iterate().map(Value.generate).collect(new Joiner(", ")).orElse("");
		return this.caller.generate() + "(" + joined + ")";
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : Value, child : Value) {
	}

	/* @Override
        public */ generate() : string {
		return this.parent.generate() + "[" + this.child.generate() + "]";
	}
}
/* public */class Main/*  */ {/* 

    private interface Type extends Argument {
        String generate();
    } *//* 

    private sealed interface Value extends LambdaValue, Caller {
        String generate();
    } */
	/* private */ CompileState(structures : List<string>, definitions : List<Definition>) : record {
		/* public CompileState()  */{
			/* this(Lists.empty(), Lists.empty()) */;
		}
		/* private Option<Type> resolve(String name)  */{
			return this.definitions.iterate().filter((definition : unknown) => definition.name.equals(name)).next().map(Definition.type);
		}
		/* public CompileState addStructure(String structure)  */{
			return new CompileState(this.structures.addLast(structure), this.definitions);
		}
		/* public CompileState withDefinitions(List<Definition> definitions)  */{
			return new CompileState(this.structures, definitions);
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
	/* private static */ compileStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed = parseStatements(state, input, mapper);
		return new Tuple(parsed.left, generateStatements(parsed.right));
	}
	/* private static */ generateStatements(statements : List<string>) : string {
		return generateAll(Main.mergeStatements, statements);
	}
	/* private static */ parseStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, List<string>] {
		return parseAll(state, input, Main.foldStatementChar, mapper);
	}
	/* private static */ generateAll(merger : (arg0 : StringBuilder, arg1 : string) => StringBuilder, elements : List<string>) : string {
		return elements.iterate().fold(new StringBuilder(), merger).toString();
	}
	/* private static  */ parseAll<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return getCompileStateListTuple(state, input, folder, (state1 : unknown, s : unknown) => new Some(mapper(state1, s))).orElseGet(() => new Tuple(state, Lists.empty()));
	}
	/* private static  */ getCompileStateListTuple<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let initial : Option<[CompileState, List<T>]> = new Some(new Tuple(state, Lists.empty()));
		return divideAll(input, folder).iterate().fold(initial, (tuple : unknown, element : unknown) => {
			return tuple.flatMap((inner : unknown) => {
				let state1 = inner.left;
				let right = inner.right;
				return mapper(state1, element).map((applied : unknown) => {
					return new Tuple(applied.left, right.addLast(applied.right));
				});
			});
		});
	}
	/* private static */ mergeStatements(stringBuilder : StringBuilder, str : string) : StringBuilder {
		return stringBuilder.append(str);
	}
	/* private static */ divideAll(input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState) : List<string> {
		let current = new DivideState(input);
		/* while (true)  */{
			let maybePopped = current.pop().map((tuple : unknown) => {
				return foldSingleQuotes(tuple).or(() => foldDoubleQuotes(tuple)).orElseGet(() => folder(tuple.right, tuple.left));
			});
			/* if (maybePopped.isPresent())  */{
				let /* current  */ = maybePopped.orElse(current);
			}
			/* else  */{
				/* break */;
			}
		}
		return current.advance().segments;
	}
	/* private static */ foldDoubleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {
		/* if (tuple.left == '\"')  */{
			let current = tuple[1].append(tuple[0]);
			/* while (true)  */{
				let maybePopped = current.popAndAppendToTuple();
				/* if (maybePopped.isEmpty())  */{
					/* break */;
				}
				let popped = maybePopped.orElse(null);
				let /* current  */ = popped.right;
				/* if (popped.left == '\\')  */{
					let /* current  */ = current.popAndAppendToOption().orElse(current);
				}
				/* if (popped.left == '\"')  */{
					/* break */;
				}
			}
			return new Some(current);
		}
		return new None();
	}
	/* private static */ foldSingleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {
		/* if (tuple.left != '\'')  */{
			return new None();
		}
		let appended = tuple[1].append(tuple[0]);
		return appended.popAndAppendToTuple().map(Main.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	/* private static */ foldEscaped(escaped : [Character, DivideState]) : DivideState {
		/* if (escaped.left == '\\')  */{
			return escaped[1].popAndAppendToOption().orElse(escaped[1]);
		}
		return escaped[1];
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
		return first(stripped, sourceInfix, (beforeInfix : unknown, right : unknown) => {
			return first(right, "{", (beforeContent : unknown, withEnd : unknown) => {
				let strippedWithEnd = withEnd.strip();
				return suffix(strippedWithEnd, "}", (content1 : unknown) => {
					return first(beforeContent, " implements ", (s : unknown, s2 : unknown) => {
						return structureWithMaybeParams(targetInfix, state, beforeInfix, s, content1);
					}).or(() => {
						return structureWithMaybeParams(targetInfix, state, beforeInfix, beforeContent, content1);
					});
				});
			});
		});
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return suffix(beforeContent, ")", (s : unknown) => {
			return first(s, "(", (s1 : unknown, s2 : unknown) => {
				let parsed = parseParameters(state, s2);
				return getOred(targetInfix, parsed.left, beforeInfix, s1, content1, parsed.right);
			});
		}).or(() => {
			return getOred(targetInfix, state, beforeInfix, beforeContent, content1, Lists.empty());
		});
	}
	/* private static */ getOred(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>) : Option<[CompileState, string]> {
		return first(beforeContent, "<", (name : unknown, withTypeParams : unknown) => {
			return first(withTypeParams, ">", (typeParamsString : unknown, afterTypeParams : unknown) => {
				let /* final */ compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1 : unknown, s : unknown) => new Tuple(state1, s.strip());
				let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1 : unknown, s : unknown) => new Some(compileStateStringTupleBiFunction.apply(state1, s)));
				return assemble(typeParams.left, targetInfix, beforeInfix, name, content1, typeParams.right, afterTypeParams, params);
			});
		}).or(() => {
			return assemble(state, targetInfix, beforeInfix, beforeContent, content1, Lists.empty(), "", params);
		});
	}
	/* private static */ assemble(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string, params : List<Parameter>) : Option<[CompileState, string]> {
		let name = rawName.strip();
		/* if (!isSymbol(name))  */{
			return new None();
		}
		let joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map((inner : unknown) => "<" + inner + ">").orElse("");
		let parsed = parseStatements(state, content, (state0 : unknown, input : unknown) => compileClassSegment(state0, input, 1));
		/* List<String> parsed1 */;
		/* if (params.isEmpty())  */{
			let /* parsed1  */ = parsed.right;
		}
		/* else  */{
			let joined = joinValues(retainDefinitions(params));
			let constructorIndent = createIndent(1);
			let /* parsed1  */ = parsed.right.addFirst(constructorIndent + "constructor (" + joined + ") {" + constructorIndent + "}\n");
		}
		let parsed2 = parsed1.iterate().collect(new Joiner()).orElse("");
		let generated = generatePlaceholder(beforeInfix.strip()) + targetInfix + name + joinedTypeParams + generatePlaceholder(afterTypeParams) + " {" + parsed2 + "\n}\n";
		return new Some(new Tuple(parsed.left.addStructure(generated), ""));
	}
	/* private static */ retainDefinition(parameter : Parameter) : Option<Definition> {
		/* if (parameter instanceof Definition definition)  */{
			return new Some(definition);
		}
		return new None();
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c = input.charAt(i);
			/* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */{
				/* continue */;
			}
			return false;
		}
		return true;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		/* if (!input.endsWith(suffix))  */{
			return new None();
		}
		let slice = input.substring(0, input.length() - suffix.length());
		return mapper(slice);
	}
	/* private static */ compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return compileWhitespace(input, state).or(() => compileClass(input, depth, state)).or(() => structure(input, "interface ", "interface ", state)).or(() => structure(input, "record ", "class ", state)).or(() => compileMethod(state, input, depth)).or(() => compileDefinitionStatement(input, depth, state)).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
	}
	/* private static */ compileWhitespace(input : string, state : CompileState) : Option<[CompileState, string]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple(state, ""));
		}
		return new None();
	}
	/* private static */ compileMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return first(input, "(", (definitionString : unknown, withParams : unknown) => {
			return first(withParams, ")", (parametersString : unknown, rawContent : unknown) => {
				return parseDefinition(state, definitionString).flatMap((definitionTuple : unknown) => {
					let definitionState = definitionTuple.left;
					let definition = definitionTuple.right;
					let parametersTuple = parseParameters(definitionState, parametersString);
					let parameters = parametersTuple.right;
					let definitions = retainDefinitions(parameters);
					let joinedParameters = joinValues(definitions);
					let content = rawContent.strip();
					let indent = createIndent(depth);
					let generatedHeader = definition.generateWithParams("(" + joinedParameters + ")");
					/* if (content.equals(";"))  */{
						return new Some(new Tuple(parametersTuple.left, indent + generatedHeader + ";"));
					}
					/* if (content.startsWith("{") && content.endsWith("}"))  */{
						let substring = content.substring(1, content.length() - 1);
						let statementsTuple = compileFunctionSegments(parametersTuple.left.withDefinitions(definitions), substring, depth);
						let generated = indent + generatedHeader + " {" + statementsTuple.right + indent + "}";
						return new Some(new Tuple(statementsTuple.left, generated));
					}
					return new None();
				});
			});
		});
	}
	/* private static */ joinValues(retainParameters : List<Definition>) : string {
		return retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
	}
	/* private static */ retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right.iterate().map(Main.retainDefinition).flatMap(Iterators.fromOption).collect(new ListCollector());
	}
	/* private static */ parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return parseValuesOrEmpty(state, params, (state1 : unknown, s : unknown) => new Some(compileParameter(state1, s)));
	}
	/* private static */ compileFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return compileStatements(state, input, (state1 : unknown, input1 : unknown) => compileFunctionSegment(state1, input1, depth + 1));
	}
	/* private static */ compileFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input.strip();
		/* if (stripped.isEmpty())  */{
			return new Tuple(state, "");
		}
		return suffix(stripped, ";", (s : unknown) => {
			let tuple = statementValue(state, s, depth);
			return new Some(new Tuple(tuple.left, createIndent(depth) + tuple.right + ";"));
		}).or(() => {
			return block(state, depth, stripped);
		}).orElseGet(() => {
			return new Tuple(state, generatePlaceholder(stripped));
		});
	}
	/* private static */ block(state : CompileState, depth : number, stripped : string) : Option<[CompileState, string]> {
		return suffix(stripped, "}", (withoutEnd : unknown) => {
			return split(() => {
				let divisions = divideAll(withoutEnd, Main.foldBlockStart);
				return divisions.removeFirst().map((removed : unknown) => {
					let right = removed.left;
					let left = removed.right.iterate().collect(new Joiner("")).orElse("");
					return new Tuple(right, left);
				});
			}, (beforeContent : unknown, content : unknown) => {
				return suffix(beforeContent, "{", (s : unknown) => {
					let compiled = compileFunctionSegments(state, content, depth);
					let indent = createIndent(depth);
					return new Some(new Tuple(compiled.left, indent + generatePlaceholder(s) + "{" + compiled.right + indent + "}"));
				});
			});
		});
	}
	/* private static */ foldBlockStart(state : DivideState, c : Character) : DivideState {
		let appended = state.append(c);
		/* if (c == '{' && state.isLevel())  */{
			return appended.advance();
		}
		/* if (c == '{')  */{
			return appended.enter();
		}
		/* if (c == '}')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ statementValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input.strip();
		/* if (stripped.startsWith("return "))  */{
			let value = stripped.substring("return ".length());
			let tuple = compileValue(state, value, depth);
			return new Tuple(tuple.left, "return " + tuple.right);
		}
		return first(stripped, "=", (s : unknown, s2 : unknown) => {
			let definitionTuple = compileDefinition(state, s);
			let valueTuple = compileValue(definitionTuple.left, s2, depth);
			return new Some(new Tuple(valueTuple.left, "let " + definitionTuple.right + " = " + valueTuple.right));
		}).orElseGet(() => {
			return new Tuple(state, generatePlaceholder(stripped));
		});
	}
	/* private static */ compileValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let tuple = parseValue(state, input, depth);
		return new Tuple(tuple.left, tuple.right.generate());
	}
	/* private static */ parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return parseLambda(state, input, depth).or(() => parseString(state, input)).or(() => parseDataAccess(state, input, depth)).or(() => parseSymbolValue(state, input)).or(() => parseInvocation(state, input, depth)).or(() => parseOperation(state, input, depth, "+")).or(() => parseOperation(state, input, depth, "-")).or(() => parseDigits(state, input)).or(() => parseNot(state, input, depth)).or(() => parseMethodReference(state, input, depth)).orElseGet(() => new [CompileState, Value](state, new Placeholder(input)));
	}
	/* private static */ parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return last(input, "::", (s : unknown, s2 : unknown) => {
			let tuple = parseValue(state, s, depth);
			return new Some(new Tuple(tuple.left, new DataAccess(tuple.right, s2)));
		});
	}
	/* private static */ parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		/* if (stripped.startsWith("!"))  */{
			let slice = stripped.substring(1);
			let tuple = parseValue(state, slice, depth);
			let value = tuple.right;
			return new Some(new Tuple(tuple.left, new Not(value)));
		}
		return new None();
	}
	/* private static */ parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return first(input, "->", (beforeArrow : unknown, valueString : unknown) => {
			let strippedBeforeArrow = beforeArrow.strip();
			/* if (isSymbol(strippedBeforeArrow))  */{
				return assembleLambda(state, Lists.of(strippedBeforeArrow), valueString, depth);
			}
			/* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */{
				let parameterNames = divideAll(strippedBeforeArrow.substring(1, strippedBeforeArrow.length() - 1), Main.foldValueChar).iterate().map(String.strip).filter((value : unknown) => !value.isEmpty()).collect(new ListCollector());
				return assembleLambda(state, parameterNames, valueString, depth);
			}
			return new None();
		});
	}
	/* private static */ assembleLambda(state : CompileState, paramNames : List<string>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString.strip();
		/* Tuple<CompileState, LambdaValue> value */;
		/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */{
			let value1 = compileStatements(state, strippedValueString.substring(1, strippedValueString.length() - 1), (state1 : unknown, input1 : unknown) => compileFunctionSegment(state1, input1, depth + 1));
			let right = value1.right;
			let /* value  */ = new Tuple(value1.left, new BlockLambdaValue(right, depth));
		}
		/* else  */{
			let value1 = parseValue(state, strippedValueString, depth);
			let /* value  */ = new Tuple(value1.left, value1.right);
		}
		let right = value.right;
		return new Some(new Tuple(value.left, new Lambda(paramNames, right)));
	}
	/* private static */ parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		/* if (isNumber(stripped))  */{
			return new Some(new [CompileState, Value](state, new SymbolValue(stripped)));
		}
		return new None();
	}
	/* private static */ isNumber(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c = input.charAt(i);
			/* if (Character.isDigit(c))  */{
				/* continue */;
			}
			return false;
		}
		return true;
	}
	/* private static */ parseInvocation(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return suffix(input.strip(), ")", (withoutEnd : unknown) => {
			return split(() => toLast(withoutEnd, "", Main.foldInvocationStart), (callerWithEnd : unknown, argumentsString : unknown) => {
				return suffix(callerWithEnd, "(", (callerString : unknown) => {
					let callerString1 = callerString.strip();
					let callerTuple = invocationHeader(state, depth, callerString1);
					let parsed = parseValues(callerTuple.left, argumentsString, (state3 : unknown, s : unknown) => new Some(parseValue(state3, s, depth))).orElseGet(() => new Tuple(callerTuple.left, Lists.empty()));
					let oldCaller = callerTuple.right;
					let arguments = parsed.right;
					let newCaller = modifyCaller(parsed.left, oldCaller);
					let invokable = new Invokable(newCaller, arguments);
					return new Some(new Tuple(parsed.left, invokable));
				});
			});
		});
	}
	/* private static */ modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		/* if (oldCaller instanceof DataAccess access)  */{
			let type = resolveType(access.parent, state);
			/* if (type instanceof FunctionType)  */{
				return access.parent;
			}
		}
		return oldCaller;
	}
	/* private static */ resolveType(value : Value, state : CompileState) : Type {
		/* return switch (value)  */{
			/* case DataAccess dataAccess -> Primitive.Var */;
			/* case Invokable invokable -> Primitive.Var */;
			/* case Lambda lambda -> Primitive.Var */;
			/* case Not not -> Primitive.Var */;
			/* case Operation operation -> Primitive.Var */;
			/* case Placeholder placeholder -> Primitive.Var */;
			/* case StringValue stringValue -> Primitive.Var */;
			/* case SymbolValue symbolValue -> state.resolve(symbolValue.value).orElse(Primitive.Var) */;
			/* case IndexValue indexValue -> Primitive.Var */;
		}
		/*  */;
	}
	/* private static */ invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		/* if (callerString1.startsWith("new "))  */{
			let input1 : string = callerString1.substring("new ".length());
			let map = parseType(state, input1).map((type : unknown) => {
				let right = type.right;
				return new [CompileState, Caller](type.left, new ConstructionCaller(right));
			});
			/* if (map.isPresent())  */{
				return map.orElse(null);
			}
		}
		let tuple = parseValue(state, callerString1, depth);
		return new Tuple(tuple.left, tuple.right);
	}
	/* private static */ foldInvocationStart(state : DivideState, c : char) : DivideState {
		let appended = state.append(c);
		/* if (c == '(')  */{
			let enter = appended.enter();
			/* if (enter.isShallow())  */{
				return enter.advance();
			}
			return enter;
		}
		/* if (c == ')')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return last(input.strip(), ".", (parentString : unknown, rawProperty : unknown) => {
			let property = rawProperty.strip();
			/* if (!isSymbol(property))  */{
				return new None();
			}
			let tuple = parseValue(state, parentString, depth);
			let parent = tuple.right;
			let type = resolveType(parent, state);
			/* if (type instanceof TupleType)  */{
				/* if (property.equals("left"))  */{
					return new Some(new Tuple(state, new IndexValue(parent, new SymbolValue("0"))));
				}
				/* if (property.equals("right"))  */{
					return new Some(new Tuple(state, new IndexValue(parent, new SymbolValue("1"))));
				}
			}
			return new Some(new Tuple(tuple.left, new DataAccess(parent, property)));
		});
	}
	/* private static */ parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
			return new Some(new Tuple(state, new StringValue(stripped)));
		}
		return new None();
	}
	/* private static */ parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value.strip();
		/* if (isSymbol(stripped))  */{
			return new Some(new Tuple(state, new SymbolValue(stripped)));
		}
		return new None();
	}
	/* private static */ parseOperation(state : CompileState, value : string, depth : number, infix : string) : Option<[CompileState, Value]> {
		return first(value, infix, (s : unknown, s2 : unknown) => {
			let tuple = parseValue(state, s, depth);
			let tuple1 = parseValue(tuple.left, s2, depth);
			let left = tuple.right;
			let right = tuple1.right;
			return new Some(new Tuple(tuple1.left, new Operation(left, infix, right)));
		});
	}
	/* private static */ compileValues(state : CompileState, params : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed = parseValuesOrEmpty(state, params, (state1 : unknown, s : unknown) => new Some(mapper(state1, s)));
		let generated = generateValues(parsed.right);
		return new Tuple(parsed.left, generated);
	}
	/* private static */ generateValues(elements : List<string>) : string {
		return generateAll(Main.mergeValues, elements);
	}
	/* private static  */ parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return parseValues(state, input, mapper).orElseGet(() => new Tuple(state, Lists.empty()));
	}
	/* private static  */ parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return getCompileStateListTuple(state, input, Main.foldValueChar, mapper);
	}
	/* private static */ compileParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		/* if (input.isBlank())  */{
			return new Tuple(state, new Whitespace());
		}
		return parseDefinition(state, input).map((tuple : unknown) => new [CompileState, Parameter](tuple.left, tuple.right)).orElseGet(() => new Tuple(state, new Placeholder(input)));
	}
	/* private static */ compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return parseDefinition(state, input).map((tuple : unknown) => new Tuple(tuple.left, tuple.right.generate())).orElseGet(() => new Tuple(state, generatePlaceholder(input)));
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
		return suffix(input.strip(), ";", (withoutEnd : unknown) => {
			return parseDefinition(state, withoutEnd).map((result : unknown) => {
				let generated = createIndent(depth) + result.right.generate() + ";";
				return new Tuple(result.left, generated);
			});
		});
	}
	/* private static */ parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return last(input.strip(), " ", (beforeName : unknown, name : unknown) => {
			return split(() => toLast(beforeName, " ", Main.foldTypeSeparator), (beforeType : unknown, type : unknown) => {
				return suffix(beforeType.strip(), ">", (withoutTypeParamStart : unknown) => {
					return first(withoutTypeParamStart, "<", (beforeTypeParams : unknown, typeParamsString : unknown) => {
						let /* final */ compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1 : unknown, s : unknown) => new Tuple(state1, s.strip());
						let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1 : unknown, s : unknown) => new Some(compileStateStringTupleBiFunction.apply(state1, s)));
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
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState) : Option<[string, string]> {
		let divisions = divideAll(input, folder);
		return divisions.removeLast().map((removed : unknown) => {
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
		return parseType(state, type).map((type1 : unknown) => {
			let node = new Definition(beforeTypeParams, type1.right, name.strip(), typeParams);
			return new Tuple(type1.left, node);
		});
	}
	/* private static */ foldValueChar(state : DivideState, c : char) : DivideState {
		/* if (c == ',' && state.isLevel())  */{
			return state.advance();
		}
		let appended = state.append(c);
		/* if (c == '-')  */{
			let peeked = appended.peek();
			/* if (peeked == '>')  */{
				return appended.popAndAppendToOption().orElse(appended);
			}
			/* else  */{
				return appended;
			}
		}
		/* if (c == '<' || c == '(' || c == '{')  */{
			return appended.enter();
		}
		/* if (c == '>' || c == ')' || c == '}')  */{
			return appended.exit();
		}
		return appended;
	}
	/* private static */ compileType(state : CompileState, input : string) : Option<[CompileState, string]> {
		return parseType(state, input).map((tuple : unknown) => new Tuple(tuple.left, tuple.right.generate()));
	}
	/* private static */ parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input.strip();
		/* if (stripped.equals("int") || stripped.equals("Integer"))  */{
			return new Some(new Tuple(state, Primitive.Int));
		}
		/* if (stripped.equals("String"))  */{
			return new Some(new Tuple(state, Primitive.String));
		}
		/* if (stripped.equals("var"))  */{
			return new Some(new Tuple(state, Primitive.Var));
		}
		/* if (isSymbol(stripped))  */{
			return new Some(new Tuple(state, new Symbol(stripped)));
		}
		return template(state, input).or(() => varArgs(state, input));
	}
	/* private static */ varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix(input, "...", (s : unknown) => {
			return parseType(state, s).map((inner : unknown) => {
				let newState = inner.left;
				let child = inner.right;
				return new Tuple(newState, new ArrayType(child));
			});
		});
	}
	/* private static */ template(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix(input.strip(), ">", (withoutEnd : unknown) => {
			return first(withoutEnd, "<", (base : unknown, argumentsString : unknown) => {
				let strippedBase = base.strip();
				return parseValues(state, argumentsString, Main.argument).map((argumentsTuple : unknown) => {
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
		return parseType(state, input).map((tuple : unknown) => new Tuple(tuple.left, tuple.right));
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix(input, infix, Main.findLast, mapper);
	}
	/* private static */ findLast(input : string, infix : string) : Option<number> {
		let index = input.lastIndexOf(infix);
		/* if (index == -1)  */{
			return new None<number>();
		}
		return new Some(index);
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix(input, infix, Main.findFirst, mapper);
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return split(() => locator(input, infix).map((index : unknown) => {
			let left = input.substring(0, index);
			let right = input.substring(index + infix.length());
			return new Tuple(left, right);
		}), mapper);
	}
	/* private static  */ split<T>(splitter : () => Option<[string, string]>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter().flatMap((tuple : unknown) => mapper(tuple.left, tuple.right));
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
        Boolean("boolean"),
        Var("var");

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
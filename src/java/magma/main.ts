/* private */interface Tuple2<A, B>/*   */ {
	left() : A;
	right() : B;
}
/* private */interface Option<T>/*   */ {
	map<R>(mapper : (arg0 : T) => R) : Option<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (arg0 : T) => boolean) : Option<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => Option<T>) : Option<T>;
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R>;
	isEmpty() : boolean;
	and<R>(other : () => Option<R>) : Option<[T, R]>;
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
	zip<R>(other : Iterator<R>) : Iterator<[T, R]>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T>;
	iterate() : Iterator<T>;
	removeLast() : Option<[List<T>, T]>;
	get(index : number) : Option<T>;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : Iterator<[number, T]>;
	removeFirst() : Option<[T, List<T>]>;
	addAllLast(others : List<T>) : List<T>;
}
/* private */interface Head<T>/*   */ {
	next() : Option<T>;
}
/* private */interface Type/*  */ {
	generate() : string;
	replace(mapping : /* Map */<string, Type>) : Type;
}
/* private */interface Argument/*  */ {
}
/* private */interface Parameter/*  */ {
}
/* private sealed */interface Value/*  */ {
	generate() : string;
	type() : Type;
}
/* private */interface LambdaValue/*  */ {
	generate() : string;
}
/* private sealed */interface Caller/*  */ {
	generate() : string;
}
/* private */interface FindableType/*  */ {
	typeParams() : List<string>;
	find(name : string) : Option<Type>;
	replace(mapping : /* Map */<string, Type>) : Type {
		return /* this */;
	}
}
/* private static */class None<T>/*  */ {
	map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new None();
	}
	isPresent() : boolean {
		return /* false */;
	}
	orElse(other : T) : T {
		return other;
	}
	filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new None();
	}
	orElseGet(supplier : () => T) : T {
		return supplier();
	}
	or(other : () => Option<T>) : Option<T> {
		return other.get();
	}
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return new None();
	}
	isEmpty() : boolean {
		return /* true */;
	}
	and<R>(other : () => Option<R>) : Option<[T, R]> {
		return new None();
	}
}
/* private */class Some<T>/*  */ {
	constructor (value : T) {
	}

	map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper(/* this */.value));
	}
	isPresent() : boolean {
		return /* true */;
	}
	orElse(other : T) : T {
		return /* this */.value;
	}
	filter(predicate : (arg0 : T) => boolean) : Option<T> {
		/* if (predicate.test(this.value))  */{
			return /* this */;
		}
		return new None();
	}
	orElseGet(supplier : () => T) : T {
		return /* this */.value;
	}
	or(other : () => Option<T>) : Option<T> {
		return /* this */;
	}
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper(/* this */.value);
	}
	isEmpty() : boolean {
		return /* false */;
	}
	and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other.get().map((otherValue) => new /* Tuple2Impl */(/* this */.value, otherValue));
	}
}
/* private static */class SingleHead<T>/*  */ {
	value : T;
	retrieved : boolean;
	SingleHead(value : T) : /* public */ {
		let /* this.value  */ = value;
		let /* this.retrieved  */ = /* false */;
	}
	next() : Option<T> {
		/* if (this.retrieved)  */{
			return new None();
		}
		let /* this.retrieved  */ = /* true */;
		return new Some(/* this */.value);
	}
}
/* private static */class EmptyHead<T>/*  */ {
	next() : Option<T> {
		return new None();
	}
}
/* private */class HeadedIterator<T>/*  */ {
	constructor (head : Head<T>) {
	}

	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current = initial;
		/* while (true)  */{
			let finalCurrent : R = /* current */;
			let optional = /* this */.head.next().map((inner) => folder(/* finalCurrent */, inner));
			/* if (optional.isPresent())  */{
				let /* current  */ = /* optional */.orElse(/* null */);
			}
			/* else  */{
				return /* current */;
			}
		}
	}
	map<R>(mapper : (arg0 : T) => R) : Iterator<R> {
		return new HeadedIterator(() => /* this */.head.next().map(mapper));
	}
	collect<R>(collector : Collector<T, R>) : R {
		return /* this */.fold(collector.createInitial(), collector.fold);
	}
	filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return /* this */.flatMap((element) => {
			/* if (predicate.test(element))  */{
				return new HeadedIterator(new SingleHead(element));
			}
			return new HeadedIterator(new EmptyHead());
		});
	}
	next() : Option<T> {
		return /* this */.head.next();
	}
	flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
		return new HeadedIterator(new /* FlatMapHead */(/* this */.head, f));
	}
	zip<R>(other : Iterator<R>) : Iterator<[T, R]> {
		return new HeadedIterator(() => /* HeadedIterator */.this.head.next().and(other.next));
	}
}
/* private static */class RangeHead/*  */ {
	length : number;
	counter : number;
	RangeHead(length : number) : /* public */ {
		let /* this.length  */ = length;
	}
	next() : Option<number> {
		/* if (this.counter < this.length)  */{
			let value = /* this */.counter;
			/* this.counter++ */;
			return new Some(value);
		}
		return new None();
	}
}
/* private static final */class JVMList<T>/*  */ {
	elements : /* java.util.List */<T>;
	JVMList(elements : /* java.util.List */<T>) : /* private */ {
		let /* this.elements  */ = elements;
	}
	JVMList() : /* public */ {
		/* this(new ArrayList<>()) */;
	}
	addLast(element : T) : List<T> {
		/* this.elements.add(element) */;
		return /* this */;
	}
	iterate() : Iterator<T> {
		return /* this */.iterateWithIndices().map(/* Tuple2 */.right);
	}
	removeLast() : Option<[List<T>, T]> {
		/* if (this.elements.isEmpty())  */{
			return new None();
		}
		let slice = /* this */.elements.subList(0, /* this */.elements.size() - 1);
		let last = /* this */.elements.getLast();
		return new Some(new /* Tuple2Impl */<List<T>, T>(new JVMList(/* slice */), /* last */));
	}
	size() : number {
		return /* this */.elements.size();
	}
	isEmpty() : boolean {
		return /* this */.elements.isEmpty();
	}
	addFirst(element : T) : List<T> {
		/* this.elements.addFirst(element) */;
		return /* this */;
	}
	iterateWithIndices() : Iterator<[number, T]> {
		return new HeadedIterator(new RangeHead(/* this */.elements.size())).map((index : T) => new /* Tuple2Impl */(index, /* this */.elements.get(index)));
	}
	removeFirst() : Option<[T, List<T>]> {
		/* if (this.elements.isEmpty())  */{
			return new None();
		}
		let first = /* this */.elements.getFirst();
		let slice = /* this */.elements.subList(1, /* this */.elements.size());
		return new Some(new /* Tuple2Impl */<T, List<T>>(/* first */, new JVMList(/* slice */)));
	}
	addAllLast(others : List<T>) : List<T> {
		let initial : List<T> = /* this */;
		return others.iterate().fold(initial, /* List */.addLast);
	}
	get(index : number) : Option<T> {
		/* if (index < this.elements.size())  */{
			return new Some(/* this */.elements.get(index));
		}
		/* else  */{
			return new None();
		}
	}
}
/* private static */class Lists/*  */ {
	empty<T>() : List<T> {
		return new JVMList();
	}
	of<T>(elements : T[]) : List<T> {
		return new JVMList(new /* ArrayList */(/* Arrays */.asList(elements)));
	}
}
/* private */class Definition/*  */ {
	constructor (maybeBefore : Option<string>, name : string, type : Type, typeParams : List<string>) {
	}

	createSimpleDefinition(name : string, type : Type) : Definition {
		return new Definition(new None(), name, type, /* Lists */.empty());
	}
	generate() : string {
		return /* this */.generateWithParams("");
	}
	generateWithParams(params : string) : string {
		let joined = /* this */.joinTypeParams();
		let before = /* this */.joinBefore();
		let typeString = /* this */.generateType();
		return /* before */ + /* this */.name + /* joined */ + params + /* typeString */;
	}
	generateType() : string {
		/* if (this.type.equals(Primitive.Unknown))  */{
			return "";
		}
		return " : " + /* this */.type.generate();
	}
	joinBefore() : string {
		return !/* isDebug ? "" : this */.maybeBefore.filter((value) => !value.isEmpty()).map(/* Main */.generatePlaceholder).map((inner) => inner + " ").orElse("");
	}
	joinTypeParams() : string {
		return /* this */.typeParams.iterate().collect(new /* Joiner */()).map((inner) => "<" + inner + ">").orElse("");
	}
	mapType(mapper : (arg0 : Type) => Type) : Definition {
		return new Definition(/* this */.maybeBefore, /* this */.name, mapper(/* this */.type), /* this */.typeParams);
	}
	toString() : string {
		return "Definition[" + "maybeBefore=" + /* this */.maybeBefore + ", " + "name=" + /* this */.name + ", " + "type=" + /* this */.type + ", " + "typeParams=" + /* this */.typeParams + /*  ']' */;
	}
}
/* private */class ObjectType/*  */ {
	constructor (name : string, typeParams : List<string>, definitions : List<Definition>) {
	}

	generate() : string {
		return /* this */.name;
	}
	replace(mapping : /* Map */<string, Type>) : Type {
		return new ObjectType(/* this */.name, /* this */.typeParams, /* this */.definitions.iterate().map((definition) => definition.mapType((type) => type(mapping))).collect(new /* ListCollector */()));
	}
	find(name : string) : Option<Type> {
		return /* this */.definitions.iterate().filter((definition) => definition.name.equals(name)).map(/* Definition */.type).next();
	}
}
/* private */class TypeParam/*  */ {
	constructor (value : string) {
	}

	generate() : string {
		return /* this */.value;
	}
	replace(mapping : /* Map */<string, Type>) : Type {
		/* if (mapping.containsKey(this.value))  */{
			return mapping.get(/* this */.value);
		}
		return /* this */;
	}
}
/* private */class CompileState/*  */ {
	constructor (structures : List<string>, definitions : List<Definition>, objectTypes : List<ObjectType>, maybeStructName : Option<string>, typeParams : List<string>, typeRegister : Option<Type>) {
	}

	CompileState() : /* public */ {
		/* this(Lists.empty(), Lists.empty(), Lists.empty(), new None<>(), Lists.empty(), new None<>()) */;
	}
	resolveValue(name : string) : Option<Type> {
		return /* this */.definitions.iterate().filter((definition) => definition.name.equals(name)).next().map(/* Definition */.type);
	}
	addStructure(structure : string) : CompileState {
		return new CompileState(/* this */.structures.addLast(structure), /* this */.definitions, /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams, /* this */.typeRegister);
	}
	withDefinitions(definitions : List<Definition>) : CompileState {
		return new CompileState(/* this */.structures, /* this */.definitions.addAllLast(definitions), /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams, /* this */.typeRegister);
	}
	resolveType(name : string) : Option<Type> {
		/* if (this.maybeStructName.filter(inner -> inner.equals(name)).isPresent())  */{
			return new Some(new ObjectType(name, /* this */.typeParams, /* this */.definitions));
		}
		let maybeTypeParam = /* this */.typeParams.iterate().filter((param) => param.equals(name)).next();
		/* if (maybeTypeParam instanceof Some(var value))  */{
			return new Some(new TypeParam(value));
		}
		return /* this */.objectTypes.iterate().filter((type) => type.name.equals(name)).next().map((type) => type);
	}
	addType(type : ObjectType) : CompileState {
		return new CompileState(/* this */.structures, /* this */.definitions, /* this */.objectTypes.addLast(type), /* this */.maybeStructName, /* this */.typeParams, /* this */.typeRegister);
	}
	withDefinition(definition : Definition) : CompileState {
		return new CompileState(/* this */.structures, /* this */.definitions.addLast(definition), /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams, /* this */.typeRegister);
	}
	withStructName(name : string) : CompileState {
		return new CompileState(/* this */.structures, /* this */.definitions, /* this */.objectTypes, new Some(name), /* this */.typeParams, /* this */.typeRegister);
	}
	withTypeParams(typeParams : List<string>) : CompileState {
		return new CompileState(/* this */.structures, /* this */.definitions, /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams.addAllLast(typeParams), /* this */.typeRegister);
	}
	withExpectedType(type : Type) : CompileState {
		return new CompileState(/* this */.structures, /* this */.definitions, /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams, new Some(type));
	}
}
/* private static */class DivideState/*  */ {
	input : string;
	index : number;
	depth : number;
	segments : List<string>;
	buffer : /* StringBuilder */;
	DivideState(input : string, index : number, segments : List<string>, buffer : /* StringBuilder */, depth : number) : /* public */ {
		let /* this.segments  */ = segments;
		let /* this.buffer  */ = buffer;
		let /* this.depth  */ = depth;
		let /* this.input  */ = input;
		let /* this.index  */ = index;
	}
	DivideState(input : string) : /* public */ {
		/* this(input, 0, Lists.empty(), new StringBuilder(), 0) */;
	}
	advance() : DivideState {
		let /* this.segments  */ = /* this */.segments.addLast(/* this */.buffer.toString());
		let /* this.buffer  */ = new /* StringBuilder */();
		return /* this */;
	}
	append(c : /* char */) : DivideState {
		/* this.buffer.append(c) */;
		return /* this */;
	}
	enter() : DivideState {
		/* this.depth++ */;
		return /* this */;
	}
	isLevel() : boolean {
		return /* this.depth == 0 */;
	}
	exit() : DivideState {
		/* this.depth-- */;
		return /* this */;
	}
	isShallow() : boolean {
		return /* this.depth == 1 */;
	}
	pop() : Option<[/* Character */, DivideState]> {
		/* if (this.index < this.input.length())  */{
			let c = /* this */.input.charAt(/* this */.index);
			return new Some(new /* Tuple2Impl */(c, new DivideState(/* this */.input, /* this */.index + 1, /* this */.segments, /* this */.buffer, /* this */.depth)));
		}
		return new None();
	}
	popAndAppendToTuple() : Option<[/* Character */, DivideState]> {
		return /* this */.pop().map((tuple) => {
			let c = tuple.left();
			let right = tuple.right();
			return new /* Tuple2Impl */(c, right(c));
		});
	}
	popAndAppendToOption() : Option<DivideState> {
		return /* this */.popAndAppendToTuple().map(/* Tuple2 */.right);
	}
	peek() : /* char */ {
		return /* this */.input.charAt(/* this */.index);
	}
}
/* private */class Joiner/*  */ {
	constructor (delimiter : string) {
	}

	Joiner() : /* private */ {
		/* this("") */;
	}
	createInitial() : Option<string> {
		return new None();
	}
	fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current.map((inner : string) => inner + /* this */.delimiter + element).orElse(element));
	}
}
/* private static */class ListCollector<T>/*  */ {
	createInitial() : List<T> {
		return /* Lists */.empty();
	}
	fold(current : List<T>, element : T) : List<T> {
		return current.addLast(element);
	}
}
/* private */class Tuple2Impl<A, B>/*  */ {
	constructor (left : A, right : B) {
	}

}
/* private static */class FlatMapHead<T, R>/*  */ {
	mapper : (arg0 : T) => Iterator<R>;
	head : Head<T>;
	current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>) : /* public */ {
		let /* this.mapper  */ = mapper;
		let /* this.current  */ = new None();
		let /* this.head  */ = head;
	}
	next() : Option<R> {
		/* while (true)  */{
			/* if (this.current.isPresent())  */{
				let inner : Iterator<R> = /* this */.current.orElse(/* null */);
				let maybe : Option<R> = inner.next();
				/* if (maybe.isPresent())  */{
					return /* maybe */;
				}
				/* else  */{
					let /* this.current  */ = new None();
				}
			}
			let outer : Option<T> = /* this */.head.next();
			/* if (outer.isPresent())  */{
				let /* this.current  */ = /* outer */.map(/* this */.mapper);
			}
			/* else  */{
				return new None();
			}
		}
	}
}
/* private */class ArrayType/*  */ {
	constructor (right : Type) {
	}

	generate() : string {
		return /* this */.right().generate() + "[]";
	}
	replace(mapping : /* Map */<string, Type>) : Type {
		return /* this */;
	}
}
/* private static */class Whitespace/*  */ {
}
/* private static */class Iterators/*  */ {
	fromOption<T>(option : Option<T>) : Iterator<T> {
		let single : Option<Head<T>> = option.map(SingleHead.new);
		return new HeadedIterator(/* single */.orElseGet(/* EmptyHead */.new));
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List<Type>, returns : Type) {
	}

	generate() : string {
		let joined = /* this */.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
		return "(" + /* joined */ + ") => " + /* this */.returns.generate();
	}
	replace(mapping : /* Map */<string, Type>) : Type {
		return new FunctionType(/* this */.arguments.iterate().map((type) => type(mapping)).collect(new ListCollector()), /* this */.returns);
	}
}
/* private */class TupleType/*  */ {
	constructor (arguments : List<Type>) {
	}

	generate() : string {
		let joinedArguments = /* this */.arguments.iterate().map(/* Type */.generate).collect(new Joiner(", ")).orElse("");
		return "[" + joinedArguments + "]";
	}
	replace(mapping : /* Map */<string, Type>) : Type {
		return /* this */;
	}
}
/* private */class Template/*  */ {
	constructor (base : FindableType, arguments : List<Type>) {
	}

	generate() : string {
		let joinedArguments = /* this */.arguments.iterate().map(/* Type */.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		return /* this */.base.generate() + /* joinedArguments */;
	}
	typeParams() : List<string> {
		return /* this */.base.typeParams();
	}
	find(name : string) : Option<Type> {
		return /* this */.base.find(name).map((found) => {
			let mapping = /* this */.base.typeParams().iterate().zip(/* this */.arguments.iterate()).collect(new /* MapCollector */());
			return found.replace(mapping);
		});
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}

	generate() : string {
		return /* generatePlaceholder */(/* this */.input);
	}
	type() : Type {
		return /* Primitive */.Unknown;
	}
	typeParams() : List<string> {
		return /* Lists */.empty();
	}
	find(name : string) : Option<Type> {
		return new None();
	}
}
/* private */class StringValue/*  */ {
	constructor (stripped : string) {
	}

	generate() : string {
		return /* this */.stripped;
	}
	type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : Value, property : string, type : Type) {
	}

	generate() : string {
		return /* this */.parent.generate() + "." + /* this */.property + /* createDebugString */(/* this */.type);
	}
	type() : Type {
		return /* this */.type;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : Type) {
	}

	generate() : string {
		return "new " + /* this */.type.generate();
	}
	toFunction() : FunctionType {
		return new FunctionType(/* Lists */.empty(), /* this */.type);
	}
}
/* private */class Operation/*  */ {
	constructor (left : Value, infix : string, right : Value) {
	}

	generate() : string {
		return /* this */.left().generate() + " " + /* this */.infix + " " + /* this */.right().generate();
	}
	type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class Not/*  */ {
	constructor (value : Value) {
	}

	generate() : string {
		return "!" + /* this */.value.generate();
	}
	type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class BlockLambdaValue/*  */ {
	constructor (right : string, depth : number) {
	}

	generate() : string {
		return "{" + this.right() + createIndent(this.depth) + "}";
	}
}
/* private */class Lambda/*  */ {
	constructor (parameters : List<Definition>, body : LambdaValue) {
	}

	generate() : string {
		let joined = /* this */.parameters.iterate().map(/* Definition */.generate).collect(new Joiner(", ")).orElse("");
		return "(" + /* joined */ + ") => " + /* this */.body.generate();
	}
	type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : Caller, arguments : List<Value>, type : Type) {
	}

	generate() : string {
		let joined = /* this */.arguments.iterate().map(/* Value */.generate).collect(new Joiner(", ")).orElse("");
		return /* this */.caller.generate() + "(" + /* joined */ + ")" + /* createDebugString */(/* this */.type);
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : Value, child : Value) {
	}

	generate() : string {
		return /* this */.parent.generate() + "[" + this.child.generate() + "]";
	}
	type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (stripped : string, type : Type) {
	}

	generate() : string {
		return /* this */.stripped + /* createDebugString */(/* this */.type);
	}
}
/* private */class MapCollector<K, V>/*  */ {
	constructor () {
	}

	createInitial() : /* Map */<K, V> {
		return new /* HashMap */();
	}
	fold(current : /* Map */<K, V>, element : [K, V]) : /* Map */<K, V> {
		/* current.put(element.left(), element.right()) */;
		return current;
	}
}
/* private */class Primitive/*  */ {
	Unknown("unknown") : /*  */;
	value : string;/* 

        Primitive(String value) {
            this.value = value;
        } */
	generate() : string {
		return /* this */.value;
	}
	replace(mapping : /* Map */<string, Type>) : Type {
		return /* this */;
	}
}
/* public */class Main/*  */ {/* 

    private static final boolean isDebug = false; */
	main() : /* void */ {
		/* try  */{
			let parent = /* Paths */.get(".", "src", "java", "magma");
			let source = /* parent */.resolve("Main.java");
			let target = /* parent */.resolve("main.ts");
			let input = /* Files */.readString(/* source */);
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
	compile(input : string) : string {
		let tuple = /* compileStatements */(new CompileState(), input, /* Main */.compileRootSegment);
		let joined = tuple.left().structures.iterate().collect(new Joiner()).orElse("");
		return /* joined */ + tuple.right();
	}
	compileStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed = /* parseStatements */(state, input, mapper);
		return new Tuple2Impl(/* parsed */.left(), /* generateStatements */(/* parsed */.right()));
	}
	generateStatements(statements : List<string>) : string {
		return /* generateAll */(/* Main */.mergeStatements, statements);
	}
	parseStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, List<string>] {
		return /* parseAll0 */(state, input, /* Main */.foldStatementChar, mapper);
	}
	generateAll(merger : (arg0 : /* StringBuilder */, arg1 : string) => /* StringBuilder */, elements : List<string>) : string {
		return elements.iterate().fold(new /* StringBuilder */(), merger).toString();
	}
	parseAll0<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return /* getCompileStateListTuple */(state, input, folder, (state1, s) => new Some(mapper(state1, s))).orElseGet(() => new Tuple2Impl(state, /* Lists */.empty()));
	}
	getCompileStateListTuple<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return /* parseAll */(state, input, folder, (state1, tuple) => mapper(state1, tuple.right()));
	}
	parseAll<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let initial : Option<[CompileState, List<T>]> = new Some(new Tuple2Impl(state, /* Lists */.empty()));
		return /* divideAll */(input, folder).iterateWithIndices().fold(initial, (tuple, element) => {
			return tuple.flatMap((inner) => {
				let state1 = inner.left();
				let right = inner.right();
				return mapper(state1, element).map((applied) => {
					return new Tuple2Impl(applied.left(), right(applied.right()));
				});
			});
		});
	}
	mergeStatements(stringBuilder : /* StringBuilder */, str : string) : /* StringBuilder */ {
		return stringBuilder.append(str);
	}
	divideAll(input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState) : List<string> {
		let current = new DivideState(input);
		/* while (true)  */{
			let maybePopped = current.pop().map((tuple : [/* Character */, DivideState]) => {
				return /* foldSingleQuotes */(tuple).or(() => /* foldDoubleQuotes */(tuple)).orElseGet(() => folder(tuple.right(), tuple.left()));
			});
			/* if (maybePopped.isPresent())  */{
				let /* current  */ = /* maybePopped */.orElse(current);
			}
			/* else  */{
				/* break */;
			}
		}
		return current.advance().segments;
	}
	foldDoubleQuotes(tuple : [/* Character */, DivideState]) : Option<DivideState> {
		/* if (tuple.left() == '\"')  */{
			let current = tuple.right().append(tuple.left());
			/* while (true)  */{
				let maybePopped = current.popAndAppendToTuple();
				/* if (maybePopped.isEmpty())  */{
					/* break */;
				}
				let popped = /* maybePopped */.orElse(/* null */);
				let /* current  */ = /* popped */.right();
				/* if (popped.left() == '\\')  */{
					let /* current  */ = current.popAndAppendToOption().orElse(current);
				}
				/* if (popped.left() == '\"')  */{
					/* break */;
				}
			}
			return new Some(current);
		}
		return new None();
	}
	foldSingleQuotes(tuple : [/* Character */, DivideState]) : Option<DivideState> {
		/* if (tuple.left() != '\'')  */{
			return new None();
		}
		let appended = tuple.right().append(tuple.left());
		return /* appended */.popAndAppendToTuple().map(/* Main */.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	foldEscaped(escaped : [/* Character */, DivideState]) : DivideState {
		/* if (escaped.left() == '\\')  */{
			return escaped[1]().popAndAppendToOption().orElse(escaped[1]());
		}
		return escaped[1]();
	}
	foldStatementChar(state : DivideState, c : /* char */) : DivideState {
		let append = state.append(c);
		/* if (c == ';' && append.isLevel())  */{
			return append();
		}
		/* if (c == '}' && append.isShallow())  */{
			return append().exit();
		}
		/* if (c == '{' || c == '(')  */{
			return append();
		}
		/* if (c == '}' || c == ')')  */{
			return append();
		}
		return append;
	}
	compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped = input.strip();
		/* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */{
			return new Tuple2Impl(state, "");
		}
		return /* compileClass */(/* stripped */, 0, state).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */(/* stripped */)));
	}
	compileClass(stripped : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return /* compileStructure */(stripped, "class ", "class ", state);
	}
	compileStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, string]> {
		return /* first */(stripped, sourceInfix, (beforeInfix, right) => {
			return /* first */(right, "{", (beforeContent, withEnd) => {
				return /* suffix */(withEnd.strip(), "}", (content1) => {
					return /* getOr */(targetInfix, state, beforeInfix, beforeContent, content1);
				});
			});
		});
	}
	getOr(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return /* first */(beforeContent, " implements ", (s, s2) => {
			return /* structureWithMaybeExtends */(targetInfix, state, beforeInfix, s, content1);
		}).or(() => {
			return /* structureWithMaybeExtends */(targetInfix, state, beforeInfix, beforeContent, content1);
		});
	}
	structureWithMaybeExtends(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return /* first */(beforeContent, " extends ", (s, s2) => {
			return /* structureWithMaybeParams */(targetInfix, state, beforeInfix, s, content1);
		}).or(() => {
			return /* structureWithMaybeParams */(targetInfix, state, beforeInfix, beforeContent, content1);
		});
	}
	structureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return /* suffix */(beforeContent.strip(), ")", (s) => {
			return /* first */(s, "(", (s1, s2) => {
				let parsed = /* parseParameters */(state, s2);
				return /* getOred */(targetInfix, /* parsed */.left(), beforeInfix, s1, content1, /* parsed */.right());
			});
		}).or(() => {
			return /* getOred */(targetInfix, state, beforeInfix, beforeContent, content1, /* Lists */.empty());
		});
	}
	getOred(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>) : Option<[CompileState, string]> {
		return /* first */(beforeContent, "<", (name, withTypeParams) => {
			return /* first */(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
				let compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => new Tuple2Impl(state1, s.strip());
				let typeParams = /* parseValuesOrEmpty */(state, typeParamsString, (state1, s) => new Some(/* compileStateStringTupleBiFunction */.apply(state1, s)));
				return /* assembleStructure */(typeParams(), targetInfix, beforeInfix, name, content1, typeParams(), afterTypeParams, params);
			});
		}).or(() => {
			return /* assembleStructure */(state, targetInfix, beforeInfix, beforeContent, content1, /* Lists */.empty(), "", params);
		});
	}
	assembleStructure(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string, params : List<Parameter>) : Option<[CompileState, string]> {
		let name = rawName.strip();
		/* if (!isSymbol(name))  */{
			return new None();
		}
		let joinedTypeParams = typeParams().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		let parsed = parseStatements(state.withStructName(name).withTypeParams(typeParams), content, (state0, input) => /* compileClassSegment */(state0, input, 1));
		/* List<String> parsed1 */;
		/* if (params.isEmpty())  */{
			let /* parsed1  */ = /* parsed */.right();
		}
		/* else  */{
			let joined = /* joinValues */(/* retainDefinitions */(params));
			let constructorIndent = /* createIndent */(1);
			let /* parsed1  */ = /* parsed */.right().addFirst(/* constructorIndent */ + "constructor (" + joined + ") {" + constructorIndent + "}\n");
		}
		let parsed2 = /* parsed1 */.iterate().collect(new Joiner()).orElse("");
		let generated = /* generatePlaceholder */(beforeInfix.strip()) + targetInfix + name + /* joinedTypeParams */ + /* generatePlaceholder */(afterTypeParams) + " {" + parsed2 + "\n}\n";
		return new Some(new Tuple2Impl(/* parsed */.left().addStructure(/* generated */).addType(new ObjectType(name, typeParams, /* parsed */.left().definitions)), ""));
	}
	retainDefinition(parameter : Parameter) : Option<Definition> {
		/* if (parameter instanceof Definition definition)  */{
			return new Some(definition);
		}
		return new None();
	}
	isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c = input.charAt(/* i */);
			/* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		/* if (!input.endsWith(suffix))  */{
			return new None();
		}
		let slice = input.substring(0, input.length() - suffix.length());
		return mapper(/* slice */);
	}
	compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return /* compileWhitespace */(input, state).or(() => compileClass(input, depth, state)).or(() => compileStructure(input, "interface ", "interface ", state)).or(() => compileStructure(input, "record ", "class ", state)).or(() => compileStructure(input, "enum ", "class ", state)).or(() => /* compileMethod */(state, input, depth)).or(() => /* compileDefinitionStatement */(input, depth, state)).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */(input)));
	}
	compileWhitespace(input : string, state : CompileState) : Option<[CompileState, string]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple2Impl(state, ""));
		}
		return new None();
	}
	compileMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return /* first */(input, "(", (definitionString, withParams) => {
			return /* first */(withParams, ")", (parametersString, rawContent) => {
				return /* parseDefinition */(state, definitionString).flatMap((definitionTuple) => {
					let definitionState = definitionTuple.left();
					let definition = definitionTuple.right();
					let parametersTuple = /* parseParameters */(/* definitionState */, parametersString);
					let rawParameters = /* parametersTuple */.right();
					let parameters = /* retainDefinitions */(/* rawParameters */);
					let joinedParameters = /* joinValues */(/* parameters */);
					let content = rawContent.strip();
					let indent = /* createIndent */(depth);
					let paramTypes = /* parameters */.iterate().map(/* Definition */.type).collect(new ListCollector());
					let toDefine = /* Definition */.createSimpleDefinition(definition.name, new FunctionType(/* paramTypes */, definition.type));
					let generatedHeader = definition.generateWithParams("(" + joinedParameters + ")");
					/* if (content.equals(";"))  */{
						return new Some(new Tuple2Impl(/* parametersTuple */.left().withDefinition(/* toDefine */), /* indent */ + /* generatedHeader */ + ";"));
					}
					/* if (content.startsWith("{") && content.endsWith("}"))  */{
						let substring = content.substring(1, content.length() - 1);
						let statementsTuple = /* compileFunctionSegments */(/* parametersTuple */.left().withDefinitions(/* parameters */), /* substring */, depth);
						let generated = /* indent */ + /* generatedHeader */ + " {" + statementsTuple.right() + indent + "}";
						return new Some(new Tuple2Impl(/* statementsTuple */.left().withDefinition(/* toDefine */), /* generated */));
					}
					return new None();
				});
			});
		});
	}
	joinValues(retainParameters : List<Definition>) : string {
		return retainParameters.iterate().map(/* Definition */.generate).collect(new Joiner(", ")).orElse("");
	}
	retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right().map(/* Main */.retainDefinition).flatMap(/* Iterators */.fromOption).collect(new ListCollector());
	}
	parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return /* parseValuesOrEmpty */(state, params, (state1, s) => new Some(/* compileParameter */(state1, s)));
	}
	compileFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return compileStatements(state, input, (state1, input1) => /* compileFunctionSegment */(state1, input1, depth + 1));
	}
	compileFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input.strip();
		/* if (stripped.isEmpty())  */{
			return new Tuple2Impl(state, "");
		}
		return /* compileFunctionStatement */(state, depth, stripped).or(() => /* compileBlock */(state, depth, stripped)).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */(stripped)));
	}
	compileFunctionStatement(state : CompileState, depth : number, stripped : string) : Option<[CompileState, string]> {
		return suffix(stripped, ";", (s) => {
			let tuple = /* statementValue */(state, s, depth);
			return new Some(new Tuple2Impl(tuple.left(), /* createIndent */(depth) + tuple.right() + ";"));
		});
	}
	compileBlock(state : CompileState, depth : number, stripped : string) : Option<[CompileState, string]> {
		return suffix(stripped, "}", (withoutEnd) => {
			return /* split */(() => {
				let divisions = divideAll(withoutEnd, /* Main */.foldBlockStart);
				return /* divisions */.removeFirst().map((removed) => {
					let right = removed.left();
					let left = removed.right().iterate().collect(new Joiner("")).orElse("");
					return new Tuple2Impl(right, left);
				});
			}, (beforeContent, content) => {
				return suffix(beforeContent, "{", (s) => {
					let compiled = compileFunctionSegments(state, content, depth);
					let indent = /* createIndent */(depth);
					return new Some(new Tuple2Impl(/* compiled */.left(), /* indent */ + /* generatePlaceholder */(s) + "{" + compiled.right() + indent + "}"));
				});
			});
		});
	}
	foldBlockStart(state : DivideState, c : /* Character */) : DivideState {
		let appended = state.append(c);
		/* if (c == '{' && state.isLevel())  */{
			return /* appended */.advance();
		}
		/* if (c == '{')  */{
			return /* appended */.enter();
		}
		/* if (c == '}')  */{
			return /* appended */.exit();
		}
		return /* appended */;
	}
	statementValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped = input.strip();
		/* if (stripped.startsWith("return "))  */{
			let value = stripped.substring("return ".length());
			let tuple = /* compileValue */(state, value, depth);
			return new Tuple2Impl(tuple.left(), "return " + tuple.right());
		}
		return /* first */(stripped, "=", (s, s2) => {
			let definitionTuple = /* compileDefinition */(state, s);
			let valueTuple = /* compileValue */(definitionTuple.left(), s2, depth);
			return new Some(new Tuple2Impl(/* valueTuple */.left(), "let " + definitionTuple.right() + " = " + /* valueTuple */.right()));
		}).orElseGet(() => {
			return new Tuple2Impl(state, /* generatePlaceholder */(stripped));
		});
	}
	compileValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let tuple = /* parseValue */(state, input, depth);
		return new Tuple2Impl(tuple.left(), tuple.right().generate());
	}
	parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return /* parseLambda */(state, input, depth).or(() => /* parseString */(state, input)).or(() => /* parseDataAccess */(state, input, depth)).or(() => /* parseSymbolValue */(state, input)).or(() => /* parseInvokable */(state, input, depth)).or(() => /* parseOperation */(state, input, depth, "+")).or(() => /* parseOperation */(state, input, depth, "-")).or(() => /* parseDigits */(state, input)).or(() => /* parseNot */(state, input, depth)).or(() => /* parseMethodReference */(state, input, depth)).orElseGet(() => new Tuple2Impl<CompileState, Value>(state, new Placeholder(input)));
	}
	parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* last */(input, "::", (s, s2) => {
			let tuple = parseValue(state, s, depth);
			return new Some(new Tuple2Impl(tuple.left(), new DataAccess(tuple.right(), s2, /* Primitive */.Unknown)));
		});
	}
	parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		/* if (stripped.startsWith("!"))  */{
			let slice = stripped.substring(1);
			let tuple = parseValue(state, /* slice */, depth);
			let value = tuple.right();
			return new Some(new Tuple2Impl(tuple.left(), new Not(value)));
		}
		return new None();
	}
	parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* first */(input, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow.strip();
			/* if (isSymbol(strippedBeforeArrow))  */{
				let type : Type = /* Primitive */.Unknown;
				/* if (state.typeRegister instanceof Some(var expectedType))  */{
					/* if (expectedType instanceof FunctionType functionType)  */{
						let /* type  */ = /* functionType */.arguments.get(0).orElse(/* null */);
					}
				}
				return /* assembleLambda */(state, /* Lists */.of(/* Definition */.createSimpleDefinition(/* strippedBeforeArrow */, type)), valueString, depth);
			}
			/* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */{
				let parameterNames = divideAll(/* strippedBeforeArrow */.substring(1, /* strippedBeforeArrow */.length() - 1), /* Main */.foldValueChar).iterate().map(/* String */.strip).filter((value : T) => !value.isEmpty()).map((name : T) => /* Definition */.createSimpleDefinition(name, /* Primitive */.Unknown)).collect(new ListCollector());
				return /* assembleLambda */(state, /* parameterNames */, valueString, depth);
			}
			return new None();
		});
	}
	assembleLambda(state : CompileState, definitions : List<Definition>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString = valueString.strip();
		/* Tuple2Impl<CompileState, LambdaValue> value */;
		let state2 = state.withDefinitions(definitions);
		/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */{
			let value1 = compileStatements(/* state2 */, /* strippedValueString */.substring(1, /* strippedValueString */.length() - 1), (state1, input1) => compileFunctionSegment(state1, input1, depth + 1));
			let right = /* value1 */.right();
			let /* value  */ = new Tuple2Impl(/* value1 */.left(), new BlockLambdaValue(right, depth));
		}
		/* else  */{
			let value1 = parseValue(/* state2 */, /* strippedValueString */, depth);
			let /* value  */ = new Tuple2Impl(/* value1 */.left(), /* value1 */.right());
		}
		let right = value.right();
		return new Some(new Tuple2Impl(value.left(), new Lambda(definitions, right)));
	}
	parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		/* if (isNumber(stripped))  */{
			return new Some(new Tuple2Impl<CompileState, Value>(state, new SymbolValue(stripped, /* Primitive */.Int)));
		}
		return new None();
	}
	isNumber(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c = input.charAt(/* i */);
			/* if (Character.isDigit(c))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	parseInvokable(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return suffix(input.strip(), ")", (withoutEnd) => {
			return /* split */(() => /* toLast */(withoutEnd, "", /* Main */.foldInvocationStart), (callerWithEnd, argumentsString) => {
				return suffix(callerWithEnd, "(", (callerString) => {
					return /* assembleInvokable */(state, depth, argumentsString, callerString.strip());
				});
			});
		});
	}
	assembleInvokable(state : CompileState, depth : number, argumentsString : string, callerString : string) : Some<[CompileState, Value]> {
		let callerTuple = /* invocationHeader */(state, depth, callerString);
		let oldCallerState = /* callerTuple */.left();
		let oldCaller = /* callerTuple */.right();
		let newCaller = /* modifyCaller */(/* oldCallerState */, /* oldCaller */);
		let callerType = /* findCallerType */(/* newCaller */);
		let argumentsTuple = /* parseValuesWithIndices */(/* oldCallerState */, argumentsString, (currentState, pair) => {
			let index = pair.left();
			let element = pair.right();
			let expectedType = /* callerType */.arguments.get(index).orElse(/* Primitive */.Unknown);
			let withExpected = currentState.withExpectedType(/* expectedType */);
			let valueTuple = /* parseArgument */(/* withExpected */, element, depth);
			let valueState = /* valueTuple */.left();
			let value = /* valueTuple */.right();
			let actualType = /* valueTuple */.left().typeRegister.orElse(/* Primitive */.Unknown);
			return new Some(new Tuple2Impl(/* valueState */, new Tuple2Impl(value, /* actualType */)));
		}).orElseGet(() => new Tuple2Impl(/* oldCallerState */, /* Lists */.empty()));
		let argumentsState = /* argumentsTuple */.left();
		let argumentsWithActualTypes = /* argumentsTuple */.right();
		let arguments = /* argumentsWithActualTypes */.iterate().map(/* Tuple2 */.left).map(/* Main */.retainValue).flatMap(/* Iterators */.fromOption).collect(new ListCollector());
		let invokable = new Invokable(/* newCaller */, /* arguments */, /* callerType */.returns);
		return new Some(new Tuple2Impl(/* argumentsState */, /* invokable */));
	}
	retainValue(argument : Argument) : Option<Value> {
		/* if (argument instanceof Value value)  */{
			return new Some(value);
		}
		return new None();
	}
	parseArgument(state : CompileState, element : string, depth : number) : [CompileState, Argument] {
		/* if (element.isEmpty())  */{
			return new Tuple2Impl(state, new Whitespace());
		}
		let tuple = parseValue(state, element, depth);
		return new Tuple2Impl(tuple.left(), tuple.right());
	}
	findCallerType(newCaller : Caller) : FunctionType {
		let callerType : FunctionType = new FunctionType(/* Lists */.empty(), /* Primitive */.Unknown);
		/* switch (newCaller)  */{
			/* case ConstructionCaller constructionCaller ->  */{
				let /* callerType  */ = /* constructionCaller */.toFunction();
			}
			/* case Value value ->  */{
				let type = value.type();
				/* if (type instanceof FunctionType functionType)  */{
					let /* callerType  */ = /* functionType */;
				}
			}
		}
		return /* callerType */;
	}
	modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		/* if (oldCaller instanceof DataAccess access)  */{
			let type = resolveType(/* access */.parent, state);
			/* if (type instanceof FunctionType)  */{
				return /* access */.parent;
			}
		}
		return oldCaller;
	}
	resolveType(value : Value, state : CompileState) : Type {
		/* return switch (value)  */{
			/* case DataAccess dataAccess -> Primitive.Unknown */;
			/* case Invokable invokable -> Primitive.Unknown */;
			/* case Lambda lambda -> Primitive.Unknown */;
			/* case Not not -> Primitive.Unknown */;
			/* case Operation operation -> Primitive.Unknown */;
			/* case Placeholder placeholder -> Primitive.Unknown */;
			/* case StringValue stringValue -> Primitive.Unknown */;
			/* case SymbolValue symbolValue -> symbolValue.type */;
			/* case IndexValue indexValue -> Primitive.Unknown */;
		}
		/*  */;
	}
	invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		/* if (callerString1.startsWith("new "))  */{
			let input1 : string = callerString1.substring("new ".length());
			let map = /* parseType */(state, input1).map((type) => {
				let right = type();
				return new Tuple2Impl<CompileState, Caller>(type(), new ConstructionCaller(right));
			});
			/* if (map.isPresent())  */{
				return map(/* null */);
			}
		}
		let tuple = parseValue(state, callerString1, depth);
		return new Tuple2Impl(tuple.left(), tuple.right());
	}
	foldInvocationStart(state : DivideState, c : /* char */) : DivideState {
		let appended = state.append(c);
		/* if (c == '(')  */{
			let enter = /* appended */.enter();
			/* if (enter.isShallow())  */{
				return enter();
			}
			return enter;
		}
		/* if (c == ')')  */{
			return /* appended */.exit();
		}
		return /* appended */;
	}
	parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* last */(input.strip(), ".", (parentString, rawProperty) => {
			let property = rawProperty.strip();
			/* if (!isSymbol(property))  */{
				return new None();
			}
			let tuple = parseValue(state, parentString, depth);
			let parent = tuple.right();
			let parentType = /* parent */.type();
			/* if (parentType instanceof TupleType)  */{
				/* if (property.equals("left"))  */{
					return new Some(new Tuple2Impl(state, new IndexValue(/* parent */, new SymbolValue("0", /* Primitive */.Int))));
				}
				/* if (property.equals("right"))  */{
					return new Some(new Tuple2Impl(state, new IndexValue(/* parent */, new SymbolValue("1", /* Primitive */.Int))));
				}
			}
			let type : Type = /* Primitive */.Unknown;
			/* if (parentType instanceof FindableType objectType)  */{
				/* if (objectType.find(property) instanceof Some(var memberType))  */{
					let /* type  */ = /* memberType */;
				}
			}
			return new Some(new Tuple2Impl(tuple.left(), new DataAccess(/* parent */, /* property */, type)));
		});
	}
	parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped = input.strip();
		/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
			return new Some(new Tuple2Impl(state, new StringValue(stripped)));
		}
		return new None();
	}
	parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped = value.strip();
		/* if (isSymbol(stripped))  */{
			/* if (state.resolveValue(stripped) instanceof Some(var type))  */{
				return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
			}
			return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
		}
		return new None();
	}
	parseOperation(state : CompileState, value : string, depth : number, infix : string) : Option<[CompileState, Value]> {
		return /* first */(value, infix, (s, s2) => {
			let tuple = parseValue(state, s, depth);
			let tuple1 = parseValue(tuple.left(), s2, depth);
			let left = tuple.right();
			let right = /* tuple1 */.right();
			return new Some(new Tuple2Impl(/* tuple1 */.left(), new Operation(left, infix, right)));
		});
	}
	compileValues(state : CompileState, params : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed = /* parseValuesOrEmpty */(state, params, (state1, s) => new Some(mapper(state1, s)));
		let generated = /* generateValues */(/* parsed */.right());
		return new Tuple2Impl(/* parsed */.left(), /* generated */);
	}
	generateValues(elements : List<string>) : string {
		return generateAll(/* Main */.mergeValues, elements);
	}
	parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return /* parseValues */(state, input, mapper).orElseGet(() => new Tuple2Impl(state, /* Lists */.empty()));
	}
	parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return /* parseValuesWithIndices */(state, input, (state1, tuple) => mapper(state1, tuple.right()));
	}
	parseValuesWithIndices<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : [number, string]) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return parseAll(state, input, /* Main */.foldValueChar, mapper);
	}
	compileParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		/* if (input.isBlank())  */{
			return new Tuple2Impl(state, new Whitespace());
		}
		return /* parseDefinition */(state, input).map((tuple) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
	}
	compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return /* parseDefinition */(state, input).map((tuple) => new Tuple2Impl(tuple.left(), tuple.right().generate())).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */(input)));
	}
	mergeValues(cache : /* StringBuilder */, element : string) : /* StringBuilder */ {
		/* if (cache.isEmpty())  */{
			return cache.append(element);
		}
		return cache.append(", ").append(element);
	}
	createIndent(depth : number) : string {
		return "\n" + "\t".repeat(depth);
	}
	compileDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return suffix(input.strip(), ";", (withoutEnd) => {
			return /* parseDefinition */(state, withoutEnd).map((result) => {
				let generated = createIndent(depth) + result.right().generate() + ";";
				return new Tuple2Impl(result.left(), /* generated */);
			});
		});
	}
	parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return /* last */(input.strip(), " ", (beforeName, name) => {
			return /* split */(() => /* toLast */(beforeName, " ", /* Main */.foldTypeSeparator), (beforeType, type) => {
				return suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
					return /* first */(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
						let compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1, s) => new Tuple2Impl(state1, s.strip());
						let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(/* compileStateStringTupleBiFunction */.apply(state1, s)));
						return /* assembleDefinition */(typeParams(), new Some<string>(beforeTypeParams), name, typeParams(), type);
					});
				}).or(() => {
					return /* assembleDefinition */(state, new Some<string>(beforeType), name, /* Lists */.empty(), type);
				});
			}).or(() => {
				return /* assembleDefinition */(state, new None<string>(), name, /* Lists */.empty(), beforeName);
			});
		});
	}
	toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState) : Option<[string, string]> {
		let divisions = divideAll(input, folder);
		return /* divisions */.removeLast().map((removed) => {
			let left = removed.left().iterate().collect(new Joiner(separator)).orElse("");
			let right = removed.right();
			return new Tuple2Impl(left, right);
		});
	}
	foldTypeSeparator(state : DivideState, c : /* Character */) : DivideState {
		/* if (c == ' ' && state.isLevel())  */{
			return state.advance();
		}
		let appended = state.append(c);
		/* if (c == '<')  */{
			return /* appended */.enter();
		}
		/* if (c == '>')  */{
			return /* appended */.exit();
		}
		return /* appended */;
	}
	assembleDefinition(state : CompileState, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return /* parseType */(state.withTypeParams(typeParams), type).map((type1) => {
			let node = new Definition(beforeTypeParams, name.strip(), type1.right(), typeParams);
			return new Tuple2Impl(type1.left(), /* node */);
		});
	}
	foldValueChar(state : DivideState, c : /* char */) : DivideState {
		/* if (c == ',' && state.isLevel())  */{
			return state.advance();
		}
		let appended = state.append(c);
		/* if (c == '-')  */{
			let peeked = /* appended */.peek();
			/* if (peeked == '>')  */{
				return /* appended */.popAndAppendToOption().orElse(/* appended */);
			}
			/* else  */{
				return /* appended */;
			}
		}
		/* if (c == '<' || c == '(' || c == '{')  */{
			return /* appended */.enter();
		}
		/* if (c == '>' || c == ')' || c == '}')  */{
			return /* appended */.exit();
		}
		return /* appended */;
	}
	compileType(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* parseType */(state, input).map((tuple) => new Tuple2Impl(tuple.left(), tuple.right().generate()));
	}
	parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped = input.strip();
		/* if (stripped.equals("int") || stripped.equals("Integer"))  */{
			return new Some(new Tuple2Impl(state, /* Primitive */.Int));
		}
		/* if (stripped.equals("String"))  */{
			return new Some(new Tuple2Impl(state, /* Primitive */.String));
		}
		/* if (stripped.equals("var"))  */{
			return new Some(new Tuple2Impl(state, /* Primitive */.Unknown));
		}
		/* if (stripped.equals("boolean"))  */{
			return new Some(new Tuple2Impl(state, /* Primitive */.Boolean));
		}
		/* if (isSymbol(stripped))  */{
			/* if (state.resolveType(stripped) instanceof Some(var resolved))  */{
				return new Some(new Tuple2Impl(state, /* resolved */));
			}
			/* else  */{
				return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
			}
		}
		return /* parseTemplate */(state, input).or(() => /* varArgs */(state, input));
	}
	varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix(input, "...", (s) => {
			return parseType(state, s).map((inner : [CompileState, Type]) => {
				let newState = inner.left();
				let child = inner.right();
				return new Tuple2Impl(/* newState */, new ArrayType(/* child */));
			});
		});
	}
	parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return suffix(input.strip(), ">", (withoutEnd) => {
			return /* first */(withoutEnd, "<", (base, argumentsString) => {
				let strippedBase = base.strip();
				return parseValues(state, argumentsString, /* Main */.argument).map((argumentsTuple : [CompileState, List<T>]) => {
					return /* assembleTemplate */(/* strippedBase */, argumentsTuple[0](), argumentsTuple[1]());
				});
			});
		});
	}
	assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children = arguments.iterate().map(/* Main */.retainType).flatMap(/* Iterators */.fromOption).collect(new ListCollector());
		/* if (base.equals("BiFunction"))  */{
			return new Tuple2Impl(state, new FunctionType(/* Lists */.of(/* children */.get(0).orElse(/* null */), /* children */.get(1).orElse(/* null */)), /* children */.get(2).orElse(/* null */)));
		}
		/* if (base.equals("Function"))  */{
			return new Tuple2Impl(state, new FunctionType(/* Lists */.of(/* children */.get(0).orElse(/* null */)), /* children */.get(1).orElse(/* null */)));
		}
		/* if (base.equals("Predicate"))  */{
			return new Tuple2Impl(state, new FunctionType(/* Lists */.of(/* children */.get(0).orElse(/* null */)), /* Primitive */.Boolean));
		}
		/* if (base.equals("Supplier"))  */{
			return new Tuple2Impl(state, new FunctionType(/* Lists */.empty(), /* children */.get(0).orElse(/* null */)));
		}
		/* if (base.equals("Tuple2") && children.size() >= 2)  */{
			return new Tuple2Impl(state, new TupleType(/* children */));
		}
		/* if (state.resolveType(base) instanceof Some(var baseType) && baseType instanceof FindableType findableType)  */{
			return new Tuple2Impl(state, new Template(/* findableType */, /* children */));
		}
		return new Tuple2Impl(state, new Template(new Placeholder(base), /* children */));
	}
	retainType(argument : Argument) : Option<Type> {
		/* if (argument instanceof Type type)  */{
			return new Some(type);
		}
		/* else  */{
			return new None<Type>();
		}
	}
	argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple2Impl(state, new Whitespace()));
		}
		return parseType(state, input).map((tuple : [CompileState, Type]) => new Tuple2Impl(tuple.left(), tuple.right()));
	}
	last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix(input, infix, /* Main */.findLast, mapper);
	}
	findLast(input : string, infix : string) : Option<number> {
		let index = input.lastIndexOf(infix);
		/* if (index == -1)  */{
			return new None<number>();
		}
		return new Some(index);
	}
	first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix(input, infix, /* Main */.findFirst, mapper);
	}
	split<T>(splitter : () => Option<[string, string]>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter().flatMap((splitTuple : [string, string]) => splitMapper(splitTuple[0](), splitTuple[1]()));
	}
	infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return split(() => locator(input, infix).map((index : number) => {
			let left = input.substring(0, index);
			let right = input.substring(index + infix.length());
			return new Tuple2Impl(left, right);
		}), mapper);
	}
	findFirst(input : string, infix : string) : Option<number> {
		let index = input.indexOf(infix);
		/* if (index == -1)  */{
			return new None<number>();
		}
		return new Some(index);
	}
	generatePlaceholder(input : string) : string {
		let replaced = input.replace("/*", "content-start").replace("*/", "content-end");
		return "/* " + replaced + " */";
	}
	createDebugString(type : Type) : string {
		/* if (!Main.isDebug)  */{
			return "";
		}
		return generatePlaceholder(": " + type.generate());
	}
}
/*  */
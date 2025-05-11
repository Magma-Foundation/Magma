/* private */interface Option<T>/*   */ {
	map<R>(mapper : (arg0 : T) => R) : /* Option */<R>;
	isPresent() : boolean;
	orElse(other : T) : T;
	filter(predicate : (arg0 : T) => boolean) : /* Option */<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => /* Option */<T>) : /* Option */<T>;
	flatMap<R>(mapper : (arg0 : T) => /* Option */<R>) : /* Option */<R>;
	isEmpty() : boolean;
}
/* private */interface Collector<T, C>/*   */ {
	createInitial() : C;
	fold(current : C, element : T) : C;
}
/* private */interface Iterator<T>/*   */ {
	fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R;
	map<R>(mapper : (arg0 : T) => R) : /* Iterator */<R>;
	collect<R>(collector : /* Collector */<T, R>) : R;
	filter(predicate : (arg0 : T) => boolean) : /* Iterator */<T>;
	next() : /* Option */<T>;
	flatMap<R>(f : (arg0 : T) => /* Iterator */<R>) : /* Iterator */<R>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : /* List */<T>;
	iterate() : /* Iterator */<T>;
	removeLast() : /* Option */<[/* List */<T>, T]>;
	get(index : number) : T;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : /* List */<T>;
	iterateWithIndices() : /* Iterator */<[number, T]>;
	removeFirst() : /* Option */<[T, /* List */<T>]>;
}
/* private */interface Head<T>/*   */ {
	next() : /* Option */<T>;
}
/* private */interface Argument/*  */ {
}
/* private */interface Parameter/*  */ {
}
/* private */interface LambdaValue/*  */ {
	generate() : string;
}
/* private sealed */interface Caller/*  */ {
	generate() : string;
}
/* private */class Some<T>/*  */ {
	constructor (value : T) {
	}

	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : /* Option */<R> {
		return new /* Some */(mapper/*  : (arg0 : T) => R */(/* this */.value)/* : R */)/* : content-start Some content-end */;
	}
	/* @Override
        public */ isPresent() : boolean {
		return /* true */;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return /* this */.value;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : /* Option */<T> {
		/* if (predicate.test(this.value))  */{
			return /* this */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return /* this */.value;
	}
	/* @Override
        public */ or(other : () => /* Option */<T>) : /* Option */<T> {
		return /* this */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => /* Option */<R>) : /* Option */<R> {
		return mapper/*  : (arg0 : T) => content-start Option content-end<R> */(/* this */.value)/* : content-start Option content-end<R> */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return /* false */;
	}
}
/* private static */class None<T>/*  */ {
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : /* Option */<R> {
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* @Override
        public */ isPresent() : boolean {
		return /* false */;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return other/*  : T */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : /* Option */<T> {
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return supplier/*  : () => T */(/*  */)/* : T */;
	}
	/* @Override
        public */ or(other : () => /* Option */<T>) : /* Option */<T> {
		return other/*  : () => content-start Option content-end<T> */(/*  */)/* : content-start Option content-end<T> */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => /* Option */<R>) : /* Option */<R> {
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return /* true */;
	}
}
/* private static */class SingleHead<T>/*  */ {
	/* private final */ value : T;
	/* private */ retrieved : boolean;
	SingleHead(value : T) : public {
		let /* this.value  */ = value/*  : T */;
		let /* this.retrieved  */ = /* false */;
	}
	/* @Override
        public */ next() : /* Option */<T> {
		/* if (this.retrieved)  */{
			return new /* None */(/*  */)/* : content-start None content-end */;
		}
		let /* this.retrieved  */ = /* true */;
		return new /* Some */(/* this */.value)/* : content-start Some content-end */;
	}
}
/* private static */class EmptyHead<T>/*  */ {
	/* @Override
        public */ next() : /* Option */<T> {
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
}
/* private */class HeadedIterator<T>/*  */ {
	constructor (head : /* Head */<T>) {
	}

	/* @Override
        public  */ fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current : var = initial/*  : R */;
		/* while (true)  */{
			let finalCurrent : R = /* current */;
			let optional : var = /* this */.head.next(/*  */)/* : unknown */.map((inner : unknown) => folder/*  : (arg0 : R, arg1 : T) => R */(/* finalCurrent */, /* inner */)/* : R */)/* : unknown */;
			/* if (optional.isPresent())  */{
				let /* current  */ = /* optional */.orElse(/* null */)/* : unknown */;
			}
			/* else  */{
				return /* current */;
			}
		}
	}
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : /* Iterator */<R> {
		return new /* HeadedIterator */(() => /* this */.head.next(/*  */)/* : unknown */.map(mapper/*  : (arg0 : T) => R */)/* : unknown */)/* : content-start HeadedIterator content-end */;
	}
	/* @Override
        public  */ collect<R>(collector : /* Collector */<T, R>) : R {
		return /* this */.fold(collector/*  : content-start Collector content-end<T, R> */.createInitial(/*  */)/* : unknown */, collector/*  : content-start Collector content-end<T, R> */.fold)/* : unknown */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : /* Iterator */<T> {
		return /* this */.flatMap((element : unknown) => {
			/* if (predicate.test(element))  */{
				return new /* HeadedIterator */(new /* SingleHead */(/* element */)/* : content-start SingleHead content-end */)/* : content-start HeadedIterator content-end */;
			}
			return new /* HeadedIterator */(new /* EmptyHead */(/*  */)/* : content-start EmptyHead content-end */)/* : content-start HeadedIterator content-end */;
		})/* : unknown */;
	}
	/* @Override
        public */ next() : /* Option */<T> {
		return /* this */.head.next(/*  */)/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : T) => /* Iterator */<R>) : /* Iterator */<R> {
		return new /* HeadedIterator */(new /* FlatMapHead */(/* this */.head, f/*  : (arg0 : T) => content-start Iterator content-end<R> */)/* : content-start FlatMapHead content-end */)/* : content-start HeadedIterator content-end */;
	}
}
/* private static */class RangeHead/*  */ {
	/* private final */ length : number;
	/* private */ counter : number;
	RangeHead(length : number) : public {
		let /* this.length  */ = length/*  : number */;
	}
	/* @Override
        public */ next() : /* Option */<number> {
		/* if (this.counter < this.length)  */{
			let value : var = /* this */.counter;
			/* this.counter++ */;
			return new /* Some */(/* value */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
}
/* private static final */class JVMList<T>/*  */ {
	/* private final */ elements : /* java.util.List */<T>;
	JVMList(elements : /* java.util.List */<T>) : private {
		let /* this.elements  */ = elements/*  : content-start java.util.List content-end<T> */;
	}
	JVMList() : public {
		/* this(new ArrayList<>()) */;
	}
	/* @Override
            public */ addLast(element : T) : /* List */<T> {
		/* this.elements.add(element) */;
		return /* this */;
	}
	/* @Override
            public */ iterate() : /* Iterator */<T> {
		return /* this */.iterateWithIndices(/*  */)/* : unknown */.map(/* Tuple */.right)/* : unknown */;
	}
	/* @Override
            public */ removeLast() : /* Option */<[/* List */<T>, T]> {
		/* if (this.elements.isEmpty())  */{
			return new /* None */(/*  */)/* : content-start None content-end */;
		}
		let slice : var = /* this */.elements.subList(0/*  : number */, /* this */.elements.size(/*  */)/* : unknown */ - 1/*  : number */)/* : unknown */;
		let last : var = /* this */.elements.getLast(/*  */)/* : unknown */;
		return new /* Some */(new [/* List */<T>, T](new /* JVMList */(/* slice */)/* : content-start JVMList content-end */, /* last */)/* : [content-start List content-end<T>, T] */)/* : content-start Some content-end */;
	}
	/* @Override
            public */ get(index : number) : T {
		return /* this */.elements.get(index/*  : number */)/* : unknown */;
	}
	/* @Override
            public */ size() : number {
		return /* this */.elements.size(/*  */)/* : unknown */;
	}
	/* @Override
            public */ isEmpty() : boolean {
		return /* this */.elements.isEmpty(/*  */)/* : unknown */;
	}
	/* @Override
            public */ addFirst(element : T) : /* List */<T> {
		/* this.elements.addFirst(element) */;
		return /* this */;
	}
	/* @Override
            public */ iterateWithIndices() : /* Iterator */<[number, T]> {
		return new /* HeadedIterator */(new RangeHead(/* this */.elements.size(/*  */)/* : unknown */)/* : RangeHead */)/* : content-start HeadedIterator content-end */.map((index : unknown) => new /* Tuple */(/* index */, /* this */.elements.get(/* index */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* @Override
            public */ removeFirst() : /* Option */<[T, /* List */<T>]> {
		/* if (this.elements.isEmpty())  */{
			return new /* None */(/*  */)/* : content-start None content-end */;
		}
		let first : var = /* this */.elements.getFirst(/*  */)/* : unknown */;
		let slice : var = /* this */.elements.subList(1/*  : number */, /* this */.elements.size(/*  */)/* : unknown */)/* : unknown */;
		return new /* Some */(new [T, /* List */<T>](/* first */, new /* JVMList */(/* slice */)/* : content-start JVMList content-end */)/* : [T, content-start List content-end<T>] */)/* : content-start Some content-end */;
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : /* List */<T> {
		return new /* JVMList */(/*  */)/* : content-start JVMList content-end */;
	}
	/* public static  */ of<T>(elements : T[]) : /* List */<T> {
		return new /* JVMList */(new /* ArrayList */(/* Arrays */.asList(elements/*  : T[] */)/* : unknown */)/* : content-start ArrayList content-end */)/* : content-start JVMList content-end */;
	}
}
/* private */class ObjectType/*  */ {
	constructor (name : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.name;
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : /* List */<string>;
	/* private */ buffer : StringBuilder;
	DivideState(input : string, index : number, segments : /* List */<string>, buffer : StringBuilder, depth : number) : public {
		let /* this.segments  */ = segments/*  : content-start List content-end<string> */;
		let /* this.buffer  */ = buffer/*  : StringBuilder */;
		let /* this.depth  */ = depth/*  : number */;
		let /* this.input  */ = input/*  : string */;
		let /* this.index  */ = index/*  : number */;
	}
	DivideState(input : string) : public {
		/* this(input, 0, Lists.empty(), new StringBuilder(), 0) */;
	}
	/* private */ advance() : DivideState {
		let /* this.segments  */ = /* this */.segments.addLast(/* this */.buffer.toString(/*  */)/* : unknown */)/* : unknown */;
		let /* this.buffer  */ = new StringBuilder(/*  */)/* : StringBuilder */;
		return /* this */;
	}
	/* private */ append(c : char) : DivideState {
		/* this.buffer.append(c) */;
		return /* this */;
	}
	/* public */ enter() : DivideState {
		/* this.depth++ */;
		return /* this */;
	}
	/* public */ isLevel() : boolean {
		return /* this.depth == 0 */;
	}
	/* public */ exit() : DivideState {
		/* this.depth-- */;
		return /* this */;
	}
	/* public */ isShallow() : boolean {
		return /* this.depth == 1 */;
	}
	/* public */ pop() : /* Option */<[Character, DivideState]> {
		/* if (this.index < this.input.length())  */{
			let c : var = /* this */.input.charAt(/* this */.index)/* : unknown */;
			return new /* Some */(new /* Tuple */(/* c */, new DivideState(/* this */.input, /* this */.index + 1/*  : number */, /* this */.segments, /* this */.buffer, /* this */.depth)/* : DivideState */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* public */ popAndAppendToTuple() : /* Option */<[Character, DivideState]> {
		return /* this */.pop(/*  */)/* : unknown */.map((tuple : unknown) => new /* Tuple */(/* tuple */.left, /* tuple */.right.append(/* tuple */.left)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* public */ popAndAppendToOption() : /* Option */<DivideState> {
		return /* this */.popAndAppendToTuple(/*  */)/* : unknown */.map(/* Tuple */.right)/* : unknown */;
	}
	/* public */ peek() : char {
		return /* this */.input.charAt(/* this */.index)/* : unknown */;
	}
}
/* private */class Joiner/*  */ {
	constructor (delimiter : string) {
	}

	Joiner() : private {
		/* this("") */;
	}
	/* @Override
        public */ createInitial() : /* Option */<string> {
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* @Override
        public */ fold(current : /* Option */<string>, element : string) : /* Option */<string> {
		return new /* Some */(current/*  : content-start Option content-end<string> */.map((inner : unknown) => /* inner */ + /* this */.delimiter + element/*  : string */)/* : unknown */.orElse(element/*  : string */)/* : unknown */)/* : content-start Some content-end */;
	}
}
/* private */class Definition/*  */ {
	constructor (maybeBefore : /* Option */<string>, type : Type, name : string, typeParams : /* List */<string>) {
	}

	/* private */ generate() : string {
		return /* this */.generateWithParams("")/* : unknown */;
	}
	/* public */ generateWithParams(params : string) : string {
		let joined : var = /* this */.joinTypeParams(/*  */)/* : unknown */;
		let before : var = /* this */.joinBefore(/*  */)/* : unknown */;
		let typeString : var = /* this */.generateType(/*  */)/* : unknown */;
		return /* before */ + /* this */.name + /* joined */ + params/*  : string */ + /* typeString */;
	}
	/* private */ generateType() : string {
		/* if (this.type.equals(Primitive.Unknown))  */{
			return "";
		}
		return " : " + /* this */.type.generate(/*  */)/* : unknown */;
	}
	/* private */ joinBefore() : string {
		return /* this */.maybeBefore.filter((value : unknown) => !/* value */.isEmpty(/*  */)/* : unknown */)/* : unknown */.map(/* Main */.generatePlaceholder)/* : unknown */.map((inner : unknown) => /* inner */ + " ")/* : unknown */.orElse("")/* : unknown */;
	}
	/* private */ joinTypeParams() : string {
		return /* this */.typeParams.iterate(/*  */)/* : unknown */.collect(new Joiner(/*  */)/* : Joiner */)/* : unknown */.map((inner : unknown) => "<" + inner + ">")/* : unknown */.orElse("")/* : unknown */;
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : /* List */<T> {
		return /* Lists */.empty(/*  */)/* : unknown */;
	}
	/* @Override
        public */ fold(current : /* List */<T>, element : T) : /* List */<T> {
		return current/*  : content-start List content-end<T> */.addLast(element/*  : T */)/* : unknown */;
	}
}
/* private */class Tuple<A, B>/* (A left, B right)  */ {
}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : T) => /* Iterator */<R>;
	/* private final */ head : /* Head */<T>;
	/* private */ current : /* Option */</* Iterator */<R>>;
	FlatMapHead(head : /* Head */<T>, mapper : (arg0 : T) => /* Iterator */<R>) : public {
		let /* this.mapper  */ = mapper/*  : (arg0 : T) => content-start Iterator content-end<R> */;
		let /* this.current  */ = new /* None */(/*  */)/* : content-start None content-end */;
		let /* this.head  */ = head/*  : content-start Head content-end<T> */;
	}
	/* @Override
        public */ next() : /* Option */<R> {
		/* while (true)  */{
			/* if (this.current.isPresent())  */{
				let inner : /* Iterator */<R> = /* this */.current.orElse(/* null */)/* : unknown */;
				let maybe : /* Option */<R> = /* inner */.next(/*  */)/* : unknown */;
				/* if (maybe.isPresent())  */{
					return /* maybe */;
				}
				/* else  */{
					let /* this.current  */ = new /* None */(/*  */)/* : content-start None content-end */;
				}
			}
			let outer : /* Option */<T> = /* this */.head.next(/*  */)/* : unknown */;
			/* if (outer.isPresent())  */{
				let /* this.current  */ = /* outer */.map(/* this */.mapper)/* : unknown */;
			}
			/* else  */{
				return new /* None */(/*  */)/* : content-start None content-end */;
			}
		}
	}
}
/* private */class SymbolType/*  */ {
	constructor (input : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.input;
	}
}
/* private static */class ArrayType/*  */ {
	/* private final */ right : Type;
	ArrayType(right : Type) : public {
		let /* this.right  */ = right/*  : Type */;
	}
	/* @Override
        public */ generate() : string {
		return /* this */.right.generate(/*  */)/* : unknown */ + "[]";
	}
}
/* private static */class Whitespace/*  */ {
}
/* private static */class Iterators/*  */ {
	/* public static  */ fromOption<T>(option : /* Option */<T>) : /* Iterator */<T> {
		let single : /* Option */</* Head */<T>> = option/*  : content-start Option content-end<T> */.map(/* SingleHead */.new)/* : unknown */;
		return new /* HeadedIterator */(/* single */.orElseGet(/* EmptyHead */.new)/* : unknown */)/* : content-start HeadedIterator content-end */;
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : /* List */<Type>, returns : Type) {
	}

	/* @Override
        public */ generate() : string {
		let joined : var = /* this */.arguments(/*  */)/* : unknown */.iterateWithIndices(/*  */)/* : unknown */.map((pair : unknown) => "arg" + /* pair */.left + " : " + /* pair */.right.generate(/*  */)/* : unknown */)/* : unknown */.collect(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
		return "(" + /* joined */ + ") => " + /* this */.returns.generate(/*  */)/* : unknown */;
	}
}
/* private */class TupleType/*  */ {
	constructor (arguments : /* List */<Type>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments : var = /* this */.arguments.iterate(/*  */)/* : unknown */.map(/* Type */.generate)/* : unknown */.collect(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
}
/* private */class Template/*  */ {
	constructor (base : Type, arguments : /* List */<Type>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments : var = /* this */.arguments.iterate(/*  */)/* : unknown */.map(/* Type */.generate)/* : unknown */.collect(new Joiner(", ")/* : Joiner */)/* : unknown */.map((inner : unknown) => "<" + inner + ">")/* : unknown */.orElse("")/* : unknown */;
		return /* this */.base.generate(/*  */)/* : unknown */ + /* joinedArguments */;
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* generatePlaceholder */(/* this */.input)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class StringValue/*  */ {
	constructor (stripped : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.stripped;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : Value, property : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.parent.generate(/*  */)/* : unknown */ + "." + /* this */.property;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : Type) {
	}

	/* @Override
        public */ generate() : string {
		return "new " + /* this */.type.generate(/*  */)/* : unknown */;
	}
}
/* private */class Operation/*  */ {
	constructor (left : Value, infix : string, right : Value) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.left.generate(/*  */)/* : unknown */ + " " + /* this */.infix + " " + /* this */.right.generate(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class Not/*  */ {
	constructor (value : Value) {
	}

	/* @Override
        public */ generate() : string {
		return "!" + /* this */.value.generate(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown;
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
	constructor (parameterNames : /* List */<string>, body : LambdaValue) {
	}

	/* @Override
        public */ generate() : string {
		let joined : var = /* this */.parameterNames.iterate(/*  */)/* : unknown */.map((inner : unknown) => /* inner */ + " : unknown")/* : unknown */.collect(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
		return "(" + /* joined */ + ") => " + /* this */.body.generate(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : Caller, arguments : /* List */<Value>, returnsType : Type) {
	}

	/* @Override
        public */ generate() : string {
		let joined : var = /* this */.arguments.iterate(/*  */)/* : unknown */.map(/* Value */.generate)/* : unknown */.collect(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
		return /* this */.caller.generate(/*  */)/* : unknown */ + "(" + /* joined */ + ")" + /* generatePlaceholder */(": " + /* this */.returnsType.generate(/*  */)/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : Value, child : Value) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.parent.generate(/*  */)/* : unknown */ + "[" + this.child.generate() + "]";
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (stripped : string, type : Type) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.stripped + /* generatePlaceholder */(" : " + /* this */.type.generate(/*  */)/* : unknown */)/* : unknown */;
	}
}
/* public */class Main/*  */ {/* 

    private interface Type extends Argument {
        String generate();
    } *//* 

    private sealed interface Value extends LambdaValue, Caller {
        String generate();

        Type type();
    } */
	/* private */ CompileState(structures : /* List */<string>, definitions : /* List */<Definition>, types : /* List */<ObjectType>) : record {
		/* public CompileState()  */{
			/* this(Lists.empty(), Lists.empty(), Lists.empty()) */;
		}
		/* private Option<Type> resolveValue(String name)  */{
			return /* this */.definitions.iterate(/*  */)/* : unknown */.filter((definition : unknown) => /* definition */.name.equals(/* name */)/* : unknown */)/* : unknown */.next(/*  */)/* : unknown */.map(/* Definition */.type)/* : unknown */;
		}
		/* public CompileState addStructure(String structure)  */{
			return new CompileState(/* this */.structures.addLast(/* structure */)/* : unknown */, /* this */.definitions, /* this */.types)/* : CompileState */;
		}
		/* public CompileState withDefinitions(List<Definition> definitions)  */{
			return new CompileState(/* this */.structures, definitions/*  : content-start List content-end<Definition> */, /* this */.types)/* : CompileState */;
		}
		/* public Option<ObjectType> resolveType(String name)  */{
			return /* this */.types.iterate(/*  */)/* : unknown */.filter((type : unknown) => /* type */.name.equals(/* name */)/* : unknown */)/* : unknown */.next(/*  */)/* : unknown */;
		}
	}
	/* public static */ main() : void {
		/* try  */{
			let parent : var = /* Paths */.get(".", "src", "java", "magma")/* : unknown */;
			let source : var = /* parent */.resolve("Main.java")/* : unknown */;
			let target : var = /* parent */.resolve("main.ts")/* : unknown */;
			let input : var = /* Files */.readString(/* source */)/* : unknown */;
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
		let tuple : var = /* compileStatements */(new CompileState(/*  */)/* : CompileState */, input/*  : string */, /* Main */.compileRootSegment)/* : unknown */;
		let joined : var = /* tuple */.left.structures.iterate(/*  */)/* : unknown */.collect(new Joiner(/*  */)/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
		return /* joined */ + /* tuple */.right;
	}
	/* private static */ compileStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed : var = /* parseStatements */(state/*  : CompileState */, input/*  : string */, mapper/*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */)/* : unknown */;
		return new /* Tuple */(/* parsed */.left, /* generateStatements */(/* parsed */.right)/* : unknown */)/* : content-start Tuple content-end */;
	}
	/* private static */ generateStatements(statements : /* List */<string>) : string {
		return /* generateAll */(/* Main */.mergeStatements, statements/*  : content-start List content-end<string> */)/* : unknown */;
	}
	/* private static */ parseStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, /* List */<string>] {
		return /* parseAll */(state/*  : CompileState */, input/*  : string */, /* Main */.foldStatementChar, mapper/*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */)/* : unknown */;
	}
	/* private static */ generateAll(merger : (arg0 : StringBuilder, arg1 : string) => StringBuilder, elements : /* List */<string>) : string {
		return elements/*  : content-start List content-end<string> */.iterate(/*  */)/* : unknown */.fold(new StringBuilder(/*  */)/* : StringBuilder */, merger/*  : (arg0 : StringBuilder, arg1 : string) => StringBuilder */)/* : unknown */.toString(/*  */)/* : unknown */;
	}
	/* private static  */ parseAll<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, /* List */<T>] {
		return /* getCompileStateListTuple */(state/*  : CompileState */, input/*  : string */, folder/*  : (arg0 : DivideState, arg1 : Character) => DivideState */, (state1 : unknown, s : unknown) => new /* Some */(mapper/*  : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(/* state1 */, /* s */)/* : [CompileState, T] */)/* : content-start Some content-end */)/* : unknown */.orElseGet(() => new /* Tuple */(state/*  : CompileState */, /* Lists */.empty(/*  */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* private static  */ getCompileStateListTuple<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => /* Option */<[CompileState, T]>) : /* Option */<[CompileState, /* List */<T>]> {
		let initial : /* Option */<[CompileState, /* List */<T>]> = new /* Some */(new /* Tuple */(state/*  : CompileState */, /* Lists */.empty(/*  */)/* : unknown */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		return /* divideAll */(input/*  : string */, folder/*  : (arg0 : DivideState, arg1 : Character) => DivideState */)/* : unknown */.iterate(/*  */)/* : unknown */.fold(/* initial */, (tuple : unknown, element : unknown) => {
			return /* tuple */.flatMap((inner : unknown) => {
				let state1 : var = /* inner */.left;
				let right : var = /* inner */.right;
				return mapper/*  : (arg0 : CompileState, arg1 : string) => content-start Option content-end<[CompileState, T]> */(/* state1 */, /* element */)/* : content-start Option content-end<[CompileState, T]> */.map((applied : unknown) => {
					return new /* Tuple */(/* applied */.left, /* right */.addLast(/* applied */.right)/* : unknown */)/* : content-start Tuple content-end */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ mergeStatements(stringBuilder : StringBuilder, str : string) : StringBuilder {
		return stringBuilder/*  : StringBuilder */.append(str/*  : string */)/* : unknown */;
	}
	/* private static */ divideAll(input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState) : /* List */<string> {
		let current : var = new DivideState(input/*  : string */)/* : DivideState */;
		/* while (true)  */{
			let maybePopped : var = /* current */.pop(/*  */)/* : unknown */.map((tuple : unknown) => {
				return /* foldSingleQuotes */(/* tuple */)/* : unknown */.or(() => /* foldDoubleQuotes */(/* tuple */)/* : unknown */)/* : unknown */.orElseGet(() => folder/*  : (arg0 : DivideState, arg1 : Character) => DivideState */(/* tuple */.right, /* tuple */.left)/* : DivideState */)/* : unknown */;
			})/* : unknown */;
			/* if (maybePopped.isPresent())  */{
				let /* current  */ = /* maybePopped */.orElse(/* current */)/* : unknown */;
			}
			/* else  */{
				/* break */;
			}
		}
		return /* current */.advance(/*  */)/* : unknown */.segments;
	}
	/* private static */ foldDoubleQuotes(tuple : [Character, DivideState]) : /* Option */<DivideState> {
		/* if (tuple.left == '\"')  */{
			let current : var = tuple/*  : [Character, DivideState] */.right.append(tuple/*  : [Character, DivideState] */[0/*  : number */])/* : unknown */;
			/* while (true)  */{
				let maybePopped : var = /* current */.popAndAppendToTuple(/*  */)/* : unknown */;
				/* if (maybePopped.isEmpty())  */{
					/* break */;
				}
				let popped : var = /* maybePopped */.orElse(/* null */)/* : unknown */;
				let /* current  */ = /* popped */.right;
				/* if (popped.left == '\\')  */{
					let /* current  */ = /* current */.popAndAppendToOption(/*  */)/* : unknown */.orElse(/* current */)/* : unknown */;
				}
				/* if (popped.left == '\"')  */{
					/* break */;
				}
			}
			return new /* Some */(/* current */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* private static */ foldSingleQuotes(tuple : [Character, DivideState]) : /* Option */<DivideState> {
		/* if (tuple.left != '\'')  */{
			return new /* None */(/*  */)/* : content-start None content-end */;
		}
		let appended : var = tuple/*  : [Character, DivideState] */.right.append(tuple/*  : [Character, DivideState] */[0/*  : number */])/* : unknown */;
		return /* appended */.popAndAppendToTuple(/*  */)/* : unknown */.map(/* Main */.foldEscaped)/* : unknown */.flatMap(/* DivideState */.popAndAppendToOption)/* : unknown */;
	}
	/* private static */ foldEscaped(escaped : [Character, DivideState]) : DivideState {
		/* if (escaped.left == '\\')  */{
			return escaped/*  : [Character, DivideState] */.right.popAndAppendToOption(/*  */)/* : unknown */.orElse(escaped/*  : [Character, DivideState] */.right)/* : unknown */;
		}
		return escaped/*  : [Character, DivideState] */.right;
	}
	/* private static */ foldStatementChar(state : DivideState, c : char) : DivideState {
		let append : var = state/*  : DivideState */.append(c/*  : char */)/* : unknown */;
		/* if (c == ';' && append.isLevel())  */{
			return /* append */.advance(/*  */)/* : unknown */;
		}
		/* if (c == '}' && append.isShallow())  */{
			return /* append */.advance(/*  */)/* : unknown */.exit(/*  */)/* : unknown */;
		}
		/* if (c == '{' || c == '(')  */{
			return /* append */.enter(/*  */)/* : unknown */;
		}
		/* if (c == '}' || c == ')')  */{
			return /* append */.exit(/*  */)/* : unknown */;
		}
		return /* append */;
	}
	/* private static */ compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped : var = input/*  : string */.strip(/*  */)/* : unknown */;
		/* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */{
			return new /* Tuple */(state/*  : CompileState */, "")/* : content-start Tuple content-end */;
		}
		return /* compileClass */(/* stripped */, 0/*  : number */, state/*  : CompileState */)/* : unknown */.orElseGet(() => new /* Tuple */(state/*  : CompileState */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* private static */ compileClass(stripped : string, depth : number, state : CompileState) : /* Option */<[CompileState, string]> {
		return /* structure */(stripped/*  : string */, "class ", "class ", state/*  : CompileState */)/* : unknown */;
	}
	/* private static */ structure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : /* Option */<[CompileState, string]> {
		return /* first */(stripped/*  : string */, sourceInfix/*  : string */, (beforeInfix : unknown, right : unknown) => {
			return /* first */(/* right */, "{", (beforeContent : unknown, withEnd : unknown) => {
				let strippedWithEnd : var = /* withEnd */.strip(/*  */)/* : unknown */;
				return /* suffix */(/* strippedWithEnd */, "}", (content1 : unknown) => {
					return /* first */(/* beforeContent */, " implements ", (s : unknown, s2 : unknown) => {
						return /* structureWithMaybeParams */(targetInfix/*  : string */, state/*  : CompileState */, /* beforeInfix */, /* s */, /* content1 */)/* : unknown */;
					})/* : unknown */.or(() => {
						return /* structureWithMaybeParams */(targetInfix/*  : string */, state/*  : CompileState */, /* beforeInfix */, /* beforeContent */, /* content1 */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : /* Option */<[CompileState, string]> {
		return /* suffix */(beforeContent/*  : string */, ")", (s : unknown) => {
			return /* first */(/* s */, "(", (s1 : unknown, s2 : unknown) => {
				let parsed : var = /* parseParameters */(state/*  : CompileState */, /* s2 */)/* : unknown */;
				return /* getOred */(targetInfix/*  : string */, /* parsed */.left, beforeInfix/*  : string */, /* s1 */, content1/*  : string */, /* parsed */.right)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or(() => {
			return /* getOred */(targetInfix/*  : string */, state/*  : CompileState */, beforeInfix/*  : string */, beforeContent/*  : string */, content1/*  : string */, /* Lists */.empty(/*  */)/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOred(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : /* List */<Parameter>) : /* Option */<[CompileState, string]> {
		return /* first */(beforeContent/*  : string */, "<", (name : unknown, withTypeParams : unknown) => {
			return /* first */(/* withTypeParams */, ">", (typeParamsString : unknown, afterTypeParams : unknown) => {
				let /* final */ compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1 : unknown, s : unknown) => new /* Tuple */(/* state1 */, /* s */.strip(/*  */)/* : unknown */)/* : content-start Tuple content-end */;
				let typeParams : var = /* parseValuesOrEmpty */(state/*  : CompileState */, /* typeParamsString */, (state1 : unknown, s : unknown) => new /* Some */(/* compileStateStringTupleBiFunction */.apply(/* state1 */, /* s */)/* : unknown */)/* : content-start Some content-end */)/* : unknown */;
				return /* assemble */(/* typeParams */.left, targetInfix/*  : string */, beforeInfix/*  : string */, /* name */, content1/*  : string */, /* typeParams */.right, /* afterTypeParams */, params/*  : content-start List content-end<Parameter> */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or(() => {
			return /* assemble */(state/*  : CompileState */, targetInfix/*  : string */, beforeInfix/*  : string */, beforeContent/*  : string */, content1/*  : string */, /* Lists */.empty(/*  */)/* : unknown */, "", params/*  : content-start List content-end<Parameter> */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assemble(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : /* List */<string>, afterTypeParams : string, params : /* List */<Parameter>) : /* Option */<[CompileState, string]> {
		let name : var = rawName/*  : string */.strip(/*  */)/* : unknown */;
		/* if (!isSymbol(name))  */{
			return new /* None */(/*  */)/* : content-start None content-end */;
		}
		let joinedTypeParams : var = typeParams/*  : content-start List content-end<string> */.iterate(/*  */)/* : unknown */.collect(new Joiner(", ")/* : Joiner */)/* : unknown */.map((inner : unknown) => "<" + inner + ">")/* : unknown */.orElse("")/* : unknown */;
		let parsed : var = /* parseStatements */(state/*  : CompileState */, content/*  : string */, (state0 : unknown, input : unknown) => /* compileClassSegment */(/* state0 */, /* input */, 1/*  : number */)/* : unknown */)/* : unknown */;
		/* List<String> parsed1 */;
		/* if (params.isEmpty())  */{
			let /* parsed1  */ = /* parsed */.right;
		}
		/* else  */{
			let joined : var = /* joinValues */(/* retainDefinitions */(params/*  : content-start List content-end<Parameter> */)/* : unknown */)/* : unknown */;
			let constructorIndent : var = /* createIndent */(1/*  : number */)/* : unknown */;
			let /* parsed1  */ = /* parsed */.right.addFirst(/* constructorIndent */ + "constructor (" + joined + ") {" + constructorIndent + "}\n")/* : unknown */;
		}
		let parsed2 : var = /* parsed1 */.iterate(/*  */)/* : unknown */.collect(new Joiner(/*  */)/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
		let generated : var = /* generatePlaceholder */(beforeInfix/*  : string */.strip(/*  */)/* : unknown */)/* : unknown */ + targetInfix/*  : string */ + /* name */ + /* joinedTypeParams */ + /* generatePlaceholder */(afterTypeParams/*  : string */)/* : unknown */ + " {" + parsed2 + "\n}\n";
		return new /* Some */(new /* Tuple */(/* parsed */.left.addStructure(/* generated */)/* : unknown */, "")/* : content-start Tuple content-end */)/* : content-start Some content-end */;
	}
	/* private static */ retainDefinition(parameter : Parameter) : /* Option */<Definition> {
		/* if (parameter instanceof Definition definition)  */{
			return new /* Some */(/* definition */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c : var = input/*  : string */.charAt(/* i */)/* : unknown */;
			/* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => /* Option */<T>) : /* Option */<T> {
		/* if (!input.endsWith(suffix))  */{
			return new /* None */(/*  */)/* : content-start None content-end */;
		}
		let slice : var = input/*  : string */.substring(0/*  : number */, input/*  : string */.length(/*  */)/* : unknown */ - suffix/*  : string */.length(/*  */)/* : unknown */)/* : unknown */;
		return mapper/*  : (arg0 : string) => content-start Option content-end<T> */(/* slice */)/* : content-start Option content-end<T> */;
	}
	/* private static */ compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return /* compileWhitespace */(input/*  : string */, state/*  : CompileState */)/* : unknown */.or(() => /* compileClass */(input/*  : string */, depth/*  : number */, state/*  : CompileState */)/* : unknown */)/* : unknown */.or(() => /* structure */(input/*  : string */, "interface ", "interface ", state/*  : CompileState */)/* : unknown */)/* : unknown */.or(() => /* structure */(input/*  : string */, "record ", "class ", state/*  : CompileState */)/* : unknown */)/* : unknown */.or(() => /* compileMethod */(state/*  : CompileState */, input/*  : string */, depth/*  : number */)/* : unknown */)/* : unknown */.or(() => /* compileDefinitionStatement */(input/*  : string */, depth/*  : number */, state/*  : CompileState */)/* : unknown */)/* : unknown */.orElseGet(() => new /* Tuple */(state/*  : CompileState */, /* generatePlaceholder */(input/*  : string */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* private static */ compileWhitespace(input : string, state : CompileState) : /* Option */<[CompileState, string]> {
		/* if (input.isBlank())  */{
			return new /* Some */(new /* Tuple */(state/*  : CompileState */, "")/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* private static */ compileMethod(state : CompileState, input : string, depth : number) : /* Option */<[CompileState, string]> {
		return /* first */(input/*  : string */, "(", (definitionString : unknown, withParams : unknown) => {
			return /* first */(/* withParams */, ")", (parametersString : unknown, rawContent : unknown) => {
				return /* parseDefinition */(state/*  : CompileState */, /* definitionString */)/* : unknown */.flatMap((definitionTuple : unknown) => {
					let definitionState : var = /* definitionTuple */.left;
					let definition : var = /* definitionTuple */.right;
					let parametersTuple : var = /* parseParameters */(/* definitionState */, /* parametersString */)/* : unknown */;
					let parameters : var = /* parametersTuple */.right;
					let definitions : var = /* retainDefinitions */(/* parameters */)/* : unknown */;
					let joinedParameters : var = /* joinValues */(/* definitions */)/* : unknown */;
					let content : var = /* rawContent */.strip(/*  */)/* : unknown */;
					let indent : var = /* createIndent */(depth/*  : number */)/* : unknown */;
					let generatedHeader : var = /* definition */.generateWithParams("(" + joinedParameters + ")")/* : unknown */;
					/* if (content.equals(";"))  */{
						return new /* Some */(new /* Tuple */(/* parametersTuple */.left, /* indent */ + /* generatedHeader */ + ";")/* : content-start Tuple content-end */)/* : content-start Some content-end */;
					}
					/* if (content.startsWith("{") && content.endsWith("}"))  */{
						let substring : var = /* content */.substring(1/*  : number */, /* content */.length(/*  */)/* : unknown */ - 1/*  : number */)/* : unknown */;
						let statementsTuple : var = /* compileFunctionSegments */(/* parametersTuple */.left.withDefinitions(/* definitions */)/* : unknown */, /* substring */, depth/*  : number */)/* : unknown */;
						let generated : var = /* indent */ + /* generatedHeader */ + " {" + statementsTuple.right + indent + "}";
						return new /* Some */(new /* Tuple */(/* statementsTuple */.left, /* generated */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
					}
					return new /* None */(/*  */)/* : content-start None content-end */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ joinValues(retainParameters : /* List */<Definition>) : string {
		return retainParameters/*  : content-start List content-end<Definition> */.iterate(/*  */)/* : unknown */.map(/* Definition */.generate)/* : unknown */.collect(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
	}
	/* private static */ retainDefinitions(right : /* List */<Parameter>) : /* List */<Definition> {
		return right/*  : content-start List content-end<Parameter> */.iterate(/*  */)/* : unknown */.map(/* Main */.retainDefinition)/* : unknown */.flatMap(/* Iterators */.fromOption)/* : unknown */.collect(new /* ListCollector */(/*  */)/* : content-start ListCollector content-end */)/* : unknown */;
	}
	/* private static */ parseParameters(state : CompileState, params : string) : [CompileState, /* List */<Parameter>] {
		return /* parseValuesOrEmpty */(state/*  : CompileState */, params/*  : string */, (state1 : unknown, s : unknown) => new /* Some */(/* compileParameter */(/* state1 */, /* s */)/* : unknown */)/* : content-start Some content-end */)/* : unknown */;
	}
	/* private static */ compileFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return /* compileStatements */(state/*  : CompileState */, input/*  : string */, (state1 : unknown, input1 : unknown) => /* compileFunctionSegment */(/* state1 */, /* input1 */, depth/*  : number */ + 1/*  : number */)/* : unknown */)/* : unknown */;
	}
	/* private static */ compileFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped : var = input/*  : string */.strip(/*  */)/* : unknown */;
		/* if (stripped.isEmpty())  */{
			return new /* Tuple */(state/*  : CompileState */, "")/* : content-start Tuple content-end */;
		}
		return /* suffix */(/* stripped */, ";", (s : unknown) => {
			let tuple : var = /* statementValue */(state/*  : CompileState */, /* s */, depth/*  : number */)/* : unknown */;
			return new /* Some */(new /* Tuple */(/* tuple */.left, /* createIndent */(depth/*  : number */)/* : unknown */ + /* tuple */.right + ";")/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		})/* : unknown */.or(() => {
			return /* block */(state/*  : CompileState */, depth/*  : number */, /* stripped */)/* : unknown */;
		})/* : unknown */.orElseGet(() => {
			return new /* Tuple */(state/*  : CompileState */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : content-start Tuple content-end */;
		})/* : unknown */;
	}
	/* private static */ block(state : CompileState, depth : number, stripped : string) : /* Option */<[CompileState, string]> {
		return /* suffix */(stripped/*  : string */, "}", (withoutEnd : unknown) => {
			return /* split */(() => {
				let divisions : var = /* divideAll */(/* withoutEnd */, /* Main */.foldBlockStart)/* : unknown */;
				return /* divisions */.removeFirst(/*  */)/* : unknown */.map((removed : unknown) => {
					let right : var = /* removed */.left;
					let left : var = /* removed */.right.iterate(/*  */)/* : unknown */.collect(new Joiner("")/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
					return new /* Tuple */(/* right */, /* left */)/* : content-start Tuple content-end */;
				})/* : unknown */;
			}, (beforeContent : unknown, content : unknown) => {
				return /* suffix */(/* beforeContent */, "{", (s : unknown) => {
					let compiled : var = /* compileFunctionSegments */(state/*  : CompileState */, /* content */, depth/*  : number */)/* : unknown */;
					let indent : var = /* createIndent */(depth/*  : number */)/* : unknown */;
					return new /* Some */(new /* Tuple */(/* compiled */.left, /* indent */ + /* generatePlaceholder */(/* s */)/* : unknown */ + "{" + compiled.right + indent + "}")/* : content-start Tuple content-end */)/* : content-start Some content-end */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ foldBlockStart(state : DivideState, c : Character) : DivideState {
		let appended : var = state/*  : DivideState */.append(c/*  : Character */)/* : unknown */;
		/* if (c == '{' && state.isLevel())  */{
			return /* appended */.advance(/*  */)/* : unknown */;
		}
		/* if (c == '{')  */{
			return /* appended */.enter(/*  */)/* : unknown */;
		}
		/* if (c == '}')  */{
			return /* appended */.exit(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ statementValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped : var = input/*  : string */.strip(/*  */)/* : unknown */;
		/* if (stripped.startsWith("return "))  */{
			let value : var = /* stripped */.substring("return ".length(/*  */)/* : unknown */)/* : unknown */;
			let tuple : var = /* compileValue */(state/*  : CompileState */, /* value */, depth/*  : number */)/* : unknown */;
			return new /* Tuple */(/* tuple */.left, "return " + /* tuple */.right)/* : content-start Tuple content-end */;
		}
		return /* first */(/* stripped */, "=", (s : unknown, s2 : unknown) => {
			let definitionTuple : var = /* compileDefinition */(state/*  : CompileState */, /* s */)/* : unknown */;
			let valueTuple : var = /* compileValue */(/* definitionTuple */.left, /* s2 */, depth/*  : number */)/* : unknown */;
			return new /* Some */(new /* Tuple */(/* valueTuple */.left, "let " + /* definitionTuple */.right + " = " + /* valueTuple */.right)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		})/* : unknown */.orElseGet(() => {
			return new /* Tuple */(state/*  : CompileState */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : content-start Tuple content-end */;
		})/* : unknown */;
	}
	/* private static */ compileValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let tuple : var = /* parseValue */(state/*  : CompileState */, input/*  : string */, depth/*  : number */)/* : unknown */;
		return new /* Tuple */(/* tuple */.left, /* tuple */.right.generate(/*  */)/* : unknown */)/* : content-start Tuple content-end */;
	}
	/* private static */ parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return /* parseLambda */(state/*  : CompileState */, input/*  : string */, depth/*  : number */)/* : unknown */.or(() => /* parseString */(state/*  : CompileState */, input/*  : string */)/* : unknown */)/* : unknown */.or(() => /* parseDataAccess */(state/*  : CompileState */, input/*  : string */, depth/*  : number */)/* : unknown */)/* : unknown */.or(() => /* parseSymbolValue */(state/*  : CompileState */, input/*  : string */)/* : unknown */)/* : unknown */.or(() => /* parseInvocation */(state/*  : CompileState */, input/*  : string */, depth/*  : number */)/* : unknown */)/* : unknown */.or(() => /* parseOperation */(state/*  : CompileState */, input/*  : string */, depth/*  : number */, "+")/* : unknown */)/* : unknown */.or(() => /* parseOperation */(state/*  : CompileState */, input/*  : string */, depth/*  : number */, "-")/* : unknown */)/* : unknown */.or(() => /* parseDigits */(state/*  : CompileState */, input/*  : string */)/* : unknown */)/* : unknown */.or(() => /* parseNot */(state/*  : CompileState */, input/*  : string */, depth/*  : number */)/* : unknown */)/* : unknown */.or(() => /* parseMethodReference */(state/*  : CompileState */, input/*  : string */, depth/*  : number */)/* : unknown */)/* : unknown */.orElseGet(() => new [CompileState, Value](state/*  : CompileState */, new Placeholder(input/*  : string */)/* : Placeholder */)/* : [CompileState, Value] */)/* : unknown */;
	}
	/* private static */ parseMethodReference(state : CompileState, input : string, depth : number) : /* Option */<[CompileState, Value]> {
		return /* last */(input/*  : string */, "::", (s : unknown, s2 : unknown) => {
			let tuple : var = /* parseValue */(state/*  : CompileState */, /* s */, depth/*  : number */)/* : unknown */;
			return new /* Some */(new /* Tuple */(/* tuple */.left, new DataAccess(/* tuple */.right, /* s2 */)/* : DataAccess */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		})/* : unknown */;
	}
	/* private static */ parseNot(state : CompileState, input : string, depth : number) : /* Option */<[CompileState, Value]> {
		let stripped : var = input/*  : string */.strip(/*  */)/* : unknown */;
		/* if (stripped.startsWith("!"))  */{
			let slice : var = /* stripped */.substring(1/*  : number */)/* : unknown */;
			let tuple : var = /* parseValue */(state/*  : CompileState */, /* slice */, depth/*  : number */)/* : unknown */;
			let value : var = /* tuple */.right;
			return new /* Some */(new /* Tuple */(/* tuple */.left, new Not(/* value */)/* : Not */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* private static */ parseLambda(state : CompileState, input : string, depth : number) : /* Option */<[CompileState, Value]> {
		return /* first */(input/*  : string */, "->", (beforeArrow : unknown, valueString : unknown) => {
			let strippedBeforeArrow : var = /* beforeArrow */.strip(/*  */)/* : unknown */;
			/* if (isSymbol(strippedBeforeArrow))  */{
				return /* assembleLambda */(state/*  : CompileState */, /* Lists */.of(/* strippedBeforeArrow */)/* : unknown */, /* valueString */, depth/*  : number */)/* : unknown */;
			}
			/* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */{
				let parameterNames : var = /* divideAll */(/* strippedBeforeArrow */.substring(1/*  : number */, /* strippedBeforeArrow */.length(/*  */)/* : unknown */ - 1/*  : number */)/* : unknown */, /* Main */.foldValueChar)/* : unknown */.iterate(/*  */)/* : unknown */.map(/* String */.strip)/* : unknown */.filter((value : unknown) => !/* value */.isEmpty(/*  */)/* : unknown */)/* : unknown */.collect(new /* ListCollector */(/*  */)/* : content-start ListCollector content-end */)/* : unknown */;
				return /* assembleLambda */(state/*  : CompileState */, /* parameterNames */, /* valueString */, depth/*  : number */)/* : unknown */;
			}
			return new /* None */(/*  */)/* : content-start None content-end */;
		})/* : unknown */;
	}
	/* private static */ assembleLambda(state : CompileState, paramNames : /* List */<string>, valueString : string, depth : number) : /* Some */<[CompileState, Value]> {
		let strippedValueString : var = valueString/*  : string */.strip(/*  */)/* : unknown */;
		/* Tuple<CompileState, LambdaValue> value */;
		/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */{
			let value1 : var = /* compileStatements */(state/*  : CompileState */, /* strippedValueString */.substring(1/*  : number */, /* strippedValueString */.length(/*  */)/* : unknown */ - 1/*  : number */)/* : unknown */, (state1 : unknown, input1 : unknown) => /* compileFunctionSegment */(/* state1 */, /* input1 */, depth/*  : number */ + 1/*  : number */)/* : unknown */)/* : unknown */;
			let right : var = /* value1 */.right;
			let /* value  */ = new /* Tuple */(/* value1 */.left, new BlockLambdaValue(/* right */, depth/*  : number */)/* : BlockLambdaValue */)/* : content-start Tuple content-end */;
		}
		/* else  */{
			let value1 : var = /* parseValue */(state/*  : CompileState */, /* strippedValueString */, depth/*  : number */)/* : unknown */;
			let /* value  */ = new /* Tuple */(/* value1 */.left, /* value1 */.right)/* : content-start Tuple content-end */;
		}
		let right : var = /* value */.right;
		return new /* Some */(new /* Tuple */(/* value */.left, new Lambda(paramNames/*  : content-start List content-end<string> */, /* right */)/* : Lambda */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
	}
	/* private static */ parseDigits(state : CompileState, input : string) : /* Option */<[CompileState, Value]> {
		let stripped : var = input/*  : string */.strip(/*  */)/* : unknown */;
		/* if (isNumber(stripped))  */{
			return new /* Some */(new [CompileState, Value](state/*  : CompileState */, new SymbolValue(/* stripped */, /* Primitive */.Int)/* : SymbolValue */)/* : [CompileState, Value] */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* private static */ isNumber(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c : var = input/*  : string */.charAt(/* i */)/* : unknown */;
			/* if (Character.isDigit(c))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	/* private static */ parseInvocation(state : CompileState, input : string, depth : number) : /* Option */<[CompileState, Value]> {
		return /* suffix */(input/*  : string */.strip(/*  */)/* : unknown */, ")", (withoutEnd : unknown) => {
			return /* split */(() => /* toLast */(/* withoutEnd */, "", /* Main */.foldInvocationStart)/* : unknown */, (callerWithEnd : unknown, argumentsString : unknown) => {
				return /* suffix */(/* callerWithEnd */, "(", (callerString : unknown) => {
					let callerString1 : var = /* callerString */.strip(/*  */)/* : unknown */;
					let callerTuple : var = /* invocationHeader */(state/*  : CompileState */, depth/*  : number */, /* callerString1 */)/* : unknown */;
					let parsed : var = /* parseValues */(/* callerTuple */.left, /* argumentsString */, (state3 : unknown, s : unknown) => new /* Some */(/* parseValue */(/* state3 */, /* s */, depth/*  : number */)/* : unknown */)/* : content-start Some content-end */)/* : unknown */.orElseGet(() => new /* Tuple */(/* callerTuple */.left, /* Lists */.empty(/*  */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
					let oldCaller : var = /* callerTuple */.right;
					let arguments : var = /* parsed */.right;
					let newCaller : var = /* modifyCaller */(/* parsed */.left, /* oldCaller */)/* : unknown */;
					let var : Type = /* Primitive */.Unknown;
					/* switch (newCaller)  */{
						/* case ConstructionCaller constructionCaller ->  */{
							let /* var  */ = /* constructionCaller */.type;
						}
						/* case Value value ->  */{
							let type : var = /* value */.type(/*  */)/* : unknown */;
							/* if (type instanceof FunctionType functionType)  */{
								let /* var  */ = /* functionType */.returns;
							}
						}
					}
					let invokable : var = new Invokable(/* newCaller */, /* arguments */, /* var */)/* : Invokable */;
					return new /* Some */(new /* Tuple */(/* parsed */.left, /* invokable */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		/* if (oldCaller instanceof DataAccess access)  */{
			let type : var = /* resolveType */(/* access */.parent, state/*  : CompileState */)/* : unknown */;
			/* if (type instanceof FunctionType)  */{
				return /* access */.parent;
			}
		}
		return oldCaller/*  : Caller */;
	}
	/* private static */ resolveType(value : Value, state : CompileState) : Type {
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
	/* private static */ invocationHeader(state : CompileState, depth : number, callerString1 : string) : [CompileState, Caller] {
		/* if (callerString1.startsWith("new "))  */{
			let input1 : string = callerString1/*  : string */.substring("new ".length(/*  */)/* : unknown */)/* : unknown */;
			let map : var = /* parseType */(state/*  : CompileState */, /* input1 */)/* : unknown */.map((type : unknown) => {
				let right : var = /* type */.right;
				return new [CompileState, Caller](/* type */.left, new ConstructionCaller(/* right */)/* : ConstructionCaller */)/* : [CompileState, Caller] */;
			})/* : unknown */;
			/* if (map.isPresent())  */{
				return /* map */.orElse(/* null */)/* : unknown */;
			}
		}
		let tuple : var = /* parseValue */(state/*  : CompileState */, callerString1/*  : string */, depth/*  : number */)/* : unknown */;
		return new /* Tuple */(/* tuple */.left, /* tuple */.right)/* : content-start Tuple content-end */;
	}
	/* private static */ foldInvocationStart(state : DivideState, c : char) : DivideState {
		let appended : var = state/*  : DivideState */.append(c/*  : char */)/* : unknown */;
		/* if (c == '(')  */{
			let enter : var = /* appended */.enter(/*  */)/* : unknown */;
			/* if (enter.isShallow())  */{
				return /* enter */.advance(/*  */)/* : unknown */;
			}
			return /* enter */;
		}
		/* if (c == ')')  */{
			return /* appended */.exit(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ parseDataAccess(state : CompileState, input : string, depth : number) : /* Option */<[CompileState, Value]> {
		return /* last */(input/*  : string */.strip(/*  */)/* : unknown */, ".", (parentString : unknown, rawProperty : unknown) => {
			let property : var = /* rawProperty */.strip(/*  */)/* : unknown */;
			/* if (!isSymbol(property))  */{
				return new /* None */(/*  */)/* : content-start None content-end */;
			}
			let tuple : var = /* parseValue */(state/*  : CompileState */, /* parentString */, depth/*  : number */)/* : unknown */;
			let parent : var = /* tuple */.right;
			let type : var = /* resolveType */(/* parent */, state/*  : CompileState */)/* : unknown */;
			/* if (type instanceof TupleType)  */{
				/* if (property.equals("left"))  */{
					return new /* Some */(new /* Tuple */(state/*  : CompileState */, new IndexValue(/* parent */, new SymbolValue("0", /* Primitive */.Int)/* : SymbolValue */)/* : IndexValue */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
				}
				/* if (property.equals("type"))  */{
					return new /* Some */(new /* Tuple */(state/*  : CompileState */, new IndexValue(/* parent */, new SymbolValue("1", /* Primitive */.Int)/* : SymbolValue */)/* : IndexValue */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
				}
			}
			return new /* Some */(new /* Tuple */(/* tuple */.left, new DataAccess(/* parent */, /* property */)/* : DataAccess */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		})/* : unknown */;
	}
	/* private static */ parseString(state : CompileState, input : string) : /* Option */<[CompileState, Value]> {
		let stripped : var = input/*  : string */.strip(/*  */)/* : unknown */;
		/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
			return new /* Some */(new /* Tuple */(state/*  : CompileState */, new StringValue(/* stripped */)/* : StringValue */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* private static */ parseSymbolValue(state : CompileState, value : string) : /* Option */<[CompileState, Value]> {
		let stripped : var = value/*  : string */.strip(/*  */)/* : unknown */;
		/* if (isSymbol(stripped))  */{
			/* if (state.resolveValue(stripped) instanceof Some(var type))  */{
				return new /* Some */(new /* Tuple */(state/*  : CompileState */, new SymbolValue(/* stripped */, /* type */)/* : SymbolValue */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
			}
			return new /* Some */(new /* Tuple */(state/*  : CompileState */, new Placeholder(/* stripped */)/* : Placeholder */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* private static */ parseOperation(state : CompileState, value : string, depth : number, infix : string) : /* Option */<[CompileState, Value]> {
		return /* first */(value/*  : string */, infix/*  : string */, (s : unknown, s2 : unknown) => {
			let tuple : var = /* parseValue */(state/*  : CompileState */, /* s */, depth/*  : number */)/* : unknown */;
			let tuple1 : var = /* parseValue */(/* tuple */.left, /* s2 */, depth/*  : number */)/* : unknown */;
			let left : var = /* tuple */.right;
			let right : var = /* tuple1 */.right;
			return new /* Some */(new /* Tuple */(/* tuple1 */.left, new Operation(/* left */, infix/*  : string */, /* right */)/* : Operation */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		})/* : unknown */;
	}
	/* private static */ compileValues(state : CompileState, params : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed : var = /* parseValuesOrEmpty */(state/*  : CompileState */, params/*  : string */, (state1 : unknown, s : unknown) => new /* Some */(mapper/*  : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(/* state1 */, /* s */)/* : [CompileState, string] */)/* : content-start Some content-end */)/* : unknown */;
		let generated : var = /* generateValues */(/* parsed */.right)/* : unknown */;
		return new /* Tuple */(/* parsed */.left, /* generated */)/* : content-start Tuple content-end */;
	}
	/* private static */ generateValues(elements : /* List */<string>) : string {
		return /* generateAll */(/* Main */.mergeValues, elements/*  : content-start List content-end<string> */)/* : unknown */;
	}
	/* private static  */ parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => /* Option */<[CompileState, T]>) : [CompileState, /* List */<T>] {
		return /* parseValues */(state/*  : CompileState */, input/*  : string */, mapper/*  : (arg0 : CompileState, arg1 : string) => content-start Option content-end<[CompileState, T]> */)/* : unknown */.orElseGet(() => new /* Tuple */(state/*  : CompileState */, /* Lists */.empty(/*  */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* private static  */ parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => /* Option */<[CompileState, T]>) : /* Option */<[CompileState, /* List */<T>]> {
		return /* getCompileStateListTuple */(state/*  : CompileState */, input/*  : string */, /* Main */.foldValueChar, mapper/*  : (arg0 : CompileState, arg1 : string) => content-start Option content-end<[CompileState, T]> */)/* : unknown */;
	}
	/* private static */ compileParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		/* if (input.isBlank())  */{
			return new /* Tuple */(state/*  : CompileState */, new Whitespace(/*  */)/* : Whitespace */)/* : content-start Tuple content-end */;
		}
		return /* parseDefinition */(state/*  : CompileState */, input/*  : string */)/* : unknown */.map((tuple : unknown) => new [CompileState, Parameter](/* tuple */.left, /* tuple */.right)/* : [CompileState, Parameter] */)/* : unknown */.orElseGet(() => new /* Tuple */(state/*  : CompileState */, new Placeholder(input/*  : string */)/* : Placeholder */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* private static */ compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return /* parseDefinition */(state/*  : CompileState */, input/*  : string */)/* : unknown */.map((tuple : unknown) => new /* Tuple */(/* tuple */.left, /* tuple */.right.generate(/*  */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */.orElseGet(() => new /* Tuple */(state/*  : CompileState */, /* generatePlaceholder */(input/*  : string */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* private static */ mergeValues(cache : StringBuilder, element : string) : StringBuilder {
		/* if (cache.isEmpty())  */{
			return cache/*  : StringBuilder */.append(element/*  : string */)/* : unknown */;
		}
		return cache/*  : StringBuilder */.append(", ")/* : unknown */.append(element/*  : string */)/* : unknown */;
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat(depth/*  : number */)/* : unknown */;
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : CompileState) : /* Option */<[CompileState, string]> {
		return /* suffix */(input/*  : string */.strip(/*  */)/* : unknown */, ";", (withoutEnd : unknown) => {
			return /* parseDefinition */(state/*  : CompileState */, /* withoutEnd */)/* : unknown */.map((result : unknown) => {
				let generated : var = /* createIndent */(depth/*  : number */)/* : unknown */ + /* result */.right.generate(/*  */)/* : unknown */ + ";";
				return new /* Tuple */(/* result */.left, /* generated */)/* : content-start Tuple content-end */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseDefinition(state : CompileState, input : string) : /* Option */<[CompileState, Definition]> {
		return /* last */(input/*  : string */.strip(/*  */)/* : unknown */, " ", (beforeName : unknown, name : unknown) => {
			return /* split */(() => /* toLast */(/* beforeName */, " ", /* Main */.foldTypeSeparator)/* : unknown */, (beforeType : unknown, type : unknown) => {
				return /* suffix */(/* beforeType */.strip(/*  */)/* : unknown */, ">", (withoutTypeParamStart : unknown) => {
					return /* first */(/* withoutTypeParamStart */, "<", (beforeTypeParams : unknown, typeParamsString : unknown) => {
						let /* final */ compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1 : unknown, s : unknown) => new /* Tuple */(/* state1 */, /* s */.strip(/*  */)/* : unknown */)/* : content-start Tuple content-end */;
						let typeParams : var = /* parseValuesOrEmpty */(state/*  : CompileState */, /* typeParamsString */, (state1 : unknown, s : unknown) => new /* Some */(/* compileStateStringTupleBiFunction */.apply(/* state1 */, /* s */)/* : unknown */)/* : content-start Some content-end */)/* : unknown */;
						return /* assembleDefinition */(/* typeParams */.left, new /* Some */<string>(/* beforeTypeParams */)/* : content-start Some content-end<string> */, /* name */, /* typeParams */.right, /* type */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */.or(() => {
					return /* assembleDefinition */(state/*  : CompileState */, new /* Some */<string>(/* beforeType */)/* : content-start Some content-end<string> */, /* name */, /* Lists */.empty(/*  */)/* : unknown */, /* type */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */.or(() => {
				return /* assembleDefinition */(state/*  : CompileState */, new /* None */<string>(/*  */)/* : content-start None content-end<string> */, /* name */, /* Lists */.empty(/*  */)/* : unknown */, /* beforeName */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState) : /* Option */<[string, string]> {
		let divisions : var = /* divideAll */(input/*  : string */, folder/*  : (arg0 : DivideState, arg1 : Character) => DivideState */)/* : unknown */;
		return /* divisions */.removeLast(/*  */)/* : unknown */.map((removed : unknown) => {
			let left : var = /* removed */.left.iterate(/*  */)/* : unknown */.collect(new Joiner(separator/*  : string */)/* : Joiner */)/* : unknown */.orElse("")/* : unknown */;
			let right : var = /* removed */.right;
			return new /* Tuple */(/* left */, /* right */)/* : content-start Tuple content-end */;
		})/* : unknown */;
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : Character) : DivideState {
		/* if (c == ' ' && state.isLevel())  */{
			return state/*  : DivideState */.advance(/*  */)/* : unknown */;
		}
		let appended : var = state/*  : DivideState */.append(c/*  : Character */)/* : unknown */;
		/* if (c == '<')  */{
			return /* appended */.enter(/*  */)/* : unknown */;
		}
		/* if (c == '>')  */{
			return /* appended */.exit(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ assembleDefinition(state : CompileState, beforeTypeParams : /* Option */<string>, name : string, typeParams : /* List */<string>, type : string) : /* Option */<[CompileState, Definition]> {
		return /* parseType */(state/*  : CompileState */, type/*  : string */)/* : unknown */.map((type1 : unknown) => {
			let node : var = new Definition(beforeTypeParams/*  : content-start Option content-end<string> */, /* type1 */.right, name/*  : string */.strip(/*  */)/* : unknown */, typeParams/*  : content-start List content-end<string> */)/* : Definition */;
			return new /* Tuple */(/* type1 */.left, /* node */)/* : content-start Tuple content-end */;
		})/* : unknown */;
	}
	/* private static */ foldValueChar(state : DivideState, c : char) : DivideState {
		/* if (c == ',' && state.isLevel())  */{
			return state/*  : DivideState */.advance(/*  */)/* : unknown */;
		}
		let appended : var = state/*  : DivideState */.append(c/*  : char */)/* : unknown */;
		/* if (c == '-')  */{
			let peeked : var = /* appended */.peek(/*  */)/* : unknown */;
			/* if (peeked == '>')  */{
				return /* appended */.popAndAppendToOption(/*  */)/* : unknown */.orElse(/* appended */)/* : unknown */;
			}
			/* else  */{
				return /* appended */;
			}
		}
		/* if (c == '<' || c == '(' || c == '{')  */{
			return /* appended */.enter(/*  */)/* : unknown */;
		}
		/* if (c == '>' || c == ')' || c == '}')  */{
			return /* appended */.exit(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ compileType(state : CompileState, input : string) : /* Option */<[CompileState, string]> {
		return /* parseType */(state/*  : CompileState */, input/*  : string */)/* : unknown */.map((tuple : unknown) => new /* Tuple */(/* tuple */.left, /* tuple */.right.generate(/*  */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* private static */ parseType(state : CompileState, input : string) : /* Option */<[CompileState, Type]> {
		let stripped : var = input/*  : string */.strip(/*  */)/* : unknown */;
		/* if (stripped.equals("int") || stripped.equals("Integer"))  */{
			return new /* Some */(new /* Tuple */(state/*  : CompileState */, /* Primitive */.Int)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		/* if (stripped.equals("String"))  */{
			return new /* Some */(new /* Tuple */(state/*  : CompileState */, /* Primitive */.String)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		/* if (stripped.equals("type"))  */{
			return new /* Some */(new /* Tuple */(state/*  : CompileState */, /* Primitive */.Unknown)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		/* if (isSymbol(stripped))  */{
			return new /* Some */(new /* Tuple */(state/*  : CompileState */, new SymbolType(/* stripped */)/* : SymbolType */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		return /* parseTemplate */(state/*  : CompileState */, input/*  : string */)/* : unknown */.or(() => /* varArgs */(state/*  : CompileState */, input/*  : string */)/* : unknown */)/* : unknown */;
	}
	/* private static */ varArgs(state : CompileState, input : string) : /* Option */<[CompileState, Type]> {
		return /* suffix */(input/*  : string */, "...", (s : unknown) => {
			return /* parseType */(state/*  : CompileState */, /* s */)/* : unknown */.map((inner : unknown) => {
				let newState : var = /* inner */.left;
				let child : var = /* inner */.right;
				return new /* Tuple */(/* newState */, new ArrayType(/* child */)/* : ArrayType */)/* : content-start Tuple content-end */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseTemplate(state : CompileState, input : string) : /* Option */<[CompileState, Type]> {
		return /* suffix */(input/*  : string */.strip(/*  */)/* : unknown */, ">", (withoutEnd : unknown) => {
			return /* first */(/* withoutEnd */, "<", (base : unknown, argumentsString : unknown) => {
				let strippedBase : var = /* base */.strip(/*  */)/* : unknown */;
				return /* parseValues */(state/*  : CompileState */, /* argumentsString */, /* Main */.argument)/* : unknown */.map((argumentsTuple : unknown) => {
					return /* assembleTemplate */(/* strippedBase */, /* argumentsTuple */.left, /* argumentsTuple */.right)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleTemplate(base : string, state : CompileState, arguments : /* List */<Argument>) : [CompileState, Type] {
		let children : var = arguments/*  : content-start List content-end<Argument> */.iterate(/*  */)/* : unknown */.map(/* Main */.retainType)/* : unknown */.flatMap(/* Iterators */.fromOption)/* : unknown */.collect(new /* ListCollector */(/*  */)/* : content-start ListCollector content-end */)/* : unknown */;
		/* if (base.equals("BiFunction"))  */{
			return new /* Tuple */(state/*  : CompileState */, new FunctionType(/* Lists */.of(/* children */.get(0/*  : number */)/* : unknown */, /* children */.get(1/*  : number */)/* : unknown */)/* : unknown */, /* children */.get(2/*  : number */)/* : unknown */)/* : FunctionType */)/* : content-start Tuple content-end */;
		}
		/* if (base.equals("Function"))  */{
			return new /* Tuple */(state/*  : CompileState */, new FunctionType(/* Lists */.of(/* children */.get(0/*  : number */)/* : unknown */)/* : unknown */, /* children */.get(1/*  : number */)/* : unknown */)/* : FunctionType */)/* : content-start Tuple content-end */;
		}
		/* if (base.equals("Predicate"))  */{
			return new /* Tuple */(state/*  : CompileState */, new FunctionType(/* Lists */.of(/* children */.get(0/*  : number */)/* : unknown */)/* : unknown */, /* Primitive */.Boolean)/* : FunctionType */)/* : content-start Tuple content-end */;
		}
		/* if (base.equals("Supplier"))  */{
			return new /* Tuple */(state/*  : CompileState */, new FunctionType(/* Lists */.empty(/*  */)/* : unknown */, /* children */.get(0/*  : number */)/* : unknown */)/* : FunctionType */)/* : content-start Tuple content-end */;
		}
		/* if (base.equals("Tuple") && children.size() >= 2)  */{
			return new /* Tuple */(state/*  : CompileState */, new TupleType(/* children */)/* : TupleType */)/* : content-start Tuple content-end */;
		}
		/* if (state.resolveType(base) instanceof Some(var baseType))  */{
			return new /* Tuple */(state/*  : CompileState */, new Template(/* baseType */, /* children */)/* : Template */)/* : content-start Tuple content-end */;
		}
		/* else  */{
			return new /* Tuple */(state/*  : CompileState */, new Template(new Placeholder(base/*  : string */)/* : Placeholder */, /* children */)/* : Template */)/* : content-start Tuple content-end */;
		}
	}
	/* private static */ retainType(argument : Argument) : /* Option */<Type> {
		/* if (argument instanceof Type type)  */{
			return new /* Some */(/* type */)/* : content-start Some content-end */;
		}
		/* else  */{
			return new /* None */<Type>(/*  */)/* : content-start None content-end<Type> */;
		}
	}
	/* private static */ argument(state : CompileState, input : string) : /* Option */<[CompileState, Argument]> {
		/* if (input.isBlank())  */{
			return new /* Some */(new /* Tuple */(state/*  : CompileState */, new Whitespace(/*  */)/* : Whitespace */)/* : content-start Tuple content-end */)/* : content-start Some content-end */;
		}
		return /* parseType */(state/*  : CompileState */, input/*  : string */)/* : unknown */.map((tuple : unknown) => new /* Tuple */(/* tuple */.left, /* tuple */.right)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => /* Option */<T>) : /* Option */<T> {
		return infix/*  : string */(input/*  : string */, infix/*  : string */, /* Main */.findLast, mapper/*  : (arg0 : string, arg1 : string) => content-start Option content-end<T> */)/* : unknown */;
	}
	/* private static */ findLast(input : string, infix : string) : /* Option */<number> {
		let index : var = input/*  : string */.lastIndexOf(infix/*  : string */)/* : unknown */;
		/* if (index == -1)  */{
			return new /* None */<number>(/*  */)/* : content-start None content-end<number> */;
		}
		return new /* Some */(/* index */)/* : content-start Some content-end */;
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => /* Option */<T>) : /* Option */<T> {
		return infix/*  : string */(input/*  : string */, infix/*  : string */, /* Main */.findFirst, mapper/*  : (arg0 : string, arg1 : string) => content-start Option content-end<T> */)/* : unknown */;
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => /* Option */<number>, mapper : (arg0 : string, arg1 : string) => /* Option */<T>) : /* Option */<T> {
		return /* split */(() => locator/*  : (arg0 : string, arg1 : string) => content-start Option content-end<number> */(input/*  : string */, infix/*  : string */)/* : content-start Option content-end<number> */.map((index : unknown) => {
			let left : var = input/*  : string */.substring(0/*  : number */, /* index */)/* : unknown */;
			let right : var = input/*  : string */.substring(/* index */ + infix/*  : string */.length(/*  */)/* : unknown */)/* : unknown */;
			return new /* Tuple */(/* left */, /* right */)/* : content-start Tuple content-end */;
		})/* : unknown */, mapper/*  : (arg0 : string, arg1 : string) => content-start Option content-end<T> */)/* : unknown */;
	}
	/* private static  */ split<T>(splitter : () => /* Option */<[string, string]>, mapper : (arg0 : string, arg1 : string) => /* Option */<T>) : /* Option */<T> {
		return splitter/*  : () => content-start Option content-end<[string, string]> */(/*  */)/* : content-start Option content-end<[string, string]> */.flatMap((tuple : unknown) => mapper/*  : (arg0 : string, arg1 : string) => content-start Option content-end<T> */(/* tuple */.left, /* tuple */.right)/* : content-start Option content-end<T> */)/* : unknown */;
	}
	/* private static */ findFirst(input : string, infix : string) : /* Option */<number> {
		let index : var = input/*  : string */.indexOf(infix/*  : string */)/* : unknown */;
		/* if (index == -1)  */{
			return new /* None */<number>(/*  */)/* : content-start None content-end<number> */;
		}
		return new /* Some */(/* index */)/* : content-start Some content-end */;
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced : var = input/*  : string */.replace("/*", "content-start")/* : unknown */.replace("*/", "content-end")/* : unknown */;
		return "/* " + replaced + " */";
	}/* 

    private enum Primitive implements Type {
        Int("number"),
        String("string"),
        Boolean("boolean"),
        Unknown("unknown");

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
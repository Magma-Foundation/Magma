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
	collect<R>(collector : Collector<T, R>) : R;
	filter(predicate : (arg0 : T) => boolean) : /* Iterator */<T>;
	next() : Option<T>;
	flatMap<R>(f : (arg0 : T) => /* Iterator */<R>) : /* Iterator */<R>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : /* List */<T>;
	iterate() : Iterator<T>;
	removeLast() : Option<[/* List */<T>, T]>;
	get(index : number) : T;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : /* List */<T>;
	iterateWithIndices() : Iterator<[number, T]>;
	removeFirst() : Option<[T, /* List */<T>]>;
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
/* private sealed */interface Caller/*  */ {
	generate() : string;
}
/* private */class Some<T>/*  */ {
	constructor (value : T) {
	}

	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new /* Some */(mapper/* : (arg0 : T) => R */(/* this */.value/* : unknown */)/* : R */)/* : content-start Some content-end */;
	}
	/* @Override
        public */ isPresent() : boolean {
		return /* true */;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return /* this */.value/* : unknown */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		/* if (predicate.test(this.value))  */{
			return /* this */;
		}
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return /* this */.value/* : unknown */;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return /* this */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper/* : (arg0 : T) => Option<R> */(/* this */.value/* : unknown */)/* : Option<R> */;
	}
	/* @Override
        public */ isEmpty() : boolean {
		return /* false */;
	}
}
/* private static */class None<T>/*  */ {
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* @Override
        public */ isPresent() : boolean {
		return /* false */;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return other/* : T */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new /* None */(/*  */)/* : content-start None content-end */;
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return supplier/* : () => T */(/*  */)/* : T */;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return other/* : () => Option<T> */(/*  */)/* : Option<T> */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
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
		let /* this.value  */ = value/* : T */;
		let /* this.retrieved  */ = /* false */;
	}
	/* @Override
        public */ next() : Option<T> {
		/* if (this.retrieved)  */{
			return new None(/*  */)/* : None */;
		}
		let /* this.retrieved  */ = /* true */;
		return new Some(/* this */.value/* : unknown */)/* : Some */;
	}
}
/* private static */class EmptyHead<T>/*  */ {
	/* @Override
        public */ next() : Option<T> {
		return new None(/*  */)/* : None */;
	}
}
/* private */class HeadedIterator<T>/*  */ {
	constructor (head : Head<T>) {
	}

	/* @Override
        public  */ fold<R>(initial : R, folder : (arg0 : R, arg1 : T) => R) : R {
		let current : var = initial/* : R */;
		/* while (true)  */{
			let finalCurrent : R = /* current */;
			let optional : var = /* this */.head/* : unknown */.next/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((inner : unknown) => folder/* : (arg0 : R, arg1 : T) => R */(/* finalCurrent */, /* inner */)/* : R */)/* : unknown */;
			/* if (optional.isPresent())  */{
				let /* current  */ = /* optional */.orElse/* : unknown */(/* null */)/* : unknown */;
			}
			/* else  */{
				return /* current */;
			}
		}
	}
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Iterator<R> {
		return new /* HeadedIterator */(() => /* this */.head/* : unknown */.next/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(mapper/* : (arg0 : T) => R */)/* : unknown */)/* : content-start HeadedIterator content-end */;
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return /* this */.fold/* : unknown */(collector/* : Collector<T, R> */.createInitial/* : () => C */(/*  */)/* : unknown */, collector/* : Collector<T, R> */.fold/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return /* this */.flatMap/* : unknown */((element : unknown) => {
			/* if (predicate.test(element))  */{
				return new /* HeadedIterator */(new SingleHead(/* element */)/* : SingleHead */)/* : content-start HeadedIterator content-end */;
			}
			return new /* HeadedIterator */(new EmptyHead(/*  */)/* : EmptyHead */)/* : content-start HeadedIterator content-end */;
		})/* : unknown */;
	}
	/* @Override
        public */ next() : Option<T> {
		return /* this */.head/* : unknown */.next/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
		return new /* HeadedIterator */(new /* FlatMapHead */(/* this */.head/* : unknown */, f/* : (arg0 : T) => Iterator<R> */)/* : content-start FlatMapHead content-end */)/* : content-start HeadedIterator content-end */;
	}
}
/* private static */class RangeHead/*  */ {
	/* private final */ length : number;
	/* private */ counter : number;
	RangeHead(length : number) : public {
		let /* this.length  */ = length/* : number */;
	}
	/* @Override
        public */ next() : Option<number> {
		/* if (this.counter < this.length)  */{
			let value : var = /* this */.counter/* : unknown */;
			/* this.counter++ */;
			return new Some(/* value */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
}
/* private static final */class JVMList<T>/*  */ {
	/* private final */ elements : /* java.util.List */<T>;
	JVMList(elements : /* java.util.List */<T>) : private {
		let /* this.elements  */ = elements/* : content-start java.util.List content-end<T> */;
	}
	JVMList() : public {
		/* this(new ArrayList<>()) */;
	}
	/* @Override
            public */ addLast(element : T) : List<T> {
		/* this.elements.add(element) */;
		return /* this */;
	}
	/* @Override
            public */ iterate() : Iterator<T> {
		return /* this */.iterateWithIndices/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Tuple */.right/* : unknown */)/* : unknown */;
	}
	/* @Override
            public */ removeLast() : Option<[List<T>, T]> {
		/* if (this.elements.isEmpty())  */{
			return new None(/*  */)/* : None */;
		}
		let slice : var = /* this */.elements/* : unknown */.subList/* : unknown */(0/* : number */, /* this */.elements/* : unknown */.size/* : unknown */(/*  */)/* : unknown */ - 1/* : number */)/* : unknown */;
		let last : var = /* this */.elements/* : unknown */.getLast/* : unknown */(/*  */)/* : unknown */;
		return new Some(new [List<T>, T](new /* JVMList */(/* slice */)/* : content-start JVMList content-end */, /* last */)/* : [List<T>, T] */)/* : Some */;
	}
	/* @Override
            public */ get(index : number) : T {
		return /* this */.elements/* : unknown */.get/* : unknown */(index/* : number */)/* : unknown */;
	}
	/* @Override
            public */ size() : number {
		return /* this */.elements/* : unknown */.size/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
            public */ isEmpty() : boolean {
		return /* this */.elements/* : unknown */.isEmpty/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
            public */ addFirst(element : T) : List<T> {
		/* this.elements.addFirst(element) */;
		return /* this */;
	}
	/* @Override
            public */ iterateWithIndices() : Iterator<[number, T]> {
		return new HeadedIterator(new RangeHead(/* this */.elements/* : unknown */.size/* : unknown */(/*  */)/* : unknown */)/* : RangeHead */)/* : HeadedIterator */.map/* : unknown */((index : unknown) => new /* Tuple */(/* index */, /* this */.elements/* : unknown */.get/* : unknown */(/* index */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* @Override
            public */ removeFirst() : Option<[T, List<T>]> {
		/* if (this.elements.isEmpty())  */{
			return new None(/*  */)/* : None */;
		}
		let first : var = /* this */.elements/* : unknown */.getFirst/* : unknown */(/*  */)/* : unknown */;
		let slice : var = /* this */.elements/* : unknown */.subList/* : unknown */(1/* : number */, /* this */.elements/* : unknown */.size/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
		return new Some(new [T, List<T>](/* first */, new /* JVMList */(/* slice */)/* : content-start JVMList content-end */)/* : [T, List<T>] */)/* : Some */;
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : List<T> {
		return new JVMList(/*  */)/* : JVMList */;
	}
	/* public static  */ of<T>(elements : T[]) : List<T> {
		return new JVMList(new /* ArrayList */(/* Arrays */.asList/* : unknown */(elements/* : T[] */)/* : unknown */)/* : content-start ArrayList content-end */)/* : JVMList */;
	}
}
/* private */class ObjectType/*  */ {
	constructor (name : string, definitions : List<Definition>) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.name/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return /* this */.definitions/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.filter/* : unknown */((definition : unknown) => /* definition */.name/* : unknown */.equals/* : unknown */(name/* : string */)/* : unknown */)/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */.next/* : unknown */(/*  */)/* : unknown */;
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : List<string>;
	/* private */ buffer : StringBuilder;
	DivideState(input : string, index : number, segments : List<string>, buffer : StringBuilder, depth : number) : public {
		let /* this.segments  */ = segments/* : List<string> */;
		let /* this.buffer  */ = buffer/* : StringBuilder */;
		let /* this.depth  */ = depth/* : number */;
		let /* this.input  */ = input/* : string */;
		let /* this.index  */ = index/* : number */;
	}
	DivideState(input : string) : public {
		/* this(input, 0, Lists.empty(), new StringBuilder(), 0) */;
	}
	/* private */ advance() : DivideState {
		let /* this.segments  */ = /* this */.segments/* : unknown */.addLast/* : unknown */(/* this */.buffer/* : unknown */.toString/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
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
	/* public */ pop() : Option<[Character, DivideState]> {
		/* if (this.index < this.input.length())  */{
			let c : var = /* this */.input/* : unknown */.charAt/* : unknown */(/* this */.index/* : unknown */)/* : unknown */;
			return new Some(new /* Tuple */(/* c */, new DivideState(/* this */.input/* : unknown */, /* this */.index/* : unknown */ + 1/* : number */, /* this */.segments/* : unknown */, /* this */.buffer/* : unknown */, /* this */.depth/* : unknown */)/* : DivideState */)/* : content-start Tuple content-end */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* public */ popAndAppendToTuple() : Option<[Character, DivideState]> {
		return /* this */.pop/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((tuple : unknown) => new /* Tuple */(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */.append/* : unknown */(/* tuple */.left/* : unknown */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return /* this */.popAndAppendToTuple/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Tuple */.right/* : unknown */)/* : unknown */;
	}
	/* public */ peek() : char {
		return /* this */.input/* : unknown */.charAt/* : unknown */(/* this */.index/* : unknown */)/* : unknown */;
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
		return new None(/*  */)/* : None */;
	}
	/* @Override
        public */ fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current/* : Option<string> */.map/* : (arg0 : (arg0 : T) => R) => content-start Option content-end<R> */((inner : unknown) => /* inner */ + /* this */.delimiter/* : unknown */ + element/* : string */)/* : unknown */.orElse/* : unknown */(element/* : string */)/* : unknown */)/* : Some */;
	}
}
/* private */class Definition/*  */ {
	constructor (maybeBefore : Option<string>, name : string, type : Type, typeParams : List<string>) {
	}

	Definition(name : string, type : Type) : public {
		/* this(new None<>(), name, type, Lists.empty()) */;
	}
	/* private */ generate() : string {
		return /* this */.generateWithParams/* : unknown */("")/* : unknown */;
	}
	/* public */ generateWithParams(params : string) : string {
		let joined : var = /* this */.joinTypeParams/* : unknown */(/*  */)/* : unknown */;
		let before : var = /* this */.joinBefore/* : unknown */(/*  */)/* : unknown */;
		let typeString : var = /* this */.generateType/* : unknown */(/*  */)/* : unknown */;
		return /* before */ + /* this */.name/* : unknown */ + /* joined */ + params/* : string */ + /* typeString */;
	}
	/* private */ generateType() : string {
		/* if (this.type.equals(Primitive.Unknown))  */{
			return "";
		}
		return " : " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* private */ joinBefore() : string {
		return /* this */.maybeBefore/* : unknown */.filter/* : unknown */((value : unknown) => !/* value */.isEmpty/* : unknown */(/*  */)/* : unknown */)/* : unknown */.map/* : unknown */(/* Main */.generatePlaceholder/* : unknown */)/* : unknown */.map/* : unknown */((inner : unknown) => /* inner */ + " ")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* private */ joinTypeParams() : string {
		return /* this */.typeParams/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(/*  */)/* : Joiner */)/* : unknown */.map/* : unknown */((inner : unknown) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return /* Lists */.empty/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return current/* : List<T> */.addLast/* : (arg0 : T) => content-start List content-end<T> */(element/* : T */)/* : unknown */;
	}
}
/* private */class Tuple<A, B>/* (A left, B right)  */ {
}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : T) => Iterator<R>;
	/* private final */ head : Head<T>;
	/* private */ current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>) : public {
		let /* this.mapper  */ = mapper/* : (arg0 : T) => Iterator<R> */;
		let /* this.current  */ = new None(/*  */)/* : None */;
		let /* this.head  */ = head/* : Head<T> */;
	}
	/* @Override
        public */ next() : Option<R> {
		/* while (true)  */{
			/* if (this.current.isPresent())  */{
				let inner : Iterator<R> = /* this */.current/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
				let maybe : Option<R> = /* inner */.next/* : unknown */(/*  */)/* : unknown */;
				/* if (maybe.isPresent())  */{
					return /* maybe */;
				}
				/* else  */{
					let /* this.current  */ = new None(/*  */)/* : None */;
				}
			}
			let outer : Option<T> = /* this */.head/* : unknown */.next/* : unknown */(/*  */)/* : unknown */;
			/* if (outer.isPresent())  */{
				let /* this.current  */ = /* outer */.map/* : unknown */(/* this */.mapper/* : unknown */)/* : unknown */;
			}
			/* else  */{
				return new None(/*  */)/* : None */;
			}
		}
	}
}
/* private */class SymbolType/*  */ {
	constructor (input : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.input/* : unknown */;
	}
}
/* private */class ArrayType/*  */ {
	constructor (right : Type) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + "[]";
	}
}
/* private static */class Whitespace/*  */ {
}
/* private static */class Iterators/*  */ {
	/* public static  */ fromOption<T>(option : Option<T>) : Iterator<T> {
		let single : Option<Head<T>> = option/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => content-start Option content-end<R> */(/* SingleHead */.new/* : unknown */)/* : unknown */;
		return new HeadedIterator(/* single */.orElseGet/* : unknown */(/* EmptyHead */.new/* : unknown */)/* : unknown */)/* : HeadedIterator */;
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List<Type>, returns : Type) {
	}

	/* @Override
        public */ generate() : string {
		let joined : var = /* this */.arguments/* : unknown */(/*  */)/* : unknown */.iterateWithIndices/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((pair : unknown) => "arg" + /* pair */.left/* : unknown */ + " : " + /* pair */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + /* joined */ + ") => " + /* this */.returns/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
}
/* private */class TupleType/*  */ {
	constructor (arguments : List<Type>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments : var = /* this */.arguments/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
}
/* private */class Template/*  */ {
	constructor (base : FindableType, arguments : List<Type>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments : var = /* this */.arguments/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner : unknown) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return /* this */.base/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + /* joinedArguments */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return /* this */.base/* : unknown */.find/* : unknown */(name/* : string */)/* : unknown */;
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* generatePlaceholder */(/* this */.input/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : Option<Type> {
		return new None(/*  */)/* : None */;
	}
}
/* private */class StringValue/*  */ {
	constructor (stripped : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.stripped/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : Value, property : string, type : Type) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.parent/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + "." + /* this */.property/* : unknown */ + /* generatePlaceholder */(": " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : Type) {
	}

	/* @Override
        public */ generate() : string {
		return "new " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
}
/* private */class Operation/*  */ {
	constructor (left : Value, infix : string, right : Value) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.left/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + " " + /* this */.infix/* : unknown */ + " " + /* this */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Not/*  */ {
	constructor (value : Value) {
	}

	/* @Override
        public */ generate() : string {
		return "!" + /* this */.value/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
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
		let joined : var = /* this */.parameterNames/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((inner : unknown) => /* inner */ + " : unknown")/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + /* joined */ + ") => " + /* this */.body/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : Caller, arguments : List<Value>, type : Type) {
	}

	/* @Override
        public */ generate() : string {
		let joined : var = /* this */.arguments/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return /* this */.caller/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + "(" + /* joined */ + ")" + /* generatePlaceholder */(": " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : Value, child : Value) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.parent/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + "[" + this.child.generate() + "]";
	}
	/* @Override
        public */ type() : Type {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (stripped : string, type : Type) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.stripped/* : unknown */ + /* generatePlaceholder */(": " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
	}
}
/* public */class Main/*  */ {/* 

    private interface Type extends Argument {
        String generate();
    } *//* 

    private sealed interface Value extends LambdaValue, Caller {
        String generate();

        Type type();
    } *//* 

    private interface FindableType extends Type {
        Option<Type> find(String name);
    } */
	/* private */ CompileState(structures : List<string>, definitions : List<Definition>, types : List<ObjectType>) : record {
		/* public CompileState()  */{
			/* this(Lists.empty(), Lists.empty(), Lists.empty()) */;
		}
		/* private Option<Type> resolveValue(String name)  */{
			return /* this */.definitions/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.filter/* : unknown */((definition : unknown) => /* definition */.name/* : unknown */.equals/* : unknown */(/* name */)/* : unknown */)/* : unknown */.next/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */;
		}
		/* public CompileState addStructure(String structure)  */{
			return new CompileState(/* this */.structures/* : unknown */.addLast/* : unknown */(/* structure */)/* : unknown */, /* this */.definitions/* : unknown */, /* this */.types/* : unknown */)/* : CompileState */;
		}
		/* public CompileState withDefinitions(List<Definition> definitions)  */{
			return new CompileState(/* this */.structures/* : unknown */, definitions/* : List<Definition> */, /* this */.types/* : unknown */)/* : CompileState */;
		}
		/* public Option<ObjectType> resolveType(String name)  */{
			return /* this */.types/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.filter/* : unknown */((type : unknown) => /* type */.name/* : unknown */.equals/* : unknown */(/* name */)/* : unknown */)/* : unknown */.next/* : unknown */(/*  */)/* : unknown */;
		}
		/* public CompileState addType(ObjectType type)  */{
			return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.types/* : unknown */.addLast/* : unknown */(/* type */)/* : unknown */)/* : CompileState */;
		}
		/* public CompileState withDefinition(Definition definition)  */{
			return new CompileState(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */.addLast/* : unknown */(/* definition */)/* : unknown */, /* this */.types/* : unknown */)/* : CompileState */;
		}
	}
	/* public static */ main() : void {
		/* try  */{
			let parent : var = /* Paths */.get/* : unknown */(".", "src", "java", "magma")/* : unknown */;
			let source : var = /* parent */.resolve/* : unknown */("Main.java")/* : unknown */;
			let target : var = /* parent */.resolve/* : unknown */("main.ts")/* : unknown */;
			let input : var = /* Files */.readString/* : unknown */(/* source */)/* : unknown */;
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
		let tuple : var = /* compileStatements */(new CompileState(/*  */)/* : CompileState */, input/* : string */, /* Main */.compileRootSegment/* : unknown */)/* : unknown */;
		let joined : var = /* tuple */.left/* : unknown */.structures/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(/*  */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return /* joined */ + /* tuple */.right/* : unknown */;
	}
	/* private static */ compileStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed : var = /* parseStatements */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */)/* : unknown */;
		return new Tuple(/* parsed */.left/* : unknown */, /* generateStatements */(/* parsed */.right/* : unknown */)/* : unknown */)/* : Tuple */;
	}
	/* private static */ generateStatements(statements : List<string>) : string {
		return /* generateAll */(/* Main */.mergeStatements/* : unknown */, statements/* : List<string> */)/* : unknown */;
	}
	/* private static */ parseStatements(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, List<string>] {
		return /* parseAll */(state/* : CompileState */, input/* : string */, /* Main */.foldStatementChar/* : unknown */, mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */)/* : unknown */;
	}
	/* private static */ generateAll(merger : (arg0 : StringBuilder, arg1 : string) => StringBuilder, elements : List<string>) : string {
		return elements/* : List<string> */.iterate/* : () => Iterator<T> */(/*  */)/* : unknown */.fold/* : unknown */(new StringBuilder(/*  */)/* : StringBuilder */, merger/* : (arg0 : StringBuilder, arg1 : string) => StringBuilder */)/* : unknown */.toString/* : unknown */(/*  */)/* : unknown */;
	}
	/* private static  */ parseAll<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, T]) : [CompileState, List<T>] {
		return /* getCompileStateListTuple */(state/* : CompileState */, input/* : string */, folder/* : (arg0 : DivideState, arg1 : Character) => DivideState */, (state1 : unknown, s : unknown) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, T] */(/* state1 */, /* s */)/* : [CompileState, T] */)/* : Some */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(state/* : CompileState */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static  */ getCompileStateListTuple<T>(state : CompileState, input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		let initial : Option<[CompileState, List<T>]> = new Some(new Tuple(state/* : CompileState */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : Some */;
		return /* divideAll */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : Character) => DivideState */)/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.fold/* : unknown */(/* initial */, (tuple : unknown, element : unknown) => {
			return /* tuple */.flatMap/* : unknown */((inner : unknown) => {
				let state1 : var = /* inner */.left/* : unknown */;
				let right : var = /* inner */.right/* : unknown */;
				return mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */(/* state1 */, /* element */)/* : Option<[CompileState, T]> */.map/* : (arg0 : (arg0 : T) => R) => content-start Option content-end<R> */((applied : unknown) => {
					return new Tuple(/* applied */.left/* : unknown */, /* right */.addLast/* : unknown */(/* applied */.right/* : unknown */)/* : unknown */)/* : Tuple */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ mergeStatements(stringBuilder : StringBuilder, str : string) : StringBuilder {
		return stringBuilder/* : StringBuilder */.append/* : unknown */(str/* : string */)/* : unknown */;
	}
	/* private static */ divideAll(input : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState) : List<string> {
		let current : var = new DivideState(input/* : string */)/* : DivideState */;
		/* while (true)  */{
			let maybePopped : var = /* current */.pop/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((tuple : unknown) => {
				return /* foldSingleQuotes */(/* tuple */)/* : unknown */.or/* : unknown */(() => /* foldDoubleQuotes */(/* tuple */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => folder/* : (arg0 : DivideState, arg1 : Character) => DivideState */(/* tuple */.right/* : unknown */, /* tuple */.left/* : unknown */)/* : DivideState */)/* : unknown */;
			})/* : unknown */;
			/* if (maybePopped.isPresent())  */{
				let /* current  */ = /* maybePopped */.orElse/* : unknown */(/* current */)/* : unknown */;
			}
			/* else  */{
				/* break */;
			}
		}
		return /* current */.advance/* : unknown */(/*  */)/* : unknown */.segments/* : unknown */;
	}
	/* private static */ foldDoubleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {
		/* if (tuple.left == '\"')  */{
			let current : var = tuple/* : [Character, DivideState] */.right/* : unknown */.append/* : unknown */(tuple/* : [Character, DivideState] */[0/* : number */])/* : unknown */;
			/* while (true)  */{
				let maybePopped : var = /* current */.popAndAppendToTuple/* : unknown */(/*  */)/* : unknown */;
				/* if (maybePopped.isEmpty())  */{
					/* break */;
				}
				let popped : var = /* maybePopped */.orElse/* : unknown */(/* null */)/* : unknown */;
				let /* current  */ = /* popped */.right/* : unknown */;
				/* if (popped.left == '\\')  */{
					let /* current  */ = /* current */.popAndAppendToOption/* : unknown */(/*  */)/* : unknown */.orElse/* : unknown */(/* current */)/* : unknown */;
				}
				/* if (popped.left == '\"')  */{
					/* break */;
				}
			}
			return new Some(/* current */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ foldSingleQuotes(tuple : [Character, DivideState]) : Option<DivideState> {
		/* if (tuple.left != '\'')  */{
			return new None(/*  */)/* : None */;
		}
		let appended : var = tuple/* : [Character, DivideState] */.right/* : unknown */.append/* : unknown */(tuple/* : [Character, DivideState] */[0/* : number */])/* : unknown */;
		return /* appended */.popAndAppendToTuple/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* DivideState */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	/* private static */ foldEscaped(escaped : [Character, DivideState]) : DivideState {
		/* if (escaped.left == '\\')  */{
			return escaped/* : [Character, DivideState] */.right/* : unknown */.popAndAppendToOption/* : unknown */(/*  */)/* : unknown */.orElse/* : unknown */(escaped/* : [Character, DivideState] */.right/* : unknown */)/* : unknown */;
		}
		return escaped/* : [Character, DivideState] */.right/* : unknown */;
	}
	/* private static */ foldStatementChar(state : DivideState, c : char) : DivideState {
		let append : var = state/* : DivideState */.append/* : unknown */(c/* : char */)/* : unknown */;
		/* if (c == ';' && append.isLevel())  */{
			return /* append */.advance/* : unknown */(/*  */)/* : unknown */;
		}
		/* if (c == '}' && append.isShallow())  */{
			return /* append */.advance/* : unknown */(/*  */)/* : unknown */.exit/* : unknown */(/*  */)/* : unknown */;
		}
		/* if (c == '{' || c == '(')  */{
			return /* append */.enter/* : unknown */(/*  */)/* : unknown */;
		}
		/* if (c == '}' || c == ')')  */{
			return /* append */.exit/* : unknown */(/*  */)/* : unknown */;
		}
		return /* append */;
	}
	/* private static */ compileRootSegment(state : CompileState, input : string) : [CompileState, string] {
		let stripped : var = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */{
			return new Tuple(state/* : CompileState */, "")/* : Tuple */;
		}
		return /* compileClass */(/* stripped */, 0/* : number */, state/* : CompileState */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(state/* : CompileState */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ compileClass(stripped : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return /* compileStructure */(stripped/* : string */, "class ", "class ", state/* : CompileState */)/* : unknown */;
	}
	/* private static */ compileStructure(stripped : string, sourceInfix : string, targetInfix : string, state : CompileState) : Option<[CompileState, string]> {
		return /* first */(stripped/* : string */, sourceInfix/* : string */, (beforeInfix : unknown, right : unknown) => {
			return /* first */(/* right */, "{", (beforeContent : unknown, withEnd : unknown) => {
				let strippedWithEnd : var = /* withEnd */.strip/* : unknown */(/*  */)/* : unknown */;
				return /* suffix */(/* strippedWithEnd */, "}", (content1 : unknown) => {
					return /* first */(/* beforeContent */, " implements ", (s : unknown, s2 : unknown) => {
						return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : CompileState */, /* beforeInfix */, /* s */, /* content1 */)/* : unknown */;
					})/* : unknown */.or/* : unknown */(() => {
						return /* structureWithMaybeParams */(targetInfix/* : string */, state/* : CompileState */, /* beforeInfix */, /* beforeContent */, /* content1 */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string) : Option<[CompileState, string]> {
		return /* suffix */(beforeContent/* : string */, ")", (s : unknown) => {
			return /* first */(/* s */, "(", (s1 : unknown, s2 : unknown) => {
				let parsed : var = /* parseParameters */(state/* : CompileState */, /* s2 */)/* : unknown */;
				return /* getOred */(targetInfix/* : string */, /* parsed */.left/* : unknown */, beforeInfix/* : string */, /* s1 */, content1/* : string */, /* parsed */.right/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* getOred */(targetInfix/* : string */, state/* : CompileState */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOred(targetInfix : string, state : CompileState, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>) : Option<[CompileState, string]> {
		return /* first */(beforeContent/* : string */, "<", (name : unknown, withTypeParams : unknown) => {
			return /* first */(/* withTypeParams */, ">", (typeParamsString : unknown, afterTypeParams : unknown) => {
				let /* final */ compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1 : unknown, s : unknown) => new Tuple(/* state1 */, /* s */.strip/* : unknown */(/*  */)/* : unknown */)/* : Tuple */;
				let typeParams : var = /* parseValuesOrEmpty */(state/* : CompileState */, /* typeParamsString */, (state1 : unknown, s : unknown) => new Some(/* compileStateStringTupleBiFunction */.apply/* : unknown */(/* state1 */, /* s */)/* : unknown */)/* : Some */)/* : unknown */;
				return /* assemble */(/* typeParams */.left/* : unknown */, targetInfix/* : string */, beforeInfix/* : string */, /* name */, content1/* : string */, /* typeParams */.right/* : unknown */, /* afterTypeParams */, params/* : List<Parameter> */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* assemble */(state/* : CompileState */, targetInfix/* : string */, beforeInfix/* : string */, beforeContent/* : string */, content1/* : string */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */, "", params/* : List<Parameter> */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assemble(state : CompileState, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string, params : List<Parameter>) : Option<[CompileState, string]> {
		let name : var = rawName/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (!isSymbol(name))  */{
			return new None(/*  */)/* : None */;
		}
		let joinedTypeParams : var = typeParams/* : List<string> */.iterate/* : () => Iterator<T> */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner : unknown) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let parsed : var = /* parseStatements */(state/* : CompileState */, content/* : string */, (state0 : unknown, input : unknown) => /* compileClassSegment */(/* state0 */, /* input */, 1/* : number */)/* : unknown */)/* : unknown */;
		/* List<String> parsed1 */;
		/* if (params.isEmpty())  */{
			let /* parsed1  */ = /* parsed */.right/* : unknown */;
		}
		/* else  */{
			let joined : var = /* joinValues */(/* retainDefinitions */(params/* : List<Parameter> */)/* : unknown */)/* : unknown */;
			let constructorIndent : var = /* createIndent */(1/* : number */)/* : unknown */;
			let /* parsed1  */ = /* parsed */.right/* : unknown */.addFirst/* : unknown */(/* constructorIndent */ + "constructor (" + joined + ") {" + constructorIndent + "}\n")/* : unknown */;
		}
		let parsed2 : var = /* parsed1 */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(/*  */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let generated : var = /* generatePlaceholder */(beforeInfix/* : string */.strip/* : unknown */(/*  */)/* : unknown */)/* : unknown */ + targetInfix/* : string */ + /* name */ + /* joinedTypeParams */ + /* generatePlaceholder */(afterTypeParams/* : string */)/* : unknown */ + " {" + parsed2 + "\n}\n";
		return new Some(new Tuple(/* parsed */.left/* : unknown */.addStructure/* : unknown */(/* generated */)/* : unknown */.addType/* : unknown */(new ObjectType(/* name */, /* parsed */.left/* : unknown */.definitions/* : unknown */)/* : ObjectType */)/* : unknown */, "")/* : Tuple */)/* : Some */;
	}
	/* private static */ retainDefinition(parameter : Parameter) : Option<Definition> {
		/* if (parameter instanceof Definition definition)  */{
			return new Some(/* definition */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c : var = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			/* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		/* if (!input.endsWith(suffix))  */{
			return new None(/*  */)/* : None */;
		}
		let slice : var = input/* : string */.substring/* : unknown */(0/* : number */, input/* : string */.length/* : unknown */(/*  */)/* : unknown */ - suffix/* : string */.length/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */(/* slice */)/* : Option<T> */;
	}
	/* private static */ compileClassSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return /* compileWhitespace */(input/* : string */, state/* : CompileState */)/* : unknown */.or/* : unknown */(() => /* compileClass */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileStructure */(input/* : string */, "interface ", "interface ", state/* : CompileState */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileStructure */(input/* : string */, "record ", "class ", state/* : CompileState */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileMethod */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileDefinitionStatement */(input/* : string */, depth/* : number */, state/* : CompileState */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(state/* : CompileState */, /* generatePlaceholder */(input/* : string */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ compileWhitespace(input : string, state : CompileState) : Option<[CompileState, string]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple(state/* : CompileState */, "")/* : Tuple */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ compileMethod(state : CompileState, input : string, depth : number) : Option<[CompileState, string]> {
		return /* first */(input/* : string */, "(", (definitionString : unknown, withParams : unknown) => {
			return /* first */(/* withParams */, ")", (parametersString : unknown, rawContent : unknown) => {
				return /* parseDefinition */(state/* : CompileState */, /* definitionString */)/* : unknown */.flatMap/* : unknown */((definitionTuple : unknown) => {
					let definitionState : var = /* definitionTuple */.left/* : unknown */;
					let definition : var = /* definitionTuple */.right/* : unknown */;
					let parametersTuple : var = /* parseParameters */(/* definitionState */, /* parametersString */)/* : unknown */;
					let rawParameters : var = /* parametersTuple */.right/* : unknown */;
					let parameters : var = /* retainDefinitions */(/* rawParameters */)/* : unknown */;
					let joinedParameters : var = /* joinValues */(/* parameters */)/* : unknown */;
					let content : var = /* rawContent */.strip/* : unknown */(/*  */)/* : unknown */;
					let indent : var = /* createIndent */(depth/* : number */)/* : unknown */;
					let paramTypes : var = /* parameters */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector(/*  */)/* : ListCollector */)/* : unknown */;
					let toDefine : var = new Definition(/* definition */.name/* : unknown */, new FunctionType(/* paramTypes */, /* definition */.type/* : unknown */)/* : FunctionType */)/* : Definition */;
					let generatedHeader : var = /* definition */.generateWithParams/* : unknown */("(" + joinedParameters + ")")/* : unknown */;
					/* if (content.equals(";"))  */{
						return new Some(new Tuple(/* parametersTuple */.left/* : unknown */.withDefinition/* : unknown */(/* toDefine */)/* : unknown */, /* indent */ + /* generatedHeader */ + ";")/* : Tuple */)/* : Some */;
					}
					/* if (content.startsWith("{") && content.endsWith("}"))  */{
						let substring : var = /* content */.substring/* : unknown */(1/* : number */, /* content */.length/* : unknown */(/*  */)/* : unknown */ - 1/* : number */)/* : unknown */;
						let statementsTuple : var = /* compileFunctionSegments */(/* parametersTuple */.left/* : unknown */.withDefinitions/* : unknown */(/* parameters */)/* : unknown */, /* substring */, depth/* : number */)/* : unknown */;
						let generated : var = /* indent */ + /* generatedHeader */ + " {" + statementsTuple.right + indent + "}";
						return new Some(new Tuple(/* statementsTuple */.left/* : unknown */.withDefinition/* : unknown */(/* toDefine */)/* : unknown */, /* generated */)/* : Tuple */)/* : Some */;
					}
					return new None(/*  */)/* : None */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ joinValues(retainParameters : List<Definition>) : string {
		return retainParameters/* : List<Definition> */.iterate/* : () => Iterator<T> */(/*  */)/* : unknown */.map/* : unknown */(/* Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* private static */ retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right/* : List<Parameter> */.iterate/* : () => Iterator<T> */(/*  */)/* : unknown */.map/* : unknown */(/* Main */.retainDefinition/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector(/*  */)/* : ListCollector */)/* : unknown */;
	}
	/* private static */ parseParameters(state : CompileState, params : string) : [CompileState, List<Parameter>] {
		return /* parseValuesOrEmpty */(state/* : CompileState */, params/* : string */, (state1 : unknown, s : unknown) => new Some(/* compileParameter */(/* state1 */, /* s */)/* : unknown */)/* : Some */)/* : unknown */;
	}
	/* private static */ compileFunctionSegments(state : CompileState, input : string, depth : number) : [CompileState, string] {
		return /* compileStatements */(state/* : CompileState */, input/* : string */, (state1 : unknown, input1 : unknown) => /* compileFunctionSegment */(/* state1 */, /* input1 */, depth/* : number */ + 1/* : number */)/* : unknown */)/* : unknown */;
	}
	/* private static */ compileFunctionSegment(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped : var = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.isEmpty())  */{
			return new Tuple(state/* : CompileState */, "")/* : Tuple */;
		}
		return /* suffix */(/* stripped */, ";", (s : unknown) => {
			let tuple : var = /* statementValue */(state/* : CompileState */, /* s */, depth/* : number */)/* : unknown */;
			return new Some(new Tuple(/* tuple */.left/* : unknown */, /* createIndent */(depth/* : number */)/* : unknown */ + /* tuple */.right/* : unknown */ + ";")/* : Tuple */)/* : Some */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* block */(state/* : CompileState */, depth/* : number */, /* stripped */)/* : unknown */;
		})/* : unknown */.orElseGet/* : unknown */(() => {
			return new Tuple(state/* : CompileState */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : Tuple */;
		})/* : unknown */;
	}
	/* private static */ block(state : CompileState, depth : number, stripped : string) : Option<[CompileState, string]> {
		return /* suffix */(stripped/* : string */, "}", (withoutEnd : unknown) => {
			return /* split */(() => {
				let divisions : var = /* divideAll */(/* withoutEnd */, /* Main */.foldBlockStart/* : unknown */)/* : unknown */;
				return /* divisions */.removeFirst/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((removed : unknown) => {
					let right : var = /* removed */.left/* : unknown */;
					let left : var = /* removed */.right/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
					return new Tuple(/* right */, /* left */)/* : Tuple */;
				})/* : unknown */;
			}, (beforeContent : unknown, content : unknown) => {
				return /* suffix */(/* beforeContent */, "{", (s : unknown) => {
					let compiled : var = /* compileFunctionSegments */(state/* : CompileState */, /* content */, depth/* : number */)/* : unknown */;
					let indent : var = /* createIndent */(depth/* : number */)/* : unknown */;
					return new Some(new Tuple(/* compiled */.left/* : unknown */, /* indent */ + /* generatePlaceholder */(/* s */)/* : unknown */ + "{" + compiled.right + indent + "}")/* : Tuple */)/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ foldBlockStart(state : DivideState, c : Character) : DivideState {
		let appended : var = state/* : DivideState */.append/* : unknown */(c/* : Character */)/* : unknown */;
		/* if (c == '{' && state.isLevel())  */{
			return /* appended */.advance/* : unknown */(/*  */)/* : unknown */;
		}
		/* if (c == '{')  */{
			return /* appended */.enter/* : unknown */(/*  */)/* : unknown */;
		}
		/* if (c == '}')  */{
			return /* appended */.exit/* : unknown */(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ statementValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let stripped : var = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.startsWith("return "))  */{
			let value : var = /* stripped */.substring/* : unknown */("return ".length/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
			let tuple : var = /* compileValue */(state/* : CompileState */, /* value */, depth/* : number */)/* : unknown */;
			return new Tuple(/* tuple */.left/* : unknown */, "return " + /* tuple */.right/* : unknown */)/* : Tuple */;
		}
		return /* first */(/* stripped */, "=", (s : unknown, s2 : unknown) => {
			let definitionTuple : var = /* compileDefinition */(state/* : CompileState */, /* s */)/* : unknown */;
			let valueTuple : var = /* compileValue */(/* definitionTuple */.left/* : unknown */, /* s2 */, depth/* : number */)/* : unknown */;
			return new Some(new Tuple(/* valueTuple */.left/* : unknown */, "let " + /* definitionTuple */.right/* : unknown */ + " = " + /* valueTuple */.right/* : unknown */)/* : Tuple */)/* : Some */;
		})/* : unknown */.orElseGet/* : unknown */(() => {
			return new Tuple(state/* : CompileState */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : Tuple */;
		})/* : unknown */;
	}
	/* private static */ compileValue(state : CompileState, input : string, depth : number) : [CompileState, string] {
		let tuple : var = /* parseValue */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */;
		return new Tuple(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : Tuple */;
	}
	/* private static */ parseValue(state : CompileState, input : string, depth : number) : [CompileState, Value] {
		return /* parseLambda */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */.or/* : unknown */(() => /* parseString */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDataAccess */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseSymbolValue */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInvocation */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, "+")/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(state/* : CompileState */, input/* : string */, depth/* : number */, "-")/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDigits */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseNot */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseMethodReference */(state/* : CompileState */, input/* : string */, depth/* : number */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new [CompileState, Value](state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : [CompileState, Value] */)/* : unknown */;
	}
	/* private static */ parseMethodReference(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* last */(input/* : string */, "::", (s : unknown, s2 : unknown) => {
			let tuple : var = /* parseValue */(state/* : CompileState */, /* s */, depth/* : number */)/* : unknown */;
			return new Some(new Tuple(/* tuple */.left/* : unknown */, new DataAccess(/* tuple */.right/* : unknown */, /* s2 */, /* Primitive */.Unknown/* : unknown */)/* : DataAccess */)/* : Tuple */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ parseNot(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		let stripped : var = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.startsWith("!"))  */{
			let slice : var = /* stripped */.substring/* : unknown */(1/* : number */)/* : unknown */;
			let tuple : var = /* parseValue */(state/* : CompileState */, /* slice */, depth/* : number */)/* : unknown */;
			let value : var = /* tuple */.right/* : unknown */;
			return new Some(new Tuple(/* tuple */.left/* : unknown */, new Not(/* value */)/* : Not */)/* : Tuple */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ parseLambda(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* first */(input/* : string */, "->", (beforeArrow : unknown, valueString : unknown) => {
			let strippedBeforeArrow : var = /* beforeArrow */.strip/* : unknown */(/*  */)/* : unknown */;
			/* if (isSymbol(strippedBeforeArrow))  */{
				return /* assembleLambda */(state/* : CompileState */, /* Lists */.of/* : unknown */(/* strippedBeforeArrow */)/* : unknown */, /* valueString */, depth/* : number */)/* : unknown */;
			}
			/* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */{
				let parameterNames : var = /* divideAll */(/* strippedBeforeArrow */.substring/* : unknown */(1/* : number */, /* strippedBeforeArrow */.length/* : unknown */(/*  */)/* : unknown */ - 1/* : number */)/* : unknown */, /* Main */.foldValueChar/* : unknown */)/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value : unknown) => !/* value */.isEmpty/* : unknown */(/*  */)/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector(/*  */)/* : ListCollector */)/* : unknown */;
				return /* assembleLambda */(state/* : CompileState */, /* parameterNames */, /* valueString */, depth/* : number */)/* : unknown */;
			}
			return new None(/*  */)/* : None */;
		})/* : unknown */;
	}
	/* private static */ assembleLambda(state : CompileState, paramNames : List<string>, valueString : string, depth : number) : Some<[CompileState, Value]> {
		let strippedValueString : var = valueString/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* Tuple<CompileState, LambdaValue> value */;
		/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */{
			let value1 : var = /* compileStatements */(state/* : CompileState */, /* strippedValueString */.substring/* : unknown */(1/* : number */, /* strippedValueString */.length/* : unknown */(/*  */)/* : unknown */ - 1/* : number */)/* : unknown */, (state1 : unknown, input1 : unknown) => /* compileFunctionSegment */(/* state1 */, /* input1 */, depth/* : number */ + 1/* : number */)/* : unknown */)/* : unknown */;
			let right : var = /* value1 */.right/* : unknown */;
			let /* value  */ = new Tuple(/* value1 */.left/* : unknown */, new BlockLambdaValue(/* right */, depth/* : number */)/* : BlockLambdaValue */)/* : Tuple */;
		}
		/* else  */{
			let value1 : var = /* parseValue */(state/* : CompileState */, /* strippedValueString */, depth/* : number */)/* : unknown */;
			let /* value  */ = new Tuple(/* value1 */.left/* : unknown */, /* value1 */.right/* : unknown */)/* : Tuple */;
		}
		let right : var = /* value */.right/* : unknown */;
		return new Some(new Tuple(/* value */.left/* : unknown */, new Lambda(paramNames/* : List<string> */, /* right */)/* : Lambda */)/* : Tuple */)/* : Some */;
	}
	/* private static */ parseDigits(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped : var = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (isNumber(stripped))  */{
			return new Some(new [CompileState, Value](state/* : CompileState */, new SymbolValue(/* stripped */, /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : [CompileState, Value] */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ isNumber(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c : var = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			/* if (Character.isDigit(c))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	/* private static */ parseInvocation(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* suffix */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, ")", (withoutEnd : unknown) => {
			return /* split */(() => /* toLast */(/* withoutEnd */, "", /* Main */.foldInvocationStart/* : unknown */)/* : unknown */, (callerWithEnd : unknown, argumentsString : unknown) => {
				return /* suffix */(/* callerWithEnd */, "(", (callerString : unknown) => {
					let callerString1 : var = /* callerString */.strip/* : unknown */(/*  */)/* : unknown */;
					let callerTuple : var = /* invocationHeader */(state/* : CompileState */, depth/* : number */, /* callerString1 */)/* : unknown */;
					let parsed : var = /* parseValues */(/* callerTuple */.left/* : unknown */, /* argumentsString */, (state3 : unknown, s : unknown) => new Some(/* parseValue */(/* state3 */, /* s */, depth/* : number */)/* : unknown */)/* : Some */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(/* callerTuple */.left/* : unknown */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */;
					let oldCaller : var = /* callerTuple */.right/* : unknown */;
					let arguments : var = /* parsed */.right/* : unknown */;
					let newCaller : var = /* modifyCaller */(/* parsed */.left/* : unknown */, /* oldCaller */)/* : unknown */;
					let var : Type = /* Primitive */.Unknown/* : unknown */;
					/* switch (newCaller)  */{
						/* case ConstructionCaller constructionCaller ->  */{
							let /* var  */ = /* constructionCaller */.type/* : unknown */;
						}
						/* case Value value ->  */{
							let type : var = /* value */.type/* : unknown */(/*  */)/* : unknown */;
							/* if (type instanceof FunctionType functionType)  */{
								let /* var  */ = /* functionType */.returns/* : unknown */;
							}
						}
					}
					let invokable : var = new Invokable(/* newCaller */, /* arguments */, /* var */)/* : Invokable */;
					return new Some(new Tuple(/* parsed */.left/* : unknown */, /* invokable */)/* : Tuple */)/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ modifyCaller(state : CompileState, oldCaller : Caller) : Caller {
		/* if (oldCaller instanceof DataAccess access)  */{
			let type : var = /* resolveType */(/* access */.parent/* : unknown */, state/* : CompileState */)/* : unknown */;
			/* if (type instanceof FunctionType)  */{
				return /* access */.parent/* : unknown */;
			}
		}
		return oldCaller/* : Caller */;
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
			let input1 : string = callerString1/* : string */.substring/* : unknown */("new ".length/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
			let map : var = /* parseType */(state/* : CompileState */, /* input1 */)/* : unknown */.map/* : unknown */((type : unknown) => {
				let right : var = /* type */.right/* : unknown */;
				return new [CompileState, Caller](/* type */.left/* : unknown */, new ConstructionCaller(/* right */)/* : ConstructionCaller */)/* : [CompileState, Caller] */;
			})/* : unknown */;
			/* if (map.isPresent())  */{
				return /* map */.orElse/* : unknown */(/* null */)/* : unknown */;
			}
		}
		let tuple : var = /* parseValue */(state/* : CompileState */, callerString1/* : string */, depth/* : number */)/* : unknown */;
		return new Tuple(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */)/* : Tuple */;
	}
	/* private static */ foldInvocationStart(state : DivideState, c : char) : DivideState {
		let appended : var = state/* : DivideState */.append/* : unknown */(c/* : char */)/* : unknown */;
		/* if (c == '(')  */{
			let enter : var = /* appended */.enter/* : unknown */(/*  */)/* : unknown */;
			/* if (enter.isShallow())  */{
				return /* enter */.advance/* : unknown */(/*  */)/* : unknown */;
			}
			return /* enter */;
		}
		/* if (c == ')')  */{
			return /* appended */.exit/* : unknown */(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ parseDataAccess(state : CompileState, input : string, depth : number) : Option<[CompileState, Value]> {
		return /* last */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, ".", (parentString : unknown, rawProperty : unknown) => {
			let property : var = /* rawProperty */.strip/* : unknown */(/*  */)/* : unknown */;
			/* if (!isSymbol(property))  */{
				return new None(/*  */)/* : None */;
			}
			let tuple : var = /* parseValue */(state/* : CompileState */, /* parentString */, depth/* : number */)/* : unknown */;
			let parent : var = /* tuple */.right/* : unknown */;
			let parentType : var = /* parent */.type/* : unknown */(/*  */)/* : unknown */;
			/* if (parentType instanceof TupleType)  */{
				/* if (property.equals("left"))  */{
					return new Some(new Tuple(state/* : CompileState */, new IndexValue(/* parent */, new SymbolValue("0", /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */)/* : Tuple */)/* : Some */;
				}
				/* if (property.equals("type"))  */{
					return new Some(new Tuple(state/* : CompileState */, new IndexValue(/* parent */, new SymbolValue("1", /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */)/* : Tuple */)/* : Some */;
				}
			}
			let type : Type = /* Primitive */.Unknown/* : unknown */;
			/* if (parentType instanceof FindableType objectType)  */{
				/* if (objectType.find(property) instanceof Some(var memberType))  */{
					let /* type  */ = /* memberType */;
				}
			}
			return new Some(new Tuple(/* tuple */.left/* : unknown */, new DataAccess(/* parent */, /* property */, /* type */)/* : DataAccess */)/* : Tuple */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ parseString(state : CompileState, input : string) : Option<[CompileState, Value]> {
		let stripped : var = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
			return new Some(new Tuple(state/* : CompileState */, new StringValue(/* stripped */)/* : StringValue */)/* : Tuple */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ parseSymbolValue(state : CompileState, value : string) : Option<[CompileState, Value]> {
		let stripped : var = value/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (isSymbol(stripped))  */{
			/* if (state.resolveValue(stripped) instanceof Some(var type))  */{
				return new Some(new Tuple(state/* : CompileState */, new SymbolValue(/* stripped */, /* type */)/* : SymbolValue */)/* : Tuple */)/* : Some */;
			}
			return new Some(new Tuple(state/* : CompileState */, new Placeholder(/* stripped */)/* : Placeholder */)/* : Tuple */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ parseOperation(state : CompileState, value : string, depth : number, infix : string) : Option<[CompileState, Value]> {
		return /* first */(value/* : string */, infix/* : string */, (s : unknown, s2 : unknown) => {
			let tuple : var = /* parseValue */(state/* : CompileState */, /* s */, depth/* : number */)/* : unknown */;
			let tuple1 : var = /* parseValue */(/* tuple */.left/* : unknown */, /* s2 */, depth/* : number */)/* : unknown */;
			let left : var = /* tuple */.right/* : unknown */;
			let right : var = /* tuple1 */.right/* : unknown */;
			return new Some(new Tuple(/* tuple1 */.left/* : unknown */, new Operation(/* left */, infix/* : string */, /* right */)/* : Operation */)/* : Tuple */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ compileValues(state : CompileState, params : string, mapper : (arg0 : CompileState, arg1 : string) => [CompileState, string]) : [CompileState, string] {
		let parsed : var = /* parseValuesOrEmpty */(state/* : CompileState */, params/* : string */, (state1 : unknown, s : unknown) => new Some(mapper/* : (arg0 : CompileState, arg1 : string) => [CompileState, string] */(/* state1 */, /* s */)/* : [CompileState, string] */)/* : Some */)/* : unknown */;
		let generated : var = /* generateValues */(/* parsed */.right/* : unknown */)/* : unknown */;
		return new Tuple(/* parsed */.left/* : unknown */, /* generated */)/* : Tuple */;
	}
	/* private static */ generateValues(elements : List<string>) : string {
		return /* generateAll */(/* Main */.mergeValues/* : unknown */, elements/* : List<string> */)/* : unknown */;
	}
	/* private static  */ parseValuesOrEmpty<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : [CompileState, List<T>] {
		return /* parseValues */(state/* : CompileState */, input/* : string */, mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(state/* : CompileState */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static  */ parseValues<T>(state : CompileState, input : string, mapper : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]>) : Option<[CompileState, List<T>]> {
		return /* getCompileStateListTuple */(state/* : CompileState */, input/* : string */, /* Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : CompileState, arg1 : string) => Option<[CompileState, T]> */)/* : unknown */;
	}
	/* private static */ compileParameter(state : CompileState, input : string) : [CompileState, Parameter] {
		/* if (input.isBlank())  */{
			return new Tuple(state/* : CompileState */, new Whitespace(/*  */)/* : Whitespace */)/* : Tuple */;
		}
		return /* parseDefinition */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple : unknown) => new [CompileState, Parameter](/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */)/* : [CompileState, Parameter] */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(state/* : CompileState */, new Placeholder(input/* : string */)/* : Placeholder */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ compileDefinition(state : CompileState, input : string) : [CompileState, string] {
		return /* parseDefinition */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple : unknown) => new Tuple(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(state/* : CompileState */, /* generatePlaceholder */(input/* : string */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ mergeValues(cache : StringBuilder, element : string) : StringBuilder {
		/* if (cache.isEmpty())  */{
			return cache/* : StringBuilder */.append/* : unknown */(element/* : string */)/* : unknown */;
		}
		return cache/* : StringBuilder */.append/* : unknown */(", ")/* : unknown */.append/* : unknown */(element/* : string */)/* : unknown */;
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : CompileState) : Option<[CompileState, string]> {
		return /* suffix */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, ";", (withoutEnd : unknown) => {
			return /* parseDefinition */(state/* : CompileState */, /* withoutEnd */)/* : unknown */.map/* : unknown */((result : unknown) => {
				let generated : var = /* createIndent */(depth/* : number */)/* : unknown */ + /* result */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + ";";
				return new Tuple(/* result */.left/* : unknown */, /* generated */)/* : Tuple */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseDefinition(state : CompileState, input : string) : Option<[CompileState, Definition]> {
		return /* last */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, " ", (beforeName : unknown, name : unknown) => {
			return /* split */(() => /* toLast */(/* beforeName */, " ", /* Main */.foldTypeSeparator/* : unknown */)/* : unknown */, (beforeType : unknown, type : unknown) => {
				return /* suffix */(/* beforeType */.strip/* : unknown */(/*  */)/* : unknown */, ">", (withoutTypeParamStart : unknown) => {
					return /* first */(/* withoutTypeParamStart */, "<", (beforeTypeParams : unknown, typeParamsString : unknown) => {
						let /* final */ compileStateStringTupleBiFunction : (arg0 : CompileState, arg1 : string) => [CompileState, string] = (state1 : unknown, s : unknown) => new Tuple(/* state1 */, /* s */.strip/* : unknown */(/*  */)/* : unknown */)/* : Tuple */;
						let typeParams : var = /* parseValuesOrEmpty */(state/* : CompileState */, /* typeParamsString */, (state1 : unknown, s : unknown) => new Some(/* compileStateStringTupleBiFunction */.apply/* : unknown */(/* state1 */, /* s */)/* : unknown */)/* : Some */)/* : unknown */;
						return /* assembleDefinition */(/* typeParams */.left/* : unknown */, new Some<string>(/* beforeTypeParams */)/* : Some<string> */, /* name */, /* typeParams */.right/* : unknown */, /* type */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */.or/* : unknown */(() => {
					return /* assembleDefinition */(state/* : CompileState */, new Some<string>(/* beforeType */)/* : Some<string> */, /* name */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */, /* type */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */.or/* : unknown */(() => {
				return /* assembleDefinition */(state/* : CompileState */, new None<string>(/*  */)/* : None<string> */, /* name */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */, /* beforeName */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : Character) => DivideState) : Option<[string, string]> {
		let divisions : var = /* divideAll */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : Character) => DivideState */)/* : unknown */;
		return /* divisions */.removeLast/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((removed : unknown) => {
			let left : var = /* removed */.left/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(separator/* : string */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let right : var = /* removed */.right/* : unknown */;
			return new Tuple(/* left */, /* right */)/* : Tuple */;
		})/* : unknown */;
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : Character) : DivideState {
		/* if (c == ' ' && state.isLevel())  */{
			return state/* : DivideState */.advance/* : unknown */(/*  */)/* : unknown */;
		}
		let appended : var = state/* : DivideState */.append/* : unknown */(c/* : Character */)/* : unknown */;
		/* if (c == '<')  */{
			return /* appended */.enter/* : unknown */(/*  */)/* : unknown */;
		}
		/* if (c == '>')  */{
			return /* appended */.exit/* : unknown */(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ assembleDefinition(state : CompileState, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[CompileState, Definition]> {
		return /* parseType */(state/* : CompileState */, type/* : string */)/* : unknown */.map/* : unknown */((type1 : unknown) => {
			let node : var = new Definition(beforeTypeParams/* : Option<string> */, name/* : string */.strip/* : unknown */(/*  */)/* : unknown */, /* type1 */.right/* : unknown */, typeParams/* : List<string> */)/* : Definition */;
			return new Tuple(/* type1 */.left/* : unknown */, /* node */)/* : Tuple */;
		})/* : unknown */;
	}
	/* private static */ foldValueChar(state : DivideState, c : char) : DivideState {
		/* if (c == ',' && state.isLevel())  */{
			return state/* : DivideState */.advance/* : unknown */(/*  */)/* : unknown */;
		}
		let appended : var = state/* : DivideState */.append/* : unknown */(c/* : char */)/* : unknown */;
		/* if (c == '-')  */{
			let peeked : var = /* appended */.peek/* : unknown */(/*  */)/* : unknown */;
			/* if (peeked == '>')  */{
				return /* appended */.popAndAppendToOption/* : unknown */(/*  */)/* : unknown */.orElse/* : unknown */(/* appended */)/* : unknown */;
			}
			/* else  */{
				return /* appended */;
			}
		}
		/* if (c == '<' || c == '(' || c == '{')  */{
			return /* appended */.enter/* : unknown */(/*  */)/* : unknown */;
		}
		/* if (c == '>' || c == ')' || c == '}')  */{
			return /* appended */.exit/* : unknown */(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ compileType(state : CompileState, input : string) : Option<[CompileState, string]> {
		return /* parseType */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple : unknown) => new Tuple(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ parseType(state : CompileState, input : string) : Option<[CompileState, Type]> {
		let stripped : var = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.equals("int") || stripped.equals("Integer"))  */{
			return new Some(new Tuple(state/* : CompileState */, /* Primitive */.Int/* : unknown */)/* : Tuple */)/* : Some */;
		}
		/* if (stripped.equals("String"))  */{
			return new Some(new Tuple(state/* : CompileState */, /* Primitive */.String/* : unknown */)/* : Tuple */)/* : Some */;
		}
		/* if (stripped.equals("type"))  */{
			return new Some(new Tuple(state/* : CompileState */, /* Primitive */.Unknown/* : unknown */)/* : Tuple */)/* : Some */;
		}
		/* if (isSymbol(stripped))  */{
			return new Some(new Tuple(state/* : CompileState */, new SymbolType(/* stripped */)/* : SymbolType */)/* : Tuple */)/* : Some */;
		}
		return /* parseTemplate */(state/* : CompileState */, input/* : string */)/* : unknown */.or/* : unknown */(() => /* varArgs */(state/* : CompileState */, input/* : string */)/* : unknown */)/* : unknown */;
	}
	/* private static */ varArgs(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return /* suffix */(input/* : string */, "...", (s : unknown) => {
			return /* parseType */(state/* : CompileState */, /* s */)/* : unknown */.map/* : unknown */((inner : unknown) => {
				let newState : var = /* inner */.left/* : unknown */;
				let child : var = /* inner */.right/* : unknown */;
				return new Tuple(/* newState */, new ArrayType(/* child */)/* : ArrayType */)/* : Tuple */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseTemplate(state : CompileState, input : string) : Option<[CompileState, Type]> {
		return /* suffix */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, ">", (withoutEnd : unknown) => {
			return /* first */(/* withoutEnd */, "<", (base : unknown, argumentsString : unknown) => {
				let strippedBase : var = /* base */.strip/* : unknown */(/*  */)/* : unknown */;
				return /* parseValues */(state/* : CompileState */, /* argumentsString */, /* Main */.argument/* : unknown */)/* : unknown */.map/* : unknown */((argumentsTuple : unknown) => {
					return /* assembleTemplate */(/* strippedBase */, /* argumentsTuple */.left/* : unknown */, /* argumentsTuple */.right/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleTemplate(base : string, state : CompileState, arguments : List<Argument>) : [CompileState, Type] {
		let children : var = arguments/* : List<Argument> */.iterate/* : () => Iterator<T> */(/*  */)/* : unknown */.map/* : unknown */(/* Main */.retainType/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* Iterators */.fromOption/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector(/*  */)/* : ListCollector */)/* : unknown */;
		/* if (base.equals("BiFunction"))  */{
			return new Tuple(state/* : CompileState */, new FunctionType(/* Lists */.of/* : unknown */(/* children */.get/* : unknown */(0/* : number */)/* : unknown */, /* children */.get/* : unknown */(1/* : number */)/* : unknown */)/* : unknown */, /* children */.get/* : unknown */(2/* : number */)/* : unknown */)/* : FunctionType */)/* : Tuple */;
		}
		/* if (base.equals("Function"))  */{
			return new Tuple(state/* : CompileState */, new FunctionType(/* Lists */.of/* : unknown */(/* children */.get/* : unknown */(0/* : number */)/* : unknown */)/* : unknown */, /* children */.get/* : unknown */(1/* : number */)/* : unknown */)/* : FunctionType */)/* : Tuple */;
		}
		/* if (base.equals("Predicate"))  */{
			return new Tuple(state/* : CompileState */, new FunctionType(/* Lists */.of/* : unknown */(/* children */.get/* : unknown */(0/* : number */)/* : unknown */)/* : unknown */, /* Primitive */.Boolean/* : unknown */)/* : FunctionType */)/* : Tuple */;
		}
		/* if (base.equals("Supplier"))  */{
			return new Tuple(state/* : CompileState */, new FunctionType(/* Lists */.empty/* : unknown */(/*  */)/* : unknown */, /* children */.get/* : unknown */(0/* : number */)/* : unknown */)/* : FunctionType */)/* : Tuple */;
		}
		/* if (base.equals("Tuple") && children.size() >= 2)  */{
			return new Tuple(state/* : CompileState */, new TupleType(/* children */)/* : TupleType */)/* : Tuple */;
		}
		/* if (state.resolveType(base) instanceof Some(var baseType))  */{
			return new Tuple(state/* : CompileState */, new Template(/* baseType */, /* children */)/* : Template */)/* : Tuple */;
		}
		/* else  */{
			return new Tuple(state/* : CompileState */, new Template(new Placeholder(base/* : string */)/* : Placeholder */, /* children */)/* : Template */)/* : Tuple */;
		}
	}
	/* private static */ retainType(argument : Argument) : Option<Type> {
		/* if (argument instanceof Type type)  */{
			return new Some(/* type */)/* : Some */;
		}
		/* else  */{
			return new None<Type>(/*  */)/* : None<Type> */;
		}
	}
	/* private static */ argument(state : CompileState, input : string) : Option<[CompileState, Argument]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple(state/* : CompileState */, new Whitespace(/*  */)/* : Whitespace */)/* : Tuple */)/* : Some */;
		}
		return /* parseType */(state/* : CompileState */, input/* : string */)/* : unknown */.map/* : unknown */((tuple : unknown) => new Tuple(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, /* Main */.findLast/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	/* private static */ findLast(input : string, infix : string) : Option<number> {
		let index : var = input/* : string */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		/* if (index == -1)  */{
			return new None<number>(/*  */)/* : None<number> */;
		}
		return new Some(/* index */)/* : Some */;
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, /* Main */.findFirst/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return /* split */(() => locator/* : (arg0 : string, arg1 : string) => Option<number> */(input/* : string */, infix/* : string */)/* : Option<number> */.map/* : (arg0 : (arg0 : T) => R) => content-start Option content-end<R> */((index : unknown) => {
			let left : var = input/* : string */.substring/* : unknown */(0/* : number */, /* index */)/* : unknown */;
			let right : var = input/* : string */.substring/* : unknown */(/* index */ + infix/* : string */.length/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
			return new Tuple(/* left */, /* right */)/* : Tuple */;
		})/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	/* private static  */ split<T>(splitter : () => Option<[string, string]>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter/* : () => Option<[string, string]> */(/*  */)/* : Option<[string, string]> */.flatMap/* : (arg0 : (arg0 : T) => content-start Option content-end<R>) => content-start Option content-end<R> */((tuple : unknown) => mapper/* : (arg0 : string, arg1 : string) => Option<T> */(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */)/* : Option<T> */)/* : unknown */;
	}
	/* private static */ findFirst(input : string, infix : string) : Option<number> {
		let index : var = input/* : string */.indexOf/* : unknown */(infix/* : string */)/* : unknown */;
		/* if (index == -1)  */{
			return new None<number>(/*  */)/* : None<number> */;
		}
		return new Some(/* index */)/* : Some */;
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced : var = input/* : string */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
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
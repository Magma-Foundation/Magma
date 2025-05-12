/* private */interface Option<T>/*   */ {
	map<R>(mapper : (arg0 : T) => R) : Option<R>;
	isPresent() : /* boolean */;
	orElse(other : T) : T;
	filter(predicate : (arg0 : T) => boolean) : Option<T>;
	orElseGet(supplier : () => T) : T;
	or(other : () => Option<T>) : Option<T>;
	flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R>;
	isEmpty() : /* boolean */;
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
	isEmpty() : /* boolean */;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : Iterator<[number, T]>;
	removeFirst() : Option<[T, List<T>]>;
	addAllLast(others : List<T>) : List<T>;
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
		return new Some(mapper/* : (arg0 : T) => R */(/* this */.value/* : unknown */)/* : R */)/* : Some */;
	}
	/* @Override
        public */ isPresent() : /* boolean */ {
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
        public */ isEmpty() : /* boolean */ {
		return /* false */;
	}
	/* @Override
        public  */ and<R>(other : () => Option<R>) : Option<[T, R]> {
		return other/* : () => Option<R> */(/*  */)/* : Option<R> */.map/* : (arg0 : (arg0 : R) => R) => Option<R> */((otherValue : R) => new /* Tuple */(/* this */.value/* : unknown */, otherValue/* : R */)/* : content-start Tuple content-end */)/* : Option<R> */;
	}
}
/* private static */class None<T>/*  */ {
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new None(/*  */)/* : None */;
	}
	/* @Override
        public */ isPresent() : /* boolean */ {
		return /* false */;
	}
	/* @Override
        public */ orElse(other : T) : T {
		return other/* : T */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		return new None(/*  */)/* : None */;
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
		return new None(/*  */)/* : None */;
	}
	/* @Override
        public */ isEmpty() : /* boolean */ {
		return /* true */;
	}
	/* @Override
        public  */ and<R>(other : () => Option<R>) : Option<[T, R]> {
		return new None(/*  */)/* : None */;
	}
}
/* private static */class SingleHead<T>/*  */ {
	/* private final */ value : T;
	/* private */ retrieved : /* boolean */;
	SingleHead(value : T) : /* public */ {
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
		let current = initial/* : R */;
		/* while (true)  */{
			let finalCurrent : R = /* current */;
			let optional = /* this */.head/* : unknown */.next/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((inner) => /* folder */.apply/* : unknown */(/* finalCurrent */, inner/* : unknown */)/* : unknown */)/* : unknown */;
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
		return new HeadedIterator(() => /* this */.head/* : unknown */.next/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* mapper */)/* : unknown */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return /* this */.fold/* : unknown */(collector/* : Collector<T, R> */.createInitial/* : () => C */(/*  */)/* : C */, collector/* : Collector<T, R> */.fold/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return /* this */.flatMap/* : unknown */((element) => {
			/* if (predicate.test(element))  */{
				return new HeadedIterator(new SingleHead(element/* : unknown */)/* : SingleHead */)/* : HeadedIterator */;
			}
			return new HeadedIterator(new EmptyHead(/*  */)/* : EmptyHead */)/* : HeadedIterator */;
		})/* : unknown */;
	}
	/* @Override
        public */ next() : Option<T> {
		return /* this */.head/* : unknown */.next/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
		return new HeadedIterator(new /* FlatMapHead */(/* this */.head/* : unknown */, f/* : (arg0 : T) => Iterator<R> */)/* : content-start FlatMapHead content-end */)/* : HeadedIterator */;
	}
	/* @Override
        public  */ zip<R>(other : Iterator<R>) : Iterator<[T, R]> {
		return new HeadedIterator(() => /* HeadedIterator */.this/* : unknown */.head/* : unknown */.next/* : unknown */(/*  */)/* : unknown */.and/* : unknown */(/* other */.next/* : unknown */)/* : unknown */)/* : HeadedIterator */;
	}
}
/* private static */class RangeHead/*  */ {
	/* private final */ length : number;
	/* private */ counter : number;
	RangeHead(length : number) : /* public */ {
		let /* this.length  */ = length/* : number */;
	}
	/* @Override
        public */ next() : Option<number> {
		/* if (this.counter < this.length)  */{
			let value = /* this */.counter/* : unknown */;
			/* this.counter++ */;
			return new Some(/* value */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
}
/* private static final */class JVMList<T>/*  */ {
	/* private final */ elements : /* java.util.List */<T>;
	JVMList(elements : /* java.util.List */<T>) : /* private */ {
		let /* this.elements  */ = elements/* : content-start java.util.List content-end<T> */;
	}
	JVMList() : /* public */ {
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
		let slice = /* this */.elements/* : unknown */.subList/* : unknown */(0/* : number */, /* this */.elements/* : unknown */.size/* : unknown */(/*  */)/* : unknown */ - 1/* : number */)/* : unknown */;
		let last = /* this */.elements/* : unknown */.getLast/* : unknown */(/*  */)/* : unknown */;
		return new Some(new [List<T>, T](new JVMList(/* slice */)/* : JVMList */, /* last */)/* : [List<T>, T] */)/* : Some */;
	}
	/* @Override
            public */ size() : number {
		return /* this */.elements/* : unknown */.size/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
            public */ isEmpty() : /* boolean */ {
		return /* this */.elements/* : unknown */.isEmpty/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
            public */ addFirst(element : T) : List<T> {
		/* this.elements.addFirst(element) */;
		return /* this */;
	}
	/* @Override
            public */ iterateWithIndices() : Iterator<[number, T]> {
		return new HeadedIterator(new RangeHead(/* this */.elements/* : unknown */.size/* : unknown */(/*  */)/* : unknown */)/* : RangeHead */)/* : HeadedIterator */.map/* : unknown */((index) => new /* Tuple */(index/* : unknown */, /* this */.elements/* : unknown */.get/* : unknown */(index/* : unknown */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* @Override
            public */ removeFirst() : Option<[T, List<T>]> {
		/* if (this.elements.isEmpty())  */{
			return new None(/*  */)/* : None */;
		}
		let first = /* this */.elements/* : unknown */.getFirst/* : unknown */(/*  */)/* : unknown */;
		let slice = /* this */.elements/* : unknown */.subList/* : unknown */(1/* : number */, /* this */.elements/* : unknown */.size/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
		return new Some(new [T, List<T>](/* first */, new JVMList(/* slice */)/* : JVMList */)/* : [T, List<T>] */)/* : Some */;
	}
	/* @Override
            public */ addAllLast(others : List<T>) : List<T> {
		let initial : List<T> = /* this */;
		return others/* : List<T> */.iterate/* : () => Iterator<T> */(/*  */)/* : Iterator<T> */.fold/* : (arg0 : C, arg1 : T) => C */(/* initial */, /* List */.addLast/* : unknown */)/* : C */;
	}
	/* @Override
            public */ get(index : number) : Option<T> {
		/* if (index < this.elements.size())  */{
			return new Some(/* this */.elements/* : unknown */.get/* : unknown */(index/* : number */)/* : unknown */)/* : Some */;
		}
		/* else  */{
			return new None(/*  */)/* : None */;
		}
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
	constructor (name : string, typeParams : List<string>, definitions : List</* Definition */>) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.name/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return new ObjectType(/* this */.name/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.definitions/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((definition) => definition/* : unknown */.mapType/* : unknown */((type) => type/* : unknown */.replace/* : unknown */(/* mapping */)/* : unknown */)/* : unknown */)/* : unknown */.collect/* : unknown */(new /* ListCollector */(/*  */)/* : content-start ListCollector content-end */)/* : unknown */)/* : ObjectType */;
	}
	/* @Override
        public */ find(name : string) : Option</* Type */> {
		return /* this */.definitions/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */.equals/* : unknown */(/* name */)/* : unknown */)/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */.next/* : unknown */(/*  */)/* : unknown */;
	}
}
/* private */class TypeParam/*  */ {
	constructor (value : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.value/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		/* if (mapping.containsKey(this.value))  */{
			return mapping/* : content-start Map content-end<string, content-start Type content-end> */.get/* : unknown */(/* this */.value/* : unknown */)/* : unknown */;
		}
		return /* this */;
	}
}
/* private static */class DivideState/*  */ {
	/* private final */ input : string;
	/* private final */ index : number;
	/* private */ depth : number;
	/* private */ segments : List<string>;
	/* private */ buffer : /* StringBuilder */;
	DivideState(input : string, index : number, segments : List<string>, buffer : /* StringBuilder */, depth : number) : /* public */ {
		let /* this.segments  */ = segments/* : List<string> */;
		let /* this.buffer  */ = buffer/* : content-start StringBuilder content-end */;
		let /* this.depth  */ = depth/* : number */;
		let /* this.input  */ = input/* : string */;
		let /* this.index  */ = index/* : number */;
	}
	DivideState(input : string) : /* public */ {
		/* this(input, 0, Lists.empty(), new StringBuilder(), 0) */;
	}
	/* private */ advance() : DivideState {
		let /* this.segments  */ = /* this */.segments/* : unknown */.addLast/* : unknown */(/* this */.buffer/* : unknown */.toString/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
		let /* this.buffer  */ = new /* StringBuilder */(/*  */)/* : content-start StringBuilder content-end */;
		return /* this */;
	}
	/* private */ append(c : /* char */) : DivideState {
		/* this.buffer.append(c) */;
		return /* this */;
	}
	/* public */ enter() : DivideState {
		/* this.depth++ */;
		return /* this */;
	}
	/* public */ isLevel() : /* boolean */ {
		return /* this.depth == 0 */;
	}
	/* public */ exit() : DivideState {
		/* this.depth-- */;
		return /* this */;
	}
	/* public */ isShallow() : /* boolean */ {
		return /* this.depth == 1 */;
	}
	/* public */ pop() : Option<[/* Character */, DivideState]> {
		/* if (this.index < this.input.length())  */{
			let c = /* this */.input/* : unknown */.charAt/* : unknown */(/* this */.index/* : unknown */)/* : unknown */;
			return new Some(new /* Tuple */(/* c */, new DivideState(/* this */.input/* : unknown */, /* this */.index/* : unknown */ + 1/* : number */, /* this */.segments/* : unknown */, /* this */.buffer/* : unknown */, /* this */.depth/* : unknown */)/* : DivideState */)/* : content-start Tuple content-end */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* public */ popAndAppendToTuple() : Option<[/* Character */, DivideState]> {
		return /* this */.pop/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((tuple) => new /* Tuple */(tuple/* : unknown */.left/* : unknown */, tuple/* : unknown */.right/* : unknown */.append/* : unknown */(tuple/* : unknown */.left/* : unknown */)/* : unknown */)/* : content-start Tuple content-end */)/* : unknown */;
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return /* this */.popAndAppendToTuple/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Tuple */.right/* : unknown */)/* : unknown */;
	}
	/* public */ peek() : /* char */ {
		return /* this */.input/* : unknown */.charAt/* : unknown */(/* this */.index/* : unknown */)/* : unknown */;
	}
}
/* private */class Joiner/*  */ {
	constructor (delimiter : string) {
	}

	Joiner() : /* private */ {
		/* this("") */;
	}
	/* @Override
        public */ createInitial() : Option<string> {
		return new None(/*  */)/* : None */;
	}
	/* @Override
        public */ fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current/* : Option<string> */.map/* : (arg0 : (arg0 : string) => R) => Option<R> */((inner : string) => inner/* : string */ + /* this */.delimiter/* : unknown */ + /* element */)/* : Option<R> */.orElse/* : (arg0 : R) => T */(/* element */)/* : T */)/* : Some */;
	}
}
/* private */class Definition/*  */ {
	constructor (maybeBefore : Option<string>, name : string, type : /* Type */, typeParams : List<string>) {
	}

	Definition(name : string, type : /* Type */) : /* public */ {
		/* this(new None<>(), name, type, Lists.empty()) */;
	}
	/* private */ generate() : string {
		return /* this */.generateWithParams/* : unknown */("")/* : unknown */;
	}
	/* public */ generateWithParams(params : string) : string {
		let joined = /* this */.joinTypeParams/* : unknown */(/*  */)/* : unknown */;
		let before = /* this */.joinBefore/* : unknown */(/*  */)/* : unknown */;
		let typeString = /* this */.generateType/* : unknown */(/*  */)/* : unknown */;
		return /* before */ + /* this */.name/* : unknown */ + /* joined */ + params/* : string */ + /* typeString */;
	}
	/* private */ generateType() : string {
		/* if (this.type.equals(Primitive.Unknown))  */{
			return "";
		}
		return " : " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* private */ joinBefore() : string {
		return /* this */.maybeBefore/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */(/*  */)/* : unknown */)/* : unknown */.map/* : unknown */(/* Main */.generatePlaceholder/* : unknown */)/* : unknown */.map/* : unknown */((inner) => inner/* : unknown */ + " ")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* private */ joinTypeParams() : string {
		return /* this */.typeParams/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(/*  */)/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
	}
	/* public */ mapType(mapper : (arg0 : /* Type */) => /* Type */) : Definition {
		return new Definition(/* this */.maybeBefore/* : unknown */, /* this */.name/* : unknown */, mapper/* : (arg0 : content-start Type content-end) => content-start Type content-end */(/* this */.type/* : unknown */)/* : content-start Type content-end */, /* this */.typeParams/* : unknown */)/* : Definition */;
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return /* Lists */.empty/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return current/* : List<T> */.addLast/* : (arg0 : T) => List<T> */(element/* : T */)/* : List<T> */;
	}
}
/* private */class Tuple<A, B>/* (A left, B right)  */ {
}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : T) => Iterator<R>;
	/* private final */ head : Head<T>;
	/* private */ current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>) : /* public */ {
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
/* private */class ArrayType/*  */ {
	constructor (right : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + "[]";
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return /* this */;
	}
}
/* private static */class Whitespace/*  */ {
}
/* private static */class Iterators/*  */ {
	/* public static  */ fromOption<T>(option : Option<T>) : Iterator<T> {
		let single : Option<Head<T>> = option/* : Option<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(/* SingleHead */.new/* : unknown */)/* : Option<R> */;
		return new HeadedIterator(/* single */.orElseGet/* : unknown */(/* EmptyHead */.new/* : unknown */)/* : unknown */)/* : HeadedIterator */;
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List</* Type */>, returns : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		let joined = /* this */.arguments/* : unknown */(/*  */)/* : unknown */.iterateWithIndices/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((pair) => "arg" + pair/* : unknown */.left/* : unknown */ + " : " + pair/* : unknown */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + /* joined */ + ") => " + /* this */.returns/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return new FunctionType(/* this */.arguments/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((type) => type/* : unknown */.replace/* : unknown */(/* mapping */)/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector(/*  */)/* : ListCollector */)/* : unknown */, /* this */.returns/* : unknown */)/* : FunctionType */;
	}
}
/* private */class TupleType/*  */ {
	constructor (arguments : List</* Type */>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments = /* this */.arguments/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "[" + joinedArguments + "]";
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return /* this */;
	}
}
/* private */class Template/*  */ {
	constructor (base : /* FindableType */, arguments : List</* Type */>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments = /* this */.arguments/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Type */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return /* this */.base/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + /* joinedArguments */;
	}
	/* @Override
        public */ typeParams() : List<string> {
		return /* this */.base/* : unknown */.typeParams/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : Option</* Type */> {
		return /* this */.base/* : unknown */.find/* : unknown */(name/* : string */)/* : unknown */.map/* : unknown */((found) => {
			let mapping = /* this */.base/* : unknown */.typeParams/* : unknown */(/*  */)/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.zip/* : unknown */(/* this */.arguments/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */)/* : unknown */.collect/* : unknown */(new /* MapCollector */(/*  */)/* : content-start MapCollector content-end */)/* : unknown */;
			return found/* : unknown */.replace/* : unknown */(/* mapping */)/* : unknown */;
		})/* : unknown */;
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
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
	/* @Override
        public */ typeParams() : List<string> {
		return /* Lists */.empty/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ find(name : string) : Option</* Type */> {
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
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : /* Value */, property : string, type : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.parent/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + "." + /* this */.property/* : unknown */ + /* generatePlaceholder */(": " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* this */.type/* : unknown */;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		return "new " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* public */ toFunction() : FunctionType {
		return new FunctionType(/* Lists */.empty/* : unknown */(/*  */)/* : unknown */, /* this */.type/* : unknown */)/* : FunctionType */;
	}
}
/* private */class Operation/*  */ {
	constructor (left : /* Value */, infix : string, right : /* Value */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.left/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + " " + /* this */.infix/* : unknown */ + " " + /* this */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Not/*  */ {
	constructor (value : /* Value */) {
	}

	/* @Override
        public */ generate() : string {
		return "!" + /* this */.value/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
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
	constructor (parameters : List<Definition>, body : LambdaValue) {
	}

	/* @Override
        public */ generate() : string {
		let joined = /* this */.parameters/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Definition */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return "(" + /* joined */ + ") => " + /* this */.body/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */;
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : Caller, arguments : List</* Value */>, type : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		let joined = /* this */.arguments/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Value */.generate/* : unknown */)/* : unknown */.collect/* : unknown */(new Joiner(", ")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return /* this */.caller/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + "(" + /* joined */ + ")" + /* generatePlaceholder */(": " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : /* Value */, child : /* Value */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.parent/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + "[" + this.child.generate() + "]";
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown/* : unknown */;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (stripped : string, type : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.stripped/* : unknown */ + /* generatePlaceholder */(": " + /* this */.type/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
	}
}
/* private */class MapCollector<K, V>/*  */ {
	constructor () {
	}

	/* @Override
        public */ createInitial() : /* Map */<K, V> {
		return new /* HashMap */(/*  */)/* : content-start HashMap content-end */;
	}
	/* @Override
        public */ fold(current : /* Map */<K, V>, element : [K, V]) : /* Map */<K, V> {
		/* current.put(element.left, element.right) */;
		return current/* : content-start Map content-end<K, V> */;
	}
}
/* public */class Main/*  */ {/* 

    private interface Type extends Argument {
        String generate();

        Type replace(Map<String, Type> mapping);
    } *//* 

    private sealed interface Value extends LambdaValue, Caller {
        String generate();

        Type type();
    } *//* 

    private interface FindableType extends Type {
        List<String> typeParams();

        Option<Type> find(String name);

        @Override
        default Type replace(Map<String, Type> mapping) {
            return this;
        }
    } */
	/* private */ CompileState(structures : List<string>, definitions : List</* Definition */>, objectTypes : List<ObjectType>, maybeStructName : Option<string>, typeParams : List<string>, expectedType : Option</* Type */>) : /* record */ {
		/* public CompileState()  */{
			/* this(Lists.empty(), Lists.empty(), Lists.empty(), new None<>(), Lists.empty(), new None<>()) */;
		}
		/* private Option<Type> resolveValue(String name)  */{
			return /* this */.definitions/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.filter/* : unknown */((definition) => definition/* : unknown */.name/* : unknown */.equals/* : unknown */(/* name */)/* : unknown */)/* : unknown */.next/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */;
		}
		/* public CompileState addStructure(String structure)  */{
			return new /* CompileState */(/* this */.structures/* : unknown */.addLast/* : unknown */(/* structure */)/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.maybeStructName/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.expectedType/* : unknown */)/* : content-start CompileState content-end */;
		}
		/* public CompileState withDefinitions(List<Definition> definitions)  */{
			return new /* CompileState */(/* this */.structures/* : unknown */, /* definitions */, /* this */.objectTypes/* : unknown */, /* this */.maybeStructName/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.expectedType/* : unknown */)/* : content-start CompileState content-end */;
		}
		/* public Option<Type> resolveType(String name)  */{
			/* if (this.maybeStructName.filter(inner -> inner.equals(name)).isPresent())  */{
				return new Some(new ObjectType(/* name */, /* this */.typeParams/* : unknown */, /* this */.definitions/* : unknown */)/* : ObjectType */)/* : Some */;
			}
			let maybeTypeParam = /* this */.typeParams/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.filter/* : unknown */((param) => param/* : unknown */.equals/* : unknown */(/* name */)/* : unknown */)/* : unknown */.next/* : unknown */(/*  */)/* : unknown */;
			/* if (maybeTypeParam instanceof Some(var value))  */{
				return new Some(new TypeParam(/* value */)/* : TypeParam */)/* : Some */;
			}
			return /* this */.objectTypes/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.filter/* : unknown */((type) => type/* : unknown */.name/* : unknown */.equals/* : unknown */(/* name */)/* : unknown */)/* : unknown */.next/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((type) => type/* : unknown */)/* : unknown */;
		}
		/* public CompileState addType(ObjectType type)  */{
			return new /* CompileState */(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */.addLast/* : unknown */(type/* : unknown */)/* : unknown */, /* this */.maybeStructName/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.expectedType/* : unknown */)/* : content-start CompileState content-end */;
		}
		/* public CompileState withDefinition(Definition definition)  */{
			return new /* CompileState */(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */.addLast/* : unknown */(/* definition */)/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.maybeStructName/* : unknown */, /* this */.typeParams/* : unknown */, /* this */.expectedType/* : unknown */)/* : content-start CompileState content-end */;
		}
		/* public CompileState withStructName(String name)  */{
			return new /* CompileState */(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, new Some(/* name */)/* : Some */, /* this */.typeParams/* : unknown */, /* this */.expectedType/* : unknown */)/* : content-start CompileState content-end */;
		}
		/* public CompileState withTypeParams(List<String> typeParams)  */{
			return new /* CompileState */(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.maybeStructName/* : unknown */, /* this */.typeParams/* : unknown */.addAllLast/* : unknown */(/* typeParams */)/* : unknown */, /* this */.expectedType/* : unknown */)/* : content-start CompileState content-end */;
		}
		/* public CompileState withExpectedType(Type type)  */{
			return new /* CompileState */(/* this */.structures/* : unknown */, /* this */.definitions/* : unknown */, /* this */.objectTypes/* : unknown */, /* this */.maybeStructName/* : unknown */, /* this */.typeParams/* : unknown */, new Some(type/* : unknown */)/* : Some */)/* : content-start CompileState content-end */;
		}
	}
	/* public static */ main() : /* void */ {
		/* try  */{
			let parent = /* Paths */.get/* : unknown */(".", "src", "java", "magma")/* : unknown */;
			let source = /* parent */.resolve/* : unknown */("Main.java")/* : unknown */;
			let target = /* parent */.resolve/* : unknown */("main.ts")/* : unknown */;
			let input = /* Files */.readString/* : unknown */(/* source */)/* : unknown */;
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
		let tuple = /* compileStatements */(new /* CompileState */(/*  */)/* : content-start CompileState content-end */, input/* : string */, /* Main */.compileRootSegment/* : unknown */)/* : unknown */;
		let joined = /* tuple */.left/* : unknown */.structures/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(/*  */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		return /* joined */ + /* tuple */.right/* : unknown */;
	}
	/* private static */ compileStatements(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => [/* CompileState */, string]) : [/* CompileState */, string] {
		let parsed = /* parseStatements */(state/* : content-start CompileState content-end */, input/* : string */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, string] */)/* : unknown */;
		return new Tuple(/* parsed */.left/* : unknown */, /* generateStatements */(/* parsed */.right/* : unknown */)/* : unknown */)/* : Tuple */;
	}
	/* private static */ generateStatements(statements : List<string>) : string {
		return /* generateAll */(/* Main */.mergeStatements/* : unknown */, statements/* : List<string> */)/* : unknown */;
	}
	/* private static */ parseStatements(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => [/* CompileState */, string]) : [/* CompileState */, List<string>] {
		return /* parseAll0 */(state/* : content-start CompileState content-end */, input/* : string */, /* Main */.foldStatementChar/* : unknown */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => [content-start CompileState content-end, string] */)/* : unknown */;
	}
	/* private static */ generateAll(merger : (arg0 : /* StringBuilder */, arg1 : string) => /* StringBuilder */, elements : List<string>) : string {
		return elements/* : List<string> */.iterate/* : () => Iterator<T> */(/*  */)/* : Iterator<T> */.fold/* : (arg0 : C, arg1 : T) => C */(new /* StringBuilder */(/*  */)/* : content-start StringBuilder content-end */, merger/* : (arg0 : content-start StringBuilder content-end, arg1 : string) => content-start StringBuilder content-end */)/* : C */.toString/* : unknown */(/*  */)/* : unknown */;
	}
	/* private static  */ parseAll0<T>(state : /* CompileState */, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : /* CompileState */, arg1 : string) => [/* CompileState */, T]) : [/* CompileState */, List<T>] {
		return /* getCompileStateListTuple */(state/* : content-start CompileState content-end */, input/* : string */, folder/* : (arg0 : DivideState, arg1 : content-start Character content-end) => DivideState */, (state1, s) => new Some(/* mapper */.apply/* : unknown */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(/* state */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static  */ getCompileStateListTuple<T>(state : /* CompileState */, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : /* CompileState */, arg1 : string) => Option<[/* CompileState */, T]>) : Option<[/* CompileState */, List<T>]> {
		return /* parseAll */(state/* : content-start CompileState content-end */, input/* : string */, folder/* : (arg0 : DivideState, arg1 : content-start Character content-end) => DivideState */, (state1, tuple) => /* mapper */.apply/* : unknown */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */)/* : unknown */)/* : unknown */;
	}
	/* private static  */ parseAll<T>(state : /* CompileState */, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : /* CompileState */, arg1 : [number, string]) => Option<[/* CompileState */, T]>) : Option<[/* CompileState */, List<T>]> {
		let initial : Option<[/* CompileState */, List<T>]> = new Some(new Tuple(state/* : content-start CompileState content-end */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : Some */;
		return /* divideAll */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : content-start Character content-end) => DivideState */)/* : unknown */.iterateWithIndices/* : unknown */(/*  */)/* : unknown */.fold/* : unknown */(/* initial */, (tuple, element) => {
			return tuple/* : unknown */.flatMap/* : unknown */((inner) => {
				let state1 = inner/* : unknown */.left/* : unknown */;
				let right = inner/* : unknown */.right/* : unknown */;
				return /* mapper */.apply/* : unknown */(/* state1 */, /* element */)/* : unknown */.map/* : unknown */((applied) => {
					return new Tuple(applied/* : unknown */.left/* : unknown */, /* right */.addLast/* : unknown */(applied/* : unknown */.right/* : unknown */)/* : unknown */)/* : Tuple */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ mergeStatements(stringBuilder : /* StringBuilder */, str : string) : /* StringBuilder */ {
		return stringBuilder/* : content-start StringBuilder content-end */.append/* : unknown */(str/* : string */)/* : unknown */;
	}
	/* private static */ divideAll(input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState) : List<string> {
		let current = new DivideState(input/* : string */)/* : DivideState */;
		/* while (true)  */{
			let maybePopped = /* current */.pop/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((tuple) => {
				return /* foldSingleQuotes */(tuple/* : unknown */)/* : unknown */.or/* : unknown */(() => /* foldDoubleQuotes */(/* tuple */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => /* folder */.apply/* : unknown */(/* tuple */.right/* : unknown */, /* tuple */.left/* : unknown */)/* : unknown */)/* : unknown */;
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
	/* private static */ foldDoubleQuotes(tuple : [/* Character */, DivideState]) : Option<DivideState> {
		/* if (tuple.left == '\"')  */{
			let current = tuple/* : [content-start Character content-end, DivideState] */.right/* : unknown */.append/* : unknown */(tuple/* : [content-start Character content-end, DivideState] */[0/* : number */])/* : unknown */;
			/* while (true)  */{
				let maybePopped = /* current */.popAndAppendToTuple/* : unknown */(/*  */)/* : unknown */;
				/* if (maybePopped.isEmpty())  */{
					/* break */;
				}
				let popped = /* maybePopped */.orElse/* : unknown */(/* null */)/* : unknown */;
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
	/* private static */ foldSingleQuotes(tuple : [/* Character */, DivideState]) : Option<DivideState> {
		/* if (tuple.left != '\'')  */{
			return new None(/*  */)/* : None */;
		}
		let appended = tuple/* : [content-start Character content-end, DivideState] */.right/* : unknown */.append/* : unknown */(tuple/* : [content-start Character content-end, DivideState] */[0/* : number */])/* : unknown */;
		return /* appended */.popAndAppendToTuple/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Main */.foldEscaped/* : unknown */)/* : unknown */.flatMap/* : unknown */(/* DivideState */.popAndAppendToOption/* : unknown */)/* : unknown */;
	}
	/* private static */ foldEscaped(escaped : [/* Character */, DivideState]) : DivideState {
		/* if (escaped.left == '\\')  */{
			return escaped/* : [content-start Character content-end, DivideState] */.right/* : unknown */.popAndAppendToOption/* : unknown */(/*  */)/* : unknown */.orElse/* : unknown */(escaped/* : [content-start Character content-end, DivideState] */.right/* : unknown */)/* : unknown */;
		}
		return escaped/* : [content-start Character content-end, DivideState] */.right/* : unknown */;
	}
	/* private static */ foldStatementChar(state : DivideState, c : /* char */) : DivideState {
		let append = state/* : DivideState */.append/* : unknown */(c/* : content-start char content-end */)/* : unknown */;
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
	/* private static */ compileRootSegment(state : /* CompileState */, input : string) : [/* CompileState */, string] {
		let stripped = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */{
			return new Tuple(state/* : content-start CompileState content-end */, "")/* : Tuple */;
		}
		return /* compileClass */(/* stripped */, 0/* : number */, state/* : content-start CompileState content-end */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(/* state */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ compileClass(stripped : string, depth : number, state : /* CompileState */) : Option<[/* CompileState */, string]> {
		return /* compileStructure */(stripped/* : string */, "class ", "class ", state/* : content-start CompileState content-end */)/* : unknown */;
	}
	/* private static */ compileStructure(stripped : string, sourceInfix : string, targetInfix : string, state : /* CompileState */) : Option<[/* CompileState */, string]> {
		return /* first */(stripped/* : string */, sourceInfix/* : string */, (beforeInfix, right) => {
			return /* first */(right/* : unknown */, "{", (beforeContent, withEnd) => {
				let strippedWithEnd = withEnd/* : unknown */.strip/* : unknown */(/*  */)/* : unknown */;
				return /* suffix */(/* strippedWithEnd */, "}", (content1) => {
					return /* first */(/* beforeContent */, " implements ", (s, s2) => {
						return /* structureWithMaybeParams */(/* targetInfix */, /* state */, /* beforeInfix */, s/* : unknown */, /* content1 */)/* : unknown */;
					})/* : unknown */.or/* : unknown */(() => {
						return /* structureWithMaybeParams */(/* targetInfix */, /* state */, /* beforeInfix */, /* beforeContent */, /* content1 */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : /* CompileState */, beforeInfix : string, beforeContent : string, content1 : string) : Option<[/* CompileState */, string]> {
		return /* suffix */(beforeContent/* : string */, ")", (s) => {
			return /* first */(s/* : unknown */, "(", (s1, s2) => {
				let parsed = /* parseParameters */(/* state */, s2/* : unknown */)/* : unknown */;
				return /* getOred */(/* targetInfix */, /* parsed */.left/* : unknown */, /* beforeInfix */, s1/* : unknown */, /* content1 */, /* parsed */.right/* : unknown */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* getOred */(/* targetInfix */, /* state */, /* beforeInfix */, /* beforeContent */, /* content1 */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ getOred(targetInfix : string, state : /* CompileState */, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>) : Option<[/* CompileState */, string]> {
		return /* first */(beforeContent/* : string */, "<", (name, withTypeParams) => {
			return /* first */(withTypeParams/* : unknown */, ">", (typeParamsString, afterTypeParams) => {
				let /* final */ compileStateStringTupleBiFunction : (arg0 : /* CompileState */, arg1 : string) => [/* CompileState */, string] = (state1, s) => new Tuple(state1/* : unknown */, s/* : unknown */.strip/* : unknown */(/*  */)/* : unknown */)/* : Tuple */;
				let typeParams = /* parseValuesOrEmpty */(/* state */, /* typeParamsString */, (state1, s) => new Some(/* compileStateStringTupleBiFunction */.apply/* : unknown */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */;
				return /* assembleStructure */(/* typeParams */.left/* : unknown */, /* targetInfix */, /* beforeInfix */, /* name */, /* content1 */, /* typeParams */.right/* : unknown */, /* afterTypeParams */, /* params */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* assembleStructure */(/* state */, /* targetInfix */, /* beforeInfix */, /* beforeContent */, /* content1 */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */, "", /* params */)/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleStructure(state : /* CompileState */, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string, params : List<Parameter>) : Option<[/* CompileState */, string]> {
		let name = rawName/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (!isSymbol(name))  */{
			return new None(/*  */)/* : None */;
		}
		let joinedTypeParams = typeParams/* : List<string> */.iterate/* : () => Iterator<T> */(/*  */)/* : Iterator<T> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.map/* : unknown */((inner) => "<" + inner + ">")/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let parsed = /* parseStatements */(/* state */.withStructName/* : unknown */(/* name */)/* : unknown */.withTypeParams/* : unknown */(/* typeParams */)/* : unknown */, /* content */, (state0, input) => /* compileClassSegment */(state0/* : unknown */, input/* : unknown */, 1/* : number */)/* : unknown */)/* : unknown */;
		/* List<String> parsed1 */;
		/* if (params.isEmpty())  */{
			let /* parsed1  */ = /* parsed */.right/* : unknown */;
		}
		/* else  */{
			let joined = /* joinValues */(/* retainDefinitions */(/* params */)/* : unknown */)/* : unknown */;
			let constructorIndent = /* createIndent */(1/* : number */)/* : unknown */;
			let /* parsed1  */ = /* parsed */.right/* : unknown */.addFirst/* : unknown */(/* constructorIndent */ + "constructor (" + joined + ") {" + constructorIndent + "}\n")/* : unknown */;
		}
		let parsed2 = /* parsed1 */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(/*  */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
		let generated = /* generatePlaceholder */(/* beforeInfix */.strip/* : unknown */(/*  */)/* : unknown */)/* : unknown */ + /* targetInfix */ + /* name */ + /* joinedTypeParams */ + /* generatePlaceholder */(/* afterTypeParams */)/* : unknown */ + " {" + parsed2 + "\n}\n";
		return new Some(new Tuple(/* parsed */.left/* : unknown */.addStructure/* : unknown */(/* generated */)/* : unknown */.addType/* : unknown */(new ObjectType(/* name */, /* typeParams */, /* parsed */.left/* : unknown */.definitions/* : unknown */)/* : ObjectType */)/* : unknown */, "")/* : Tuple */)/* : Some */;
	}
	/* private static */ retainDefinition(parameter : Parameter) : Option<Definition> {
		/* if (parameter instanceof Definition definition)  */{
			return new Some(/* definition */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ isSymbol(input : string) : /* boolean */ {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
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
		let slice = input/* : string */.substring/* : unknown */(0/* : number */, input/* : string */.length/* : unknown */(/*  */)/* : unknown */ - suffix/* : string */.length/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
		return mapper/* : (arg0 : string) => Option<T> */(/* slice */)/* : Option<T> */;
	}
	/* private static */ compileClassSegment(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, string] {
		return /* compileWhitespace */(input/* : string */, state/* : content-start CompileState content-end */)/* : unknown */.or/* : unknown */(() => /* compileClass */(/* input */, /* depth */, /* state */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileStructure */(/* input */, "interface ", "interface ", /* state */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileStructure */(/* input */, "record ", "class ", /* state */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileMethod */(/* state */, /* input */, /* depth */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* compileDefinitionStatement */(/* input */, /* depth */, /* state */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(/* state */, /* generatePlaceholder */(/* input */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ compileWhitespace(input : string, state : /* CompileState */) : Option<[/* CompileState */, string]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple(state/* : content-start CompileState content-end */, "")/* : Tuple */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ compileMethod(state : /* CompileState */, input : string, depth : number) : Option<[/* CompileState */, string]> {
		return /* first */(input/* : string */, "(", (definitionString, withParams) => {
			return /* first */(withParams/* : unknown */, ")", (parametersString, rawContent) => {
				return /* parseDefinition */(/* state */, /* definitionString */)/* : unknown */.flatMap/* : unknown */((definitionTuple) => {
					let definitionState = definitionTuple/* : unknown */.left/* : unknown */;
					let definition = definitionTuple/* : unknown */.right/* : unknown */;
					let parametersTuple = /* parseParameters */(/* definitionState */, /* parametersString */)/* : unknown */;
					let rawParameters = /* parametersTuple */.right/* : unknown */;
					let parameters = /* retainDefinitions */(/* rawParameters */)/* : unknown */;
					let joinedParameters = /* joinValues */(/* parameters */)/* : unknown */;
					let content = /* rawContent */.strip/* : unknown */(/*  */)/* : unknown */;
					let indent = /* createIndent */(/* depth */)/* : unknown */;
					let paramTypes = /* parameters */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* Definition */.type/* : unknown */)/* : unknown */.collect/* : unknown */(new ListCollector(/*  */)/* : ListCollector */)/* : unknown */;
					let toDefine = new Definition(/* definition */.name/* : unknown */, new FunctionType(/* paramTypes */, /* definition */.type/* : unknown */)/* : FunctionType */)/* : Definition */;
					let generatedHeader = /* definition */.generateWithParams/* : unknown */("(" + joinedParameters + ")")/* : unknown */;
					/* if (content.equals(";"))  */{
						return new Some(new Tuple(/* parametersTuple */.left/* : unknown */.withDefinition/* : unknown */(/* toDefine */)/* : unknown */, /* indent */ + /* generatedHeader */ + ";")/* : Tuple */)/* : Some */;
					}
					/* if (content.startsWith("{") && content.endsWith("}"))  */{
						let substring = /* content */.substring/* : unknown */(1/* : number */, /* content */.length/* : unknown */(/*  */)/* : unknown */ - 1/* : number */)/* : unknown */;
						let statementsTuple = /* compileFunctionSegments */(/* parametersTuple */.left/* : unknown */.withDefinitions/* : unknown */(/* parameters */)/* : unknown */, /* substring */, /* depth */)/* : unknown */;
						let generated = /* indent */ + /* generatedHeader */ + " {" + statementsTuple.right + indent + "}";
						return new Some(new Tuple(/* statementsTuple */.left/* : unknown */.withDefinition/* : unknown */(/* toDefine */)/* : unknown */, /* generated */)/* : Tuple */)/* : Some */;
					}
					return new None(/*  */)/* : None */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ joinValues(retainParameters : List<Definition>) : string {
		return retainParameters/* : List<Definition> */.iterate/* : () => Iterator<T> */(/*  */)/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(/* Definition */.generate/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new Joiner(", ")/* : Joiner */)/* : R */.orElse/* : unknown */("")/* : unknown */;
	}
	/* private static */ retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right/* : List<Parameter> */.iterate/* : () => Iterator<T> */(/*  */)/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(/* Main */.retainDefinition/* : unknown */)/* : Option<R> */.flatMap/* : (arg0 : (arg0 : R) => Option<R>) => Option<R> */(/* Iterators */.fromOption/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector(/*  */)/* : ListCollector */)/* : R */;
	}
	/* private static */ parseParameters(state : /* CompileState */, params : string) : [/* CompileState */, List<Parameter>] {
		return /* parseValuesOrEmpty */(state/* : content-start CompileState content-end */, params/* : string */, (state1, s) => new Some(/* compileParameter */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */;
	}
	/* private static */ compileFunctionSegments(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, string] {
		return /* compileStatements */(state/* : content-start CompileState content-end */, input/* : string */, (state1, input1) => /* compileFunctionSegment */(state1/* : unknown */, input1/* : unknown */, /* depth */ + 1/* : number */)/* : unknown */)/* : unknown */;
	}
	/* private static */ compileFunctionSegment(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, string] {
		let stripped = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.isEmpty())  */{
			return new Tuple(state/* : content-start CompileState content-end */, "")/* : Tuple */;
		}
		return /* suffix */(/* stripped */, ";", (s) => {
			let tuple = /* statementValue */(/* state */, s/* : unknown */, /* depth */)/* : unknown */;
			return new Some(new Tuple(/* tuple */.left/* : unknown */, /* createIndent */(/* depth */)/* : unknown */ + /* tuple */.right/* : unknown */ + ";")/* : Tuple */)/* : Some */;
		})/* : unknown */.or/* : unknown */(() => {
			return /* block */(/* state */, /* depth */, /* stripped */)/* : unknown */;
		})/* : unknown */.orElseGet/* : unknown */(() => {
			return new Tuple(/* state */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : Tuple */;
		})/* : unknown */;
	}
	/* private static */ block(state : /* CompileState */, depth : number, stripped : string) : Option<[/* CompileState */, string]> {
		return /* suffix */(stripped/* : string */, "}", (withoutEnd) => {
			return /* split */(() => {
				let divisions = /* divideAll */(/* withoutEnd */, /* Main */.foldBlockStart/* : unknown */)/* : unknown */;
				return /* divisions */.removeFirst/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((removed) => {
					let right = removed/* : unknown */.left/* : unknown */;
					let left = removed/* : unknown */.right/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner("")/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
					return new Tuple(/* right */, /* left */)/* : Tuple */;
				})/* : unknown */;
			}, (beforeContent, content) => {
				return /* suffix */(beforeContent/* : unknown */, "{", (s) => {
					let compiled = /* compileFunctionSegments */(/* state */, /* content */, /* depth */)/* : unknown */;
					let indent = /* createIndent */(/* depth */)/* : unknown */;
					return new Some(new Tuple(/* compiled */.left/* : unknown */, /* indent */ + /* generatePlaceholder */(s/* : unknown */)/* : unknown */ + "{" + compiled.right + indent + "}")/* : Tuple */)/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ foldBlockStart(state : DivideState, c : /* Character */) : DivideState {
		let appended = state/* : DivideState */.append/* : unknown */(c/* : content-start Character content-end */)/* : unknown */;
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
	/* private static */ statementValue(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, string] {
		let stripped = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.startsWith("return "))  */{
			let value = /* stripped */.substring/* : unknown */("return ".length/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
			let tuple = /* compileValue */(state/* : content-start CompileState content-end */, /* value */, depth/* : number */)/* : unknown */;
			return new Tuple(/* tuple */.left/* : unknown */, "return " + /* tuple */.right/* : unknown */)/* : Tuple */;
		}
		return /* first */(/* stripped */, "=", (s, s2) => {
			let definitionTuple = /* compileDefinition */(/* state */, s/* : unknown */)/* : unknown */;
			let valueTuple = /* compileValue */(/* definitionTuple */.left/* : unknown */, s2/* : unknown */, /* depth */)/* : unknown */;
			return new Some(new Tuple(/* valueTuple */.left/* : unknown */, "let " + /* definitionTuple */.right/* : unknown */ + " = " + /* valueTuple */.right/* : unknown */)/* : Tuple */)/* : Some */;
		})/* : unknown */.orElseGet/* : unknown */(() => {
			return new Tuple(/* state */, /* generatePlaceholder */(/* stripped */)/* : unknown */)/* : Tuple */;
		})/* : unknown */;
	}
	/* private static */ compileValue(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, string] {
		let tuple = /* parseValue */(state/* : content-start CompileState content-end */, input/* : string */, depth/* : number */)/* : unknown */;
		return new Tuple(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : Tuple */;
	}
	/* private static */ parseValue(state : /* CompileState */, input : string, depth : number) : [/* CompileState */, /* Value */] {
		return /* parseLambda */(state/* : content-start CompileState content-end */, input/* : string */, depth/* : number */)/* : unknown */.or/* : unknown */(() => /* parseString */(/* state */, /* input */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDataAccess */(/* state */, /* input */, /* depth */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseSymbolValue */(/* state */, /* input */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseInvocation */(/* state */, /* input */, /* depth */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(/* state */, /* input */, /* depth */, "+")/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseOperation */(/* state */, /* input */, /* depth */, "-")/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseDigits */(/* state */, /* input */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseNot */(/* state */, /* input */, /* depth */)/* : unknown */)/* : unknown */.or/* : unknown */(() => /* parseMethodReference */(/* state */, /* input */, /* depth */)/* : unknown */)/* : unknown */.orElseGet/* : unknown */(() => new [/* CompileState */, /* Value */](/* state */, new Placeholder(/* input */)/* : Placeholder */)/* : [content-start CompileState content-end, content-start Value content-end] */)/* : unknown */;
	}
	/* private static */ parseMethodReference(state : /* CompileState */, input : string, depth : number) : Option<[/* CompileState */, /* Value */]> {
		return /* last */(input/* : string */, "::", (s, s2) => {
			let tuple = /* parseValue */(/* state */, s/* : unknown */, /* depth */)/* : unknown */;
			return new Some(new Tuple(/* tuple */.left/* : unknown */, new DataAccess(/* tuple */.right/* : unknown */, s2/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : DataAccess */)/* : Tuple */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ parseNot(state : /* CompileState */, input : string, depth : number) : Option<[/* CompileState */, /* Value */]> {
		let stripped = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.startsWith("!"))  */{
			let slice = /* stripped */.substring/* : unknown */(1/* : number */)/* : unknown */;
			let tuple = /* parseValue */(state/* : content-start CompileState content-end */, /* slice */, depth/* : number */)/* : unknown */;
			let value = /* tuple */.right/* : unknown */;
			return new Some(new Tuple(/* tuple */.left/* : unknown */, new Not(/* value */)/* : Not */)/* : Tuple */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ parseLambda(state : /* CompileState */, input : string, depth : number) : Option<[/* CompileState */, /* Value */]> {
		return /* first */(input/* : string */, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow/* : unknown */.strip/* : unknown */(/*  */)/* : unknown */;
			/* if (isSymbol(strippedBeforeArrow))  */{
				let type : /* Type */ = /* Primitive */.Unknown/* : unknown */;
				/* if (state.expectedType instanceof Some(var expectedType))  */{
					/* if (expectedType instanceof FunctionType functionType)  */{
						let /* type  */ = /* functionType */.arguments/* : unknown */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */;
					}
				}
				return /* assembleLambda */(/* state */, /* Lists */.of/* : unknown */(new Definition(/* strippedBeforeArrow */, /* type */)/* : Definition */)/* : unknown */, valueString/* : unknown */, /* depth */)/* : unknown */;
			}
			/* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */{
				let parameterNames = /* divideAll */(/* strippedBeforeArrow */.substring/* : unknown */(1/* : number */, /* strippedBeforeArrow */.length/* : unknown */(/*  */)/* : unknown */ - 1/* : number */)/* : unknown */, /* Main */.foldValueChar/* : unknown */)/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.map/* : unknown */(/* String */.strip/* : unknown */)/* : unknown */.filter/* : unknown */((value) => !value/* : unknown */.isEmpty/* : unknown */(/*  */)/* : unknown */)/* : unknown */.map/* : unknown */((name) => new Definition(name/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : Definition */)/* : unknown */.collect/* : unknown */(new ListCollector(/*  */)/* : ListCollector */)/* : unknown */;
				return /* assembleLambda */(/* state */, /* parameterNames */, /* valueString */, /* depth */)/* : unknown */;
			}
			return new None(/*  */)/* : None */;
		})/* : unknown */;
	}
	/* private static */ assembleLambda(state : /* CompileState */, definitions : List<Definition>, valueString : string, depth : number) : Some<[/* CompileState */, /* Value */]> {
		let strippedValueString = valueString/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* Tuple<CompileState, LambdaValue> value */;
		let state2 = state/* : content-start CompileState content-end */.withDefinitions/* : unknown */(definitions/* : List<Definition> */)/* : unknown */;
		/* if (strippedValueString.startsWith("{") && strippedValueString.endsWith("}"))  */{
			let value1 = /* compileStatements */(/* state2 */, /* strippedValueString */.substring/* : unknown */(1/* : number */, /* strippedValueString */.length/* : unknown */(/*  */)/* : unknown */ - 1/* : number */)/* : unknown */, (state1, input1) => /* compileFunctionSegment */(state1/* : unknown */, input1/* : unknown */, /* depth */ + 1/* : number */)/* : unknown */)/* : unknown */;
			let right = /* value1 */.right/* : unknown */;
			let /* value  */ = new Tuple(/* value1 */.left/* : unknown */, new BlockLambdaValue(/* right */, /* depth */)/* : BlockLambdaValue */)/* : Tuple */;
		}
		/* else  */{
			let value1 = /* parseValue */(/* state2 */, /* strippedValueString */, /* depth */)/* : unknown */;
			let /* value  */ = new Tuple(/* value1 */.left/* : unknown */, /* value1 */.right/* : unknown */)/* : Tuple */;
		}
		let right = /* value */.right/* : unknown */;
		return new Some(new Tuple(/* value */.left/* : unknown */, new Lambda(/* definitions */, /* right */)/* : Lambda */)/* : Tuple */)/* : Some */;
	}
	/* private static */ parseDigits(state : /* CompileState */, input : string) : Option<[/* CompileState */, /* Value */]> {
		let stripped = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (isNumber(stripped))  */{
			return new Some(new [/* CompileState */, /* Value */](state/* : content-start CompileState content-end */, new SymbolValue(/* stripped */, /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : [content-start CompileState content-end, content-start Value content-end] */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ isNumber(input : string) : /* boolean */ {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c = input/* : string */.charAt/* : unknown */(/* i */)/* : unknown */;
			/* if (Character.isDigit(c))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	/* private static */ parseInvocation(state : /* CompileState */, input : string, depth : number) : Option<[/* CompileState */, /* Value */]> {
		return /* suffix */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, ")", (withoutEnd) => {
			return /* split */(() => /* toLast */(/* withoutEnd */, "", /* Main */.foldInvocationStart/* : unknown */)/* : unknown */, (callerWithEnd, argumentsString) => {
				return /* suffix */(callerWithEnd/* : unknown */, "(", (callerString) => {
					let callerString1 = callerString/* : unknown */.strip/* : unknown */(/*  */)/* : unknown */;
					let callerTuple = /* invocationHeader */(/* state */, /* depth */, /* callerString1 */)/* : unknown */;
					let oldCaller = /* callerTuple */.right/* : unknown */;
					let newCaller = /* modifyCaller */(/* callerTuple */.left/* : unknown */, /* oldCaller */)/* : unknown */;
					let callerType : FunctionType = new FunctionType(/* Lists */.empty/* : unknown */(/*  */)/* : unknown */, /* Primitive */.Unknown/* : unknown */)/* : FunctionType */;
					/* switch (newCaller)  */{
						/* case ConstructionCaller constructionCaller ->  */{
							let /* callerType  */ = /* constructionCaller */.toFunction/* : unknown */(/*  */)/* : unknown */;
						}
						/* case Value value ->  */{
							let type = /* value */.type/* : unknown */(/*  */)/* : unknown */;
							/* if (type instanceof FunctionType functionType)  */{
								let /* callerType  */ = /* functionType */;
							}
						}
					}
					let finalCallerType : FunctionType = /* callerType */;
					let parsed = /* parseValuesWithIndices */(/* callerTuple */.left/* : unknown */, /* argumentsString */, (state3, pair) => {
						let argumentType = /* finalCallerType */.arguments/* : unknown */.get/* : unknown */(pair/* : unknown */.left/* : unknown */)/* : unknown */.orElse/* : unknown */(/* Primitive */.Unknown/* : unknown */)/* : unknown */;
						let state4 : /* CompileState */ = state3/* : unknown */.withExpectedType/* : unknown */(/* argumentType */)/* : unknown */;
						return new Some(/* parseValue */(/* state4 */, pair/* : unknown */.right/* : unknown */, /* depth */)/* : unknown */)/* : Some */;
					})/* : unknown */.orElseGet/* : unknown */(() => new Tuple(/* callerTuple */.left/* : unknown */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */;
					let arguments = /* parsed */.right/* : unknown */;
					let invokable = new Invokable(/* newCaller */, /* arguments */, /* callerType */.returns/* : unknown */)/* : Invokable */;
					return new Some(new Tuple(/* parsed */.left/* : unknown */, /* invokable */)/* : Tuple */)/* : Some */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ modifyCaller(state : /* CompileState */, oldCaller : Caller) : Caller {
		/* if (oldCaller instanceof DataAccess access)  */{
			let type = /* resolveType */(/* access */.parent/* : unknown */, state/* : content-start CompileState content-end */)/* : unknown */;
			/* if (type instanceof FunctionType)  */{
				return /* access */.parent/* : unknown */;
			}
		}
		return oldCaller/* : Caller */;
	}
	/* private static */ resolveType(value : /* Value */, state : /* CompileState */) : /* Type */ {
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
	/* private static */ invocationHeader(state : /* CompileState */, depth : number, callerString1 : string) : [/* CompileState */, Caller] {
		/* if (callerString1.startsWith("new "))  */{
			let input1 : string = callerString1/* : string */.substring/* : unknown */("new ".length/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
			let map = /* parseType */(state/* : content-start CompileState content-end */, /* input1 */)/* : unknown */.map/* : unknown */((type) => {
				let right = type/* : unknown */.right/* : unknown */;
				return new [/* CompileState */, Caller](type/* : unknown */.left/* : unknown */, new ConstructionCaller(/* right */)/* : ConstructionCaller */)/* : [content-start CompileState content-end, Caller] */;
			})/* : unknown */;
			/* if (map.isPresent())  */{
				return /* map */.orElse/* : unknown */(/* null */)/* : unknown */;
			}
		}
		let tuple = /* parseValue */(/* state */, /* callerString1 */, /* depth */)/* : unknown */;
		return new Tuple(/* tuple */.left/* : unknown */, /* tuple */.right/* : unknown */)/* : Tuple */;
	}
	/* private static */ foldInvocationStart(state : DivideState, c : /* char */) : DivideState {
		let appended = state/* : DivideState */.append/* : unknown */(c/* : content-start char content-end */)/* : unknown */;
		/* if (c == '(')  */{
			let enter = /* appended */.enter/* : unknown */(/*  */)/* : unknown */;
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
	/* private static */ parseDataAccess(state : /* CompileState */, input : string, depth : number) : Option<[/* CompileState */, /* Value */]> {
		return /* last */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, ".", (parentString, rawProperty) => {
			let property = rawProperty/* : unknown */.strip/* : unknown */(/*  */)/* : unknown */;
			/* if (!isSymbol(property))  */{
				return new None(/*  */)/* : None */;
			}
			let tuple = /* parseValue */(/* state */, parentString/* : unknown */, /* depth */)/* : unknown */;
			let parent = /* tuple */.right/* : unknown */;
			let parentType = /* parent */.type/* : unknown */(/*  */)/* : unknown */;
			/* if (parentType instanceof TupleType)  */{
				/* if (property.equals("left"))  */{
					return new Some(new Tuple(/* state */, new IndexValue(/* parent */, new SymbolValue("0", /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */)/* : Tuple */)/* : Some */;
				}
				/* if (property.equals("type"))  */{
					return new Some(new Tuple(/* state */, new IndexValue(/* parent */, new SymbolValue("1", /* Primitive */.Int/* : unknown */)/* : SymbolValue */)/* : IndexValue */)/* : Tuple */)/* : Some */;
				}
			}
			let type : /* Type */ = /* Primitive */.Unknown/* : unknown */;
			/* if (parentType instanceof FindableType objectType)  */{
				/* if (objectType.find(property) instanceof Some(var memberType))  */{
					let /* type  */ = /* memberType */;
				}
			}
			return new Some(new Tuple(/* tuple */.left/* : unknown */, new DataAccess(/* parent */, /* property */, /* type */)/* : DataAccess */)/* : Tuple */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ parseString(state : /* CompileState */, input : string) : Option<[/* CompileState */, /* Value */]> {
		let stripped = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
			return new Some(new Tuple(state/* : content-start CompileState content-end */, new StringValue(/* stripped */)/* : StringValue */)/* : Tuple */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ parseSymbolValue(state : /* CompileState */, value : string) : Option<[/* CompileState */, /* Value */]> {
		let stripped = value/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (isSymbol(stripped))  */{
			/* if (state.resolveValue(stripped) instanceof Some(var type))  */{
				return new Some(new Tuple(state/* : content-start CompileState content-end */, new SymbolValue(/* stripped */, /* type */)/* : SymbolValue */)/* : Tuple */)/* : Some */;
			}
			return new Some(new Tuple(state/* : content-start CompileState content-end */, new Placeholder(/* stripped */)/* : Placeholder */)/* : Tuple */)/* : Some */;
		}
		return new None(/*  */)/* : None */;
	}
	/* private static */ parseOperation(state : /* CompileState */, value : string, depth : number, infix : string) : Option<[/* CompileState */, /* Value */]> {
		return /* first */(value/* : string */, infix/* : string */, (s, s2) => {
			let tuple = /* parseValue */(/* state */, s/* : unknown */, /* depth */)/* : unknown */;
			let tuple1 = /* parseValue */(/* tuple */.left/* : unknown */, s2/* : unknown */, /* depth */)/* : unknown */;
			let left = /* tuple */.right/* : unknown */;
			let right = /* tuple1 */.right/* : unknown */;
			return new Some(new Tuple(/* tuple1 */.left/* : unknown */, new Operation(/* left */, /* infix */, /* right */)/* : Operation */)/* : Tuple */)/* : Some */;
		})/* : unknown */;
	}
	/* private static */ compileValues(state : /* CompileState */, params : string, mapper : (arg0 : /* CompileState */, arg1 : string) => [/* CompileState */, string]) : [/* CompileState */, string] {
		let parsed = /* parseValuesOrEmpty */(state/* : content-start CompileState content-end */, params/* : string */, (state1, s) => new Some(/* mapper */.apply/* : unknown */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */;
		let generated = /* generateValues */(/* parsed */.right/* : unknown */)/* : unknown */;
		return new Tuple(/* parsed */.left/* : unknown */, /* generated */)/* : Tuple */;
	}
	/* private static */ generateValues(elements : List<string>) : string {
		return /* generateAll */(/* Main */.mergeValues/* : unknown */, elements/* : List<string> */)/* : unknown */;
	}
	/* private static  */ parseValuesOrEmpty<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => Option<[/* CompileState */, T]>) : [/* CompileState */, List<T>] {
		return /* parseValues */(state/* : content-start CompileState content-end */, input/* : string */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : string) => Option<[content-start CompileState content-end, T]> */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(/* state */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static  */ parseValues<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => Option<[/* CompileState */, T]>) : Option<[/* CompileState */, List<T>]> {
		return /* parseValuesWithIndices */(state/* : content-start CompileState content-end */, input/* : string */, (state1, tuple) => /* mapper */.apply/* : unknown */(state1/* : unknown */, tuple/* : unknown */.right/* : unknown */)/* : unknown */)/* : unknown */;
	}
	/* private static  */ parseValuesWithIndices<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : [number, string]) => Option<[/* CompileState */, T]>) : Option<[/* CompileState */, List<T>]> {
		return /* parseAll */(state/* : content-start CompileState content-end */, input/* : string */, /* Main */.foldValueChar/* : unknown */, mapper/* : (arg0 : content-start CompileState content-end, arg1 : [number, string]) => Option<[content-start CompileState content-end, T]> */)/* : unknown */;
	}
	/* private static */ compileParameter(state : /* CompileState */, input : string) : [/* CompileState */, Parameter] {
		/* if (input.isBlank())  */{
			return new Tuple(state/* : content-start CompileState content-end */, new Whitespace(/*  */)/* : Whitespace */)/* : Tuple */;
		}
		return /* parseDefinition */(state/* : content-start CompileState content-end */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new [/* CompileState */, Parameter](tuple/* : unknown */.left/* : unknown */, tuple/* : unknown */.right/* : unknown */)/* : [content-start CompileState content-end, Parameter] */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(/* state */, new Placeholder(/* input */)/* : Placeholder */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ compileDefinition(state : /* CompileState */, input : string) : [/* CompileState */, string] {
		return /* parseDefinition */(state/* : content-start CompileState content-end */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple(tuple/* : unknown */.left/* : unknown */, tuple/* : unknown */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */.orElseGet/* : unknown */(() => new Tuple(/* state */, /* generatePlaceholder */(/* input */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ mergeValues(cache : /* StringBuilder */, element : string) : /* StringBuilder */ {
		/* if (cache.isEmpty())  */{
			return cache/* : content-start StringBuilder content-end */.append/* : unknown */(element/* : string */)/* : unknown */;
		}
		return cache/* : content-start StringBuilder content-end */.append/* : unknown */(", ")/* : unknown */.append/* : unknown */(element/* : string */)/* : unknown */;
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat/* : unknown */(depth/* : number */)/* : unknown */;
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : /* CompileState */) : Option<[/* CompileState */, string]> {
		return /* suffix */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, ";", (withoutEnd) => {
			return /* parseDefinition */(/* state */, withoutEnd/* : unknown */)/* : unknown */.map/* : unknown */((result) => {
				let generated = /* createIndent */(/* depth */)/* : unknown */ + result/* : unknown */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */ + ";";
				return new Tuple(result/* : unknown */.left/* : unknown */, /* generated */)/* : Tuple */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseDefinition(state : /* CompileState */, input : string) : Option<[/* CompileState */, Definition]> {
		return /* last */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, " ", (beforeName, name) => {
			return /* split */(() => /* toLast */(/* beforeName */, " ", /* Main */.foldTypeSeparator/* : unknown */)/* : unknown */, (beforeType, type) => {
				return /* suffix */(beforeType/* : unknown */.strip/* : unknown */(/*  */)/* : unknown */, ">", (withoutTypeParamStart) => {
					return /* first */(withoutTypeParamStart/* : unknown */, "<", (beforeTypeParams, typeParamsString) => {
						let /* final */ compileStateStringTupleBiFunction : (arg0 : /* CompileState */, arg1 : string) => [/* CompileState */, string] = (state1, s) => new Tuple(state1/* : unknown */, s/* : unknown */.strip/* : unknown */(/*  */)/* : unknown */)/* : Tuple */;
						let typeParams = /* parseValuesOrEmpty */(/* state */, /* typeParamsString */, (state1, s) => new Some(/* compileStateStringTupleBiFunction */.apply/* : unknown */(state1/* : unknown */, s/* : unknown */)/* : unknown */)/* : Some */)/* : unknown */;
						return /* assembleDefinition */(/* typeParams */.left/* : unknown */, new Some<string>(/* beforeTypeParams */)/* : Some<string> */, /* name */, /* typeParams */.right/* : unknown */, /* type */)/* : unknown */;
					})/* : unknown */;
				})/* : unknown */.or/* : unknown */(() => {
					return /* assembleDefinition */(/* state */, new Some<string>(/* beforeType */)/* : Some<string> */, /* name */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */, /* type */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */.or/* : unknown */(() => {
				return /* assembleDefinition */(/* state */, new None<string>(/*  */)/* : None<string> */, /* name */, /* Lists */.empty/* : unknown */(/*  */)/* : unknown */, /* beforeName */)/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState) : Option<[string, string]> {
		let divisions = /* divideAll */(input/* : string */, folder/* : (arg0 : DivideState, arg1 : content-start Character content-end) => DivideState */)/* : unknown */;
		return /* divisions */.removeLast/* : unknown */(/*  */)/* : unknown */.map/* : unknown */((removed) => {
			let left = removed/* : unknown */.left/* : unknown */.iterate/* : unknown */(/*  */)/* : unknown */.collect/* : unknown */(new Joiner(/* separator */)/* : Joiner */)/* : unknown */.orElse/* : unknown */("")/* : unknown */;
			let right = removed/* : unknown */.right/* : unknown */;
			return new Tuple(/* left */, /* right */)/* : Tuple */;
		})/* : unknown */;
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : /* Character */) : DivideState {
		/* if (c == ' ' && state.isLevel())  */{
			return state/* : DivideState */.advance/* : unknown */(/*  */)/* : unknown */;
		}
		let appended = state/* : DivideState */.append/* : unknown */(c/* : content-start Character content-end */)/* : unknown */;
		/* if (c == '<')  */{
			return /* appended */.enter/* : unknown */(/*  */)/* : unknown */;
		}
		/* if (c == '>')  */{
			return /* appended */.exit/* : unknown */(/*  */)/* : unknown */;
		}
		return /* appended */;
	}
	/* private static */ assembleDefinition(state : /* CompileState */, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<[/* CompileState */, Definition]> {
		return /* parseType */(state/* : content-start CompileState content-end */.withTypeParams/* : unknown */(typeParams/* : List<string> */)/* : unknown */, type/* : string */)/* : unknown */.map/* : unknown */((type1) => {
			let node = new Definition(/* beforeTypeParams */, /* name */.strip/* : unknown */(/*  */)/* : unknown */, type1/* : unknown */.right/* : unknown */, /* typeParams */)/* : Definition */;
			return new Tuple(type1/* : unknown */.left/* : unknown */, /* node */)/* : Tuple */;
		})/* : unknown */;
	}
	/* private static */ foldValueChar(state : DivideState, c : /* char */) : DivideState {
		/* if (c == ',' && state.isLevel())  */{
			return state/* : DivideState */.advance/* : unknown */(/*  */)/* : unknown */;
		}
		let appended = state/* : DivideState */.append/* : unknown */(c/* : content-start char content-end */)/* : unknown */;
		/* if (c == '-')  */{
			let peeked = /* appended */.peek/* : unknown */(/*  */)/* : unknown */;
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
	/* private static */ compileType(state : /* CompileState */, input : string) : Option<[/* CompileState */, string]> {
		return /* parseType */(state/* : content-start CompileState content-end */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple(tuple/* : unknown */.left/* : unknown */, tuple/* : unknown */.right/* : unknown */.generate/* : unknown */(/*  */)/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static */ parseType(state : /* CompileState */, input : string) : Option<[/* CompileState */, /* Type */]> {
		let stripped = input/* : string */.strip/* : unknown */(/*  */)/* : unknown */;
		/* if (stripped.equals("int") || stripped.equals("Integer"))  */{
			return new Some(new Tuple(state/* : content-start CompileState content-end */, /* Primitive */.Int/* : unknown */)/* : Tuple */)/* : Some */;
		}
		/* if (stripped.equals("String"))  */{
			return new Some(new Tuple(state/* : content-start CompileState content-end */, /* Primitive */.String/* : unknown */)/* : Tuple */)/* : Some */;
		}
		/* if (stripped.equals("var"))  */{
			return new Some(new Tuple(state/* : content-start CompileState content-end */, /* Primitive */.Unknown/* : unknown */)/* : Tuple */)/* : Some */;
		}
		/* if (isSymbol(stripped))  */{
			/* if (state.resolveType(stripped) instanceof Some(var resolved))  */{
				return new Some(new Tuple(state/* : content-start CompileState content-end */, /* resolved */)/* : Tuple */)/* : Some */;
			}
			/* else  */{
				return new Some(new Tuple(state/* : content-start CompileState content-end */, new Placeholder(/* stripped */)/* : Placeholder */)/* : Tuple */)/* : Some */;
			}
		}
		return /* parseTemplate */(state/* : content-start CompileState content-end */, input/* : string */)/* : unknown */.or/* : unknown */(() => /* varArgs */(/* state */, /* input */)/* : unknown */)/* : unknown */;
	}
	/* private static */ varArgs(state : /* CompileState */, input : string) : Option<[/* CompileState */, /* Type */]> {
		return /* suffix */(input/* : string */, "...", (s) => {
			return /* parseType */(/* state */, s/* : unknown */)/* : unknown */.map/* : unknown */((inner) => {
				let newState = inner/* : unknown */.left/* : unknown */;
				let child = inner/* : unknown */.right/* : unknown */;
				return new Tuple(/* newState */, new ArrayType(/* child */)/* : ArrayType */)/* : Tuple */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ parseTemplate(state : /* CompileState */, input : string) : Option<[/* CompileState */, /* Type */]> {
		return /* suffix */(input/* : string */.strip/* : unknown */(/*  */)/* : unknown */, ">", (withoutEnd) => {
			return /* first */(withoutEnd/* : unknown */, "<", (base, argumentsString) => {
				let strippedBase = base/* : unknown */.strip/* : unknown */(/*  */)/* : unknown */;
				return /* parseValues */(/* state */, argumentsString/* : unknown */, /* Main */.argument/* : unknown */)/* : unknown */.map/* : unknown */((argumentsTuple) => {
					return /* assembleTemplate */(/* strippedBase */, argumentsTuple/* : unknown */.left/* : unknown */, argumentsTuple/* : unknown */.right/* : unknown */)/* : unknown */;
				})/* : unknown */;
			})/* : unknown */;
		})/* : unknown */;
	}
	/* private static */ assembleTemplate(base : string, state : /* CompileState */, arguments : List<Argument>) : [/* CompileState */, /* Type */] {
		let children = arguments/* : List<Argument> */.iterate/* : () => Iterator<T> */(/*  */)/* : Iterator<T> */.map/* : (arg0 : (arg0 : T) => R) => Option<R> */(/* Main */.retainType/* : unknown */)/* : Option<R> */.flatMap/* : (arg0 : (arg0 : R) => Option<R>) => Option<R> */(/* Iterators */.fromOption/* : unknown */)/* : Option<R> */.collect/* : (arg0 : Collector<T, R>) => R */(new ListCollector(/*  */)/* : ListCollector */)/* : R */;
		/* if (base.equals("BiFunction"))  */{
			return new Tuple(state/* : content-start CompileState content-end */, new FunctionType(/* Lists */.of/* : unknown */(/* children */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */, /* children */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, /* children */.get/* : unknown */(2/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */)/* : Tuple */;
		}
		/* if (base.equals("Function"))  */{
			return new Tuple(state/* : content-start CompileState content-end */, new FunctionType(/* Lists */.of/* : unknown */(/* children */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, /* children */.get/* : unknown */(1/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */)/* : Tuple */;
		}
		/* if (base.equals("Predicate"))  */{
			return new Tuple(state/* : content-start CompileState content-end */, new FunctionType(/* Lists */.of/* : unknown */(/* children */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : unknown */, /* Primitive */.Boolean/* : unknown */)/* : FunctionType */)/* : Tuple */;
		}
		/* if (base.equals("Supplier"))  */{
			return new Tuple(state/* : content-start CompileState content-end */, new FunctionType(/* Lists */.empty/* : unknown */(/*  */)/* : unknown */, /* children */.get/* : unknown */(0/* : number */)/* : unknown */.orElse/* : unknown */(/* null */)/* : unknown */)/* : FunctionType */)/* : Tuple */;
		}
		/* if (base.equals("Tuple") && children.size() >= 2)  */{
			return new Tuple(state/* : content-start CompileState content-end */, new TupleType(/* children */)/* : TupleType */)/* : Tuple */;
		}
		/* if (state.resolveType(base) instanceof Some(var baseType) && baseType instanceof FindableType findableType)  */{
			return new Tuple(state/* : content-start CompileState content-end */, new Template(/* findableType */, /* children */)/* : Template */)/* : Tuple */;
		}
		return new Tuple(state/* : content-start CompileState content-end */, new Template(new Placeholder(base/* : string */)/* : Placeholder */, /* children */)/* : Template */)/* : Tuple */;
	}
	/* private static */ retainType(argument : Argument) : Option</* Type */> {
		/* if (argument instanceof Type type)  */{
			return new Some(/* type */)/* : Some */;
		}
		/* else  */{
			return new None</* Type */>(/*  */)/* : None<content-start Type content-end> */;
		}
	}
	/* private static */ argument(state : /* CompileState */, input : string) : Option<[/* CompileState */, Argument]> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple(state/* : content-start CompileState content-end */, new Whitespace(/*  */)/* : Whitespace */)/* : Tuple */)/* : Some */;
		}
		return /* parseType */(state/* : content-start CompileState content-end */, input/* : string */)/* : unknown */.map/* : unknown */((tuple) => new Tuple(tuple/* : unknown */.left/* : unknown */, tuple/* : unknown */.right/* : unknown */)/* : Tuple */)/* : unknown */;
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, /* Main */.findLast/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	/* private static */ findLast(input : string, infix : string) : Option<number> {
		let index = input/* : string */.lastIndexOf/* : unknown */(infix/* : string */)/* : unknown */;
		/* if (index == -1)  */{
			return new None<number>(/*  */)/* : None<number> */;
		}
		return new Some(/* index */)/* : Some */;
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix/* : string */(input/* : string */, infix/* : string */, /* Main */.findFirst/* : unknown */, mapper/* : (arg0 : string, arg1 : string) => Option<T> */)/* : unknown */;
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return /* split */(() => /* locator */.apply/* : unknown */(/* input */, /* infix */)/* : unknown */.map/* : unknown */((index) => {
			let left = /* input */.substring/* : unknown */(0/* : number */, index/* : unknown */)/* : unknown */;
			let right = /* input */.substring/* : unknown */(index/* : unknown */ + /* infix */.length/* : unknown */(/*  */)/* : unknown */)/* : unknown */;
			return new Tuple(/* left */, /* right */)/* : Tuple */;
		})/* : unknown */, /* mapper */)/* : unknown */;
	}
	/* private static  */ split<T>(splitter : () => Option<[string, string]>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter/* : () => Option<[string, string]> */(/*  */)/* : Option<[string, string]> */.flatMap/* : (arg0 : (arg0 : [string, string]) => Option<R>) => Option<R> */((tuple : [string, string]) => /* mapper */.apply/* : unknown */(tuple/* : [string, string] */[0/* : number */], tuple/* : [string, string] */.right/* : unknown */)/* : unknown */)/* : Option<R> */;
	}
	/* private static */ findFirst(input : string, infix : string) : Option<number> {
		let index = input/* : string */.indexOf/* : unknown */(infix/* : string */)/* : unknown */;
		/* if (index == -1)  */{
			return new None<number>(/*  */)/* : None<number> */;
		}
		return new Some(/* index */)/* : Some */;
	}
	/* private static */ generatePlaceholder(input : string) : string {
		let replaced = input/* : string */.replace/* : unknown */("/*", "content-start")/* : unknown */.replace/* : unknown */("*/", "content-end")/* : unknown */;
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

        @Override
        public Type replace(Map<String, Type> mapping) {
            return this;
        }
    } */
}
/*  */
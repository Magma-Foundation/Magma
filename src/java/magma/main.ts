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
	and<R>(other : () => Option<R>) : Option<Tuple2<T, R>>;
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
	zip<R>(other : Iterator<R>) : Iterator<Tuple2<T, R>>;
}
/* private */interface List<T>/*   */ {
	addLast(element : T) : List<T>;
	iterate() : Iterator<T>;
	removeLast() : Option<Tuple2<List<T>, T>>;
	get(index : number) : Option<T>;
	size() : number;
	isEmpty() : boolean;
	addFirst(element : T) : List<T>;
	iterateWithIndices() : Iterator<Tuple2<number, T>>;
	removeFirst() : Option<Tuple2<T, List<T>>>;
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
/* private static */class None<T>/*  */ {
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new None();
	}
	/* @Override
        public */ isPresent() : boolean {
		return /* false */;
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
		return other.get();
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return new None();
	}
	/* @Override
        public */ isEmpty() : boolean {
		return /* true */;
	}
	/* @Override
        public  */ and<R>(other : () => Option<R>) : Option<Tuple2<T, R>> {
		return new None();
	}
}
/* private */class Some<T>/*  */ {
	constructor (value : T) {
	}

	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Option<R> {
		return new Some(mapper(/* this */.value));
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
        public */ filter(predicate : (arg0 : T) => boolean) : Option<T> {
		/* if (predicate.test(this.value))  */{
			return /* this */;
		}
		return new None();
	}
	/* @Override
        public */ orElseGet(supplier : () => T) : T {
		return /* this */.value;
	}
	/* @Override
        public */ or(other : () => Option<T>) : Option<T> {
		return /* this */;
	}
	/* @Override
        public  */ flatMap<R>(mapper : (arg0 : T) => Option<R>) : Option<R> {
		return mapper(/* this */.value);
	}
	/* @Override
        public */ isEmpty() : boolean {
		return /* false */;
	}
	/* @Override
        public  */ and<R>(other : () => Option<R>) : Option<Tuple2<T, R>> {
		return other.get().map((otherValue) => new /* Tuple2Impl */(/* this */.value, otherValue));
	}
}
/* private static */class SingleHead<T>/*  */ {
	/* private final */ value : T;
	/* private */ retrieved : boolean;
	SingleHead(value : T) : /* public */ {
		let /* this.value  */ = value;
		let /* this.retrieved  */ = /* false */;
	}
	/* @Override
        public */ next() : Option<T> {
		/* if (this.retrieved)  */{
			return new None();
		}
		let /* this.retrieved  */ = /* true */;
		return new Some(/* this */.value);
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
	/* @Override
        public  */ map<R>(mapper : (arg0 : T) => R) : Iterator<R> {
		return new HeadedIterator(() => /* this */.head.next().map(mapper));
	}
	/* @Override
        public  */ collect<R>(collector : Collector<T, R>) : R {
		return /* this */.fold(collector.createInitial(), collector.fold);
	}
	/* @Override
        public */ filter(predicate : (arg0 : T) => boolean) : Iterator<T> {
		return /* this */.flatMap((element) => {
			/* if (predicate.test(element))  */{
				return new HeadedIterator(new SingleHead(element));
			}
			return new HeadedIterator(new EmptyHead());
		});
	}
	/* @Override
        public */ next() : Option<T> {
		return /* this */.head.next();
	}
	/* @Override
        public  */ flatMap<R>(f : (arg0 : T) => Iterator<R>) : Iterator<R> {
		return new HeadedIterator(new /* FlatMapHead */(/* this */.head, f));
	}
	/* @Override
        public  */ zip<R>(other : Iterator<R>) : Iterator<Tuple2<T, R>> {
		return new HeadedIterator(() => /* HeadedIterator */.this.head.next().and(other.next));
	}
}
/* private static */class RangeHead/*  */ {
	/* private final */ length : number;
	/* private */ counter : number;
	RangeHead(length : number) : /* public */ {
		let /* this.length  */ = length;
	}
	/* @Override
        public */ next() : Option<number> {
		/* if (this.counter < this.length)  */{
			let value = /* this */.counter;
			/* this.counter++ */;
			return new Some(value);
		}
		return new None();
	}
}
/* private static final */class JVMList<T>/*  */ {
	/* private final */ elements : /* java.util.List */<T>;
	JVMList(elements : /* java.util.List */<T>) : /* private */ {
		let /* this.elements  */ = elements;
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
		return /* this */.iterateWithIndices().map(/* Tuple2 */.right);
	}
	/* @Override
            public */ removeLast() : Option<Tuple2<List<T>, T>> {
		/* if (this.elements.isEmpty())  */{
			return new None();
		}
		let slice = /* this */.elements.subList(0, /* this */.elements.size() - 1);
		let last = /* this */.elements.getLast();
		return new Some(new /* Tuple2Impl */<List<T>, T>(new JVMList(/* slice */), /* last */));
	}
	/* @Override
            public */ size() : number {
		return /* this */.elements.size();
	}
	/* @Override
            public */ isEmpty() : boolean {
		return /* this */.elements.isEmpty();
	}
	/* @Override
            public */ addFirst(element : T) : List<T> {
		/* this.elements.addFirst(element) */;
		return /* this */;
	}
	/* @Override
            public */ iterateWithIndices() : Iterator<Tuple2<number, T>> {
		return new HeadedIterator(new RangeHead(/* this */.elements.size())).map((index : T) => new /* Tuple2Impl */(index, /* this */.elements.get(index)));
	}
	/* @Override
            public */ removeFirst() : Option<Tuple2<T, List<T>>> {
		/* if (this.elements.isEmpty())  */{
			return new None();
		}
		let first = /* this */.elements.getFirst();
		let slice = /* this */.elements.subList(1, /* this */.elements.size());
		return new Some(new /* Tuple2Impl */<T, List<T>>(/* first */, new JVMList(/* slice */)));
	}
	/* @Override
            public */ addAllLast(others : List<T>) : List<T> {
		let initial : List<T> = /* this */;
		return others.iterate().fold(initial, /* List */.addLast);
	}
	/* @Override
            public */ get(index : number) : Option<T> {
		/* if (index < this.elements.size())  */{
			return new Some(/* this */.elements.get(index));
		}
		/* else  */{
			return new None();
		}
	}
}
/* private static */class Lists/*  */ {
	/* public static  */ empty<T>() : List<T> {
		return new JVMList();
	}
	/* public static  */ of<T>(elements : T[]) : List<T> {
		return new JVMList(new /* ArrayList */(/* Arrays */.asList(elements)));
	}
}
/* private */class ObjectType/*  */ {
	constructor (name : string, typeParams : List<string>, definitions : List</* Definition */>) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.name;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return new ObjectType(/* this */.name, /* this */.typeParams, /* this */.definitions.iterate().map((definition) => definition.mapType((type) => type.replace(mapping))).collect(new /* ListCollector */()));
	}
	/* @Override
        public */ find(name : string) : Option</* Type */> {
		return /* this */.definitions.iterate().filter((definition) => definition.name.equals(name)).map(/* Definition */.type).next();
	}
}
/* private */class TypeParam/*  */ {
	constructor (value : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.value;
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		/* if (mapping.containsKey(this.value))  */{
			return mapping.get(/* this */.value);
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
		let /* this.segments  */ = segments;
		let /* this.buffer  */ = buffer;
		let /* this.depth  */ = depth;
		let /* this.input  */ = input;
		let /* this.index  */ = index;
	}
	DivideState(input : string) : /* public */ {
		/* this(input, 0, Lists.empty(), new StringBuilder(), 0) */;
	}
	/* private */ advance() : DivideState {
		let /* this.segments  */ = /* this */.segments.addLast(/* this */.buffer.toString());
		let /* this.buffer  */ = new /* StringBuilder */();
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
	/* public */ pop() : Option<Tuple2</* Character */, DivideState>> {
		/* if (this.index < this.input.length())  */{
			let c = /* this */.input.charAt(/* this */.index);
			return new Some(new /* Tuple2Impl */(c, new DivideState(/* this */.input, /* this */.index + 1, /* this */.segments, /* this */.buffer, /* this */.depth)));
		}
		return new None();
	}
	/* public */ popAndAppendToTuple() : Option<Tuple2</* Character */, DivideState>> {
		return /* this */.pop().map((tuple) => {
			let c = tuple.left();
			let right = tuple.right();
			return new /* Tuple2Impl */(c, right(c));
		});
	}
	/* public */ popAndAppendToOption() : Option<DivideState> {
		return /* this */.popAndAppendToTuple().map(/* Tuple2 */.right);
	}
	/* public */ peek() : /* char */ {
		return /* this */.input.charAt(/* this */.index);
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
		return new None();
	}
	/* @Override
        public */ fold(current : Option<string>, element : string) : Option<string> {
		return new Some(current.map((inner : string) => inner + /* this */.delimiter + element).orElse(element));
	}
}
/* private */class Definition/*  */ {
	constructor (maybeBefore : Option<string>, name : string, type : /* Type */, typeParams : List<string>) {
	}

	Definition(name : string, type : /* Type */) : /* public */ {
		/* this(new None<>(), name, type, Lists.empty()) */;
	}
	/* private */ generate() : string {
		return /* this */.generateWithParams("");
	}
	/* public */ generateWithParams(params : string) : string {
		let joined = /* this */.joinTypeParams();
		let before = /* this */.joinBefore();
		let typeString = /* this */.generateType();
		return /* before */ + /* this */.name + /* joined */ + params + /* typeString */;
	}
	/* private */ generateType() : string {
		/* if (this.type.equals(Primitive.Unknown))  */{
			return "";
		}
		return " : " + /* this */.type.generate();
	}
	/* private */ joinBefore() : string {
		return /* this */.maybeBefore.filter((value) => !value.isEmpty()).map(/* Main */.generatePlaceholder).map((inner) => inner + " ").orElse("");
	}
	/* private */ joinTypeParams() : string {
		return /* this */.typeParams.iterate().collect(new Joiner()).map((inner) => "<" + inner + ">").orElse("");
	}
	/* public */ mapType(mapper : (arg0 : /* Type */) => /* Type */) : Definition {
		return new Definition(/* this */.maybeBefore, /* this */.name, mapper(/* this */.type), /* this */.typeParams);
	}
}
/* private static */class ListCollector<T>/*  */ {
	/* @Override
        public */ createInitial() : List<T> {
		return /* Lists */.empty();
	}
	/* @Override
        public */ fold(current : List<T>, element : T) : List<T> {
		return current.addLast(element);
	}
}
/* private */class Tuple2Impl<A, B>/*  */ {
	constructor (left : A, right : B) {
	}

}
/* private static */class FlatMapHead<T, R>/*  */ {
	/* private final */ mapper : (arg0 : T) => Iterator<R>;
	/* private final */ head : Head<T>;
	/* private */ current : Option<Iterator<R>>;
	FlatMapHead(head : Head<T>, mapper : (arg0 : T) => Iterator<R>) : /* public */ {
		let /* this.mapper  */ = mapper;
		let /* this.current  */ = new None();
		let /* this.head  */ = head;
	}
	/* @Override
        public */ next() : Option<R> {
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
	constructor (right : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.right().generate() + "[]";
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
		let single : Option<Head<T>> = option.map(SingleHead.new);
		return new HeadedIterator(/* single */.orElseGet(/* EmptyHead */.new));
	}
}
/* private */class FunctionType/*  */ {
	constructor (arguments : List</* Type */>, returns : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		let joined = /* this */.arguments().iterateWithIndices().map((pair) => "arg" + pair.left() + " : " + pair.right().generate()).collect(new Joiner(", ")).orElse("");
		return "(" + /* joined */ + ") => " + /* this */.returns.generate();
	}
	/* @Override
        public */ replace(mapping : /* Map */<string, /* Type */>) : /* Type */ {
		return new FunctionType(/* this */.arguments.iterate().map((type) => type.replace(mapping)).collect(new ListCollector()), /* this */.returns);
	}
}
/* private */class TupleType/*  */ {
	constructor (arguments : List</* Type */>) {
	}

	/* @Override
        public */ generate() : string {
		let joinedArguments = /* this */.arguments.iterate().map(/* Type */.generate).collect(new Joiner(", ")).orElse("");
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
		let joinedArguments = /* this */.arguments.iterate().map(/* Type */.generate).collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
		return /* this */.base.generate() + /* joinedArguments */;
	}
	/* @Override
        public */ typeParams() : List<string> {
		return /* this */.base.typeParams();
	}
	/* @Override
        public */ find(name : string) : Option</* Type */> {
		return /* this */.base.find(name).map((found) => {
			let mapping = /* this */.base.typeParams().iterate().zip(/* this */.arguments.iterate()).collect(new /* MapCollector */());
			return found.replace(mapping);
		});
	}
}
/* private */class Placeholder/*  */ {
	constructor (input : string) {
	}

	/* @Override
        public */ generate() : string {
		return /* generatePlaceholder */(/* this */.input);
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown;
	}
	/* @Override
        public */ typeParams() : List<string> {
		return /* Lists */.empty();
	}
	/* @Override
        public */ find(name : string) : Option</* Type */> {
		return new None();
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
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown;
	}
}
/* private */class DataAccess/*  */ {
	constructor (parent : /* Value */, property : string, type : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.parent.generate() + "." + /* this */.property + /* createDebugString */(/* this */.type);
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* this */.type;
	}
}
/* private */class ConstructionCaller/*  */ {
	constructor (type : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		return "new " + /* this */.type.generate();
	}
	/* public */ toFunction() : FunctionType {
		return new FunctionType(/* Lists */.empty(), /* this */.type);
	}
}
/* private */class Operation/*  */ {
	constructor (left : /* Value */, infix : string, right : /* Value */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.left().generate() + " " + /* this */.infix + " " + /* this */.right().generate();
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown;
	}
}
/* private */class Not/*  */ {
	constructor (value : /* Value */) {
	}

	/* @Override
        public */ generate() : string {
		return "!" + /* this */.value.generate();
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown;
	}
}
/* private */class BlockLambdaValue/*  */ {
	constructor (right : string, depth : number) {
	}

	/* @Override
        public */ generate() : string {
		return "{" + this.right() + createIndent(this.depth) + "}";
	}
}
/* private */class Lambda/*  */ {
	constructor (parameters : List<Definition>, body : LambdaValue) {
	}

	/* @Override
        public */ generate() : string {
		let joined = /* this */.parameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
		return "(" + /* joined */ + ") => " + /* this */.body.generate();
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown;
	}
}
/* private */class Invokable/*  */ {
	constructor (caller : Caller, arguments : List</* Value */>, type : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		let joined = /* this */.arguments.iterate().map(/* Value */.generate).collect(new Joiner(", ")).orElse("");
		return /* this */.caller.generate() + "(" + /* joined */ + ")" + /* createDebugString */(/* this */.type);
	}
}
/* private */class IndexValue/*  */ {
	constructor (parent : /* Value */, child : /* Value */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.parent.generate() + "[" + this.child.generate() + "]";
	}
	/* @Override
        public */ type() : /* Type */ {
		return /* Primitive */.Unknown;
	}
}
/* private */class SymbolValue/*  */ {
	constructor (stripped : string, type : /* Type */) {
	}

	/* @Override
        public */ generate() : string {
		return /* this */.stripped + /* createDebugString */(/* this */.type);
	}
}
/* private */class MapCollector<K, V>/*  */ {
	constructor () {
	}

	/* @Override
        public */ createInitial() : /* Map */<K, V> {
		return new /* HashMap */();
	}
	/* @Override
        public */ fold(current : /* Map */<K, V>, element : Tuple2<K, V>) : /* Map */<K, V> {
		/* current.put(element.left(), element.right()) */;
		return current;
	}
}
/* public */class Main/*  */ {/* 

    private interface Type extends Argument {
        String generate();

        Type replace(Map<String, Type> mapping);
    } *//* 

    private sealed interface Value extends LambdaValue, Caller, Argument {
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
	/* private */ CompileState(structures : List<string>, definitions : List</* Definition */>, objectTypes : List<ObjectType>, maybeStructName : Option<string>, typeParams : List<string>, typeRegister : Option</* Type */>) : /* record */ {
		/* public CompileState()  */{
			/* this(Lists.empty(), Lists.empty(), Lists.empty(), new None<>(), Lists.empty(), new None<>()) */;
		}
		/* private Option<Type> resolveValue(String name)  */{
			return /* this */.definitions.iterate().filter((definition) => definition.name.equals(name)).next().map(/* Definition */.type);
		}
		/* public CompileState addStructure(String structure)  */{
			return new /* CompileState */(/* this */.structures.addLast(/* structure */), /* this */.definitions, /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams, /* this */.typeRegister);
		}
		/* public CompileState withDefinitions(List<Definition> definitions)  */{
			return new /* CompileState */(/* this */.structures, /* this */.definitions.addAllLast(definitions), /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams, /* this */.typeRegister);
		}
		/* public Option<Type> resolveType(String name)  */{
			/* if (this.maybeStructName.filter(inner -> inner.equals(name)).isPresent())  */{
				return new Some(new ObjectType(name, /* this */.typeParams, /* this */.definitions));
			}
			let maybeTypeParam = /* this */.typeParams.iterate().filter((param) => param.equals(name)).next();
			/* if (maybeTypeParam instanceof Some(var value))  */{
				return new Some(new TypeParam(value));
			}
			return /* this */.objectTypes.iterate().filter((type) => type.name.equals(name)).next().map((type) => type);
		}
		/* public CompileState addType(ObjectType type)  */{
			return new /* CompileState */(/* this */.structures, /* this */.definitions, /* this */.objectTypes.addLast(type), /* this */.maybeStructName, /* this */.typeParams, /* this */.typeRegister);
		}
		/* public CompileState withDefinition(Definition definition)  */{
			return new /* CompileState */(/* this */.structures, /* this */.definitions.addLast(definition), /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams, /* this */.typeRegister);
		}
		/* public CompileState withStructName(String name)  */{
			return new /* CompileState */(/* this */.structures, /* this */.definitions, /* this */.objectTypes, new Some(name), /* this */.typeParams, /* this */.typeRegister);
		}
		/* public CompileState withTypeParams(List<String> typeParams)  */{
			return new /* CompileState */(/* this */.structures, /* this */.definitions, /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams.addAllLast(typeParams), /* this */.typeRegister);
		}
		/* public CompileState withExpectedType(Type type)  */{
			return new /* CompileState */(/* this */.structures, /* this */.definitions, /* this */.objectTypes, /* this */.maybeStructName, /* this */.typeParams, new Some(type));
		}
	}/* 

    private static final boolean isDebug = false; */
	/* public static */ main() : /* void */ {
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
	/* private static */ compile(input : string) : string {
		let tuple = /* compileStatements */(new /* CompileState */(), input, /* Main */.compileRootSegment);
		let joined = tuple.left().structures.iterate().collect(new Joiner()).orElse("");
		return /* joined */ + tuple.right();
	}
	/* private static */ compileStatements(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => Tuple2</* CompileState */, string>) : Tuple2</* CompileState */, string> {
		let parsed = /* parseStatements */(state, input, mapper);
		return new Tuple2Impl(/* parsed */.left(), /* generateStatements */(/* parsed */.right()));
	}
	/* private static */ generateStatements(statements : List<string>) : string {
		return /* generateAll */(/* Main */.mergeStatements, statements);
	}
	/* private static */ parseStatements(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => Tuple2</* CompileState */, string>) : Tuple2</* CompileState */, List<string>> {
		return /* parseAll0 */(state, input, /* Main */.foldStatementChar, mapper);
	}
	/* private static */ generateAll(merger : (arg0 : /* StringBuilder */, arg1 : string) => /* StringBuilder */, elements : List<string>) : string {
		return elements.iterate().fold(new /* StringBuilder */(), merger).toString();
	}
	/* private static  */ parseAll0<T>(state : /* CompileState */, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : /* CompileState */, arg1 : string) => Tuple2</* CompileState */, T>) : Tuple2</* CompileState */, List<T>> {
		return /* getCompileStateListTuple */(state, input, folder, (state1, s) => new Some(mapper(state1, s))).orElseGet(() => new Tuple2Impl(state, /* Lists */.empty()));
	}
	/* private static  */ getCompileStateListTuple<T>(state : /* CompileState */, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : /* CompileState */, arg1 : string) => Option<Tuple2</* CompileState */, T>>) : Option<Tuple2</* CompileState */, List<T>>> {
		return /* parseAll */(state, input, folder, (state1, tuple) => mapper(state1, tuple.right()));
	}
	/* private static  */ parseAll<T>(state : /* CompileState */, input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState, mapper : (arg0 : /* CompileState */, arg1 : Tuple2<number, string>) => Option<Tuple2</* CompileState */, T>>) : Option<Tuple2</* CompileState */, List<T>>> {
		let initial : Option<Tuple2</* CompileState */, List<T>>> = new Some(new Tuple2Impl(state, /* Lists */.empty()));
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
	/* private static */ mergeStatements(stringBuilder : /* StringBuilder */, str : string) : /* StringBuilder */ {
		return stringBuilder.append(str);
	}
	/* private static */ divideAll(input : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState) : List<string> {
		let current = new DivideState(input);
		/* while (true)  */{
			let maybePopped = current.pop().map((tuple : Tuple2</* Character */, DivideState>) => {
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
	/* private static */ foldDoubleQuotes(tuple : Tuple2</* Character */, DivideState>) : Option<DivideState> {
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
	/* private static */ foldSingleQuotes(tuple : Tuple2</* Character */, DivideState>) : Option<DivideState> {
		/* if (tuple.left() != '\'')  */{
			return new None();
		}
		let appended = tuple.right().append(tuple.left());
		return /* appended */.popAndAppendToTuple().map(/* Main */.foldEscaped).flatMap(DivideState.popAndAppendToOption);
	}
	/* private static */ foldEscaped(escaped : Tuple2</* Character */, DivideState>) : DivideState {
		/* if (escaped.left() == '\\')  */{
			return escaped.right().popAndAppendToOption().orElse(escaped.right());
		}
		return escaped.right();
	}
	/* private static */ foldStatementChar(state : DivideState, c : /* char */) : DivideState {
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
	/* private static */ compileRootSegment(state : /* CompileState */, input : string) : Tuple2</* CompileState */, string> {
		let stripped = input.strip();
		/* if (stripped.startsWith("package ") || stripped.startsWith("import "))  */{
			return new Tuple2Impl(state, "");
		}
		return /* compileClass */(/* stripped */, 0, state).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */(/* stripped */)));
	}
	/* private static */ compileClass(stripped : string, depth : number, state : /* CompileState */) : Option<Tuple2</* CompileState */, string>> {
		return /* compileStructure */(stripped, "class ", "class ", state);
	}
	/* private static */ compileStructure(stripped : string, sourceInfix : string, targetInfix : string, state : /* CompileState */) : Option<Tuple2</* CompileState */, string>> {
		return /* first */(stripped, sourceInfix, (beforeInfix, right) => {
			return /* first */(right, "{", (beforeContent, withEnd) => {
				let strippedWithEnd = withEnd.strip();
				return /* suffix */(/* strippedWithEnd */, "}", (content1) => {
					return /* first */(beforeContent, " implements ", (s, s2) => {
						return /* structureWithMaybeParams */(targetInfix, state, beforeInfix, s, content1);
					}).or(() => {
						return /* structureWithMaybeParams */(targetInfix, state, beforeInfix, beforeContent, content1);
					});
				});
			});
		});
	}
	/* private static */ structureWithMaybeParams(targetInfix : string, state : /* CompileState */, beforeInfix : string, beforeContent : string, content1 : string) : Option<Tuple2</* CompileState */, string>> {
		return /* suffix */(beforeContent, ")", (s) => {
			return /* first */(s, "(", (s1, s2) => {
				let parsed = /* parseParameters */(state, s2);
				return /* getOred */(targetInfix, /* parsed */.left(), beforeInfix, s1, content1, /* parsed */.right());
			});
		}).or(() => {
			return /* getOred */(targetInfix, state, beforeInfix, beforeContent, content1, /* Lists */.empty());
		});
	}
	/* private static */ getOred(targetInfix : string, state : /* CompileState */, beforeInfix : string, beforeContent : string, content1 : string, params : List<Parameter>) : Option<Tuple2</* CompileState */, string>> {
		return /* first */(beforeContent, "<", (name, withTypeParams) => {
			return /* first */(withTypeParams, ">", (typeParamsString, afterTypeParams) => {
				let /* final */ compileStateStringTupleBiFunction : (arg0 : /* CompileState */, arg1 : string) => Tuple2</* CompileState */, string> = (state1, s) => new Tuple2Impl(state1, s.strip());
				let typeParams = /* parseValuesOrEmpty */(state, typeParamsString, (state1, s) => new Some(/* compileStateStringTupleBiFunction */.apply(state1, s)));
				return /* assembleStructure */(typeParams.left(), targetInfix, beforeInfix, name, content1, typeParams.right(), afterTypeParams, params);
			});
		}).or(() => {
			return /* assembleStructure */(state, targetInfix, beforeInfix, beforeContent, content1, /* Lists */.empty(), "", params);
		});
	}
	/* private static */ assembleStructure(state : /* CompileState */, targetInfix : string, beforeInfix : string, rawName : string, content : string, typeParams : List<string>, afterTypeParams : string, params : List<Parameter>) : Option<Tuple2</* CompileState */, string>> {
		let name = rawName.strip();
		/* if (!isSymbol(name))  */{
			return new None();
		}
		let joinedTypeParams = typeParams.iterate().collect(new Joiner(", ")).map((inner) => "<" + inner + ">").orElse("");
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
	/* private static */ retainDefinition(parameter : Parameter) : Option<Definition> {
		/* if (parameter instanceof Definition definition)  */{
			return new Some(definition);
		}
		return new None();
	}
	/* private static */ isSymbol(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c = input.charAt(/* i */);
			/* if (Character.isLetter(c) || (i != 0 && Character.isDigit(c)))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	/* private static  */ suffix<T>(input : string, suffix : string, mapper : (arg0 : string) => Option<T>) : Option<T> {
		/* if (!input.endsWith(suffix))  */{
			return new None();
		}
		let slice = input.substring(0, input.length() - suffix.length());
		return mapper(/* slice */);
	}
	/* private static */ compileClassSegment(state : /* CompileState */, input : string, depth : number) : Tuple2</* CompileState */, string> {
		return /* compileWhitespace */(input, state).or(() => compileClass(input, depth, state)).or(() => compileStructure(input, "interface ", "interface ", state)).or(() => compileStructure(input, "record ", "class ", state)).or(() => /* compileMethod */(state, input, depth)).or(() => /* compileDefinitionStatement */(input, depth, state)).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */(input)));
	}
	/* private static */ compileWhitespace(input : string, state : /* CompileState */) : Option<Tuple2</* CompileState */, string>> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple2Impl(state, ""));
		}
		return new None();
	}
	/* private static */ compileMethod(state : /* CompileState */, input : string, depth : number) : Option<Tuple2</* CompileState */, string>> {
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
					let paramTypes = /* parameters */.iterate().map(Definition.type).collect(new ListCollector());
					let toDefine = new Definition(definition.name, new FunctionType(/* paramTypes */, definition.type));
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
	/* private static */ joinValues(retainParameters : List<Definition>) : string {
		return retainParameters.iterate().map(Definition.generate).collect(new Joiner(", ")).orElse("");
	}
	/* private static */ retainDefinitions(right : List<Parameter>) : List<Definition> {
		return right().map(/* Main */.retainDefinition).flatMap(/* Iterators */.fromOption).collect(new ListCollector());
	}
	/* private static */ parseParameters(state : /* CompileState */, params : string) : Tuple2</* CompileState */, List<Parameter>> {
		return /* parseValuesOrEmpty */(state, params, (state1, s) => new Some(/* compileParameter */(state1, s)));
	}
	/* private static */ compileFunctionSegments(state : /* CompileState */, input : string, depth : number) : Tuple2</* CompileState */, string> {
		return compileStatements(state, input, (state1, input1) => /* compileFunctionSegment */(state1, input1, depth + 1));
	}
	/* private static */ compileFunctionSegment(state : /* CompileState */, input : string, depth : number) : Tuple2</* CompileState */, string> {
		let stripped = input.strip();
		/* if (stripped.isEmpty())  */{
			return new Tuple2Impl(state, "");
		}
		return /* compileFunctionStatement */(state, depth, stripped).or(() => /* compileBlock */(state, depth, stripped)).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */(stripped)));
	}
	/* private static */ compileFunctionStatement(state : /* CompileState */, depth : number, stripped : string) : Option<Tuple2</* CompileState */, string>> {
		return suffix(stripped, ";", (s) => {
			let tuple = /* statementValue */(state, s, depth);
			return new Some(new Tuple2Impl(tuple.left(), /* createIndent */(depth) + tuple.right() + ";"));
		});
	}
	/* private static */ compileBlock(state : /* CompileState */, depth : number, stripped : string) : Option<Tuple2</* CompileState */, string>> {
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
	/* private static */ foldBlockStart(state : DivideState, c : /* Character */) : DivideState {
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
	/* private static */ statementValue(state : /* CompileState */, input : string, depth : number) : Tuple2</* CompileState */, string> {
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
	/* private static */ compileValue(state : /* CompileState */, input : string, depth : number) : Tuple2</* CompileState */, string> {
		let tuple = /* parseValue */(state, input, depth);
		return new Tuple2Impl(tuple.left(), tuple.right().generate());
	}
	/* private static */ parseValue(state : /* CompileState */, input : string, depth : number) : Tuple2</* CompileState */, /* Value */> {
		return /* parseLambda */(state, input, depth).or(() => /* parseString */(state, input)).or(() => /* parseDataAccess */(state, input, depth)).or(() => /* parseSymbolValue */(state, input)).or(() => /* parseInvokable */(state, input, depth)).or(() => /* parseOperation */(state, input, depth, "+")).or(() => /* parseOperation */(state, input, depth, "-")).or(() => /* parseDigits */(state, input)).or(() => /* parseNot */(state, input, depth)).or(() => /* parseMethodReference */(state, input, depth)).orElseGet(() => new Tuple2Impl</* CompileState */, /* Value */>(state, new Placeholder(input)));
	}
	/* private static */ parseMethodReference(state : /* CompileState */, input : string, depth : number) : Option<Tuple2</* CompileState */, /* Value */>> {
		return /* last */(input, "::", (s, s2) => {
			let tuple = parseValue(state, s, depth);
			return new Some(new Tuple2Impl(tuple.left(), new DataAccess(tuple.right(), s2, /* Primitive */.Unknown)));
		});
	}
	/* private static */ parseNot(state : /* CompileState */, input : string, depth : number) : Option<Tuple2</* CompileState */, /* Value */>> {
		let stripped = input.strip();
		/* if (stripped.startsWith("!"))  */{
			let slice = stripped.substring(1);
			let tuple = parseValue(state, /* slice */, depth);
			let value = tuple.right();
			return new Some(new Tuple2Impl(tuple.left(), new Not(value)));
		}
		return new None();
	}
	/* private static */ parseLambda(state : /* CompileState */, input : string, depth : number) : Option<Tuple2</* CompileState */, /* Value */>> {
		return /* first */(input, "->", (beforeArrow, valueString) => {
			let strippedBeforeArrow = beforeArrow.strip();
			/* if (isSymbol(strippedBeforeArrow))  */{
				let type : /* Type */ = /* Primitive */.Unknown;
				/* if (state.typeRegister instanceof Some(var expectedType))  */{
					/* if (expectedType instanceof FunctionType functionType)  */{
						let /* type  */ = /* functionType */.arguments.get(0).orElse(/* null */);
					}
				}
				return /* assembleLambda */(state, /* Lists */.of(new Definition(/* strippedBeforeArrow */, type)), valueString, depth);
			}
			/* if (strippedBeforeArrow.startsWith("(") && strippedBeforeArrow.endsWith(")"))  */{
				let parameterNames = divideAll(/* strippedBeforeArrow */.substring(1, /* strippedBeforeArrow */.length() - 1), /* Main */.foldValueChar).iterate().map(/* String */.strip).filter((value : T) => !value.isEmpty()).map((name : T) => new Definition(name, /* Primitive */.Unknown)).collect(new ListCollector());
				return /* assembleLambda */(state, /* parameterNames */, valueString, depth);
			}
			return new None();
		});
	}
	/* private static */ assembleLambda(state : /* CompileState */, definitions : List<Definition>, valueString : string, depth : number) : Some<Tuple2</* CompileState */, /* Value */>> {
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
	/* private static */ parseDigits(state : /* CompileState */, input : string) : Option<Tuple2</* CompileState */, /* Value */>> {
		let stripped = input.strip();
		/* if (isNumber(stripped))  */{
			return new Some(new Tuple2Impl</* CompileState */, /* Value */>(state, new SymbolValue(stripped, /* Primitive */.Int)));
		}
		return new None();
	}
	/* private static */ isNumber(input : string) : boolean {
		/* for (var i = 0; i < input.length(); i++)  */{
			let c = input.charAt(/* i */);
			/* if (Character.isDigit(c))  */{
				/* continue */;
			}
			return /* false */;
		}
		return /* true */;
	}
	/* private static */ parseInvokable(state : /* CompileState */, input : string, depth : number) : Option<Tuple2</* CompileState */, /* Value */>> {
		return suffix(input.strip(), ")", (withoutEnd) => {
			return /* split */(() => /* toLast */(withoutEnd, "", /* Main */.foldInvocationStart), (callerWithEnd, argumentsString) => {
				return suffix(callerWithEnd, "(", (callerString) => {
					return /* assembleInvokable */(state, depth, argumentsString, callerString.strip());
				});
			});
		});
	}
	/* private static */ assembleInvokable(state : /* CompileState */, depth : number, argumentsString : string, callerString : string) : Some<Tuple2</* CompileState */, /* Value */>> {
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
	/* private static */ retainValue(argument : Argument) : Option</* Value */> {
		/* if (argument instanceof Value value)  */{
			return new Some(value);
		}
		return new None();
	}
	/* private static */ parseArgument(state : /* CompileState */, element : string, depth : number) : Tuple2</* CompileState */, Argument> {
		/* if (element.isEmpty())  */{
			return new Tuple2Impl(state, new Whitespace());
		}
		let tuple = parseValue(state, element, depth);
		return new Tuple2Impl(tuple.left(), tuple.right());
	}
	/* private static */ findCallerType(newCaller : Caller) : FunctionType {
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
	/* private static */ modifyCaller(state : /* CompileState */, oldCaller : Caller) : Caller {
		/* if (oldCaller instanceof DataAccess access)  */{
			let type = /* resolveType */(/* access */.parent, state);
			/* if (type instanceof FunctionType)  */{
				return /* access */.parent;
			}
		}
		return oldCaller;
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
	/* private static */ invocationHeader(state : /* CompileState */, depth : number, callerString1 : string) : Tuple2</* CompileState */, Caller> {
		/* if (callerString1.startsWith("new "))  */{
			let input1 : string = callerString1.substring("new ".length());
			let map = /* parseType */(state, input1).map((type) => {
				let right = type.right();
				return new Tuple2Impl</* CompileState */, Caller>(type.left(), new ConstructionCaller(right));
			});
			/* if (map.isPresent())  */{
				return map(/* null */);
			}
		}
		let tuple = parseValue(state, callerString1, depth);
		return new Tuple2Impl(tuple.left(), tuple.right());
	}
	/* private static */ foldInvocationStart(state : DivideState, c : /* char */) : DivideState {
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
	/* private static */ parseDataAccess(state : /* CompileState */, input : string, depth : number) : Option<Tuple2</* CompileState */, /* Value */>> {
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
			let type : /* Type */ = /* Primitive */.Unknown;
			/* if (parentType instanceof FindableType objectType)  */{
				/* if (objectType.find(property) instanceof Some(var memberType))  */{
					let /* type  */ = /* memberType */;
				}
			}
			return new Some(new Tuple2Impl(tuple.left(), new DataAccess(/* parent */, /* property */, type)));
		});
	}
	/* private static */ parseString(state : /* CompileState */, input : string) : Option<Tuple2</* CompileState */, /* Value */>> {
		let stripped = input.strip();
		/* if (stripped.startsWith("\"") && stripped.endsWith("\""))  */{
			return new Some(new Tuple2Impl(state, new StringValue(stripped)));
		}
		return new None();
	}
	/* private static */ parseSymbolValue(state : /* CompileState */, value : string) : Option<Tuple2</* CompileState */, /* Value */>> {
		let stripped = value.strip();
		/* if (isSymbol(stripped))  */{
			/* if (state.resolveValue(stripped) instanceof Some(var type))  */{
				return new Some(new Tuple2Impl(state, new SymbolValue(stripped, type)));
			}
			return new Some(new Tuple2Impl(state, new Placeholder(stripped)));
		}
		return new None();
	}
	/* private static */ parseOperation(state : /* CompileState */, value : string, depth : number, infix : string) : Option<Tuple2</* CompileState */, /* Value */>> {
		return /* first */(value, infix, (s, s2) => {
			let tuple = parseValue(state, s, depth);
			let tuple1 = parseValue(tuple.left(), s2, depth);
			let left = tuple.right();
			let right = /* tuple1 */.right();
			return new Some(new Tuple2Impl(/* tuple1 */.left(), new Operation(left, infix, right)));
		});
	}
	/* private static */ compileValues(state : /* CompileState */, params : string, mapper : (arg0 : /* CompileState */, arg1 : string) => Tuple2</* CompileState */, string>) : Tuple2</* CompileState */, string> {
		let parsed = /* parseValuesOrEmpty */(state, params, (state1, s) => new Some(mapper(state1, s)));
		let generated = /* generateValues */(/* parsed */.right());
		return new Tuple2Impl(/* parsed */.left(), /* generated */);
	}
	/* private static */ generateValues(elements : List<string>) : string {
		return generateAll(/* Main */.mergeValues, elements);
	}
	/* private static  */ parseValuesOrEmpty<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => Option<Tuple2</* CompileState */, T>>) : Tuple2</* CompileState */, List<T>> {
		return /* parseValues */(state, input, mapper).orElseGet(() => new Tuple2Impl(state, /* Lists */.empty()));
	}
	/* private static  */ parseValues<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : string) => Option<Tuple2</* CompileState */, T>>) : Option<Tuple2</* CompileState */, List<T>>> {
		return /* parseValuesWithIndices */(state, input, (state1, tuple) => mapper(state1, tuple.right()));
	}
	/* private static  */ parseValuesWithIndices<T>(state : /* CompileState */, input : string, mapper : (arg0 : /* CompileState */, arg1 : Tuple2<number, string>) => Option<Tuple2</* CompileState */, T>>) : Option<Tuple2</* CompileState */, List<T>>> {
		return parseAll(state, input, /* Main */.foldValueChar, mapper);
	}
	/* private static */ compileParameter(state : /* CompileState */, input : string) : Tuple2</* CompileState */, Parameter> {
		/* if (input.isBlank())  */{
			return new Tuple2Impl(state, new Whitespace());
		}
		return /* parseDefinition */(state, input).map((tuple) => new Tuple2Impl</* CompileState */, Parameter>(tuple.left(), tuple.right())).orElseGet(() => new Tuple2Impl(state, new Placeholder(input)));
	}
	/* private static */ compileDefinition(state : /* CompileState */, input : string) : Tuple2</* CompileState */, string> {
		return /* parseDefinition */(state, input).map((tuple) => new Tuple2Impl(tuple.left(), tuple.right().generate())).orElseGet(() => new Tuple2Impl(state, /* generatePlaceholder */(input)));
	}
	/* private static */ mergeValues(cache : /* StringBuilder */, element : string) : /* StringBuilder */ {
		/* if (cache.isEmpty())  */{
			return cache.append(element);
		}
		return cache.append(", ").append(element);
	}
	/* private static */ createIndent(depth : number) : string {
		return "\n" + "\t".repeat(depth);
	}
	/* private static */ compileDefinitionStatement(input : string, depth : number, state : /* CompileState */) : Option<Tuple2</* CompileState */, string>> {
		return suffix(input.strip(), ";", (withoutEnd) => {
			return /* parseDefinition */(state, withoutEnd).map((result) => {
				let generated = createIndent(depth) + result.right().generate() + ";";
				return new Tuple2Impl(result.left(), /* generated */);
			});
		});
	}
	/* private static */ parseDefinition(state : /* CompileState */, input : string) : Option<Tuple2</* CompileState */, Definition>> {
		return /* last */(input.strip(), " ", (beforeName, name) => {
			return /* split */(() => /* toLast */(beforeName, " ", /* Main */.foldTypeSeparator), (beforeType, type) => {
				return suffix(beforeType.strip(), ">", (withoutTypeParamStart) => {
					return /* first */(withoutTypeParamStart, "<", (beforeTypeParams, typeParamsString) => {
						let /* final */ compileStateStringTupleBiFunction : (arg0 : /* CompileState */, arg1 : string) => Tuple2</* CompileState */, string> = (state1, s) => new Tuple2Impl(state1, s.strip());
						let typeParams = parseValuesOrEmpty(state, typeParamsString, (state1, s) => new Some(/* compileStateStringTupleBiFunction */.apply(state1, s)));
						return /* assembleDefinition */(typeParams.left(), new Some<string>(beforeTypeParams), name, typeParams.right(), type);
					});
				}).or(() => {
					return /* assembleDefinition */(state, new Some<string>(beforeType), name, /* Lists */.empty(), type);
				});
			}).or(() => {
				return /* assembleDefinition */(state, new None<string>(), name, /* Lists */.empty(), beforeName);
			});
		});
	}
	/* private static */ toLast(input : string, separator : string, folder : (arg0 : DivideState, arg1 : /* Character */) => DivideState) : Option<Tuple2<string, string>> {
		let divisions = divideAll(input, folder);
		return /* divisions */.removeLast().map((removed) => {
			let left = removed.left().iterate().collect(new Joiner(separator)).orElse("");
			let right = removed.right();
			return new Tuple2Impl(left, right);
		});
	}
	/* private static */ foldTypeSeparator(state : DivideState, c : /* Character */) : DivideState {
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
	/* private static */ assembleDefinition(state : /* CompileState */, beforeTypeParams : Option<string>, name : string, typeParams : List<string>, type : string) : Option<Tuple2</* CompileState */, Definition>> {
		return /* parseType */(state.withTypeParams(typeParams), type).map((type1) => {
			let node = new Definition(beforeTypeParams, name.strip(), type1.right(), typeParams);
			return new Tuple2Impl(type1.left(), /* node */);
		});
	}
	/* private static */ foldValueChar(state : DivideState, c : /* char */) : DivideState {
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
	/* private static */ compileType(state : /* CompileState */, input : string) : Option<Tuple2</* CompileState */, string>> {
		return /* parseType */(state, input).map((tuple) => new Tuple2Impl(tuple.left(), tuple.right().generate()));
	}
	/* private static */ parseType(state : /* CompileState */, input : string) : Option<Tuple2</* CompileState */, /* Type */>> {
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
	/* private static */ varArgs(state : /* CompileState */, input : string) : Option<Tuple2</* CompileState */, /* Type */>> {
		return suffix(input, "...", (s) => {
			return parseType(state, s).map((inner : Tuple2</* CompileState */, /* Type */>) => {
				let newState = inner.left();
				let child = inner.right();
				return new Tuple2Impl(/* newState */, new ArrayType(/* child */));
			});
		});
	}
	/* private static */ parseTemplate(state : /* CompileState */, input : string) : Option<Tuple2</* CompileState */, /* Type */>> {
		return suffix(input.strip(), ">", (withoutEnd) => {
			return /* first */(withoutEnd, "<", (base, argumentsString) => {
				let strippedBase = base.strip();
				return parseValues(state, argumentsString, /* Main */.argument).map((argumentsTuple : Tuple2</* CompileState */, List<T>>) => {
					return /* assembleTemplate */(/* strippedBase */, argumentsTuple.left(), argumentsTuple.right());
				});
			});
		});
	}
	/* private static */ assembleTemplate(base : string, state : /* CompileState */, arguments : List<Argument>) : Tuple2</* CompileState */, /* Type */> {
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
		/* if (base.equals("Tuple") && children.size() >= 2)  */{
			return new Tuple2Impl(state, new TupleType(/* children */));
		}
		/* if (state.resolveType(base) instanceof Some(var baseType) && baseType instanceof FindableType findableType)  */{
			return new Tuple2Impl(state, new Template(/* findableType */, /* children */));
		}
		return new Tuple2Impl(state, new Template(new Placeholder(base), /* children */));
	}
	/* private static */ retainType(argument : Argument) : Option</* Type */> {
		/* if (argument instanceof Type type)  */{
			return new Some(type);
		}
		/* else  */{
			return new None</* Type */>();
		}
	}
	/* private static */ argument(state : /* CompileState */, input : string) : Option<Tuple2</* CompileState */, Argument>> {
		/* if (input.isBlank())  */{
			return new Some(new Tuple2Impl(state, new Whitespace()));
		}
		return parseType(state, input).map((tuple : Tuple2</* CompileState */, /* Type */>) => new Tuple2Impl(tuple.left(), tuple.right()));
	}
	/* private static  */ last<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix(input, infix, /* Main */.findLast, mapper);
	}
	/* private static */ findLast(input : string, infix : string) : Option<number> {
		let index = input.lastIndexOf(infix);
		/* if (index == -1)  */{
			return new None<number>();
		}
		return new Some(index);
	}
	/* private static  */ first<T>(input : string, infix : string, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return infix(input, infix, /* Main */.findFirst, mapper);
	}
	/* private static  */ infix<T>(input : string, infix : string, locator : (arg0 : string, arg1 : string) => Option<number>, mapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return /* split */(() => locator(input, infix).map((index : number) => {
			let left = input.substring(0, index);
			let right = input.substring(index + infix.length());
			return new Tuple2Impl(left, right);
		}), mapper);
	}
	/* private static  */ split<T>(splitter : () => Option<Tuple2<string, string>>, splitMapper : (arg0 : string, arg1 : string) => Option<T>) : Option<T> {
		return splitter().flatMap((splitTuple : Tuple2<string, string>) => splitMapper(splitTuple.left(), splitTuple.right()));
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
	}
	/* private static */ createDebugString(type : /* Type */) : string {
		/* if (!Main.isDebug)  */{
			return "";
		}
		return generatePlaceholder(": " + type.generate());
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
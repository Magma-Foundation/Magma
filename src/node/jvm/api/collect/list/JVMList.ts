/*[
	AccessNode, 
	Actual, 
	Argument, 
	ArrayType, 
	BooleanType, 
	Caller, 
	Characters, 
	Collector, 
	CompileState, 
	Console, 
	ConstructionCaller, 
	ConstructorHeader, 
	Definition, 
	DivideState, 
	EmptyHead, 
	Err, 
	Files, 
	FlatMapHead, 
	FunctionHeader, 
	FunctionSegment, 
	FunctionType, 
	Head, 
	HeadedQuery, 
	IOError, 
	ImmutableCompileState, 
	Import, 
	IncompleteRoot, 
	IncompleteRootSegment, 
	InvokableNode, 
	Joiner, 
	LambdaNode, 
	List, 
	ListCollector, 
	Lists, 
	Location, 
	Main, 
	MapHead, 
	Namespace, 
	None, 
	NotNode, 
	Ok, 
	OperationNode, 
	Option, 
	Parameter, 
	Path, 
	Placeholder, 
	Platform, 
	PrimitiveType, 
	Queries, 
	Query, 
	RangeHead, 
	Result, 
	SingleHead, 
	SliceType, 
	Some, 
	Source, 
	StringNode, 
	Strings, 
	SymbolNode, 
	TemplateType, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	Value, 
	VariadicType, 
	Whitespace, 
	ZipHead
]*/
import { List } from "../../../../magma/api/collect/list/List";
import { Path } from "../../../magma/api/io/Path";
import { Head } from "../../../../magma/api/collect/head/Head";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
import { Query } from "../../../../magma/api/collect/Query";
import { Collector } from "../../../../magma/api/collect/Collector";
import { MapHead } from "../../../../magma/api/collect/head/MapHead";
import { FlatMapHead } from "../../../../magma/api/collect/head/FlatMapHead";
import { EmptyHead } from "../../../../magma/api/collect/head/EmptyHead";
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { ZipHead } from "../../../../magma/api/collect/head/ZipHead";
import { Result } from "../../../../magma/api/result/Result";
import { Ok } from "../../../../magma/api/result/Ok";
import { SingleHead } from "../../../../magma/api/collect/head/SingleHead";
import { Some } from "../../../../magma/api/option/Some";
import { Lists } from "../../../../jvm/api/collect/list/Lists";
import { HeadedQuery } from "../../../magma/api/collect/head/HeadedQuery";
import { IOError } from "../../../magma/api/io/IOError";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
import { Platform } from "../../../magma/app/io/Platform";
import { Location } from "../../../magma/app/io/Location";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { Type } from "../../../magma/api/Type";
import { Source } from "../../../magma/app/io/Source";
import { Import } from "../../../magma/app/compile/Import";
import { Joiner } from "../../../../magma/api/collect/Joiner";
import { Strings } from "../../../../jvm/api/text/Strings";
import { FunctionHeader } from "../../../magma/app/compile/define/FunctionHeader";
import { CompileState } from "../../../magma/app/compile/CompileState";
import { Main } from "../../../../magma/app/Main";
import { Value } from "../../../../magma/app/compile/value/Value";
import { PrimitiveType } from "../../../../magma/app/compile/type/PrimitiveType";
import { Parameter } from "../../../../magma/app/compile/define/Parameter";
import { Caller } from "../../../../magma/app/compile/value/Caller";
import { ConstructorHeader } from "../../../../magma/app/compile/value/ConstructorHeader";
import { ListCollector } from "../../../magma/api/collect/list/ListCollector";
import { Files } from "../../jvm/api/io/Files";
import { Console } from "../../jvm/api/io/Console";
import { Queries } from "../../magma/api/collect/Queries";
import { ImmutableCompileState } from "../../magma/app/compile/ImmutableCompileState";
import { DivideState } from "../../magma/app/compile/text/DivideState";
import { FunctionSegment } from "../../magma/app/compile/FunctionSegment";
import { ConstructionCaller } from "../../magma/app/compile/value/ConstructionCaller";
import { Argument } from "../../magma/app/compile/value/Argument";
import { InvokableNode } from "../../magma/app/compile/value/InvokableNode";
import { StringNode } from "../../magma/app/compile/value/StringNode";
import { NotNode } from "../../magma/app/compile/value/NotNode";
import { LambdaNode } from "../../magma/app/compile/value/LambdaNode";
import { SymbolNode } from "../../magma/app/compile/value/SymbolNode";
import { AccessNode } from "../../magma/app/compile/value/AccessNode";
import { OperationNode } from "../../magma/app/compile/value/OperationNode";
import { RangeHead } from "../../magma/api/collect/head/RangeHead";
import { Characters } from "../../jvm/api/text/Characters";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { Placeholder } from "../../magma/app/compile/text/Placeholder";
import { ArrayType } from "../../magma/app/compile/type/ArrayType";
import { VariadicType } from "../../magma/app/compile/type/VariadicType";
import { SliceType } from "../../magma/app/compile/type/SliceType";
import { BooleanType } from "../../magma/app/compile/type/BooleanType";
import { TemplateType } from "../../magma/app/compile/type/TemplateType";
import { FunctionType } from "../../magma/app/compile/type/FunctionType";
export interface ListsInstance {
	static fromArray<T>(elements: T[]): List<T>;
	static empty<T>(): List<T>;
	static of<T>(...elements: T[]): List<T>;
}
export interface ConsoleInstance {
	static printErrLn(message: string): void;
}
export interface FilesInstance {
	static get(first: string, ...more: string[]): Path;
}
export interface CharactersInstance {
	static isDigit(c: string): boolean;
	static isLetter(c: string): boolean;
}
export interface StringsInstance {
	static length(stripped: string): number;
	static sliceBetween(input: string, startInclusive: number, endExclusive: number): string;
	static sliceFrom(input: string, startInclusive: number): string;
	static isEmpty(cache: string): boolean;
	static equalsTo(left: string, right: string): boolean;
	static strip(input: string): string;
	static isBlank(value: string): boolean;
	static charAt(input: string, index: number): string;
}
export interface Actual {
}
export interface Namespace {
}
export interface Collector<T, C> {
	createInitial(): C;
	fold(current: C, element: T): C;
}
export class EmptyHead<T> implements Head<T> {
	next(): Option<T> {
		return new None<T>(/*auto*/);
	}
}
export class FlatMapHead<T, R> implements Head<R> {
	mapper: (arg0 : T) => Query<R>;
	head: Head<T>;
	current: Query<R>;
	constructor (head: Head<T>, initial: Query<R>, mapper: (arg0 : T) => Query<R>) {
		this/*auto*/.head = head/*Head<T>*/;
		this/*auto*/.current = initial/*Query<R>*/;
		this/*auto*/.mapper = mapper/*(arg0 : T) => Query<R>*/;
	}
	next(): Option<R> {
		while (true/*auto*/) {
			let next = this/*auto*/.current.next(/*auto*/);
			if (next/*auto*/.isPresent(/*auto*/)) {
				return next/*auto*/;
			}
			let tuple = this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper).toTuple(this/*auto*/.current);
			if (tuple/*auto*/.left(/*auto*/)) {
				this/*auto*/.current = tuple/*auto*/.right(/*auto*/);
			}
			else {
				return new None<R>(/*auto*/);
			}
		}
	}
}
export interface Head<T> {
	next(): Option<T>;
}
export class HeadedQuery<T> implements Query<T> {
	head: Head<T>;
	constructor (head: Head<T>) {
		this.head = head;
	}
	next(): Option<T> {
		return this/*auto*/.head.next(/*auto*/);
	}
	collect<C>(collector: Collector<T, C>): C {
		return this/*auto*/.foldWithInitial(collector/*Collector<T, C>*/.createInitial(/*auto*/), (current: C, element: T) => collector/*Collector<T, C>*/.fold(current/*auto*/, element/*auto*/));
	}
	map<R>(mapper: (arg0 : T) => R): Query<R> {
		return new HeadedQuery<R>(new MapHead<T, R>(this/*auto*/.head, mapper/*(arg0 : T) => R*/));
	}
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R {
		let result = initial/*R*/;
		while (true/*auto*/) {
			let finalResult = result/*auto*/;
			let maybeNext = this/*auto*/.head.next(/*auto*/).map((inner: T) => folder/*(arg0 : R, arg1 : T) => R*/(finalResult/*auto*/, inner/*auto*/)).toTuple(finalResult/*auto*/);
			if (maybeNext/*auto*/.left(/*auto*/)) {
				result/*auto*/ = maybeNext/*auto*/.right(/*auto*/);
			}
			else {
				return result/*auto*/;
			}
		}
	}
	foldWithMapper<R>(next: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R> {
		return this/*auto*/.head.next(/*auto*/).map(next/*(arg0 : T) => R*/).map((maybeNext: R) => this/*auto*/.foldWithInitial(maybeNext/*auto*/, folder/*(arg0 : R, arg1 : T) => R*/));
	}
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R> {
		return this/*auto*/.head.next(/*auto*/).map(mapper/*(arg0 : T) => Query<R>*/).map((initial: Query<R>) => new HeadedQuery<R>(new FlatMapHead<T, R>(this/*auto*/.head, initial/*R*/, mapper/*(arg0 : T) => Query<R>*/))).orElseGet(() => new HeadedQuery<R>(new EmptyHead<R>(/*auto*/)));
	}
	allMatch(predicate: (arg0 : T) => boolean): boolean {
		return this/*auto*/.foldWithInitial(true/*auto*/, (maybeAllTrue: boolean, element: T) => maybeAllTrue/*auto*/ && predicate/*(arg0 : T) => boolean*/(element/*auto*/));
	}
	anyMatch(predicate: (arg0 : T) => boolean): boolean {
		return this/*auto*/.foldWithInitial(false/*auto*/, (aBoolean: boolean, t: T) => aBoolean/*auto*/ || predicate/*(arg0 : T) => boolean*/(t/*auto*/));
	}
	zip<R>(other: Query<R>): Query<Tuple2<T, R>> {
		return new HeadedQuery<Tuple2<T, R>>(new ZipHead<T, R>(this/*auto*/.head, other/*Query<R>*/));
	}
	foldWithInitialToResult<R, X>(initial: R, mapper: (arg0 : R, arg1 : T) => Result<R, X>): Result<R, X> {
		let initialResult: Result<R, X> = new Ok<R, X>(initial/*R*/);
		return this/*auto*/.foldWithInitial(initialResult/*auto*/, (currentResult: Result<R, X>, element: T) => currentResult/*auto*/.flatMapValue((current: R) => mapper/*(arg0 : R, arg1 : T) => Result<R, X>*/(current/*auto*/, element/*auto*/)));
	}
	filter(predicate: (arg0 : T) => boolean): Query<T> {
		return this/*auto*/.flatMap((element: T) => {
			if (predicate/*(arg0 : T) => boolean*/(element/*T*/)) {
				return new HeadedQuery<T>(new SingleHead<T>(element/*T*/));
			}
			else {
				return new HeadedQuery<T>(new EmptyHead<T>(/*auto*/));
			}
		});
	}
}
export class MapHead<T, R> implements Head<R> {
	head: Head<T>;
	mapper: (arg0 : T) => R;
	constructor (head: Head<T>, mapper: (arg0 : T) => R) {
		this.head = head;
		this.mapper = mapper;
	}
	next(): Option<R> {
		return this/*auto*/.head.next(/*auto*/).map(this/*auto*/.mapper);
	}
}
export class RangeHead implements Head<number> {
	length: number;
	counter: number;
	constructor (length: number) {
		this/*auto*/.length = length/*number*/;
		this/*auto*/.counter = 0/*auto*/;
	}
	next(): Option<number> {
		if (this/*auto*/.counter >= this/*auto*/.length) {
			return new None<number>(/*auto*/);
		}
		let value = this/*auto*/.counter;
		this/*auto*/.counter++;
		return new Some<number>(value/*auto*/);
	}
}
export class SingleHead<T> implements Head<T> {
	element: T;
	retrieved: boolean;
	constructor (element: T) {
		this/*auto*/.element = element/*T*/;
		this/*auto*/.retrieved = false/*auto*/;
	}
	next(): Option<T> {
		if (this/*auto*/.retrieved) {
			return new None<T>(/*auto*/);
		}
		this/*auto*/.retrieved = true/*auto*/;
		return new Some<T>(this/*auto*/.element);
	}
}
export class ZipHead<T, R> implements Head<Tuple2<T, R>> {
	head: Head<T>;
	other: Query<R>;
	constructor (head: Head<T>, other: Query<R>) {
		this.head = head;
		this.other = other;
	}
	next(): Option<Tuple2<T, R>> {
		return this/*auto*/.head.next(/*auto*/).and(() => this/*auto*/.other.next(/*auto*/));
	}
}
export class Joiner implements Collector<string, Option<string>> {
	delimiter: string;
	constructor (delimiter: string) {
		this.delimiter = delimiter;
	}
	static empty(): Joiner {
		return new Joiner("");
	}
	static joinOrEmpty(items: List<string>, delimiter: string, prefix: string, suffix: string): string {
		return items/*List<string>*/.query(/*auto*/).collect(new Joiner(delimiter/*string*/)).map((inner: string) => prefix/*string*/ + inner/*auto*/ + suffix/*string*/).orElse("");
	}
	createInitial(): Option<string> {
		return new None<string>(/*auto*/);
	}
	fold(maybe: Option<string>, element: string): Option<string> {
		return new Some<string>(maybe/*Option<string>*/.map((inner: string) => inner/*auto*/ + this/*auto*/.delimiter + element/*string*/).orElse(element/*string*/));
	}
}
export interface List<T> {
	addLast(element: T): List<T>;
	query(): Query<T>;
	size(): number;
	subList(startInclusive: number, endExclusive: number): Option<List<T>>;
	findLast(): Option<T>;
	findFirst(): Option<T>;
	find(index: number): Option<T>;
	queryWithIndices(): Query<Tuple2<number, T>>;
	addAll(others: List<T>): List<T>;
	contains(element: T, equator: (arg0 : T, arg1 : T) => boolean): boolean;
	queryReversed(): Query<T>;
	addFirst(element: T): List<T>;
	isEmpty(): boolean;
	equalsTo(other: List<T>, equator: (arg0 : T, arg1 : T) => boolean): boolean;
	removeValue(element: T, equator: (arg0 : T, arg1 : T) => boolean): List<T>;
	removeLast(): Option<List<T>>;
	sort(sorter: (arg0 : T, arg1 : T) => number): List<T>;
}
export class ListCollector<T> implements Collector<T, List<T>> {
	createInitial(): List<T> {
		return Lists/*auto*/.empty(/*auto*/);
	}
	fold(current: List<T>, element: T): List<T> {
		return current/*List<T>*/.addLast(element/*T*/);
	}
}
export class Queries {
	static fromOption<T>(option: Option<T>): Query<T> {
		return new HeadedQuery<T>(option/*Option<T>*/.map((element: T) => Queries/*auto*/.getTSingleHead(element/*T*/)).orElseGet(() => new EmptyHead<T>(/*auto*/)));
	}
	static getTSingleHead<T>(element: T): Head<T> {
		return new SingleHead<T>(element/*T*/);
	}
	static fromArray<T>(elements: T[]): Query<T> {
		/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
	}
}
export interface Query<T> {
	collect<C>(collector: Collector<T, C>): C;
	map<R>(mapper: (arg0 : T) => R): Query<R>;
	foldWithInitial<R>(initial: R, folder: (arg0 : R, arg1 : T) => R): R;
	foldWithMapper<R>(mapper: (arg0 : T) => R, folder: (arg0 : R, arg1 : T) => R): Option<R>;
	flatMap<R>(mapper: (arg0 : T) => Query<R>): Query<R>;
	next(): Option<T>;
	allMatch(predicate: (arg0 : T) => boolean): boolean;
	filter(predicate: (arg0 : T) => boolean): Query<T>;
	anyMatch(predicate: (arg0 : T) => boolean): boolean;
	zip<R>(other: Query<R>): Query<Tuple2<T, R>>;
	foldWithInitialToResult<R, X>(initial: R, mapper: (arg0 : R, arg1 : T) => Result<R, X>): Result<R, X>;
}
export interface IOError {
	display(): string;
}
export interface Path {
	asString(): string;
	writeString(output: string): Option<IOError>;
	readString(): Result<string, IOError>;
	walk(): Result<List<Path>, IOError>;
	findFileName(): string;
	endsWith(suffix: string): boolean;
	relativize(source: Path): Path;
	getParent(): Path;
	query(): Query<string>;
	resolveChildSegments(children: List<string>): Path;
	resolveChild(name: string): Path;
	exists(): boolean;
	createDirectories(): Option<IOError>;
}
export class None<T> implements Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new None<R>(/*auto*/);
	}
	orElse(other: T): T {
		return other/*T*/;
	}
	orElseGet(supplier: () => T): T {
		return supplier/*() => T*/(/*auto*/);
	}
	isPresent(): boolean {
		return false/*auto*/;
	}
	ifPresent(consumer: (arg0 : T) => void): void {
	}
	or(other: () => Option<T>): Option<T> {
		return other/*() => Option<T>*/(/*auto*/);
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return new None<R>(/*auto*/);
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		return new None<T>(/*auto*/);
	}
	toTuple(other: T): Tuple2<boolean, T> {
		return new Tuple2Impl<boolean, T>(false/*auto*/, other/*T*/);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return new None<Tuple2<T, R>>(/*auto*/);
	}
}
export interface Option<T> {
	map<R>(mapper: (arg0 : T) => R): Option<R>;
	orElse(other: T): T;
	orElseGet(supplier: () => T): T;
	isPresent(): boolean;
	ifPresent(consumer: (arg0 : T) => void): void;
	or(other: () => Option<T>): Option<T>;
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R>;
	filter(predicate: (arg0 : T) => boolean): Option<T>;
	toTuple(other: T): Tuple2<boolean, T>;
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>>;
}
export class Some<T> implements Option<T> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	map<R>(mapper: (arg0 : T) => R): Option<R> {
		return new Some<R>(mapper/*(arg0 : T) => R*/(this/*auto*/.value));
	}
	orElse(other: T): T {
		return this/*auto*/.value;
	}
	orElseGet(supplier: () => T): T {
		return this/*auto*/.value;
	}
	isPresent(): boolean {
		return true/*auto*/;
	}
	ifPresent(consumer: (arg0 : T) => void): void {
		consumer/*(arg0 : T) => void*/(this/*auto*/.value);
	}
	or(other: () => Option<T>): Option<T> {
		return this/*auto*/;
	}
	flatMap<R>(mapper: (arg0 : T) => Option<R>): Option<R> {
		return mapper/*(arg0 : T) => Option<R>*/(this/*auto*/.value);
	}
	filter(predicate: (arg0 : T) => boolean): Option<T> {
		if (predicate/*(arg0 : T) => boolean*/(this/*auto*/.value)) {
			return this/*auto*/;
		}
		return new None<T>(/*auto*/);
	}
	toTuple(other: T): Tuple2<boolean, T> {
		return new Tuple2Impl<boolean, T>(true/*auto*/, this/*auto*/.value);
	}
	and<R>(other: () => Option<R>): Option<Tuple2<T, R>> {
		return other/*() => Option<R>*/(/*auto*/).map((otherValue: R) => new Tuple2Impl<T, R>(this/*auto*/.value, otherValue/*auto*/));
	}
}
export class Err<T, X> implements Result<T, X> {
	error: X;
	constructor (error: X) {
		this.error = error;
	}
	findError(): Option<X> {
		return new Some<X>(this/*auto*/.error);
	}
	findValue(): Option<T> {
		return new None<T>(/*auto*/);
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenErr/*(arg0 : X) => R*/(this/*auto*/.error);
	}
	flatMapValue<R>(mapper: (arg0 : T) => Result<R, X>): Result<R, X> {
		return new Err<>(this/*auto*/.error);
	}
	mapValue<R>(mapper: (arg0 : T) => R): Result<R, X> {
		return new Err<>(this/*auto*/.error);
	}
}
export class Ok<T, X> implements Result<T, X> {
	value: T;
	constructor (value: T) {
		this.value = value;
	}
	findError(): Option<X> {
		return new None<X>(/*auto*/);
	}
	findValue(): Option<T> {
		return new Some<T>(this/*auto*/.value);
	}
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R {
		return whenOk/*(arg0 : T) => R*/(this/*auto*/.value);
	}
	flatMapValue<R>(mapper: (arg0 : T) => Result<R, X>): Result<R, X> {
		return mapper/*(arg0 : T) => Result<R, X>*/(this/*auto*/.value);
	}
	mapValue<R>(mapper: (arg0 : T) => R): Result<R, X> {
		return new Ok<>(mapper/*(arg0 : T) => R*/(this/*auto*/.value));
	}
}
export interface Result<T, X> {
	findError(): Option<X>;
	findValue(): Option<T>;
	match<R>(whenOk: (arg0 : T) => R, whenErr: (arg0 : X) => R): R;
	flatMapValue<R>(mapper: (arg0 : T) => Result<R, X>): Result<R, X>;
	mapValue<R>(mapper: (arg0 : T) => R): Result<R, X>;
}
export interface Tuple2<A, B> {
	left(): A;
	right(): B;
}
export class Tuple2Impl<A, B> implements Tuple2<A, B> {
	leftValue: A;
	rightValue: B;
	constructor (leftValue: A, rightValue: B) {
		this.leftValue = leftValue;
		this.rightValue = rightValue;
	}
	left(): A {
		return this/*auto*/.leftValue;
	}
	right(): B {
		return this/*auto*/.rightValue;
	}
}
export interface Type {
	generate(): string;
	isFunctional(): boolean;
	isVar(): boolean;
	generateBeforeName(): string;
}
export interface CompileState {
	functionName(): string;
	findLastStructureName(): Option<string>;
	createIndent(): string;
	isPlatform(platform: Platform): boolean;
	hasLastStructureNameOf(name: string): boolean;
	addResolvedImportFromCache(base: string): CompileState;
	addResolvedImportWithNamespace(namespace: List<string>, child: string): CompileState;
	withLocation(namespace: Location): CompileState;
	append(element: string): CompileState;
	pushStructureName(name: string): CompileState;
	enterDepth(): CompileState;
	exitDepth(): CompileState;
	defineAll(definitions: List<Definition>): CompileState;
	resolve(name: string): Option<Type>;
	clearImports(): CompileState;
	clearGenerated(): CompileState;
	addSource(source: Source): CompileState;
	findSource(name: string): Option<Source>;
	popStructureName(): CompileState;
	mapLocation(mapper: (arg0 : Location) => Location): CompileState;
	withPlatform(platform: Platform): CompileState;
	imports(): List<Import>;
	join(): string;
	findCurrentLocation(): Option<Location>;
	platform(): Platform;
	addFunction(function: string): CompileState;
	findDefinedTypes(): List<string>;
	defineType(name: string): CompileState;
	clearDefinedTypes(): CompileState;
}
export class Definition {
	annotations: List<string>;
	modifiers: List<string>;
	typeParams: List<string>;
	type: Type;
	name: string;
	constructor (annotations: List<string>, modifiers: List<string>, typeParams: List<string>, type: Type, name: string) {
		this.annotations = annotations;
		this.modifiers = modifiers;
		this.typeParams = typeParams;
		this.type = type;
		this.name = name;
	}
	constructor (type: Type, name: string) {
		this/*auto*/(Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), type/*Type*/, name/*string*/);
	}
	generate(platform: Platform): string {
		return this/*auto*/.generateWithAfterName(platform/*Platform*/, "");
	}
	asDefinition(): Option<Definition> {
		return new Some<Definition>(this/*auto*/);
	}
	generateWithAfterName(platform: Platform, afterName: string): string {
		let joinedTypeParams = this/*auto*/.joinTypeParams(/*auto*/);
		let joinedModifiers = this/*auto*/.joinModifiers(/*auto*/);
		if (Platform/*auto*/.Windows === platform/*Platform*/) {
			return joinedModifiers/*auto*/ + this/*auto*/.type.generateBeforeName(/*auto*/) + this/*auto*/.type.generate(/*auto*/) + " " + this/*auto*/.name + afterName/*string*/;
		}
		return joinedModifiers/*auto*/ + this/*auto*/.type.generateBeforeName(/*auto*/) + this/*auto*/.name + joinedTypeParams/*auto*/ + afterName/*string*/ + this/*auto*/.generateType(/*auto*/);
	}
	joinModifiers(): string {
		return this/*auto*/.modifiers.query(/*auto*/).map((value: string) => value/*auto*/ + " ").collect(new Joiner("")).orElse("");
	}
	generateType(): string {
		if (this/*auto*/.type.isVar(/*auto*/)) {
			return "";
		}
		return ": " + this/*auto*/.type.generate(/*auto*/);
	}
	joinTypeParams(): string {
		return Joiner/*auto*/.joinOrEmpty(this/*auto*/.typeParams, ", ", "<", ">");
	}
	hasAnnotation(annotation: string): boolean {
		return this/*auto*/.annotations.contains(annotation/*string*/, Strings/*auto*/.equalsTo);
	}
	removeModifier(modifier: string): Definition {
		return new Definition(this/*auto*/.annotations, this/*auto*/.modifiers.removeValue(modifier/*string*/, Strings/*auto*/.equalsTo), this/*auto*/.typeParams, this/*auto*/.type, this/*auto*/.name);
	}
	addModifierLast(modifier: string): Definition {
		return new Definition(this/*auto*/.annotations, this/*auto*/.modifiers.addLast(modifier/*string*/), this/*auto*/.typeParams, this/*auto*/.type, this/*auto*/.name);
	}
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string {
		let joinedDefinitions = definitions/*List<Definition>*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*Platform*/)).collect(new Joiner(", ")).orElse("");
		return this/*auto*/.generateWithAfterName(platform/*Platform*/, "(" + joinedDefinitions/*auto*/ + ")");
	}
}
export interface FunctionHeader<S extends FunctionHeader<S>> {
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string;
	hasAnnotation(annotation: string): boolean;
	removeModifier(modifier: string): S;
	addModifierLast(modifier: string): S;
}
export interface Parameter {
	generate(platform: Platform): string;
	asDefinition(): Option<Definition>;
}
export class FunctionSegment<S extends FunctionHeader<S>> {
	header: FunctionHeader<S>;
	definitions: List<Definition>;
	maybeContent: Option<string>;
	constructor (header: FunctionHeader<S>, definitions: List<Definition>, maybeContent: Option<string>) {
		this.header = header;
		this.definitions = definitions;
		this.maybeContent = maybeContent;
	}
	generate(platform: Platform, indent: string): string {
		let content = this/*auto*/.maybeContent(/*auto*/).map((inner: string) => " {" + inner/*auto*/ + indent/*string*/ + "}").orElse(";");
		return indent/*string*/ + this/*auto*/.header.generateWithDefinitions(platform/*Platform*/, this/*auto*/.definitions(/*auto*/)) + content/*auto*/;
	}
}
export class ImmutableCompileState implements CompileState {
	platform: Platform;
	findCurrentLocation: Option<Location>;
	sources: List<Source>;
	imports: List<Import>;
	structureNames: List<string>;
	structures: string;
	functions: string;
	definitions: List<Definition>;
	depth: number;
	definedTypes: List<string>;
	constructor (platform: Platform, findCurrentLocation: Option<Location>, sources: List<Source>, imports: List<Import>, structureNames: List<string>, structures: string, functions: string, definitions: List<Definition>, depth: number, definedTypes: List<string>) {
		this.platform = platform;
		this.findCurrentLocation = findCurrentLocation;
		this.sources = sources;
		this.imports = imports;
		this.structureNames = structureNames;
		this.structures = structures;
		this.functions = functions;
		this.definitions = definitions;
		this.depth = depth;
		this.definedTypes = definedTypes;
	}
	static createInitial(): CompileState {
		return new ImmutableCompileState(Platform/*auto*/.Magma, new None<Location>(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), "", "", Lists/*auto*/.empty(/*auto*/), 0/*auto*/, Lists/*auto*/.empty(/*auto*/));
	}
	hasLastStructureNameOf(name: string): boolean {
		return this/*auto*/.structureNames.findLast(/*auto*/).filter((anObject: string) => Strings/*auto*/.equalsTo(name/*string*/, anObject/*auto*/)).isPresent(/*auto*/);
	}
	withLocation(namespace: Location): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, new Some<Location>(namespace/*Location*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	append(element: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures + element/*string*/, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	pushStructureName(name: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.addLast(name/*string*/), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	enterDepth(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth + 1/*auto*/, this/*auto*/.definedTypes);
	}
	exitDepth(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth - 1/*auto*/, this/*auto*/.definedTypes);
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions.addAll(definitions/*List<Definition>*/), this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	resolve(name: string): Option<Type> {
		return this/*auto*/.definitions.queryReversed(/*auto*/).filter((definition: Definition) => Strings/*auto*/.equalsTo(definition/*auto*/.name(/*auto*/), name/*string*/)).map((definition1: Definition) => definition1/*auto*/.type(/*auto*/)).next(/*auto*/);
	}
	clearImports(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, Lists/*auto*/.empty(/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	clearGenerated(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, "", "", this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	addSource(source: Source): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources.addLast(source/*Source*/), this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	addResolvedImportFromCache(base: string): CompileState {
		if (this/*auto*/.structureNames.query(/*auto*/).anyMatch((inner: string) => Strings/*auto*/.equalsTo(inner/*auto*/, base/*string*/))) {
			return this/*auto*/;
		}
		return this/*auto*/.findSource(base/*string*/).map((source: Source) => this/*auto*/.addResolvedImportWithNamespace(source/*Source*/.computeNamespace(/*auto*/), source/*Source*/.computeName(/*auto*/))).orElse(this/*auto*/);
	}
	popStructureName(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.removeLast(/*auto*/).orElse(this/*auto*/.structureNames), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	mapLocation(mapper: (arg0 : Location) => Location): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation.map(mapper/*(arg0 : Location) => Location*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	withPlatform(platform: Platform): CompileState {
		return new ImmutableCompileState(platform/*Platform*/, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	addFunction(function: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions + function/*string*/, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	findDefinedTypes(): List<string> {
		return this/*auto*/.definedTypes;
	}
	defineType(name: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes.addLast(name/*string*/));
	}
	clearDefinedTypes(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, Lists/*auto*/.empty(/*auto*/));
	}
	join(): string {
		return this/*auto*/.structures + this/*auto*/.functions;
	}
	addResolvedImportWithNamespace(oldParent: List<string>, child: string): CompileState {
		let namespace = this/*auto*/.findCurrentLocation.map((location: Location) => location/*auto*/.namespace(/*auto*/)).orElse(Lists/*auto*/.empty(/*auto*/));
		let newParent = oldParent/*List<string>*/;
		if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
			if (namespace/*Location*/.isEmpty(/*auto*/)) {
				newParent/*auto*/ = newParent/*auto*/.addFirst(".");
			}
			let i = 0/*auto*/;
			let size = namespace/*Location*/.size(/*auto*/);
			while (i/*auto*/ < size/*auto*/) {
				newParent/*auto*/ = newParent/*auto*/.addFirst("..");
				i/*auto*/++;
			}
		}
		if (this/*auto*/.imports.query(/*auto*/).filter((node: Import) => Strings/*auto*/.equalsTo(node/*auto*/.child(/*auto*/), child/*string*/)).next(/*auto*/).isPresent(/*auto*/)) {
			return this/*auto*/;
		}
		let importString = new Import(newParent/*auto*/, child/*string*/);
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports.addLast(importString/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	findSource(name: string): Option<Source> {
		return this/*auto*/.sources.query(/*auto*/).filter((source: Source) => Strings/*auto*/.equalsTo(source/*Source*/.computeName(/*auto*/), name/*string*/)).next(/*auto*/);
	}
	isPlatform(platform: Platform): boolean {
		return platform/*Platform*/ === this/*auto*/.platform(/*auto*/);
	}
	createIndent(): string {
		let indent = this/*auto*/.depth(/*auto*/);
		return "\n" + "\t".repeat(indent/*string*/);
	}
	functionName(): string {
		return "temp";
	}
	findLastStructureName(): Option<string> {
		return this/*auto*/.structureNames(/*auto*/).findLast(/*auto*/);
	}
}
export class Import {
	namespace: List<string>;
	child: string;
	constructor (namespace: List<string>, child: string) {
		this.namespace = namespace;
		this.child = child;
	}
	generate(platform: Platform): string {
		if (Platform/*auto*/.Magma === platform/*Platform*/) {
			let joinedNamespace = this/*auto*/.namespace.query(/*auto*/).collect(new Joiner(".")).orElse("");
			return "import " + joinedNamespace/*auto*/ + "." + this/*auto*/.child + ";\n";
		}
		let joinedNamespace = this/*auto*/.namespace.addLast(this/*auto*/.child).query(/*auto*/).collect(new Joiner("/")).orElse("");
		return "import { " + this/*auto*/.child + " } from \"" + joinedNamespace/*auto*/ + "\";\n";
	}
}
export class DivideState {
	segments: List<string>;
	buffer: string;
	depth: number;
	input: string;
	index: number;
	constructor (segments: List<string>, buffer: string, depth: number, input: string, index: number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	static createInitial(input: string): DivideState {
		return new DivideState(Lists/*auto*/.empty(/*auto*/), "", 0/*auto*/, input/*string*/, 0/*auto*/);
	}
	advance(): DivideState {
		return new DivideState(this/*auto*/.segments.addLast(this/*auto*/.buffer), "", this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index);
	}
	append(c: string): DivideState {
		return new DivideState(this/*auto*/.segments, this/*auto*/.buffer + c/*string*/, this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index);
	}
	isLevel(): boolean {
		return 0/*auto*/ === this/*auto*/.depth;
	}
	enter(): DivideState {
		return new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth + 1/*auto*/, this/*auto*/.input, this/*auto*/.index);
	}
	exit(): DivideState {
		return new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth - 1/*auto*/, this/*auto*/.input, this/*auto*/.index);
	}
	isShallow(): boolean {
		return 1/*auto*/ === this/*auto*/.depth;
	}
	pop(): Option<Tuple2<DivideState, string>> {
		if (this/*auto*/.index >= Strings/*auto*/.length(this/*auto*/.input)) {
			return new None<Tuple2<DivideState, string>>(/*auto*/);
		}
		let c = Strings/*auto*/.charAt(this/*auto*/.input, this/*auto*/.index);
		let nextState = new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index + 1/*auto*/);
		return new Some<Tuple2<DivideState, string>>(new Tuple2Impl<DivideState, string>(nextState/*auto*/, c/*string*/));
	}
	popAndAppendToTuple(): Option<Tuple2<DivideState, string>> {
		return this/*auto*/.pop(/*auto*/).map((inner: Tuple2<DivideState, string>) => new Tuple2Impl<DivideState, string>(inner/*auto*/.left(/*auto*/).append(inner/*auto*/.right(/*auto*/)), inner/*auto*/.right(/*auto*/)));
	}
	popAndAppendToOption(): Option<DivideState> {
		return this/*auto*/.popAndAppendToTuple(/*auto*/).map((tuple: Tuple2<DivideState, string>) => tuple/*auto*/.left(/*auto*/));
	}
	peek(): string {
		return Strings/*auto*/.charAt(this/*auto*/.input, this/*auto*/.index);
	}
	startsWith(slice: string): boolean {
		return Strings/*auto*/.sliceFrom(this/*auto*/.input, this/*auto*/.index).startsWith(slice/*string*/);
	}
}
export class Placeholder {
	input: string;
	constructor (input: string) {
		this.input = input;
	}
	generate(): string {
		return Main/*auto*/.generatePlaceholder(this/*auto*/.input);
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	generate(platform: Platform): string {
		return this/*auto*/.generate(/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>(/*auto*/);
	}
	toValue(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
export class Whitespace implements Parameter {
	generate(platform: Platform): string {
		return "";
	}
	asDefinition(): Option<Definition> {
		return new None<Definition>(/*auto*/);
	}
}
export class ArrayType implements Type {
	child: Type;
	constructor (child: Type) {
		this.child = child;
	}
	generate(): string {
		return this/*auto*/.child.generate(/*auto*/) + "[]";
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
export class BooleanType implements Type {
	platform: Platform;
	constructor (platform: Platform) {
		this.platform = platform;
	}
	generate(): string {
		if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
			return "boolean";
		}
		return "Bool";
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
export class FunctionType implements Type {
	args: List<string>;
	returns: string;
	constructor (args: List<string>, returns: string) {
		this.args = args;
		this.returns = returns;
	}
	generate(): string {
		let joinedArguments = this/*auto*/.args.queryWithIndices(/*auto*/).map((tuple: Tuple2<number, string>) => "arg" + tuple/*auto*/.left(/*auto*/) + " : " + tuple/*auto*/.right(/*auto*/)).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments/*auto*/ + ") => " + this/*auto*/.returns;
	}
	isFunctional(): boolean {
		return true/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
export class PrimitiveType implements Type {
	static String/*auto*/: PrimitiveType = new PrimitiveType("string");
	static Number/*auto*/: PrimitiveType = new PrimitiveType("number");
	static Var/*auto*/: PrimitiveType = new PrimitiveType("var");
	static Void/*auto*/: PrimitiveType = new PrimitiveType("void");
	static Auto/*auto*/: PrimitiveType = new PrimitiveType("auto");
	static I8/*auto*/: PrimitiveType = new PrimitiveType("I8");
	static I32/*auto*/: PrimitiveType = new PrimitiveType("I32");
	value: string;
	constructor (value: string) {
		this/*auto*/.value = value/*string*/;
	}
	generate(): string {
		return this/*auto*/.value;
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return PrimitiveType/*auto*/.Var === this/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
export class SliceType implements Type {
	type: Type;
	constructor (type: Type) {
		this.type = type;
	}
	generate(): string {
		return "&[" + this/*auto*/.type.generate(/*auto*/) + "]";
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
export class TemplateType implements Type {
	base: string;
	args: List<string>;
	constructor (base: string, args: List<string>) {
		this.base = base;
		this.args = args;
	}
	static generateValueStrings(values: List<string>): string {
		return Main/*auto*/.generateAll(values/*List<string>*/, TemplateType/*auto*/.mergeValues);
	}
	static mergeValues(cache: string, element: string): string {
		if (Strings/*auto*/.isEmpty(cache/*string*/)) {
			return cache/*string*/ + element/*string*/;
		}
		return cache/*string*/ + ", " + element/*string*/;
	}
	generate(): string {
		return this/*auto*/.base + "<" + TemplateType/*auto*/.generateValueStrings(this/*auto*/.args) + ">";
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}
export class VariadicType implements Type {
	type: Type;
	constructor (type: Type) {
		this.type = type;
	}
	generate(): string {
		return this/*auto*/.type.generate(/*auto*/) + "[]";
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "...";
	}
}
export class AccessNode implements Value {
	child: Value;
	property: string;
	constructor (child: Value, property: string) {
		this.child = child;
		this.property = property;
	}
	generate(platform: Platform): string {
		return this/*auto*/.child.generate(platform/*Platform*/) + "." + this/*auto*/.property;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new Some<Value>(this/*auto*/.child);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
export interface Argument {
	toValue(): Option<Value>;
}
export interface Caller {
	generate(platform: Platform): string;
	findChild(): Option<Value>;
}
export class ConstructionCaller implements Caller {
	right: string;
	platform: Platform;
	constructor (right: string, platform: Platform) {
		this.right = right;
		this.platform = platform;
	}
	generate(platform: Platform): string {
		if (Platform/*auto*/.Magma === this/*auto*/.platform) {
			return this/*auto*/.right;
		}
		return "new " + this/*auto*/.right;
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
}
export class ConstructorHeader implements FunctionHeader<ConstructorHeader> {
	generateWithAfterName(platform: Platform, afterName: string): string {
		return "constructor " + afterName/*string*/;
	}
	hasAnnotation(annotation: string): boolean {
		return false/*auto*/;
	}
	removeModifier(modifier: string): ConstructorHeader {
		return this/*auto*/;
	}
	addModifierLast(modifier: string): ConstructorHeader {
		return this/*auto*/;
	}
	generateWithDefinitions0(platform: Platform, definitions: string): string {
		return generateWithAfterName/*auto*/(platform/*Platform*/, "(" + definitions/*string*/ + ")");
	}
	generateWithDefinitions(platform: Platform, definitions: List<Definition>): string {
		let joinedDefinitions = definitions/*List<Definition>*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*Platform*/)).collect(new Joiner(", ")).orElse("");
		return this/*auto*/.generateWithDefinitions0(platform/*Platform*/, joinedDefinitions/*auto*/);
	}
}
export class InvokableNode implements Value {
	caller: Caller;
	args: List<Value>;
	constructor (caller: Caller, args: List<Value>) {
		this.caller = caller;
		this.args = args;
	}
	generate(platform: Platform): string {
		let joinedArguments = this/*auto*/.joinArgs(platform/*Platform*/);
		return this/*auto*/.caller.generate(platform/*Platform*/) + "(" + joinedArguments/*auto*/ + ")";
	}
	joinArgs(platform: Platform): string {
		return this/*auto*/.args.query(/*auto*/).map((value: Value) => value/*string*/.generate(platform/*Platform*/)).collect(new Joiner(", ")).orElse("");
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new Some<string>("\n\tstatic " + this/*auto*/.caller.generate(platform/*Platform*/) + ": " + structureName/*string*/ + " = new " + structureName/*string*/ + "(" + this/*auto*/.joinArgs(platform/*Platform*/) + ");");
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
export class LambdaNode implements Value {
	paramNames: List<Definition>;
	content: string;
	constructor (paramNames: List<Definition>, content: string) {
		this.paramNames = paramNames;
		this.content = content;
	}
	generate(platform: Platform): string {
		let joinedParamNames = this/*auto*/.paramNames.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*Platform*/)).collect(new Joiner(", ")).orElse("");
		return "(" + joinedParamNames/*auto*/ + ")" + " => " + this/*auto*/.content;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
export class NotNode implements Value {
	child: string;
	constructor (child: string) {
		this.child = child;
	}
	generate(platform: Platform): string {
		return this/*auto*/.child;
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
export class OperationNode implements Value {
	left: Value;
	targetInfix: string;
	right: Value;
	constructor (left: Value, targetInfix: string, right: Value) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
	generate(platform: Platform): string {
		return this/*auto*/.left.generate(platform/*Platform*/) + " " + this/*auto*/.targetInfix + " " + this/*auto*/.right.generate(platform/*Platform*/);
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
export class StringNode implements Value {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
	generate(platform: Platform): string {
		return "\"" + this/*auto*/.value + "\"";
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
	type(): Type {
		return PrimitiveType/*auto*/.Auto;
	}
}
export class SymbolNode {
	value: string;
	type: Type;
	constructor (value: string, type: Type) {
		this.value = value;
		this.type = type;
	}
	generate(platform: Platform): string {
		return this/*auto*/.value + Main/*auto*/.generatePlaceholder(type/*Type*/.generate(/*auto*/));
	}
	toValue(): Option<Value> {
		return new Some<Value>(this/*auto*/);
	}
	findChild(): Option<Value> {
		return new None<Value>(/*auto*/);
	}
	generate(): string {
		return this/*auto*/.value;
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
	generateAsEnumValue(structureName: string, platform: Platform): Option<string> {
		return new None<string>(/*auto*/);
	}
}
export interface Value extends Argument, Caller  {
	generateAsEnumValue(structureName: string, platform: Platform): Option<string>;
	type(): Type;
}
export class Location {
	namespace: List<string>;
	name: string;
	constructor (namespace: List<string>, name: string) {
		this.namespace = namespace;
		this.name = name;
	}
}
export class Platform {
	static TypeScript/*auto*/: Platform = new Platform("node", "ts");
	static Magma/*auto*/: Platform = new Platform("magma", "mgs");
	static Windows/*auto*/: Platform = new Platform("windows", "h", "c");
	root: string;
	extension: string[];
	constructor (root: string, ...extensions: string[]) {
		this/*auto*/.root = root/*string*/;
		this/*auto*/.extension = extensions/*string[]*/;
	}
}
export class Source {
	sourceDirectory: Path;
	source: Path;
	constructor (sourceDirectory: Path, source: Path) {
		this.sourceDirectory = sourceDirectory;
		this.source = source;
	}
	read(): Result<string, IOError> {
		return this/*auto*/.source.readString(/*auto*/);
	}
	computeName(): string {
		let fileName = this/*auto*/.source.findFileName(/*auto*/);
		let separator = fileName/*auto*/.lastIndexOf(".");
		return fileName/*auto*/.substring(0/*auto*/, separator/*auto*/);
	}
	computeNamespace(): List<string> {
		return this/*auto*/.sourceDirectory.relativize(this/*auto*/.source).getParent(/*auto*/).query(/*auto*/).collect(new ListCollector<string>(/*auto*/));
	}
	computeLocation(): Location {
		return new Location(this/*auto*/.computeNamespace(/*auto*/), this/*auto*/.computeName(/*auto*/));
	}
}
class IncompleteRoot {
	location: Location;
	outputsByExtensions: List<IncompleteRootSegment>;
	constructor (location: Location, outputsByExtensions: List<IncompleteRootSegment>) {
		this.location = location;
		this.outputsByExtensions = outputsByExtensions;
	}
}
class IncompleteRootSegment {
	value: string;
	constructor (value: string) {
		this.value = value;
	}
}
export class Main {
	static main(): void {
		let sourceDirectory = Files/*auto*/.get(".", "src", "java");
		sourceDirectory/*auto*/.walk(/*auto*/).match((children: List<Path>) => Main/*auto*/.runWithSources(Main/*auto*/.findSources(children/*auto*/, sourceDirectory/*auto*/)), (value: IOError) => new Some<IOError>(value/*string*/)).map((error: IOError) => error/*auto*/.display(/*auto*/)).ifPresent((displayed: string) => Console/*auto*/.printErrLn(displayed/*auto*/));
	}
	static runWithSources(sources: List<Source>): Option<IOError> {
		return Queries/*auto*/.fromArray(Platform/*auto*/.values(/*auto*/)).foldWithInitialToResult(Main/*auto*/.createInitialState(sources/*List<Source>*/), (current1: CompileState, platform: Platform) => Main/*auto*/.runWithPlatform(current1/*auto*/, platform/*Platform*/, sources/*List<Source>*/)).findError(/*auto*/);
	}
	static runWithPlatform(initial: CompileState, platform: Platform, sources: List<Source>): Result<CompileState, IOError> {
		/*final Tuple2<CompileState, List<IncompleteRoot>> compileStateListTuple2*/;
		compileStateListTuple2/*auto*/ = new Tuple2Impl<CompileState, List<IncompleteRoot>>(initial/*CompileState*/.clearDefinedTypes(/*auto*/), Lists/*auto*/.empty(/*auto*/));
		return sources/*List<Source>*/.query(/*auto*/).foldWithInitialToResult(compileStateListTuple2/*auto*/, (tuple: Tuple2<CompileState, List<IncompleteRoot>>, source: Source) => Main/*auto*/.foldWithInput(platform/*Platform*/, tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/), source/*Source*/)).flatMapValue((result: Tuple2<CompileState, List<IncompleteRoot>>) => Main/*auto*/.completeAll(platform/*Platform*/, result/*auto*/.left(/*auto*/), result/*auto*/.right(/*auto*/)));
	}
	static completeAll(platform: Platform, state: CompileState, incomplete: List<IncompleteRoot>): Result<CompileState, IOError> {
		return incomplete/*List<IncompleteRoot>*/.query(/*auto*/).foldWithInitialToResult(state/*CompileState*/, (current: CompileState, incompleteRoot: IncompleteRoot) => Main/*auto*/.complete(current/*List<T>*/, incompleteRoot/*auto*/, platform/*Platform*/));
	}
	static foldWithInput(platform: Platform, state: CompileState, incomplete: List<IncompleteRoot>, source: Source): Result<Tuple2<CompileState, List<IncompleteRoot>>, IOError> {
		return Main/*auto*/.runWithInput(state/*CompileState*/, platform/*Platform*/, source/*Source*/).mapValue((result: Tuple2<CompileState, IncompleteRoot>) => {
			return new Tuple2Impl<>(result/*Tuple2<CompileState, IncompleteRoot>*/.left(/*auto*/), incomplete/*List<IncompleteRoot>*/.addLast(result/*Tuple2<CompileState, IncompleteRoot>*/.right(/*auto*/)));
		});
	}
	static runWithInput(state: CompileState, platform: Platform, source: Source): Result<Tuple2<CompileState, IncompleteRoot>, IOError> {
		return source/*Source*/.read(/*auto*/).mapValue((input: string) => {
			return Main/*auto*/.prepareRoot(state/*CompileState*/, source/*Source*/, input/*string*/, platform/*Platform*/);
		});
	}
	static createInitialState(sources: List<Source>): CompileState {
		return sources/*List<Source>*/.query(/*auto*/).foldWithInitial(ImmutableCompileState/*auto*/.createInitial(/*auto*/), (state: CompileState, source: Source) => state/*CompileState*/.addSource(source/*Source*/));
	}
	static findSources(children: List<Path>, sourceDirectory: Path): List<Source> {
		return children/*List<Path>*/.query(/*auto*/).filter((source: Path) => source/*Source*/.endsWith(".java")).map((child: Path) => new Source(sourceDirectory/*Path*/, child/*string*/)).collect(new ListCollector<Source>(/*auto*/));
	}
	static prepareRoot(state: CompileState, source: Source, input: string, platform: Platform): Tuple2Impl<CompileState, IncompleteRoot> {
		let location = source/*Source*/.computeLocation(/*auto*/);
		let initialized = state/*CompileState*/.withPlatform(platform/*Platform*/).withLocation(location/*auto*/);
		return Main/*auto*/.getCompileStateIncompleteRootTuple2(input/*string*/, initialized/*auto*/, location/*auto*/);
	}
	static getCompileStateIncompleteRootTuple2(input: string, initialized: CompileState, location: Location): Tuple2Impl<CompileState, IncompleteRoot> {
		let statementsTuple = Main/*auto*/.parseAll(initialized/*CompileState*/, input/*string*/, Main/*auto*/.foldStatements, (state1: CompileState, s: string) => new Some<>(Main/*auto*/.parseRootSegment(state1/*auto*/, s/*auto*/))).orElse(new Tuple2Impl<>(initialized/*CompileState*/, Lists/*auto*/.empty(/*auto*/)));
		let statementsState = statementsTuple/*auto*/.left(/*auto*/);
		let statements = statementsTuple/*auto*/.right(/*auto*/);
		let incomplete = new IncompleteRoot(location/*Location*/, statements/*auto*/);
		return new Tuple2Impl<CompileState, IncompleteRoot>(statementsState/*auto*/, incomplete/*List<IncompleteRoot>*/);
	}
	static getStringStringHashMap(state: CompileState, segments: List<IncompleteRootSegment>): Tuple2<CompileState, Map<string, string>> {
		let location = state/*CompileState*/.findCurrentLocation(/*auto*/).orElse(new Location(Lists/*auto*/.empty(/*auto*/), ""));
		let entries = new HashMap<string, string>(/*auto*/);
		let namespace = location/*Location*/.namespace(/*auto*/);
		let name = location/*Location*/.name(/*auto*/);
		let joinedDefinedTypes = state/*CompileState*/.findDefinedTypes(/*auto*/).sort(String/*auto*/.compareTo).query(/*auto*/).map((value: string) => "\n\t" + value/*string*/).collect(new Joiner(", ")).orElse("");
		let debug = "/*[" + joinedDefinedTypes/*auto*/ + "\n]*/\n";
		let generatedMain = Main/*auto*/.createMain(name/*string*/);
		let imports = Main/*auto*/.generateOrFoldImports(state/*CompileState*/);
		let joinedOutput = segments/*List<IncompleteRootSegment>*/.query(/*auto*/).map(IncompleteRootSegment/*auto*/.value).collect(new Joiner("")).orElse("");
		if (state/*CompileState*/.isPlatform(Platform/*auto*/.Windows)) {
			let value = /* namespace.query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + name*/;
			/*entries.put(Platform.Windows.extension[0], debug + Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"))*/;
			/*entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + name + ".h\"") + state.join() + joinedOutput + generatedMain)*/;
		}
		else {
			/*entries.put(state.platform().extension[0], debug + imports + state.join() + joinedOutput + generatedMain)*/;
		}
		return new Tuple2Impl<>(state/*CompileState*/.clearImports(/*auto*/).clearGenerated(/*auto*/), entries/*auto*/);
	}
	static complete(state: CompileState, incomplete: IncompleteRoot, platform: Platform): Result<CompileState, IOError> {
		let entries = Main/*auto*/.getStringStringHashMap(state/*CompileState*/, incomplete/*IncompleteRoot*/.outputsByExtensions(/*auto*/));
		/*return Main.writeOutputEntries(platform, incomplete.location(), entries.right())
                .<Result<CompileState, IOError>>map((IOError error) -> new Err<CompileState, IOError>(error))
                .orElseGet(() -> new Ok<>(entries.left()))*/;
	}
	static writeOutputEntries(platform: Platform, location: Location, outputsByExtensions: Map<string, string>): Option<IOError> {
		let initial: Option<IOError> = new None<IOError>(/*auto*/);
		let platformRoot = Files/*auto*/.get(".", "src", platform/*Platform*/.root);
		return Queries/*auto*/.fromArray(platform/*Platform*/.extension).foldWithInitial(initial/*CompileState*/, (maybeError0: Option<IOError>, extension: string) => maybeError0/*auto*/.or(() => Main/*auto*/.writeOutputEntryWithParent(platformRoot/*auto*/, extension/*auto*/, location/*Location*/, outputsByExtensions/*Map<string, string>*/)));
	}
	static writeOutputEntryWithParent(directory: Path, extension: string, location: Location, outputsByExtensions: Map<string, string>): Option<IOError> {
		return Main/*auto*/.ensureTargetParent(directory/*Path*/, location/*Location*/.namespace(/*auto*/)).match((targetParent: Path) => Main/*auto*/.writeOutputEntry(targetParent/*auto*/, location/*Location*/, outputsByExtensions/*Map<string, string>*/, extension/*string*/), Some/*auto*/.new);
	}
	static writeOutputEntry(targetParent: Path, location: Location, outputsByExtensions: Map<string, string>, extension: string): Option<IOError> {
		let target = targetParent/*Path*/.resolveChild(location/*Location*/.name(/*auto*/) + "." + extension/*string*/);
		return target/*auto*/.writeString(outputsByExtensions/*Map<string, string>*/.get(extension/*string*/));
	}
	static ensureTargetParent(directory: Path, namespace: List<string>): Result<Path, IOError> {
		let targetParent = directory/*Path*/.resolveChildSegments(namespace/*List<string>*/);
		if (targetParent/*Path*/.exists(/*auto*/)) {
			return new Ok<>(targetParent/*Path*/);
		}
		/*return targetParent.createDirectories()
                .<Result<Path, IOError>>map((IOError error) -> new Err<>(error))
                .orElseGet(() -> new Ok<>(targetParent))*/;
	}
	static generateOrFoldImports(state: CompileState): string {
		if (state/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
			return Main/*auto*/.foldImports(state/*CompileState*/);
		}
		return Main/*auto*/.generateImports(state/*CompileState*/);
	}
	static generateImports(state: CompileState): string {
		return state/*CompileState*/.imports(/*auto*/).query(/*auto*/).map((anImport: Import) => anImport/*auto*/.generate(state/*CompileState*/.platform(/*auto*/))).collect(new Joiner("")).orElse("");
	}
	static foldImports(statementsState: CompileState): string {
		return statementsState/*CompileState*/.imports(/*auto*/).query(/*auto*/).foldWithInitial(Lists/*auto*/.empty(/*auto*/), Main/*auto*/.foldImport).query(/*auto*/).foldWithInitial("", Main/*auto*/.generateEntry);
	}
	static generateEntry(current: string, entry: Tuple2<List<string>, List<string>>): string {
		let joinedNamespace = entry/*Tuple2<List<string>, List<string>>*/.left(/*auto*/).query(/*auto*/).collect(new Joiner(".")).orElse("");
		let joinedChildren = entry/*Tuple2<List<string>, List<string>>*/.right(/*auto*/).query(/*auto*/).collect(new Joiner(", ")).orElse("");
		return current/*string*/ + "import " + joinedNamespace/*auto*/ + ".{ " + joinedChildren/*auto*/ + " };\n";
	}
	static foldImport(current: List<Tuple2<List<string>, List<string>>>, anImport: Import): List<Tuple2<List<string>, List<string>>> {
		let namespace = anImport/*Import*/.namespace(/*auto*/);
		let child = anImport/*Import*/.child(/*auto*/);
		if (Main/*auto*/.hasNamespace(current/*List<Tuple2<List<string>, List<string>>>*/, namespace/*List<string>*/)) {
			return Main/*auto*/.attachChildToMapEntries(current/*List<Tuple2<List<string>, List<string>>>*/, namespace/*List<string>*/, child/*string*/);
		}
		else {
			return current/*List<Tuple2<List<string>, List<string>>>*/.addLast(new Tuple2Impl<>(namespace/*List<string>*/, Lists/*auto*/.of(child/*string*/)));
		}
	}
	static hasNamespace(map: List<Tuple2<List<string>, List<string>>>, namespace: List<string>): boolean {
		return map/*List<Tuple2<List<string>, List<string>>>*/.query(/*auto*/).map(Tuple2/*auto*/.left).anyMatch((stringList: List<string>) => namespace/*List<string>*/.equalsTo(stringList/*auto*/, String/*auto*/.equals));
	}
	static attachChildToMapEntries(map: List<Tuple2<List<string>, List<string>>>, namespace: List<string>, child: string): List<Tuple2<List<string>, List<string>>> {
		return map/*List<Tuple2<List<string>, List<string>>>*/.query(/*auto*/).map((tuple: Tuple2<List<string>, List<string>>) => Main/*auto*/.attachChildToMapEntry(namespace/*List<string>*/, child/*string*/, tuple/*auto*/)).collect(new ListCollector<>(/*auto*/));
	}
	static attachChildToMapEntry(namespace: List<string>, child: string, tuple: Tuple2<List<string>, List<string>>): Tuple2<List<string>, List<string>> {
		let entryNamespace = tuple/*Tuple2<List<string>, List<string>>*/.left(/*auto*/);
		let entryValues = tuple/*Tuple2<List<string>, List<string>>*/.right(/*auto*/);
		if (entryNamespace/*auto*/.equalsTo(namespace/*List<string>*/, String/*auto*/.equals)) {
			return new Tuple2Impl<>(entryNamespace/*auto*/, entryValues/*auto*/.addLast(child/*string*/));
		}
		else {
			return tuple/*Tuple2<List<string>, List<string>>*/;
		}
	}
	static generateDirective(content: string): string {
		return "#" + content/*string*/ + "\n";
	}
	static createMain(name: string): string {
		if (Strings/*auto*/.equalsTo(name/*string*/, "Main")) {
			return "Main.main();";
		}
		return "";
	}
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>): Tuple2<CompileState, string> {
		return Main/*auto*/.compileAll(state/*CompileState*/, input/*string*/, Main/*auto*/.foldStatements, mapper/*(arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>*/, Main/*auto*/.mergeStatements);
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple2<CompileState, string> {
		let folded = Main/*auto*/.parseAll(state/*CompileState*/, input/*string*/, folder/*(arg0 : DivideState, arg1 : string) => DivideState*/, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, string>>(mapper/*(arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>*/(state1/*auto*/, s/*auto*/))).orElse(new Tuple2Impl<CompileState, List<string>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/)));
		return new Tuple2Impl<CompileState, string>(folded/*auto*/.left(/*auto*/), Main/*auto*/.generateAll(folded/*auto*/.right(/*auto*/), merger/*(arg0 : string, arg1 : string) => string*/));
	}
	static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements/*List<string>*/.query(/*auto*/).foldWithInitial("", merger/*(arg0 : string, arg1 : string) => string*/);
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, biFunction: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main/*auto*/.divide(input/*string*/, folder/*(arg0 : DivideState, arg1 : string) => DivideState*/).query(/*auto*/).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/))), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: string) => maybeCurrent/*auto*/.flatMap((current: Tuple2<CompileState, List<T>>) => {
			let currentState = current/*Tuple2<CompileState, List<T>>*/.left(/*auto*/);
			let currentElement = current/*Tuple2<CompileState, List<T>>*/.right(/*auto*/);
			return biFunction/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>*/(currentState/*auto*/, segment/*auto*/).map((mappedTuple: Tuple2<CompileState, T>) => {
				let mappedState = mappedTuple/*Tuple2<CompileState, T>*/.left(/*auto*/);
				let mappedElement = mappedTuple/*Tuple2<CompileState, T>*/.right(/*auto*/);
				return new Tuple2Impl<CompileState, List<T>>(mappedState/*auto*/, currentElement/*auto*/.addLast(mappedElement/*auto*/));
			});
		}));
	}
	static mergeStatements(cache: string, element: string): string {
		return cache/*string*/ + element/*string*/;
	}
	static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current = DivideState/*auto*/.createInitial(input/*string*/);
		while (true/*auto*/) {
			let poppedTuple0 = current/*Tuple2<CompileState, List<T>>*/.pop(/*auto*/).toTuple(new Tuple2Impl<DivideState, string>(current/*Tuple2<CompileState, List<T>>*/, "\0"));
			if (!poppedTuple0/*auto*/.left(/*auto*/)) {
				break;
			}
			let poppedTuple = poppedTuple0/*auto*/.right(/*auto*/);
			let poppedState = poppedTuple/*auto*/.left(/*auto*/);
			let popped = poppedTuple/*auto*/.right(/*auto*/);
			current/*Tuple2<CompileState, List<T>>*/ = Main/*auto*/.foldSingleQuotes(poppedState/*auto*/, popped/*auto*/).or(() => Main/*auto*/.foldDoubleQuotes(poppedState/*auto*/, popped/*auto*/)).orElseGet(() => folder/*(arg0 : DivideState, arg1 : string) => DivideState*/(poppedState/*auto*/, popped/*auto*/));
		}
		return current/*Tuple2<CompileState, List<T>>*/.advance(/*auto*/).segments(/*auto*/);
	}
	static foldDoubleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\"" !== c/*string*/) {
			return new None<DivideState>(/*auto*/);
		}
		let appended = state/*DivideState*/.append(c/*string*/);
		while (true/*auto*/) {
			let maybeTuple = appended/*auto*/.popAndAppendToTuple(/*auto*/).toTuple(new Tuple2Impl<DivideState, string>(appended/*auto*/, "\0"));
			if (!maybeTuple/*auto*/.left(/*auto*/)) {
				break;
			}
			let tuple = maybeTuple/*auto*/.right(/*auto*/);
			appended/*auto*/ = tuple/*Tuple2<List<string>, List<string>>*/.left(/*auto*/);
			if ("\\" === tuple/*Tuple2<List<string>, List<string>>*/.right(/*auto*/)) {
				appended/*auto*/ = appended/*auto*/.popAndAppendToOption(/*auto*/).orElse(appended/*auto*/);
			}
			if ("\"" === tuple/*Tuple2<List<string>, List<string>>*/.right(/*auto*/)) {
				break;
			}
		}
		return new Some<DivideState>(appended/*auto*/);
	}
	static foldSingleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\'" !== c/*string*/) {
			return new None<DivideState>(/*auto*/);
		}
		return state/*DivideState*/.append(c/*string*/).popAndAppendToTuple(/*auto*/).flatMap(Main/*auto*/.foldEscaped).flatMap((state1: DivideState) => state1/*auto*/.popAndAppendToOption(/*auto*/));
	}
	static foldEscaped(tuple: Tuple2<DivideState, string>): Option<DivideState> {
		let state = tuple/*Tuple2<DivideState, string>*/.left(/*auto*/);
		let c = tuple/*Tuple2<DivideState, string>*/.right(/*auto*/);
		if ("\\" === c/*string*/) {
			return state/*DivideState*/.popAndAppendToOption(/*auto*/);
		}
		return new Some<DivideState>(state/*DivideState*/);
	}
	static foldStatements(state: DivideState, c: string): DivideState {
		let appended = state/*DivideState*/.append(c/*string*/);
		if (";" === c/*string*/ && appended/*auto*/.isLevel(/*auto*/)) {
			return appended/*auto*/.advance(/*auto*/);
		}
		if ("}" === c/*string*/ && appended/*auto*/.isShallow(/*auto*/)) {
			return appended/*auto*/.advance(/*auto*/).exit(/*auto*/);
		}
		if ("{" === c/*string*/ || "(" === c/*string*/) {
			return appended/*auto*/.enter(/*auto*/);
		}
		if ("}" === c/*string*/ || ")" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static parseRootSegment(state: CompileState, input: string): Tuple2<CompileState, IncompleteRootSegment> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, Lists/*auto*/.of(Main/*auto*/.typed(Main/*auto*/.compileWhitespace), Main/*auto*/.typed(Main/*auto*/.compileNamespaced), Main/*auto*/.typed(Main/*auto*/.createStructureRule("class ", "class ")), Main/*auto*/.typed(Main/*auto*/.createStructureRule("interface ", "interface ")), Main/*auto*/.typed(Main/*auto*/.createStructureRule("record ", "class ")), Main/*auto*/.typed(Main/*auto*/.createStructureRule("enum ", "class ")))).orElseGet(() => new Tuple2Impl<CompileState, IncompleteRootSegment>(state/*CompileState*/, new IncompleteRootSegment(Main/*auto*/.generatePlaceholder(input/*string*/))));
	}
	static typed(mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, IncompleteRootSegment>> {
		return (state: CompileState, s: string) => {
			let apply = mapper/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>*/(state/*CompileState*/, s/*string*/);
			return apply/*auto*/.map((result: Tuple2<CompileState, string>) => {
				return new Tuple2Impl<>(result/*Tuple2<CompileState, string>*/.left(/*auto*/), new IncompleteRootSegment(result/*Tuple2<CompileState, string>*/.right(/*auto*/)));
			});
		};
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state: CompileState, input1: string) => Main/*auto*/.compileFirst(input1/*auto*/, sourceInfix/*string*/, (beforeInfix: string, afterInfix: string) => Main/*auto*/.compileFirst(afterInfix/*auto*/, "{", (beforeContent: string, withEnd: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withEnd/*auto*/), "}", (inputContent: string) => Main/*auto*/.compileLast(beforeInfix/*auto*/, "\n", (s: string, s2: string) => {
			let annotations = Main/*auto*/.parseAnnotations(s/*string*/);
			if (annotations/*auto*/.contains("Actual", Strings/*auto*/.equalsTo)) {
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, ""));
			}
			return Main/*auto*/.compileStructureWithImplementing(state/*CompileState*/, annotations/*auto*/, Main/*auto*/.parseModifiers(s2/*string*/), targetInfix/*string*/, beforeContent/*auto*/, inputContent/*auto*/);
		}).or(() => {
			let modifiers = Main/*auto*/.parseModifiers(beforeContent/*auto*/);
			return Main/*auto*/.compileStructureWithImplementing(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), modifiers/*auto*/, targetInfix/*string*/, beforeContent/*auto*/, inputContent/*auto*/);
		}))));
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileLast(beforeContent/*string*/, " implements ", (s: string, s2: string) => Main/*auto*/.parseType(state/*CompileState*/, s2/*string*/).flatMap((implementingTuple: Tuple2<CompileState, Type>) => Main/*auto*/.compileStructureWithExtends(implementingTuple/*auto*/.left(/*auto*/), annotations/*List<string>*/, modifiers/*List<string>*/, targetInfix/*string*/, s/*string*/, new Some<Type>(implementingTuple/*auto*/.right(/*auto*/)), content/*string*/))).or(() => Main/*auto*/.compileStructureWithExtends(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, targetInfix/*string*/, beforeContent/*string*/, new None<Type>(/*auto*/), content/*string*/));
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(beforeContent/*string*/, " extends ", (beforeExtends: string, afterExtends: string) => Main/*auto*/.compileStructureWithParameters(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, targetInfix/*string*/, beforeExtends/*auto*/, new Some<string>(afterExtends/*auto*/), maybeImplementing/*Option<Type>*/, inputContent/*string*/)).or(() => Main/*auto*/.compileStructureWithParameters(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, targetInfix/*string*/, beforeContent/*string*/, new None<string>(/*auto*/), maybeImplementing/*Option<Type>*/, inputContent/*string*/));
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: Option<string>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(beforeContent/*string*/, "(", (rawName: string, withParameters: string) => Main/*auto*/.compileFirst(withParameters/*auto*/, ")", (parametersString: string, _: string) => {
			let name = Strings/*auto*/.strip(rawName/*auto*/);
			let parametersTuple = Main/*auto*/.parseParameters(state/*CompileState*/, parametersString/*string*/);
			let parameters = Main/*auto*/.retainDefinitionsFromParameters(parametersTuple/*auto*/.right(/*auto*/));
			return Main/*auto*/.compileStructureWithTypeParams(parametersTuple/*auto*/.left(/*auto*/), targetInfix/*string*/, inputContent/*string*/, name/*string*/, parameters/*auto*/, maybeImplementing/*Option<Type>*/, annotations/*List<string>*/, modifiers/*List<string>*/, maybeSuperType/*Option<string>*/);
		})).or(() => Main/*auto*/.compileStructureWithTypeParams(state/*CompileState*/, targetInfix/*string*/, inputContent/*string*/, beforeContent/*string*/, Lists/*auto*/.empty(/*auto*/), maybeImplementing/*Option<Type>*/, annotations/*List<string>*/, modifiers/*List<string>*/, maybeSuperType/*Option<string>*/));
	}
	static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters/*List<Parameter>*/.query(/*auto*/).map((parameter: Parameter) => parameter/*auto*/.asDefinition(/*auto*/)).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<Definition>(/*auto*/));
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeParams/*string*/), ">", (withoutTypeParamEnd: string) => Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", (name: string, typeParamsString: string) => {
			let typeParams = Main/*auto*/.divideValues(typeParamsString/*string*/);
			return Main/*auto*/.assembleStructure(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, infix/*string*/, name/*string*/, typeParams/*auto*/, parameters/*List<Definition>*/, maybeImplementing/*Option<Type>*/, content/*string*/, maybeSuperType/*Option<string>*/);
		})).or(() => Main/*auto*/.assembleStructure(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, infix/*string*/, beforeParams/*string*/, Lists/*auto*/.empty(/*auto*/), parameters/*List<Definition>*/, maybeImplementing/*Option<Type>*/, content/*string*/, maybeSuperType/*Option<string>*/));
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: List<string>, parameters: List<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		let name = Strings/*auto*/.strip(rawName/*string*/);
		if (!Main/*auto*/.isSymbol(name/*string*/)) {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
		let outputContentTuple = Main/*auto*/.compileStatements(state/*CompileState*/.pushStructureName(name/*string*/), content/*string*/, Main/*auto*/.compileClassSegment);
		let outputContentState = outputContentTuple/*auto*/.left(/*auto*/).popStructureName(/*auto*/);
		let outputContent = outputContentTuple/*auto*/.right(/*auto*/);
		let platform = outputContentState/*auto*/.platform(/*auto*/);
		let constructorString = Main/*auto*/.generateConstructorFromRecordParameters(parameters/*List<Definition>*/, platform/*Platform*/);
		let joinedTypeParams = Joiner/*auto*/.joinOrEmpty(typeParams/*List<string>*/, ", ", "<", ">");
		let implementingString = Main/*auto*/.generateImplementing(maybeImplementing/*Option<Type>*/);
		let newModifiers = Main/*auto*/.modifyModifiers0(oldModifiers/*List<string>*/);
		let joinedModifiers = newModifiers/*auto*/.query(/*auto*/).map((value: string) => value/*string*/ + " ").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
		return Main/*auto*/.getTuple2Some(outputContentState/*auto*/.defineType(name/*string*/), annotations/*List<string>*/, infix/*string*/, parameters/*List<Definition>*/, maybeSuperType/*Option<string>*/, name/*string*/, joinedModifiers/*auto*/, joinedTypeParams/*auto*/, implementingString/*auto*/, platform/*Platform*/, constructorString/*auto*/, outputContent/*auto*/);
	}
	static getTuple2Some(state: CompileState, annotations: List<string>, infix: string, parameters: List<Definition>, maybeSuperType: Option<string>, name: string, joinedModifiers: string, joinedTypeParams: string, implementingString: string, platform: Platform, constructorString: string, outputContent: string): Some<Tuple2<CompileState, string>> {
		if (annotations/*List<string>*/.contains("Namespace", Strings/*auto*/.equalsTo)) {
			let actualInfix = "interface ";
			let newName = name/*string*/ + "Instance";
			let generated = joinedModifiers/*string*/ + actualInfix/*auto*/ + newName/*auto*/ + joinedTypeParams/*string*/ + implementingString/*string*/ + " {" + Main/*auto*/.joinParameters(parameters/*List<Definition>*/, platform/*Platform*/) + constructorString/*string*/ + outputContent/*string*/ + "\n}\n";
			let withNewLocation = state/*CompileState*/.append(generated/*auto*/).mapLocation((location: Location) => new Location(location/*Location*/.namespace(/*auto*/), location/*Location*/.name(/*auto*/) + "Instance"));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(withNewLocation/*auto*/, ""));
		}
		else {
			let extendsString = maybeSuperType/*Option<string>*/.map((inner: string) => " extends " + inner/*auto*/).orElse("");
			let infix1 = Main/*auto*/.retainStruct(infix/*string*/, state/*CompileState*/);
			let generated = joinedModifiers/*string*/ + infix1/*auto*/ + name/*string*/ + joinedTypeParams/*string*/ + extendsString/*auto*/ + implementingString/*string*/ + " {" + Main/*auto*/.joinParameters(parameters/*List<Definition>*/, platform/*Platform*/) + constructorString/*string*/ + outputContent/*string*/ + "\n}\n";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/.append(generated/*auto*/), ""));
		}
	}
	static retainStruct(infix: string, outputContentState: CompileState): string {
		if (outputContentState/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
			return "struct ";
		}
		return infix/*string*/;
	}
	static modifyModifiers0(oldModifiers: List<string>): List<string> {
		if (oldModifiers/*List<string>*/.contains("public", Strings/*auto*/.equalsTo)) {
			return Lists/*auto*/.of("export");
		}
		return Lists/*auto*/.empty(/*auto*/);
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing/*Option<Type>*/.map((type: Type) => type/*Type*/.generate(/*auto*/)).map((inner: string) => " implements " + inner/*auto*/).orElse("");
	}
	static generateConstructorFromRecordParameters(parameters: List<Definition>, platform: Platform): string {
		return parameters/*List<Definition>*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*Platform*/)).collect(new Joiner(", ")).map((generatedParameters: string) => Main/*auto*/.generateConstructorWithParameterString(parameters/*List<Definition>*/, generatedParameters/*auto*/)).orElse("");
	}
	static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: string): string {
		let constructorAssignments = Main/*auto*/.generateConstructorAssignments(parameters/*List<Definition>*/);
		return "\n\tconstructor (" + parametersString/*string*/ + ") {" + constructorAssignments/*auto*/ + "\n\t}";
	}
	static generateConstructorAssignments(parameters: List<Definition>): string {
		return parameters/*List<Definition>*/.query(/*auto*/).map((definition: Definition) => "\n\t\tthis." + definition/*auto*/.name(/*auto*/) + " = " + definition/*auto*/.name(/*auto*/) + ";").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
	}
	static joinParameters(parameters: List<Definition>, platform: Platform): string {
		return parameters/*List<Definition>*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*Platform*/)).map((generated: string) => "\n\t" + generated/*auto*/ + ";").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (stripped/*auto*/.startsWith("package ") || stripped/*auto*/.startsWith("import ")) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, ""));
		}
		return new None<>(/*auto*/);
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, rules/*List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>*/).orElseGet(() => new Tuple2Impl<CompileState, string>(state/*CompileState*/, Main/*auto*/.generatePlaceholder(input/*string*/)));
	}
	static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules/*List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>*/.query(/*auto*/).map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => Main/*auto*/.getApply(state/*CompileState*/, input/*string*/, rule/*auto*/)).flatMap(Queries/*auto*/.fromOption).next(/*auto*/);
	}
	static getApply<T>(state: CompileState, input: string, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>*/(state/*CompileState*/, input/*string*/);
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state1/*CompileState*/, input1/*string*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.createStructureRule("class ", "class "), Main/*auto*/.createStructureRule("interface ", "interface "), Main/*auto*/.createStructureRule("record ", "class "), Main/*auto*/.createStructureRule("enum ", "class "), Main/*auto*/.compileMethod, Main/*auto*/.compileFieldDefinition));
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(input/*string*/, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams = Strings/*auto*/.strip(beforeParams/*string*/);
			return Main/*auto*/.compileLast(strippedBeforeParams/*auto*/, " ", (_: string, name: string) => {
				if (state/*CompileState*/.hasLastStructureNameOf(name/*string*/)) {
					return Main/*auto*/.compileMethodWithBeforeParams(state/*CompileState*/, new ConstructorHeader(/*auto*/), withParams/*string*/);
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			}).or(() => {
				if (state/*CompileState*/.hasLastStructureNameOf(strippedBeforeParams/*auto*/)) {
					return Main/*auto*/.compileMethodWithBeforeParams(state/*CompileState*/, new ConstructorHeader(/*auto*/), withParams/*string*/);
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			}).or(() => Main/*auto*/.parseDefinition(state/*CompileState*/, beforeParams/*string*/).flatMap((tuple: Tuple2<CompileState, Definition>) => Main/*auto*/.compileMethodWithBeforeParams(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), tuple/*Tuple2<DivideState, string>*/.right(/*auto*/), withParams/*string*/)));
		});
	}
	static compileMethodWithBeforeParams<S extends FunctionHeader<S>>(state: CompileState, header: FunctionHeader<S>, withParams: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(withParams/*string*/, ")", (params: string, afterParams: string) => {
			let parametersTuple = Main/*auto*/.parseParameters(state/*CompileState*/, params/*string*/);
			let parametersState = parametersTuple/*auto*/.left(/*auto*/);
			let parameters = parametersTuple/*auto*/.right(/*auto*/);
			let definitions = Main/*auto*/.retainDefinitionsFromParameters(parameters/*List<Definition>*/);
			let newHeader = Main/*auto*/.retainDef(header/*FunctionHeader<S>*/, parametersState/*auto*/);
			if (newHeader/*auto*/.hasAnnotation("Actual")) {
				let sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new None<>(/*auto*/));
				let generate = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), "\n\t");
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState/*auto*/, generate/*auto*/));
			}
			return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(afterParams/*string*/), "{", (withoutContentStart: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withoutContentStart/*auto*/), "}", (withoutContentEnd: string) => {
				let compileState1 = parametersState/*auto*/.enterDepth(/*auto*/);
				let compileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
				let statementsTuple = Main/*auto*/.compileFunctionStatements(compileState/*auto*/.defineAll(definitions/*List<Definition>*/), withoutContentEnd/*string*/);
				let compileState2 = statementsTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/);
				let indent = compileState2/*auto*/.createIndent(/*auto*/);
				let exited = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
				let sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new Some<>(statementsTuple/*auto*/.right(/*auto*/)));
				let generated = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), indent/*string*/);
				if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(exited/*auto*/.addFunction(generated/*auto*/), ""));
				}
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(exited/*auto*/, generated/*auto*/));
			})).or(() => {
				if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(afterParams/*string*/))) {
					let sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new None<>(/*auto*/));
					let generate = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), "\n\t");
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState/*auto*/, generate/*auto*/));
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			});
		});
	}
	static retainDef<S extends FunctionHeader<S>>(header: FunctionHeader<S>, parametersState: CompileState): FunctionHeader<S> {
		if (parametersState/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
			return header/*FunctionHeader<S>*/.addModifierLast("def").removeModifier("mut");
		}
		return header/*FunctionHeader<S>*/;
	}
	static parseParameters(state: CompileState, params: string): Tuple2<CompileState, List<Parameter>> {
		return Main/*auto*/.parseValuesOrEmpty(state/*CompileState*/, params/*string*/, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, Parameter>>(Main/*auto*/.parseParameterOrPlaceholder(state1/*CompileState*/, s/*string*/)));
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileStatements(state/*CompileState*/, input/*string*/, Main/*auto*/.compileFunctionSegment);
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state/*CompileState*/, input/*string*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.compileEmptySegment, Main/*auto*/.compileBlock, Main/*auto*/.compileFunctionStatement, Main/*auto*/.compileReturnWithoutSuffix));
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(input/*string*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, ";"));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileReturn(input1/*string*/, (withoutPrefix: string) => Main/*auto*/.compileValue(state1/*CompileState*/, withoutPrefix/*auto*/)).map((tuple: Tuple2<CompileState, string>) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), state1/*CompileState*/.createIndent(/*auto*/) + tuple/*Tuple2<DivideState, string>*/.right(/*auto*/)));
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), "}", (withoutEnd: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*auto*/, "", Main/*auto*/.foldBlockStarts), (beforeContentWithEnd: string, content: string) => Main/*auto*/.compileSuffix(beforeContentWithEnd/*auto*/, "{", (beforeContent: string) => Main/*auto*/.compileBlockHeader(state/*CompileState*/, beforeContent/*string*/).flatMap((headerTuple: Tuple2<CompileState, string>) => {
			let contentTuple = Main/*auto*/.compileFunctionStatements(headerTuple/*Tuple2<CompileState, string>*/.left(/*auto*/).enterDepth(/*auto*/), content/*string*/);
			let indent = state/*CompileState*/.createIndent(/*auto*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(contentTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/), indent/*string*/ + headerTuple/*Tuple2<CompileState, string>*/.right(/*auto*/) + "{" + contentTuple/*auto*/.right(/*auto*/) + indent/*string*/ + "}"));
		}))));
	}
	static foldBlockStarts(state: DivideState, c: string): DivideState {
		let appended = state/*DivideState*/.append(c/*string*/);
		if ("{" === c/*string*/) {
			let entered = appended/*auto*/.enter(/*auto*/);
			if (entered/*auto*/.isShallow(/*auto*/)) {
				return entered/*auto*/.advance(/*auto*/);
			}
			else {
				return entered/*auto*/;
			}
		}
		if ("}" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static compileBlockHeader(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, Lists/*auto*/.of(Main/*auto*/.createConditionalRule("if"), Main/*auto*/.createConditionalRule("while"), Main/*auto*/.compileElse));
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input1: string) => Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input1/*string*/), prefix/*string*/, (withoutPrefix: string) => {
			let strippedCondition = Strings/*auto*/.strip(withoutPrefix/*string*/);
			return Main/*auto*/.compilePrefix(strippedCondition/*auto*/, "(", (withoutConditionStart: string) => Main/*auto*/.compileSuffix(withoutConditionStart/*auto*/, ")", (withoutConditionEnd: string) => {
				let tuple = Main/*auto*/.compileValueOrPlaceholder(state1/*CompileState*/, withoutConditionEnd/*string*/);
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), prefix/*string*/ + " (" + tuple/*Tuple2<DivideState, string>*/.right(/*auto*/) + ") "));
			}));
		});
	}
	static compileElse(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo("else", Strings/*auto*/.strip(input/*string*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, "else "));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static compileFunctionStatement(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), ";", (withoutEnd: string) => {
			let valueTuple = Main/*auto*/.compileFunctionStatementValue(state/*CompileState*/, withoutEnd/*string*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple/*auto*/.left(/*auto*/), state/*CompileState*/.createIndent(/*auto*/) + valueTuple/*auto*/.right(/*auto*/) + ";"));
		});
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state/*CompileState*/, withoutEnd/*string*/, Lists/*auto*/.of(Main/*auto*/.compileReturnWithValue, Main/*auto*/.compileAssignment, (state1: CompileState, input: string) => Main/*auto*/.parseInvokable(state1/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), tuple/*Tuple2<DivideState, string>*/.right(/*auto*/).generate(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/).platform(/*auto*/)))), Main/*auto*/.createPostRule("++"), Main/*auto*/.createPostRule("--"), Main/*auto*/.compileBreak));
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo("break", Strings/*auto*/.strip(input/*string*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, "break"));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), suffix/*string*/, (child: string) => {
			let tuple = Main/*auto*/.compileValueOrPlaceholder(state1/*CompileState*/, child/*string*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), tuple/*Tuple2<DivideState, string>*/.right(/*auto*/) + suffix/*string*/));
		});
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileReturn(input/*string*/, (value1: string) => Main/*auto*/.compileValue(state/*CompileState*/, value1/*auto*/));
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*string*/), "return ", (value: string) => mapper/*(arg0 : string) => Option<Tuple2<CompileState, string>>*/(value/*string*/).flatMap((tuple: Tuple2<CompileState, string>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), "return " + tuple/*Tuple2<DivideState, string>*/.right(/*auto*/)))));
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), ")", (withoutEnd: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*string*/, "", Main/*auto*/.foldInvocationStarts), (callerWithArgStart: string, args: string) => Main/*auto*/.compileSuffix(callerWithArgStart/*auto*/, "(", (callerString: string) => Main/*auto*/.compilePrefix(Strings/*auto*/.strip(callerString/*auto*/), "new ", (type: string) => Main/*auto*/.compileType(state/*CompileState*/, type/*Type*/).flatMap((callerTuple: Tuple2<CompileState, string>) => {
			let callerState = callerTuple/*Tuple2<CompileState, string>*/.left(/*auto*/);
			let caller = callerTuple/*Tuple2<CompileState, string>*/.right(/*auto*/);
			return Main/*auto*/.assembleInvokable(callerState/*auto*/, new ConstructionCaller(caller/*auto*/, callerState/*auto*/.platform(/*auto*/)), args/*auto*/);
		})).or(() => Main/*auto*/.parseValue(state/*CompileState*/, callerString/*auto*/).flatMap((callerTuple: Tuple2<CompileState, Value>) => Main/*auto*/.assembleInvokable(callerTuple/*Tuple2<CompileState, string>*/.left(/*auto*/), callerTuple/*Tuple2<CompileState, string>*/.right(/*auto*/), args/*auto*/))))));
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return Main/*auto*/.splitFolded(input/*string*/, folder/*(arg0 : DivideState, arg1 : string) => DivideState*/, (divisions1: List<string>) => Main/*auto*/.selectLast(divisions1/*auto*/, delimiter/*string*/));
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple2<string, string>>): Option<Tuple2<string, string>> {
		let divisions = Main/*auto*/.divide(input/*string*/, folder/*(arg0 : DivideState, arg1 : string) => DivideState*/);
		if (2/*auto*/ > divisions/*auto*/.size(/*auto*/)) {
			return new None<Tuple2<string, string>>(/*auto*/);
		}
		return selector/*(arg0 : List<string>) => Option<Tuple2<string, string>>*/(divisions/*auto*/);
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let beforeLast = divisions/*List<string>*/.subList(0/*auto*/, divisions/*List<string>*/.size(/*auto*/) - 1/*auto*/).orElse(divisions/*List<string>*/);
		let last = divisions/*List<string>*/.findLast(/*auto*/).orElse("");
		let joined = beforeLast/*auto*/.query(/*auto*/).collect(new Joiner(delimiter/*string*/)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(joined/*auto*/, last/*auto*/));
	}
	static foldInvocationStarts(state: DivideState, c: string): DivideState {
		let appended = state/*DivideState*/.append(c/*string*/);
		if ("(" === c/*string*/) {
			let entered = appended/*auto*/.enter(/*auto*/);
			if (entered/*auto*/.isShallow(/*auto*/)) {
				return entered/*auto*/.advance(/*auto*/);
			}
			else {
				return entered/*auto*/;
			}
		}
		if (")" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.parseValues(state/*CompileState*/, argsString/*string*/, (state1: CompileState, s: string) => Main/*auto*/.parseArgument(state1/*CompileState*/, s/*string*/)).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState = argsTuple/*Tuple2<CompileState, List<Argument>>*/.left(/*auto*/);
			let args = Main/*auto*/.retain(argsTuple/*Tuple2<CompileState, List<Argument>>*/.right(/*auto*/), (argument: Argument) => argument/*auto*/.toValue(/*auto*/));
			let newCaller = Main/*auto*/.transformCaller(argsState/*auto*/, oldCaller/*Caller*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState/*auto*/, new InvokableNode(newCaller/*auto*/, args/*auto*/)));
		});
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller/*Caller*/.findChild(/*auto*/).flatMap((parent: Value) => {
			let parentType = parent/*Value*/.type(/*auto*/);
			if (parentType/*auto*/.isFunctional(/*auto*/)) {
				return new Some<Caller>(parent/*Value*/);
			}
			return new None<Caller>(/*auto*/);
		}).orElse(oldCaller/*Caller*/);
	}
	static retain<T, R>(args: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
		return args/*List<T>*/.query(/*auto*/).map(mapper/*(arg0 : T) => Option<R>*/).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<R>(/*auto*/));
	}
	static parseArgument(state1: CompileState, input: string): Option<Tuple2<CompileState, Argument>> {
		return Main/*auto*/.parseValue(state1/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), tuple/*Tuple2<DivideState, string>*/.right(/*auto*/)));
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(input/*string*/, "=", (destination: string, source: string) => {
			let sourceTuple = Main/*auto*/.compileValueOrPlaceholder(state/*CompileState*/, source/*string*/);
			let destinationTuple = Main/*auto*/.compileValue(sourceTuple/*auto*/.left(/*auto*/), destination/*string*/).or(() => Main/*auto*/.parseDefinition(sourceTuple/*auto*/.left(/*auto*/), destination/*string*/).map((definitionTuple: Tuple2<CompileState, Definition>) => {
				let definitionState = definitionTuple/*Tuple2<CompileState, Definition>*/.left(/*auto*/);
				let definition = definitionTuple/*Tuple2<CompileState, Definition>*/.right(/*auto*/);
				let let = Main/*auto*/.attachLet(definitionState/*auto*/, definition/*auto*/);
				let generate = let/*auto*/.generate(definitionState/*auto*/.platform(/*auto*/));
				return new Tuple2Impl<CompileState, string>(definitionState/*auto*/, generate/*auto*/);
			})).orElseGet(() => new Tuple2Impl<CompileState, string>(sourceTuple/*auto*/.left(/*auto*/), Main/*auto*/.generatePlaceholder(destination/*string*/)));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple/*auto*/.left(/*auto*/), destinationTuple/*auto*/.right(/*auto*/) + " = " + sourceTuple/*auto*/.right(/*auto*/)));
		});
	}
	static attachLet(definitionState: CompileState, definition: Definition): Definition {
		/*final Definition let*/;
		if (definitionState/*CompileState*/.isPlatform(Platform/*auto*/.Windows)) {
			let/*auto*/ = definition/*Definition*/;
		}
		else {
			let/*auto*/ = definition/*Definition*/.addModifierLast("let");
		}
		return let/*auto*/;
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileValue(state/*CompileState*/, input/*string*/).orElseGet(() => new Tuple2Impl<CompileState, string>(state/*CompileState*/, Main/*auto*/.generatePlaceholder(input/*string*/)));
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseValue(state/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Value>) => {
			let generated = tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).generate(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/).platform(/*auto*/));
			return new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), generated/*auto*/);
		});
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, Lists/*auto*/.of(Main/*auto*/.parseLambda, Main/*auto*/.createOperatorRule("+"), Main/*auto*/.createOperatorRule("-"), Main/*auto*/.createOperatorRule("<="), Main/*auto*/.createOperatorRule("<"), Main/*auto*/.createOperatorRule("&&"), Main/*auto*/.createOperatorRule("||"), Main/*auto*/.createOperatorRule(">"), Main/*auto*/.createOperatorRule(">="), Main/*auto*/.parseInvokable, Main/*auto*/.createAccessRule("."), Main/*auto*/.createAccessRule("::"), Main/*auto*/.parseSymbol, Main/*auto*/.parseNot, Main/*auto*/.parseNumber, Main/*auto*/.createOperatorRuleWithDifferentInfix("==", "==="), Main/*auto*/.createOperatorRuleWithDifferentInfix("!=", "!=="), Main/*auto*/.createTextRule("\""), Main/*auto*/.createTextRule("'")));
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped = Strings/*auto*/.strip(input1/*string*/);
			return Main/*auto*/.compilePrefix(stripped/*auto*/, slice/*string*/, (s: string) => Main/*auto*/.compileSuffix(s/*string*/, slice/*string*/, (s1: string) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1/*CompileState*/, new StringNode(s1/*auto*/)))));
		};
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*string*/), "!", (withoutPrefix: string) => {
			let childTuple = Main/*auto*/.compileValueOrPlaceholder(state/*CompileState*/, withoutPrefix/*string*/);
			let childState = childTuple/*auto*/.left(/*auto*/);
			let child = "!" + childTuple/*auto*/.right(/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new NotNode(child/*string*/)));
		});
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compileFirst(input/*string*/, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow = Strings/*auto*/.strip(beforeArrow/*string*/);
			return Main/*auto*/.compilePrefix(strippedBeforeArrow/*auto*/, "(", (withoutStart: string) => Main/*auto*/.compileSuffix(withoutStart/*auto*/, ")", (withoutEnd: string) => Main/*auto*/.parseValues(state/*CompileState*/, withoutEnd/*string*/, (state1: CompileState, s: string) => Main/*auto*/.parseParameter(state1/*CompileState*/, s/*string*/)).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => Main/*auto*/.compileLambdaWithParameterNames(paramNames/*auto*/.left(/*auto*/), Main/*auto*/.retainDefinitionsFromParameters(paramNames/*auto*/.right(/*auto*/)), afterArrow/*string*/))));
		});
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow = Strings/*auto*/.strip(afterArrow/*string*/);
		return Main/*auto*/.compilePrefix(strippedAfterArrow/*auto*/, "{", (withoutContentStart: string) => Main/*auto*/.compileSuffix(withoutContentStart/*auto*/, "}", (withoutContentEnd: string) => {
			let statementsTuple = Main/*auto*/.compileFunctionStatements(state/*CompileState*/.enterDepth(/*auto*/).defineAll(paramNames/*List<Definition>*/), withoutContentEnd/*string*/);
			let statementsState = statementsTuple/*auto*/.left(/*auto*/);
			let statements = statementsTuple/*auto*/.right(/*auto*/);
			let exited = statementsState/*CompileState*/.exitDepth(/*auto*/);
			let content = "{" + statements/*auto*/ + exited/*auto*/.createIndent(/*auto*/) + "}";
			if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
				return Main/*auto*/.assembleLambdaWithContent(exited/*auto*/, paramNames/*List<Definition>*/, content/*string*/);
			}
			return Main/*auto*/.getSome(exited/*auto*/, paramNames/*List<Definition>*/, content/*string*/);
		})).or(() => Main/*auto*/.compileValue(state/*CompileState*/, strippedAfterArrow/*auto*/).flatMap((tuple: Tuple2<CompileState, string>) => {
			let state1 = tuple/*Tuple2<CompileState, string>*/.left(/*auto*/);
			let content = tuple/*Tuple2<CompileState, string>*/.right(/*auto*/);
			if (state1/*CompileState*/.isPlatform(Platform/*auto*/.Windows)) {
				return Main/*auto*/.assembleLambdaWithContent(state1/*CompileState*/, paramNames/*List<Definition>*/, "\n\treturn " + content/*string*/ + ";");
			}
			return Main/*auto*/.getSome(state1/*CompileState*/, paramNames/*List<Definition>*/, content/*string*/);
		}));
	}
	static getSome(state: CompileState, parameters: List<Definition>, content: string): Some<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/, new LambdaNode(parameters/*List<Definition>*/, content/*string*/)));
	}
	static assembleLambdaWithContent(state: CompileState, parameters: List<Definition>, content: string): Some<Tuple2<CompileState, Value>> {
		let lambdaDefinition = new Definition(PrimitiveType/*auto*/.Auto, state/*CompileState*/.functionName(/*auto*/));
		let value = new FunctionSegment<Definition>(lambdaDefinition/*auto*/, parameters/*List<Definition>*/, new Some<>(content/*string*/));
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/.addFunction(value/*string*/.generate(state/*CompileState*/.platform(/*auto*/), "\n")), new SymbolNode("lambdaDefinition", PrimitiveType/*auto*/.Auto)));
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.createOperatorRuleWithDifferentInfix(infix/*string*/, infix/*string*/);
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state: CompileState, input: string) => Main/*auto*/.compileLast(input/*string*/, infix/*string*/, (childString: string, rawProperty: string) => {
			let property = Strings/*auto*/.strip(rawProperty/*string*/);
			if (!Main/*auto*/.isSymbol(property/*auto*/)) {
				return new None<Tuple2<CompileState, Value>>(/*auto*/);
			}
			return Main/*auto*/.parseValue(state/*CompileState*/, childString/*string*/).flatMap((childTuple: Tuple2<CompileState, Value>) => {
				let childState = childTuple/*Tuple2<CompileState, Value>*/.left(/*auto*/);
				let child = childTuple/*Tuple2<CompileState, Value>*/.right(/*auto*/);
				return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new AccessNode(child/*string*/, property/*auto*/)));
			});
		});
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFolded(input1/*string*/, Main/*auto*/.foldOperator(sourceInfix/*string*/), (divisions: List<string>) => Main/*auto*/.selectFirst(divisions/*List<string>*/, sourceInfix/*string*/)), (leftString: string, rightString: string) => Main/*auto*/.parseValue(state1/*CompileState*/, leftString/*auto*/).flatMap((leftTuple: Tuple2<CompileState, Value>) => Main/*auto*/.parseValue(leftTuple/*auto*/.left(/*auto*/), rightString/*auto*/).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
			let left = leftTuple/*auto*/.right(/*auto*/);
			let right = rightTuple/*Tuple2<CompileState, Value>*/.right(/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), new OperationNode(left/*auto*/, targetInfix/*string*/, right/*auto*/)));
		})));
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first = divisions/*List<string>*/.findFirst(/*auto*/).orElse("");
		let afterFirst = divisions/*List<string>*/.subList(1/*auto*/, divisions/*List<string>*/.size(/*auto*/)).orElse(divisions/*List<string>*/).query(/*auto*/).collect(new Joiner(delimiter/*string*/)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(first/*auto*/, afterFirst/*auto*/));
	}
	static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state: DivideState, c: string) => {
			if (c/*string*/ === Strings/*auto*/.charAt(infix/*string*/, 0/*auto*/) && state/*DivideState*/.startsWith(Strings/*auto*/.sliceFrom(infix/*string*/, 1/*auto*/))) {
				let length = Strings/*auto*/.length(infix/*string*/) - 1/*auto*/;
				let counter = 0/*auto*/;
				let current = state/*DivideState*/;
				while (counter/*auto*/ < length/*number*/) {
					counter/*auto*/++;
					current/*Tuple2<CompileState, List<T>>*/ = current/*Tuple2<CompileState, List<T>>*/.pop(/*auto*/).map((tuple: Tuple2<DivideState, string>) => tuple/*Tuple2<CompileState, string>*/.left(/*auto*/)).orElse(current/*Tuple2<CompileState, List<T>>*/);
				}
				return current/*Tuple2<CompileState, List<T>>*/.advance(/*auto*/);
			}
			return state/*DivideState*/.append(c/*string*/);
		};
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (Main/*auto*/.isNumber(stripped/*auto*/)) {
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/, new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>(/*auto*/);
		}
	}
	static isNumber(input: string): boolean {
		let query = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*string*/)));
		return query/*auto*/.map((index: number) => input/*string*/.charAt(index/*auto*/)).allMatch((c: string) => Characters/*auto*/.isDigit(c/*string*/));
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
			let withImport = state/*CompileState*/.addResolvedImportFromCache(stripped/*auto*/);
			let symbolNode = new SymbolNode(stripped/*auto*/, state/*CompileState*/.resolve(stripped/*auto*/).orElse(PrimitiveType/*auto*/.Auto));
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport/*auto*/, symbolNode/*auto*/));
		}
		else {
			return new None<Tuple2<CompileState, Value>>(/*auto*/);
		}
	}
	static isSymbol(input: string): boolean {
		let query = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*string*/)));
		return query/*auto*/.allMatch((index: number) => Main/*auto*/.isSymbolChar(index/*auto*/, Strings/*auto*/.charAt(input/*string*/, index/*auto*/)));
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c/*string*/ || Characters/*auto*/.isLetter(c/*string*/) || /*auto*/(0/*auto*/ !== index/*number*/ && Characters/*auto*/.isDigit(c/*string*/));
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		if (!input/*string*/.startsWith(infix/*string*/)) {
			return new None<Tuple2<CompileState, T>>(/*auto*/);
		}
		let slice = Strings/*auto*/.sliceFrom(input/*string*/, Strings/*auto*/.length(infix/*string*/));
		return mapper/*(arg0 : string) => Option<Tuple2<CompileState, T>>*/(slice/*string*/);
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseWhitespace(state/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Whitespace>) => {
			let generate = tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/).generate(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).platform(/*auto*/));
			return new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), generate/*auto*/);
		});
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings/*auto*/.isBlank(input/*string*/)) {
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state/*CompileState*/, new Whitespace(/*auto*/)));
		}
		return new None<Tuple2<CompileState, Whitespace>>(/*auto*/);
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), ";", (withoutEnd: string) => Main/*auto*/.getTupleOption(state/*CompileState*/, withoutEnd/*string*/).or(() => Main/*auto*/.compileEnumValues(state/*CompileState*/, withoutEnd/*string*/)));
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseParameter(state/*CompileState*/, withoutEnd/*string*/).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => {
			let generate = "\n\t" + definitionTuple/*Tuple2<CompileState, Parameter>*/.right(/*auto*/).generate(definitionTuple/*Tuple2<CompileState, Parameter>*/.left(/*auto*/).platform(/*auto*/)) + ";";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(definitionTuple/*Tuple2<CompileState, Parameter>*/.left(/*auto*/), generate/*auto*/));
		});
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseValues(state/*CompileState*/, withoutEnd/*string*/, (state1: CompileState, s: string) => Main/*auto*/.parseInvokable(state1/*CompileState*/, s/*string*/).flatMap((tuple: Tuple2<CompileState, Value>) => {
			let structureName = state/*CompileState*/.findLastStructureName(/*auto*/).orElse("");
			return tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).generateAsEnumValue(structureName/*string*/, state/*CompileState*/.platform(/*auto*/)).map((stringOption: string) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), stringOption/*auto*/));
		})).map((tuple: Tuple2<CompileState, List<string>>) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).query(/*auto*/).collect(new Joiner("")).orElse("")));
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return Main/*auto*/.parseParameter(state/*CompileState*/, input/*string*/).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state/*CompileState*/, new Placeholder(input/*string*/)));
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return Main/*auto*/.parseWhitespace(state/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Whitespace>) => Main/*auto*/.getCompileStateParameterTuple2(tuple/*Tuple2<CompileState, Value>*/)).or(() => Main/*auto*/.parseDefinition(state/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/))));
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/));
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.compileLast(Strings/*auto*/.strip(input/*string*/), " ", (beforeName: string, name: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(Strings/*auto*/.strip(beforeName/*auto*/), " ", Main/*auto*/.foldTypeSeparators), (beforeType: string, type: string) => Main/*auto*/.compileLast(Strings/*auto*/.strip(beforeType/*auto*/), "\n", (annotationsString: string, afterAnnotations: string) => {
			let annotations = Main/*auto*/.parseAnnotations(annotationsString/*string*/);
			return Main/*auto*/.parseDefinitionWithAnnotations(state/*CompileState*/, annotations/*List<string>*/, afterAnnotations/*string*/, type/*Type*/, name/*string*/);
		}).or(() => Main/*auto*/.parseDefinitionWithAnnotations(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), beforeType/*auto*/, type/*Type*/, name/*string*/))).or(() => Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), beforeName/*auto*/, name/*string*/)));
	}
	static parseAnnotations(s: string): List<string> {
		return Main/*auto*/.divide(s/*string*/, (state1: DivideState, c: string) => Main/*auto*/.foldDelimited(state1/*CompileState*/, c/*string*/, "\n")).query(/*auto*/).map((s2: string) => Strings/*auto*/.strip(s2/*string*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*string*/)).filter((value: string) => 1/*auto*/ <= Strings/*auto*/.length(value/*string*/)).map((value: string) => Strings/*auto*/.sliceFrom(value/*string*/, 1/*auto*/)).map((s1: string) => Strings/*auto*/.strip(s1/*auto*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*string*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeType/*string*/), ">", (withoutTypeParamEnd: string) => Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", (beforeTypeParams: string, typeParamsString: string) => {
			let typeParams = Main/*auto*/.divideValues(typeParamsString/*string*/);
			return Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, annotations/*List<string>*/, typeParams/*List<string>*/, Main/*auto*/.parseModifiers(beforeTypeParams/*string*/), type/*string*/, name/*string*/);
		})).or(() => {
			let divided = Main/*auto*/.parseModifiers(beforeType/*string*/);
			return Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, annotations/*List<string>*/, Lists/*auto*/.empty(/*auto*/), divided/*auto*/, type/*string*/, name/*string*/);
		});
	}
	static parseModifiers(beforeType: string): List<string> {
		return Main/*auto*/.divide(Strings/*auto*/.strip(beforeType/*string*/), (state1: DivideState, c: string) => Main/*auto*/.foldDelimited(state1/*CompileState*/, c/*string*/, " ")).query(/*auto*/).map((s: string) => Strings/*auto*/.strip(s/*string*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*string*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter/*string*/ === c/*string*/) {
			return state1/*DivideState*/.advance(/*auto*/);
		}
		return state1/*DivideState*/.append(c/*string*/);
	}
	static divideValues(input: string): List<string> {
		return Main/*auto*/.divide(input/*string*/, Main/*auto*/.foldValues).query(/*auto*/).map((input1: string) => Strings/*auto*/.strip(input1/*string*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*string*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static foldTypeSeparators(state: DivideState, c: string): DivideState {
		if (" " === c/*string*/ && state/*DivideState*/.isLevel(/*auto*/)) {
			return state/*DivideState*/.advance(/*auto*/);
		}
		let appended = state/*DivideState*/.append(c/*string*/);
		if ("<" === c/*string*/) {
			return appended/*auto*/.enter(/*auto*/);
		}
		if (">" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, oldModifiers: List<string>, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.parseType(state/*CompileState*/, type/*string*/).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = Main/*auto*/.modifyModifiers(oldModifiers/*List<string>*/, state/*CompileState*/.platform(/*auto*/));
			let generated = new Definition(annotations/*List<string>*/, newModifiers/*auto*/, typeParams/*List<string>*/, typeTuple/*Tuple2<CompileState, Type>*/.right(/*auto*/), name/*string*/);
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple/*Tuple2<CompileState, Type>*/.left(/*auto*/), generated/*auto*/));
		});
	}
	static modifyModifiers(oldModifiers: List<string>, platform: Platform): List<string> {
		let list = Main/*auto*/.retainFinal(oldModifiers/*List<string>*/, platform/*Platform*/);
		if (oldModifiers/*List<string>*/.contains("static", Strings/*auto*/.equalsTo)) {
			return list/*auto*/.addLast("static");
		}
		return list/*auto*/;
	}
	static retainFinal(oldModifiers: List<string>, platform: Platform): List<string> {
		if (oldModifiers/*List<string>*/.contains("final", Strings/*auto*/.equalsTo) || Platform/*auto*/.Magma !== platform/*Platform*/) {
			return Lists/*auto*/.empty(/*auto*/);
		}
		return Lists/*auto*/.of("mut");
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple2<CompileState, Type> {
		return Main/*auto*/.parseType(state/*CompileState*/, type/*string*/).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/))).orElseGet(() => new Tuple2Impl<CompileState, Type>(state/*CompileState*/, new Placeholder(type/*string*/)));
	}
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseType(state/*CompileState*/, type/*string*/).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/).generate(/*auto*/)));
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.or(state/*CompileState*/, type/*string*/, Lists/*auto*/.of(Main/*auto*/.parseArrayType, Main/*auto*/.parseVarArgs, Main/*auto*/.parseGeneric, Main/*auto*/.parsePrimitive, Main/*auto*/.parseSymbolType));
	}
	static parseArrayType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		return Main/*auto*/.compileSuffix(stripped/*auto*/, "[]", (s: string) => {
			let child = Main/*auto*/.parseTypeOrPlaceholder(state/*CompileState*/, s/*string*/);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*string*/.left(/*auto*/), new ArrayType(child/*string*/.right(/*auto*/))));
		});
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		return Main/*auto*/.compileSuffix(stripped/*auto*/, "...", (s: string) => {
			let child = Main/*auto*/.parseTypeOrPlaceholder(state/*CompileState*/, s/*string*/);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*string*/.left(/*auto*/), new VariadicType(child/*string*/.right(/*auto*/))));
		});
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
			let symbolNode = new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state/*CompileState*/.addResolvedImportFromCache(stripped/*auto*/), symbolNode/*auto*/));
		}
		return new None<Tuple2<CompileState, Type>>(/*auto*/);
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.findPrimitiveValue(Strings/*auto*/.strip(input/*string*/), state/*CompileState*/.platform(/*auto*/)).map((result: Type) => new Tuple2Impl<CompileState, Type>(state/*CompileState*/, result/*Tuple2<CompileState, string>*/));
	}
	static findPrimitiveValue(input: string, platform: Platform): Option<Type> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (Strings/*auto*/.equalsTo("char", stripped/*auto*/) || Strings/*auto*/.equalsTo("Character", stripped/*auto*/)) {
			if (Platform/*auto*/.TypeScript === platform/*Platform*/) {
				return new Some<Type>(PrimitiveType/*auto*/.String);
			}
			else {
				return new Some<Type>(PrimitiveType/*auto*/.I8);
			}
		}
		if (Strings/*auto*/.equalsTo("String", stripped/*auto*/)) {
			if (Platform/*auto*/.TypeScript === platform/*Platform*/) {
				return new Some<Type>(PrimitiveType/*auto*/.String);
			}
			else {
				return new Some<Type>(new SliceType(PrimitiveType/*auto*/.I8));
			}
		}
		if (Strings/*auto*/.equalsTo("int", stripped/*auto*/) || Strings/*auto*/.equalsTo("Integer", stripped/*auto*/)) {
			if (Platform/*auto*/.Magma === platform/*Platform*/) {
				return new Some<Type>(PrimitiveType/*auto*/.I32);
			}
			else {
				return new Some<Type>(PrimitiveType/*auto*/.Number);
			}
		}
		if (Strings/*auto*/.equalsTo("boolean", stripped/*auto*/) || Strings/*auto*/.equalsTo("Boolean", stripped/*auto*/)) {
			return new Some<Type>(new BooleanType(platform/*Platform*/));
		}
		if (Strings/*auto*/.equalsTo("var", stripped/*auto*/)) {
			return new Some<Type>(PrimitiveType/*auto*/.Var);
		}
		if (Strings/*auto*/.equalsTo("void", stripped/*auto*/)) {
			return new Some<Type>(PrimitiveType/*auto*/.Void);
		}
		return new None<Type>(/*auto*/);
	}
	static parseGeneric(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), ">", (withoutEnd: string) => Main/*auto*/.compileFirst(withoutEnd/*string*/, "<", (baseString: string, argsString: string) => {
			let argsTuple = Main/*auto*/.parseValuesOrEmpty(state/*CompileState*/, argsString/*string*/, (state1: CompileState, s: string) => Main/*auto*/.compileTypeArgument(state1/*DivideState*/, s/*string*/));
			let argsState = argsTuple/*Tuple2<CompileState, List<Argument>>*/.left(/*auto*/);
			let args = argsTuple/*Tuple2<CompileState, List<Argument>>*/.right(/*auto*/);
			let base = Strings/*auto*/.strip(baseString/*string*/);
			return Main/*auto*/.assembleFunctionType(argsState/*auto*/, base/*string*/, args/*List<T>*/).or(() => {
				let compileState = argsState/*auto*/.addResolvedImportFromCache(base/*string*/);
				return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState/*auto*/, new TemplateType(base/*string*/, args/*List<T>*/)));
			});
		}));
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.mapFunctionType(base/*string*/, args/*List<string>*/).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state/*CompileState*/, generated/*auto*/));
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if (Strings/*auto*/.equalsTo("Function", base/*string*/)) {
			return args/*List<string>*/.findFirst(/*auto*/).and(() => args/*List<string>*/.find(1/*auto*/)).map((tuple: Tuple2<string, string>) => new FunctionType(Lists/*auto*/.of(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/)), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/)));
		}
		if (Strings/*auto*/.equalsTo("BiFunction", base/*string*/)) {
			return args/*List<string>*/.find(0/*auto*/).and(() => args/*List<string>*/.find(1/*auto*/)).and(() => args/*List<string>*/.find(2/*auto*/)).map((tuple: Tuple2<Tuple2<string, string>, string>) => new FunctionType(Lists/*auto*/.of(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).right(/*auto*/)), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/)));
		}
		if (Strings/*auto*/.equalsTo("Supplier", base/*string*/)) {
			return args/*List<string>*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.empty(/*auto*/), first/*auto*/));
		}
		if (Strings/*auto*/.equalsTo("Consumer", base/*string*/)) {
			return args/*List<string>*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.of(first/*auto*/), "void"));
		}
		if (Strings/*auto*/.equalsTo("Predicate", base/*string*/)) {
			return args/*List<string>*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.of(first/*auto*/), "boolean"));
		}
		return new None<Type>(/*auto*/);
	}
	static compileTypeArgument(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, Lists/*auto*/.of((state2: CompileState, input1: string) => Main/*auto*/.compileWhitespace(state2/*auto*/, input1/*string*/), (state1: CompileState, type: string) => Main/*auto*/.compileType(state1/*DivideState*/, type/*string*/)));
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return Main/*auto*/.parseValues(state/*CompileState*/, input/*string*/, mapper/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>*/).orElse(new Tuple2Impl<CompileState, List<T>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/)));
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main/*auto*/.parseAll(state/*CompileState*/, input/*string*/, Main/*auto*/.foldValues, mapper/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>*/);
	}
	static foldValues(state: DivideState, c: string): DivideState {
		if ("," === c/*string*/ && state/*DivideState*/.isLevel(/*auto*/)) {
			return state/*DivideState*/.advance(/*auto*/);
		}
		let appended = state/*DivideState*/.append(c/*string*/);
		if ("-" === c/*string*/) {
			let peeked = appended/*auto*/.peek(/*auto*/);
			if (">" === peeked/*auto*/) {
				return appended/*auto*/.popAndAppendToOption(/*auto*/).orElse(appended/*auto*/);
			}
			else {
				return appended/*auto*/;
			}
		}
		if ("<" === c/*string*/ || "(" === c/*string*/) {
			return appended/*auto*/.enter(/*auto*/);
		}
		if (">" === c/*string*/ || ")" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileInfix(input/*string*/, infix/*string*/, Main/*auto*/.findLast, mapper/*(arg0 : string, arg1 : string) => Option<T>*/);
	}
	static findLast(input: string, infix: string): number {
		return input/*string*/.lastIndexOf(infix/*string*/);
	}
	static compileSuffix<T>(input: string, suffix: string, mapper: (arg0 : string) => Option<T>): Option<T> {
		if (!input/*string*/.endsWith(suffix/*string*/)) {
			return new None<T>(/*auto*/);
		}
		let length = Strings/*auto*/.length(input/*string*/);
		let length1 = Strings/*auto*/.length(suffix/*string*/);
		let content = Strings/*auto*/.sliceBetween(input/*string*/, 0/*auto*/, length/*number*/ - length1/*auto*/);
		return mapper/*(arg0 : string) => Option<T>*/(content/*string*/);
	}
	static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileInfix(input/*string*/, infix/*string*/, Main/*auto*/.findFirst, mapper/*(arg0 : string, arg1 : string) => Option<T>*/);
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileSplit(Main/*auto*/.split(input/*string*/, infix/*string*/, locator/*(arg0 : string, arg1 : string) => number*/), mapper/*(arg0 : string, arg1 : string) => Option<T>*/);
	}
	static compileSplit<T>(splitter: Option<Tuple2<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter/*Option<Tuple2<string, string>>*/.flatMap((tuple: Tuple2<string, string>) => mapper/*(arg0 : string, arg1 : string) => Option<T>*/(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/)));
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple2<string, string>> {
		let index = locator/*(arg0 : string, arg1 : string) => number*/(input/*string*/, infix/*string*/);
		if (0/*auto*/ > index/*number*/) {
			return new None<Tuple2<string, string>>(/*auto*/);
		}
		let left = Strings/*auto*/.sliceBetween(input/*string*/, 0/*auto*/, index/*number*/);
		let length = Strings/*auto*/.length(infix/*string*/);
		let right = Strings/*auto*/.sliceFrom(input/*string*/, index/*number*/ + length/*number*/);
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(left/*auto*/, right/*auto*/));
	}
	static findFirst(input: string, infix: string): number {
		return input/*string*/.indexOf(infix/*string*/);
	}
	static generatePlaceholder(input: string): string {
		let replaced = input/*string*/.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced/*auto*/ + "*/";
	}
}
Main.main();
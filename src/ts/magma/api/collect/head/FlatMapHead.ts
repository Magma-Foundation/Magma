/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Files: jvm.api.io, 
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	HeadedQuery: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	Iterators: magma.api.collect, 
	Joiner: magma.api.collect, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Query: magma.api.collect, 
	Console: magma.api.io, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Err: magma.api.result, 
	Ok: magma.api.result, 
	Result: magma.api.result, 
	Characters: magma.api.text, 
	Strings: magma.api.text, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	CompileState: magma.app.compile, 
	ConstructionCaller: magma.app.compile.define, 
	ConstructorHeader: magma.app.compile.define, 
	Definition: magma.app.compile.define, 
	MethodHeader: magma.app.compile.define, 
	Parameter: magma.app.compile.define, 
	DivideState: magma.app.compile, 
	ImmutableCompileState: magma.app.compile, 
	ImmutableDivideState: magma.app.compile, 
	Import: magma.app.compile, 
	Placeholder: magma.app.compile.text, 
	Symbol: magma.app.compile.text, 
	Whitespace: magma.app.compile.text, 
	FunctionType: magma.app.compile.type, 
	PrimitiveType: magma.app.compile.type, 
	TemplateType: magma.app.compile.type, 
	Type: magma.app.compile.type, 
	VariadicType: magma.app.compile.type, 
	AccessValue: magma.app.compile.value, 
	Argument: magma.app.compile.value, 
	Caller: magma.app.compile.value, 
	Invokable: magma.app.compile.value, 
	Lambda: magma.app.compile.value, 
	Not: magma.app.compile.value, 
	Operation: magma.app.compile.value, 
	StringValue: magma.app.compile.value, 
	Value: magma.app.compile.value, 
	Source: magma.app.io, 
	Main: magma.app
]*/
import { Head } from "../../../../magma/api/collect/head/Head";
import { Query } from "../../../../magma/api/collect/Query";
import { Option } from "../../../../magma/api/option/Option";
import { None } from "../../../../magma/api/option/None";
export class FlatMapHead<T, R> implements Head<R> {
	mapper: (arg0 : T) => Query<R>;
	head: Head<T>;
	current: Query<R>;
	constructor (head: Head<T>, initial: Query<R>, mapper: (arg0 : T) => Query<R>) {
		this.head/*unknown*/ = head/*Head<T>*/;
		this.current/*unknown*/ = initial/*Query<R>*/;
		this.mapper/*unknown*/ = mapper/*(arg0 : T) => Query<R>*/;
	}
	next(): Option<R> {
		while (true/*unknown*/){
			let next = this.current.next()/*unknown*/;
			if (next.isPresent()/*unknown*/){
				return next/*unknown*/;
			}
			let tuple = this.head.next().map(this.mapper).toTuple(this.current)/*unknown*/;
			if (tuple.left()/*unknown*/){
				this.current/*unknown*/ = tuple.right()/*unknown*/;
			}
			else {
				return new None<R>()/*unknown*/;
			}
		}
	}
}

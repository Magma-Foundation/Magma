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
import { List } from "../../../magma/api/collect/list/List";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { Query } from "../../../magma/api/collect/Query";
import { Tuple2 } from "../../../magma/api/Tuple2";
import { Option } from "../../../magma/api/option/Option";
import { Strings } from "../../../magma/api/text/Strings";
import { None } from "../../../magma/api/option/None";
import { Some } from "../../../magma/api/option/Some";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
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
		return new DivideState(Lists.empty(), "", 0, input, 0)/*unknown*/;
	}
	query(): Query<string> {
		return this.segments.query()/*unknown*/;
	}
	advance(): DivideState {
		return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index)/*unknown*/;
	}
	append(c: string): DivideState {
		return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index)/*unknown*/;
	}
	isLevel(): boolean {
		return 0 === this.depth/*unknown*/;
	}
	enter(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index)/*unknown*/;
	}
	exit(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index)/*unknown*/;
	}
	isShallow(): boolean {
		return 1 === this.depth/*unknown*/;
	}
	pop(): Option<Tuple2<DivideState, string>> {
		if (this.index >= Strings.length(this.input)/*unknown*/){
			return new None<Tuple2<DivideState, string>>()/*unknown*/;
		}
		let c = this.input.charAt(this.index)/*unknown*/;
		let nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1)/*unknown*/;
		return new Some<Tuple2<DivideState, string>>(new Tuple2Impl<DivideState, string>(nextState, c))/*unknown*/;
	}
	popAndAppendToTuple(): Option<Tuple2<DivideState, string>> {
		return this.pop().map((inner: Tuple2<DivideState, string>) => new Tuple2Impl<DivideState, string>(inner.left().append(inner.right()), inner.right())/*unknown*/)/*unknown*/;
	}
	popAndAppendToOption(): Option<DivideState> {
		return this.popAndAppendToTuple().map((tuple: Tuple2<DivideState, string>) => tuple.left()/*unknown*/)/*unknown*/;
	}
	peek(): string {
		return this.input.charAt(this.index)/*unknown*/;
	}
	startsWith(slice: string): boolean {
		return Strings.sliceFrom(this.input, this.index).startsWith(slice)/*unknown*/;
	}
}

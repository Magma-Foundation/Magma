// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState, Import, DivideState]
import { List } from "../../../../magma/api/collect/list/List";
import { Lists } from "../../../../jvm/api/collect/list/Lists";
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { Option } from "../../../../magma/api/option/Option";
import { Strings } from "../../../../jvm/api/text/Strings";
import { None } from "../../../../magma/api/option/None";
import { Some } from "../../../../magma/api/option/Some";
import { Tuple2Impl } from "../../../../magma/api/Tuple2Impl";
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

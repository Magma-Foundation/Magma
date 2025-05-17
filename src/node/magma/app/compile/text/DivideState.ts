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
		return new DivideState(Lists.empty(), "", 0, input, 0);
	}
	advance(): DivideState {
		return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
	}
	append(c: string): DivideState {
		return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
	}
	isLevel(): boolean {
		return 0 === this.depth;
	}
	enter(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
	}
	exit(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
	}
	isShallow(): boolean {
		return 1 === this.depth;
	}
	pop(): Option<Tuple2<DivideState, string>> {
		if (this.index >= Strings.length(this.input)) {
			return new None<Tuple2<DivideState, string>>();
		}
		let c = Strings.charAt(this.input, this.index);
		let nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
		return new Some<Tuple2<DivideState, string>>(new Tuple2Impl<DivideState, string>(nextState, c));
	}
	popAndAppendToTuple(): Option<Tuple2<DivideState, string>> {
		return this.pop().map((inner: Tuple2<DivideState, string>) => new Tuple2Impl<DivideState, string>(inner.left().append(inner.right()), inner.right()));
	}
	popAndAppendToOption(): Option<DivideState> {
		return this.popAndAppendToTuple().map((tuple: Tuple2<DivideState, string>) => tuple.left());
	}
	peek(): string {
		return Strings.charAt(this.input, this.index);
	}
	startsWith(slice: string): boolean {
		return Strings.sliceFrom(this.input, this.index).startsWith(slice);
	}
}

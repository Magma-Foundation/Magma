#include "./DivideState.h"
export class DivideState {
	mut segments: List<&[I8]>;
	mut buffer: &[I8];
	mut depth: number;
	mut input: &[I8];
	mut index: number;
	constructor (mut segments: List<&[I8]>, mut buffer: &[I8], mut depth: number, mut input: &[I8], mut index: number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	mut static createInitial(input: &[I8]): DivideState {
		return new DivideState(Lists.empty(), "", 0, input, 0);
	}
	mut advance(): DivideState {
		return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
	}
	mut append(c: I8): DivideState {
		return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
	}
	mut isLevel(): Bool {
		return 0 === this.depth;
	}
	mut enter(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
	}
	mut exit(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
	}
	mut isShallow(): Bool {
		return 1 === this.depth;
	}
	mut pop(): Option<Tuple2<DivideState, I8>> {
		if (this.index >= Strings.length(this.input)) {
			return new None<Tuple2<DivideState, I8>>();
		}
		let c = Strings.charAt(this.input, this.index);
		let nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
		return new Some<Tuple2<DivideState, I8>>(new Tuple2Impl<DivideState, I8>(nextState, c));
	}
	mut popAndAppendToTuple(): Option<Tuple2<DivideState, I8>> {
		return this.pop().map((mut inner: Tuple2<DivideState, I8>) => new Tuple2Impl<DivideState, I8>(inner.left().append(inner.right()), inner.right()));
	}
	mut popAndAppendToOption(): Option<DivideState> {
		return this.popAndAppendToTuple().map((mut tuple: Tuple2<DivideState, I8>) => tuple.left());
	}
	mut peek(): I8 {
		return Strings.charAt(this.input, this.index);
	}
	mut startsWith(slice: &[I8]): Bool {
		return Strings.sliceFrom(this.input, this.index).startsWith(slice);
	}
}

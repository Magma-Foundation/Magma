#include "./DivideState.h"
export class DivideState {
	segments: List<&[I8]>;
	buffer: &[I8];
	depth: number;
	input: &[I8];
	index: number;
	constructor (segments: List<&[I8]>, buffer: &[I8], depth: number, input: &[I8], index: number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
}

static createInitial(input: &[I8]): DivideState {
	return new DivideState(Lists.empty(), "", 0, input, 0);
}
advance(): DivideState {
	return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
}
append(c: I8): DivideState {
	return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
}
isLevel(): Bool {
	return 0 === this.depth;
}
enter(): DivideState {
	return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
}
exit(): DivideState {
	return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
}
isShallow(): Bool {
	return 1 === this.depth;
}
pop(): Option<Tuple2<DivideState, I8>> {
	if (this.index >= Strings.length(this.input)) {
		return new None<Tuple2<DivideState, I8>>();
	}
	let c: I8 = Strings.charAt(this.input, this.index);
	let nextState: DivideState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
	return new Some<Tuple2<DivideState, I8>>(new Tuple2Impl<DivideState, I8>(nextState, c));
}
popAndAppendToTuple(): Option<Tuple2<DivideState, I8>> {
	return this.pop().map((inner: Tuple2<DivideState, I8>) => new Tuple2Impl<DivideState, I8>(inner.left().append(inner.right()), inner.right()));
}
popAndAppendToOption(): Option<DivideState> {
	return this.popAndAppendToTuple().map((tuple: Tuple2<DivideState, I8>) => tuple.left());
}
peek(): I8 {
	return Strings.charAt(this.input, this.index);
}
startsWith(slice: &[I8]): Bool {
	return Strings.sliceFrom(this.input, this.index).startsWith(slice);
}
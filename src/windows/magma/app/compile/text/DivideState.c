#include "./DivideState.h"
export class DivideState {
	List<&[I8]> segments;
	&[I8] buffer;
	number depth;
	&[I8] input;
	number index;
	constructor (List<&[I8]> segments, &[I8] buffer, number depth, &[I8] input, number index) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
}

static DivideState createInitial(&[I8] input) {
	return new DivideState(Lists.empty(), "", 0, input, 0);
}
DivideState advance() {
	return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
}
DivideState append(I8 c) {
	return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
}
Bool isLevel() {
	return 0 === this.depth;
}
DivideState enter() {
	return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
}
DivideState exit() {
	return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
}
Bool isShallow() {
	return 1 === this.depth;
}
Option<Tuple2<DivideState, I8>> pop() {
	if (this.index >= Strings.length(this.input)) {
		return new None<Tuple2<DivideState, I8>>();
	}
	I8 c = Strings.charAt(this.input, this.index);
	DivideState nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
	return new Some<Tuple2<DivideState, I8>>(new Tuple2Impl<DivideState, I8>(nextState, c));
}
auto temp(Tuple2<DivideState, I8> inner) {new Tuple2Impl<DivideState, I8>(inner.left().append(inner.right()), inner.right())
}
Option<Tuple2<DivideState, I8>> popAndAppendToTuple() {
	return this.pop().map(temp);
}
auto temp(Tuple2<DivideState, I8> tuple) {tuple.left()
}
Option<DivideState> popAndAppendToOption() {
	return this.popAndAppendToTuple().map(temp);
}
I8 peek() {
	return Strings.charAt(this.input, this.index);
}
Bool startsWith(&[I8] slice) {
	return Strings.sliceFrom(this.input, this.index).startsWith(slice);
}
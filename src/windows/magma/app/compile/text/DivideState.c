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
	return new DivideState(Lists/*auto*/.empty(/*auto*/), "", 0/*auto*/, input/*auto*/, 0/*auto*/);
}
DivideState advance() {
	return new DivideState(this/*auto*/.segments.addLast(this/*auto*/.buffer), "", this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index);
}
DivideState append(I8 c) {
	return new DivideState(this/*auto*/.segments, this/*auto*/.buffer + c/*auto*/, this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index);
}
Bool isLevel() {
	return 0/*auto*/ === this/*auto*/.depth;
}
DivideState enter() {
	return new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth + 1/*auto*/, this/*auto*/.input, this/*auto*/.index);
}
DivideState exit() {
	return new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth - 1/*auto*/, this/*auto*/.input, this/*auto*/.index);
}
Bool isShallow() {
	return 1/*auto*/ === this/*auto*/.depth;
}
Option<Tuple2<DivideState, I8>> pop() {
	if (this/*auto*/.index >= Strings/*auto*/.length(this/*auto*/.input)) {
		return new None<Tuple2<DivideState, I8>>(/*auto*/);
	}
	I8 c = Strings/*auto*/.charAt(this/*auto*/.input, this/*auto*/.index);
	DivideState nextState = new DivideState(this/*auto*/.segments, this/*auto*/.buffer, this/*auto*/.depth, this/*auto*/.input, this/*auto*/.index + 1/*auto*/);
	return new Some<Tuple2<DivideState, I8>>(new Tuple2Impl<DivideState, I8>(nextState/*auto*/, c/*auto*/));
}
auto temp(Tuple2<DivideState, I8> inner) {
	return new Tuple2Impl<DivideState, I8>(inner/*auto*/.left(/*auto*/).append(inner/*auto*/.right(/*auto*/)), inner/*auto*/.right(/*auto*/));
}
Option<Tuple2<DivideState, I8>> popAndAppendToTuple() {
	return this/*auto*/.pop(/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<DivideState, I8> tuple) {
	return tuple/*auto*/.left(/*auto*/);
}
Option<DivideState> popAndAppendToOption() {
	return this/*auto*/.popAndAppendToTuple(/*auto*/).map(lambdaDefinition/*auto*/);
}
I8 peek() {
	return Strings/*auto*/.charAt(this/*auto*/.input, this/*auto*/.index);
}
Bool startsWith(&[I8] slice) {
	return Strings/*auto*/.sliceFrom(this/*auto*/.input, this/*auto*/.index).startsWith(slice/*auto*/);
}
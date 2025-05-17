#include "./FunctionType.h"
export class FunctionType implements Type {
	List<&[I8]> args;
	&[I8] returns;
	constructor (List<&[I8]> args, &[I8] returns) {
		this.args = args;
		this.returns = returns;
	}
}

auto temp(Tuple2<number, &[I8]> tuple) {"arg" + tuple.left() + " : " + tuple.right()
}
&[I8] generate() {
	&[I8] joinedArguments = this.args.queryWithIndices().map(temp).collect(new Joiner(", ")).orElse("");
	return "(" + joinedArguments + ") => " + this.returns;
}
Bool isFunctional() {
	return true;
}
Bool isVar() {
	return false;
}
&[I8] generateBeforeName() {
	return "";
}
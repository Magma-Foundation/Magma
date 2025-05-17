#include "./FunctionType.h"
export class FunctionType implements Type {
	args: List<&[I8]>;
	returns: &[I8];
	constructor (args: List<&[I8]>, returns: &[I8]) {
		this.args = args;
		this.returns = returns;
	}
}

generate(): &[I8] {
	let joinedArguments: &[I8] = this.args.queryWithIndices().map((tuple: Tuple2<number, &[I8]>) => "arg" + tuple.left() + " : " + tuple.right()).collect(new Joiner(", ")).orElse("");
	return "(" + joinedArguments + ") => " + this.returns;
}
isFunctional(): Bool {
	return true;
}
isVar(): Bool {
	return false;
}
generateBeforeName(): &[I8] {
	return "";
}
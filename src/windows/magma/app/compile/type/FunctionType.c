#include "./FunctionType.h"
export class FunctionType implements Type {
	mut args: List<&[I8]>;
	mut returns: &[I8];
	constructor (mut args: List<&[I8]>, mut returns: &[I8]) {
		this.args = args;
		this.returns = returns;
	}
}

mut generate(): &[I8] {
	let joinedArguments: &[I8] = this.args.queryWithIndices().map((mut tuple: Tuple2<number, &[I8]>) => "arg" + tuple.left() + " : " + tuple.right()).collect(new Joiner(", ")).orElse("");
	return "(" + joinedArguments + ") => " + this.returns;
}
mut isFunctional(): Bool {
	return true;
}
mut isVar(): Bool {
	return false;
}
mut generateBeforeName(): &[I8] {
	return "";
}
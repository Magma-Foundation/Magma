#include "./FunctionType.h"
export class FunctionType implements Type {
	List<&[I8]> args;
	&[I8] returns;
	constructor (List<&[I8]> args, &[I8] returns) {
		this.args = args;
		this.returns = returns;
	}
}

auto temp(Tuple2<number, &[I8]> tuple) {
	return "arg" + tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/) + " : " + tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/);
}
&[I8] generate() {
	var joinedArguments = this/*auto*/.args.queryWithIndices(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return "(" + joinedArguments/*auto*/ + ") => " + this/*auto*/.returns;
}
Bool isFunctional() {
	return true/*auto*/;
}
Bool isVar() {
	return false/*auto*/;
}
&[I8] generateBeforeName() {
	return "";
}
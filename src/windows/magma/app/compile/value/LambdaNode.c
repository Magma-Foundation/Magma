#include "./LambdaNode.h"
export class LambdaNode implements Value {
	List<Definition> paramNames;
	&[I8] content;
	constructor (List<Definition> paramNames, &[I8] content) {
		this.paramNames = paramNames;
		this.content = content;
	}
}

auto temp(Definition definition) {
	return definition/*auto*/.generate(platform/*auto*/);
}
&[I8] generate(Platform platform) {
	&[I8] joinedParamNames = this/*auto*/.paramNames.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return "(" + joinedParamNames/*auto*/ + ")" + " => " + this/*auto*/.content;
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Type resolve(CompileState state) {
	return PrimitiveType/*auto*/.Auto;
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
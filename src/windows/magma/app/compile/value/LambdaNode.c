#include "./LambdaNode.h"
export class LambdaNode implements Value {
	List<Definition> paramNames;
	&[I8] content;
	constructor (List<Definition> paramNames, &[I8] content) {
		this.paramNames = paramNames;
		this.content = content;
	}
}

auto temp(Definition definition) {definition.generate(platform)
}
&[I8] generate(Platform platform) {
	&[I8] joinedParamNames = this.paramNames.query().map(temp).collect(new Joiner(", ")).orElse("");
	return "(" + joinedParamNames + ")" + " => " + this.content;
}
Option<Value> toValue() {
	return new Some<Value>(this);
}
Option<Value> findChild() {
	return new None<Value>();
}
Type resolve(CompileState state) {
	return PrimitiveType.Auto;
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>();
}
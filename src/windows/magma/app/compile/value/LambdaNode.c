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
	return definition/*Definition*/.generate(platform/*Platform*/);
}
&[I8] generate(Platform platform) {
	var joinedParamNames = this/*auto*/.paramNames.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).orElse("");
	return "(" + joinedParamNames/*auto*/ + ")" + " => " + this/*auto*/.content;
}
Option<Value> toValue() {
	return new Some<Value>(this/*auto*/);
}
Option<Value> findChild() {
	return new None<Value>(/*auto*/);
}
Option<&[I8]> generateAsEnumValue(&[I8] structureName, Platform platform) {
	return new None<&[I8]>(/*auto*/);
}
Type type() {
	return PrimitiveType/*auto*/.Auto;
}
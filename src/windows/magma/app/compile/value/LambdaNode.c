#include "./LambdaNode.h"
export class LambdaNode implements Value {
	paramNames: List<Definition>;
	content: &[I8];
	constructor (paramNames: List<Definition>, content: &[I8]) {
		this.paramNames = paramNames;
		this.content = content;
	}
}

generate(): &[I8] {
	let joinedParamNames: &[I8] = this.paramNames.query().map((definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
	return "(" + joinedParamNames + ")" + " => " + this.content;
}
toValue(): Option<Value> {
	return new Some<Value>(this);
}
findChild(): Option<Value> {
	return new None<Value>();
}
resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
}
generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
	return new None<&[I8]>();
}
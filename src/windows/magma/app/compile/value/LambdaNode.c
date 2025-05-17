#include "./LambdaNode.h"
export class LambdaNode implements Value {
	mut paramNames: List<Definition>;
	mut content: &[I8];
	constructor (mut paramNames: List<Definition>, mut content: &[I8]) {
		this.paramNames = paramNames;
		this.content = content;
	}
}

mut generate(): &[I8] {
	let joinedParamNames = this.paramNames.query().map((mut definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
	return "(" + joinedParamNames + ")" + " => " + this.content;
}
mut toValue(): Option<Value> {
	return new Some<Value>(this);
}
mut findChild(): Option<Value> {
	return new None<Value>();
}
mut resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
}
mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
	return new None<&[I8]>();
}
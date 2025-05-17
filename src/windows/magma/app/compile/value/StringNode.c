#include "./StringNode.h"
export class StringNode implements Value {
	value: &[I8];
	constructor (value: &[I8]) {
		this.value = value;
	}
}

generate(): &[I8] {
	return "\"" + this.value + "\"";
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
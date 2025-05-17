#include "./SymbolNode.h"
export class SymbolNode {
	value: &[I8];
	constructor (value: &[I8]) {
		this.value = value;
	}
}

generate(): &[I8] {
	return this.value;
}
resolve(state: CompileState): Type {
	return state.resolve(this.value).orElse(PrimitiveType.Unknown);
}
toValue(): Option<Value> {
	return new Some<Value>(this);
}
findChild(): Option<Value> {
	return new None<Value>();
}
isFunctional(): Bool {
	return false;
}
isVar(): Bool {
	return false;
}
generateBeforeName(): &[I8] {
	return "";
}
generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
	return new None<&[I8]>();
}
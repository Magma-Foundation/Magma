#include "./SymbolNode.h"
export class SymbolNode {
	mut value: &[I8];
	constructor (mut value: &[I8]) {
		this.value = value;
	}
	mut generate(): &[I8] {
		return this.value;
	}
	mut resolve(state: CompileState): Type {
		return state.resolve(this.value).orElse(PrimitiveType.Unknown);
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}

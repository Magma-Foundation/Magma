#include "./StringValue.h"
class StringValue implements Value {
	mut value: &[I8];
	constructor (mut value: &[I8]) {
		this.value = value;
	}
	mut generate(): &[I8] {
		return "\"" + this.value + "\"";
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}

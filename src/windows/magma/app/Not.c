#include "./Not.h"
class Not implements Value {
	mut child: &[I8];
	constructor (mut child: &[I8]) {
		this.child = child;
	}
	mut generate(): &[I8] {
		return this.child;
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

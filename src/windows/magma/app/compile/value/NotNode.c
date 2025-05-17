#include "./NotNode.h"
export class NotNode implements Value {
	child: &[I8];
	constructor (child: &[I8]) {
		this.child = child;
	}
}

generate(): &[I8] {
	return this.child;
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
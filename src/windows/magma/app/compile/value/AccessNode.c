#include "./AccessNode.h"
export class AccessNode implements Value {
	child: Value;
	property: &[I8];
	constructor (child: Value, property: &[I8]) {
		this.child = child;
		this.property = property;
	}
}

generate(): &[I8] {
	return this.child.generate() + "." + this.property;
}
toValue(): Option<Value> {
	return new Some<Value>(this);
}
findChild(): Option<Value> {
	return new Some<Value>(this.child);
}
resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
}
generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
	return new None<&[I8]>();
}
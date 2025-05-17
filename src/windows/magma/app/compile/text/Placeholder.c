#include "./Placeholder.h"
export class Placeholder {
	input: &[I8];
	constructor (input: &[I8]) {
		this.input = input;
	}
}

generate(): &[I8] {
	return Main.generatePlaceholder(this.input);
}
isFunctional(): Bool {
	return false;
}
findChild(): Option<Value> {
	return new None<Value>();
}
asDefinition(): Option<Definition> {
	return new None<Definition>();
}
toValue(): Option<Value> {
	return new None<Value>();
}
resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
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
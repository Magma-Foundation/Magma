#include "./Placeholder.h"
export class Placeholder {
	mut input: &[I8];
	constructor (mut input: &[I8]) {
		this.input = input;
	}
}

mut generate(): &[I8] {
	return Main.generatePlaceholder(this.input);
}
mut isFunctional(): Bool {
	return false;
}
mut findChild(): Option<Value> {
	return new None<Value>();
}
mut asDefinition(): Option<Definition> {
	return new None<Definition>();
}
mut toValue(): Option<Value> {
	return new None<Value>();
}
mut resolve(state: CompileState): Type {
	return PrimitiveType.Unknown;
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
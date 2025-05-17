#include "./Whitespace.h"
export class Whitespace implements Parameter {
}

generate(): &[I8] {
	return "";
}
asDefinition(): Option<Definition> {
	return new None<Definition>();
}
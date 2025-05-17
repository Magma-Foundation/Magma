#include "./Whitespace.h"
class Whitespace implements Parameter {
	mut generate(): &[I8] {
		return "";
	}
	mut asDefinition(): Option<Definition> {
		return new None<Definition>();
	}
}

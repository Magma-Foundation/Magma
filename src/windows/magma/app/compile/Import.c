#include "./Import.h"
export class Import {
	namespace: List<&[I8]>;
	child: &[I8];
	constructor (namespace: List<&[I8]>, child: &[I8]) {
		this.namespace = namespace;
		this.child = child;
	}
}

generate(platform: Platform): &[I8] {
	if (Platform.Magma === platform) {
		let joinedNamespace: &[I8] = this.namespace.query().collect(new Joiner(".")).orElse("");
		return "import " + joinedNamespace + "." + this.child + ";\n";
	}
	let joinedNamespace: &[I8] = this.namespace.addLast(this.child).query().collect(new Joiner("/")).orElse("");
	return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
}
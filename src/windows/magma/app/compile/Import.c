#include "./Import.h"
export class Import {
	mut namespace: List<&[I8]>;
	mut child: &[I8];
	constructor (mut namespace: List<&[I8]>, mut child: &[I8]) {
		this.namespace = namespace;
		this.child = child;
	}
}

mut generate(platform: Platform): &[I8] {
	if (Platform.Magma === platform) {
		let joinedNamespace: &[I8] = this.namespace.query().collect(new Joiner(".")).orElse("");
		return "import " + joinedNamespace + "." + this.child + ";\n";
	}
	let joinedNamespace: &[I8] = this.namespace.addLast(this.child).query().collect(new Joiner("/")).orElse("");
	return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
}
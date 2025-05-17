#include "./Import.h"
export class Import {
	List<&[I8]> namespace;
	&[I8] child;
	constructor (List<&[I8]> namespace, &[I8] child) {
		this.namespace = namespace;
		this.child = child;
	}
}

&[I8] generate(Platform platform) {
	if (Platform.Magma === platform) {
		&[I8] joinedNamespace = this.namespace.query().collect(new Joiner(".")).orElse("");
		return "import " + joinedNamespace + "." + this.child + ";\n";
	}
	&[I8] joinedNamespace = this.namespace.addLast(this.child).query().collect(new Joiner("/")).orElse("");
	return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
}
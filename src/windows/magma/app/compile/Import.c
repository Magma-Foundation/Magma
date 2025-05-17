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
	if (Platform/*auto*/.Magma === platform/*auto*/) {
		&[I8] joinedNamespace = this/*auto*/.namespace.query(/*auto*/).collect(new Joiner(".")).orElse("");
		return "import " + joinedNamespace/*auto*/ + "." + this/*auto*/.child + ";\n";
	}
	&[I8] joinedNamespace = this/*auto*/.namespace.addLast(this/*auto*/.child).query(/*auto*/).collect(new Joiner("/")).orElse("");
	return "import { " + this/*auto*/.child + " } from \"" + joinedNamespace/*auto*/ + "\";\n";
}
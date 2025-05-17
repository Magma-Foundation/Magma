#include "./Platform.h"
export class Platform {
	static TypeScript/*auto*/: Platform = new Platform("node", "ts");
	static Magma/*auto*/: Platform = new Platform("magma", "mgs");
	static Windows/*auto*/: Platform = new Platform("windows", "h", "c");
	&[I8] root;
	&[I8][] extension;
}

constructor (&[I8] root, ...&[I8][] extensions) {
	this/*auto*/.root = root/*auto*/;
	this/*auto*/.extension = extensions/*auto*/;
}
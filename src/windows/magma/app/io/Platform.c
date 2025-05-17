#include "./Platform.h"
export class Platform {
	static TypeScript: Platform = new Platform("node", "ts");
	static Magma: Platform = new Platform("magma", "mgs");
	static Windows: Platform = new Platform("windows", "h", "c");
	&[I8] root;
	&[I8][] extension;
}

constructor (&[I8] root, ...&[I8][] extensions) {
	this.root = root;
	this.extension = extensions;
}
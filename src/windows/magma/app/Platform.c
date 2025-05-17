#include "./Platform.h"
export class Platform {
	static TypeScript: Platform = new Platform("node", "ts");
	static Magma: Platform = new Platform("magma", "mgs");
	static Windows: Platform = new Platform("windows", "h", "c");
	root: &[I8];
	extension: &[I8][];
	constructor (root: &[I8], ...extensions: &[I8][]) {
		this.root = root;
		this.extension = extensions;
	}
}

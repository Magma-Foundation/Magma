export class Platform {
	static TypeScript: Platform = new Platform("node", "ts");
	static Magma: Platform = new Platform("magma", "mgs");
	static Windows: Platform = new Platform("windows", "h", "c");
	root: string;
	extension: string[];
	constructor (root: string, ...extensions: string[]) {
		this.root = root;
		this.extension = extensions;
	}
}

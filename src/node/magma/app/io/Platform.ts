export class Platform {
	static TypeScript/*auto*/: Platform = new Platform("node", "ts");
	static Magma/*auto*/: Platform = new Platform("magma", "mgs");
	static Windows/*auto*/: Platform = new Platform("windows", "h", "c");
	root: string;
	extension: string[];
	constructor (root: string, ...extensions: string[]) {
		this/*auto*/.root = root/*string*/;
		this/*auto*/.extension = extensions/*string[]*/;
	}
}

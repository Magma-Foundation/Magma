import { List } from "../../../magma/api/collect/list/List";
import { Platform } from "../../../magma/app/io/Platform";
import { Joiner } from "../../../magma/api/collect/Joiner";
export class Import {
	namespace: List<string>;
	child: string;
	constructor (namespace: List<string>, child: string) {
		this.namespace = namespace;
		this.child = child;
	}
	generate(platform: Platform): string {
		if (Platform/*auto*/.Magma === platform/*Platform*/) {
			let joinedNamespace: string = this/*auto*/.namespace.query(/*auto*/).collect(new Joiner(".")).orElse("");
			return "import " + joinedNamespace/*auto*/ + "." + this/*auto*/.child + ";\n";
		}
		let joinedNamespace: string = this/*auto*/.namespace.addLast(this/*auto*/.child).query(/*auto*/).collect(new Joiner("/")).orElse("");
		return "import { " + this/*auto*/.child + " } from \"" + joinedNamespace/*auto*/ + "\";\n";
	}
}

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
		if (Platform.Magma === platform) {
			let joinedNamespace: string = this.namespace.query().collect(new Joiner(".")).orElse("");
			return "import " + joinedNamespace + "." + this.child + ";\n";
		}
		let joinedNamespace: string = this.namespace.addLast(this.child).query().collect(new Joiner("/")).orElse("");
		return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
	}
}

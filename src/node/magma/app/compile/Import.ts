/*[
	Actual, 
	Characters, 
	Collector, 
	CompileState, 
	Console, 
	Definition, 
	EmptyHead, 
	Err, 
	Files, 
	FlatMapHead, 
	FunctionHeader, 
	FunctionSegment, 
	Head, 
	HeadedQuery, 
	IOError, 
	ImmutableCompileState, 
	Import, 
	Joiner, 
	List, 
	ListCollector, 
	Lists, 
	MapHead, 
	Namespace, 
	None, 
	Ok, 
	Option, 
	Parameter, 
	Path, 
	Queries, 
	Query, 
	RangeHead, 
	Result, 
	SingleHead, 
	Some, 
	Strings, 
	Tuple2, 
	Tuple2Impl, 
	Type, 
	ZipHead
]*/
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
			let joinedNamespace = this/*auto*/.namespace.query(/*auto*/).collect(new Joiner(".")).orElse("");
			return "import " + joinedNamespace/*auto*/ + "." + this/*auto*/.child + ";\n";
		}
		let joinedNamespace = this/*auto*/.namespace.addLast(this/*auto*/.child).query(/*auto*/).collect(new Joiner("/")).orElse("");
		return "import { " + this/*auto*/.child + " } from \"" + joinedNamespace/*auto*/ + "\";\n";
	}
}

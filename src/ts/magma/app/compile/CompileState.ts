/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Files: jvm.api.io, 
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	HeadedQuery: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	Iterators: magma.api.collect, 
	Joiner: magma.api.collect, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Query: magma.api.collect, 
	Console: magma.api.io, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Err: magma.api.result, 
	Ok: magma.api.result, 
	Result: magma.api.result, 
	Characters: magma.api.text, 
	Strings: magma.api.text, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	CompileState: magma.app.compile, 
	ConstructionCaller: magma.app.compile.define, 
	ConstructorHeader: magma.app.compile.define, 
	Definition: magma.app.compile.define, 
	MethodHeader: magma.app.compile.define, 
	Parameter: magma.app.compile.define, 
	DivideState: magma.app.compile, 
	Import: magma.app.compile, 
	Placeholder: magma.app.compile.text, 
	Symbol: magma.app.compile.text, 
	Whitespace: magma.app.compile.text, 
	FunctionType: magma.app.compile.type, 
	PrimitiveType: magma.app.compile.type, 
	TemplateType: magma.app.compile.type, 
	Type: magma.app.compile.type, 
	VariadicType: magma.app.compile.type, 
	AccessValue: magma.app.compile.value, 
	Argument: magma.app.compile.value, 
	Caller: magma.app.compile.value, 
	Invokable: magma.app.compile.value, 
	Lambda: magma.app.compile.value, 
	Not: magma.app.compile.value, 
	Operation: magma.app.compile.value, 
	StringValue: magma.app.compile.value, 
	Value: magma.app.compile.value, 
	Source: magma.app.io, 
	Main: magma.app
]*/
import { Import } from "../../../magma/app/compile/Import";
import { List } from "../../../magma/api/collect/list/List";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { Option } from "../../../magma/api/option/Option";
import { Source } from "../../../magma/app/io/Source";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { None } from "../../../magma/api/option/None";
import { Joiner } from "../../../magma/api/collect/Joiner";
import { Query } from "../../../magma/api/collect/Query";
import { Strings } from "../../../magma/api/text/Strings";
import { Some } from "../../../magma/api/option/Some";
export class CompileState {
	imports: List<Import>;
	output: string;
	structureNames: List<string>;
	depth: number;
	definitions: List<Definition>;
	maybeNamespace: Option<List<string>>;
	sources: List<Source>;
	constructor (imports: List<Import>, output: string, structureNames: List<string>, depth: number, definitions: List<Definition>, maybeNamespace: Option<List<string>>, sources: List<Source>) {
		this.imports = imports;
		this.output = output;
		this.structureNames = structureNames;
		this.depth = depth;
		this.definitions = definitions;
		this.maybeNamespace = maybeNamespace;
		this.sources = sources;
	}
	static createInitial(): CompileState {
		return new CompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<List<string>>(), Lists.empty());
	}
	getJoined(otherOutput: string): string {
		let imports = this.queryImports().map((anImport: Import) => anImport.generate()).collect(new Joiner("")).orElse("");
		return imports + this.output + otherOutput;
	}
	queryImports(): Query<Import> {
		return this.imports.query();
	}
	querySources(): Query<Source> {
		return this.sources.query();
	}
	createIndent(): string {
		return "\n" + "\t".repeat(this.depth);
	}
	findLastStructureName(): Option<string> {
		return this.structureNames.findLast();
	}
	isLastWithin(name: string): boolean {
		return this.structureNames.findLast().filter((anObject: string) => Strings.equalsTo(name, anObject)).isPresent();
	}
	addResolvedImport(parent: List<string>, child: string): CompileState {
		let parent1 = parent;
		let namespace = this.maybeNamespace.orElse(Lists.empty());
		if (namespace.isEmpty()){
			parent1 = parent1.addFirst(".");
		}
		let i = 0;
		let size = namespace.size();
		while (i < size){
			parent1 = parent1.addFirst("..");
			i++;
		}
		let stringList = parent1.addLast(child);
		if (this.imports.query().filter((node: Import) => node.hasSameChild(child)).next().isPresent()){
			return this;
		}
		let importString = new Import(stringList, child);
		return new CompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
	}
	withNamespace(namespace: List<string>): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<List<string>>(namespace), this.sources);
	}
	append(element: string): CompileState {
		return new CompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
	}
	pushStructureName(name: string): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeNamespace, this.sources);
	}
	enterDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeNamespace, this.sources);
	}
	exitDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeNamespace, this.sources);
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeNamespace, this.sources);
	}
	resolve(name: string): Option<Definition> {
		return this.definitions.queryReversed().filter((definition: Definition) => definition.isNamed(name)).next();
	}
	clearImports(): CompileState {
		return new CompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
	}
	clearOutput(): CompileState {
		return new CompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources);
	}
	addSource(source: Source): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeNamespace, this.sources.addLast(source));
	}
	findSource(name: string): Option<Source> {
		return this.sources.query().filter((source: Source) => Strings.equalsTo(source.computeName(), name)).next();
	}
	addResolvedImportFromCache(base: string): CompileState {
		if (this.structureNames.query().anyMatch((inner: string) => Strings.equalsTo(inner, base))){
			return this;
		}
		return this.findSource(base).map((source: Source) => this.addResolvedImport(source.computeNamespace(), source.computeName())).orElse(this);
	}
	popStructureName(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeNamespace, this.sources);
	}
}

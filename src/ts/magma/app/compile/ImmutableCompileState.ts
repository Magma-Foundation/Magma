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
	HeadedIter: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	Iter: magma.api.collect, 
	Iters: magma.api.collect, 
	Joiner: magma.api.collect, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
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
	Dependency: magma.app.compile, 
	DivideState: magma.app.compile, 
	ImmutableCompileState: magma.app.compile, 
	ImmutableDivideState: magma.app.compile, 
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
	Location: magma.app, 
	Main: magma.app, 
	Platform: magma.app
]*/
import { CompileState } from "../../../magma/app/compile/CompileState";
import { Import } from "../../../magma/app/compile/Import";
import { List } from "../../../magma/api/collect/list/List";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { Option } from "../../../magma/api/option/Option";
import { Source } from "../../../magma/app/io/Source";
import { Platform } from "../../../magma/app/Platform";
import { Dependency } from "../../../magma/app/compile/Dependency";
import { Joiner } from "../../../magma/api/collect/Joiner";
import { Iter } from "../../../magma/api/collect/Iter";
import { Strings } from "../../../magma/api/text/Strings";
import { Location } from "../../../magma/app/Location";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { Some } from "../../../magma/api/option/Some";
export class ImmutableCompileState implements CompileState {
	imports: List<Import>;
	output: string;
	structureNames: List<string>;
	depth: number;
	definitions: List<Definition>;
	maybeLocation: Option<>;
	sources: List<Source>;
	platform: Platform;
	dependencies: List<Dependency>;
	constructor (imports: List<Import>, output: string, structureNames: List<string>, depth: number, definitions: List<Definition>, maybeLocation: Option<>, sources: List<Source>, platform: Platform, dependencies: List<Dependency>) {
		this.imports = imports;
		this.output = output;
		this.structureNames = structureNames;
		this.depth = depth;
		this.definitions = definitions;
		this.maybeLocation = maybeLocation;
		this.sources = sources;
		this.platform = platform;
		this.dependencies = dependencies;
	}
	join(otherOutput: string): string {
		let joinedImports = this.queryImports().map((anImport: Import) => anImport.generate()/*unknown*/).collect(new Joiner("")).orElse("")/*unknown*/;
		return joinedImports + this.output + otherOutput/*unknown*/;
	}
	querySources(): Iter<Source> {
		return this.sources.query()/*unknown*/;
	}
	createIndent(): string {
		return "\n" + "\t".repeat(this.depth)/*unknown*/;
	}
	findLastStructureName(): Option<string> {
		return this.structureNames.findLast()/*unknown*/;
	}
	isLastWithin(name: string): boolean {
		return this.structureNames.findLast().filter((anObject: string) => Strings.equalsTo(name, anObject)/*unknown*/).isPresent()/*unknown*/;
	}
	addResolvedImport(parent: List<string>, child: string): CompileState {
		if (Platform.PlantUML === this.platform/*unknown*/){
			let name = maybeLocation.map(Location.name).orElse("")/*unknown*/;
			let dependency = new Dependency(name, child)/*unknown*/;
			if (!this/*unknown*/.dependencies.contains(dependency)/*unknown*/){
				return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies.addLast(dependency))/*unknown*/;
			}
		}
		let parent1 = parent/*List<string>*/;
		let namespace = this.maybeLocation.map(Location.namespace).orElse(Lists.empty())/*unknown*/;
		if (namespace.isEmpty()/*unknown*/){
			parent1/*unknown*/ = parent1.addFirst(".")/*unknown*/;
		}
		let i = 0/*unknown*/;
		let size = namespace.size()/*unknown*/;
		while (i < size/*unknown*/){
			parent1/*unknown*/ = parent1.addFirst("..")/*unknown*/;
			i/*unknown*/++;
		}
		let stringList = parent1.addLast(child)/*unknown*/;
		if (this.imports.query().filter((node: Import) => node.hasSameChild(child)/*unknown*/).next().isPresent()/*unknown*/){
			return this/*unknown*/;
		}
		let importString = new Import(stringList, child)/*unknown*/;
		return new ImmutableCompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	withLocation(location: Location): CompileState {
		return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<>(location), this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	append(element: string): CompileState {
		return new ImmutableCompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	pushStructureName(name: string): CompileState {
		return new ImmutableCompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	enterDepth(): CompileState {
		return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	exitDepth(): CompileState {
		return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	resolve(name: string): Option<Definition> {
		return this.definitions.queryReversed().filter((definition: Definition) => definition.isNamed(name)/*unknown*/).next()/*unknown*/;
	}
	clearImports(): CompileState {
		return new ImmutableCompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	clearOutput(): CompileState {
		return new ImmutableCompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	addSource(source: Source): CompileState {
		return new ImmutableCompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources.addLast(source), this.platform, this.dependencies)/*unknown*/;
	}
	findSource(name: string): Option<Source> {
		return this.sources.query().filter((source: Source) => Strings.equalsTo(source.computeName(), name)/*unknown*/).next()/*unknown*/;
	}
	addResolvedImportFromCache(base: string): CompileState {
		if (this.structureNames.query().anyMatch((inner: string) => Strings.equalsTo(inner, base)/*unknown*/)/*unknown*/){
			return this/*unknown*/;
		}
		return this.findSource(base).map((source: Source) => this.addResolvedImport(source.computeNamespace(), source.computeName())/*unknown*/).orElse(this)/*unknown*/;
	}
	popStructureName(): CompileState {
		return new ImmutableCompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform, this.dependencies)/*unknown*/;
	}
	withPlatform(platform: Platform): CompileState {
		return new ImmutableCompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, platform, this.dependencies)/*unknown*/;
	}
	hasPlatform(platform: Platform): boolean {
		return this.platform === platform/*unknown*/;
	}
	findOutput(): string {
		return this.output/*unknown*/;
	}
	queryImports(): Iter<Import> {
		return this.imports.query()/*unknown*/;
	}
	queryDependencies(): Iter<Dependency> {
		return this.dependencies.query()/*unknown*/;
	}
}

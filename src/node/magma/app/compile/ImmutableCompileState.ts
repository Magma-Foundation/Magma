// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head, HeadedQuery, MapHead, RangeHead, SingleHead, ZipHead, Joiner, List, ListCollector, Queries, Query, IOError, Path, None, Option, Some, Err, Ok, Result, Tuple2, Tuple2Impl, Type, CompileState, Definition, FunctionHeader, Parameter, FunctionSegment, ImmutableCompileState]
import { CompileState } from "../../../magma/app/compile/CompileState";
import { Platform } from "../../../magma/app/io/Platform";
import { Location } from "../../../magma/app/io/Location";
import { Option } from "../../../magma/api/option/Option";
import { Source } from "../../../magma/app/io/Source";
import { List } from "../../../magma/api/collect/list/List";
import { Import } from "../../../magma/app/compile/Import";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { None } from "../../../magma/api/option/None";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { Strings } from "../../../jvm/api/text/Strings";
import { Some } from "../../../magma/api/option/Some";
import { Type } from "../../../magma/api/Type";
export class ImmutableCompileState implements CompileState {
	platform: Platform;
	findCurrentLocation: Option<Location>;
	sources: List<Source>;
	imports: List<Import>;
	structureNames: List<string>;
	structures: string;
	functions: string;
	definitions: List<Definition>;
	depth: number;
	definedTypes: List<string>;
	constructor (platform: Platform, findCurrentLocation: Option<Location>, sources: List<Source>, imports: List<Import>, structureNames: List<string>, structures: string, functions: string, definitions: List<Definition>, depth: number, definedTypes: List<string>) {
		this.platform = platform;
		this.findCurrentLocation = findCurrentLocation;
		this.sources = sources;
		this.imports = imports;
		this.structureNames = structureNames;
		this.structures = structures;
		this.functions = functions;
		this.definitions = definitions;
		this.depth = depth;
		this.definedTypes = definedTypes;
	}
	static createInitial(): CompileState {
		return new ImmutableCompileState(Platform/*auto*/.Magma, new None<Location>(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), "", "", Lists/*auto*/.empty(/*auto*/), 0/*auto*/, Lists/*auto*/.empty(/*auto*/));
	}
	hasLastStructureNameOf(name: string): boolean {
		return this/*auto*/.structureNames.findLast(/*auto*/).filter((anObject: string) => Strings/*auto*/.equalsTo(name/*string*/, anObject/*auto*/)).isPresent(/*auto*/);
	}
	withLocation(namespace: Location): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, new Some<Location>(namespace/*Location*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	append(element: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures + element/*string*/, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	pushStructureName(name: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.addLast(name/*string*/), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	enterDepth(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth + 1/*auto*/, this/*auto*/.definedTypes);
	}
	exitDepth(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth - 1/*auto*/, this/*auto*/.definedTypes);
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions.addAll(definitions/*List<Definition>*/), this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	resolve(name: string): Option<Type> {
		return this/*auto*/.definitions.queryReversed(/*auto*/).filter((definition: Definition) => Strings/*auto*/.equalsTo(definition/*auto*/.name(/*auto*/), name/*string*/)).map((definition1: Definition) => definition1/*auto*/.type(/*auto*/)).next(/*auto*/);
	}
	clearImports(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, Lists/*auto*/.empty(/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	clear(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, "", "", this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	addSource(source: Source): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources.addLast(source/*Source*/), this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	addResolvedImportFromCache(base: string): CompileState {
		if (this/*auto*/.structureNames.query(/*auto*/).anyMatch((inner: string) => Strings/*auto*/.equalsTo(inner/*auto*/, base/*string*/))) {
			return this/*auto*/;
		}
		return this/*auto*/.findSource(base/*string*/).map((source: Source) => this/*auto*/.addResolvedImportWithNamespace(source/*Source*/.computeNamespace(/*auto*/), source/*Source*/.computeName(/*auto*/))).orElse(this/*auto*/);
	}
	popStructureName(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.removeLast(/*auto*/).orElse(this/*auto*/.structureNames), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	mapLocation(mapper: (arg0 : Location) => Location): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation.map(mapper/*(arg0 : Location) => Location*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	withPlatform(platform: Platform): CompileState {
		return new ImmutableCompileState(platform/*Platform*/, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	addFunction(function: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions + function/*string*/, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	findDefinedTypes(): List<string> {
		return this/*auto*/.definedTypes;
	}
	defineType(name: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes.addLast(name/*string*/));
	}
	clearDefinedTypes(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, Lists/*auto*/.empty(/*auto*/));
	}
	join(): string {
		return this/*auto*/.structures + this/*auto*/.functions;
	}
	addResolvedImportWithNamespace(oldParent: List<string>, child: string): CompileState {
		let namespace = this/*auto*/.findCurrentLocation.map((location: Location) => location/*auto*/.namespace(/*auto*/)).orElse(Lists/*auto*/.empty(/*auto*/));
		let newParent = oldParent/*List<string>*/;
		if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
			if (namespace/*Location*/.isEmpty(/*auto*/)) {
				newParent/*auto*/ = newParent/*auto*/.addFirst(".");
			}
			let i = 0/*auto*/;
			let size = namespace/*Location*/.size(/*auto*/);
			while (i/*auto*/ < size/*auto*/) {
				newParent/*auto*/ = newParent/*auto*/.addFirst("..");
				i/*auto*/++;
			}
		}
		if (this/*auto*/.imports.query(/*auto*/).filter((node: Import) => Strings/*auto*/.equalsTo(node/*auto*/.child(/*auto*/), child/*string*/)).next(/*auto*/).isPresent(/*auto*/)) {
			return this/*auto*/;
		}
		let importString = new Import(newParent/*auto*/, child/*string*/);
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports.addLast(importString/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
	}
	findSource(name: string): Option<Source> {
		return this/*auto*/.sources.query(/*auto*/).filter((source: Source) => Strings/*auto*/.equalsTo(source/*Source*/.computeName(/*auto*/), name/*string*/)).next(/*auto*/);
	}
	isPlatform(platform: Platform): boolean {
		return platform/*Platform*/ === this/*auto*/.platform(/*auto*/);
	}
	createIndent(): string {
		let indent = this/*auto*/.depth(/*auto*/);
		return "\n" + "\t".repeat(indent/*string*/);
	}
	functionName(): string {
		return "temp";
	}
	findLastStructureName(): Option<string> {
		return this/*auto*/.structureNames(/*auto*/).findLast(/*auto*/);
	}
}

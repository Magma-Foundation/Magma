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
	constructor (platform: Platform, findCurrentLocation: Option<Location>, sources: List<Source>, imports: List<Import>, structureNames: List<string>, structures: string, functions: string, definitions: List<Definition>, depth: number) {
		this.platform = platform;
		this.findCurrentLocation = findCurrentLocation;
		this.sources = sources;
		this.imports = imports;
		this.structureNames = structureNames;
		this.structures = structures;
		this.functions = functions;
		this.definitions = definitions;
		this.depth = depth;
	}
	static createInitial(): CompileState {
		return new ImmutableCompileState(Platform/*auto*/.Magma, new None<Location>(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), "", "", Lists/*auto*/.empty(/*auto*/), 0/*auto*/);
	}
	hasLastStructureNameOf(name: string): boolean {
		return this/*auto*/.structureNames.findLast(/*auto*/).filter((anObject: string) => Strings/*auto*/.equalsTo(name/*auto*/, anObject/*auto*/)).isPresent(/*auto*/);
	}
	withLocation(namespace: Location): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, new Some<Location>(namespace/*auto*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	append(element: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures + element/*auto*/, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	pushStructureName(name: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.addLast(name/*auto*/), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	enterDepth(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth + 1/*auto*/);
	}
	exitDepth(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth - 1/*auto*/);
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions.addAll(definitions/*auto*/), this/*auto*/.depth);
	}
	resolve(name: string): Option<Type> {
		return this/*auto*/.definitions.queryReversed(/*auto*/).filter((definition: Definition) => Strings/*auto*/.equalsTo(definition/*auto*/.name(/*auto*/), name/*auto*/)).map((definition1: Definition) => definition1/*auto*/.type(/*auto*/)).next(/*auto*/);
	}
	clearImports(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, Lists/*auto*/.empty(/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	clear(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, "", "", this/*auto*/.definitions, this/*auto*/.depth);
	}
	addSource(source: Source): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources.addLast(source/*auto*/), this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	addResolvedImportFromCache(base: string): CompileState {
		if (this/*auto*/.structureNames.query(/*auto*/).anyMatch((inner: string) => Strings/*auto*/.equalsTo(inner/*auto*/, base/*auto*/))) {
			return this/*auto*/;
		}
		return this/*auto*/.findSource(base/*auto*/).map((source: Source) => this/*auto*/.addResolvedImportWithNamespace(source/*auto*/.computeNamespace(/*auto*/), source/*auto*/.computeName(/*auto*/))).orElse(this/*auto*/);
	}
	popStructureName(): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.removeLast(/*auto*/).orElse(this/*auto*/.structureNames), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	mapLocation(mapper: (arg0 : Location) => Location): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation.map(mapper/*auto*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	withPlatform(platform: Platform): CompileState {
		return new ImmutableCompileState(platform/*auto*/, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	addFunction(function: string): CompileState {
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions + function/*auto*/, this/*auto*/.definitions, this/*auto*/.depth);
	}
	join(): string {
		return this/*auto*/.structures + this/*auto*/.functions;
	}
	addResolvedImportWithNamespace(oldParent: List<string>, child: string): CompileState {
		let namespace: List<string> = this/*auto*/.findCurrentLocation.map((location: Location) => location/*auto*/.namespace(/*auto*/)).orElse(Lists/*auto*/.empty(/*auto*/));
		List/*auto*/ < String/*auto*/ > newParent/*auto*/ = oldParent/*auto*/;
		if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
			if (namespace/*auto*/.isEmpty(/*auto*/)) {
				newParent/*auto*/ = newParent/*auto*/.addFirst(".");
			}
			let i: number = 0/*auto*/;
			let size: number = namespace/*auto*/.size(/*auto*/);
			while (i/*auto*/ < size/*auto*/) {
				newParent/*auto*/ = newParent/*auto*/.addFirst("..");
				i/*auto*/++;
			}
		}
		if (this/*auto*/.imports.query(/*auto*/).filter((node: Import) => Strings/*auto*/.equalsTo(node/*auto*/.child(/*auto*/), child/*auto*/)).next(/*auto*/).isPresent(/*auto*/)) {
			return this/*auto*/;
		}
		let importString: Import = new Import(newParent/*auto*/, child/*auto*/);
		return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports.addLast(importString/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
	}
	findSource(name: string): Option<Source> {
		return this/*auto*/.sources.query(/*auto*/).filter((source: Source) => Strings/*auto*/.equalsTo(source/*auto*/.computeName(/*auto*/), name/*auto*/)).next(/*auto*/);
	}
	isPlatform(platform: Platform): boolean {
		return platform/*auto*/ === this/*auto*/.platform(/*auto*/);
	}
	createIndent(): string {
		let indent: number = this/*auto*/.depth(/*auto*/);
		return "\n" + "\t".repeat(indent/*auto*/);
	}
	functionName(): string {
		return "temp";
	}
	findLastStructureName(): Option<string> {
		return this/*auto*/.structureNames(/*auto*/).findLast(/*auto*/);
	}
}

import { Import } from "../../../magma/app/compile/Import";
import { List } from "../../../magma/api/collect/list/List";
import { Definition } from "../../../magma/app/Definition";
import { Location } from "../../../magma/app/Location";
import { Option } from "../../../magma/api/option/Option";
import { Source } from "../../../magma/app/Source";
import { Platform } from "../../../magma/app/Platform";
import { Lists } from "../../../jvm/api/collect/list/Lists";
import { None } from "../../../magma/api/option/None";
import { Strings } from "../../../jvm/api/text/Strings";
import { Some } from "../../../magma/api/option/Some";
import { Type } from "../../../magma/api/Type";
export class CompileState {
	imports: List<Import>;
	output: string;
	structureNames: List<string>;
	depth: number;
	definitions: List<Definition>;
	maybeLocation: Option<Location>;
	sources: List<Source>;
	platform: Platform;
	constructor (imports: List<Import>, output: string, structureNames: List<string>, depth: number, definitions: List<Definition>, maybeLocation: Option<Location>, sources: List<Source>, platform: Platform) {
		this.imports = imports;
		this.output = output;
		this.structureNames = structureNames;
		this.depth = depth;
		this.definitions = definitions;
		this.maybeLocation = maybeLocation;
		this.sources = sources;
		this.platform = platform;
	}
	static createInitial(): CompileState {
		return new CompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<Location>(), Lists.empty(), Platform.Magma);
	}
	isLastWithin(name: string): boolean {
		return this.structureNames.findLast().filter((anObject: string) => Strings.equalsTo(name, anObject)).isPresent();
	}
	addResolvedImport(oldParent: List<string>, child: string): CompileState {
		let namespace = this.maybeLocation.map((location: Location) => location.namespace()).orElse(Lists.empty());
		let newParent = oldParent;
		if (Platform.TypeScript === this.platform) {
			if (namespace.isEmpty()) {
				newParent = newParent.addFirst(".");
			}
			let i = 0;
			let size = namespace.size();
			while (i < size) {
				newParent = newParent.addFirst("..");
				i++;
			}
		}
		if (this.imports.query().filter((node: Import) => Strings.equalsTo(node.child(), child)).next().isPresent()) {
			return this;
		}
		let importString = new Import(newParent, child);
		return new CompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	withLocation(namespace: Location): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<Location>(namespace), this.sources, this.platform);
	}
	append(element: string): CompileState {
		return new CompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	pushStructureName(name: string): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	enterDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	exitDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	defineAll(definitions: List<Definition>): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeLocation, this.sources, this.platform);
	}
	resolve(name: string): Option<Type> {
		return this.definitions.queryReversed().filter((definition: Definition) => Strings.equalsTo(definition.name(), name)).map((definition1: Definition) => definition1.type()).next();
	}
	clearImports(): CompileState {
		return new CompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	clearOutput(): CompileState {
		return new CompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	addSource(source: Source): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources.addLast(source), this.platform);
	}
	findSource(name: string): Option<Source> {
		return this.sources.query().filter((source: Source) => Strings.equalsTo(source.computeName(), name)).next();
	}
	addResolvedImportFromCache(base: string): CompileState {
		if (this.structureNames.query().anyMatch((inner: string) => Strings.equalsTo(inner, base))) {
			return this;
		}
		return this.findSource(base).map((source: Source) => this.addResolvedImport(source.computeNamespace(), source.computeName())).orElse(this);
	}
	popStructureName(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mapLocation(mapper: (arg0 : Location) => Location): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation.map(mapper), this.sources, this.platform);
	}
	withPlatform(platform: Platform): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, platform);
	}
}

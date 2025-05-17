#include "./ImmutableCompileState.h"
export class ImmutableCompileState implements CompileState {
	platform: Platform;
	findCurrentLocation: Option<Location>;
	sources: List<Source>;
	imports: List<Import>;
	structureNames: List<&[I8]>;
	structures: &[I8];
	functions: &[I8];
	definitions: List<Definition>;
	depth: number;
	constructor (platform: Platform, findCurrentLocation: Option<Location>, sources: List<Source>, imports: List<Import>, structureNames: List<&[I8]>, structures: &[I8], functions: &[I8], definitions: List<Definition>, depth: number) {
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
}

static createInitial(): CompileState {
	return new ImmutableCompileState(Platform.Magma, new None<Location>(), Lists.empty(), Lists.empty(), Lists.empty(), "", "", Lists.empty(), 0);
}
hasLastStructureNameOf(name: &[I8]): Bool {
	return this.structureNames.findLast().filter((anObject: &[I8]) => Strings.equalsTo(name, anObject)).isPresent();
}
withLocation(namespace: Location): CompileState {
	return new ImmutableCompileState(this.platform, new Some<Location>(namespace), this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
append(element: &[I8]): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures + element, this.functions, this.definitions, this.depth);
}
pushStructureName(name: &[I8]): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames.addLast(name), this.structures, this.functions, this.definitions, this.depth);
}
enterDepth(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth + 1);
}
exitDepth(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth - 1);
}
defineAll(definitions: List<Definition>): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions.addAll(definitions), this.depth);
}
resolve(name: &[I8]): Option<Type> {
	return this.definitions.queryReversed().filter((definition: Definition) => Strings.equalsTo(definition.name(), name)).map((definition1: Definition) => definition1.type()).next();
}
clearImports(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, Lists.empty(), this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
clear(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, "", "", this.definitions, this.depth);
}
addSource(source: Source): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources.addLast(source), this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
addResolvedImportFromCache(base: &[I8]): CompileState {
	if (this.structureNames.query().anyMatch((inner: &[I8]) => Strings.equalsTo(inner, base))) {
		return this;
	}
	return this.findSource(base).map((source: Source) => this.addResolvedImportWithNamespace(source.computeNamespace(), source.computeName())).orElse(this);
}
popStructureName(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames.removeLast().orElse(this.structureNames), this.structures, this.functions, this.definitions, this.depth);
}
mapLocation(mapper: (arg0 : Location) => Location): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation.map(mapper), this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
withPlatform(platform: Platform): CompileState {
	return new ImmutableCompileState(platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
addFunction(function: &[I8]): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions + function, this.definitions, this.depth);
}
join(): &[I8] {
	return this.structures + this.functions;
}
addResolvedImportWithNamespace(oldParent: List<&[I8]>, child: &[I8]): CompileState {
	let namespace: List<&[I8]> = this.findCurrentLocation.map((location: Location) => location.namespace()).orElse(Lists.empty());
	List < String > newParent = oldParent;
	if (Platform.TypeScript === this.platform) {
		if (namespace.isEmpty()) {
			newParent = newParent.addFirst(".");
		}
		let i: number = 0;
		let size: number = namespace.size();
		while (i < size) {
			newParent = newParent.addFirst("..");
			i++;
		}
	}
	if (this.imports.query().filter((node: Import) => Strings.equalsTo(node.child(), child)).next().isPresent()) {
		return this;
	}
	let importString: Import = new Import(newParent, child);
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports.addLast(importString), this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
findSource(name: &[I8]): Option<Source> {
	return this.sources.query().filter((source: Source) => Strings.equalsTo(source.computeName(), name)).next();
}
isPlatform(platform: Platform): Bool {
	return platform === this.platform();
}
createIndent(): &[I8] {
	let indent: number = this.depth();
	return "\n" + "\t".repeat(indent);
}
findLastStructureName(): Option<&[I8]> {
	return this.structureNames().findLast();
}
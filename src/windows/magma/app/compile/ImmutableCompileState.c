#include "./ImmutableCompileState.h"
export class ImmutableCompileState implements CompileState {
	mut platform: Platform;
	mut findCurrentLocation: Option<Location>;
	mut sources: List<Source>;
	mut imports: List<Import>;
	mut structureNames: List<&[I8]>;
	mut structures: &[I8];
	mut functions: &[I8];
	mut definitions: List<Definition>;
	mut depth: number;
	constructor (mut platform: Platform, mut findCurrentLocation: Option<Location>, mut sources: List<Source>, mut imports: List<Import>, mut structureNames: List<&[I8]>, mut structures: &[I8], mut functions: &[I8], mut definitions: List<Definition>, mut depth: number) {
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

mut static createInitial(): CompileState {
	return new ImmutableCompileState(Platform.Magma, new None<Location>(), Lists.empty(), Lists.empty(), Lists.empty(), "", "", Lists.empty(), 0);
}
mut hasLastStructureNameOf(name: &[I8]): Bool {
	return this.structureNames.findLast().filter((mut anObject: &[I8]) => Strings.equalsTo(name, anObject)).isPresent();
}
mut withLocation(namespace: Location): CompileState {
	return new ImmutableCompileState(this.platform, new Some<Location>(namespace), this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
mut append(element: &[I8]): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures + element, this.functions, this.definitions, this.depth);
}
mut pushStructureName(name: &[I8]): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames.addLast(name), this.structures, this.functions, this.definitions, this.depth);
}
mut enterDepth(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth + 1);
}
mut exitDepth(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth - 1);
}
mut defineAll(definitions: List<Definition>): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions.addAll(definitions), this.depth);
}
mut resolve(name: &[I8]): Option<Type> {
	return this.definitions.queryReversed().filter((mut definition: Definition) => Strings.equalsTo(definition.name(), name)).map((mut definition1: Definition) => definition1.type()).next();
}
mut clearImports(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, Lists.empty(), this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
mut clear(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, "", "", this.definitions, this.depth);
}
mut addSource(source: Source): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources.addLast(source), this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
mut addResolvedImportFromCache(base: &[I8]): CompileState {
	if (this.structureNames.query().anyMatch((mut inner: &[I8]) => Strings.equalsTo(inner, base))) {
		return this;
	}
	return this.findSource(base).map((mut source: Source) => this.addResolvedImportWithNamespace(source.computeNamespace(), source.computeName())).orElse(this);
}
mut popStructureName(): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames.removeLast().orElse(this.structureNames), this.structures, this.functions, this.definitions, this.depth);
}
mut mapLocation(mapper: (arg0 : Location) => Location): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation.map(mapper), this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
mut withPlatform(platform: Platform): CompileState {
	return new ImmutableCompileState(platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
mut addFunction(function: &[I8]): CompileState {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions + function, this.definitions, this.depth);
}
mut join(): &[I8] {
	return this.structures + this.functions;
}
mut addResolvedImportWithNamespace(oldParent: List<&[I8]>, child: &[I8]): CompileState {
	let namespace = this.findCurrentLocation.map((mut location: Location) => location.namespace()).orElse(Lists.empty());
	let mut newParent = oldParent;
	if (Platform.TypeScript === this.platform) {
		if (namespace.isEmpty()) {
			newParent = newParent.addFirst(".");
		}
		let mut i = 0;
		let size = namespace.size();
		while (i < size) {
			newParent = newParent.addFirst("..");
			i++;
		}
	}
	if (this.imports.query().filter((mut node: Import) => Strings.equalsTo(node.child(), child)).next().isPresent()) {
		return this;
	}
	let importString = new Import(newParent, child);
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports.addLast(importString), this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
mut findSource(name: &[I8]): Option<Source> {
	return this.sources.query().filter((mut source: Source) => Strings.equalsTo(source.computeName(), name)).next();
}
mut isPlatform(platform: Platform): Bool {
	return platform === this.platform();
}
mut createIndent(): &[I8] {
	let indent: number = this.depth();
	return "\n" + "\t".repeat(indent);
}
mut findLastStructureName(): Option<&[I8]> {
	return this.structureNames().findLast();
}
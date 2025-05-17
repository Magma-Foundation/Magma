#include "./ImmutableCompileState.h"
export class ImmutableCompileState implements CompileState {
	Platform platform;
	Option<Location> findCurrentLocation;
	List<Source> sources;
	List<Import> imports;
	List<&[I8]> structureNames;
	&[I8] structures;
	&[I8] functions;
	List<Definition> definitions;
	number depth;
	constructor (Platform platform, Option<Location> findCurrentLocation, List<Source> sources, List<Import> imports, List<&[I8]> structureNames, &[I8] structures, &[I8] functions, List<Definition> definitions, number depth) {
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

static CompileState createInitial() {
	return new ImmutableCompileState(Platform.Magma, new None<Location>(), Lists.empty(), Lists.empty(), Lists.empty(), "", "", Lists.empty(), 0);
}
Bool hasLastStructureNameOf(&[I8] name) {
	return this.structureNames.findLast().filter((&[I8] anObject) => Strings.equalsTo(name, anObject)).isPresent();
}
CompileState withLocation(Location namespace) {
	return new ImmutableCompileState(this.platform, new Some<Location>(namespace), this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
CompileState append(&[I8] element) {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures + element, this.functions, this.definitions, this.depth);
}
CompileState pushStructureName(&[I8] name) {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames.addLast(name), this.structures, this.functions, this.definitions, this.depth);
}
CompileState enterDepth() {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth + 1);
}
CompileState exitDepth() {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth - 1);
}
CompileState defineAll(List<Definition> definitions) {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions.addAll(definitions), this.depth);
}
Option<Type> resolve(&[I8] name) {
	return this.definitions.queryReversed().filter((Definition definition) => Strings.equalsTo(definition.name(), name)).map((Definition definition1) => definition1.type()).next();
}
CompileState clearImports() {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, Lists.empty(), this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
CompileState clear() {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, "", "", this.definitions, this.depth);
}
CompileState addSource(Source source) {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources.addLast(source), this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
CompileState addResolvedImportFromCache(&[I8] base) {
	if (this.structureNames.query().anyMatch((&[I8] inner) => Strings.equalsTo(inner, base))) {
		return this;
	}
	return this.findSource(base).map((Source source) => this.addResolvedImportWithNamespace(source.computeNamespace(), source.computeName())).orElse(this);
}
CompileState popStructureName() {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames.removeLast().orElse(this.structureNames), this.structures, this.functions, this.definitions, this.depth);
}
CompileState mapLocation((arg0 : Location) => Location mapper) {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation.map(mapper), this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
CompileState withPlatform(Platform platform) {
	return new ImmutableCompileState(platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
CompileState addFunction(&[I8] function) {
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports, this.structureNames, this.structures, this.functions + function, this.definitions, this.depth);
}
&[I8] join() {
	return this.structures + this.functions;
}
CompileState addResolvedImportWithNamespace(List<&[I8]> oldParent, &[I8] child) {
	List<&[I8]> namespace = this.findCurrentLocation.map((Location location) => location.namespace()).orElse(Lists.empty());
	List < String > newParent = oldParent;
	if (Platform.TypeScript === this.platform) {
		if (namespace.isEmpty()) {
			newParent = newParent.addFirst(".");
		}
		number i = 0;
		number size = namespace.size();
		while (i < size) {
			newParent = newParent.addFirst("..");
			i++;
		}
	}
	if (this.imports.query().filter((Import node) => Strings.equalsTo(node.child(), child)).next().isPresent()) {
		return this;
	}
	Import importString = new Import(newParent, child);
	return new ImmutableCompileState(this.platform, this.findCurrentLocation, this.sources, this.imports.addLast(importString), this.structureNames, this.structures, this.functions, this.definitions, this.depth);
}
Option<Source> findSource(&[I8] name) {
	return this.sources.query().filter((Source source) => Strings.equalsTo(source.computeName(), name)).next();
}
Bool isPlatform(Platform platform) {
	return platform === this.platform();
}
&[I8] createIndent() {
	number indent = this.depth();
	return "\n" + "\t".repeat(indent);
}
Option<&[I8]> findLastStructureName() {
	return this.structureNames().findLast();
}
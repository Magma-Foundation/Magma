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
	return new ImmutableCompileState(Platform/*auto*/.Magma, new None<Location>(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), "", "", Lists/*auto*/.empty(/*auto*/), 0/*auto*/);
}
auto temp(&[I8] anObject) {
	return Strings/*auto*/.equalsTo(name/*auto*/, anObject/*auto*/);
}
Bool hasLastStructureNameOf(&[I8] name) {
	return this/*auto*/.structureNames.findLast(/*auto*/).filter(lambdaDefinition/*auto*/).isPresent(/*auto*/);
}
CompileState withLocation(Location namespace) {
	return new ImmutableCompileState(this/*auto*/.platform, new Some<Location>(namespace/*auto*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
CompileState append(&[I8] element) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures + element/*auto*/, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
CompileState pushStructureName(&[I8] name) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.addLast(name/*auto*/), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
CompileState enterDepth() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth + 1/*auto*/);
}
CompileState exitDepth() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth - 1/*auto*/);
}
CompileState defineAll(List<Definition> definitions) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions.addAll(definitions/*auto*/), this/*auto*/.depth);
}
auto temp(Definition definition) {
	return Strings/*auto*/.equalsTo(definition/*auto*/.name(/*auto*/), name/*auto*/);
}
auto temp(Definition definition1) {
	return definition1/*auto*/.type(/*auto*/);
}
Option<Type> resolve(&[I8] name) {
	return this/*auto*/.definitions.queryReversed(/*auto*/).filter(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).next(/*auto*/);
}
CompileState clearImports() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, Lists/*auto*/.empty(/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
CompileState clear() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, "", "", this/*auto*/.definitions, this/*auto*/.depth);
}
CompileState addSource(Source source) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources.addLast(source/*auto*/), this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
auto temp(&[I8] inner) {
	return Strings/*auto*/.equalsTo(inner/*auto*/, base/*auto*/);
}
auto temp(Source source) {
	return this/*auto*/.addResolvedImportWithNamespace(source/*auto*/.computeNamespace(/*auto*/), source/*auto*/.computeName(/*auto*/));
}
CompileState addResolvedImportFromCache(&[I8] base) {
	if (this/*auto*/.structureNames.query(/*auto*/).anyMatch(lambdaDefinition/*auto*/)) {
		return this/*auto*/;
	}
	return this/*auto*/.findSource(base/*auto*/).map(lambdaDefinition/*auto*/).orElse(this/*auto*/);
}
CompileState popStructureName() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.removeLast(/*auto*/).orElse(this/*auto*/.structureNames), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
CompileState mapLocation((arg0 : Location) => Location mapper) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation.map(mapper/*auto*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
CompileState withPlatform(Platform platform) {
	return new ImmutableCompileState(platform/*auto*/, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
CompileState addFunction(&[I8] function) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions + function/*auto*/, this/*auto*/.definitions, this/*auto*/.depth);
}
&[I8] join() {
	return this/*auto*/.structures + this/*auto*/.functions;
}
auto temp(Location location) {
	return location/*auto*/.namespace(/*auto*/);
}
auto temp(Import node) {
	return Strings/*auto*/.equalsTo(node/*auto*/.child(/*auto*/), child/*auto*/);
}
CompileState addResolvedImportWithNamespace(List<&[I8]> oldParent, &[I8] child) {
	List<&[I8]> namespace = this/*auto*/.findCurrentLocation.map(lambdaDefinition/*auto*/).orElse(Lists/*auto*/.empty(/*auto*/));
	List/*auto*/ < String/*auto*/ > newParent/*auto*/ = oldParent/*auto*/;
	if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
		if (namespace/*auto*/.isEmpty(/*auto*/)) {
			newParent/*auto*/ = newParent/*auto*/.addFirst(".");
		}
		number i = 0/*auto*/;
		number size = namespace/*auto*/.size(/*auto*/);
		while (i/*auto*/ < size/*auto*/) {
			newParent/*auto*/ = newParent/*auto*/.addFirst("..");
			i/*auto*/++;
		}
	}
	if (this/*auto*/.imports.query(/*auto*/).filter(lambdaDefinition/*auto*/).next(/*auto*/).isPresent(/*auto*/)) {
		return this/*auto*/;
	}
	Import importString = new Import(newParent/*auto*/, child/*auto*/);
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports.addLast(importString/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth);
}
auto temp(Source source) {
	return Strings/*auto*/.equalsTo(source/*auto*/.computeName(/*auto*/), name/*auto*/);
}
Option<Source> findSource(&[I8] name) {
	return this/*auto*/.sources.query(/*auto*/).filter(lambdaDefinition/*auto*/).next(/*auto*/);
}
Bool isPlatform(Platform platform) {
	return platform/*auto*/ === this/*auto*/.platform(/*auto*/);
}
&[I8] createIndent() {
	number indent = this/*auto*/.depth(/*auto*/);
	return "\n" + "\t".repeat(indent/*auto*/);
}
&[I8] functionName() {
	return "temp";
}
Option<&[I8]> findLastStructureName() {
	return this/*auto*/.structureNames(/*auto*/).findLast(/*auto*/);
}
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
	List<&[I8]> definedTypes;
	constructor (Platform platform, Option<Location> findCurrentLocation, List<Source> sources, List<Import> imports, List<&[I8]> structureNames, &[I8] structures, &[I8] functions, List<Definition> definitions, number depth, List<&[I8]> definedTypes) {
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
}

static CompileState createInitial() {
	return new ImmutableCompileState(Platform/*auto*/.Magma, new None<Location>(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), "", "", Lists/*auto*/.empty(/*auto*/), 0/*auto*/, Lists/*auto*/.empty(/*auto*/));
}
auto temp(&[I8] anObject) {
	return Strings/*auto*/.equalsTo(name/*&[I8]*/, anObject/*auto*/);
}
Bool hasLastStructureNameOf(&[I8] name) {
	return this/*auto*/.structureNames.findLast(/*auto*/).filter(lambdaDefinition/*auto*/).isPresent(/*auto*/);
}
CompileState withLocation(Location namespace) {
	return new ImmutableCompileState(this/*auto*/.platform, new Some<Location>(namespace/*Location*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState append(&[I8] element) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures + element/*&[I8]*/, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState pushStructureName(&[I8] name) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.addLast(name/*&[I8]*/), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState enterDepth() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth + 1/*auto*/, this/*auto*/.definedTypes);
}
CompileState exitDepth() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth - 1/*auto*/, this/*auto*/.definedTypes);
}
CompileState defineAll(List<Definition> definitions) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions.addAll(definitions/*List<Definition>*/), this/*auto*/.depth, this/*auto*/.definedTypes);
}
auto temp(Definition definition) {
	return Strings/*auto*/.equalsTo(definition/*Definition*/.name(/*auto*/), name/*&[I8]*/);
}
auto temp(Definition definition1) {
	return definition1/*auto*/.type(/*auto*/);
}
Option<Type> resolve(&[I8] name) {
	return this/*auto*/.definitions.queryReversed(/*auto*/).filter(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).next(/*auto*/);
}
CompileState clearImports() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, Lists/*auto*/.empty(/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState clear() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, "", "", this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState addSource(Source source) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources.addLast(source/*Source*/), this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
auto temp(&[I8] inner) {
	return Strings/*auto*/.equalsTo(inner/*auto*/, base/*&[I8]*/);
}
auto temp(Source source) {
	return this/*auto*/.addResolvedImportWithNamespace(source/*Source*/.computeNamespace(/*auto*/), source/*Source*/.computeName(/*auto*/));
}
CompileState addResolvedImportFromCache(&[I8] base) {
	if (this/*auto*/.structureNames.query(/*auto*/).anyMatch(lambdaDefinition/*auto*/)) {
		return this/*auto*/;
	}
	return this/*auto*/.findSource(base/*&[I8]*/).map(lambdaDefinition/*auto*/).orElse(this/*auto*/);
}
CompileState popStructureName() {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames.removeLast(/*auto*/).orElse(this/*auto*/.structureNames), this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState mapLocation((arg0 : Location) => Location mapper) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation.map(mapper/*(arg0 : Location) => Location*/), this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState withPlatform(Platform platform) {
	return new ImmutableCompileState(platform/*Platform*/, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
CompileState addFunction(&[I8] function) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions + function/*&[I8]*/, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
List<&[I8]> findDefinedTypes() {
	return this/*auto*/.definedTypes;
}
CompileState defineType(&[I8] name) {
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports, this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes.addLast(name/*&[I8]*/));
}
&[I8] join() {
	return this/*auto*/.structures + this/*auto*/.functions;
}
auto temp(Location location) {
	return location/*auto*/.namespace(/*auto*/);
}
auto temp(Import node) {
	return Strings/*auto*/.equalsTo(node/*auto*/.child(/*auto*/), child/*&[I8]*/);
}
CompileState addResolvedImportWithNamespace(List<&[I8]> oldParent, &[I8] child) {
	var namespace = this/*auto*/.findCurrentLocation.map(lambdaDefinition/*auto*/).orElse(Lists/*auto*/.empty(/*auto*/));
	var newParent = oldParent/*List<&[I8]>*/;
	if (Platform/*auto*/.TypeScript === this/*auto*/.platform) {
		if (namespace/*Location*/.isEmpty(/*auto*/)) {
			newParent/*auto*/ = newParent/*auto*/.addFirst(".");
		}
		var i = 0/*auto*/;
		var size = namespace/*Location*/.size(/*auto*/);
		while (i/*auto*/ < size/*auto*/) {
			newParent/*auto*/ = newParent/*auto*/.addFirst("..");
			i/*auto*/++;
		}
	}
	if (this/*auto*/.imports.query(/*auto*/).filter(lambdaDefinition/*auto*/).next(/*auto*/).isPresent(/*auto*/)) {
		return this/*auto*/;
	}
	var importString = new Import(newParent/*auto*/, child/*&[I8]*/);
	return new ImmutableCompileState(this/*auto*/.platform, this/*auto*/.findCurrentLocation, this/*auto*/.sources, this/*auto*/.imports.addLast(importString/*auto*/), this/*auto*/.structureNames, this/*auto*/.structures, this/*auto*/.functions, this/*auto*/.definitions, this/*auto*/.depth, this/*auto*/.definedTypes);
}
auto temp(Source source) {
	return Strings/*auto*/.equalsTo(source/*Source*/.computeName(/*auto*/), name/*&[I8]*/);
}
Option<Source> findSource(&[I8] name) {
	return this/*auto*/.sources.query(/*auto*/).filter(lambdaDefinition/*auto*/).next(/*auto*/);
}
Bool isPlatform(Platform platform) {
	return platform/*Platform*/ === this/*auto*/.platform(/*auto*/);
}
&[I8] createIndent() {
	var indent = this/*auto*/.depth(/*auto*/);
	return "\n" + "\t".repeat(indent/*&[I8]*/);
}
&[I8] functionName() {
	return "temp";
}
Option<&[I8]> findLastStructureName() {
	return this/*auto*/.structureNames(/*auto*/).findLast(/*auto*/);
}
#include "./CompileState.h"
export class CompileState {
	mut imports: List<>;
	mut output: &[I8];
	mut structureNames: List<&[I8]>;
	mut depth: number;
	mut definitions: List<Definition>;
	mut maybeLocation: Option<>;
	mut sources: List<>;
	constructor (mut imports: List<>, mut output: &[I8], mut structureNames: List<&[I8]>, mut depth: number, mut definitions: List<Definition>, mut maybeLocation: Option<>, mut sources: List<>) {
		this.imports = imports;
		this.output = output;
		this.structureNames = structureNames;
		this.depth = depth;
		this.definitions = definitions;
		this.maybeLocation = maybeLocation;
		this.sources = sources;
	}
	mut static createInitial(): CompileState {
		return new CompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<>(), Lists.empty(), Main.Platform.Magma);
	}
	mut isLastWithin(name: &[I8]): Bool {
		return this.structureNames.findLast().filter((mut anObject: &[I8]) => Strings.equalsTo(name, anObject)).isPresent();
	}
	mut addResolvedImport(oldParent: List<&[I8]>, child: &[I8]): CompileState {
		let namespace = /* this.maybeLocation
                .map((Main.Location location) -> location.namespace())
                .orElse(Lists.empty())*/;
		let mut newParent = oldParent;
		if (Main.Platform.TypeScript === this.platform) {
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
		if (/*this.imports
                .query()
                .filter((Main.Import node) -> Strings.equalsTo(node.child(), child))
                .next()
                .isPresent()*/) {
			return this;
		}
		let importString = /* new Main.Import(newParent, child)*/;
		return new CompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut withLocation(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<>(namespace), this.sources, this.platform);
	}
	mut append(element: &[I8]): CompileState {
		return new CompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut pushStructureName(name: &[I8]): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut enterDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut exitDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut defineAll(definitions: List<Definition>): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeLocation, this.sources, this.platform);
	}
	mut resolve(name: &[I8]): Option<Type> {
		return this.definitions.queryReversed().filter((mut definition: Definition) => Strings.equalsTo(definition.name(), name)).map((mut definition1: Definition) => definition1.type()).next();
	}
	mut clearImports(): CompileState {
		return new CompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut clearOutput(): CompileState {
		return new CompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut addSource(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources.addLast(source), this.platform);
	}
	mut findSource(name: &[I8]): Option<> {
		/*return this.sources.query()
                .filter((Main.Source source) -> Strings.equalsTo(source.computeName(), name))
                .next()*/;
	}
	mut addResolvedImportFromCache(base: &[I8]): CompileState {
		if (this.structureNames.query().anyMatch((mut inner: &[I8]) => Strings.equalsTo(inner, base))) {
			return this;
		}
		/*return this.findSource(base)
                .map((Main.Source source) -> this.addResolvedImport(source.computeNamespace(), source.computeName()))
                .orElse(this)*/;
	}
	mut popStructureName(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut mapLocation(mapper: Function<>): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation.map(mapper), this.sources, this.platform);
	}
	mut withPlatform(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, platform);
	}
}

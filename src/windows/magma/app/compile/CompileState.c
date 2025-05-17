#include "./CompileState.h"
export interface CompileState {
	findLastStructureName(): Option<&[I8]>;
	createIndent(): &[I8];
	isPlatform(platform: Platform): Bool;
	hasLastStructureNameOf(name: &[I8]): Bool;
	addResolvedImportFromCache(base: &[I8]): CompileState;
	addResolvedImportWithNamespace(namespace: List<&[I8]>, child: &[I8]): CompileState;
	withLocation(namespace: Location): CompileState;
	append(element: &[I8]): CompileState;
	pushStructureName(name: &[I8]): CompileState;
	enterDepth(): CompileState;
	exitDepth(): CompileState;
	defineAll(definitions: List<Definition>): CompileState;
	resolve(name: &[I8]): Option<Type>;
	clearImports(): CompileState;
	clear(): CompileState;
	addSource(source: Source): CompileState;
	findSource(name: &[I8]): Option<Source>;
	popStructureName(): CompileState;
	mapLocation(mapper: (arg0 : Location) => Location): CompileState;
	withPlatform(platform: Platform): CompileState;
	imports(): List<Import>;
	join(): &[I8];
	findCurrentLocation(): Option<Location>;
	platform(): Platform;
	addFunction(function: &[I8]): CompileState;
}

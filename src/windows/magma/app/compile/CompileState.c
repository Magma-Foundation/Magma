#include "./CompileState.h"
export interface CompileState {
	mut findLastStructureName(): Option<&[I8]>;
	mut createIndent(): &[I8];
	mut isPlatform(mut platform: Platform): Bool;
	mut hasLastStructureNameOf(mut name: &[I8]): Bool;
	mut addResolvedImportFromCache(mut base: &[I8]): CompileState;
	mut addResolvedImportWithNamespace(mut namespace: List<&[I8]>, mut child: &[I8]): CompileState;
	mut withLocation(mut namespace: Location): CompileState;
	mut append(mut element: &[I8]): CompileState;
	mut pushStructureName(mut name: &[I8]): CompileState;
	mut enterDepth(): CompileState;
	mut exitDepth(): CompileState;
	mut defineAll(mut definitions: List<Definition>): CompileState;
	mut resolve(mut name: &[I8]): Option<Type>;
	mut clearImports(): CompileState;
	mut clear(): CompileState;
	mut addSource(mut source: Source): CompileState;
	mut findSource(mut name: &[I8]): Option<Source>;
	mut popStructureName(): CompileState;
	mut mapLocation(mut mapper: (arg0 : Location) => Location): CompileState;
	mut withPlatform(mut platform: Platform): CompileState;
	mut imports(): List<Import>;
	mut join(): &[I8];
	mut findCurrentLocation(): Option<Location>;
	mut platform(): Platform;
	mut addFunction(mut function: &[I8]): CompileState;
}

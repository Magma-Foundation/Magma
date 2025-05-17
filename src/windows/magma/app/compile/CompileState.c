#include "./CompileState.h"
export interface CompileState {
	mut isLastWithin(mut name: &[I8]): Bool;
	mut addResolvedImport(mut oldParent: List<&[I8]>, mut child: &[I8]): CompileState;
	mut withLocation(mut namespace: Location): CompileState;
	mut append(mut element: &[I8]): CompileState;
	mut pushStructureName(mut name: &[I8]): CompileState;
	mut enterDepth(): CompileState;
	mut exitDepth(): CompileState;
	mut defineAll(mut definitions: List<Definition>): CompileState;
	mut resolve(mut name: &[I8]): Option<Type>;
	mut clearImports(): CompileState;
	mut clearOutput(): CompileState;
	mut addSource(mut source: Source): CompileState;
	mut findSource(mut name: &[I8]): Option<Source>;
	mut addResolvedImportFromCache(mut base: &[I8]): CompileState;
	mut popStructureName(): CompileState;
	mut mapLocation(mut mapper: (arg0 : Location) => Location): CompileState;
	mut withPlatform(mut platform: Platform): CompileState;
	mut imports(): List<Import>;
	mut output(): &[I8];
	mut structureNames(): List<&[I8]>;
	mut depth(): number;
	mut definitions(): List<Definition>;
	mut maybeLocation(): Option<Location>;
	mut sources(): List<Source>;
	mut platform(): Platform;
}

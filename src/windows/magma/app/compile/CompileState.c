#include "./CompileState.h"
export interface CompileState {
	Option<&[I8]> findLastStructureName();
	&[I8] createIndent();
	Bool isPlatform(Platform platform);
	Bool hasLastStructureNameOf(&[I8] name);
	CompileState addResolvedImportFromCache(&[I8] base);
	CompileState addResolvedImportWithNamespace(List<&[I8]> namespace, &[I8] child);
	CompileState withLocation(Location namespace);
	CompileState append(&[I8] element);
	CompileState pushStructureName(&[I8] name);
	CompileState enterDepth();
	CompileState exitDepth();
	CompileState defineAll(List<Definition> definitions);
	Option<Type> resolve(&[I8] name);
	CompileState clearImports();
	CompileState clear();
	CompileState addSource(Source source);
	Option<Source> findSource(&[I8] name);
	CompileState popStructureName();
	CompileState mapLocation((arg0 : Location) => Location mapper);
	CompileState withPlatform(Platform platform);
	List<Import> imports();
	&[I8] join();
	Option<Location> findCurrentLocation();
	Platform platform();
	CompileState addFunction(&[I8] function);
}

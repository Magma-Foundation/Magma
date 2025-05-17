import { Option } from "../../../magma/api/option/Option";
import { Platform } from "../../../magma/app/io/Platform";
import { List } from "../../../magma/api/collect/list/List";
import { Location } from "../../../magma/app/io/Location";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { Type } from "../../../magma/api/Type";
import { Source } from "../../../magma/app/io/Source";
import { Import } from "../../../magma/app/compile/Import";
export interface CompileState {findLastStructureName(): Option<string>;createIndent(): string;isPlatform(platform: Platform): boolean;hasLastStructureNameOf(name: string): boolean;addResolvedImportFromCache(base: string): CompileState;addResolvedImportWithNamespace(namespace: List<string>, child: string): CompileState;withLocation(namespace: Location): CompileState;append(element: string): CompileState;pushStructureName(name: string): CompileState;enterDepth(): CompileState;exitDepth(): CompileState;defineAll(definitions: List<Definition>): CompileState;resolve(name: string): Option<Type>;clearImports(): CompileState;clear(): CompileState;addSource(source: Source): CompileState;findSource(name: string): Option<Source>;popStructureName(): CompileState;mapLocation(mapper: (arg0 : Location) => Location): CompileState;withPlatform(platform: Platform): CompileState;imports(): List<Import>;join(): string;findCurrentLocation(): Option<Location>;platform(): Platform;addFunction(function: string): CompileState;
}

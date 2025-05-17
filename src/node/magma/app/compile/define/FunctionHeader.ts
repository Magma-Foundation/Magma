import { Platform } from "../../../../magma/app/io/Platform";
import { Definition } from "../../../../magma/app/compile/define/Definition";
import { List } from "../../../../magma/api/collect/list/List";
export interface FunctionHeader<S extends FunctionHeader<S>> {generateWithDefinitions(platform: Platform, definitions: List<Definition>): string;hasAnnotation(annotation: string): boolean;removeModifier(modifier: string): S;addModifierLast(modifier: string): S;
}

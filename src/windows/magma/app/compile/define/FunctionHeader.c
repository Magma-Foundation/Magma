#include "./FunctionHeader.h"
export interface FunctionHeader<S extends FunctionHeader<S>> {&[I8] generateWithDefinitions(Platform platform, List<Definition> definitions);Bool hasAnnotation(&[I8] annotation);S removeModifier(&[I8] modifier);S addModifierLast(&[I8] modifier);
}

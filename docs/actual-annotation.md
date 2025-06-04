# Platform Specific Code with `@Actual`

Some parts of the compiler rely on platform APIs such as Java's file system classes.  When the compiler is translated to TypeScript these implementations are not available.  Classes and static methods annotated with `@Actual` are therefore removed from the generated TypeScript output.  Instance methods ignore the annotation.

This mechanism mirrors Kotlin's expect/actual feature.  The Java sources define the full API but platform specific pieces can be provided separately as `.ts` files when compiling the TypeScript version of the compiler.


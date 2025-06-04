# Java to C Conversion Notes

This document lists the Java language features currently used by the Magmac compiler and provides guidance on how safely they can be mapped to C.

## Features in Use

The compiler deliberately relies on a small subset of Java features.  The architecture document summarises them:

```
- classes, records and enums
- generics
- lambda expressions and method references
- the `var` keyword for local variables
- basic loops and standard library collections/streams
```

All instance field accesses in the Java sources are qualified with `this`.

The project intentionally avoids reflection, multi-threading and advanced I/O APIs.

## Recommendations for C

- **Classes, records and enums** – represent these as `struct` definitions in C.  Methods become functions that accept a pointer to the struct as the first parameter.
- **Generics** – C lacks generics, so implement type-parameterised structures with preprocessor macros or `void *` pointers and manual casts.
- **Lambda expressions and method references** – convert them to function pointers, optionally paired with a context struct when a closure is required.
- **`var` declarations** – pick an explicit C type for each variable.  In most cases the Java type already makes this clear.
- **Collections and streams** – replace with lightweight container implementations or an external library.  Iteration constructs map directly to `for` or `while` loops.
- **Sealed interfaces** – model the variant hierarchy with tagged unions.  Exhaustive `switch` statements in C mirror the Java `switch` over sealed types.
- **Option/Result objects** – define structs containing a success flag and a union of values.  Helper functions can mirror the Java API.
- **Exception handling** – Java's `try`/`catch` blocks around I/O become error codes returned by functions in C.  Propagate errors explicitly rather than relying on unwinding.

Because the code does not rely on reflection, threads or modules, no special handling for those features is needed when targeting C.

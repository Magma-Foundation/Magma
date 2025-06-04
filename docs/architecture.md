# Compiler Architecture

The compiler is structured as a set of stages. Source files are first lexed and parsed into an abstract syntax tree (AST). After parsing, the AST is transformed and emitted for each `TargetPlatform`.

```
Sources → Lexer → Parser → StagedCompiler → Generator → Output
```

The `StagedCompiler` combines a `Parser` with a `Generator`. A platform implementation (for example `PlantUMLTargetPlatform` or `TypeScriptTargetPlatform`) provides the specific parser rules and a generator that knows how to convert the AST into the desired output format.

At a lower level the compiler builds its tree in layers. Source text is lexed according to a set of `Rule`s and placed into a generic `Node` structure. These nodes are then transformed into the sealed interfaces that make up the real AST. The AST may be adjusted and serialized back into `Node`s so that additional `Rule`s can be applied before code generation.

Using sealed interfaces gives the compiler a strongly typed model of the language. Exhaustive `switch` statements over the sealed hierarchy ensure every case is handled and invalid combinations do not compile. This approach lets us prove that the compiler will always output syntactically correct code, something a loosely typed `Node` tree cannot guarantee.

The `CompileApplication` ties everything together. It loads the source units, runs the configured compiler and writes the results to the target directory.

The relationships between the main classes are illustrated in `diagram.png` in this folder:

![Architecture Diagram](diagram.png)

## Bootstrapping Approach

This project aims to become a self-hosting compiler. The current implementation
is written in Java but only relies on a small subset of language features. The
compiler initially targets TypeScript so that a TypeScript build of the compiler
can be produced. Once the TypeScript version is operational, the Java front end
will be replaced with a parser for the Magma programming language. After the
compiler can compile itself from Magma to TypeScript, a new back end will target
C code generated through Clang and eventually integrate with LLVM.

### Supported Java Features

The source code purposely avoids complex parts of Java to simplify translation
to other languages. Features currently in use include:

- classes, records and enums
- generics
- lambda expressions and method references
- the `var` keyword for local variables
- basic loops and standard library collections/streams

The following features are **not** used:

- reflection or dynamic class loading
- multi-threading and synchronization primitives
- Java modules or annotations other than `@Override`
- advanced I/O or networking APIs

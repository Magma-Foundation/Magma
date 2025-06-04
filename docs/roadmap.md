# Project Roadmap

This document summarises the long‑term goals of the Magmac compiler and lists the main gaps in the current Java→TypeScript translation.

## Primary Goal

The Magmac compiler is designed to become a **bootstrapped, self‑hosting compiler**. The current implementation is written in Java and produces TypeScript so that the compiler can run inside JavaScript environments. Once the TypeScript version is stable, the Java parser will be replaced with one for the Magma language itself. After the compiler can compile its own sources from Magma to TypeScript, new back‑ends will target C via Clang and later LLVM.

## Roadmap Overview

1. **Stabilise the Java implementation** and refine the TypeScript pipeline.
2. **Produce a functional TypeScript build** of the compiler.
3. **Introduce a Magma front end** to achieve self‑hosting.
4. **Add additional back ends**, starting with C/LLVM.
5. **Refactor and document** the codebase continually.

## Missing Features for Java → TypeScript Conversion

The TypeScript pipeline is incomplete and several constructs are emitted as placeholders. Notable gaps include:

- **Lambda and `switch` expressions** are mapped to `0` during conversion. The relevant code can be seen in `JavaTypescriptParser`:
  ```java
  case JavaLang.Lambda javaLambda -> new TypescriptLang.Number("0");
  case JavaLang.SwitchNode javaSwitchNode -> new TypescriptLang.Number("0");
  ```
- **`instanceof` expressions are now supported via the `InstanceOf` node.**
- **Block headers** such as `if`, `for` or `while` are not parsed. The parser always emits `if (true)`:
  ```java
  private static TypescriptLang.TypescriptBlockHeader parseHeader(JavaLang.BlockHeader header) {
      return new TypescriptLang.TypescriptConditional(ConditionalType.If, new Symbol("true"));
  }
  ```
- **`case` statements** inside `switch` blocks are ignored and become whitespace:
  ```java
  case JavaLang.Case caseNode -> new TypescriptLang.Whitespace();
  ```
- **Variadic types and some complex type constructs** are replaced with the symbol `?`:
  ```java
  case JavaLang.JavaVariadicType type -> CompileResults.Ok(new Symbol("?"));
  ```

Because of these missing features the generated TypeScript currently does not compile, and the CI workflow skips the `tsc` step. Filling in these gaps is required before the compiler can bootstrap itself.

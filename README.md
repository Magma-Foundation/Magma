# Magmac Compiler

This project contains an experimental compiler written in Java. The compiler parses a Java inspired language and can produce different targets such as TypeScript sources or PlantUML class diagrams.

The compiler is designed to be bootstrapped. Initially the Java implementation
generates TypeScript so that the compiler can run in a JavaScript environment.
Once stable, the Java front end will be replaced with a parser for the Magma
language itself. After the compiler is self-hosting in Magma, additional back
ends will be added starting with C output via Clang and eventually LLVM.

## Getting Started

The sources are located in `packages/compiler-java/src/main/java`. A modern JDK (17 or newer) is required to build the project. A simple way to compile everything into the `packages/compiler-java/out` directory is:

```bash
javac -d packages/compiler-java/out $(find packages/compiler-java/src/main/java -name '*.java')
```

The entry point of the compiler is `magmac.Main`. After compiling you can run:

```bash
java -cp packages/compiler-java/out magmac.Main
```

This will scan the sources, run the compiler pipeline and write the generated outputs to the directories configured by each `TargetPlatform` (for example `diagrams` for PlantUML files and `src/web` for TypeScript files).

### TypeScript build

A recent Node.js runtime (version 18 or newer is recommended) is required to experiment with the TypeScript version of the compiler. From the repository root install the dependencies and launch the entry point:

```bash
cd packages/compiler-ts
npm install
npm start
```

The script executes `ts-node` and runs the TypeScript build of `magmac.Main`.

Primitive Java types are translated to their TypeScript equivalents. Numeric primitives
(`byte`, `short`, `int`, `long`, `float`, `double`) become `number`, `boolean` stays
`boolean` and `char` or `String` are emitted as `string`.

## Repository Layout

- `packages/compiler-java/src/main/java` – Java source code for the compiler implementation
- `packages/compiler-ts` – TypeScript tooling and build configuration
- `diagrams/` – PlantUML files produced by the compiler
- `docs/` – Documentation and architecture diagrams

See [`docs/architecture.md`](docs/architecture.md) for an overview of how the compiler is structured.
General refactoring plans are tracked in [`docs/architecture-improvements.md`](docs/architecture-improvements.md).
For details on how the PlantUML class diagram is generated see [`docs/diagram-generation.md`](docs/diagram-generation.md).
The inspection report produced by IntelliJ is summarised in [`docs/inspection/tasks.md`](docs/inspection/tasks.md).
For a high level roadmap and a list of missing Java→TypeScript features see [`docs/roadmap.md`](docs/roadmap.md).
Suggestions for profiling the compiler's performance can be found in [`docs/performance-profiling.md`](docs/performance-profiling.md).
Guidance on translating the current Java features to C is available in [`docs/java-to-c.md`](docs/java-to-c.md).

Instance fields in the Java sources are always accessed using `this`. Java does
not require it, but TypeScript does, and using the same convention avoids an
extra lookup step when translating the compiler.

## Continuous Integration

The repository is built on every pull request using a GitHub Actions workflow.
It compiles the Java sources with JDK&nbsp;21 and preview features enabled.
Compilation of the generated TypeScript is currently **skipped** because the
compiler's TypeScript pipeline is still under development.

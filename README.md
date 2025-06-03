# Magmac Compiler

This project contains an experimental compiler written in Java. The compiler parses a Java inspired language and can produce different targets such as TypeScript sources or PlantUML class diagrams.

## Getting Started

The sources are located in `src/java`. A modern JDK (17 or newer) is required to build the project. A simple way to compile everything into the `out` directory is:

```bash
javac -d out $(find src/java -name '*.java')
```

The entry point of the compiler is `magmac.Main`. After compiling you can run:

```bash
java -cp out magmac.Main
```

This will scan the sources, run the compiler pipeline and write the generated outputs to the directories configured by each `TargetPlatform` (for example `diagrams` for PlantUML files and `src/web` for TypeScript files).

## Repository Layout

- `src/java` – Java source code for the compiler implementation
- `diagrams/` – PlantUML files produced by the compiler
- `docs/` – Documentation and architecture diagrams

See [`docs/architecture.md`](docs/architecture.md) for an overview of how the compiler is structured.

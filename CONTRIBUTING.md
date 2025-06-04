# Contributing to Magmac

Thank you for your interest in improving the Magmac compiler! This document outlines the basic workflow for contributors and lists a few starter tasks.

## Development Setup

1. **Fork the repository** and clone your fork locally.
2. Install the Node.js dependencies:
   ```bash
   npm install
   ```
3. Build the Java sources (JDK 21 with preview features enabled):
   ```bash
   mkdir -p out
   javac --release 21 --enable-preview -d out $(find src/java -name '*.java')
   ```
4. The TypeScript pipeline is still experimental, so the CI workflow currently skips compiling `.ts` files.

## Running the Tests

The project uses JUnit. Tests can be run similarly to the CI workflow:

```bash
curl -L -o junit-platform-console-standalone.jar \
  https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.1/junit-platform-console-standalone-1.10.1.jar
javac --release 21 --enable-preview -cp junit-platform-console-standalone.jar:out -d out $(find test/java -name '*.java')
java --enable-preview -jar junit-platform-console-standalone.jar --class-path out --scan-class-path
```

## Code Style

- Follow the existing formatting in the Java and TypeScript sources.
- Prefer private fields and small helper methods as suggested in [`docs/inspection/tasks.md`](docs/inspection/tasks.md).

## Good First Issues

If you are new to the project, here are some manageable tasks to get started:

- Implement support for `case` statements and proper block headers in the TypeScript parser. See the list of missing features in [`docs/roadmap.md`](docs/roadmap.md).
- Translate lambda expressions and `switch` expressions instead of emitting `0` placeholders.
- Remove unused methods and tidy up formatting as described in [`docs/inspection/tasks.md`](docs/inspection/tasks.md).
- Add unit tests for areas of the compiler that currently lack coverage.

We welcome pull requests that address any of the issues above or that improve the documentation. Feel free to open an issue or discussion if you have questions!

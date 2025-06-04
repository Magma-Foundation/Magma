# PlantUML Diagram Generation

This project can produce a UML class diagram describing its own structure. The diagram is written in PlantUML syntax and stored under `diagrams/diagram.puml`.

## Self-hosted generation

When the compiler is executed it scans the Java sources located in `src/java`. The `PlantUMLTargetPlatform` configures a pipeline which uses `JavaPlantUMLParser` to parse these sources into an AST. That AST is converted into a list of `PlantUMLRootSegment` nodes and merged by `MergeDiagram`. Finally the `RuleGenerator` serialises the nodes back into PlantUML text.

Because the input sources are the compiler's own files, the diagram is effectively produced by the compiler reading itself.

The generated `.puml` file can be rendered with any PlantUML renderer or converted to an image (see `diagrams/diagram-0.png`).

## Included elements

The parser records each class, interface and enum that appears in `src/java` and
adds `extends`/`implements` relationships.  Generic type arguments on those
clauses are turned into dependency edges as well.  Examples of enum definitions
can be seen in the generated output:

```
enum ConditionalType
enum PlantUMLStructureType
```

No field or method information is written to the diagram and packages are not
represented.

## Missing elements

* Members such as fields or method signatures
* Access modifiers or annotations
* Package groupings
* Dependencies that only occur inside method bodies

## Observed smells in `diagram.puml`

Many nodes depend on standard library types like `List`, `Map` and `BiFunction`.
For instance several lines in the file contain:

```
List --> BiFunction
ParseUnit --> BiFunction
```

This noise makes the diagram harder to read.  A few ideas to clean it up:

* Filter out dependencies on common Java library types
* Collapse generic arguments so repeated edges are not emitted
* Group classes by package to break the diagram into smaller chunks

## Possible clean ups

* Provide a script or task to regenerate the diagram automatically, for example a Gradle or npm script.
* Split the `MergeDiagram` logic into smaller helper functions to make it easier to maintain.
* Group related classes in the output or generate multiple diagrams to reduce clutter.
* Consider excluding trivial dependencies so that the diagram focuses on the main architecture.

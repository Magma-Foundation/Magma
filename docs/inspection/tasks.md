# Inspection Cleanup Tasks

This file summarizes potential refactoring tasks based on the IntelliJ inspection report found in `index.html`. The report flagged over 560 warnings which fall into several recurring categories.

## Recommended Actions

- **Reduce public method exposure** – many classes expose public methods that are not part of any interface. Lower their visibility or introduce interfaces where appropriate.
- **Move or privatize single-use static methods** – several static helper methods are called from only one other class. Move them closer to their call site or make them private to avoid leaking unnecessary API surface.
- **Break up large or highly coupled classes** – classes like `JavaDeserializers`, `JavaRules`, `JavaTypescriptParser`, and `TreeParser` have numerous dependencies and complex logic. Consider splitting them into smaller components or introducing interfaces to decouple responsibilities.
- **Remove unused code** – the inspection lists multiple unused fields, variables, and methods. Delete these to keep the codebase clean.
- **Replace protected fields** – some classes rely on `protected` member variables. Prefer private fields with accessor methods to maintain encapsulation.
- **Fix general formatting and style issues** – qualify instance field access with `this`, avoid unqualified static method calls, clean up unused imports, and follow consistent parameter naming conventions.
- **Simplify lambdas and specify Locale** – convert statement lambdas to expression lambdas when possible and provide a `Locale` to methods like `toLowerCase()` and `toUpperCase()`.
- **Resolve cyclic dependencies** – the project has many cycles between classes and packages. Break these by reorganizing packages or introducing abstractions.
- **Consider modular reorganization** – the module contains over 200 intertwined classes. Splitting the project into smaller modules or packages would improve maintainability.
- **Update Javadoc and naming** – audit Javadoc formatting and naming conventions. Use `{@code}` in place of `<code>` and remove dangling comments.

## Architectural State

The inspection indicates that the codebase is highly coupled with numerous cycles between classes and packages. Direct and transitive dependencies are widespread, making the architecture difficult to maintain. Significant refactoring is needed to achieve better modularity and separation of concerns.


# Ongoing Architectural Improvements

This document collects general plans and ideas for refining the compiler's design. The goal is to reduce coupling between modules and make it easier to add new back ends in the future.

## Reduce Tight Coupling

- **Split large classes into smaller components** so that each class has a single responsibility.
- **Introduce clearer package boundaries** to minimise circular dependencies.
- **Move static helper methods** closer to their call sites or make them private where possible.

## Consistent Result Wrappers

Many parts of the code already wrap operations that can fail in domain‑specific `Result` types. Some modules still return `Result<T, ApplicationError>` directly. Introducing an `ApplicationResult` wrapper would mirror `CompileResult` and `IOResult`, keeping error handling consistent across the compiler.

## Modularity and Interfaces

- Consider reorganising the project into smaller modules. An explicit module structure makes dependencies obvious and encourages interface‑driven design.
- Expose narrow interfaces for public APIs and keep implementation details package‑private.

## Future Work

These improvements are incremental. Refactoring should be accompanied by updated tests and documentation. Tracking progress in `docs/inspection/tasks.md` helps highlight remaining issues.

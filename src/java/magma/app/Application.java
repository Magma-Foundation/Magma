package magma.app;

import jvm.api.io.Files;
import magma.api.Tuple2Impl;
import magma.api.collect.Iters;
import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;
import magma.api.io.IOError;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.app.compile.CompileState;
import magma.app.compile.Context;
import magma.app.compile.Dependency;
import magma.app.compile.ImmutableCompileState;
import magma.app.compile.Import;
import magma.app.io.Source;

public record Application(Sources sources, Targets targets) {
    static Result<CompileState, IOError> run(Application application) {
        return Iters.fromArray(Platform.values()).foldWithInitialToResult(ImmutableCompileState.createEmpty(), (CompileState state, Platform platform) -> {
            return application.sources().listSources().flatMapValue((Iterable<Source> children) -> {
                return Application.runWithChildren(state.mapContext((Context context) -> context.withPlatform(platform)), children, application.targets());
            });
        });
    }

    private static Result<CompileState, IOError> runWithChildren(CompileState state, Iterable<Source> sources, Targets targets) {
        var initial = sources.iter().foldWithInitial(state, (CompileState current, Source source) -> current.mapContext(context -> context.addSource(source)));
        var folded = sources.iter().foldWithInitialToResult(initial, (state1, source) -> Application.runWithSource(state1, source, targets));

        if (state.context().hasPlatform(Platform.PlantUML) && folded instanceof Ok(var result)) {
            var diagramPath = Files.get(".", "diagram.puml");

            var joinedDependencies = result.registry().iterDependencies()
                    .map((Dependency dependency) -> dependency.name() + " --> " + dependency.child() + "\n")
                    .collect(new Joiner(""))
                    .orElse("");

            var maybeError = diagramPath.writeString("@startuml\nskinparam linetype ortho\n" + result.registry().output() + joinedDependencies + "@enduml");
            if (maybeError instanceof Some(var error)) {
                return new Err<CompileState, IOError>(error);
            }
        }

        return folded;
    }

    private static Result<CompileState, IOError> runWithSource(CompileState state, Source source, Targets targets) {
        return source.read().flatMapValue((String input) -> Application.compileAndWrite(state, source, input, targets));
    }

    private static Result<CompileState, IOError> compileAndWrite(CompileState state, Source source, String input, Targets targets) {
        var location = source.createLocation();
        var compiled = Compiler.compileRoot(state, input, location);
        var compiledState = compiled.left();

        if (compiledState.context().hasPlatform(Platform.PlantUML)) {
            return new Ok<CompileState, IOError>(compiledState);
        }

        var segment = state.context().iterSources()
                .map((Source source1) -> Application.formatSource(source1))
                .collect(new Joiner(", "))
                .orElse("");

        var otherOutput = compiled.right();
        var joinedImports = compiledState.registry().queryImports()
                .map((Import anImport) -> anImport.generate())
                .collect(new Joiner(""))
                .orElse("");

        var joined = joinedImports + compiledState.registry().output() + otherOutput;
        var output = new Tuple2Impl<CompileState, String>(state, "/*[" + segment + "\n]*/\n" + joined);
        var cleared = output.left().mapRegistry(registry -> registry.clearImports()).mapRegistry(registry1 -> registry1.clearOutput());
        return Application.writeTarget(source, cleared, output.right(), targets);
    }

    private static Result<CompileState, IOError> writeTarget(Source source, CompileState state, String output, Targets targets) {
        return targets.writeSource(source.createLocation(), output)
                .<Result<CompileState, IOError>>map((IOError error) -> new Err<CompileState, IOError>(error))
                .orElseGet(() -> new Ok<CompileState, IOError>(state));
    }

    private static String formatSource(Source source) {
        return "\n\t" + source.createLocation().name() + ": " + Application.joinNamespace(source);
    }

    private static String joinNamespace(Source source) {
        return source.createLocation().namespace()
                .iter()
                .collect(new Joiner("."))
                .orElse("");
    }
}
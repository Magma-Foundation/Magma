package magma.app;

import jvm.api.io.Files;
import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;
import magma.api.io.IOError;
import magma.api.option.Option;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.app.compile.CompileState;
import magma.app.compile.Context;
import magma.app.compile.Dependency;
import magma.app.compile.ImmutableCompileState;
import magma.app.compile.Import;
import magma.app.compile.Registry;
import magma.app.io.Source;

public record Application(Sources sources, Targets targets) {
    private static String formatSource(Source source) {
        return "\n\t" + source.createLocation().name() + ": " + Application.joinNamespace(source);
    }

    private static String joinNamespace(Source source) {
        return source.createLocation().namespace()
                .iter()
                .collect(new Joiner("."))
                .orElse("");
    }

    Option<IOError> runWith(Platform platform) {
        return this.sources.listSources()
                .flatMapValue((Iterable<Source> children) -> this.runWithChildren(platform, children))
                .findError();
    }

    private Result<CompileState, IOError> runWithChildren(Platform platform, Iterable<Source> children) {
        CompileState state = ImmutableCompileState.createEmpty().mapContext((Context context) -> context.withPlatform(platform));
        var initial = children.iter().foldWithInitial(state, (CompileState current, Source source) -> current.mapContext(context1 -> context1.addSource(source)));
        var folded = children.iter().foldWithInitialToResult(initial, (CompileState state1, Source source) -> this.runWithSource(state1, source));

        if (!state.context().hasPlatform(Platform.PlantUML) || !(folded instanceof Ok(var result))) {
            return folded;
        }

        var diagramPath = Files.get(".", "diagram.puml");
        var joinedDependencies = result.registry().iterDependencies()
                .map((Dependency dependency) -> dependency.name() + " --> " + dependency.child() + "\n")
                .collect(new Joiner(""))
                .orElse("");

        var maybeError = diagramPath.writeString("@startuml\nskinparam linetype ortho\n" + result.registry().output() + joinedDependencies + "@enduml");
        if (!(maybeError instanceof Some(var error))) {
            return folded;
        }

        return new Err<CompileState, IOError>(error);
    }

    private Result<CompileState, IOError> runWithSource(CompileState state, Source source) {
        return source.read().flatMapValue((String input) -> this.runWithInput(state, source, input));
    }

    private Result<CompileState, IOError> runWithInput(CompileState state1, Source source, String input) {
        var location = source.createLocation();
        var compiled = Compiler.compileRoot(state1, input, location);
        var compiledState = compiled.left();

        if (compiledState.context().hasPlatform(Platform.PlantUML)) {
            return new Ok<CompileState, IOError>(compiledState);
        }

        var segment = state1.context().iterSources()
                .map((Source source1) -> Application.formatSource(source1))
                .collect(new Joiner(", "))
                .orElse("");

        var otherOutput = compiled.right();
        var joinedImports = compiledState.registry().queryImports()
                .map((Import anImport) -> anImport.generate())
                .collect(new Joiner(""))
                .orElse("");

        var joined = joinedImports + compiledState.registry().output() + otherOutput;
        var cleared = state1.mapRegistry((Registry registry) -> registry.reset());
        return this.writeTarget(source, cleared, "/*[" + segment + "\n]*/\n" + joined);
    }

    private Result<CompileState, IOError> writeTarget(Source source, CompileState cleared, String output) {
        return this.targets().writeSource(source.createLocation(), output)
                .<Result<CompileState, IOError>>map((IOError error) -> new Err<CompileState, IOError>(error))
                .orElseGet(() -> new Ok<CompileState, IOError>(cleared));
    }
}
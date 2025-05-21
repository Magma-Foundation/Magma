package magma.app;

import jvm.api.collect.list.Lists;
import jvm.api.io.Files;
import magma.api.Tuple2Impl;
import magma.api.collect.Iters;
import magma.api.collect.Joiner;
import magma.api.collect.list.Iterable;
import magma.api.io.Console;
import magma.api.io.IOError;
import magma.api.option.None;
import magma.api.option.Some;
import magma.api.result.Err;
import magma.api.result.Ok;
import magma.api.result.Result;
import magma.app.compile.CompileState;
import magma.app.compile.Dependency;
import magma.app.compile.ImmutableCompileState;
import magma.app.compile.Import;
import magma.app.io.Source;

public final class Main {
    public static void main() {
        var sourceDirectory = Files.get(".", "src", "java");
        var sources = new Sources(sourceDirectory);
        Main.runWithSourceDirectory(sources)
                .findError()
                .map((IOError error) -> error.display())
                .ifPresent((String displayed) -> Console.printErrLn(displayed));
    }

    private static Result<CompileState, IOError> runWithSourceDirectory(Sources sources) {
        return Iters.fromArray(Platform.values()).foldWithInitialToResult(Main.createInitialState(), (CompileState state, Platform platform) -> {
            return sources.listSources().flatMapValue((Iterable<Source> children) -> {
                return Main.runWithChildren(state.withPlatform(platform), children);
            });
        });
    }

    private static Result<CompileState, IOError> runWithChildren(CompileState state, Iterable<Source> sources) {
        var initial = sources.iter().foldWithInitial(state, (CompileState current, Source source) -> current.addSource(source));
        var folded = sources.iter().foldWithInitialToResult(initial, Main::runWithSource);

        if (state.hasPlatform(Platform.PlantUML) && folded instanceof Ok(var result)) {
            var diagramPath = Files.get(".", "diagram.puml");

            var joinedDependencies = result.queryDependencies()
                    .map((Dependency dependency) -> dependency.name() + " --> " + dependency.child() + "\n")
                    .collect(new Joiner(""))
                    .orElse("");

            var maybeError = diagramPath.writeString("@startuml\nskinparam linetype ortho\n" + result.findOutput() + joinedDependencies + "@enduml");
            if (maybeError instanceof Some(var error)) {
                return new Err<CompileState, IOError>(error);
            }
        }

        return folded;
    }

    private static Result<CompileState, IOError> runWithSource(CompileState state, Source source) {
        return source.read().flatMapValue((String input) -> Main.compileAndWrite(state, source, input));
    }

    private static Result<CompileState, IOError> compileAndWrite(CompileState state, Source source, String input) {
        var location = source.createLocation();
        var compiled = Compiler.compileRoot(state, input, location);
        var compiledState = compiled.left();

        if (compiledState.hasPlatform(Platform.PlantUML)) {
            return new Ok<CompileState, IOError>(compiledState);
        }

        var segment = state.querySources()
                .map((Source source1) -> Main.formatSource(source1))
                .collect(new Joiner(", "))
                .orElse("");

        var otherOutput = compiled.right();
        var joinedImports = compiledState.queryImports()
                .map((Import anImport) -> anImport.generate())
                .collect(new Joiner(""))
                .orElse("");

        var joined = joinedImports + compiledState.findOutput() + otherOutput;
        var output = new Tuple2Impl<CompileState, String>(state, "/*[" + segment + "\n]*/\n" + joined);
        var cleared = output.left().clearImports().clearOutput();
        return Main.writeTarget(source, cleared, output.right());
    }

    private static Result<CompileState, IOError> writeTarget(Source source, CompileState state, String output) {
        return new Targets(Files.get(".", "src", "ts"))
                .writeSource(source.createLocation(), output)
                .<Result<CompileState, IOError>>map((IOError error) -> new Err<CompileState, IOError>(error))
                .orElseGet(() -> new Ok<CompileState, IOError>(state));
    }

    private static String formatSource(Source source) {
        return "\n\t" + source.createLocation().name() + ": " + Main.joinNamespace(source);
    }

    private static String joinNamespace(Source source) {
        return source.createLocation().namespace()
                .iter()
                .collect(new Joiner("."))
                .orElse("");
    }

    private static CompileState createInitialState() {
        return new ImmutableCompileState(
                Lists.empty(),
                "",
                Lists.empty(),
                0,
                Lists.empty(),
                new None<Location>(),
                Lists.empty(),
                Platform.TypeScript,
                Lists.empty());
    }
}
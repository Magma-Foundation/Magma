package magmac;

import jvm.io.Console;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.error.Error;
import magmac.api.iter.Iters;
import magmac.api.result.Result;
import magmac.app.Application;
import magmac.app.LexingStage;
import magmac.app.config.PlantUMLTargetPlatform;
import magmac.app.config.TargetPlatform;
import magmac.app.config.TypeScriptTargetPlatform;
import magmac.app.error.ApplicationError;
import magmac.app.io.sources.PathSources;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.lang.node.JavaRoot;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.lexer.RuleLexer;
import magmac.app.stage.unit.UnitSet;

import java.nio.file.Path;
import java.nio.file.Paths;

final class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));

        Main.getUnitSetApplicationErrorResult(sources)
                .match(Main::getNext, Some::new)
                .ifPresent(error -> Console.handleError(error.display()));
    }

    private static Option<Error> getNext(UnitSet<JavaRoot> result) {
        return Iters.fromValues(new PlantUMLTargetPlatform(), new TypeScriptTargetPlatform())
                .map((TargetPlatform platform) -> Main.getErrorOption(platform, result))
                .flatMap(Iters::fromOption)
                .next();
    }

    private static Result<UnitSet<JavaRoot>, ApplicationError> getUnitSetApplicationErrorResult(Sources sources) {
        Lexer lexer = new RuleLexer(JavaRoot.createRule());
        return new LexingStage<JavaRoot>(lexer, JavaRoot::deserialize)
                .getUnitSetApplicationErrorResult(sources);
    }

    private static Option<Error> getErrorOption(TargetPlatform platform, UnitSet<JavaRoot> roots) {
        Path targetPath = platform.createTargetPath();
        String extension = platform.createExtension();
        Targets targets = new PathTargets(targetPath, extension);
        Application application = platform.createApplication(targets);
        return application.compileAndWrite(roots);
    }
}
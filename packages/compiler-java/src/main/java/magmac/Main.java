package magmac;

import jvm.io.Console;
import magmac.api.Option;
import magmac.api.Some;
import magmac.api.error.Error;
import magmac.api.iter.Iters;
import magmac.api.result.Result;
import magmac.app.LexingStage;
import magmac.app.config.PlantUMLTargetPlatform;
import magmac.app.config.TargetPlatform;
import magmac.app.config.TypeScriptTargetPlatform;
import magmac.app.error.ApplicationError;
import magmac.app.io.sources.PathSources;
import magmac.app.io.sources.Sources;
import magmac.app.lang.java.JavaDeserializers;
import magmac.app.lang.JavaRules;
import magmac.app.lang.java.JavaLang;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.lexer.RuleLexer;
import magmac.app.stage.unit.UnitSet;

import java.nio.file.Paths;

final class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "packages", "compiler-java", "src", "main", "java"));

        Main.loadSources(sources)
                .match(Main::getNext, Some::new)
                .ifPresent(error -> Console.handleError(error.display()));
    }

    private static Option<Error> getNext(UnitSet<JavaLang.Root> result) {
        return Iters.fromValues(new PlantUMLTargetPlatform(), new TypeScriptTargetPlatform())
                .map((TargetPlatform platform) -> Main.run(result, platform))
                .flatMap(Iters::fromOption)
                .next();
    }

    private static Result<UnitSet<JavaLang.Root>, ApplicationError> loadSources(Sources sources) {
        Lexer lexer = new RuleLexer(JavaRules.createRule());
        return new LexingStage<JavaLang.Root>(lexer, node -> JavaLang.Root.getChildren(node, JavaDeserializers::deserializeRootSegment))
                .getUnitSetApplicationErrorResult(sources);
    }

    private static Option<Error> run(UnitSet<JavaLang.Root> roots, TargetPlatform platform) {
        return platform.createApplication().parseAndStore(roots);
    }
}
package magmac;

import magmac.api.iter.Iters;
import magmac.app.CompileApplication;
import magmac.app.Config;
import magmac.app.Error;
import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.config.PlantUMLTargetPlatform;
import magmac.app.config.TypeScriptTargetPlatform;
import magmac.app.config.TargetPlatform;
import magmac.app.io.sources.PathSources;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.lang.FlattenJava;
import magmac.app.stage.AfterAll;
import magmac.app.stage.Passer;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.parse.TreeParser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

final class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));

        Iters.fromValues(new PlantUMLTargetPlatform(), new TypeScriptTargetPlatform())
                .map(platform -> Main.run(platform, sources))
                .flatMap(Iters::fromOption)
                .next()
                .ifPresent(error -> Main.handleError(error));
    }

    private static Optional<Error> run(TargetPlatform platform, Sources sources) {
        Targets targets = Main.createTargets(platform);
        Lexer lexer = Config.createLexer();
        Parser parser = Main.createParser(platform);
        Generator generator = new RuleGenerator(platform.createRule());
        Compiler compiler = new StagedCompiler(lexer, parser, generator);
        return new CompileApplication(sources, compiler, targets).run();
    }

    private static Targets createTargets(TargetPlatform platform) {
        Path targetPath = platform.createTargetPath();
        String extension = platform.createExtension();
        Targets targets = new PathTargets(targetPath, extension);
        return targets;
    }

    private static Parser createParser(TargetPlatform platform) {
        AfterAll afterAllChildren = platform.createAfterAll();
        Passer afterChild = platform.createAfterChild();
        Parser parser = new TreeParser(new FlattenJava(), afterChild, afterAllChildren);
        return parser;
    }

    private static void handleError(Error error) {
        //noinspection CallToPrintStackTrace
        System.err.println(error.display());
    }
}

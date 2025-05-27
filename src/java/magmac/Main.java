package magmac;

import magmac.api.iter.Iters;
import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.Config;
import magmac.app.Error;
import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.rule.Rule;
import magmac.app.io.sources.PathSources;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.lang.PlantUMLRoots;
import magmac.app.lang.TargetPlatform;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

final class Main {
    public static void main() {
        Sources sources = new PathSources(Paths.get(".", "src", "java"));

        Iters.fromArray(TargetPlatform.values())
                .map(platform -> Main.getDiagrams(platform, sources))
                .flatMap(Iters::fromOption)
                .next()
                .ifPresent(error -> Main.handleError(error));
    }

    private static Optional<Error> getDiagrams(TargetPlatform platform, Sources sources) {
        Path targetPath = platform.createTargetPath();
        Targets targets = new PathTargets(targetPath);
        Rule rule = PlantUMLRoots.createRule();

        Lexer lexer = Config.createLexer();
        Parser parser = Config.createParser();

        Generator generator = new RuleGenerator(rule);
        Compiler compiler = new StagedCompiler(lexer, parser, generator);
        Application application = new CompileApplication(sources, compiler, targets);
        return application.run();
    }

    private static void handleError(Error error) {
        //noinspection CallToPrintStackTrace
        System.err.println(error.display());
    }
}

package magmac;

import magmac.api.iter.Iters;
import magmac.app.CompileApplication;
import magmac.app.Config;
import magmac.app.Error;
import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.io.sources.PathSources;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.Targets;
import magmac.app.lang.TargetPlatform;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.Parser;

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
        Targets targets = platform.createTargets();
        Lexer lexer = Config.createLexer();
        Parser parser = platform.createParser();
        Generator generator = platform.createGenerator();
        Compiler compiler = new StagedCompiler(lexer, parser, generator);
        return new CompileApplication(sources, compiler, targets).run();
    }

    private static void handleError(Error error) {
        //noinspection CallToPrintStackTrace
        System.err.println(error.display());
    }
}

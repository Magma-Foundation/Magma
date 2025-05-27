package magmac;

import magmac.api.Tuple2;
import magmac.api.iter.Iters;
import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.Config;
import magmac.app.Error;
import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.node.Node;
import magmac.app.compile.rule.Rule;
import magmac.app.io.sources.PathSources;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.Targets;
import magmac.app.lang.AfterPasser;
import magmac.app.lang.FlattenJava;
import magmac.app.lang.TargetPlatform;
import magmac.app.stage.AfterAll;
import magmac.app.stage.Passer;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.parse.ParseState;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.parse.TreeParser;

import java.nio.file.Paths;
import java.util.Optional;

final class Main {
    private static class MyPasser implements Passer {
        @Override
        public Optional<Tuple2<ParseState, Node>> pass(ParseState state, Node node) {
            return Optional.empty();
        }
    }

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
        Rule rule = platform.createRule();

        Lexer lexer = Config.createLexer();
        AfterAll afterAllChildren = platform.createAfterAll();

        Passer afterChild = switch (platform) {
            case PlantUML -> new AfterPasser();
            case TypeScript -> new MyPasser();
        };

        Parser parser = new TreeParser(new FlattenJava(), afterChild, afterAllChildren);

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

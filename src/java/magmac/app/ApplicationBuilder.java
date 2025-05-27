package magmac.app;

import magmac.api.error.Error;
import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.config.TargetPlatform;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.lang.FlattenJava;
import magmac.app.lang.JavaLang;
import magmac.app.stage.AfterAll;
import magmac.app.stage.Passer;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.lexer.RuleLexer;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.parse.TreeParser;

import java.nio.file.Path;
import magmac.api.Option;

public final class ApplicationBuilder {
    public static Option<Error> run(TargetPlatform platform, Sources sources) {
        Path targetPath = platform.createTargetPath();
        String extension = platform.createExtension();
        Targets targets = new PathTargets(targetPath, extension);

        Lexer lexer = new RuleLexer(JavaLang.createRule());

        AfterAll afterAllChildren = platform.createAfterAll();
        Passer afterChild = platform.createAfterChild();
        Parser parser = new TreeParser(new FlattenJava(), afterChild, afterAllChildren);

        Generator generator = new RuleGenerator(platform.createRule());
        Compiler compiler = new StagedCompiler(lexer, parser, generator);
        return new CompileApplication(sources, compiler, targets).run();
    }
}
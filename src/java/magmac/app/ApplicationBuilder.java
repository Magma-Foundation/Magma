package magmac.app;

import magmac.api.Option;
import magmac.api.error.Error;
import magmac.app.compile.Compiler;
import magmac.app.config.TargetPlatform;
import magmac.app.io.sources.Sources;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.lang.node.JavaRoot;
import magmac.app.stage.lexer.Lexer;
import magmac.app.stage.lexer.RuleLexer;

import java.nio.file.Path;

public final class ApplicationBuilder {
    public static Option<Error> run(TargetPlatform platform, Sources sources) {
        Path targetPath = platform.createTargetPath();
        String extension = platform.createExtension();
        Targets targets = new PathTargets(targetPath, extension);
        Lexer lexer = new RuleLexer(JavaRoot.createRule());
        Compiler compiler = platform.createCompiler(lexer);
        Application application = new CompileApplication(sources, compiler, targets);
        return application.run();
    }
}
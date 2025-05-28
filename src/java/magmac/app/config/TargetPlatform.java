package magmac.app.config;

import magmac.app.compile.Compiler;
import magmac.app.compile.rule.Rule;
import magmac.app.stage.after.AfterAll;
import magmac.app.stage.Passer;
import magmac.app.stage.lexer.Lexer;

import java.nio.file.Path;

public interface TargetPlatform {
    Compiler createCompiler(Lexer lexer);

    Passer createAfterChild();

    Path createTargetPath();

    AfterAll createAfterAll();

    String createExtension();

    Rule createRule();
}

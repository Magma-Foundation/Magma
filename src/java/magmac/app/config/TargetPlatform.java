package magmac.app.config;

import magmac.app.Application;
import magmac.app.compile.Compiler;
import magmac.app.compile.rule.Rule;
import magmac.app.io.targets.Targets;
import magmac.app.stage.after.AfterAll;
import magmac.app.stage.Passer;

import java.nio.file.Path;

public interface TargetPlatform {
    Application createApplication(Targets targets);

    Compiler createCompiler();

    Passer createAfterChild();

    Path createTargetPath();

    AfterAll createAfterAll();

    String createExtension();

    Rule createRule();
}

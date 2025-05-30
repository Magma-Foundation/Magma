package magmac.app.config;

import magmac.app.Application;
import magmac.app.compile.Compiler;
import magmac.app.compile.rule.Rule;
import magmac.app.io.targets.Targets;

import java.nio.file.Path;

public interface TargetPlatform {
    Application createApplication();

    Application createApplication0(Targets targets);

    Compiler createCompiler();

    Path createTargetPath();

    String createExtension();

    Rule createRule();
}

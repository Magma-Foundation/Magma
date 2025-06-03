package magmac.app.config;

import magmac.app.Application;
import magmac.app.compile.Compiler;
import magmac.app.compile.rule.Rule;

import java.nio.file.Path;

public interface TargetPlatform {
    Application createApplication();

    Compiler createCompiler();

    Path createTargetPath();

    String createExtension();

    Rule createRule();
}

package magmac.app.config;

import magmac.app.Application;
import magmac.app.compile.Compiler;
import magmac.app.compile.rule.Rule;

import java.nio.file.Path;

/**
 * Creates the components for generating output for a specific target.
 */
public interface TargetPlatform {
    Application createApplication();

    Compiler createCompiler();

    Path createTargetPath();

    String createExtension();

    Rule createRule();
}

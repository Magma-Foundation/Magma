package magmac.app.config;

import magmac.app.compile.rule.Rule;
import magmac.app.stage.AfterAll;
import magmac.app.stage.Passer;

import java.nio.file.Path;

public interface TargetPlatform {
    Passer createAfterChild();

    Path createTargetPath();

    AfterAll createAfterAll();

    String createExtension();

    Rule createRule();
}

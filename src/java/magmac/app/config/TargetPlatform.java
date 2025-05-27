package magmac.app.config;

import magmac.app.io.targets.Targets;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.parse.Parser;

public interface TargetPlatform {
    Generator createGenerator();

    Parser createParser();

    Targets createTargets();
}

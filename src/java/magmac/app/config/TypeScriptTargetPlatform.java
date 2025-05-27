package magmac.app.config;

import magmac.app.compile.rule.Rule;
import magmac.app.lang.TypeScriptPasser;
import magmac.app.lang.TypescriptRoots;
import magmac.app.stage.AfterAll;
import magmac.app.stage.EmptyAfterAll;
import magmac.app.stage.Passer;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class TypeScriptTargetPlatform implements TargetPlatform {
    @Override
    public Passer createAfterChild() {
        return new TypeScriptPasser();
    }

    @Override
    public Path createTargetPath() {
        return Paths.get(".", "src", "web");
    }

    @Override
    public AfterAll createAfterAll() {
        return new EmptyAfterAll();
    }

    @Override
    public String createExtension() {
        return "ts";
    }

    @Override
    public Rule createRule() {
        return TypescriptRoots.createRule();
    }
}
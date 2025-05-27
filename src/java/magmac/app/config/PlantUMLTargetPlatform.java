package magmac.app.config;

import magmac.app.compile.rule.Rule;
import magmac.app.lang.PlantUMLAfterPasser;
import magmac.app.lang.MergeDiagram;
import magmac.app.lang.PlantUMLLang;
import magmac.app.stage.AfterAll;
import magmac.app.stage.Passer;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PlantUMLTargetPlatform implements TargetPlatform {
    @Override
    public Passer createAfterChild() {
        return new PlantUMLAfterPasser();
    }

    @Override
    public Path createTargetPath() {
        return Paths.get(".", "diagrams");
    }

    @Override
    public AfterAll createAfterAll() {
        return new MergeDiagram();
    }

    @Override
    public String createExtension() {
        return "puml";
    }

    @Override
    public Rule createRule() {
        return PlantUMLLang.createRule();
    }
}
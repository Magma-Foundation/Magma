package magmac.app.config;

import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.rule.Rule;
import magmac.app.io.targets.Targets;
import magmac.app.lang.JavaPlantUMLParser;
import magmac.app.lang.MergeDiagram;
import magmac.app.lang.PlantUMLAfterPasser;
import magmac.app.lang.node.PlantUMLRoot;
import magmac.app.stage.Passer;
import magmac.app.stage.after.AfterAll;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;

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
        return PlantUMLRoot.createRule();
    }

    @Override
    public Compiler createCompiler() {
        Generator generator = new RuleGenerator(this.createRule());
        return new StagedCompiler<PlantUMLRoot>(new JavaPlantUMLParser(), generator);
    }

    @Override
    public Application createApplication(Targets targets) {
        return new CompileApplication<>(createCompiler(), targets);
    }
}
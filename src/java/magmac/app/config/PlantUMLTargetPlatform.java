package magmac.app.config;

import magmac.app.Application;
import magmac.app.CompileApplication;
import magmac.app.compile.Compiler;
import magmac.app.compile.StagedCompiler;
import magmac.app.compile.rule.Rule;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.lang.JavaPlantUMLParser;
import magmac.app.lang.node.PlantUMLRoot;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;

import java.nio.file.Path;
import java.nio.file.Paths;

public final class PlantUMLTargetPlatform implements TargetPlatform {

    @Override
    public Path createTargetPath() {
        return Paths.get(".", "diagrams");
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

    private Application createApplication0(Targets targets) {
        return new CompileApplication<>(createCompiler(), targets);
    }

    @Override
    public Application createApplication() {
        Path targetPath = this.createTargetPath();
        String extension = this.createExtension();
        Targets targets = new PathTargets(targetPath, extension);
        return this.createApplication0(targets);
    }
}
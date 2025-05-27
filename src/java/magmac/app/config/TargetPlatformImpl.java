package magmac.app.config;

import magmac.app.compile.rule.Rule;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.lang.AfterPasser;
import magmac.app.lang.FlattenJava;
import magmac.app.lang.MergeDiagram;
import magmac.app.lang.PlantUMLRoots;
import magmac.app.lang.TypeScriptPasser;
import magmac.app.lang.TypescriptRoots;
import magmac.app.stage.AfterAll;
import magmac.app.stage.EmptyAfterAll;
import magmac.app.stage.Passer;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.parse.TreeParser;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum TargetPlatformImpl implements TargetPlatform {
    PlantUML,
    TypeScript;

    @Override
    public Generator createGenerator() {
        return new RuleGenerator(this.createRule());
    }

    @Override
    public Parser createParser() {
        AfterAll afterAllChildren = this.createAfterAll();
        Passer afterChild = this.createAfterChild();
        return new TreeParser(new FlattenJava(), afterChild, afterAllChildren);
    }

    private Passer createAfterChild() {
        return switch (this) {
            case PlantUML -> new AfterPasser();
            case TypeScript -> new TypeScriptPasser();
        };
    }

    private Rule createRule() {
        return switch (this) {
            case PlantUML -> PlantUMLRoots.createRule();
            case TypeScript -> TypescriptRoots.createRule();
        };
    }

    @Override
    public Targets createTargets() {
        Path targetPath = this.createTargetPath();
        String extension = this.createExtension();

        return new PathTargets(targetPath, extension);
    }

    private String createExtension() {
        return switch (this) {
            case PlantUML -> "puml";
            case TypeScript -> "ts";
        };
    }

    private AfterAll createAfterAll() {
        return switch (this) {
            case PlantUML -> new MergeDiagram();
            case TypeScript -> new EmptyAfterAll();
        };
    }

    private Path createTargetPath() {
        return switch (this) {
            case PlantUML -> Paths.get(".", "diagrams");
            case TypeScript -> Paths.get(".", "src", "web");
        };
    }
}

package magmac.app.lang;

import magmac.app.compile.rule.Rule;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.stage.AfterAll;
import magmac.app.stage.EmptyAfterAll;
import magmac.app.stage.Passer;
import magmac.app.stage.generate.Generator;
import magmac.app.stage.generate.RuleGenerator;
import magmac.app.stage.parse.Parser;
import magmac.app.stage.parse.TreeParser;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum TargetPlatform {
    PlantUML,
    TypeScript;

    public Generator createGenerator() {
        return new RuleGenerator(this.createRule());
    }

    public Parser createParser() {
        AfterAll afterAllChildren = this.createAfterAll();
        Passer afterChild = this.createAfterChild();
        return new TreeParser(new FlattenJava(), afterChild, afterAllChildren);
    }

    public Passer createAfterChild() {
        return switch (this) {
            case PlantUML -> new AfterPasser();
            case TypeScript -> new TypeScriptPasser();
        };
    }

    public Rule createRule() {
        return switch (this) {
            case PlantUML -> PlantUMLRoots.createRule();
            case TypeScript -> TypescriptRoots.createRule();
        };
    }

    public Targets createTargets() {
        Path targetPath = this.createTargetPath();
        String extension = this.createExtension();

        return new PathTargets(targetPath, extension);
    }

    public String createExtension() {
        return switch (this) {
            case PlantUML -> "puml";
            case TypeScript -> "ts";
        };
    }

    public AfterAll createAfterAll() {
        return switch (this) {
            case PlantUML -> new MergeDiagram();
            case TypeScript -> new EmptyAfterAll();
        };
    }

    public Path createTargetPath() {
        return switch (this) {
            case PlantUML -> Paths.get(".", "diagrams");
            case TypeScript -> Paths.get(".", "src", "web");
        };
    }
}

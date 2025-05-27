package magmac.app.lang;

import magmac.app.compile.rule.Rule;
import magmac.app.io.targets.PathTargets;
import magmac.app.io.targets.Targets;
import magmac.app.stage.AfterAll;
import magmac.app.stage.EmptyAfterAll;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum TargetPlatform {
    PlantUML,
    TypeScript;

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

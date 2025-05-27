package magmac.app.lang;

import magmac.app.stage.AfterAll;
import magmac.app.stage.EmptyAfterAll;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum TargetPlatform {
    PlantUML,
    TypeScript;

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

package magmac.app.lang;

import java.nio.file.Path;
import java.nio.file.Paths;

public enum TargetPlatform {
    PlantUML,
    TypeScript;

    public Path createTargetPath() {
        return switch (this) {
            case PlantUML -> Paths.get(".", "diagrams");
            case TypeScript -> Paths.get(".", "src", "web");
        };
    }
}

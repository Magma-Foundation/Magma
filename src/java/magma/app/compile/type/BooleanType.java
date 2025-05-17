package magma.app.compile.type;

import magma.api.Type;
import magma.app.io.Platform;

public record BooleanType(Platform platform) implements Type {
    @Override
    public String generate() {
        if (Platform.TypeScript == this.platform) {
            return "boolean";
        }

        return "Bool";
    }

    @Override
    public boolean isFunctional() {
        return false;
    }

    @Override
    public boolean isVar() {
        return false;
    }

    @Override
    public String generateBeforeName() {
        return "";
    }
}

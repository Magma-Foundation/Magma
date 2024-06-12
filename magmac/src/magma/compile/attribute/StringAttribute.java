package magma.compile.attribute;

import java.util.Optional;

public record StringAttribute(String value) implements Attribute {
    @Override
    public Optional<String> asString() {
        return Optional.of(value);
    }

    @Override
    public String format(int depth) {
        return "\"" + value + "\"";
    }
}

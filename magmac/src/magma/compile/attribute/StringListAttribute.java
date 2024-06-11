package magma.compile.attribute;

import java.util.List;
import java.util.Optional;

public record StringListAttribute(List<String> values) implements Attribute {
    @Override
    public Optional<List<String>> asStringList() {
        return Optional.of(values);
    }
}

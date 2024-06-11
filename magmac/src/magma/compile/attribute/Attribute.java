package magma.compile.attribute;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    default Optional<String> asString() {
        return Optional.empty();
    }

    default Optional<List<String>> asStringList() {
        return Optional.empty();
    }
}

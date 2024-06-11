package magma.compile.attribute;

import java.util.List;
import java.util.Optional;

/**
 * The Attribute interface provides methods to retrieve attribute values
 * as different types. This interface includes default methods to get
 * the attribute value as a string or a list of strings.
 */
public interface Attribute {

    /**
     * Retrieves the attribute value as a string.
     *
     * @return an Optional containing the string value if present, otherwise empty
     */
    default Optional<String> asString() {
        return Optional.empty();
    }

    /**
     * Retrieves the attribute value as a list of strings.
     *
     * @return an Optional containing the list of string values if present, otherwise empty
     */
    default Optional<List<String>> asStringList() {
        return Optional.empty();
    }
}

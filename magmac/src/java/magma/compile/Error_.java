package magma.compile;

import java.util.List;
import java.util.Optional;

/**
 * Interface representing an error that may occur during compilation, with methods to retrieve error details.
 */
public interface Error_ {

    /**
     * Retrieves the error message, if present.
     *
     * @return an Optional containing the error message, or an empty Optional if no message is present
     */
    Optional<String> findMessage();

    /**
     * Retrieves the list of causes for this error, if any.
     *
     * @return an Optional containing a list of causes, or an empty Optional if no causes are present
     */
    Optional<List<Error_>> findCauses();

    /**
     * Retrieves the context in which the error occurred, if present.
     *
     * @return an Optional containing the error context, or an empty Optional if no context is present
     */
    Optional<String> findContext();

    /**
     * Calculates the depth of the error, which may represent the number of nested causes.
     *
     * @return an integer representing the depth of the error
     */
    int calculateDepth();
}
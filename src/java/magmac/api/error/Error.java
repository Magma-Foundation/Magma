package magmac.api.error;

/**
 * Marker interface for compiler errors.
 */

public interface Error {
    /**
     * Returns a human-readable description.
     */
    String display();
}

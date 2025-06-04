package magmac.api.collect.map;

import java.util.HashMap;

/**
 * Factory methods for creating {@link Map} instances.
 */

public final class Maps {
    /**
     * Returns an empty map instance.
     */
    public static <K, V> Map<K, V> empty() {
        return new JVMMap<>(new HashMap<>());
    }
}

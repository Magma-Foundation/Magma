package magmac.api.collect.map;

import java.util.HashMap;

public class Maps {
    public static <K, V> Map<K, V> empty() {
        return new JVMMap<>(new HashMap<>());
    }
}

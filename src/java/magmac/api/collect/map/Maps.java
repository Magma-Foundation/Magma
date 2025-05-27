package magmac.api.collect.map;

import java.util.HashMap;

public class Maps {
    public static <K, V> JVMMap<K, V> empty() {
        return new JVMMap<>(new HashMap<>());
    }
}

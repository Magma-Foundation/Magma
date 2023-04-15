package com.meti;

import java.util.Optional;

public interface Node {
    boolean is(Object key);

    Optional<Attribute> apply(Object key);
}

package com.meti;

import java.util.Optional;

public interface Node {
    Optional<Attribute> apply(String key);
}

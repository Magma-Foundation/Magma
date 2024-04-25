package com.meti;

import java.util.Optional;

public interface Result {
    Optional<String> findInstanceValue();

    Optional<String> findStaticValue();
}

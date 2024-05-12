package com.meti;

import java.util.Optional;

public interface Node {
    Optional<String> findValue();

    Optional<String> findName();
}

package com.meti;

import java.util.List;
import java.util.Optional;

public interface StackResult {
    Optional<List<Node>> findInner();

    Optional<String> findOuter();

    StackResult withOuter(String outer);

    StackResult withInner(String inner);
}

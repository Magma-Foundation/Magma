package com.meti;

import java.util.List;
import java.util.Optional;

public interface StackResult {
    Optional<List<Node>> findInner();

    Optional<List<Node>> findOuter();

    StackResult withOuter(Node outer);

    StackResult withInner(String inner);
}

package com.meti;

import java.util.Optional;

interface StackResult {
    Optional<String> findInner();

    Optional<String> findOuter();

    StackResult withOuter(String outer);
}

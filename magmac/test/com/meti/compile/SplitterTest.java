package com.meti.compile;

import com.meti.collect.stream.Collectors;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SplitterTest {
    @Test
    void invoke_lambda() {
        assertIterableEquals(new Splitter("test(() -> {})")
                .split()
                .collect(Collectors.toNativeList()),
                List.of("test(() -> {})"));
    }
}
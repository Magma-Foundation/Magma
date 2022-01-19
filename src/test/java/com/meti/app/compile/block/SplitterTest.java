package com.meti.app.compile.block;

import com.meti.app.compile.common.block.Splitter;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class SplitterTest {

    @Test
    void split() {
        var value = new RootText("test(() => {})");
        var actual = new Splitter(value)
                .split()
                .collect(Collectors.toList());
        var expected = List.of(value);
        assertIterableEquals(expected, actual);
    }
}
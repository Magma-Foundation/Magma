package com.meti.app.compile.common.block;

import com.meti.app.compile.node.Text;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class SplitterTest {

    @Test
    void split() {
        var value = new Text("test(() => {})");
        var actual = new Splitter(value)
                .split()
                .collect(Collectors.toList());
        var expected = List.of(value);
        assertIterableEquals(expected, actual);
    }
}
package com.meti.app.compile.split;

import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.common.block.Splitter;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class SplitterTest {

    @Test
    void split() throws StreamException {
        var value = new RootText("test(() => {})");
        var actual = new Splitter(value)
                .split()
                .foldRight(new ArrayList<>(), (inputs, input) -> {
                    inputs.add(input);
                    return inputs;
                });
        var expected = List.of(value);
        assertIterableEquals(expected, actual);
    }
}
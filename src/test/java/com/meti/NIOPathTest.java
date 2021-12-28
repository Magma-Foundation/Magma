package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.NIOPath.Root;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NIOPathTest {

    @Test
    void computeFileNameWithoutExtension() {
        var expected = "test";
        var value = Root.resolveChild(expected + ".mg");
        var actual = value.computeFileNameWithoutExtension();
        assertEquals(expected, actual);
    }

    @Test
    void resolveSibling() {
        var expected = Root.resolveChild("sibling");
        var actual = Root.resolveChild("origin")
                .resolveSibling("sibling");
        assertEquals(expected, actual);
    }
}
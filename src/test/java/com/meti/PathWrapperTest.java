package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathWrapperTest {

    @Test
    void computeRetractedFileName() {
        var wrapper = PathWrapper.Root.resolve("test.c");
        var name = wrapper.computeRetractedFileName();
        assertEquals("test", name);
    }
}
package com.meti.api.io;

import org.junit.jupiter.api.Test;

import static com.meti.api.io.NIOFile.Root;
import static org.junit.jupiter.api.Assertions.*;

class NIOFileTest {

    @Test
    void computeFileNameWithoutExtension() {
        var expected = "test";
        var value = Root.resolveChild(expected + ".mg");
        var actual = value.computeFileNameWithoutExtension();
        assertEquals(expected, actual);
    }

    @Test
    void validate_existing() throws IOException {
        var path = Root.resolveChild("test");
        path.create();
        boolean exists = path.existing().isPresent();
        path.deleteIfExists();
        assertTrue(exists);
    }

    @Test
    void invalidate_existing(){
        assertFalse(Root.resolveChild("test").existing().isPresent());
    }

    @Test
    void resolveSibling() {
        var expected = Root.resolveChild("sibling");
        var actual = Root.resolveChild("origin")
                .resolveSibling("sibling");
        assertEquals(expected, actual);
    }
}
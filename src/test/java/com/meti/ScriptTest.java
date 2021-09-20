package com.meti;

import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScriptTest {

    @Test
    void validate_has_extension_of() {
        var script = new Script(Paths.get(".", ".a"));
        assertTrue(script.hasExtensionOf("a"));
    }

    @Test
    void invalidate_has_extension_of() {
        var script = new Script(Paths.get(".", ".b"));
        assertFalse(script.hasExtensionOf("a"));
    }
}
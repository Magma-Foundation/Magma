package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CRenderingStageTest {
    @Test
    void testFunction() {
        renderImpl("U16", 0, "unsigned int test(){return 0;}");
    }

    private void renderImpl(String type, int value, String expected) {
        try {
            var actual = new CRenderingStage("test", type, value).render();
            assertEquals(expected, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void testFunctionBody() {
        renderImpl("I16", 420, "int test(){return 420;}");
    }
}
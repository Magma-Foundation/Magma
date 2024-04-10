package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MagmaTest {

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> MagmaToJavaCompiler.compileMagmaToJava(FeatureTest.TEST_UPPER_SYMBOL, "test"));
    }

    @Test
    void magmaSmallest() throws CompileException {
        assertEquals("", JavaToMagmaCompiler.compileJavaToMagma(MagmaToJavaCompiler.compileMagmaToJava(FeatureTest.TEST_UPPER_SYMBOL, "")));
    }

}

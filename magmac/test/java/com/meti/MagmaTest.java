package com.meti;

import static com.meti.Application.compileJavaToMagma;
import static com.meti.Application.compileMagmaToJava;
import static com.meti.CompiledTest.TEST_UPPER_SYMBOL;
import static com.meti.Lang.renderBlock;
import static com.meti.Lang.renderMagmaFunction;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class MagmaTest {
    static void assertMagma(String input) {
        try {
            assertEquals(input, compileJavaToMagma(compileMagmaToJava(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    static void assertMagmaFunctionStatements(String definition) {
        assertMagma(renderMagmaFunction(TEST_UPPER_SYMBOL, renderBlock(definition)));
    }
}

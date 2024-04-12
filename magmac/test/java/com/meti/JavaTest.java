package com.meti;

import static com.meti.Application.compileJavaToMagma;
import static com.meti.Application.compileMagmaToJava;
import static com.meti.CompiledTest.TEST_UPPER_SYMBOL;
import static com.meti.Lang.renderBlock;
import static com.meti.Lang.renderJavaClass;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaTest {
    static void assertJava(String input) {
        try {
            assertEquals(input, compileMagmaToJava(compileJavaToMagma(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    static void assertJavaClassContent(String content) {
        assertJava(renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(content)));
    }
}

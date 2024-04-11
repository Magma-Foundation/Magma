package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.Application.*;
import static com.meti.CompiledTest.TEST_LOWER_SYMBOL;
import static com.meti.CompiledTest.TEST_UPPER_SYMBOL;
import static org.junit.jupiter.api.Assertions.*;

public class JavaFeatureTest {
    private static void assertJava(String input) {
        try {
            assertEquals(input, compileMagmaToJava(compileJavaToMagma(input)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSimple(String name) {
        assertJava(renderJavaClass(name));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void definitionName(String name) {
        assertJavaClassMember(renderJavaDefinition(name));
    }

    private static void assertJavaClassMember(String content) {
        assertJava(renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(content)));
    }

    @Test
    void definitionType() {
        assertJavaClassMember(renderJavaDefinition(TEST_LOWER_SYMBOL, INT_TYPE));
    }

    @Test
    void javaPublic() {
        assertJava(PUBLIC_KEYWORD + renderJavaClass(TEST_UPPER_SYMBOL));
    }
}

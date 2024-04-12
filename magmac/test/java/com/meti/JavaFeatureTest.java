package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.meti.Application.compileJavaToMagma;
import static com.meti.Application.compileMagmaToJava;
import static com.meti.CompiledTest.TEST_UPPER_SYMBOL;
import static com.meti.Lang.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JavaFeatureTest {
    @Test
    void javaPackage() throws CompileException {
        var namespace = List.of("com", "meti");
        var input = renderPackage(namespace) + renderJavaClass(TEST_UPPER_SYMBOL);
        var magmaSource = compileJavaToMagma(input);
        assertEquals(input, compileMagmaToJava(magmaSource, namespace));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaPackage(String name) throws CompileException {
        var input = renderPackage(name) + renderJavaClass(TEST_UPPER_SYMBOL);
        var magmaSource = compileJavaToMagma(input);
        assertEquals(input, compileMagmaToJava(magmaSource, List.of(name)));
    }

    @Test
    void javaInvalid() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSimple(String name) {
        JavaTest.assertJava(renderJavaClass(name));
    }

    @Test
    void javaPublic() {
        JavaTest.assertJava(PUBLIC_KEYWORD_WITH_SPACE + renderJavaClass(TEST_UPPER_SYMBOL));
    }
}

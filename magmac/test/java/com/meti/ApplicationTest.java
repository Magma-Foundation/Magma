package com.meti;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.JavaLang.*;
import static com.meti.Lang.renderBlock;
import static com.meti.MagmaLang.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {
    public static final String TEST_UPPER_SYMBOL = "Test";

    private static String compileMagmaToJava(String namespace, String magmaInput) throws CompileException {
        if (magmaInput.isEmpty()) {
            return renderJavaClass(namespace);
        }

        if (magmaInput.startsWith(LET_KEYWORD)) {
            var before = magmaInput.substring(LET_KEYWORD.length(), magmaInput.indexOf('=')).strip();
            var separator = before.indexOf(':');

            String name;
            String outputType;
            if (separator == -1) {
                name = before;
                outputType = INT_TYPE;
            } else {
                name = before.substring(0, separator).strip();
                var inputType = before.substring(separator + 1).strip();
                if (inputType.equals(I64)) {
                    outputType = LONG_TYPE;
                } else {
                    throw new CompileException("Unknown Magma type: " + inputType);
                }
            }

            return renderJavaClass(namespace,
                    renderBlock(
                            renderJavaDefinition(name, outputType)
                    )
            );
        }

        throw createUnknownInput(magmaInput);
    }

    private static CompileException createUnknownInput(String magmaInput) {
        return new CompileException("Unknown input: " + magmaInput);
    }

    private static String compileJavaToMagma(String javaInput) throws CompileException {
        if (javaInput.startsWith(JavaLang.CLASS_KEYWORD)) {
            var contentStart = javaInput.indexOf('{');
            var contentEnd = javaInput.lastIndexOf('}');
            var contentString = javaInput.substring(contentStart + 1, contentEnd).strip();
            var separator = contentString.indexOf(';');
            if (separator == -1) return "";

            var fieldString = contentString.substring(0, separator).strip();
            if (fieldString.isEmpty()) return "";

            var valueSeparator = fieldString.indexOf('=');
            var before = fieldString.substring(0, valueSeparator).strip();
            var nameSeparator = before.lastIndexOf(' ');

            var type = before.substring(0, nameSeparator).strip();
            var name = before.substring(nameSeparator + 1).strip();

            if (type.equals(INT_TYPE)) {
                return renderMagmaDefinitionWithTypeString(name, "");
            } else {
                return renderMagmaDefinition(name, I64);
            }
        } else {
            throw createUnknownInput(javaInput);
        }
    }

    private static void assertJava(String namespace, String content) {
        try {
            assertEquals(content, compileMagmaToJava(namespace, compileJavaToMagma(content)));
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void invalidMagma() {
        assertThrows(CompileException.class, () -> compileMagmaToJava(TEST_UPPER_SYMBOL, "test"));
    }

    @Test
    void invalidJava() {
        assertThrows(CompileException.class, () -> compileJavaToMagma(""));
    }

    @Test
    void magmaSmallest() throws CompileException {
        assertEquals("", compileJavaToMagma(compileMagmaToJava(TEST_UPPER_SYMBOL, "")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void javaSmallest(String className) {
        assertJava(className, renderJavaClass(className));
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void javaField(String name) {
        assertJava(TEST_UPPER_SYMBOL, renderJavaClass(TEST_UPPER_SYMBOL, renderBlock(renderJavaDefinition(name))));
    }

    @Test
    void javaFieldType() {
        assertJava(TEST_UPPER_SYMBOL,
                renderJavaClass(TEST_UPPER_SYMBOL,
                        renderBlock(
                                renderJavaDefinition("test", JavaLang.LONG_TYPE)
                        )
                )
        );
    }
}

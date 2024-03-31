package com.meti;

import com.meti.java.JavaClassNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import com.meti.magma.MagmaMethodBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;

public class ClassFeatureTest {
    public static final String TEST_BODY = "0";

    @Test
    void testConstructor() {
        assertCompile(new JavaClassNodeBuilder()
                .withName(TEST_SYMBOL)
                .withContent(TEST_SYMBOL + "(){}"), new MagmaClassNodeBuilder().withName(TEST_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleClasses(String name) {
        assertCompile(new JavaClassNodeBuilder().withName(name), new MagmaClassNodeBuilder().withName(name));
    }

    @Test
    void testPublicKeyword() {
        assertCompile(new JavaClassNodeBuilder()
                        .withPrefix(Lang.PUBLIC_KEYWORD)
                        .withName(TEST_SYMBOL),
                new MagmaClassNodeBuilder()
                        .withPrefix("export ")
                        .withName(TEST_SYMBOL));
    }

    @Test
    void testBody() {
        assertCompile(new JavaClassNodeBuilder()
                        .withName(TEST_SYMBOL)
                        .withContent(TEST_BODY),
                new MagmaClassNodeBuilder()
                        .withName(TEST_SYMBOL)
                        .withContent(TEST_BODY));
    }
}

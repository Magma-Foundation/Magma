package com.meti;

import com.meti.java.JavaClassNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;

public class ClassFeatureTest {
    public static final String TEST_BODY = "0";

    @Test
    void testConstructor() {

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
                        .withName(FeatureTest.TEST_SYMBOL),
                new MagmaClassNodeBuilder()
                        .withPrefix("export ")
                        .withName(FeatureTest.TEST_SYMBOL));
    }

    @Test
    void testBody() {
        assertCompile(new JavaClassNodeBuilder()
                        .withName(FeatureTest.TEST_SYMBOL)
                        .withContent(TEST_BODY),
                new MagmaClassNodeBuilder()
                        .withName(FeatureTest.TEST_SYMBOL)
                        .withContent(TEST_BODY));
    }
}

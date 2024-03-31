package com.meti;

import com.meti.java.JavaClassNodeBuilder;
import com.meti.java.JavaLang;
import com.meti.magma.MagmaClassNodeBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;

public class ClassFeatureTest {
    public static final String TEST_BODY = "0";

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleClasses(String name) {
        assertCompile(JavaLang.renderJavaClass(name, ""), new MagmaClassNodeBuilder().withPrefix("").withName(name).withContent("").build().render());
    }

    @Test
    void testPublicKeyword() {
        assertCompile(new JavaClassNodeBuilder().setPrefix(Lang.PUBLIC_KEYWORD).setName(FeatureTest.TEST_SYMBOL).setContent("").createJavaClassNode().renderJavaClass(), new MagmaClassNodeBuilder().withPrefix("export ").withName(FeatureTest.TEST_SYMBOL).withContent("").build().render());
    }

    @Test
    void testBody() {
        assertCompile(JavaLang.renderJavaClass(FeatureTest.TEST_SYMBOL, TEST_BODY),
                new MagmaClassNodeBuilder().withPrefix("").withName(FeatureTest.TEST_SYMBOL).withContent(TEST_BODY).build().render());
    }
}

package com.meti;

import com.meti.java.JavaClassNodeBuilder;
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
        assertCompile(new JavaClassNodeBuilder().setPrefix("").setName(name).setContent("").createJavaClassNode().renderJavaClass(), new MagmaClassNodeBuilder().withPrefix("").withName(name).withContent("").build().render());
    }

    @Test
    void testPublicKeyword() {
        assertCompile(new JavaClassNodeBuilder().setPrefix(Lang.PUBLIC_KEYWORD).setName(FeatureTest.TEST_SYMBOL).setContent("").createJavaClassNode().renderJavaClass(), new MagmaClassNodeBuilder().withPrefix("export ").withName(FeatureTest.TEST_SYMBOL).withContent("").build().render());
    }

    @Test
    void testBody() {
        assertCompile(new JavaClassNodeBuilder().setPrefix("").setName(FeatureTest.TEST_SYMBOL).setContent(TEST_BODY).createJavaClassNode().renderJavaClass(),
                new MagmaClassNodeBuilder().withPrefix("").withName(FeatureTest.TEST_SYMBOL).withContent(TEST_BODY).build().render());
    }
}

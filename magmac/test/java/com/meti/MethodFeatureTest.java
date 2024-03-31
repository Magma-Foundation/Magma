package com.meti;

import com.meti.java.JavaClassNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import com.meti.magma.MagmaDeclaration;
import com.meti.magma.MagmaMethodBuilder;
import com.meti.magma.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;
import static com.meti.FeatureTest.assertWithinClass;

public class MethodFeatureTest {
    @Test
    void testWithNoBody() {
        assertWithinClass(new JavaMethodBuilder()
                        .withName(TEST_SYMBOL)
                        .withContent(";")
                        .build()
                        .render(),
                new MagmaDeclaration("", "", TEST_SYMBOL, "() => Void") + ";");
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second"})
    void testSimpleMethod(String name) {
        assertWithinClass(new JavaMethodBuilder()
                        .withName(name)
                        .withContent("{}"),
                new MagmaMethodBuilder()
                        .withName(name)
                        .withType("Void")
                        .withContent("{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testAnnotation(String name) {
        String prefix = new Annotation(name, "").render();
        assertWithinClass(new JavaMethodBuilder()
                        .withAnnotations(prefix)
                        .withName(TEST_SYMBOL)
                        .withContent("{}"),
                new MagmaMethodBuilder()
                        .withPrefix(new Annotation(name, "").render())
                        .withName(TEST_SYMBOL)
                        .withType("Void")
                        .withContent("{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testException(String name) {
        assertWithinClass(new JavaMethodBuilder()
                        .withName(TEST_SYMBOL)
                        .withThrows(" throws " + name + " ")
                        .withContent("{}"),
                new MagmaMethodBuilder()
                        .withName(TEST_SYMBOL)
                        .withType("Void")
                        .withContent("{}")
                        .withExceptionString(" ? " + name));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3})
    void testMultipleAnnotations(int count) {
        var rendered = new Annotation("First", "").render().repeat(count);
        assertWithinClass(new JavaMethodBuilder()
                        .withAnnotations(rendered)
                        .withName(TEST_SYMBOL)
                        .withContent("{}"),
                new MagmaMethodBuilder()
                        .withPrefix(rendered)
                        .withName(TEST_SYMBOL)
                        .withType("Void")
                        .withContent("{}"));
    }

    @Test
    void testAnnotationWithValues() {
        String prefix = new Annotation("First", "(ints = {2, 3})").render();
        assertWithinClass(new JavaMethodBuilder()
                        .withAnnotations(prefix)
                        .withName(TEST_SYMBOL)
                        .withContent("{}"),
                new MagmaMethodBuilder()
                        .withPrefix(new Annotation("First", "(ints = [2, 3])").render())
                        .withName(TEST_SYMBOL)
                        .withType("Void")
                        .withContent("{}"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Foo", "Bar"})
    void testStatic(String className) {
        var content = new MagmaMethodBuilder()
                .withName(TEST_SYMBOL).withType("Void").withContent("{}")
                .build()
                .render();

        var rendered = new JavaMethodBuilder()
                .withFlagsString("static ")
                .withName(TEST_SYMBOL)
                .withContent("{}")
                .build()
                .render();

        assertCompile(new JavaClassNodeBuilder()
                        .withName(className)
                        .withContent(rendered)
                        .build()
                        .render(),
                new ObjectNode("", className, content).render() + "\n\n"
                + new MagmaClassNodeBuilder().withName(className).withContent("").build().render());
    }
}

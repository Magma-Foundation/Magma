package com.meti;

import com.meti.java.JavaClassNodeBuilder;
import com.meti.magma.MagmaClassNodeBuilder;
import com.meti.magma.MagmaMethodBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.meti.CompiledTest.assertCompile;
import static com.meti.FeatureTest.TEST_SYMBOL;

public class ClassFeatureTest {
    public static final String TEST_BODY = "0";

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void testConstructorParameter(int count) {
        var inputParams = IntStream.range(0, count).mapToObj(i -> "int value" + i).collect(Collectors.joining(", "));

        var outputParams = IntStream.range(0, count).mapToObj(i -> "value" + i + " : I32").collect(Collectors.joining(", "));

        assertCompile(new JavaClassNodeBuilder().withName(TEST_SYMBOL).withContent(new JavaMethodBuilder().withName(TEST_SYMBOL).withContent("{}").withParameters(inputParams)), new MagmaMethodBuilder().withPrefix("class ").withName(TEST_SYMBOL).withParameters(outputParams).withContent("{\n}"));
    }

    @Test
    void testConstructor() {
        assertCompile(new JavaClassNodeBuilder().withName(TEST_SYMBOL).withContent(TEST_SYMBOL + "(){}"), new MagmaClassNodeBuilder().withName(TEST_SYMBOL));
    }

    @ParameterizedTest
    @ValueSource(strings = {"First", "Second"})
    void testSimpleClasses(String name) {
        assertCompile(new JavaClassNodeBuilder().withName(name), new MagmaClassNodeBuilder().withName(name));
    }

    @Test
    void testPublicKeyword() {
        assertCompile(new JavaClassNodeBuilder().withPrefix(Lang.PUBLIC_KEYWORD).withName(TEST_SYMBOL), new MagmaClassNodeBuilder().withPrefix("export ").withName(TEST_SYMBOL));
    }

    @Test
    void testBody() {
        assertCompile(new JavaClassNodeBuilder()
                        .withName(TEST_SYMBOL)
                        .withContent(TEST_BODY),
                new MagmaClassNodeBuilder()
                        .withName(TEST_SYMBOL)
                        .withContent("{\n\t" + TEST_BODY + "\n}"));
    }
}

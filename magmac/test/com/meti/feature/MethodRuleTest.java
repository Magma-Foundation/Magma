package com.meti.feature;

import com.meti.FeatureTest;
import com.meti.Tuple;
import com.meti.stage.JavaLexingStage;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodRuleTest extends FeatureTest {
    private static final Stack<String> stack = new Stack<>();

    private static void assertMethod(String input) {
        assertTrue(JavaLexingStage.METHOD_RULE.lexImpl(input).map(Tuple::b).isPresent());
    }

    @Test
    void type() {
        assertMethod("int empty(){}");
    }

    @Test
    void simple() {
        assertMethod("void empty(){}");
    }

    @Test
    void name() {
        assertMethod("void test(){}");
    }

    @Test
    void body() {
        assertMethod("void test(){int temp = 0;}");
    }
}

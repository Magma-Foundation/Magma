package com.meti.feature;

import com.meti.FeatureTest;
import com.meti.java.MethodLexer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MethodRuleTest extends FeatureTest {
    private static void assertMethod(String input) {
        assertTrue(MethodLexer.RULE.apply(input).isPresent());
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

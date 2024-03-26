package com.meti.feature;

import com.meti.FeatureTest;
import com.meti.rule.*;
import com.meti.stage.JavaLexingStage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.meti.rule.AndRule.And;

public class InvocationRuleTest extends FeatureTest {
    private static void assertInvocation(String input) {
        Assertions.assertTrue(JavaLexingStage.INVOKE.apply(input).isPresent());
    }

    private static void assertValid(String value, Rule rule) {
        Assertions.assertTrue(rule.apply(value).isPresent());
    }

    @Test
    void simple() {
        assertInvocation("empty()");
    }

    @Test
    void simple0() {
        assertValid("empty", new ContentRule("caller", "value", 0));
    }

    @Test
    void simple1() {
        assertValid("empty(", And(
                new ContentRule("caller", "value", 0),
                new RequireRule("(")));
    }

    @Test
    void simple2() {
        assertValid("empty(", And(
                new ContentRule("caller", "value", 0),
                new RequireRule("("),
                new ListRule(new ExtractNodeElementRule("arguments", "value", 0))));
    }

    @Test
    void simple3() {
        assertValid("", new ListRule(new ExtractNodeElementRule("arguments", "value", 0)));
    }

    @Test
    void simple4() {
        assertValid("(", And(
                new RequireRule("("),
                new ListRule(new ExtractNodeElementRule("arguments", "value", 0))));
    }

    @Test
    void caller() {
        assertInvocation("test()");
    }

    @Test
    void oneParameter() {
        assertInvocation("test(1)");
    }

    @Test
    void twoParameters() {
        assertInvocation("test(1, 2)");
    }
}

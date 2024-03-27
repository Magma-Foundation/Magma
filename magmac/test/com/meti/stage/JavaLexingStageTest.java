package com.meti.stage;

import com.meti.rule.*;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static com.meti.rule.AndRule.And;
import static com.meti.rule.WhitespaceRule.PADDING;
import static com.meti.rule.WhitespaceRule.WHITESPACE;
import static com.meti.stage.JavaLexingStage.VOLATILE_VALUE_RULE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaLexingStageTest {
    private final Stack<String> stack = new Stack<>();

    @Test
    void test1() {
        var present = ((Rule) new NodeRule("value", VOLATILE_VALUE_RULE)).lexImpl("0")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test2() {
        Rule rule = And(
                new ListDelimitingRule(WHITESPACE, new StringListRule("flags", Rules.Enum("public", "final"))),
                PADDING,
                Rules.ExtractSymbol("type"),
                WHITESPACE,
                Rules.ExtractSymbol("name"),
                PADDING,
                new RequireRule("="),
                PADDING
        );
        var present = rule.lexImpl("int x = ")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test18() {
        Rule rule = And(
                new ListDelimitingRule(WHITESPACE, new StringListRule("flags", Rules.Enum("public", "final"))),
                PADDING
        );
        var present = rule.lexImpl("")
                .isPresent();

        assertTrue(present);
    }


    @Test
    void test16() {
        Rule rule = And(
                new ListDelimitingRule(WHITESPACE, new StringListRule("flags", Rules.Enum("public", "final"))),
                PADDING,
                Rules.ExtractSymbol("type"),
                WHITESPACE
        );
        var present = rule.lexImpl("int ")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test17() {
        Rule rule = And(
                Rules.ExtractSymbol("type"),
                WHITESPACE
        );
        var present = rule.lexImpl("int ")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test15() {
        Rule rule = And(
                Rules.ExtractSymbol("name"),
                PADDING,
                new RequireRule("="),
                PADDING
        );
        var present = rule.lexImpl("x = ")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test14() {
        Rule rule = And(
                PADDING,
                new RequireRule("="),
                PADDING
        );
        var present = rule.lexImpl(" = ")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test13() {
        Rule rule = And(
                new RequireRule("="),
                PADDING
        );
        var present = rule.lexImpl("= ")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test12() {
        Rule rule = PADDING;
        var present = rule.lexImpl(" ")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test5() {
        Rule rule = And(
                WHITESPACE,
                new NodeRule("value", VOLATILE_VALUE_RULE)
        );
        var present = rule.lexImpl(" 0").isPresent();

        assertTrue(present);
    }

    @Test
    void test6() {
        var rule = new LazyRule();
        rule.set(OrRule.Or(
                new NamedRule("string", JavaLexingStage.STRING),
                new NamedRule("int", JavaLexingStage.INT),
                new NamedRule("symbol", JavaLexingStage.SYMBOL_TOKEN),
                new NamedRule("field", JavaLexingStage.FIELD),
                new NamedRule("invoke", JavaLexingStage.INVOKE)
        ));

        Rule rule1 = And(
                WHITESPACE,
                new NodeRule("value", rule)
        );
        var present = rule1.lexImpl(" 0").isPresent();

        assertTrue(present);
    }

    @Test
    void test9() {
        var rule = new LazyRule();
        rule.set(
                new NamedRule("symbol", JavaLexingStage.SYMBOL_TOKEN)
        );

        Rule rule1 = And(
                WHITESPACE,
                new NodeRule("value", rule)
        );
        var present = rule1.lexImpl(" 0").isPresent();

        assertFalse(present);
    }

    @Test
    void test10() {
        var rule = new LazyRule();
        rule.set(
                new NamedRule("field", JavaLexingStage.FIELD)
        );

        Rule rule1 = And(
                WHITESPACE,
                new NodeRule("value", rule)
        );
        var present = rule1.lexImpl(" 0").isPresent();

        assertFalse(present);
    }

    @Test
    void invoke() {
        assertTrue(JavaLexingStage.INVOKE.lexImpl("test()").isPresent());
    }

    @Test
    void test11() {
        var rule = new LazyRule();
        rule.set(new NamedRule("invoke", JavaLexingStage.INVOKE));

        Rule rule1 = And(
                WHITESPACE,
                new NodeRule("value", rule)
        );
        var present = rule1.lexImpl(" test()").isPresent();

        assertTrue(present);
    }

    @Test
    void test8() {
        var rule = new LazyRule();
        rule.set(
                new NamedRule("int", JavaLexingStage.INT)
        );

        Rule rule1 = And(
                WHITESPACE,
                new NodeRule("value", rule)
        );
        var present = rule1.lexImpl(" 0").isPresent();

        assertTrue(present);
    }

    @Test
    void test7() {
        var rule = new LazyRule();
        rule.set(
                new NamedRule("string", JavaLexingStage.STRING)
        );

        Rule rule1 = And(
                WHITESPACE,
                new NodeRule("value", rule)
        );
        var present = rule1.lexImpl(" 0").isPresent();

        assertFalse(present);
    }

    @Test
    void
    test4() {
        Rule rule = And(
                PADDING,
                new NodeRule("value", VOLATILE_VALUE_RULE)
        );
        var present = rule.lexImpl(" 0").isPresent();

        assertTrue(present);
    }

    @Test
    void test3() {
        var present = JavaLexingStage.DEFINITION_RULE.lexImpl("int x = 0")
                .isPresent();

        assertTrue(present);
    }

    @Test
    void test() {
        var present = JavaLexingStage.DEFINITION_RULE.lexImpl("int x = 0")
                .isPresent();

        assertTrue(present);
    }
}
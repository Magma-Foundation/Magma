package com.meti.bug;

import com.meti.rule.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.meti.rule.AndRule.And;
import static com.meti.rule.WhitespaceRule.PADDING;
import static com.meti.rule.WhitespaceRule.WHITESPACE;
import static com.meti.stage.JavaLexingStage.*;

public class Bug0 {

    private static void assertValid(String input, Rule rule) {
        Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            var present = rule
                    .lexImpl(input)
                    .isPresent();

            Assertions.assertTrue(present);
        });
    }

    @Test
    void empty() {
        assertValid("", new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER)));
    }

    @Test
    void delimiter() {
        assertValid(";", new ListDelimitingRule(new RequireRule(";"), WhitespaceRule.PADDING));
    }

    @Test
    void line() {
        assertValid("int first = 1;", new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER)));
    }

    @Test
    void test() {
        assertValid("int first = 1; int second = 2;", new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER)));
    }

    @Test
    void noEnd() {
        assertValid("int first = 1; int second = 2",
                new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER)));
    }

    @Test
    void noEnd1() {
        assertValid("int first = 1; int second = 1",
                new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER)));
    }

    @Test
    void noEnd2() {
        assertValid("int first = 1; int second = 1",
                new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", new NamedRule("definition", DEFINITION_RULE))));
    }

    @Test
    void noEnd10() {
        assertValid("first 1;second 1", new ListDelimitingRule(new RequireRule(";"), And(
                Rules.ExtractSymbol("name"),
                PADDING,
                new NodeRule("value", VOLATILE_VALUE_RULE)
        )));
    }

    @Test
    void noEnd9() {
        assertValid(" 1; 1",
                new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", new NamedRule("definition", And(
                        PADDING,
                        new NodeRule("value", VOLATILE_VALUE_RULE)
                )))));
    }

    @Test
    void noEnd8() {
        assertValid("int first =; int second =",
                new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", new NamedRule("definition", And(
                        new ListDelimitingRule(WHITESPACE, new StringListRule("flags", Rules.Enum("public", "final"))),
                        PADDING,
                        Rules.ExtractSymbol("type"),
                        WHITESPACE,
                        Rules.ExtractSymbol("name"),
                        PADDING,
                        new RequireRule("=")
                )))));
    }

    @Test
    void noEnd7() {
        assertValid("1;1",
                new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("value", VOLATILE_VALUE_RULE)));
    }

    @Test
    void noEnd6() {
        assertValid("1;1",
                new ListDelimitingRule(new RequireRule(";"), new NodeRule("value", VOLATILE_VALUE_RULE)));
    }


    @Test
    void noEnd4() {
        assertValid("test();test()", new ListDelimitingRule(new RequireRule(";"), INVOKE));
    }

    @Test
    void noEnd3() {
        assertValid("1;1",
                new ListDelimitingRule(new RequireRule(";"), INT));
    }

    @Test
    void noEnd5() {
        assertValid("1;1", new ListDelimitingRule(new RequireRule(";"),
                new NodeElementRule("int", new NamedRule("int", INT))));
    }
}

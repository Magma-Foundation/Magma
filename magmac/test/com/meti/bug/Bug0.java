package com.meti.bug;

import com.meti.rule.*;
import com.meti.stage.JavaLexingStage;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Stack;

import static com.meti.rule.AndRule.And;
import static com.meti.rule.WhitespaceRule.PADDING;
import static com.meti.stage.JavaLexingStage.CLASS_MEMBER;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Bug0 {
    @Test
    void multiple() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            var present = new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER))
                    .lex("int first = 1; int second = 2;", new Stack<>())
                    .isPresent();

            assertTrue(present);
        });
    }

    @Test
    void onlyDefinitions() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            var present = new ListDelimitingRule(new RequireRule(";"),
                    new NamedRule("definition", JavaLexingStage.DEFINITION_RULE))
                    .lex("int first = 1; int second = 2;", new Stack<>())
                    .isPresent();

            assertTrue(present);
        });
    }

    @Test
    void cleaned() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            var present = new ListDelimitingRule(new RequireRule(";"),
                    new NamedRule("definition", And(
                            Rules.ExtractSymbol("name"),
                            PADDING,
                            new NodeRule("value", JavaLexingStage.VOLATILE_VALUE_RULE)
                    )))
                    .lex("first 1;second 2;", new Stack<>())
                    .isPresent();

            assertTrue(present);
        });
    }


    @Test
    void unwrapLazy() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            var inner = new LazyRule();
            inner.set(OrRule.Or(
                    JavaLexingStage.INT,
                    And(inner, new RequireRule(".")),
                    And(inner, new RequireRule("("))
            ));

            var present = new ListDelimitingRule(new RequireRule(";"),
                    new NamedRule("definition", And(
                            Rules.ExtractSymbol("name"),
                            PADDING,
                            inner
                    )))
                    .lex("first 1;second 2;", new Stack<>())
                    .isPresent();

            assertTrue(present);
        });
    }
}

package com.meti.bug;

import com.meti.rule.*;
import com.meti.stage.JavaLexingStage;
import org.junit.jupiter.api.Test;

import static com.meti.bug.RuleTest.assertValid;
import static com.meti.rule.AndRule.And;
import static com.meti.rule.WhitespaceRule.PADDING;
import static com.meti.rule.WhitespaceRule.WHITESPACE;

class Bug1 {
    @Test
    void noEnd1() {
        var volatileValueRule = new LazyRule();
        volatileValueRule.set(OrRule.Or(
                new NamedRule("int", JavaLexingStage.INT_RULE),
                new NamedRule("field", And(
                        volatileValueRule,
                        new RequireRule("."),
                        Rules.ExtractSymbol("member")
                ))
        ));

        assertValid("int first 1;int second 1", new ListDelimitingRule(new RequireRule(";"),
                And(Rules.ExtractSymbol("type"),
                        WHITESPACE,
                        Rules.ExtractSymbol("name"),
                        PADDING,
                        new NodeRule("value", volatileValueRule)
                )));
    }
}
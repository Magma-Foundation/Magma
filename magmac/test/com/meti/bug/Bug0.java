package com.meti.bug;

import com.meti.rule.ListDelimitingRule;
import com.meti.rule.NodeElementRule;
import com.meti.rule.RequireRule;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static com.meti.stage.JavaLexingStage.CLASS_MEMBER;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Bug0 {
    @Test
    void multiple() {
        assertTrue(new ListDelimitingRule(new RequireRule(";"), new NodeElementRule("body", CLASS_MEMBER))
                .lex("int first = 1; int second = 2;", new Stack<>())
                .isPresent());
    }
}

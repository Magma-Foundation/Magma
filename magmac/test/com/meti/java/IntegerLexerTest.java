package com.meti.java;

import com.meti.rule.ListRule;
import com.meti.rule.RangeRule;
import com.meti.rule.TextRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerLexerTest {
    @Test
    void valid(){
        assertTrue(((Lexer) new RuleLexer("int", new TextRule("value", new ListRule(new RangeRule('0', '9'))), "123"))
                .lex()
                .isPresent());
    }

    @Test
    void invalid() {
        assertFalse(((Lexer) new RuleLexer("int", new TextRule("value", new ListRule(new RangeRule('0', '9'))), "test"))
                .lex()
                .isPresent());
    }
}
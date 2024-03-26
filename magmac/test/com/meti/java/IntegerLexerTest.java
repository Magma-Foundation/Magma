package com.meti.java;

import com.meti.lex.Lexer;
import com.meti.lex.RuleLexer;
import com.meti.rule.ListRule;
import com.meti.rule.RangeRule;
import com.meti.rule.ExtractTextRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntegerLexerTest {
    @Test
    void valid(){
        assertTrue(((Lexer) new RuleLexer("int", "123", new ExtractTextRule("value", new ListRule(new RangeRule('0', '9')))))
                .lex()
                .isPresent());
    }

    @Test
    void invalid() {
        assertFalse(((Lexer) new RuleLexer("int", "test", new ExtractTextRule("value", new ListRule(new RangeRule('0', '9')))))
                .lex()
                .isPresent());
    }
}
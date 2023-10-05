package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValueRuleLexerTest {
    @Test
    void test() {
        var rule = new ValueRuleLexer(JavaString.apply("\" \""))
                .lex()
                .head()
                .unwrapOrElseGet(Assertions::fail);
        assertEquals(new ValueRule(JavaString.apply(" ")), rule);
    }
}
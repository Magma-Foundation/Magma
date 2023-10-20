package com.meti.compile.rule;

import com.meti.api.collect.JavaString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentRuleLexerTest {
    @Test
    void test() {
        var rule = new ContentRuleLexer(JavaString.apply("<test>"))
                .lex()
                .head()
                .unwrapOrElseGet(Assertions::fail);

        assertEquals(new ContentRule(JavaString.apply("test")), rule);
    }
}
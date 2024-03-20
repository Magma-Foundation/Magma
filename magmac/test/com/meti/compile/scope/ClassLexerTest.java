package com.meti.compile.scope;

import com.meti.collect.JavaList;
import com.meti.collect.option.IntentionalException;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.ExtractRule.Extract;
import static com.meti.compile.rule.ExtractSymbolRule.Symbol;
import static com.meti.compile.rule.WhitespaceRule.Whitespace;
import static com.meti.compile.scope.ClassLexer.EXTENDS;
import static com.meti.compile.scope.ClassLexer.RULE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClassLexerTest {
    @Test
    void what1() {
        assertTrue(Join(
                Whitespace,
                Extract("content")
        ).apply(JavaString.from("est {}")).isEmpty());
    }

    @Test
    void what() throws IntentionalException {
        var apply = Join(
                Symbol("name"),
                Whitespace,
                Extract("content")
        ).apply(JavaString.from("Test {}")).$();

        assertEquals(JavaString.from("Test"), apply.findText("name").$());
        assertEquals(JavaList.from(JavaString.from("{}")), apply.findTextList("content").$());
    }

    @Test
    void extendsEmpty() {
        assertTrue(EXTENDS.apply(JavaString.Empty).isPresent());
    }

    @Test
    void extendsPresent() throws IntentionalException {
        var extendsSuper = EXTENDS.apply(JavaString.from("extends Super ")).$();
        var actual = extendsSuper.findText("superclass").$();
        assertEquals(JavaString.from("Super"), actual);
    }

    @Test
    void rule() {
        assertTrue(RULE.apply(JavaString.from("class Test {}")).isPresent());
    }

    @Test
    void ruleWithFlags() {
        assertTrue(RULE.apply(JavaString.from("public static class Test {}")).isPresent());
    }

    @Test
    void rule_super() throws IntentionalException {
        var apply = RULE.apply(JavaString.from("class Test extends Super {}")).$();
        var actual = apply.findText("superclass").$();
        assertEquals(JavaString.from("Super"), actual);
    }

    @Test
    void apply() throws IntentionalException {
        var actual = new ClassLexer(JavaString.from("public static class Test extends Super {}"))
                .lex()
                .next()
                .$();

        assertEquals(JavaList.from(JavaString.from("public"), JavaString.from("static")),
                actual.apply("flags").$().asListOfStrings().$());

        assertEquals(JavaString.from("Test"), actual.apply("name").$().asString().$());
        assertEquals(JavaString.from("Super"), actual.apply("superclass").$().asString().$());
        assertEquals(JavaString.from("{}"), actual.apply("body").$().asNode().$()
                .apply("value")
                .$()
                .asString()
                .$());
    }
}
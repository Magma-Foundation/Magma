package com.meti.compile.scope;

import com.meti.collect.option.IntentionalException;
import com.meti.compile.rule.Rule;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static com.meti.compile.rule.ConjunctionRule.Join;
import static com.meti.compile.rule.ExtractSymbolRule.Symbol;
import static com.meti.compile.rule.Rules.Optional;
import static com.meti.compile.rule.TextRule.Text;
import static com.meti.compile.rule.WhitespaceRule.Whitespace;
import static com.meti.compile.scope.ClassLexer.EXTENDS_RULE;
import static com.meti.compile.scope.ClassLexer.PREFIX;
import static org.junit.jupiter.api.Assertions.*;

class ClassLexerTest {
    private static void assertValid(Rule rule, String slice) {
        assertTimeoutPreemptively(Duration.ofSeconds(1), () -> {
            assertTrue(rule.apply(new JavaString(slice)).isPresent());
        });
    }

    @Test
    void extendsRule() throws IntentionalException {
        var actual = EXTENDS_RULE.apply(new JavaString("extends Test"))
                .$()
                .findText("superclass")
                .$();
        assertEquals("Test", actual.inner());
    }

    @Test
    void flagRule() throws IntentionalException {
        var actual = ClassLexer.FLAG_RULE.apply(new JavaString("public static"))
                .$()
                .findTextList("flags")
                .$()
                .unwrap();

        assertIterableEquals(List.of(new JavaString("public"), new JavaString("static")), actual);
    }

    @Test
    void prefixWithContent() {
        assertValid(PREFIX, "public static ");
    }

    @Test
    void prefix() {
        assertValid(PREFIX, "");
    }

    @Test
    void lex1() {
        assertValid(Join(
                        PREFIX,
                        Text("class"),
                        Whitespace),
                "class ");
    }

    @Test
    void lex2() {
        assertValid(Join(
                        PREFIX,
                        Text("class"),
                        Whitespace,
                        Symbol("name"),
                        Whitespace),
                "class IntentionalException ");
    }

    @Test
    void lex3() {
        assertValid(Join(
                        PREFIX,
                        Text("class"),
                        Whitespace,
                        Symbol("name"),
                        Whitespace,
                        Optional(EXTENDS_RULE),
                        Whitespace),
                "class IntentionalException extends Exception ");
    }

/*    @Test
    void lex() throws IntentionalException {
        var actual = new ClassLexer(new JavaString("class IntentionalException extends Exception {}"))
                .lex()
                .next()
                .$();

        assertNotNull(actual);
    }*/
}
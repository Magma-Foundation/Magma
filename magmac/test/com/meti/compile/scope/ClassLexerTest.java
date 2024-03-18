package com.meti.compile.scope;

import com.meti.collect.option.IntentionalException;
import com.meti.collect.stream.Collectors;
import com.meti.collect.stream.Stream;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassLexerTest {
    @Test
    void extendsRule() throws IntentionalException {
        var actual = ClassLexer.EXTENDS_RULE.apply(new JavaString("extends Test"))
                .next()
                .$()
                .findText("superclass")
                .$();
        assertEquals("Test", actual.inner());
    }

    @Test
    void flagRule() throws IntentionalException {
        var actual = ClassLexer.FLAG_RULE.apply(new JavaString("public static"))
                .collect(Collectors.toList())
                .unwrap()
                .get(0)
                .findTextList("flags")
                .$()
                .unwrap();

        assertIterableEquals(List.of(new JavaString("public"), new JavaString("static")), actual);
    }

    @Test
    void lex() throws IntentionalException {
        var actual = new ClassLexer(new JavaString("class IntentionalException extends Exception {}"))
                .lex()
                .next()
                .$();

        assertNotNull(actual);
    }
}
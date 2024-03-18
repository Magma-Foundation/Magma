package com.meti.compile.scope;

import com.meti.collect.option.IntentionalException;
import com.meti.collect.stream.Stream;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClassLexerTest {

    @Test
    void lex() throws IntentionalException {
        var actual = new ClassLexer(new JavaString("class IntentionalException extends Exception {}"))
                .lex()
                .next()
                .$();

        assertNotNull(actual);
    }
}
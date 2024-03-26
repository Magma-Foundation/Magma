package com.meti.java;

import com.meti.FeatureTest;
import com.meti.lex.Lexer;
import com.meti.lex.RuleLexer;
import com.meti.stage.JavaLexingStage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefinitionLexerTest extends FeatureTest {
    private static void assertDefinition(String input) {
        Assertions.assertTrue(((Lexer) new RuleLexer("definition", input, JavaLexingStage.DEFINITION_RULE)).lex().isPresent());
    }

    @Test
    void simple() {
        assertDefinition("int value = 0");
    }

    @Test
    void name() {
        assertDefinition("int test = 0");
    }

    @Test
    void type() {
        assertDefinition("long value = 0");
    }

    @Test
    void value() {
        assertDefinition("int value = 100");
    }

    @Test
    void finalKeyword() {
        assertDefinition("final int value = 100");
    }

    @Test
    void publicKeyword() {
        assertDefinition("public final int value = 100");
    }
}

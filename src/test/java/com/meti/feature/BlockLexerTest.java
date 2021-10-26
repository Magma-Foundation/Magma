package com.meti.feature;

import com.meti.Input;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BlockLexerTest {

    @Test
    void isValid() {
        assertTrue(new BlockLexer(new Input("{}")).isValid());
    }

    @Test
    void processValid() {
        var expected = new Block(List.of(new Content(new Input("return 420"))));
        var actual = new BlockLexer(new Input("{return 420;}")).processValid();
        assertEquals(expected, actual);
    }
}
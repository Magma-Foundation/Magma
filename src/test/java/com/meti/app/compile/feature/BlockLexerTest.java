package com.meti.app.compile.feature;

import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Input;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockLexerTest {
    @Test
    void validate() {
        assertTrue(new BlockLexer(new Input("{}")).validate());
    }

    @Test
    void processDefined() throws CompileException {
        var expected = new Block(List.of(new Content(new Input("return 420"))));
        var actual = new BlockLexer(new Input("{return 420;}")).processDefined();
        assertEquals(expected, actual);
    }

}

package com.meti.app.compile;

import com.meti.app.compile.feature.Block;
import com.meti.app.compile.feature.IntegerNode;
import com.meti.app.compile.feature.Return;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaLexingStageTest {
    @Test
    void block() throws CompileException {
        var expected = new Block(List.of(new Return(new IntegerNode(420))));
        var actual = new MagmaLexingStage("{return 420;}").lex();
        assertEquals(expected, actual);
    }
}
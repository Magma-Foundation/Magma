package com.meti.app.compile.magma.lex;

import com.meti.app.compile.common.DefinitionNode;
import com.meti.app.compile.common.ReferenceType;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaLexerTest {
    @Test
    void inner_types() throws CompileException {
        var input = new InputNode(new RootText("let x : &I8"));

        var expected = new DefinitionNode(new Declaration("x", new ReferenceType(new IntegerType(true, 8))));
        var actual = new MagmaLexer().transformNodeAST(input);

        assertEquals(expected, actual);
    }
}
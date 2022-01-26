package com.meti.app.compile.magma;

import com.meti.app.compile.common.EmptyField;
import com.meti.app.compile.common.Field;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.common.returns.Return;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.stage.CompileException;
import com.meti.app.compile.text.RootText;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaLexerTest {
    @Test
    void block() throws CompileException {
        var lexer = new MagmaLexer();

        var expected = new Block(new Return(new IntegerNode(0)));
        var actual = lexer.transformNodeAST(new InputNode(new RootText("{return 0;}")));
        assertEquals(expected, actual);
    }

    @Test
    void implementation() throws CompileException {
        var lexer = new MagmaLexer();
        var output = lexer.transformNodeAST(new InputNode(new RootText("def main() : I16 => {return 0;}")));
        var identity = new EmptyField(new RootText("main"), new IntegerType(true, 16), Field.Flag.Def);
        var body = new Block(new Return(new IntegerNode(0)));
        var expected = new Implementation(identity, body);
        assertEquals(expected, output);
    }
}
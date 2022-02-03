package com.meti.app.compile.magma;

import com.meti.app.compile.common.Definition;
import com.meti.app.compile.common.Fields;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.feature.function.Return;
import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.magma.lex.MagmaLexer;
import com.meti.app.compile.node.InputNode;
import com.meti.app.compile.primitive.Primitive;
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
        var identity = new Declaration(new RootText("main"), new IntegerType(true, 16), Definition.Flag.Def);
        var body = new Block(new Return(new IntegerNode(0)));
        var expected = new Implementation(identity, body);
        assertEquals(expected, output);
    }

    @Test
    void implementation_with_parameters() throws CompileException {
        var lexer = new MagmaLexer();
        var input = new InputNode(new RootText("def empty(state : Bool) : Void => {return 0}"));
        var output = lexer.transformNodeAST(input);

        var identity = new Fields.Builder()
                .withName("empty")
                .withType(Primitive.Void)
                .withFlag(Definition.Flag.Def)
                .build();

        var parameter = new Fields.Builder()
                .withName("state")
                .withType(Primitive.Bool)
                .build();

        var body = new Block(new Return(new IntegerNode(0)));
        var expected = new Implementation(identity, body, parameter);
        assertEquals(expected, output);
    }
}
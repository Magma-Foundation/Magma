package com.meti.app.compile.feature.function;

import com.meti.app.compile.common.Definition;
import com.meti.app.compile.common.Implementation;
import com.meti.app.compile.common.block.Block;
import com.meti.app.compile.common.integer.IntegerNode;
import com.meti.app.compile.common.integer.IntegerType;
import com.meti.app.compile.feature.scope.Declaration;
import com.meti.app.compile.parse.State;
import com.meti.app.compile.stage.CompileException;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.magma.ImplicitType.ImplicitType_;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ImplementationParserTest {
    @Test
    void implicit() throws CompileException {
        var inputIdentity = new Declaration("main", ImplicitType_, Definition.Flag.Def);
        var inputRoot = new Implementation(inputIdentity, new Block(new Return(new IntegerNode(0))));

        var expectedIdentity = new Declaration("main", new IntegerType(true, 16), Definition.Flag.Def);
        var expectedRoot = new Implementation(expectedIdentity, new Block(new Return(new IntegerNode(0))));

        var input = new State(inputRoot);
        var output = new State(expectedRoot);

        var actual = new ImplementationParser(input).modifyBeforeASTImpl2();
        assertEquals(output, actual);
    }

    @Test
    void nominal() throws CompileException {
        var identity = new Declaration("main", new IntegerType(true, 16), Definition.Flag.Def);
        var root = new Implementation(identity, new Block(new Return(new IntegerNode(0))));
        var expected = new State(root);
        var actual = new ImplementationParser(expected).modifyBeforeASTImpl2();
        assertEquals(expected, actual);
    }
}
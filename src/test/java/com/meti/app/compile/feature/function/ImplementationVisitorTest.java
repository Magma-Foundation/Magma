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

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImplementationVisitorTest {
    @Test
    void nominal() throws CompileException {
        var identity = new Declaration("main", new IntegerType(true, 16), Definition.Flag.Def);
        var root = new Implementation(identity, new Block(new Return(new IntegerNode(0))));
        var expected = new State(root);
        var actual = new ImplementationVisitor(expected).onParseImpl();
        assertEquals(expected, actual);
    }
}
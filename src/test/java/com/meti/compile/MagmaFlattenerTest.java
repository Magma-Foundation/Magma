package com.meti.compile;

import com.meti.compile.cache.Cache;
import com.meti.compile.common.block.Block;
import com.meti.compile.common.returns.Return;
import com.meti.compile.common.variable.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaFlattenerTest {
    @Test
    void node() throws CompileException {
        var expected = new Cache(new Return(new Variable("first")), new Variable("second"));
        var actual = new MagmaFlattener().apply(new Return(new Cache(new Variable("first"), new Variable("second"))));
        assertEquals(expected, actual);
    }

    @Test
    void nodes() throws CompileException {
        var expected = new Cache(new Block(new Variable("first")), new Variable("second"));
        var actual = new MagmaFlattener().apply(new Block(new Cache(new Variable("first"), new Variable("second"))));
        assertEquals(expected, actual);
    }
}
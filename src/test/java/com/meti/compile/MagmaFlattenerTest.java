package com.meti.compile;

import com.meti.compile.common.returns.Return;
import com.meti.compile.common.variable.Variable;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaFlattenerTest {
    @Test
    void node() throws CompileException {
        var expected = new Cache(new Return(new Variable("test")));
        var actual = new MagmaFlattener().apply(new Return(new Cache(new Variable("test"))));
        assertEquals(expected, actual);
    }
}
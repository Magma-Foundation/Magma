package com.meti.app.compile;

import com.meti.app.compile.feature.Return;
import com.meti.app.compile.node.Content;
import com.meti.app.compile.node.Input;
import com.meti.app.magma.MagmaLexer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractStageTest {

    @Test
    void process() throws CompileException {
        var expected = new Return(new Content(new Input("420")));
        var actual = new MagmaLexer(new Input("return 420")).process();
        assertEquals(expected, actual);
    }
}
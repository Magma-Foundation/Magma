package com.meti.app.compile;

import com.meti.app.compile.feature.IntegerNode;
import com.meti.app.compile.feature.Return;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CRenderingStageTest {

    @Test
    void render() throws CompileException {
        var result = new CRenderingStage(new Return(new IntegerNode(420)))
                .render()
                .asString()
                .orElse("");
        assertEquals("return 420;", result);
    }
}
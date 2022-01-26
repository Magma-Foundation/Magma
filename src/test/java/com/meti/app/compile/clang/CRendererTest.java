package com.meti.app.compile.clang;

import com.meti.app.compile.common.variable.Variable;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.stage.CompileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CRendererTest {
    @Test
    void variable() throws CompileException {
        var output = new CRenderer().transformNodeAST(new Variable("test"))
                .apply(Attribute.Type.Value)
                .asOutput()
                .compute();
        assertEquals("test", output);
    }
}
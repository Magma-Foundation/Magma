package com.meti.app;

import com.meti.app.attribute.Attribute;
import com.meti.app.node.*;
import com.meti.app.process.MagmaRenderer;
import com.meti.app.stage.CRenderingProcessingStage;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class CompilerTest {
    private void assertFunction(String name, String type) {
        try {
            var input = new MagmaRenderer(name, type).render();
            var parameters = new Sequence(Collections.emptyList());
            var identity = type.equals("I16")
                    ? Primitive.I16.asFieldWithOnset(name, parameters)
                    : Primitive.U16.asFieldWithOnset(name, parameters);
            var root = new Function(identity, new Block(List.of(new Return(new IntegerNode(0)))));
            var output = new CRenderingProcessingStage(root)
                    .process()
                    .apply(Attribute.Type.Value)
                    .asString();
            CompiledTest.assertCompile(input, output);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void empty() {
        CompiledTest.assertCompile("", "");
    }

    @Test
    void name() {
        assertFunction("empty", "I16");
    }

    @Test
    void testMain() {
        assertFunction("main", "I16");
    }

    @Test
    void type() {
        assertFunction("test", "U16");
    }
}

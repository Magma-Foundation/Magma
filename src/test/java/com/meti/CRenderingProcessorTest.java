package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CRenderingProcessorTest {
    @Test
    void testFunction() {
        renderImpl("U16", 0, "unsigned int test(){return 0;}");
    }

    private void renderImpl(String type, int value, String expected) {
        try {
            var parameters = new Sequence(Collections.emptyList());
            var identity = type.equals("I16")
                    ? Primitive.I16.asField("test", parameters)
                    : Primitive.U16.asField("test", parameters);
            var root = new Function(identity, new Block(List.of(new Return(new IntegerNode(value)))));
            var actual = new CRenderingProcessingStage(root).process().apply(Attribute.Type.Value).asString();
            assertEquals(expected, actual);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void testFunctionBody() {
        renderImpl("I16", 420, "int test(){return 420;}");
    }
}
package com.meti;

import com.meti.node.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class CompilerTest {
    @Test
    void empty() {
        assertCompile("", "");
    }

    private void assertFunction(String name, String type) {
        try {
            var input = new MagmaRenderer(name, type).render();
            var parameters = new Sequence(Collections.emptyList());
            var identity = type.equals("I16")
                    ? Primitive.I16.asField(name, parameters)
                    : Primitive.U16.asField(name, parameters);
            var root = new Function(identity, new Block(List.of(new Return(new IntegerNode(0)))));
            var output = new CRenderingProcessingStage(root)
                    .process()
                    .apply(Attribute.Type.Value)
                    .asString();
            assertCompile(input, output);
        } catch (CompileException e) {
            fail(e);
        }
    }

    @Test
    void name() {
        assertFunction("empty", "I16");
    }

    private static void assertCompile(String input, String output) {
        try {
            var actual = new Compiler(input).compile();
            assertEquals(output, actual);
        } catch (CompileException e) {
            fail(e);
        }
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

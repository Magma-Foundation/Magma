package com.meti;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class DeclarationFeatureTest {
    @Test
    void declaration() {
        declare(new Declaration("x", PrimitiveType.I16, "420"));
    }

    private void declare(Declaration node) {
        try {
            var output = new Compiler(node.renderMagma()).compile();
            assertEquals(node.renderNative(), output);
        } catch (ApplicationException e) {
            fail(e);
        }
    }

    @Test
    void declaration_name() {
        declare(new Declaration("test", PrimitiveType.I16, "420"));
    }

    @Test
    void declaration_type() {
        declare(new Declaration("x", PrimitiveType.U16, "420"));
    }

    @Test
    void declaration_value() {
        declare(new Declaration("x", PrimitiveType.U16, "69"));
    }
}

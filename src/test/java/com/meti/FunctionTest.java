package com.meti;

import org.junit.jupiter.api.Test;

class FunctionTest extends FeatureTest {
    @Test
    void name() {
        var body = new Content("");
        var input = new MagmaFunctionRenderer().render(new Function(new Field("empty", "Void"), body));
        var output = new CFunctionRenderer().render(new Function(new Field("empty", "void"), body))
                .compute();
        assertCompile(input, output);
    }

    @Test
    void type() {
        var body = new Content("{return 420;}");
        var input = new MagmaFunctionRenderer().render(new Function(new Field("supplier", "U16"), body));
        var output = new CFunctionRenderer().render(new Function(new Field("supplier", "unsigned int"), body))
                .compute();
        assertCompile(input, output);
    }

    @Test
    void body() {
        var body = new Content("{return 420;}");
        var input = new MagmaFunctionRenderer().render(new Function(new Field("supplier", "I16"), body));
        var output = new CFunctionRenderer().render(new Function(new Field("supplier", "int"), body))
                .compute();
        assertCompile(input, output);
    }
}
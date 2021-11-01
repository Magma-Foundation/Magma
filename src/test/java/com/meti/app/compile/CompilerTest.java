package com.meti.app.compile;

import com.meti.app.clang.CRenderer;
import com.meti.app.compile.feature.Import;
import com.meti.app.magma.MagmaRenderingStage;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.feature.FeatureTest.assertCompile;

class CompilerTest {
    @Test
    void multiple() throws CompileException {
        var input = render("stdio") + ";" + render("stdlib") + ";";
        var first = renderNative("stdio");
        var second = renderNative("stdlib");
        var output = first + second;
        assertCompile(input, output);
    }

    private String renderNative(String stdio) throws CompileException {
        return new CRenderer(new Import(stdio)).process().asString().orElse("");
    }

    private String render(String name) throws CompileException {
        return new MagmaRenderingStage(new Import(name))
                .process()
                .asString()
                .orElse("");
    }
}
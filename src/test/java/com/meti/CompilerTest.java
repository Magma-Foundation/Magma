package com.meti;

import com.meti.clang.CRenderingStage;
import com.meti.feature.Import;
import com.meti.magma.MagmaRenderingStage;
import org.junit.jupiter.api.Test;

import static com.meti.feature.FeatureTest.assertCompile;

class CompilerTest {
    @Test
    void multiple() {
        var input = new MagmaRenderingStage(new Import("stdio"))
                            .render()
                            .asString()
                            .orElse("") + ";" + new MagmaRenderingStage(new Import("stdlib"))
                            .render()
                            .asString()
                            .orElse("") + ";";
        var first = new CRenderingStage(new Import("stdio")).render().asString().orElse("");
        var second = new CRenderingStage(new Import("stdlib")).render().asString().orElse("");
        var output = first + second;
        assertCompile(input, output);
    }
}
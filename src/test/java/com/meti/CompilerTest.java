package com.meti;

import com.meti.clang.CRenderingStage;
import com.meti.compile.feature.Import;
import com.meti.magma.MagmaRenderingStage;
import org.junit.jupiter.api.Test;

import static com.meti.compile.feature.FeatureTest.assertCompile;

class CompilerTest {
    @Test
    void multiple() {
        var input = new MagmaRenderingStage(new Import("stdio"))
                            .process()
                            .asString()
                            .orElse("") + ";" + new MagmaRenderingStage(new Import("stdlib"))
                            .process()
                            .asString()
                            .orElse("") + ";";
        var first = new CRenderingStage(new Import("stdio")).process().asString().orElse("");
        var second = new CRenderingStage(new Import("stdlib")).process().asString().orElse("");
        var output = first + second;
        assertCompile(input, output);
    }
}
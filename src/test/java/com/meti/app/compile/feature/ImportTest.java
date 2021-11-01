package com.meti.app.compile.feature;

import com.meti.app.clang.CRenderer;
import com.meti.app.compile.CompileException;
import com.meti.app.magma.MagmaRenderingStage;
import org.junit.jupiter.api.Test;

import static com.meti.app.compile.feature.FeatureTest.assertCompile;

class ImportTest {

    @Test
    void another() throws CompileException {
        var input = new MagmaRenderingStage(new Import("stdlib"))
                .process()
                .asString()
                .orElse("");
        var output = new CRenderer(new Import("stdlib")).process()
                .asString()
                .orElse("");
        assertCompile(input, output);
    }
}
package com.meti.feature;

import com.meti.clang.CRenderingStage;
import com.meti.magma.MagmaRenderingStage;
import org.junit.jupiter.api.Test;

import static com.meti.feature.FeatureTest.assertCompile;

class ImportTest {

    @Test
    void another() {
        var input = new MagmaRenderingStage(new Import("stdlib"))
                .process()
                .asString()
                .orElse("");
        var output = new CRenderingStage(new Import("stdlib")).process()
                .asString()
                .orElse("");
        assertCompile(input, output);
    }
}
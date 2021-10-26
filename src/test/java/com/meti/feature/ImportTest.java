package com.meti.feature;

import com.meti.Compiler;
import org.junit.jupiter.api.Test;

import static com.meti.feature.FeatureTest.assertCompile;

class ImportTest {

    @Test
    void another() {
        var input = Compiler.renderNativeImport("stdlib");
        var output = Compiler.renderC(new Import("stdlib")).compute();
        assertCompile(input, output);
    }
}
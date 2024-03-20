package com.meti.compile;

import org.junit.jupiter.api.Test;

class CompilerTest extends FeatureTest {
    @Test
    void integer(){
        assertCompile("0", "0");
    }
}
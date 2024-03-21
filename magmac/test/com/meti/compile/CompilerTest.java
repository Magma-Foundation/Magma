package com.meti.compile;

import org.junit.jupiter.api.Test;

class CompilerTest extends FeatureTest {
    @Test
    void simple(){
        assertCompile("0", "0");
    }

    @Test
    void child() {
        assertCompile("return 0", "return 0");
    }

    @Test
    void children() {
        assertCompile("{return 0;}", """
                {
                \treturn 0;
                }""");
    }
}
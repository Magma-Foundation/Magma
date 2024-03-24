package com.meti;

import org.junit.jupiter.api.Test;

public class DefinitionTest extends FeatureTest {
    @Test
    void simple() {
        assertCompile("class Test {int value = 0;}",
                """
                        class def Test() => {
                        \tlet value : I32 = 0;
                        }""");
    }
}

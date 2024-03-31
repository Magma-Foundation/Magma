package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.CompiledTest.assertCompile;

public class ClassFeatureTest {
    @Test
    void simpleClass() {
        assertCompile("class Test {}", "class def Test() => {}");
    }
}

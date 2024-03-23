package com.meti;

import org.junit.jupiter.api.Test;

public class ClassTest extends FeatureTest {
    @Test
    void simple() {
        assertCompile("class Test {}",
                "class def Test() => {}");
    }

    @Test
    void name() {
        assertCompile("class Foo {}", "class def Foo() => {}");
    }
}

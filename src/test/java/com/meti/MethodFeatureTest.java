package com.meti;

import org.junit.jupiter.api.Test;

public class MethodFeatureTest extends FeatureTest {
    @Test
    void test() {
        assertCompile(new JavaClass("Test", new Block()),
                new Implementation("Test", new Block()));
    }
}

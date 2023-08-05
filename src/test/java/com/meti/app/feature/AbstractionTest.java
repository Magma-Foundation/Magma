package com.meti.app.feature;

import org.junit.jupiter.api.Test;

public class AbstractionTest extends CompiledTest {
    @Test
    void test() {
        assertCompile("void test();", "def test() : Void");
    }
}

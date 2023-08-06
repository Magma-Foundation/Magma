package com.meti.app.feature;

import org.junit.jupiter.api.Test;

public class AbstractionTest extends CompiledTest {
    @Test
    void parameter() {
        assertCompile("void test(int value);", "def test(value : I16) : Void");
    }

    @Test
    void test() {
        assertCompile("void test();", "def test() : Void");
    }
}

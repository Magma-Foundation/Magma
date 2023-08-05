package com.meti.app.feature;

import org.junit.jupiter.api.Test;

public class InterfaceTest extends CompiledTest {
    @Test
    void test() {
        assertCompile("interface Test {}", "trait Test {}");
    }
}

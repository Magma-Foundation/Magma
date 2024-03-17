package com.meti.compile.feature;

import com.meti.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class TraitTest extends CompiledTest {
    @Test
    void test() {
        assertCompile("interface Test {}", "trait Test {}");
    }
}

package com.meti;

import org.junit.jupiter.api.Test;

public class DeclarationFeatureTest extends CompilerTest {
    @Test
    void declaration() {
        assertCompile("const x : I16 = 420;", "int x=420;");
    }
}

package com.meti;

import org.junit.jupiter.api.Test;

public class DeclarationTest extends FeatureTest {
    @Test
    void test() {
        assertCompiles("const x : I16 = 0",
                "struct _index_ __index__(){" +
                        "struct _index_ this={};" +
                        "int x=0;" +
                        "return this;}");
    }
}

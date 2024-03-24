package com.meti;

import org.junit.jupiter.api.Test;

public class StringTest  extends FeatureTest {
    @Test
    void test(){
        assertCompile("class Test {String value = \"test\";}",
                "class def Test() => {\n" +
                "\tlet value : String = \"test\";\n" +
                "}");
    }
}

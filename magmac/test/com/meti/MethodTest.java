package com.meti;

import org.junit.jupiter.api.Test;

public class MethodTest extends FeatureTest {
    @Test
    void test() {
        assertCompile("class Test {void empty(){}}",
                "class def Test() => {\n" +
                "\tdef empty() : Void => {}\n" +
                "}");
    }
}

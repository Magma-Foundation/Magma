package com.meti;

import org.junit.jupiter.api.Test;

public class IfFeatureTest extends FeatureTest {
    @Test
    void test_false() {
        assertCompile("if(false){}", "if(0){}");
    }

    @Test
    void test_true() {
        assertCompile("if(true){}", "if(1){}");
    }
}

package com.meti;

import org.junit.jupiter.api.Test;

public class IfFeatureTest extends FeatureTest {
    @Test
    void test() {
        assertCompile("if(true){}", "if(1){}");
    }
}

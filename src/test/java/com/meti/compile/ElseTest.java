package com.meti.compile;

import org.junit.jupiter.api.Test;

public class ElseTest {
    @Test
    void test(){
        CompiledTest.assertSourceCompile("else {}", "else {}");
    }
}

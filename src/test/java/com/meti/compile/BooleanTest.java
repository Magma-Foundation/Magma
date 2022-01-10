package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.*;

public class BooleanTest {
    @Test
    void test_true(){
        assertSourceCompile("true", "1");
    }

    @Test
    void test_false(){
        assertSourceCompile("false", "0");
    }
}

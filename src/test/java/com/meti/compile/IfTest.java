package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.*;

public class IfTest {
    @Test
    void test(){
        assertSourceCompile("if(true) 10", "if(1)10");
    }
}

package com.meti.app.compile.condition;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class IfTest {
    @Test
    void test(){
        assertSourceCompile("if(true) 10", "if(1)10");
    }
}

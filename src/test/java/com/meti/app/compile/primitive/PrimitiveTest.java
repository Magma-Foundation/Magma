package com.meti.app.compile.primitive;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class PrimitiveTest {
    @Test
    void bool(){
        assertSourceCompile("def empty(state : Bool) : Void => {}", "void empty(int state){}");
    }
}

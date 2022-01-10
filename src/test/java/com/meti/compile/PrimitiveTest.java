package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.*;

public class PrimitiveTest {
    @Test
    void bool(){
        assertSourceCompile("def empty(state : Bool) : Void => {}", "void empty(int state){}");
    }
}

package com.meti.app.compile.function;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class FunctionTypeTest {
    @Test
    void function_type() {
        assertSourceCompile("let x : () => Void;", "void (*x)();");
    }

    @Test
    void function_type_parameters() {
        assertSourceCompile("let x : (I16, U16) => Void", "void (*x)(int,unsigned int);");
    }

    @Test
    void functional_parameters() {
        assertSourceCompile("def doSomething(actor : () => Void) => {}", "void doSomething(void (*actor)()){}");
    }
}

package com.meti.app.compile.function;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertHeaderCompiles;
import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class ClosureTest {
    @Test
    void in_block() {
        assertSourceCompile("{def test() => {}}", "void test(){}{}");
    }

    @Test
    void in_function() {
        assertSourceCompile("def outer() => {def inner() => {}}", "void inner(){}void outer(){}");
    }

    @Test
    void header() {
        assertHeaderCompiles("def outer() => {def inner() => {}}",
                "void inner();void outer();struct outer_{}");
    }

    @Test
    void scope_source() {
        assertSourceCompile("def outer() => {def inner() => {}}",
                "void inner(){}void outer(){struct outer_t outer_this;}");
    }
}

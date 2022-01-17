package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertSourceCompile;

public class ClosureTest {
    @Test
    void in_block() {
        assertSourceCompile("{def test() => {}}", "void test(){}{}");
    }

    @Test
    void in_block_in_function() {
        assertSourceCompile("{def outer() => {def inner() => {}}}", "void outer(){}void inner(){}{}");
    }

    @Test
    void in_double_function() {
        assertSourceCompile("def first() => {def second() => {def third() => {}}}",
                "void second(){}void third(){}void first(){}");
    }

    @Test
    void in_function() {
        assertSourceCompile("def outer() => {def inner() => {}}", "void inner(){}void outer(){}");
    }
}

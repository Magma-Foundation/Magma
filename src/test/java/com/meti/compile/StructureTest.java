package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class StructureTest {
    @Test
    void empty() {
        assertCompile("struct Empty{}", "struct Empty{};");
    }

    @Test
    void one_field() {
        assertCompile("struct Wrapper{value : I16}", "struct Wrapper{int value;};");
    }
}

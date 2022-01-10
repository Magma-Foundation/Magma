package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertHeaderCompile;

public class StructureTest {
    @Test
    void empty() {
        assertHeaderCompile("struct Empty{}", "struct Empty{};");
    }

    @Test
    void one_field() {
        assertHeaderCompile("struct Wrapper{value : I16}", "struct Wrapper{int value;};");
    }
}

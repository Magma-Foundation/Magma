package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertHeaderCompiles;

public class StructureTest {
    @Test
    void empty() {
        assertHeaderCompiles("struct Empty{}", "struct Empty{};");
    }

    @Test
    void one_field() {
        assertHeaderCompiles("struct Wrapper{value : I16}", "struct Wrapper{int value;};");
    }
}

package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.assertCompile;

public class StructureTest {
    @Test
    void empty() {
        assertCompile("struct Empty{}", "struct Empty{};");
    }
}

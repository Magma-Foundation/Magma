package com.meti.compile;

import org.junit.jupiter.api.Test;

import static com.meti.compile.CompiledTest.*;

public class BinaryTest {
    @Test
    void test() {
        assertSourceCompile("{x = 10}", "{x=10;}");
    }
}

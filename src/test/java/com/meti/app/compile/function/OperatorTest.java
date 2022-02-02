package com.meti.app.compile.function;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class OperatorTest {
    @Test
    void nominal() {
        assertSourceCompile("operator def ??(first : I16, second : I16) => {return false;}" +
                            "5 ?? 6",
                "int ??(int first, int second){return 0;}");
    }
}

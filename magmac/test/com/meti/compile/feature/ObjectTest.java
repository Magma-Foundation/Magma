package com.meti.compile.feature;

import com.meti.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class ObjectTest extends CompiledTest {
    @Test
    void test() {
        assertCompile("class Test {}", "\n" +
                                       "object Test = {\n" +
                                       "}");
    }
}

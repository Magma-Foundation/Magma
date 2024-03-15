package com.meti.compile.feature;

import com.meti.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class TextBlockTest extends CompiledTest {
    @Test
    void basic() {
        assertCompile("\"\"\"test\"\"\"", "\"\"\"test\"\"\"");
    }
}

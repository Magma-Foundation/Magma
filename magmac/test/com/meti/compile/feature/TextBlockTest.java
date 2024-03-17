package com.meti.compile.feature;

import com.meti.compile.FeatureTest;
import org.junit.jupiter.api.Test;

public class TextBlockTest extends FeatureTest {
    @Test
    void basic() {
        assertCompile("\"\"\"test\"\"\"", "\"\"\"test\"\"\"");
    }
}

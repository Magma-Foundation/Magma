package com.meti.compile.feature;

import com.meti.compile.FeatureTest;
import org.junit.jupiter.api.Test;

public class TraitTest extends FeatureTest {
    @Test
    void public_() {
        assertCompile("public interface Test {}", "export trait Test {\n}");
    }

    @Test
    void simple() {
        assertCompile("interface Test {}", "trait Test {\n}");
    }
}

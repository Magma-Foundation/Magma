package com.meti.compile.feature;

import com.meti.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class TraitTest extends CompiledTest {
    @Test
    void public_() {
        assertCompile("public interface Test {}", "export trait Test {}");
    }

    @Test
    void simple() {
        assertCompile("interface Test {}", "trait Test {}");
    }
}

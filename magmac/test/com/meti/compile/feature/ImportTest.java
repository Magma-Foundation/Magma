package com.meti.compile.feature;

import com.meti.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class ImportTest extends CompiledTest {
    @Test
    void testStatic() {
        assertCompile("import static Test.a", "import { a } from Test;\n");
    }

    @Test
    void testStaticAll() {
        assertCompile("import static Test.*", "import Test;\n");
    }

    @Test
    void simple() {
        assertCompile("import parent.Child", "import { Child } from parent;\n");
    }
}

package com.meti.compile.feature;

import com.meti.compile.FeatureTest;
import org.junit.jupiter.api.Test;

public class ImportTest extends FeatureTest {
    @Test
    void testStatic() {
        assertCompile("import static Test.left", "import { left } from Test;\n");
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

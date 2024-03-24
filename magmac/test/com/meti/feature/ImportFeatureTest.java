package com.meti.feature;

import com.meti.FeatureTest;
import org.junit.jupiter.api.Test;

public class ImportFeatureTest extends FeatureTest {
    @Test
    void importStatic() {
        assertCompile("import static parent.Child", "import { Child } from parent;");
    }

    @Test
    void importStaticAll() {
        assertCompile("import static parent.*", "import * from parent;");
    }

    @Test
    void importName() {
        assertCompile("import org.junit.jupiter.api.Test;", "import { Test } from org.junit.jupiter.api;");
    }

    @Test
    void importParent() {
        assertCompile("import parent.Child;", "import { Child } from parent;");
    }
}

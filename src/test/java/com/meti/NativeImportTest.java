package com.meti;

import org.junit.jupiter.api.Test;

public class NativeImportTest extends FeatureTest {
    @Test
    void native_import() {
        assertNativeImport("string");
    }

    private void assertNativeImport(String string) {
        var input = new MagmaNativeImportRenderer(string).render();
        var output = new IncludeDirectiveRenderer(string).render();
        assertCompile(input, output);
    }

    @Test
    void another_native_import() {
        assertNativeImport("stdio");
    }
}

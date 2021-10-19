package com.meti;

import org.junit.jupiter.api.Test;

public class NativeImportTest extends FeatureTest {
    @Test
    void native_import() {
        assertNativeImport("string");
    }

    @Test
    void native_imports() {

    }

    private void assertNativeImport(String value) {
        var input = new MagmaNativeImportRenderer().render(value);
        var output = new IncludeDirectiveRenderer().render(value);
        assertCompile(input, output);
    }

    @Test
    void another_native_import() {
        assertNativeImport("stdio");
    }
}

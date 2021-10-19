package com.meti;

import org.junit.jupiter.api.Test;

public class NativeImportTest extends FeatureTest {
    @Test
    void native_import() {
        assertNativeImport("string");
    }

    @Test
    void native_imports() {
        var inputRenderer = new MagmaNativeImportRenderer();
        var inputFirst = inputRenderer.render("first") + ";";
        var inputSecond = inputRenderer.render("second") + ";";
        var input = inputFirst + inputSecond;

        var outputRenderer = new IncludeDirectiveRenderer();
        var outputFirst = outputRenderer.render("first");
        var outputSecond = outputRenderer.render("second");
        var output = outputFirst + outputSecond;

        assertCompile(input, output);
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

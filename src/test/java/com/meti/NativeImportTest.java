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

        var outputRenderer = new CImportRenderer();
        var outputFirst = outputRenderer.render(new Import("first")).compute();
        var outputSecond = outputRenderer.render(new Import("second")).compute();
        var output = outputFirst + outputSecond;

        assertCompile(input, output);
    }

    private void assertNativeImport(String value) {
        var input = new MagmaNativeImportRenderer().render(value);
        var output = new CImportRenderer().render(new Import(value))
                .compute();
        assertCompile(input, output);
    }

    @Test
    void another_native_import() {
        assertNativeImport("stdio");
    }
}

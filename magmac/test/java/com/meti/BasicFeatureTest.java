package com.meti;

import org.junit.jupiter.api.Test;

import static com.meti.JavaLang.renderPackage;
import static com.meti.MagmaCompiler.run;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasicFeatureTest {
    @Test
    void testEmpty() {
        FeatureTest.assertCompile("", "");
    }

    @Test
    void testPackages() {
        FeatureTest.assertCompile(renderPackage("com.meti"), "");
    }

    @Test
    void testMultiplePackages() {
        assertThrows(CompileException.class,
                () -> run(renderPackage("first") + renderPackage("second")));
    }

}

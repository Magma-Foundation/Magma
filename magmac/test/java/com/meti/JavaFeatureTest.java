package com.meti;

import org.junit.jupiter.api.Assertions;

public class JavaFeatureTest {
    static void assertJava(String namespace, String content) {
        try {
            var magmaSource = JavaToMagmaCompiler.compileJavaToMagma(content);
            var javaSource = MagmaToJavaCompiler.compileMagmaToJava(namespace, magmaSource);
            Assertions.assertEquals(content, javaSource);
        } catch (CompileException e) {
            Assertions.fail(e);
        }
    }
}
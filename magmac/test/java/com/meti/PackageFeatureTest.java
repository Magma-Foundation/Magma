package com.meti;

import org.junit.jupiter.api.Test;

public class PackageFeatureTest {
    @Test
    void testPackageRemoval() {
        CompiledTest.assertCompile("package test;", "");
    }
}

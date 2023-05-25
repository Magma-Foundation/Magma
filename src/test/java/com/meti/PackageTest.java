package com.meti;

import org.junit.jupiter.api.Test;

public class PackageTest extends FeatureTest {
    @Test
    void anyPackage() {
        assertCompile("package test;", "");
    }
}

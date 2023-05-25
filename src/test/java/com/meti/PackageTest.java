package com.meti;

import org.junit.jupiter.api.Test;

public class PackageTest extends CompiledTest {
    @Test
    void anyPackage() {
        assertCompile("package test;", "");
    }
}

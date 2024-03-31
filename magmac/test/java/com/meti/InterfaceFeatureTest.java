package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterfaceFeatureTest {
    @Test
    void test() {
        assertEquals("trait Test {}", Compiler.compile("interface Test {}"));
    }
}

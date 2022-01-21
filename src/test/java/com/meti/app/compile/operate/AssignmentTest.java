package com.meti.app.compile.operate;

import com.meti.app.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class AssignmentTest {
    @Test
    void test(){
        CompiledTest.assertSourceCompile("x = 69", "x = 69");
    }

    @Test
    void test_boolean(){
        CompiledTest.assertSourceCompile("x = false", "x = 0");
    }
}

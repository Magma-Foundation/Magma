package com.meti.compile.feature;

import com.meti.compile.CompileException;
import com.meti.compile.FeatureTest;
import org.junit.jupiter.api.Test;

public class InvocationTest extends FeatureTest {
    @Test
    void simple() throws CompileException {
        assertCompile("test()", "test()");
    }

    @Test
    void lambda() throws CompileException {
        assertCompile("test(() -> {})", "test(() => {})");
    }
}

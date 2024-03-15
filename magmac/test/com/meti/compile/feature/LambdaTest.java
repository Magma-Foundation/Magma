package com.meti.compile.feature;

import com.meti.compile.CompileException;
import com.meti.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class LambdaTest extends CompiledTest {
    @Test
    void basic() throws CompileException {
        assertCompile("() -> {}", "() => {}");
    }

    @Test
    void parameter() throws CompileException {
        assertCompile("test -> {}", "test => {}");
    }

    @Test
    void body() throws CompileException {
        assertCompile("() -> {return \"test\";}", "() => {return \"test\";}");
    }
}

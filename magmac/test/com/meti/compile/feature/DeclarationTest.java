package com.meti.compile.feature;

import com.meti.compile.CompileException;
import com.meti.compile.CompiledTest;
import org.junit.jupiter.api.Test;

public class DeclarationTest extends CompiledTest {
    @Test
    void simple() throws CompileException {
        assertCompile("var test=\"test\";", "let test = \"test\"");
    }
}

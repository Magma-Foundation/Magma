package com.meti.compile.feature;

import com.meti.compile.CompileException;
import com.meti.compile.FeatureTest;
import org.junit.jupiter.api.Test;

public class DeclarationTest extends FeatureTest {
    @Test
    void complex() throws CompileException {
        assertCompile("var actual = new Compiler(input)", "let actual = Compiler(input)");
    }

    @Test
    void simple() throws CompileException {
        assertCompile("var test=\"test\";", "let test = \"test\"");
    }
}

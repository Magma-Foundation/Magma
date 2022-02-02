package com.meti.app.compile.extern;

import org.junit.jupiter.api.Test;

import static com.meti.app.compile.CompiledTest.assertSourceCompile;

public class KeywordTest {
    @Test
    void external() {
        assertSourceCompile("extern def test() : Void", "");
    }

    @Test
    void external_operator() {
        assertSourceCompile("extern operator def !(state : Bool) : Bool;! false", "!0");
    }
}

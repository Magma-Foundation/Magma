package com.meti.compile;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class InvocationCompilerTest {

    @Test
    void splitInvocationArguments() {
        var input = "otherValue -> new Tuple<>(this.value, otherValue)";
        var actual = InvocationCompiler.splitInvocationArguments(input);
        assertIterableEquals(Collections.singletonList(input), actual);
    }
}
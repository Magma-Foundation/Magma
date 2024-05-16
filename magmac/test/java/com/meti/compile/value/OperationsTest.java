package com.meti.compile.value;

import com.meti.api.option.OptionException;
import com.meti.compile.JavaString;
import com.meti.compile.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OperationsTest {

    @Test
    void lex() throws OptionException {
        var expected = new Node()
                .withSlice("left", "value()")
                .withSlice("right", "amount");

        var actual = Operations.lex(new JavaString("value() + amount")).$();
        assertEquals(expected, actual);
    }
}
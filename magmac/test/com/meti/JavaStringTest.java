package com.meti;

import com.meti.api.collect.Index;
import com.meti.api.collect.JavaString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaStringTest {

    @Test
    void firstIndexOfSlice() {
        var index = new JavaString("foobar")
                .firstIndexOfSlice("bar")
                .map(Index::value)
                .unwrapOrElseGet(Assertions::fail);
        assertEquals(3, index);
    }

    @Test
    void firstIndexOfChar() {
        var index = new JavaString("foobar")
                .firstIndexOfChar('o')
                .map(Index::value)
                .unwrapOrElseGet(Assertions::fail);
        assertEquals(1, index);
    }

    @Test
    void lastIndexOfChar() {
        var index = new JavaString("foobar")
                .lastIndexOfChar('o')
                .map(Index::value)
                .unwrapOrElseGet(Assertions::fail);
        assertEquals(2, index);
    }

    @Test
    void slice() {
        var string = new JavaString("foobar");
        var start = string.firstIndexOfChar('o').unwrapOrElseGet(Assertions::fail);
        var end = string.firstIndexOfChar('b').unwrapOrElseGet(Assertions::fail);
        var slice = string.sliceBetween(start.to(end).unwrapOrElseGet(Assertions::fail));
        assertEquals("oo", slice);
    }

    @Test
    void testSlice() {
    }

    @Test
    void sliceTo() {
    }

    @Test
    void length() {
    }

    @Test
    void value() {
    }
}
package com.meti;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AbstractIterableTest {

    @Test
    void allMatchValid() {
        assertTrue(Iterables.of(2, 4).allMatch(value -> value % 2 == 0));
    }

    @Test
    void allMatchInvalid() {
        assertFalse(Iterables.of(2, 5).allMatch(value -> value % 2 == 0));
    }

    @Test
    void take() {
        var actual = Iterables.of(1, 2, 3, 4).take(2).unwrapOrPanic();
        assertEquals(3, actual.head().unwrapOrPanic());
        assertEquals(4, actual.head().unwrapOrPanic());
    }

    @Test
    void filter() {
        var actual = Iterables.of(1, 2, 3, 4)
                .filter(value -> value % 2 == 0)
                .collect(Iterables.toIntSum());
        assertEquals(6, actual);
    }

    @Test
    void zip() {
        var first = Iterables.of(1, 2, 3, 4);
        var second = Iterables.of(5, 6, 7, 8);
        var actual = first.zip(second)
                .map(values -> values.value0() * values.value1())
                .foldLeft(0, Integer::sum);
        assertEquals(70, actual);
    }

    @Test
    void flatMap() {
        var actual = Iterables.of(1, 2, 3, 4)
                .flatMap(value -> Iterables.of(value, value + 4))
                .foldLeft(0, Integer::sum);
        assertEquals(36, actual);
    }

    @Test
    void map() {
        var actual = Iterables.of(1, 2, 3, 4)
                .map(value -> value + 1)
                .foldLeft(0, Integer::sum);
        assertEquals(14, actual);
    }

    @Test
    void foldLeft() {
        var actual = Iterables.of(1, 2, 3, 4).foldLeft(0, Integer::sum);
        assertEquals(10, actual);
    }

    @Test
    void collect() {
        var expected = Set.of(1, 2, 3, 4);
        var actual = Iterables.of(1, 2, 3, 4).collect(Iterables.toSet());
        assertTrue(expected.containsAll(actual.unwrap()));
        assertTrue(actual.unwrap().containsAll(expected));
    }
}
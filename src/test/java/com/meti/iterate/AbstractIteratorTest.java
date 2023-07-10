package com.meti.iterate;

import com.meti.java.JavaList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class AbstractIteratorTest {

    @Test
    void collect() {
    }

    @Test
    void forEach() {
    }

    @Test
    void take() {
    }

    @Test
    void flatMap() {
        var actual = Iterators.of(1, 3)
                .flatMap(value -> Iterators.of(value, value + 1))
                .foldLeft(0, Integer::sum);
        assertEquals(10, actual);
    }

    @Test
    void filter() {
        var actual = Iterators.of(1, 2, 3, 4)
                .filter(value -> value % 2 == 0)
                .collect(JavaList.asList())
                .unwrap();
        assertIterableEquals(List.of(2, 4), actual);
    }

    @Test
    void foldLeft() {
    }

    @Test
    void map() {
    }

    @Test
    void into() {
    }
}
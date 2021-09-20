package com.meti;

import com.meti.stream.JavaListStream;
import com.meti.stream.StreamException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractStreamTest {
    private int counter;

    @Test
    void forEach() throws StreamException {
        counter = 0;
        new JavaListStream<>(List.of(0, 0)).forEach(value -> counter++);
        assertEquals(2, counter);
    }

    @Test
    void foldRight() throws StreamException {
        var sum = new JavaListStream<>(List.of(1, 2, 3)).foldRight(0, Integer::sum);
        assertEquals(6, sum);
    }

    @Test
    void map() throws StreamException {
        var sum = new JavaListStream<>(List.of(1, 2, 3))
                .map(value -> value + 1)
                .foldRight(0, Integer::sum);
        assertEquals(9, sum);
    }
}
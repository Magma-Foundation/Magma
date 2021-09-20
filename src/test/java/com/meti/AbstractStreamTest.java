package com.meti;

import com.meti.stream.JavaListStream;
import com.meti.stream.StreamException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void filter() throws Exception {
        var set = new JavaListStream<>(List.of(1, 2))
                .filter(value -> value % 2 == 1)
                .foldRight(new HashSet<>(), (F2E1<HashSet<Integer>, Integer, HashSet<Integer>, Exception>) (objects, integer) -> {
                    var copy = new HashSet<>(objects);
                    copy.add(integer);
                    return copy;
                });
        assertTrue(set.contains(1));
        assertFalse(set.contains(2));
    }
}
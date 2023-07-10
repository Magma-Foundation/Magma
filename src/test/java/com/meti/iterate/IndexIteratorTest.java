package com.meti.iterate;

import com.meti.collect.Index;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IndexIteratorTest {
    @Test
    void head() {
        var iterator = new Impl();
        assertEquals(0, iterator.head().unwrap());
        assertEquals(1, iterator.head().unwrap());
        assertEquals(2, iterator.head().unwrap());
    }

    static class Impl extends IndexIterator<Integer> {

        @Override
        protected Integer apply(Index index) {
            return index.value();
        }

        @Override
        protected Index length() {
            return new Index(3, 3);
        }
    }
}
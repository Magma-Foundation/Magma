package com.meti;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JavaListStreamTest {
    private int counter;

    @Test
    void forEach() {
        counter = 0;
        new JavaListStream<>(List.of(0, 0)).forEach(value -> counter++);
        assertEquals(2, counter);
    }
}
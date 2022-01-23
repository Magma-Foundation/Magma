package com.meti.api.collect.java;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class JavaListTest {

    @Test
    void toNativeList() {
        var expected = List.of(1, 2, 3);
        var actual = JavaList.toNativeList(new JavaList<>(expected));
        assertIterableEquals(expected, actual);
    }
}
package com.meti.java;

import com.meti.core.None;
import com.meti.core.Some;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NonEmptyJavaListTest {

    @Test
    public void testFrom_EmptyList_ReturnsNone() {
        var emptyList = new JavaList<>(List.of());
        var result = NonEmptyJavaList.from(emptyList);
        assertTrue(result instanceof None);
    }

    @Test
    public void testFrom_NonEmptyList_ReturnsSomeWithNonEmptyJavaList() {
        var values = Arrays.asList("A", "B", "C");
        var nonEmptyList = new JavaList<>(values);
        var result = NonEmptyJavaList.from(nonEmptyList);
        assertTrue(result instanceof Some);
        var nonEmptyJavaList = result.unwrap();
        assertEquals(values, nonEmptyJavaList.unwrap());
    }

    @Test
    public void testLastIndex_ReturnsCorrectIndex() {
        var values = Arrays.asList("A", "B", "C");
        var nonEmptyJavaList = new NonEmptyJavaList<>(values);
        var lastIndex = nonEmptyJavaList.lastIndex();
        assertEquals(2, lastIndex.value());
        assertEquals(3, lastIndex.length());
    }

    @Test
    public void testFirst_ReturnsFirstElement() {
        var values = Arrays.asList("A", "B", "C");
        var nonEmptyJavaList = new NonEmptyJavaList<>(values);
        var firstElement = nonEmptyJavaList.first();
        assertEquals("A", firstElement);
    }
}

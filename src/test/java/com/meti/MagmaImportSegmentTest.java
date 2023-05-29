package com.meti;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MagmaImportSegmentTest {
    @Test
    void get() {
        var node = new Content("value");
        var child = new MagmaImportSegment(List.of("child"), List.of(node));
        var parent = new MagmaImportSegment(List.of(), List.of(child));
        assertEquals(child, parent.get("child").orElseThrow());
    }

    @Test
    void insert() {
        var expected = new MagmaImportSegment(
                List.of("parent"),
                List.of(
                        new MagmaImportSegment(
                                List.of("child"),
                                List.of(new Content("bar"))
                        )
                )
        );
        var actual = new MagmaImportSegment("parent").insert(List.of("child", "Bar"));
        assertEquals(expected, actual);
    }
}
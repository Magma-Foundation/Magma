package com.meti;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StructureRendererTest {

    @Test
    void render() throws CompileException {
        var members = List.of(
                new Content("int x"),
                new Content("int y")
        );

        var expected = "struct Point{int x;int y;}";
        var actual = new StructureRenderer(";")
                .render(new Structure("Point", members))
                .compute();
        assertEquals(expected, actual);
    }
}
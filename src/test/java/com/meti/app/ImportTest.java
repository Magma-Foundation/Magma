package com.meti.app;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.meti.java.JavaList.ofList;
import static com.meti.java.JavaString.from;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportTest {
    private static void assertRender(String output, Function<Import, Import> mapper) {
        var root = new Import(from("parent"));
        var parent = mapper.apply(root).render();
        assertEquals(output, parent.unwrap());
    }

    @Test
    void value() {
        assertRender("parent", import_ -> import_);
    }

    @Test
    void child() {
        assertRender("{ Child } from parent", import_ -> import_.addPath(ofList(from("Child"))));
    }

    @Test
    void sibling() {
        assertRender("{ Child, Sibling } from parent", import_ -> import_
                .addPath(ofList(from("Child")))
                .addPath(ofList(from("Sibling"))));
    }
}
package com.meti.app;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.meti.java.JavaLists.ofNonEmptyList;
import static com.meti.java.JavaString.from;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ImportTest {
    private static void assertRender(String output, Function<Import, Import> mapper) {
        assertRender("parent", output, mapper);
    }

    private static void assertRender(String parent, String output, Function<Import, Import> mapper) {
        var root = new Import(from(parent));
        var rendered = mapper.apply(root).render(0);
        assertEquals(output, rendered.unwrap());
    }

    @Test
    void value() {
        assertRender("name", "name", import_ -> import_);
    }

    @Test
    void child() {
        assertRender("{ Child } from parent", import_ -> import_.addPath(ofNonEmptyList(from("Child"))));
    }

    @Test
    void sibling() {
        assertRender("{ Child, Sibling } from parent", import_ -> import_
                .addPath(ofNonEmptyList(from("Child")))
                .addPath(ofNonEmptyList(from("Sibling"))));
    }

    @Test
    void duplicateSiblings() {
        assertRender("{ Child } from parent", import_ -> import_
                .addPath(ofNonEmptyList(from("Child")))
                .addPath(ofNonEmptyList(from("Child"))));
    }
}
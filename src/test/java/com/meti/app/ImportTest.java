package com.meti.app;

import com.meti.java.JavaString;
import com.meti.java.NonEmptyJavaList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ImportTest {

    @Test
    void define() {
        var key = new JavaString("test");
        var children = new Import().define(NonEmptyJavaList.ofNonEmpty(key))
                .children();

        assertTrue(children.apply(key).unwrap().isEmpty());
    }
}
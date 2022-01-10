package com.meti.source;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackagingTest {

    @Test
    void relativize() {
        var expected = new Packaging("Sibling");
        var actual = new Packaging("Index").relativize(expected);
        assertEquals(expected, actual);
    }

    @Test
    void relativize_cousin() {
        var thisPackage = new Packaging("Index", "Parent");
        var thatPackage = new Packaging("Cousin", "Uncle");

        var expected = new Packaging("Cousin", "..", "Uncle");
        var actual = thisPackage.relativize(thatPackage);

        assertEquals(expected, actual);
    }

    @Test
    void relativize_that_parent() {
        var thatPackage = new Packaging("Nephew", "Sibling");
        var actual = new Packaging("Index").relativize(thatPackage);
        assertEquals(new Packaging("Nephew", "Sibling"), actual);
    }

    @Test
    void relativize_this_parent() {
        var thatPackage = new Packaging("Uncle");
        var actual = new Packaging("Index", "Parent").relativize(thatPackage);
        assertEquals(new Packaging("Uncle", ".."), actual);
    }
}
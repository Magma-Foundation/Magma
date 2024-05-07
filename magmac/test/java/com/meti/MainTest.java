package com.meti;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @Test
    void split() {
        var value = "test";
        var output = Main.split(value);
        assertIterableEquals(singletonList(value), output);
    }
}

package com.meti;

import com.meti.safe.NativeString;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResolverTest {

    @Test
    void resolve() {
        var expected = NativeString.from("u32");
        var actual = new Resolver(NativeString.from("10u32"))
                .resolve();

        assertEquals(expected, actual);
    }
}
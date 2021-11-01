package com.meti.api;

import com.meti.api.option.None;
import com.meti.api.option.Some;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OptionStreamTest {

    @Test
    void empty() {
        assertThrows(EndOfStreamException.class, () -> new OptionStream<>(new None<>()).head());
    }

    @Test
    void some_present() {
        assertDoesNotThrow(() -> new OptionStream<>(new Some<>("test")).head());
    }

    @Test
    void some_retrieved() throws EndOfStreamException {
        var stream = new OptionStream<>(new Some<>("test"));
        stream.head();
        assertThrows(EndOfStreamException.class, stream::head);
    }
}
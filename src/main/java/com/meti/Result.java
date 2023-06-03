package com.meti;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

public interface Result {
    static Result ok(Set<Path> values) {
        return new Ok(values);
    }

    static Result err(IOException e) {
        return new Err(e);
    }

    Set<Path> unwrapValue();

    class Ok implements Result {
        private final Set<Path> values;

        public Ok(Set<Path> values) {
            this.values = values;
        }

        @Override
        public Set<Path> unwrapValue() {
            return values;
        }
    }

    record Err(IOException e) implements Result {
        @Override
        public Set<Path> unwrapValue() {
            throw new RuntimeException(e);
        }
    }
}

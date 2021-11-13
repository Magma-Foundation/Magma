package com.meti;

import java.nio.file.Path;

public record Some(Path value) implements Option {
    @Override
    public boolean isPresent() {
        return true;
    }
}

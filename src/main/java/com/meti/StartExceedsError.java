package com.meti;

public class StartExceedsError extends BoundsError {
    private final int value;

    public StartExceedsError(int value) {
        this.value = value;
    }
}

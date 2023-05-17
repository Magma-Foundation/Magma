package com.meti;

public class StartNegativeError extends BoundsError {
    private final int value;

    public StartNegativeError(int value) {
        this.value = value;
    }
}

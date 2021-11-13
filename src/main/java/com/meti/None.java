package com.meti;

public class None implements Option {
    @Override
    public boolean isPresent() {
        return false;
    }
}

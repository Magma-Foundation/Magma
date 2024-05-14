package com.meti.compile;

public record JavaString(String unwrap) {
    JavaString strip() {
        return new JavaString(unwrap.strip());
    }
}
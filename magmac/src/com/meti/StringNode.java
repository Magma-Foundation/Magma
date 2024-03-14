package com.meti;

import static com.meti.Some.Some;

public record StringNode(String value) {
    Option<String> render() {
        return Some("\"" + value() + "\"");
    }
}
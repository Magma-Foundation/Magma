package com.meti.api.json;

public record JSONFormatter(String value) {
    String format() {
        var buffer = new StringBuilder();
        var length = value().length();
        var depth = 0;

        for (int i = 0; i < length; i++) {
            var c = value().charAt(i);
            if (c == ':') {
                buffer.append(" : ");
            } else if (c == '{' || c == '[') {
                buffer.append(c).append("\n").append("\t".repeat(depth + 1));
                depth += 1;
            } else if (c == '}' || c == ']') {
                depth -= 1;
                buffer.append('\n').append("\t".repeat(depth)).append(c);
            } else {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }
}
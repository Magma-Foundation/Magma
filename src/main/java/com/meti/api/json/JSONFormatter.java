package com.meti.api.json;

import java.util.Stack;

public record JSONFormatter(String value) {
    public JSONFormatter(JSONNode root) {
        this(root.toString());
    }

    public String toString() {
        var buffer = new StringBuilder();
        var length = value().length();
        var depth = 0;

        var objectStack = new Stack<Boolean>();
        var arrayStack = new Stack<Boolean>();

        var inString = false;

        for (int i = 0; i < length; i++) {
            var c = value.charAt(i);
            if (inString) {
                buffer.append(c);

                if (c == '\"') {
                    inString = false;
                }
            } else {
                if (c == ':') {
                    buffer.append(" : ");
                } else if (c == ',') {
                    buffer.append(c).append('\n').append("\t".repeat(depth));
                } else if (c == '{' || c == '[') {
                    buffer.append(c).append('\n').append("\t".repeat(depth + 1));
                    depth += 1;

                    if (c == '{') {
                        objectStack.push(false);
                    } else {
                        arrayStack.push(false);
                    }
                } else if (c == '}' || c == ']') {
                    depth -= 1;
                    if ((c == '}' && objectStack.pop()) || (c == ']' && arrayStack.pop())) {
                        buffer.append('\n').append("\t".repeat(depth)).append(c);
                    } else {
                        buffer = new StringBuilder(buffer.toString().trim());
                        buffer.append(c);
                    }
                } else {
                    buffer.append(c);

                    if (c == '\"') inString = true;

                    if (!objectStack.isEmpty()) {
                        objectStack.pop();
                        objectStack.push(true);
                    }

                    if (!arrayStack.isEmpty()) {
                        arrayStack.pop();
                        arrayStack.push(true);
                    }
                }
            }
        }
        return buffer.toString();
    }
}
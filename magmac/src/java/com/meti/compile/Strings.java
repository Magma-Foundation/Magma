package com.meti.compile;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Strings {
    public static List<String> splitMembers(String input) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;
        var queue = toQueue(input);

        while (!queue.isEmpty()) {
            var c = queue.pop();

            if (c == '\'') {
                builder.append(c);
                var next = queue.pop();
                builder.append(next);
                if (next == '\\') {
                    builder.append(queue.pop());
                }

                builder.append(queue.pop());
                continue;
            }

            if (c == '\"') {
                builder.append(c);

                while (!queue.isEmpty()) {
                    var next = queue.pop();
                    builder.append(next);
                    if (next == '\\') {
                        builder.append(queue.pop());
                    }
                    if (next == '\"') {
                        break;
                    }
                }

                continue;
            }

            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                builder.append(c);
                depth = 0;

                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{' || c == '(') depth++;
                if (c == '}' || c == ')') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        return lines;
    }

    static LinkedList<Character> toQueue(String input) {
        var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));
        return queue;
    }

    static List<String> splitTypeString(String modifiersAndType) {
        var lines = new ArrayList<String>();
        var builder = new StringBuilder();
        var depth = 0;

        for (int i = 0; i < modifiersAndType.length(); i++) {
            var c = modifiersAndType.charAt(i);

            if (c == ' ' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '<') depth++;
                if (c == '>') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        return lines;
    }

    static boolean isSymbol(String stripped) {
        if (stripped.isEmpty()) return false;

        var first = stripped.charAt(0);
        if (!Character.isLetter(first) && first != '$') return false;

        for (int i = 1; i < stripped.length(); i++) {
            var c = stripped.charAt(i);
            if (!Character.isLetter(c) && !Character.isDigit(c) && c != '$') {
                return false;
            }
        }
        return true;
    }

    static boolean isAssignable(String token) {
        if (isSymbol(token)) return true;

        var separator = token.indexOf('.');
        if (separator == -1) return false;

        return isAssignable(token.substring(0, separator).strip()) && isSymbol(token.substring(separator + 1).strip());
    }
}
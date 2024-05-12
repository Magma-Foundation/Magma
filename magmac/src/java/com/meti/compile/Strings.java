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
        var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

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

            if (c == ';' && depth == 0) {
                lines.add(builder.toString());
                builder = new StringBuilder();
            } else if (c == '}' && depth == 1) {
                builder.append(c);
                depth = 0;

                lines.add(builder.toString());
                builder = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
                builder.append(c);
            }
        }

        lines.add(builder.toString());
        return lines;
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
}
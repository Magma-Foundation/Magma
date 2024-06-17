package magma.compile.rule.split;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParamSplitter implements Splitter {
    @Override
    public List<String> split(String input) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;

        var queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            var c = queue.pop();

            if (c == '\"') {
                buffer.append(c);

                while (!queue.isEmpty()) {
                    var next = queue.pop();
                    buffer.append(next);

                    if (next == '\\') {
                        buffer.append(queue.pop());
                        continue;
                    }

                    if (next == '\"') {
                        break;
                    }
                }

                continue;
            }


            if(c == '-') {
                buffer.append(c);
                if (!queue.isEmpty()) {
                    var next = queue.peek();
                    if(next == '>') {
                        buffer.append(queue.pop());
                        continue;
                    }
                }
                continue;
            }

            if (c == ',' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '(' || c == '<') depth++;
                if (c == ')' || c == '>') depth--;
                buffer.append(c);
            }
        }

        segments.add(buffer.toString());

        return segments.stream()
                .filter(value -> !value.isBlank())
                .toList();
    }
}

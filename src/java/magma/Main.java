package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    private record Tuple<A, B>(A left, B right) {
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String input) {
        return compileAll(input, input1 -> compileRootSegment(input1, 0));
    }

    private static String compileAll(String input, Function<String, String> compiler) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;

        LinkedList<Character> queue = IntStream.range(0, input.length())
                .mapToObj(input::charAt)
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            char c = queue.pop();
            if (c == '\'') {
                buffer.append(c);

                Character c1 = queue.pop();
                buffer.append(c1);
                if (c1 == '\\') {
                    buffer.append(queue.pop());
                }

                buffer.append(queue.pop());
                continue;
            }
            if (c == '\"') {
                buffer.append(c);

                while (!queue.isEmpty()) {
                    Character next = queue.pop();
                    buffer.append(next);

                    if (next == '\\') {
                        buffer.append(queue.pop());
                    }
                    if (next == '\"') {
                        break;
                    }
                }
                continue;
            }

            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            }
            else if (c == '}' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            }
            else {
                if (c == '{' || c == '(') {
                    depth++;
                }
                if (c == '}' || c == ')') {
                    depth--;
                }
            }
        }
        segments.add(buffer.toString());

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compiler.apply(segment));
        }
        return output.toString();
    }

    private static String compileRootSegment(String input, int depth) {
        String stripped = input.strip();
        String indent = "\n" + "\t".repeat(depth);
        if (stripped.endsWith("}")) {
            String withoutEnd = stripped.substring(0, stripped.length() - "}".length());
            int contentStart = findContentStart(withoutEnd);

            if (contentStart >= 0) {
                String beforeContent = withoutEnd.substring(0, contentStart);
                String afterContent = withoutEnd.substring(contentStart + "{".length());
                return format(generatePlaceholder(beforeContent) + "{" + compileAll(afterContent, input1 -> {
                    return compileRootSegment(input1, depth + 1);
                }) + indent + "}", depth, indent);
            }
        }

        return format(generatePlaceholder(stripped), depth, indent);
    }

    private static int findContentStart(String withoutEnd) {
        LinkedList<Tuple<Integer, Character>> queue = IntStream.range(0, withoutEnd.length())
                .mapToObj(index -> new Tuple<>(index, withoutEnd.charAt(index)))
                .collect(Collectors.toCollection(LinkedList::new));

        while (!queue.isEmpty()) {
            Tuple<Integer, Character> c = queue.pop();
            if (c.right == '\'') {
                Tuple<Integer, Character> tuple = queue.pop();
                if (tuple.right == '\'') {
                    queue.pop();
                }
                queue.pop();
                continue;
            }
            if (c.right == '{') {
                return c.left;
            }
        }

        return -1;
    }

    private static String format(String output, int depth, String indent) {
        if (depth == 0) {
            return output + "\n";
        }
        else {
            return indent + output;
        }
    }

    private static String generatePlaceholder(String stripped) {
        return "/*" + stripped + "*/";
    }
}

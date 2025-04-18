package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    private record Node(Map<String, String> strings, Map<String, java.util.List<Node>> nodeLists) {
        public Node() {
            this(new HashMap<>(), new HashMap<>());
        }

        private static String createJSONProperty(int depth, String name, String value) {
            String indent = "\t".repeat(depth);
            return "\n" + indent + "\"" + name + "\" : " + value;
        }

        private String format(int depth) {
            Stream<String> stringString = this.formatStrings(depth);
            Stream<String> stringStream = this.formatNodeLists(depth);

            String content = Stream.concat(stringString, stringStream).collect(Collectors.joining(", "));
            String indent = "\t".repeat(depth);
            return "{" + content + "\n" + indent + "}";
        }

        private Stream<String> formatNodeLists(int depth) {
            return this.nodeLists.entrySet()
                    .stream()
                    .map(entry -> this.formatNodeList(depth, entry));
        }

        private String formatNodeList(int depth, Map.Entry<String, List<Node>> entry) {
            String joined = entry.getValue()
                    .stream()
                    .map(node -> node.format(depth + 1))
                    .collect(Collectors.joining(", "));

            return createJSONProperty(depth + 1, entry.getKey(), "[" + joined + "]");
        }

        private Stream<String> formatStrings(int depth) {
            return this.strings.entrySet()
                    .stream()
                    .map(entry -> createJSONProperty(depth + 1, entry.getKey(), "\"" + entry.getValue() + "\""));
        }

        public Node withString(String propertyKey, String propertyValue) {
            this.strings.put(propertyKey, propertyValue);
            return this;
        }

        public Node withNodeList(String propertyKey, List<Node> propertyValues) {
            this.nodeLists.put(propertyKey, propertyValues);
            return this;
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            ArrayList<String> segments = new ArrayList<>();
            StringBuilder buffer = new StringBuilder();
            int depth = 0;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                buffer.append(c);
                if (c == ';' && depth == 0) {
                    segments.add(buffer.toString());
                    buffer = new StringBuilder();
                }
                else {
                    if (c == '{') {
                        depth++;
                    }
                    if (c == '}') {
                        depth--;
                    }
                }
            }
            segments.add(buffer.toString());

            List<Node> children = segments.stream()
                    .map(Main::compileRootSegment)
                    .toList();

            String formatted = new Node().withNodeList("children", children).format(0);
            Path target = source.resolveSibling("Main.java.ast.json");
            Files.writeString(target, formatted);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static Node compileRootSegment(String value) {
        if (value.strip().endsWith(";")) {
            String withoutEnd = value.strip().substring(0, value.strip().length() - ";".length());
            int prefixSegment = withoutEnd.indexOf(" ");
            if (prefixSegment >= 0) {
                String left = withoutEnd.substring(0, prefixSegment);
                String right = withoutEnd.substring(prefixSegment + " ".length());

                return new Node().withString("type", left).withString("segments", right);
            }
        }

        return new Node().withString("value", value);
    }

}
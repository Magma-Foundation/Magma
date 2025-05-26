package magmac;

import magmac.app.State;
import magmac.app.node.MapNode;
import magmac.app.node.Node;
import magmac.app.rule.InfixRule;
import magmac.app.rule.PrefixRule;
import magmac.app.rule.Rule;
import magmac.app.rule.StringRule;
import magmac.app.rule.StripRule;
import magmac.app.rule.SuffixRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        Path sourceDirectory = Paths.get(".", "src", "java");
        try (Stream<Path> stream = Files.walk(sourceDirectory)) {
            Set<Path> sources = stream.collect(Collectors.toSet());
            StringBuilder output = new StringBuilder();
            for (Path source : sources) {
                if (!Files.isRegularFile(source)) {
                    continue;
                }

                String fileName = source.getFileName().toString();
                if (!fileName.endsWith(".java")) {
                    continue;
                }

                int fileSeparator = fileName.lastIndexOf('.');
                String name = fileName.substring(0, fileSeparator);

                output.append("class " + name + "\n");
                String input = Files.readString(source);
                output.append(Main.compile(name, input));
            }

            Path target = Paths.get(".", "diagram.puml");
            Files.writeString(target, "@startuml\nskinparam linetype ortho\n" +
                    output +
                    "@enduml\n");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compile(String name, String input) {
        List<String> segments = Main.divide(input);
        StringBuilder state = new StringBuilder();
        for (String segment : segments) {
            String stripped = segment.strip();
            state = Main.compileRootSegment(state, name, stripped).orElse(state);
        }

        return state.toString();
    }

    private static Optional<StringBuilder> compileRootSegment(StringBuilder state, String name, String input) {
        return Main.createImportRule().lex(input).toOptional().map((Node value) -> {
            return Main.parseImport(state, name, value);
        });
    }

    private static Rule createImportRule() {
        return new StripRule(new SuffixRule(new PrefixRule("import ", new InfixRule(new StringRule("namespace"), ".", new StringRule("child"))), ";"));
    }

    private static StringBuilder parseImport(StringBuilder state, String parent, Node node) {
        Node dependency = new MapNode()
                .withString("parent", parent)
                .withString("child", node.findString("child").orElse(""));

        return Main.getStringBuilder(state, dependency);
    }

    private static StringBuilder getStringBuilder(StringBuilder state, Node dependency) {
        String parent = dependency.findString("parent").orElse("");
        String child = dependency.findString("child").orElse("");
        return state.append(parent + " --> " + child + "\n");
    }

    private static List<String> divide(String input) {
        State current = new State();
        int length = input.length();
        for (int i = 0; i < length; i++) {
            char c = input.charAt(i);
            current = Main.fold(current, c);
        }

        return current.advance().segments();
    }

    private static State fold(State state, char c) {
        State appended = state.append(c);
        if (';' == c) {
            return appended.advance();
        }
        else {
            return appended;
        }
    }

}

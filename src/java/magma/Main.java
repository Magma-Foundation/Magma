package magma;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Main {
    private interface DivideState {
        DivideState advance();

        DivideState append(char c);

        List<String> segments();

        boolean isLevel();

        DivideState enter();

        DivideState exit();

        boolean isShallow();
    }

    private static class MutableDivideState implements DivideState {
        private final List<String> segments;
        private int depth;
        private StringBuilder buffer;

        private MutableDivideState(List<String> segments, StringBuilder buffer, int depth) {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public MutableDivideState() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        @Override
        public DivideState advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        @Override
        public DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        @Override
        public List<String> segments() {
            return this.segments;
        }

        @Override
        public boolean isLevel() {
            return this.depth == 0;
        }

        @Override
        public DivideState enter() {
            this.depth++;
            return this;
        }

        @Override
        public DivideState exit() {
            this.depth--;
            return this;
        }

        @Override
        public boolean isShallow() {
            return this.depth == 1;
        }
    }

    private static final List<String> structs = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String compile(String input) {
        List<String> compiled = compileStatementsToList(input, Main::compileRootSegment);
        compiled.addAll(structs);
        return mergeStatements(compiled);
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        List<String> compiled = compileStatementsToList(input, compiler);
        return mergeStatements(compiled);
    }

    private static String mergeStatements(List<String> compiled) {
        StringBuilder output = new StringBuilder();
        for (String s : compiled) {
            output.append(s);
        }

        return output.toString();
    }

    private static ArrayList<String> compileStatementsToList(String input, Function<String, String> compiler) {
        List<String> segments = divide(input);
        ArrayList<String> compiled = new ArrayList<String>();
        for (String segment : segments) {
            compiled.add(compiler.apply(segment));
        }
        return compiled;
    }

    private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return "";
        }

        if (stripped.startsWith("import ")) {
            return "#include <temp.h>\n";
        }

        return compileClass(stripped)
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileClass(String stripped) {
        return compileToStruct(stripped, "class ");
    }

    private static Optional<String> compileToStruct(String stripped, String infix) {
        return compileInfix(stripped, infix, (_, right) -> {
            return compileInfix(right, "{", (name, withEnd) -> {
                return compileSuffix(withEnd, "}", s -> {
                    String outputContent = compileStatements(s, Main::compileClassMember);
                    String value = "struct " + name + " {" +
                            outputContent +
                            "\n};\n";
                    structs.add(value);
                    return Optional.of("");
                });
            });
        });
    }

    private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> compiler) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        String slice = input.substring(0, input.length() - suffix.length());
        return compiler.apply(slice);
    }

    private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<String>> compiler) {
        return compileInfix(input, infix, compiler, Main::locateFirst);
    }

    private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<String>> compiler, BiFunction<String, String, Integer> locator) {
        int index = locator.apply(input, infix);
        if (index < 0) {
            return Optional.empty();
        }
        String left = input.substring(0, index).strip();
        String right = input.substring(index + infix.length()).strip();
        return compiler.apply(left, right);
    }

    private static int locateFirst(String input, String infix) {
        return input.indexOf(infix);
    }

    private static String compileClassMember(String classMember) {
        String stripped = classMember.strip();
        return compileToStruct(stripped, "interface")
                .or(() -> compileToStruct(stripped, "class "))
                .or(() -> compileMethod(stripped))
                .orElseGet(() -> generatePlaceholder(stripped));
    }

    private static Optional<String> compileMethod(String input) {
        return compileInfix(input, "(", (definition, _) -> {
            return compileInfix(definition.strip(), " ", (beforeName, name) -> {
                return Optional.of("\n\t" + generatePlaceholder(beforeName) + " (*" + name + ")();");
            }, String::lastIndexOf);
        });
    }

    private static List<String> divide(String input) {
        DivideState current = new MutableDivideState();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return current.advance().segments();
    }

    private static DivideState divideStatementChar(DivideState current, char c) {
        DivideState appended = current.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        else if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        }
        else if (c == '{') {
            return appended.enter();
        }
        else if (c == '}') {
            return appended.exit();
        }
        else {
            return appended;
        }
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + "*/";
    }
}
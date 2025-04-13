#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
struct Main {
};
struct DivideState {
};
/* DivideState advance();*//* DivideState append(char c);*//* List<String> segments();*//* boolean isLevel();*//* DivideState enter();*//* DivideState exit();*//* boolean isShallow();*//* *//* private static class MutableDivideState implements DivideState {
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
    }*//* public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path target = source.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*//* private static String compile(String input) {
        return compileStatements(input, Main::compileRootSegment);
    }*//* private static String compileStatements(String input, Function<String, String> compiler) {
        List<String> segments = divide(input);
        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compiler.apply(segment));
        }
        return output.toString();
    }*//* private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return "";
        }

        if (stripped.startsWith("import ")) {
            return "#include <temp.h>\n";
        }

        return compileClass(stripped)
                .orElseGet(() -> generatePlaceholder(stripped));
    }*//* private static Optional<String> compileClass(String stripped) {
        return compileToStruct(stripped, "class ");
    }*//* private static Optional<String> compileToStruct(String stripped, String infix) {
        return compileInfix(stripped, infix, (_, right) -> {
            return compileInfix(right, "{", (name, withEnd) -> {
                return compileSuffix(withEnd, "}", s -> {
                    String outputContent = compileStatements(s, Main::compileClassMember);
                    return Optional.of("struct " + name + " {\n};\n" + outputContent);
                });
            });
        });
    }*//* private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> compiler) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }
        String slice = input.substring(0, input.length() - suffix.length());
        return compiler.apply(slice);
    }*//* private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<String>> compiler) {
        int index = input.indexOf(infix);
        if (index < 0) {
            return Optional.empty();
        }
        String left = input.substring(0, index).strip();
        String right = input.substring(index + infix.length()).strip();
        return compiler.apply(left, right);
    }*//* private static String compileClassMember(String classMember) {
        String stripped = classMember.strip();
        return compileToStruct(stripped, "interface")
                .orElseGet(() -> generatePlaceholder(stripped));
    }*//* private static List<String> divide(String input) {
        DivideState current = new MutableDivideState();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = divideStatementChar(current, c);
        }

        return current.advance().segments();
    }*//* private static DivideState divideStatementChar(DivideState current, char c) {
        DivideState appended = current.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        else if (c == '}*//* ' && appended.isShallow()) {
            return appended.advance().exit();
        }*//* else if (c == '{') {
            return appended.enter();
        }
        else if (c == '}*//* ') {
            return appended.exit();
        }*//* else {
            return appended;
        }*//* *//* private static String generatePlaceholder(String input) {
        return "/* " + input + "*/";
    }*//* }*/
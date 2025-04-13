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
/* private interface DivideState {
        DivideState advance();

        DivideState append(char c);

        List<String> segments();

        boolean isLevel();

        DivideState enter();

        DivideState exit();
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
    }

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
        return compileStatements(input, Main::compileRootSegment);
    }

    private static String compileStatements(String input, Function<String, String> compiler) {
        List<String> segments = divide(input);
        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compiler.apply(segment));
        }
        return output.toString();
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
        int classIndex = stripped.indexOf("class ");
        if (classIndex < 0) {
            return Optional.empty();
        }
        String right = stripped.substring(classIndex + "class ".length());
        int contentStart = right.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        String name = right.substring(0, contentStart).strip();
        String withEnd = right.substring(contentStart + "{".length()).strip();
        if (withEnd.endsWith("}")) {
            String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
            String outputContent = compileStatements(inputContent, Main::compileClassMember);
            return Optional.of("struct " + name + " {\n};\n" + outputContent);
        }
        else {
            return Optional.empty();
        }
    }

    private static String compileClassMember(String classMember) {
        return generatePlaceholder(classMember);
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
*/
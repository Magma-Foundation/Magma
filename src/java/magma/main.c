/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.function.Function; */
/* public  */struct Main {/* private static class State {
        private final List<String> segments;
        private final StringBuilder buffer;
        private final int depth;

        */ private State(/* List<String> segments, StringBuilder buffer, int depth */){/* 
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder(), 0);
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        private boolean isLevel() {
            return this.depth == 0;
        }

        private State append(char c) {
            return new State(this.segments, this.buffer.append(c), this.depth);
        }

        private State advance() {
            List<String> copy = new ArrayList<>(this.segments);
            copy.add(this.buffer.toString());
            return new State(copy, new StringBuilder(), this.depth);
        }

        private State enter() {
            return new State(this.segments, this.buffer, this.depth + 1);
        }

        private State exit() {
            return new State(this.segments, this.buffer, this.depth - 1);
        }
     */}/* public static */ void main(/* String[] args */){/* 
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            Path output = source.resolveSibling("main.c");
            Files.writeString(output, compile(input));

            new ProcessBuilder("cmd.exe", "/c", "build.bat")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
     */}/* private static */ String compile(/* String input */){/* 
        return compileStatements(input, Main::compileRootSegment) + "int main(){\n\treturn 0;\n}\n";
     */}/* private static */ String compileStatements(/* String input, Function<String, String> compiler */){/* 
        List<String> segments = divide(input, new State());

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
     */}/* private static */ List<String> divide(/* String input, State state */){/* 
        State current = state;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            current = foldStatementChar(current, c);
        }

        return current.advance().segments;
     */}/* private static */ State foldStatementChar(/* State current, char c */){/* 
        State appended = current.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == ' */}/* ' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        }
        if (c == '} *//* ') {
            return appended.exit();
        } *//* return appended; *//*  */};
/* private static String compileRootSegment(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("package ")) {
            return "";
        }
        int classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            String modifiers = stripped.substring(0, classIndex);
            String afterKeyword = stripped.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compileStatements(inputContent, Main::compileClassSegment);
                    if (isSymbol(name)) {
                        return generatePlaceholder(modifiers) + "struct " + name + " {" + outputContent + "};\n";
                    }
                }
            }
        }

        return generatePlaceholder(stripped) + "\n";
    } */
/* private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!Character.isLetter(input.charAt(i))) {
                return false;
            }
        }
        return true;
    } */
/* private static String compileClassSegment(String input) {
        String stripped = input.strip();
        int paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            String definition = stripped.substring(0, paramStart).strip();
            int nameSeparator = definition.lastIndexOf(" ");
            if (nameSeparator >= 0) {
                String beforeName = definition.substring(0, nameSeparator).strip();
                String name = definition.substring(nameSeparator + " ".length()).strip();

                int typeSeparator = beforeName.lastIndexOf(" ");
                if (typeSeparator >= 0) {
                    String beforeType = beforeName.substring(0, typeSeparator);
                    String type = beforeName.substring(typeSeparator + " ".length());

                    String withParams = stripped.substring(paramStart + "(".length());
                    int paramEnd = withParams.indexOf(")");
                    if (paramEnd >= 0) {
                        String params = withParams.substring(0, paramEnd);
                        String withBraces = withParams.substring(paramEnd + ")".length()).strip();
                        if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                            String content = withBraces.substring(1, withBraces.length() - 1);
                            return generatePlaceholder(beforeType) + " " + type + " " + name + "(" + generatePlaceholder(params) + "){" + generatePlaceholder(content) + "}";
                        }
                    }
                }
            }
        }
        return generatePlaceholder(stripped);
    } */
/* private static String generatePlaceholder(String input) {
        String replaced = input
                .replace("<comment-start>", "<comment-start>")
                .replace("<comment-end>", "<comment-end>");

        return "<comment-start> " + replaced + " <comment-end>";
    } */
/* } */
int main(){
	return 0;
}

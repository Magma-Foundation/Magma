/* public  */struct Main {
};
/* public static final List<String> methods = new ArrayList<> */(/* ); */
/* public static void main */(/* ) {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */
/* private static String compileRoot */(/* String input) {
        return compileAll(input, Main::compileRootSegment) + String.join("", methods);
    } */
/* private static String compileAll */(/* String input, Function<String, String> compiler) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        var i = 0;
        while (i < input.length()) {
            var c = input.charAt(i);

            if (c == '\'') {
                buffer.append(c);
                i++;

                var maybeSlash = input.charAt(i);
                buffer.append(maybeSlash);
                i++;

                if (maybeSlash == '\\') {
                    var escaped = input.charAt(i);
                    buffer.append(escaped);
                    i++;
                }

                var slash = input.charAt(i);
                buffer.append(slash);
                i++;
                continue;
            }

            buffer.append(c);
            i++;
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
                if (c == '{') {
                    depth++;
                }
                if (c == '}') {
                    depth--;
                }
            }
        }
        segments.add(buffer.toString());

        var output = new StringBuilder();
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        }

        return output.toString();
    } */
/* private static String compileRootSegment */(/* String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        var classIndex = stripped.indexOf("class ");
        if (classIndex >= 0) {
            var beforeClass = stripped.substring(0, classIndex);
            var afterClass = stripped.substring(classIndex + "class ".length());
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var name = afterClass.substring(0, contentStart).strip();
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var content = withEnd.substring(0, withEnd.length() - "}".length());
                    return generatePlaceholder(beforeClass) + "struct " + name + " {" + compileAll(content, Main::compileClassSegment) + "\n};\n";
                }
            }
        }

        return generatePlaceholder(stripped);
    } */
/* private static String compileClassSegment */(/* String input) {
        var stripped = input.strip();
        if (stripped.isEmpty()) {
            return "";
        }

        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var beforeParams = stripped.substring(0, paramStart);
            var afterParams = stripped.substring(paramStart + "(".length());
            var generated = generatePlaceholder(beforeParams) + "(" + generatePlaceholder(afterParams) + "\n";
            methods.add(generated);
            return "";
        }

        return generatePlaceholder(stripped);
    } */
/* private static String generatePlaceholder */(/* String input) {
        return "/* " + input + " */";
    } */

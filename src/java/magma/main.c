/* public  */struct Main { /* public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } *//* 

    private static String compileRoot(String input) {
        return compileAll(input, Main::compileRootSegment);
    } *//* 

    private static String compileAll(String input, Function<String, String> compiler) {
        var segments = new ArrayList<String>();
        var buffer = new StringBuilder();
        var depth = 0;
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
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
                if (c == '{') {
                    depth++;
                }
                if (c == '}') {
                    depth--;
                }
            }
        } *//* 
        segments.add(buffer.toString()); *//* 

        var output = new StringBuilder(); *//* 
        for (var segment : segments) {
            output.append(compiler.apply(segment));
        } *//* 

        return output.toString(); *//* 
     */}/* private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        var classIndex = stripped.indexOf(" */struct ");
        if (classIndex >= 0) { /* var beforeClass = stripped.substring(0, classIndex); *//* 
            var afterClass = stripped.substring(classIndex + "class ".length()); *//* 
            var contentStart = afterClass.indexOf("{");
            if (contentStart >= 0) {
                var name = afterClass.substring(0, contentStart).strip();
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var content = withEnd.substring(0, withEnd.length() - "}".length());
                    return generatePlaceholder(beforeClass) + "struct " + name + " { " + compileAll(content, Main::compileClassSegment) + "}";
                }
            } *//* 
        }

        return generatePlaceholder(stripped);
     */}/* private static String compileClassSegment(String classSegment) {
        return generatePlaceholder(classSegment);
    } *//* private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    } *//* } */
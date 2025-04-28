/* public  */struct Main { /* 
     */}/* private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        var classIndex = stripped.indexOf(" */struct ");
        if (classIndex >= 0) { }/* private static String compileClassSegment(String input) {
        var paramStart = input.indexOf("(");
        if (paramStart >= 0) {
            var beforeParams = input.substring(0, paramStart);
            var afterParams = input.substring(paramStart + "(".length());
            var generated = generatePlaceholder(beforeParams) + "(" + generatePlaceholder(afterParams);
            methods.add(generated);
            return "";
        }

        return generatePlaceholder(input);
    } *//* private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    } *//* } *//* public static final List<String> methods = new ArrayList<> */(/* ); *//* 

    public static void main */(/* ) {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } *//* 

    private static String compileRoot */(/* String input) {
        return compileAll(input, Main::compileRootSegment) + String.join("", methods);
    } *//* 

    private static String compileAll */(/* String input, Function<String, String> compiler) {
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
        segments.add */(/* buffer.toString()); *//* 

        var output = new StringBuilder */(/* ); *//* 
        for  */(/* var segment : segments) {
            output.append(compiler.apply(segment));
        } *//* 

        return output.toString */(/* ); *//* var beforeClass = stripped.substring */(/* 0, classIndex); *//* 
            var afterClass = stripped.substring */(/* classIndex + "class ".length()); *//* 
            var contentStart = afterClass.indexOf */(/* "{");
            if (contentStart >= 0) {
                var name = afterClass.substring(0, contentStart).strip();
                var withEnd = afterClass.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    var content = withEnd.substring(0, withEnd.length() - "}".length());
                    return generatePlaceholder(beforeClass) + "struct " + name + " { " + compileAll(content, Main::compileClassSegment) + "}";
                }
            } *//* 
        }

        return generatePlaceholder */(/* stripped);
     */
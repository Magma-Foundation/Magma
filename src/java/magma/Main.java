void main() throws IOException {
    var source = Paths.get(".", "src", "java", "magma", "Main.java");
    var input = Files.readString(source);

    var target = source.resolveSibling("main.c");
    Files.writeString(target, this.compileRoot(input));
}

private String compileRoot(String input) {
    var segments = new ArrayList<String>();
    var buffer = new StringBuilder();
    var depth = 0;
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        buffer.append(c);
        if (c == ';') {
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
    return this.compileRootSegment(input);
}

private String compileRootSegment(String input) {
    var paramStart = input.indexOf("(");
    if (paramStart >= 0) {
        var beforeParams = input.substring(0, paramStart);
        var withParams = input.substring(paramStart + "(".length());

        return this.generatePlaceholder(beforeParams) + "(" + this.generatePlaceholder(withParams);
    }

    return this.generatePlaceholder(input);
}

private String generatePlaceholder(String input) {
    return "/* " + input + " */";
}
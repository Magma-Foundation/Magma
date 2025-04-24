void main() {
    try {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var input = Files.readString(source);

        var target = source.resolveSibling("main.c");
        Files.writeString(target, this.compileRoot(input));
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private String compileRoot(String input) {
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
    }
    segments.add(buffer.toString());

    var output = new StringBuilder();
    for (var segment : segments) {
        output.append(this.compileRootSegment(segment));
    }

    return output.toString();
}

private String compileRootSegment(String input) {
    var paramStart = input.indexOf("(");
    if (paramStart >= 0) {
        var definition = input.substring(0, paramStart);
        var withParams = input.substring(paramStart + "(".length());
        var paramEnd = withParams.indexOf(")");
        if (paramEnd >= 0) {
            var params = withParams.substring(0, paramEnd);
            var withBraces = withParams.substring(paramEnd + ")".length());
            return this.generatePlaceholder(definition) + "(" + this.generatePlaceholder(params) + ")" + this.generatePlaceholder(withBraces);
        }
    }

    return this.generatePlaceholder(input);
}

private String generatePlaceholder(String input) {
    return "/* " + input + " */";
}
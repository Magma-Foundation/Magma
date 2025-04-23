void main() {
    try {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var input = Files.readString(source);

        var target = source.resolveSibling("main.c");
        Files.writeString(target, this.compileRoot(input));
    } catch (IOException e) {
        //noinspection CallToPrintStackTrace
        e.printStackTrace();
    }
}

private String compileRoot(String input) {
    var segments = new ArrayList<String>();
    var buffer = new StringBuilder();
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        buffer.append(c);

        if (c == ';') {
            segments.add(buffer.toString());
            buffer = new StringBuilder();
        }
    }
    segments.add(buffer.toString());

    var output = new StringBuilder();
    for (var segment : segments) {
        output.append(this.generatePlaceholder(segment));
    }

    return output.toString();
}

private String generatePlaceholder(String input) {
    return "/* " + input + " */";
}
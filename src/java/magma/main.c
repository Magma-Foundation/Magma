/* void main() {
    try {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var input = Files.readString(source);

        var target = source.resolveSibling("main.c");
        Files.writeString(target, this.compileRoot(input));
    } catch (IOException e) {
        e.printStackTrace();
    }
} *//* 

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
    } *//* 
    segments.add(buffer.toString()); *//* 

    var output = new StringBuilder(); *//* 
    for (var segment : segments) {
        output.append(this.compileRootSegment(segment));
    } *//* 

    return output.toString(); *//* 
}

private String compileRootSegment(String input) {
    return "/* " + input + " */"; *//* 
} */
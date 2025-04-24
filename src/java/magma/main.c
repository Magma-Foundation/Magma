void main(/*  */){/* 
    try {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");
        var input = Files.readString(source);

        var target = source.resolveSibling("main.c");
        Files.writeString(target, this.compileRoot(input));
    } catch (IOException e) {
        e.printStackTrace();
    }
 */
}
/* private */ char* compileRoot(/* String input */){/* 
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
     */
}
/* 
    segments.add(buffer.toString()); *//* 

    var output = new StringBuilder(); *//* for */(/* var segment : segments */){/* 
        output.append(this.compileRootSegment(segment));
     */
}
/* 

    return output.toString(); *//* 
}

private String compileRootSegment(String input) {
    var paramStart = input.indexOf("("); *//* if */(/* paramStart >= 0 */){/* 
        var definition = input.substring(0, paramStart);
        var withParams = input.substring(paramStart + "(".length());
        var paramEnd = withParams.indexOf(")");
        if (paramEnd >= 0) {
            var params = withParams.substring(0, paramEnd);
            var withBraces = withParams.substring(paramEnd + ")".length()).strip();
            if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                var content = withBraces.substring(1, withBraces.length() - 1);
                return this.compileDefinition(definition) + "(" + this.generatePlaceholder(params) + "){" + this.generatePlaceholder(content) + "\n}\n";
            }
        }
     */
}
/* 

    return this.generatePlaceholder(input); *//* 
}

private String compileDefinition(String input) {
    var stripped = input.strip(); *//* 
    var nameSeparator = stripped.lastIndexOf(" "); *//* if */(/* nameSeparator >= 0 */){/* 
        var beforeName = stripped.substring(0, nameSeparator).strip();
        var name = stripped.substring(nameSeparator + " ".length());

        var typeSeparator = beforeName.lastIndexOf(" ");
        if (typeSeparator >= 0) {
            var beforeType = beforeName.substring(0, typeSeparator);
            String type = this.compileType(beforeName.substring(typeSeparator + " ".length()));
            return this.generatePlaceholder(beforeType) + " " + type + " " + name;
        }
        else {
            String type = this.compileType(beforeName);
            return type + " " + name;
        }
     */
}
/* 

    return this.generatePlaceholder(stripped); *//* 
}

private String compileType(String beforeName) {
    var stripped = beforeName.strip(); *//* 
    if (stripped.equals("void")) {
        return "void";
    } *//* 

    if (stripped.equals("String")) {
        return "char*";
    } *//* 

    return this.generatePlaceholder(stripped); *//* 
}

private String generatePlaceholder(String input) {
    return "/* " + input + " */"; *//* 
} */
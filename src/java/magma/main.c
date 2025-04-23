/* sealed private */struct Result<T, X> permits Ok, Err {/*  */
}
/* sealed private */struct Option<T> permits Some, None {/*  */
}
/*  */struct Ok<T, X>(T value) implements Result<T, X> {/*  */
}
/*  */struct Err<T, X>(X error) implements Result<T, X> {/*  */
}
/*  */struct Some<T>(T value) implements Option<T> {/*  */
}
/* static final */struct None<T> implements Option<T> {/*  */
}
void main(/*  */){
	/* var */ source = /*  Paths.get(".", "src", "java", "magma", "Main.java") */;
	/* var */ target = /*  source.resolveSibling("main.c") */;/* 

    if (this.run(source, target) instanceof Some(var error)) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    } *//* 
 */
}
/* private Option<IOException> */ run(/* Path source, Path target */){/* 
    return switch (this.readString(source)) {
        case Err(var error) -> new Some<>(error);
        case Ok(var input) -> this.writeString(target, this.compile(input));
    } *//* ; *//* 
 */
}
/* private String */ compile(/* String input */){/* 
    return this.compileAll(input, this::compileRootSegment); *//* 
 */
}
/* private String */ compileAll(/* String input, Function<String, String> compiler */){
	/* var */ segments = /*  new ArrayList<String>() */;
	/* var */ buffer = /*  new StringBuilder() */;
	/* var */ depth = /*  0 */;
	/* for (var */ i = /*  0 */;/*  i < input.length(); *//*  i++) {
        var c = input.charAt(i);
        buffer.append(c);

        if (c == ';' && depth == 0) {
            segments.add(buffer.toString());
            buffer = new StringBuilder();
        }
        else if (c == '} *//* ' && depth == 1) {
            segments.add(buffer.toString());
            buffer = new StringBuilder();
            depth--;
        } *//* 
        else if (c == '{') {
            depth++;
        }
        else if (c == '} *//* ') {
            depth--;
        } *//* 
     */
}
/* 
    segments.add(buffer.toString()); *//* 

    var output = new StringBuilder(); *//* for */(/* var segment : segments */){/* 
        output.append(compiler.apply(segment)); *//* 
     */
}
/* 

    return output.toString(); *//* 
}

private String compileRootSegment(String input0) {
    var rules = List.<Function<String, Option<String>>>of(
            input -> this.compileStructured(input, "interface "),
            input -> this.compileStructured(input, "record "),
            input -> this.compileStructured(input, "class "),
            this::compileMethod
    ); *//* for */(/* var rule : rules */){/* 
        if (rule.apply(input0) instanceof Some(var compiled)) {
            return compiled;
        } *//* 
     */
}
/* 

    return this.generatePlaceholder(input0); *//* 
}

private Option<String> compileMethod(String input) {
    var paramStart = input.indexOf("("); *//* if */(/* paramStart >= 0 */){
	/* var */ definition = /*  input.substring(0, paramStart).strip() */;
	/* var */ withParams = /*  input.substring(paramStart + "(".length()) */;
	/* var */ paramEnd = /*  withParams.indexOf(")") */;/* 
        if (paramEnd >= 0) {
            var params = withParams.substring(0, paramEnd).strip();
            var withBraces = withParams.substring(paramEnd + ")".length()).strip();
            if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                var inputContent = withBraces.substring(1, withBraces.length() - 1);
                var outputContent = this.compileAll(inputContent, this::compileStatementOrBlock);
                return new Some<>(this.compileDefinition(definition) + "(" + this.generatePlaceholder(params) + "){" + outputContent + "\n}\n");
            }
        } *//* 
     */
}
/* 

    return new None<>(); *//* 
}

private String compileDefinition(String input) {
    var stripped = input.strip(); *//* 
    var space = stripped.lastIndexOf(" "); *//* if */(/* space >= 0 */){
	/* var */ type = /*  stripped.substring(0, space) */;
	/* var */ name = /*  stripped.substring(space + " ".length()) */;/* 
        return this.compileType(type) + " " + name; *//* 
     */
}
/* 

    return this.generatePlaceholder(stripped); *//* 
}

private String compileType(String input) {
    var stripped = input.strip(); *//* 
    if (stripped.equals("void")) {
        return "void";
    } *//* 

    return this.generatePlaceholder(input); *//* 
}

private String compileStatementOrBlock(String input) {
    var stripped = input.strip(); *//* 
    if (stripped.endsWith("; *//* ")) {
        var statement = stripped.substring(0, stripped.length() - ";".length());
        var valueSeparator = statement.indexOf("=");
        if (valueSeparator >= 0) {
            var definition = statement.substring(0, valueSeparator).strip();
            var value = statement.substring(valueSeparator + "=".length());
            return "\n\t" + this.compileDefinition(definition) + " = " + this.compileValue(value) + ";";
        }
    } *//* 

    return this.generatePlaceholder(input); *//* 
}

private String compileValue(String value) {
    return this.generatePlaceholder(value); *//* 
}

private Option<String> compileStructured(String input, String infix) {
    var interfaceIndex = input.indexOf(infix); *//* if */(/* interfaceIndex < 0 */){/* 
        return new None<>(); *//* 
     */
}
/* 

    var beforeKeyword = input.substring(0, interfaceIndex).strip(); *//* 
    var afterKeyword = input.substring(interfaceIndex + infix.length()); *//* 
    var contentStart = afterKeyword.indexOf("{");
    if (contentStart < 0) {
        return new None<>();
    }

    var beforeContent = afterKeyword.substring(0, contentStart).strip();
    var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
    if (!withEnd.endsWith("}")) {
        return new None<>();
    }

    var content = withEnd.substring(0, withEnd.length() - "} *//* ".length()); *//* 
    return new Some<>(this.generatePlaceholder(beforeKeyword) + "struct " +
            beforeContent + " {" + this.generatePlaceholder(content) + "\n} *//* \n"); *//* 
}

private String generatePlaceholder(String input) {
    return "/* " + input + " */"; *//* }

private Option<IOException> */ writeString(/* Path target, String output */){/* 
    try {
        Files.writeString(target, output);
        return new None<>();
     */
}
/* catch */(/* IOException e */){/* 
        return new Some<>(e); *//* 
     */
}
/* }

private Result<String, IOException> */ readString(/* Path source */){/* 
    try {
        return new Ok<>(Files.readString(source));
     */
}
/* catch */(/* IOException e */){/* 
        return new Err<>(e); *//* 
     */
}
/* 
} */
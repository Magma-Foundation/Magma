/* sealed private*/struct Result<T, X> {/* */
}
/* sealed private*/struct Option<T> {/* */
}
/* 

record Ok<T, X>(T value) implements Result<T, X> {
}*//* 

record Err<T, X>(X error) implements Result<T, X> {
}*//* 

record Some<T>(T value) implements Option<T> {
}*//* 

static final class None<T> implements Option<T> {
}*//* 

void main() {
    var source = Paths.get(".", "src", "java", "magma", "Main.java");
    var target = source.resolveSibling("main.c");

    if (this.run(source, target) instanceof Some(var error)) {
        //noinspection CallToPrintStackTrace
        error.printStackTrace();
    }
}*//* 

private Option<IOException> run(Path source, Path target) {
    return switch (this.readString(source)) {
        case Err(var error) -> new Some<>(error);
        case Ok(var input) -> this.writeString(target, this.compile(input));
    };
}*//* 

private String compile(String input) {
    var segments = new ArrayList<String>();
    var buffer = new StringBuilder();
    var depth = 0;
    for (var i = 0; i < input.length(); i++) {
        var c = input.charAt(i);
        buffer.append(c);

        if (c == '}' && depth == 1) {
            segments.add(buffer.toString());
            buffer = new StringBuilder();
            depth--;
        }
        else if (c == '{') {
            depth++;
        }
        else if (c == '}') {
            depth--;
        }
    }*//* 
    segments.add(buffer.toString());

    var output = new StringBuilder();
    for (var segment : segments) {
        output.append(this.compileRootSegment(segment));
    }*//* return output.toString();
}

private String compileRootSegment(String input) {
    var interfaceIndex = input.indexOf("*/struct ");
    if (interfaceIndex >= 0) {/* var beforeKeyword = input.substring(0, interfaceIndex).strip();
        var afterKeyword = input.substring(interfaceIndex + "interface ".length());
        var contentStart = afterKeyword.indexOf("{");
        if (contentStart >= 0) {
            var beforeContent = afterKeyword.substring(0, contentStart).strip();
            var withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
            if (withEnd.endsWith("}")) {
                var content = withEnd.substring(0, withEnd.length() - "}".length());
                return this.generatePlaceholder(beforeKeyword) + "struct " +
                        beforeContent + " {" + this.generatePlaceholder(content) + "\n}\n";
            }
        }
    */
}
/* 

    return this.generatePlaceholder(input);
}

private String generatePlaceholder(String input) {
    return "/* " + input + "*/";
}

private Option<IOException> writeString(Path target, String output) {
    try {
        Files.writeString(target, output);
        return new None<>();
    }*//*  catch (IOException e) {
        return new Some<>(e);
    }*//* 
}

private Result<String, IOException> readString(Path source) {
    try {
        return new Ok<>(Files.readString(source));
    }*//*  catch (IOException e) {
        return new Err<>(e);
    }*//* 
}*/
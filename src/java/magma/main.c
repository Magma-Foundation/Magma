/* private static class State {
    private final List<String> segments; *//* 
    private StringBuilder buffer; *//* 

    private State(List<String> segments, StringBuilder buffer) {
        this.segments = segments; *//* 
        this.buffer = buffer; *//* 
    }

    public State() {
        this(new ArrayList<>(), new StringBuilder()); *//* 
    }

    public List<String> getSegments() {
        return this.segments; *//* 
    }

    public StringBuilder getBuffer() {
        return this.buffer; *//* 
    }

    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer; *//* 
    }

    public List<String> segments() {
        return this.segments; *//* 
    }
}

void main() {
    try {
        var source = Paths.get(".", "src", "java", "magma", "Main.java"); *//* 
        var input = Files.readString(source); *//* 

        var target = source.resolveSibling("main.c"); *//* 
        Files.writeString(target, this.compileRoot(input)); *//* 
    } catch (IOException e) {
        //noinspection CallToPrintStackTrace
        e.printStackTrace(); *//* 
    }
}

private String compileRoot(String input) {
    var segments = this.divide(input, new State()).segments; *//* 

    var output = new StringBuilder(); *//* 
    for (var segment : segments) {
        output.append(this.generatePlaceholder(segment)); *//* 
    }

    return output.toString(); *//* 
}

private State divide(String input, State state) {
    var current = state; *//* 
    for (var i = 0; *//*  i < input.length(); *//*  i++) {
        var c = input.charAt(i); *//* 
        current = this.foldStatementChar(current, c); *//* 
    }
    return this.advance(current); *//* 
}

private State foldStatementChar(State state, char c) {
    var appended = this.append(state, c); *//* 
    if (c == '; *//* ') {
        return this.advance(appended); *//* 
    }
    else {
        return appended; *//* 
    }
}

private State append(State state, char c) {
    state.getBuffer().append(c); *//* 
    return state; *//* 
}

private State advance(State state) {
    state.segments().add(state.getBuffer().toString()); *//* 
    state.setBuffer(new StringBuilder()); *//* 
    return state; *//* 
}

private String generatePlaceholder(String input) {
    return "/* " + input + " */"; *//* 
} */
package com.meti;

public class Function {
    private final String body;

    public Function(String body) {
        this.body = body;
    }

    String render() {
        return "int main(){\n" + body + "\treturn 0;\n}\n";
    }
}

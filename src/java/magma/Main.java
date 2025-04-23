void main() throws IOException {
    var source = Paths.get(".", "src", "java", "magma", "Main.java");
    var input = Files.readString(source);

    var target = source.resolveSibling("main.c");
    Files.writeString(target, this.compileRoot(input));
}

private String compileRoot(String input) {
    return "/* " + input + " */";
}
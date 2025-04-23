void main() throws IOException {
    var source = Paths.get(".", "src", "java", "magma", "Main.java");
    var input = Files.readString(source);

    var target = source.resolveSibling("main.c");
    Files.writeString(target, "/* " + target + " */");
}
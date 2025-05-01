/* public  */struct Main {/* 
    private record State(List<String> segments, StringBuilder buffer) {
        public State() {
            this(new ArrayList<>(), new StringBuilder()); *//* }

        private State advance() {
            var copy = new ArrayList<String>(this.segments); *//* copy.add(this.buffer.toString()); *//* return new State(copy, new StringBuilder()); *//* }

        private State append(char c) {
            return new State(this.segments, this.buffer.append(c)); *//* }
    }

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java"); *//* var target = source.resolveSibling("main.c"); *//* var input = Files.readString(source); *//* Files.writeString(target, compileRoot(input)); *//* } catch (IOException e) {
            e.printStackTrace(); *//* }
    }

    private static String compileRoot(String input) {
        var segments = divide(input); *//* var output = new StringBuilder(); *//* for (var segment : segments) {
            output.append(compileRootSegment(segment)); *//* }

        return output.toString(); *//* }

    private static String compileRootSegment(String input) {
        var stripped = input.strip(); *//* if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return ""; *//* }

        return compileInfix(stripped, " */struct ", (beforeKeyword, afterKeyword) -> {/* 
            return compileInfix(afterKeyword, "{", (name, withEnd) -> {
                return Optional.of(generatePlaceholder(beforeKeyword) + "struct " + name.strip() + " {" + generatePlaceholder(withEnd)); *//* }); *//* }).orElseGet(() -> generatePlaceholder(stripped)); *//* }

    private static Optional<String> compileInfix(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        var classIndex = input.indexOf(infix); *//* if (classIndex >= 0) {
            var beforeKeyword = input.substring(0, classIndex); *//* var afterKeyword = input.substring(classIndex + infix.length()); *//* return mapper.apply(beforeKeyword, afterKeyword); *//* }
        return Optional.empty(); *//* }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */"; *//* }

    private static List<String> divide(String input) {
        State current = new State(); *//* for (var i = 0; *//* i < input.length(); *//* i++) {
            var c = input.charAt(i); *//* current = foldStatementChar(current, c); *//* }
        return current.advance().segments; *//* }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c); *//* if (c == '; *//* ') {
            return appended.advance(); *//* }
        return appended; *//* }

    private static Optional<String> getString(String beforeKeyword, String afterKeyword) {
        return Optional.of(generatePlaceholder(beforeKeyword) + "struct " + generatePlaceholder(afterKeyword)); *//* }
} */
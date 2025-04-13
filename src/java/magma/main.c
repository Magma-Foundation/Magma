#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
/* public class Main {
    private static class State {
        private final List<String> segments;*//* private StringBuilder buffer;*//* private State(List<String> segments, StringBuilder buffer) {
            this.segments = segments;*//* this.buffer = buffer;*//* }

        public State() {
            this(new ArrayList<>(), new StringBuilder());*//* }

        private State advance() {
            this.segments().add(this.buffer.toString());*//* this.buffer = new StringBuilder();*//* return this;*//* }

        private State append(char c) {
            this.buffer.append(c);*//* return this;*//* }

        public List<String> segments() {
            return this.segments;*//* }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");*//* String input = Files.readString(source);*//* Path target = source.resolveSibling("main.c");*//* Files.writeString(target, compile(input));*//* } catch (IOException e) {
            throw new RuntimeException(e);*//* }
    }

    private static String compile(String input) {
        List<String> segments = divide(input);*//* StringBuilder output = new StringBuilder();*//* for (String segment : segments) {
            output.append(compileRootSegment(segment));*//* }
        return output.toString();*//* }

    private static String compileRootSegment(String input) {
        String stripped = input.strip();*//* if (stripped.startsWith("package ")) {
            return "";*//* }

        if (stripped.startsWith("import ")) {
            return "#include <temp.h>\n";*//* }

        return generatePlaceholder(stripped);*//* }

    private static List<String> divide(String input) {
        State current = new State();*//* for (int i = 0;*//* i < input.length();*//* i++) {
            char c = input.charAt(i);*//* current = divideStatementChar(current, c);*//* }

        return current.advance().segments();*//* }

    private static State divideStatementChar(State current, char c) {
        State appended = current.append(c);*//* if (c == ';*//* ') {
            return appended.advance();*//* }
        else {
            return appended;*//* }
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + "*/";*//* }
}*/
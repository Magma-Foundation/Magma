/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Paths; *//* 
import java.util.ArrayList; *//* 
import java.util.List; *//* 

public class Main {
    private static class State {
        private final List<String> segments; *//* 
        private StringBuilder buffer; *//* 

        private State(List<String> segments, StringBuilder buffer) {
            this.segments = segments; *//* 
            this.buffer = buffer; *//* 
        }

        public State() {
            this(new ArrayList<>(), new StringBuilder()); *//* 
        }

        private State advance() {
            this.segments.add(this.buffer.toString()); *//* 
            this.buffer = new StringBuilder(); *//* 
            return this; *//* 
        }

        private State append(char c) {
            this.buffer.append(c); *//* 
            return this; *//* 
        }
    }

    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java"); *//* 
            var input = Files.readString(source); *//* 

            var target = source.resolveSibling("main.c"); *//* 
            Files.writeString(target, compile(input)); *//* 
        } catch (IOException e) {
            e.printStackTrace(); *//* 
        }
    }

    private static String compile(String input) {
        var segments = divide(input); *//* 

        var output = new StringBuilder(); *//* 
        for (var segment : segments) {
            output.append(compileRootSegments(segment)); *//* 
        }

        return output.toString(); *//* 
    }

    private static List<String> divide(String input) {
        var current = new State(); *//* 
        for (var i = 0; *//*  i < input.length(); *//*  i++) {
            var c = input.charAt(i); *//* 
            current = foldStatementChar(current, c); *//* 
        }

        return current.advance().segments; *//* 
    }

    private static State foldStatementChar(State state, char c) {
        var appended = state.append(c); *//* 
        if (c == '; *//* ') {
            return appended.advance(); *//* 
        }
        return appended; *//* 
    }

    private static String compileRootSegments(String input) {
        return "/* " + input + " */"; *//* 
    }
}
 */
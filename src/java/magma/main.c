/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Path; *//* 
import java.nio.file.Paths; *//* 
import java.util.ArrayList; *//* 
import java.util.List; *//* 

public class Main {
    private record State(List<String> segments, StringBuilder buffer) {
        public static State createEmpty() {
            return new State(new ArrayList<>(), new StringBuilder()); *//* 
        }

        private State advance() {
            List<String> copy = new ArrayList<>(this.segments); *//* 
            copy.add(this.buffer.toString()); *//* 

            return new State(copy, new StringBuilder()); *//* 
        }

        private State append(char c) {
            return new State(this.segments, this.buffer.append(c)); *//* 
        }
    }

    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java"); *//* 
            String input = Files.readString(source); *//* 

            Path target = source.resolveSibling("main.c"); *//* 
            Files.writeString(target, compileRoot(input)); *//* 
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace(); *//* 
        }
    }

    private static String compileRoot(String input) {
        State current = State.createEmpty(); *//* 
        for (int i = 0; *//*  i < input.length(); *//*  i++) {
            char c = input.charAt(i); *//* 
            current = foldStatementChar(current, c); *//* 
        }
        List<String> copy = current.advance().segments; *//* 

        StringBuilder output = new StringBuilder(); *//* 
        for (String segment : copy) {
            output.append(compileRootSegment(segment)); *//* 
        }
        return output.toString(); *//* 
    }

    private static State foldStatementChar(State state, char c) {
        State appended = state.append(c); *//* 
        if (c == '; *//* ') {
            return appended.advance(); *//* 
        }
        else {
            return appended; *//* 
        }
    }

    private static String compileRootSegment(String input) {
        return "/* " + input + " */"; *//* 
    }
}
 */
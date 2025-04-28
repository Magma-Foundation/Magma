/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Paths; *//* 
import java.util.ArrayList; *//* 

public class Main {
    public static void main() {
        try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java"); *//* 
            var target = source.resolveSibling("main.c"); *//* 

            var input = Files.readString(source); *//* 
            Files.writeString(target, compileRoot(input)); *//* 
        } catch (IOException e) {
            e.printStackTrace(); *//* 
        }
    }

    private static String compileRoot(String input) {
        var segments = new ArrayList<String>(); *//* 
        var buffer = new StringBuilder(); *//* 
        for (var i = 0; *//*  i < input.length(); *//*  i++) {
            var c = input.charAt(i); *//* 
            buffer.append(c); *//* 

            if (c == '; *//* ') {
                segments.add(buffer.toString()); *//* 
                buffer = new StringBuilder(); *//* 
            }
        }
        segments.add(buffer.toString()); *//* 

        var output = new StringBuilder(); *//* 
        for (var segment : segments) {
            output.append(compileRootSegment(segment)); *//* 
        }

        return output.toString(); *//* 
    }

    private static String compileRootSegment(String segment) {
        return generatePlaceholder(segment); *//* 
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */"; *//* 
    }
}
 */
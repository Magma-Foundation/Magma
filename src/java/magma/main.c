/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Paths; *//* 
import java.util.ArrayList; *//* 

public class Main {
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
            output.append(compileRootSegments(segment)); *//* 
        }
        return output.toString(); *//* 
    }

    private static String compileRootSegments(String input) {
        return "/* " + input + " */"; *//* 
    }
}
 */
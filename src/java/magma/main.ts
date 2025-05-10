/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Paths; *//* 
import java.util.ArrayList; *//* 

public class Main {
    public static void main() {
        try {
            var parent = Paths.get(".", "src", "java", "magma"); *//* 
            var source = parent.resolve("Main.java"); *//* 
            var target = parent.resolve("main.ts"); *//* 

            var input = Files.readString(source); *//* 
            Files.writeString(target, compile(input)); *//* 

            new ProcessBuilder("cmd", "/c", "npm", "exec", "ts-node", target.toAbsolutePath().toString())
                    .inheritIO()
                    .start()
                    .waitFor(); *//* 
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e); *//* 
        }
    }

    private static String compile(String input) {
        var segments = new ArrayList<String>(); *//* 
        var buffer = new StringBuilder(); *//* 
        for (var i = 0; *//*  i < input.length(); *//*  i++) {
            var c = input.charAt(i); *//* 
            buffer.append(c); *//* 

            if(c == '; *//* ') {
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
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end"); *//* 

        return "content-start " + replaced + " content-end"; *//* 
    }
}
 */
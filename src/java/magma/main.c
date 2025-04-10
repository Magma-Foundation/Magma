#include "./java/io/IOException"
#include "./java/nio/file/Files"
#include "./java/nio/file/Path"
#include "./java/nio/file/Paths"
#include "./java/util/ArrayList"
#include "./java/util/regex/Pattern"
/* 

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java"); *//* 
            String input = Files.readString(source); *//* 
            Path target = source.resolveSibling("main.c"); *//* 
            Files.writeString(target, compile(input)); *//* 
        } catch (IOException e) {
            throw new RuntimeException(e); *//* 
        }
    }

    private static String compile(String input) {
        ArrayList<String> segments = new ArrayList<>(); *//* 
        StringBuilder buffer = new StringBuilder(); *//* 
        for (int i = 0; *//*  i < input.length(); *//*  i++) {
            char c = input.charAt(i); *//* 
            buffer.append(c); *//* 
            if (c == '; *//* ') {
                segments.add(buffer.toString()); *//* 
                buffer = new StringBuilder(); *//* 
            }
        }
        segments.add(buffer.toString()); *//* 

        StringBuilder output = new StringBuilder(); *//* 
        for (String segment : segments) {
            output.append(compileRootSegment(segment)); *//* 
        }

        return output.toString(); *//* 
    }

    private static String compileRootSegment(String input) {
        if (input.startsWith("package ")) return ""; *//* 
        if (input.strip().startsWith("import ")) {
            String right = input.strip().substring("import ".length()); *//* 
            if (right.endsWith("; *//* ")) {
                String content = right.substring(0, right.length() - "; *//* ".length()); *//* 
                String joined = String.join("/", content.split(Pattern.quote("."))); *//* 
                return "#include \"./" + joined + "\"\n"; *//* 
            }
        }
        return "/* " + input + " */"; *//* 
    }
}
 */
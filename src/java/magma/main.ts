/*package magma;*//*

import java.io.IOException;*//*
import java.nio.file.Files;*//*
import java.nio.file.Paths;*//*
import java.util.ArrayList;*//*

public class Main {
    public static void main() {
        var source = Paths.get(".", "src", "java", "magma", "Main.java");*//*
        var target = source.resolveSibling("main.ts");*//*
        try {
            var input = Files.readString(source);*//*
            Files.writeString(target, compile(input));*//*
        } catch (IOException e) {
            throw new RuntimeException(e);*//*
        }
    }

    private static String compile(String input) {
        var segments = new ArrayList<String>();*//*
        var buffer = new StringBuilder();*//*
        for (var i = 0;*//* i < input.length();*//* i++) {
            var c = input.charAt(i);*//*
            buffer.append(c);*//*
            if(c == ';*//*') {
                segments.add(buffer.toString());*//*
                buffer = new StringBuilder();*//*
            }
        }

        segments.add(buffer.toString());*//*

        var output = new StringBuilder();*//*
        for (var segment : segments) {
            output.append(compileRootSegment(segment));*//*
        }
        return output.toString();*//*
    }

    private static String compileRootSegment(String segment) {
        return placeholder(segment);*//*
    }

    private static String placeholder(String input) {
        var replaced = input
                .replace("start", "start")
                .replace("end", "end");*//*

        return "start" + replaced + "end";*//*
    }
}*/
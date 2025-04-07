/* package magma; */
/* 

import java.io.IOException; */
/* 
import java.nio.file.Files; */
/* 
import java.nio.file.Path; */
/* 
import java.nio.file.Paths; */
/* 
import java.util.ArrayList; */
/* 

public class Main {
    public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java"); */
/* 
            String input = Files.readString(source); */
/* 
            String output = getString(input) + "int main(){\n\treturn 0; */
/* \n}\n"; */
/* 

            Path target = source.resolveSibling("main.c"); */
/* 
            Files.writeString(target, output); */
/* 
        } catch (IOException e) {
            throw new RuntimeException(e); */
/* 
        }
    }

    private static String getString(String input) {
        ArrayList<String> segments = new ArrayList<>(); */
/* 
        StringBuilder buffer = new StringBuilder(); */
/* 
        for (int i = 0; */
/*  i < input.length(); */
/*  i++) {
            char c = input.charAt(i); */
/* 
            buffer.append(c); */
/* 
            if (c == '; */
/* ') {
                segments.add(buffer.toString()); */
/* 
                buffer = new StringBuilder(); */
/* 
            }
        }
        segments.add(buffer.toString()); */
/* 

        StringBuilder output = new StringBuilder(); */
/* 
        for (String segment : segments) {
            output.append(compileRootSegment(segment)); */
/* 
        }

        return output.toString(); */
/* 
    }

    private static String compileRootSegment(String input) {
        System.err.println("Invalid root segment: " + input); */
/* 
        return "/* " + input + " */\n"; */
/* 
    }
} */
int main(){
	return 0;
}

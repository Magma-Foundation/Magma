#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
/* 

public  */struct Main {
};
/* public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String output = getString(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getString(String input) {
        ArrayList<String> segments = new ArrayList<>();
        StringBuilder buffer = new StringBuilder();
        int depth = 0;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            }
        }
        segments.add(buffer.toString());

        StringBuilder output = new StringBuilder();
        for (String segment : segments) {
            output.append(compileRootSegment(segment));
        }

        return output.toString();
    }

    private static String compileRootSegment(String input) {
        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include <temp.h>\n";
        int keywordIndex = input.indexOf("class ");
        if (keywordIndex >= 0) {
            String modifiers = input.substring(0, keywordIndex);
            String right = input.substring(keywordIndex + "class ".length());
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String body = right.substring(contentStart + "{".length()).strip();
                if (body.endsWith("}")) {
                    return generatePlaceholder(modifiers) + "struct " + name + " {\n};\n" + generatePlaceholder(body);
                }
            }
        }

        System.err.println("Invalid root segment: " + input);
        return generatePlaceholder(input);
    }

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    }
} */int main(){
	__main__();
	return 0;
}

#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
struct Main {
};
public static void main(String[] args) {
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
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

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, output + "int main(){\n\treturn 0;\n}\n");
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    }

    private static String compileRootSegment(String input) {
        if (input.startsWith("package ")) return "";
        if (input.strip().startsWith("import ")) return "#include <temp.h>\n";

        int classIndex = input.indexOf("class ");
        if (classIndex >= 0) {
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String content = withEnd.substring(0, withEnd.length() - "}".length());
                    return "struct " + name + " {\n};\n" + content;
                }
            }
        }

        System.err.println("Invalid segment: " + input);
        return input;
    }
int main(){
	return 0;
}

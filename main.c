/* package magma; *//* 

import java.io.IOException; *//* 
import java.nio.file.Files; *//* 
import java.nio.file.Path; *//* 
import java.nio.file.Paths; *//* 
import java.util.ArrayList; *//* 
import java.util.List; *//* 
import java.util.Optional; *//* 
import java.util.function.Function; *//* 

public  */struct Main {/* private static final List<String> methods = new ArrayList<>(); *//* 
 */
}
void __main__(/* String[] args */){/* 
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);

            Path target = Paths.get(".", "main.c");
            Files.writeString(target, compile(input));

            new ProcessBuilder("clang", "-o", "main.exe", "main.c")
                    .inheritIO()
                    .start()
                    .waitFor();
        } *//*  catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } *//* 
     */}/* String */ compile(/* String input */){/* 
        String output = compileAll(input, Main::compileRootSegment); *//* 
        String joinedMethods = String.join("", methods); *//* 
        return output + joinedMethods; *//* 
     */}/* String */ compileAll(/* String input, Function<String, String> compileRootSegment */){/* 
        ArrayList<String> segments = new ArrayList<>(); *//* 
        StringBuilder buffer = new StringBuilder(); *//* 
        int depth = 0; *//* 
        for (int i = 0; *//*  i < input.length(); *//*  i++) {
            char c = input.charAt(i);
            if (c == '\'') {
                buffer.append(c);

                i++;
                char c1 = input.charAt(i);
                buffer.append(c1);

                if (c1 == '\\') {
                    i++;
                    buffer.append(input.charAt(i));
                }

                i++;
                buffer.append(input.charAt(i));

                continue;
            }

            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            }
            else if (c == '}' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            }
            else {
                if (c == '{') {
                    depth++;
                }
                if (c == '}') {
                    depth--;
                }
            }
        } *//* 
        segments.add(buffer.toString()); *//* 

        StringBuilder output = new StringBuilder(); *//* 
        for (String segment : segments) {
            output.append(compileRootSegment.apply(segment));
        } *//* 

        return output.toString(); *//* 
     */}/* String */ compileRootSegment(/* String input */){/* 
        if (input.isBlank()) {
            return "";
        } *//* 

        int classIndex = input.indexOf("class "); *//* 
        if (classIndex >= 0) {
            String beforeKeyword = input.substring(0, classIndex);
            String afterKeyword = input.substring(classIndex + "class ".length());
            int contentStart = afterKeyword.indexOf("{");
            if (contentStart >= 0) {
                String name = afterKeyword.substring(0, contentStart).strip();
                String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
                if (withEnd.endsWith("}")) {
                    String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                    String outputContent = compileAll(inputContent, Main::compileClassSegment);
                    if (isSymbol(name)) {
                        return generatePlaceholder(beforeKeyword) + "struct " + name + " {" + outputContent + "\n}\n";
                    }
                }
            }
        } *//* 
        return generatePlaceholder(input); *//* 
     */}/* String */ compileClassSegment(/* String input */){/* 
        return compileMethod(input).orElseGet(() -> generatePlaceholder(input)); *//* 
     */}/* Optional<String> */ compileMethod(/* String input */){/* 
        int paramStart = input.indexOf("("); *//* 
        if (paramStart < 0) {
            return Optional.empty();
        } *//* 

        String definition = input.substring(0, paramStart).strip(); *//* 
        return compileDefinition(definition).flatMap(outputDefinition -> {
            String withParams = input.substring(paramStart + "(".length());
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String params = withParams.substring(0, paramEnd).strip();
                String withBraces = withParams.substring(paramEnd + ")".length()).strip();

                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    String content = withBraces.substring(1, withBraces.length() - 1);
                    String outputContent = compileAll(content, Main::compileStatementOrBlock);
                    String generated = outputDefinition + "(" + generatePlaceholder(params) + "){" + outputContent + "}";
                    methods.add(generated);
                    return Optional.of("");
                }
            }
            return Optional.empty();
        } *//* ); *//* 
     */}/* Optional<String> */ compileDefinition(/* String input */){/* 
        String stripped = input.strip(); *//* 
        int nameSeparator = stripped.lastIndexOf(" "); *//* 
        if (nameSeparator < 0) {
            return Optional.empty();
        } *//* 
        String beforeName = stripped.substring(0, nameSeparator).strip(); *//* 
        String oldName = stripped.substring(nameSeparator + " ".length()).strip(); *//* 

        if (!isSymbol(oldName)) {
            return Optional.empty();
        } *//* 

        String newName = oldName.equals("main") ? "__main__" : oldName; *//* 
        int typeSeparator = beforeName.lastIndexOf(" "); *//* 
        String type = typeSeparator >= 0
                ? beforeName.substring(typeSeparator + " ".length())
                : beforeName; *//* 

        String outputDefinition = compileType(type) + " " + newName; *//* 
        return Optional.of(outputDefinition); *//* 
     */}/* String */ compileType(/* String type */){/* 
        String stripped = type.strip(); *//* 
        if (stripped.equals("void")) {
            return "void";
        } *//* 

        return generatePlaceholder(stripped); *//* 
     */}/* String */ compileStatementOrBlock(/* String input */){/* 
        return generatePlaceholder(input); *//* 
     */}/* boolean */ isSymbol(/* String input */){/* 
        for (int i = 0; *//*  i < input.length(); *//*  i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* 
        return true; *//* 
     */}/* String */ generatePlaceholder(/* String input */){/* 
        return "/* " + input + " */"; *//* 
     */}
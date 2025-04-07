#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
#include <temp.h>
/* 

public  */struct Main {
};
/* public static */void main(struct String* args){/* 
        try {
            Path source = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(source);
            String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n}\n";

            Path target = source.resolveSibling("main.c");
            Files.writeString(target, output);
        } *//*  catch (IOException e) {
            throw new RuntimeException(e);
        } *//* 
     */
}
/* private static */struct String compile(struct String input){/* 
        Optional<String> s = compileStatements(input, Main::compileRootSegment); *//* 
        return s.orElse(""); *//* 
     */
}
/* private static *//* Optional<String> */ compileStatements(struct String input, struct  Function<String, /*  *//* Optional<String>> */ compiler){/* 
        return compileAll(divideStatements(input), compiler, Main::mergeStatements); *//* 
     */
}
/* private static *//* Optional<String> */ compileAll(/* 
            *//* List<String> */ segments, /* 
           */struct  Function<String, /*  *//* Optional<String>> */ compiler, /* 
           */struct  BiFunction<StringBuilder, struct  String, /*  StringBuilder> merger
   */struct  ){/* 
        Optional<StringBuilder> maybeOutput = Optional.of(new StringBuilder()); *//* 
        for (String segment : segments) {
            maybeOutput = maybeOutput.flatMap(output -> {
                return compiler.apply(segment).map(str -> merger.apply(output, str));
            });
        } *//* 

        return maybeOutput.map(StringBuilder::toString); *//* 
     */
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, /*  */struct String str){/* 
        return output.append(str); *//* 
     */
}
/* private static *//* ArrayList<String> */ divideStatements(struct String input){/* 
        ArrayList<String> segments = new ArrayList<>(); *//* 
        StringBuilder buffer = new StringBuilder(); *//* 
        int depth = 0; *//* 
        for (int i = 0; *//*  i < input.length(); *//*  i++) {
            char c = input.charAt(i);
            buffer.append(c);
            if (c == ';' && depth == 0) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
            } else if (c == '} *//* ' && depth == 1) {
                segments.add(buffer.toString());
                buffer = new StringBuilder();
                depth--;
            } *//*  else {
                if (c == '{') depth++;
                if (c == '}') depth--;
            } *//* 
         */
}
/* 
        segments.add(buffer.toString()); *//* 
        return segments; *//* 
     *//* 

    private static Optional<String> compileRootSegment(String input) {
        if (input.startsWith("package ")) return Optional.of("");
        if (input.strip().startsWith("import ")) return Optional.of("#include <temp.h>\n");
        int keywordIndex = input.indexOf(" */struct ");
        if (keywordIndex >= 0) {
};
/* String modifiers = input.substring(0, keywordIndex); *//* 
            String right = input.substring(keywordIndex + "class ".length()); *//* 
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String body = right.substring(contentStart + "{".length()).strip();
                if (body.endsWith("}")) {
                    String inputContent = body.substring(0, body.length() - "}".length());
                    return compileStatements(inputContent, Main::compileClassSegment).map(outputContent -> {
                        return generatePlaceholder(modifiers) + "struct " + name + " {\n};\n" + outputContent;
                    });
                }
            } *//* 
        }

        return Optional.of(invalidate("root segment", input));
     *//* 

    private static String invalidate(String type, String input) {
        System.err.println("Invalid " + type + ": " + input);
        return generatePlaceholder(input);
    } *//* 

    private static Optional<String> compileClassSegment(String input) {
        Optional<String> maybeMethod = compileMethod(input);
        if (maybeMethod.isPresent()) return maybeMethod;

        return Optional.of(invalidate("class segment", input));
    } *//* 

    private static Optional<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return Optional.empty();

        String header = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());
        int paramEnd = withParams.indexOf(")");
        if (paramEnd >= 0) {
            String paramString = withParams.substring(0, paramEnd);
            String withBody = withParams.substring(paramEnd + ")".length()).strip();
            List<String> inputParams = Arrays.asList(paramString.split(Pattern.quote(",")));

            if (withBody.startsWith("{") && withBody.endsWith("}")) {
                return compileAll(inputParams, Main::compileDefinition, Main::mergeValues)
                        .flatMap(outputParams -> {
                            return compileDefinition(header).flatMap(definition -> {
                                return compileStatements(withBody.substring(1, withBody.length() - 1), Main::compileStatement).map(statement -> {
                                    return definition + "(" +
                                            outputParams +
                                            "){" +
                                            statement +
                                            "\n}\n";
                                });
                            });
                        });
            } else {
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    } *//* 

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(", ").append(element);
    } *//* 

    private static Optional<String> compileStatement(String input) {
        return Optional.of(invalidate("statement", input));
    } *//* 

    private static Optional<String> compileDefinition(String header) {
        int nameSeparator = header.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = header.substring(0, nameSeparator);

            int space = beforeName.lastIndexOf(" ");

            String modifiers;
            String type;
            if (space >= 0) {
                modifiers = generatePlaceholder(beforeName.substring(0, space));
                type = beforeName.substring(space + 1);
            } else {
                modifiers = "";
                type = beforeName;
            }

            String name = header.substring(nameSeparator + " ".length());
            return Optional.of(modifiers + compileType(type) + " " + name);
        }
        return Optional.of(generatePlaceholder(header));
    } *//* 

    private static String compileType(String type) {
        String stripped = type.strip();
        if (stripped.equals("void")) return "void";
        if (stripped.endsWith("[]")) return compileType(stripped.substring(0, stripped.length() - "[]".length())) + "*";

        if (isSymbol(stripped)) {
            return "struct " + stripped;
        }

        return generatePlaceholder(stripped);
    } *//* 

    private static boolean isSymbol(String input) {
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    } *//* 

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    } *//* 
} */int main(){
	__main__();
	return 0;
}

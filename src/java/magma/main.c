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
/* private sealed interface Result<T, X> permits Ok, Err {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    } *//* 

    private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(value);
        }
    } *//* 

    private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(error);
        }
    } *//* public static */void main(struct String* args){
	struct Path source = Paths.get(".", "src", "java", "magma", "Main.java");/* 
        readString(source)
                .match(input -> runWithInput(source, input), Optional::of)
                .ifPresent(Throwable::printStackTrace); *//* 
     */
}
/* private static *//* Optional<IOException> */ runWithInput(struct Path source, struct String input){/* 
        String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n} *//* \n"; */
	struct Path target = source.resolveSibling("main.c");/* 
        return writeString(target, output); *//* 
     */
}
/* private static *//* Optional<IOException> */ writeString(struct Path target, struct String output){/* 
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } *//*  catch (IOException e) {
            return Optional.of(e);
        } *//* 
     */
}
/* private static Result<String, *//* IOException> */ readString(struct Path source){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } *//* 
     */
}
/* private static */struct String compile(struct String input){
	/* Optional<String> */ s = compileStatements(input, /*  Main::compileRootSegment */);/* 
        return s.orElse(""); *//* 
     */
}
/* private static *//* Optional<String> */ compileStatements(struct String input, /* Function<String */, /* Optional<String>> */ compiler){/* 
        return compileAll(divideStatements(input), compiler, Main::mergeStatements); *//* 
     */
}
/* private static *//* Optional<String> */ compileAll(/* List<String> */ segments, /* Function<String */, /* Optional<String>> */ compiler, /* BiFunction<StringBuilder */, /* String */, /* StringBuilder> */ merger){
	/* Optional<StringBuilder> */ maybeOutput = Optional.of(/* new StringBuilder */());/* 
        for (String segment : segments) {
            maybeOutput = maybeOutput.flatMap(output -> {
                return compiler.apply(segment).map(str -> merger.apply(output, str));
            });
        } *//* 

        return maybeOutput.map(StringBuilder::toString); *//* 
     */
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, struct String str){/* 
        return output.append(str); *//* 
     */
}
/* private static *//* ArrayList<String> */ divideStatements(struct String input){
	/* ArrayList<String> */ segments = /* new ArrayList<> */();
	struct StringBuilder buffer = /* new StringBuilder */();
	struct int depth = /*  0 */;
	/* for *//* (int */ i = /*  0 */;/*  i < input.length(); *//*  i++) {
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

            if (withBody.startsWith("{") && withBody.endsWith("}")) {
                return compileValues(paramString, Main::compileDefinition)
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

    private static Optional<String> compileValues(String input, Function<String, Optional<String>> compiler) {
        return compileAll(Arrays.asList(input.split(Pattern.quote(","))), compiler, Main::mergeValues);
    } *//* 

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(", ").append(element);
    } *//* 

    private static Optional<String> compileStatement(String input) {
        String stripped = input.strip();
        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            int separator = withoutEnd.indexOf("=");
            if (separator >= 0) {
                String inputDefinition = withoutEnd.substring(0, separator);
                String inputValue = withoutEnd.substring(separator + "=".length());
                return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
                    return compileValue(inputValue).map(outputValue -> {
                        return "\n\t" + outputDefinition + " = " + outputValue + ";";
                    });
                });
            }
        }

        return Optional.of(invalidate("statement", input));
    } *//* 

    private static Optional<String> compileValue(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) return Optional.of(stripped);

        if (stripped.endsWith(")")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ")".length());
            int argsStart = withoutEnd.indexOf("(");
            if (argsStart >= 0) {
                String inputCaller = withoutEnd.substring(0, argsStart);
                String inputArguments = withoutEnd.substring(argsStart + 1);
                return compileValues(inputArguments, Main::compileValue).flatMap(outputValues -> {
                    return compileValue(inputCaller).map(outputCaller -> {
                        return outputCaller + "(" + outputValues + ")";
                    });
                });
            }
        }

        int separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            String object = stripped.substring(0, separator);
            String property = stripped.substring(separator + ".".length());

            return compileValue(object).map(newObject -> {
                return newObject + "." + property;
            });
        }

        if (isSymbol(stripped)) {
            return Optional.of(stripped);
        }

        return Optional.of(generatePlaceholder(input));
    } *//* 

    private static Optional<String> compileDefinition(String input) {
        String stripped = input.strip();
        int nameSeparator = stripped.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            String beforeName = stripped.substring(0, nameSeparator);

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

            String name = stripped.substring(nameSeparator + " ".length());
            return Optional.of(modifiers + compileType(type) + " " + name);
        }
        return Optional.of(generatePlaceholder(stripped));
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

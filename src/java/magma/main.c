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
    } *//* 

    private static final List<String> imports = new ArrayList<>(); *//* 
    private static final List<String> structs = new ArrayList<>(); *//* 
    private static final List<String> functions = new ArrayList<>(); *//* 
    private static final int lambdaCounter = 0; *//* 
        segments.add(buffer.toString()); *//* 
        return segments; *//* 
     *//* String modifiers = input.substring(0, keywordIndex); *//* 
            String right = input.substring(keywordIndex + "class ".length()); *//* 
            int contentStart = right.indexOf("{");
            if (contentStart >= 0) {
                String name = right.substring(0, contentStart).strip();
                String body = right.substring(contentStart + "{".length()).strip();
                if (body.endsWith("}")) {
                    String inputContent = body.substring(0, body.length() - "}".length());
                    return compileStatements(inputContent, Main::compileClassSegment).map(outputContent -> {
                        return generateStruct(modifiers, name) + outputContent;
                    });
                }
            } *//* 
        }

        return Optional.of(invalidate("root segment", input));
     *//* 

    private static String generateStruct(String modifiers, String name) {
        String generated = generatePlaceholder(modifiers) + "struct " + name + " {\n};\n";
        structs.add(generated);
        return "";
    } *//* 

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
        if (paramEnd < 0) return Optional.empty();

        String paramString = withParams.substring(0, paramEnd);
        String withBody = withParams.substring(paramEnd + ")".length()).strip();

        if (!withBody.startsWith("{") || !withBody.endsWith("}")) return Optional.empty();

        return compileValues(paramString, Main::compileDefinition).flatMap(outputParams -> {
            return compileDefinition(header).flatMap(definition -> {
                return compileStatements(withBody.substring(1, withBody.length() - 1), Main::compileStatement).map(statement -> {
                    return addFunction(definition, outputParams, statement);
                });
            });
        });
    } *//* 

    private static String addFunction(String definition, String params, String content) {
        String function = definition + "(" + params + "){" + content + "\n}\n";
        functions.add(function);
        return "";
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
        if (stripped.isEmpty()) return Optional.of("");

        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());

            int separator = withoutEnd.indexOf("=");
            if (separator >= 0) {
                String inputDefinition = withoutEnd.substring(0, separator);
                String inputValue = withoutEnd.substring(separator + "=".length());
                return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
                    return compileValue(inputValue).map(outputValue -> {
                        return generateStatement(outputDefinition + " = " + outputValue);
                    });
                });
            }

            Optional<String> maybeInvocation = compileInvocation(withoutEnd);
            if (maybeInvocation.isPresent()) return maybeInvocation.map(Main::generateStatement);
        }

        return Optional.of(invalidate("statement", input));
    } *//* 

    private static String generateStatement(String value) {
        return "\n\t" + value + ";";
    } *//* 

    private static Optional<String> compileValue(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) return Optional.of(stripped);

        Optional<String> maybeInvocation = compileInvocation(stripped);
        if (maybeInvocation.isPresent()) return maybeInvocation;

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

        int arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            String paramName = stripped.substring(0, arrowIndex).strip();
            String value = stripped.substring(arrowIndex + "->".length());
            String lambda = "__lambda" + lambdaCounter + "__";
            addFunction(generateDefinition("", "auto", lambda), generateDefinition("", "auto", paramName), "{\n\treturn " +
                    value + ";\n" +
                    "}");
            
            return Optional.of(lambda);
        }

        return Optional.of(invalidate("value", input));
    } *//* 

    private static Optional<String> compileInvocation(String stripped) {
        if (!stripped.endsWith(")")) return Optional.empty();
        String withoutEnd = stripped.substring(0, stripped.length() - ")".length());

        int argsStart = -1;
        int depth = 0;
        for (int i = withoutEnd.length() - 1; i >= 0; i--) {
            char c = withoutEnd.charAt(i);
            if (c == '(' && depth == 0) {
                argsStart = i;
                break;
            } else {
                if (c == ')') depth++;
                if (c == '(') depth--;
            }
        }

        if (argsStart < 0) return Optional.empty();

        String inputCaller = withoutEnd.substring(0, argsStart);
        String inputArguments = withoutEnd.substring(argsStart + 1);
        return compileValues(inputArguments, Main::compileValue).flatMap(outputValues -> {
            return compileValue(inputCaller).map(outputCaller -> {
                return outputCaller + "(" + outputValues + ")";
            });
        });
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
            return Optional.of(generateDefinition(modifiers, compileType(type), name));
        }

        return Optional.of(invalidate("definition", stripped));
    } *//* 

    private static String generateDefinition(String modifiers, String type, String name) {
        return modifiers + type + " " + name;
    } *//* 

    private static String compileType(String type) {
        String stripped = type.strip();
        if (stripped.equals("void")) return "void";
        if (stripped.endsWith("[]")) return compileType(stripped.substring(0, stripped.length() - "[]".length())) + "*";

        if (isSymbol(stripped)) {
            return "struct " + stripped;
        }

        return invalidate("type", stripped);
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
} */#include <temp.h>
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
/* 

    private static Optional<String> compileRootSegment(String input) {
        if (input.startsWith("package ")) return Optional.of("");

        if (input.strip().startsWith("import ")) {
            String value = "#include <temp.h>\n";
            imports.add(value);
            return Optional.of("");
        }

        int keywordIndex = input.indexOf(" */struct ");
        if (keywordIndex >= 0) {
};
auto __lambda0__(auto input){{
	return  runWithInput(source;
}
}
/* public static */void main(struct String* args){
	struct Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	readString(source).match(__lambda0__, /*  input) */, /*  Optional::of */).ifPresent(/* Throwable::printStackTrace */);
}
/* private static *//* Optional<IOException> */ runWithInput(struct Path source, struct String input){/* 
        String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n} *//* \n"; */
	struct Path target = source.resolveSibling("main.c");
	/* return writeString */(target, output);
}
/* private static *//* Optional<IOException> */ writeString(struct Path target, struct String output){/* 
        try {
            Files.writeString(target, output);
            return Optional.empty();
        } *//*  catch (IOException e) {
            return Optional.of(e);
        } */
}
/* private static Result<String, *//* IOException> */ readString(struct Path source){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } */
}
auto __lambda0__(auto compiled){{
	return  mergeAll(compiled;
}
}
/* private static */struct String compile(struct String input){
	/* List<String> */ segments = divideStatements(input);/* 
        return parseAll(segments, Main::compileRootSegment)
                .map(compiled -> {
                    compiled.addAll(imports);
                    compiled.addAll(structs);
                    compiled.addAll(functions);
                    return compiled;
                } */
	/* )
                 */.map(__lambda0__, /*  Main::mergeStatements) */).orElse("");
}
/* private static *//* Optional<String> */ compileStatements(struct String input, /* Function<String */, /* Optional<String>> */ compiler){
	/* return compileAll */(divideStatements(input), compiler, /*  Main::mergeStatements */);
}
auto __lambda0__(auto compiled){{
	return  mergeAll(compiled;
}
}
/* private static *//* Optional<String> */ compileAll(/* List<String> */ segments, /* Function<String */, /* Optional<String>> */ compiler, /* BiFunction<StringBuilder */, /* String */, /* StringBuilder> */ merger){
	/* return parseAll */(segments, compiler).map(__lambda0__, /*  merger) */);
}
/* private static */struct String mergeAll(/* List<String> */ compiled, /* BiFunction<StringBuilder */, /* String */, /* StringBuilder> */ merger){
	struct StringBuilder output = /* new StringBuilder */();/* 

        for (String segment : compiled) {
            output = merger.apply(output, segment);
        } */
	/* return output */.toString();
}
/* private static *//* Optional<List<String>> */ parseAll(/* List<String> */ segments, /* Function<String */, /* Optional<String>> */ compiler){
	/* Optional<List<String>> */ maybeCompiled = Optional.of(/* new ArrayList<String> */());/* 
        for (String segment : segments) {
            maybeCompiled = maybeCompiled.flatMap(allCompiled -> {
                return compiler.apply(segment).map(compiled -> {
                    allCompiled.add(compiled);
                    return allCompiled;
                });
            });
        } *//* 
        return maybeCompiled; */
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, struct String str){
	/* return output */.append(str);
}
/* private static *//* ArrayList<String> */ divideStatements(struct String input){
	/* ArrayList<String> */ segments = /* new ArrayList<> */();
	struct StringBuilder buffer = /* new StringBuilder */();
	struct int depth = /*  0 */;
	/* for *//* (int */ i = /*  0 */;
	/* i < input */.length();/*  i++) {
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
            } */
}
int main(){
	__main__();
	return 0;
}

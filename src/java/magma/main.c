/* 

    private static final List<String> imports = new ArrayList<>(); *//* 
    private static final List<String> structs = new ArrayList<>(); *//* 
    private static final List<String> functions = new ArrayList<>(); *//* 
    private static int lambdaCounter = 0; *//* ' && appended.isShallow()) return appended.advance().exit(); *//* 
        if (c == '{') return appended.enter();
        if (c == '} *//* ') return appended.exit(); *//* 
        return appended; *//* 
     *//* 

    private static boolean isEmpty(State state) {
        return state.queue.isEmpty();
    } *//* String modifiers = input.substring(0, classIndex); *//* 
            String right = input.substring(classIndex + "class ".length()); *//* 
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

        return new Some<>(invalidate("root segment", input));
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

    private static Option<String> compileClassSegment(String input) {
        Option<String> maybeMethod = compileMethod(input);
        if (maybeMethod.isPresent()) return maybeMethod;

        int interfaceIndex = input.indexOf("interface ");
        if (interfaceIndex >= 0) {
            return new Some<>(generateStruct("", "Temp"));
        }

        int recordIndex = input.indexOf("record ");
        if (recordIndex >= 0) {
            return new Some<>(generateStruct("", "Temp"));
        }

        return new Some<>(invalidate("class segment", input));
    } *//* 

    private static Option<String> compileMethod(String input) {
        int paramStart = input.indexOf("(");
        if (paramStart < 0) return new None<>();

        String header = input.substring(0, paramStart).strip();
        String withParams = input.substring(paramStart + "(".length());
        int paramEnd = withParams.indexOf(")");
        if (paramEnd < 0) return new None<>();

        String paramString = withParams.substring(0, paramEnd);
        String withBody = withParams.substring(paramEnd + ")".length()).strip();

        if (!withBody.startsWith("{") || !withBody.endsWith("}")) return new None<>();

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

    private static Option<String> compileValues(String input, Function<String, Option<String>> compiler) {
        return compileAll(divideAll(input, Main::divideValueChar), compiler, Main::mergeValues);
    } *//* 

    private static State divideValueChar(State state, Character c) {
        if (c == ',' && state.isLevel()) return state.advance();

        State appended = state.append(c);
        if (c == '(') return appended.enter();
        if (c == ')') return appended.exit();
        return appended;
    } *//* 

    private static StringBuilder mergeValues(StringBuilder buffer, String element) {
        if (buffer.isEmpty()) return buffer.append(element);
        return buffer.append(", ").append(element);
    } *//* 

    private static Option<String> compileStatement(String input) {
        String stripped = input.strip();
        if (stripped.isEmpty()) return new Some<>("");

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

            Option<String> maybeInvocation = compileInvocation(withoutEnd);
            if (maybeInvocation.isPresent()) return maybeInvocation.map(Main::generateStatement);
        }

        return new Some<>(invalidate("statement", input));
    } *//* 

    private static String generateStatement(String value) {
        return "\n\t" + value + ";";
    } *//* 

    private static Option<String> compileValue(String input) {
        String stripped = input.strip();
        if (stripped.startsWith("\"") && stripped.endsWith("\"")) return new Some<>(stripped);

        int arrowIndex = stripped.indexOf("->");
        if (arrowIndex >= 0) {
            String paramName = stripped.substring(0, arrowIndex).strip();
            String inputValue = stripped.substring(arrowIndex + "->".length());
            if (isSymbol(paramName)) {
                return compileValue(inputValue).map(outputValue -> generateLambda(paramName, outputValue));
            }
        }

        Option<String> maybeInvocation = compileInvocation(stripped);
        if (maybeInvocation.isPresent()) return maybeInvocation;

        int dataSeparator = stripped.lastIndexOf(".");
        if (dataSeparator >= 0) {
            String object = stripped.substring(0, dataSeparator);
            String property = stripped.substring(dataSeparator + ".".length());

            return compileValue(object).map(newObject -> {
                return newObject + "." + property;
            });
        }

        int methodSeparator = stripped.lastIndexOf("::");
        if (methodSeparator >= 0) {
            String object = stripped.substring(0, methodSeparator);
            String property = stripped.substring(methodSeparator + "::".length());

            return compileValue(object).map(newObject -> {
                String caller = newObject + "." + property;
                String paramName = newObject.toLowerCase();
                return generateLambda(paramName, generateInvocation(caller, paramName));
            });
        }

        if (isSymbol(stripped)) {
            return new Some<>(stripped);
        }

        return new Some<>(invalidate("value", input));
    } *//* 

    private static String generateLambda(String paramName, String lambdaValue) {
        String lambda = "__lambda" + lambdaCounter + "__";
        lambdaCounter++;

        String definition = generateDefinition("", "auto", lambda);
        String param = generateDefinition("", "auto", paramName);
        addFunction(definition, param, "\n\treturn " + lambdaValue + ";");

        return lambda;
    } *//* 

    private static Option<String> compileInvocation(String stripped) {
        if (!stripped.endsWith(")")) return new None<>();
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

        if (argsStart < 0) return new None<>();

        String inputCaller = withoutEnd.substring(0, argsStart);
        String inputArguments = withoutEnd.substring(argsStart + 1);
        return compileValues(inputArguments, Main::compileValue).flatMap(outputValues -> {
            return compileValue(inputCaller).map(outputCaller -> {
                return generateInvocation(outputCaller, outputValues);
            });
        });
    } *//* 

    private static String generateInvocation(String caller, String arguments) {
        return caller + "(" + arguments + ")";
    } *//* 

    private static Option<String> compileDefinition(String input) {
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
            return new Some<>(generateDefinition(modifiers, compileType(type), name));
        }

        return new Some<>(invalidate("definition", stripped));
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
#include <temp.h>
#include <temp.h>
/*  */struct Temp {
};
/*  */struct Temp {
};
/*  */struct Temp {
};
/*  */struct Temp {
};
/*  */struct Temp {
};
/* 

public  */struct Main {
};
/* 

    private static Option<String> compileRootSegment(String input) {
        if (input.startsWith("package ")) return new Some<>("");

        if (input.strip().startsWith("import ")) {
            String value = "#include <temp.h>\n";
            imports.add(value);
            return new Some<>("");
        }

        int classIndex = input.indexOf(" */struct ");
        if (classIndex >= 0) {
};
/* private static class State {
        private final Deque<Character> queue;
        private final List<String> segments;
        private StringBuilder buffer;
        private int depth;

        */struct private State(/* Deque<Character> */ queue){
	this(queue, /* new ArrayList<> */(), /* new StringBuilder */(), /*  0 */);
	/* }

        private State(Deque<Character> queue, List<String> segments, StringBuilder buffer, int depth) {
           */struct  this.queue = queue;
	/* this.segments */ = segments;
	/* this.buffer */ = buffer;
	/* this.depth */ = depth;
	/* }

        private State enter() {
           */struct  this.depth = /*  depth + 1 */;/* 
            return this; */
	/* }

        private State exit() {
           */struct  this.depth = /*  depth - 1 */;/* 
            return this; */
	/* }

        private State append(char c) {
            buffer */.append(c);/* 
            return this; */
	/* }

        private State advance() {
            segments */.add(buffer.toString());
	/* this.buffer */ = /* new StringBuilder */();/* 
            return this; */
	/* }

        private boolean isLevel() {
            */struct return depth = /* = 0 */;
	/* }

        private char getPop() {
            return queue */.pop();
	/* }

        private boolean isShallow() {
            */struct return depth = /* = 1 */;/* 
        }

        public List<String> segments() {
            return segments; *//* 
        }
     */
}
/* private static final class None<T> implements Option<T> {
        @Override
        public */void ifPresent(/* Consumer<T> */ ifPresent){
	/* }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<> */();
	/* }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<> */();/* 
        }

        @Override
        public T orElse(T other) {
            return other; *//* 
        }

        @Override
        public boolean isPresent() {
            return false; *//* 
        }
     */
}
auto __lambda0__(auto throwable){
	return Throwable.printStackTrace(throwable);
}
auto __lambda1__(auto input){
	return runWithInput(source, input);
}
auto __lambda2__(auto some){
	return Some.new(some);
}
/* public static */void main(struct String* args){
	struct Path source = Paths.get(".", "src", "java", "magma", "Main.java");
	readString(source).match(__lambda1__, __lambda2__).ifPresent(__lambda0__);
}
/* private static *//* Option<IOException> */ runWithInput(struct Path source, struct String input){/* 
        String output = compile(input) + "int main(){\n\t__main__();\n\treturn 0;\n} *//* \n"; */
	struct Path target = source.resolveSibling("main.c");
	/* return writeString */(target, output);
}
/* private static *//* Option<IOException> */ writeString(struct Path target, struct String output){/* 
        try {
            Files.writeString(target, output);
            return new None<>();
        } *//*  catch (IOException e) {
            return new Some<>(e);
        } */
}
/* private static Result<String, *//* IOException> */ readString(struct Path source){/* 
        try {
            return new Ok<>(Files.readString(source));
        } *//*  catch (IOException e) {
            return new Err<>(e);
        } */
}
auto __lambda3__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda4__(auto main){
	return Main.mergeStatements(main);
}
auto __lambda5__(auto compiled){
	return mergeAll(compiled, __lambda4__);
}
/* private static */struct String compile(struct String input){
	/* List<String> */ segments = divideAll(input, __lambda3__);/* 
        return parseAll(segments, Main::compileRootSegment)
                .map(compiled -> {
                    compiled.addAll(imports);
                    compiled.addAll(structs);
                    compiled.addAll(functions);
                    return compiled;
                } */
	/* )
                 */.map(__lambda5__).orElse("");
}
auto __lambda6__(auto main){
	return Main.divideStatementChar(main);
}
auto __lambda7__(auto main){
	return Main.mergeStatements(main);
}
/* private static *//* Option<String> */ compileStatements(struct String input, /* Function<String */, /* Option<String>> */ compiler){
	/* return compileAll */(divideAll(input, __lambda6__), compiler, __lambda7__);
}
auto __lambda8__(auto compiled){
	return mergeAll(compiled, merger);
}
/* private static *//* Option<String> */ compileAll(/* List<String> */ segments, /* Function<String */, /* Option<String>> */ compiler, /* BiFunction<StringBuilder */, /* String */, /* StringBuilder> */ merger){
	/* return parseAll */(segments, compiler).map(__lambda8__);
}
/* private static */struct String mergeAll(/* List<String> */ compiled, /* BiFunction<StringBuilder */, /* String */, /* StringBuilder> */ merger){
	struct StringBuilder output = /* new StringBuilder */();/* 

        for (String segment : compiled) {
            output = merger.apply(output, segment);
        } */
	/* return output */.toString();
}
/* private static *//* Option<List<String>> */ parseAll(/* List<String> */ segments, /* Function<String */, /* Option<String>> */ compiler){
	/* Option<List<String>> */ maybeCompiled = /* new Some<> */(/* new ArrayList<> */());/* 
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
auto __lambda9__(auto linkedlist){
	return LinkedList.new(linkedlist);
}
auto __lambda10__(auto input){
	return input.charAt(input);
}
/* private static *//* List<String> */ divideAll(struct String input, /* BiFunction<State */, /* Character */, /* State> */ divider){
	/* LinkedList<Character> */ queue = IntStream.range(/* 0 */, input.length()).mapToObj(__lambda10__).collect(Collectors.toCollection(__lambda9__));
	struct State state = /* new State */(queue);
	struct State current = state;/* 
        while (!isEmpty(current)) {
            char c = current.getPop();
            current = divider.apply(current, c);
        } */
	/* return current */.advance().segments();
}
/* private static */struct State divideStatementChar(struct State state, struct char c){
	struct State appended = state.append(c);
	struct if (c = /* = ' */;
	/* ' && appended */.isLevel()) return appended.advance();/* 
        if (c == ' */
}
int main(){
	__main__();
	return 0;
}

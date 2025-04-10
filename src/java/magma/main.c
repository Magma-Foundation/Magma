#include "./org/jetbrains/annotations/NotNull"
#include "./java/io/IOException"
#include "./java/nio/file/Path"
#include "./java/nio/file/Paths"
#include "./java/util/ArrayList"
#include "./java/util/Arrays"
#include "./java/util/Collections"
#include "./java/util/Deque"
#include "./java/util/LinkedList"
#include "./java/util/List"
#include "./java/util/Optional"
#include "./java/util/function/BiFunction"
#include "./java/util/function/Function"
#include "./java/util/regex/Pattern"
#include "./java/util/stream/Collectors"
#include "./java/util/stream/IntStream"
struct Result<T, X> {
	Function<struct X, struct R> whenErr);
};
struct Err<T, X>(X error) implements Result<T, X> {
};
struct Ok<T, X>(T value) implements Result<T, X> {
};
struct State {
	Deque<char> queue;
	List<struct String> segments;
	struct StringBuilder buffer;
	int depth;
};
struct Main {
};
List<struct String> imports = ArrayList<>();
List<struct String> structs = ArrayList<>();
List<struct String> globals = ArrayList<>();
List<struct String> methods = ArrayList<>();
<R> R match(Function<struct T, struct R> whenOk, Function<struct X, struct R> whenErr) {
	return whenErr.apply(this.error);
}
<R> R match(Function<struct T, struct R> whenOk, Function<struct X, struct R> whenErr) {
	return whenOk.apply(this.value);
}
struct private State(Deque<char> queue, List<struct String> segments, struct StringBuilder buffer, int depth) {
	this.queue = queue;
	this.segments = segments;
	this.buffer = buffer;
	this.depth = depth;
}
struct public State(Deque<char> queue) {
	this(queue, ArrayList<>(), struct StringBuilder(), /*  0 */);
}
struct State advance() {
	this.segments.add(this.buffer.toString());
	this.buffer = struct StringBuilder();
	return this;
}
struct State append(char c) {
	this.buffer.append(c);
	return this;
}
int isLevel() {
	return this.depth == 0;
}
char pop() {
	return this.queue.pop();
}
int hasElements() {
	return /* !this */.queue.isEmpty();
}
struct State exit() {
	this.depth = this.depth - 1;
	return this;
}
struct State enter() {
	this.depth = this.depth + 1;
	return this;
}
List<struct String> segments() {
	return this.segments;
}
void main(struct String* args) {
	/* Path source */ = Paths.get(/* " */.", /*  "src" */, /*  "java" */, /*  "magma" */, /* "Main */.java");
	magma.Files.readString(/* source) */.match(/* input -> compileAndWrite(input, source), Optional::of */).ifPresent(Throwable::printStackTrace);
}
Optional<struct IOException> compileAndWrite(struct String input, struct Path source) {
	/* Path target */ = source.resolveSibling(/* "main */.c");
	/* String output */ = compile(input);
	return magma.Files.writeString(target, output);
}
struct String compile(struct String input) {
	/* List<String> segments */ = divide(input, /*  Main::divideStatementChar */);
	return parseAll(segments, /* Main::compileRootSegment) */.map(/* list -> {
                    List<String> copy = new ArrayList<String> */(/* );
                    copy */.addAll(/* imports);
                    copy */.addAll(/* structs);
                    copy */.addAll(/* globals);
                    copy */.addAll(methods);
                    copy.addAll(list);
                    return copy;
                }).map(compiled -> mergeAll(Main::mergeStatements, compiled)).or(() -> generatePlaceholder(input)).orElse("");
}
Optional<struct String> compileStatements(struct String input, Function<struct String, Optional<struct String>> compiler) {
	return compileAndMerge(/* divide(input */, /*  Main::divideStatementChar) */, compiler, /*  Main::mergeStatements */);
}
Optional<struct String> compileAndMerge(List<struct String> segments, Function<struct String, Optional<struct String>> compiler, BiFunction<struct StringBuilder, struct String, struct StringBuilder> merger) {
	return parseAll(segments, /* compiler) */.map(/* compiled -> mergeAll(merger, compiled */));
}
struct String mergeAll(BiFunction<struct StringBuilder, struct String, struct StringBuilder> merger, List<struct String> compiled) {
	/* StringBuilder output */ = struct StringBuilder();/* 
        for (String segment : compiled) {
            output = merger.apply(output, segment);
        } */
	return output.toString();
}
Optional<List<struct String>> parseAll(List<struct String> segments, Function<struct String, Optional<struct String>> compiler) {
	/* Optional<List<String>> maybeCompiled */ = Optional.of(ArrayList<struct String>());/* 
        for (String segment : segments) {
            maybeCompiled = maybeCompiled.flatMap(allCompiled -> compiler.apply(segment).map(compiledSegment -> {
                allCompiled.add(compiledSegment);
                return allCompiled;
            }));
        } */
	return maybeCompiled;
}
struct StringBuilder mergeStatements(struct StringBuilder output, struct String compiled) {
	return output.append(compiled);
}
List<struct String> divide(struct String input, BiFunction<struct State, struct Character, struct State> divider) {
	/* LinkedList<Character> queue */ = IntStream.range(/* 0 */, input.length(/* )) */.mapToObj(/* input::charAt */).collect(Collectors.toCollection(LinkedList::new));
	/* State state */ = struct State(queue);/* 
        while (state.hasElements()) {
            char c = state.pop();

            if (c == '\'') {
                state.append(c);
                char maybeSlash = state.pop();
                state.append(maybeSlash);

                if (maybeSlash == '\\') state.append(state.pop());
                state.append(state.pop());
                continue;
            }

            state = divider.apply(state, c);
        } */
	return state.advance(/* ) */.segments();
}
struct State divideStatementChar(struct State state, char c) {
	/* State appended */ = state.append(c);
	/* if (c */ = /* = ';' && appended */.isLevel(/* )) return appended */.advance();
	/* if (c */ = /* = '}' && isShallow */(/* appended)) return appended */.advance().exit();
	/* if (c */ = /* = '{' || c == ' */(/* ') return appended */.enter();
	/* if (c */ = /* = '}' || c == ')') return appended */.exit();
	return appended;
}
int isShallow(struct State state) {
	return state.depth == 1;
}
Optional<struct String> compileRootSegment(struct String input) {
	if(input.startsWith("package ")) return Optional.of("");
	/* String stripped */ = input.strip();/* 
        if (stripped.startsWith("import ")) {
            String right = stripped.substring("import ".length());
            if (right.endsWith(";")) {
                String content = right.substring(0, right.length() - ";".length());
                String joined = String.join("/", content.split(Pattern.quote(".")));
                imports.add("#include \"./" + joined + "\"\n");
                return Optional.of("");
            }
        } */
	/* Optional<String> maybeClass */ = compileToStruct(input, /*  "class " */, ArrayList<>());/* 
        if (maybeClass.isPresent()) return maybeClass; */
	return generatePlaceholder(input);
}
Optional<struct String> compileToStruct(struct String input, struct String infix, List<struct String> typeParams) {
	/* int classIndex */ = input.indexOf(infix);
	if(/* classIndex < 0) return Optional */.empty();
	/* String afterKeyword */ = input.substring(/* classIndex + infix */.length());/* 
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart >= 0) {
            String name = afterKeyword.substring(0, contentStart).strip();
            String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
            if (withEnd.endsWith("}")) {
                String inputContent = withEnd.substring(0, withEnd.length() - "}".length());
                return compileStatements(inputContent, input1 -> compileClassMember(input1, typeParams)).map(outputContent -> {
                    structs.add("struct " + name + " {\n" + outputContent + "};\n");
                    return "";
                });
            }
        } */
	return Optional.empty();
}
Optional<struct String> compileClassMember(struct String input, List<struct String> typeParams) {
	return compileWhitespace(/* input) */.or((/* ) -> compileToStruct */(input, /*  "interface " */, /* typeParams)) */.or((/* ) -> compileToStruct */(input, /*  "record " */, /* typeParams)) */.or((/* ) -> compileToStruct */(input, /*  "class " */, typeParams)).or(() -> compileGlobalInitialization(input, typeParams)).or(() -> compileDefinitionStatement(input)).or(() -> compileMethod(input, typeParams)).or(() -> generatePlaceholder(input));
}
Optional<struct String> compileDefinitionStatement(struct String input) {
	/* String stripped */ = input.strip();/* 
        if (stripped.endsWith(";")) {
            String content = stripped.substring(0, stripped.length() - ";".length());
            return compileDefinition(content).map(result -> "\t" + result + ";\n");
        } */
	return Optional.empty();
}
Optional<struct String> compileGlobalInitialization(struct String input, List<struct String> typeParams) {
	return compileInitialization(input, /* typeParams) */.map(generated -> {
            globals.add(generated + ";\n");
            return "";
        });
}
Optional<struct String> compileInitialization(struct String input, List<struct String> typeParams) {
	if(/* !input */.endsWith(";")) return Optional.empty();
	/* String withoutEnd */ = input.substring(/* 0 */, input.length(/* ) - ";" */.length());
	/* int valueSeparator */ = withoutEnd.indexOf(/* "=" */);
	if(/* valueSeparator < 0) return Optional */.empty();
	/* String definition */ = withoutEnd.substring(/* 0 */, /* valueSeparator) */.strip();
	/* String value */ = withoutEnd.substring(/* valueSeparator + "=" */.length(/* ) */).strip();
	return compileDefinition(/* definition) */.flatMap(/* outputDefinition -> {
            return compileValue(value, typeParams */).map(outputValue -> {
                return outputDefinition + " = " + outputValue;
            });
        });
}
Optional<struct String> compileWhitespace(struct String input) {
	if(input.isBlank()) return Optional.of("");
	return Optional.empty();
}
Optional<struct String> compileMethod(struct String input, List<struct String> typeParams) {
	/* int paramStart */ = input.indexOf(/* " */(/* ");
        if  */(/* paramStart < 0) return Optional */.empty(/* );

        String inputDefinition = input */.substring(0, /* paramStart) */.strip(/* );
        String withParams = input */.substring(paramStart + "(".length());

        return compileDefinition(inputDefinition).flatMap(outputDefinition -> {
            int paramEnd = withParams.indexOf(")");
            if (paramEnd < 0) return Optional.empty();

            String params = withParams.substring(0, /*  paramEnd);
            return compileValues(params */, /*  definition -> {
                return compileWhitespace */(/* definition) */.or((/* ) -> compileDefinition */(definition)).or(() -> generatePlaceholder(definition));
            }).flatMap(outputParams -> {
                String header = "\t".repeat(0) + outputDefinition + "(" + outputParams + ")";
                String body = withParams.substring(paramEnd + ")".length()).strip();
                if (body.startsWith("{") && body.endsWith("}")) {
                    String inputContent = body.substring("{".length(), body.length() - "}".length());
                    return compileStatements(inputContent, input1 -> compileStatementOrBlock(input1, typeParams)).flatMap(outputContent -> {
                        methods.add(header + " {" + outputContent + "\n}\n");
                        return Optional.of("");
                    });
                }

                return Optional.of(header + ";");
            });
        });
}
Optional<struct String> compileValues(struct String input, Function<struct String, Optional<struct String>> compiler) {
	/* List<String> divided */ = divide(input, /*  Main::divideValueChar */);
	return compileValues(divided, compiler);
}
struct State divideValueChar(struct State state, char c) {
	/* if (c */ = /* = ',' && state */.isLevel(/* )) return state */.advance();
	/* State appended */ = state.append(c);
	/* if (c */ = /* = '<') return appended */.enter();
	/* if (c */ = /* = '>') return appended */.exit();
	return appended;
}
Optional<struct String> compileValues(List<struct String> params, Function<struct String, Optional<struct String>> compoiler) {
	return compileAndMerge(params, compoiler, /*  Main::mergeValues */);
}
Optional<struct String> compileStatementOrBlock(struct String input, List<struct String> typeParams) {
	return compileWhitespace(/* input) */.or((/* ) -> compileStatement */(input, /* typeParams) */.map(/* result -> "\n\t" + result + ";")) */.or(() -> compileInitialization(input, typeParams).map(value -> "\n\t" + value + ";")).or(() -> generatePlaceholder(input));
}
Optional<struct String> compileStatement(struct String input, List<struct String> typeParams) {
	/* String stripped */ = input.strip();/* 
        if (stripped.endsWith(";")) {
            String withoutEnd = stripped.substring(0, stripped.length() - ";".length());
            if (withoutEnd.startsWith("return ")) {
                return compileValue(withoutEnd.substring("return ".length()), typeParams).map(result -> "return " + result);
            }

            int valueSeparator = withoutEnd.indexOf("=");
            if (valueSeparator >= 0) {
                String destination = withoutEnd.substring(0, valueSeparator).strip();
                String source = withoutEnd.substring(valueSeparator + "=".length()).strip();
                return compileValue(destination, typeParams).flatMap(newDest -> {
                    return compileValue(source, typeParams).map(newSource -> {
                        return newDest + " = " + newSource;
                    });
                });
            }

            Optional<String> maybeInvocation = compileInvocation(withoutEnd, typeParams);
            if (maybeInvocation.isPresent()) return maybeInvocation;
        } */
	return Optional.empty();
}
Optional<struct String> compileValue(struct String input, List<struct String> typeParams) {
	/* String stripped */ = input.strip();/* 
        if (stripped.startsWith("new ")) {
            String slice = stripped.substring("new ".length());
            int argsStart = slice.indexOf("(");
            if (argsStart >= 0) {
                String type = slice.substring(0, argsStart);
                String withEnd = slice.substring(argsStart + "(".length()).strip();
                if (withEnd.endsWith(")")) {
                    String argsString = withEnd.substring(0, withEnd.length() - ")".length());
                    return compileType(type, typeParams).flatMap(outputType -> {
                        return compileArgs(argsString, typeParams).map(value -> outputType + value);
                    });
                }
            }
        } */
	/* Optional<String> invocation */ = compileInvocation(input, typeParams);/* 
        if (invocation.isPresent()) return invocation; */
	/* int separator */ = input.lastIndexOf(/* " */.");/* 
        if (separator >= 0) {
            String object = input.substring(0, separator).strip();
            String property = input.substring(separator + ".".length()).strip();
            return compileValue(object, typeParams).map(compiled -> compiled + "." + property);
        } *//* 

        if (isSymbol(stripped)) {
            return Optional.of(stripped);
        } */
	return generatePlaceholder(input);
}
Optional<struct String> compileInvocation(struct String input, List<struct String> typeParams) {/* 
        int argsStart = input.indexOf("(");
        if (argsStart >= 0) {
            String type = input.substring(0, argsStart);
            String withEnd = input.substring(argsStart + "(".length()).strip();
            if (withEnd.endsWith(")")) {
                String argsString = withEnd.substring(0, withEnd.length() - ")".length());
                return compileValue(type, typeParams).flatMap(caller -> {
                    return compileArgs(argsString, typeParams).map(value -> caller + value);
                });
            }
        } */
	return Optional.empty();
}
Optional<struct String> compileArgs(struct String argsString, List<struct String> typeParams) {
	return compileValues(argsString, /* arg -> {
            return compileWhitespace */(/* arg) */.or(() -> compileValue(arg, typeParams));
        }).map(args -> {
            return "(" + args + ")";
        });
}
struct StringBuilder mergeValues(struct StringBuilder cache, struct String element) {
	if(cache.isEmpty()) return cache.append(element);
	return cache.append(/* " */, /* ") */.append(element);
}
Optional<struct String> compileDefinition(struct String definition) {
	/* int nameSeparator */ = definition.lastIndexOf(/* " " */);/* 
        if (nameSeparator >= 0) {
            String beforeName = definition.substring(0, nameSeparator).strip();
            String name = definition.substring(nameSeparator + " ".length()).strip();

            int typeSeparator = -1;
            int depth = 0;
            for (int i = beforeName.length() - 1; i >= 0; i--) {
                char c = beforeName.charAt(i);
                if (c == ' ' && depth == 0) {
                    typeSeparator = i;
                    break;
                } else {
                    if (c == '>') depth++;
                    if (c == '<') depth--;
                }
            }

            if (typeSeparator >= 0) {
                String beforeType = beforeName.substring(0, typeSeparator).strip();

                List<String> typeParams;
                if (beforeType.endsWith(">")) {
                    String withoutEnd = beforeType.substring(0, beforeType.length() - ">".length());
                    int typeParamStart = withoutEnd.indexOf("<");
                    if (typeParamStart >= 0) {
                        String substring = withoutEnd.substring(typeParamStart + 1);
                        typeParams = splitValues(substring);
                    } else {
                        typeParams = Collections.emptyList();
                    }
                } else {
                    typeParams = Collections.emptyList();
                }

                String inputType = beforeName.substring(typeSeparator + " ".length());
                return compileType(inputType, typeParams).flatMap(outputType -> Optional.of(generateDefinition(typeParams, outputType, name)));
            } else {
                return compileType(beforeName, Collections.emptyList()).flatMap(outputType -> Optional.of(generateDefinition(Collections.emptyList(), outputType, name)));
            }
        } */
	return Optional.empty();
}
List<struct String> splitValues(struct String substring) {
	/* String[] paramsArrays */ = substring.strip(/* ) */.split(Pattern.quote(", /* ") */);
	return Arrays.stream(/* paramsArrays) */.map(/* String::strip) */.filter(/* param -> !param */.isEmpty()).toList();
}
struct String generateDefinition(List<struct String> maybeTypeParams, struct String type, struct String name) {/* 
        String typeParamsString; *//* 
        if (maybeTypeParams.isEmpty()) {
            typeParamsString = "";
        } *//*  else {
            typeParamsString = "<" + String.join(", ", maybeTypeParams) + "> ";
        } */
	return /* typeParamsString + type + " " + name */;
}
Optional<struct String> compileType(struct String input, List<struct String> typeParams) {
	if(input.equals("void")) return Optional.of("void");/* 

        if (input.equals("int") || input.equals("Integer") || input.equals("boolean") || input.equals("Boolean")) {
            return Optional.of("int");
        } *//* 

        if (input.equals("char") || input.equals("Character")) {
            return Optional.of("char");
        } *//* 

        if (input.endsWith("[]")) {
            return compileType(input.substring(0, input.length() - "[]".length()), typeParams)
                    .map(value -> value + "*");
        } */
	/* String stripped */ = input.strip();/* 
        if (isSymbol(stripped)) {
            if (typeParams.contains(stripped)) {
                return Optional.of(stripped);
            } else {
                return Optional.of("struct " + stripped);
            }
        } *//* 

        if (stripped.endsWith(">")) {
            String slice = stripped.substring(0, stripped.length() - ">".length());
            int argsStart = slice.indexOf("<");
            if (argsStart >= 0) {
                String base = slice.substring(0, argsStart).strip();
                String params = slice.substring(argsStart + "<".length()).strip();
                return compileValues(params, type -> {
                    return compileWhitespace(type).or(() -> compileType(type, typeParams));
                }).map(compiled -> {
                    return base + "<" + compiled + ">";
                });
            }
        } */
	return generatePlaceholder(input);
}
int isSymbol(struct String input) {/* 
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) continue;
            return false;
        } */
	return true;
}
Optional<struct String> generatePlaceholder(struct String input) {
	return Optional.of(/* "/* " + input + " */" */);
}
/* 
 */
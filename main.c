/* package magma; */
/* import java.io.IOException; */
/* import java.nio.file.Files; */
/* import java.nio.file.Path; */
/* import java.nio.file.Paths; */
/* import java.util.ArrayList; */
/* import java.util.List; */
/* import java.util.Optional; */
/* import java.util.function.BiFunction; */
/* import java.util.function.Function; */
struct State {
	/* List<String> */ segments;
	char* buffer;
	int depth;
}
struct Main {
	/* List<String> */ structs;
	/* List<String> */ methods;
}
/* private */ State(/* List<String> */ segments, char* buffer, int depth){/* 
            this.segments = segments; *//* 
            this.buffer = buffer; *//* 
            this.depth = depth; *//* 
         */}/* public */ State(/*  */){/* 
            this(new ArrayList<>(), "", 0); *//* 
         */}/* boolean */ isLevel(/*  */){/* 
            return this.depth == 0; *//* 
         */}/* boolean */ isShallow(/*  */){/* 
            return this.depth == 1; *//* 
         */}void advance(/*  */){/* 
            this.segments.add(this.buffer); *//* 
            this.buffer = ""; *//* 
         */}void exit(/*  */){/* 
            this.depth = this.depth - 1; *//* 
         */}void enter(/*  */){/* 
            this.depth = this.depth + 1; *//* 
         */}void append(/* char */ c){/* 
            this.buffer = this.buffer + c; *//* 
         */}void __main__(/* String[] */ args){/* 
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
     */}char* compile(char* input){/* 
        String output = compileAllStatements(input, Main::compileRootSegment); *//* 
        String joinedStructs = String.join("", structs); *//* 
        String joinedMethods = String.join("", methods); *//* 
        return output + joinedStructs + joinedMethods; *//* 
     */}char* compileAllStatements(char* input, /*  Function<String */, /* String> */ compileRootSegment){/* 
        return compileAll(input, Main::foldStatementChar, compileRootSegment, Main::mergeStatements); *//* 
     */}char* compileAll(char* input, /*  BiFunction<State */, /*  Character */, /* State> */ folder, /*  Function<String */, /* String> */ compileRootSegment, /*  BiFunction<String */, /*  String */, /* String> */ merger){/* 
        State state = new State(); *//* 
        for (int i = 0; *//*  i < input.length(); *//*  i++) {
            char c = input.charAt(i);
            if (c == '\'') {
                state.append(c);

                i++;
                char c1 = input.charAt(i);
                state.append(c1);

                if (c1 == '\\') {
                    i++;
                    state.append(input.charAt(i));
                }

                i++;
                state.append(input.charAt(i));

                continue;
            }

            state = folder.apply(state, c);
        } *//* 
        state.advance(); *//* 

        List<String> segments = state.segments; *//* 
        String output = ""; *//* 
        for (String segment : segments) {
            String compiled = compileRootSegment.apply(segment);
            output = merger.apply(output, compiled);
        } *//* 

        return output; *//* 
     */}char* mergeStatements(char* output, char* compiled){/* 
        return output + compiled; *//* 
     */}/* State */ foldStatementChar(/* State */ state, /* char */ c){/* 
        state.append(c); *//* 
        if (c == ';' && state.isLevel()) {
            state.advance();
        } *//* 
        else if (c == '}' && state.isShallow()) {
            state.advance();
            state.exit();
        } *//* 
        else {
            if (c == '{') {
                state.enter();
            }
            if (c == '}') {
                state.exit();
            }
        } *//* 
        return state; *//* 
     */}char* compileRootSegment(char* input){/* 
        if (input.isBlank()) {
            return "";
        } *//* 

        return compileClass(input).orElseGet(() -> generatePlaceholder(input.strip()) + "\n"); *//* 

     */}/* Optional<String> */ compileClass(char* input){/* 
        int classIndex = input.indexOf("class "); *//* 
        if (classIndex < 0) {
            return Optional.empty();
        } *//* 
        String afterKeyword = input.substring(classIndex + "class ".length()); *//* 
        int contentStart = afterKeyword.indexOf("{");
        if (contentStart < 0) {
            return Optional.empty();
        }
        String name = afterKeyword.substring(0, contentStart).strip();
        String withEnd = afterKeyword.substring(contentStart + "{".length()).strip();
        if (!withEnd.endsWith("}")) {
            return Optional.empty();
        }
        String inputContent = withEnd.substring(0, withEnd.length() - "} *//* ".length()); *//* 
        String outputContent = compileAllStatements(inputContent, Main::compileClassSegment); *//* 
        if (!isSymbol(name)) {
            return Optional.empty();
        } *//* 
        String generated = "struct " + name + " {" + outputContent + "\n} *//* \n"; *//* 
        structs.add(generated); *//* 
        return Optional.of(""); *//* 
     */}char* compileClassSegment(char* input){/* 
        return compileWhitespace(input)
                .or(() -> compileClass(input))
                .or(() -> compileMethod(input))
                .or(() -> compileInitialization(input))
                .or(() -> compileDefinitionStatement(input))
                .orElseGet(() -> generatePlaceholder(input)); *//* 
     */}/* Optional<String> */ compileInitialization(char* input){/* 
        int valueSeparator = input.indexOf("="); *//* 
        if (valueSeparator >= 0) {
            return compileDefinition(input.substring(0, valueSeparator))
                    .map(value -> "\n\t" + value + ";");
        } *//* 
        else {
            return Optional.empty();
        } *//* 
     */}/* Optional<String> */ compileWhitespace(char* input){/* 
        if (input.isBlank()) {
            return Optional.of("");
        } *//* 
        return Optional.empty(); *//* 
     */}/* Optional<String> */ compileDefinitionStatement(char* input){/* 
        String stripped = input.strip(); *//* 
        if (stripped.endsWith("; *//* ")) {
            String slice = stripped.substring(0, stripped.length() - ";".length());
            return compileDefinition(slice).map(inner -> "\n\t" + inner + ";");
        } *//* 
        else {
            return Optional.empty();
        } *//* 
     */}/* Optional<String> */ compileMethod(char* input){/* 
        int paramStart = input.indexOf("("); *//* 
        if (paramStart < 0) {
            return Optional.empty();
        } *//* 

        String definition = input.substring(0, paramStart).strip(); *//* 
        return compileDefinition(definition).flatMap(outputDefinition -> {
            String withParams = input.substring(paramStart + "(".length());
            int paramEnd = withParams.indexOf(")");
            if (paramEnd >= 0) {
                String inputParams = withParams.substring(0, paramEnd).strip();
                String outputParams = compileAll(inputParams, Main::foldValueChar, param -> compileDefinition(param).orElseGet(() -> generatePlaceholder(param)), Main::mergeValues);

                String withBraces = withParams.substring(paramEnd + ")".length()).strip();
                if (withBraces.startsWith("{") && withBraces.endsWith("}")) {
                    String content = withBraces.substring(1, withBraces.length() - 1);
                    String outputContent = compileAllStatements(content, Main::compileStatementOrBlock);
                    String generated = outputDefinition + "(" + outputParams + "){" + outputContent + "}";
                    methods.add(generated);
                    return Optional.of("");
                }
            }
            return Optional.empty();
        } *//* ); *//* 
     */}/* State */ foldValueChar(/* State */ state, /* char */ c){/* 
        if (c == ',') {
            state.advance();
        } *//* 
        else {
            state.append(c);
        } *//* 

        return state; *//* 
     */}char* mergeValues(char* cache, char* element){/* 
        if (cache.isEmpty()) {
            return element;
        } *//* 
        return cache + ", " + element; *//* 
     */}/* Optional<String> */ compileDefinition(char* input){/* 
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
     */}char* compileType(char* type){/* 
        String stripped = type.strip(); *//* 
        if (stripped.equals("void")) {
            return "void";
        } *//* 
        if (stripped.equals("String")) {
            return "char*";
        } *//* 
        if (stripped.equals("int")) {
            return "int";
        } *//* 

        return generatePlaceholder(stripped); *//* 
     */}char* compileStatementOrBlock(char* input){/* 
        return generatePlaceholder(input); *//* 
     */}/* boolean */ isSymbol(char* input){/* 
        for (int i = 0; *//*  i < input.length(); *//*  i++) {
            char c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        } *//* 
        return true; *//* 
     */}char* generatePlaceholder(char* input){/* 
        return "/* " + input + " */"; *//* 
     */}
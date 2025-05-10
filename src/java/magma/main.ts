/* public  */class Main {
	/* private  */interface Iterator<T> {
		/* <R> */ fold(/* R initial, BiFunction<R, T, R> folder */) : /* R */;
		/* <R> */ map(/* Function<T, R> mapper */) : /* Iterator<R> */;/* 
     */}
	/* 

    private  */interface List<T> {
		/* List<T> add */;
		/* 

        Iterator<T> iterate */;/* 
     */}
	/* 

    private  */interface Head<T> {
		/* Optional<T> next */;/* 
     */}
	/* private */ HeadedIterator<T>(/* Head<T> head */) : /* record *//* implements Iterator<T> {
        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            var current = initial;
            while (true) {
                R finalCurrent = current;
                var optional = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (optional.isPresent()) {
                    current = optional.get();
                }
                else {
                    return current;
                }
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }
    } */
	/* 

    private static  */class RangeHead implements Head<Integer> {
		/* private final */ length : /* int */;
		/* private */ counter : /* int */;
		/* 

        public RangeHead *//* {
            this.length = length;
        } */
		/* @Override
        public */ next(/*  */) : /* Optional<Integer> *//* {
            if (this.counter < this.length) {
                var value = this.counter;
                this.counter++;
                return Optional.of(value);
            }

            return Optional.empty();
        } *//* 
     */}
	/* 


    private static  */class Lists {
		/* private */ JVMList<T>(/* java.util.List<T> elements */) : /* record *//* implements List<T> {


            public JVMList() {
                this(new ArrayList<>());
            }

            @Override
            public List<T> add(T element) {
                this.elements.add(element);
                return this;
            }

            @Override
            public Iterator<T> iterate() {
                return new HeadedIterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
            }
        } */
		/* public static <T> */ empty(/*  */) : /* List<T> *//* {
            return new JVMList<>();
        } *//* 
     */}
	/* 

    private static  */class State {
		/* private */ segments : /* List<String> */;
		/* private */ buffer : /* StringBuilder */;
		/* private */ depth : /* int */;
		/* 

        public State *//* {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
		/* 

        public State *//* {
            this(Lists.empty(), new StringBuilder(), 0);
        } */
		/* private */ advance(/*  */) : /* State *//* {
            this.segments = this.segments.add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } */
		/* private */ append(/* char c */) : /* State *//* {
            this.buffer.append(c);
            return this;
        } */
		/* public */ enter(/*  */) : /* State *//* {
            this.depth++;
            return this;
        } */
		/* public */ isLevel(/*  */) : /* boolean *//* {
            return this.depth == 0;
        } */
		/* public */ exit(/*  */) : /* State *//* {
            this.depth--;
            return this;
        } */
		/* public */ isShallow(/*  */) : /* boolean *//* {
            return this.depth == 1;
        } *//* 
     */}
	/* private */ Definition(/* String beforeType, String type, String name */) : /* record *//* {
        private String generate() {
            return this.generateWithParams("");
        }

        public String generateWithParams(String params) {
            return generatePlaceholder(this.beforeType) + " " + this.name + params + " : " + this.type;
        }
    } */
	/* public static */ main(/*  */) : /* void *//* {
        try {
            var parent = Paths.get(".", "src", "java", "magma");
            var source = parent.resolve("Main.java");
            var target = parent.resolve("main.ts");

            var input = Files.readString(source);
            Files.writeString(target, compile(input));

            new ProcessBuilder("cmd", "/c", "npm", "exec", "tsc")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    } */
	/* private static */ compile(/* String input */) : /* String *//* {
        return compileStatements(input, Main::compileRootSegment);
    } */
	/* private static */ compileStatements(/* String input, Function<String, String> mapper */) : /* String *//* {
        return divide(input)
                .iterate()
                .map(mapper)
                .fold(new StringBuilder(), StringBuilder::append)
                .toString();
    } */
	/* private static */ divide(/* String input */) : /* List<String> *//* {
        var current = new State();
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            current = fold(current, c);
        }

        return current.advance().segments;
    } */
	/* private static */ fold(/* State state, char c */) : /* State *//* {
        var append = state.append(c);
        if (c == ';' && append.isLevel()) {
            return append.advance();
        }
        if (c == '} */
	/* ' */ append.isShallow(/*  */) : /* && *//* ) {
            return append.advance().exit();
        } */
	/* 
        if  *//* {
            return append.enter();
        }
        if (c == '} */
	/* ') {
            */ append.exit(/*  */) : /* return *//* ;
        } *//* 
        return append; *//* 
     */
}
/* private static String compileRootSegment(String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return "";
        }

        return compileClass(stripped, 0).orElseGet(() -> generatePlaceholder(stripped));
    } *//* private static Optional<String> compileClass(String stripped, int depth) {
        return compileStructure(stripped, depth, "class ");
    } *//* private static Optional<String> compileStructure(String stripped, int depth, String infix) {
        return first(stripped, infix, (left, right) -> {
            return first(right, "{", (name, withEnd) -> {
                var strippedWithEnd = withEnd.strip();
                return compileSuffix(strippedWithEnd, "}", content1 -> {
                    var strippedName = name.strip();

                    var beforeIndent = depth == 0 ? "" : "\n\t";
                    var afterIndent = depth == 0 ? "\n" : "";

                    var statements = compileStatements(content1, input -> compileClassSegment(input, depth + 1));
                    return Optional.of(beforeIndent + generatePlaceholder(left) + infix + strippedName + " {" + statements + afterIndent + "}" + afterIndent);
                });
            });
        });
    } *//* private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } *//* private static Optional<String> compileSuffix(String input, String suffix, Function<String, Optional<String>> mapper) {
        if (!input.endsWith(suffix)) {
            return Optional.empty();
        }

        var slice = input.substring(0, input.length() - suffix.length());
        return mapper.apply(slice);
    } *//* private static String compileClassSegment(String input, int depth) {
        return compileClass(input, depth)
                .or(() -> compileStructure(input, depth, "interface "))
                .or(() -> compileMethod(input, depth))
                .or(() -> compileDefinitionStatement(input, depth))
                .orElseGet(() -> generatePlaceholder(input));
    } *//* private static @NotNull Optional<? extends String> compileMethod(String input, int depth) {
        return first(input, "(", (definition, withParams) -> {
            return first(withParams, ")", (params, rawContent) -> {
                var content = rawContent.strip();
                var newContent = content.equals(";") ? ";" : generatePlaceholder(content);

                return Optional.of(createIndent(depth) + parseDefinition(definition)
                        .map(definition1 -> definition1.generateWithParams("(" + generatePlaceholder(params) + ")"))
                        .orElseGet(() -> generatePlaceholder(definition)) + newContent);
            });
        });
    } *//* private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    } *//* private static Optional<String> compileDefinitionStatement(String input, int depth) {
        return compileSuffix(input.strip(), ";", withoutEnd -> {
            return parseDefinition(withoutEnd).map(result -> createIndent(depth) + result.generate() + ";");
        });
    } *//* private static Optional<Definition> parseDefinition(String input) {
        return compileLast(input.strip(), " ", (beforeName, name) -> {
            return compileLast(beforeName, " ", (beforeType, type) -> {
                return Optional.of(new Definition(beforeType, compileType(type), name.strip()));
            });
        });
    } *//* private static String compileType(String type) {
        return generatePlaceholder(type);
    } *//* private static <T> Optional<T> compileLast(String input, String infix, BiFunction<String, String, Optional<T>> mapper) {
        return compileInfix(input, infix, Main::findLast, mapper);
    } *//* private static Optional<Integer> findLast(String input, String infix) {
        var index = input.lastIndexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } *//* private static Optional<String> first(String input, String infix, BiFunction<String, String, Optional<String>> mapper) {
        return compileInfix(input, infix, Main::findFirst, mapper);
    } *//* private static <T> Optional<T> compileInfix(
            String input,
            String infix,
            BiFunction<String, String, Optional<Integer>> locator,
            BiFunction<String, String, Optional<T>> mapper
    ) {
        return locator.apply(input, infix).flatMap(index -> {
            var left = input.substring(0, index);
            var right = input.substring(index + infix.length());
            return mapper.apply(left, right);
        });
    } *//* private static Optional<Integer> findFirst(String input, String infix) {
        var index = input.indexOf(infix);
        return index == -1 ? Optional.empty() : Optional.of(index);
    } *//* private static String generatePlaceholder(String input) {
        var replaced = input
                .replace("content-start", "content-start")
                .replace("content-end", "content-end");

        return "content-start " + replaced + " content-end";
    } *//* } */
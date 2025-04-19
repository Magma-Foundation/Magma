// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// Tuple</* CompileState */, /*  String */>
// Map</* String */, Option<Tuple</* CompileState */, /*  String */>>(*)(List</* Node */>)>
// Map</* String */, /*  String */>
// Map</* String */, List</* Node */>>
// Tuple</* CompileState */, List</* String */>>
// Tuple</* T */, List</* T */>>
/* private static */ struct DivideState {
	/* private final */ List</* String */> segments;
	/* private */ /* StringBuilder */ buffer;
	/* private */ int depth;
	/* private */ DivideState(List</* String */> segments, /* StringBuilder */ buffer, int depth)/*  {
            this.segments = segments;
            this.buffer = buffer;
            this.depth = depth;
        } */
	/* public */ DivideState()/*  {
            this(Lists.empty(), new StringBuilder(), 0);
        } */
	/* private */ /* DivideState */ exit()/*  {
            this.depth = this.depth - 1;
            return this;
        } */
	/* private */ /* DivideState */ enter()/*  {
            this.depth = this.depth + 1;
            return this;
        } */
	/* private */ /* DivideState */ advance()/*  {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        } */
	/* private */ /* boolean */ isShallow()/*  {
            return this.depth == 1;
        } */
	/* private */ /* DivideState */ append(/* char */ c)/*  {
            this.buffer.append(c);
            return this;
        } */
	/* private */ /* boolean */ isLevel()/*  {
            return this.depth == 0;
        } */
	/* public */ List</* String */> segments()/*  {
            return this.segments;
        } */
};
/* public */ struct Main {
	/* record */ Some<T>(/* T */ value)/*  implements Option<T> {
        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public T orElseGet(Supplier<T> other) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }
    } */
	/* record Tuple<A, */ B>(/* A */ left, /* B */ right)/*  {
    } */
	/* record */ CompileState(List</* String */> imports, List</* String */> structs, List</* Node */> expansions, Map</* String */, Option<Tuple</* CompileState */, /*  String */>>(*)(List</* Node */>)> expandables)/*  {
        public CompileState() {
            this(Lists.empty(), Lists.empty(), Lists.empty(), new HashMap<>());
        }

        public CompileState addStruct(String struct) {
            return new CompileState(this.imports, this.structs.add(struct), this.expansions, this.expandables);
        }

        public CompileState addImport(String imports) {
            return new CompileState(this.imports.add(imports), this.structs, this.expansions, this.expandables);
        }

        public CompileState addGeneric(Node node) {
            if (!this.expansions.contains(node)) {
                return new CompileState(this.imports, this.structs, this.expansions.add(node), this.expandables);
            }
            
            return this;
        }

        public CompileState addExpandable(String name, Function<List<Node>, Option<Tuple<CompileState, String>>> mapper) {
            Map<String, Function<List<Node>, Option<Tuple<CompileState, String>>>> copy = this.expandables;
            copy.put(name, mapper);
            return new CompileState(this.imports, this.structs, this.expansions, copy);
        }

        public Option<Tuple<Node, CompileState>> popExpansion() {
            return this.expansions.removeFirst().map(tuple -> {
                return new Tuple<>(tuple.left, new CompileState(this.imports, this.structs, tuple.right, this.expandables));
            });
        }
    } */
	/* private */ /* record */ Joiner(/* String */ delimiter)/*  implements Collector<String, Option<String>> {
        private Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> current, String element) {
            return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    } */
	/* public */ /* record */ HeadedIterator<T>(Head</* T */> head)/*  implements Iterator<T> {
        @Override
        public <C> C collect(Collector<T, C> collector) {
            return this.fold(collector.createInitial(), collector::fold);
        }

        @Override
        public <R> R fold(R initial, BiFunction<R, T, R> folder) {
            R current = initial;
            while (true) {
                R finalCurrent = current;
                Option<R> option = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (option.isEmpty()) {
                    return current;
                }
                current = option.orElse(null);
            }
        }

        @Override
        public <R> Iterator<R> map(Function<T, R> mapper) {
            return new HeadedIterator<>(() -> this.head.next().map(mapper));
        }
    } */
	/* public */ /* record */ Node(Option</* String */> type, Map</* String */, /*  String */> strings, Map</* String */, List</* Node */>> nodeLists)/*  {
        public Node() {
            this(new None<String>(), new HashMap<>(), new HashMap<>());
        }

        public Node(String type) {
            this(new Some<String>(type), new HashMap<>(), new HashMap<>());
        }

        public Node withString(String propertyKey, String propertyValue) {
            this.strings.put(propertyKey, propertyValue);
            return this;
        }

        public Node withNodeList(String propertyKey, List<Node> propertyValues) {
            this.nodeLists.put(propertyKey, propertyValues);
            return this;
        }

        public Option<List<Node>> findNodeList(String propertyKey) {
            if (this.nodeLists.containsKey(propertyKey)) {
                return new Some<>(this.nodeLists.get(propertyKey));
            }
            return new None<>();
        }

        public Option<String> findString(String propertyKey) {
            if (this.strings.containsKey(propertyKey)) {
                return new Some<>(this.strings.get(propertyKey));
            }
            else {
                return new None<>();
            }
        }

        public boolean is(String type) {
            return this.type.isPresent() && this.type.orElse(null).equals(type);
        }
    } */
	/* public static */ /* void */ main(/* String[] */ args)/*  {
        try {
            Path path = Paths.get(".", "src", "java", "magma", "Main.java");
            String input = Files.readString(path);

            Path target = path.resolveSibling("main.c");
            Files.writeString(target, compile(input));
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
    } */
	/* private static */ /* String */ compile(/* String */ input)/*  {
        CompileState oldState = new CompileState();
        Tuple<CompileState, String> output = compileStatements(oldState, input, Main::compileRootSegment);

        CompileState currentState = output.left;
        StringBuilder buffered = new StringBuilder();
        List<Node> visited = Lists.empty();

        while (!currentState.expansions.isEmpty()) {
            Option<Tuple<Node, CompileState>> option = currentState.popExpansion();
            if (option.isEmpty()) {
                break;
            }

            Tuple<Node, CompileState> entry = option.orElse(null);
            Node expansion = entry.left;

            CompileState withoutExpansion = entry.right;
            currentState = withoutExpansion;
            if (!visited.contains(expansion)) {
                System.out.println(generateType(expansion));
                Tuple<CompileState, String> tuple = expand(expansion, withoutExpansion);
                currentState = tuple.left;
                buffered.append(tuple.right);
                visited = visited.add(expansion);
            }
        }

        CompileState newState1 = currentState;
        String joinedImports = join(newState1.imports);
        String joinedStructs = join(newState1.structs);
        String joinedGenerics = newState1.expansions.iter()
                .map(Main::generateGenericType)
                .map(generic -> "// " + generic + "\n")
                .collect(new Joiner())
                .orElse("");

        return joinedImports + joinedGenerics + buffered + joinedStructs + buffered;
    } */
	/* private static */ Tuple</* CompileState */, /*  String */> expand(/* Node */ expansion, /* CompileState */ state)/*  {
        String base = expansion.findString("base").orElse("");
        List<Node> arguments = expansion.findNodeList("arguments").orElse(Lists.empty());

        Map<String, Function<List<Node>, Option<Tuple<CompileState, String>>>> expandables = state.expandables;
        if (expandables.containsKey(base)) {
            return expandables.get(base).apply(arguments).orElse(new Tuple<>(state, ""));
        }
        else {
            return new Tuple<>(state, "// " + generateGenericType(expansion) + "\n");
        }
    } */
	/* private static */ /* String */ join(List</* String */> list)/*  {
        return list.iter()
                .collect(new Joiner())
                .orElse("");
    } */
	/* private static */ Tuple</* CompileState */, /*  String */> compileStatements(/* CompileState */ state, /* String */ input, Tuple</* CompileState */, /*  String */>(*compiler)(/* CompileState */, /*  String */))/*  {
        return compileAll(state, input, Main::foldStatementChar, compiler, Main::mergeStatements);
    } */
	/* private static */ Tuple</* CompileState */, /*  String */> compileAll(/* CompileState */ state, /* String */ input, /*  DivideState */(*divider)(/* DivideState */, /*  Character */), Tuple</* CompileState */, /*  String */>(*compiler)(/* CompileState */, /*  String */), /*  StringBuilder */(*merger)(/* StringBuilder */, /*  String */))/*  {
        Tuple<CompileState, List<String>> compiled = parseAll(state, input, divider, compiler);
        return new Tuple<>(compiled.left, mergeAll(merger, compiled));
    } */
	/* private static */ Tuple</* CompileState */, List</* String */>> parseAll(/* CompileState */ state, /* String */ input, /*  DivideState */(*divider)(/* DivideState */, /*  Character */), Tuple</* CompileState */, /*  String */>(*compiler)(/* CompileState */, /*  String */))/*  {
        return divide(input, divider).iter().fold(new Tuple<CompileState, List<String>>(state, Lists.empty()),
                (current, element) -> parseElement(compiler, current, element));
    } */
	/* private static */ Tuple</* CompileState */, List</* String */>> parseElement(Tuple</* CompileState */, /*  String */>(*compiler)(/* CompileState */, /*  String */), Tuple</* CompileState */, List</* String */>> current, /* String */ element)/*  {
        CompileState currentState = current.left;
        List<String> currentCache = current.right;

        Tuple<CompileState, String> compiledTuple = compiler.apply(currentState, element);
        CompileState newState = compiledTuple.left;
        String compiled = compiledTuple.right;

        return new Tuple<>(newState, currentCache.add(compiled));
    } */
	/* private static */ /* String */ mergeAll(/*  StringBuilder */(*merger)(/* StringBuilder */, /*  String */), Tuple</* CompileState */, List</* String */>> fold)/*  {
        return fold.right.iter().fold(new StringBuilder(), merger).toString();
    } */
	/* private static */ /* StringBuilder */ mergeStatements(/* StringBuilder */ current, /* String */ statement)/*  {
        return current.append(statement);
    } */
	/* private static */ List</* String */> divide(/* String */ input, /*  DivideState */(*folder)(/* DivideState */, /*  Character */))/*  {
        DivideState current = new DivideState();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            current = folder.apply(current, c);
        }

        return current.advance().segments;
    } */
	/* private static */ /* DivideState */ foldStatementChar(/* DivideState */ state, /* char */ c)/*  {
        DivideState appended = state.append(c);
        if (c == ';' && appended.isLevel()) {
            return appended.advance();
        }
        if (c == '} */
	/* ' */ /* && */ appended.isShallow()/* ) {
            return appended.advance().exit();
        } *//* 
        if (c == '{') {
            return appended.enter();
        }
        if (c == '} */
	/* ') { */ /* return */ appended.exit()/* ;
        } */
	/* return */ appended;
};
/* public */ struct List {
	Iterator</* T */> iter();
	List</* T */> add(/* T */ element);
	/* T */ get(int index);
	/* boolean */ contains(/* T */ element);
	/* boolean */ isEmpty();
	Option<Tuple</* T */, List</* T */>>> removeFirst();
};
/* public */ struct List {
	Iterator</* T */> iter();
	List</* T */> add(/* T */ element);
	/* T */ get(int index);
	/* boolean */ contains(/* T */ element);
	/* boolean */ isEmpty();
	Option<Tuple</* T */, List</* T */>>> removeFirst();
};
/* public */ struct Option {
	/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
	/* T */ orElse(/* T */ other);
	/* boolean */ isEmpty();
	/* boolean */ isPresent();
	/* T */ orElseGet(/* T */(*other)());
	Option</* T */> or(Option</* T */>(*other)());
	/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
};
/* private */ struct Head {
	Option</* T */> next();
};
/* public */ struct Option {
	/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
	/* T */ orElse(/* T */ other);
	/* boolean */ isEmpty();
	/* boolean */ isPresent();
	/* T */ orElseGet(/* T */(*other)());
	Option</* T */> or(Option</* T */>(*other)());
	/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
};
/* public */ struct Iterator {
	/* <C> */ /* C */ collect(Collector</* T */, /*  C */> collector);
	/* <R> */ /* R */ fold(/* R */ initial, /*  R */(*folder)(/* R */, /*  T */));
	/* <R> */ Iterator</* R */> map(/*  R */(*mapper)(/* T */));
};
/* public */ struct List {
	Iterator</* T */> iter();
	List</* T */> add(/* T */ element);
	/* T */ get(int index);
	/* boolean */ contains(/* T */ element);
	/* boolean */ isEmpty();
	Option<Tuple</* T */, List</* T */>>> removeFirst();
};
/* public */ struct Option {
	/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
	/* T */ orElse(/* T */ other);
	/* boolean */ isEmpty();
	/* boolean */ isPresent();
	/* T */ orElseGet(/* T */(*other)());
	Option</* T */> or(Option</* T */>(*other)());
	/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
};
/* public */ struct Option {
	/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
	/* T */ orElse(/* T */ other);
	/* boolean */ isEmpty();
	/* boolean */ isPresent();
	/* T */ orElseGet(/* T */(*other)());
	Option</* T */> or(Option</* T */>(*other)());
	/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
};
/* public */ struct Option {
	/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
	/* T */ orElse(/* T */ other);
	/* boolean */ isEmpty();
	/* boolean */ isPresent();
	/* T */ orElseGet(/* T */(*other)());
	Option</* T */> or(Option</* T */>(*other)());
	/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
};
/* public */ struct Collector {
	/* C */ createInitial();
	/* C */ fold(/* C */ current, /* T */ element);
};
/* public */ struct Iterator {
	/* <C> */ /* C */ collect(Collector</* T */, /*  C */> collector);
	/* <R> */ /* R */ fold(/* R */ initial, /*  R */(*folder)(/* R */, /*  T */));
	/* <R> */ Iterator</* R */> map(/*  R */(*mapper)(/* T */));
};
// Tuple</* CompileState */, /*  String */>
// Map</* String */, Option<Tuple</* CompileState */, /*  String */>>(*)(List</* Node */>)>
// Map</* String */, /*  String */>
// Map</* String */, List</* Node */>>
// Tuple</* CompileState */, List</* String */>>
// Tuple</* T */, List</* T */>>

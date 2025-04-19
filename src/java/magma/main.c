// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
// #include <temp.h>
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
	/* private */ int isShallow()/*  {
            return this.depth == 1;
        } */
	/* private */ /* DivideState */ append(/* char */ c)/*  {
            this.buffer.append(c);
            return this;
        } */
	/* private */ int isLevel()/*  {
            return this.depth == 0;
        } */
	/* public */ List</* String */> segments()/*  {
            return this.segments;
        } */
};
/* public */ struct Main {
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
        Map<String, Function<Node, Option<Tuple<CompileState, String>>>> expandables = state.expandables;
        if (expandables.containsKey(base)) {
            return expandables.get(base).apply(expansion).orElse(new Tuple<>(state, ""));
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
/* public */ struct List</* String */> {
	Iterator</* T */> iter();
	List</* T */> add(/* T */ element);
	/* T */ get(int index);
	int contains(/* T */ element);
	int isEmpty();
	Option<Tuple</* T */, List</* T */>>> removeFirst();
};
/*  */ struct Tuple</* CompileState */, /*  String */> {
};
/*  */ struct Tuple</* CompileState */, List</* String */>> {
};
/* public */ struct Iterator</* T */> {
	/* <C> */ /* C */ collect(Collector</* T */, /*  C */> collector);
	/* <R> */ /* R */ fold(/* R */ initial, /*  R */(*folder)(/* R */, /*  T */));
	/* <R> */ Iterator</* R */> map(/*  R */(*mapper)(/* T */));
};
/* public */ struct List</* T */> {
	Iterator</* T */> iter();
	List</* T */> add(/* T */ element);
	/* T */ get(int index);
	int contains(/* T */ element);
	int isEmpty();
	Option<Tuple</* T */, List</* T */>>> removeFirst();
};
/*  */ struct Tuple</* T */, List</* T */>> {
};
/* public */ struct Option<Tuple</* T */, List</* T */>>> {
	/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
	/* T */ orElse(/* T */ other);
	int isEmpty();
	int isPresent();
	/* T */ orElseGet(/* T */(*other)());
	Option</* T */> or(Option</* T */>(*other)());
	/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
};
/* public */ struct Collector</* T */, /*  C */> {
	/* C */ createInitial();
	/* C */ fold(/* C */ current, /* T */ element);
};
/* public */ struct Iterator</* R */> {
	/* <C> */ /* C */ collect(Collector</* T */, /*  C */> collector);
	/* <R> */ /* R */ fold(/* R */ initial, /*  R */(*folder)(/* R */, /*  T */));
	/* <R> */ Iterator</* R */> map(/*  R */(*mapper)(/* T */));
};
/* public */ struct Option</* R */> {
	/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
	/* T */ orElse(/* T */ other);
	int isEmpty();
	int isPresent();
	/* T */ orElseGet(/* T */(*other)());
	Option</* T */> or(Option</* T */>(*other)());
	/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
};
/* public */ struct Option</* T */> {
	/* <R> */ Option</* R */> map(/*  R */(*mapper)(/* T */));
	/* T */ orElse(/* T */ other);
	int isEmpty();
	int isPresent();
	/* T */ orElseGet(/* T */(*other)());
	Option</* T */> or(Option</* T */>(*other)());
	/* <R> */ Option</* R */> flatMap(Option</* R */>(*mapper)(/* T */));
};

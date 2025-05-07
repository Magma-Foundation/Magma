package magma;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
public class Main {
	private interface Option<T> {
		Option<R> map<R>(T -> R mapper);
		boolean isPresent();
		T orElse(T other);
		T orElseGet(() -> T other);
		Option<T> or(() -> Option<T> other);
		Option<R> flatMap<R>(T -> Option<R> mapper);
		Option<T> filter(T -> boolean predicate);
	}
	private interface Collector<T, C> {
		C createInitial();
		C fold(C current, T element);
	}
	private interface Iterator<T> {
		Iterator<R> map<R>(T -> R mapper);
		C collect<C>(Collector<T, C> collector);
		C fold<C>(C initial, (C, T) -> C folder);
		boolean anyMatch(T -> boolean predicate);
	}
	private interface List<T> {
		List<T> mapLast(T -> T mapper);
		List<T> add(T element);
		Iterator<T> iterate();
		boolean isEmpty();
		boolean contains(T element);
		int size();
		List<T> subList(int startInclusive, int endExclusive);
		T getLast();
		T get(int index);
		Iterator<T> iterateReverse();
		List<T> addAll(List<T> others);
		List<T> removeLast();
	}
	private interface Head<T> {
		Option<T> next();
	}
	private interface Node {
		String generate();
	}
	private interface StructSegment extends Node {
	}
	private record Some<T>(T value) implements Option<T> {
		@Override
        public Option<R> map<R>(T -> R mapper){
			return new Some<>(/* mapper */.apply(this.value));
		}
		@Override
        public boolean isPresent(){
			return /* true */;
		}
		public T get(){
			return this.value;
		}
		@Override
        public T orElse(T other){
			return this.value;
		}
		@Override
        public T orElseGet(() -> T other){
			return this.value;
		}
		@Override
        public Option<T> or(() -> Option<T> other){
			return this;
		}
		@Override
        public Option<R> flatMap<R>(T -> Option<R> mapper){
			return /* mapper */.apply(this.value);
		}
		@Override
        public Option<T> filter(T -> boolean predicate){
			return /* predicate */.test(this.value) ? this : new None<>();
		}
	}
	private record None<T>() implements Option<T> {
		@Override
        public Option<R> map<R>(T -> R mapper){
			return new None<>(/*  */);
		}
		@Override
        public boolean isPresent(){
			return /* false */;
		}
		@Override
        public T orElse(T other){
			return /* other */;
		}
		@Override
        public T orElseGet(() -> T other){
			return /* other */.get(/*  */);
		}
		@Override
        public Option<T> or(() -> Option<T> other){
			return /* other */.get(/*  */);
		}
		@Override
        public Option<R> flatMap<R>(T -> Option<R> mapper){
			return new None<>(/*  */);
		}
		@Override
        public Option<T> filter(T -> boolean predicate){
			return new None<>(/*  */);
		}
	}
	private static class RangeHead implements Head<Integer> {
        private final int length;
        private int counter = 0; /* public */ RangeHead(int length){
		/* this.length = length */;
		/* }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<>();
            } */
		/* var value = this.counter */;
		/* this.counter++ */;
		return new Some<>(/* value */);
		/* } */
	}
	private record HeadedIterator<T>(Head<T> head) implements Iterator<T> {
		@Override
        public Iterator<R> map<R>(T -> R mapper){
			return new HeadedIterator<>(/*  */(/* ) -> this */.head.next(/*  */).map(mapper));
		}
		@Override
        public C collect<C>(Collector<T, C> collector){
			return this.fold(/* collector */.createInitial(/*  */), /* collector::fold */);
		}
		@Override
        public C fold<C>(C initial, (C, T) -> C folder){
			/* var current = initial */;
			/* while (true) {
                C finalCurrent = current;
                var folded = this.head.next().map(inner -> folder.apply(finalCurrent, inner));
                if (folded.isPresent()) {
                    current = folded.orElse(null);
                }
                else {
                    return current;
                }
            } */
		}
		@Override
        public boolean anyMatch(T -> boolean predicate){
			return this.fold(/* false */, /* (aBoolean */, /* t) -> aBoolean || predicate */.test(/* t */));
		}
	}
	private static class Lists {
		private record MutableList<T>(/* java.util.List */<T> elements) implements List<T> {
			/* public */ MutableList(){
				/* this(new ArrayList<>()) */;
			}
			@Override
            public List<T> add(T element){
				/* this.elements.add(element) */;
				return this;
			}
			@Override
            public Iterator<T> iterate(){
				return new HeadedIterator<>(new /* RangeHead */(this.elements.size(/* ) */)).map(this.elements::get);
			}
			@Override
            public boolean isEmpty(){
				return this.elements.isEmpty(/*  */);
			}
			@Override
            public boolean contains(T element){
				return this.elements.contains(/* element */);
			}
			@Override
            public int size(){
				return this.elements.size(/*  */);
			}
			@Override
            public List<T> subList(int startInclusive, int endExclusive){
				return new MutableList<>(/* new ArrayList<>(this */.elements.subList(startInclusive, /* endExclusive)) */);
			}
			@Override
            public T getLast(){
				return this.elements.getLast(/*  */);
			}
			@Override
            public T get(int index){
				return this.elements.get(/* index */);
			}
			@Override
            public Iterator<T> iterateReverse(){
				return new HeadedIterator<>(new /* RangeHead */(this.elements.size(/* )) */).map(index -> this.elements.size() - index - 1).map(this.elements::get);
			}
			@Override
            public List<T> addAll(List<T> others){
				return /* others */.iterate(/* ) */.<List<T>>fold(this, /* List::add */);
			}
			@Override
            public List<T> removeLast(){
				/* this.elements.removeLast() */;
				return this;
			}
			private List<T> setLast(T element){
				/* this.elements.set(this.elements.size() - 1, element) */;
				return this;
			}
			@Override
            public List<T> mapLast(T -> T mapper){
				/* var oldLast = this.getLast() */;
				/* var newLast = mapper.apply(oldLast) */;
				return this.setLast(/* newLast */);
			}
		}
		public static List<T> empty<T>(){
			return new MutableList<>(/*  */);
		}
		public static List<T> of<T>(/* T... */ elements){
			return new MutableList<>(new /* ArrayList */<>(/* Arrays */.asList(/* elements */)));
		}
	}
	private static class DivideState {
		private List<String> segments;
		private int depth;
		private /* StringBuilder */ buffer;
		/* private */ DivideState(List<String> segments, /* StringBuilder */ buffer, int depth){
			/* this.segments = segments */;
			/* this.buffer = buffer */;
			/* this.depth = depth */;
		}
		/* public */ DivideState(){
			/* this(Lists.empty(), new StringBuilder(), 0) */;
		}
		private DivideState append(/* char */ c){
			/* this.buffer.append(c) */;
			return this;
		}
		private DivideState advance(){
			/* this.segments = this.segments.add(this.buffer.toString()) */;
			/* this.buffer = new StringBuilder() */;
			return this;
		}
		public boolean isLevel(){
			return this.depth == 0;
		}
		public DivideState enter(){
			/* this.depth++ */;
			return this;
		}
		public DivideState exit(){
			/* this.depth-- */;
			return this;
		}
		public boolean isShallow(){
			return this.depth == 1;
		}
	}/* 

    private record Joiner(String delimiter) implements Collector<String, Option<String>> {
        public Joiner() {
            this("");
        }

        @Override
        public Option<String> createInitial() {
            return new None<>();
        }

        @Override
        public Option<String> fold(Option<String> maybeCurrent, String element) {
            return new Some<>(maybeCurrent.map(inner -> inner + this.delimiter + element).orElse(element));
        }
    } */
	private static class ListCollector<T> implements Collector<T, List<T>> {
		@Override
        public List<T> createInitial(){
			return Lists.empty(/*  */);
		}
		@Override
        public List<T> fold(List<T> current, T element){
			return /* current */.add(/* element */);
		}
	}
	private /* record */ Frame(Option<String> maybeName, List<String> typeParams, List /* typeNames */ <String>){
		/* public Frame() {
            this(new None<>(), Lists.empty(), Lists.empty());
        } */
		/* public boolean isDefined(String typeName) {
            return this.isThis(typeName)
                    || this.typeParams.contains(typeName)
                    || this.typeNames.contains(typeName);
        } */
		/* private boolean isThis(String input) {
            return this.maybeName.filter(name -> name.equals(input)).isPresent();
        } */
		/* public Frame withName(String name) {
            return new Frame(new Some<>(name), this.typeParams, this.typeNames);
        } */
		/* public Frame withTypeParams(List<String> typeParams) {
            return new Frame(this.maybeName, this.typeParams.addAll(typeParams), this.typeNames);
        } */
		/* public Frame withDefinition(String definition) {
            return new Frame(this.maybeName, this.typeParams, this.typeNames.add(definition));
        } */
	}
	private static final class CompileState {
		private final List</* Frame */> frames;
		/* public */ CompileState(){
			/* this(Lists.empty()) */;
		}
		/* public */ CompileState(/* Main.List */</* Frame */> frames){
			/* this.frames = frames */;
		}
		private CompileState enter(){
			return new CompileState(this.frames.add(new /* Frame */(/*  */)));
		}
		private CompileState defineThis(String name){
			return new CompileState(this.frames.mapLast(/* last -> last */.withName(/* name */)));
		}
		private boolean isDefined(String input){
			return this.frames.iterateReverse(/* ) */.anyMatch(/* frame -> frame */.isDefined(input));
		}
		public CompileState defineTypeParams(List<String> typeParams){
			return new CompileState(this.frames.mapLast(/* last -> last */.withTypeParams(/* typeParams */)));
		}
		public CompileState exit(){
			return new CompileState(this.frames.removeLast(/*  */));
		}
		public CompileState defineType(String definition){
			return new CompileState(this.frames.mapLast(/* last -> last */.withDefinition(/* definition */)));
		}
	}
	private /* record */ StructurePrototype(String beforeInfix, String infix, String name, List<String> typeParams, Option<String> maybeSuperType, List<String> parameters, List<String> interfaces, String content, int /* depth */ ){
		/* public StructurePrototype() {
            this("", "", "", Lists.empty(), new None<>(), Lists.empty(), Lists.empty(), "", 0);
        } */
		/* private String generate() {
            var typeParamsStrings = this.typeParams().isEmpty() ? "" : "<" + join(", ", this.typeParams()) + ">";
            var paramStrings = this.parameters.isEmpty() ? "" : "(" + join(", ", this.parameters) + ")";
            var extendsString = this.maybeSuperType.map(extendsSlice -> " extends " + extendsSlice).orElse("");
            var interfacesString = this.interfaces.iterate().collect(new Joiner(", ")).map(result -> " implements " + result).orElse("");
            return this.beforeInfix + this.infix + this.name() + typeParamsStrings + paramStrings + extendsString + interfacesString;
        } */
		/* public StructurePrototype withBeforeInfix(String beforeInfix) {
            return new StructurePrototype(beforeInfix, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.content, this.depth);
        } */
		/* public StructurePrototype withInfix(String infix) {
            return new StructurePrototype(this.beforeInfix, infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.content, this.depth);
        } */
		/* public StructurePrototype withTypeParams(List<String> typeParams) {
            return new StructurePrototype(this.beforeInfix, this.infix, this.name, typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.content, this.depth);
        } */
		/* public StructurePrototype withParameters(List<String> parameters) {
            return new StructurePrototype(this.beforeInfix, this.infix, this.name, this.typeParams, this.maybeSuperType, parameters, this.interfaces, this.content, this.depth);
        } */
		/* public StructurePrototype withInterfaces(List<String> interfaces) {
            return new StructurePrototype(this.beforeInfix, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, interfaces, this.content, this.depth);
        } */
		/* public StructurePrototype withContent(String content) {
            return new StructurePrototype(this.beforeInfix, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, content, this.depth);
        } */
		/* public StructurePrototype withDepth(int depth) {
            return new StructurePrototype(this.beforeInfix, this.infix, this.name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.content, depth);
        } */
		/* public StructurePrototype withName(String name) {
            if (name.isEmpty()) {
                return this;
            }
            return new StructurePrototype(this.beforeInfix, this.infix, name, this.typeParams, this.maybeSuperType, this.parameters, this.interfaces, this.content, this.depth);
        } */
		/* public StructurePrototype withSuperType(String type) {
            return new StructurePrototype(this.beforeInfix, this.infix, this.name, this.typeParams, new Some<String>(type), this.parameters, this.interfaces, this.content, this.depth);
        } */
	}
	private record Tuple<A, B>(A left, B right) {
	}
	private static class Whitespace implements StructSegment {
		@Override
        public String generate(){
			return /* "" */;
		}
	}/* 

    private record Structure(StructurePrototype structurePrototype, List<StructSegment> statements,
                             int depth) implements StructSegment {
        @Override
        public String generate() {
            var s1 = this.structurePrototype.generate();

            var joinedStatements = this.statements.iterate()
                    .map(Node::generate)
                    .collect(new Joiner())
                    .orElse("");

            var generated = s1 + " {" + joinedStatements + createIndent(this.depth()) + "}";
            return this.depth == 0 ? generated + "\n" : (createIndent(this.depth()) + generated);
        }
    } */
	private record Content(String input) implements StructSegment {
		@Override
        public String generate(){
			return this.input;
		}
	}
	private record Placeholder(String input) implements /* Node, StructSegment */ {
		@Override
        public String generate(){
			return /* generatePlaceholder */(this.input);
		}
	}
	private record StructureType(/* StructurePrototype */ prototype) {
	}
	public record Statement(int depth, String content) implements StructSegment {
		@Override
        public String generate(){
			return /* createIndent(this */.depth()) + this.content() + ";
			/* " */;
		}
	}
	public static /* void */ main(){
		/* var root = Paths.get(".", "src", "java", "magma") */;
		/* var source = root.resolve("Main.java") */;
		/* var target = root.resolve("main.c") */;
		/* try {
            var input = Files.readString(source);
            Files.writeString(target, compile(input));
        } */
		/* catch (IOException e) {
            e.printStackTrace();
        } */
	}
	private static String compile(String input){
		/* CompileState compileState = new CompileState() */;
		return /* compileStatements */(/* compileState */.enter(/*  */), /* input */, /* Main::compileRootSegment */).right;
	}
	private static Tuple<CompileState, String> compileStatements(CompileState state, String input, (CompileState, String) -> Tuple<CompileState, String> mapper){
		/* var parsed = parseStatements(state, input, mapper) */;
		return new Tuple<>(/* parsed */.left, /* join("" */, /* parsed */.right));
	}
	private static Tuple<CompileState, List<T>> parseStatements<T>(CompileState state, String input, BiFunction /* mapper */ <CompileState, String, Tuple<CompileState, T>>){
		return /* parseAll */(/* state */, /* input */, /* Main::foldStatementValue */, /* mapper */);
	}
	private static String join(String delimiter, List<String> elements){
		return /* elements */.iterate(/* ) */.collect(new /* Joiner */(/* delimiter */)).orElse("");
	}
	private static Tuple<CompileState, List<T>> parseAll<T>(CompileState state, String input, (DivideState, /*  Character */) -> DivideState folder, BiFunction /* mapper */ <CompileState, String, Tuple<CompileState, T>>){
		/* return divide(input, folder).iterate().fold(new Tuple<>(state, Lists.empty()), (tuple, element) -> {
            var currentState = tuple.left;
            var currentElements = tuple.right;

            var newTuple = mapper.apply(currentState, element);
            var newState = newTuple.left;
            var newElement = newTuple.right;
            return new Tuple<>(newState, currentElements.add(newElement));
        } */
		/* ) */;
	}
	private static List<String> divide(String input, (DivideState, /*  Character */) -> DivideState folder){
		/* DivideState state = new DivideState() */;
		/* for (var i = 0 */;
		/* i < input.length() */;
		/* i++) {
            var c = input.charAt(i);
            state = folder.apply(state, c);
        } */
		return /* state */.advance(/*  */).segments;
	}
	private static DivideState foldStatementValue(DivideState state, /* char */ c){
		/* var appended = state.append(c) */;
		/* if (c == ' */;
		/* ' && appended.isLevel()) {
            return appended.advance();
        } */
		/* if (c == ' */
	}/* ' && appended.isShallow()) {
            return appended.advance().exit();
        } */
	/* if */ (/* c == '{' */){
		return /* appended */.enter(/*  */);
		/* }
        if (c == ' */
	}/* ') {
            return appended.exit();
        } */
	/* return */ appended;
}
/* 

    private static Tuple<CompileState, String> compileRootSegment(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
            return new Tuple<>(state, stripped + "\n");
        }

        return generate(() -> parseClass(state, stripped, 0))
                .orElseGet(() -> compilePlaceholder(state, input));
    } *//* 

    private static Option<Tuple<CompileState, StructSegment>> parseClass(CompileState state, String input, int depth) {
        return parseStructure(state, "class ", input, depth);
    } *//* 

    private static Option<Tuple<CompileState, StructSegment>> parseStructure(CompileState state, String infix, String input, int depth) {
        var stripped = input.strip();
        if (stripped.endsWith("} *//* ")) {
            var withoutContentEnd = stripped.substring(0, stripped.length() - "} *//* ".length()); *//* 
            var contentStart = withoutContentEnd.indexOf("{");
            if (contentStart >= 0) {
                var beforeContent = withoutContentEnd.substring(0, contentStart);
                var content = withoutContentEnd.substring(contentStart + "{".length());
                var keywordIndex = beforeContent.indexOf(infix);
                if (keywordIndex >= 0) {
                    var beforeInfix = beforeContent.substring(0, keywordIndex);
                    var afterInfix = beforeContent.substring(keywordIndex + infix.length()).strip();

                    var prototype = new StructurePrototype()
                            .withBeforeInfix(beforeInfix)
                            .withInfix(infix)
                            .withContent(content)
                            .withDepth(depth);

                    return parseStructureWithMaybeTypeParameters(state, prototype, afterInfix);
                }
            }
        }

        return new None<>();
    } *//* 

    private static Option<Tuple<CompileState, StructSegment>> structWithMaybeImplements(CompileState state, String input, StructurePrototype prototype) {
        var entered = state.enter();
        var implementsIndex = input.indexOf(" implements ");
        if (implementsIndex >= 0) {
            var left = input.substring(0, implementsIndex).strip();
            var right = input.substring(implementsIndex + " implements ".length());
            var tuple = compileType(entered, right);
            return assembleStructure(tuple.left, prototype.withName(left).withInterfaces(Lists.of(tuple.right)));
        }

        return assembleStructure(entered, prototype.withName(input));
    } *//* 

    private static Option<Tuple<CompileState, StructSegment>> parseStructureWithMaybeExtends(CompileState state, StructurePrototype prototype, String afterInfix) {
        var extendsIndex = afterInfix.indexOf(" extends ");
        if (extendsIndex >= 0) {
            var left = afterInfix.substring(0, extendsIndex).strip();
            var right = afterInfix.substring(extendsIndex + " extends ".length());
            var tuple = compileType(state, right);
            return structWithMaybeImplements(tuple.left, left, prototype.withSuperType(tuple.right));
        }

        return structWithMaybeImplements(state, afterInfix, prototype);
    } *//* 

    private static Option<Tuple<CompileState, StructSegment>> parseStructureWithMaybeParameters(
            CompileState state,
            StructurePrototype prototype,
            String input
    ) {
        var paramEnd = input.lastIndexOf(")");
        if (paramEnd >= 0) {
            var left = input.substring(0, paramEnd);
            var next = input.substring(paramEnd + ")".length());

            var paramStart = left.indexOf("(");
            if (paramStart >= 0) {
                var name = left.substring(0, paramStart);
                var paramsString = left.substring(paramStart + "(".length());
                var parsed = parseValues(state, paramsString, Main::compileParameter);
                var parameters = parsed.right;

                return parseStructureWithMaybeExtends(parsed.left, prototype.withParameters(parameters).withName(name), next);
            }
        }

        return parseStructureWithMaybeExtends(state, prototype, input);
    } *//* 

    private static Option<Tuple<CompileState, StructSegment>> parseStructureWithMaybeTypeParameters(CompileState state, StructurePrototype prototype, String afterInfix) {
        var typeParamsEnd = afterInfix.indexOf(">");
        if (typeParamsEnd >= 0) {
            var beforeTypeParams = afterInfix.substring(0, typeParamsEnd);
            var afterTypeParams = afterInfix.substring(typeParamsEnd + ">".length());

            var typeParamsStart = beforeTypeParams.indexOf("<");
            if (typeParamsStart >= 0) {
                var name = beforeTypeParams.substring(0, typeParamsStart).strip();
                var typeParamString = beforeTypeParams.substring(typeParamsStart + "<".length());
                var typeParamTuple = parseValues(state, typeParamString, Main::stripToTuple);
                var typeParamsState = typeParamTuple.left;
                var typeParams = typeParamTuple.right;

                var newPrototype = prototype.withName(name).withTypeParams(typeParams);
                return parseStructureWithMaybeParameters(typeParamsState.defineTypeParams(typeParams), newPrototype, afterTypeParams);
            }
        }

        return parseStructureWithMaybeParameters(state, prototype, afterInfix);
    } *//* 

    private static Option<Tuple<CompileState, StructSegment>> assembleStructure(CompileState state, StructurePrototype prototype) {
        var name = prototype.name;
        if (isSymbol(name)) {
            var depth1 = prototype.depth;

            var statementsTuple = parseStatements(state.defineThis(name), prototype.content, (state0, segment) -> compileClassStatement(state0, segment, depth1 + 1));

            var statement = statementsTuple.right;
            var exited = statementsTuple.left.exit();

            var defined = exited.defineType(name);
            return new Some<>(new Tuple<CompileState, StructSegment>(defined, new Structure(prototype, statement, depth1)));
        }
        return new None<>();
    } *//* 

    private static Tuple<CompileState, StructSegment> compileClassStatement(CompileState state, String input, int depth) {
        return Main.<Whitespace, StructSegment>typed(() -> parseWhitespace(state, input))
                .or(() -> parseClass(state, input, depth))
                .or(() -> parseStructure(state, "interface ", input, depth))
                .or(() -> parseStructure(state, "record ", input, depth))
                .or(() -> typed(() -> parseStatement(input, depth, definition1 -> compileDefinition(state, definition1))))
                .or(() -> parseMethod(input, depth, state))
                .orElseGet(() -> parsePlaceholder0(state, input));
    } *//* 

    private static Tuple<CompileState, StructSegment> parsePlaceholder0(CompileState state, String input) {
        var tuple = parsePlaceholder(state, input);
        return new Tuple<>(tuple.left, tuple.right);
    } *//* 

    private static <T extends R, R> Option<Tuple<CompileState, R>> typed(Supplier<Option<Tuple<CompileState, T>>> supplier) {
        return supplier.get().map(tuple -> new Tuple<>(tuple.left, tuple.right));
    } *//* 

    private static Tuple<CompileState, String> compilePlaceholder(CompileState state, String input) {
        var tuple = parsePlaceholder(state, input);
        return new Tuple<>(tuple.left, tuple.right.generate());
    } *//* 

    private static Tuple<CompileState, Placeholder> parsePlaceholder(CompileState state, String input) {
        return new Tuple<>(state, new Placeholder(input));
    } *//* 

    private static Option<Tuple<CompileState, StructSegment>> parseMethod(String input, int depth, CompileState state) {
        var stripped = input.strip();
        var paramStart = stripped.indexOf("(");
        if (paramStart >= 0) {
            var left = stripped.substring(0, paramStart);
            var withParams = stripped.substring(paramStart + "(".length());
            return compileDefinition(state, left).flatMap(definitionTuple -> {
                var paramEnd = withParams.indexOf(")");
                if (paramEnd >= 0) {
                    var params = withParams.substring(0, paramEnd);
                    var maybeWithBraces = withParams.substring(paramEnd + ")".length()).strip();
                    var tuple = compileParameters(definitionTuple.left, params);

                    var header = createIndent(depth) + definitionTuple.right + "(" + tuple.right + ")";
                    if (maybeWithBraces.equals(";")) {
                        return new Some<>(new Tuple<CompileState, StructSegment>(tuple.left, new Content(header + ";")));
                    }
                    if (maybeWithBraces.startsWith("{") && maybeWithBraces.endsWith("}")) {
                        var inputContent = maybeWithBraces.substring(1, maybeWithBraces.length() - 1);
                        var statementsTuple = compileStatements(tuple.left, inputContent, (state1, input1) -> compileFunctionSegment(state1, input1, depth + 1));
                        var outputContent = "{" + statementsTuple.right + "\n" + "\t".repeat(depth) + "}";
                        return new Some<>(new Tuple<CompileState, StructSegment>(statementsTuple.left, new Content(header + outputContent)));
                    }
                }
                return new None<>();
            });
        }

        return new None<>();
    } *//* 

    private static Tuple<CompileState, String> compileFunctionSegment(CompileState state, String input, int depth) {
        return compileWhitespace(state, input)
                .or(() -> parseStatement(input, depth, slice -> compileStatementValue(state, slice)).map(tuple -> new Tuple<>(tuple.left, tuple.right.generate())))
                .orElseGet(() -> new Tuple<>(state, "\n" + "\t".repeat(depth) + generatePlaceholder(input.strip())));
    } *//* 

    private static Option<Tuple<CompileState, String>> compileStatementValue(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.startsWith("return ")) {
            var tuple = compileValue(state, stripped.substring("return ".length()));
            return new Some<>(new Tuple<>(tuple.left, "return " + tuple.right));
        }

        return new Some<>(new Tuple<>(state, generatePlaceholder(stripped)));
    } *//* 

    private static Tuple<CompileState, String> compileValue(CompileState state, String input) {
        var stripped = input.strip();
        if (stripped.endsWith(")")) {
            var withoutEnd = stripped.substring(0, stripped.length() - ")".length());
            var argsStart = withoutEnd.indexOf("(");
            if (argsStart >= 0) {
                var beforeArgs = withoutEnd.substring(0, argsStart).strip();
                var args = withoutEnd.substring(argsStart + "(".length());
                if (beforeArgs.startsWith("new ")) {
                    var type = compileType(state, beforeArgs.substring("new ".length()));
                    var tuple = compileValues(type.left, args, Main::compileValue);
                    return new Tuple<>(tuple.left, "new " + type.right + "(" + tuple.right + ")");
                }
                else {
                    var type = compileValue(state, beforeArgs);
                    var tuple = compileValues(type.left, args, Main::compileValue);
                    return new Tuple<>(tuple.left, type.right + "(" + tuple.right + ")");
                }
            }
        }

        var separator = stripped.lastIndexOf(".");
        if (separator >= 0) {
            var parent = stripped.substring(0, separator);
            var property = stripped.substring(separator + ".".length());
            var tuple = compileValue(state, parent);
            return new Tuple<>(tuple.left, tuple.right + "." + property);
        }

        if (stripped.equals("this") || state.isDefined(stripped)) {
            return new Tuple<>(state, stripped);
        }

        return new Tuple<>(state, generatePlaceholder(stripped));
    } *//* 

    private static Tuple<CompileState, String> compileParameters(CompileState state, String params) {
        return compileValues(state, params, Main::compileParameter);
    } *//* 

    private static Tuple<CompileState, String> compileValues(CompileState state, String params, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        var parsed = parseValues(state, params, mapper);
        var joined = join(", ", parsed.right);
        return new Tuple<>(parsed.left, joined);
    } *//* 

    private static Tuple<CompileState, List<String>> parseValues(CompileState state, String input, BiFunction<CompileState, String, Tuple<CompileState, String>> mapper) {
        return parseAll(state, input, Main::foldValueChar, mapper);
    } *//* 

    private static DivideState foldValueChar(DivideState state, char c) {
        if (c == ',' && state.isLevel()) {
            return state.advance();
        }
        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    } *//* 

    private static Tuple<CompileState, String> compileParameter(CompileState state, String input) {
        return compileWhitespace(state, input)
                .or(() -> compileDefinition(state, input))
                .orElseGet(() -> compilePlaceholder(state, input));
    } *//* 

    private static Option<Tuple<CompileState, String>> compileWhitespace(CompileState state, String input) {
        return generate(() -> parseWhitespace(state, input));
    } *//* 

    private static <T extends Node> Option<Tuple<CompileState, String>> generate(Supplier<Option<Tuple<CompileState, T>>> parser) {
        return parser.get().map(tuple -> new Tuple<>(tuple.left, tuple.right.generate()));
    } *//* 

    private static Option<Tuple<CompileState, Whitespace>> parseWhitespace(CompileState state, String input) {
        if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, Whitespace>(state, new Whitespace()));
        }
        return new None<>();
    } *//* 

    private static Option<Tuple<CompileState, Statement>> parseStatement(String input, int depth, Function<String, Option<Tuple<CompileState, String>>> mapper) {
        var stripped = input.strip();
        if (!stripped.endsWith(";")) {
            return new None<>();
        }

        var content = stripped.substring(0, stripped.length() - ";".length());
        return mapper.apply(content).map(generated -> {
            return new Tuple<>(generated.left, new Statement(depth, generated.right));
        });
    } *//* 

    private static Option<Tuple<CompileState, String>> compileDefinition(CompileState state, String input) {
        var nameSeparator = input.lastIndexOf(" ");
        if (nameSeparator >= 0) {
            var beforeName = input.substring(0, nameSeparator).strip();
            var name = input.substring(nameSeparator + " ".length()).strip();
            if (isSymbol(name)) {
                var divisions = divide(beforeName, Main::foldTypeDivisions);
                if (divisions.size() >= 2) {
                    var beforeType = join(" ", divisions.subList(0, divisions.size() - 1)).strip();
                    var type = divisions.getLast();

                    if (beforeType.endsWith(">")) {
                        var withoutTypeParamEnd = beforeType.substring(0, beforeType.length() - ">".length());
                        var typeParamStart = withoutTypeParamEnd.indexOf("<");
                        if (typeParamStart >= 0) {
                            var beforeTypeParams = withoutTypeParamEnd.substring(0, typeParamStart).strip();
                            var typeParams = parseValues(state, withoutTypeParamEnd.substring(typeParamStart + "<".length()), Main::stripToTuple);
                            return new Some<>(generateDefinition(new Some<String>(beforeTypeParams), type, name, typeParams.left, typeParams.right));
                        }
                    }

                    return new Some<>(generateDefinition(new Some<String>(beforeType), type, name, state, Lists.empty()));
                }
                else {
                    return new Some<>(generateDefinition(new None<String>(), beforeName, name, state, Lists.empty()));
                }
            }
        }
        return new None<>();
    } *//* 

    private static Tuple<CompileState, String> stripToTuple(CompileState t, String u) {
        return new Tuple<>(t, u.strip());
    } *//* 

    private static DivideState foldTypeDivisions(DivideState state, char c) {
        if (c == ' ' && state.isLevel()) {
            return state.advance();
        }

        var appended = state.append(c);
        if (c == '<') {
            return appended.enter();
        }
        if (c == '>') {
            return appended.exit();
        }
        return appended;
    } *//* 

    private static Tuple<CompileState, String> generateDefinition(Option<String> maybeBeforeType, String type, String name, CompileState state, List<String> typeParams) {
        var beforeTypeString = maybeBeforeType.filter(value -> !value.isEmpty()).map(beforeType -> beforeType + " ").orElse("");
        var typeParamString = typeParams.isEmpty() ? "" : "<" + join(", ", typeParams) + ">";
        var typeTuple = compileType(state.defineTypeParams(typeParams), type);
        return new Tuple<>(typeTuple.left, beforeTypeString + typeTuple.right + " " + name + typeParamString);
    } *//* 

    private static Tuple<CompileState, String> compileType(CompileState state, String input) {
        var stripped = input.strip();

        if (stripped.equals("int")) {
            return new Tuple<>(state, stripped);
        }

        if (stripped.equals("String")) {
            return new Tuple<>(state, stripped);
        }

        if (stripped.equals("boolean")) {
            return new Tuple<>(state, stripped);
        }


        if (state.isDefined(stripped)) {
            return new Tuple<>(state, stripped);
        }

        if (stripped.endsWith(">")) {
            var withEnd = stripped.substring(0, stripped.length() - ">".length());
            var typeArgsStart = withEnd.indexOf("<");
            if (typeArgsStart >= 0) {
                var base = withEnd.substring(0, typeArgsStart).strip();
                var inputTypeArgs = withEnd.substring(typeArgsStart + "<".length());
                var parsed = parseValues(state, inputTypeArgs, Main::compileTypeArgument);
                var typeArgs = parsed.right;
                var outputTypeArgs = join(", ", typeArgs);

                if (base.equals("Function")) {
                    return new Tuple<>(state, generateFunctionalType(Lists.of(typeArgs.get(0)), typeArgs.get(1)));
                }

                if (base.equals("BiFunction")) {
                    return new Tuple<>(state, generateFunctionalType(Lists.of(typeArgs.get(0), typeArgs.get(1)), typeArgs.get(2)));
                }

                if (base.equals("Predicate")) {
                    return new Tuple<>(state, generateFunctionalType(Lists.of(typeArgs.get(0)), "boolean"));
                }

                if (base.equals("Supplier")) {
                    return new Tuple<>(state, generateFunctionalType(Lists.empty(), typeArgs.get(0)));
                }

                return new Tuple<>(state, compileBaseType(base, state) + "<" + outputTypeArgs + ">");
            }
        }

        return compilePlaceholder(state, input);
    } *//* 

    private static Tuple<CompileState, String> compileTypeArgument(CompileState state1, String input1) {
        return compileWhitespace(state1, input1).orElseGet(() -> compileType(state1, input1));
    } *//* 

    private static String generateFunctionalType(List<String> arguments, String returns) {
        var argumentString = arguments.size() == 1 ? arguments.get(0) : "(" + join(", ", arguments) + ")";
        return argumentString + " -> " + returns;
    } *//* 

    private static String compileBaseType(String base, CompileState state) {
        if (state.isDefined(base)) {
            return base;
        }

        return generatePlaceholder(base);
    } *//* 

    private static boolean isSymbol(String input) {
        for (var i = 0; i < input.length(); i++) {
            var c = input.charAt(i);
            if (Character.isLetter(c)) {
                continue;
            }
            return false;
        }
        return true;
    } *//* 

    private static String createIndent(int depth) {
        return "\n" + "\t".repeat(depth);
    } *//* 

    private static String generatePlaceholder(String input) {
        return "/* " + input + " */";
    } *//* 
}
 */
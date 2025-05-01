enum OptionVariant {
	Some,
	None
};
union OptionValue<T> {
	Some<T> some;
	None<T> none;
};
/* private sealed */struct Option<T> {
	OptionVariant _variant;
	OptionValue<T> _value;
};
/* private */struct Head<S, T> {
	S _super;
	template Option<T> (*next)(S);
};
/* private */struct List<S, T> {
	S _super;
	template List<T> (*addLast)(S, T);
	template Iterator<T> (*iterate)(S);
	template Option<(template List<T>, T)> (*removeLast)(S);
	int (*isEmpty)(S);
	T (*get)(S, int);
	template List<T> (*addFirst)(S, T);
	int (*contains)(S, T);
};
/* private */struct Collector<S, T, C> {
	S _super;
	C (*createInitial)(S);
	C (*fold)(S, C, T);
};
/* private */struct Splitter<S> {
	S _super;
	template Option<(char*, char*)> (*split)(S, char*);
};
/* private */struct Type extends Node<S> {
	S _super;
};
/* private */struct Parameter extends Node<S> {
	S _super;
};
/* private */struct Node<S> {
	S _super;
	/* String_ */ (*generate)(S);
};
/* private */struct String_ {char* value
};
/* private */struct Some<T>(T value) implements Option<T> {
};
/* private */struct None<T>() implements Option<T> {
};
/* private */struct Iterator<T> {template Head</* T */> head
};
/* private */struct Joiner(String delimiter) implements Collector<String, Option<String>> {
};
/* private */struct StructurePrototype {char* typechar* nametemplate List<char*> typeParamstemplate List<char*> variants
};
/* private */struct CompileState {template List<char*> structstemplate List<char*> functionstemplate Option</* StructurePrototype */> maybeStructureType
};
/* private */struct DivideState {char* inputtemplate List<char*> segments/* StringBuilder */ bufferint indexint depth
};
/* private */struct Tuple<A, B> {/* A */ left/* B */ right
};
/* private */struct InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter {
};
/* private */struct Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter {/* 

        private String generate0() {
            var beforeTypeString = this.maybeBeforeType.map(beforeType -> generatePlaceholder(beforeType) + " ").orElse("");
            return beforeTypeString + this.type.generateWithName(this.name).toSlice();
        } */
};
/* private */struct Content(String input) implements Type, Parameter {/* 

        private String generate0() {
            return generatePlaceholder(this.input);
        } */
};
/* private */struct Functional(List<Type> arguments, Type returns) implements Type {
};
/* private */struct Template(String base, List<Type> arguments) implements Type {/* 

        private String generate0() {
            var generatedTuple = this.arguments().iterate()
                    .map(type -> type.generate().toSlice())
                    .collect(new Joiner(", "))
                    .orElse("");

            return "template " + this.base() + "<" + generatedTuple + ">";
        } */
};
/* private */struct TypeParameter(String value) implements Type {/* 

        private String generate0() {
            return this.value;
        } */
};
/* private */struct Ref(Type type) implements Type {/* 

        private String generate0() {
            return this.type.generate().toSlice() + "*";
        } */
};
/* private */struct TupleType(List<Type> arguments) implements Type {/* 

        private String generate0() {
            return "(" + generateNodesAsValues(this.arguments) + ")";
        } */
};
/* public */struct Main {/* 

    @External
    private record JavaList<T>(java.util.List<T> elements) implements List<T> {
        public JavaList() {
            this(new ArrayList<>());
        }

        @Override
        public List<T> addLast(T element) {
            var copy = new ArrayList<>(this.elements);
            copy.add(element);
            return new JavaList<>(copy);
        }

        @Override
        public Iterator<T> iterate() {
            return new Iterator<>(new RangeHead(this.elements.size())).map(this.elements::get);
        }

        @Override
        public Option<Tuple<List<T>, T>> removeLast() {
            if (this.elements.isEmpty()) {
                return new None<>();
            }

            var slice = this.elements.subList(0, this.elements.size() - 1);
            var last = this.elements.getLast();
            return new Some<>(new Tuple<>(new JavaList<>(new ArrayList<>(slice)), last));
        }

        @Override
        public boolean isEmpty() {
            return this.elements.isEmpty();
        }

        @Override
        public T get(int index) {
            return this.elements.get(index);
        }

        @Override
        public List<T> addFirst(T element) {
            var copy = this.copy();
            copy.addFirst(element);
            return new JavaList<>(copy);
        }

        @Override
        public boolean contains(T element) {
            return this.elements.contains(element);
        }

        private java.util.List<T> copy() {
            return new ArrayList<T>(this.elements);
        }
    } *//* 

    private enum Primitive implements Type {
        Auto("auto"),
        I8("char"),
        I32("int");
        private final String value;

        Primitive(String value) {
            this.value = value;
        }

        @Override
        public String_ generate() {
            return new String_(this.generate0());
        }

        private String generate0() {
            return this.value;
        }
    } */
};
/* default */ /* String_ */ generateWithName_Type extends Node(char* name){/* return this.generate().appendSlice(" ").appendSlice(name); *//*  */
}
/* public */ char* toSlice_String_(){/* return this.value; *//*  */
}
/* public */ /* String_ */ appendSlice_String_(char* slice){/* return new String_(this.value + slice); *//*  */
}
/* @Override
 public <R> */ template Option</* R */> map_Some(/*  R */ (*mapper)(/* T */)){/* return new Some<>(mapper.apply(this.value)); *//*  */
}
/* @Override
 public */ int isEmpty_Some(){/* return false; *//*  */
}
/* @Override
 public */ /* T */ orElse_Some(/* T */ other){/* return this.value; *//*  */
}
/* @Override
 public */ template Option</* T */> or_Some(template Option</* T */> (*other)()){/* return this; *//*  */
}
/* @Override
 public */ /* T */ orElseGet_Some(/* T */ (*other)()){/* return this.value; *//*  */
}
/* @Override
 public <R> */ template Option</* R */> flatMap_Some(template Option</* R */> (*mapper)(/* T */)){/* return mapper.apply(this.value); *//*  */
}
/* @Override
 public */ template Option</* T */> filter_Some(template Predicate</* T */> predicate){/* return predicate.test(this.value) ? this : new None<>(); *//*  */
}
/* @Override
 public */ int isPresent_Some(){/* return true; *//*  */
}
/* @Override
 public <R> */ template Option</* R */> map_None(/*  R */ (*mapper)(/* T */)){/* return new None<>(); *//*  */
}
/* @Override
 public */ int isEmpty_None(){/* return true; *//*  */
}
/* @Override
 public */ /* T */ orElse_None(/* T */ other){/* return other; *//*  */
}
/* @Override
 public */ template Option</* T */> or_None(template Option</* T */> (*other)()){/* return other.get(); *//*  */
}
/* @Override
 public */ /* T */ orElseGet_None(/* T */ (*other)()){/* return other.get(); *//*  */
}
/* @Override
 public <R> */ template Option</* R */> flatMap_None(template Option</* R */> (*mapper)(/* T */)){/* return new None<>(); *//*  */
}
/* @Override
 public */ template Option</* T */> filter_None(template Predicate</* T */> predicate){/* return new None<>(); *//*  */
}
/* @Override
 public */ int isPresent_None(){/* return false; *//*  */
}
/* public <C> */ /* C */ collect_Iterator(template Collector<T, /*  C */> collector){/* return this.fold(collector.createInitial(), collector::fold); *//*  */
}
/* private <C> */ /* C */ fold_Iterator(/* C */ initial, template BiFunction</* C */, T, /*  C */> folder){/* var current = initial; *//* while (true) {
                C finalCurrent = current;
                var maybeNext = this.head.next().map(next -> folder.apply(finalCurrent, next));
                if (maybeNext.isEmpty()) {
                    return current;
                }
                else {
                    current = maybeNext.orElse(null);
                }
            } *//*  */
}
/* public <R> */ template Iterator</* R */> flatMap_Iterator(template Iterator</* R */> (*mapper)(T)){/* return this.map(mapper).fold(new Iterator<>(new EmptyHead<>()), Iterator::concat); *//*  */
}
/* public <R> */ template Iterator</* R */> map_Iterator(/*  R */ (*mapper)(T)){/* return new Iterator<>(() -> this.head.next().map(mapper)); *//*  */
}
/* private */ template Iterator<T> concat_Iterator(template Iterator<T> other){/* return new Iterator<>(() -> this.head.next().or(other::next)); *//*  */
}
/* public */ template Option<T> next_Iterator(){/* return this.head.next(); *//*  */
}
/* private static final class RangeHead implements Head<Integer> {
 private final int length;
 private int counter = 0;

 */ /* private */ RangeHead(int length){/* this.length = length; *//* }

        @Override
        public Option<Integer> next() {
            if (this.counter >= this.length) {
                return new None<>();
            } *//* var value = this.counter; *//* this.counter++; *//* return new Some<>(value); *//* } */
}
/* private static class Lists {
 public static <T> */ template List</* T */> of(/* T... */ elements){/* return new JavaList<>(Arrays.asList(elements)); *//* }

        public static <T> List<T> empty() {
            return new JavaList<>(new ArrayList<>()); *//* } */
}
/* private static class EmptyHead<T> implements Head<T> {
 @Override
 public */ template Option</* T */> next(){/* return new None<>(); *//* } */
}
/* public */ Joiner_Joiner(String delimiter) implements Collector(){/* this(""); *//*  */
}
/* @Override
 public */ template Option<String> createInitial_Joiner(String delimiter) implements Collector(){/* return new None<>(); *//*  */
}
/* @Override
 public */ template Option<String> fold_Joiner(String delimiter) implements Collector(template Option<String> current, String element){/* return new Some<>(current.map(inner -> inner + this.delimiter + element).orElse(element)); *//*  */
}
/* public */ CompileState_CompileState(){/* this(Lists.empty(), Lists.empty(), new None<>()); *//*  */
}
/* private */ char* generate_CompileState(){/* return this.getJoin(this.structs) + this.getJoin(this.functions); *//*  */
}
/* private */ char* getJoin_CompileState(template List<char*> lists){/* return lists.iterate().collect(new Joiner()).orElse(""); *//*  */
}
/* public */ /* CompileState */ addStruct_CompileState(char* struct){/* return new CompileState(this.structs.addLast(struct), this.functions, this.maybeStructureType); *//*  */
}
/* public */ /* CompileState */ addFunction_CompileState(char* function){/* return new CompileState(this.structs, this.functions.addLast(function), this.maybeStructureType); *//*  */
}
/* public */ /* CompileState */ withStructType_CompileState(/* StructurePrototype */ type){/* return new CompileState(this.structs, this.functions, new Some<>(type)); *//*  */
}
/* public */ /* CompileState */ withoutStructType_CompileState(){/* return new CompileState(this.structs, this.functions, new None<>()); *//*  */
}
/* public */ DivideState_DivideState(char* input){/* this(input, new JavaList<>(), new StringBuilder(), 0, 0); *//*  */
}
/* private */ template Option</* DivideState */> popAndAppend_DivideState(){/* return this.popAndAppendToTuple().map(Tuple::right); *//*  */
}
/* private */ template Option<(/* Character */, /*  DivideState */)> popAndAppendToTuple_DivideState(){/* return this.pop().map(tuple -> {
                var c = tuple.left;
                var state = tuple.right;
                return new Tuple<>(c, state.append(c));
            } *//* ); *//*  */
}
/* private */ /* DivideState */ append_DivideState(/* char */ c){/* return new DivideState(this.input, this.segments, this.buffer.append(c), this.index, this.depth); *//*  */
}
/* public */ template Option<(/* Character */, /*  DivideState */)> pop_DivideState(){/* if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<Character, DivideState>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            } *//* else {
                return new None<>();
            } *//*  */
}
/* private */ /* DivideState */ advance_DivideState(){/* var withBuffer = this.buffer.isEmpty() ? this.segments : this.segments.addLast(this.buffer.toString()); *//* return new DivideState(this.input, withBuffer, new StringBuilder(), this.index, this.depth); *//*  */
}
/* public */ /* DivideState */ exit_DivideState(){/* return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth - 1); *//*  */
}
/* public */ int isLevel_DivideState(){/* return this.depth == 0; *//*  */
}
/* public */ /* DivideState */ enter_DivideState(){/* return new DivideState(this.input, this.segments, this.buffer, this.index, this.depth + 1); *//*  */
}
/* public */ int isShallow_DivideState(){/* return this.depth == 1; *//*  */
}
/* public static <A, B, C> */ (A, /*  C */) (*mapRight_Tuple)((A, B))(/*  C */ (*mapper)(B)){/* return tuple -> new Tuple<>(tuple.left, mapper.apply(tuple.right)); *//*  */
}
/* private static class Iterators {
 public static <T> */ template Iterator</* T */> fromOptions(template Option</* T */> option){/* return new Iterator<>(option.<Head<T>>map(SingleHead::new).orElseGet(EmptyHead::new)); *//* } */
}
/* private static class SingleHead<T> implements Head<T> {
 private final T value;
 private boolean retrieved = false;

 */ /* public */ SingleHead(/* T */ value){/* this.value = value; *//* }

        @Override
        public Option<T> next() {
            if (this.retrieved) {
                return new None<>();
            } *//* this.retrieved = true; *//* return new Some<>(this.value); *//* } */
}
/* @Override
 public */ template Option<(char*, char*)> split_InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter(char* input){/* return this.apply(input).map(classIndex -> {
                var beforeKeyword = input.substring(0, classIndex);
                var afterKeyword = input.substring(classIndex + this.length());
                return new Tuple<>(beforeKeyword, afterKeyword);
            } *//* ); *//*  */
}
/* private */ int length_InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter(){/* return this.infix.length(); *//*  */
}
/* private */ template Option</* Integer */> apply_InfixSplitter(String infix,
                                 BiFunction<String, String, Option<Integer>> locator) implements Splitter(char* input){/* return this.locator().apply(input, this.infix); *//*  */
}
/* private static class TypeSeparatorSplitter implements Splitter {
 @Override
 public */ template Option<(char*, char*)> split(char* input){/* return divide(input, TypeSeparatorSplitter::fold).removeLast().flatMap(segments -> {
                var left = segments.left;
                if (left.isEmpty()) {
                    return new None<>();
                }

                var beforeType = left.iterate().collect(new Joiner(" ")).orElse("");
                var type = segments.right;
                return new Some<>(new Tuple<>(beforeType, type));
            } *//* ); *//* }

        private static DivideState fold(DivideState state, char c) {
            if (c == ' ' && state.isLevel()) {
                return state.advance();
            } *//* var appended = state.append(c); *//* if (c == '<') {
                return appended.enter();
            } *//* if (c == '>') {
                return appended.exit();
            } *//* return appended; *//* } */
}
/* public */ Definition_Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter(/* Type */ type, char* name){/* this(new None<>(), type, name); *//*  */
}
/* public */ /* Definition */ mapName_Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter(char* (*mapper)(char*)){/* return new Definition(this.maybeBeforeType, this.type, mapper.apply(this.name)); *//*  */
}
/* @Override
 public */ /* String_ */ generate_Definition(Option<String> maybeBeforeType, Type type, String name) implements Parameter(){/* return new String_(this.generate0()); *//*  */
}
/* @Override
 public */ /* String_ */ generate_Content(String input) implements Type, Parameter(){/* return new String_(this.generate0()); *//*  */
}
/* @Override
 public */ /* String_ */ generate_Functional(List<Type> arguments, Type returns) implements Type(){/* return this.generateWithName(""); *//*  */
}
/* @Override
 public */ /* String_ */ generateWithName_Functional(List<Type> arguments, Type returns) implements Type(char* name){/* var joinedArguments = this.arguments().iterate()
                    .map(type -> type.generate().toSlice())
                    .collect(new Joiner(", "))
                    .orElse(""); *//* return this.returns.generate()
                    .appendSlice(" (*")
                    .appendSlice(name)
                    .appendSlice(")(")
                    .appendSlice(joinedArguments)
                    .appendSlice(")"); *//*  */
}
/* @Override
 public */ /* String_ */ generate_Template(String base, List<Type> arguments) implements Type(){/* return new String_(this.generate0()); *//*  */
}
/* private static class ListCollector<T> implements Collector<T, List<T>> {
 @Override
 public */ template List</* T */> createInitial(){/* return Lists.empty(); *//* }

        @Override
        public List<T> fold(List<T> current, T element) {
            return current.addLast(element); *//* } */
}
/* @Override
 public */ /* String_ */ generate_TypeParameter(String value) implements Type(){/* return new String_(this.generate0()); *//*  */
}
/* @Override
 public */ /* String_ */ generate_Ref(Type type) implements Type(){/* return new String_(this.generate0()); *//*  */
}
/* @Override
 public */ /* String_ */ generate_TupleType(List<Type> arguments) implements Type(){/* return new String_(this.generate0()); *//*  */
}
/* public static */ /* void */ main(){/* try {
            var source = Paths.get(".", "src", "java", "magma", "Main.java");
            var target = source.resolveSibling("main.c");

            var input = Files.readString(source);
            Files.writeString(target, compileRoot(input));
        } *//* catch (IOException e) {
            e.printStackTrace();
        } *//*  */
}
/* private static */ char* compileRoot(char* input){/* var state = new CompileState(); *//* var tuple = compileAll(state, input, Main::compileRootSegment)
                .orElse(new Tuple<>(state, "")); *//* return tuple.right + tuple.left.generate(); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> compileAll(/* CompileState */ initial, char* input, template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, char*)>> mapper){/* return all(initial, input, Main::foldStatementChar, mapper, Main::mergeStatements); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> all(/* CompileState */ initial, char* input, template BiFunction</* DivideState */, /*  Character */, /*  DivideState */> folder, template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, char*)>> mapper, template BiFunction</* StringBuilder */, char*, /*  StringBuilder */> merger){/* return parseAll(initial, input, folder, mapper).map(tuple -> new Tuple<>(tuple.left, generateAll(merger, tuple.right))); *//*  */
}
/* private static */ /* StringBuilder */ mergeStatements(/* StringBuilder */ output, char* right){/* return output.append(right); *//*  */
}
/* private static */ /* DivideState */ foldStatementChar(/* DivideState */ state, /* char */ c){/* var appended = state.append(c); *//* if (c == ';' && appended.isLevel()) {
            return appended.advance();
        } *//* if (c == '}' && appended.isShallow()) {
            return appended.advance().exit();
        } *//* if (c == '{') {
            return appended.enter();
        } *//* if (c == '}') {
            return appended.exit();
        } *//* return appended; *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> compileRootSegment(/* CompileState */ state, char* input){/* return or(state, input, Lists.of(
                Main::whitespace,
                Main::compileNamespaced,
                structure("class", "class "),
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        )); *//*  */
}
/* private static */ template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, char*)>> structure(char* type, char* infix){/* return (state, input) -> first(input, infix, (beforeKeyword, afterKeyword) -> {
            var slices = Arrays.stream(beforeKeyword.split(" "))
                    .map(String::strip)
                    .filter(value -> !value.isEmpty())
                    .toList();

            if (slices.contains("@External")) {
                return new None<>();
            }

            return first(afterKeyword, "{", (beforeContent, withEnd) -> {
                return or(state, beforeContent, Lists.of(
                        (state0, beforeContent0) -> structureWithVariants(type, state0, beforeKeyword, beforeContent0, withEnd),
                        (state0, beforeContent0) -> structureWithoutVariants(type, state0, beforeKeyword, beforeContent0, Lists.empty(), withEnd)
                ));
            });
        } *//* ); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> structureWithVariants(char* type, /* CompileState */ state, char* beforeKeyword, char* beforeContent, char* withEnd){/* return first(beforeContent, " permits ", (beforePermits, variantsString) -> {
            return parseValues(state, variantsString, Main::symbol).flatMap(params -> {
                return structureWithoutVariants(type, params.left, beforeKeyword, beforePermits, params.right, withEnd);
            });
        } *//* ); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> symbol(/* CompileState */ state, char* value){/* return new Some<>(new Tuple<>(state, value.strip())); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> structureWithoutVariants(char* type, /* CompileState */ state, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){/* return or(state, beforeContent, Lists.of(
                (instance, before) -> structureWithParams(type, instance, beforeKeyword, before, variants, withEnd),
                (instance, before) -> structureWithMaybeTypeParams(type, instance, beforeKeyword, before.strip(), "", variants, withEnd)
        )); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> structureWithParams(char* type, /* CompileState */ instance, char* beforeKeyword, char* beforeContent, template List<char*> variants, char* withEnd){/* return suffix(beforeContent.strip(), ")", withoutEnd -> first(withoutEnd, "(", (name, paramString) -> {
            return all(instance, paramString, Main::foldValueChar, (instance1, paramString1) -> parameter(instance1, paramString1).map(Tuple.mapRight(parameter -> parameter.generate().toSlice())), Main::mergeStatements).flatMap(params -> {
                return structureWithMaybeTypeParams(type, params.left, beforeKeyword, name, params.right, variants, withEnd);
            });
        } *//* )); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Parameter */)> parameter(/* CompileState */ instance, char* paramString){/* return Main.or(instance, paramString, Lists.of(
                wrap(Main::definition),
                wrap(Main::content)
        )); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> structureWithMaybeTypeParams(char* type, /* CompileState */ state, char* beforeKeyword, char* beforeParams, char* params, template List<char*> variants, char* withEnd){/* return or(state, beforeParams, Lists.of(
                (state0, beforeParams0) -> structureWithTypeParams(type, state0, beforeParams0, beforeKeyword, params, variants, withEnd),
                (state0, name) -> structureWithName(type, state0, beforeKeyword, name, Lists.empty(), params, variants, withEnd)
        )); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> structureWithTypeParams(char* type, /* CompileState */ state, char* beforeKeyword, char* params, template List<char*> variants, char* withEnd){/* return suffix(beforeParams0.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (name, typeParamString) -> {
                return parseValues(state, typeParamString, Main::symbol).flatMap(values -> {
                    return structureWithName(type, values.left, beforeKeyword, name, values.right, params, variants, withEnd);
                });
            });
        } *//* ); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> structureWithName(char* type, /* CompileState */ state, char* beforeKeyword, char* name, template List<char*> typeParams, char* params, template List<char*> variants, char* withEnd){/* return suffix(withEnd.strip(), "}", content -> {
            return compileAll(state.withStructType(new StructurePrototype(type, name, typeParams, variants)), content, Main::structSegment).flatMap(tuple -> {
                return new Some<>(assembleStruct(type, tuple.left, beforeKeyword, name, typeParams, params, variants, tuple.right));
            }).map(tuple -> new Tuple<>(tuple.left.withoutStructType(), tuple.right));
        } *//* ); *//*  */
}
/* private static */ (/* CompileState */, char*) assembleStruct(char* type, /* CompileState */ state, char* beforeKeyword, char* name, template List<char*> typeParams, char* params, template List<char*> variants, char* oldContent){/* if (!variants.isEmpty()) {
            var enumName = name + "Variant";
            var enumFields = variants.iterate()
                    .map(variant -> "\n\t" + variant)
                    .collect(new Joiner(","))
                    .orElse("");
            var generatedEnum = "enum " + enumName + " {" + enumFields + "\n};\n";

            var typeParamString = generateTypeParams(typeParams);
            var unionName = name + "Value" + typeParamString;
            var unionFields = variants.iterate()
                    .map(variant -> "\n\t" + variant + typeParamString + " " + variant.toLowerCase() + ";")
                    .collect(new Joiner(""))
                    .orElse("");
            var generateUnion = "union " + unionName + " {" + unionFields + "\n};\n";

            var compileState = state.addStruct(generatedEnum).addStruct(generateUnion);
            var newContent = "\n\t" + enumName + " _variant;"
                    + "\n\t" + unionName + " _value;"
                    + oldContent;

            return generateStruct(compileState, beforeKeyword, name, typeParamString, params, newContent);
        } *//* if (type.equals("interface")) {
            var typeParamString = generateTypeParams(typeParams.addFirst("S"));
            var newContent = "\n\tS _super;" + oldContent;
            return generateStruct(state, beforeKeyword, name, typeParamString, params, newContent);
        } *//* return generateStruct(state, beforeKeyword, name, generateTypeParams(typeParams), params, oldContent); *//*  */
}
/* private static */ (/* CompileState */, char*) generateStruct(/* CompileState */ state, char* beforeKeyword, char* name, char* typeParamString, char* params, char* content){/* var generatedStruct = generatePlaceholder(beforeKeyword.strip()) + "struct " + name + typeParamString + " {" + params + content + "\n};\n"; *//* return new Tuple<CompileState, String>(state.addStruct(generatedStruct), ""); *//*  */
}
/* private static */ char* generateTypeParams(template List<char*> typeParams){/* return typeParams.isEmpty() ? "" : "<" + typeParams.iterate().collect(new Joiner(", ")).orElse("") + ">"; *//*  */
}
/* private static <T> */ template Option<(/* CompileState */, /*  T */)> or(/* CompileState */ state, char* input, template List<template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, /*  T */)>>> actions){/* return actions.iterate()
                .map(action -> action.apply(state, input))
                .flatMap(Iterators::fromOptions)
                .next(); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> compileNamespaced(/* CompileState */ state, char* input){/* if (input.strip().startsWith("package ") || input.strip().startsWith("import ")) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } *//* return new None<>(); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> structSegment(/* CompileState */ state, char* input){/* return or(state, input, Lists.of(
                Main::whitespace,
                Main::annotation,
                structure("record", "record "),
                structure("interface", "interface "),
                Main::method,
                Main::definitionStatement,
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        )); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> definitionStatement(/* CompileState */ state, char* input){/* return suffix(input.strip(), ";", withoutEnd -> definition(state, withoutEnd).map(Tuple.mapRight(definition -> definition.generate().toSlice())).map(value -> {
            var generated = "\n\t" + value.right + ";";
            return new Tuple<>(value.left, generated);
        } *//* )); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Content */)> content(/* CompileState */ state, char* input){/* return new Some<>(new Tuple<CompileState, Content>(state, new Content(input))); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> whitespace(/* CompileState */ state, char* input){/* if (input.isBlank()) {
            return new Some<>(new Tuple<CompileState, String>(state, ""));
        } *//* return new None<>(); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> method(/* CompileState */ state, char* input){/* return first(input, "(", (inputDefinition, withParams) -> {
            return first(withParams, ")", (paramsString, withBraces) -> {
                return compileMethodHeader(state, inputDefinition).flatMap(outputDefinition -> {
                    return parseValues(outputDefinition.left, paramsString, Main::parameter).flatMap(outputParams -> {
                        var params = outputParams.right
                                .iterate()
                                .map(Main::retainDefinition)
                                .flatMap(Iterators::fromOptions)
                                .collect(new ListCollector<>());

                        return or(outputParams.left, withBraces, Lists.of(
                                (state0, element) -> methodWithoutContent(state0, outputDefinition.right, params, element),
                                (state0, element) -> methodWithContent(state0, outputDefinition.right, params, element)));
                    });
                });
            });
        } *//* ); *//*  */
}
/* private static */ template Option</* Definition */> retainDefinition(/* Parameter */ param){/* if (param instanceof Definition definition) {
            return new Some<>(definition);
        } *//* else {
            return new None<>();
        } *//*  */
}
/* private static <T> */ template Option<(/* CompileState */, template List</* T */>)> parseValues(/* CompileState */ state, char* input, template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, /*  T */)>> compiler){/* return parseAll(state, input, Main::foldValueChar, compiler); *//*  */
}
/* private static <T> */ template Option<(/* CompileState */, template List</* T */>)> parseAll(/* CompileState */ initial, char* input, template BiFunction</* DivideState */, /*  Character */, /*  DivideState */> folder, template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, /*  T */)>> mapper){/* return divide(input, folder)
                .iterate()
                .<Option<Tuple<CompileState, List<T>>>>fold(new Some<>(new Tuple<CompileState, List<T>>(initial, Lists.empty())),
                        (maybeCurrent, segment) -> maybeCurrent.flatMap(
                                state -> foldElement(state, segment, mapper))); *//*  */
}
/* private static <T> */ template Option<(/* CompileState */, template List</* T */>)> foldElement((/* CompileState */, template List</* T */>) state, char* segment, template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, /*  T */)>> mapper){/* var oldState = state.left; *//* var oldCache = state.right; *//* return mapper.apply(oldState, segment).map(result -> {
            var newState = result.left;
            var newElement = result.right;
            return new Tuple<>(newState, oldCache.addLast(newElement));
        } *//* ); *//*  */
}
/* private static */ template List<char*> divide(char* input, template BiFunction</* DivideState */, /*  Character */, /*  DivideState */> folder){/* DivideState current = new DivideState(input); *//* while (true) {
            var maybePopped = current.pop();
            if (maybePopped.isEmpty()) {
                break;
            }

            var popped = maybePopped.orElse(null);
            var c = popped.left;
            var state = popped.right;
            current = foldSingleQuotes(state, c)
                    .or(() -> foldDoubleQuotes(state, c))
                    .orElseGet(() -> folder.apply(state, c));
        } *//* return current.advance().segments; *//*  */
}
/* private static */ template Option</* DivideState */> foldDoubleQuotes(/* DivideState */ state, /* char */ c){/* if (c != '\"') {
            return new None<>();
        } *//* var appended = state.append(c); *//* while (true) {
            var maybeTuple = appended.popAndAppendToTuple();
            if (maybeTuple.isEmpty()) {
                break;
            }

            var nextTuple = maybeTuple.orElse(null);
            var next = nextTuple.left;
            appended = nextTuple.right;

            if (next == '\\') {
                appended = appended.popAndAppend().orElse(appended);
            }
            if (next == '\"') {
                break;
            }
        } *//* return new Some<>(appended); *//*  */
}
/* private static */ template Option</* DivideState */> foldSingleQuotes(/* DivideState */ state, /* char */ c){/* if (c != '\'') {
            return new None<>();
        } *//* return state.append(c).pop().map(maybeNextTuple -> {
            var nextChar = maybeNextTuple.left;
            var nextState = maybeNextTuple.right.append(nextChar);

            var withEscaped = nextChar == '\\'
                    ? nextState.popAndAppend().orElse(nextState)
                    : nextState;

            return withEscaped.popAndAppend().orElse(withEscaped);
        } *//* ); *//*  */
}
/* private static */ /* DivideState */ foldValueChar(/* DivideState */ state, /* char */ c){/* if (c == ',' && state.isLevel()) {
            return state.advance();
        } *//* var appended = state.append(c); *//* if (c == '<') {
            return appended.enter();
        } *//* if (c == '>') {
            return appended.exit();
        } *//* return appended; *//*  */
}
/* private static <T extends Node> */ char* generateNodesAsValues(template List</* T */> params){/* return params.iterate()
                .map(t -> t.generate().toSlice())
                .collect(new Joiner(", "))
                .orElse(""); *//*  */
}
/* private static */ char* generateAll(template BiFunction</* StringBuilder */, char*, /*  StringBuilder */> merger, template List<char*> right){/* return right.iterate().fold(new StringBuilder(), merger).toString(); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> methodWithoutContent(/* CompileState */ state, /* Definition */ definition, template List</* Definition */> params, char* content){/* if (!content.equals(";")) {
            return new None<>();
        } *//* String generated; *//* if (state.maybeStructureType.filter(value -> value.type.equals("interface") && value.variants.isEmpty()).isPresent()) {
            var returnType = definition.type;
            var name = definition.name;
            var argumentTypes = params.iterate()
                    .map(Definition::type)
                    .collect(new ListCollector<>())
                    .addFirst(new TypeParameter("S"));

            var functionalType = new Functional(argumentTypes, returnType);
            var definition0 = new Definition(functionalType, name);
            generated = "\n\t" + definition0.generate().toSlice() + ";";
        } *//* else {
            generated = "";
        } *//* return new Some<>(new Tuple<CompileState, String>(state, generated)); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> methodWithContent(/* CompileState */ state, /* Definition */ definition, template List</* Definition */> params, char* withBraces){/* return prefix(withBraces.strip(), "{", withoutStart1 -> {
            return suffix(withoutStart1, "}", content -> {
                return compileAll(state, content, Main::compileFunctionSegment).flatMap(tuple -> {
                    var paramStrings = generateNodesAsValues(params);

                    var generated = definition
                            .mapName(name -> state.maybeStructureType.map(structureType -> name + "_" + structureType.name).orElse(name)).generate().toSlice() + "(" + paramStrings + "){" + tuple.right + "\n}\n";
                    return new Some<>(new Tuple<>(state.addFunction(generated), ""));
                });
            });
        } *//* ); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Definition */)> compileMethodHeader(/* CompileState */ state, char* definition){/* return or(state, definition, Lists.of(
                Main::definition,
                Main::constructor
        )); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Definition */)> constructor(/* CompileState */ state, char* input){/* return split(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (_, name) -> state.maybeStructureType.flatMap(structureType -> {
            if (!structureType.name.equals(name)) {
                return new None<>();
            }

            return new Some<>(new Tuple<>(state, new Definition(Primitive.Auto, name)));
        } *//* )); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> compileFunctionSegment(/* CompileState */ state, char* input){/* return or(state, input.strip(), Lists.of(
                (state1, input1) -> content(state1, input1).map(Tuple.mapRight(content -> content.generate().toSlice()))
        )); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Definition */)> definition(/* CompileState */ state, char* input){/* return split(input.strip(), new InfixSplitter(" ", Main::lastIndexOfSlice), (beforeName, name) -> {
            if (!isSymbol(name)) {
                return new None<>();
            }

            return Main.or(state, beforeName.strip(), Lists.of(
                    (instance, beforeName0) -> definitionWithTypeSeparator(instance, beforeName0, name),
                    (instance, beforeName0) -> definitionWithoutTypeSeparator(instance, beforeName0, name)
            ));
        } *//* ); *//*  */
}
/* private static */ int isSymbol(char* value){/* for (var i = 0; *//* i < value.length(); *//* i++) {
            var c = value.charAt(i);
            if (Character.isLetter(c) || c == '_') {
                continue;
            }
            return false;
        } *//* return true; *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Definition */)> definitionWithoutTypeSeparator(/* CompileState */ state, char* type, char* name){/* return type(state, type).flatMap(typeTuple -> {
            var definition = new Definition(new None<>(), typeTuple.right, name.strip());
            return new Some<>(new Tuple<>(typeTuple.left, definition));
        } *//* ); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Definition */)> definitionWithTypeSeparator(/* CompileState */ state, char* beforeName, char* name){/* return split(beforeName, new TypeSeparatorSplitter(), (beforeType, typeString) -> {
            return type(state, typeString).flatMap(typeTuple -> {
                return new Some<>(new Tuple<>(typeTuple.left, new Definition(new Some<>(beforeType), typeTuple.right, name.strip())));
            });
        } *//* ); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Type */)> type(/* CompileState */ state, char* input){/* return Main.or(state, input, Lists.of(
                Main::primitive,
                Main::template,
                Main::typeParam,
                Main::string,
                wrap(Main::content)
        )); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Type */)> string(/* CompileState */ state, char* input){/* if (input.strip().equals("String")) {
            return new Some<>(new Tuple<>(state, new Ref(Primitive.I8)));
        } *//* else {
            return new None<>();
        } *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Type */)> typeParam(/* CompileState */ state, char* input){/* if (state.maybeStructureType instanceof Some(var structureType)) {
            var stripped = input.strip();
            if (structureType.typeParams.contains(stripped)) {
                return new Some<>(new Tuple<>(state, new TypeParameter(stripped)));
            }
        } *//* return new None<>(); *//*  */
}
/* private static <S, T extends S> */ template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, /*  S */)>> wrap(template BiFunction</* CompileState */, char*, template Option<(/* CompileState */, /*  T */)>> content){/* return (state, input) -> content.apply(state, input).map(Tuple.mapRight(value -> value)); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Type */)> primitive(/* CompileState */ state, char* input){/* var stripped = input.strip(); *//* if (stripped.equals("boolean") || stripped.equals("int")) {
            return new Some<>(new Tuple<>(state, Primitive.I32));
        } *//* return new None<>(); *//*  */
}
/* private static */ template Option<(/* CompileState */, /*  Type */)> template(/* CompileState */ state, char* input){/* return suffix(input.strip(), ">", withoutEnd -> {
            return first(withoutEnd, "<", (base, argumentsString) -> {
                return parseValues(state, argumentsString, Main::type).flatMap(argumentsTuple -> {
                    var arguments = argumentsTuple.right;

                    Type generated;
                    if (base.equals("Function")) {
                        generated = new Functional(Lists.of(arguments.get(0)), arguments.get(1));
                    }
                    else if (base.equals("Supplier")) {
                        generated = new Functional(Lists.empty(), arguments.get(0));
                    }
                    else if (base.equals("Tuple")) {
                        generated = new TupleType(arguments);
                    }
                    else {
                        generated = new Template(base, arguments);
                    }

                    return new Some<>(new Tuple<>(argumentsTuple.left, generated));
                });
            });
        } *//* ); *//*  */
}
/* private static */ template Option</* Integer */> lastIndexOfSlice(char* input, char* infix){/* var index = input.lastIndexOf(infix); *//* return index == -1 ? new None<Integer>() : new Some<>(index); *//*  */
}
/* private static */ template Option<(/* CompileState */, char*)> prefix(char* input, char* prefix, template Option<(/* CompileState */, char*)> (*mapper)(char*)){/* if (!input.startsWith(prefix)) {
            return new None<>();
        } *//* var slice = input.substring(prefix.length()); *//* return mapper.apply(slice); *//*  */
}
/* private static <T> */ template Option</* T */> suffix(char* input, char* suffix, template Option</* T */> (*mapper)(char*)){/* if (!input.endsWith(suffix)) {
            return new None<>();
        } *//* var slice = input.substring(0, input.length() - suffix.length()); *//* return mapper.apply(slice); *//*  */
}
/* private static */ char* generatePlaceholder(char* input){/* return "/* " + input + " */"; *//*  */
}
/* private static <T> */ template Option</* T */> first(char* input, char* infix, template BiFunction<char*, char*, template Option</* T */>> mapper){/* return split(input, new InfixSplitter(infix, Main::firstIndexOfSlice), mapper); *//*  */
}
/* private static <T> */ template Option</* T */> split(char* input, /* Splitter */ splitter, template BiFunction<char*, char*, template Option</* T */>> mapper){/* return splitter.split(input).flatMap(tuple -> {
            return mapper.apply(tuple.left, tuple.right);
        } *//* ); *//*  */
}
/* private static */ template Option</* Integer */> firstIndexOfSlice(char* input, char* infix){/* var index = input.indexOf(infix); *//* return index == -1 ? new None<Integer>() : new Some<>(index); *//*  */
}

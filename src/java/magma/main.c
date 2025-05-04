/* package magma;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main  */{
};
/* private */struct record CompileState(/* List<String> */ functions, struct int counter){
	/* public CompileState() */{
		this(/* ArrayList<> */::new(), /*  0 */);
	}
	/* public CompileState addFunction(String generated) */{
		this.functions.add(generated);
		return this;
	}
	/* public Tuple<String, CompileState> createName(String category) */{
		auto name = /*  category + this.counter */;
		auto next = struct CompileState::new(this.functions, /*  this.counter + 1 */);
		return /* Tuple<> */::new(name, next);
	}
}
/* private record *//* Tuple<A, */ B>(struct A left, struct B right){
}
/* private */struct record Definition(/* List<String> */ annotations, /* List<String> */ modifiers, char* beforeType, char* type, char* name){
	/* private String generate() */{
		/* String annotationsStrings */;
		if (this.annotations.isEmpty()){
			/* annotationsStrings  */ = "";
		}
		/* else */{
			/* annotationsStrings  */ = /*  this.annotations.stream().map(value -> "@" + value).collect(Collectors.joining("\n")) + "\n" */;
		}
		auto modifiersString = /*  this.modifiers.isEmpty() ? "" : String.join(" ", this.modifiers) + " " */;
		auto beforeTypeString = /*  this.beforeType.isEmpty() ? "" : generatePlaceholder(this.beforeType) */;
		/* return annotationsStrings + modifiersString + beforeTypeString + this.type + " " + this.name */;
	}
}
auto lambda0(auto error){
	return System.err.println(error.display());
}
auto lambda1(auto input){
	auto output = compile(input);
	return writeTarget(output);
}
/* public static */void main(/*  */){
	readSource().match(lambda1, struct Some::new).ifPresent(lambda0);
}
/* private static */char* compile(char* input){
	auto state = struct CompileState::new();
	auto stripped = input.strip();
	if (stripped.endsWith("}")){
		auto withoutEnd = stripped.substring(/* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = withoutEnd.indexOf("{");
		if (/* contentStart >= 0 */){
			auto left = withoutEnd.substring(/* 0 */, contentStart);
			auto right = withoutEnd.substring(/* contentStart + "{".length() */);
			auto result = compileRoot(right, state);
			auto joined = String.join("", result.left.functions);
			/* return generatePlaceholder(left) + "{\n};\n" + joined + result.right */;
		}
	}
	return generatePlaceholder(stripped);
}
/* private static Tuple<CompileState, *//* String> */ compileRoot(char* input, struct CompileState state){
	return compileStatements(state, input, struct Main::compileClassSegment);
}
/* private static Tuple<CompileState, *//* String> */ compileStatements(struct CompileState state, char* input, /*  BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* String>> */ mapper){
	return compileAll(state, input, struct Main::foldStatementChar, mapper, struct Main::mergeStatements);
}
/* private static Tuple<CompileState, *//* String> */ compileAll(struct CompileState initial, char* input, /* 
            BiFunction<DivideState */, /*  Character */, /* DivideState> */ folder, /* 
            BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* String>> */ mapper, /* 
            BiFunction<StringBuilder */, /*  String */, /* StringBuilder> */ merger){
	auto tuple = parseAll(initial, input, folder, mapper);
	return /* Tuple<> */::new(tuple.left, /*  generateAll(tuple.right */, /*  merger) */);
}
/* private static <T> Tuple<CompileState, *//* List<T>> */ parseAll(struct CompileState initial, char* input, /* 
            BiFunction<DivideState */, /*  Character */, /* DivideState> */ folder, /* 
            BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* T>> */ mapper){
	auto segments = divideAll(input, folder);
	auto current = initial;
	auto compiled = /* ArrayList<T> */::new();
	/* for (var segment : segments) */{
		auto mapped = mapper.apply(current, segment);
		/* current  */ = mapped.left;
		compiled.add(mapped.right);
	}
	return /* Tuple<> */::new(current, compiled);
}
/* private static */char* generateAll(/* List<String> */ elements, /*  BiFunction<StringBuilder */, /*  String */, /* StringBuilder> */ merger){
	auto output = struct StringBuilder::new();
	/* for (var element : elements) */{
		/* output  */ = merger.apply(output, element);
	}
	return output.toString();
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, char* mapped){
	return output.append(mapped);
}
/* private static *//* List<String> */ divideAll(char* input, /*  BiFunction<DivideState */, /*  Character */, /* DivideState> */ folder){
	auto current = struct DivideState::new(input);
	/* while (true) */{
		auto maybePopped = current.pop();
		if (/* !(maybePopped instanceof Some(var popped)) */){
			/* break */;
		}
		/* current  */ = foldSingleQuotes(popped.right, popped.left).or(/* () -> foldDoubleQuotes(popped.right */, /*  popped.left) */).orElseGet(/* () -> folder.apply(popped.right */, /*  popped.left) */);
	}
	return current.advance().segments;
}
/* private static *//* Option<DivideState> */ foldDoubleQuotes(struct DivideState state, struct char maybeDoubleQuotes){
	if (/* maybeDoubleQuotes != '' */){
		return /* None<> */::new();
	}
	auto current = state.append(maybeDoubleQuotes);
	@hile (true) {
@f (!(current.popAndAppendToTuple() instanceof Some(var popped))) {
@reak;
@
auto next = /*  popped.left;
            current = popped.right;

            if (next == '') {
                current = current.popAndAppendToOption().orElse(current);
            }
            if (next == '') {
                break;
            }
        }

        return new Some<>(current) */;
}
/* private static *//* Option<DivideState> */ foldSingleQuotes(struct DivideState state, struct char c){
	if (/* c != '' */){
		return /* None<> */::new();
	}
	auto appended = state.append(c);
	return appended.pop().flatMap(/* popped -> popped.left == ' popped.right.popAndAppendToOption() : new Some<>(popped.right) */).flatMap(struct DivideState::popAndAppendToOption);
}
/* private static */struct DivideState foldStatementChar(struct DivideState state, struct char c){
	auto appended = state.append(c);
	if (/* c == ' && appended.isLevel() */){
		return appended.advance();
	}
	if (/* c == ' && appended.isShallow() */){
		return appended.advance().exit();
	}
	/* else */struct if (c = /* = ' || c == ') {
            return appended.enter();
        }
        else if (c == ' || c == ') {
            return appended.exit();
        }
        return appended */;
}
/* private static Tuple<CompileState, *//* String> */ compileClassSegment(struct CompileState state, char* input){
	auto stripped = input.strip();
	if (stripped.endsWith("}")){
		auto withoutContentEnd = stripped.substring(/* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = withoutContentEnd.indexOf("{");
		if (/* contentStart >= 0 */){
			auto beforeContent = withoutContentEnd.substring(/* 0 */, contentStart).strip();
			auto right = withoutContentEnd.substring(/* contentStart + "{".length() */);
			if (beforeContent.endsWith(")")){
				auto withoutParamEnd = beforeContent.substring(/* 0 */, /*  beforeContent.length() - ")".length() */);
				auto paramStart = withoutParamEnd.indexOf("(");
				if (/* paramStart >= 0 */){
					auto definitionString = withoutParamEnd.substring(/* 0 */, paramStart);
					auto inputParams = withoutParamEnd.substring(/* paramStart + "(".length() */);
					if (/* parseDefinition(state, definitionString) instanceof Some(var definitionTuple) */){
						auto definition = definitionTuple.right;
						auto paramsTuple = compileValues(definitionTuple.left, inputParams, struct Main::compileDefinitionOrPlaceholder);
						auto paramsState = paramsTuple.left;
						auto paramsString = paramsTuple.right;
						auto header = /*  definition.generate() + "(" + paramsString + ")" */;
						if (definition.modifiers.contains("expect")){
							return /* Tuple<> */::new(paramsState, /*  header + ";\n" */);
						}
						auto statementsTuple = compileStatements(paramsState, right, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  1) */);
						auto generated = /*  header + "{" + statementsTuple.right + "\n}\n" */;
						return /* Tuple<> */::new(statementsTuple.left.addFunction(generated), "");
					}
				}
			}
		}
	}
	return /* Tuple<> */::new(state, /*  generatePlaceholder(stripped) + "\n" */);
}
/* private static Tuple<CompileState, *//* String> */ compileFunctionSegment(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip();
	if (stripped.isEmpty()){
		return /* Tuple<> */::new(state, "");
	}
	auto indent = "\n" + "\t".repeat(depth);
	if (stripped.endsWith(";")){
		auto withoutEnd = stripped.substring(/* 0 */, /*  stripped.length() - ";".length() */);
		auto statements = compileFunctionStatementValue(withoutEnd, state, depth);
		return /* Tuple<> */::new(statements.left, /*  indent + statements.right + ";" */);
	}
	if (stripped.endsWith("}")){
		auto withoutEnd = stripped.substring(/* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = withoutEnd.indexOf("{");
		if (/* contentStart >= 0 */){
			auto beforeContent = withoutEnd.substring(/* 0 */, contentStart);
			auto content = withoutEnd.substring(/* contentStart + "{".length() */);
			auto newContent = compileStatements(state, content, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  depth + 1) */);
			auto string = compileBlockHeader(newContent.left, beforeContent, depth);
			return /* Tuple<> */::new(string.left, /*  indent + string.right + "{" + newContent.right + indent + "}" */);
		}
	}
	return /* Tuple<> */::new(state, generatePlaceholder(stripped));
}
/* private static Tuple<CompileState, *//* String> */ compileBlockHeader(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip();
	if (stripped.startsWith("if")){
		auto withoutPrefix = stripped.substring("if".length()).strip();
		if (/* withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")") */){
			auto value = withoutPrefix.substring(/* 1 */, /*  withoutPrefix.length() - 1 */);
			auto tuple = compileValueOrPlaceholder(state, value, depth);
			return /* Tuple<> */::new(tuple.left, "if (" + tuple.right + ")");
		}
	}
	return /* Tuple<> */::new(state, generatePlaceholder(stripped));
}
/* private static Tuple<CompileState, *//* String> */ compileFunctionStatementValue(char* input, struct CompileState state, struct int depth){
	return compileReturn(state, input, depth).or(/* () -> compileInvokable(state */, input, /*  depth).map(tuple -> new Tuple<>(tuple.left */, tuple.right.generate(/* )) */)).or(/* () -> compileAssignment(state */, input, /*  depth) */).orElseGet(/* () -> new Tuple<>(state */, generatePlaceholder(/* input) */));
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileReturn(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip();
	if (stripped.startsWith("return ")){
		auto right = stripped.substring("return ".length());
		if (/* compileValue(state, right, depth) instanceof Some(var other) */){
			return /* Some<> */::new(/* new Tuple<>(other.left */, /*  "return " + other.right) */);
		}
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileAssignment(struct CompileState state, char* input, struct int depth){
	auto valueSeparator = input.indexOf("=");
	if (/* valueSeparator < 0 */){
		return /* None<> */::new();
	}
	auto left = input.substring(/* 0 */, valueSeparator);
	auto right = input.substring(/* valueSeparator + "=".length() */);
	auto definitionTuple = compileDefinitionOrPlaceholder(state, left);
	auto valueTuple = compileValueOrPlaceholder(definitionTuple.left, right, depth);
	return /* Some<> */::new(/* new Tuple<>(valueTuple.left */, /*  definitionTuple.right + " = " + valueTuple.right) */);
}
/* private static Tuple<CompileState, *//* String> */ compileDefinitionOrPlaceholder(struct CompileState state, char* input){
	return compileDefinition(state, input).orElseGet(/* () -> new Tuple<>(state */, generatePlaceholder(/* input) */));
}
auto lambda2(auto tuple){
	return /* Tuple<> */::new(/* tuple.left( */);
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileDefinition(struct CompileState state, char* input){
	return parseDefinition(state, input).map(lambda2, tuple.right().generate(/* ) */));
}
/* private static Option<Tuple<CompileState, *//* Definition>> */ parseDefinition(struct CompileState state, char* input){
	auto stripped = input.strip();
	auto valueSeparator = stripped.lastIndexOf(" ");
	if (/* valueSeparator >= 0 */){
		auto beforeName = stripped.substring(/* 0 */, valueSeparator);
		auto name = stripped.substring(/* valueSeparator + " ".length() */).strip();
		auto annotationSeparator = beforeName.lastIndexOf("\n");
		if (/* annotationSeparator < 0 */){
			return definitionWithAnnotations(state, Collections.emptyList(), beforeName, name);
		}
		auto annotationsArray = beforeName.substring(/* 0 */, annotationSeparator).strip().split(Pattern.quote("\n"));
		/* var annotations = Arrays.stream(annotationsArray)
                    .map(String */::strip).map(/* slice -> slice.isEmpty() ? "" : slice.substring(1) */).toList();
		auto beforeName0 = beforeName.substring(/* annotationSeparator + "\n".length() */);
		return definitionWithAnnotations(state, annotations, /*  beforeName0 */, name);
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* Definition>> */ definitionWithAnnotations(struct CompileState state, /* List<String> */ annotations, char* withoutAnnotations, char* name){
	auto stripped = withoutAnnotations.strip();
	auto typeSeparator = stripped.lastIndexOf(" ");
	if (/* typeSeparator >= 0 */){
		auto beforeType = stripped.substring(/* 0 */, typeSeparator);
		auto type = stripped.substring(/* typeSeparator + " ".length() */);
		return definitionWithBeforeType(state, annotations, beforeType, type, name);
	}
	return definitionWithBeforeType(state, annotations, "", stripped, name);
}
/* private static Some<Tuple<CompileState, *//* Definition>> */ definitionWithBeforeType(struct CompileState state, /* List<String> */ annotations, char* beforeType, char* type, char* name){
	auto typeResult = compileType(state, type);
	auto newAnnotations = /* ArrayList<String> */::new();
	auto newModifiers = /* ArrayList<String> */::new();
	/* for (var annotation : annotations) */{
		if (annotation.equals("Actual")){
			newModifiers.add("expect");
		}
		/* else */{
			newAnnotations.add(annotation);
		}
	}
	return /* Some<> */::new(/* new Tuple<>(typeResult.left */, /*  new Definition(newAnnotations */, newModifiers, beforeType, typeResult.right, /*  name)) */);
}
/* private static Tuple<CompileState, *//* String> */ compileType(struct CompileState state, char* input){
	auto stripped = input.strip();
	if (stripped.equals("var")){
		return /* Tuple<> */::new(state, "auto");
	}
	if (stripped.equals("void")){
		return /* Tuple<> */::new(state, "void");
	}
	if (stripped.equals("String")){
		return /* Tuple<> */::new(state, "char*");
	}
	if (isSymbol(stripped)){
		return /* Tuple<> */::new(state, /*  "struct " + stripped */);
	}
	return /* Tuple<> */::new(state, generatePlaceholder(stripped));
}
/* private static Option<Tuple<CompileState, *//* Invocation>> */ compileInvokable(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip();
	if (/* !stripped.endsWith(")") */){
		return /* None<> */::new();
	}
	auto withoutEnd = stripped.substring(/* 0 */, /*  stripped.length() - ")".length() */);
	auto divisions = divideAll(withoutEnd, struct Main::foldInvocationStart);
	if (/* divisions.size() < 2 */){
		return /* None<> */::new();
	}
	auto joined = String.join("", /*  divisions.subList(0 */, divisions.size(/* ) - 1 */));
	auto caller = joined.substring(/* 0 */, /*  joined.length() - ")".length() */);
	auto inputArguments = divisions.getLast();
	auto argumentsTuple = parseValues(state, inputArguments, /*  (state1 */, /*  input1) -> parseValue(state1 */, /*  input1 */, /*  depth)
                .orElseGet(() -> new Tuple<>(state1 */, struct Content::new(/* input1)) */));
	auto argumentState = argumentsTuple.left;
	auto arguments = argumentsTuple.right;
	if (caller.startsWith("new ")){
		auto withoutPrefix = caller.substring("new ".length());
		auto callerTuple = compileType(argumentState, withoutPrefix);
		return /* Some<> */::new(/* new Tuple<>(callerTuple.left */, /*  new Invocation(new MethodAccess(callerTuple.right */, /*  "new") */, /*  arguments)) */);
	}
	if (/* parseValue(argumentState, caller, depth) instanceof Some(var callerTuple) */){
		return /* Some<> */::new(/* new Tuple<>(callerTuple.left */, /*  new Invocation(callerTuple.right */, /*  arguments)) */);
	}
	return /* None<> */::new();
}
/* private static Tuple<CompileState, *//* String> */ compileValues(struct CompileState state, char* input, /*  BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* String>> */ compiler){
	auto tuple = parseValues(state, input, compiler);
	return /* Tuple<> */::new(tuple.left, generateValues(tuple.right));
}
/* private static */char* generateValues(/* List<String> */ elements){
	return generateAll(elements, struct Main::mergeValues);
}
/* private static <T> Tuple<CompileState, *//* List<T>> */ parseValues(struct CompileState state, char* input, /* 
            BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* T>> */ compiler){
	return parseAll(state, input, struct Main::foldValueChar, compiler);
}
/* private static */struct StringBuilder mergeValues(struct StringBuilder cache, char* element){
	if (cache.isEmpty()){
		return cache.append(element);
	}
	return cache.append(", ").append(element);
}
/* private static */struct DivideState foldValueChar(struct DivideState state, struct char c){
	if (/* c == ' && state.isLevel() */){
		return state.advance();
	}
	return state.append(c);
}
/* private static Tuple<CompileState, *//* String> */ compileValueOrPlaceholder(struct CompileState state, char* input, struct int depth){
	return compileValue(state, input, depth).orElseGet(/* () -> {
            return new Tuple<>(state */, /*  generatePlaceholder(input));
        } */);
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileValue(struct CompileState state, char* input, struct int depth){
	return parseValue(state, input, depth).map(/* tuple -> new Tuple<>(tuple.left */, tuple.right.generate(/* ) */));
}
/* private static Option<Tuple<CompileState, *//* Value>> */ parseValue(struct CompileState state, char* input, struct int depth){
	return or(state, input, List.of(/* type(Main */::compileString), /* 
                type((state0 */, /*  input0) -> compileLambda(state0 */, /*  input0 */, /*  depth)) */, /* 
                type((state0 */, /*  input0) -> compileInvokable(state0 */, /*  input0 */, /*  depth)) */, /* 
                type((state0 */, /*  input0) -> compileAccess(state0 */, /*  input0 */, /*  depth)) */, type(struct Main::compileSymbolValue), type(struct Main::compileMethodReference)));
}
/* private static Option<Tuple<CompileState, *//* Value>> */ or(struct CompileState state, char* input, /* 
            List<BiFunction<CompileState */, /*  String */, /*  Option<Tuple<CompileState */, /* Value>>>> */ rules){
	/* for (var rule : rules) */{
		auto applied = rule.apply(state, input);
		if (applied.isPresent()){
			return applied;
		}
	}
	return /* None<> */::new();
}
/* private static <S, T extends S> BiFunction<CompileState, String, Option<Tuple<CompileState, *//* S>>> */ type(/* BiFunction<CompileState */, /*  String */, /*  Option<Tuple<CompileState */, /* T>>> */ mapper){
	/* return (state, input) -> mapper.apply(state, input).map(value -> new Tuple<>(value.left, value.right)) */;
}
/* private static Option<Tuple<CompileState, *//* MethodAccess>> */ compileMethodReference(struct CompileState state, char* input){
	auto functionSeparator = input.strip().indexOf("::");
	if (/* functionSeparator < 0 */){
		return /* None<> */::new();
	}
	auto left = input.strip().substring(/* 0 */, functionSeparator);
	/* var right = input.strip().substring(functionSeparator + " */::".length()).strip();
	auto leftTuple = compileType(state, left);
	return /* Some<> */::new(/* new Tuple<>(leftTuple.left */, /*  new MethodAccess(leftTuple.right */, /*  right)) */);
}
/* private static Option<Tuple<CompileState, *//* Symbol>> */ compileSymbolValue(struct CompileState state, char* input){
	auto stripped = input.strip();
	if (isSymbol(stripped)){
		return /* Some<> */::new(/* new Tuple<CompileState */, /*  Symbol>(state */, struct Symbol::new(/* stripped) */));
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* Access>> */ compileAccess(struct CompileState state, char* input, struct int depth){
	auto separator = input.strip().lastIndexOf(".");
	if (/* separator < 0 */){
		return /* None<> */::new();
	}
	auto parent = input.strip().substring(/* 0 */, separator);
	auto child = input.strip().substring(/* separator + ".".length() */);
	if (/* !isSymbol(child) || !(compileValue(state, parent, depth) instanceof Some(var tuple)) */){
		return /* None<> */::new();
	}
	return /* Some<> */::new(/* new Tuple<CompileState */, /*  Access>(tuple.left */, /*  new Access(child */, /*  tuple.right)) */);
}
/* private static Option<Tuple<CompileState, *//* StringValue>> */ compileString(struct CompileState state, char* input){
	auto stripped = input.strip();
	if (/* !stripped.startsWith("\"") || !stripped.endsWith("\"") */){
		return /* None<> */::new();
	}
	return /* Some<> */::new(/* new Tuple<>(state */, /*  new StringValue(stripped.substring(1 */, stripped.length(/* ) - 1)) */));
}
/* private static Option<Tuple<CompileState, *//* Symbol>> */ compileLambda(struct CompileState state, char* input, struct int depth){
	auto arrowIndex = input.indexOf("->");
	if (/* arrowIndex >= 0 */){
		auto beforeArrow = input.substring(/* 0 */, arrowIndex).strip();
		auto afterArrow = input.substring(/* arrowIndex + "->".length() */);
		if (isSymbol(beforeArrow)){
			auto withBraces = afterArrow.strip();
			/* if (withBraces.startsWith(" */{
				@) && withBraces.endsWith("}")) {
auto content = /*  withBraces.substring(1, withBraces.length() - 1);
                    var result = compileStatements(state, content, (state1, input1) -> compileFunctionSegment(state1, input1, depth));
                    return assembleLambda(result.left, beforeArrow, result.right) */;
			}
			/* else */{
				if (/* compileValue(state, afterArrow, depth) instanceof Some(var valueTuple) */){
					return assembleLambda(valueTuple.left, beforeArrow, "\n\treturn " + valueTuple.right + ";");
				}
			}
		}
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* Symbol>> */ assembleLambda(struct CompileState state, char* beforeArrow, char* content){
	auto nameTuple = state.createName("lambda");
	auto name = nameTuple.left;
	return /* Some<> */::new(/* Tuple<> */::new(/* nameTuple.right.addFunction("auto " + name + "(auto " + beforeArrow + "){" + content + "\n}\n" */), struct Symbol::new(/* name) */));
}
/* private static */struct boolean isSymbol(char* input){
	auto stripped = input.strip();
	/* for (var i = 0; i < stripped.length(); i++) */{
		auto c = stripped.charAt(i);
		if (Character.isLetter(c)){
			/* continue */;
		}
		return false;
	}
	return true;
}
/* private static */struct DivideState foldInvocationStart(struct DivideState state, struct char c){
	auto appended = state.append(c);
	struct if (c = /* = ') {
            var entered = appended.enter();
            if (appended.isShallow()) {
                return entered.advance();
            }
            else {
                return entered;
            }
        }
        if (c == ') {
            return appended.exit();
        }
        return appended */;
}
/* private static */char* generatePlaceholder(char* stripped){
	return "/* " + stripped + " */";
}
/* private interface Result<T, X> {
        <R> R match(Function<T, R> whenOk, Function<X, R> whenErr);
    } */
/* private interface Option<T> {
        void ifPresent(Consumer<T> consumer);

        T orElseGet(Supplier<T> supplier);

        Option<T> or(Supplier<Option<T>> other);

        boolean isPresent();

        <R> Option<R> map(Function<T, R> mapper);

        <R> Option<R> flatMap(Function<T, Option<R>> mapper);

        T orElse(T other);
    } */
/* private interface Error {
        String display();
    } */
/* private @interface Actual {
    } */
/* private interface Value {
        String generate();
    } */
/* private record IOError(IOException exception) implements Error {
        @Override
        public String display() {
            var writer = new StringWriter();
            this.exception.printStackTrace(new PrintWriter(writer));
            return writer.toString();
        }
    } */
/* record None<T>() implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return supplier.get();
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return other.get();
        }

        @Override
        public boolean isPresent() {
            return false;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new None<>();
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return new None<>();
        }

        @Override
        public T orElse(T other) {
            return other;
        }
    } */
/* private record Some<T>(T value) implements Option<T> {
        @Override
        public void ifPresent(Consumer<T> consumer) {
            consumer.accept(this.value);
        }

        @Override
        public T orElseGet(Supplier<T> supplier) {
            return this.value;
        }

        @Override
        public Option<T> or(Supplier<Option<T>> other) {
            return this;
        }

        @Override
        public boolean isPresent() {
            return true;
        }

        @Override
        public <R> Option<R> map(Function<T, R> mapper) {
            return new Some<>(mapper.apply(this.value));
        }

        @Override
        public <R> Option<R> flatMap(Function<T, Option<R>> mapper) {
            return mapper.apply(this.value);
        }

        @Override
        public T orElse(T other) {
            return this.value;
        }
    } */
/* private record Ok<T, X>(T value) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenOk.apply(this.value);
        }
    } */
/* private record Err<T, X>(X error) implements Result<T, X> {
        @Override
        public <R> R match(Function<T, R> whenOk, Function<X, R> whenErr) {
            return whenErr.apply(this.error);
        }
    } */
/* private static class DivideState {
        private final String input;
        private final List<String> segments;
        private final int index;
        private StringBuilder buffer;
        private int depth;

        public DivideState(String input) {
            this(input, new ArrayList<>(), new StringBuilder(), 0, 0);
        }

        public DivideState(String input, List<String> segments, StringBuilder buffer, int index, int depth) {
            this.input = input;
            this.segments = segments;
            this.index = index;
            this.depth = depth;
            this.buffer = buffer;
        }

        private DivideState enter() {
            this.depth = this.depth + 1;
            return this;
        }

        private DivideState exit() {
            this.depth = this.depth - 1;
            return this;
        }

        private DivideState advance() {
            this.segments().add(this.buffer.toString());
            this.buffer = new StringBuilder();
            return this;
        }

        public List<String> segments() {
            return this.segments;
        }

        private boolean isShallow() {
            return this.depth == 1;
        }

        public boolean isLevel() {
            return this.depth == 0;
        }

        public Option<DivideState> popAndAppendToOption() {
            return this.popAndAppendToTuple().map(Tuple::right);
        }

        public Option<Tuple<Character, DivideState>> popAndAppendToTuple() {
            return this.pop().map(tuple -> {
                return new Tuple<>(tuple.left, tuple.right.append(tuple.left));
            });
        }

        private DivideState append(char c) {
            this.buffer.append(c);
            return this;
        }

        public Option<Tuple<Character, DivideState>> pop() {
            if (this.index < this.input.length()) {
                var c = this.input.charAt(this.index);
                return new Some<>(new Tuple<>(c, new DivideState(this.input, this.segments, this.buffer, this.index + 1, this.depth)));
            }
            else {
                return new None<>();
            }
        }
    } */
/* private record StringValue(String value) implements Value {
        @Override
        public String generate() {
            return "\"" + this.value + "\"";
        }
    } */
/* private record Symbol(String value) implements Value {
        @Override
        public String generate() {
            return this.value;
        }
    } */
/* private record Invocation(Value caller, List<Value> arguments) implements Value {
        @Override
        public String generate() {
            var joined = this.arguments.stream()
                    .map(Value::generate)
                    .collect(Collectors.joining(", "));

            return this.caller.generate() + "(" + joined + ")";
        }
    } */
/* private record Access(String child, String parent) implements Value {
        @Override
        public String generate() {
            return this.parent() + "." + this.child();
        }
    } */
/* private record MethodAccess(String parent, String child) implements Value {
        @Override
        public String generate() {
            return this.parent() + "::" + this.child();
        }
    } */
/* private record Content(String input) implements Value {
        @Override
        public String generate() {
            return generatePlaceholder(this.input);
        }
    } */
/* public static final Path SOURCE = Paths.get(".", "src", "java", "magma", "Main.java"); */
/* public static final Path TARGET = SOURCE.resolveSibling("main.c"); */
expect /* private static *//* Option<IOError> */ writeTarget(char* output);
expect /* private static Result<String, *//* IOError> */ readSource(/*  */);
/*  */

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main  */{
};
/* private */struct record Frame(/* Map<String */, /* Integer> */ counters, /* List<String> */ statements){
	/* public Frame() */{
		this(/* HashMap<> */::new(), /* ArrayList<> */::new());
	}
	/* public Tuple<String, Frame> createName(String category) */{
		if (/* !this.counters.containsKey(category) */){
			this.counters.put(this.counters, category, /*  0 */);
		}
		auto oldCounter = this.counters.get(this.counters, category);
		auto name = /*  category + oldCounter */;
		auto newCounter = /*  oldCounter + 1 */;
		this.counters.put(this.counters, category, newCounter);
		return /* Tuple<> */::new(name, this);
	}
	/* public Frame addStatement(String statement) */{
		this.statements.add(this.statements, statement);
		return this;
	}
}
/* private */struct record CompileState(/* List<String> */ functions, /* List<Frame> */ frames){
	/* public CompileState() */{
		this(/* ArrayList<> */::new(), /* ArrayList<> */::new(Collections.singletonList(Collections, struct Frame::new())));
	}
	/* public CompileState addFunction(String generated) */{
		this.functions.add(this.functions, generated);
		return this;
	}
	/* public Tuple<String, CompileState> createName(String category) */{
		auto frame = this.frames.getLast(this.frames, );
		auto nameTuple = frame.createName(frame, category);
		this.frames.set(this.frames, /* this.frames.size() - 1 */, nameTuple.right);
		return /* Tuple<> */::new(nameTuple.left, this);
	}
	/* public CompileState addStatement(String statement) */{
	auto local0 = this.frames.getLast(this.frames, );
		addStatement(local0, statement);
		return this;
	}
	/* public CompileState enter() */{
		this.frames.add(this.frames, struct Frame::new());
		return this;
	}
	/* public CompileState exit() */{
		this.frames.removeLast(this.frames, );
		return this;
	}
}
/* private record *//* Tuple<A, */ B>(struct A left, struct B right){
}
/* private */struct record Definition(/* List<String> */ annotations, /* List<String> */ modifiers, char* beforeType, char* type, char* name){
	/* private String generate() */{
		/* String annotationsStrings */;
		if (this.annotations.isEmpty(this.annotations, )){
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
	return System.err.println(System.err, error.display(error, ));
}
auto lambda1(auto input){
	auto output = compile(input);
	return writeTarget(output);
}
/* public static */void main(/*  */){
	auto local0 = readSource();
	auto local1 = match(local0, lambda1, struct Some::new);
	ifPresent(local1, lambda0);
}
/* private static */char* compile(char* input){
	auto state = struct CompileState::new();
	auto stripped = input.strip(input, );
	if (stripped.endsWith(stripped, "}")){
		auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = withoutEnd.indexOf(withoutEnd, "{");
		if (/* contentStart >= 0 */){
			auto left = withoutEnd.substring(withoutEnd, /* 0 */, contentStart);
			auto right = withoutEnd.substring(withoutEnd, /* contentStart + "{".length() */);
			auto result = compileRoot(right, state);
			auto joined = String.join(String, "", result.left.functions);
			/* return generatePlaceholder(left) + "{\n};\n" + joined + result.right */;
		}
	}
	return generatePlaceholder(stripped);
}
/* private static Tuple<CompileState, *//* String> */ compileRoot(char* input, struct CompileState state){
	return compileStatements(state, input, struct Main::compileClassSegment);
}
/* private static Tuple<CompileState, *//* String> */ compileStatements(struct CompileState state, char* input, /*  BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* String>> */ mapper){
	auto tuple = parseStatements(state, input, mapper);
	return /* Tuple<> */::new(tuple.left, generateStatements(tuple.right));
}
/* private static */char* generateStatements(/* List<String> */ elements){
	return generateAll(elements, struct Main::mergeStatements);
}
/* private static Tuple<CompileState, *//* List<String>> */ parseStatements(struct CompileState state, char* input, /*  BiFunction<CompileState */, /*  String */, /*  Tuple<CompileState */, /* String>> */ mapper){
	return parseAll(state, input, struct Main::foldStatementChar, mapper);
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
		auto mapped = mapper.apply(mapper, current, segment);
		/* current  */ = mapped.left;
		compiled.add(compiled, mapped.right);
	}
	return /* Tuple<> */::new(current, compiled);
}
/* private static *//* List<String> */ divideAll(char* input, /*  BiFunction<DivideState */, /*  Character */, /* DivideState> */ folder){
	auto current = struct DivideState::new(input);
	/* while (true) */{
	auto local0 = foldSingleQuotes(popped.right, popped.left);
	auto local1 = or(local0, /* () -> foldDoubleQuotes(popped.right */, /*  popped.left) */);
		auto maybePopped = current.pop(current, );
		if (/* !(maybePopped instanceof Some(var popped)) */){
			/* break */;
		}
		/* current  */ = orElseGet(local1, /* () -> folder.apply(popped.right */, /*  popped.left) */);
	}
	return current.advance(current, ).segments;
}
/* private static *//* Option<DivideState> */ foldDoubleQuotes(struct DivideState state, struct char maybeDoubleQuotes){
	if (/* maybeDoubleQuotes != '' */){
		return /* None<> */::new();
	}
	auto current = state.append(state, maybeDoubleQuotes);
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
	auto local0 = appended.pop(appended, );
	auto local1 = flatMap(local0, /* popped -> popped.left == ' popped.right.popAndAppendToOption() : new Some<>(popped.right) */);
	if (/* c != '' */){
		return /* None<> */::new();
	}
	auto appended = state.append(state, c);
	return flatMap(local1, struct DivideState::popAndAppendToOption);
}
/* private static */char* generateAll(/* List<String> */ elements, /*  BiFunction<StringBuilder */, /*  String */, /* StringBuilder> */ merger){
	auto output = struct StringBuilder::new();
	/* for (var element : elements) */{
		/* output  */ = merger.apply(merger, output, element);
	}
	return output.toString(output, );
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, char* mapped){
	return output.append(output, mapped);
}
/* private static */struct DivideState foldStatementChar(struct DivideState state, struct char c){
	auto appended = state.append(state, c);
	if (/* c == ' && appended.isLevel() */){
		return appended.advance(appended, );
	}
	if (/* c == ' && appended.isShallow() */){
	auto local0 = appended.advance(appended, );
		return exit(local0, );
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
	auto stripped = input.strip(input, );
	if (stripped.endsWith(stripped, "}")){
		auto withoutContentEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = withoutContentEnd.indexOf(withoutContentEnd, "{");
		if (/* contentStart >= 0 */){
	auto local0 = withoutContentEnd.substring(withoutContentEnd, /* 0 */, contentStart);
			auto beforeContent = strip(local0, );
			auto right = withoutContentEnd.substring(withoutContentEnd, /* contentStart + "{".length() */);
			if (beforeContent.endsWith(beforeContent, ")")){
				auto withoutParamEnd = beforeContent.substring(beforeContent, /* 0 */, /*  beforeContent.length() - ")".length() */);
				auto paramStart = withoutParamEnd.indexOf(withoutParamEnd, "(");
				if (/* paramStart >= 0 */){
					auto definitionString = withoutParamEnd.substring(withoutParamEnd, /* 0 */, paramStart);
					auto inputParams = withoutParamEnd.substring(withoutParamEnd, /* paramStart + "(".length() */);
					if (/* parseDefinition(state, definitionString) instanceof Some(var definitionTuple) */){
	auto local0 = statementsState.frames(statementsState, );
	auto local1 = statementsState.exit(statementsState, );
						auto definition = definitionTuple.right;
						auto paramsTuple = compileValues(definitionTuple.left, inputParams, struct Main::compileDefinitionOrPlaceholder);
						auto paramsState = paramsTuple.left;
						auto paramsString = paramsTuple.right;
						auto header = /*  definition.generate() + "(" + paramsString + ")" */;
						if (definition.modifiers.contains(definition.modifiers, "expect")){
							return /* Tuple<> */::new(paramsState, /*  header + ";\n" */);
						}
						auto statementsTuple = parseStatements(paramsState.enter(paramsState, ), right, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  1) */);
						auto statementsState = statementsTuple.left;
						auto statements = statementsTuple.right;
						auto oldStatements = /* ArrayList<String> */::new();
						oldStatements.addAll(oldStatements, getLast(local0, ).statements);
						oldStatements.addAll(oldStatements, statements);
						auto generated = /*  header + "{" + generateStatements(oldStatements) + "\n}\n" */;
						return /* Tuple<> */::new(addFunction(local1, generated), "");
					}
				}
			}
		}
	}
	return /* Tuple<> */::new(state, /*  generatePlaceholder(stripped) + "\n" */);
}
/* private static Tuple<CompileState, *//* String> */ compileFunctionSegment(struct CompileState state, char* input, struct int depth){
	auto local0 = "\n" + "\t";
	auto local1 = compileBlock(state, depth, stripped, indent);
	auto stripped = input.strip(input, );
	if (stripped.isEmpty(stripped, )){
		return /* Tuple<> */::new(state, "");
	}
	auto indent = repeat(local0, depth);
	if (stripped.endsWith(stripped, ";")){
		auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - ";".length() */);
		auto statements = compileFunctionStatementValue(withoutEnd, state, depth);
		return /* Tuple<> */::new(statements.left, /*  indent + statements.right + ";" */);
	}
	return orElseGet(local1, /* () -> new Tuple<>(state */, generatePlaceholder(/* stripped) */));
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileBlock(struct CompileState state, struct int depth, char* stripped, char* indent){
	if (/* !stripped.endsWith("}") */){
		return /* None<> */::new();
	}
	auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - "}".length() */);
	auto contentStart = withoutEnd.indexOf(withoutEnd, "{");
	if (/* contentStart < 0 */){
		return /* None<> */::new();
	}
	auto beforeContent = withoutEnd.substring(withoutEnd, /* 0 */, contentStart);
	auto content = withoutEnd.substring(withoutEnd, /* contentStart + "{".length() */);
	struct CompileState state2 = state.enter(state, );
	auto tuple = parseStatements(/* state2 */, content, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  depth + 1) */);
	auto oldStatements = /* ArrayList<String> */::new();
	oldStatements.addAll(oldStatements, tuple.left.frames.getLast(tuple.left.frames, ).statements);
	oldStatements.addAll(oldStatements, tuple.right);
	auto string = compileBlockHeader(tuple.left.exit(tuple.left, ), beforeContent, depth);
	return /* Some<> */::new(/* new Tuple<>(string.left */, /*  indent + string.right + "{" + generateStatements(oldStatements) + indent + "}") */);
}
/* private static Tuple<CompileState, *//* String> */ compileBlockHeader(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip(input, );
	if (stripped.startsWith(stripped, "if")){
	auto local0 = "if";
	auto local1 = "if";
	auto local2 = stripped.substring(stripped, length(local1, ));
		auto withoutPrefix = strip(local2, );
		if (/* withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")") */){
			auto value = withoutPrefix.substring(withoutPrefix, /* 1 */, /*  withoutPrefix.length() - 1 */);
			auto tuple = compileValueOrPlaceholder(state, value, depth);
			return /* Tuple<> */::new(tuple.left, "if (" + tuple.right + ")");
		}
	}
	return /* Tuple<> */::new(state, generatePlaceholder(stripped));
}
/* private static Tuple<CompileState, *//* String> */ compileFunctionStatementValue(char* input, struct CompileState state, struct int depth){
	auto local0 = compileReturn(state, input, depth);
	auto local1 = or(local0, /* () -> compileInvokable(state */, input, /*  depth).map(tuple -> new Tuple<>(tuple.left */, tuple.right.generate(tuple.right, /* )) */));
	auto local2 = or(local1, /* () -> compileAssignment(state */, input, /*  depth) */);
	return orElseGet(local2, /* () -> new Tuple<>(state */, generatePlaceholder(/* input) */));
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileReturn(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip(input, );
	if (stripped.startsWith(stripped, "return ")){
	auto local0 = "return ";
	auto local1 = "return ";
		auto right = stripped.substring(stripped, length(local1, ));
		if (/* compileValue(state, right, depth) instanceof Some(var other) */){
			return /* Some<> */::new(/* new Tuple<>(other.left */, /*  "return " + other.right) */);
		}
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileAssignment(struct CompileState state, char* input, struct int depth){
	auto valueSeparator = input.indexOf(input, "=");
	if (/* valueSeparator < 0 */){
		return /* None<> */::new();
	}
	auto left = input.substring(input, /* 0 */, valueSeparator);
	auto right = input.substring(input, /* valueSeparator + "=".length() */);
	auto definitionTuple = compileDefinitionOrPlaceholder(state, left);
	auto valueTuple = compileValueOrPlaceholder(definitionTuple.left, right, depth);
	return /* Some<> */::new(/* new Tuple<>(valueTuple.left */, /*  definitionTuple.right + " = " + valueTuple.right) */);
}
/* private static Tuple<CompileState, *//* String> */ compileDefinitionOrPlaceholder(struct CompileState state, char* input){
	auto local0 = compileDefinition(state, input);
	return orElseGet(local0, /* () -> new Tuple<>(state */, generatePlaceholder(/* input) */));
}
auto lambda0(auto tuple){
	return /* Tuple<> */::new(/* tuple.left( */);
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileDefinition(struct CompileState state, char* input){
	auto local0 = tuple.right(tuple, );
	auto local1 = parseDefinition(state, input);
	return map(local1, lambda0, generate(local0, /* ) */));
}
/* private static Option<Tuple<CompileState, *//* Definition>> */ parseDefinition(struct CompileState state, char* input){
	auto stripped = input.strip(input, );
	auto valueSeparator = stripped.lastIndexOf(stripped, " ");
	if (/* valueSeparator >= 0 */){
	auto local0 = stripped.substring(stripped, /* valueSeparator + " ".length() */);
	auto local1 = beforeName.substring(beforeName, /* 0 */, annotationSeparator);
	auto local2 = strip(local1, );
	auto local3 = /* var annotations = Arrays.stream(annotationsArray)
                    .map(String */::strip);
	auto local4 = map(local3, /* slice -> slice.isEmpty() ? "" : slice.substring(1) */);
		auto beforeName = stripped.substring(stripped, /* 0 */, valueSeparator);
		auto name = strip(local0, );
		auto annotationSeparator = beforeName.lastIndexOf(beforeName, "\n");
		if (/* annotationSeparator < 0 */){
			return definitionWithAnnotations(state, Collections.emptyList(Collections, ), beforeName, name);
		}
		auto annotationsArray = split(local2, Pattern.quote(Pattern, "\n"));
		toList(local4, );
		auto beforeName0 = beforeName.substring(beforeName, /* annotationSeparator + "\n".length() */);
		return definitionWithAnnotations(state, annotations, /*  beforeName0 */, name);
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* Definition>> */ definitionWithAnnotations(struct CompileState state, /* List<String> */ annotations, char* withoutAnnotations, char* name){
	auto stripped = withoutAnnotations.strip(withoutAnnotations, );
	auto typeSeparator = stripped.lastIndexOf(stripped, " ");
	if (/* typeSeparator >= 0 */){
		auto beforeType = stripped.substring(stripped, /* 0 */, typeSeparator);
		auto type = stripped.substring(stripped, /* typeSeparator + " ".length() */);
		return definitionWithBeforeType(state, annotations, beforeType, type, name);
	}
	return definitionWithBeforeType(state, annotations, "", stripped, name);
}
/* private static Some<Tuple<CompileState, *//* Definition>> */ definitionWithBeforeType(struct CompileState state, /* List<String> */ annotations, char* beforeType, char* type, char* name){
	auto typeResult = compileType(state, type);
	auto newAnnotations = /* ArrayList<String> */::new();
	auto newModifiers = /* ArrayList<String> */::new();
	/* for (var annotation : annotations) */{
		if (annotation.equals(annotation, "Actual")){
			newModifiers.add(newModifiers, "expect");
		}
		/* else */{
			newAnnotations.add(newAnnotations, annotation);
		}
	}
	return /* Some<> */::new(/* new Tuple<>(typeResult.left */, /*  new Definition(newAnnotations */, newModifiers, beforeType, typeResult.right, /*  name)) */);
}
/* private static Tuple<CompileState, *//* String> */ compileType(struct CompileState state, char* input){
	auto stripped = input.strip(input, );
	if (stripped.equals(stripped, "var")){
		return /* Tuple<> */::new(state, "auto");
	}
	if (stripped.equals(stripped, "void")){
		return /* Tuple<> */::new(state, "void");
	}
	if (stripped.equals(stripped, "String")){
		return /* Tuple<> */::new(state, "char*");
	}
	if (isSymbol(stripped)){
		return /* Tuple<> */::new(state, /*  "struct " + stripped */);
	}
	return /* Tuple<> */::new(state, generatePlaceholder(stripped));
}
/* private static Option<Tuple<CompileState, *//* Invocation>> */ compileInvokable(struct CompileState state, char* input, struct int depth){
	auto stripped = input.strip(input, );
	if (/* !stripped.endsWith(")") */){
		return /* None<> */::new();
	}
	auto withoutEnd = stripped.substring(stripped, /* 0 */, /*  stripped.length() - ")".length() */);
	auto divisions = divideAll(withoutEnd, struct Main::foldInvocationStart);
	if (/* divisions.size() < 2 */){
		return /* None<> */::new();
	}
	auto joined = String.join(String, "", /*  divisions.subList(0 */, divisions.size(divisions, /* ) - 1 */));
	auto callerString = joined.substring(joined, /* 0 */, /*  joined.length() - ")".length() */);
	auto inputArguments = divisions.getLast(divisions, );
	auto argumentsTuple = parseValues(state, inputArguments, /*  (state1 */, /*  input1) -> parseValue(state1 */, /*  input1 */, /*  depth)
                .orElseGet(() -> new Tuple<>(state1 */, struct Content::new(/* input1)) */));
	auto argumentState = argumentsTuple.left;
	auto oldArguments = argumentsTuple.right;
	if (callerString.startsWith(callerString, "new ")){
	auto local0 = "new ";
	auto local1 = "new ";
		auto withoutPrefix = callerString.substring(callerString, length(local1, ));
		auto callerTuple = compileType(argumentState, withoutPrefix);
		return /* Some<> */::new(/* new Tuple<>(callerTuple.left */, /*  new Invocation(new MethodAccess(callerTuple.right */, /*  "new") */, /*  oldArguments)) */);
	}
	if (/* parseValue(argumentState, callerString, depth) instanceof Some(var callerTuple) */){
		auto callerState = callerTuple.left;
		auto oldCaller = callerTuple.right;
		auto nextState = callerState;
		struct Value newCaller = oldCaller;
		auto newArguments = /* ArrayList<Value> */::new();
		if (/* oldCaller instanceof DataAccess(Value parent, var property) */){
			/* Value thisArgument */;
			if (/* parent instanceof Symbol symbol */){
				/* thisArgument  */ = symbol;
			}
			/* else if (parent instanceof DataAccess access) */{
				/* thisArgument  */ = access;
			}
			/* else */{
				auto localTuple = nextState.createName(nextState, "local");
				/* thisArgument  */ = struct Symbol::new(localTuple.left);
				/* newCaller  */ = struct Symbol::new(property);
				/* nextState  */ = localTuple.right.addStatement(localTuple.right, "\n\tauto " + localTuple.left + " = " + parent.generate() + ";");
			}
			newArguments.add(newArguments, thisArgument);
		}
		newArguments.addAll(newArguments, oldArguments);
		return /* Some<> */::new(/* new Tuple<>(nextState */, /*  new Invocation(newCaller */, /*  newArguments)) */);
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
	auto local0 = cache.append(cache, ", ");
	if (cache.isEmpty(cache, )){
		return cache.append(cache, element);
	}
	return append(local0, element);
}
/* private static */struct DivideState foldValueChar(struct DivideState state, struct char c){
	if (/* c == ' && state.isLevel() */){
		return state.advance(state, );
	}
	return state.append(state, c);
}
/* private static Tuple<CompileState, *//* String> */ compileValueOrPlaceholder(struct CompileState state, char* input, struct int depth){
	auto local0 = compileValue(state, input, depth);
	return orElseGet(local0, /* () -> {
            return new Tuple<>(state */, /*  generatePlaceholder(input));
        } */);
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileValue(struct CompileState state, char* input, struct int depth){
	auto local0 = parseValue(state, input, depth);
	return map(local0, /* tuple -> new Tuple<>(tuple.left */, tuple.right.generate(tuple.right, /* ) */));
}
/* private static Option<Tuple<CompileState, *//* Value>> */ parseValue(struct CompileState state, char* input, struct int depth){
	return or(state, input, List.of(List, /* type(Main */::compileString), /* 
                type((state0 */, /*  input0) -> compileLambda(state0 */, /*  input0 */, /*  depth)) */, /* 
                type((state0 */, /*  input0) -> compileInvokable(state0 */, /*  input0 */, /*  depth)) */, /* 
                type((state0 */, /*  input0) -> compileAccess(state0 */, /*  input0 */, /*  depth)) */, type(struct Main::compileSymbolValue), type(struct Main::compileMethodReference)));
}
/* private static Option<Tuple<CompileState, *//* Value>> */ or(struct CompileState state, char* input, /* 
            List<BiFunction<CompileState */, /*  String */, /*  Option<Tuple<CompileState */, /* Value>>>> */ rules){
	/* for (var rule : rules) */{
		auto applied = rule.apply(rule, state, input);
		if (applied.isPresent(applied, )){
			return applied;
		}
	}
	return /* None<> */::new();
}
/* private static <S, T extends S> BiFunction<CompileState, String, Option<Tuple<CompileState, *//* S>>> */ type(/* BiFunction<CompileState */, /*  String */, /*  Option<Tuple<CompileState */, /* T>>> */ mapper){
	/* return (state, input) -> mapper.apply(state, input).map(value -> new Tuple<>(value.left, value.right)) */;
}
/* private static Option<Tuple<CompileState, *//* MethodAccess>> */ compileMethodReference(struct CompileState state, char* input){
	auto local0 = input.strip(input, );
	auto local1 = input.strip(input, );
	auto local2 = /* functionSeparator + " */::";
	auto local3 = /* var right = input.strip().substring(functionSeparator + " */::".length());
	auto functionSeparator = indexOf(local0, "::");
	if (/* functionSeparator < 0 */){
		return /* None<> */::new();
	}
	auto left = substring(local1, /* 0 */, functionSeparator);
	strip(local3, );
	auto leftTuple = compileType(state, left);
	return /* Some<> */::new(/* new Tuple<>(leftTuple.left */, /*  new MethodAccess(leftTuple.right */, /*  right)) */);
}
/* private static Option<Tuple<CompileState, *//* Symbol>> */ compileSymbolValue(struct CompileState state, char* input){
	auto stripped = input.strip(input, );
	if (isSymbol(stripped)){
		return /* Some<> */::new(/* new Tuple<CompileState */, /*  Symbol>(state */, struct Symbol::new(/* stripped) */));
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* DataAccess>> */ compileAccess(struct CompileState state, char* input, struct int depth){
	auto local0 = input.strip(input, );
	auto local1 = input.strip(input, );
	auto local2 = input.strip(input, );
	auto separator = lastIndexOf(local0, ".");
	if (/* separator < 0 */){
		return /* None<> */::new();
	}
	auto parent = substring(local1, /* 0 */, separator);
	auto child = substring(local2, /* separator + ".".length() */);
	if (/* !isSymbol(child) || !(parseValue(state, parent, depth) instanceof Some(var tuple)) */){
		return /* None<> */::new();
	}
	return /* Some<> */::new(/* new Tuple<CompileState */, /*  DataAccess>(tuple.left */, /*  new DataAccess(tuple.right */, /*  child)) */);
}
/* private static Option<Tuple<CompileState, *//* StringValue>> */ compileString(struct CompileState state, char* input){
	auto stripped = input.strip(input, );
	if (/* !stripped.startsWith("\"") || !stripped.endsWith("\"") */){
		return /* None<> */::new();
	}
	return /* Some<> */::new(/* new Tuple<>(state */, /*  new StringValue(stripped.substring(1 */, stripped.length(stripped, /* ) - 1)) */));
}
/* private static Option<Tuple<CompileState, *//* Symbol>> */ compileLambda(struct CompileState state, char* input, struct int depth){
	auto arrowIndex = input.indexOf(input, "->");
	if (/* arrowIndex >= 0 */){
	auto local0 = input.substring(input, /* 0 */, arrowIndex);
		auto beforeArrow = strip(local0, );
		auto afterArrow = input.substring(input, /* arrowIndex + "->".length() */);
		if (isSymbol(beforeArrow)){
			auto withBraces = afterArrow.strip(afterArrow, );
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
	auto nameTuple = state.createName(state, "lambda");
	auto name = nameTuple.left;
	return /* Some<> */::new(/* Tuple<> */::new(/* nameTuple.right.addFunction("auto " + name + "(auto " + beforeArrow + "){" + content + "\n}\n" */), struct Symbol::new(/* name) */));
}
/* private static */struct boolean isSymbol(char* input){
	auto stripped = input.strip(input, );
	/* for (var i = 0; i < stripped.length(); i++) */{
		auto c = stripped.charAt(stripped, i);
		if (Character.isLetter(Character, c)){
			/* continue */;
		}
		return false;
	}
	return true;
}
/* private static */struct DivideState foldInvocationStart(struct DivideState state, struct char c){
	auto appended = state.append(state, c);
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
/* private record DataAccess(Value parent, String child) implements Value {
        @Override
        public String generate() {
            return this.parent.generate() + "." + this.child;
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

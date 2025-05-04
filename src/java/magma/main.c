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
	auto local0 = this.counters;
	auto local1 = this.counters;
		if (/* !this.counters.containsKey(category) */){
	auto local0 = this.counters;
			put(local0, category, /*  0 */);
		}
		auto oldCounter = get(local0, category);
		auto name = /*  category + oldCounter */;
		auto newCounter = /*  oldCounter + 1 */;
		put(local1, category, newCounter);
		return /* Tuple<> */::new(name, this);
	}
	/* public Frame addStatement(String statement) */{
	auto local0 = this.statements;
		add(local0, statement);
		return this;
	}
}
/* private */struct record CompileState(/* List<String> */ functions, /* List<Frame> */ frames){
	/* public CompileState() */{
	auto local0 = Collections;
		this(/* ArrayList<> */::new(), /* ArrayList<> */::new(singletonList(local0, struct Frame::new())));
	}
	/* public CompileState addFunction(String generated) */{
	auto local0 = this.functions;
		add(local0, generated);
		return this;
	}
	/* public Tuple<String, CompileState> createName(String category) */{
	auto local0 = this.frames;
	auto local1 = frame;
	auto local2 = this.frames;
		auto frame = getLast(local0, );
		auto nameTuple = createName(local1, category);
		set(local2, /* this.frames.size() - 1 */, nameTuple.right);
		return /* Tuple<> */::new(nameTuple.left, this);
	}
	/* public CompileState addStatement(String statement) */{
	auto local0 = this.frames;
	auto local1 = getLast(local0, );
		addStatement(local1, statement);
		return this;
	}
	/* public CompileState enter() */{
	auto local0 = this.frames;
		add(local0, struct Frame::new());
		return this;
	}
	/* public CompileState exit() */{
	auto local0 = this.frames;
		removeLast(local0, );
		return this;
	}
}
/* private record *//* Tuple<A, */ B>(struct A left, struct B right){
}
/* private */struct record Definition(/* List<String> */ annotations, /* List<String> */ modifiers, char* beforeType, char* type, char* name){
	/* private String generate() */{
	auto local0 = this.annotations;
		/* String annotationsStrings */;
		if (isEmpty(local0, )){
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
	return println(local1, display(local0, ));
}
auto lambda1(auto input){
	auto output = compile(input);
	return writeTarget(output);
}
/* public static */void main(/*  */){
	auto local0 = error;
	auto local1 = System.err;
	auto local2 = readSource();
	auto local3 = match(local2, lambda1, struct Some::new);
	ifPresent(local3, lambda0);
}
/* private static */char* compile(char* input){
	auto local0 = input;
	auto local1 = stripped;
	auto state = struct CompileState::new();
	auto stripped = strip(local0, );
	if (endsWith(local1, "}")){
	auto local0 = stripped;
	auto local1 = withoutEnd;
		auto withoutEnd = substring(local0, /* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = indexOf(local1, "{");
		if (/* contentStart >= 0 */){
	auto local0 = withoutEnd;
	auto local1 = withoutEnd;
	auto local2 = String;
			auto left = substring(local0, /* 0 */, contentStart);
			auto right = substring(local1, /* contentStart + "{".length() */);
			auto result = compileRoot(right, state);
			auto joined = join(local2, "", result.left.functions);
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
	auto local0 = mapper;
	auto local1 = compiled;
		auto mapped = apply(local0, current, segment);
		/* current  */ = mapped.left;
		add(local1, mapped.right);
	}
	return /* Tuple<> */::new(current, compiled);
}
/* private static *//* List<String> */ divideAll(char* input, /*  BiFunction<DivideState */, /*  Character */, /* DivideState> */ folder){
	auto local0 = current;
	auto current = struct DivideState::new(input);
	/* while (true) */{
	auto local0 = current;
	auto local1 = foldSingleQuotes(popped.right, popped.left);
	auto local2 = or(local1, /* () -> foldDoubleQuotes(popped.right */, /*  popped.left) */);
		auto maybePopped = pop(local0, );
		if (/* !(maybePopped instanceof Some(var popped)) */){
			/* break */;
		}
		/* current  */ = orElseGet(local2, /* () -> folder.apply(popped.right */, /*  popped.left) */);
	}
	return advance(local0, ).segments;
}
/* private static *//* Option<DivideState> */ foldDoubleQuotes(struct DivideState state, struct char maybeDoubleQuotes){
	auto local0 = state;
	if (/* maybeDoubleQuotes != '' */){
		return /* None<> */::new();
	}
	auto current = append(local0, maybeDoubleQuotes);
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
	auto local0 = state;
	auto local1 = appended;
	auto local2 = pop(local1, );
	auto local3 = flatMap(local2, /* popped -> popped.left == ' popped.right.popAndAppendToOption() : new Some<>(popped.right) */);
	if (/* c != '' */){
		return /* None<> */::new();
	}
	auto appended = append(local0, c);
	return flatMap(local3, struct DivideState::popAndAppendToOption);
}
/* private static */char* generateAll(/* List<String> */ elements, /*  BiFunction<StringBuilder */, /*  String */, /* StringBuilder> */ merger){
	auto local0 = output;
	auto output = struct StringBuilder::new();
	/* for (var element : elements) */{
	auto local0 = merger;
		/* output  */ = apply(local0, output, element);
	}
	return toString(local0, );
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, char* mapped){
	auto local0 = output;
	return append(local0, mapped);
}
/* private static */struct DivideState foldStatementChar(struct DivideState state, struct char c){
	auto local0 = state;
	auto appended = append(local0, c);
	if (/* c == ' && appended.isLevel() */){
	auto local0 = appended;
		return advance(local0, );
	}
	if (/* c == ' && appended.isShallow() */){
	auto local0 = appended;
	auto local1 = advance(local0, );
		return exit(local1, );
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
	auto local0 = input;
	auto local1 = stripped;
	auto stripped = strip(local0, );
	if (endsWith(local1, "}")){
	auto local0 = stripped;
	auto local1 = withoutContentEnd;
		auto withoutContentEnd = substring(local0, /* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = indexOf(local1, "{");
		if (/* contentStart >= 0 */){
	auto local0 = withoutContentEnd;
	auto local1 = substring(local0, /* 0 */, contentStart);
	auto local2 = withoutContentEnd;
	auto local3 = beforeContent;
			auto beforeContent = strip(local1, );
			auto right = substring(local2, /* contentStart + "{".length() */);
			if (endsWith(local3, ")")){
	auto local0 = beforeContent;
	auto local1 = withoutParamEnd;
				auto withoutParamEnd = substring(local0, /* 0 */, /*  beforeContent.length() - ")".length() */);
				auto paramStart = indexOf(local1, "(");
				if (/* paramStart >= 0 */){
	auto local0 = withoutParamEnd;
	auto local1 = withoutParamEnd;
					auto definitionString = substring(local0, /* 0 */, paramStart);
					auto inputParams = substring(local1, /* paramStart + "(".length() */);
					if (/* parseDefinition(state, definitionString) instanceof Some(var definitionTuple) */){
	auto local0 = definition.modifiers;
	auto local1 = paramsState;
	auto local2 = paramsState;
	auto local3 = statementsState;
	auto local4 = frames(local3, );
	auto local5 = oldStatements;
	auto local6 = oldStatements;
	auto local7 = statementsState;
	auto local8 = exit(local7, );
						auto definition = definitionTuple.right;
						auto paramsTuple = compileValues(definitionTuple.left, inputParams, struct Main::compileDefinitionOrPlaceholder);
						auto paramsState = paramsTuple.left;
						auto paramsString = paramsTuple.right;
						auto header = /*  definition.generate() + "(" + paramsString + ")" */;
						if (contains(local0, "expect")){
							return /* Tuple<> */::new(paramsState, /*  header + ";\n" */);
						}
						auto statementsTuple = parseStatements(enter(local2, ), right, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  1) */);
						auto statementsState = statementsTuple.left;
						auto statements = statementsTuple.right;
						auto oldStatements = /* ArrayList<String> */::new();
						addAll(local5, getLast(local4, ).statements);
						addAll(local6, statements);
						auto generated = /*  header + "{" + generateStatements(oldStatements) + "\n}\n" */;
						return /* Tuple<> */::new(addFunction(local8, generated), "");
					}
				}
			}
		}
	}
	return /* Tuple<> */::new(state, /*  generatePlaceholder(stripped) + "\n" */);
}
/* private static Tuple<CompileState, *//* String> */ compileFunctionSegment(struct CompileState state, char* input, struct int depth){
	auto local0 = input;
	auto local1 = stripped;
	auto local2 = "\n" + "\t";
	auto local3 = stripped;
	auto local4 = compileBlock(state, depth, stripped, indent);
	auto stripped = strip(local0, );
	if (isEmpty(local1, )){
		return /* Tuple<> */::new(state, "");
	}
	auto indent = repeat(local2, depth);
	if (endsWith(local3, ";")){
	auto local0 = stripped;
		auto withoutEnd = substring(local0, /* 0 */, /*  stripped.length() - ";".length() */);
		auto statements = compileFunctionStatementValue(withoutEnd, state, depth);
		return /* Tuple<> */::new(statements.left, /*  indent + statements.right + ";" */);
	}
	return orElseGet(local4, /* () -> new Tuple<>(state */, generatePlaceholder(/* stripped) */));
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileBlock(struct CompileState state, struct int depth, char* stripped, char* indent){
	auto local0 = stripped;
	auto local1 = withoutEnd;
	auto local2 = withoutEnd;
	auto local3 = withoutEnd;
	auto local4 = state;
	auto local5 = tuple.left.frames;
	auto local6 = oldStatements;
	auto local7 = oldStatements;
	auto local8 = tuple.left;
	auto local9 = tuple.left;
	if (/* !stripped.endsWith("}") */){
		return /* None<> */::new();
	}
	auto withoutEnd = substring(local0, /* 0 */, /*  stripped.length() - "}".length() */);
	auto contentStart = indexOf(local1, "{");
	if (/* contentStart < 0 */){
		return /* None<> */::new();
	}
	auto beforeContent = substring(local2, /* 0 */, contentStart);
	auto content = substring(local3, /* contentStart + "{".length() */);
	struct CompileState state2 = enter(local4, );
	auto tuple = parseStatements(/* state2 */, content, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  depth + 1) */);
	auto oldStatements = /* ArrayList<String> */::new();
	addAll(local6, getLast(local5, ).statements);
	addAll(local7, tuple.right);
	auto string = compileBlockHeader(exit(local9, ), beforeContent, depth);
	return /* Some<> */::new(/* new Tuple<>(string.left */, /*  indent + string.right + "{" + generateStatements(oldStatements) + indent + "}") */);
}
/* private static Tuple<CompileState, *//* String> */ compileBlockHeader(struct CompileState state, char* input, struct int depth){
	auto local0 = input;
	auto local1 = stripped;
	auto stripped = strip(local0, );
	if (startsWith(local1, "if")){
	auto local0 = "if";
	auto local1 = "if";
	auto local2 = stripped;
	auto local3 = substring(local2, length(local1, ));
		auto withoutPrefix = strip(local3, );
		if (/* withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")") */){
	auto local0 = withoutPrefix;
			auto value = substring(local0, /* 1 */, /*  withoutPrefix.length() - 1 */);
			auto tuple = compileValueOrPlaceholder(state, value, depth);
			return /* Tuple<> */::new(tuple.left, "if (" + tuple.right + ")");
		}
	}
	return /* Tuple<> */::new(state, generatePlaceholder(stripped));
}
/* private static Tuple<CompileState, *//* String> */ compileFunctionStatementValue(char* input, struct CompileState state, struct int depth){
	auto local0 = tuple.right;
	auto local1 = compileReturn(state, input, depth);
	auto local2 = or(local1, /* () -> compileInvokable(state */, input, /*  depth).map(tuple -> new Tuple<>(tuple.left */, generate(local0, /* )) */));
	auto local3 = or(local2, /* () -> compileAssignment(state */, input, /*  depth) */);
	return orElseGet(local3, /* () -> new Tuple<>(state */, generatePlaceholder(/* input) */));
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileReturn(struct CompileState state, char* input, struct int depth){
	auto local0 = input;
	auto local1 = stripped;
	auto stripped = strip(local0, );
	if (startsWith(local1, "return ")){
	auto local0 = "return ";
	auto local1 = "return ";
	auto local2 = stripped;
		auto right = substring(local2, length(local1, ));
		if (/* compileValue(state, right, depth) instanceof Some(var other) */){
			return /* Some<> */::new(/* new Tuple<>(other.left */, /*  "return " + other.right) */);
		}
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileAssignment(struct CompileState state, char* input, struct int depth){
	auto local0 = input;
	auto local1 = input;
	auto local2 = input;
	auto valueSeparator = indexOf(local0, "=");
	if (/* valueSeparator < 0 */){
		return /* None<> */::new();
	}
	auto left = substring(local1, /* 0 */, valueSeparator);
	auto right = substring(local2, /* valueSeparator + "=".length() */);
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
	auto local0 = tuple;
	auto local1 = right(local0, );
	auto local2 = parseDefinition(state, input);
	return map(local2, lambda0, generate(local1, /* ) */));
}
/* private static Option<Tuple<CompileState, *//* Definition>> */ parseDefinition(struct CompileState state, char* input){
	auto local0 = input;
	auto local1 = stripped;
	auto stripped = strip(local0, );
	auto valueSeparator = lastIndexOf(local1, " ");
	if (/* valueSeparator >= 0 */){
	auto local0 = stripped;
	auto local1 = stripped;
	auto local2 = substring(local1, /* valueSeparator + " ".length() */);
	auto local3 = beforeName;
	auto local4 = Pattern;
	auto local5 = Pattern;
	auto local6 = beforeName;
	auto local7 = substring(local6, /* 0 */, annotationSeparator);
	auto local8 = strip(local7, );
	auto local9 = /* var annotations = Arrays.stream(annotationsArray)
                    .map(String */::strip);
	auto local10 = map(local9, /* slice -> slice.isEmpty() ? "" : slice.substring(1) */);
	auto local11 = beforeName;
		auto beforeName = substring(local0, /* 0 */, valueSeparator);
		auto name = strip(local2, );
		auto annotationSeparator = lastIndexOf(local3, "\n");
		if (/* annotationSeparator < 0 */){
	auto local0 = Collections;
			return definitionWithAnnotations(state, emptyList(local0, ), beforeName, name);
		}
		auto annotationsArray = split(local8, quote(local5, "\n"));
		toList(local10, );
		auto beforeName0 = substring(local11, /* annotationSeparator + "\n".length() */);
		return definitionWithAnnotations(state, annotations, /*  beforeName0 */, name);
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* Definition>> */ definitionWithAnnotations(struct CompileState state, /* List<String> */ annotations, char* withoutAnnotations, char* name){
	auto local0 = withoutAnnotations;
	auto local1 = stripped;
	auto stripped = strip(local0, );
	auto typeSeparator = lastIndexOf(local1, " ");
	if (/* typeSeparator >= 0 */){
	auto local0 = stripped;
	auto local1 = stripped;
		auto beforeType = substring(local0, /* 0 */, typeSeparator);
		auto type = substring(local1, /* typeSeparator + " ".length() */);
		return definitionWithBeforeType(state, annotations, beforeType, type, name);
	}
	return definitionWithBeforeType(state, annotations, "", stripped, name);
}
/* private static Some<Tuple<CompileState, *//* Definition>> */ definitionWithBeforeType(struct CompileState state, /* List<String> */ annotations, char* beforeType, char* type, char* name){
	auto typeResult = compileType(state, type);
	auto newAnnotations = /* ArrayList<String> */::new();
	auto newModifiers = /* ArrayList<String> */::new();
	/* for (var annotation : annotations) */{
	auto local0 = annotation;
		if (equals(local0, "Actual")){
	auto local0 = newModifiers;
			add(local0, "expect");
		}
		/* else */{
	auto local0 = newAnnotations;
			add(local0, annotation);
		}
	}
	return /* Some<> */::new(/* new Tuple<>(typeResult.left */, /*  new Definition(newAnnotations */, newModifiers, beforeType, typeResult.right, /*  name)) */);
}
/* private static Tuple<CompileState, *//* String> */ compileType(struct CompileState state, char* input){
	auto local0 = input;
	auto local1 = stripped;
	auto local2 = stripped;
	auto local3 = stripped;
	auto stripped = strip(local0, );
	if (equals(local1, "var")){
		return /* Tuple<> */::new(state, "auto");
	}
	if (equals(local2, "void")){
		return /* Tuple<> */::new(state, "void");
	}
	if (equals(local3, "String")){
		return /* Tuple<> */::new(state, "char*");
	}
	if (isSymbol(stripped)){
		return /* Tuple<> */::new(state, /*  "struct " + stripped */);
	}
	return /* Tuple<> */::new(state, generatePlaceholder(stripped));
}
/* private static Option<Tuple<CompileState, *//* Invocation>> */ compileInvokable(struct CompileState state, char* input, struct int depth){
	auto local0 = input;
	auto local1 = stripped;
	auto local2 = divisions;
	auto local3 = divisions;
	auto local4 = String;
	auto local5 = joined;
	auto local6 = divisions;
	auto local7 = callerString;
	auto stripped = strip(local0, );
	if (/* !stripped.endsWith(")") */){
		return /* None<> */::new();
	}
	auto withoutEnd = substring(local1, /* 0 */, /*  stripped.length() - ")".length() */);
	auto divisions = divideAll(withoutEnd, struct Main::foldInvocationStart);
	if (/* divisions.size() < 2 */){
		return /* None<> */::new();
	}
	auto joined = join(local4, "", /*  divisions.subList(0 */, size(local3, /* ) - 1 */));
	auto callerString = substring(local5, /* 0 */, /*  joined.length() - ")".length() */);
	auto inputArguments = getLast(local6, );
	auto argumentsTuple = parseValues(state, inputArguments, /*  (state1 */, /*  input1) -> parseValue(state1 */, /*  input1 */, /*  depth)
                .orElseGet(() -> new Tuple<>(state1 */, struct Content::new(/* input1)) */));
	auto argumentState = argumentsTuple.left;
	auto oldArguments = argumentsTuple.right;
	if (startsWith(local7, "new ")){
	auto local0 = "new ";
	auto local1 = "new ";
	auto local2 = callerString;
		auto withoutPrefix = substring(local2, length(local1, ));
		auto callerTuple = compileType(argumentState, withoutPrefix);
		return /* Some<> */::new(/* new Tuple<>(callerTuple.left */, /*  new Invocation(new MethodAccess(callerTuple.right */, /*  "new") */, /*  oldArguments)) */);
	}
	if (/* parseValue(argumentState, callerString, depth) instanceof Some(var callerTuple) */){
	auto local0 = newArguments;
		auto callerState = callerTuple.left;
		auto oldCaller = callerTuple.right;
		auto nextState = callerState;
		struct Value newCaller = oldCaller;
		auto newArguments = /* ArrayList<Value> */::new();
		if (/* oldCaller instanceof DataAccess(Value parent, var property) */){
	auto local0 = nextState;
	auto local1 = newArguments;
	auto local2 = localTuple.right;
			auto localTuple = createName(local0, "local");
			add(local1, struct Symbol::new(localTuple.left));
			/* newCaller  */ = struct Symbol::new(property);
			/* nextState  */ = addStatement(local2, "\n\tauto " + localTuple.left + " = " + parent.generate() + ";");
		}
		addAll(local0, oldArguments);
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
	auto local0 = cache;
	auto local1 = cache;
	auto local2 = append(local1, ", ");
	if (isEmpty(local0, )){
	auto local0 = cache;
		return append(local0, element);
	}
	return append(local2, element);
}
/* private static */struct DivideState foldValueChar(struct DivideState state, struct char c){
	auto local0 = state;
	if (/* c == ' && state.isLevel() */){
	auto local0 = state;
		return advance(local0, );
	}
	return append(local0, c);
}
/* private static Tuple<CompileState, *//* String> */ compileValueOrPlaceholder(struct CompileState state, char* input, struct int depth){
	auto local0 = compileValue(state, input, depth);
	return orElseGet(local0, /* () -> {
            return new Tuple<>(state */, /*  generatePlaceholder(input));
        } */);
}
/* private static Option<Tuple<CompileState, *//* String>> */ compileValue(struct CompileState state, char* input, struct int depth){
	auto local0 = tuple.right;
	auto local1 = parseValue(state, input, depth);
	return map(local1, /* tuple -> new Tuple<>(tuple.left */, generate(local0, /* ) */));
}
/* private static Option<Tuple<CompileState, *//* Value>> */ parseValue(struct CompileState state, char* input, struct int depth){
	auto local0 = List;
	return or(state, input, of(local0, /* type(Main */::compileString), /* 
                type((state0 */, /*  input0) -> compileLambda(state0 */, /*  input0 */, /*  depth)) */, /* 
                type((state0 */, /*  input0) -> compileInvokable(state0 */, /*  input0 */, /*  depth)) */, /* 
                type((state0 */, /*  input0) -> compileAccess(state0 */, /*  input0 */, /*  depth)) */, type(struct Main::compileSymbolValue), type(struct Main::compileMethodReference)));
}
/* private static Option<Tuple<CompileState, *//* Value>> */ or(struct CompileState state, char* input, /* 
            List<BiFunction<CompileState */, /*  String */, /*  Option<Tuple<CompileState */, /* Value>>>> */ rules){
	/* for (var rule : rules) */{
	auto local0 = rule;
	auto local1 = applied;
		auto applied = apply(local0, state, input);
		if (isPresent(local1, )){
			return applied;
		}
	}
	return /* None<> */::new();
}
/* private static <S, T extends S> BiFunction<CompileState, String, Option<Tuple<CompileState, *//* S>>> */ type(/* BiFunction<CompileState */, /*  String */, /*  Option<Tuple<CompileState */, /* T>>> */ mapper){
	/* return (state, input) -> mapper.apply(state, input).map(value -> new Tuple<>(value.left, value.right)) */;
}
/* private static Option<Tuple<CompileState, *//* MethodAccess>> */ compileMethodReference(struct CompileState state, char* input){
	auto local0 = input;
	auto local1 = strip(local0, );
	auto local2 = input;
	auto local3 = strip(local2, );
	auto local4 = /* functionSeparator + " */::";
	auto local5 = /* var right = input.strip().substring(functionSeparator + " */::".length());
	auto functionSeparator = indexOf(local1, "::");
	if (/* functionSeparator < 0 */){
		return /* None<> */::new();
	}
	auto left = substring(local3, /* 0 */, functionSeparator);
	strip(local5, );
	auto leftTuple = compileType(state, left);
	return /* Some<> */::new(/* new Tuple<>(leftTuple.left */, /*  new MethodAccess(leftTuple.right */, /*  right)) */);
}
/* private static Option<Tuple<CompileState, *//* Symbol>> */ compileSymbolValue(struct CompileState state, char* input){
	auto local0 = input;
	auto stripped = strip(local0, );
	if (isSymbol(stripped)){
		return /* Some<> */::new(/* new Tuple<CompileState */, /*  Symbol>(state */, struct Symbol::new(/* stripped) */));
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* DataAccess>> */ compileAccess(struct CompileState state, char* input, struct int depth){
	auto local0 = input;
	auto local1 = strip(local0, );
	auto local2 = input;
	auto local3 = strip(local2, );
	auto local4 = input;
	auto local5 = strip(local4, );
	auto separator = lastIndexOf(local1, ".");
	if (/* separator < 0 */){
		return /* None<> */::new();
	}
	auto parent = substring(local3, /* 0 */, separator);
	auto child = substring(local5, /* separator + ".".length() */);
	if (/* !isSymbol(child) || !(parseValue(state, parent, depth) instanceof Some(var tuple)) */){
		return /* None<> */::new();
	}
	return /* Some<> */::new(/* new Tuple<CompileState */, /*  DataAccess>(tuple.left */, /*  new DataAccess(tuple.right */, /*  child)) */);
}
/* private static Option<Tuple<CompileState, *//* StringValue>> */ compileString(struct CompileState state, char* input){
	auto local0 = input;
	auto local1 = stripped;
	auto stripped = strip(local0, );
	if (/* !stripped.startsWith("\"") || !stripped.endsWith("\"") */){
		return /* None<> */::new();
	}
	return /* Some<> */::new(/* new Tuple<>(state */, /*  new StringValue(stripped.substring(1 */, length(local1, /* ) - 1)) */));
}
/* private static Option<Tuple<CompileState, *//* Symbol>> */ compileLambda(struct CompileState state, char* input, struct int depth){
	auto local0 = input;
	auto arrowIndex = indexOf(local0, "->");
	if (/* arrowIndex >= 0 */){
	auto local0 = input;
	auto local1 = substring(local0, /* 0 */, arrowIndex);
	auto local2 = input;
		auto beforeArrow = strip(local1, );
		auto afterArrow = substring(local2, /* arrowIndex + "->".length() */);
		if (isSymbol(beforeArrow)){
	auto local0 = afterArrow;
			auto withBraces = strip(local0, );
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
	auto local0 = state;
	auto nameTuple = createName(local0, "lambda");
	auto name = nameTuple.left;
	return /* Some<> */::new(/* Tuple<> */::new(/* nameTuple.right.addFunction("auto " + name + "(auto " + beforeArrow + "){" + content + "\n}\n" */), struct Symbol::new(/* name) */));
}
/* private static */struct boolean isSymbol(char* input){
	auto local0 = input;
	auto stripped = strip(local0, );
	/* for (var i = 0; i < stripped.length(); i++) */{
	auto local0 = stripped;
	auto local1 = Character;
		auto c = charAt(local0, i);
		if (isLetter(local1, c)){
			/* continue */;
		}
		return false;
	}
	return true;
}
/* private static */struct DivideState foldInvocationStart(struct DivideState state, struct char c){
	auto local0 = state;
	auto appended = append(local0, c);
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

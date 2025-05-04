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
	auto local0 = this.counters;
	auto local1 = this.counters;
	auto local2 = this.counters;
	auto local3 = statements;
	/* public Frame() */{
		this(/* HashMap<> */::new(), /* ArrayList<> */::new());
	}
	/* public Tuple<String, Frame> createName(String category) */{
		if (/* !this.counters.containsKey(category) */){
			put(local0, category, /*  0 */);
		}
		auto oldCounter = get(local1, category);
		auto name = /*  category + oldCounter */;
		auto newCounter = /*  oldCounter + 1 */;
		put(local2, category, newCounter);
		return /* Tuple<> */::new(name, this);
	}
	/* public Frame addStatement(String statement) */{
		add(local3, statement);
		return this;
	}
}
/* private */struct record CompileState(/* List<String> */ functions, /* List<Frame> */ frames){
	auto local0 = Collections;
	auto local1 = this.functions;
	auto local2 = this.frames;
	auto local3 = frame;
	auto local4 = this.frames;
	auto local5 = this.frames;
	auto local6 = getLast(local5, );
	auto local7 = this.frames;
	auto local8 = this.frames;
	/* public CompileState() */{
		this(/* ArrayList<> */::new(), /* ArrayList<> */::new(singletonList(local0, struct Frame::new())));
	}
	/* public CompileState addFunction(String generated) */{
		add(local1, generated);
		return this;
	}
	/* public Tuple<String, CompileState> createName(String category) */{
		auto frame = getLast(local2, );
		auto nameTuple = createName(local3, category);
		set(local4, /* this.frames.size() - 1 */, nameTuple.right);
		return /* Tuple<> */::new(nameTuple.left, this);
	}
	/* public CompileState addStatement(String statement) */{
		addStatement(local6, statement);
		return this;
	}
	/* public CompileState enter() */{
		add(local7, struct Frame::new());
		return this;
	}
	/* public CompileState exit() */{
		removeLast(local8, );
		return this;
	}
}
/* private record *//* Tuple<A, */ B>(struct A left, struct B right){
}
/* private */struct record Definition(/* List<String> */ annotations, /* List<String> */ modifiers, char* beforeType, char* type, char* name){
	auto local0 = this.annotations;
	/* private String generate() */{
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
	auto local2 = withoutEnd;
	auto local3 = withoutEnd;
	auto local4 = withoutEnd;
	auto local5 = String;
	auto local6 = stripped;
	auto state = struct CompileState::new();
	auto stripped = strip(local0, );
	if (endsWith(local6, "}")){
		auto withoutEnd = substring(local1, /* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = indexOf(local2, "{");
		if (/* contentStart >= 0 */){
			auto left = substring(local3, /* 0 */, contentStart);
			auto right = substring(local4, /* contentStart + "{".length() */);
			auto result = compileRoot(right, state);
			auto joined = join(local5, "", result.left.functions);
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
	auto local0 = mapper;
	auto local1 = compiled;
	auto segments = divideAll(input, folder);
	auto current = initial;
	auto compiled = /* ArrayList<T> */::new();
	/* for (var segment : segments) */{
		auto mapped = apply(local0, current, segment);
		/* current  */ = mapped.left;
		add(local1, mapped.right);
	}
	return /* Tuple<> */::new(current, compiled);
}
/* private static *//* List<String> */ divideAll(char* input, /*  BiFunction<DivideState */, /*  Character */, /* DivideState> */ folder){
	auto local0 = current;
	auto local1 = foldSingleQuotes(popped.right, popped.left);
	auto local2 = or(local1, /* () -> foldDoubleQuotes(popped.right */, /*  popped.left) */);
	auto local3 = current;
	auto current = struct DivideState::new(input);
	/* while (true) */{
		auto maybePopped = pop(local0, );
		if (/* !(maybePopped instanceof Some(var popped)) */){
			/* break */;
		}
		/* current  */ = orElseGet(local2, /* () -> folder.apply(popped.right */, /*  popped.left) */);
	}
	return advance(local3, ).segments;
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
	auto local0 = merger;
	auto local1 = output;
	auto output = struct StringBuilder::new();
	/* for (var element : elements) */{
		/* output  */ = apply(local0, output, element);
	}
	return toString(local1, );
}
/* private static */struct StringBuilder mergeStatements(struct StringBuilder output, char* mapped){
	auto local0 = output;
	return append(local0, mapped);
}
/* private static */struct DivideState foldStatementChar(struct DivideState state, struct char c){
	auto local0 = state;
	auto local1 = appended;
	auto local2 = appended;
	auto local3 = advance(local2, );
	auto appended = append(local0, c);
	if (/* c == ' && appended.isLevel() */){
		return advance(local1, );
	}
	if (/* c == ' && appended.isShallow() */){
		return exit(local3, );
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
	auto local2 = withoutContentEnd;
	auto local3 = withoutContentEnd;
	auto local4 = substring(local3, /* 0 */, contentStart);
	auto local5 = withoutContentEnd;
	auto local6 = beforeContent;
	auto local7 = withoutParamEnd;
	auto local8 = withoutParamEnd;
	auto local9 = withoutParamEnd;
	auto local10 = definition.modifiers;
	auto local11 = paramsState;
	auto local12 = paramsState;
	auto local13 = statementsState;
	auto local14 = frames(local13, );
	auto local15 = oldStatements;
	auto local16 = oldStatements;
	auto local17 = statementsState;
	auto local18 = exit(local17, );
	auto local19 = beforeContent;
	auto local20 = stripped;
	auto stripped = strip(local0, );
	if (endsWith(local20, "}")){
		auto withoutContentEnd = substring(local1, /* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = indexOf(local2, "{");
		if (/* contentStart >= 0 */){
			auto beforeContent = strip(local4, );
			auto right = substring(local5, /* contentStart + "{".length() */);
			if (endsWith(local19, ")")){
				auto withoutParamEnd = substring(local6, /* 0 */, /*  beforeContent.length() - ")".length() */);
				auto paramStart = indexOf(local7, "(");
				if (/* paramStart >= 0 */){
					auto definitionString = substring(local8, /* 0 */, paramStart);
					auto inputParams = substring(local9, /* paramStart + "(".length() */);
					if (/* parseDefinition(state, definitionString) instanceof Some(var definitionTuple) */){
						auto definition = definitionTuple.right;
						auto paramsTuple = compileValues(definitionTuple.left, inputParams, struct Main::compileDefinitionOrPlaceholder);
						auto paramsState = paramsTuple.left;
						auto paramsString = paramsTuple.right;
						auto header = /*  definition.generate() + "(" + paramsString + ")" */;
						if (contains(local10, "expect")){
							return /* Tuple<> */::new(paramsState, /*  header + ";\n" */);
						}
						auto statementsTuple = parseStatements(enter(local12, ), right, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  1) */);
						auto statementsState = statementsTuple.left;
						auto statements = statementsTuple.right;
						auto oldStatements = /* ArrayList<String> */::new();
						addAll(local15, getLast(local14, ).statements);
						addAll(local16, statements);
						auto generated = /*  header + "{" + generateStatements(oldStatements) + "\n}\n" */;
						return /* Tuple<> */::new(addFunction(local18, generated), "");
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
	auto local4 = stripped;
	auto local5 = stripped;
	auto local6 = withoutEnd;
	auto local7 = withoutEnd;
	auto local8 = withoutEnd;
	auto local9 = stripped;
	auto stripped = strip(local0, );
	if (isEmpty(local1, )){
		return /* Tuple<> */::new(state, "");
	}
	auto indent = repeat(local2, depth);
	if (endsWith(local4, ";")){
		auto withoutEnd = substring(local3, /* 0 */, /*  stripped.length() - ";".length() */);
		auto statements = compileFunctionStatementValue(withoutEnd, state, depth);
		return /* Tuple<> */::new(statements.left, /*  indent + statements.right + ";" */);
	}
	if (endsWith(local9, "}")){
		auto withoutEnd = substring(local5, /* 0 */, /*  stripped.length() - "}".length() */);
		auto contentStart = indexOf(local6, "{");
		if (/* contentStart >= 0 */){
			auto beforeContent = substring(local7, /* 0 */, contentStart);
			auto content = substring(local8, /* contentStart + "{".length() */);
			auto newContent = compileStatements(state, content, /*  (state1 */, /*  input1) -> compileFunctionSegment(state1 */, /*  input1 */, /*  depth + 1) */);
			auto string = compileBlockHeader(newContent.left, beforeContent, depth);
			return /* Tuple<> */::new(string.left, /*  indent + string.right + "{" + newContent.right + indent + "}" */);
		}
	}
	return /* Tuple<> */::new(state, generatePlaceholder(stripped));
}
/* private static Tuple<CompileState, *//* String> */ compileBlockHeader(struct CompileState state, char* input, struct int depth){
	auto local0 = input;
	auto local1 = "if";
	auto local2 = "if";
	auto local3 = stripped;
	auto local4 = substring(local3, length(local2, ));
	auto local5 = withoutPrefix;
	auto local6 = stripped;
	auto stripped = strip(local0, );
	if (startsWith(local6, "if")){
		auto withoutPrefix = strip(local4, );
		if (/* withoutPrefix.startsWith("(") && withoutPrefix.endsWith(")") */){
			auto value = substring(local5, /* 1 */, /*  withoutPrefix.length() - 1 */);
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
	auto local1 = "return ";
	auto local2 = "return ";
	auto local3 = stripped;
	auto local4 = stripped;
	auto stripped = strip(local0, );
	if (startsWith(local4, "return ")){
		auto right = substring(local3, length(local2, ));
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
	auto local2 = stripped;
	auto local3 = stripped;
	auto local4 = substring(local3, /* valueSeparator + " ".length() */);
	auto local5 = beforeName;
	auto local6 = Collections;
	auto local7 = Pattern;
	auto local8 = Pattern;
	auto local9 = beforeName;
	auto local10 = substring(local9, /* 0 */, annotationSeparator);
	auto local11 = strip(local10, );
	auto local12 = /* var annotations = Arrays.stream(annotationsArray)
                    .map(String */::strip);
	auto local13 = map(local12, /* slice -> slice.isEmpty() ? "" : slice.substring(1) */);
	auto local14 = beforeName;
	auto stripped = strip(local0, );
	auto valueSeparator = lastIndexOf(local1, " ");
	if (/* valueSeparator >= 0 */){
		auto beforeName = substring(local2, /* 0 */, valueSeparator);
		auto name = strip(local4, );
		auto annotationSeparator = lastIndexOf(local5, "\n");
		if (/* annotationSeparator < 0 */){
			return definitionWithAnnotations(state, emptyList(local6, ), beforeName, name);
		}
		auto annotationsArray = split(local11, quote(local8, "\n"));
		toList(local13, );
		auto beforeName0 = substring(local14, /* annotationSeparator + "\n".length() */);
		return definitionWithAnnotations(state, annotations, /*  beforeName0 */, name);
	}
	return /* None<> */::new();
}
/* private static Option<Tuple<CompileState, *//* Definition>> */ definitionWithAnnotations(struct CompileState state, /* List<String> */ annotations, char* withoutAnnotations, char* name){
	auto local0 = withoutAnnotations;
	auto local1 = stripped;
	auto local2 = stripped;
	auto local3 = stripped;
	auto stripped = strip(local0, );
	auto typeSeparator = lastIndexOf(local1, " ");
	if (/* typeSeparator >= 0 */){
		auto beforeType = substring(local2, /* 0 */, typeSeparator);
		auto type = substring(local3, /* typeSeparator + " ".length() */);
		return definitionWithBeforeType(state, annotations, beforeType, type, name);
	}
	return definitionWithBeforeType(state, annotations, "", stripped, name);
}
/* private static Some<Tuple<CompileState, *//* Definition>> */ definitionWithBeforeType(struct CompileState state, /* List<String> */ annotations, char* beforeType, char* type, char* name){
	auto local0 = newModifiers;
	auto local1 = annotation;
	auto local2 = newAnnotations;
	auto typeResult = compileType(state, type);
	auto newAnnotations = /* ArrayList<String> */::new();
	auto newModifiers = /* ArrayList<String> */::new();
	/* for (var annotation : annotations) */{
		if (equals(local1, "Actual")){
			add(local0, "expect");
		}
		/* else */{
			add(local2, annotation);
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
	auto local7 = "new ";
	auto local8 = "new ";
	auto local9 = callerString;
	auto local10 = callerString;
	auto local11 = nextState;
	auto local12 = newArguments;
	auto local13 = localTuple.right;
	auto local14 = newArguments;
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
	if (startsWith(local10, "new ")){
		auto withoutPrefix = substring(local9, length(local8, ));
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
			auto localTuple = createName(local11, "local");
			add(local12, struct Symbol::new(localTuple.left));
			/* newCaller  */ = struct Symbol::new(property);
			/* nextState  */ = addStatement(local13, "\n\tauto " + localTuple.left + " = " + parent.generate() + ";");
		}
		addAll(local14, oldArguments);
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
	auto local2 = cache;
	auto local3 = append(local2, ", ");
	if (isEmpty(local1, )){
		return append(local0, element);
	}
	return append(local3, element);
}
/* private static */struct DivideState foldValueChar(struct DivideState state, struct char c){
	auto local0 = state;
	auto local1 = state;
	if (/* c == ' && state.isLevel() */){
		return advance(local0, );
	}
	return append(local1, c);
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
	auto local0 = rule;
	auto local1 = applied;
	/* for (var rule : rules) */{
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
	auto local1 = input;
	auto local2 = substring(local1, /* 0 */, arrowIndex);
	auto local3 = input;
	auto local4 = afterArrow;
	auto arrowIndex = indexOf(local0, "->");
	if (/* arrowIndex >= 0 */){
		auto beforeArrow = strip(local2, );
		auto afterArrow = substring(local3, /* arrowIndex + "->".length() */);
		if (isSymbol(beforeArrow)){
			auto withBraces = strip(local4, );
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
	auto local1 = stripped;
	auto local2 = Character;
	auto stripped = strip(local0, );
	/* for (var i = 0; i < stripped.length(); i++) */{
		auto c = charAt(local1, i);
		if (isLetter(local2, c)){
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

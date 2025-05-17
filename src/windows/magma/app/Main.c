#include "./Main.h"
export class Main {
}

auto temp(List<Path> children) {
	return Main/*auto*/.runWithChildren(children/*auto*/, sourceDirectory/*auto*/);
}
auto temp(IOError value) {
	return new Some<IOError>(value/*auto*/);
}
auto temp(IOError error) {
	return error/*auto*/.display(/*auto*/);
}
auto temp(&[I8] displayed) {
	return Console/*auto*/.printErrLn(displayed/*auto*/);
}
static void main() {
	Path sourceDirectory = Files/*auto*/.get(".", "src", "java");
	sourceDirectory/*auto*/.walk(/*auto*/).match(lambdaDefinition/*auto*/, lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).ifPresent(lambdaDefinition/*auto*/);
}
auto temp(Path source) {
	return source/*auto*/.endsWith(".java");
}
auto temp(Path child) {
	return new Source(sourceDirectory/*auto*/, child/*auto*/);
}
auto temp(CompileState state, Source source) {
	return state/*auto*/.addSource(source/*auto*/);
}
auto temp(Tuple2<CompileState, Option<IOError>> current, Source source1) {
	return Main/*auto*/.foldChild(current/*auto*/.left(/*auto*/), current/*auto*/.right(/*auto*/), source1/*auto*/);
}
static Option<IOError> runWithChildren(List<Path> children, Path sourceDirectory) {
	List<Source> sources = children/*auto*/.query(/*auto*/).filter(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).collect(new ListCollector<Source>(/*auto*/));
	CompileState initial = sources/*auto*/.query(/*auto*/).foldWithInitial(ImmutableCompileState/*auto*/.createInitial(/*auto*/), lambdaDefinition/*auto*/);
	return sources/*auto*/.query(/*auto*/).foldWithInitial(Main/*auto*/.createInitialState(initial/*auto*/), lambdaDefinition/*auto*/).right(/*auto*/);
}
static Tuple2<CompileState, Option<IOError>> createInitialState(CompileState state) {
	return new Tuple2Impl<CompileState, Option<IOError>>(state/*auto*/, new None<IOError>(/*auto*/));
}
static Tuple2<CompileState, Option<IOError>> foldChild(CompileState state, Option<IOError> maybeError, Source source) {
	if (maybeError/*auto*/.isPresent(/*auto*/)) {
		return new Tuple2Impl<CompileState, Option<IOError>>(state/*auto*/, maybeError/*auto*/);
	}
	return Main/*auto*/.runWithSource(state/*auto*/, source/*auto*/);
}
auto temp(&[I8] input) {
	return Main/*auto*/.getCompileStateOptionTuple2(state/*auto*/, source/*auto*/, input/*auto*/);
}
auto temp(IOError value) {
	return new Tuple2Impl<CompileState, Option<IOError>>(state/*auto*/, new Some<IOError>(value/*auto*/));
}
static Tuple2<CompileState, Option<IOError>> runWithSource(CompileState state, Source source) {
	return source/*auto*/.read(/*auto*/).match(lambdaDefinition/*auto*/, lambdaDefinition/*auto*/);
}
auto temp() {
	return magmaTuple/*auto*/.right(/*auto*/);
}
auto temp() {
	return windowsTuple/*auto*/.right(/*auto*/);
}
static Tuple2Impl<CompileState, Option<IOError>> getCompileStateOptionTuple2(CompileState state, Source source, &[I8] input) {
	Tuple2<CompileState, Option<IOError>> typeScriptTuple = Main/*auto*/.compileAndWrite(state/*auto*/, source/*auto*/, input/*auto*/, Platform/*auto*/.TypeScript);
	Tuple2<CompileState, Option<IOError>> magmaTuple = Main/*auto*/.compileAndWrite(typeScriptTuple/*auto*/.left(/*auto*/), source/*auto*/, input/*auto*/, Platform/*auto*/.Magma);
	Tuple2<CompileState, Option<IOError>> windowsTuple = Main/*auto*/.compileAndWrite(magmaTuple/*auto*/.left(/*auto*/), source/*auto*/, input/*auto*/, Platform/*auto*/.Windows);
	return new Tuple2Impl<CompileState, Option<IOError>>(windowsTuple/*auto*/.left(/*auto*/), typeScriptTuple/*auto*/.right(/*auto*/).or(lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/));
}
auto temp() {
	return target/*auto*/.writeString(output/*auto*/.right(/*auto*/).get(extension/*auto*/));
}
auto temp(Option<IOError> ioErrorOption, &[I8] extension) {{
		Path target = targetParent/*auto*/.resolveChild(location/*auto*/.name(/*auto*/) + "." + extension/*auto*/);
		return ioErrorOption/*auto*/.or(lambdaDefinition/*auto*/);
	}
}
static Tuple2<CompileState, Option<IOError>> compileAndWrite(CompileState state, Source source, &[I8] input, Platform platform) {
	CompileState state1 = state/*auto*/.withLocation(source/*auto*/.computeLocation(/*auto*/)).withPlatform(platform/*auto*/);
	Tuple2Impl<CompileState, Map<&[I8], &[I8]>> output = Main/*auto*/.compileRoot(state1/*auto*/, source/*auto*/, input/*auto*/);
	Location location = output/*auto*/.left(/*auto*/).findCurrentLocation(/*auto*/).orElse(new Location(Lists/*auto*/.empty(/*auto*/), ""));
	Path targetDirectory = Files/*auto*/.get(".", "src", platform/*auto*/.root);
	Path targetParent = targetDirectory/*auto*/.resolveChildSegments(location/*auto*/.namespace(/*auto*/));
	if (!targetParent/*auto*/.exists(/*auto*/)) {
		Option<IOError> maybeError = targetParent/*auto*/.createDirectories(/*auto*/);
		if (maybeError/*auto*/.isPresent(/*auto*/)) {
			return new Tuple2Impl<CompileState, Option<IOError>>(output/*auto*/.left(/*auto*/), maybeError/*auto*/);
		}
	}
	Option<IOError> initial = new None<IOError>(/*auto*/);
	Option<IOError> ioErrorOption1 = Queries/*auto*/.fromArray(platform/*auto*/.extension).foldWithInitial(initial/*auto*/, lambdaDefinition/*auto*/);
	return new Tuple2Impl<CompileState, Option<IOError>>(output/*auto*/.left(/*auto*/), ioErrorOption1/*auto*/);
}
auto temp(Import anImport) {
	return anImport/*auto*/.generate(state/*auto*/.platform(/*auto*/));
}
static Tuple2Impl<CompileState, Map<&[I8], &[I8]>> compileRoot(CompileState state, Source source, &[I8] input) {
	Tuple2<CompileState, &[I8]> statementsTuple = Main/*auto*/.compileStatements(state/*auto*/, input/*auto*/, Main/*auto*/.compileRootSegment);
	CompileState statementsState = statementsTuple/*auto*/.left(/*auto*/);
	&[I8] imports = statementsState/*auto*/.imports(/*auto*/).query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner("")).orElse("");
	CompileState compileState = statementsState/*auto*/.clearImports(/*auto*/).clear(/*auto*/);
	&[I8] withMain = Main/*auto*/.createMain(source/*auto*/);
	HashMap<&[I8], &[I8]> entries = new HashMap<&[I8], &[I8]>(/*auto*/);
	Platform platform = state/*auto*/.platform(/*auto*/);
	if (Platform/*auto*/.Windows === platform/*auto*/) {
		&[I8] value = /* source.computeNamespace().query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + source.computeName()*/;
		/*entries.put(Platform.Windows.extension[0], Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"))*/;
		/*entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + source.computeName() + ".h\"") + statementsState.join() + statementsTuple.right() + withMain)*/;
	}
	else {
		/*entries.put(platform.extension[0], imports + statementsState.join() + statementsTuple.right() + withMain)*/;
	}
	return new Tuple2Impl<>(compileState/*auto*/, entries/*auto*/);
}
static &[I8] generateDirective(&[I8] content) {
	return "#" + content/*auto*/ + "\n";
}
static &[I8] createMain(Source source) {
	if (Strings/*auto*/.equalsTo(source/*auto*/.computeName(/*auto*/), "Main")) {
		return "Main.main();";
	}
	return "";
}
static Tuple2<CompileState, &[I8]> compileStatements(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]> mapper) {
	return Main/*auto*/.compileAll(state/*auto*/, input/*auto*/, Main/*auto*/.foldStatements, mapper/*auto*/, Main/*auto*/.mergeStatements);
}
auto temp(CompileState state1, &[I8] s) {
	return new Some<Tuple2<CompileState, &[I8]>>(mapper/*auto*/(state1/*auto*/, s/*auto*/));
}
static Tuple2<CompileState, &[I8]> compileAll(CompileState state, &[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]> mapper, (arg0 : &[I8], arg1 : &[I8]) => &[I8] merger) {
	Tuple2<CompileState, List<&[I8]>> folded = Main/*auto*/.parseAll(state/*auto*/, input/*auto*/, folder/*auto*/, lambdaDefinition/*auto*/).orElse(new Tuple2Impl<CompileState, List<&[I8]>>(state/*auto*/, Lists/*auto*/.empty(/*auto*/)));
	return new Tuple2Impl<CompileState, &[I8]>(folded/*auto*/.left(/*auto*/), Main/*auto*/.generateAll(folded/*auto*/.right(/*auto*/), merger/*auto*/));
}
static &[I8] generateAll(List<&[I8]> elements, (arg0 : &[I8], arg1 : &[I8]) => &[I8] merger) {
	return elements/*auto*/.query(/*auto*/).foldWithInitial("", merger/*auto*/);
}
auto temp(Tuple2<CompileState, T> mappedTuple) {{
			CompileState mappedState = mappedTuple/*auto*/.left(/*auto*/);
			T mappedElement = mappedTuple/*auto*/.right(/*auto*/);
			return new Tuple2Impl<CompileState, List<T>>(mappedState/*auto*/, currentElement/*auto*/.addLast(mappedElement/*auto*/));
		}
}
auto temp(Tuple2<CompileState, List<T>> current) {{
		CompileState currentState = current/*auto*/.left(/*auto*/);
		List<T> currentElement = current/*auto*/.right(/*auto*/);
		return biFunction/*auto*/(currentState/*auto*/, segment/*auto*/).map(lambdaDefinition/*auto*/);
	}
}
auto temp(Option<Tuple2<CompileState, List<T>>> maybeCurrent, &[I8] segment) {
	return maybeCurrent/*auto*/.flatMap(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, &[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> biFunction) {
	return Main/*auto*/.divide(input/*auto*/, folder/*auto*/).query(/*auto*/).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state/*auto*/, Lists/*auto*/.empty(/*auto*/))), lambdaDefinition/*auto*/);
}
static &[I8] mergeStatements(&[I8] cache, &[I8] element) {
	return cache/*auto*/ + element/*auto*/;
}
auto temp() {
	return Main/*auto*/.foldDoubleQuotes(poppedState/*auto*/, popped/*auto*/);
}
auto temp() {
	return folder/*auto*/(poppedState/*auto*/, popped/*auto*/);
}
static List<&[I8]> divide(&[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder) {
	DivideState current = DivideState/*auto*/.createInitial(input/*auto*/);
	while (true/*auto*/) {
		Tuple2<Bool, Tuple2<DivideState, I8>> poppedTuple0 = current/*auto*/.pop(/*auto*/).toTuple(new Tuple2Impl<DivideState, I8>(current/*auto*/, "\0"));
		if (!poppedTuple0/*auto*/.left(/*auto*/)) {
			break;
		}
		Tuple2<DivideState, I8> poppedTuple = poppedTuple0/*auto*/.right(/*auto*/);
		DivideState poppedState = poppedTuple/*auto*/.left(/*auto*/);
		I8 popped = poppedTuple/*auto*/.right(/*auto*/);
		current/*auto*/ = Main/*auto*/.foldSingleQuotes(poppedState/*auto*/, popped/*auto*/).or(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
	}
	return current/*auto*/.advance(/*auto*/).segments(/*auto*/);
}
static Option<DivideState> foldDoubleQuotes(DivideState state, I8 c) {
	if ("\"" !== c/*auto*/) {
		return new None<DivideState>(/*auto*/);
	}
	DivideState appended = state/*auto*/.append(c/*auto*/);
	while (true/*auto*/) {
		Tuple2<Bool, Tuple2<DivideState, I8>> maybeTuple = appended/*auto*/.popAndAppendToTuple(/*auto*/).toTuple(new Tuple2Impl<DivideState, I8>(appended/*auto*/, "\0"));
		if (!maybeTuple/*auto*/.left(/*auto*/)) {
			break;
		}
		Tuple2<DivideState, I8> tuple = maybeTuple/*auto*/.right(/*auto*/);
		appended/*auto*/ = tuple/*auto*/.left(/*auto*/);
		if ("\\" === tuple/*auto*/.right(/*auto*/)) {
			appended/*auto*/ = appended/*auto*/.popAndAppendToOption(/*auto*/).orElse(appended/*auto*/);
		}
		if ("\"" === tuple/*auto*/.right(/*auto*/)) {
			break;
		}
	}
	return new Some<DivideState>(appended/*auto*/);
}
auto temp(DivideState state1) {
	return state1/*auto*/.popAndAppendToOption(/*auto*/);
}
static Option<DivideState> foldSingleQuotes(DivideState state, I8 c) {
	if ("\'" !== c/*auto*/) {
		return new None<DivideState>(/*auto*/);
	}
	return state/*auto*/.append(c/*auto*/).popAndAppendToTuple(/*auto*/).flatMap(Main/*auto*/.foldEscaped).flatMap(lambdaDefinition/*auto*/);
}
static Option<DivideState> foldEscaped(Tuple2<DivideState, I8> tuple) {
	DivideState state = tuple/*auto*/.left(/*auto*/);
	I8 c = tuple/*auto*/.right(/*auto*/);
	if ("\\" === c/*auto*/) {
		return state/*auto*/.popAndAppendToOption(/*auto*/);
	}
	return new Some<DivideState>(state/*auto*/);
}
static DivideState foldStatements(DivideState state, I8 c) {
	DivideState appended = state/*auto*/.append(c/*auto*/);
	if (";" === c/*auto*/ && appended/*auto*/.isLevel(/*auto*/)) {
		return appended/*auto*/.advance(/*auto*/);
	}
	if ("}" === c/*auto*/ && appended/*auto*/.isShallow(/*auto*/)) {
		return appended/*auto*/.advance(/*auto*/).exit(/*auto*/);
	}
	if ("{" === c/*auto*/ || "(" === c/*auto*/) {
		return appended/*auto*/.enter(/*auto*/);
	}
	if ("}" === c/*auto*/ || ")" === c/*auto*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
static Tuple2<CompileState, &[I8]> compileRootSegment(CompileState state, &[I8] input) {
	return Main/*auto*/.compileOrPlaceholder(state/*auto*/, input/*auto*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.compileNamespaced, Main/*auto*/.createStructureRule("class ", "class "), Main/*auto*/.createStructureRule("interface ", "interface "), Main/*auto*/.createStructureRule("record ", "class "), Main/*auto*/.createStructureRule("enum ", "class ")));
}
auto temp(&[I8] s, &[I8] s2) {{
		List<&[I8]> annotations = Main/*auto*/.parseAnnotations(s/*auto*/);
		if (annotations/*auto*/.contains("Actual", Strings/*auto*/.equalsTo)) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*auto*/, ""));
		}
		return Main/*auto*/.compileStructureWithImplementing(state/*auto*/, annotations/*auto*/, Main/*auto*/.parseModifiers(s2/*auto*/), targetInfix/*auto*/, beforeContent/*auto*/, inputContent/*auto*/);
	}
}
auto temp() {{
		List<&[I8]> modifiers = Main/*auto*/.parseModifiers(beforeContent/*auto*/);
		return Main/*auto*/.compileStructureWithImplementing(state/*auto*/, Lists/*auto*/.empty(/*auto*/), modifiers/*auto*/, targetInfix/*auto*/, beforeContent/*auto*/, inputContent/*auto*/);
	}
}
auto temp(&[I8] inputContent) {
	return Main/*auto*/.compileLast(beforeInfix/*auto*/, "\n", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeContent, &[I8] withEnd) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withEnd/*auto*/), "}", lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeInfix, &[I8] afterInfix) {
	return Main/*auto*/.compileFirst(afterInfix/*auto*/, "{", lambdaDefinition/*auto*/);
}
auto temp(CompileState state, &[I8] input1) {
	return Main/*auto*/.compileFirst(input1/*auto*/, sourceInfix/*auto*/, lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createStructureRule(&[I8] sourceInfix, &[I8] targetInfix) {
	return lambdaDefinition/*auto*/;
}
auto temp(Tuple2<CompileState, Type> implementingTuple) {
	return Main/*auto*/.compileStructureWithExtends(implementingTuple/*auto*/.left(/*auto*/), annotations/*auto*/, modifiers/*auto*/, targetInfix/*auto*/, s/*auto*/, new Some<Type>(implementingTuple/*auto*/.right(/*auto*/)), content/*auto*/);
}
auto temp(&[I8] s, &[I8] s2) {
	return Main/*auto*/.parseType(state/*auto*/, s2/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp() {
	return Main/*auto*/.compileStructureWithExtends(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, targetInfix/*auto*/, beforeContent/*auto*/, new None<Type>(/*auto*/), content/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithImplementing(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, &[I8] content) {
	return Main/*auto*/.compileLast(beforeContent/*auto*/, " implements ", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeExtends, &[I8] afterExtends) {
	return Main/*auto*/.compileStructureWithParameters(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, targetInfix/*auto*/, beforeExtends/*auto*/, new Some<&[I8]>(afterExtends/*auto*/), maybeImplementing/*auto*/, inputContent/*auto*/);
}
auto temp() {
	return Main/*auto*/.compileStructureWithParameters(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, targetInfix/*auto*/, beforeContent/*auto*/, new None<&[I8]>(/*auto*/), maybeImplementing/*auto*/, inputContent/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithExtends(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, Option<Type> maybeImplementing, &[I8] inputContent) {
	return Main/*auto*/.compileFirst(beforeContent/*auto*/, " extends ", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] parametersString, &[I8] _) {{
		&[I8] name = Strings/*auto*/.strip(rawName/*auto*/);
		Tuple2<CompileState, List<Parameter>> parametersTuple = Main/*auto*/.parseParameters(state/*auto*/, parametersString/*auto*/);
		List<Definition> parameters = Main/*auto*/.retainDefinitionsFromParameters(parametersTuple/*auto*/.right(/*auto*/));
		return Main/*auto*/.compileStructureWithTypeParams(parametersTuple/*auto*/.left(/*auto*/), targetInfix/*auto*/, inputContent/*auto*/, name/*auto*/, parameters/*auto*/, maybeImplementing/*auto*/, annotations/*auto*/, modifiers/*auto*/, maybeSuperType/*auto*/);
	}
}
auto temp(&[I8] rawName, &[I8] withParameters) {
	return Main/*auto*/.compileFirst(withParameters/*auto*/, ")", lambdaDefinition/*auto*/);
}
auto temp() {
	return Main/*auto*/.compileStructureWithTypeParams(state/*auto*/, targetInfix/*auto*/, inputContent/*auto*/, beforeContent/*auto*/, Lists/*auto*/.empty(/*auto*/), maybeImplementing/*auto*/, annotations/*auto*/, modifiers/*auto*/, maybeSuperType/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithParameters(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, Option<&[I8]> maybeSuperType, Option<Type> maybeImplementing, &[I8] inputContent) {
	return Main/*auto*/.compileFirst(beforeContent/*auto*/, "(", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(Parameter parameter) {
	return parameter/*auto*/.asDefinition(/*auto*/);
}
static List<Definition> retainDefinitionsFromParameters(List<Parameter> parameters) {
	return parameters/*auto*/.query(/*auto*/).map(lambdaDefinition/*auto*/).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<Definition>(/*auto*/));
}
auto temp(&[I8] name, &[I8] typeParamsString) {{
		List<&[I8]> typeParams = Main/*auto*/.divideValues(typeParamsString/*auto*/);
		return Main/*auto*/.assembleStructure(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, infix/*auto*/, name/*auto*/, typeParams/*auto*/, parameters/*auto*/, maybeImplementing/*auto*/, content/*auto*/, maybeSuperType/*auto*/);
	}
}
auto temp(&[I8] withoutTypeParamEnd) {
	return Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", lambdaDefinition/*auto*/);
}
auto temp() {
	return Main/*auto*/.assembleStructure(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, infix/*auto*/, beforeParams/*auto*/, Lists/*auto*/.empty(/*auto*/), parameters/*auto*/, maybeImplementing/*auto*/, content/*auto*/, maybeSuperType/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithTypeParams(CompileState state, &[I8] infix, &[I8] content, &[I8] beforeParams, List<Definition> parameters, Option<Type> maybeImplementing, List<&[I8]> annotations, List<&[I8]> modifiers, Option<&[I8]> maybeSuperType) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeParams/*auto*/), ">", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] value) {
	return value/*auto*/ + " ";
}
auto temp(Location location) {
	return new Location(location/*auto*/.namespace(/*auto*/), location/*auto*/.name(/*auto*/) + "Instance");
}
auto temp(&[I8] inner) {
	return " extends " + inner/*auto*/;
}
static Option<Tuple2<CompileState, &[I8]>> assembleStructure(CompileState state, List<&[I8]> annotations, List<&[I8]> oldModifiers, &[I8] infix, &[I8] rawName, List<&[I8]> typeParams, List<Definition> parameters, Option<Type> maybeImplementing, &[I8] content, Option<&[I8]> maybeSuperType) {
	&[I8] name = Strings/*auto*/.strip(rawName/*auto*/);
	if (!Main/*auto*/.isSymbol(name/*auto*/)) {
		return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
	}
	Tuple2<CompileState, &[I8]> outputContentTuple = Main/*auto*/.compileStatements(state/*auto*/.pushStructureName(name/*auto*/), content/*auto*/, Main/*auto*/.compileClassSegment);
	CompileState outputContentState = outputContentTuple/*auto*/.left(/*auto*/).popStructureName(/*auto*/);
	&[I8] outputContent = outputContentTuple/*auto*/.right(/*auto*/);
	Platform platform = outputContentState/*auto*/.platform(/*auto*/);
	&[I8] constructorString = Main/*auto*/.generateConstructorFromRecordParameters(parameters/*auto*/, platform/*auto*/);
	&[I8] joinedTypeParams = Joiner/*auto*/.joinOrEmpty(typeParams/*auto*/, ", ", "<", ">");
	&[I8] implementingString = Main/*auto*/.generateImplementing(maybeImplementing/*auto*/);
	List<&[I8]> newModifiers = Main/*auto*/.modifyModifiers0(oldModifiers/*auto*/);
	&[I8] joinedModifiers = newModifiers/*auto*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
	if (annotations/*auto*/.contains("Namespace", Strings/*auto*/.equalsTo)) {
		&[I8] actualInfix = "interface ";
		&[I8] newName = name/*auto*/ + "Instance";
		&[I8] generated = joinedModifiers/*auto*/ + actualInfix/*auto*/ + newName/*auto*/ + joinedTypeParams/*auto*/ + implementingString/*auto*/ + " {" + Main/*auto*/.joinParameters(parameters/*auto*/, platform/*auto*/) + constructorString/*auto*/ + outputContent/*auto*/ + "\n}\n";
		CompileState withNewLocation = outputContentState/*auto*/.append(generated/*auto*/).mapLocation(lambdaDefinition/*auto*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(withNewLocation/*auto*/, ""));
	}
	else {
		&[I8] extendsString = maybeSuperType/*auto*/.map(lambdaDefinition/*auto*/).orElse("");
		&[I8] infix1 = Main/*auto*/.retainStruct(infix/*auto*/, outputContentState/*auto*/);
		&[I8] generated = joinedModifiers/*auto*/ + infix1/*auto*/ + name/*auto*/ + joinedTypeParams/*auto*/ + extendsString/*auto*/ + implementingString/*auto*/ + " {" + Main/*auto*/.joinParameters(parameters/*auto*/, platform/*auto*/) + constructorString/*auto*/ + outputContent/*auto*/ + "\n}\n";
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(outputContentState/*auto*/.append(generated/*auto*/), ""));
	}
}
static &[I8] retainStruct(&[I8] infix, CompileState outputContentState) {
	if (outputContentState/*auto*/.isPlatform(Platform/*auto*/.Magma)) {
		return "struct ";
	}
	return infix/*auto*/;
}
static List<&[I8]> modifyModifiers0(List<&[I8]> oldModifiers) {
	if (oldModifiers/*auto*/.contains("public", Strings/*auto*/.equalsTo)) {
		return Lists/*auto*/.of("export");
	}
	return Lists/*auto*/.empty(/*auto*/);
}
auto temp(Type type) {
	return type/*auto*/.generate(/*auto*/);
}
auto temp(&[I8] inner) {
	return " implements " + inner/*auto*/;
}
static &[I8] generateImplementing(Option<Type> maybeImplementing) {
	return maybeImplementing/*auto*/.map(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).orElse("");
}
auto temp(Definition definition) {
	return definition/*auto*/.generate(platform/*auto*/);
}
auto temp(&[I8] generatedParameters) {
	return Main/*auto*/.generateConstructorWithParameterString(parameters/*auto*/, generatedParameters/*auto*/);
}
static &[I8] generateConstructorFromRecordParameters(List<Definition> parameters, Platform platform) {
	return parameters/*auto*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(new Joiner(", ")).map(lambdaDefinition/*auto*/).orElse("");
}
static &[I8] generateConstructorWithParameterString(List<Definition> parameters, &[I8] parametersString) {
	&[I8] constructorAssignments = Main/*auto*/.generateConstructorAssignments(parameters/*auto*/);
	return "\n\tconstructor (" + parametersString/*auto*/ + ") {" + constructorAssignments/*auto*/ + "\n\t}";
}
auto temp(Definition definition) {
	return "\n\t\tthis." + definition/*auto*/.name(/*auto*/) + " = " + definition/*auto*/.name(/*auto*/) + ";";
}
static &[I8] generateConstructorAssignments(List<Definition> parameters) {
	return parameters/*auto*/.query(/*auto*/).map(lambdaDefinition/*auto*/).collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
}
auto temp(Definition definition) {
	return definition/*auto*/.generate(platform/*auto*/);
}
auto temp(&[I8] generated) {
	return "\n\t" + generated/*auto*/ + ";";
}
static &[I8] joinParameters(List<Definition> parameters, Platform platform) {
	return parameters/*auto*/.query(/*auto*/).map(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
}
static Option<Tuple2<CompileState, &[I8]>> compileNamespaced(CompileState state, &[I8] input) {
	&[I8] stripped = Strings/*auto*/.strip(input/*auto*/);
	if (stripped/*auto*/.startsWith("package ") || stripped/*auto*/.startsWith("import ")) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*auto*/, ""));
	}
	return new None<>(/*auto*/);
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(state/*auto*/, Main/*auto*/.generatePlaceholder(input/*auto*/));
}
static Tuple2<CompileState, &[I8]> compileOrPlaceholder(CompileState state, &[I8] input, List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>> rules) {
	return Main/*auto*/.or(state/*auto*/, input/*auto*/, rules/*auto*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp((arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> rule) {
	return Main/*auto*/.getApply(state/*auto*/, input/*auto*/, rule/*auto*/);
}
static Option<Tuple2<CompileState, T>> or(CompileState state, &[I8] input, List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>> rules) {
	return rules/*auto*/.query(/*auto*/).map(lambdaDefinition/*auto*/).flatMap(Queries/*auto*/.fromOption).next(/*auto*/);
}
static Option<Tuple2<CompileState, T>> getApply(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> rule) {
	return rule/*auto*/(state/*auto*/, input/*auto*/);
}
static Tuple2<CompileState, &[I8]> compileClassSegment(CompileState state1, &[I8] input1) {
	return Main/*auto*/.compileOrPlaceholder(state1/*auto*/, input1/*auto*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.createStructureRule("class ", "class "), Main/*auto*/.createStructureRule("interface ", "interface "), Main/*auto*/.createStructureRule("record ", "class "), Main/*auto*/.createStructureRule("enum ", "class "), Main/*auto*/.compileMethod, Main/*auto*/.compileFieldDefinition));
}
auto temp(&[I8] _, &[I8] name) {{
			if (state/*auto*/.hasLastStructureNameOf(name/*auto*/)) {
				return Main/*auto*/.compileMethodWithBeforeParams(state/*auto*/, new ConstructorHeader(/*auto*/), withParams/*auto*/);
			}
			return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
		}
}
auto temp() {{
			if (state/*auto*/.hasLastStructureNameOf(strippedBeforeParams/*auto*/)) {
				return Main/*auto*/.compileMethodWithBeforeParams(state/*auto*/, new ConstructorHeader(/*auto*/), withParams/*auto*/);
			}
			return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
		}
}
auto temp(Tuple2<CompileState, Definition> tuple) {
	return Main/*auto*/.compileMethodWithBeforeParams(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/), withParams/*auto*/);
}
auto temp() {
	return Main/*auto*/.parseDefinition(state/*auto*/, beforeParams/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeParams, &[I8] withParams) {{
		&[I8] strippedBeforeParams = Strings/*auto*/.strip(beforeParams/*auto*/);
		return Main/*auto*/.compileLast(strippedBeforeParams/*auto*/, " ", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileMethod(CompileState state, &[I8] input) {
	return Main/*auto*/.compileFirst(input/*auto*/, "(", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutContentEnd) {{
			CompileState compileState1 = parametersState/*auto*/.enterDepth(/*auto*/);
			CompileState compileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
			Tuple2<CompileState, &[I8]> statementsTuple = Main/*auto*/.compileFunctionStatements(compileState/*auto*/.defineAll(definitions/*auto*/), withoutContentEnd/*auto*/);
			CompileState compileState2 = statementsTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/);
			&[I8] indent = compileState2/*auto*/.createIndent(/*auto*/);
			CompileState exited = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
			FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*auto*/, new Some<>(statementsTuple/*auto*/.right(/*auto*/)));
			&[I8] generated = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), indent/*auto*/);
			if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited/*auto*/.addFunction(generated/*auto*/), ""));
			}
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited/*auto*/, generated/*auto*/));
		}
}
auto temp(&[I8] withoutContentStart) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withoutContentStart/*auto*/), "}", lambdaDefinition/*auto*/);
}
auto temp() {{
			if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(afterParams/*auto*/))) {
				FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*auto*/, new None<>(/*auto*/));
				&[I8] generate = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), "\n\t");
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState/*auto*/, generate/*auto*/));
			}
			return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
		}
}
auto temp(&[I8] params, &[I8] afterParams) {{
		Tuple2<CompileState, List<Parameter>> parametersTuple = Main/*auto*/.parseParameters(state/*auto*/, params/*auto*/);
		CompileState parametersState = parametersTuple/*auto*/.left(/*auto*/);
		List<Parameter> parameters = parametersTuple/*auto*/.right(/*auto*/);
		List<Definition> definitions = Main/*auto*/.retainDefinitionsFromParameters(parameters/*auto*/);
		FunctionHeader<S> newHeader = Main/*auto*/.retainDef(header/*auto*/, parametersState/*auto*/);
		if (newHeader/*auto*/.hasAnnotation("Actual")) {
			S aStatic = newHeader/*auto*/.removeModifier("static");
			FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*auto*/, new None<>(/*auto*/));
			&[I8] generate = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), "\n\t");
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState/*auto*/, generate/*auto*/));
		}
		return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(afterParams/*auto*/), "{", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileMethodWithBeforeParams(CompileState state, FunctionHeader<S> header, &[I8] withParams) {
	return Main/*auto*/.compileFirst(withParams/*auto*/, ")", lambdaDefinition/*auto*/);
}
static FunctionHeader<S> retainDef(FunctionHeader<S> header, CompileState parametersState) {
	if (parametersState/*auto*/.isPlatform(Platform/*auto*/.Magma)) {
		return header/*auto*/.addModifierLast("def").removeModifier("mut");
	}
	return header/*auto*/;
}
auto temp(CompileState state1, &[I8] s) {
	return new Some<Tuple2<CompileState, Parameter>>(Main/*auto*/.parseParameterOrPlaceholder(state1/*auto*/, s/*auto*/));
}
static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, &[I8] params) {
	return Main/*auto*/.parseValuesOrEmpty(state/*auto*/, params/*auto*/, lambdaDefinition/*auto*/);
}
static Tuple2<CompileState, &[I8]> compileFunctionStatements(CompileState state, &[I8] input) {
	return Main/*auto*/.compileStatements(state/*auto*/, input/*auto*/, Main/*auto*/.compileFunctionSegment);
}
static Tuple2<CompileState, &[I8]> compileFunctionSegment(CompileState state, &[I8] input) {
	return Main/*auto*/.compileOrPlaceholder(state/*auto*/, input/*auto*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.compileEmptySegment, Main/*auto*/.compileBlock, Main/*auto*/.compileFunctionStatement, Main/*auto*/.compileReturnWithoutSuffix));
}
static Option<Tuple2<CompileState, &[I8]>> compileEmptySegment(CompileState state, &[I8] input) {
	if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(input/*auto*/))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*auto*/, ";"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
	}
}
auto temp(&[I8] withoutPrefix) {
	return Main/*auto*/.compileValue(state1/*auto*/, withoutPrefix/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), state1/*auto*/.createIndent(/*auto*/) + tuple/*auto*/.right(/*auto*/));
}
static Option<Tuple2<CompileState, &[I8]>> compileReturnWithoutSuffix(CompileState state1, &[I8] input1) {
	return Main/*auto*/.compileReturn(input1/*auto*/, lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> headerTuple) {{
		Tuple2<CompileState, &[I8]> contentTuple = Main/*auto*/.compileFunctionStatements(headerTuple/*auto*/.left(/*auto*/).enterDepth(/*auto*/), content/*auto*/);
		&[I8] indent = state/*auto*/.createIndent(/*auto*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(contentTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/), indent/*auto*/ + headerTuple/*auto*/.right(/*auto*/) + "{" + contentTuple/*auto*/.right(/*auto*/) + indent/*auto*/ + "}"));
	}
}
auto temp(&[I8] beforeContent) {
	return Main/*auto*/.compileBlockHeader(state/*auto*/, beforeContent/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeContentWithEnd, &[I8] content) {
	return Main/*auto*/.compileSuffix(beforeContentWithEnd/*auto*/, "{", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*auto*/, "", Main/*auto*/.foldBlockStarts), lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileBlock(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), "}", lambdaDefinition/*auto*/);
}
static DivideState foldBlockStarts(DivideState state, I8 c) {
	DivideState appended = state/*auto*/.append(c/*auto*/);
	if ("{" === c/*auto*/) {
		DivideState entered = appended/*auto*/.enter(/*auto*/);
		if (entered/*auto*/.isShallow(/*auto*/)) {
			return entered/*auto*/.advance(/*auto*/);
		}
		else {
			return entered/*auto*/;
		}
	}
	if ("}" === c/*auto*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
static Option<Tuple2<CompileState, &[I8]>> compileBlockHeader(CompileState state, &[I8] input) {
	return Main/*auto*/.or(state/*auto*/, input/*auto*/, Lists/*auto*/.of(Main/*auto*/.createConditionalRule("if"), Main/*auto*/.createConditionalRule("while"), Main/*auto*/.compileElse));
}
auto temp(&[I8] withoutConditionEnd) {{
			Tuple2<CompileState, &[I8]> tuple = Main/*auto*/.compileValueOrPlaceholder(state1/*auto*/, withoutConditionEnd/*auto*/);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), prefix/*auto*/ + " (" + tuple/*auto*/.right(/*auto*/) + ") "));
		}
}
auto temp(&[I8] withoutConditionStart) {
	return Main/*auto*/.compileSuffix(withoutConditionStart/*auto*/, ")", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutPrefix) {{
		&[I8] strippedCondition = Strings/*auto*/.strip(withoutPrefix/*auto*/);
		return Main/*auto*/.compilePrefix(strippedCondition/*auto*/, "(", lambdaDefinition/*auto*/);
	}
}
auto temp(CompileState state1, &[I8] input1) {
	return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input1/*auto*/), prefix/*auto*/, lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createConditionalRule(&[I8] prefix) {
	return lambdaDefinition/*auto*/;
}
static Option<Tuple2<CompileState, &[I8]>> compileElse(CompileState state, &[I8] input) {
	if (Strings/*auto*/.equalsTo("else", Strings/*auto*/.strip(input/*auto*/))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*auto*/, "else "));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
	}
}
auto temp(&[I8] withoutEnd) {{
		Tuple2<CompileState, &[I8]> valueTuple = Main/*auto*/.compileFunctionStatementValue(state/*auto*/, withoutEnd/*auto*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(valueTuple/*auto*/.left(/*auto*/), state/*auto*/.createIndent(/*auto*/) + valueTuple/*auto*/.right(/*auto*/) + ";"));
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileFunctionStatement(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), ";", lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Value> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/).generate(tuple/*auto*/.left(/*auto*/).platform(/*auto*/)));
}
auto temp(CompileState state1, &[I8] input) {
	return Main/*auto*/.parseInvokable(state1/*auto*/, input/*auto*/).map(lambdaDefinition/*auto*/);
}
static Tuple2<CompileState, &[I8]> compileFunctionStatementValue(CompileState state, &[I8] withoutEnd) {
	return Main/*auto*/.compileOrPlaceholder(state/*auto*/, withoutEnd/*auto*/, Lists/*auto*/.of(Main/*auto*/.compileReturnWithValue, Main/*auto*/.compileAssignment, lambdaDefinition/*auto*/, Main/*auto*/.createPostRule("++"), Main/*auto*/.createPostRule("--"), Main/*auto*/.compileBreak));
}
static Option<Tuple2<CompileState, &[I8]>> compileBreak(CompileState state, &[I8] input) {
	if (Strings/*auto*/.equalsTo("break", Strings/*auto*/.strip(input/*auto*/))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state/*auto*/, "break"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>(/*auto*/);
	}
}
auto temp(&[I8] child) {{
		Tuple2<CompileState, &[I8]> tuple = Main/*auto*/.compileValueOrPlaceholder(state1/*auto*/, child/*auto*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/) + suffix/*auto*/));
	}
}
auto temp(CompileState state1, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), suffix/*auto*/, lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createPostRule(&[I8] suffix) {
	return lambdaDefinition/*auto*/;
}
auto temp(&[I8] value1) {
	return Main/*auto*/.compileValue(state/*auto*/, value1/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileReturnWithValue(CompileState state, &[I8] input) {
	return Main/*auto*/.compileReturn(input/*auto*/, lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {
	return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), "return " + tuple/*auto*/.right(/*auto*/)));
}
auto temp(&[I8] value) {
	return mapper/*auto*/(value/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileReturn(&[I8] input, (arg0 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> mapper) {
	return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*auto*/), "return ", lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> callerTuple) {{
		CompileState callerState = callerTuple/*auto*/.left(/*auto*/);
		&[I8] caller = callerTuple/*auto*/.right(/*auto*/);
		return Main/*auto*/.assembleInvokable(callerState/*auto*/, new ConstructionCaller(caller/*auto*/, callerState/*auto*/.platform(/*auto*/)), args/*auto*/);
	}
}
auto temp(&[I8] type) {
	return Main/*auto*/.compileType(state/*auto*/, type/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Value> callerTuple) {
	return Main/*auto*/.assembleInvokable(callerTuple/*auto*/.left(/*auto*/), callerTuple/*auto*/.right(/*auto*/), args/*auto*/);
}
auto temp() {
	return Main/*auto*/.parseValue(state/*auto*/, callerString/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] callerString) {
	return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(callerString/*auto*/), "new ", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(&[I8] callerWithArgStart, &[I8] args) {
	return Main/*auto*/.compileSuffix(callerWithArgStart/*auto*/, "(", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*auto*/, "", Main/*auto*/.foldInvocationStarts), lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), ")", lambdaDefinition/*auto*/);
}
auto temp(List<&[I8]> divisions1) {
	return Main/*auto*/.selectLast(divisions1/*auto*/, delimiter/*auto*/);
}
static Option<Tuple2<&[I8], &[I8]>> splitFoldedLast(&[I8] input, &[I8] delimiter, (arg0 : DivideState, arg1 : I8) => DivideState folder) {
	return Main/*auto*/.splitFolded(input/*auto*/, folder/*auto*/, lambdaDefinition/*auto*/);
}
static Option<Tuple2<&[I8], &[I8]>> splitFolded(&[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : List<&[I8]>) => Option<Tuple2<&[I8], &[I8]>> selector) {
	List<&[I8]> divisions = Main/*auto*/.divide(input/*auto*/, folder/*auto*/);
	if (2/*auto*/ > divisions/*auto*/.size(/*auto*/)) {
		return new None<Tuple2<&[I8], &[I8]>>(/*auto*/);
	}
	return selector/*auto*/(divisions/*auto*/);
}
static Option<Tuple2<&[I8], &[I8]>> selectLast(List<&[I8]> divisions, &[I8] delimiter) {
	List<&[I8]> beforeLast = divisions/*auto*/.subList(0/*auto*/, divisions/*auto*/.size(/*auto*/) - 1/*auto*/).orElse(divisions/*auto*/);
	&[I8] last = divisions/*auto*/.findLast(/*auto*/).orElse("");
	&[I8] joined = beforeLast/*auto*/.query(/*auto*/).collect(new Joiner(delimiter/*auto*/)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(joined/*auto*/, last/*auto*/));
}
static DivideState foldInvocationStarts(DivideState state, I8 c) {
	DivideState appended = state/*auto*/.append(c/*auto*/);
	if ("(" === c/*auto*/) {
		DivideState entered = appended/*auto*/.enter(/*auto*/);
		if (entered/*auto*/.isShallow(/*auto*/)) {
			return entered/*auto*/.advance(/*auto*/);
		}
		else {
			return entered/*auto*/;
		}
	}
	if (")" === c/*auto*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
auto temp(CompileState state1, &[I8] s) {
	return Main/*auto*/.parseArgument(state1/*auto*/, s/*auto*/);
}
auto temp(Argument argument) {
	return argument/*auto*/.toValue(/*auto*/);
}
auto temp(Tuple2<CompileState, List<Argument>> argsTuple) {{
		CompileState argsState = argsTuple/*auto*/.left(/*auto*/);
		List<Value> args = Main/*auto*/.retain(argsTuple/*auto*/.right(/*auto*/), lambdaDefinition/*auto*/);
		Caller newCaller = Main/*auto*/.transformCaller(argsState/*auto*/, oldCaller/*auto*/);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState/*auto*/, new InvokableNode(newCaller/*auto*/, args/*auto*/)));
	}
}
static Option<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, Caller oldCaller, &[I8] argsString) {
	return Main/*auto*/.parseValues(state/*auto*/, argsString/*auto*/, lambdaDefinition/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(Value parent) {{
		Type parentType = parent/*auto*/.resolve(state/*auto*/);
		if (parentType/*auto*/.isFunctional(/*auto*/)) {
			return new Some<Caller>(parent/*auto*/);
		}
		return new None<Caller>(/*auto*/);
	}
}
static Caller transformCaller(CompileState state, Caller oldCaller) {
	return oldCaller/*auto*/.findChild(/*auto*/).flatMap(lambdaDefinition/*auto*/).orElse(oldCaller/*auto*/);
}
static List<R> retain(List<T> args, (arg0 : T) => Option<R> mapper) {
	return args/*auto*/.query(/*auto*/).map(mapper/*auto*/).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<R>(/*auto*/));
}
auto temp(Tuple2<CompileState, Value> tuple) {
	return new Tuple2Impl<CompileState, Argument>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/));
}
static Option<Tuple2<CompileState, Argument>> parseArgument(CompileState state1, &[I8] input) {
	return Main/*auto*/.parseValue(state1/*auto*/, input/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Definition> definitionTuple) {{
			CompileState definitionState = definitionTuple/*auto*/.left(/*auto*/);
			Definition definition = definitionTuple/*auto*/.right(/*auto*/);
			Definition let = Main/*auto*/.attachLet(definitionState/*auto*/, definition/*auto*/);
			&[I8] generate = let/*auto*/.generate(definitionState/*auto*/.platform(/*auto*/));
			return new Tuple2Impl<CompileState, &[I8]>(definitionState/*auto*/, generate/*auto*/);
		}
}
auto temp() {
	return Main/*auto*/.parseDefinition(sourceTuple/*auto*/.left(/*auto*/), destination/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(sourceTuple/*auto*/.left(/*auto*/), Main/*auto*/.generatePlaceholder(destination/*auto*/));
}
auto temp(&[I8] destination, &[I8] source) {{
		Tuple2<CompileState, &[I8]> sourceTuple = Main/*auto*/.compileValueOrPlaceholder(state/*auto*/, source/*auto*/);
		Tuple2<CompileState, &[I8]> destinationTuple = Main/*auto*/.compileValue(sourceTuple/*auto*/.left(/*auto*/), destination/*auto*/).or(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(destinationTuple/*auto*/.left(/*auto*/), destinationTuple/*auto*/.right(/*auto*/) + " = " + sourceTuple/*auto*/.right(/*auto*/)));
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileAssignment(CompileState state, &[I8] input) {
	return Main/*auto*/.compileFirst(input/*auto*/, "=", lambdaDefinition/*auto*/);
}
static Definition attachLet(CompileState definitionState, Definition definition) {
	/*final Definition let*/;
	if (definitionState/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
		let/*auto*/ = definition/*auto*/;
	}
	else {
		let/*auto*/ = definition/*auto*/.addModifierLast("let");
	}
	return let/*auto*/;
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(state/*auto*/, Main/*auto*/.generatePlaceholder(input/*auto*/));
}
static Tuple2<CompileState, &[I8]> compileValueOrPlaceholder(CompileState state, &[I8] input) {
	return Main/*auto*/.compileValue(state/*auto*/, input/*auto*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Value> tuple) {{
		&[I8] generated = tuple/*auto*/.right(/*auto*/).generate(tuple/*auto*/.left(/*auto*/).platform(/*auto*/));
		return new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), generated/*auto*/);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileValue(CompileState state, &[I8] input) {
	return Main/*auto*/.parseValue(state/*auto*/, input/*auto*/).map(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Value>> parseValue(CompileState state, &[I8] input) {
	return Main/*auto*/.or(state/*auto*/, input/*auto*/, Lists/*auto*/.of(Main/*auto*/.parseLambda, Main/*auto*/.createOperatorRule("+"), Main/*auto*/.createOperatorRule("-"), Main/*auto*/.createOperatorRule("<="), Main/*auto*/.createOperatorRule("<"), Main/*auto*/.createOperatorRule("&&"), Main/*auto*/.createOperatorRule("||"), Main/*auto*/.createOperatorRule(">"), Main/*auto*/.createOperatorRule(">="), Main/*auto*/.parseInvokable, Main/*auto*/.createAccessRule("."), Main/*auto*/.createAccessRule("::"), Main/*auto*/.parseSymbol, Main/*auto*/.parseNot, Main/*auto*/.parseNumber, Main/*auto*/.createOperatorRuleWithDifferentInfix("==", "==="), Main/*auto*/.createOperatorRuleWithDifferentInfix("!=", "!=="), Main/*auto*/.createTextRule("\""), Main/*auto*/.createTextRule("'")));
}
auto temp(&[I8] s1) {
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1/*auto*/, new StringNode(s1/*auto*/)));
}
auto temp(&[I8] s) {
	return Main/*auto*/.compileSuffix(s/*auto*/, slice/*auto*/, lambdaDefinition/*auto*/);
}
auto temp(CompileState state1, &[I8] input1) {{
		&[I8] stripped = Strings/*auto*/.strip(input1/*auto*/);
		return Main/*auto*/.compilePrefix(stripped/*auto*/, slice/*auto*/, lambdaDefinition/*auto*/);
	}
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createTextRule(&[I8] slice) {
	return lambdaDefinition/*auto*/;
}
auto temp(&[I8] withoutPrefix) {{
		Tuple2<CompileState, &[I8]> childTuple = Main/*auto*/.compileValueOrPlaceholder(state/*auto*/, withoutPrefix/*auto*/);
		CompileState childState = childTuple/*auto*/.left(/*auto*/);
		&[I8] child = "!" + childTuple/*auto*/.right(/*auto*/);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new NotNode(child/*auto*/)));
	}
}
static Option<Tuple2<CompileState, Value>> parseNot(CompileState state, &[I8] input) {
	return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*auto*/), "!", lambdaDefinition/*auto*/);
}
auto temp(CompileState state1, &[I8] s) {
	return Main/*auto*/.parseParameter(state1/*auto*/, s/*auto*/);
}
auto temp(Tuple2<CompileState, List<Parameter>> paramNames) {
	return Main/*auto*/.compileLambdaWithParameterNames(paramNames/*auto*/.left(/*auto*/), Main/*auto*/.retainDefinitionsFromParameters(paramNames/*auto*/.right(/*auto*/)), afterArrow/*auto*/);
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.parseValues(state/*auto*/, withoutEnd/*auto*/, lambdaDefinition/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutStart) {
	return Main/*auto*/.compileSuffix(withoutStart/*auto*/, ")", lambdaDefinition/*auto*/);
}
auto temp(&[I8] beforeArrow, &[I8] afterArrow) {{
		&[I8] strippedBeforeArrow = Strings/*auto*/.strip(beforeArrow/*auto*/);
		return Main/*auto*/.compilePrefix(strippedBeforeArrow/*auto*/, "(", lambdaDefinition/*auto*/);
	}
}
static Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, &[I8] input) {
	return Main/*auto*/.compileFirst(input/*auto*/, "->", lambdaDefinition/*auto*/);
}
auto temp(&[I8] withoutContentEnd) {{
		Tuple2<CompileState, &[I8]> statementsTuple = Main/*auto*/.compileFunctionStatements(state/*auto*/.enterDepth(/*auto*/).defineAll(paramNames/*auto*/), withoutContentEnd/*auto*/);
		CompileState statementsState = statementsTuple/*auto*/.left(/*auto*/);
		&[I8] statements = statementsTuple/*auto*/.right(/*auto*/);
		CompileState exited = statementsState/*auto*/.exitDepth(/*auto*/);
		&[I8] content = "{" + statements/*auto*/ + exited/*auto*/.createIndent(/*auto*/) + "}";
		if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
			return Main/*auto*/.assembleLambdaWithContent(exited/*auto*/, paramNames/*auto*/, content/*auto*/);
		}
		return Main/*auto*/.getSome(exited/*auto*/, paramNames/*auto*/, content/*auto*/);
	}
}
auto temp(&[I8] withoutContentStart) {
	return Main/*auto*/.compileSuffix(withoutContentStart/*auto*/, "}", lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {{
		CompileState state1 = tuple/*auto*/.left(/*auto*/);
		&[I8] content = tuple/*auto*/.right(/*auto*/);
		if (state1/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
			return Main/*auto*/.assembleLambdaWithContent(state1/*auto*/, paramNames/*auto*/, "\n\treturn " + content/*auto*/ + ";");
		}
		return Main/*auto*/.getSome(state1/*auto*/, paramNames/*auto*/, content/*auto*/);
	}
}
auto temp() {
	return Main/*auto*/.compileValue(state/*auto*/, strippedAfterArrow/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Value>> compileLambdaWithParameterNames(CompileState state, List<Definition> paramNames, &[I8] afterArrow) {
	&[I8] strippedAfterArrow = Strings/*auto*/.strip(afterArrow/*auto*/);
	return Main/*auto*/.compilePrefix(strippedAfterArrow/*auto*/, "{", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
static Some<Tuple2<CompileState, Value>> getSome(CompileState state, List<Definition> parameters, &[I8] content) {
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*auto*/, new LambdaNode(parameters/*auto*/, content/*auto*/)));
}
static Some<Tuple2<CompileState, Value>> assembleLambdaWithContent(CompileState state, List<Definition> parameters, &[I8] content) {
	Definition lambdaDefinition = new Definition(PrimitiveType/*auto*/.Auto, state/*auto*/.functionName(/*auto*/));
	FunctionSegment<Definition> value = new FunctionSegment<Definition>(lambdaDefinition/*auto*/, parameters/*auto*/, new Some<>(content/*auto*/));
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*auto*/.addFunction(value/*auto*/.generate(state/*auto*/.platform(/*auto*/), "\n")), new SymbolNode("lambdaDefinition", PrimitiveType/*auto*/.Auto)));
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createOperatorRule(&[I8] infix) {
	return Main/*auto*/.createOperatorRuleWithDifferentInfix(infix/*auto*/, infix/*auto*/);
}
auto temp(Tuple2<CompileState, Value> childTuple) {{
			CompileState childState = childTuple/*auto*/.left(/*auto*/);
			Value child = childTuple/*auto*/.right(/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new AccessNode(child/*auto*/, property/*auto*/)));
		}
}
auto temp(&[I8] childString, &[I8] rawProperty) {{
		&[I8] property = Strings/*auto*/.strip(rawProperty/*auto*/);
		if (!Main/*auto*/.isSymbol(property/*auto*/)) {
			return new None<Tuple2<CompileState, Value>>(/*auto*/);
		}
		return Main/*auto*/.parseValue(state/*auto*/, childString/*auto*/).flatMap(lambdaDefinition/*auto*/);
	}
}
auto temp(CompileState state, &[I8] input) {
	return Main/*auto*/.compileLast(input/*auto*/, infix/*auto*/, lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createAccessRule(&[I8] infix) {
	return lambdaDefinition/*auto*/;
}
auto temp(List<&[I8]> divisions) {
	return Main/*auto*/.selectFirst(divisions/*auto*/, sourceInfix/*auto*/);
}
auto temp(Tuple2<CompileState, Value> rightTuple) {{
		Value left = leftTuple/*auto*/.right(/*auto*/);
		Value right = rightTuple/*auto*/.right(/*auto*/);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple/*auto*/.left(/*auto*/), new OperationNode(left/*auto*/, targetInfix/*auto*/, right/*auto*/)));
	}
}
auto temp(Tuple2<CompileState, Value> leftTuple) {
	return Main/*auto*/.parseValue(leftTuple/*auto*/.left(/*auto*/), rightString/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] leftString, &[I8] rightString) {
	return Main/*auto*/.parseValue(state1/*auto*/, leftString/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(CompileState state1, &[I8] input1) {
	return Main/*auto*/.compileSplit(Main/*auto*/.splitFolded(input1/*auto*/, Main/*auto*/.foldOperator(sourceInfix/*auto*/), lambdaDefinition/*auto*/), lambdaDefinition/*auto*/);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createOperatorRuleWithDifferentInfix(&[I8] sourceInfix, &[I8] targetInfix) {
	return lambdaDefinition/*auto*/;
}
static Option<Tuple2<&[I8], &[I8]>> selectFirst(List<&[I8]> divisions, &[I8] delimiter) {
	&[I8] first = divisions/*auto*/.findFirst(/*auto*/).orElse("");
	&[I8] afterFirst = divisions/*auto*/.subList(1/*auto*/, divisions/*auto*/.size(/*auto*/)).orElse(divisions/*auto*/).query(/*auto*/).collect(new Joiner(delimiter/*auto*/)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(first/*auto*/, afterFirst/*auto*/));
}
auto temp(Tuple2<DivideState, I8> tuple) {
	return tuple/*auto*/.left(/*auto*/);
}
auto temp(DivideState state, I8 c) {{
		if (c/*auto*/ === Strings/*auto*/.charAt(infix/*auto*/, 0/*auto*/) && state/*auto*/.startsWith(Strings/*auto*/.sliceFrom(infix/*auto*/, 1/*auto*/))) {
			number length = Strings/*auto*/.length(infix/*auto*/) - 1/*auto*/;
			number counter = 0/*auto*/;
			DivideState current = state/*auto*/;
			while (counter/*auto*/ < length/*auto*/) {
				counter/*auto*/++;
				current/*auto*/ = current/*auto*/.pop(/*auto*/).map(lambdaDefinition/*auto*/).orElse(current/*auto*/);
			}
			return current/*auto*/.advance(/*auto*/);
		}
		return state/*auto*/.append(c/*auto*/);
	}
}
static (arg0 : DivideState, arg1 : I8) => DivideState foldOperator(&[I8] infix) {
	return lambdaDefinition/*auto*/;
}
static Option<Tuple2<CompileState, Value>> parseNumber(CompileState state, &[I8] input) {
	&[I8] stripped = Strings/*auto*/.strip(input/*auto*/);
	if (Main/*auto*/.isNumber(stripped/*auto*/)) {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*auto*/, new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
	}
	else {
		return new None<Tuple2<CompileState, Value>>(/*auto*/);
	}
}
auto temp(number index) {
	return input/*auto*/.charAt(index/*auto*/);
}
auto temp(I8 c) {
	return Characters/*auto*/.isDigit(c/*auto*/);
}
static Bool isNumber(&[I8] input) {
	HeadedQuery<number> query = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*auto*/)));
	return query/*auto*/.map(lambdaDefinition/*auto*/).allMatch(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Value>> parseSymbol(CompileState state, &[I8] input) {
	&[I8] stripped = Strings/*auto*/.strip(input/*auto*/);
	if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
		CompileState withImport = state/*auto*/.addResolvedImportFromCache(stripped/*auto*/);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport/*auto*/, new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
	}
	else {
		return new None<Tuple2<CompileState, Value>>(/*auto*/);
	}
}
auto temp(number index) {
	return Main/*auto*/.isSymbolChar(index/*auto*/, Strings/*auto*/.charAt(input/*auto*/, index/*auto*/));
}
static Bool isSymbol(&[I8] input) {
	HeadedQuery<number> query = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*auto*/)));
	return query/*auto*/.allMatch(lambdaDefinition/*auto*/);
}
static Bool isSymbolChar(number index, I8 c) {
	return "_" === c/*auto*/ || Characters/*auto*/.isLetter(c/*auto*/) || /*auto*/(0/*auto*/ !== index/*auto*/ && Characters/*auto*/.isDigit(c/*auto*/));
}
static Option<Tuple2<CompileState, T>> compilePrefix(&[I8] input, &[I8] infix, (arg0 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	if (!input/*auto*/.startsWith(infix/*auto*/)) {
		return new None<Tuple2<CompileState, T>>(/*auto*/);
	}
	&[I8] slice = Strings/*auto*/.sliceFrom(input/*auto*/, Strings/*auto*/.length(infix/*auto*/));
	return mapper/*auto*/(slice/*auto*/);
}
auto temp(Tuple2<CompileState, Whitespace> tuple) {{
		&[I8] generate = tuple/*auto*/.right(/*auto*/).generate(tuple/*auto*/.left(/*auto*/).platform(/*auto*/));
		return new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), generate/*auto*/);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileWhitespace(CompileState state, &[I8] input) {
	return Main/*auto*/.parseWhitespace(state/*auto*/, input/*auto*/).map(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, &[I8] input) {
	if (Strings/*auto*/.isBlank(input/*auto*/)) {
		return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state/*auto*/, new Whitespace(/*auto*/)));
	}
	return new None<Tuple2<CompileState, Whitespace>>(/*auto*/);
}
auto temp() {
	return Main/*auto*/.compileEnumValues(state/*auto*/, withoutEnd/*auto*/);
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.getTupleOption(state/*auto*/, withoutEnd/*auto*/).or(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileFieldDefinition(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), ";", lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Parameter> definitionTuple) {{
		&[I8] generate = "\n\t" + definitionTuple/*auto*/.right(/*auto*/).generate(definitionTuple/*auto*/.left(/*auto*/).platform(/*auto*/)) + ";";
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(definitionTuple/*auto*/.left(/*auto*/), generate/*auto*/));
	}
}
static Option<Tuple2<CompileState, &[I8]>> getTupleOption(CompileState state, &[I8] withoutEnd) {
	return Main/*auto*/.parseParameter(state/*auto*/, withoutEnd/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(&[I8] stringOption) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), stringOption/*auto*/);
}
auto temp(Tuple2<CompileState, Value> tuple) {{
		&[I8] structureName = state/*auto*/.findLastStructureName(/*auto*/).orElse("");
		return tuple/*auto*/.right(/*auto*/).generateAsEnumValue(structureName/*auto*/, state/*auto*/.platform(/*auto*/)).map(lambdaDefinition/*auto*/);
	}
}
auto temp(CompileState state1, &[I8] s) {
	return Main/*auto*/.parseInvokable(state1/*auto*/, s/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, List<&[I8]>> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/).query(/*auto*/).collect(new Joiner("")).orElse(""));
}
static Option<Tuple2<CompileState, &[I8]>> compileEnumValues(CompileState state, &[I8] withoutEnd) {
	return Main/*auto*/.parseValues(state/*auto*/, withoutEnd/*auto*/, lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp() {
	return new Tuple2Impl<CompileState, Parameter>(state/*auto*/, new Placeholder(input/*auto*/));
}
static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(CompileState state, &[I8] input) {
	return Main/*auto*/.parseParameter(state/*auto*/, input/*auto*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Whitespace> tuple) {
	return Main/*auto*/.getCompileStateParameterTuple2(tuple/*auto*/);
}
auto temp(Tuple2<CompileState, Definition> tuple) {
	return new Tuple2Impl<CompileState, Parameter>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/));
}
auto temp() {
	return Main/*auto*/.parseDefinition(state/*auto*/, input/*auto*/).map(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Parameter>> parseParameter(CompileState state, &[I8] input) {
	return Main/*auto*/.parseWhitespace(state/*auto*/, input/*auto*/).map(lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(Tuple2<CompileState, Whitespace> tuple) {
	return new Tuple2Impl<CompileState, Parameter>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/));
}
auto temp(&[I8] annotationsString, &[I8] afterAnnotations) {{
		List<&[I8]> annotations = Main/*auto*/.parseAnnotations(annotationsString/*auto*/);
		return Main/*auto*/.parseDefinitionWithAnnotations(state/*auto*/, annotations/*auto*/, afterAnnotations/*auto*/, type/*auto*/, name/*auto*/);
	}
}
auto temp() {
	return Main/*auto*/.parseDefinitionWithAnnotations(state/*auto*/, Lists/*auto*/.empty(/*auto*/), beforeType/*auto*/, type/*auto*/, name/*auto*/);
}
auto temp(&[I8] beforeType, &[I8] type) {
	return Main/*auto*/.compileLast(Strings/*auto*/.strip(beforeType/*auto*/), "\n", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp() {
	return Main/*auto*/.parseDefinitionWithTypeParameters(state/*auto*/, Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), beforeName/*auto*/, name/*auto*/);
}
auto temp(&[I8] beforeName, &[I8] name) {
	return Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(Strings/*auto*/.strip(beforeName/*auto*/), " ", Main/*auto*/.foldTypeSeparators), lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, &[I8] input) {
	return Main/*auto*/.compileLast(Strings/*auto*/.strip(input/*auto*/), " ", lambdaDefinition/*auto*/);
}
auto temp(DivideState state1, I8 c) {
	return Main/*auto*/.foldDelimited(state1/*auto*/, c/*auto*/, "\n");
}
auto temp(&[I8] s2) {
	return Strings/*auto*/.strip(s2/*auto*/);
}
auto temp(&[I8] value) {
	return !Strings/*auto*/.isEmpty(value/*auto*/);
}
auto temp(&[I8] value) {
	return 1/*auto*/ <= Strings/*auto*/.length(value/*auto*/);
}
auto temp(&[I8] value) {
	return Strings/*auto*/.sliceFrom(value/*auto*/, 1/*auto*/);
}
auto temp(&[I8] s1) {
	return Strings/*auto*/.strip(s1/*auto*/);
}
auto temp(&[I8] value) {
	return !Strings/*auto*/.isEmpty(value/*auto*/);
}
static List<&[I8]> parseAnnotations(&[I8] s) {
	return Main/*auto*/.divide(s/*auto*/, lambdaDefinition/*auto*/).query(/*auto*/).map(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).collect(new ListCollector<&[I8]>(/*auto*/));
}
auto temp(&[I8] beforeTypeParams, &[I8] typeParamsString) {{
		List<&[I8]> typeParams = Main/*auto*/.divideValues(typeParamsString/*auto*/);
		return Main/*auto*/.parseDefinitionWithTypeParameters(state/*auto*/, annotations/*auto*/, typeParams/*auto*/, Main/*auto*/.parseModifiers(beforeTypeParams/*auto*/), type/*auto*/, name/*auto*/);
	}
}
auto temp(&[I8] withoutTypeParamEnd) {
	return Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", lambdaDefinition/*auto*/);
}
auto temp() {{
		List<&[I8]> divided = Main/*auto*/.parseModifiers(beforeType/*auto*/);
		return Main/*auto*/.parseDefinitionWithTypeParameters(state/*auto*/, annotations/*auto*/, Lists/*auto*/.empty(/*auto*/), divided/*auto*/, type/*auto*/, name/*auto*/);
	}
}
static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(CompileState state, List<&[I8]> annotations, &[I8] beforeType, &[I8] type, &[I8] name) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeType/*auto*/), ">", lambdaDefinition/*auto*/).or(lambdaDefinition/*auto*/);
}
auto temp(DivideState state1, I8 c) {
	return Main/*auto*/.foldDelimited(state1/*auto*/, c/*auto*/, " ");
}
auto temp(&[I8] s) {
	return Strings/*auto*/.strip(s/*auto*/);
}
auto temp(&[I8] value) {
	return !Strings/*auto*/.isEmpty(value/*auto*/);
}
static List<&[I8]> parseModifiers(&[I8] beforeType) {
	return Main/*auto*/.divide(Strings/*auto*/.strip(beforeType/*auto*/), lambdaDefinition/*auto*/).query(/*auto*/).map(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).collect(new ListCollector<&[I8]>(/*auto*/));
}
static DivideState foldDelimited(DivideState state1, I8 c, I8 delimiter) {
	if (delimiter/*auto*/ === c/*auto*/) {
		return state1/*auto*/.advance(/*auto*/);
	}
	return state1/*auto*/.append(c/*auto*/);
}
auto temp(&[I8] input1) {
	return Strings/*auto*/.strip(input1/*auto*/);
}
auto temp(&[I8] value) {
	return !Strings/*auto*/.isEmpty(value/*auto*/);
}
static List<&[I8]> divideValues(&[I8] input) {
	return Main/*auto*/.divide(input/*auto*/, Main/*auto*/.foldValues).query(/*auto*/).map(lambdaDefinition/*auto*/).filter(lambdaDefinition/*auto*/).collect(new ListCollector<&[I8]>(/*auto*/));
}
static DivideState foldTypeSeparators(DivideState state, I8 c) {
	if (" " === c/*auto*/ && state/*auto*/.isLevel(/*auto*/)) {
		return state/*auto*/.advance(/*auto*/);
	}
	DivideState appended = state/*auto*/.append(c/*auto*/);
	if ("<" === c/*auto*/) {
		return appended/*auto*/.enter(/*auto*/);
	}
	if (">" === c/*auto*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
auto temp(Tuple2<CompileState, Type> typeTuple) {{
		List<&[I8]> newModifiers = Main/*auto*/.modifyModifiers(oldModifiers/*auto*/, state/*auto*/.platform(/*auto*/));
		Definition generated = new Definition(annotations/*auto*/, newModifiers/*auto*/, typeParams/*auto*/, typeTuple/*auto*/.right(/*auto*/), name/*auto*/);
		return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple/*auto*/.left(/*auto*/), generated/*auto*/));
	}
}
static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeParameters(CompileState state, List<&[I8]> annotations, List<&[I8]> typeParams, List<&[I8]> oldModifiers, &[I8] type, &[I8] name) {
	return Main/*auto*/.parseType(state/*auto*/, type/*auto*/).flatMap(lambdaDefinition/*auto*/);
}
static List<&[I8]> modifyModifiers(List<&[I8]> oldModifiers, Platform platform) {
	List<&[I8]> list = Main/*auto*/.retainFinal(oldModifiers/*auto*/, platform/*auto*/);
	if (oldModifiers/*auto*/.contains("static", Strings/*auto*/.equalsTo)) {
		return list/*auto*/.addLast("static");
	}
	return list/*auto*/;
}
static List<&[I8]> retainFinal(List<&[I8]> oldModifiers, Platform platform) {
	if (oldModifiers/*auto*/.contains("final", Strings/*auto*/.equalsTo) || Platform/*auto*/.Magma !== platform/*auto*/) {
		return Lists/*auto*/.empty(/*auto*/);
	}
	return Lists/*auto*/.of("mut");
}
auto temp(Tuple2<CompileState, Type> tuple) {
	return new Tuple2Impl<CompileState, Type>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/));
}
auto temp() {
	return new Tuple2Impl<CompileState, Type>(state/*auto*/, new Placeholder(type/*auto*/));
}
static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, &[I8] type) {
	return Main/*auto*/.parseType(state/*auto*/, type/*auto*/).map(lambdaDefinition/*auto*/).orElseGet(lambdaDefinition/*auto*/);
}
auto temp(Tuple2<CompileState, Type> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/).generate(/*auto*/));
}
static Option<Tuple2<CompileState, &[I8]>> compileType(CompileState state, &[I8] type) {
	return Main/*auto*/.parseType(state/*auto*/, type/*auto*/).map(lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Type>> parseType(CompileState state, &[I8] type) {
	return Main/*auto*/.or(state/*auto*/, type/*auto*/, Lists/*auto*/.of(Main/*auto*/.parseArrayType, Main/*auto*/.parseVarArgs, Main/*auto*/.parseGeneric, Main/*auto*/.parsePrimitive, Main/*auto*/.parseSymbolType));
}
auto temp(&[I8] s) {{
		Tuple2<CompileState, Type> child = Main/*auto*/.parseTypeOrPlaceholder(state/*auto*/, s/*auto*/);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*auto*/.left(/*auto*/), new ArrayType(child/*auto*/.right(/*auto*/))));
	}
}
static Option<Tuple2<CompileState, Type>> parseArrayType(CompileState state, &[I8] input) {
	&[I8] stripped = Strings/*auto*/.strip(input/*auto*/);
	return Main/*auto*/.compileSuffix(stripped/*auto*/, "[]", lambdaDefinition/*auto*/);
}
auto temp(&[I8] s) {{
		Tuple2<CompileState, Type> child = Main/*auto*/.parseTypeOrPlaceholder(state/*auto*/, s/*auto*/);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*auto*/.left(/*auto*/), new VariadicType(child/*auto*/.right(/*auto*/))));
	}
}
static Option<Tuple2<CompileState, Type>> parseVarArgs(CompileState state, &[I8] input) {
	&[I8] stripped = Strings/*auto*/.strip(input/*auto*/);
	return Main/*auto*/.compileSuffix(stripped/*auto*/, "...", lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Type>> parseSymbolType(CompileState state, &[I8] input) {
	&[I8] stripped = Strings/*auto*/.strip(input/*auto*/);
	if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state/*auto*/.addResolvedImportFromCache(stripped/*auto*/), new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
	}
	return new None<Tuple2<CompileState, Type>>(/*auto*/);
}
auto temp(Type result) {
	return new Tuple2Impl<CompileState, Type>(state/*auto*/, result/*auto*/);
}
static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, &[I8] input) {
	return Main/*auto*/.findPrimitiveValue(Strings/*auto*/.strip(input/*auto*/), state/*auto*/.platform(/*auto*/)).map(lambdaDefinition/*auto*/);
}
static Option<Type> findPrimitiveValue(&[I8] input, Platform platform) {
	&[I8] stripped = Strings/*auto*/.strip(input/*auto*/);
	if (Strings/*auto*/.equalsTo("char", stripped/*auto*/) || Strings/*auto*/.equalsTo("Character", stripped/*auto*/)) {
		if (Platform/*auto*/.TypeScript === platform/*auto*/) {
			return new Some<Type>(PrimitiveType/*auto*/.String);
		}
		else {
			return new Some<Type>(PrimitiveType/*auto*/.I8);
		}
	}
	if (Strings/*auto*/.equalsTo("String", stripped/*auto*/)) {
		if (Platform/*auto*/.TypeScript === platform/*auto*/) {
			return new Some<Type>(PrimitiveType/*auto*/.String);
		}
		else {
			return new Some<Type>(new SliceType(PrimitiveType/*auto*/.I8));
		}
	}
	if (Strings/*auto*/.equalsTo("int", stripped/*auto*/) || Strings/*auto*/.equalsTo("Integer", stripped/*auto*/)) {
		if (Platform/*auto*/.Magma === platform/*auto*/) {
			return new Some<Type>(PrimitiveType/*auto*/.I32);
		}
		else {
			return new Some<Type>(PrimitiveType/*auto*/.Number);
		}
	}
	if (Strings/*auto*/.equalsTo("boolean", stripped/*auto*/) || Strings/*auto*/.equalsTo("Boolean", stripped/*auto*/)) {
		return new Some<Type>(new BooleanType(platform/*auto*/));
	}
	if (Strings/*auto*/.equalsTo("var", stripped/*auto*/)) {
		return new Some<Type>(PrimitiveType/*auto*/.Var);
	}
	if (Strings/*auto*/.equalsTo("void", stripped/*auto*/)) {
		return new Some<Type>(PrimitiveType/*auto*/.Void);
	}
	return new None<Type>(/*auto*/);
}
auto temp(CompileState state1, &[I8] s) {
	return Main/*auto*/.compileTypeArgument(state1/*auto*/, s/*auto*/);
}
auto temp() {{
			CompileState compileState = argsState/*auto*/.addResolvedImportFromCache(base/*auto*/);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState/*auto*/, new TemplateType(base/*auto*/, args/*auto*/)));
		}
}
auto temp(&[I8] baseString, &[I8] argsString) {{
		Tuple2<CompileState, List<&[I8]>> argsTuple = Main/*auto*/.parseValuesOrEmpty(state/*auto*/, argsString/*auto*/, lambdaDefinition/*auto*/);
		CompileState argsState = argsTuple/*auto*/.left(/*auto*/);
		List<&[I8]> args = argsTuple/*auto*/.right(/*auto*/);
		&[I8] base = Strings/*auto*/.strip(baseString/*auto*/);
		return Main/*auto*/.assembleFunctionType(argsState/*auto*/, base/*auto*/, args/*auto*/).or(lambdaDefinition/*auto*/);
	}
}
auto temp(&[I8] withoutEnd) {
	return Main/*auto*/.compileFirst(withoutEnd/*auto*/, "<", lambdaDefinition/*auto*/);
}
static Option<Tuple2<CompileState, Type>> parseGeneric(CompileState state, &[I8] input) {
	return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), ">", lambdaDefinition/*auto*/);
}
auto temp(Type generated) {
	return new Tuple2Impl<CompileState, Type>(state/*auto*/, generated/*auto*/);
}
static Option<Tuple2<CompileState, Type>> assembleFunctionType(CompileState state, &[I8] base, List<&[I8]> args) {
	return Main/*auto*/.mapFunctionType(base/*auto*/, args/*auto*/).map(lambdaDefinition/*auto*/);
}
auto temp() {
	return args/*auto*/.find(1/*auto*/);
}
auto temp(Tuple2<&[I8], &[I8]> tuple) {
	return new FunctionType(Lists/*auto*/.of(tuple/*auto*/.left(/*auto*/)), tuple/*auto*/.right(/*auto*/));
}
auto temp() {
	return args/*auto*/.find(1/*auto*/);
}
auto temp() {
	return args/*auto*/.find(2/*auto*/);
}
auto temp(Tuple2<Tuple2<&[I8], &[I8]>, &[I8]> tuple) {
	return new FunctionType(Lists/*auto*/.of(tuple/*auto*/.left(/*auto*/).left(/*auto*/), tuple/*auto*/.left(/*auto*/).right(/*auto*/)), tuple/*auto*/.right(/*auto*/));
}
auto temp(&[I8] first) {
	return new FunctionType(Lists/*auto*/.empty(/*auto*/), first/*auto*/);
}
auto temp(&[I8] first) {
	return new FunctionType(Lists/*auto*/.of(first/*auto*/), "void");
}
auto temp(&[I8] first) {
	return new FunctionType(Lists/*auto*/.of(first/*auto*/), "boolean");
}
static Option<Type> mapFunctionType(&[I8] base, List<&[I8]> args) {
	if (Strings/*auto*/.equalsTo("Function", base/*auto*/)) {
		return args/*auto*/.findFirst(/*auto*/).and(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/);
	}
	if (Strings/*auto*/.equalsTo("BiFunction", base/*auto*/)) {
		return args/*auto*/.find(0/*auto*/).and(lambdaDefinition/*auto*/).and(lambdaDefinition/*auto*/).map(lambdaDefinition/*auto*/);
	}
	if (Strings/*auto*/.equalsTo("Supplier", base/*auto*/)) {
		return args/*auto*/.findFirst(/*auto*/).map(lambdaDefinition/*auto*/);
	}
	if (Strings/*auto*/.equalsTo("Consumer", base/*auto*/)) {
		return args/*auto*/.findFirst(/*auto*/).map(lambdaDefinition/*auto*/);
	}
	if (Strings/*auto*/.equalsTo("Predicate", base/*auto*/)) {
		return args/*auto*/.findFirst(/*auto*/).map(lambdaDefinition/*auto*/);
	}
	return new None<Type>(/*auto*/);
}
auto temp(CompileState state2, &[I8] input1) {
	return Main/*auto*/.compileWhitespace(state2/*auto*/, input1/*auto*/);
}
auto temp(CompileState state1, &[I8] type) {
	return Main/*auto*/.compileType(state1/*auto*/, type/*auto*/);
}
static Option<Tuple2<CompileState, &[I8]>> compileTypeArgument(CompileState state, &[I8] input) {
	return Main/*auto*/.or(state/*auto*/, input/*auto*/, Lists/*auto*/.of(lambdaDefinition/*auto*/, lambdaDefinition/*auto*/));
}
static Tuple2<CompileState, List<T>> parseValuesOrEmpty(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	return Main/*auto*/.parseValues(state/*auto*/, input/*auto*/, mapper/*auto*/).orElse(new Tuple2Impl<CompileState, List<T>>(state/*auto*/, Lists/*auto*/.empty(/*auto*/)));
}
static Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	return Main/*auto*/.parseAll(state/*auto*/, input/*auto*/, Main/*auto*/.foldValues, mapper/*auto*/);
}
static DivideState foldValues(DivideState state, I8 c) {
	if ("," === c/*auto*/ && state/*auto*/.isLevel(/*auto*/)) {
		return state/*auto*/.advance(/*auto*/);
	}
	DivideState appended = state/*auto*/.append(c/*auto*/);
	if ("-" === c/*auto*/) {
		I8 peeked = appended/*auto*/.peek(/*auto*/);
		if (">" === peeked/*auto*/) {
			return appended/*auto*/.popAndAppendToOption(/*auto*/).orElse(appended/*auto*/);
		}
		else {
			return appended/*auto*/;
		}
	}
	if ("<" === c/*auto*/ || "(" === c/*auto*/) {
		return appended/*auto*/.enter(/*auto*/);
	}
	if (">" === c/*auto*/ || ")" === c/*auto*/) {
		return appended/*auto*/.exit(/*auto*/);
	}
	return appended/*auto*/;
}
static Option<T> compileLast(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main/*auto*/.compileInfix(input/*auto*/, infix/*auto*/, Main/*auto*/.findLast, mapper/*auto*/);
}
static number findLast(&[I8] input, &[I8] infix) {
	return input/*auto*/.lastIndexOf(infix/*auto*/);
}
static Option<T> compileSuffix(&[I8] input, &[I8] suffix, (arg0 : &[I8]) => Option<T> mapper) {
	if (!input/*auto*/.endsWith(suffix/*auto*/)) {
		return new None<T>(/*auto*/);
	}
	number length = Strings/*auto*/.length(input/*auto*/);
	number length1 = Strings/*auto*/.length(suffix/*auto*/);
	&[I8] content = Strings/*auto*/.sliceBetween(input/*auto*/, 0/*auto*/, length/*auto*/ - length1/*auto*/);
	return mapper/*auto*/(content/*auto*/);
}
static Option<T> compileFirst(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main/*auto*/.compileInfix(input/*auto*/, infix/*auto*/, Main/*auto*/.findFirst, mapper/*auto*/);
}
static Option<T> compileInfix(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => number locator, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main/*auto*/.compileSplit(Main/*auto*/.split(input/*auto*/, infix/*auto*/, locator/*auto*/), mapper/*auto*/);
}
auto temp(Tuple2<&[I8], &[I8]> tuple) {
	return mapper/*auto*/(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/));
}
static Option<T> compileSplit(Option<Tuple2<&[I8], &[I8]>> splitter, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return splitter/*auto*/.flatMap(lambdaDefinition/*auto*/);
}
static Option<Tuple2<&[I8], &[I8]>> split(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => number locator) {
	number index = locator/*auto*/(input/*auto*/, infix/*auto*/);
	if (0/*auto*/ > index/*auto*/) {
		return new None<Tuple2<&[I8], &[I8]>>(/*auto*/);
	}
	&[I8] left = Strings/*auto*/.sliceBetween(input/*auto*/, 0/*auto*/, index/*auto*/);
	number length = Strings/*auto*/.length(infix/*auto*/);
	&[I8] right = Strings/*auto*/.sliceFrom(input/*auto*/, index/*auto*/ + length/*auto*/);
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(left/*auto*/, right/*auto*/));
}
static number findFirst(&[I8] input, &[I8] infix) {
	return input/*auto*/.indexOf(infix/*auto*/);
}
static &[I8] generatePlaceholder(&[I8] input) {
	&[I8] replaced = input/*auto*/.replace("/*", "start").replace("*/", "end");
	return "/*" + replaced/*auto*/ + "*/";
}Main.main();
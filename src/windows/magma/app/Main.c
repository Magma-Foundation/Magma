#include "./Main.h"
export class Main {
}

auto temp(List<Path> children) {
	return Main.runWithChildren(children, sourceDirectory);
}
auto temp(IOError value) {
	return new Some<IOError>(value);
}
auto temp(IOError error) {
	return error.display();
}
auto temp(&[I8] displayed) {
	return Console.printErrLn(displayed);
}
static void main() {
	Path sourceDirectory = Files.get(".", "src", "java");
	sourceDirectory.walk().match(temp, temp).map(temp).ifPresent(temp);
}
auto temp(Path source) {
	return source.endsWith(".java");
}
auto temp(Path child) {
	return new Source(sourceDirectory, child);
}
auto temp(CompileState state, Source source) {
	return state.addSource(source);
}
auto temp(Tuple2<CompileState, Option<IOError>> current, Source source1) {
	return Main.foldChild(current.left(), current.right(), source1);
}
static Option<IOError> runWithChildren(List<Path> children, Path sourceDirectory) {
	List<Source> sources = children.query().filter(temp).map(temp).collect(new ListCollector<Source>());
	CompileState initial = sources.query().foldWithInitial(ImmutableCompileState.createInitial(), temp);
	return sources.query().foldWithInitial(Main.createInitialState(initial), temp).right();
}
static Tuple2<CompileState, Option<IOError>> createInitialState(CompileState state) {
	return new Tuple2Impl<CompileState, Option<IOError>>(state, new None<IOError>());
}
static Tuple2<CompileState, Option<IOError>> foldChild(CompileState state, Option<IOError> maybeError, Source source) {
	if (maybeError.isPresent()) {
		return new Tuple2Impl<CompileState, Option<IOError>>(state, maybeError);
	}
	return Main.runWithSource(state, source);
}
auto temp(&[I8] input) {
	return Main.getCompileStateOptionTuple2(state, source, input);
}
auto temp(IOError value) {
	return new Tuple2Impl<CompileState, Option<IOError>>(state, new Some<IOError>(value));
}
static Tuple2<CompileState, Option<IOError>> runWithSource(CompileState state, Source source) {
	return source.read().match(temp, temp);
}
auto temp() {
	return magmaTuple.right();
}
auto temp() {
	return windowsTuple.right();
}
static Tuple2Impl<CompileState, Option<IOError>> getCompileStateOptionTuple2(CompileState state, Source source, &[I8] input) {
	Tuple2<CompileState, Option<IOError>> typeScriptTuple = Main.compileAndWrite(state, source, input, Platform.TypeScript);
	Tuple2<CompileState, Option<IOError>> magmaTuple = Main.compileAndWrite(typeScriptTuple.left(), source, input, Platform.Magma);
	Tuple2<CompileState, Option<IOError>> windowsTuple = Main.compileAndWrite(magmaTuple.left(), source, input, Platform.Windows);
	return new Tuple2Impl<CompileState, Option<IOError>>(windowsTuple.left(), typeScriptTuple.right().or(temp).or(temp));
}
auto temp() {
	return target.writeString(output.right().get(extension));
}
auto temp(Option<IOError> ioErrorOption, &[I8] extension) {{
		Path target = targetParent.resolveChild(location.name() + "." + extension);
		return ioErrorOption.or(temp);
	}
}
static Tuple2<CompileState, Option<IOError>> compileAndWrite(CompileState state, Source source, &[I8] input, Platform platform) {
	CompileState state1 = state.withLocation(source.computeLocation()).withPlatform(platform);
	Tuple2Impl<CompileState, Map<&[I8], &[I8]>> output = Main.compileRoot(state1, source, input);
	Location location = output.left().findCurrentLocation().orElse(new Location(Lists.empty(), ""));
	Path targetDirectory = Files.get(".", "src", platform.root);
	Path targetParent = targetDirectory.resolveChildSegments(location.namespace());
	if (!targetParent.exists()) {
		Option<IOError> maybeError = targetParent.createDirectories();
		if (maybeError.isPresent()) {
			return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), maybeError);
		}
	}
	Option<IOError> initial = new None<IOError>();
	Option<IOError> ioErrorOption1 = Queries.fromArray(platform.extension).foldWithInitial(initial, temp);
	return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), ioErrorOption1);
}
auto temp(Import anImport) {
	return anImport.generate(state.platform());
}
static Tuple2Impl<CompileState, Map<&[I8], &[I8]>> compileRoot(CompileState state, Source source, &[I8] input) {
	Tuple2<CompileState, &[I8]> statementsTuple = Main.compileStatements(state, input, Main.compileRootSegment);
	CompileState statementsState = statementsTuple.left();
	&[I8] imports = statementsState.imports().query().map(temp).collect(new Joiner("")).orElse("");
	CompileState compileState = statementsState.clearImports().clear();
	&[I8] withMain = Main.createMain(source);
	HashMap<&[I8], &[I8]> entries = new HashMap<&[I8], &[I8]>();
	Platform platform = state.platform();
	if (Platform.Windows === platform) {
		&[I8] value = /* source.computeNamespace().query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + source.computeName()*/;
		/*entries.put(Platform.Windows.extension[0], Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"))*/;
		/*entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + source.computeName() + ".h\"") + statementsState.join() + statementsTuple.right() + withMain)*/;
	}
	else {
		/*entries.put(platform.extension[0], imports + statementsState.join() + statementsTuple.right() + withMain)*/;
	}
	return new Tuple2Impl<>(compileState, entries);
}
static &[I8] generateDirective(&[I8] content) {
	return "#" + content + "\n";
}
static &[I8] createMain(Source source) {
	if (Strings.equalsTo(source.computeName(), "Main")) {
		return "Main.main();";
	}
	return "";
}
static Tuple2<CompileState, &[I8]> compileStatements(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]> mapper) {
	return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
}
auto temp(CompileState state1, &[I8] s) {
	return new Some<Tuple2<CompileState, &[I8]>>(mapper(state1, s));
}
static Tuple2<CompileState, &[I8]> compileAll(CompileState state, &[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]> mapper, (arg0 : &[I8], arg1 : &[I8]) => &[I8] merger) {
	Tuple2<CompileState, List<&[I8]>> folded = Main.parseAll(state, input, folder, temp).orElse(new Tuple2Impl<CompileState, List<&[I8]>>(state, Lists.empty()));
	return new Tuple2Impl<CompileState, &[I8]>(folded.left(), Main.generateAll(folded.right(), merger));
}
static &[I8] generateAll(List<&[I8]> elements, (arg0 : &[I8], arg1 : &[I8]) => &[I8] merger) {
	return elements.query().foldWithInitial("", merger);
}
auto temp(Tuple2<CompileState, T> mappedTuple) {{
			CompileState mappedState = mappedTuple.left();
			T mappedElement = mappedTuple.right();
			return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
		}
}
auto temp(Tuple2<CompileState, List<T>> current) {{
		CompileState currentState = current.left();
		List<T> currentElement = current.right();
		return biFunction(currentState, segment).map(temp);
	}
}
auto temp(Option<Tuple2<CompileState, List<T>>> maybeCurrent, &[I8] segment) {
	return maybeCurrent.flatMap(temp);
}
static Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, &[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> biFunction) {
	return Main.divide(input, folder).query().foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), temp);
}
static &[I8] mergeStatements(&[I8] cache, &[I8] element) {
	return cache + element;
}
auto temp() {
	return Main.foldDoubleQuotes(poppedState, popped);
}
auto temp() {
	return folder(poppedState, popped);
}
static List<&[I8]> divide(&[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder) {
	DivideState current = DivideState.createInitial(input);
	while (true) {
		Tuple2<Bool, Tuple2<DivideState, I8>> poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, I8>(current, "\0"));
		if (!poppedTuple0.left()) {
			break;
		}
		Tuple2<DivideState, I8> poppedTuple = poppedTuple0.right();
		DivideState poppedState = poppedTuple.left();
		I8 popped = poppedTuple.right();
		current = Main.foldSingleQuotes(poppedState, popped).or(temp).orElseGet(temp);
	}
	return current.advance().segments();
}
static Option<DivideState> foldDoubleQuotes(DivideState state, I8 c) {
	if ("\"" !== c) {
		return new None<DivideState>();
	}
	DivideState appended = state.append(c);
	while (true) {
		Tuple2<Bool, Tuple2<DivideState, I8>> maybeTuple = appended.popAndAppendToTuple().toTuple(new Tuple2Impl<DivideState, I8>(appended, "\0"));
		if (!maybeTuple.left()) {
			break;
		}
		Tuple2<DivideState, I8> tuple = maybeTuple.right();
		appended = tuple.left();
		if ("\\" === tuple.right()) {
			appended = appended.popAndAppendToOption().orElse(appended);
		}
		if ("\"" === tuple.right()) {
			break;
		}
	}
	return new Some<DivideState>(appended);
}
auto temp(DivideState state1) {
	return state1.popAndAppendToOption();
}
static Option<DivideState> foldSingleQuotes(DivideState state, I8 c) {
	if ("\'" !== c) {
		return new None<DivideState>();
	}
	return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap(temp);
}
static Option<DivideState> foldEscaped(Tuple2<DivideState, I8> tuple) {
	DivideState state = tuple.left();
	I8 c = tuple.right();
	if ("\\" === c) {
		return state.popAndAppendToOption();
	}
	return new Some<DivideState>(state);
}
static DivideState foldStatements(DivideState state, I8 c) {
	DivideState appended = state.append(c);
	if (";" === c && appended.isLevel()) {
		return appended.advance();
	}
	if ("}" === c && appended.isShallow()) {
		return appended.advance().exit();
	}
	if ("{" === c || "(" === c) {
		return appended.enter();
	}
	if ("}" === c || ")" === c) {
		return appended.exit();
	}
	return appended;
}
static Tuple2<CompileState, &[I8]> compileRootSegment(CompileState state, &[I8] input) {
	return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class ")));
}
auto temp(&[I8] s, &[I8] s2) {{
		List<&[I8]> annotations = Main.parseAnnotations(s);
		if (annotations.contains("Actual", Strings.equalsTo)) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
		}
		return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent);
	}
}
auto temp() {{
		List<&[I8]> modifiers = Main.parseModifiers(beforeContent);
		return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
	}
}
auto temp(&[I8] inputContent) {
	return Main.compileLast(beforeInfix, "\n", temp).or(temp);
}
auto temp(&[I8] beforeContent, &[I8] withEnd) {
	return Main.compileSuffix(Strings.strip(withEnd), "}", temp);
}
auto temp(&[I8] beforeInfix, &[I8] afterInfix) {
	return Main.compileFirst(afterInfix, "{", temp);
}
auto temp(CompileState state, &[I8] input1) {
	return Main.compileFirst(input1, sourceInfix, temp);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createStructureRule(&[I8] sourceInfix, &[I8] targetInfix) {
	return temp;
}
auto temp(Tuple2<CompileState, Type> implementingTuple) {
	return Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content);
}
auto temp(&[I8] s, &[I8] s2) {
	return Main.parseType(state, s2).flatMap(temp);
}
auto temp() {
	return Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithImplementing(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, &[I8] content) {
	return Main.compileLast(beforeContent, " implements ", temp).or(temp);
}
auto temp(&[I8] beforeExtends, &[I8] afterExtends) {
	return Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeExtends, new Some<&[I8]>(afterExtends), maybeImplementing, inputContent);
}
auto temp() {
	return Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, new None<&[I8]>(), maybeImplementing, inputContent);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithExtends(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, Option<Type> maybeImplementing, &[I8] inputContent) {
	return Main.compileFirst(beforeContent, " extends ", temp).or(temp);
}
auto temp(&[I8] parametersString, &[I8] _) {{
		&[I8] name = Strings.strip(rawName);
		Tuple2<CompileState, List<Parameter>> parametersTuple = Main.parseParameters(state, parametersString);
		List<Definition> parameters = Main.retainDefinitionsFromParameters(parametersTuple.right());
		return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
	}
}
auto temp(&[I8] rawName, &[I8] withParameters) {
	return Main.compileFirst(withParameters, ")", temp);
}
auto temp() {
	return Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithParameters(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, Option<&[I8]> maybeSuperType, Option<Type> maybeImplementing, &[I8] inputContent) {
	return Main.compileFirst(beforeContent, "(", temp).or(temp);
}
auto temp(Parameter parameter) {
	return parameter.asDefinition();
}
static List<Definition> retainDefinitionsFromParameters(List<Parameter> parameters) {
	return parameters.query().map(temp).flatMap(Queries.fromOption).collect(new ListCollector<Definition>());
}
auto temp(&[I8] name, &[I8] typeParamsString) {{
		List<&[I8]> typeParams = Main.divideValues(typeParamsString);
		return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
	}
}
auto temp(&[I8] withoutTypeParamEnd) {
	return Main.compileFirst(withoutTypeParamEnd, "<", temp);
}
auto temp() {
	return Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType);
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithTypeParams(CompileState state, &[I8] infix, &[I8] content, &[I8] beforeParams, List<Definition> parameters, Option<Type> maybeImplementing, List<&[I8]> annotations, List<&[I8]> modifiers, Option<&[I8]> maybeSuperType) {
	return Main.compileSuffix(Strings.strip(beforeParams), ">", temp).or(temp);
}
auto temp(&[I8] value) {
	return value + " ";
}
auto temp(Location location) {
	return new Location(location.namespace(), location.name() + "Instance");
}
auto temp(&[I8] inner) {
	return " extends " + inner;
}
static Option<Tuple2<CompileState, &[I8]>> assembleStructure(CompileState state, List<&[I8]> annotations, List<&[I8]> oldModifiers, &[I8] infix, &[I8] rawName, List<&[I8]> typeParams, List<Definition> parameters, Option<Type> maybeImplementing, &[I8] content, Option<&[I8]> maybeSuperType) {
	&[I8] name = Strings.strip(rawName);
	if (!Main.isSymbol(name)) {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
	Tuple2<CompileState, &[I8]> outputContentTuple = Main.compileStatements(state.pushStructureName(name), content, Main.compileClassSegment);
	CompileState outputContentState = outputContentTuple.left().popStructureName();
	&[I8] outputContent = outputContentTuple.right();
	Platform platform = outputContentState.platform();
	&[I8] constructorString = Main.generateConstructorFromRecordParameters(parameters, platform);
	&[I8] joinedTypeParams = Joiner.joinOrEmpty(typeParams, ", ", "<", ">");
	&[I8] implementingString = Main.generateImplementing(maybeImplementing);
	List<&[I8]> newModifiers = Main.modifyModifiers0(oldModifiers);
	&[I8] joinedModifiers = newModifiers.query().map(temp).collect(Joiner.empty()).orElse("");
	if (annotations.contains("Namespace", Strings.equalsTo)) {
		&[I8] actualInfix = "interface ";
		&[I8] newName = name + "Instance";
		&[I8] generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters, platform) + constructorString + outputContent + "\n}\n";
		CompileState withNewLocation = outputContentState.append(generated).mapLocation(temp);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(withNewLocation, ""));
	}
	else {
		&[I8] extendsString = maybeSuperType.map(temp).orElse("");
		&[I8] infix1 = Main.retainStruct(infix, outputContentState);
		&[I8] generated = joinedModifiers + infix1 + name + joinedTypeParams + extendsString + implementingString + " {" + Main.joinParameters(parameters, platform) + constructorString + outputContent + "\n}\n";
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(outputContentState.append(generated), ""));
	}
}
static &[I8] retainStruct(&[I8] infix, CompileState outputContentState) {
	if (outputContentState.isPlatform(Platform.Magma)) {
		return "struct ";
	}
	return infix;
}
static List<&[I8]> modifyModifiers0(List<&[I8]> oldModifiers) {
	if (oldModifiers.contains("public", Strings.equalsTo)) {
		return Lists.of("export");
	}
	return Lists.empty();
}
auto temp(Type type) {
	return type.generate();
}
auto temp(&[I8] inner) {
	return " implements " + inner;
}
static &[I8] generateImplementing(Option<Type> maybeImplementing) {
	return maybeImplementing.map(temp).map(temp).orElse("");
}
auto temp(Definition definition) {
	return definition.generate(platform);
}
auto temp(&[I8] generatedParameters) {
	return Main.generateConstructorWithParameterString(parameters, generatedParameters);
}
static &[I8] generateConstructorFromRecordParameters(List<Definition> parameters, Platform platform) {
	return parameters.query().map(temp).collect(new Joiner(", ")).map(temp).orElse("");
}
static &[I8] generateConstructorWithParameterString(List<Definition> parameters, &[I8] parametersString) {
	&[I8] constructorAssignments = Main.generateConstructorAssignments(parameters);
	return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
}
auto temp(Definition definition) {
	return "\n\t\tthis." + definition.name() + " = " + definition.name() + ";";
}
static &[I8] generateConstructorAssignments(List<Definition> parameters) {
	return parameters.query().map(temp).collect(Joiner.empty()).orElse("");
}
auto temp(Definition definition) {
	return definition.generate(platform);
}
auto temp(&[I8] generated) {
	return "\n\t" + generated + ";";
}
static &[I8] joinParameters(List<Definition> parameters, Platform platform) {
	return parameters.query().map(temp).map(temp).collect(Joiner.empty()).orElse("");
}
static Option<Tuple2<CompileState, &[I8]>> compileNamespaced(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
	}
	return new None<>();
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(state, Main.generatePlaceholder(input));
}
static Tuple2<CompileState, &[I8]> compileOrPlaceholder(CompileState state, &[I8] input, List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>> rules) {
	return Main.or(state, input, rules).orElseGet(temp);
}
auto temp((arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> rule) {
	return Main.getApply(state, input, rule);
}
static Option<Tuple2<CompileState, T>> or(CompileState state, &[I8] input, List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>> rules) {
	return rules.query().map(temp).flatMap(Queries.fromOption).next();
}
static Option<Tuple2<CompileState, T>> getApply(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> rule) {
	return rule(state, input);
}
static Tuple2<CompileState, &[I8]> compileClassSegment(CompileState state1, &[I8] input1) {
	return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class "), Main.compileMethod, Main.compileFieldDefinition));
}
auto temp(&[I8] _, &[I8] name) {{
			if (state.hasLastStructureNameOf(name)) {
				return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		}
}
auto temp() {{
			if (state.hasLastStructureNameOf(strippedBeforeParams)) {
				return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		}
}
auto temp(Tuple2<CompileState, Definition> tuple) {
	return Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams);
}
auto temp() {
	return Main.parseDefinition(state, beforeParams).flatMap(temp);
}
auto temp(&[I8] beforeParams, &[I8] withParams) {{
		&[I8] strippedBeforeParams = Strings.strip(beforeParams);
		return Main.compileLast(strippedBeforeParams, " ", temp).or(temp).or(temp);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileMethod(CompileState state, &[I8] input) {
	return Main.compileFirst(input, "(", temp);
}
auto temp(&[I8] withoutContentEnd) {{
			CompileState compileState1 = parametersState.enterDepth();
			CompileState compileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
			Tuple2<CompileState, &[I8]> statementsTuple = Main.compileFunctionStatements(compileState.defineAll(definitions), withoutContentEnd);
			CompileState compileState2 = statementsTuple.left().exitDepth();
			&[I8] indent = compileState2.createIndent();
			CompileState exited = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
			FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader, definitions, new Some<>(statementsTuple.right()));
			&[I8] generated = sFunctionSegment.generate(parametersState.platform(), indent);
			if (exited.isPlatform(Platform.Windows)) {
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited.addFunction(generated), ""));
			}
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited, generated));
		}
}
auto temp(&[I8] withoutContentStart) {
	return Main.compileSuffix(Strings.strip(withoutContentStart), "}", temp);
}
auto temp() {{
			if (Strings.equalsTo(";", Strings.strip(afterParams))) {
				FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader, definitions, new None<>());
				&[I8] generate = sFunctionSegment.generate(parametersState.platform(), "\n\t");
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState, generate));
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		}
}
auto temp(&[I8] params, &[I8] afterParams) {{
		Tuple2<CompileState, List<Parameter>> parametersTuple = Main.parseParameters(state, params);
		CompileState parametersState = parametersTuple.left();
		List<Parameter> parameters = parametersTuple.right();
		List<Definition> definitions = Main.retainDefinitionsFromParameters(parameters);
		FunctionHeader<S> newHeader = Main.retainDef(header, parametersState);
		if (newHeader.hasAnnotation("Actual")) {
			S aStatic = newHeader.removeModifier("static");
			FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader, definitions, new None<>());
			&[I8] generate = sFunctionSegment.generate(parametersState.platform(), "\n\t");
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState, generate));
		}
		return Main.compilePrefix(Strings.strip(afterParams), "{", temp).or(temp);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileMethodWithBeforeParams(CompileState state, FunctionHeader<S> header, &[I8] withParams) {
	return Main.compileFirst(withParams, ")", temp);
}
static FunctionHeader<S> retainDef(FunctionHeader<S> header, CompileState parametersState) {
	if (parametersState.isPlatform(Platform.Magma)) {
		return header.addModifierLast("def").removeModifier("mut");
	}
	return header;
}
auto temp(CompileState state1, &[I8] s) {
	return new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s));
}
static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, &[I8] params) {
	return Main.parseValuesOrEmpty(state, params, temp);
}
static Tuple2<CompileState, &[I8]> compileFunctionStatements(CompileState state, &[I8] input) {
	return Main.compileStatements(state, input, Main.compileFunctionSegment);
}
static Tuple2<CompileState, &[I8]> compileFunctionSegment(CompileState state, &[I8] input) {
	return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
}
static Option<Tuple2<CompileState, &[I8]>> compileEmptySegment(CompileState state, &[I8] input) {
	if (Strings.equalsTo(";", Strings.strip(input))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ";"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
}
auto temp(&[I8] withoutPrefix) {
	return Main.compileValue(state1, withoutPrefix);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), state1.createIndent() + tuple.right());
}
static Option<Tuple2<CompileState, &[I8]>> compileReturnWithoutSuffix(CompileState state1, &[I8] input1) {
	return Main.compileReturn(input1, temp).map(temp);
}
auto temp(Tuple2<CompileState, &[I8]> headerTuple) {{
		Tuple2<CompileState, &[I8]> contentTuple = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content);
		&[I8] indent = state.createIndent();
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
	}
}
auto temp(&[I8] beforeContent) {
	return Main.compileBlockHeader(state, beforeContent).flatMap(temp);
}
auto temp(&[I8] beforeContentWithEnd, &[I8] content) {
	return Main.compileSuffix(beforeContentWithEnd, "{", temp);
}
auto temp(&[I8] withoutEnd) {
	return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), temp);
}
static Option<Tuple2<CompileState, &[I8]>> compileBlock(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), "}", temp);
}
static DivideState foldBlockStarts(DivideState state, I8 c) {
	DivideState appended = state.append(c);
	if ("{" === c) {
		DivideState entered = appended.enter();
		if (entered.isShallow()) {
			return entered.advance();
		}
		else {
			return entered;
		}
	}
	if ("}" === c) {
		return appended.exit();
	}
	return appended;
}
static Option<Tuple2<CompileState, &[I8]>> compileBlockHeader(CompileState state, &[I8] input) {
	return Main.or(state, input, Lists.of(Main.createConditionalRule("if"), Main.createConditionalRule("while"), Main.compileElse));
}
auto temp(&[I8] withoutConditionEnd) {{
			Tuple2<CompileState, &[I8]> tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), prefix + " (" + tuple.right() + ") "));
		}
}
auto temp(&[I8] withoutConditionStart) {
	return Main.compileSuffix(withoutConditionStart, ")", temp);
}
auto temp(&[I8] withoutPrefix) {{
		&[I8] strippedCondition = Strings.strip(withoutPrefix);
		return Main.compilePrefix(strippedCondition, "(", temp);
	}
}
auto temp(CompileState state1, &[I8] input1) {
	return Main.compilePrefix(Strings.strip(input1), prefix, temp);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createConditionalRule(&[I8] prefix) {
	return temp;
}
static Option<Tuple2<CompileState, &[I8]>> compileElse(CompileState state, &[I8] input) {
	if (Strings.equalsTo("else", Strings.strip(input))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, "else "));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
}
auto temp(&[I8] withoutEnd) {{
		Tuple2<CompileState, &[I8]> valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"));
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileFunctionStatement(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), ";", temp);
}
auto temp(Tuple2<CompileState, Value> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate(tuple.left().platform()));
}
auto temp(CompileState state1, &[I8] input) {
	return Main.parseInvokable(state1, input).map(temp);
}
static Tuple2<CompileState, &[I8]> compileFunctionStatementValue(CompileState state, &[I8] withoutEnd) {
	return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, temp, Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
}
static Option<Tuple2<CompileState, &[I8]>> compileBreak(CompileState state, &[I8] input) {
	if (Strings.equalsTo("break", Strings.strip(input))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, "break"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
}
auto temp(&[I8] child) {{
		Tuple2<CompileState, &[I8]> tuple = Main.compileValueOrPlaceholder(state1, child);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right() + suffix));
	}
}
auto temp(CompileState state1, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), suffix, temp);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createPostRule(&[I8] suffix) {
	return temp;
}
auto temp(&[I8] value1) {
	return Main.compileValue(state, value1);
}
static Option<Tuple2<CompileState, &[I8]>> compileReturnWithValue(CompileState state, &[I8] input) {
	return Main.compileReturn(input, temp);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {
	return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), "return " + tuple.right()));
}
auto temp(&[I8] value) {
	return mapper(value).flatMap(temp);
}
static Option<Tuple2<CompileState, &[I8]>> compileReturn(&[I8] input, (arg0 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> mapper) {
	return Main.compilePrefix(Strings.strip(input), "return ", temp);
}
auto temp(Tuple2<CompileState, &[I8]> callerTuple) {{
		CompileState callerState = callerTuple.left();
		&[I8] caller = callerTuple.right();
		return Main.assembleInvokable(callerState, new ConstructionCaller(caller, callerState.platform()), args);
	}
}
auto temp(&[I8] type) {
	return Main.compileType(state, type).flatMap(temp);
}
auto temp(Tuple2<CompileState, Value> callerTuple) {
	return Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args);
}
auto temp() {
	return Main.parseValue(state, callerString).flatMap(temp);
}
auto temp(&[I8] callerString) {
	return Main.compilePrefix(Strings.strip(callerString), "new ", temp).or(temp);
}
auto temp(&[I8] callerWithArgStart, &[I8] args) {
	return Main.compileSuffix(callerWithArgStart, "(", temp);
}
auto temp(&[I8] withoutEnd) {
	return Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), temp);
}
static Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), ")", temp);
}
auto temp(List<&[I8]> divisions1) {
	return Main.selectLast(divisions1, delimiter);
}
static Option<Tuple2<&[I8], &[I8]>> splitFoldedLast(&[I8] input, &[I8] delimiter, (arg0 : DivideState, arg1 : I8) => DivideState folder) {
	return Main.splitFolded(input, folder, temp);
}
static Option<Tuple2<&[I8], &[I8]>> splitFolded(&[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : List<&[I8]>) => Option<Tuple2<&[I8], &[I8]>> selector) {
	List<&[I8]> divisions = Main.divide(input, folder);
	if (2 > divisions.size()) {
		return new None<Tuple2<&[I8], &[I8]>>();
	}
	return selector(divisions);
}
static Option<Tuple2<&[I8], &[I8]>> selectLast(List<&[I8]> divisions, &[I8] delimiter) {
	List<&[I8]> beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
	&[I8] last = divisions.findLast().orElse("");
	&[I8] joined = beforeLast.query().collect(new Joiner(delimiter)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(joined, last));
}
static DivideState foldInvocationStarts(DivideState state, I8 c) {
	DivideState appended = state.append(c);
	if ("(" === c) {
		DivideState entered = appended.enter();
		if (entered.isShallow()) {
			return entered.advance();
		}
		else {
			return entered;
		}
	}
	if (")" === c) {
		return appended.exit();
	}
	return appended;
}
auto temp(CompileState state1, &[I8] s) {
	return Main.parseArgument(state1, s);
}
auto temp(Argument argument) {
	return argument.toValue();
}
auto temp(Tuple2<CompileState, List<Argument>> argsTuple) {{
		CompileState argsState = argsTuple.left();
		List<Value> args = Main.retain(argsTuple.right(), temp);
		Caller newCaller = Main.transformCaller(argsState, oldCaller);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new InvokableNode(newCaller, args)));
	}
}
static Option<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, Caller oldCaller, &[I8] argsString) {
	return Main.parseValues(state, argsString, temp).flatMap(temp);
}
auto temp(Value parent) {{
		Type parentType = parent.resolve(state);
		if (parentType.isFunctional()) {
			return new Some<Caller>(parent);
		}
		return new None<Caller>();
	}
}
static Caller transformCaller(CompileState state, Caller oldCaller) {
	return oldCaller.findChild().flatMap(temp).orElse(oldCaller);
}
static List<R> retain(List<T> args, (arg0 : T) => Option<R> mapper) {
	return args.query().map(mapper).flatMap(Queries.fromOption).collect(new ListCollector<R>());
}
auto temp(Tuple2<CompileState, Value> tuple) {
	return new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right());
}
static Option<Tuple2<CompileState, Argument>> parseArgument(CompileState state1, &[I8] input) {
	return Main.parseValue(state1, input).map(temp);
}
auto temp(Tuple2<CompileState, Definition> definitionTuple) {{
			CompileState definitionState = definitionTuple.left();
			Definition definition = definitionTuple.right();
			Definition let = Main.attachLet(definitionState, definition);
			&[I8] generate = let.generate(definitionState.platform());
			return new Tuple2Impl<CompileState, &[I8]>(definitionState, generate);
		}
}
auto temp() {
	return Main.parseDefinition(sourceTuple.left(), destination).map(temp);
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(sourceTuple.left(), Main.generatePlaceholder(destination));
}
auto temp(&[I8] destination, &[I8] source) {{
		Tuple2<CompileState, &[I8]> sourceTuple = Main.compileValueOrPlaceholder(state, source);
		Tuple2<CompileState, &[I8]> destinationTuple = Main.compileValue(sourceTuple.left(), destination).or(temp).orElseGet(temp);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileAssignment(CompileState state, &[I8] input) {
	return Main.compileFirst(input, "=", temp);
}
static Definition attachLet(CompileState definitionState, Definition definition) {
	/*final Definition let*/;
	if (definitionState.isPlatform(Platform.Windows)) {
		let = definition;
	}
	else {
		let = definition.addModifierLast("let");
	}
	return let;
}
auto temp() {
	return new Tuple2Impl<CompileState, &[I8]>(state, Main.generatePlaceholder(input));
}
static Tuple2<CompileState, &[I8]> compileValueOrPlaceholder(CompileState state, &[I8] input) {
	return Main.compileValue(state, input).orElseGet(temp);
}
auto temp(Tuple2<CompileState, Value> tuple) {{
		&[I8] generated = tuple.right().generate(tuple.left().platform());
		return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), generated);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileValue(CompileState state, &[I8] input) {
	return Main.parseValue(state, input).map(temp);
}
static Option<Tuple2<CompileState, Value>> parseValue(CompileState state, &[I8] input) {
	return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
}
auto temp(&[I8] s1) {
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringNode(s1)));
}
auto temp(&[I8] s) {
	return Main.compileSuffix(s, slice, temp);
}
auto temp(CompileState state1, &[I8] input1) {{
		&[I8] stripped = Strings.strip(input1);
		return Main.compilePrefix(stripped, slice, temp);
	}
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createTextRule(&[I8] slice) {
	return temp;
}
auto temp(&[I8] withoutPrefix) {{
		Tuple2<CompileState, &[I8]> childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
		CompileState childState = childTuple.left();
		&[I8] child = "!" + childTuple.right();
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new NotNode(child)));
	}
}
static Option<Tuple2<CompileState, Value>> parseNot(CompileState state, &[I8] input) {
	return Main.compilePrefix(Strings.strip(input), "!", temp);
}
auto temp(CompileState state1, &[I8] s) {
	return Main.parseParameter(state1, s);
}
auto temp(Tuple2<CompileState, List<Parameter>> paramNames) {
	return Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow);
}
auto temp(&[I8] withoutEnd) {
	return Main.parseValues(state, withoutEnd, temp).flatMap(temp);
}
auto temp(&[I8] withoutStart) {
	return Main.compileSuffix(withoutStart, ")", temp);
}
auto temp(&[I8] beforeArrow, &[I8] afterArrow) {{
		&[I8] strippedBeforeArrow = Strings.strip(beforeArrow);
		return Main.compilePrefix(strippedBeforeArrow, "(", temp);
	}
}
static Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, &[I8] input) {
	return Main.compileFirst(input, "->", temp);
}
auto temp(&[I8] withoutContentEnd) {{
		Tuple2<CompileState, &[I8]> statementsTuple = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
		CompileState statementsState = statementsTuple.left();
		&[I8] statements = statementsTuple.right();
		CompileState exited = statementsState.exitDepth();
		&[I8] content = "{" + statements + exited.createIndent() + "}";
		if (exited.isPlatform(Platform.Windows)) {
			return getTuple2Some(exited, paramNames, content);
		}
		return getSome(exited, paramNames, content);
	}
}
auto temp(&[I8] withoutContentStart) {
	return Main.compileSuffix(withoutContentStart, "}", temp);
}
auto temp(Tuple2<CompileState, &[I8]> tuple) {{
		CompileState state1 = tuple.left();
		&[I8] content = tuple.right();
		if (state1.isPlatform(Platform.Windows)) {
			return getTuple2Some(state1, paramNames, "\n\treturn " + content + ";");
		}
		return getSome(state1, paramNames, content);
	}
}
auto temp() {
	return Main.compileValue(state, strippedAfterArrow).flatMap(temp);
}
static Option<Tuple2<CompileState, Value>> compileLambdaWithParameterNames(CompileState state, List<Definition> paramNames, &[I8] afterArrow) {
	&[I8] strippedAfterArrow = Strings.strip(afterArrow);
	return Main.compilePrefix(strippedAfterArrow, "{", temp).or(temp);
}
static Some<Tuple2<CompileState, Value>> getSome(CompileState state, List<Definition> parameters, &[I8] content) {
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new LambdaNode(parameters, content)));
}
static Some<Tuple2<CompileState, Value>> getTuple2Some(CompileState state, List<Definition> parameters, &[I8] content) {
	FunctionSegment<Definition> value = new FunctionSegment<Definition>(new Definition(PrimitiveType.Auto, "temp"), parameters, new Some<>(content));
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state.addFunction(value.generate(state.platform(), "\n")), new SymbolNode("temp")));
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createOperatorRule(&[I8] infix) {
	return Main.createOperatorRuleWithDifferentInfix(infix, infix);
}
auto temp(Tuple2<CompileState, Value> childTuple) {{
			CompileState childState = childTuple.left();
			Value child = childTuple.right();
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessNode(child, property)));
		}
}
auto temp(&[I8] childString, &[I8] rawProperty) {{
		&[I8] property = Strings.strip(rawProperty);
		if (!Main.isSymbol(property)) {
			return new None<Tuple2<CompileState, Value>>();
		}
		return Main.parseValue(state, childString).flatMap(temp);
	}
}
auto temp(CompileState state, &[I8] input) {
	return Main.compileLast(input, infix, temp);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createAccessRule(&[I8] infix) {
	return temp;
}
auto temp(List<&[I8]> divisions) {
	return Main.selectFirst(divisions, sourceInfix);
}
auto temp(Tuple2<CompileState, Value> rightTuple) {{
		Value left = leftTuple.right();
		Value right = rightTuple.right();
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new OperationNode(left, targetInfix, right)));
	}
}
auto temp(Tuple2<CompileState, Value> leftTuple) {
	return Main.parseValue(leftTuple.left(), rightString).flatMap(temp);
}
auto temp(&[I8] leftString, &[I8] rightString) {
	return Main.parseValue(state1, leftString).flatMap(temp);
}
auto temp(CompileState state1, &[I8] input1) {
	return Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), temp), temp);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createOperatorRuleWithDifferentInfix(&[I8] sourceInfix, &[I8] targetInfix) {
	return temp;
}
static Option<Tuple2<&[I8], &[I8]>> selectFirst(List<&[I8]> divisions, &[I8] delimiter) {
	&[I8] first = divisions.findFirst().orElse("");
	&[I8] afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(first, afterFirst));
}
auto temp(Tuple2<DivideState, I8> tuple) {
	return tuple.left();
}
auto temp(DivideState state, I8 c) {{
		if (c === Strings.charAt(infix, 0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
			number length = Strings.length(infix) - 1;
			number counter = 0;
			DivideState current = state;
			while (counter < length) {
				counter++;
				current = current.pop().map(temp).orElse(current);
			}
			return current.advance();
		}
		return state.append(c);
	}
}
static (arg0 : DivideState, arg1 : I8) => DivideState foldOperator(&[I8] infix) {
	return temp;
}
static Option<Tuple2<CompileState, Value>> parseNumber(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	if (Main.isNumber(stripped)) {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new SymbolNode(stripped)));
	}
	else {
		return new None<Tuple2<CompileState, Value>>();
	}
}
auto temp(number index) {
	return input.charAt(index);
}
auto temp(I8 c) {
	return Characters.isDigit(c);
}
static Bool isNumber(&[I8] input) {
	HeadedQuery<number> query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
	return query.map(temp).allMatch(temp);
}
static Option<Tuple2<CompileState, Value>> parseSymbol(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	if (Main.isSymbol(stripped)) {
		CompileState withImport = state.addResolvedImportFromCache(stripped);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new SymbolNode(stripped)));
	}
	else {
		return new None<Tuple2<CompileState, Value>>();
	}
}
auto temp(number index) {
	return Main.isSymbolChar(index, Strings.charAt(input, index));
}
static Bool isSymbol(&[I8] input) {
	HeadedQuery<number> query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
	return query.allMatch(temp);
}
static Bool isSymbolChar(number index, I8 c) {
	return "_" === c || Characters.isLetter(c) || (0 !== index && Characters.isDigit(c));
}
static Option<Tuple2<CompileState, T>> compilePrefix(&[I8] input, &[I8] infix, (arg0 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	if (!input.startsWith(infix)) {
		return new None<Tuple2<CompileState, T>>();
	}
	&[I8] slice = Strings.sliceFrom(input, Strings.length(infix));
	return mapper(slice);
}
auto temp(Tuple2<CompileState, Whitespace> tuple) {{
		&[I8] generate = tuple.right().generate(tuple.left().platform());
		return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), generate);
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileWhitespace(CompileState state, &[I8] input) {
	return Main.parseWhitespace(state, input).map(temp);
}
static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, &[I8] input) {
	if (Strings.isBlank(input)) {
		return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
	}
	return new None<Tuple2<CompileState, Whitespace>>();
}
auto temp() {
	return Main.compileEnumValues(state, withoutEnd);
}
auto temp(&[I8] withoutEnd) {
	return Main.getTupleOption(state, withoutEnd).or(temp);
}
static Option<Tuple2<CompileState, &[I8]>> compileFieldDefinition(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), ";", temp);
}
auto temp(Tuple2<CompileState, Parameter> definitionTuple) {{
		&[I8] generate = "\n\t" + definitionTuple.right().generate(definitionTuple.left().platform()) + ";";
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(definitionTuple.left(), generate));
	}
}
static Option<Tuple2<CompileState, &[I8]>> getTupleOption(CompileState state, &[I8] withoutEnd) {
	return Main.parseParameter(state, withoutEnd).flatMap(temp);
}
auto temp(&[I8] stringOption) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), stringOption);
}
auto temp(Tuple2<CompileState, Value> tuple) {{
		&[I8] structureName = state.findLastStructureName().orElse("");
		return tuple.right().generateAsEnumValue(structureName, state.platform()).map(temp);
	}
}
auto temp(CompileState state1, &[I8] s) {
	return Main.parseInvokable(state1, s).flatMap(temp);
}
auto temp(Tuple2<CompileState, List<&[I8]>> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse(""));
}
static Option<Tuple2<CompileState, &[I8]>> compileEnumValues(CompileState state, &[I8] withoutEnd) {
	return Main.parseValues(state, withoutEnd, temp).map(temp);
}
auto temp() {
	return new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input));
}
static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(CompileState state, &[I8] input) {
	return Main.parseParameter(state, input).orElseGet(temp);
}
auto temp(Tuple2<CompileState, Whitespace> tuple) {
	return Main.getCompileStateParameterTuple2(tuple);
}
auto temp(Tuple2<CompileState, Definition> tuple) {
	return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
}
auto temp() {
	return Main.parseDefinition(state, input).map(temp);
}
static Option<Tuple2<CompileState, Parameter>> parseParameter(CompileState state, &[I8] input) {
	return Main.parseWhitespace(state, input).map(temp).or(temp);
}
static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(Tuple2<CompileState, Whitespace> tuple) {
	return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
}
auto temp(&[I8] annotationsString, &[I8] afterAnnotations) {{
		List<&[I8]> annotations = Main.parseAnnotations(annotationsString);
		return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
	}
}
auto temp() {
	return Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name);
}
auto temp(&[I8] beforeType, &[I8] type) {
	return Main.compileLast(Strings.strip(beforeType), "\n", temp).or(temp);
}
auto temp() {
	return Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name);
}
auto temp(&[I8] beforeName, &[I8] name) {
	return Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main.foldTypeSeparators), temp).or(temp);
}
static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, &[I8] input) {
	return Main.compileLast(Strings.strip(input), " ", temp);
}
auto temp(DivideState state1, I8 c) {
	return Main.foldDelimited(state1, c, "\n");
}
auto temp(&[I8] s2) {
	return Strings.strip(s2);
}
auto temp(&[I8] value) {
	return !Strings.isEmpty(value);
}
auto temp(&[I8] value) {
	return 1 <= Strings.length(value);
}
auto temp(&[I8] value) {
	return Strings.sliceFrom(value, 1);
}
auto temp(&[I8] s1) {
	return Strings.strip(s1);
}
auto temp(&[I8] value) {
	return !Strings.isEmpty(value);
}
static List<&[I8]> parseAnnotations(&[I8] s) {
	return Main.divide(s, temp).query().map(temp).filter(temp).filter(temp).map(temp).map(temp).filter(temp).collect(new ListCollector<&[I8]>());
}
auto temp(&[I8] beforeTypeParams, &[I8] typeParamsString) {{
		List<&[I8]> typeParams = Main.divideValues(typeParamsString);
		return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
	}
}
auto temp(&[I8] withoutTypeParamEnd) {
	return Main.compileFirst(withoutTypeParamEnd, "<", temp);
}
auto temp() {{
		List<&[I8]> divided = Main.parseModifiers(beforeType);
		return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
	}
}
static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(CompileState state, List<&[I8]> annotations, &[I8] beforeType, &[I8] type, &[I8] name) {
	return Main.compileSuffix(Strings.strip(beforeType), ">", temp).or(temp);
}
auto temp(DivideState state1, I8 c) {
	return Main.foldDelimited(state1, c, " ");
}
auto temp(&[I8] s) {
	return Strings.strip(s);
}
auto temp(&[I8] value) {
	return !Strings.isEmpty(value);
}
static List<&[I8]> parseModifiers(&[I8] beforeType) {
	return Main.divide(Strings.strip(beforeType), temp).query().map(temp).filter(temp).collect(new ListCollector<&[I8]>());
}
static DivideState foldDelimited(DivideState state1, I8 c, I8 delimiter) {
	if (delimiter === c) {
		return state1.advance();
	}
	return state1.append(c);
}
auto temp(&[I8] input1) {
	return Strings.strip(input1);
}
auto temp(&[I8] value) {
	return !Strings.isEmpty(value);
}
static List<&[I8]> divideValues(&[I8] input) {
	return Main.divide(input, Main.foldValues).query().map(temp).filter(temp).collect(new ListCollector<&[I8]>());
}
static DivideState foldTypeSeparators(DivideState state, I8 c) {
	if (" " === c && state.isLevel()) {
		return state.advance();
	}
	DivideState appended = state.append(c);
	if ("<" === c) {
		return appended.enter();
	}
	if (">" === c) {
		return appended.exit();
	}
	return appended;
}
auto temp(Tuple2<CompileState, Type> typeTuple) {{
		List<&[I8]> newModifiers = Main.modifyModifiers(oldModifiers, state.platform());
		Definition generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
		return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
	}
}
static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeParameters(CompileState state, List<&[I8]> annotations, List<&[I8]> typeParams, List<&[I8]> oldModifiers, &[I8] type, &[I8] name) {
	return Main.parseType(state, type).flatMap(temp);
}
static List<&[I8]> modifyModifiers(List<&[I8]> oldModifiers, Platform platform) {
	List<&[I8]> list = Main.retainFinal(oldModifiers, platform);
	if (oldModifiers.contains("static", Strings.equalsTo)) {
		return list.addLast("static");
	}
	return list;
}
static List<&[I8]> retainFinal(List<&[I8]> oldModifiers, Platform platform) {
	if (oldModifiers.contains("final", Strings.equalsTo) || Platform.Magma !== platform) {
		return Lists.empty();
	}
	return Lists.of("mut");
}
auto temp(Tuple2<CompileState, Type> tuple) {
	return new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right());
}
auto temp() {
	return new Tuple2Impl<CompileState, Type>(state, new Placeholder(type));
}
static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, &[I8] type) {
	return Main.parseType(state, type).map(temp).orElseGet(temp);
}
auto temp(Tuple2<CompileState, Type> tuple) {
	return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate());
}
static Option<Tuple2<CompileState, &[I8]>> compileType(CompileState state, &[I8] type) {
	return Main.parseType(state, type).map(temp);
}
static Option<Tuple2<CompileState, Type>> parseType(CompileState state, &[I8] type) {
	return Main.or(state, type, Lists.of(Main.parseArrayType, Main.parseVarArgs, Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
}
auto temp(&[I8] s) {{
		Tuple2<CompileState, Type> child = Main.parseTypeOrPlaceholder(state, s);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new ArrayType(child.right())));
	}
}
static Option<Tuple2<CompileState, Type>> parseArrayType(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	return Main.compileSuffix(stripped, "[]", temp);
}
auto temp(&[I8] s) {{
		Tuple2<CompileState, Type> child = Main.parseTypeOrPlaceholder(state, s);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())));
	}
}
static Option<Tuple2<CompileState, Type>> parseVarArgs(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	return Main.compileSuffix(stripped, "...", temp);
}
static Option<Tuple2<CompileState, Type>> parseSymbolType(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	if (Main.isSymbol(stripped)) {
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state.addResolvedImportFromCache(stripped), new SymbolNode(stripped)));
	}
	return new None<Tuple2<CompileState, Type>>();
}
auto temp(Type result) {
	return new Tuple2Impl<CompileState, Type>(state, result);
}
static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, &[I8] input) {
	return Main.findPrimitiveValue(Strings.strip(input), state.platform()).map(temp);
}
static Option<Type> findPrimitiveValue(&[I8] input, Platform platform) {
	&[I8] stripped = Strings.strip(input);
	if (Strings.equalsTo("char", stripped) || Strings.equalsTo("Character", stripped)) {
		if (Platform.TypeScript === platform) {
			return new Some<Type>(PrimitiveType.String);
		}
		else {
			return new Some<Type>(PrimitiveType.I8);
		}
	}
	if (Strings.equalsTo("String", stripped)) {
		if (Platform.TypeScript === platform) {
			return new Some<Type>(PrimitiveType.String);
		}
		else {
			return new Some<Type>(new SliceType(PrimitiveType.I8));
		}
	}
	if (Strings.equalsTo("int", stripped) || Strings.equalsTo("Integer", stripped)) {
		if (Platform.Magma === platform) {
			return new Some<Type>(PrimitiveType.I32);
		}
		else {
			return new Some<Type>(PrimitiveType.Number);
		}
	}
	if (Strings.equalsTo("boolean", stripped) || Strings.equalsTo("Boolean", stripped)) {
		return new Some<Type>(new BooleanType(platform));
	}
	if (Strings.equalsTo("var", stripped)) {
		return new Some<Type>(PrimitiveType.Var);
	}
	if (Strings.equalsTo("void", stripped)) {
		return new Some<Type>(PrimitiveType.Void);
	}
	return new None<Type>();
}
auto temp(CompileState state1, &[I8] s) {
	return Main.compileTypeArgument(state1, s);
}
auto temp() {{
			CompileState compileState = argsState.addResolvedImportFromCache(base);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)));
		}
}
auto temp(&[I8] baseString, &[I8] argsString) {{
		Tuple2<CompileState, List<&[I8]>> argsTuple = Main.parseValuesOrEmpty(state, argsString, temp);
		CompileState argsState = argsTuple.left();
		List<&[I8]> args = argsTuple.right();
		&[I8] base = Strings.strip(baseString);
		return Main.assembleFunctionType(argsState, base, args).or(temp);
	}
}
auto temp(&[I8] withoutEnd) {
	return Main.compileFirst(withoutEnd, "<", temp);
}
static Option<Tuple2<CompileState, Type>> parseGeneric(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), ">", temp);
}
auto temp(Type generated) {
	return new Tuple2Impl<CompileState, Type>(state, generated);
}
static Option<Tuple2<CompileState, Type>> assembleFunctionType(CompileState state, &[I8] base, List<&[I8]> args) {
	return Main.mapFunctionType(base, args).map(temp);
}
auto temp() {
	return args.find(1);
}
auto temp(Tuple2<&[I8], &[I8]> tuple) {
	return new FunctionType(Lists.of(tuple.left()), tuple.right());
}
auto temp() {
	return args.find(1);
}
auto temp() {
	return args.find(2);
}
auto temp(Tuple2<Tuple2<&[I8], &[I8]>, &[I8]> tuple) {
	return new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right());
}
auto temp(&[I8] first) {
	return new FunctionType(Lists.empty(), first);
}
auto temp(&[I8] first) {
	return new FunctionType(Lists.of(first), "void");
}
auto temp(&[I8] first) {
	return new FunctionType(Lists.of(first), "boolean");
}
static Option<Type> mapFunctionType(&[I8] base, List<&[I8]> args) {
	if (Strings.equalsTo("Function", base)) {
		return args.findFirst().and(temp).map(temp);
	}
	if (Strings.equalsTo("BiFunction", base)) {
		return args.find(0).and(temp).and(temp).map(temp);
	}
	if (Strings.equalsTo("Supplier", base)) {
		return args.findFirst().map(temp);
	}
	if (Strings.equalsTo("Consumer", base)) {
		return args.findFirst().map(temp);
	}
	if (Strings.equalsTo("Predicate", base)) {
		return args.findFirst().map(temp);
	}
	return new None<Type>();
}
auto temp(CompileState state2, &[I8] input1) {
	return Main.compileWhitespace(state2, input1);
}
auto temp(CompileState state1, &[I8] type) {
	return Main.compileType(state1, type);
}
static Option<Tuple2<CompileState, &[I8]>> compileTypeArgument(CompileState state, &[I8] input) {
	return Main.or(state, input, Lists.of(temp, temp));
}
static Tuple2<CompileState, List<T>> parseValuesOrEmpty(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	return Main.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
}
static Option<Tuple2<CompileState, List<T>>> parseValues(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> mapper) {
	return Main.parseAll(state, input, Main.foldValues, mapper);
}
static DivideState foldValues(DivideState state, I8 c) {
	if ("," === c && state.isLevel()) {
		return state.advance();
	}
	DivideState appended = state.append(c);
	if ("-" === c) {
		I8 peeked = appended.peek();
		if (">" === peeked) {
			return appended.popAndAppendToOption().orElse(appended);
		}
		else {
			return appended;
		}
	}
	if ("<" === c || "(" === c) {
		return appended.enter();
	}
	if (">" === c || ")" === c) {
		return appended.exit();
	}
	return appended;
}
static Option<T> compileLast(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main.compileInfix(input, infix, Main.findLast, mapper);
}
static number findLast(&[I8] input, &[I8] infix) {
	return input.lastIndexOf(infix);
}
static Option<T> compileSuffix(&[I8] input, &[I8] suffix, (arg0 : &[I8]) => Option<T> mapper) {
	if (!input.endsWith(suffix)) {
		return new None<T>();
	}
	number length = Strings.length(input);
	number length1 = Strings.length(suffix);
	&[I8] content = Strings.sliceBetween(input, 0, length - length1);
	return mapper(content);
}
static Option<T> compileFirst(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main.compileInfix(input, infix, Main.findFirst, mapper);
}
static Option<T> compileInfix(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => number locator, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return Main.compileSplit(Main.split(input, infix, locator), mapper);
}
auto temp(Tuple2<&[I8], &[I8]> tuple) {
	return mapper(tuple.left(), tuple.right());
}
static Option<T> compileSplit(Option<Tuple2<&[I8], &[I8]>> splitter, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return splitter.flatMap(temp);
}
static Option<Tuple2<&[I8], &[I8]>> split(&[I8] input, &[I8] infix, (arg0 : &[I8], arg1 : &[I8]) => number locator) {
	number index = locator(input, infix);
	if (0 > index) {
		return new None<Tuple2<&[I8], &[I8]>>();
	}
	&[I8] left = Strings.sliceBetween(input, 0, index);
	number length = Strings.length(infix);
	&[I8] right = Strings.sliceFrom(input, index + length);
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(left, right));
}
static number findFirst(&[I8] input, &[I8] infix) {
	return input.indexOf(infix);
}
static &[I8] generatePlaceholder(&[I8] input) {
	&[I8] replaced = input.replace("/*", "start").replace("*/", "end");
	return "/*" + replaced + "*/";
}Main.main();
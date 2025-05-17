#include "./Main.h"
export class Main {
}

static void main() {
	Path sourceDirectory = Files.get(".", "src", "java");
	sourceDirectory.walk().match((List<Path> children) => Main.runWithChildren(children, sourceDirectory), (IOError value) => new Some<IOError>(value)).map((IOError error) => error.display()).ifPresent((&[I8] displayed) => Console.printErrLn(displayed));
}
static Option<IOError> runWithChildren(List<Path> children, Path sourceDirectory) {
	List<Source> sources = children.query().filter((Path source) => source.endsWith(".java")).map((Path child) => new Source(sourceDirectory, child)).collect(new ListCollector<Source>());
	CompileState initial = sources.query().foldWithInitial(ImmutableCompileState.createInitial(), (CompileState state, Source source) => state.addSource(source));
	return sources.query().foldWithInitial(Main.createInitialState(initial), (Tuple2<CompileState, Option<IOError>> current, Source source1) => Main.foldChild(current.left(), current.right(), source1)).right();
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
static Tuple2<CompileState, Option<IOError>> runWithSource(CompileState state, Source source) {
	return source.read().match((&[I8] input) => Main.getCompileStateOptionTuple2(state, source, input), (IOError value) => new Tuple2Impl<CompileState, Option<IOError>>(state, new Some<IOError>(value)));
}
static Tuple2Impl<CompileState, Option<IOError>> getCompileStateOptionTuple2(CompileState state, Source source, &[I8] input) {
	Tuple2<CompileState, Option<IOError>> typeScriptTuple = Main.compileAndWrite(state, source, input, Platform.TypeScript);
	Tuple2<CompileState, Option<IOError>> magmaTuple = Main.compileAndWrite(typeScriptTuple.left(), source, input, Platform.Magma);
	Tuple2<CompileState, Option<IOError>> windowsTuple = Main.compileAndWrite(magmaTuple.left(), source, input, Platform.Windows);
	return new Tuple2Impl<CompileState, Option<IOError>>(windowsTuple.left(), typeScriptTuple.right().or(() => magmaTuple.right()).or(() => windowsTuple.right()));
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
	Option<IOError> ioErrorOption1 = Queries.fromArray(platform.extension).foldWithInitial(initial, (Option<IOError> ioErrorOption, &[I8] extension) => {
		Path target = targetParent.resolveChild(location.name() + "." + extension);
		return ioErrorOption.or(() => target.writeString(output.right().get(extension)));
	});
	return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), ioErrorOption1);
}
static Tuple2Impl<CompileState, Map<&[I8], &[I8]>> compileRoot(CompileState state, Source source, &[I8] input) {
	Tuple2<CompileState, &[I8]> statementsTuple = Main.compileStatements(state, input, Main.compileRootSegment);
	CompileState statementsState = statementsTuple.left();
	&[I8] imports = statementsState.imports().query().map((Import anImport) => anImport.generate(state.platform())).collect(new Joiner("")).orElse("");
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
static Tuple2<CompileState, &[I8]> compileAll(CompileState state, &[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]> mapper, (arg0 : &[I8], arg1 : &[I8]) => &[I8] merger) {
	Tuple2<CompileState, List<&[I8]>> folded = Main.parseAll(state, input, folder, (CompileState state1, &[I8] s) => new Some<Tuple2<CompileState, &[I8]>>(mapper(state1, s))).orElse(new Tuple2Impl<CompileState, List<&[I8]>>(state, Lists.empty()));
	return new Tuple2Impl<CompileState, &[I8]>(folded.left(), Main.generateAll(folded.right(), merger));
}
static &[I8] generateAll(List<&[I8]> elements, (arg0 : &[I8], arg1 : &[I8]) => &[I8] merger) {
	return elements.query().foldWithInitial("", merger);
}
static Option<Tuple2<CompileState, List<T>>> parseAll(CompileState state, &[I8] input, (arg0 : DivideState, arg1 : I8) => DivideState folder, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> biFunction) {
	return Main.divide(input, folder).query().foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (Option<Tuple2<CompileState, List<T>>> maybeCurrent, &[I8] segment) => maybeCurrent.flatMap((Tuple2<CompileState, List<T>> current) => {
		CompileState currentState = current.left();
		List<T> currentElement = current.right();
		return biFunction(currentState, segment).map((Tuple2<CompileState, T> mappedTuple) => {
			CompileState mappedState = mappedTuple.left();
			T mappedElement = mappedTuple.right();
			return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
		});
	}));
}
static &[I8] mergeStatements(&[I8] cache, &[I8] element) {
	return cache + element;
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
		current = Main.foldSingleQuotes(poppedState, popped).or(() => Main.foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder(poppedState, popped));
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
static Option<DivideState> foldSingleQuotes(DivideState state, I8 c) {
	if ("\'" !== c) {
		return new None<DivideState>();
	}
	return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap((DivideState state1) => state1.popAndAppendToOption());
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
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createStructureRule(&[I8] sourceInfix, &[I8] targetInfix) {
	return (CompileState state, &[I8] input1) => Main.compileFirst(input1, sourceInfix, (&[I8] beforeInfix, &[I8] afterInfix) => Main.compileFirst(afterInfix, "{", (&[I8] beforeContent, &[I8] withEnd) => Main.compileSuffix(Strings.strip(withEnd), "}", (&[I8] inputContent) => Main.compileLast(beforeInfix, "\n", (&[I8] s, &[I8] s2) => {
		List<&[I8]> annotations = Main.parseAnnotations(s);
		if (annotations.contains("Actual", Strings.equalsTo)) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
		}
		return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent);
	}).or(() => {
		List<&[I8]> modifiers = Main.parseModifiers(beforeContent);
		return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
	}))));
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithImplementing(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, &[I8] content) {
	return Main.compileLast(beforeContent, " implements ", (&[I8] s, &[I8] s2) => Main.parseType(state, s2).flatMap((Tuple2<CompileState, Type> implementingTuple) => Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content))).or(() => Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content));
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithExtends(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, Option<Type> maybeImplementing, &[I8] inputContent) {
	return Main.compileFirst(beforeContent, " extends ", (&[I8] beforeExtends, &[I8] afterExtends) => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeExtends, new Some<&[I8]>(afterExtends), maybeImplementing, inputContent)).or(() => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, new None<&[I8]>(), maybeImplementing, inputContent));
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithParameters(CompileState state, List<&[I8]> annotations, List<&[I8]> modifiers, &[I8] targetInfix, &[I8] beforeContent, Option<&[I8]> maybeSuperType, Option<Type> maybeImplementing, &[I8] inputContent) {
	return Main.compileFirst(beforeContent, "(", (&[I8] rawName, &[I8] withParameters) => Main.compileFirst(withParameters, ")", (&[I8] parametersString, &[I8] _) => {
		&[I8] name = Strings.strip(rawName);
		Tuple2<CompileState, List<Parameter>> parametersTuple = Main.parseParameters(state, parametersString);
		List<Definition> parameters = Main.retainDefinitionsFromParameters(parametersTuple.right());
		return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
	})).or(() => Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType));
}
static List<Definition> retainDefinitionsFromParameters(List<Parameter> parameters) {
	return parameters.query().map((Parameter parameter) => parameter.asDefinition()).flatMap(Queries.fromOption).collect(new ListCollector<Definition>());
}
static Option<Tuple2<CompileState, &[I8]>> compileStructureWithTypeParams(CompileState state, &[I8] infix, &[I8] content, &[I8] beforeParams, List<Definition> parameters, Option<Type> maybeImplementing, List<&[I8]> annotations, List<&[I8]> modifiers, Option<&[I8]> maybeSuperType) {
	return Main.compileSuffix(Strings.strip(beforeParams), ">", (&[I8] withoutTypeParamEnd) => Main.compileFirst(withoutTypeParamEnd, "<", (&[I8] name, &[I8] typeParamsString) => {
		List<&[I8]> typeParams = Main.divideValues(typeParamsString);
		return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
	})).or(() => Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType));
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
	&[I8] joinedModifiers = newModifiers.query().map((&[I8] value) => value + " ").collect(Joiner.empty()).orElse("");
	if (annotations.contains("Namespace", Strings.equalsTo)) {
		&[I8] actualInfix = "interface ";
		&[I8] newName = name + "Instance";
		&[I8] generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters, platform) + constructorString + outputContent + "\n}\n";
		CompileState withNewLocation = outputContentState.append(generated).mapLocation((Location location) => new Location(location.namespace(), location.name() + "Instance"));
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(withNewLocation, ""));
	}
	else {
		&[I8] extendsString = maybeSuperType.map((&[I8] inner) => " extends " + inner).orElse("");
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
static &[I8] generateImplementing(Option<Type> maybeImplementing) {
	return maybeImplementing.map((Type type) => type.generate()).map((&[I8] inner) => " implements " + inner).orElse("");
}
static &[I8] generateConstructorFromRecordParameters(List<Definition> parameters, Platform platform) {
	return parameters.query().map((Definition definition) => definition.generate(platform)).collect(new Joiner(", ")).map((&[I8] generatedParameters) => Main.generateConstructorWithParameterString(parameters, generatedParameters)).orElse("");
}
static &[I8] generateConstructorWithParameterString(List<Definition> parameters, &[I8] parametersString) {
	&[I8] constructorAssignments = Main.generateConstructorAssignments(parameters);
	return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
}
static &[I8] generateConstructorAssignments(List<Definition> parameters) {
	return parameters.query().map((Definition definition) => "\n\t\tthis." + definition.name() + " = " + definition.name() + ";").collect(Joiner.empty()).orElse("");
}
static &[I8] joinParameters(List<Definition> parameters, Platform platform) {
	return parameters.query().map((Definition definition) => definition.generate(platform)).map((&[I8] generated) => "\n\t" + generated + ";").collect(Joiner.empty()).orElse("");
}
static Option<Tuple2<CompileState, &[I8]>> compileNamespaced(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
	}
	return new None<>();
}
static Tuple2<CompileState, &[I8]> compileOrPlaceholder(CompileState state, &[I8] input, List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>> rules) {
	return Main.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(state, Main.generatePlaceholder(input)));
}
static Option<Tuple2<CompileState, T>> or(CompileState state, &[I8] input, List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>> rules) {
	return rules.query().map(((arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> rule) => Main.getApply(state, input, rule)).flatMap(Queries.fromOption).next();
}
static Option<Tuple2<CompileState, T>> getApply(CompileState state, &[I8] input, (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>> rule) {
	return rule(state, input);
}
static Tuple2<CompileState, &[I8]> compileClassSegment(CompileState state1, &[I8] input1) {
	return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class "), Main.compileMethod, Main.compileFieldDefinition));
}
static Option<Tuple2<CompileState, &[I8]>> compileMethod(CompileState state, &[I8] input) {
	return Main.compileFirst(input, "(", (&[I8] beforeParams, &[I8] withParams) => {
		&[I8] strippedBeforeParams = Strings.strip(beforeParams);
		return Main.compileLast(strippedBeforeParams, " ", (&[I8] _, &[I8] name) => {
			if (state.hasLastStructureNameOf(name)) {
				return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		}).or(() => {
			if (state.hasLastStructureNameOf(strippedBeforeParams)) {
				return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		}).or(() => Main.parseDefinition(state, beforeParams).flatMap((Tuple2<CompileState, Definition> tuple) => Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)));
	});
}
static Option<Tuple2<CompileState, &[I8]>> compileMethodWithBeforeParams(CompileState state, FunctionHeader<S> header, &[I8] withParams) {
	return Main.compileFirst(withParams, ")", (&[I8] params, &[I8] afterParams) => {
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
		return Main.compilePrefix(Strings.strip(afterParams), "{", (&[I8] withoutContentStart) => Main.compileSuffix(Strings.strip(withoutContentStart), "}", (&[I8] withoutContentEnd) => {
			CompileState compileState1 = parametersState.enterDepth();
			CompileState compileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
			Tuple2<CompileState, &[I8]> statementsTuple = Main.compileFunctionStatements(compileState.defineAll(definitions), withoutContentEnd);
			CompileState compileState2 = statementsTuple.left().exitDepth();
			&[I8] indent = compileState2.createIndent();
			CompileState exited = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
			FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader, definitions, new Some<>(statementsTuple.right()));
			&[I8] generated = indent + sFunctionSegment.generate(parametersState.platform(), indent);
			if (exited.isPlatform(Platform.Windows)) {
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited.addFunction(generated), ""));
			}
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited, generated));
		})).or(() => {
			if (Strings.equalsTo(";", Strings.strip(afterParams))) {
				FunctionSegment<S> sFunctionSegment = new FunctionSegment<S>(newHeader, definitions, new None<>());
				&[I8] generate = sFunctionSegment.generate(parametersState.platform(), "\n\t");
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState, generate));
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		});
	});
}
static FunctionHeader<S> retainDef(FunctionHeader<S> header, CompileState parametersState) {
	if (parametersState.isPlatform(Platform.Magma)) {
		return header.addModifierLast("def").removeModifier("mut");
	}
	return header;
}
static Tuple2<CompileState, List<Parameter>> parseParameters(CompileState state, &[I8] params) {
	return Main.parseValuesOrEmpty(state, params, (CompileState state1, &[I8] s) => new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s)));
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
static Option<Tuple2<CompileState, &[I8]>> compileReturnWithoutSuffix(CompileState state1, &[I8] input1) {
	return Main.compileReturn(input1, (&[I8] withoutPrefix) => Main.compileValue(state1, withoutPrefix)).map((Tuple2<CompileState, &[I8]> tuple) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), state1.createIndent() + tuple.right()));
}
static Option<Tuple2<CompileState, &[I8]>> compileBlock(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), "}", (&[I8] withoutEnd) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (&[I8] beforeContentWithEnd, &[I8] content) => Main.compileSuffix(beforeContentWithEnd, "{", (&[I8] beforeContent) => Main.compileBlockHeader(state, beforeContent).flatMap((Tuple2<CompileState, &[I8]> headerTuple) => {
		Tuple2<CompileState, &[I8]> contentTuple = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content);
		&[I8] indent = state.createIndent();
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
	}))));
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
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createConditionalRule(&[I8] prefix) {
	return (CompileState state1, &[I8] input1) => Main.compilePrefix(Strings.strip(input1), prefix, (&[I8] withoutPrefix) => {
		&[I8] strippedCondition = Strings.strip(withoutPrefix);
		return Main.compilePrefix(strippedCondition, "(", (&[I8] withoutConditionStart) => Main.compileSuffix(withoutConditionStart, ")", (&[I8] withoutConditionEnd) => {
			Tuple2<CompileState, &[I8]> tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), prefix + " (" + tuple.right() + ") "));
		}));
	});
}
static Option<Tuple2<CompileState, &[I8]>> compileElse(CompileState state, &[I8] input) {
	if (Strings.equalsTo("else", Strings.strip(input))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, "else "));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
}
static Option<Tuple2<CompileState, &[I8]>> compileFunctionStatement(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), ";", (&[I8] withoutEnd) => {
		Tuple2<CompileState, &[I8]> valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"));
	});
}
static Tuple2<CompileState, &[I8]> compileFunctionStatementValue(CompileState state, &[I8] withoutEnd) {
	return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (CompileState state1, &[I8] input) => Main.parseInvokable(state1, input).map((Tuple2<CompileState, Value> tuple) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate(tuple.left().platform()))), Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
}
static Option<Tuple2<CompileState, &[I8]>> compileBreak(CompileState state, &[I8] input) {
	if (Strings.equalsTo("break", Strings.strip(input))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, "break"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> createPostRule(&[I8] suffix) {
	return (CompileState state1, &[I8] input) => Main.compileSuffix(Strings.strip(input), suffix, (&[I8] child) => {
		Tuple2<CompileState, &[I8]> tuple = Main.compileValueOrPlaceholder(state1, child);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right() + suffix));
	});
}
static Option<Tuple2<CompileState, &[I8]>> compileReturnWithValue(CompileState state, &[I8] input) {
	return Main.compileReturn(input, (&[I8] value1) => Main.compileValue(state, value1));
}
static Option<Tuple2<CompileState, &[I8]>> compileReturn(&[I8] input, (arg0 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> mapper) {
	return Main.compilePrefix(Strings.strip(input), "return ", (&[I8] value) => mapper(value).flatMap((Tuple2<CompileState, &[I8]> tuple) => new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), "return " + tuple.right()))));
}
static Option<Tuple2<CompileState, Value>> parseInvokable(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), ")", (&[I8] withoutEnd) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (&[I8] callerWithArgStart, &[I8] args) => Main.compileSuffix(callerWithArgStart, "(", (&[I8] callerString) => Main.compilePrefix(Strings.strip(callerString), "new ", (&[I8] type) => Main.compileType(state, type).flatMap((Tuple2<CompileState, &[I8]> callerTuple) => {
		CompileState callerState = callerTuple.left();
		&[I8] caller = callerTuple.right();
		return Main.assembleInvokable(callerState, new ConstructionCaller(caller, callerState.platform()), args);
	})).or(() => Main.parseValue(state, callerString).flatMap((Tuple2<CompileState, Value> callerTuple) => Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args))))));
}
static Option<Tuple2<&[I8], &[I8]>> splitFoldedLast(&[I8] input, &[I8] delimiter, (arg0 : DivideState, arg1 : I8) => DivideState folder) {
	return Main.splitFolded(input, folder, (List<&[I8]> divisions1) => Main.selectLast(divisions1, delimiter));
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
static Option<Tuple2<CompileState, Value>> assembleInvokable(CompileState state, Caller oldCaller, &[I8] argsString) {
	return Main.parseValues(state, argsString, (CompileState state1, &[I8] s) => Main.parseArgument(state1, s)).flatMap((Tuple2<CompileState, List<Argument>> argsTuple) => {
		CompileState argsState = argsTuple.left();
		List<Value> args = Main.retain(argsTuple.right(), (Argument argument) => argument.toValue());
		Caller newCaller = Main.transformCaller(argsState, oldCaller);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new InvokableNode(newCaller, args)));
	});
}
static Caller transformCaller(CompileState state, Caller oldCaller) {
	return oldCaller.findChild().flatMap((Value parent) => {
		Type parentType = parent.resolve(state);
		if (parentType.isFunctional()) {
			return new Some<Caller>(parent);
		}
		return new None<Caller>();
	}).orElse(oldCaller);
}
static List<R> retain(List<T> args, (arg0 : T) => Option<R> mapper) {
	return args.query().map(mapper).flatMap(Queries.fromOption).collect(new ListCollector<R>());
}
static Option<Tuple2<CompileState, Argument>> parseArgument(CompileState state1, &[I8] input) {
	return Main.parseValue(state1, input).map((Tuple2<CompileState, Value> tuple) => new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right()));
}
static Option<Tuple2<CompileState, &[I8]>> compileAssignment(CompileState state, &[I8] input) {
	return Main.compileFirst(input, "=", (&[I8] destination, &[I8] source) => {
		Tuple2<CompileState, &[I8]> sourceTuple = Main.compileValueOrPlaceholder(state, source);
		Tuple2<CompileState, &[I8]> destinationTuple = Main.compileValue(sourceTuple.left(), destination).or(() => Main.parseDefinition(sourceTuple.left(), destination).map((Tuple2<CompileState, Definition> definitionTuple) => {
			CompileState definitionState = definitionTuple.left();
			Definition definition = definitionTuple.right();
			Definition let = Main.attachLet(definitionState, definition);
			&[I8] generate = let.generate(definitionState.platform());
			return new Tuple2Impl<CompileState, &[I8]>(definitionState, generate);
		})).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(sourceTuple.left(), Main.generatePlaceholder(destination)));
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
	});
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
static Tuple2<CompileState, &[I8]> compileValueOrPlaceholder(CompileState state, &[I8] input) {
	return Main.compileValue(state, input).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(state, Main.generatePlaceholder(input)));
}
static Option<Tuple2<CompileState, &[I8]>> compileValue(CompileState state, &[I8] input) {
	return Main.parseValue(state, input).map((Tuple2<CompileState, Value> tuple) => {
		&[I8] generated = tuple.right().generate(tuple.left().platform());
		return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), generated);
	});
}
static Option<Tuple2<CompileState, Value>> parseValue(CompileState state, &[I8] input) {
	return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createTextRule(&[I8] slice) {
	return (CompileState state1, &[I8] input1) => {
		&[I8] stripped = Strings.strip(input1);
		return Main.compilePrefix(stripped, slice, (&[I8] s) => Main.compileSuffix(s, slice, (&[I8] s1) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringNode(s1)))));
	};
}
static Option<Tuple2<CompileState, Value>> parseNot(CompileState state, &[I8] input) {
	return Main.compilePrefix(Strings.strip(input), "!", (&[I8] withoutPrefix) => {
		Tuple2<CompileState, &[I8]> childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
		CompileState childState = childTuple.left();
		&[I8] child = "!" + childTuple.right();
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new NotNode(child)));
	});
}
static Option<Tuple2<CompileState, Value>> parseLambda(CompileState state, &[I8] input) {
	return Main.compileFirst(input, "->", (&[I8] beforeArrow, &[I8] afterArrow) => {
		&[I8] strippedBeforeArrow = Strings.strip(beforeArrow);
		return Main.compilePrefix(strippedBeforeArrow, "(", (&[I8] withoutStart) => Main.compileSuffix(withoutStart, ")", (&[I8] withoutEnd) => Main.parseValues(state, withoutEnd, (CompileState state1, &[I8] s) => Main.parseParameter(state1, s)).flatMap((Tuple2<CompileState, List<Parameter>> paramNames) => Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow))));
	});
}
static Option<Tuple2<CompileState, Value>> compileLambdaWithParameterNames(CompileState state, List<Definition> paramNames, &[I8] afterArrow) {
	&[I8] strippedAfterArrow = Strings.strip(afterArrow);
	return Main.compilePrefix(strippedAfterArrow, "{", (&[I8] withoutContentStart) => Main.compileSuffix(withoutContentStart, "}", (&[I8] withoutContentEnd) => {
		Tuple2<CompileState, &[I8]> statementsTuple = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
		CompileState statementsState = statementsTuple.left();
		&[I8] statements = statementsTuple.right();
		CompileState exited = statementsState.exitDepth();
		return Main.assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}");
	})).or(() => Main.compileValue(state, strippedAfterArrow).flatMap((Tuple2<CompileState, &[I8]> tuple) => Main.assembleLambda(tuple.left(), paramNames, tuple.right())));
}
static Option<Tuple2<CompileState, Value>> assembleLambda(CompileState state, List<Definition> paramNames, &[I8] content) {
	if (state.isPlatform(Platform.Windows)) {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state.addFunction(""), new LambdaNode(paramNames, content)));
	}
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new LambdaNode(paramNames, content)));
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createOperatorRule(&[I8] infix) {
	return Main.createOperatorRuleWithDifferentInfix(infix, infix);
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createAccessRule(&[I8] infix) {
	return (CompileState state, &[I8] input) => Main.compileLast(input, infix, (&[I8] childString, &[I8] rawProperty) => {
		&[I8] property = Strings.strip(rawProperty);
		if (!Main.isSymbol(property)) {
			return new None<Tuple2<CompileState, Value>>();
		}
		return Main.parseValue(state, childString).flatMap((Tuple2<CompileState, Value> childTuple) => {
			CompileState childState = childTuple.left();
			Value child = childTuple.right();
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessNode(child, property)));
		});
	});
}
static (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> createOperatorRuleWithDifferentInfix(&[I8] sourceInfix, &[I8] targetInfix) {
	return (CompileState state1, &[I8] input1) => Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (List<&[I8]> divisions) => Main.selectFirst(divisions, sourceInfix)), (&[I8] leftString, &[I8] rightString) => Main.parseValue(state1, leftString).flatMap((Tuple2<CompileState, Value> leftTuple) => Main.parseValue(leftTuple.left(), rightString).flatMap((Tuple2<CompileState, Value> rightTuple) => {
		Value left = leftTuple.right();
		Value right = rightTuple.right();
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new OperationNode(left, targetInfix, right)));
	})));
}
static Option<Tuple2<&[I8], &[I8]>> selectFirst(List<&[I8]> divisions, &[I8] delimiter) {
	&[I8] first = divisions.findFirst().orElse("");
	&[I8] afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(first, afterFirst));
}
static (arg0 : DivideState, arg1 : I8) => DivideState foldOperator(&[I8] infix) {
	return (DivideState state, I8 c) => {
		if (c === Strings.charAt(infix, 0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
			number length = Strings.length(infix) - 1;
			number counter = 0;
			DivideState current = state;
			while (counter < length) {
				counter++;
				current = current.pop().map((Tuple2<DivideState, I8> tuple) => tuple.left()).orElse(current);
			}
			return current.advance();
		}
		return state.append(c);
	};
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
static Bool isNumber(&[I8] input) {
	HeadedQuery<number> query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
	return query.map((number index) => input.charAt(index)).allMatch((I8 c) => Characters.isDigit(c));
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
static Bool isSymbol(&[I8] input) {
	HeadedQuery<number> query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
	return query.allMatch((number index) => Main.isSymbolChar(index, Strings.charAt(input, index)));
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
static Option<Tuple2<CompileState, &[I8]>> compileWhitespace(CompileState state, &[I8] input) {
	return Main.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) => {
		&[I8] generate = tuple.right().generate(tuple.left().platform());
		return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), generate);
	});
}
static Option<Tuple2<CompileState, Whitespace>> parseWhitespace(CompileState state, &[I8] input) {
	if (Strings.isBlank(input)) {
		return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
	}
	return new None<Tuple2<CompileState, Whitespace>>();
}
static Option<Tuple2<CompileState, &[I8]>> compileFieldDefinition(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), ";", (&[I8] withoutEnd) => Main.getTupleOption(state, withoutEnd).or(() => Main.compileEnumValues(state, withoutEnd)));
}
static Option<Tuple2<CompileState, &[I8]>> getTupleOption(CompileState state, &[I8] withoutEnd) {
	return Main.parseParameter(state, withoutEnd).flatMap((Tuple2<CompileState, Parameter> definitionTuple) => {
		&[I8] generate = "\n\t" + definitionTuple.right().generate(definitionTuple.left().platform()) + ";";
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(definitionTuple.left(), generate));
	});
}
static Option<Tuple2<CompileState, &[I8]>> compileEnumValues(CompileState state, &[I8] withoutEnd) {
	return Main.parseValues(state, withoutEnd, (CompileState state1, &[I8] s) => Main.parseInvokable(state1, s).flatMap((Tuple2<CompileState, Value> tuple) => {
		&[I8] structureName = state.findLastStructureName().orElse("");
		return tuple.right().generateAsEnumValue(structureName, state.platform()).map((&[I8] stringOption) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), stringOption));
	})).map((Tuple2<CompileState, List<&[I8]>> tuple) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse("")));
}
static Tuple2<CompileState, Parameter> parseParameterOrPlaceholder(CompileState state, &[I8] input) {
	return Main.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
}
static Option<Tuple2<CompileState, Parameter>> parseParameter(CompileState state, &[I8] input) {
	return Main.parseWhitespace(state, input).map((Tuple2<CompileState, Whitespace> tuple) => Main.getCompileStateParameterTuple2(tuple)).or(() => Main.parseDefinition(state, input).map((Tuple2<CompileState, Definition> tuple) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
}
static Tuple2<CompileState, Parameter> getCompileStateParameterTuple2(Tuple2<CompileState, Whitespace> tuple) {
	return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
}
static Option<Tuple2<CompileState, Definition>> parseDefinition(CompileState state, &[I8] input) {
	return Main.compileLast(Strings.strip(input), " ", (&[I8] beforeName, &[I8] name) => Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main.foldTypeSeparators), (&[I8] beforeType, &[I8] type) => Main.compileLast(Strings.strip(beforeType), "\n", (&[I8] annotationsString, &[I8] afterAnnotations) => {
		List<&[I8]> annotations = Main.parseAnnotations(annotationsString);
		return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
	}).or(() => Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name))).or(() => Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)));
}
static List<&[I8]> parseAnnotations(&[I8] s) {
	return Main.divide(s, (DivideState state1, I8 c) => Main.foldDelimited(state1, c, "\n")).query().map((&[I8] s2) => Strings.strip(s2)).filter((&[I8] value) => !Strings.isEmpty(value)).filter((&[I8] value) => 1 <= Strings.length(value)).map((&[I8] value) => Strings.sliceFrom(value, 1)).map((&[I8] s1) => Strings.strip(s1)).filter((&[I8] value) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
}
static Option<Tuple2<CompileState, Definition>> parseDefinitionWithAnnotations(CompileState state, List<&[I8]> annotations, &[I8] beforeType, &[I8] type, &[I8] name) {
	return Main.compileSuffix(Strings.strip(beforeType), ">", (&[I8] withoutTypeParamEnd) => Main.compileFirst(withoutTypeParamEnd, "<", (&[I8] beforeTypeParams, &[I8] typeParamsString) => {
		List<&[I8]> typeParams = Main.divideValues(typeParamsString);
		return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
	})).or(() => {
		List<&[I8]> divided = Main.parseModifiers(beforeType);
		return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
	});
}
static List<&[I8]> parseModifiers(&[I8] beforeType) {
	return Main.divide(Strings.strip(beforeType), (DivideState state1, I8 c) => Main.foldDelimited(state1, c, " ")).query().map((&[I8] s) => Strings.strip(s)).filter((&[I8] value) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
}
static DivideState foldDelimited(DivideState state1, I8 c, I8 delimiter) {
	if (delimiter === c) {
		return state1.advance();
	}
	return state1.append(c);
}
static List<&[I8]> divideValues(&[I8] input) {
	return Main.divide(input, Main.foldValues).query().map((&[I8] input1) => Strings.strip(input1)).filter((&[I8] value) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
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
static Option<Tuple2<CompileState, Definition>> parseDefinitionWithTypeParameters(CompileState state, List<&[I8]> annotations, List<&[I8]> typeParams, List<&[I8]> oldModifiers, &[I8] type, &[I8] name) {
	return Main.parseType(state, type).flatMap((Tuple2<CompileState, Type> typeTuple) => {
		List<&[I8]> newModifiers = Main.modifyModifiers(oldModifiers, state.platform());
		Definition generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
		return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
	});
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
static Tuple2<CompileState, Type> parseTypeOrPlaceholder(CompileState state, &[I8] type) {
	return Main.parseType(state, type).map((Tuple2<CompileState, Type> tuple) => new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())).orElseGet(() => new Tuple2Impl<CompileState, Type>(state, new Placeholder(type)));
}
static Option<Tuple2<CompileState, &[I8]>> compileType(CompileState state, &[I8] type) {
	return Main.parseType(state, type).map((Tuple2<CompileState, Type> tuple) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate()));
}
static Option<Tuple2<CompileState, Type>> parseType(CompileState state, &[I8] type) {
	return Main.or(state, type, Lists.of(Main.parseArrayType, Main.parseVarArgs, Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
}
static Option<Tuple2<CompileState, Type>> parseArrayType(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	return Main.compileSuffix(stripped, "[]", (&[I8] s) => {
		Tuple2<CompileState, Type> child = Main.parseTypeOrPlaceholder(state, s);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new ArrayType(child.right())));
	});
}
static Option<Tuple2<CompileState, Type>> parseVarArgs(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	return Main.compileSuffix(stripped, "...", (&[I8] s) => {
		Tuple2<CompileState, Type> child = Main.parseTypeOrPlaceholder(state, s);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())));
	});
}
static Option<Tuple2<CompileState, Type>> parseSymbolType(CompileState state, &[I8] input) {
	&[I8] stripped = Strings.strip(input);
	if (Main.isSymbol(stripped)) {
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state.addResolvedImportFromCache(stripped), new SymbolNode(stripped)));
	}
	return new None<Tuple2<CompileState, Type>>();
}
static Option<Tuple2<CompileState, Type>> parsePrimitive(CompileState state, &[I8] input) {
	return Main.findPrimitiveValue(Strings.strip(input), state.platform()).map((Type result) => new Tuple2Impl<CompileState, Type>(state, result));
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
static Option<Tuple2<CompileState, Type>> parseGeneric(CompileState state, &[I8] input) {
	return Main.compileSuffix(Strings.strip(input), ">", (&[I8] withoutEnd) => Main.compileFirst(withoutEnd, "<", (&[I8] baseString, &[I8] argsString) => {
		Tuple2<CompileState, List<&[I8]>> argsTuple = Main.parseValuesOrEmpty(state, argsString, (CompileState state1, &[I8] s) => Main.compileTypeArgument(state1, s));
		CompileState argsState = argsTuple.left();
		List<&[I8]> args = argsTuple.right();
		&[I8] base = Strings.strip(baseString);
		return Main.assembleFunctionType(argsState, base, args).or(() => {
			CompileState compileState = argsState.addResolvedImportFromCache(base);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)));
		});
	}));
}
static Option<Tuple2<CompileState, Type>> assembleFunctionType(CompileState state, &[I8] base, List<&[I8]> args) {
	return Main.mapFunctionType(base, args).map((Type generated) => new Tuple2Impl<CompileState, Type>(state, generated));
}
static Option<Type> mapFunctionType(&[I8] base, List<&[I8]> args) {
	if (Strings.equalsTo("Function", base)) {
		return args.findFirst().and(() => args.find(1)).map((Tuple2<&[I8], &[I8]> tuple) => new FunctionType(Lists.of(tuple.left()), tuple.right()));
	}
	if (Strings.equalsTo("BiFunction", base)) {
		return args.find(0).and(() => args.find(1)).and(() => args.find(2)).map((Tuple2<Tuple2<&[I8], &[I8]>, &[I8]> tuple) => new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right()));
	}
	if (Strings.equalsTo("Supplier", base)) {
		return args.findFirst().map((&[I8] first) => new FunctionType(Lists.empty(), first));
	}
	if (Strings.equalsTo("Consumer", base)) {
		return args.findFirst().map((&[I8] first) => new FunctionType(Lists.of(first), "void"));
	}
	if (Strings.equalsTo("Predicate", base)) {
		return args.findFirst().map((&[I8] first) => new FunctionType(Lists.of(first), "boolean"));
	}
	return new None<Type>();
}
static Option<Tuple2<CompileState, &[I8]>> compileTypeArgument(CompileState state, &[I8] input) {
	return Main.or(state, input, Lists.of((CompileState state2, &[I8] input1) => Main.compileWhitespace(state2, input1), (CompileState state1, &[I8] type) => Main.compileType(state1, type)));
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
static Option<T> compileSplit(Option<Tuple2<&[I8], &[I8]>> splitter, (arg0 : &[I8], arg1 : &[I8]) => Option<T> mapper) {
	return splitter.flatMap((Tuple2<&[I8], &[I8]> tuple) => mapper(tuple.left(), tuple.right()));
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
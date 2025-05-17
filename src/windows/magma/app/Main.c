#include "./Main.h"
export class Main {
}

static main(): void {
	let sourceDirectory: Path = Files.get(".", "src", "java");
	sourceDirectory.walk().match((children: List<Path>) => Main.runWithChildren(children, sourceDirectory), (value: IOError) => new Some<IOError>(value)).map((error: IOError) => error.display()).ifPresent((displayed: &[I8]) => Console.printErrLn(displayed));
}
static runWithChildren(children: List<Path>, sourceDirectory: Path): Option<IOError> {
	let sources: List<Source> = children.query().filter((source: Path) => source.endsWith(".java")).map((child: Path) => new Source(sourceDirectory, child)).collect(new ListCollector<Source>());
	let initial: CompileState = sources.query().foldWithInitial(ImmutableCompileState.createInitial(), (state: CompileState, source: Source) => state.addSource(source));
	return sources.query().foldWithInitial(Main.createInitialState(initial), (current: Tuple2<CompileState, Option<IOError>>, source1: Source) => Main.foldChild(current.left(), current.right(), source1)).right();
}
static createInitialState(state: CompileState): Tuple2<CompileState, Option<IOError>> {
	return new Tuple2Impl<CompileState, Option<IOError>>(state, new None<IOError>());
}
static foldChild(state: CompileState, maybeError: Option<IOError>, source: Source): Tuple2<CompileState, Option<IOError>> {
	if (maybeError.isPresent()) {
		return new Tuple2Impl<CompileState, Option<IOError>>(state, maybeError);
	}
	return Main.runWithSource(state, source);
}
static runWithSource(state: CompileState, source: Source): Tuple2<CompileState, Option<IOError>> {
	return source.read().match((input: &[I8]) => Main.getCompileStateOptionTuple2(state, source, input), (value: IOError) => new Tuple2Impl<CompileState, Option<IOError>>(state, new Some<IOError>(value)));
}
static getCompileStateOptionTuple2(state: CompileState, source: Source, input: &[I8]): Tuple2Impl<CompileState, Option<IOError>> {
	let typeScriptTuple: Tuple2<CompileState, Option<IOError>> = Main.compileAndWrite(state, source, input, Platform.TypeScript);
	let magmaTuple: Tuple2<CompileState, Option<IOError>> = Main.compileAndWrite(typeScriptTuple.left(), source, input, Platform.Magma);
	let windowsTuple: Tuple2<CompileState, Option<IOError>> = Main.compileAndWrite(magmaTuple.left(), source, input, Platform.Windows);
	return new Tuple2Impl<CompileState, Option<IOError>>(windowsTuple.left(), typeScriptTuple.right().or(() => magmaTuple.right()).or(() => windowsTuple.right()));
}
static compileAndWrite(state: CompileState, source: Source, input: &[I8], platform: Platform): Tuple2<CompileState, Option<IOError>> {
	let state1: CompileState = state.withLocation(source.computeLocation()).withPlatform(platform);
	let output: Tuple2Impl<CompileState, Map<&[I8], &[I8]>> = Main.compileRoot(state1, source, input);
	let location: Location = output.left().findCurrentLocation().orElse(new Location(Lists.empty(), ""));
	let targetDirectory: Path = Files.get(".", "src", platform.root);
	let targetParent: Path = targetDirectory.resolveChildSegments(location.namespace());
	if (!targetParent.exists()) {
		let maybeError: Option<IOError> = targetParent.createDirectories();
		if (maybeError.isPresent()) {
			return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), maybeError);
		}
	}
	let initial: Option<IOError> = new None<IOError>();
	let ioErrorOption1: Option<IOError> = Queries.fromArray(platform.extension).foldWithInitial(initial, (ioErrorOption: Option<IOError>, extension: &[I8]) => {
		let target: Path = targetParent.resolveChild(location.name() + "." + extension);
		return ioErrorOption.or(() => target.writeString(output.right().get(extension)));
	});
	return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), ioErrorOption1);
}
static compileRoot(state: CompileState, source: Source, input: &[I8]): Tuple2Impl<CompileState, Map<&[I8], &[I8]>> {
	let statementsTuple: Tuple2<CompileState, &[I8]> = Main.compileStatements(state, input, Main.compileRootSegment);
	let statementsState: CompileState = statementsTuple.left();
	let imports: &[I8] = statementsState.imports().query().map((anImport: Import) => anImport.generate(state.platform())).collect(new Joiner("")).orElse("");
	let compileState: CompileState = statementsState.clearImports().clear();
	let withMain: &[I8] = Main.createMain(source);
	let entries: HashMap<&[I8], &[I8]> = new HashMap<&[I8], &[I8]>();
	let platform: Platform = state.platform();
	if (Platform.Windows === platform) {
		let value: &[I8] = /* source.computeNamespace().query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + source.computeName()*/;
		/*entries.put(Platform.Windows.extension[0], Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"))*/;
		/*entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + source.computeName() + ".h\"") + statementsState.join() + statementsTuple.right() + withMain)*/;
	}
	else {
		/*entries.put(platform.extension[0], imports + statementsState.join() + statementsTuple.right() + withMain)*/;
	}
	return new Tuple2Impl<>(compileState, entries);
}
static generateDirective(content: &[I8]): &[I8] {
	return "#" + content + "\n";
}
static createMain(source: Source): &[I8] {
	if (Strings.equalsTo(source.computeName(), "Main")) {
		return "Main.main();";
	}
	return "";
}
static compileStatements(state: CompileState, input: &[I8], mapper: (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]>): Tuple2<CompileState, &[I8]> {
	return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
}
static compileAll(state: CompileState, input: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState, mapper: (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]>, merger: (arg0 : &[I8], arg1 : &[I8]) => &[I8]): Tuple2<CompileState, &[I8]> {
	let folded: Tuple2<CompileState, List<&[I8]>> = Main.parseAll(state, input, folder, (state1: CompileState, s: &[I8]) => new Some<Tuple2<CompileState, &[I8]>>(mapper(state1, s))).orElse(new Tuple2Impl<CompileState, List<&[I8]>>(state, Lists.empty()));
	return new Tuple2Impl<CompileState, &[I8]>(folded.left(), Main.generateAll(folded.right(), merger));
}
static generateAll(elements: List<&[I8]>, merger: (arg0 : &[I8], arg1 : &[I8]) => &[I8]): &[I8] {
	return elements.query().foldWithInitial("", merger);
}
static parseAll<T>(state: CompileState, input: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState, biFunction: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
	return Main.divide(input, folder).query().foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: &[I8]) => maybeCurrent.flatMap((current: Tuple2<CompileState, List<T>>) => {
		let currentState: CompileState = current.left();
		let currentElement: List<T> = current.right();
		return biFunction(currentState, segment).map((mappedTuple: Tuple2<CompileState, T>) => {
			let mappedState: CompileState = mappedTuple.left();
			let mappedElement: T = mappedTuple.right();
			return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
		});
	}));
}
static mergeStatements(cache: &[I8], element: &[I8]): &[I8] {
	return cache + element;
}
static divide(input: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState): List<&[I8]> {
	let current: DivideState = DivideState.createInitial(input);
	while (true) {
		let poppedTuple0: Tuple2<Bool, Tuple2<DivideState, I8>> = current.pop().toTuple(new Tuple2Impl<DivideState, I8>(current, "\0"));
		if (!poppedTuple0.left()) {
			break;
		}
		let poppedTuple: Tuple2<DivideState, I8> = poppedTuple0.right();
		let poppedState: DivideState = poppedTuple.left();
		let popped: I8 = poppedTuple.right();
		current = Main.foldSingleQuotes(poppedState, popped).or(() => Main.foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder(poppedState, popped));
	}
	return current.advance().segments();
}
static foldDoubleQuotes(state: DivideState, c: I8): Option<DivideState> {
	if ("\"" !== c) {
		return new None<DivideState>();
	}
	let appended: DivideState = state.append(c);
	while (true) {
		let maybeTuple: Tuple2<Bool, Tuple2<DivideState, I8>> = appended.popAndAppendToTuple().toTuple(new Tuple2Impl<DivideState, I8>(appended, "\0"));
		if (!maybeTuple.left()) {
			break;
		}
		let tuple: Tuple2<DivideState, I8> = maybeTuple.right();
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
static foldSingleQuotes(state: DivideState, c: I8): Option<DivideState> {
	if ("\'" !== c) {
		return new None<DivideState>();
	}
	return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap((state1: DivideState) => state1.popAndAppendToOption());
}
static foldEscaped(tuple: Tuple2<DivideState, I8>): Option<DivideState> {
	let state: DivideState = tuple.left();
	let c: I8 = tuple.right();
	if ("\\" === c) {
		return state.popAndAppendToOption();
	}
	return new Some<DivideState>(state);
}
static foldStatements(state: DivideState, c: I8): DivideState {
	let appended: DivideState = state.append(c);
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
static compileRootSegment(state: CompileState, input: &[I8]): Tuple2<CompileState, &[I8]> {
	return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class ")));
}
static createStructureRule(sourceInfix: &[I8], targetInfix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> {
	return (state: CompileState, input1: &[I8]) => Main.compileFirst(input1, sourceInfix, (beforeInfix: &[I8], afterInfix: &[I8]) => Main.compileFirst(afterInfix, "{", (beforeContent: &[I8], withEnd: &[I8]) => Main.compileSuffix(Strings.strip(withEnd), "}", (inputContent: &[I8]) => Main.compileLast(beforeInfix, "\n", (s: &[I8], s2: &[I8]) => {
		let annotations: List<&[I8]> = Main.parseAnnotations(s);
		if (annotations.contains("Actual", Strings.equalsTo)) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
		}
		return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent);
	}).or(() => {
		let modifiers: List<&[I8]> = Main.parseModifiers(beforeContent);
		return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
	}))));
}
static compileStructureWithImplementing(state: CompileState, annotations: List<&[I8]>, modifiers: List<&[I8]>, targetInfix: &[I8], beforeContent: &[I8], content: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileLast(beforeContent, " implements ", (s: &[I8], s2: &[I8]) => Main.parseType(state, s2).flatMap((implementingTuple: Tuple2<CompileState, Type>) => Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content))).or(() => Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content));
}
static compileStructureWithExtends(state: CompileState, annotations: List<&[I8]>, modifiers: List<&[I8]>, targetInfix: &[I8], beforeContent: &[I8], maybeImplementing: Option<Type>, inputContent: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileFirst(beforeContent, " extends ", (beforeExtends: &[I8], afterExtends: &[I8]) => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeExtends, new Some<&[I8]>(afterExtends), maybeImplementing, inputContent)).or(() => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, new None<&[I8]>(), maybeImplementing, inputContent));
}
static compileStructureWithParameters(state: CompileState, annotations: List<&[I8]>, modifiers: List<&[I8]>, targetInfix: &[I8], beforeContent: &[I8], maybeSuperType: Option<&[I8]>, maybeImplementing: Option<Type>, inputContent: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileFirst(beforeContent, "(", (rawName: &[I8], withParameters: &[I8]) => Main.compileFirst(withParameters, ")", (parametersString: &[I8], _: &[I8]) => {
		let name: &[I8] = Strings.strip(rawName);
		let parametersTuple: Tuple2<CompileState, List<Parameter>> = Main.parseParameters(state, parametersString);
		let parameters: List<Definition> = Main.retainDefinitionsFromParameters(parametersTuple.right());
		return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
	})).or(() => Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType));
}
static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
	return parameters.query().map((parameter: Parameter) => parameter.asDefinition()).flatMap(Queries.fromOption).collect(new ListCollector<Definition>());
}
static compileStructureWithTypeParams(state: CompileState, infix: &[I8], content: &[I8], beforeParams: &[I8], parameters: List<Definition>, maybeImplementing: Option<Type>, annotations: List<&[I8]>, modifiers: List<&[I8]>, maybeSuperType: Option<&[I8]>): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileSuffix(Strings.strip(beforeParams), ">", (withoutTypeParamEnd: &[I8]) => Main.compileFirst(withoutTypeParamEnd, "<", (name: &[I8], typeParamsString: &[I8]) => {
		let typeParams: List<&[I8]> = Main.divideValues(typeParamsString);
		return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
	})).or(() => Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType));
}
static assembleStructure(state: CompileState, annotations: List<&[I8]>, oldModifiers: List<&[I8]>, infix: &[I8], rawName: &[I8], typeParams: List<&[I8]>, parameters: List<Definition>, maybeImplementing: Option<Type>, content: &[I8], maybeSuperType: Option<&[I8]>): Option<Tuple2<CompileState, &[I8]>> {
	let name: &[I8] = Strings.strip(rawName);
	if (!Main.isSymbol(name)) {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
	let outputContentTuple: Tuple2<CompileState, &[I8]> = Main.compileStatements(state.pushStructureName(name), content, Main.compileClassSegment);
	let outputContentState: CompileState = outputContentTuple.left().popStructureName();
	let outputContent: &[I8] = outputContentTuple.right();
	let constructorString: &[I8] = Main.generateConstructorFromRecordParameters(parameters);
	let joinedTypeParams: &[I8] = Joiner.joinOrEmpty(typeParams, ", ", "<", ">");
	let implementingString: &[I8] = Main.generateImplementing(maybeImplementing);
	let newModifiers: List<&[I8]> = Main.modifyModifiers0(oldModifiers);
	let joinedModifiers: &[I8] = newModifiers.query().map((value: &[I8]) => value + " ").collect(Joiner.empty()).orElse("");
	if (annotations.contains("Namespace", Strings.equalsTo)) {
		let actualInfix: &[I8] = "interface ";
		let newName: &[I8] = name + "Instance";
		let generated: &[I8] = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
		let withNewLocation: CompileState = outputContentState.append(generated).mapLocation((location: Location) => new Location(location.namespace(), location.name() + "Instance"));
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(withNewLocation, ""));
	}
	else {
		let extendsString: &[I8] = maybeSuperType.map((inner: &[I8]) => " extends " + inner).orElse("");
		let infix1: &[I8] = Main.retainStruct(infix, outputContentState);
		let generated: &[I8] = joinedModifiers + infix1 + name + joinedTypeParams + extendsString + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(outputContentState.append(generated), ""));
	}
}
static retainStruct(infix: &[I8], outputContentState: CompileState): &[I8] {
	if (outputContentState.isPlatform(Platform.Magma)) {
		return "struct ";
	}
	return infix;
}
static modifyModifiers0(oldModifiers: List<&[I8]>): List<&[I8]> {
	if (oldModifiers.contains("public", Strings.equalsTo)) {
		return Lists.of("export");
	}
	return Lists.empty();
}
static generateImplementing(maybeImplementing: Option<Type>): &[I8] {
	return maybeImplementing.map((type: Type) => type.generate()).map((inner: &[I8]) => " implements " + inner).orElse("");
}
static generateConstructorFromRecordParameters(parameters: List<Definition>): &[I8] {
	return parameters.query().map((definition: Definition) => definition.generate()).collect(new Joiner(", ")).map((generatedParameters: &[I8]) => Main.generateConstructorWithParameterString(parameters, generatedParameters)).orElse("");
}
static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: &[I8]): &[I8] {
	let constructorAssignments: &[I8] = Main.generateConstructorAssignments(parameters);
	return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
}
static generateConstructorAssignments(parameters: List<Definition>): &[I8] {
	return parameters.query().map((definition: Definition) => "\n\t\tthis." + definition.name() + " = " + definition.name() + ";").collect(Joiner.empty()).orElse("");
}
static joinParameters(parameters: List<Definition>): &[I8] {
	return parameters.query().map((definition: Definition) => definition.generate()).map((generated: &[I8]) => "\n\t" + generated + ";").collect(Joiner.empty()).orElse("");
}
static compileNamespaced(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	let stripped: &[I8] = Strings.strip(input);
	if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
	}
	return new None<>();
}
static compileOrPlaceholder(state: CompileState, input: &[I8], rules: List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>>): Tuple2<CompileState, &[I8]> {
	return Main.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(state, Main.generatePlaceholder(input)));
}
static or<T>(state: CompileState, input: &[I8], rules: List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
	return rules.query().map((rule: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>) => Main.getApply(state, input, rule)).flatMap(Queries.fromOption).next();
}
static getApply<T>(state: CompileState, input: &[I8], rule: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
	return rule(state, input);
}
static compileClassSegment(state1: CompileState, input1: &[I8]): Tuple2<CompileState, &[I8]> {
	return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class "), Main.compileMethod, Main.compileFieldDefinition));
}
static compileMethod(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileFirst(input, "(", (beforeParams: &[I8], withParams: &[I8]) => {
		let strippedBeforeParams: &[I8] = Strings.strip(beforeParams);
		return Main.compileLast(strippedBeforeParams, " ", (_: &[I8], name: &[I8]) => {
			if (state.hasLastStructureNameOf(name)) {
				return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		}).or(() => {
			if (state.hasLastStructureNameOf(strippedBeforeParams)) {
				return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		}).or(() => Main.parseDefinition(state, beforeParams).flatMap((tuple: Tuple2<CompileState, Definition>) => Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)));
	});
}
static compileMethodWithBeforeParams<S extends FunctionHeader<S>>(state: CompileState, header: FunctionHeader<S>, withParams: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileFirst(withParams, ")", (params: &[I8], afterParams: &[I8]) => {
		let parametersTuple: Tuple2<CompileState, List<Parameter>> = Main.parseParameters(state, params);
		let parametersState: CompileState = parametersTuple.left();
		let parameters: List<Parameter> = parametersTuple.right();
		let definitions: List<Definition> = Main.retainDefinitionsFromParameters(parameters);
		let joinedDefinitions: &[I8] = definitions.query().map((definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
		let newHeader: FunctionHeader<S> = Main.retainDef(header, parametersState);
		if (newHeader.hasAnnotation("Actual")) {
			let headerGenerated: &[I8] = newHeader.removeModifier("static").generateWithAfterName("(" + joinedDefinitions + ")");
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState, "\n\t" + headerGenerated + ";\n"));
		}
		let headerGenerated: &[I8] = newHeader.generateWithAfterName("(" + joinedDefinitions + ")");
		return Main.compilePrefix(Strings.strip(afterParams), "{", (withoutContentStart: &[I8]) => Main.compileSuffix(Strings.strip(withoutContentStart), "}", (withoutContentEnd: &[I8]) => {
			let compileState1: CompileState = parametersState.enterDepth();
			let compileState: CompileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
			let statementsTuple: Tuple2<CompileState, &[I8]> = Main.compileFunctionStatements(compileState.defineAll(definitions), withoutContentEnd);
			let compileState2: CompileState = statementsTuple.left().exitDepth();
			let indent: &[I8] = compileState2.createIndent();
			let exited: CompileState = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
			let generated: &[I8] = indent + headerGenerated + " {" + statementsTuple.right() + indent + "}";
			if (exited.isPlatform(Platform.Windows)) {
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited.addFunction(generated), ""));
			}
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(exited, generated));
		})).or(() => {
			if (Strings.equalsTo(";", Strings.strip(afterParams))) {
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState, "\n\t" + headerGenerated + ";"));
			}
			return new None<Tuple2<CompileState, &[I8]>>();
		});
	});
}
static retainDef<S extends FunctionHeader<S>>(header: FunctionHeader<S>, parametersState: CompileState): FunctionHeader<S> {
	if (parametersState.isPlatform(Platform.Magma)) {
		return header.addModifier("def").removeModifier("mut");
	}
	return header;
}
static parseParameters(state: CompileState, params: &[I8]): Tuple2<CompileState, List<Parameter>> {
	return Main.parseValuesOrEmpty(state, params, (state1: CompileState, s: &[I8]) => new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s)));
}
static compileFunctionStatements(state: CompileState, input: &[I8]): Tuple2<CompileState, &[I8]> {
	return Main.compileStatements(state, input, Main.compileFunctionSegment);
}
static compileFunctionSegment(state: CompileState, input: &[I8]): Tuple2<CompileState, &[I8]> {
	return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
}
static compileEmptySegment(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	if (Strings.equalsTo(";", Strings.strip(input))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ";"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
}
static compileReturnWithoutSuffix(state1: CompileState, input1: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileReturn(input1, (withoutPrefix: &[I8]) => Main.compileValue(state1, withoutPrefix)).map((tuple: Tuple2<CompileState, &[I8]>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), state1.createIndent() + tuple.right()));
}
static compileBlock(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileSuffix(Strings.strip(input), "}", (withoutEnd: &[I8]) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd: &[I8], content: &[I8]) => Main.compileSuffix(beforeContentWithEnd, "{", (beforeContent: &[I8]) => Main.compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple2<CompileState, &[I8]>) => {
		let contentTuple: Tuple2<CompileState, &[I8]> = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content);
		let indent: &[I8] = state.createIndent();
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
	}))));
}
static foldBlockStarts(state: DivideState, c: I8): DivideState {
	let appended: DivideState = state.append(c);
	if ("{" === c) {
		let entered: DivideState = appended.enter();
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
static compileBlockHeader(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.or(state, input, Lists.of(Main.createConditionalRule("if"), Main.createConditionalRule("while"), Main.compileElse));
}
static createConditionalRule(prefix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> {
	return (state1: CompileState, input1: &[I8]) => Main.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: &[I8]) => {
		let strippedCondition: &[I8] = Strings.strip(withoutPrefix);
		return Main.compilePrefix(strippedCondition, "(", (withoutConditionStart: &[I8]) => Main.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: &[I8]) => {
			let tuple: Tuple2<CompileState, &[I8]> = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), prefix + " (" + tuple.right() + ") "));
		}));
	});
}
static compileElse(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	if (Strings.equalsTo("else", Strings.strip(input))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, "else "));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
}
static compileFunctionStatement(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: &[I8]) => {
		let valueTuple: Tuple2<CompileState, &[I8]> = Main.compileFunctionStatementValue(state, withoutEnd);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"));
	});
}
static compileFunctionStatementValue(state: CompileState, withoutEnd: &[I8]): Tuple2<CompileState, &[I8]> {
	return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (state1: CompileState, input: &[I8]) => Main.parseInvokable(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate())), Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
}
static compileBreak(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	if (Strings.equalsTo("break", Strings.strip(input))) {
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, "break"));
	}
	else {
		return new None<Tuple2<CompileState, &[I8]>>();
	}
}
static createPostRule(suffix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> {
	return (state1: CompileState, input: &[I8]) => Main.compileSuffix(Strings.strip(input), suffix, (child: &[I8]) => {
		let tuple: Tuple2<CompileState, &[I8]> = Main.compileValueOrPlaceholder(state1, child);
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right() + suffix));
	});
}
static compileReturnWithValue(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileReturn(input, (value1: &[I8]) => Main.compileValue(state, value1));
}
static compileReturn(input: &[I8], mapper: (arg0 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compilePrefix(Strings.strip(input), "return ", (value: &[I8]) => mapper(value).flatMap((tuple: Tuple2<CompileState, &[I8]>) => new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), "return " + tuple.right()))));
}
static parseInvokable(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
	return Main.compileSuffix(Strings.strip(input), ")", (withoutEnd: &[I8]) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart: &[I8], args: &[I8]) => Main.compileSuffix(callerWithArgStart, "(", (callerString: &[I8]) => Main.compilePrefix(Strings.strip(callerString), "new ", (type: &[I8]) => Main.compileType(state, type).flatMap((callerTuple: Tuple2<CompileState, &[I8]>) => {
		let callerState: CompileState = callerTuple.left();
		let caller: &[I8] = callerTuple.right();
		return Main.assembleInvokable(callerState, new ConstructionCaller(caller, callerState.platform()), args);
	})).or(() => Main.parseValue(state, callerString).flatMap((callerTuple: Tuple2<CompileState, Value>) => Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args))))));
}
static splitFoldedLast(input: &[I8], delimiter: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState): Option<Tuple2<&[I8], &[I8]>> {
	return Main.splitFolded(input, folder, (divisions1: List<&[I8]>) => Main.selectLast(divisions1, delimiter));
}
static splitFolded(input: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState, selector: (arg0 : List<&[I8]>) => Option<Tuple2<&[I8], &[I8]>>): Option<Tuple2<&[I8], &[I8]>> {
	let divisions: List<&[I8]> = Main.divide(input, folder);
	if (2 > divisions.size()) {
		return new None<Tuple2<&[I8], &[I8]>>();
	}
	return selector(divisions);
}
static selectLast(divisions: List<&[I8]>, delimiter: &[I8]): Option<Tuple2<&[I8], &[I8]>> {
	let beforeLast: List<&[I8]> = divisions.subList(0, divisions.size() - 1).orElse(divisions);
	let last: &[I8] = divisions.findLast().orElse("");
	let joined: &[I8] = beforeLast.query().collect(new Joiner(delimiter)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(joined, last));
}
static foldInvocationStarts(state: DivideState, c: I8): DivideState {
	let appended: DivideState = state.append(c);
	if ("(" === c) {
		let entered: DivideState = appended.enter();
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
static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: &[I8]): Option<Tuple2<CompileState, Value>> {
	return Main.parseValues(state, argsString, (state1: CompileState, s: &[I8]) => Main.parseArgument(state1, s)).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
		let argsState: CompileState = argsTuple.left();
		let args: List<Value> = Main.retain(argsTuple.right(), (argument: Argument) => argument.toValue());
		let newCaller: Caller = Main.transformCaller(argsState, oldCaller);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new InvokableNode(newCaller, args)));
	});
}
static transformCaller(state: CompileState, oldCaller: Caller): Caller {
	return oldCaller.findChild().flatMap((parent: Value) => {
		let parentType: Type = parent.resolve(state);
		if (parentType.isFunctional()) {
			return new Some<Caller>(parent);
		}
		return new None<Caller>();
	}).orElse(oldCaller);
}
static retain<T, R>(args: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
	return args.query().map(mapper).flatMap(Queries.fromOption).collect(new ListCollector<R>());
}
static parseArgument(state1: CompileState, input: &[I8]): Option<Tuple2<CompileState, Argument>> {
	return Main.parseValue(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right()));
}
static compileAssignment(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileFirst(input, "=", (destination: &[I8], source: &[I8]) => {
		let sourceTuple: Tuple2<CompileState, &[I8]> = Main.compileValueOrPlaceholder(state, source);
		let destinationTuple: Tuple2<CompileState, &[I8]> = Main.compileValue(sourceTuple.left(), destination).or(() => Main.parseDefinition(sourceTuple.left(), destination).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().addModifier("let").generate()))).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(sourceTuple.left(), Main.generatePlaceholder(destination)));
		return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
	});
}
static compileValueOrPlaceholder(state: CompileState, input: &[I8]): Tuple2<CompileState, &[I8]> {
	return Main.compileValue(state, input).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(state, Main.generatePlaceholder(input)));
}
static compileValue(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.parseValue(state, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate()));
}
static parseValue(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
	return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
}
static createTextRule(slice: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> {
	return (state1: CompileState, input1: &[I8]) => {
		let stripped: &[I8] = Strings.strip(input1);
		return Main.compilePrefix(stripped, slice, (s: &[I8]) => Main.compileSuffix(s, slice, (s1: &[I8]) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringNode(s1)))));
	};
}
static parseNot(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
	return Main.compilePrefix(Strings.strip(input), "!", (withoutPrefix: &[I8]) => {
		let childTuple: Tuple2<CompileState, &[I8]> = Main.compileValueOrPlaceholder(state, withoutPrefix);
		let childState: CompileState = childTuple.left();
		let child: &[I8] = "!" + childTuple.right();
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new NotNode(child)));
	});
}
static parseLambda(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
	return Main.compileFirst(input, "->", (beforeArrow: &[I8], afterArrow: &[I8]) => {
		let strippedBeforeArrow: &[I8] = Strings.strip(beforeArrow);
		return Main.compilePrefix(strippedBeforeArrow, "(", (withoutStart: &[I8]) => Main.compileSuffix(withoutStart, ")", (withoutEnd: &[I8]) => Main.parseValues(state, withoutEnd, (state1: CompileState, s: &[I8]) => Main.parseParameter(state1, s)).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow))));
	});
}
static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: &[I8]): Option<Tuple2<CompileState, Value>> {
	let strippedAfterArrow: &[I8] = Strings.strip(afterArrow);
	return Main.compilePrefix(strippedAfterArrow, "{", (withoutContentStart: &[I8]) => Main.compileSuffix(withoutContentStart, "}", (withoutContentEnd: &[I8]) => {
		let statementsTuple: Tuple2<CompileState, &[I8]> = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
		let statementsState: CompileState = statementsTuple.left();
		let statements: &[I8] = statementsTuple.right();
		let exited: CompileState = statementsState.exitDepth();
		return Main.assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}");
	})).or(() => Main.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple2<CompileState, &[I8]>) => Main.assembleLambda(tuple.left(), paramNames, tuple.right())));
}
static assembleLambda(exited: CompileState, paramNames: List<Definition>, content: &[I8]): Option<Tuple2<CompileState, Value>> {
	return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new LambdaNode(paramNames, content)));
}
static createOperatorRule(infix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> {
	return Main.createOperatorRuleWithDifferentInfix(infix, infix);
}
static createAccessRule(infix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> {
	return (state: CompileState, input: &[I8]) => Main.compileLast(input, infix, (childString: &[I8], rawProperty: &[I8]) => {
		let property: &[I8] = Strings.strip(rawProperty);
		if (!Main.isSymbol(property)) {
			return new None<Tuple2<CompileState, Value>>();
		}
		return Main.parseValue(state, childString).flatMap((childTuple: Tuple2<CompileState, Value>) => {
			let childState: CompileState = childTuple.left();
			let child: Value = childTuple.right();
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessNode(child, property)));
		});
	});
}
static createOperatorRuleWithDifferentInfix(sourceInfix: &[I8], targetInfix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> {
	return (state1: CompileState, input1: &[I8]) => Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (divisions: List<&[I8]>) => Main.selectFirst(divisions, sourceInfix)), (leftString: &[I8], rightString: &[I8]) => Main.parseValue(state1, leftString).flatMap((leftTuple: Tuple2<CompileState, Value>) => Main.parseValue(leftTuple.left(), rightString).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
		let left: Value = leftTuple.right();
		let right: Value = rightTuple.right();
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new OperationNode(left, targetInfix, right)));
	})));
}
static selectFirst(divisions: List<&[I8]>, delimiter: &[I8]): Option<Tuple2<&[I8], &[I8]>> {
	let first: &[I8] = divisions.findFirst().orElse("");
	let afterFirst: &[I8] = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(first, afterFirst));
}
static foldOperator(infix: &[I8]): (arg0 : DivideState, arg1 : I8) => DivideState {
	return (state: DivideState, c: I8) => {
		if (c === Strings.charAt(infix, 0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
			let length: number = Strings.length(infix) - 1;
			let counter: number = 0;
			let current: DivideState = state;
			while (counter < length) {
				counter++;
				current = current.pop().map((tuple: Tuple2<DivideState, I8>) => tuple.left()).orElse(current);
			}
			return current.advance();
		}
		return state.append(c);
	};
}
static parseNumber(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
	let stripped: &[I8] = Strings.strip(input);
	if (Main.isNumber(stripped)) {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new SymbolNode(stripped)));
	}
	else {
		return new None<Tuple2<CompileState, Value>>();
	}
}
static isNumber(input: &[I8]): Bool {
	let query: HeadedQuery<number> = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
	return query.map((index: number) => input.charAt(index)).allMatch((c: I8) => Characters.isDigit(c));
}
static parseSymbol(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
	let stripped: &[I8] = Strings.strip(input);
	if (Main.isSymbol(stripped)) {
		let withImport: CompileState = state.addResolvedImportFromCache(stripped);
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new SymbolNode(stripped)));
	}
	else {
		return new None<Tuple2<CompileState, Value>>();
	}
}
static isSymbol(input: &[I8]): Bool {
	let query: HeadedQuery<number> = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
	return query.allMatch((index: number) => Main.isSymbolChar(index, Strings.charAt(input, index)));
}
static isSymbolChar(index: number, c: I8): Bool {
	return "_" === c || Characters.isLetter(c) || (0 !== index && Characters.isDigit(c));
}
static compilePrefix<T>(input: &[I8], infix: &[I8], mapper: (arg0 : &[I8]) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
	if (!input.startsWith(infix)) {
		return new None<Tuple2<CompileState, T>>();
	}
	let slice: &[I8] = Strings.sliceFrom(input, Strings.length(infix));
	return mapper(slice);
}
static compileWhitespace(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate()));
}
static parseWhitespace(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Whitespace>> {
	if (Strings.isBlank(input)) {
		return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
	}
	return new None<Tuple2<CompileState, Whitespace>>();
}
static compileFieldDefinition(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: &[I8]) => Main.getTupleOption(state, withoutEnd).or(() => Main.compileEnumValues(state, withoutEnd)));
}
static getTupleOption(state: CompileState, withoutEnd: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.parseParameter(state, withoutEnd).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";")));
}
static compileEnumValues(state: CompileState, withoutEnd: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.parseValues(state, withoutEnd, (state1: CompileState, s: &[I8]) => Main.parseInvokable(state1, s).flatMap((tuple: Tuple2<CompileState, Value>) => {
		let structureName: &[I8] = state.findLastStructureName().orElse("");
		return tuple.right().generateAsEnumValue(structureName).map((stringOption: &[I8]) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), stringOption));
	})).map((tuple: Tuple2<CompileState, List<&[I8]>>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse("")));
}
static parseParameterOrPlaceholder(state: CompileState, input: &[I8]): Tuple2<CompileState, Parameter> {
	return Main.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
}
static parseParameter(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Parameter>> {
	return Main.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => Main.getCompileStateParameterTuple2(tuple)).or(() => Main.parseDefinition(state, input).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
}
static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
	return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
}
static parseDefinition(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Definition>> {
	return Main.compileLast(Strings.strip(input), " ", (beforeName: &[I8], name: &[I8]) => Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main.foldTypeSeparators), (beforeType: &[I8], type: &[I8]) => Main.compileLast(Strings.strip(beforeType), "\n", (annotationsString: &[I8], afterAnnotations: &[I8]) => {
		let annotations: List<&[I8]> = Main.parseAnnotations(annotationsString);
		return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
	}).or(() => Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name))).or(() => Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)));
}
static parseAnnotations(s: &[I8]): List<&[I8]> {
	return Main.divide(s, (state1: DivideState, c: I8) => Main.foldDelimited(state1, c, "\n")).query().map((s2: &[I8]) => Strings.strip(s2)).filter((value: &[I8]) => !Strings.isEmpty(value)).filter((value: &[I8]) => 1 <= Strings.length(value)).map((value: &[I8]) => Strings.sliceFrom(value, 1)).map((s1: &[I8]) => Strings.strip(s1)).filter((value: &[I8]) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
}
static parseDefinitionWithAnnotations(state: CompileState, annotations: List<&[I8]>, beforeType: &[I8], type: &[I8], name: &[I8]): Option<Tuple2<CompileState, Definition>> {
	return Main.compileSuffix(Strings.strip(beforeType), ">", (withoutTypeParamEnd: &[I8]) => Main.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams: &[I8], typeParamsString: &[I8]) => {
		let typeParams: List<&[I8]> = Main.divideValues(typeParamsString);
		return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
	})).or(() => {
		let divided: List<&[I8]> = Main.parseModifiers(beforeType);
		return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
	});
}
static parseModifiers(beforeType: &[I8]): List<&[I8]> {
	return Main.divide(Strings.strip(beforeType), (state1: DivideState, c: I8) => Main.foldDelimited(state1, c, " ")).query().map((s: &[I8]) => Strings.strip(s)).filter((value: &[I8]) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
}
static foldDelimited(state1: DivideState, c: I8, delimiter: I8): DivideState {
	if (delimiter === c) {
		return state1.advance();
	}
	return state1.append(c);
}
static divideValues(input: &[I8]): List<&[I8]> {
	return Main.divide(input, Main.foldValues).query().map((input1: &[I8]) => Strings.strip(input1)).filter((value: &[I8]) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
}
static foldTypeSeparators(state: DivideState, c: I8): DivideState {
	if (" " === c && state.isLevel()) {
		return state.advance();
	}
	let appended: DivideState = state.append(c);
	if ("<" === c) {
		return appended.enter();
	}
	if (">" === c) {
		return appended.exit();
	}
	return appended;
}
static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<&[I8]>, typeParams: List<&[I8]>, oldModifiers: List<&[I8]>, type: &[I8], name: &[I8]): Option<Tuple2<CompileState, Definition>> {
	return Main.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
		let newModifiers: List<&[I8]> = Main.modifyModifiers(oldModifiers, state.platform());
		let generated: Definition = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
		return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
	});
}
static modifyModifiers(oldModifiers: List<&[I8]>, platform: Platform): List<&[I8]> {
	let list: List<&[I8]> = Main.retainFinal(oldModifiers, platform);
	if (oldModifiers.contains("static", Strings.equalsTo)) {
		return list.addLast("static");
	}
	return list;
}
static retainFinal(oldModifiers: List<&[I8]>, platform: Platform): List<&[I8]> {
	if (oldModifiers.contains("final", Strings.equalsTo) || Platform.Magma !== platform) {
		return Lists.empty();
	}
	return Lists.of("mut");
}
static parseTypeOrPlaceholder(state: CompileState, type: &[I8]): Tuple2<CompileState, Type> {
	return Main.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())).orElseGet(() => new Tuple2Impl<CompileState, Type>(state, new Placeholder(type)));
}
static compileType(state: CompileState, type: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate()));
}
static parseType(state: CompileState, type: &[I8]): Option<Tuple2<CompileState, Type>> {
	return Main.or(state, type, Lists.of(Main.parseArrayType, Main.parseVarArgs, Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
}
static parseArrayType(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
	let stripped: &[I8] = Strings.strip(input);
	return Main.compileSuffix(stripped, "[]", (s: &[I8]) => {
		let child: Tuple2<CompileState, Type> = Main.parseTypeOrPlaceholder(state, s);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new ArrayType(child.right())));
	});
}
static parseVarArgs(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
	let stripped: &[I8] = Strings.strip(input);
	return Main.compileSuffix(stripped, "...", (s: &[I8]) => {
		let child: Tuple2<CompileState, Type> = Main.parseTypeOrPlaceholder(state, s);
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())));
	});
}
static parseSymbolType(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
	let stripped: &[I8] = Strings.strip(input);
	if (Main.isSymbol(stripped)) {
		return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state.addResolvedImportFromCache(stripped), new SymbolNode(stripped)));
	}
	return new None<Tuple2<CompileState, Type>>();
}
static parsePrimitive(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
	return Main.findPrimitiveValue(Strings.strip(input), state.platform()).map((result: Type) => new Tuple2Impl<CompileState, Type>(state, result));
}
static findPrimitiveValue(input: &[I8], platform: Platform): Option<Type> {
	let stripped: &[I8] = Strings.strip(input);
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
static parseGeneric(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
	return Main.compileSuffix(Strings.strip(input), ">", (withoutEnd: &[I8]) => Main.compileFirst(withoutEnd, "<", (baseString: &[I8], argsString: &[I8]) => {
		let argsTuple: Tuple2<CompileState, List<&[I8]>> = Main.parseValuesOrEmpty(state, argsString, (state1: CompileState, s: &[I8]) => Main.compileTypeArgument(state1, s));
		let argsState: CompileState = argsTuple.left();
		let args: List<&[I8]> = argsTuple.right();
		let base: &[I8] = Strings.strip(baseString);
		return Main.assembleFunctionType(argsState, base, args).or(() => {
			let compileState: CompileState = argsState.addResolvedImportFromCache(base);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)));
		});
	}));
}
static assembleFunctionType(state: CompileState, base: &[I8], args: List<&[I8]>): Option<Tuple2<CompileState, Type>> {
	return Main.mapFunctionType(base, args).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state, generated));
}
static mapFunctionType(base: &[I8], args: List<&[I8]>): Option<Type> {
	if (Strings.equalsTo("Function", base)) {
		return args.findFirst().and(() => args.find(1)).map((tuple: Tuple2<&[I8], &[I8]>) => new FunctionType(Lists.of(tuple.left()), tuple.right()));
	}
	if (Strings.equalsTo("BiFunction", base)) {
		return args.find(0).and(() => args.find(1)).and(() => args.find(2)).map((tuple: Tuple2<Tuple2<&[I8], &[I8]>, &[I8]>) => new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right()));
	}
	if (Strings.equalsTo("Supplier", base)) {
		return args.findFirst().map((first: &[I8]) => new FunctionType(Lists.empty(), first));
	}
	if (Strings.equalsTo("Consumer", base)) {
		return args.findFirst().map((first: &[I8]) => new FunctionType(Lists.of(first), "void"));
	}
	if (Strings.equalsTo("Predicate", base)) {
		return args.findFirst().map((first: &[I8]) => new FunctionType(Lists.of(first), "boolean"));
	}
	return new None<Type>();
}
static compileTypeArgument(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
	return Main.or(state, input, Lists.of((state2: CompileState, input1: &[I8]) => Main.compileWhitespace(state2, input1), (state1: CompileState, type: &[I8]) => Main.compileType(state1, type)));
}
static parseValuesOrEmpty<T>(state: CompileState, input: &[I8], mapper: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
	return Main.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
}
static parseValues<T>(state: CompileState, input: &[I8], mapper: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
	return Main.parseAll(state, input, Main.foldValues, mapper);
}
static foldValues(state: DivideState, c: I8): DivideState {
	if ("," === c && state.isLevel()) {
		return state.advance();
	}
	let appended: DivideState = state.append(c);
	if ("-" === c) {
		let peeked: I8 = appended.peek();
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
static compileLast<T>(input: &[I8], infix: &[I8], mapper: (arg0 : &[I8], arg1 : &[I8]) => Option<T>): Option<T> {
	return Main.compileInfix(input, infix, Main.findLast, mapper);
}
static findLast(input: &[I8], infix: &[I8]): number {
	return input.lastIndexOf(infix);
}
static compileSuffix<T>(input: &[I8], suffix: &[I8], mapper: (arg0 : &[I8]) => Option<T>): Option<T> {
	if (!input.endsWith(suffix)) {
		return new None<T>();
	}
	let length: number = Strings.length(input);
	let length1: number = Strings.length(suffix);
	let content: &[I8] = Strings.sliceBetween(input, 0, length - length1);
	return mapper(content);
}
static compileFirst<T>(input: &[I8], infix: &[I8], mapper: (arg0 : &[I8], arg1 : &[I8]) => Option<T>): Option<T> {
	return Main.compileInfix(input, infix, Main.findFirst, mapper);
}
static compileInfix<T>(input: &[I8], infix: &[I8], locator: (arg0 : &[I8], arg1 : &[I8]) => number, mapper: (arg0 : &[I8], arg1 : &[I8]) => Option<T>): Option<T> {
	return Main.compileSplit(Main.split(input, infix, locator), mapper);
}
static compileSplit<T>(splitter: Option<Tuple2<&[I8], &[I8]>>, mapper: (arg0 : &[I8], arg1 : &[I8]) => Option<T>): Option<T> {
	return splitter.flatMap((tuple: Tuple2<&[I8], &[I8]>) => mapper(tuple.left(), tuple.right()));
}
static split(input: &[I8], infix: &[I8], locator: (arg0 : &[I8], arg1 : &[I8]) => number): Option<Tuple2<&[I8], &[I8]>> {
	let index: number = locator(input, infix);
	if (0 > index) {
		return new None<Tuple2<&[I8], &[I8]>>();
	}
	let left: &[I8] = Strings.sliceBetween(input, 0, index);
	let length: number = Strings.length(infix);
	let right: &[I8] = Strings.sliceFrom(input, index + length);
	return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(left, right));
}
static findFirst(input: &[I8], infix: &[I8]): number {
	return input.indexOf(infix);
}
static generatePlaceholder(input: &[I8]): &[I8] {
	let replaced: &[I8] = input.replace("/*", "start").replace("*/", "end");
	return "/*" + replaced + "*/";
}Main.main();
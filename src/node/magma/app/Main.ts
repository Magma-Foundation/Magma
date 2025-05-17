import { Files } from "../../jvm/api/io/Files";
import { Path } from "../../magma/api/io/Path";
import { List } from "../../magma/api/collect/list/List";
import { IOError } from "../../magma/api/io/IOError";
import { Some } from "../../magma/api/option/Some";
import { Console } from "../../jvm/api/io/Console";
import { Option } from "../../magma/api/option/Option";
import { Source } from "../../magma/app/io/Source";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { ImmutableCompileState } from "../../magma/app/compile/ImmutableCompileState";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Tuple2 } from "../../magma/api/Tuple2";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { None } from "../../magma/api/option/None";
import { Platform } from "../../magma/app/io/Platform";
import { Location } from "../../magma/app/io/Location";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Queries } from "../../magma/api/collect/Queries";
import { Import } from "../../magma/app/compile/Import";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Strings } from "../../jvm/api/text/Strings";
import { DivideState } from "../../magma/app/compile/text/DivideState";
import { Type } from "../../magma/api/Type";
import { Parameter } from "../../magma/app/compile/define/Parameter";
import { Definition } from "../../magma/app/compile/define/Definition";
import { ConstructorHeader } from "../../magma/app/compile/value/ConstructorHeader";
import { FunctionHeader } from "../../magma/app/compile/define/FunctionHeader";
import { FunctionSegment } from "../../magma/app/compile/FunctionSegment";
import { Value } from "../../magma/app/compile/value/Value";
import { ConstructionCaller } from "../../magma/app/compile/value/ConstructionCaller";
import { Caller } from "../../magma/app/compile/value/Caller";
import { Argument } from "../../magma/app/compile/value/Argument";
import { InvokableNode } from "../../magma/app/compile/value/InvokableNode";
import { StringNode } from "../../magma/app/compile/value/StringNode";
import { NotNode } from "../../magma/app/compile/value/NotNode";
import { PrimitiveType } from "../../magma/app/compile/type/PrimitiveType";
import { SymbolNode } from "../../magma/app/compile/value/SymbolNode";
import { LambdaNode } from "../../magma/app/compile/value/LambdaNode";
import { AccessNode } from "../../magma/app/compile/value/AccessNode";
import { OperationNode } from "../../magma/app/compile/value/OperationNode";
import { HeadedQuery } from "../../magma/api/collect/head/HeadedQuery";
import { RangeHead } from "../../magma/api/collect/head/RangeHead";
import { Characters } from "../../jvm/api/text/Characters";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { Placeholder } from "../../magma/app/compile/text/Placeholder";
import { ArrayType } from "../../magma/app/compile/type/ArrayType";
import { VariadicType } from "../../magma/app/compile/type/VariadicType";
import { SliceType } from "../../magma/app/compile/type/SliceType";
import { BooleanType } from "../../magma/app/compile/type/BooleanType";
import { TemplateType } from "../../magma/app/compile/type/TemplateType";
import { FunctionType } from "../../magma/app/compile/type/FunctionType";
export class Main {
	static main(): void {
		let sourceDirectory: Path = Files.get(".", "src", "java");
		sourceDirectory.walk().match((children: List<Path>) => Main.runWithChildren(children, sourceDirectory), (value: IOError) => new Some<IOError>(value)).map((error: IOError) => error.display()).ifPresent((displayed: string) => Console.printErrLn(displayed));
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
		return source.read().match((input: string) => Main.getCompileStateOptionTuple2(state, source, input), (value: IOError) => new Tuple2Impl<CompileState, Option<IOError>>(state, new Some<IOError>(value)));
	}
	static getCompileStateOptionTuple2(state: CompileState, source: Source, input: string): Tuple2Impl<CompileState, Option<IOError>> {
		let typeScriptTuple: Tuple2<CompileState, Option<IOError>> = Main.compileAndWrite(state, source, input, Platform.TypeScript);
		let magmaTuple: Tuple2<CompileState, Option<IOError>> = Main.compileAndWrite(typeScriptTuple.left(), source, input, Platform.Magma);
		let windowsTuple: Tuple2<CompileState, Option<IOError>> = Main.compileAndWrite(magmaTuple.left(), source, input, Platform.Windows);
		return new Tuple2Impl<CompileState, Option<IOError>>(windowsTuple.left(), typeScriptTuple.right().or(() => magmaTuple.right()).or(() => windowsTuple.right()));
	}
	static compileAndWrite(state: CompileState, source: Source, input: string, platform: Platform): Tuple2<CompileState, Option<IOError>> {
		let state1: CompileState = state.withLocation(source.computeLocation()).withPlatform(platform);
		let output: Tuple2Impl<CompileState, Map<string, string>> = Main.compileRoot(state1, source, input);
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
		let ioErrorOption1: Option<IOError> = Queries.fromArray(platform.extension).foldWithInitial(initial, (ioErrorOption: Option<IOError>, extension: string) => {
			let target: Path = targetParent.resolveChild(location.name() + "." + extension);
			return ioErrorOption.or(() => target.writeString(output.right().get(extension)));
		});
		return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), ioErrorOption1);
	}
	static compileRoot(state: CompileState, source: Source, input: string): Tuple2Impl<CompileState, Map<string, string>> {
		let statementsTuple: Tuple2<CompileState, string> = Main.compileStatements(state, input, Main.compileRootSegment);
		let statementsState: CompileState = statementsTuple.left();
		let imports: string = statementsState.imports().query().map((anImport: Import) => anImport.generate(state.platform())).collect(new Joiner("")).orElse("");
		let compileState: CompileState = statementsState.clearImports().clear();
		let withMain: string = Main.createMain(source);
		let entries: HashMap<string, string> = new HashMap<string, string>();
		let platform: Platform = state.platform();
		if (Platform.Windows === platform) {
			let value: string = /* source.computeNamespace().query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + source.computeName()*/;
			/*entries.put(Platform.Windows.extension[0], Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"))*/;
			/*entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + source.computeName() + ".h\"") + statementsState.join() + statementsTuple.right() + withMain)*/;
		}
		else {
			/*entries.put(platform.extension[0], imports + statementsState.join() + statementsTuple.right() + withMain)*/;
		}
		return new Tuple2Impl<>(compileState, entries);
	}
	static generateDirective(content: string): string {
		return "#" + content + "\n";
	}
	static createMain(source: Source): string {
		if (Strings.equalsTo(source.computeName(), "Main")) {
			return "Main.main();";
		}
		return "";
	}
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>): Tuple2<CompileState, string> {
		return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple2<CompileState, string> {
		let folded: Tuple2<CompileState, List<string>> = Main.parseAll(state, input, folder, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, string>>(mapper(state1, s))).orElse(new Tuple2Impl<CompileState, List<string>>(state, Lists.empty()));
		return new Tuple2Impl<CompileState, string>(folded.left(), Main.generateAll(folded.right(), merger));
	}
	static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements.query().foldWithInitial("", merger);
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, biFunction: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main.divide(input, folder).query().foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: string) => maybeCurrent.flatMap((current: Tuple2<CompileState, List<T>>) => {
			let currentState: CompileState = current.left();
			let currentElement: List<T> = current.right();
			return biFunction(currentState, segment).map((mappedTuple: Tuple2<CompileState, T>) => {
				let mappedState: CompileState = mappedTuple.left();
				let mappedElement: T = mappedTuple.right();
				return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
			});
		}));
	}
	static mergeStatements(cache: string, element: string): string {
		return cache + element;
	}
	static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current: DivideState = DivideState.createInitial(input);
		while (true) {
			let poppedTuple0: Tuple2<boolean, Tuple2<DivideState, string>> = current.pop().toTuple(new Tuple2Impl<DivideState, string>(current, "\0"));
			if (!poppedTuple0.left()) {
				break;
			}
			let poppedTuple: Tuple2<DivideState, string> = poppedTuple0.right();
			let poppedState: DivideState = poppedTuple.left();
			let popped: string = poppedTuple.right();
			current = Main.foldSingleQuotes(poppedState, popped).or(() => Main.foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder(poppedState, popped));
		}
		return current.advance().segments();
	}
	static foldDoubleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\"" !== c) {
			return new None<DivideState>();
		}
		let appended: DivideState = state.append(c);
		while (true) {
			let maybeTuple: Tuple2<boolean, Tuple2<DivideState, string>> = appended.popAndAppendToTuple().toTuple(new Tuple2Impl<DivideState, string>(appended, "\0"));
			if (!maybeTuple.left()) {
				break;
			}
			let tuple: Tuple2<DivideState, string> = maybeTuple.right();
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
	static foldSingleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\'" !== c) {
			return new None<DivideState>();
		}
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap((state1: DivideState) => state1.popAndAppendToOption());
	}
	static foldEscaped(tuple: Tuple2<DivideState, string>): Option<DivideState> {
		let state: DivideState = tuple.left();
		let c: string = tuple.right();
		if ("\\" === c) {
			return state.popAndAppendToOption();
		}
		return new Some<DivideState>(state);
	}
	static foldStatements(state: DivideState, c: string): DivideState {
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
	static compileRootSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class ")));
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state: CompileState, input1: string) => Main.compileFirst(input1, sourceInfix, (beforeInfix: string, afterInfix: string) => Main.compileFirst(afterInfix, "{", (beforeContent: string, withEnd: string) => Main.compileSuffix(Strings.strip(withEnd), "}", (inputContent: string) => Main.compileLast(beforeInfix, "\n", (s: string, s2: string) => {
			let annotations: List<string> = Main.parseAnnotations(s);
			if (annotations.contains("Actual", Strings.equalsTo)) {
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""));
			}
			return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent);
		}).or(() => {
			let modifiers: List<string> = Main.parseModifiers(beforeContent);
			return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
		}))));
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return Main.compileLast(beforeContent, " implements ", (s: string, s2: string) => Main.parseType(state, s2).flatMap((implementingTuple: Tuple2<CompileState, Type>) => Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content))).or(() => Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content));
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(beforeContent, " extends ", (beforeExtends: string, afterExtends: string) => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeExtends, new Some<string>(afterExtends), maybeImplementing, inputContent)).or(() => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, new None<string>(), maybeImplementing, inputContent));
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: Option<string>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(beforeContent, "(", (rawName: string, withParameters: string) => Main.compileFirst(withParameters, ")", (parametersString: string, _: string) => {
			let name: string = Strings.strip(rawName);
			let parametersTuple: Tuple2<CompileState, List<Parameter>> = Main.parseParameters(state, parametersString);
			let parameters: List<Definition> = Main.retainDefinitionsFromParameters(parametersTuple.right());
			return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
		})).or(() => Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType));
	}
	static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters.query().map((parameter: Parameter) => parameter.asDefinition()).flatMap(Queries.fromOption).collect(new ListCollector<Definition>());
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(beforeParams), ">", (withoutTypeParamEnd: string) => Main.compileFirst(withoutTypeParamEnd, "<", (name: string, typeParamsString: string) => {
			let typeParams: List<string> = Main.divideValues(typeParamsString);
			return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
		})).or(() => Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType));
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: List<string>, parameters: List<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		let name: string = Strings.strip(rawName);
		if (!Main.isSymbol(name)) {
			return new None<Tuple2<CompileState, string>>();
		}
		let outputContentTuple: Tuple2<CompileState, string> = Main.compileStatements(state.pushStructureName(name), content, Main.compileClassSegment);
		let outputContentState: CompileState = outputContentTuple.left().popStructureName();
		let outputContent: string = outputContentTuple.right();
		let platform: Platform = outputContentState.platform();
		let constructorString: string = Main.generateConstructorFromRecordParameters(parameters, platform);
		let joinedTypeParams: string = Joiner.joinOrEmpty(typeParams, ", ", "<", ">");
		let implementingString: string = Main.generateImplementing(maybeImplementing);
		let newModifiers: List<string> = Main.modifyModifiers0(oldModifiers);
		let joinedModifiers: string = newModifiers.query().map((value: string) => value + " ").collect(Joiner.empty()).orElse("");
		if (annotations.contains("Namespace", Strings.equalsTo)) {
			let actualInfix: string = "interface ";
			let newName: string = name + "Instance";
			let generated: string = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters, platform) + constructorString + outputContent + "\n}\n";
			let withNewLocation: CompileState = outputContentState.append(generated).mapLocation((location: Location) => new Location(location.namespace(), location.name() + "Instance"));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(withNewLocation, ""));
		}
		else {
			let extendsString: string = maybeSuperType.map((inner: string) => " extends " + inner).orElse("");
			let infix1: string = Main.retainStruct(infix, outputContentState);
			let generated: string = joinedModifiers + infix1 + name + joinedTypeParams + extendsString + implementingString + " {" + Main.joinParameters(parameters, platform) + constructorString + outputContent + "\n}\n";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.append(generated), ""));
		}
	}
	static retainStruct(infix: string, outputContentState: CompileState): string {
		if (outputContentState.isPlatform(Platform.Magma)) {
			return "struct ";
		}
		return infix;
	}
	static modifyModifiers0(oldModifiers: List<string>): List<string> {
		if (oldModifiers.contains("public", Strings.equalsTo)) {
			return Lists.of("export");
		}
		return Lists.empty();
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing.map((type: Type) => type.generate()).map((inner: string) => " implements " + inner).orElse("");
	}
	static generateConstructorFromRecordParameters(parameters: List<Definition>, platform: Platform): string {
		return parameters.query().map((definition: Definition) => definition.generate(platform)).collect(new Joiner(", ")).map((generatedParameters: string) => Main.generateConstructorWithParameterString(parameters, generatedParameters)).orElse("");
	}
	static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: string): string {
		let constructorAssignments: string = Main.generateConstructorAssignments(parameters);
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
	}
	static generateConstructorAssignments(parameters: List<Definition>): string {
		return parameters.query().map((definition: Definition) => "\n\t\tthis." + definition.name() + " = " + definition.name() + ";").collect(Joiner.empty()).orElse("");
	}
	static joinParameters(parameters: List<Definition>, platform: Platform): string {
		return parameters.query().map((definition: Definition) => definition.generate(platform)).map((generated: string) => "\n\t" + generated + ";").collect(Joiner.empty()).orElse("");
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		let stripped: string = Strings.strip(input);
		if (stripped.startsWith("package ") || stripped.startsWith("import ")) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""));
		}
		return new None<>();
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return Main.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Main.generatePlaceholder(input)));
	}
	static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules.query().map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => Main.getApply(state, input, rule)).flatMap(Queries.fromOption).next();
	}
	static getApply<T>(state: CompileState, input: string, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule(state, input);
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(input, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams: string = Strings.strip(beforeParams);
			return Main.compileLast(strippedBeforeParams, " ", (_: string, name: string) => {
				if (state.hasLastStructureNameOf(name)) {
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return new None<Tuple2<CompileState, string>>();
			}).or(() => {
				if (state.hasLastStructureNameOf(strippedBeforeParams)) {
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return new None<Tuple2<CompileState, string>>();
			}).or(() => Main.parseDefinition(state, beforeParams).flatMap((tuple: Tuple2<CompileState, Definition>) => Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)));
		});
	}
	static compileMethodWithBeforeParams<S extends FunctionHeader<S>>(state: CompileState, header: FunctionHeader<S>, withParams: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(withParams, ")", (params: string, afterParams: string) => {
			let parametersTuple: Tuple2<CompileState, List<Parameter>> = Main.parseParameters(state, params);
			let parametersState: CompileState = parametersTuple.left();
			let parameters: List<Parameter> = parametersTuple.right();
			let definitions: List<Definition> = Main.retainDefinitionsFromParameters(parameters);
			let newHeader: FunctionHeader<S> = Main.retainDef(header, parametersState);
			if (newHeader.hasAnnotation("Actual")) {
				let aStatic: S = newHeader.removeModifier("static");
				let sFunctionSegment: FunctionSegment<S> = new FunctionSegment<S>(newHeader, definitions, new None<>());
				let generate: string = sFunctionSegment.generate(parametersState.platform(), "\n\t");
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, generate));
			}
			return Main.compilePrefix(Strings.strip(afterParams), "{", (withoutContentStart: string) => Main.compileSuffix(Strings.strip(withoutContentStart), "}", (withoutContentEnd: string) => {
				let compileState1: CompileState = parametersState.enterDepth();
				let compileState: CompileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
				let statementsTuple: Tuple2<CompileState, string> = Main.compileFunctionStatements(compileState.defineAll(definitions), withoutContentEnd);
				let compileState2: CompileState = statementsTuple.left().exitDepth();
				let indent: string = compileState2.createIndent();
				let exited: CompileState = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
				let sFunctionSegment: FunctionSegment<S> = new FunctionSegment<S>(newHeader, definitions, new Some<>(statementsTuple.right()));
				let generated: string = sFunctionSegment.generate(parametersState.platform(), indent);
				if (exited.isPlatform(Platform.Windows)) {
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(exited.addFunction(generated), ""));
				}
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(exited, generated));
			})).or(() => {
				if (Strings.equalsTo(";", Strings.strip(afterParams))) {
					let sFunctionSegment: FunctionSegment<S> = new FunctionSegment<S>(newHeader, definitions, new None<>());
					let generate: string = sFunctionSegment.generate(parametersState.platform(), "\n\t");
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, generate));
				}
				return new None<Tuple2<CompileState, string>>();
			});
		});
	}
	static retainDef<S extends FunctionHeader<S>>(header: FunctionHeader<S>, parametersState: CompileState): FunctionHeader<S> {
		if (parametersState.isPlatform(Platform.Magma)) {
			return header.addModifierLast("def").removeModifier("mut");
		}
		return header;
	}
	static parseParameters(state: CompileState, params: string): Tuple2<CompileState, List<Parameter>> {
		return Main.parseValuesOrEmpty(state, params, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s)));
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileStatements(state, input, Main.compileFunctionSegment);
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo(";", Strings.strip(input))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ";"));
		}
		else {
			return new None<Tuple2<CompileState, string>>();
		}
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple2<CompileState, string>> {
		return Main.compileReturn(input1, (withoutPrefix: string) => Main.compileValue(state1, withoutPrefix)).map((tuple: Tuple2<CompileState, string>) => new Tuple2Impl<CompileState, string>(tuple.left(), state1.createIndent() + tuple.right()));
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), "}", (withoutEnd: string) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd: string, content: string) => Main.compileSuffix(beforeContentWithEnd, "{", (beforeContent: string) => Main.compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple2<CompileState, string>) => {
			let contentTuple: Tuple2<CompileState, string> = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content);
			let indent: string = state.createIndent();
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
		}))));
	}
	static foldBlockStarts(state: DivideState, c: string): DivideState {
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
	static compileBlockHeader(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.or(state, input, Lists.of(Main.createConditionalRule("if"), Main.createConditionalRule("while"), Main.compileElse));
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input1: string) => Main.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: string) => {
			let strippedCondition: string = Strings.strip(withoutPrefix);
			return Main.compilePrefix(strippedCondition, "(", (withoutConditionStart: string) => Main.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: string) => {
				let tuple: Tuple2<CompileState, string> = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), prefix + " (" + tuple.right() + ") "));
			}));
		});
	}
	static compileElse(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo("else", Strings.strip(input))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, "else "));
		}
		else {
			return new None<Tuple2<CompileState, string>>();
		}
	}
	static compileFunctionStatement(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			let valueTuple: Tuple2<CompileState, string> = Main.compileFunctionStatementValue(state, withoutEnd);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"));
		});
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (state1: CompileState, input: string) => Main.parseInvokable(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate(tuple.left().platform()))), Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo("break", Strings.strip(input))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, "break"));
		}
		else {
			return new None<Tuple2<CompileState, string>>();
		}
	}
	static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input: string) => Main.compileSuffix(Strings.strip(input), suffix, (child: string) => {
			let tuple: Tuple2<CompileState, string> = Main.compileValueOrPlaceholder(state1, child);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right() + suffix));
		});
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileReturn(input, (value1: string) => Main.compileValue(state, value1));
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return Main.compilePrefix(Strings.strip(input), "return ", (value: string) => mapper(value).flatMap((tuple: Tuple2<CompileState, string>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), "return " + tuple.right()))));
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compileSuffix(Strings.strip(input), ")", (withoutEnd: string) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart: string, args: string) => Main.compileSuffix(callerWithArgStart, "(", (callerString: string) => Main.compilePrefix(Strings.strip(callerString), "new ", (type: string) => Main.compileType(state, type).flatMap((callerTuple: Tuple2<CompileState, string>) => {
			let callerState: CompileState = callerTuple.left();
			let caller: string = callerTuple.right();
			return Main.assembleInvokable(callerState, new ConstructionCaller(caller, callerState.platform()), args);
		})).or(() => Main.parseValue(state, callerString).flatMap((callerTuple: Tuple2<CompileState, Value>) => Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args))))));
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return Main.splitFolded(input, folder, (divisions1: List<string>) => Main.selectLast(divisions1, delimiter));
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple2<string, string>>): Option<Tuple2<string, string>> {
		let divisions: List<string> = Main.divide(input, folder);
		if (2 > divisions.size()) {
			return new None<Tuple2<string, string>>();
		}
		return selector(divisions);
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let beforeLast: List<string> = divisions.subList(0, divisions.size() - 1).orElse(divisions);
		let last: string = divisions.findLast().orElse("");
		let joined: string = beforeLast.query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(joined, last));
	}
	static foldInvocationStarts(state: DivideState, c: string): DivideState {
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
	static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: string): Option<Tuple2<CompileState, Value>> {
		return Main.parseValues(state, argsString, (state1: CompileState, s: string) => Main.parseArgument(state1, s)).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
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
	static parseArgument(state1: CompileState, input: string): Option<Tuple2<CompileState, Argument>> {
		return Main.parseValue(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right()));
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(input, "=", (destination: string, source: string) => {
			let sourceTuple: Tuple2<CompileState, string> = Main.compileValueOrPlaceholder(state, source);
			let destinationTuple: Tuple2<CompileState, string> = Main.compileValue(sourceTuple.left(), destination).or(() => Main.parseDefinition(sourceTuple.left(), destination).map((definitionTuple: Tuple2<CompileState, Definition>) => {
				let definitionState: CompileState = definitionTuple.left();
				let definition: Definition = definitionTuple.right();
				let let: Definition = Main.attachLet(definitionState, definition);
				let generate: string = let.generate(definitionState.platform());
				return new Tuple2Impl<CompileState, string>(definitionState, generate);
			})).orElseGet(() => new Tuple2Impl<CompileState, string>(sourceTuple.left(), Main.generatePlaceholder(destination)));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
		});
	}
	static attachLet(definitionState: CompileState, definition: Definition): Definition {
		/*final Definition let*/;
		if (definitionState.isPlatform(Platform.Windows)) {
			let = definition;
		}
		else {
			let = definition.addModifierLast("let");
		}
		return let;
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileValue(state, input).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Main.generatePlaceholder(input)));
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.parseValue(state, input).map((tuple: Tuple2<CompileState, Value>) => {
			let generated: string = tuple.right().generate(tuple.left().platform());
			return new Tuple2Impl<CompileState, string>(tuple.left(), generated);
		});
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped: string = Strings.strip(input1);
			return Main.compilePrefix(stripped, slice, (s: string) => Main.compileSuffix(s, slice, (s1: string) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringNode(s1)))));
		};
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compilePrefix(Strings.strip(input), "!", (withoutPrefix: string) => {
			let childTuple: Tuple2<CompileState, string> = Main.compileValueOrPlaceholder(state, withoutPrefix);
			let childState: CompileState = childTuple.left();
			let child: string = "!" + childTuple.right();
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new NotNode(child)));
		});
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compileFirst(input, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow: string = Strings.strip(beforeArrow);
			return Main.compilePrefix(strippedBeforeArrow, "(", (withoutStart: string) => Main.compileSuffix(withoutStart, ")", (withoutEnd: string) => Main.parseValues(state, withoutEnd, (state1: CompileState, s: string) => Main.parseParameter(state1, s)).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow))));
		});
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow: string = Strings.strip(afterArrow);
		return Main.compilePrefix(strippedAfterArrow, "{", (withoutContentStart: string) => Main.compileSuffix(withoutContentStart, "}", (withoutContentEnd: string) => {
			let statementsTuple: Tuple2<CompileState, string> = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
			let statementsState: CompileState = statementsTuple.left();
			let statements: string = statementsTuple.right();
			let exited: CompileState = statementsState.exitDepth();
			return Main.assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}");
		})).or(() => Main.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple2<CompileState, string>) => Main.assembleLambda(tuple.left(), paramNames, tuple.right())));
	}
	static assembleLambda(state: CompileState, parameters: List<Definition>, content: string): Option<Tuple2<CompileState, Value>> {
		if (state.isPlatform(Platform.Windows)) {
			let value: FunctionSegment<Definition> = new FunctionSegment<Definition>(new Definition(PrimitiveType.Auto, "temp"), parameters, new Some<>(content));
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state.addFunction(value.generate(state.platform(), "\n")), new SymbolNode("temp")));
		}
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new LambdaNode(parameters, content)));
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return Main.createOperatorRuleWithDifferentInfix(infix, infix);
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state: CompileState, input: string) => Main.compileLast(input, infix, (childString: string, rawProperty: string) => {
			let property: string = Strings.strip(rawProperty);
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
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (divisions: List<string>) => Main.selectFirst(divisions, sourceInfix)), (leftString: string, rightString: string) => Main.parseValue(state1, leftString).flatMap((leftTuple: Tuple2<CompileState, Value>) => Main.parseValue(leftTuple.left(), rightString).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
			let left: Value = leftTuple.right();
			let right: Value = rightTuple.right();
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new OperationNode(left, targetInfix, right)));
		})));
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first: string = divisions.findFirst().orElse("");
		let afterFirst: string = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(first, afterFirst));
	}
	static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state: DivideState, c: string) => {
			if (c === Strings.charAt(infix, 0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
				let length: number = Strings.length(infix) - 1;
				let counter: number = 0;
				let current: DivideState = state;
				while (counter < length) {
					counter++;
					current = current.pop().map((tuple: Tuple2<DivideState, string>) => tuple.left()).orElse(current);
				}
				return current.advance();
			}
			return state.append(c);
		};
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped: string = Strings.strip(input);
		if (Main.isNumber(stripped)) {
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new SymbolNode(stripped)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>();
		}
	}
	static isNumber(input: string): boolean {
		let query: HeadedQuery<number> = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
		return query.map((index: number) => input.charAt(index)).allMatch((c: string) => Characters.isDigit(c));
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped: string = Strings.strip(input);
		if (Main.isSymbol(stripped)) {
			let withImport: CompileState = state.addResolvedImportFromCache(stripped);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new SymbolNode(stripped)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>();
		}
	}
	static isSymbol(input: string): boolean {
		let query: HeadedQuery<number> = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
		return query.allMatch((index: number) => Main.isSymbolChar(index, Strings.charAt(input, index)));
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c || Characters.isLetter(c) || (0 !== index && Characters.isDigit(c));
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		if (!input.startsWith(infix)) {
			return new None<Tuple2<CompileState, T>>();
		}
		let slice: string = Strings.sliceFrom(input, Strings.length(infix));
		return mapper(slice);
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => {
			let generate: string = tuple.right().generate(tuple.left().platform());
			return new Tuple2Impl<CompileState, string>(tuple.left(), generate);
		});
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings.isBlank(input)) {
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
		}
		return new None<Tuple2<CompileState, Whitespace>>();
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => Main.getTupleOption(state, withoutEnd).or(() => Main.compileEnumValues(state, withoutEnd)));
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main.parseParameter(state, withoutEnd).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => {
			let generate: string = "\n\t" + definitionTuple.right().generate(definitionTuple.left().platform()) + ";";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(definitionTuple.left(), generate));
		});
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main.parseValues(state, withoutEnd, (state1: CompileState, s: string) => Main.parseInvokable(state1, s).flatMap((tuple: Tuple2<CompileState, Value>) => {
			let structureName: string = state.findLastStructureName().orElse("");
			return tuple.right().generateAsEnumValue(structureName, state.platform()).map((stringOption: string) => new Tuple2Impl<CompileState, string>(tuple.left(), stringOption));
		})).map((tuple: Tuple2<CompileState, List<string>>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse("")));
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return Main.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return Main.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => Main.getCompileStateParameterTuple2(tuple)).or(() => Main.parseDefinition(state, input).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return Main.compileLast(Strings.strip(input), " ", (beforeName: string, name: string) => Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main.foldTypeSeparators), (beforeType: string, type: string) => Main.compileLast(Strings.strip(beforeType), "\n", (annotationsString: string, afterAnnotations: string) => {
			let annotations: List<string> = Main.parseAnnotations(annotationsString);
			return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
		}).or(() => Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name))).or(() => Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)));
	}
	static parseAnnotations(s: string): List<string> {
		return Main.divide(s, (state1: DivideState, c: string) => Main.foldDelimited(state1, c, "\n")).query().map((s2: string) => Strings.strip(s2)).filter((value: string) => !Strings.isEmpty(value)).filter((value: string) => 1 <= Strings.length(value)).map((value: string) => Strings.sliceFrom(value, 1)).map((s1: string) => Strings.strip(s1)).filter((value: string) => !Strings.isEmpty(value)).collect(new ListCollector<string>());
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main.compileSuffix(Strings.strip(beforeType), ">", (withoutTypeParamEnd: string) => Main.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams: string, typeParamsString: string) => {
			let typeParams: List<string> = Main.divideValues(typeParamsString);
			return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
		})).or(() => {
			let divided: List<string> = Main.parseModifiers(beforeType);
			return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
		});
	}
	static parseModifiers(beforeType: string): List<string> {
		return Main.divide(Strings.strip(beforeType), (state1: DivideState, c: string) => Main.foldDelimited(state1, c, " ")).query().map((s: string) => Strings.strip(s)).filter((value: string) => !Strings.isEmpty(value)).collect(new ListCollector<string>());
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter === c) {
			return state1.advance();
		}
		return state1.append(c);
	}
	static divideValues(input: string): List<string> {
		return Main.divide(input, Main.foldValues).query().map((input1: string) => Strings.strip(input1)).filter((value: string) => !Strings.isEmpty(value)).collect(new ListCollector<string>());
	}
	static foldTypeSeparators(state: DivideState, c: string): DivideState {
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
	static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, oldModifiers: List<string>, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers: List<string> = Main.modifyModifiers(oldModifiers, state.platform());
			let generated: Definition = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
		});
	}
	static modifyModifiers(oldModifiers: List<string>, platform: Platform): List<string> {
		let list: List<string> = Main.retainFinal(oldModifiers, platform);
		if (oldModifiers.contains("static", Strings.equalsTo)) {
			return list.addLast("static");
		}
		return list;
	}
	static retainFinal(oldModifiers: List<string>, platform: Platform): List<string> {
		if (oldModifiers.contains("final", Strings.equalsTo) || Platform.Magma !== platform) {
			return Lists.empty();
		}
		return Lists.of("mut");
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple2<CompileState, Type> {
		return Main.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())).orElseGet(() => new Tuple2Impl<CompileState, Type>(state, new Placeholder(type)));
	}
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return Main.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate()));
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return Main.or(state, type, Lists.of(Main.parseArrayType, Main.parseVarArgs, Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
	}
	static parseArrayType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped: string = Strings.strip(input);
		return Main.compileSuffix(stripped, "[]", (s: string) => {
			let child: Tuple2<CompileState, Type> = Main.parseTypeOrPlaceholder(state, s);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new ArrayType(child.right())));
		});
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped: string = Strings.strip(input);
		return Main.compileSuffix(stripped, "...", (s: string) => {
			let child: Tuple2<CompileState, Type> = Main.parseTypeOrPlaceholder(state, s);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())));
		});
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped: string = Strings.strip(input);
		if (Main.isSymbol(stripped)) {
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state.addResolvedImportFromCache(stripped), new SymbolNode(stripped)));
		}
		return new None<Tuple2<CompileState, Type>>();
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main.findPrimitiveValue(Strings.strip(input), state.platform()).map((result: Type) => new Tuple2Impl<CompileState, Type>(state, result));
	}
	static findPrimitiveValue(input: string, platform: Platform): Option<Type> {
		let stripped: string = Strings.strip(input);
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
	static parseGeneric(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main.compileSuffix(Strings.strip(input), ">", (withoutEnd: string) => Main.compileFirst(withoutEnd, "<", (baseString: string, argsString: string) => {
			let argsTuple: Tuple2<CompileState, List<string>> = Main.parseValuesOrEmpty(state, argsString, (state1: CompileState, s: string) => Main.compileTypeArgument(state1, s));
			let argsState: CompileState = argsTuple.left();
			let args: List<string> = argsTuple.right();
			let base: string = Strings.strip(baseString);
			return Main.assembleFunctionType(argsState, base, args).or(() => {
				let compileState: CompileState = argsState.addResolvedImportFromCache(base);
				return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)));
			});
		}));
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return Main.mapFunctionType(base, args).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state, generated));
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if (Strings.equalsTo("Function", base)) {
			return args.findFirst().and(() => args.find(1)).map((tuple: Tuple2<string, string>) => new FunctionType(Lists.of(tuple.left()), tuple.right()));
		}
		if (Strings.equalsTo("BiFunction", base)) {
			return args.find(0).and(() => args.find(1)).and(() => args.find(2)).map((tuple: Tuple2<Tuple2<string, string>, string>) => new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right()));
		}
		if (Strings.equalsTo("Supplier", base)) {
			return args.findFirst().map((first: string) => new FunctionType(Lists.empty(), first));
		}
		if (Strings.equalsTo("Consumer", base)) {
			return args.findFirst().map((first: string) => new FunctionType(Lists.of(first), "void"));
		}
		if (Strings.equalsTo("Predicate", base)) {
			return args.findFirst().map((first: string) => new FunctionType(Lists.of(first), "boolean"));
		}
		return new None<Type>();
	}
	static compileTypeArgument(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.or(state, input, Lists.of((state2: CompileState, input1: string) => Main.compileWhitespace(state2, input1), (state1: CompileState, type: string) => Main.compileType(state1, type)));
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return Main.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main.parseAll(state, input, Main.foldValues, mapper);
	}
	static foldValues(state: DivideState, c: string): DivideState {
		if ("," === c && state.isLevel()) {
			return state.advance();
		}
		let appended: DivideState = state.append(c);
		if ("-" === c) {
			let peeked: string = appended.peek();
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
	static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findLast, mapper);
	}
	static findLast(input: string, infix: string): number {
		return input.lastIndexOf(infix);
	}
	static compileSuffix<T>(input: string, suffix: string, mapper: (arg0 : string) => Option<T>): Option<T> {
		if (!input.endsWith(suffix)) {
			return new None<T>();
		}
		let length: number = Strings.length(input);
		let length1: number = Strings.length(suffix);
		let content: string = Strings.sliceBetween(input, 0, length - length1);
		return mapper(content);
	}
	static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findFirst, mapper);
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileSplit(Main.split(input, infix, locator), mapper);
	}
	static compileSplit<T>(splitter: Option<Tuple2<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple: Tuple2<string, string>) => mapper(tuple.left(), tuple.right()));
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple2<string, string>> {
		let index: number = locator(input, infix);
		if (0 > index) {
			return new None<Tuple2<string, string>>();
		}
		let left: string = Strings.sliceBetween(input, 0, index);
		let length: number = Strings.length(infix);
		let right: string = Strings.sliceFrom(input, index + length);
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(left, right));
	}
	static findFirst(input: string, infix: string): number {
		return input.indexOf(infix);
	}
	static generatePlaceholder(input: string): string {
		let replaced: string = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
}
Main.main();
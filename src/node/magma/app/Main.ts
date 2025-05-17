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
import { LambdaNode } from "../../magma/app/compile/value/LambdaNode";
import { PrimitiveType } from "../../magma/app/compile/type/PrimitiveType";
import { SymbolNode } from "../../magma/app/compile/value/SymbolNode";
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
		let sourceDirectory: Path = Files/*auto*/.get(".", "src", "java");
		sourceDirectory/*auto*/.walk(/*auto*/).match((children: List<Path>) => Main/*auto*/.runWithChildren(children/*auto*/, sourceDirectory/*auto*/), (value: IOError) => new Some<IOError>(value/*auto*/)).map((error: IOError) => error/*auto*/.display(/*auto*/)).ifPresent((displayed: string) => Console/*auto*/.printErrLn(displayed/*auto*/));
	}
	static runWithChildren(children: List<Path>, sourceDirectory: Path): Option<IOError> {
		let sources: List<Source> = children/*auto*/.query(/*auto*/).filter((source: Path) => source/*auto*/.endsWith(".java")).map((child: Path) => new Source(sourceDirectory/*auto*/, child/*auto*/)).collect(new ListCollector<Source>(/*auto*/));
		let initial: CompileState = sources/*auto*/.query(/*auto*/).foldWithInitial(ImmutableCompileState/*auto*/.createInitial(/*auto*/), (state: CompileState, source: Source) => state/*auto*/.addSource(source/*auto*/));
		return sources/*auto*/.query(/*auto*/).foldWithInitial(Main/*auto*/.createInitialState(initial/*auto*/), (current: Tuple2<CompileState, Option<IOError>>, source1: Source) => Main/*auto*/.foldChild(current/*auto*/.left(/*auto*/), current/*auto*/.right(/*auto*/), source1/*auto*/)).right(/*auto*/);
	}
	static createInitialState(state: CompileState): Tuple2<CompileState, Option<IOError>> {
		return new Tuple2Impl<CompileState, Option<IOError>>(state/*auto*/, new None<IOError>(/*auto*/));
	}
	static foldChild(state: CompileState, maybeError: Option<IOError>, source: Source): Tuple2<CompileState, Option<IOError>> {
		if (maybeError/*auto*/.isPresent(/*auto*/)) {
			return new Tuple2Impl<CompileState, Option<IOError>>(state/*auto*/, maybeError/*auto*/);
		}
		return Main/*auto*/.runWithSource(state/*auto*/, source/*auto*/);
	}
	static runWithSource(state: CompileState, source: Source): Tuple2<CompileState, Option<IOError>> {
		return source/*auto*/.read(/*auto*/).match((input: string) => Main/*auto*/.getCompileStateOptionTuple2(state/*auto*/, source/*auto*/, input/*auto*/), (value: IOError) => new Tuple2Impl<CompileState, Option<IOError>>(state/*auto*/, new Some<IOError>(value/*auto*/)));
	}
	static getCompileStateOptionTuple2(state: CompileState, source: Source, input: string): Tuple2Impl<CompileState, Option<IOError>> {
		let typeScriptTuple: Tuple2<CompileState, Option<IOError>> = Main/*auto*/.compileAndWrite(state/*auto*/, source/*auto*/, input/*auto*/, Platform/*auto*/.TypeScript);
		let magmaTuple: Tuple2<CompileState, Option<IOError>> = Main/*auto*/.compileAndWrite(typeScriptTuple/*auto*/.left(/*auto*/), source/*auto*/, input/*auto*/, Platform/*auto*/.Magma);
		let windowsTuple: Tuple2<CompileState, Option<IOError>> = Main/*auto*/.compileAndWrite(magmaTuple/*auto*/.left(/*auto*/), source/*auto*/, input/*auto*/, Platform/*auto*/.Windows);
		return new Tuple2Impl<CompileState, Option<IOError>>(windowsTuple/*auto*/.left(/*auto*/), typeScriptTuple/*auto*/.right(/*auto*/).or(() => magmaTuple/*auto*/.right(/*auto*/)).or(() => windowsTuple/*auto*/.right(/*auto*/)));
	}
	static compileAndWrite(state: CompileState, source: Source, input: string, platform: Platform): Tuple2<CompileState, Option<IOError>> {
		let state1: CompileState = state/*auto*/.withLocation(source/*auto*/.computeLocation(/*auto*/)).withPlatform(platform/*auto*/);
		let output: Tuple2Impl<CompileState, Map<string, string>> = Main/*auto*/.compileRoot(state1/*auto*/, source/*auto*/, input/*auto*/);
		let location: Location = output/*auto*/.left(/*auto*/).findCurrentLocation(/*auto*/).orElse(new Location(Lists/*auto*/.empty(/*auto*/), ""));
		let targetDirectory: Path = Files/*auto*/.get(".", "src", platform/*auto*/.root);
		let targetParent: Path = targetDirectory/*auto*/.resolveChildSegments(location/*auto*/.namespace(/*auto*/));
		if (!targetParent/*auto*/.exists(/*auto*/)) {
			let maybeError: Option<IOError> = targetParent/*auto*/.createDirectories(/*auto*/);
			if (maybeError/*auto*/.isPresent(/*auto*/)) {
				return new Tuple2Impl<CompileState, Option<IOError>>(output/*auto*/.left(/*auto*/), maybeError/*auto*/);
			}
		}
		let initial: Option<IOError> = new None<IOError>(/*auto*/);
		let ioErrorOption1: Option<IOError> = Queries/*auto*/.fromArray(platform/*auto*/.extension).foldWithInitial(initial/*auto*/, (ioErrorOption: Option<IOError>, extension: string) => {
			let target: Path = targetParent/*auto*/.resolveChild(location/*auto*/.name(/*auto*/) + "." + extension/*auto*/);
			return ioErrorOption/*auto*/.or(() => target/*auto*/.writeString(output/*auto*/.right(/*auto*/).get(extension/*auto*/)));
		});
		return new Tuple2Impl<CompileState, Option<IOError>>(output/*auto*/.left(/*auto*/), ioErrorOption1/*auto*/);
	}
	static compileRoot(state: CompileState, source: Source, input: string): Tuple2Impl<CompileState, Map<string, string>> {
		let statementsTuple: Tuple2<CompileState, string> = Main/*auto*/.compileStatements(state/*auto*/, input/*auto*/, Main/*auto*/.compileRootSegment);
		let statementsState: CompileState = statementsTuple/*auto*/.left(/*auto*/);
		let imports: string = statementsState/*auto*/.imports(/*auto*/).query(/*auto*/).map((anImport: Import) => anImport/*auto*/.generate(state/*auto*/.platform(/*auto*/))).collect(new Joiner("")).orElse("");
		let compileState: CompileState = statementsState/*auto*/.clearImports(/*auto*/).clear(/*auto*/);
		let withMain: string = Main/*auto*/.createMain(source/*auto*/);
		let entries: HashMap<string, string> = new HashMap<string, string>(/*auto*/);
		let platform: Platform = state/*auto*/.platform(/*auto*/);
		if (Platform/*auto*/.Windows === platform/*auto*/) {
			let value: string = /* source.computeNamespace().query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + source.computeName()*/;
			/*entries.put(Platform.Windows.extension[0], Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"))*/;
			/*entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + source.computeName() + ".h\"") + statementsState.join() + statementsTuple.right() + withMain)*/;
		}
		else {
			/*entries.put(platform.extension[0], imports + statementsState.join() + statementsTuple.right() + withMain)*/;
		}
		return new Tuple2Impl<>(compileState/*auto*/, entries/*auto*/);
	}
	static generateDirective(content: string): string {
		return "#" + content/*auto*/ + "\n";
	}
	static createMain(source: Source): string {
		if (Strings/*auto*/.equalsTo(source/*auto*/.computeName(/*auto*/), "Main")) {
			return "Main.main();";
		}
		return "";
	}
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>): Tuple2<CompileState, string> {
		return Main/*auto*/.compileAll(state/*auto*/, input/*auto*/, Main/*auto*/.foldStatements, mapper/*auto*/, Main/*auto*/.mergeStatements);
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple2<CompileState, string> {
		let folded: Tuple2<CompileState, List<string>> = Main/*auto*/.parseAll(state/*auto*/, input/*auto*/, folder/*auto*/, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, string>>(mapper/*auto*/(state1/*auto*/, s/*auto*/))).orElse(new Tuple2Impl<CompileState, List<string>>(state/*auto*/, Lists/*auto*/.empty(/*auto*/)));
		return new Tuple2Impl<CompileState, string>(folded/*auto*/.left(/*auto*/), Main/*auto*/.generateAll(folded/*auto*/.right(/*auto*/), merger/*auto*/));
	}
	static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements/*auto*/.query(/*auto*/).foldWithInitial("", merger/*auto*/);
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, biFunction: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main/*auto*/.divide(input/*auto*/, folder/*auto*/).query(/*auto*/).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state/*auto*/, Lists/*auto*/.empty(/*auto*/))), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: string) => maybeCurrent/*auto*/.flatMap((current: Tuple2<CompileState, List<T>>) => {
			let currentState: CompileState = current/*auto*/.left(/*auto*/);
			let currentElement: List<T> = current/*auto*/.right(/*auto*/);
			return biFunction/*auto*/(currentState/*auto*/, segment/*auto*/).map((mappedTuple: Tuple2<CompileState, T>) => {
				let mappedState: CompileState = mappedTuple/*auto*/.left(/*auto*/);
				let mappedElement: T = mappedTuple/*auto*/.right(/*auto*/);
				return new Tuple2Impl<CompileState, List<T>>(mappedState/*auto*/, currentElement/*auto*/.addLast(mappedElement/*auto*/));
			});
		}));
	}
	static mergeStatements(cache: string, element: string): string {
		return cache/*auto*/ + element/*auto*/;
	}
	static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current: DivideState = DivideState/*auto*/.createInitial(input/*auto*/);
		while (true/*auto*/) {
			let poppedTuple0: Tuple2<boolean, Tuple2<DivideState, string>> = current/*auto*/.pop(/*auto*/).toTuple(new Tuple2Impl<DivideState, string>(current/*auto*/, "\0"));
			if (!poppedTuple0/*auto*/.left(/*auto*/)) {
				break;
			}
			let poppedTuple: Tuple2<DivideState, string> = poppedTuple0/*auto*/.right(/*auto*/);
			let poppedState: DivideState = poppedTuple/*auto*/.left(/*auto*/);
			let popped: string = poppedTuple/*auto*/.right(/*auto*/);
			current/*auto*/ = Main/*auto*/.foldSingleQuotes(poppedState/*auto*/, popped/*auto*/).or(() => Main/*auto*/.foldDoubleQuotes(poppedState/*auto*/, popped/*auto*/)).orElseGet(() => folder/*auto*/(poppedState/*auto*/, popped/*auto*/));
		}
		return current/*auto*/.advance(/*auto*/).segments(/*auto*/);
	}
	static foldDoubleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\"" !== c/*auto*/) {
			return new None<DivideState>(/*auto*/);
		}
		let appended: DivideState = state/*auto*/.append(c/*auto*/);
		while (true/*auto*/) {
			let maybeTuple: Tuple2<boolean, Tuple2<DivideState, string>> = appended/*auto*/.popAndAppendToTuple(/*auto*/).toTuple(new Tuple2Impl<DivideState, string>(appended/*auto*/, "\0"));
			if (!maybeTuple/*auto*/.left(/*auto*/)) {
				break;
			}
			let tuple: Tuple2<DivideState, string> = maybeTuple/*auto*/.right(/*auto*/);
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
	static foldSingleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\'" !== c/*auto*/) {
			return new None<DivideState>(/*auto*/);
		}
		return state/*auto*/.append(c/*auto*/).popAndAppendToTuple(/*auto*/).flatMap(Main/*auto*/.foldEscaped).flatMap((state1: DivideState) => state1/*auto*/.popAndAppendToOption(/*auto*/));
	}
	static foldEscaped(tuple: Tuple2<DivideState, string>): Option<DivideState> {
		let state: DivideState = tuple/*auto*/.left(/*auto*/);
		let c: string = tuple/*auto*/.right(/*auto*/);
		if ("\\" === c/*auto*/) {
			return state/*auto*/.popAndAppendToOption(/*auto*/);
		}
		return new Some<DivideState>(state/*auto*/);
	}
	static foldStatements(state: DivideState, c: string): DivideState {
		let appended: DivideState = state/*auto*/.append(c/*auto*/);
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
	static compileRootSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state/*auto*/, input/*auto*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.compileNamespaced, Main/*auto*/.createStructureRule("class ", "class "), Main/*auto*/.createStructureRule("interface ", "interface "), Main/*auto*/.createStructureRule("record ", "class "), Main/*auto*/.createStructureRule("enum ", "class ")));
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state: CompileState, input1: string) => Main/*auto*/.compileFirst(input1/*auto*/, sourceInfix/*auto*/, (beforeInfix: string, afterInfix: string) => Main/*auto*/.compileFirst(afterInfix/*auto*/, "{", (beforeContent: string, withEnd: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withEnd/*auto*/), "}", (inputContent: string) => Main/*auto*/.compileLast(beforeInfix/*auto*/, "\n", (s: string, s2: string) => {
			let annotations: List<string> = Main/*auto*/.parseAnnotations(s/*auto*/);
			if (annotations/*auto*/.contains("Actual", Strings/*auto*/.equalsTo)) {
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*auto*/, ""));
			}
			return Main/*auto*/.compileStructureWithImplementing(state/*auto*/, annotations/*auto*/, Main/*auto*/.parseModifiers(s2/*auto*/), targetInfix/*auto*/, beforeContent/*auto*/, inputContent/*auto*/);
		}).or(() => {
			let modifiers: List<string> = Main/*auto*/.parseModifiers(beforeContent/*auto*/);
			return Main/*auto*/.compileStructureWithImplementing(state/*auto*/, Lists/*auto*/.empty(/*auto*/), modifiers/*auto*/, targetInfix/*auto*/, beforeContent/*auto*/, inputContent/*auto*/);
		}))));
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileLast(beforeContent/*auto*/, " implements ", (s: string, s2: string) => Main/*auto*/.parseType(state/*auto*/, s2/*auto*/).flatMap((implementingTuple: Tuple2<CompileState, Type>) => Main/*auto*/.compileStructureWithExtends(implementingTuple/*auto*/.left(/*auto*/), annotations/*auto*/, modifiers/*auto*/, targetInfix/*auto*/, s/*auto*/, new Some<Type>(implementingTuple/*auto*/.right(/*auto*/)), content/*auto*/))).or(() => Main/*auto*/.compileStructureWithExtends(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, targetInfix/*auto*/, beforeContent/*auto*/, new None<Type>(/*auto*/), content/*auto*/));
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(beforeContent/*auto*/, " extends ", (beforeExtends: string, afterExtends: string) => Main/*auto*/.compileStructureWithParameters(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, targetInfix/*auto*/, beforeExtends/*auto*/, new Some<string>(afterExtends/*auto*/), maybeImplementing/*auto*/, inputContent/*auto*/)).or(() => Main/*auto*/.compileStructureWithParameters(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, targetInfix/*auto*/, beforeContent/*auto*/, new None<string>(/*auto*/), maybeImplementing/*auto*/, inputContent/*auto*/));
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: Option<string>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(beforeContent/*auto*/, "(", (rawName: string, withParameters: string) => Main/*auto*/.compileFirst(withParameters/*auto*/, ")", (parametersString: string, _: string) => {
			let name: string = Strings/*auto*/.strip(rawName/*auto*/);
			let parametersTuple: Tuple2<CompileState, List<Parameter>> = Main/*auto*/.parseParameters(state/*auto*/, parametersString/*auto*/);
			let parameters: List<Definition> = Main/*auto*/.retainDefinitionsFromParameters(parametersTuple/*auto*/.right(/*auto*/));
			return Main/*auto*/.compileStructureWithTypeParams(parametersTuple/*auto*/.left(/*auto*/), targetInfix/*auto*/, inputContent/*auto*/, name/*auto*/, parameters/*auto*/, maybeImplementing/*auto*/, annotations/*auto*/, modifiers/*auto*/, maybeSuperType/*auto*/);
		})).or(() => Main/*auto*/.compileStructureWithTypeParams(state/*auto*/, targetInfix/*auto*/, inputContent/*auto*/, beforeContent/*auto*/, Lists/*auto*/.empty(/*auto*/), maybeImplementing/*auto*/, annotations/*auto*/, modifiers/*auto*/, maybeSuperType/*auto*/));
	}
	static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters/*auto*/.query(/*auto*/).map((parameter: Parameter) => parameter/*auto*/.asDefinition(/*auto*/)).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<Definition>(/*auto*/));
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeParams/*auto*/), ">", (withoutTypeParamEnd: string) => Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", (name: string, typeParamsString: string) => {
			let typeParams: List<string> = Main/*auto*/.divideValues(typeParamsString/*auto*/);
			return Main/*auto*/.assembleStructure(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, infix/*auto*/, name/*auto*/, typeParams/*auto*/, parameters/*auto*/, maybeImplementing/*auto*/, content/*auto*/, maybeSuperType/*auto*/);
		})).or(() => Main/*auto*/.assembleStructure(state/*auto*/, annotations/*auto*/, modifiers/*auto*/, infix/*auto*/, beforeParams/*auto*/, Lists/*auto*/.empty(/*auto*/), parameters/*auto*/, maybeImplementing/*auto*/, content/*auto*/, maybeSuperType/*auto*/));
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: List<string>, parameters: List<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		let name: string = Strings/*auto*/.strip(rawName/*auto*/);
		if (!Main/*auto*/.isSymbol(name/*auto*/)) {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
		let outputContentTuple: Tuple2<CompileState, string> = Main/*auto*/.compileStatements(state/*auto*/.pushStructureName(name/*auto*/), content/*auto*/, Main/*auto*/.compileClassSegment);
		let outputContentState: CompileState = outputContentTuple/*auto*/.left(/*auto*/).popStructureName(/*auto*/);
		let outputContent: string = outputContentTuple/*auto*/.right(/*auto*/);
		let platform: Platform = outputContentState/*auto*/.platform(/*auto*/);
		let constructorString: string = Main/*auto*/.generateConstructorFromRecordParameters(parameters/*auto*/, platform/*auto*/);
		let joinedTypeParams: string = Joiner/*auto*/.joinOrEmpty(typeParams/*auto*/, ", ", "<", ">");
		let implementingString: string = Main/*auto*/.generateImplementing(maybeImplementing/*auto*/);
		let newModifiers: List<string> = Main/*auto*/.modifyModifiers0(oldModifiers/*auto*/);
		let joinedModifiers: string = newModifiers/*auto*/.query(/*auto*/).map((value: string) => value/*auto*/ + " ").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
		if (annotations/*auto*/.contains("Namespace", Strings/*auto*/.equalsTo)) {
			let actualInfix: string = "interface ";
			let newName: string = name/*auto*/ + "Instance";
			let generated: string = joinedModifiers/*auto*/ + actualInfix/*auto*/ + newName/*auto*/ + joinedTypeParams/*auto*/ + implementingString/*auto*/ + " {" + Main/*auto*/.joinParameters(parameters/*auto*/, platform/*auto*/) + constructorString/*auto*/ + outputContent/*auto*/ + "\n}\n";
			let withNewLocation: CompileState = outputContentState/*auto*/.append(generated/*auto*/).mapLocation((location: Location) => new Location(location/*auto*/.namespace(/*auto*/), location/*auto*/.name(/*auto*/) + "Instance"));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(withNewLocation/*auto*/, ""));
		}
		else {
			let extendsString: string = maybeSuperType/*auto*/.map((inner: string) => " extends " + inner/*auto*/).orElse("");
			let infix1: string = Main/*auto*/.retainStruct(infix/*auto*/, outputContentState/*auto*/);
			let generated: string = joinedModifiers/*auto*/ + infix1/*auto*/ + name/*auto*/ + joinedTypeParams/*auto*/ + extendsString/*auto*/ + implementingString/*auto*/ + " {" + Main/*auto*/.joinParameters(parameters/*auto*/, platform/*auto*/) + constructorString/*auto*/ + outputContent/*auto*/ + "\n}\n";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState/*auto*/.append(generated/*auto*/), ""));
		}
	}
	static retainStruct(infix: string, outputContentState: CompileState): string {
		if (outputContentState/*auto*/.isPlatform(Platform/*auto*/.Magma)) {
			return "struct ";
		}
		return infix/*auto*/;
	}
	static modifyModifiers0(oldModifiers: List<string>): List<string> {
		if (oldModifiers/*auto*/.contains("public", Strings/*auto*/.equalsTo)) {
			return Lists/*auto*/.of("export");
		}
		return Lists/*auto*/.empty(/*auto*/);
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing/*auto*/.map((type: Type) => type/*auto*/.generate(/*auto*/)).map((inner: string) => " implements " + inner/*auto*/).orElse("");
	}
	static generateConstructorFromRecordParameters(parameters: List<Definition>, platform: Platform): string {
		return parameters/*auto*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*auto*/)).collect(new Joiner(", ")).map((generatedParameters: string) => Main/*auto*/.generateConstructorWithParameterString(parameters/*auto*/, generatedParameters/*auto*/)).orElse("");
	}
	static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: string): string {
		let constructorAssignments: string = Main/*auto*/.generateConstructorAssignments(parameters/*auto*/);
		return "\n\tconstructor (" + parametersString/*auto*/ + ") {" + constructorAssignments/*auto*/ + "\n\t}";
	}
	static generateConstructorAssignments(parameters: List<Definition>): string {
		return parameters/*auto*/.query(/*auto*/).map((definition: Definition) => "\n\t\tthis." + definition/*auto*/.name(/*auto*/) + " = " + definition/*auto*/.name(/*auto*/) + ";").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
	}
	static joinParameters(parameters: List<Definition>, platform: Platform): string {
		return parameters/*auto*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*auto*/)).map((generated: string) => "\n\t" + generated/*auto*/ + ";").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		let stripped: string = Strings/*auto*/.strip(input/*auto*/);
		if (stripped/*auto*/.startsWith("package ") || stripped/*auto*/.startsWith("import ")) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*auto*/, ""));
		}
		return new None<>(/*auto*/);
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return Main/*auto*/.or(state/*auto*/, input/*auto*/, rules/*auto*/).orElseGet(() => new Tuple2Impl<CompileState, string>(state/*auto*/, Main/*auto*/.generatePlaceholder(input/*auto*/)));
	}
	static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules/*auto*/.query(/*auto*/).map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => Main/*auto*/.getApply(state/*auto*/, input/*auto*/, rule/*auto*/)).flatMap(Queries/*auto*/.fromOption).next(/*auto*/);
	}
	static getApply<T>(state: CompileState, input: string, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule/*auto*/(state/*auto*/, input/*auto*/);
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state1/*auto*/, input1/*auto*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.createStructureRule("class ", "class "), Main/*auto*/.createStructureRule("interface ", "interface "), Main/*auto*/.createStructureRule("record ", "class "), Main/*auto*/.createStructureRule("enum ", "class "), Main/*auto*/.compileMethod, Main/*auto*/.compileFieldDefinition));
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(input/*auto*/, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams: string = Strings/*auto*/.strip(beforeParams/*auto*/);
			return Main/*auto*/.compileLast(strippedBeforeParams/*auto*/, " ", (_: string, name: string) => {
				if (state/*auto*/.hasLastStructureNameOf(name/*auto*/)) {
					return Main/*auto*/.compileMethodWithBeforeParams(state/*auto*/, new ConstructorHeader(/*auto*/), withParams/*auto*/);
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			}).or(() => {
				if (state/*auto*/.hasLastStructureNameOf(strippedBeforeParams/*auto*/)) {
					return Main/*auto*/.compileMethodWithBeforeParams(state/*auto*/, new ConstructorHeader(/*auto*/), withParams/*auto*/);
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			}).or(() => Main/*auto*/.parseDefinition(state/*auto*/, beforeParams/*auto*/).flatMap((tuple: Tuple2<CompileState, Definition>) => Main/*auto*/.compileMethodWithBeforeParams(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/), withParams/*auto*/)));
		});
	}
	static compileMethodWithBeforeParams<S extends FunctionHeader<S>>(state: CompileState, header: FunctionHeader<S>, withParams: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(withParams/*auto*/, ")", (params: string, afterParams: string) => {
			let parametersTuple: Tuple2<CompileState, List<Parameter>> = Main/*auto*/.parseParameters(state/*auto*/, params/*auto*/);
			let parametersState: CompileState = parametersTuple/*auto*/.left(/*auto*/);
			let parameters: List<Parameter> = parametersTuple/*auto*/.right(/*auto*/);
			let definitions: List<Definition> = Main/*auto*/.retainDefinitionsFromParameters(parameters/*auto*/);
			let newHeader: FunctionHeader<S> = Main/*auto*/.retainDef(header/*auto*/, parametersState/*auto*/);
			if (newHeader/*auto*/.hasAnnotation("Actual")) {
				let aStatic: S = newHeader/*auto*/.removeModifier("static");
				let sFunctionSegment: FunctionSegment<S> = new FunctionSegment<S>(newHeader/*auto*/, definitions/*auto*/, new None<>(/*auto*/));
				let generate: string = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), "\n\t");
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState/*auto*/, generate/*auto*/));
			}
			return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(afterParams/*auto*/), "{", (withoutContentStart: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withoutContentStart/*auto*/), "}", (withoutContentEnd: string) => {
				let compileState1: CompileState = parametersState/*auto*/.enterDepth(/*auto*/);
				let compileState: CompileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
				let statementsTuple: Tuple2<CompileState, string> = Main/*auto*/.compileFunctionStatements(compileState/*auto*/.defineAll(definitions/*auto*/), withoutContentEnd/*auto*/);
				let compileState2: CompileState = statementsTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/);
				let indent: string = compileState2/*auto*/.createIndent(/*auto*/);
				let exited: CompileState = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
				let sFunctionSegment: FunctionSegment<S> = new FunctionSegment<S>(newHeader/*auto*/, definitions/*auto*/, new Some<>(statementsTuple/*auto*/.right(/*auto*/)));
				let generated: string = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), indent/*auto*/);
				if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(exited/*auto*/.addFunction(generated/*auto*/), ""));
				}
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(exited/*auto*/, generated/*auto*/));
			})).or(() => {
				if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(afterParams/*auto*/))) {
					let sFunctionSegment: FunctionSegment<S> = new FunctionSegment<S>(newHeader/*auto*/, definitions/*auto*/, new None<>(/*auto*/));
					let generate: string = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), "\n\t");
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState/*auto*/, generate/*auto*/));
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			});
		});
	}
	static retainDef<S extends FunctionHeader<S>>(header: FunctionHeader<S>, parametersState: CompileState): FunctionHeader<S> {
		if (parametersState/*auto*/.isPlatform(Platform/*auto*/.Magma)) {
			return header/*auto*/.addModifierLast("def").removeModifier("mut");
		}
		return header/*auto*/;
	}
	static parseParameters(state: CompileState, params: string): Tuple2<CompileState, List<Parameter>> {
		return Main/*auto*/.parseValuesOrEmpty(state/*auto*/, params/*auto*/, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, Parameter>>(Main/*auto*/.parseParameterOrPlaceholder(state1/*auto*/, s/*auto*/)));
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileStatements(state/*auto*/, input/*auto*/, Main/*auto*/.compileFunctionSegment);
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state/*auto*/, input/*auto*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.compileEmptySegment, Main/*auto*/.compileBlock, Main/*auto*/.compileFunctionStatement, Main/*auto*/.compileReturnWithoutSuffix));
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(input/*auto*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*auto*/, ";"));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileReturn(input1/*auto*/, (withoutPrefix: string) => Main/*auto*/.compileValue(state1/*auto*/, withoutPrefix/*auto*/)).map((tuple: Tuple2<CompileState, string>) => new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), state1/*auto*/.createIndent(/*auto*/) + tuple/*auto*/.right(/*auto*/)));
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), "}", (withoutEnd: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*auto*/, "", Main/*auto*/.foldBlockStarts), (beforeContentWithEnd: string, content: string) => Main/*auto*/.compileSuffix(beforeContentWithEnd/*auto*/, "{", (beforeContent: string) => Main/*auto*/.compileBlockHeader(state/*auto*/, beforeContent/*auto*/).flatMap((headerTuple: Tuple2<CompileState, string>) => {
			let contentTuple: Tuple2<CompileState, string> = Main/*auto*/.compileFunctionStatements(headerTuple/*auto*/.left(/*auto*/).enterDepth(/*auto*/), content/*auto*/);
			let indent: string = state/*auto*/.createIndent(/*auto*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(contentTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/), indent/*auto*/ + headerTuple/*auto*/.right(/*auto*/) + "{" + contentTuple/*auto*/.right(/*auto*/) + indent/*auto*/ + "}"));
		}))));
	}
	static foldBlockStarts(state: DivideState, c: string): DivideState {
		let appended: DivideState = state/*auto*/.append(c/*auto*/);
		if ("{" === c/*auto*/) {
			let entered: DivideState = appended/*auto*/.enter(/*auto*/);
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
	static compileBlockHeader(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.or(state/*auto*/, input/*auto*/, Lists/*auto*/.of(Main/*auto*/.createConditionalRule("if"), Main/*auto*/.createConditionalRule("while"), Main/*auto*/.compileElse));
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input1: string) => Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input1/*auto*/), prefix/*auto*/, (withoutPrefix: string) => {
			let strippedCondition: string = Strings/*auto*/.strip(withoutPrefix/*auto*/);
			return Main/*auto*/.compilePrefix(strippedCondition/*auto*/, "(", (withoutConditionStart: string) => Main/*auto*/.compileSuffix(withoutConditionStart/*auto*/, ")", (withoutConditionEnd: string) => {
				let tuple: Tuple2<CompileState, string> = Main/*auto*/.compileValueOrPlaceholder(state1/*auto*/, withoutConditionEnd/*auto*/);
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), prefix/*auto*/ + " (" + tuple/*auto*/.right(/*auto*/) + ") "));
			}));
		});
	}
	static compileElse(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo("else", Strings/*auto*/.strip(input/*auto*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*auto*/, "else "));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static compileFunctionStatement(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), ";", (withoutEnd: string) => {
			let valueTuple: Tuple2<CompileState, string> = Main/*auto*/.compileFunctionStatementValue(state/*auto*/, withoutEnd/*auto*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple/*auto*/.left(/*auto*/), state/*auto*/.createIndent(/*auto*/) + valueTuple/*auto*/.right(/*auto*/) + ";"));
		});
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state/*auto*/, withoutEnd/*auto*/, Lists/*auto*/.of(Main/*auto*/.compileReturnWithValue, Main/*auto*/.compileAssignment, (state1: CompileState, input: string) => Main/*auto*/.parseInvokable(state1/*auto*/, input/*auto*/).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/).generate(tuple/*auto*/.left(/*auto*/).platform(/*auto*/)))), Main/*auto*/.createPostRule("++"), Main/*auto*/.createPostRule("--"), Main/*auto*/.compileBreak));
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo("break", Strings/*auto*/.strip(input/*auto*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*auto*/, "break"));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), suffix/*auto*/, (child: string) => {
			let tuple: Tuple2<CompileState, string> = Main/*auto*/.compileValueOrPlaceholder(state1/*auto*/, child/*auto*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/) + suffix/*auto*/));
		});
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileReturn(input/*auto*/, (value1: string) => Main/*auto*/.compileValue(state/*auto*/, value1/*auto*/));
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*auto*/), "return ", (value: string) => mapper/*auto*/(value/*auto*/).flatMap((tuple: Tuple2<CompileState, string>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), "return " + tuple/*auto*/.right(/*auto*/)))));
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), ")", (withoutEnd: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*auto*/, "", Main/*auto*/.foldInvocationStarts), (callerWithArgStart: string, args: string) => Main/*auto*/.compileSuffix(callerWithArgStart/*auto*/, "(", (callerString: string) => Main/*auto*/.compilePrefix(Strings/*auto*/.strip(callerString/*auto*/), "new ", (type: string) => Main/*auto*/.compileType(state/*auto*/, type/*auto*/).flatMap((callerTuple: Tuple2<CompileState, string>) => {
			let callerState: CompileState = callerTuple/*auto*/.left(/*auto*/);
			let caller: string = callerTuple/*auto*/.right(/*auto*/);
			return Main/*auto*/.assembleInvokable(callerState/*auto*/, new ConstructionCaller(caller/*auto*/, callerState/*auto*/.platform(/*auto*/)), args/*auto*/);
		})).or(() => Main/*auto*/.parseValue(state/*auto*/, callerString/*auto*/).flatMap((callerTuple: Tuple2<CompileState, Value>) => Main/*auto*/.assembleInvokable(callerTuple/*auto*/.left(/*auto*/), callerTuple/*auto*/.right(/*auto*/), args/*auto*/))))));
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return Main/*auto*/.splitFolded(input/*auto*/, folder/*auto*/, (divisions1: List<string>) => Main/*auto*/.selectLast(divisions1/*auto*/, delimiter/*auto*/));
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple2<string, string>>): Option<Tuple2<string, string>> {
		let divisions: List<string> = Main/*auto*/.divide(input/*auto*/, folder/*auto*/);
		if (2/*auto*/ > divisions/*auto*/.size(/*auto*/)) {
			return new None<Tuple2<string, string>>(/*auto*/);
		}
		return selector/*auto*/(divisions/*auto*/);
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let beforeLast: List<string> = divisions/*auto*/.subList(0/*auto*/, divisions/*auto*/.size(/*auto*/) - 1/*auto*/).orElse(divisions/*auto*/);
		let last: string = divisions/*auto*/.findLast(/*auto*/).orElse("");
		let joined: string = beforeLast/*auto*/.query(/*auto*/).collect(new Joiner(delimiter/*auto*/)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(joined/*auto*/, last/*auto*/));
	}
	static foldInvocationStarts(state: DivideState, c: string): DivideState {
		let appended: DivideState = state/*auto*/.append(c/*auto*/);
		if ("(" === c/*auto*/) {
			let entered: DivideState = appended/*auto*/.enter(/*auto*/);
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
	static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.parseValues(state/*auto*/, argsString/*auto*/, (state1: CompileState, s: string) => Main/*auto*/.parseArgument(state1/*auto*/, s/*auto*/)).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState: CompileState = argsTuple/*auto*/.left(/*auto*/);
			let args: List<Value> = Main/*auto*/.retain(argsTuple/*auto*/.right(/*auto*/), (argument: Argument) => argument/*auto*/.toValue(/*auto*/));
			let newCaller: Caller = Main/*auto*/.transformCaller(argsState/*auto*/, oldCaller/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState/*auto*/, new InvokableNode(newCaller/*auto*/, args/*auto*/)));
		});
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller/*auto*/.findChild(/*auto*/).flatMap((parent: Value) => {
			let parentType: Type = parent/*auto*/.resolve(state/*auto*/);
			if (parentType/*auto*/.isFunctional(/*auto*/)) {
				return new Some<Caller>(parent/*auto*/);
			}
			return new None<Caller>(/*auto*/);
		}).orElse(oldCaller/*auto*/);
	}
	static retain<T, R>(args: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
		return args/*auto*/.query(/*auto*/).map(mapper/*auto*/).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<R>(/*auto*/));
	}
	static parseArgument(state1: CompileState, input: string): Option<Tuple2<CompileState, Argument>> {
		return Main/*auto*/.parseValue(state1/*auto*/, input/*auto*/).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/)));
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(input/*auto*/, "=", (destination: string, source: string) => {
			let sourceTuple: Tuple2<CompileState, string> = Main/*auto*/.compileValueOrPlaceholder(state/*auto*/, source/*auto*/);
			let destinationTuple: Tuple2<CompileState, string> = Main/*auto*/.compileValue(sourceTuple/*auto*/.left(/*auto*/), destination/*auto*/).or(() => Main/*auto*/.parseDefinition(sourceTuple/*auto*/.left(/*auto*/), destination/*auto*/).map((definitionTuple: Tuple2<CompileState, Definition>) => {
				let definitionState: CompileState = definitionTuple/*auto*/.left(/*auto*/);
				let definition: Definition = definitionTuple/*auto*/.right(/*auto*/);
				let let: Definition = Main/*auto*/.attachLet(definitionState/*auto*/, definition/*auto*/);
				let generate: string = let/*auto*/.generate(definitionState/*auto*/.platform(/*auto*/));
				return new Tuple2Impl<CompileState, string>(definitionState/*auto*/, generate/*auto*/);
			})).orElseGet(() => new Tuple2Impl<CompileState, string>(sourceTuple/*auto*/.left(/*auto*/), Main/*auto*/.generatePlaceholder(destination/*auto*/)));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple/*auto*/.left(/*auto*/), destinationTuple/*auto*/.right(/*auto*/) + " = " + sourceTuple/*auto*/.right(/*auto*/)));
		});
	}
	static attachLet(definitionState: CompileState, definition: Definition): Definition {
		/*final Definition let*/;
		if (definitionState/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
			let/*auto*/ = definition/*auto*/;
		}
		else {
			let/*auto*/ = definition/*auto*/.addModifierLast("let");
		}
		return let/*auto*/;
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileValue(state/*auto*/, input/*auto*/).orElseGet(() => new Tuple2Impl<CompileState, string>(state/*auto*/, Main/*auto*/.generatePlaceholder(input/*auto*/)));
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseValue(state/*auto*/, input/*auto*/).map((tuple: Tuple2<CompileState, Value>) => {
			let generated: string = tuple/*auto*/.right(/*auto*/).generate(tuple/*auto*/.left(/*auto*/).platform(/*auto*/));
			return new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), generated/*auto*/);
		});
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.or(state/*auto*/, input/*auto*/, Lists/*auto*/.of(Main/*auto*/.parseLambda, Main/*auto*/.createOperatorRule("+"), Main/*auto*/.createOperatorRule("-"), Main/*auto*/.createOperatorRule("<="), Main/*auto*/.createOperatorRule("<"), Main/*auto*/.createOperatorRule("&&"), Main/*auto*/.createOperatorRule("||"), Main/*auto*/.createOperatorRule(">"), Main/*auto*/.createOperatorRule(">="), Main/*auto*/.parseInvokable, Main/*auto*/.createAccessRule("."), Main/*auto*/.createAccessRule("::"), Main/*auto*/.parseSymbol, Main/*auto*/.parseNot, Main/*auto*/.parseNumber, Main/*auto*/.createOperatorRuleWithDifferentInfix("==", "==="), Main/*auto*/.createOperatorRuleWithDifferentInfix("!=", "!=="), Main/*auto*/.createTextRule("\""), Main/*auto*/.createTextRule("'")));
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped: string = Strings/*auto*/.strip(input1/*auto*/);
			return Main/*auto*/.compilePrefix(stripped/*auto*/, slice/*auto*/, (s: string) => Main/*auto*/.compileSuffix(s/*auto*/, slice/*auto*/, (s1: string) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1/*auto*/, new StringNode(s1/*auto*/)))));
		};
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*auto*/), "!", (withoutPrefix: string) => {
			let childTuple: Tuple2<CompileState, string> = Main/*auto*/.compileValueOrPlaceholder(state/*auto*/, withoutPrefix/*auto*/);
			let childState: CompileState = childTuple/*auto*/.left(/*auto*/);
			let child: string = "!" + childTuple/*auto*/.right(/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new NotNode(child/*auto*/)));
		});
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compileFirst(input/*auto*/, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow: string = Strings/*auto*/.strip(beforeArrow/*auto*/);
			return Main/*auto*/.compilePrefix(strippedBeforeArrow/*auto*/, "(", (withoutStart: string) => Main/*auto*/.compileSuffix(withoutStart/*auto*/, ")", (withoutEnd: string) => Main/*auto*/.parseValues(state/*auto*/, withoutEnd/*auto*/, (state1: CompileState, s: string) => Main/*auto*/.parseParameter(state1/*auto*/, s/*auto*/)).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => Main/*auto*/.compileLambdaWithParameterNames(paramNames/*auto*/.left(/*auto*/), Main/*auto*/.retainDefinitionsFromParameters(paramNames/*auto*/.right(/*auto*/)), afterArrow/*auto*/))));
		});
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow: string = Strings/*auto*/.strip(afterArrow/*auto*/);
		return Main/*auto*/.compilePrefix(strippedAfterArrow/*auto*/, "{", (withoutContentStart: string) => Main/*auto*/.compileSuffix(withoutContentStart/*auto*/, "}", (withoutContentEnd: string) => {
			let statementsTuple: Tuple2<CompileState, string> = Main/*auto*/.compileFunctionStatements(state/*auto*/.enterDepth(/*auto*/).defineAll(paramNames/*auto*/), withoutContentEnd/*auto*/);
			let statementsState: CompileState = statementsTuple/*auto*/.left(/*auto*/);
			let statements: string = statementsTuple/*auto*/.right(/*auto*/);
			let exited: CompileState = statementsState/*auto*/.exitDepth(/*auto*/);
			let content: string = "{" + statements/*auto*/ + exited/*auto*/.createIndent(/*auto*/) + "}";
			if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
				return Main/*auto*/.assembleLambdaWithContent(exited/*auto*/, paramNames/*auto*/, content/*auto*/);
			}
			return Main/*auto*/.getSome(exited/*auto*/, paramNames/*auto*/, content/*auto*/);
		})).or(() => Main/*auto*/.compileValue(state/*auto*/, strippedAfterArrow/*auto*/).flatMap((tuple: Tuple2<CompileState, string>) => {
			let state1: CompileState = tuple/*auto*/.left(/*auto*/);
			let content: string = tuple/*auto*/.right(/*auto*/);
			if (state1/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
				return Main/*auto*/.assembleLambdaWithContent(state1/*auto*/, paramNames/*auto*/, "\n\treturn " + content/*auto*/ + ";");
			}
			return Main/*auto*/.getSome(state1/*auto*/, paramNames/*auto*/, content/*auto*/);
		}));
	}
	static getSome(state: CompileState, parameters: List<Definition>, content: string): Some<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*auto*/, new LambdaNode(parameters/*auto*/, content/*auto*/)));
	}
	static assembleLambdaWithContent(state: CompileState, parameters: List<Definition>, content: string): Some<Tuple2<CompileState, Value>> {
		let lambdaDefinition: Definition = new Definition(PrimitiveType/*auto*/.Auto, state/*auto*/.functionName(/*auto*/));
		let value: FunctionSegment<Definition> = new FunctionSegment<Definition>(lambdaDefinition/*auto*/, parameters/*auto*/, new Some<>(content/*auto*/));
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*auto*/.addFunction(value/*auto*/.generate(state/*auto*/.platform(/*auto*/), "\n")), new SymbolNode("lambdaDefinition", PrimitiveType/*auto*/.Auto)));
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.createOperatorRuleWithDifferentInfix(infix/*auto*/, infix/*auto*/);
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state: CompileState, input: string) => Main/*auto*/.compileLast(input/*auto*/, infix/*auto*/, (childString: string, rawProperty: string) => {
			let property: string = Strings/*auto*/.strip(rawProperty/*auto*/);
			if (!Main/*auto*/.isSymbol(property/*auto*/)) {
				return new None<Tuple2<CompileState, Value>>(/*auto*/);
			}
			return Main/*auto*/.parseValue(state/*auto*/, childString/*auto*/).flatMap((childTuple: Tuple2<CompileState, Value>) => {
				let childState: CompileState = childTuple/*auto*/.left(/*auto*/);
				let child: Value = childTuple/*auto*/.right(/*auto*/);
				return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new AccessNode(child/*auto*/, property/*auto*/)));
			});
		});
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFolded(input1/*auto*/, Main/*auto*/.foldOperator(sourceInfix/*auto*/), (divisions: List<string>) => Main/*auto*/.selectFirst(divisions/*auto*/, sourceInfix/*auto*/)), (leftString: string, rightString: string) => Main/*auto*/.parseValue(state1/*auto*/, leftString/*auto*/).flatMap((leftTuple: Tuple2<CompileState, Value>) => Main/*auto*/.parseValue(leftTuple/*auto*/.left(/*auto*/), rightString/*auto*/).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
			let left: Value = leftTuple/*auto*/.right(/*auto*/);
			let right: Value = rightTuple/*auto*/.right(/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple/*auto*/.left(/*auto*/), new OperationNode(left/*auto*/, targetInfix/*auto*/, right/*auto*/)));
		})));
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first: string = divisions/*auto*/.findFirst(/*auto*/).orElse("");
		let afterFirst: string = divisions/*auto*/.subList(1/*auto*/, divisions/*auto*/.size(/*auto*/)).orElse(divisions/*auto*/).query(/*auto*/).collect(new Joiner(delimiter/*auto*/)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(first/*auto*/, afterFirst/*auto*/));
	}
	static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state: DivideState, c: string) => {
			if (c/*auto*/ === Strings/*auto*/.charAt(infix/*auto*/, 0/*auto*/) && state/*auto*/.startsWith(Strings/*auto*/.sliceFrom(infix/*auto*/, 1/*auto*/))) {
				let length: number = Strings/*auto*/.length(infix/*auto*/) - 1/*auto*/;
				let counter: number = 0/*auto*/;
				let current: DivideState = state/*auto*/;
				while (counter/*auto*/ < length/*auto*/) {
					counter/*auto*/++;
					current/*auto*/ = current/*auto*/.pop(/*auto*/).map((tuple: Tuple2<DivideState, string>) => tuple/*auto*/.left(/*auto*/)).orElse(current/*auto*/);
				}
				return current/*auto*/.advance(/*auto*/);
			}
			return state/*auto*/.append(c/*auto*/);
		};
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped: string = Strings/*auto*/.strip(input/*auto*/);
		if (Main/*auto*/.isNumber(stripped/*auto*/)) {
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*auto*/, new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>(/*auto*/);
		}
	}
	static isNumber(input: string): boolean {
		let query: HeadedQuery<number> = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*auto*/)));
		return query/*auto*/.map((index: number) => input/*auto*/.charAt(index/*auto*/)).allMatch((c: string) => Characters/*auto*/.isDigit(c/*auto*/));
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped: string = Strings/*auto*/.strip(input/*auto*/);
		if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
			let withImport: CompileState = state/*auto*/.addResolvedImportFromCache(stripped/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport/*auto*/, new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>(/*auto*/);
		}
	}
	static isSymbol(input: string): boolean {
		let query: HeadedQuery<number> = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*auto*/)));
		return query/*auto*/.allMatch((index: number) => Main/*auto*/.isSymbolChar(index/*auto*/, Strings/*auto*/.charAt(input/*auto*/, index/*auto*/)));
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c/*auto*/ || Characters/*auto*/.isLetter(c/*auto*/) || /*auto*/(0/*auto*/ !== index/*auto*/ && Characters/*auto*/.isDigit(c/*auto*/));
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		if (!input/*auto*/.startsWith(infix/*auto*/)) {
			return new None<Tuple2<CompileState, T>>(/*auto*/);
		}
		let slice: string = Strings/*auto*/.sliceFrom(input/*auto*/, Strings/*auto*/.length(infix/*auto*/));
		return mapper/*auto*/(slice/*auto*/);
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseWhitespace(state/*auto*/, input/*auto*/).map((tuple: Tuple2<CompileState, Whitespace>) => {
			let generate: string = tuple/*auto*/.right(/*auto*/).generate(tuple/*auto*/.left(/*auto*/).platform(/*auto*/));
			return new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), generate/*auto*/);
		});
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings/*auto*/.isBlank(input/*auto*/)) {
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state/*auto*/, new Whitespace(/*auto*/)));
		}
		return new None<Tuple2<CompileState, Whitespace>>(/*auto*/);
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), ";", (withoutEnd: string) => Main/*auto*/.getTupleOption(state/*auto*/, withoutEnd/*auto*/).or(() => Main/*auto*/.compileEnumValues(state/*auto*/, withoutEnd/*auto*/)));
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseParameter(state/*auto*/, withoutEnd/*auto*/).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => {
			let generate: string = "\n\t" + definitionTuple/*auto*/.right(/*auto*/).generate(definitionTuple/*auto*/.left(/*auto*/).platform(/*auto*/)) + ";";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(definitionTuple/*auto*/.left(/*auto*/), generate/*auto*/));
		});
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseValues(state/*auto*/, withoutEnd/*auto*/, (state1: CompileState, s: string) => Main/*auto*/.parseInvokable(state1/*auto*/, s/*auto*/).flatMap((tuple: Tuple2<CompileState, Value>) => {
			let structureName: string = state/*auto*/.findLastStructureName(/*auto*/).orElse("");
			return tuple/*auto*/.right(/*auto*/).generateAsEnumValue(structureName/*auto*/, state/*auto*/.platform(/*auto*/)).map((stringOption: string) => new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), stringOption/*auto*/));
		})).map((tuple: Tuple2<CompileState, List<string>>) => new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/).query(/*auto*/).collect(new Joiner("")).orElse("")));
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return Main/*auto*/.parseParameter(state/*auto*/, input/*auto*/).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state/*auto*/, new Placeholder(input/*auto*/)));
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return Main/*auto*/.parseWhitespace(state/*auto*/, input/*auto*/).map((tuple: Tuple2<CompileState, Whitespace>) => Main/*auto*/.getCompileStateParameterTuple2(tuple/*auto*/)).or(() => Main/*auto*/.parseDefinition(state/*auto*/, input/*auto*/).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/))));
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/));
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.compileLast(Strings/*auto*/.strip(input/*auto*/), " ", (beforeName: string, name: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(Strings/*auto*/.strip(beforeName/*auto*/), " ", Main/*auto*/.foldTypeSeparators), (beforeType: string, type: string) => Main/*auto*/.compileLast(Strings/*auto*/.strip(beforeType/*auto*/), "\n", (annotationsString: string, afterAnnotations: string) => {
			let annotations: List<string> = Main/*auto*/.parseAnnotations(annotationsString/*auto*/);
			return Main/*auto*/.parseDefinitionWithAnnotations(state/*auto*/, annotations/*auto*/, afterAnnotations/*auto*/, type/*auto*/, name/*auto*/);
		}).or(() => Main/*auto*/.parseDefinitionWithAnnotations(state/*auto*/, Lists/*auto*/.empty(/*auto*/), beforeType/*auto*/, type/*auto*/, name/*auto*/))).or(() => Main/*auto*/.parseDefinitionWithTypeParameters(state/*auto*/, Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), beforeName/*auto*/, name/*auto*/)));
	}
	static parseAnnotations(s: string): List<string> {
		return Main/*auto*/.divide(s/*auto*/, (state1: DivideState, c: string) => Main/*auto*/.foldDelimited(state1/*auto*/, c/*auto*/, "\n")).query(/*auto*/).map((s2: string) => Strings/*auto*/.strip(s2/*auto*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*auto*/)).filter((value: string) => 1/*auto*/ <= Strings/*auto*/.length(value/*auto*/)).map((value: string) => Strings/*auto*/.sliceFrom(value/*auto*/, 1/*auto*/)).map((s1: string) => Strings/*auto*/.strip(s1/*auto*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*auto*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeType/*auto*/), ">", (withoutTypeParamEnd: string) => Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", (beforeTypeParams: string, typeParamsString: string) => {
			let typeParams: List<string> = Main/*auto*/.divideValues(typeParamsString/*auto*/);
			return Main/*auto*/.parseDefinitionWithTypeParameters(state/*auto*/, annotations/*auto*/, typeParams/*auto*/, Main/*auto*/.parseModifiers(beforeTypeParams/*auto*/), type/*auto*/, name/*auto*/);
		})).or(() => {
			let divided: List<string> = Main/*auto*/.parseModifiers(beforeType/*auto*/);
			return Main/*auto*/.parseDefinitionWithTypeParameters(state/*auto*/, annotations/*auto*/, Lists/*auto*/.empty(/*auto*/), divided/*auto*/, type/*auto*/, name/*auto*/);
		});
	}
	static parseModifiers(beforeType: string): List<string> {
		return Main/*auto*/.divide(Strings/*auto*/.strip(beforeType/*auto*/), (state1: DivideState, c: string) => Main/*auto*/.foldDelimited(state1/*auto*/, c/*auto*/, " ")).query(/*auto*/).map((s: string) => Strings/*auto*/.strip(s/*auto*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*auto*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter/*auto*/ === c/*auto*/) {
			return state1/*auto*/.advance(/*auto*/);
		}
		return state1/*auto*/.append(c/*auto*/);
	}
	static divideValues(input: string): List<string> {
		return Main/*auto*/.divide(input/*auto*/, Main/*auto*/.foldValues).query(/*auto*/).map((input1: string) => Strings/*auto*/.strip(input1/*auto*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*auto*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static foldTypeSeparators(state: DivideState, c: string): DivideState {
		if (" " === c/*auto*/ && state/*auto*/.isLevel(/*auto*/)) {
			return state/*auto*/.advance(/*auto*/);
		}
		let appended: DivideState = state/*auto*/.append(c/*auto*/);
		if ("<" === c/*auto*/) {
			return appended/*auto*/.enter(/*auto*/);
		}
		if (">" === c/*auto*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, oldModifiers: List<string>, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.parseType(state/*auto*/, type/*auto*/).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers: List<string> = Main/*auto*/.modifyModifiers(oldModifiers/*auto*/, state/*auto*/.platform(/*auto*/));
			let generated: Definition = new Definition(annotations/*auto*/, newModifiers/*auto*/, typeParams/*auto*/, typeTuple/*auto*/.right(/*auto*/), name/*auto*/);
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple/*auto*/.left(/*auto*/), generated/*auto*/));
		});
	}
	static modifyModifiers(oldModifiers: List<string>, platform: Platform): List<string> {
		let list: List<string> = Main/*auto*/.retainFinal(oldModifiers/*auto*/, platform/*auto*/);
		if (oldModifiers/*auto*/.contains("static", Strings/*auto*/.equalsTo)) {
			return list/*auto*/.addLast("static");
		}
		return list/*auto*/;
	}
	static retainFinal(oldModifiers: List<string>, platform: Platform): List<string> {
		if (oldModifiers/*auto*/.contains("final", Strings/*auto*/.equalsTo) || Platform/*auto*/.Magma !== platform/*auto*/) {
			return Lists/*auto*/.empty(/*auto*/);
		}
		return Lists/*auto*/.of("mut");
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple2<CompileState, Type> {
		return Main/*auto*/.parseType(state/*auto*/, type/*auto*/).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/))).orElseGet(() => new Tuple2Impl<CompileState, Type>(state/*auto*/, new Placeholder(type/*auto*/)));
	}
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseType(state/*auto*/, type/*auto*/).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, string>(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/).generate(/*auto*/)));
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.or(state/*auto*/, type/*auto*/, Lists/*auto*/.of(Main/*auto*/.parseArrayType, Main/*auto*/.parseVarArgs, Main/*auto*/.parseGeneric, Main/*auto*/.parsePrimitive, Main/*auto*/.parseSymbolType));
	}
	static parseArrayType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped: string = Strings/*auto*/.strip(input/*auto*/);
		return Main/*auto*/.compileSuffix(stripped/*auto*/, "[]", (s: string) => {
			let child: Tuple2<CompileState, Type> = Main/*auto*/.parseTypeOrPlaceholder(state/*auto*/, s/*auto*/);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*auto*/.left(/*auto*/), new ArrayType(child/*auto*/.right(/*auto*/))));
		});
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped: string = Strings/*auto*/.strip(input/*auto*/);
		return Main/*auto*/.compileSuffix(stripped/*auto*/, "...", (s: string) => {
			let child: Tuple2<CompileState, Type> = Main/*auto*/.parseTypeOrPlaceholder(state/*auto*/, s/*auto*/);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*auto*/.left(/*auto*/), new VariadicType(child/*auto*/.right(/*auto*/))));
		});
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped: string = Strings/*auto*/.strip(input/*auto*/);
		if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state/*auto*/.addResolvedImportFromCache(stripped/*auto*/), new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
		}
		return new None<Tuple2<CompileState, Type>>(/*auto*/);
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.findPrimitiveValue(Strings/*auto*/.strip(input/*auto*/), state/*auto*/.platform(/*auto*/)).map((result: Type) => new Tuple2Impl<CompileState, Type>(state/*auto*/, result/*auto*/));
	}
	static findPrimitiveValue(input: string, platform: Platform): Option<Type> {
		let stripped: string = Strings/*auto*/.strip(input/*auto*/);
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
	static parseGeneric(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*auto*/), ">", (withoutEnd: string) => Main/*auto*/.compileFirst(withoutEnd/*auto*/, "<", (baseString: string, argsString: string) => {
			let argsTuple: Tuple2<CompileState, List<string>> = Main/*auto*/.parseValuesOrEmpty(state/*auto*/, argsString/*auto*/, (state1: CompileState, s: string) => Main/*auto*/.compileTypeArgument(state1/*auto*/, s/*auto*/));
			let argsState: CompileState = argsTuple/*auto*/.left(/*auto*/);
			let args: List<string> = argsTuple/*auto*/.right(/*auto*/);
			let base: string = Strings/*auto*/.strip(baseString/*auto*/);
			return Main/*auto*/.assembleFunctionType(argsState/*auto*/, base/*auto*/, args/*auto*/).or(() => {
				let compileState: CompileState = argsState/*auto*/.addResolvedImportFromCache(base/*auto*/);
				return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState/*auto*/, new TemplateType(base/*auto*/, args/*auto*/)));
			});
		}));
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.mapFunctionType(base/*auto*/, args/*auto*/).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state/*auto*/, generated/*auto*/));
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if (Strings/*auto*/.equalsTo("Function", base/*auto*/)) {
			return args/*auto*/.findFirst(/*auto*/).and(() => args/*auto*/.find(1/*auto*/)).map((tuple: Tuple2<string, string>) => new FunctionType(Lists/*auto*/.of(tuple/*auto*/.left(/*auto*/)), tuple/*auto*/.right(/*auto*/)));
		}
		if (Strings/*auto*/.equalsTo("BiFunction", base/*auto*/)) {
			return args/*auto*/.find(0/*auto*/).and(() => args/*auto*/.find(1/*auto*/)).and(() => args/*auto*/.find(2/*auto*/)).map((tuple: Tuple2<Tuple2<string, string>, string>) => new FunctionType(Lists/*auto*/.of(tuple/*auto*/.left(/*auto*/).left(/*auto*/), tuple/*auto*/.left(/*auto*/).right(/*auto*/)), tuple/*auto*/.right(/*auto*/)));
		}
		if (Strings/*auto*/.equalsTo("Supplier", base/*auto*/)) {
			return args/*auto*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.empty(/*auto*/), first/*auto*/));
		}
		if (Strings/*auto*/.equalsTo("Consumer", base/*auto*/)) {
			return args/*auto*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.of(first/*auto*/), "void"));
		}
		if (Strings/*auto*/.equalsTo("Predicate", base/*auto*/)) {
			return args/*auto*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.of(first/*auto*/), "boolean"));
		}
		return new None<Type>(/*auto*/);
	}
	static compileTypeArgument(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.or(state/*auto*/, input/*auto*/, Lists/*auto*/.of((state2: CompileState, input1: string) => Main/*auto*/.compileWhitespace(state2/*auto*/, input1/*auto*/), (state1: CompileState, type: string) => Main/*auto*/.compileType(state1/*auto*/, type/*auto*/)));
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return Main/*auto*/.parseValues(state/*auto*/, input/*auto*/, mapper/*auto*/).orElse(new Tuple2Impl<CompileState, List<T>>(state/*auto*/, Lists/*auto*/.empty(/*auto*/)));
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main/*auto*/.parseAll(state/*auto*/, input/*auto*/, Main/*auto*/.foldValues, mapper/*auto*/);
	}
	static foldValues(state: DivideState, c: string): DivideState {
		if ("," === c/*auto*/ && state/*auto*/.isLevel(/*auto*/)) {
			return state/*auto*/.advance(/*auto*/);
		}
		let appended: DivideState = state/*auto*/.append(c/*auto*/);
		if ("-" === c/*auto*/) {
			let peeked: string = appended/*auto*/.peek(/*auto*/);
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
	static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileInfix(input/*auto*/, infix/*auto*/, Main/*auto*/.findLast, mapper/*auto*/);
	}
	static findLast(input: string, infix: string): number {
		return input/*auto*/.lastIndexOf(infix/*auto*/);
	}
	static compileSuffix<T>(input: string, suffix: string, mapper: (arg0 : string) => Option<T>): Option<T> {
		if (!input/*auto*/.endsWith(suffix/*auto*/)) {
			return new None<T>(/*auto*/);
		}
		let length: number = Strings/*auto*/.length(input/*auto*/);
		let length1: number = Strings/*auto*/.length(suffix/*auto*/);
		let content: string = Strings/*auto*/.sliceBetween(input/*auto*/, 0/*auto*/, length/*auto*/ - length1/*auto*/);
		return mapper/*auto*/(content/*auto*/);
	}
	static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileInfix(input/*auto*/, infix/*auto*/, Main/*auto*/.findFirst, mapper/*auto*/);
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileSplit(Main/*auto*/.split(input/*auto*/, infix/*auto*/, locator/*auto*/), mapper/*auto*/);
	}
	static compileSplit<T>(splitter: Option<Tuple2<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter/*auto*/.flatMap((tuple: Tuple2<string, string>) => mapper/*auto*/(tuple/*auto*/.left(/*auto*/), tuple/*auto*/.right(/*auto*/)));
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple2<string, string>> {
		let index: number = locator/*auto*/(input/*auto*/, infix/*auto*/);
		if (0/*auto*/ > index/*auto*/) {
			return new None<Tuple2<string, string>>(/*auto*/);
		}
		let left: string = Strings/*auto*/.sliceBetween(input/*auto*/, 0/*auto*/, index/*auto*/);
		let length: number = Strings/*auto*/.length(infix/*auto*/);
		let right: string = Strings/*auto*/.sliceFrom(input/*auto*/, index/*auto*/ + length/*auto*/);
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(left/*auto*/, right/*auto*/));
	}
	static findFirst(input: string, infix: string): number {
		return input/*auto*/.indexOf(infix/*auto*/);
	}
	static generatePlaceholder(input: string): string {
		let replaced: string = input/*auto*/.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced/*auto*/ + "*/";
	}
}
Main.main();
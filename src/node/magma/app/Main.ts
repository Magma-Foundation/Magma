// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries, Queries, Queries, Query, Query, Query, IOError, IOError, IOError, Path, Path, Path, None, None, None, Option, Option, Option, Some, Some, Some, Err, Err, Err, Ok, Ok, Ok, Result, Result, Result, Tuple2, Tuple2, Tuple2, Tuple2Impl, Tuple2Impl, Tuple2Impl, Type, Type, Type, CompileState, CompileState, CompileState, Definition, Definition, Definition, FunctionHeader, FunctionHeader, FunctionHeader, Parameter, Parameter, Parameter, FunctionSegment, FunctionSegment, FunctionSegment, ImmutableCompileState, ImmutableCompileState, ImmutableCompileState, Import, Import, Import, DivideState, DivideState, DivideState, Placeholder, Placeholder, Placeholder, Whitespace, Whitespace, Whitespace, ArrayType, ArrayType, ArrayType, BooleanType, BooleanType, BooleanType, FunctionType, FunctionType, FunctionType, PrimitiveType, PrimitiveType, PrimitiveType, SliceType, SliceType, SliceType, TemplateType, TemplateType, TemplateType, VariadicType, VariadicType, VariadicType, AccessNode, AccessNode, AccessNode, Argument, Argument, Argument, Caller, Caller, Caller, ConstructionCaller, ConstructionCaller, ConstructionCaller, ConstructorHeader, ConstructorHeader, ConstructorHeader, InvokableNode, InvokableNode, InvokableNode, LambdaNode, LambdaNode, LambdaNode, NotNode, NotNode, NotNode, OperationNode, OperationNode, OperationNode, StringNode, StringNode, StringNode, SymbolNode, SymbolNode, SymbolNode, Value, Value, Value, Location, Location, Location, Platform, Platform, Platform, Source, Source, Source, Main]
import { Files } from "../../jvm/api/io/Files";
import { Path } from "../../magma/api/io/Path";
import { List } from "../../magma/api/collect/list/List";
import { IOError } from "../../magma/api/io/IOError";
import { Some } from "../../magma/api/option/Some";
import { Console } from "../../jvm/api/io/Console";
import { Option } from "../../magma/api/option/Option";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Tuple2 } from "../../magma/api/Tuple2";
import { Source } from "../../magma/app/io/Source";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { None } from "../../magma/api/option/None";
import { ImmutableCompileState } from "../../magma/app/compile/ImmutableCompileState";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { Platform } from "../../magma/app/io/Platform";
import { Location } from "../../magma/app/io/Location";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Queries } from "../../magma/api/collect/Queries";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Import } from "../../magma/app/compile/Import";
import { Strings } from "../../jvm/api/text/Strings";
import { DivideState } from "../../magma/app/compile/text/DivideState";
import { Type } from "../../magma/api/Type";
import { Definition } from "../../magma/app/compile/define/Definition";
import { Parameter } from "../../magma/app/compile/define/Parameter";
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
		let sourceDirectory = Files/*auto*/.get(".", "src", "java");
		sourceDirectory/*auto*/.walk(/*auto*/).match((children: List<Path>) => Main/*auto*/.runWithChildren(children/*auto*/, sourceDirectory/*auto*/), (value: IOError) => new Some<IOError>(value/*&[I8]*/)).map((error: IOError) => error/*auto*/.display(/*auto*/)).ifPresent((displayed: string) => Console/*auto*/.printErrLn(displayed/*auto*/));
	}
	static runWithChildren(children: List<Path>, sourceDirectory: Path): Option<IOError> {
		let sources = Main/*auto*/.findSources(children/*List<Path>*/, sourceDirectory/*Path*/);
		return sources/*auto*/.query(/*auto*/).foldWithInitial(Main/*auto*/.createInitialStateToTuple(sources/*auto*/), (current: Tuple2<CompileState, Option<IOError>>, source1: Source) => Main/*auto*/.foldChild(current/*List<T>*/.left(/*auto*/), current/*List<T>*/.right(/*auto*/), source1/*auto*/)).right(/*auto*/);
	}
	static createInitialStateToTuple(sources: List<Source>): Tuple2<CompileState, Option<IOError>> {
		return new Tuple2Impl<CompileState, Option<IOError>>(Main/*auto*/.createInitialState(sources/*List<Source>*/), new None<IOError>(/*auto*/));
	}
	static createInitialState(sources: List<Source>): CompileState {
		return sources/*List<Source>*/.query(/*auto*/).foldWithInitial(ImmutableCompileState/*auto*/.createInitial(/*auto*/), (state: CompileState, source: Source) => state/*auto*/.addSource(source/*Source*/));
	}
	static findSources(children: List<Path>, sourceDirectory: Path): List<Source> {
		return children/*List<Path>*/.query(/*auto*/).filter((source: Path) => source/*Source*/.endsWith(".java")).map((child: Path) => new Source(sourceDirectory/*Path*/, child/*&[I8]*/)).collect(new ListCollector<Source>(/*auto*/));
	}
	static foldChild(state: CompileState, maybeError: Option<IOError>, source: Source): Tuple2<CompileState, Option<IOError>> {
		if (maybeError/*Option<IOError>*/.isPresent(/*auto*/)) {
			return new Tuple2Impl<CompileState, Option<IOError>>(state/*CompileState*/, maybeError/*Option<IOError>*/);
		}
		return Main/*auto*/.runWithSource(state/*CompileState*/, source/*Source*/);
	}
	static runWithSource(state: CompileState, source: Source): Tuple2<CompileState, Option<IOError>> {
		return source/*Source*/.read(/*auto*/).match((input: string) => Main/*auto*/.compileAndWritePlatforms(state/*CompileState*/, source/*Source*/, input/*&[I8]*/), (value: IOError) => new Tuple2Impl<CompileState, Option<IOError>>(state/*CompileState*/, new Some<IOError>(value/*&[I8]*/)));
	}
	static compileAndWritePlatforms(state: CompileState, source: Source, input: string): Tuple2<CompileState, Option<IOError>> {
		let current: Tuple2<CompileState, Option<IOError>> = new Tuple2Impl<CompileState, Option<IOError>>(state/*CompileState*/, new None<>(/*auto*/));/*
        for (final var platform : Platform.values()) {
            current = Main.foldPlatform(current.left(), current.right(), platform, source, input);
        }*/
		return current/*List<T>*/;
	}
	static foldPlatform(state: CompileState, maybeError: Option<IOError>, platform: Platform, source: Source, input: string): Tuple2<CompileState, Option<IOError>> {
		if (maybeError/*Option<IOError>*/.isPresent(/*auto*/)) {
			return new Tuple2Impl<>(state/*CompileState*/, maybeError/*Option<IOError>*/);
		}
		let otherTuple = Main/*auto*/.compileAndWrite(state/*CompileState*/, source/*Source*/, input/*string*/, platform/*Platform*/);
		let otherState = otherTuple/*auto*/.left(/*auto*/);
		let otherValue = otherTuple/*auto*/.right(/*auto*/);
		return new Tuple2Impl<>(otherState/*auto*/, maybeError/*Option<IOError>*/.or(() => otherValue/*auto*/));
	}
	static compileAndWrite(state: CompileState, source: Source, input: string, platform: Platform): Tuple2<CompileState, Option<IOError>> {
		let state1 = state/*CompileState*/.withLocation(source/*Source*/.computeLocation(/*auto*/)).withPlatform(platform/*Platform*/);
		let output = Main/*auto*/.compileRoot(state1/*auto*/, source/*Source*/, input/*string*/);
		let location = output/*auto*/.left(/*auto*/).findCurrentLocation(/*auto*/).orElse(new Location(Lists/*auto*/.empty(/*auto*/), ""));
		let targetDirectory = Files/*auto*/.get(".", "src", platform/*Platform*/.root);
		let targetParent = targetDirectory/*auto*/.resolveChildSegments(location/*auto*/.namespace(/*auto*/));
		if (!targetParent/*auto*/.exists(/*auto*/)) {
			let maybeError = targetParent/*auto*/.createDirectories(/*auto*/);
			if (maybeError/*Option<IOError>*/.isPresent(/*auto*/)) {
				return new Tuple2Impl<CompileState, Option<IOError>>(output/*auto*/.left(/*auto*/), maybeError/*Option<IOError>*/);
			}
		}
		let initial: Option<IOError> = new None<IOError>(/*auto*/);
		let ioErrorOption1 = Queries/*auto*/.fromArray(platform/*Platform*/.extension).foldWithInitial(initial/*R*/, (ioErrorOption: Option<IOError>, extension: string) => {
			let target = targetParent/*auto*/.resolveChild(location/*auto*/.name(/*auto*/) + "." + extension/*string*/);
			return ioErrorOption/*Option<IOError>*/.or(() => target/*auto*/.writeString(output/*auto*/.right(/*auto*/).get(extension/*string*/)));
		});
		return new Tuple2Impl<CompileState, Option<IOError>>(output/*auto*/.left(/*auto*/), ioErrorOption1/*auto*/);
	}
	static compileRoot(state: CompileState, source: Source, input: string): Tuple2<CompileState, Map<string, string>> {
		let statementsTuple = Main/*auto*/.compileStatements(state/*CompileState*/, input/*string*/, Main/*auto*/.compileRootSegment);
		let statementsState = statementsTuple/*auto*/.left(/*auto*/);
		let output = statementsTuple/*auto*/.right(/*auto*/);
		let imports = Main/*auto*/.generateOrFoldImports(statementsState/*auto*/);
		let entries = new HashMap<string, string>(/*auto*/);
		let generatedMain = Main/*auto*/.createMain(source/*Source*/);
		let clearedState = statementsState/*auto*/.clearImports(/*auto*/).clear(/*auto*/);
		let joinedDefinedTypes = clearedState/*auto*/.findDefinedTypes(/*auto*/).query(/*auto*/).collect(new Joiner(", ")).orElse("");
		let temp = "// [" + joinedDefinedTypes/*auto*/ + "]\n";
		if (!statementsState/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
			/*entries.put(statementsState.platform().extension[0], temp + imports + statementsState.join() + output + generatedMain)*/;
			return new Tuple2Impl<>(clearedState/*auto*/, entries/*auto*/);
		}
		let value = /* source.computeNamespace().query().collect(new Joiner("_")).map((String inner) -> inner + "_").orElse("") + source.computeName()*/;
		/*entries.put(Platform.Windows.extension[0], temp + Main.generateDirective("ifndef " + value) + Main.generateDirective("define " + value) + imports + Main.generateDirective("endif"))*/;
		/*entries.put(Platform.Windows.extension[1], Main.generateDirective("include \"./" + source.computeName() + ".h\"") + statementsState.join() + output + generatedMain)*/;
		return new Tuple2Impl<>(clearedState/*auto*/, entries/*auto*/);
	}
	static generateOrFoldImports(state: CompileState): string {
		if (state/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
			return Main/*auto*/.foldImports(state/*CompileState*/);
		}
		return Main/*auto*/.generateImports(state/*CompileState*/);
	}
	static generateImports(state: CompileState): string {
		return state/*CompileState*/.imports(/*auto*/).query(/*auto*/).map((anImport: Import) => anImport/*auto*/.generate(state/*CompileState*/.platform(/*auto*/))).collect(new Joiner("")).orElse("");
	}
	static foldImports(statementsState: CompileState): string {
		return statementsState/*CompileState*/.imports(/*auto*/).query(/*auto*/).foldWithInitial(Lists/*auto*/.empty(/*auto*/), Main/*auto*/.foldImport).query(/*auto*/).foldWithInitial("", Main/*auto*/.generateEntry);
	}
	static generateEntry(current: string, entry: Tuple2<List<string>, List<string>>): string {
		let joinedNamespace = entry/*Tuple2<List<string>, List<string>>*/.left(/*auto*/).query(/*auto*/).collect(new Joiner(".")).orElse("");
		let joinedChildren = entry/*Tuple2<List<string>, List<string>>*/.right(/*auto*/).query(/*auto*/).collect(new Joiner(", ")).orElse("");
		return current/*string*/ + "import " + joinedNamespace/*auto*/ + ".{ " + joinedChildren/*auto*/ + " };\n";
	}
	static foldImport(current: List<Tuple2<List<string>, List<string>>>, anImport: Import): List<Tuple2<List<string>, List<string>>> {
		let namespace = anImport/*Import*/.namespace(/*auto*/);
		let child = anImport/*Import*/.child(/*auto*/);
		if (Main/*auto*/.hasNamespace(current/*List<Tuple2<List<string>, List<string>>>*/, namespace/*Location*/)) {
			return Main/*auto*/.attachChildToMapEntries(current/*List<Tuple2<List<string>, List<string>>>*/, namespace/*Location*/, child/*&[I8]*/);
		}
		else {
			return current/*List<Tuple2<List<string>, List<string>>>*/.addLast(new Tuple2Impl<>(namespace/*Location*/, Lists/*auto*/.of(child/*&[I8]*/)));
		}
	}
	static hasNamespace(map: List<Tuple2<List<string>, List<string>>>, namespace: List<string>): boolean {
		return map/*List<Tuple2<List<string>, List<string>>>*/.query(/*auto*/).map(Tuple2/*auto*/.left).anyMatch((stringList: List<string>) => namespace/*List<string>*/.equalsTo(stringList/*auto*/, String/*auto*/.equals));
	}
	static attachChildToMapEntries(map: List<Tuple2<List<string>, List<string>>>, namespace: List<string>, child: string): List<Tuple2<List<string>, List<string>>> {
		return map/*List<Tuple2<List<string>, List<string>>>*/.query(/*auto*/).map((tuple: Tuple2<List<string>, List<string>>) => Main/*auto*/.attachChildToMapEntry(namespace/*List<string>*/, child/*string*/, tuple/*auto*/)).collect(new ListCollector<>(/*auto*/));
	}
	static attachChildToMapEntry(namespace: List<string>, child: string, tuple: Tuple2<List<string>, List<string>>): Tuple2<List<string>, List<string>> {
		let entryNamespace = tuple/*Tuple2<List<string>, List<string>>*/.left(/*auto*/);
		let entryValues = tuple/*Tuple2<List<string>, List<string>>*/.right(/*auto*/);
		if (entryNamespace/*auto*/.equalsTo(namespace/*List<string>*/, String/*auto*/.equals)) {
			return new Tuple2Impl<>(entryNamespace/*auto*/, entryValues/*auto*/.addLast(child/*string*/));
		}
		else {
			return tuple/*Tuple2<List<string>, List<string>>*/;
		}
	}
	static generateDirective(content: string): string {
		return "#" + content/*string*/ + "\n";
	}
	static createMain(source: Source): string {
		if (Strings/*auto*/.equalsTo(source/*Source*/.computeName(/*auto*/), "Main")) {
			return "Main.main();";
		}
		return "";
	}
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>): Tuple2<CompileState, string> {
		return Main/*auto*/.compileAll(state/*CompileState*/, input/*string*/, Main/*auto*/.foldStatements, mapper/*(arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>*/, Main/*auto*/.mergeStatements);
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple2<CompileState, string> {
		let folded = Main/*auto*/.parseAll(state/*CompileState*/, input/*string*/, folder/*(arg0 : DivideState, arg1 : string) => DivideState*/, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, string>>(mapper/*(arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>*/(state1/*auto*/, s/*auto*/))).orElse(new Tuple2Impl<CompileState, List<string>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/)));
		return new Tuple2Impl<CompileState, string>(folded/*auto*/.left(/*auto*/), Main/*auto*/.generateAll(folded/*auto*/.right(/*auto*/), merger/*(arg0 : string, arg1 : string) => string*/));
	}
	static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements/*List<string>*/.query(/*auto*/).foldWithInitial("", merger/*(arg0 : string, arg1 : string) => string*/);
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, biFunction: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main/*auto*/.divide(input/*string*/, folder/*(arg0 : DivideState, arg1 : string) => DivideState*/).query(/*auto*/).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/))), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: string) => maybeCurrent/*auto*/.flatMap((current: Tuple2<CompileState, List<T>>) => {
			let currentState = current/*Tuple2<CompileState, List<T>>*/.left(/*auto*/);
			let currentElement = current/*Tuple2<CompileState, List<T>>*/.right(/*auto*/);
			return biFunction/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>*/(currentState/*auto*/, segment/*auto*/).map((mappedTuple: Tuple2<CompileState, T>) => {
				let mappedState = mappedTuple/*Tuple2<CompileState, T>*/.left(/*auto*/);
				let mappedElement = mappedTuple/*Tuple2<CompileState, T>*/.right(/*auto*/);
				return new Tuple2Impl<CompileState, List<T>>(mappedState/*auto*/, currentElement/*auto*/.addLast(mappedElement/*auto*/));
			});
		}));
	}
	static mergeStatements(cache: string, element: string): string {
		return cache/*string*/ + element/*string*/;
	}
	static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): List<string> {
		let current = DivideState/*auto*/.createInitial(input/*string*/);
		while (true/*auto*/) {
			let poppedTuple0 = current/*Tuple2<CompileState, List<T>>*/.pop(/*auto*/).toTuple(new Tuple2Impl<DivideState, string>(current/*Tuple2<CompileState, List<T>>*/, "\0"));
			if (!poppedTuple0/*auto*/.left(/*auto*/)) {
				break;
			}
			let poppedTuple = poppedTuple0/*auto*/.right(/*auto*/);
			let poppedState = poppedTuple/*auto*/.left(/*auto*/);
			let popped = poppedTuple/*auto*/.right(/*auto*/);
			current/*Tuple2<CompileState, List<T>>*/ = Main/*auto*/.foldSingleQuotes(poppedState/*auto*/, popped/*auto*/).or(() => Main/*auto*/.foldDoubleQuotes(poppedState/*auto*/, popped/*auto*/)).orElseGet(() => folder/*(arg0 : DivideState, arg1 : string) => DivideState*/(poppedState/*auto*/, popped/*auto*/));
		}
		return current/*Tuple2<CompileState, List<T>>*/.advance(/*auto*/).segments(/*auto*/);
	}
	static foldDoubleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\"" !== c/*string*/) {
			return new None<DivideState>(/*auto*/);
		}
		let appended = state/*DivideState*/.append(c/*string*/);
		while (true/*auto*/) {
			let maybeTuple = appended/*auto*/.popAndAppendToTuple(/*auto*/).toTuple(new Tuple2Impl<DivideState, string>(appended/*auto*/, "\0"));
			if (!maybeTuple/*auto*/.left(/*auto*/)) {
				break;
			}
			let tuple = maybeTuple/*auto*/.right(/*auto*/);
			appended/*auto*/ = tuple/*Tuple2<List<string>, List<string>>*/.left(/*auto*/);
			if ("\\" === tuple/*Tuple2<List<string>, List<string>>*/.right(/*auto*/)) {
				appended/*auto*/ = appended/*auto*/.popAndAppendToOption(/*auto*/).orElse(appended/*auto*/);
			}
			if ("\"" === tuple/*Tuple2<List<string>, List<string>>*/.right(/*auto*/)) {
				break;
			}
		}
		return new Some<DivideState>(appended/*auto*/);
	}
	static foldSingleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\'" !== c/*string*/) {
			return new None<DivideState>(/*auto*/);
		}
		return state/*DivideState*/.append(c/*string*/).popAndAppendToTuple(/*auto*/).flatMap(Main/*auto*/.foldEscaped).flatMap((state1: DivideState) => state1/*auto*/.popAndAppendToOption(/*auto*/));
	}
	static foldEscaped(tuple: Tuple2<DivideState, string>): Option<DivideState> {
		let state = tuple/*Tuple2<DivideState, string>*/.left(/*auto*/);
		let c = tuple/*Tuple2<DivideState, string>*/.right(/*auto*/);
		if ("\\" === c/*string*/) {
			return state/*DivideState*/.popAndAppendToOption(/*auto*/);
		}
		return new Some<DivideState>(state/*DivideState*/);
	}
	static foldStatements(state: DivideState, c: string): DivideState {
		let appended = state/*DivideState*/.append(c/*string*/);
		if (";" === c/*string*/ && appended/*auto*/.isLevel(/*auto*/)) {
			return appended/*auto*/.advance(/*auto*/);
		}
		if ("}" === c/*string*/ && appended/*auto*/.isShallow(/*auto*/)) {
			return appended/*auto*/.advance(/*auto*/).exit(/*auto*/);
		}
		if ("{" === c/*string*/ || "(" === c/*string*/) {
			return appended/*auto*/.enter(/*auto*/);
		}
		if ("}" === c/*string*/ || ")" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static compileRootSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state/*CompileState*/, input/*string*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.compileNamespaced, Main/*auto*/.createStructureRule("class ", "class "), Main/*auto*/.createStructureRule("interface ", "interface "), Main/*auto*/.createStructureRule("record ", "class "), Main/*auto*/.createStructureRule("enum ", "class ")));
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state: CompileState, input1: string) => Main/*auto*/.compileFirst(input1/*auto*/, sourceInfix/*string*/, (beforeInfix: string, afterInfix: string) => Main/*auto*/.compileFirst(afterInfix/*auto*/, "{", (beforeContent: string, withEnd: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withEnd/*auto*/), "}", (inputContent: string) => Main/*auto*/.compileLast(beforeInfix/*auto*/, "\n", (s: string, s2: string) => {
			let annotations = Main/*auto*/.parseAnnotations(s/*string*/);
			if (annotations/*auto*/.contains("Actual", Strings/*auto*/.equalsTo)) {
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, ""));
			}
			return Main/*auto*/.compileStructureWithImplementing(state/*CompileState*/, annotations/*auto*/, Main/*auto*/.parseModifiers(s2/*string*/), targetInfix/*string*/, beforeContent/*auto*/, inputContent/*auto*/);
		}).or(() => {
			let modifiers = Main/*auto*/.parseModifiers(beforeContent/*auto*/);
			return Main/*auto*/.compileStructureWithImplementing(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), modifiers/*auto*/, targetInfix/*string*/, beforeContent/*auto*/, inputContent/*auto*/);
		}))));
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileLast(beforeContent/*string*/, " implements ", (s: string, s2: string) => Main/*auto*/.parseType(state/*CompileState*/, s2/*string*/).flatMap((implementingTuple: Tuple2<CompileState, Type>) => Main/*auto*/.compileStructureWithExtends(implementingTuple/*auto*/.left(/*auto*/), annotations/*List<string>*/, modifiers/*List<string>*/, targetInfix/*string*/, s/*string*/, new Some<Type>(implementingTuple/*auto*/.right(/*auto*/)), content/*string*/))).or(() => Main/*auto*/.compileStructureWithExtends(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, targetInfix/*string*/, beforeContent/*string*/, new None<Type>(/*auto*/), content/*string*/));
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(beforeContent/*string*/, " extends ", (beforeExtends: string, afterExtends: string) => Main/*auto*/.compileStructureWithParameters(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, targetInfix/*string*/, beforeExtends/*auto*/, new Some<string>(afterExtends/*auto*/), maybeImplementing/*Option<Type>*/, inputContent/*string*/)).or(() => Main/*auto*/.compileStructureWithParameters(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, targetInfix/*string*/, beforeContent/*string*/, new None<string>(/*auto*/), maybeImplementing/*Option<Type>*/, inputContent/*string*/));
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: Option<string>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(beforeContent/*string*/, "(", (rawName: string, withParameters: string) => Main/*auto*/.compileFirst(withParameters/*auto*/, ")", (parametersString: string, _: string) => {
			let name = Strings/*auto*/.strip(rawName/*auto*/);
			let parametersTuple = Main/*auto*/.parseParameters(state/*CompileState*/, parametersString/*string*/);
			let parameters = Main/*auto*/.retainDefinitionsFromParameters(parametersTuple/*auto*/.right(/*auto*/));
			return Main/*auto*/.compileStructureWithTypeParams(parametersTuple/*auto*/.left(/*auto*/), targetInfix/*string*/, inputContent/*string*/, name/*&[I8]*/, parameters/*auto*/, maybeImplementing/*Option<Type>*/, annotations/*List<string>*/, modifiers/*List<string>*/, maybeSuperType/*Option<string>*/);
		})).or(() => Main/*auto*/.compileStructureWithTypeParams(state/*CompileState*/, targetInfix/*string*/, inputContent/*string*/, beforeContent/*string*/, Lists/*auto*/.empty(/*auto*/), maybeImplementing/*Option<Type>*/, annotations/*List<string>*/, modifiers/*List<string>*/, maybeSuperType/*Option<string>*/));
	}
	static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters/*List<Parameter>*/.query(/*auto*/).map((parameter: Parameter) => parameter/*auto*/.asDefinition(/*auto*/)).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<Definition>(/*auto*/));
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeParams/*string*/), ">", (withoutTypeParamEnd: string) => Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", (name: string, typeParamsString: string) => {
			let typeParams = Main/*auto*/.divideValues(typeParamsString/*string*/);
			return Main/*auto*/.assembleStructure(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, infix/*string*/, name/*string*/, typeParams/*auto*/, parameters/*List<Definition>*/, maybeImplementing/*Option<Type>*/, content/*string*/, maybeSuperType/*Option<string>*/);
		})).or(() => Main/*auto*/.assembleStructure(state/*CompileState*/, annotations/*List<string>*/, modifiers/*List<string>*/, infix/*string*/, beforeParams/*string*/, Lists/*auto*/.empty(/*auto*/), parameters/*List<Definition>*/, maybeImplementing/*Option<Type>*/, content/*string*/, maybeSuperType/*Option<string>*/));
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: List<string>, parameters: List<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: Option<string>): Option<Tuple2<CompileState, string>> {
		let name = Strings/*auto*/.strip(rawName/*string*/);
		if (!Main/*auto*/.isSymbol(name/*string*/)) {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
		let outputContentTuple = Main/*auto*/.compileStatements(state/*CompileState*/.pushStructureName(name/*string*/), content/*string*/, Main/*auto*/.compileClassSegment);
		let outputContentState = outputContentTuple/*auto*/.left(/*auto*/).popStructureName(/*auto*/);
		let outputContent = outputContentTuple/*auto*/.right(/*auto*/);
		let platform = outputContentState/*auto*/.platform(/*auto*/);
		let constructorString = Main/*auto*/.generateConstructorFromRecordParameters(parameters/*List<Definition>*/, platform/*Platform*/);
		let joinedTypeParams = Joiner/*auto*/.joinOrEmpty(typeParams/*List<string>*/, ", ", "<", ">");
		let implementingString = Main/*auto*/.generateImplementing(maybeImplementing/*Option<Type>*/);
		let newModifiers = Main/*auto*/.modifyModifiers0(oldModifiers/*List<string>*/);
		let joinedModifiers = newModifiers/*auto*/.query(/*auto*/).map((value: string) => value/*&[I8]*/ + " ").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
		return Main/*auto*/.getTuple2Some(outputContentState/*auto*/.defineType(name/*string*/), annotations/*List<string>*/, infix/*string*/, parameters/*List<Definition>*/, maybeSuperType/*Option<string>*/, name/*string*/, joinedModifiers/*auto*/, joinedTypeParams/*auto*/, implementingString/*auto*/, platform/*Platform*/, constructorString/*auto*/, outputContent/*auto*/);
	}
	static getTuple2Some(state: CompileState, annotations: List<string>, infix: string, parameters: List<Definition>, maybeSuperType: Option<string>, name: string, joinedModifiers: string, joinedTypeParams: string, implementingString: string, platform: Platform, constructorString: string, outputContent: string): Some<Tuple2<CompileState, string>> {
		if (annotations/*List<string>*/.contains("Namespace", Strings/*auto*/.equalsTo)) {
			let actualInfix = "interface ";
			let newName = name/*string*/ + "Instance";
			let generated = joinedModifiers/*string*/ + actualInfix/*auto*/ + newName/*auto*/ + joinedTypeParams/*string*/ + implementingString/*string*/ + " {" + Main/*auto*/.joinParameters(parameters/*List<Definition>*/, platform/*Platform*/) + constructorString/*string*/ + outputContent/*string*/ + "\n}\n";
			let withNewLocation = state/*CompileState*/.append(generated/*auto*/).mapLocation((location: Location) => new Location(location/*auto*/.namespace(/*auto*/), location/*auto*/.name(/*auto*/) + "Instance"));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(withNewLocation/*auto*/, ""));
		}
		else {
			let extendsString = maybeSuperType/*Option<string>*/.map((inner: string) => " extends " + inner/*auto*/).orElse("");
			let infix1 = Main/*auto*/.retainStruct(infix/*string*/, state/*CompileState*/);
			let generated = joinedModifiers/*string*/ + infix1/*auto*/ + name/*string*/ + joinedTypeParams/*string*/ + extendsString/*auto*/ + implementingString/*string*/ + " {" + Main/*auto*/.joinParameters(parameters/*List<Definition>*/, platform/*Platform*/) + constructorString/*string*/ + outputContent/*string*/ + "\n}\n";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/.append(generated/*auto*/), ""));
		}
	}
	static retainStruct(infix: string, outputContentState: CompileState): string {
		if (outputContentState/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
			return "struct ";
		}
		return infix/*string*/;
	}
	static modifyModifiers0(oldModifiers: List<string>): List<string> {
		if (oldModifiers/*List<string>*/.contains("public", Strings/*auto*/.equalsTo)) {
			return Lists/*auto*/.of("export");
		}
		return Lists/*auto*/.empty(/*auto*/);
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing/*Option<Type>*/.map((type: Type) => type/*Type*/.generate(/*auto*/)).map((inner: string) => " implements " + inner/*auto*/).orElse("");
	}
	static generateConstructorFromRecordParameters(parameters: List<Definition>, platform: Platform): string {
		return parameters/*List<Definition>*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*Platform*/)).collect(new Joiner(", ")).map((generatedParameters: string) => Main/*auto*/.generateConstructorWithParameterString(parameters/*List<Definition>*/, generatedParameters/*auto*/)).orElse("");
	}
	static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: string): string {
		let constructorAssignments = Main/*auto*/.generateConstructorAssignments(parameters/*List<Definition>*/);
		return "\n\tconstructor (" + parametersString/*string*/ + ") {" + constructorAssignments/*auto*/ + "\n\t}";
	}
	static generateConstructorAssignments(parameters: List<Definition>): string {
		return parameters/*List<Definition>*/.query(/*auto*/).map((definition: Definition) => "\n\t\tthis." + definition/*auto*/.name(/*auto*/) + " = " + definition/*auto*/.name(/*auto*/) + ";").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
	}
	static joinParameters(parameters: List<Definition>, platform: Platform): string {
		return parameters/*List<Definition>*/.query(/*auto*/).map((definition: Definition) => definition/*auto*/.generate(platform/*Platform*/)).map((generated: string) => "\n\t" + generated/*auto*/ + ";").collect(Joiner/*auto*/.empty(/*auto*/)).orElse("");
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (stripped/*auto*/.startsWith("package ") || stripped/*auto*/.startsWith("import ")) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, ""));
		}
		return new None<>(/*auto*/);
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, rules/*List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>*/).orElseGet(() => new Tuple2Impl<CompileState, string>(state/*CompileState*/, Main/*auto*/.generatePlaceholder(input/*string*/)));
	}
	static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules/*List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>*/.query(/*auto*/).map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => Main/*auto*/.getApply(state/*CompileState*/, input/*string*/, rule/*auto*/)).flatMap(Queries/*auto*/.fromOption).next(/*auto*/);
	}
	static getApply<T>(state: CompileState, input: string, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>*/(state/*CompileState*/, input/*string*/);
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state1/*CompileState*/, input1/*string*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.createStructureRule("class ", "class "), Main/*auto*/.createStructureRule("interface ", "interface "), Main/*auto*/.createStructureRule("record ", "class "), Main/*auto*/.createStructureRule("enum ", "class "), Main/*auto*/.compileMethod, Main/*auto*/.compileFieldDefinition));
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(input/*string*/, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams = Strings/*auto*/.strip(beforeParams/*string*/);
			return Main/*auto*/.compileLast(strippedBeforeParams/*auto*/, " ", (_: string, name: string) => {
				if (state/*CompileState*/.hasLastStructureNameOf(name/*string*/)) {
					return Main/*auto*/.compileMethodWithBeforeParams(state/*CompileState*/, new ConstructorHeader(/*auto*/), withParams/*string*/);
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			}).or(() => {
				if (state/*CompileState*/.hasLastStructureNameOf(strippedBeforeParams/*auto*/)) {
					return Main/*auto*/.compileMethodWithBeforeParams(state/*CompileState*/, new ConstructorHeader(/*auto*/), withParams/*string*/);
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			}).or(() => Main/*auto*/.parseDefinition(state/*CompileState*/, beforeParams/*string*/).flatMap((tuple: Tuple2<CompileState, Definition>) => Main/*auto*/.compileMethodWithBeforeParams(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), tuple/*Tuple2<DivideState, string>*/.right(/*auto*/), withParams/*string*/)));
		});
	}
	static compileMethodWithBeforeParams<S extends FunctionHeader<S>>(state: CompileState, header: FunctionHeader<S>, withParams: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(withParams/*string*/, ")", (params: string, afterParams: string) => {
			let parametersTuple = Main/*auto*/.parseParameters(state/*CompileState*/, params/*string*/);
			let parametersState = parametersTuple/*auto*/.left(/*auto*/);
			let parameters = parametersTuple/*auto*/.right(/*auto*/);
			let definitions = Main/*auto*/.retainDefinitionsFromParameters(parameters/*List<Definition>*/);
			let newHeader = Main/*auto*/.retainDef(header/*FunctionHeader<S>*/, parametersState/*auto*/);
			if (newHeader/*auto*/.hasAnnotation("Actual")) {
				let sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new None<>(/*auto*/));
				let generate = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), "\n\t");
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState/*auto*/, generate/*auto*/));
			}
			return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(afterParams/*string*/), "{", (withoutContentStart: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(withoutContentStart/*auto*/), "}", (withoutContentEnd: string) => {
				let compileState1 = parametersState/*auto*/.enterDepth(/*auto*/);
				let compileState = /* compileState1.isPlatform(Platform.Windows) ? compileState1 : compileState1.enterDepth()*/;
				let statementsTuple = Main/*auto*/.compileFunctionStatements(compileState/*auto*/.defineAll(definitions/*List<Definition>*/), withoutContentEnd/*string*/);
				let compileState2 = statementsTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/);
				let indent = compileState2/*auto*/.createIndent(/*auto*/);
				let exited = /* compileState2.isPlatform(Platform.Windows) ? compileState2 : compileState2.exitDepth()*/;
				let sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new Some<>(statementsTuple/*auto*/.right(/*auto*/)));
				let generated = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), indent/*&[I8]*/);
				if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(exited/*auto*/.addFunction(generated/*auto*/), ""));
				}
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(exited/*auto*/, generated/*auto*/));
			})).or(() => {
				if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(afterParams/*string*/))) {
					let sFunctionSegment = new FunctionSegment<S>(newHeader/*auto*/, definitions/*List<Definition>*/, new None<>(/*auto*/));
					let generate = sFunctionSegment/*auto*/.generate(parametersState/*auto*/.platform(/*auto*/), "\n\t");
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState/*auto*/, generate/*auto*/));
				}
				return new None<Tuple2<CompileState, string>>(/*auto*/);
			});
		});
	}
	static retainDef<S extends FunctionHeader<S>>(header: FunctionHeader<S>, parametersState: CompileState): FunctionHeader<S> {
		if (parametersState/*CompileState*/.isPlatform(Platform/*auto*/.Magma)) {
			return header/*FunctionHeader<S>*/.addModifierLast("def").removeModifier("mut");
		}
		return header/*FunctionHeader<S>*/;
	}
	static parseParameters(state: CompileState, params: string): Tuple2<CompileState, List<Parameter>> {
		return Main/*auto*/.parseValuesOrEmpty(state/*CompileState*/, params/*string*/, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, Parameter>>(Main/*auto*/.parseParameterOrPlaceholder(state1/*CompileState*/, s/*string*/)));
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileStatements(state/*CompileState*/, input/*string*/, Main/*auto*/.compileFunctionSegment);
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state/*CompileState*/, input/*string*/, Lists/*auto*/.of(Main/*auto*/.compileWhitespace, Main/*auto*/.compileEmptySegment, Main/*auto*/.compileBlock, Main/*auto*/.compileFunctionStatement, Main/*auto*/.compileReturnWithoutSuffix));
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo(";", Strings/*auto*/.strip(input/*string*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, ";"));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileReturn(input1/*string*/, (withoutPrefix: string) => Main/*auto*/.compileValue(state1/*CompileState*/, withoutPrefix/*auto*/)).map((tuple: Tuple2<CompileState, string>) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), state1/*CompileState*/.createIndent(/*auto*/) + tuple/*Tuple2<DivideState, string>*/.right(/*auto*/)));
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), "}", (withoutEnd: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*auto*/, "", Main/*auto*/.foldBlockStarts), (beforeContentWithEnd: string, content: string) => Main/*auto*/.compileSuffix(beforeContentWithEnd/*auto*/, "{", (beforeContent: string) => Main/*auto*/.compileBlockHeader(state/*CompileState*/, beforeContent/*string*/).flatMap((headerTuple: Tuple2<CompileState, string>) => {
			let contentTuple = Main/*auto*/.compileFunctionStatements(headerTuple/*Tuple2<CompileState, string>*/.left(/*auto*/).enterDepth(/*auto*/), content/*string*/);
			let indent = state/*CompileState*/.createIndent(/*auto*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(contentTuple/*auto*/.left(/*auto*/).exitDepth(/*auto*/), indent/*&[I8]*/ + headerTuple/*Tuple2<CompileState, string>*/.right(/*auto*/) + "{" + contentTuple/*auto*/.right(/*auto*/) + indent/*&[I8]*/ + "}"));
		}))));
	}
	static foldBlockStarts(state: DivideState, c: string): DivideState {
		let appended = state/*DivideState*/.append(c/*string*/);
		if ("{" === c/*string*/) {
			let entered = appended/*auto*/.enter(/*auto*/);
			if (entered/*auto*/.isShallow(/*auto*/)) {
				return entered/*auto*/.advance(/*auto*/);
			}
			else {
				return entered/*auto*/;
			}
		}
		if ("}" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static compileBlockHeader(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, Lists/*auto*/.of(Main/*auto*/.createConditionalRule("if"), Main/*auto*/.createConditionalRule("while"), Main/*auto*/.compileElse));
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input1: string) => Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input1/*string*/), prefix/*string*/, (withoutPrefix: string) => {
			let strippedCondition = Strings/*auto*/.strip(withoutPrefix/*string*/);
			return Main/*auto*/.compilePrefix(strippedCondition/*auto*/, "(", (withoutConditionStart: string) => Main/*auto*/.compileSuffix(withoutConditionStart/*auto*/, ")", (withoutConditionEnd: string) => {
				let tuple = Main/*auto*/.compileValueOrPlaceholder(state1/*CompileState*/, withoutConditionEnd/*string*/);
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), prefix/*string*/ + " (" + tuple/*Tuple2<DivideState, string>*/.right(/*auto*/) + ") "));
			}));
		});
	}
	static compileElse(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo("else", Strings/*auto*/.strip(input/*string*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, "else "));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static compileFunctionStatement(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), ";", (withoutEnd: string) => {
			let valueTuple = Main/*auto*/.compileFunctionStatementValue(state/*CompileState*/, withoutEnd/*string*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple/*auto*/.left(/*auto*/), state/*CompileState*/.createIndent(/*auto*/) + valueTuple/*auto*/.right(/*auto*/) + ";"));
		});
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileOrPlaceholder(state/*CompileState*/, withoutEnd/*string*/, Lists/*auto*/.of(Main/*auto*/.compileReturnWithValue, Main/*auto*/.compileAssignment, (state1: CompileState, input: string) => Main/*auto*/.parseInvokable(state1/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), tuple/*Tuple2<DivideState, string>*/.right(/*auto*/).generate(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/).platform(/*auto*/)))), Main/*auto*/.createPostRule("++"), Main/*auto*/.createPostRule("--"), Main/*auto*/.compileBreak));
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings/*auto*/.equalsTo("break", Strings/*auto*/.strip(input/*string*/))) {
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state/*CompileState*/, "break"));
		}
		else {
			return new None<Tuple2<CompileState, string>>(/*auto*/);
		}
	}
	static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input: string) => Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), suffix/*string*/, (child: string) => {
			let tuple = Main/*auto*/.compileValueOrPlaceholder(state1/*CompileState*/, child/*string*/);
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), tuple/*Tuple2<DivideState, string>*/.right(/*auto*/) + suffix/*string*/));
		});
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileReturn(input/*string*/, (value1: string) => Main/*auto*/.compileValue(state/*CompileState*/, value1/*auto*/));
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*string*/), "return ", (value: string) => mapper/*(arg0 : string) => Option<Tuple2<CompileState, string>>*/(value/*&[I8]*/).flatMap((tuple: Tuple2<CompileState, string>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), "return " + tuple/*Tuple2<DivideState, string>*/.right(/*auto*/)))));
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), ")", (withoutEnd: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(withoutEnd/*string*/, "", Main/*auto*/.foldInvocationStarts), (callerWithArgStart: string, args: string) => Main/*auto*/.compileSuffix(callerWithArgStart/*auto*/, "(", (callerString: string) => Main/*auto*/.compilePrefix(Strings/*auto*/.strip(callerString/*auto*/), "new ", (type: string) => Main/*auto*/.compileType(state/*CompileState*/, type/*Type*/).flatMap((callerTuple: Tuple2<CompileState, string>) => {
			let callerState = callerTuple/*Tuple2<CompileState, string>*/.left(/*auto*/);
			let caller = callerTuple/*Tuple2<CompileState, string>*/.right(/*auto*/);
			return Main/*auto*/.assembleInvokable(callerState/*auto*/, new ConstructionCaller(caller/*auto*/, callerState/*auto*/.platform(/*auto*/)), args/*auto*/);
		})).or(() => Main/*auto*/.parseValue(state/*CompileState*/, callerString/*auto*/).flatMap((callerTuple: Tuple2<CompileState, Value>) => Main/*auto*/.assembleInvokable(callerTuple/*Tuple2<CompileState, string>*/.left(/*auto*/), callerTuple/*Tuple2<CompileState, string>*/.right(/*auto*/), args/*auto*/))))));
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return Main/*auto*/.splitFolded(input/*string*/, folder/*(arg0 : DivideState, arg1 : string) => DivideState*/, (divisions1: List<string>) => Main/*auto*/.selectLast(divisions1/*auto*/, delimiter/*string*/));
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple2<string, string>>): Option<Tuple2<string, string>> {
		let divisions = Main/*auto*/.divide(input/*string*/, folder/*(arg0 : DivideState, arg1 : string) => DivideState*/);
		if (2/*auto*/ > divisions/*auto*/.size(/*auto*/)) {
			return new None<Tuple2<string, string>>(/*auto*/);
		}
		return selector/*(arg0 : List<string>) => Option<Tuple2<string, string>>*/(divisions/*auto*/);
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let beforeLast = divisions/*List<string>*/.subList(0/*auto*/, divisions/*List<string>*/.size(/*auto*/) - 1/*auto*/).orElse(divisions/*List<string>*/);
		let last = divisions/*List<string>*/.findLast(/*auto*/).orElse("");
		let joined = beforeLast/*auto*/.query(/*auto*/).collect(new Joiner(delimiter/*string*/)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(joined/*auto*/, last/*auto*/));
	}
	static foldInvocationStarts(state: DivideState, c: string): DivideState {
		let appended = state/*DivideState*/.append(c/*string*/);
		if ("(" === c/*string*/) {
			let entered = appended/*auto*/.enter(/*auto*/);
			if (entered/*auto*/.isShallow(/*auto*/)) {
				return entered/*auto*/.advance(/*auto*/);
			}
			else {
				return entered/*auto*/;
			}
		}
		if (")" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.parseValues(state/*CompileState*/, argsString/*string*/, (state1: CompileState, s: string) => Main/*auto*/.parseArgument(state1/*CompileState*/, s/*string*/)).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState = argsTuple/*Tuple2<CompileState, List<Argument>>*/.left(/*auto*/);
			let args = Main/*auto*/.retain(argsTuple/*Tuple2<CompileState, List<Argument>>*/.right(/*auto*/), (argument: Argument) => argument/*auto*/.toValue(/*auto*/));
			let newCaller = Main/*auto*/.transformCaller(argsState/*auto*/, oldCaller/*Caller*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState/*auto*/, new InvokableNode(newCaller/*auto*/, args/*auto*/)));
		});
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller/*Caller*/.findChild(/*auto*/).flatMap((parent: Value) => {
			let parentType = parent/*Value*/.type(/*auto*/);
			if (parentType/*auto*/.isFunctional(/*auto*/)) {
				return new Some<Caller>(parent/*Value*/);
			}
			return new None<Caller>(/*auto*/);
		}).orElse(oldCaller/*Caller*/);
	}
	static retain<T, R>(args: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
		return args/*List<T>*/.query(/*auto*/).map(mapper/*(arg0 : T) => Option<R>*/).flatMap(Queries/*auto*/.fromOption).collect(new ListCollector<R>(/*auto*/));
	}
	static parseArgument(state1: CompileState, input: string): Option<Tuple2<CompileState, Argument>> {
		return Main/*auto*/.parseValue(state1/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple/*Tuple2<DivideState, string>*/.left(/*auto*/), tuple/*Tuple2<DivideState, string>*/.right(/*auto*/)));
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileFirst(input/*string*/, "=", (destination: string, source: string) => {
			let sourceTuple = Main/*auto*/.compileValueOrPlaceholder(state/*CompileState*/, source/*string*/);
			let destinationTuple = Main/*auto*/.compileValue(sourceTuple/*auto*/.left(/*auto*/), destination/*string*/).or(() => Main/*auto*/.parseDefinition(sourceTuple/*auto*/.left(/*auto*/), destination/*string*/).map((definitionTuple: Tuple2<CompileState, Definition>) => {
				let definitionState = definitionTuple/*Tuple2<CompileState, Definition>*/.left(/*auto*/);
				let definition = definitionTuple/*Tuple2<CompileState, Definition>*/.right(/*auto*/);
				let let = Main/*auto*/.attachLet(definitionState/*auto*/, definition/*auto*/);
				let generate = let/*auto*/.generate(definitionState/*auto*/.platform(/*auto*/));
				return new Tuple2Impl<CompileState, string>(definitionState/*auto*/, generate/*auto*/);
			})).orElseGet(() => new Tuple2Impl<CompileState, string>(sourceTuple/*auto*/.left(/*auto*/), Main/*auto*/.generatePlaceholder(destination/*string*/)));
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple/*auto*/.left(/*auto*/), destinationTuple/*auto*/.right(/*auto*/) + " = " + sourceTuple/*auto*/.right(/*auto*/)));
		});
	}
	static attachLet(definitionState: CompileState, definition: Definition): Definition {
		/*final Definition let*/;
		if (definitionState/*CompileState*/.isPlatform(Platform/*auto*/.Windows)) {
			let/*auto*/ = definition/*Definition*/;
		}
		else {
			let/*auto*/ = definition/*Definition*/.addModifierLast("let");
		}
		return let/*auto*/;
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main/*auto*/.compileValue(state/*CompileState*/, input/*string*/).orElseGet(() => new Tuple2Impl<CompileState, string>(state/*CompileState*/, Main/*auto*/.generatePlaceholder(input/*string*/)));
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseValue(state/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Value>) => {
			let generated = tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).generate(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/).platform(/*auto*/));
			return new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), generated/*auto*/);
		});
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, Lists/*auto*/.of(Main/*auto*/.parseLambda, Main/*auto*/.createOperatorRule("+"), Main/*auto*/.createOperatorRule("-"), Main/*auto*/.createOperatorRule("<="), Main/*auto*/.createOperatorRule("<"), Main/*auto*/.createOperatorRule("&&"), Main/*auto*/.createOperatorRule("||"), Main/*auto*/.createOperatorRule(">"), Main/*auto*/.createOperatorRule(">="), Main/*auto*/.parseInvokable, Main/*auto*/.createAccessRule("."), Main/*auto*/.createAccessRule("::"), Main/*auto*/.parseSymbol, Main/*auto*/.parseNot, Main/*auto*/.parseNumber, Main/*auto*/.createOperatorRuleWithDifferentInfix("==", "==="), Main/*auto*/.createOperatorRuleWithDifferentInfix("!=", "!=="), Main/*auto*/.createTextRule("\""), Main/*auto*/.createTextRule("'")));
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped = Strings/*auto*/.strip(input1/*string*/);
			return Main/*auto*/.compilePrefix(stripped/*auto*/, slice/*string*/, (s: string) => Main/*auto*/.compileSuffix(s/*string*/, slice/*string*/, (s1: string) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1/*CompileState*/, new StringNode(s1/*auto*/)))));
		};
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compilePrefix(Strings/*auto*/.strip(input/*string*/), "!", (withoutPrefix: string) => {
			let childTuple = Main/*auto*/.compileValueOrPlaceholder(state/*CompileState*/, withoutPrefix/*string*/);
			let childState = childTuple/*auto*/.left(/*auto*/);
			let child = "!" + childTuple/*auto*/.right(/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new NotNode(child/*string*/)));
		});
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.compileFirst(input/*string*/, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow = Strings/*auto*/.strip(beforeArrow/*string*/);
			return Main/*auto*/.compilePrefix(strippedBeforeArrow/*auto*/, "(", (withoutStart: string) => Main/*auto*/.compileSuffix(withoutStart/*auto*/, ")", (withoutEnd: string) => Main/*auto*/.parseValues(state/*CompileState*/, withoutEnd/*string*/, (state1: CompileState, s: string) => Main/*auto*/.parseParameter(state1/*CompileState*/, s/*string*/)).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => Main/*auto*/.compileLambdaWithParameterNames(paramNames/*auto*/.left(/*auto*/), Main/*auto*/.retainDefinitionsFromParameters(paramNames/*auto*/.right(/*auto*/)), afterArrow/*string*/))));
		});
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow = Strings/*auto*/.strip(afterArrow/*string*/);
		return Main/*auto*/.compilePrefix(strippedAfterArrow/*auto*/, "{", (withoutContentStart: string) => Main/*auto*/.compileSuffix(withoutContentStart/*auto*/, "}", (withoutContentEnd: string) => {
			let statementsTuple = Main/*auto*/.compileFunctionStatements(state/*CompileState*/.enterDepth(/*auto*/).defineAll(paramNames/*List<Definition>*/), withoutContentEnd/*string*/);
			let statementsState = statementsTuple/*auto*/.left(/*auto*/);
			let statements = statementsTuple/*auto*/.right(/*auto*/);
			let exited = statementsState/*CompileState*/.exitDepth(/*auto*/);
			let content = "{" + statements/*auto*/ + exited/*auto*/.createIndent(/*auto*/) + "}";
			if (exited/*auto*/.isPlatform(Platform/*auto*/.Windows)) {
				return Main/*auto*/.assembleLambdaWithContent(exited/*auto*/, paramNames/*List<Definition>*/, content/*string*/);
			}
			return Main/*auto*/.getSome(exited/*auto*/, paramNames/*List<Definition>*/, content/*string*/);
		})).or(() => Main/*auto*/.compileValue(state/*CompileState*/, strippedAfterArrow/*auto*/).flatMap((tuple: Tuple2<CompileState, string>) => {
			let state1 = tuple/*Tuple2<CompileState, string>*/.left(/*auto*/);
			let content = tuple/*Tuple2<CompileState, string>*/.right(/*auto*/);
			if (state1/*CompileState*/.isPlatform(Platform/*auto*/.Windows)) {
				return Main/*auto*/.assembleLambdaWithContent(state1/*CompileState*/, paramNames/*List<Definition>*/, "\n\treturn " + content/*string*/ + ";");
			}
			return Main/*auto*/.getSome(state1/*CompileState*/, paramNames/*List<Definition>*/, content/*string*/);
		}));
	}
	static getSome(state: CompileState, parameters: List<Definition>, content: string): Some<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/, new LambdaNode(parameters/*List<Definition>*/, content/*string*/)));
	}
	static assembleLambdaWithContent(state: CompileState, parameters: List<Definition>, content: string): Some<Tuple2<CompileState, Value>> {
		let lambdaDefinition = new Definition(PrimitiveType/*auto*/.Auto, state/*CompileState*/.functionName(/*auto*/));
		let value = new FunctionSegment<Definition>(lambdaDefinition/*auto*/, parameters/*List<Definition>*/, new Some<>(content/*string*/));
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/.addFunction(value/*&[I8]*/.generate(state/*CompileState*/.platform(/*auto*/), "\n")), new SymbolNode("lambdaDefinition", PrimitiveType/*auto*/.Auto)));
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return Main/*auto*/.createOperatorRuleWithDifferentInfix(infix/*string*/, infix/*string*/);
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state: CompileState, input: string) => Main/*auto*/.compileLast(input/*string*/, infix/*string*/, (childString: string, rawProperty: string) => {
			let property = Strings/*auto*/.strip(rawProperty/*string*/);
			if (!Main/*auto*/.isSymbol(property/*auto*/)) {
				return new None<Tuple2<CompileState, Value>>(/*auto*/);
			}
			return Main/*auto*/.parseValue(state/*CompileState*/, childString/*string*/).flatMap((childTuple: Tuple2<CompileState, Value>) => {
				let childState = childTuple/*Tuple2<CompileState, Value>*/.left(/*auto*/);
				let child = childTuple/*Tuple2<CompileState, Value>*/.right(/*auto*/);
				return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState/*auto*/, new AccessNode(child/*string*/, property/*auto*/)));
			});
		});
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFolded(input1/*string*/, Main/*auto*/.foldOperator(sourceInfix/*string*/), (divisions: List<string>) => Main/*auto*/.selectFirst(divisions/*List<string>*/, sourceInfix/*string*/)), (leftString: string, rightString: string) => Main/*auto*/.parseValue(state1/*CompileState*/, leftString/*auto*/).flatMap((leftTuple: Tuple2<CompileState, Value>) => Main/*auto*/.parseValue(leftTuple/*auto*/.left(/*auto*/), rightString/*auto*/).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
			let left = leftTuple/*auto*/.right(/*auto*/);
			let right = rightTuple/*Tuple2<CompileState, Value>*/.right(/*auto*/);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), new OperationNode(left/*auto*/, targetInfix/*string*/, right/*auto*/)));
		})));
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first = divisions/*List<string>*/.findFirst(/*auto*/).orElse("");
		let afterFirst = divisions/*List<string>*/.subList(1/*auto*/, divisions/*List<string>*/.size(/*auto*/)).orElse(divisions/*List<string>*/).query(/*auto*/).collect(new Joiner(delimiter/*string*/)).orElse("");
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(first/*auto*/, afterFirst/*auto*/));
	}
	static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state: DivideState, c: string) => {
			if (c/*string*/ === Strings/*auto*/.charAt(infix/*string*/, 0/*auto*/) && state/*DivideState*/.startsWith(Strings/*auto*/.sliceFrom(infix/*string*/, 1/*auto*/))) {
				let length = Strings/*auto*/.length(infix/*string*/) - 1/*auto*/;
				let counter = 0/*auto*/;
				let current = state/*DivideState*/;
				while (counter/*auto*/ < length/*number*/) {
					counter/*auto*/++;
					current/*Tuple2<CompileState, List<T>>*/ = current/*Tuple2<CompileState, List<T>>*/.pop(/*auto*/).map((tuple: Tuple2<DivideState, string>) => tuple/*Tuple2<CompileState, string>*/.left(/*auto*/)).orElse(current/*Tuple2<CompileState, List<T>>*/);
				}
				return current/*Tuple2<CompileState, List<T>>*/.advance(/*auto*/);
			}
			return state/*DivideState*/.append(c/*string*/);
		};
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (Main/*auto*/.isNumber(stripped/*auto*/)) {
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state/*CompileState*/, new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>(/*auto*/);
		}
	}
	static isNumber(input: string): boolean {
		let query = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*string*/)));
		return query/*auto*/.map((index: number) => input/*string*/.charAt(index/*auto*/)).allMatch((c: string) => Characters/*auto*/.isDigit(c/*string*/));
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
			let withImport = state/*CompileState*/.addResolvedImportFromCache(stripped/*auto*/);
			let symbolNode = new SymbolNode(stripped/*auto*/, state/*CompileState*/.resolve(stripped/*auto*/).orElse(PrimitiveType/*auto*/.Auto));
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport/*auto*/, symbolNode/*auto*/));
		}
		else {
			return new None<Tuple2<CompileState, Value>>(/*auto*/);
		}
	}
	static isSymbol(input: string): boolean {
		let query = new HeadedQuery<number>(new RangeHead(Strings/*auto*/.length(input/*string*/)));
		return query/*auto*/.allMatch((index: number) => Main/*auto*/.isSymbolChar(index/*auto*/, Strings/*auto*/.charAt(input/*string*/, index/*auto*/)));
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c/*string*/ || Characters/*auto*/.isLetter(c/*string*/) || /*auto*/(0/*auto*/ !== index/*number*/ && Characters/*auto*/.isDigit(c/*string*/));
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		if (!input/*string*/.startsWith(infix/*string*/)) {
			return new None<Tuple2<CompileState, T>>(/*auto*/);
		}
		let slice = Strings/*auto*/.sliceFrom(input/*string*/, Strings/*auto*/.length(infix/*string*/));
		return mapper/*(arg0 : string) => Option<Tuple2<CompileState, T>>*/(slice/*string*/);
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseWhitespace(state/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Whitespace>) => {
			let generate = tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/).generate(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).platform(/*auto*/));
			return new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), generate/*auto*/);
		});
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings/*auto*/.isBlank(input/*string*/)) {
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state/*CompileState*/, new Whitespace(/*auto*/)));
		}
		return new None<Tuple2<CompileState, Whitespace>>(/*auto*/);
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), ";", (withoutEnd: string) => Main/*auto*/.getTupleOption(state/*CompileState*/, withoutEnd/*string*/).or(() => Main/*auto*/.compileEnumValues(state/*CompileState*/, withoutEnd/*string*/)));
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseParameter(state/*CompileState*/, withoutEnd/*string*/).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => {
			let generate = "\n\t" + definitionTuple/*Tuple2<CompileState, Parameter>*/.right(/*auto*/).generate(definitionTuple/*Tuple2<CompileState, Parameter>*/.left(/*auto*/).platform(/*auto*/)) + ";";
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(definitionTuple/*Tuple2<CompileState, Parameter>*/.left(/*auto*/), generate/*auto*/));
		});
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseValues(state/*CompileState*/, withoutEnd/*string*/, (state1: CompileState, s: string) => Main/*auto*/.parseInvokable(state1/*CompileState*/, s/*string*/).flatMap((tuple: Tuple2<CompileState, Value>) => {
			let structureName = state/*CompileState*/.findLastStructureName(/*auto*/).orElse("");
			return tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).generateAsEnumValue(structureName/*&[I8]*/, state/*CompileState*/.platform(/*auto*/)).map((stringOption: string) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), stringOption/*auto*/));
		})).map((tuple: Tuple2<CompileState, List<string>>) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/).query(/*auto*/).collect(new Joiner("")).orElse("")));
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return Main/*auto*/.parseParameter(state/*CompileState*/, input/*string*/).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state/*CompileState*/, new Placeholder(input/*string*/)));
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return Main/*auto*/.parseWhitespace(state/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Whitespace>) => Main/*auto*/.getCompileStateParameterTuple2(tuple/*Tuple2<CompileState, Value>*/)).or(() => Main/*auto*/.parseDefinition(state/*CompileState*/, input/*string*/).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple/*Tuple2<CompileState, Value>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Value>*/.right(/*auto*/))));
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/));
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.compileLast(Strings/*auto*/.strip(input/*string*/), " ", (beforeName: string, name: string) => Main/*auto*/.compileSplit(Main/*auto*/.splitFoldedLast(Strings/*auto*/.strip(beforeName/*auto*/), " ", Main/*auto*/.foldTypeSeparators), (beforeType: string, type: string) => Main/*auto*/.compileLast(Strings/*auto*/.strip(beforeType/*auto*/), "\n", (annotationsString: string, afterAnnotations: string) => {
			let annotations = Main/*auto*/.parseAnnotations(annotationsString/*string*/);
			return Main/*auto*/.parseDefinitionWithAnnotations(state/*CompileState*/, annotations/*List<string>*/, afterAnnotations/*string*/, type/*Type*/, name/*string*/);
		}).or(() => Main/*auto*/.parseDefinitionWithAnnotations(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), beforeType/*auto*/, type/*Type*/, name/*string*/))).or(() => Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), Lists/*auto*/.empty(/*auto*/), beforeName/*auto*/, name/*string*/)));
	}
	static parseAnnotations(s: string): List<string> {
		return Main/*auto*/.divide(s/*string*/, (state1: DivideState, c: string) => Main/*auto*/.foldDelimited(state1/*CompileState*/, c/*string*/, "\n")).query(/*auto*/).map((s2: string) => Strings/*auto*/.strip(s2/*string*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*&[I8]*/)).filter((value: string) => 1/*auto*/ <= Strings/*auto*/.length(value/*&[I8]*/)).map((value: string) => Strings/*auto*/.sliceFrom(value/*&[I8]*/, 1/*auto*/)).map((s1: string) => Strings/*auto*/.strip(s1/*auto*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*&[I8]*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(beforeType/*string*/), ">", (withoutTypeParamEnd: string) => Main/*auto*/.compileFirst(withoutTypeParamEnd/*auto*/, "<", (beforeTypeParams: string, typeParamsString: string) => {
			let typeParams = Main/*auto*/.divideValues(typeParamsString/*string*/);
			return Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, annotations/*List<string>*/, typeParams/*List<string>*/, Main/*auto*/.parseModifiers(beforeTypeParams/*string*/), type/*string*/, name/*string*/);
		})).or(() => {
			let divided = Main/*auto*/.parseModifiers(beforeType/*string*/);
			return Main/*auto*/.parseDefinitionWithTypeParameters(state/*CompileState*/, annotations/*List<string>*/, Lists/*auto*/.empty(/*auto*/), divided/*auto*/, type/*string*/, name/*string*/);
		});
	}
	static parseModifiers(beforeType: string): List<string> {
		return Main/*auto*/.divide(Strings/*auto*/.strip(beforeType/*string*/), (state1: DivideState, c: string) => Main/*auto*/.foldDelimited(state1/*CompileState*/, c/*string*/, " ")).query(/*auto*/).map((s: string) => Strings/*auto*/.strip(s/*string*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*&[I8]*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter/*string*/ === c/*string*/) {
			return state1/*DivideState*/.advance(/*auto*/);
		}
		return state1/*DivideState*/.append(c/*string*/);
	}
	static divideValues(input: string): List<string> {
		return Main/*auto*/.divide(input/*string*/, Main/*auto*/.foldValues).query(/*auto*/).map((input1: string) => Strings/*auto*/.strip(input1/*string*/)).filter((value: string) => !Strings/*auto*/.isEmpty(value/*&[I8]*/)).collect(new ListCollector<string>(/*auto*/));
	}
	static foldTypeSeparators(state: DivideState, c: string): DivideState {
		if (" " === c/*string*/ && state/*DivideState*/.isLevel(/*auto*/)) {
			return state/*DivideState*/.advance(/*auto*/);
		}
		let appended = state/*DivideState*/.append(c/*string*/);
		if ("<" === c/*string*/) {
			return appended/*auto*/.enter(/*auto*/);
		}
		if (">" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, oldModifiers: List<string>, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main/*auto*/.parseType(state/*CompileState*/, type/*string*/).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = Main/*auto*/.modifyModifiers(oldModifiers/*List<string>*/, state/*CompileState*/.platform(/*auto*/));
			let generated = new Definition(annotations/*List<string>*/, newModifiers/*auto*/, typeParams/*List<string>*/, typeTuple/*Tuple2<CompileState, Type>*/.right(/*auto*/), name/*string*/);
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple/*Tuple2<CompileState, Type>*/.left(/*auto*/), generated/*auto*/));
		});
	}
	static modifyModifiers(oldModifiers: List<string>, platform: Platform): List<string> {
		let list = Main/*auto*/.retainFinal(oldModifiers/*List<string>*/, platform/*Platform*/);
		if (oldModifiers/*List<string>*/.contains("static", Strings/*auto*/.equalsTo)) {
			return list/*auto*/.addLast("static");
		}
		return list/*auto*/;
	}
	static retainFinal(oldModifiers: List<string>, platform: Platform): List<string> {
		if (oldModifiers/*List<string>*/.contains("final", Strings/*auto*/.equalsTo) || Platform/*auto*/.Magma !== platform/*Platform*/) {
			return Lists/*auto*/.empty(/*auto*/);
		}
		return Lists/*auto*/.of("mut");
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple2<CompileState, Type> {
		return Main/*auto*/.parseType(state/*CompileState*/, type/*string*/).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/))).orElseGet(() => new Tuple2Impl<CompileState, Type>(state/*CompileState*/, new Placeholder(type/*string*/)));
	}
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.parseType(state/*CompileState*/, type/*string*/).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, string>(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/).generate(/*auto*/)));
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.or(state/*CompileState*/, type/*string*/, Lists/*auto*/.of(Main/*auto*/.parseArrayType, Main/*auto*/.parseVarArgs, Main/*auto*/.parseGeneric, Main/*auto*/.parsePrimitive, Main/*auto*/.parseSymbolType));
	}
	static parseArrayType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		return Main/*auto*/.compileSuffix(stripped/*auto*/, "[]", (s: string) => {
			let child = Main/*auto*/.parseTypeOrPlaceholder(state/*CompileState*/, s/*string*/);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*string*/.left(/*auto*/), new ArrayType(child/*string*/.right(/*auto*/))));
		});
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		return Main/*auto*/.compileSuffix(stripped/*auto*/, "...", (s: string) => {
			let child = Main/*auto*/.parseTypeOrPlaceholder(state/*CompileState*/, s/*string*/);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child/*string*/.left(/*auto*/), new VariadicType(child/*string*/.right(/*auto*/))));
		});
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (Main/*auto*/.isSymbol(stripped/*auto*/)) {
			let symbolNode = new SymbolNode(stripped/*auto*/, PrimitiveType/*auto*/.Auto);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state/*CompileState*/.addResolvedImportFromCache(stripped/*auto*/), symbolNode/*auto*/));
		}
		return new None<Tuple2<CompileState, Type>>(/*auto*/);
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.findPrimitiveValue(Strings/*auto*/.strip(input/*string*/), state/*CompileState*/.platform(/*auto*/)).map((result: Type) => new Tuple2Impl<CompileState, Type>(state/*CompileState*/, result/*auto*/));
	}
	static findPrimitiveValue(input: string, platform: Platform): Option<Type> {
		let stripped = Strings/*auto*/.strip(input/*string*/);
		if (Strings/*auto*/.equalsTo("char", stripped/*auto*/) || Strings/*auto*/.equalsTo("Character", stripped/*auto*/)) {
			if (Platform/*auto*/.TypeScript === platform/*Platform*/) {
				return new Some<Type>(PrimitiveType/*auto*/.String);
			}
			else {
				return new Some<Type>(PrimitiveType/*auto*/.I8);
			}
		}
		if (Strings/*auto*/.equalsTo("String", stripped/*auto*/)) {
			if (Platform/*auto*/.TypeScript === platform/*Platform*/) {
				return new Some<Type>(PrimitiveType/*auto*/.String);
			}
			else {
				return new Some<Type>(new SliceType(PrimitiveType/*auto*/.I8));
			}
		}
		if (Strings/*auto*/.equalsTo("int", stripped/*auto*/) || Strings/*auto*/.equalsTo("Integer", stripped/*auto*/)) {
			if (Platform/*auto*/.Magma === platform/*Platform*/) {
				return new Some<Type>(PrimitiveType/*auto*/.I32);
			}
			else {
				return new Some<Type>(PrimitiveType/*auto*/.Number);
			}
		}
		if (Strings/*auto*/.equalsTo("boolean", stripped/*auto*/) || Strings/*auto*/.equalsTo("Boolean", stripped/*auto*/)) {
			return new Some<Type>(new BooleanType(platform/*Platform*/));
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
		return Main/*auto*/.compileSuffix(Strings/*auto*/.strip(input/*string*/), ">", (withoutEnd: string) => Main/*auto*/.compileFirst(withoutEnd/*string*/, "<", (baseString: string, argsString: string) => {
			let argsTuple = Main/*auto*/.parseValuesOrEmpty(state/*CompileState*/, argsString/*string*/, (state1: CompileState, s: string) => Main/*auto*/.compileTypeArgument(state1/*DivideState*/, s/*string*/));
			let argsState = argsTuple/*Tuple2<CompileState, List<Argument>>*/.left(/*auto*/);
			let args = argsTuple/*Tuple2<CompileState, List<Argument>>*/.right(/*auto*/);
			let base = Strings/*auto*/.strip(baseString/*string*/);
			return Main/*auto*/.assembleFunctionType(argsState/*auto*/, base/*&[I8]*/, args/*List<T>*/).or(() => {
				let compileState = argsState/*auto*/.addResolvedImportFromCache(base/*&[I8]*/);
				return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState/*auto*/, new TemplateType(base/*&[I8]*/, args/*List<T>*/)));
			});
		}));
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return Main/*auto*/.mapFunctionType(base/*string*/, args/*List<string>*/).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state/*CompileState*/, generated/*auto*/));
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if (Strings/*auto*/.equalsTo("Function", base/*string*/)) {
			return args/*List<string>*/.findFirst(/*auto*/).and(() => args/*List<string>*/.find(1/*auto*/)).map((tuple: Tuple2<string, string>) => new FunctionType(Lists/*auto*/.of(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/)), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/)));
		}
		if (Strings/*auto*/.equalsTo("BiFunction", base/*string*/)) {
			return args/*List<string>*/.find(0/*auto*/).and(() => args/*List<string>*/.find(1/*auto*/)).and(() => args/*List<string>*/.find(2/*auto*/)).map((tuple: Tuple2<Tuple2<string, string>, string>) => new FunctionType(Lists/*auto*/.of(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/).right(/*auto*/)), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/)));
		}
		if (Strings/*auto*/.equalsTo("Supplier", base/*string*/)) {
			return args/*List<string>*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.empty(/*auto*/), first/*auto*/));
		}
		if (Strings/*auto*/.equalsTo("Consumer", base/*string*/)) {
			return args/*List<string>*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.of(first/*auto*/), "void"));
		}
		if (Strings/*auto*/.equalsTo("Predicate", base/*string*/)) {
			return args/*List<string>*/.findFirst(/*auto*/).map((first: string) => new FunctionType(Lists/*auto*/.of(first/*auto*/), "boolean"));
		}
		return new None<Type>(/*auto*/);
	}
	static compileTypeArgument(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main/*auto*/.or(state/*CompileState*/, input/*string*/, Lists/*auto*/.of((state2: CompileState, input1: string) => Main/*auto*/.compileWhitespace(state2/*auto*/, input1/*string*/), (state1: CompileState, type: string) => Main/*auto*/.compileType(state1/*DivideState*/, type/*string*/)));
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return Main/*auto*/.parseValues(state/*CompileState*/, input/*string*/, mapper/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>*/).orElse(new Tuple2Impl<CompileState, List<T>>(state/*CompileState*/, Lists/*auto*/.empty(/*auto*/)));
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main/*auto*/.parseAll(state/*CompileState*/, input/*string*/, Main/*auto*/.foldValues, mapper/*(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>*/);
	}
	static foldValues(state: DivideState, c: string): DivideState {
		if ("," === c/*string*/ && state/*DivideState*/.isLevel(/*auto*/)) {
			return state/*DivideState*/.advance(/*auto*/);
		}
		let appended = state/*DivideState*/.append(c/*string*/);
		if ("-" === c/*string*/) {
			let peeked = appended/*auto*/.peek(/*auto*/);
			if (">" === peeked/*auto*/) {
				return appended/*auto*/.popAndAppendToOption(/*auto*/).orElse(appended/*auto*/);
			}
			else {
				return appended/*auto*/;
			}
		}
		if ("<" === c/*string*/ || "(" === c/*string*/) {
			return appended/*auto*/.enter(/*auto*/);
		}
		if (">" === c/*string*/ || ")" === c/*string*/) {
			return appended/*auto*/.exit(/*auto*/);
		}
		return appended/*auto*/;
	}
	static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileInfix(input/*string*/, infix/*string*/, Main/*auto*/.findLast, mapper/*(arg0 : string, arg1 : string) => Option<T>*/);
	}
	static findLast(input: string, infix: string): number {
		return input/*string*/.lastIndexOf(infix/*string*/);
	}
	static compileSuffix<T>(input: string, suffix: string, mapper: (arg0 : string) => Option<T>): Option<T> {
		if (!input/*string*/.endsWith(suffix/*string*/)) {
			return new None<T>(/*auto*/);
		}
		let length = Strings/*auto*/.length(input/*string*/);
		let length1 = Strings/*auto*/.length(suffix/*string*/);
		let content = Strings/*auto*/.sliceBetween(input/*string*/, 0/*auto*/, length/*number*/ - length1/*auto*/);
		return mapper/*(arg0 : string) => Option<T>*/(content/*string*/);
	}
	static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileInfix(input/*string*/, infix/*string*/, Main/*auto*/.findFirst, mapper/*(arg0 : string, arg1 : string) => Option<T>*/);
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main/*auto*/.compileSplit(Main/*auto*/.split(input/*string*/, infix/*string*/, locator/*(arg0 : string, arg1 : string) => number*/), mapper/*(arg0 : string, arg1 : string) => Option<T>*/);
	}
	static compileSplit<T>(splitter: Option<Tuple2<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter/*Option<Tuple2<string, string>>*/.flatMap((tuple: Tuple2<string, string>) => mapper/*(arg0 : string, arg1 : string) => Option<T>*/(tuple/*Tuple2<CompileState, Whitespace>*/.left(/*auto*/), tuple/*Tuple2<CompileState, Whitespace>*/.right(/*auto*/)));
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple2<string, string>> {
		let index = locator/*(arg0 : string, arg1 : string) => number*/(input/*string*/, infix/*string*/);
		if (0/*auto*/ > index/*number*/) {
			return new None<Tuple2<string, string>>(/*auto*/);
		}
		let left = Strings/*auto*/.sliceBetween(input/*string*/, 0/*auto*/, index/*number*/);
		let length = Strings/*auto*/.length(infix/*string*/);
		let right = Strings/*auto*/.sliceFrom(input/*string*/, index/*number*/ + length/*number*/);
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(left/*auto*/, right/*auto*/));
	}
	static findFirst(input: string, infix: string): number {
		return input/*string*/.indexOf(infix/*string*/);
	}
	static generatePlaceholder(input: string): string {
		let replaced = input/*string*/.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced/*auto*/ + "*/";
	}
}
Main.main();
/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Files: jvm.api.io, 
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	HeadedIter: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	Iter: magma.api.collect, 
	Iters: magma.api.collect, 
	Joiner: magma.api.collect, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Console: magma.api.io, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Err: magma.api.result, 
	Ok: magma.api.result, 
	Result: magma.api.result, 
	Characters: magma.api.text, 
	Strings: magma.api.text, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	CompileState: magma.app.compile, 
	ConstructionCaller: magma.app.compile.define, 
	ConstructorHeader: magma.app.compile.define, 
	Definition: magma.app.compile.define, 
	MethodHeader: magma.app.compile.define, 
	Parameter: magma.app.compile.define, 
	Dependency: magma.app.compile, 
	DivideState: magma.app.compile, 
	ImmutableCompileState: magma.app.compile, 
	ImmutableDivideState: magma.app.compile, 
	Import: magma.app.compile, 
	Placeholder: magma.app.compile.text, 
	Symbol: magma.app.compile.text, 
	Whitespace: magma.app.compile.text, 
	FunctionType: magma.app.compile.type, 
	PrimitiveType: magma.app.compile.type, 
	TemplateType: magma.app.compile.type, 
	Type: magma.app.compile.type, 
	VariadicType: magma.app.compile.type, 
	AccessValue: magma.app.compile.value, 
	Argument: magma.app.compile.value, 
	Caller: magma.app.compile.value, 
	Invokable: magma.app.compile.value, 
	Lambda: magma.app.compile.value, 
	Not: magma.app.compile.value, 
	Operation: magma.app.compile.value, 
	StringValue: magma.app.compile.value, 
	Value: magma.app.compile.value, 
	Source: magma.app.io, 
	Location: magma.app, 
	Main: magma.app, 
	Platform: magma.app
]*/
import { Files } from "../../jvm/api/io/Files";
import { IOError } from "../../magma/api/io/IOError";
import { Console } from "../../magma/api/io/Console";
import { CompileState } from "../../magma/app/compile/CompileState";
import { Result } from "../../magma/api/result/Result";
import { Path } from "../../magma/api/io/Path";
import { Iters } from "../../magma/api/collect/Iters";
import { Platform } from "../../magma/app/Platform";
import { List } from "../../magma/api/collect/list/List";
import { Source } from "../../magma/app/io/Source";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Err } from "../../magma/api/result/Err";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { Location } from "../../magma/app/Location";
import { Ok } from "../../magma/api/result/Ok";
import { Option } from "../../magma/api/option/Option";
import { None } from "../../magma/api/option/None";
import { Tuple2 } from "../../magma/api/Tuple2";
import { DivideState } from "../../magma/app/compile/DivideState";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Iter } from "../../magma/api/collect/Iter";
import { Strings } from "../../magma/api/text/Strings";
import { Type } from "../../magma/app/compile/type/Type";
import { Definition } from "../../magma/app/compile/define/Definition";
import { Parameter } from "../../magma/app/compile/define/Parameter";
import { ConstructorHeader } from "../../magma/app/compile/define/ConstructorHeader";
import { MethodHeader } from "../../magma/app/compile/define/MethodHeader";
import { Value } from "../../magma/app/compile/value/Value";
import { ConstructionCaller } from "../../magma/app/compile/define/ConstructionCaller";
import { Caller } from "../../magma/app/compile/value/Caller";
import { Argument } from "../../magma/app/compile/value/Argument";
import { Invokable } from "../../magma/app/compile/value/Invokable";
import { StringValue } from "../../magma/app/compile/value/StringValue";
import { Not } from "../../magma/app/compile/value/Not";
import { Lambda } from "../../magma/app/compile/value/Lambda";
import { AccessValue } from "../../magma/app/compile/value/AccessValue";
import { Operation } from "../../magma/app/compile/value/Operation";
import { Symbol } from "../../magma/app/compile/text/Symbol";
import { HeadedIter } from "../../magma/api/collect/head/HeadedIter";
import { RangeHead } from "../../magma/api/collect/head/RangeHead";
import { Characters } from "../../magma/api/text/Characters";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { Placeholder } from "../../magma/app/compile/text/Placeholder";
import { VariadicType } from "../../magma/app/compile/type/VariadicType";
import { PrimitiveType } from "../../magma/app/compile/type/PrimitiveType";
import { TemplateType } from "../../magma/app/compile/type/TemplateType";
import { FunctionType } from "../../magma/app/compile/type/FunctionType";
import { ImmutableCompileState } from "../../magma/app/compile/ImmutableCompileState";
import { ImmutableDivideState } from "../../magma/app/compile/ImmutableDivideState";
export class Main {
	static main(): void {
		let sourceDirectory = Files.get(".", "src", "java")/*unknown*/;
		Main.runWithSourceDirectory(sourceDirectory).findError().map((error: IOError) => error.display()/*unknown*/).ifPresent((displayed: string) => Console.printErrLn(displayed)/*unknown*/)/*unknown*/;
	}
	static runWithSourceDirectory(sourceDirectory: Path): Result<CompileState, IOError> {
		return Iters.fromArray(Platform.values()).foldWithInitialToResult(Main.createInitialCompileState(), (state: CompileState, platform: Platform) => {
			return sourceDirectory.walk().flatMapValue((children: List<Path>) => {
				return Main.runWithChildren(state.withPlatform(platform), children, sourceDirectory)/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static runWithChildren(state: CompileState, children: List<Path>, sourceDirectory: Path): Result<CompileState, IOError> {
		let initial = Main.retainSources(children, sourceDirectory).query().foldWithInitial(state, (current: CompileState, source: Source) => current.addSource(source)/*unknown*/)/*unknown*/;
		let folded = Main.retainSources(children, sourceDirectory).query().foldWithInitialToResult(initial, Main.runWithSource)/*unknown*/;
		if (/*state.hasPlatform(Platform.PlantUML) && folded instanceof Ok(var result)*/){
			let diagramPath = Files.get(".", "diagram.puml")/*unknown*/;
			let joinedDependencies = result.queryDependencies().map(dependency -  > dependency.name() + " --> " + dependency.child() + "\n").collect(new Joiner("")).orElse("")/*unknown*/;
			let maybeError = diagramPath.writeString("@startuml\n" + result.findOutput() + joinedDependencies + "@enduml")/*unknown*/;
			if (/*maybeError instanceof Some(var error)*/){
				return new Err<>(error)/*unknown*/;
			}
		}
		return folded/*unknown*/;
	}
	static retainSources(children: List<Path>, sourceDirectory: Path): List<Source> {
		return children.query().filter((source: Path) => source.endsWith(".java")/*unknown*/).map((child: Path) => new Source(sourceDirectory, child)/*unknown*/).collect(new ListCollector<Source>())/*unknown*/;
	}
	static runWithSource(state: CompileState, source: Source): Result<CompileState, IOError> {
		return source.read().flatMapValue((input: string) => Main.compileAndWrite(state, source, input)/*unknown*/)/*unknown*/;
	}
	static compileAndWrite(state: CompileState, source: Source, input: string): Result<CompileState, IOError> {
		let location = new Location(source.computeNamespace(), source.computeName())/*unknown*/;
		let compiled = Main.compileStatements(state.withLocation(location), input, Main.compileRootSegment)/*unknown*/;
		let compiledState = compiled.left()/*unknown*/;
		if (compiledState.hasPlatform(Platform.PlantUML)/*unknown*/){
			return new Ok<>(compiledState)/*unknown*/;
		}
		let segment = state.querySources().map((source1: Source) => Main.formatSource(source1)/*unknown*/).collect(new Joiner(", ")).orElse("")/*unknown*/;
		let joined = compiledState.join(compiled.right())/*unknown*/;
		let output = /* (Tuple2<CompileState, String>) new Tuple2Impl<CompileState, String>(state, "start[" + segment + "\n]end\n" + joined)*/;
		return Main.writeTarget(source, output.left().clearImports().clearOutput(), output.right())/*unknown*/;
	}
	static writeTarget(source: Source, state: CompileState, output: string): Result<CompileState, IOError> {
		let target = Files.get(".", "src", "ts").resolveChildSegments(source.computeNamespace()).resolveChild(source.computeName() + ".ts")/*unknown*/;
		return Main.writeTarget(target, output).orElseGet(() => new Ok<CompileState, IOError>(state)/*unknown*/)/*unknown*/;
	}
	static writeTarget(target: Path, output: string): Option<Result<CompileState, IOError>> {
		return Main.ensureTargetParent(target).or(() => target.writeString(output)/*unknown*/).map((error: IOError) => new Err<CompileState, IOError>(error)/*unknown*/)/*unknown*/;
	}
	static ensureTargetParent(target: Path): Option<IOError> {
		let parent = target.getParent()/*unknown*/;
		if (parent.exists()/*unknown*/){
			return new None<IOError>()/*unknown*/;
		}
		return parent.createDirectories()/*unknown*/;
	}
	static formatSource(source: Source): string {
		let joinedNamespace = source.computeNamespace().query().collect(new Joiner(".")).orElse("")/*unknown*/;
		return "\n\t" + source.computeName() + ": " + joinedNamespace/*unknown*/;
	}
	static compileStatements(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>): Tuple2<CompileState, string> {
		return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements)/*unknown*/;
	}
	static compileAll(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, mapper: (arg0 : CompileState, arg1 : string) => Tuple2<CompileState, string>, merger: (arg0 : string, arg1 : string) => string): Tuple2<CompileState, string> {
		let folded = Main.parseAll(state, input, folder, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, string>>(mapper(state1, s))/*unknown*/).orElse(new Tuple2Impl<CompileState, List<string>>(state, Lists.empty()))/*unknown*/;
		return new Tuple2Impl<CompileState, string>(folded.left(), Main.generateAll(folded.right(), merger))/*unknown*/;
	}
	static generateAll(elements: List<string>, merger: (arg0 : string, arg1 : string) => string): string {
		return elements.query().foldWithInitial("", merger)/*unknown*/;
	}
	static parseAll<T>(state: CompileState, input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, biFunction: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main.divide(input, folder).foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: string) => maybeCurrent.flatMap((current: Tuple2<CompileState, List<T>>) => {
			let currentState = current.left()/*unknown*/;
			let currentElement = current.right()/*unknown*/;
			return biFunction(currentState, segment).map((mappedTuple: Tuple2<CompileState, T>) => {
				let mappedState = mappedTuple.left()/*unknown*/;
				let mappedElement = mappedTuple.right()/*unknown*/;
				return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement))/*unknown*/;
			})/*unknown*/;
		})/*unknown*/)/*unknown*/;
	}
	static mergeStatements(cache: string, element: string): string {
		return cache + element/*unknown*/;
	}
	static divide(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Iter<string> {
		let current = Main.createInitialDivideState(input)/*unknown*/;
		while (true/*unknown*/){
			let poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, string>(current, "\0"))/*unknown*/;
			if (!poppedTuple0/*unknown*/.left()/*unknown*/){
				break;
			}
			let poppedTuple = poppedTuple0.right()/*unknown*/;
			let poppedState = poppedTuple.left()/*unknown*/;
			let popped = poppedTuple.right()/*unknown*/;
			current/*Tuple2<CompileState, List<T>>*/ = Main.foldSingleQuotes(poppedState, popped).or(() => Main.foldDoubleQuotes(poppedState, popped)/*unknown*/).orElseGet(() => folder(poppedState, popped)/*unknown*/)/*unknown*/;
		}
		return current.advance().query()/*unknown*/;
	}
	static foldDoubleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\"" !== c/*unknown*/){
			return new None<DivideState>()/*unknown*/;
		}
		let appended = state.append(c)/*unknown*/;
		while (true/*unknown*/){
			let maybeTuple = appended.popAndAppendToTuple().toTuple(new Tuple2Impl<DivideState, string>(appended, "\0"))/*unknown*/;
			if (!maybeTuple/*unknown*/.left()/*unknown*/){
				break;
			}
			let tuple = maybeTuple.right()/*unknown*/;
			appended/*unknown*/ = tuple.left()/*unknown*/;
			if ("\\" === tuple.right()/*unknown*/){
				appended/*unknown*/ = appended.popAndAppendToOption().orElse(appended)/*unknown*/;
			}
			if ("\"" === tuple.right()/*unknown*/){
				break;
			}
		}
		return new Some<DivideState>(appended)/*unknown*/;
	}
	static foldSingleQuotes(state: DivideState, c: string): Option<DivideState> {
		if ("\'" !== c/*unknown*/){
			return new None<DivideState>()/*unknown*/;
		}
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap((state1: DivideState) => state1.popAndAppendToOption()/*unknown*/)/*unknown*/;
	}
	static foldEscaped(tuple: Tuple2<DivideState, string>): Option<DivideState> {
		let state = tuple.left()/*unknown*/;
		let c = tuple.right()/*unknown*/;
		if ("\\" === c/*unknown*/){
			return state.popAndAppendToOption()/*unknown*/;
		}
		return new Some<DivideState>(state)/*unknown*/;
	}
	static foldStatements(state: DivideState, c: string): DivideState {
		let appended = state.append(c)/*unknown*/;
		if (";" === c && appended.isLevel()/*unknown*/){
			return appended.advance()/*unknown*/;
		}
		if ("}" === c && appended.isShallow()/*unknown*/){
			return appended.advance().exit()/*unknown*/;
		}
		if ("{" === c || "(" === c/*unknown*/){
			return appended.enter()/*unknown*/;
		}
		if ("}" === c || ")" === c/*unknown*/){
			return appended.exit()/*unknown*/;
		}
		return appended/*unknown*/;
	}
	static compileRootSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class ")))/*unknown*/;
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state: CompileState, input1: string) => Main.compileFirst(input1, sourceInfix, (beforeInfix: string, afterInfix: string) => Main.compileFirst(afterInfix, "{", (beforeContent: string, withEnd: string) => Main.compileSuffix(Strings.strip(withEnd), "}", (inputContent: string) => Main.compileLast(beforeInfix, "\n", (s: string, s2: string) => {
			let annotations = Main.parseAnnotations(s)/*unknown*/;
			if (annotations.contains("Actual")/*unknown*/){
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""))/*unknown*/;
			}
			return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent)/*unknown*/;
		}).or(() => {
			let modifiers = Main.parseModifiers(beforeContent)/*unknown*/;
			return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent)/*unknown*/;
		})/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*//*unknown*/;
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return Main.compileLast(beforeContent, " implements ", (s: string, s2: string) => Main.parseType(state, s2).flatMap((implementingTuple: Tuple2<CompileState, Type>) => Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content)/*unknown*/)/*unknown*/).or(() => Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content)/*unknown*/)/*unknown*/;
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(beforeContent, " extends ", (beforeExtends: string, afterExtends: string) => Main.parseValues(state, afterExtends, (inner0: CompileState, inner1: string) => Main.parseType(inner0, inner1)/*unknown*/).flatMap((compileStateListTuple2: Tuple2<CompileState, List<Type>>) => Main.compileStructureWithParameters(compileStateListTuple2.left(), annotations, modifiers, targetInfix, beforeExtends, compileStateListTuple2.right(), maybeImplementing, inputContent)/*unknown*/)/*unknown*/).or(() => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, Lists.empty(), maybeImplementing, inputContent)/*unknown*/)/*unknown*/;
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: List<Type>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(beforeContent, "(", (rawName: string, withParameters: string) => Main.compileFirst(withParameters, ")", (parametersString: string, _: string) => {
			let name = Strings.strip(rawName)/*unknown*/;
			let parametersTuple = Main.parseParameters(state, parametersString)/*unknown*/;
			let parameters = Main.retainDefinitionsFromParameters(parametersTuple.right())/*unknown*/;
			return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType)/*unknown*/;
		})/*unknown*/).or(() => Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType)/*unknown*/)/*unknown*/;
	}
	static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters.query().map((parameter: Parameter) => parameter.asDefinition()/*unknown*/).flatMap(Iters.fromOption).collect(new ListCollector<Definition>())/*unknown*/;
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: List<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: List<Type>): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(beforeParams), ">", (withoutTypeParamEnd: string) => Main.compileFirst(withoutTypeParamEnd, "<", (name: string, typeParamsString: string) => {
			let typeParams = Main.divideValues(typeParamsString)/*unknown*/;
			return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType)/*unknown*/;
		})/*unknown*/).or(() => Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType)/*unknown*/)/*unknown*/;
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: List<string>, parameters: List<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: List<Type>): Option<Tuple2<CompileState, string>> {
		let name = Strings.strip(rawName)/*unknown*/;
		if (!Main/*unknown*/.isSymbol(name)/*unknown*/){
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
		let outputContentTuple = Main.compileStatements(state.pushStructureName(name), content, Main.compileClassSegment)/*unknown*/;
		let outputContentState = outputContentTuple.left().popStructureName()/*unknown*/;
		let outputContent = outputContentTuple.right()/*unknown*/;
		let constructorString = Main.generateConstructorFromRecordParameters(parameters)/*unknown*/;
		let joinedTypeParams = Main.joinTypeParams(typeParams)/*unknown*/;
		let implementingString = Main.generateImplementing(maybeImplementing)/*unknown*/;
		let newModifiers = Main.modifyModifiers0(oldModifiers)/*unknown*/;
		let joinedModifiers = newModifiers.query().map((value: string) => value + " "/*unknown*/).collect(Joiner.empty()).orElse("")/*unknown*/;
		if (outputContentState.hasPlatform(Platform.PlantUML)/*unknown*/){
			let joinedImplementing = maybeImplementing.map((type: Type) => type.generateSimple()/*unknown*/).map((generated: string) => name + " <|.. " + generated + "\n"/*unknown*/).orElse("")/*unknown*/;
			let joinedSuperTypes = maybeSuperType.query().map((type: Type) => type.generateSimple()/*unknown*/).map((generated: string) => name + " <|-- " + generated + "\n"/*unknown*/).collect(new Joiner("")).orElse("")/*unknown*/;
			let generated = infix + name + joinedTypeParams + " {\n}\n" + joinedSuperTypes + joinedImplementing/*unknown*/;
			return new Some<>(new Tuple2Impl<>(outputContentState.append(generated), ""))/*unknown*/;
		}
		if (annotations.contains("Namespace")/*unknown*/){
			let actualInfix: string = "interface "/*unknown*/;
			let newName: string = name + "Instance"/*unknown*/;
			let generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n"/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.append(generated).append("export declare const " + name + ": " + newName + ";\n"), ""))/*unknown*/;
		}
		else {
			let extendsString = Main.joinExtends(maybeSuperType)/*unknown*/;
			let generated = joinedModifiers + infix + name + joinedTypeParams + extendsString + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n"/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.append(generated), ""))/*unknown*/;
		}
	}
	static joinExtends(maybeSuperType: List<Type>): string {
		return maybeSuperType.query().map((type: Type) => type.generate()/*unknown*/).collect(new Joiner(", ")).map((inner: string) => " extends " + inner/*unknown*/).orElse("")/*unknown*/;
	}
	static modifyModifiers0(oldModifiers: List<string>): List<string> {
		if (oldModifiers.contains("public")/*unknown*/){
			return Lists.of("export")/*unknown*/;
		}
		return Lists.empty()/*unknown*/;
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing.map((type: Type) => type.generate()/*unknown*/).map((inner: string) => " implements " + inner/*unknown*/).orElse("")/*unknown*/;
	}
	static joinTypeParams(typeParams: List<string>): string {
		return typeParams.query().collect(new Joiner(", ")).map((inner: string) => "<" + inner + ">"/*unknown*/).orElse("")/*unknown*/;
	}
	static generateConstructorFromRecordParameters(parameters: List<Definition>): string {
		return parameters.query().map((definition: Definition) => definition.generate()/*unknown*/).collect(new Joiner(", ")).map((generatedParameters: string) => Main.generateConstructorWithParameterString(parameters, generatedParameters)/*unknown*/).orElse("")/*unknown*/;
	}
	static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: string): string {
		let constructorAssignments = Main.generateConstructorAssignments(parameters)/*unknown*/;
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}"/*unknown*/;
	}
	static generateConstructorAssignments(parameters: List<Definition>): string {
		return parameters.query().map((definition: Definition) => definition.toAssignment()/*unknown*/).collect(Joiner.empty()).orElse("")/*unknown*/;
	}
	static joinParameters(parameters: List<Definition>): string {
		return parameters.query().map((definition: Definition) => definition.generate()/*unknown*/).map((generated: string) => "\n\t" + generated + ";"/*unknown*/).collect(Joiner.empty()).orElse("")/*unknown*/;
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (stripped.startsWith("package ") || stripped.startsWith("import ")/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""))/*unknown*/;
		}
		return new None<Tuple2<CompileState, string>>()/*unknown*/;
	}
	static compileOrPlaceholder(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return Main.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Main.generatePlaceholder(input))/*unknown*/)/*unknown*/;
	}
	static or<T>(state: CompileState, input: string, rules: List<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules.query().map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => Main.getApply(state, input, rule)/*unknown*/).flatMap(Iters.fromOption).next()/*unknown*/;
	}
	static getApply<T>(state: CompileState, input: string, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule(state, input)/*unknown*/;
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class "), Main.compileMethod, Main.compileFieldDefinition))/*unknown*/;
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(input, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams = Strings.strip(beforeParams)/*unknown*/;
			return Main.compileLast(strippedBeforeParams, " ", (_: string, name: string) => {
				if (state.isLastWithin(name)/*unknown*/){
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams)/*unknown*/;
				}
				return new None<Tuple2<CompileState, string>>()/*unknown*/;
			}).or(() => {
				if (state.findLastStructureName().filter((anObject: string) => Strings.equalsTo(strippedBeforeParams, anObject)/*unknown*/).isPresent()/*unknown*/){
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams)/*unknown*/;
				}
				return new None<Tuple2<CompileState, string>>()/*unknown*/;
			}).or(() => Main.parseDefinition(state, beforeParams).flatMap((tuple: Tuple2<CompileState, Definition>) => Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)/*unknown*/)/*unknown*/)/*unknown*/;
		})/*unknown*/;
	}
	static compileMethodWithBeforeParams(state: CompileState, header: MethodHeader, withParams: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(withParams, ")", (params: string, afterParams: string) => {
			let parametersTuple = Main.parseParameters(state, params)/*unknown*/;
			let parametersState = parametersTuple.left()/*unknown*/;
			let parameters = parametersTuple.right()/*unknown*/;
			let definitions = Main.retainDefinitionsFromParameters(parameters)/*unknown*/;
			let joinedDefinitions = definitions.query().map((definition: Definition) => definition.generate()/*unknown*/).collect(new Joiner(", ")).orElse("")/*unknown*/;
			if (header.hasAnnotation("Actual")/*unknown*/){
				let headerGenerated = header.removeModifier("static").generateWithAfterName("(" + joinedDefinitions + ")")/*unknown*/;
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";\n"))/*unknown*/;
			}
			let headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")")/*unknown*/;
			return Main.compilePrefix(Strings.strip(afterParams), "{", (withoutContentStart: string) => Main.compileSuffix(Strings.strip(withoutContentStart), "}", (withoutContentEnd: string) => {
				let statementsTuple = Main.compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd)/*unknown*/;
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(statementsTuple.left().exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right() + "\n\t}"))/*unknown*/;
			})/*unknown*/).or(() => {
				if (Strings.equalsTo(";", Strings.strip(afterParams))/*unknown*/){
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";"))/*unknown*/;
				}
				return new None<Tuple2<CompileState, string>>()/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static parseParameters(state: CompileState, params: string): Tuple2<CompileState, List<Parameter>> {
		return Main.parseValuesOrEmpty(state, params, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s))/*unknown*/)/*unknown*/;
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileStatements(state, input, Main.compileFunctionSegment)/*unknown*/;
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix))/*unknown*/;
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo(";", Strings.strip(input))/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ";"))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple2<CompileState, string>> {
		return Main.compileReturn(input1, (withoutPrefix: string) => Main.compileValue(state1, withoutPrefix)/*unknown*/).map((tuple: Tuple2<CompileState, string>) => new Tuple2Impl<CompileState, string>(tuple.left(), state1.createIndent() + tuple.right())/*unknown*/)/*unknown*/;
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), "}", (withoutEnd: string) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd: string, content: string) => Main.compileSuffix(beforeContentWithEnd, "{", (beforeContent: string) => Main.compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple2<CompileState, string>) => {
			let contentTuple = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content)/*unknown*/;
			let indent = state.createIndent()/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"))/*unknown*/;
		})/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static foldBlockStarts(state: DivideState, c: string): DivideState {
		let appended = state.append(c)/*unknown*/;
		if ("{" === c/*unknown*/){
			let entered = appended.enter()/*unknown*/;
			if (entered.isShallow()/*unknown*/){
				return entered.advance()/*unknown*/;
			}
			else {
				return entered/*unknown*/;
			}
		}
		if ("}" === c/*unknown*/){
			return appended.exit()/*unknown*/;
		}
		return appended/*unknown*/;
	}
	static compileBlockHeader(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.or(state, input, Lists.of(Main.createConditionalRule("if"), Main.createConditionalRule("while"), Main.compileElse))/*unknown*/;
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input1: string) => Main.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: string) => {
			let strippedCondition = Strings.strip(withoutPrefix)/*unknown*/;
			return Main.compilePrefix(strippedCondition, "(", (withoutConditionStart: string) => Main.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: string) => {
				let tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd)/*unknown*/;
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), prefix + " (" + tuple.right() + ")"))/*unknown*/;
			})/*unknown*/)/*unknown*/;
		})/*unknown*//*unknown*/;
	}
	static compileElse(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo("else", Strings.strip(input))/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, "else "))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
	}
	static compileFunctionStatement(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			let valueTuple = Main.compileFunctionStatementValue(state, withoutEnd)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"))/*unknown*/;
		})/*unknown*/;
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (state1: CompileState, input: string) => Main.parseInvokable(state1, input).map((tuple: Tuple2<CompileState, Value>) => Main.generateValue(tuple)/*unknown*/)/*unknown*/, Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak))/*unknown*/;
	}
	static generateValue(tuple: Tuple2<CompileState, Value>): Tuple2Impl<CompileState, string> {
		let state = tuple.left()/*unknown*/;
		let right = tuple.right()/*unknown*/;
		let generated = right.generate()/*unknown*/;
		let s = Main.generatePlaceholder(right.resolve(state).generate())/*unknown*/;
		return new Tuple2Impl<CompileState, string>(state, generated + s)/*unknown*/;
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo("break", Strings.strip(input))/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, "break"))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
	}
	static createPostRule(suffix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input: string) => Main.compileSuffix(Strings.strip(input), suffix, (child: string) => {
			let tuple = Main.compileValueOrPlaceholder(state1, child)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right() + suffix))/*unknown*/;
		})/*unknown*//*unknown*/;
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileReturn(input, (value1: string) => Main.compileValue(state, value1)/*unknown*/)/*unknown*/;
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return Main.compilePrefix(Strings.strip(input), "return ", (value: string) => mapper(value).flatMap((tuple: Tuple2<CompileState, string>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), "return " + tuple.right()))/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compileSuffix(Strings.strip(input), ")", (withoutEnd: string) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart: string, args: string) => Main.compileSuffix(callerWithArgStart, "(", (callerString: string) => Main.compilePrefix(Strings.strip(callerString), "new ", (type: string) => Main.compileType(state, type).flatMap((callerTuple: Tuple2<CompileState, string>) => {
			let callerState = callerTuple.right()/*unknown*/;
			let caller = callerTuple.left()/*unknown*/;
			return Main.assembleInvokable(caller, new ConstructionCaller(callerState), args)/*unknown*/;
		})/*unknown*/).or(() => Main.parseValue(state, callerString).flatMap((callerTuple: Tuple2<CompileState, Value>) => Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return Main.splitFolded(input, folder, (divisions1: List<string>) => Main.selectLast(divisions1, delimiter)/*unknown*/)/*unknown*/;
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple2<string, string>>): Option<Tuple2<string, string>> {
		let divisions = Main.divide(input, folder).collect(new ListCollector<string>())/*unknown*/;
		if (2 > divisions.size()/*unknown*/){
			return new None<Tuple2<string, string>>()/*unknown*/;
		}
		return selector(divisions)/*unknown*/;
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions)/*unknown*/;
		let last = divisions.findLast().orElse("")/*unknown*/;
		let joined = beforeLast.query().collect(new Joiner(delimiter)).orElse("")/*unknown*/;
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(joined, last))/*unknown*/;
	}
	static foldInvocationStarts(state: DivideState, c: string): DivideState {
		let appended = state.append(c)/*unknown*/;
		if ("(" === c/*unknown*/){
			let entered = appended.enter()/*unknown*/;
			if (entered.isShallow()/*unknown*/){
				return entered.advance()/*unknown*/;
			}
			else {
				return entered/*unknown*/;
			}
		}
		if (")" === c/*unknown*/){
			return appended.exit()/*unknown*/;
		}
		return appended/*unknown*/;
	}
	static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: string): Option<Tuple2<CompileState, Value>> {
		return Main.parseValues(state, argsString, (state1: CompileState, s: string) => Main.parseArgument(state1, s)/*unknown*/).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState = argsTuple.left()/*unknown*/;
			let args = Main.retain(argsTuple.right(), (argument: Argument) => argument.toValue()/*unknown*/)/*unknown*/;
			let newCaller = Main.transformCaller(argsState, oldCaller)/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)))/*unknown*/;
		})/*unknown*/;
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller.findChild().flatMap((parent: Value) => {
			let parentType = parent.resolve(state)/*unknown*/;
			if (parentType.isFunctional()/*unknown*/){
				return new Some<Caller>(parent)/*unknown*/;
			}
			return new None<Caller>()/*unknown*/;
		}).orElse(oldCaller)/*unknown*/;
	}
	static retain<T, R>(args: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
		return args.query().map(mapper).flatMap(Iters.fromOption).collect(new ListCollector<R>())/*unknown*/;
	}
	static parseArgument(state1: CompileState, input: string): Option<Tuple2<CompileState, Argument>> {
		return Main.parseValue(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right())/*unknown*/)/*unknown*/;
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileFirst(input, "=", (destination: string, source: string) => {
			let sourceTuple = Main.compileValueOrPlaceholder(state, source)/*unknown*/;
			let destinationTuple = Main.compileValue(sourceTuple.left(), destination).or(() => Main.parseDefinition(sourceTuple.left(), destination).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, string>(tuple.left(), "let " + tuple.right().generate())/*unknown*/)/*unknown*/).orElseGet(() => new Tuple2Impl<CompileState, string>(sourceTuple.left(), Main.generatePlaceholder(destination))/*unknown*/)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()))/*unknown*/;
		})/*unknown*/;
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Main.compileValue(state, input).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Main.generatePlaceholder(input))/*unknown*/)/*unknown*/;
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.parseValue(state, input).map((tuple: Tuple2<CompileState, Value>) => Main.generateValue(tuple)/*unknown*/)/*unknown*/;
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")))/*unknown*/;
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped = Strings.strip(input1)/*unknown*/;
			return Main.compilePrefix(stripped, slice, (s: string) => Main.compileSuffix(s, slice, (s1: string) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1)))/*unknown*/)/*unknown*/)/*unknown*/;
		}/*unknown*/;
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compilePrefix(Strings.strip(input), "!", (withoutPrefix: string) => {
			let childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix)/*unknown*/;
			let childState = childTuple.left()/*unknown*/;
			let child = "!" + childTuple.right()/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)))/*unknown*/;
		})/*unknown*/;
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Main.compileFirst(input, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow = Strings.strip(beforeArrow)/*unknown*/;
			return Main.compilePrefix(strippedBeforeArrow, "(", (withoutStart: string) => Main.compileSuffix(withoutStart, ")", (withoutEnd: string) => Main.parseValues(state, withoutEnd, (state1: CompileState, s: string) => Main.parseParameter(state1, s)/*unknown*/).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/;
		})/*unknown*/;
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow = Strings.strip(afterArrow)/*unknown*/;
		return Main.compilePrefix(strippedAfterArrow, "{", (withoutContentStart: string) => Main.compileSuffix(withoutContentStart, "}", (withoutContentEnd: string) => {
			let statementsTuple = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd)/*unknown*/;
			let statementsState = statementsTuple.left()/*unknown*/;
			let statements = statementsTuple.right()/*unknown*/;
			let exited = statementsState.exitDepth()/*unknown*/;
			return Main.assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}")/*unknown*/;
		})/*unknown*/).or(() => Main.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple2<CompileState, string>) => Main.assembleLambda(tuple.left(), paramNames, tuple.right())/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static assembleLambda(exited: CompileState, paramNames: List<Definition>, content: string): Option<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)))/*unknown*/;
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return Main.createOperatorRuleWithDifferentInfix(infix, infix)/*unknown*/;
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state: CompileState, input: string) => Main.compileLast(input, infix, (childString: string, rawProperty: string) => {
			let property = Strings.strip(rawProperty)/*unknown*/;
			if (!Main/*unknown*/.isSymbol(property)/*unknown*/){
				return new None<Tuple2<CompileState, Value>>()/*unknown*/;
			}
			return Main.parseValue(state, childString).flatMap((childTuple: Tuple2<CompileState, Value>) => {
				let childState = childTuple.left()/*unknown*/;
				let child = childTuple.right()/*unknown*/;
				return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessValue(child, property)))/*unknown*/;
			})/*unknown*/;
		})/*unknown*//*unknown*/;
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (divisions: List<string>) => Main.selectFirst(divisions, sourceInfix)/*unknown*/), (leftString: string, rightString: string) => Main.parseValue(state1, leftString).flatMap((leftTuple: Tuple2<CompileState, Value>) => Main.parseValue(leftTuple.left(), rightString).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
			let left = leftTuple.right()/*unknown*/;
			let right = rightTuple.right()/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)))/*unknown*/;
		})/*unknown*/)/*unknown*/)/*unknown*//*unknown*/;
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first = divisions.findFirst().orElse("")/*unknown*/;
		let afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("")/*unknown*/;
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(first, afterFirst))/*unknown*/;
	}
	static foldOperator(infix: string): (arg0 : DivideState, arg1 : string) => DivideState {
		return (state: DivideState, c: string) => {
			if (c === infix.charAt(0) && state.startsWith(Strings.sliceFrom(infix, 1))/*unknown*/){
				let length = Strings.length(infix) - 1/*unknown*/;
				let counter = 0/*unknown*/;
				let current = state/*DivideState*/;
				while (counter < length/*unknown*/){
					counter/*unknown*/++;
					current/*Tuple2<CompileState, List<T>>*/ = current.pop().map((tuple: Tuple2<DivideState, string>) => tuple.left()/*unknown*/).orElse(current)/*unknown*/;
				}
				return current.advance()/*unknown*/;
			}
			return state.append(c)/*unknown*/;
		}/*unknown*/;
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (Main.isNumber(stripped)/*unknown*/){
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new Symbol(stripped)))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, Value>>()/*unknown*/;
		}
	}
	static isNumber(input: string): boolean {
		let query = new HeadedIter<number>(new RangeHead(Strings.length(input)))/*unknown*/;
		return query.map(input.charAt).allMatch((c: string) => Characters.isDigit(c)/*unknown*/)/*unknown*/;
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (Main.isSymbol(stripped)/*unknown*/){
			let withImport = state.addResolvedImportFromCache(stripped)/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new Symbol(stripped)))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, Value>>()/*unknown*/;
		}
	}
	static isSymbol(input: string): boolean {
		let query = new HeadedIter<number>(new RangeHead(Strings.length(input)))/*unknown*/;
		return query.allMatch((index: number) => Main.isSymbolChar(index, input.charAt(index))/*unknown*/)/*unknown*/;
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c || Characters.isLetter(c) || (0 !== index && Characters.isDigit(c))/*unknown*/;
	}
	static compilePrefix<T>(input: string, infix: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		if (!input/*string*/.startsWith(infix)/*unknown*/){
			return new None<Tuple2<CompileState, T>>()/*unknown*/;
		}
		let slice = Strings.sliceFrom(input, Strings.length(infix))/*unknown*/;
		return mapper(slice)/*unknown*/;
	}
	static compileWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())/*unknown*/)/*unknown*/;
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings.isBlank(input)/*unknown*/){
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()))/*unknown*/;
		}
		return new None<Tuple2<CompileState, Whitespace>>()/*unknown*/;
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => Main.getTupleOption(state, withoutEnd).or(() => Main.compileEnumValues(state, withoutEnd)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main.parseParameter(state, withoutEnd).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";"))/*unknown*/)/*unknown*/;
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Main.parseValues(state, withoutEnd, (state1: CompileState, s: string) => Main.parseInvokable(state1, s).flatMap((tuple: Tuple2<CompileState, Value>) => {
			let structureName = state.findLastStructureName().orElse("")/*unknown*/;
			return tuple.right().generateAsEnumValue(structureName).map((stringOption: string) => new Tuple2Impl<CompileState, string>(tuple.left(), stringOption)/*unknown*/)/*unknown*/;
		})/*unknown*/).map((tuple: Tuple2<CompileState, List<string>>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse(""))/*unknown*/)/*unknown*/;
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return Main.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input))/*unknown*/)/*unknown*/;
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return Main.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => Main.getCompileStateParameterTuple2(tuple)/*unknown*/).or(() => Main.parseDefinition(state, input).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/;
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return Main.compileLast(Strings.strip(input), " ", (beforeName: string, name: string) => Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main.foldTypeSeparators), (beforeType: string, type: string) => Main.compileLast(Strings.strip(beforeType), "\n", (annotationsString: string, afterAnnotations: string) => {
			let annotations = Main.parseAnnotations(annotationsString)/*unknown*/;
			return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name)/*unknown*/;
		}).or(() => Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name)/*unknown*/)/*unknown*/).or(() => Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static parseAnnotations(s: string): List<string> {
		return Main.divide(s, (state1: DivideState, c: string) => Main.foldDelimited(state1, c, "\n")/*unknown*/).map((s2: string) => Strings.strip(s2)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).filter((value: string) => 1 <= Strings.length(value)/*unknown*/).map((value: string) => Strings.sliceFrom(value, 1)/*unknown*/).map((s1: string) => Strings.strip(s1)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main.compileSuffix(Strings.strip(beforeType), ">", (withoutTypeParamEnd: string) => Main.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams: string, typeParamsString: string) => {
			let typeParams = Main.divideValues(typeParamsString)/*unknown*/;
			return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name)/*unknown*/;
		})/*unknown*/).or(() => {
			let divided = Main.parseModifiers(beforeType)/*unknown*/;
			return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name)/*unknown*/;
		})/*unknown*/;
	}
	static parseModifiers(beforeType: string): List<string> {
		return Main.divide(Strings.strip(beforeType), (state1: DivideState, c: string) => Main.foldDelimited(state1, c, " ")/*unknown*/).map((s: string) => Strings.strip(s)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter === c/*unknown*/){
			return state1.advance()/*unknown*/;
		}
		return state1.append(c)/*unknown*/;
	}
	static divideValues(input: string): List<string> {
		return Main.divide(input, Main.foldValues).map((input1: string) => Strings.strip(input1)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static foldTypeSeparators(state: DivideState, c: string): DivideState {
		if (" " === c && state.isLevel()/*unknown*/){
			return state.advance()/*unknown*/;
		}
		let appended = state.append(c)/*unknown*/;
		if ("<" === c/*unknown*/){
			return appended.enter()/*unknown*/;
		}
		if (">" === c/*unknown*/){
			return appended.exit()/*unknown*/;
		}
		return appended/*unknown*/;
	}
	static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, oldModifiers: List<string>, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Main.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = Main.modifyModifiers(oldModifiers)/*unknown*/;
			let generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name)/*unknown*/;
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated))/*unknown*/;
		})/*unknown*/;
	}
	static modifyModifiers(oldModifiers: List<string>): List<string> {
		if (oldModifiers.contains("static")/*unknown*/){
			return Lists.of("static")/*unknown*/;
		}
		return Lists.empty()/*unknown*/;
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple2<CompileState, Type> {
		return Main.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())/*unknown*/).orElseGet(() => new Tuple2Impl<CompileState, Type>(state, new Placeholder(type))/*unknown*/)/*unknown*/;
	}
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return Main.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())/*unknown*/)/*unknown*/;
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return Main.or(state, type, Lists.of(Main.parseVarArgs, Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType))/*unknown*/;
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input)/*unknown*/;
		return Main.compileSuffix(stripped, "...", (s: string) => {
			let child = Main.parseTypeOrPlaceholder(state, s)/*unknown*/;
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())))/*unknown*/;
		})/*unknown*/;
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (Main.isSymbol(stripped)/*unknown*/){
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state.addResolvedImportFromCache(stripped), new Symbol(stripped)))/*unknown*/;
		}
		return new None<Tuple2<CompileState, Type>>()/*unknown*/;
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main.findPrimitiveValue(Strings.strip(input)).map((result: Type) => new Tuple2Impl<CompileState, Type>(state, result)/*unknown*/)/*unknown*/;
	}
	static findPrimitiveValue(input: string): Option<Type> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (Strings.equalsTo("char", stripped) || Strings.equalsTo("Character", stripped) || Strings.equalsTo("String", stripped)/*unknown*/){
			return new Some<Type>(PrimitiveType.String)/*unknown*/;
		}
		if (Strings.equalsTo("int", stripped) || Strings.equalsTo("Integer", stripped)/*unknown*/){
			return new Some<Type>(PrimitiveType.Number)/*unknown*/;
		}
		if (Strings.equalsTo("boolean", stripped) || Strings.equalsTo("Boolean", stripped)/*unknown*/){
			return new Some<Type>(PrimitiveType.Boolean)/*unknown*/;
		}
		if (Strings.equalsTo("var", stripped)/*unknown*/){
			return new Some<Type>(PrimitiveType.Var)/*unknown*/;
		}
		if (Strings.equalsTo("void", stripped)/*unknown*/){
			return new Some<Type>(PrimitiveType.Void)/*unknown*/;
		}
		return new None<Type>()/*unknown*/;
	}
	static parseGeneric(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Main.compileSuffix(Strings.strip(input), ">", (withoutEnd: string) => Main.compileFirst(withoutEnd, "<", (baseString: string, argsString: string) => {
			let argsTuple = Main.parseValuesOrEmpty(state, argsString, (state1: CompileState, s: string) => Main.compileTypeArgument(state1, s)/*unknown*/)/*unknown*/;
			let argsState = argsTuple.left()/*unknown*/;
			let args = argsTuple.right()/*unknown*/;
			let base = Strings.strip(baseString)/*unknown*/;
			return Main.assembleFunctionType(argsState, base, args).or(() => {
				let compileState = argsState.addResolvedImportFromCache(base)/*unknown*/;
				return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)))/*unknown*/;
			})/*unknown*/;
		})/*unknown*/)/*unknown*/;
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return Main.mapFunctionType(base, args).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state, generated)/*unknown*/)/*unknown*/;
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if (Strings.equalsTo("Function", base)/*unknown*/){
			return args.findFirst().and(() => args.find(1)/*unknown*/).map((tuple: Tuple2<string, string>) => new FunctionType(Lists.of(tuple.left()), tuple.right())/*unknown*/)/*unknown*/;
		}
		if (Strings.equalsTo("BiFunction", base)/*unknown*/){
			return args.find(0).and(() => args.find(1)/*unknown*/).and(() => args.find(2)/*unknown*/).map((tuple: Tuple2<Tuple2<string, string>, string>) => new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right())/*unknown*/)/*unknown*/;
		}
		if (Strings.equalsTo("Supplier", base)/*unknown*/){
			return args.findFirst().map((first: string) => new FunctionType(Lists.empty(), first)/*unknown*/)/*unknown*/;
		}
		if (Strings.equalsTo("Consumer", base)/*unknown*/){
			return args.findFirst().map((first: string) => new FunctionType(Lists.of(first), "void")/*unknown*/)/*unknown*/;
		}
		if (Strings.equalsTo("Predicate", base)/*unknown*/){
			return args.findFirst().map((first: string) => new FunctionType(Lists.of(first), "boolean")/*unknown*/)/*unknown*/;
		}
		return new None<Type>()/*unknown*/;
	}
	static compileTypeArgument(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Main.or(state, input, Lists.of((state2: CompileState, input1: string) => Main.compileWhitespace(state2, input1)/*unknown*/, (state1: CompileState, type: string) => Main.compileType(state1, type)/*unknown*/))/*unknown*/;
	}
	static generateValueStrings(values: List<string>): string {
		return Main.generateAll(values, Main.mergeValues)/*unknown*/;
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return Main.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()))/*unknown*/;
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main.parseAll(state, input, Main.foldValues, mapper)/*unknown*/;
	}
	static mergeValues(cache: string, element: string): string {
		if (Strings.isEmpty(cache)/*unknown*/){
			return cache + element/*unknown*/;
		}
		return cache + ", " + element/*unknown*/;
	}
	static foldValues(state: DivideState, c: string): DivideState {
		if ("," === c && state.isLevel()/*unknown*/){
			return state.advance()/*unknown*/;
		}
		let appended = state.append(c)/*unknown*/;
		if ("-" === c/*unknown*/){
			let peeked = appended.peek()/*unknown*/;
			if (">" === peeked/*unknown*/){
				return appended.popAndAppendToOption().orElse(appended)/*unknown*/;
			}
			else {
				return appended/*unknown*/;
			}
		}
		if ("<" === c || "(" === c/*unknown*/){
			return appended.enter()/*unknown*/;
		}
		if (">" === c || ")" === c/*unknown*/){
			return appended.exit()/*unknown*/;
		}
		return appended/*unknown*/;
	}
	static compileLast<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findLast, mapper)/*unknown*/;
	}
	static findLast(input: string, infix: string): number {
		return input.lastIndexOf(infix)/*unknown*/;
	}
	static compileSuffix<T>(input: string, suffix: string, mapper: (arg0 : string) => Option<T>): Option<T> {
		if (!input/*string*/.endsWith(suffix)/*unknown*/){
			return new None<T>()/*unknown*/;
		}
		let length = Strings.length(input)/*unknown*/;
		let length1 = Strings.length(suffix)/*unknown*/;
		let content = Strings.sliceBetween(input, 0, length - length1)/*unknown*/;
		return mapper(content)/*unknown*/;
	}
	static compileFirst<T>(input: string, infix: string, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findFirst, mapper)/*unknown*/;
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Main.compileSplit(Main.split(input, infix, locator), mapper)/*unknown*/;
	}
	static compileSplit<T>(splitter: Option<Tuple2<string, string>>, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return splitter.flatMap((tuple: Tuple2<string, string>) => mapper(tuple.left(), tuple.right())/*unknown*/)/*unknown*/;
	}
	static split(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number): Option<Tuple2<string, string>> {
		let index = locator(input, infix)/*unknown*/;
		if (0 > index/*unknown*/){
			return new None<Tuple2<string, string>>()/*unknown*/;
		}
		let left = Strings.sliceBetween(input, 0, index)/*unknown*/;
		let length = Strings.length(infix)/*unknown*/;
		let right = Strings.sliceFrom(input, index + length)/*unknown*/;
		return new Some<Tuple2<string, string>>(new Tuple2Impl<string, string>(left, right))/*unknown*/;
	}
	static findFirst(input: string, infix: string): number {
		return input.indexOf(infix)/*unknown*/;
	}
	static generatePlaceholder(input: string): string {
		let replaced = input.replace("/*", "start").replace("*/", "end")/*unknown*/;
		return "/*" + replaced + "*/"/*unknown*/;
	}
	static createInitialCompileState(): CompileState {
		return new ImmutableCompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<>(), Lists.empty(), Platform.TypeScript, Lists.empty())/*unknown*/;
	}
	static createInitialDivideState(input: string): DivideState {
		return new ImmutableDivideState(Lists.empty(), "", 0, input, 0)/*unknown*/;
	}
}

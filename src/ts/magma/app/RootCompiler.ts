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
	Iterable: magma.api.collect.list, 
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
	Application: magma.app, 
	CompileState: magma.app.compile, 
	Context: magma.app.compile, 
	ConstructionCaller: magma.app.compile.define, 
	ConstructorHeader: magma.app.compile.define, 
	Definition: magma.app.compile.define, 
	MethodHeader: magma.app.compile.define, 
	Parameter: magma.app.compile.define, 
	Dependency: magma.app.compile, 
	DivideState: magma.app.compile, 
	ImmutableCompileState: magma.app.compile, 
	ImmutableContext: magma.app.compile, 
	ImmutableDivideState: magma.app.compile, 
	ImmutableRegistry: magma.app.compile, 
	ImmutableStack: magma.app.compile, 
	Import: magma.app.compile, 
	Registry: magma.app.compile, 
	Stack: magma.app.compile, 
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
	Placeholder: magma.app.compile.value, 
	StringValue: magma.app.compile.value, 
	Symbol: magma.app.compile.value, 
	Value: magma.app.compile.value, 
	CompilerUtils: magma.app, 
	DefiningCompiler: magma.app, 
	DefinitionCompiler: magma.app, 
	FieldCompiler: magma.app, 
	FunctionSegmentCompiler: magma.app, 
	PathSource: magma.app.io, 
	Source: magma.app.io, 
	Location: magma.app, 
	Main: magma.app, 
	PathSources: magma.app, 
	PathTargets: magma.app, 
	Platform: magma.app, 
	RootCompiler: magma.app, 
	Sources: magma.app, 
	Targets: magma.app, 
	ValueCompiler: magma.app
]*/
import { CompileState } from "../../magma/app/compile/CompileState";
import { Tuple2 } from "../../magma/api/Tuple2";
import { CompilerUtils } from "../../magma/app/CompilerUtils";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Option } from "../../magma/api/option/Option";
import { Strings } from "../../magma/api/text/Strings";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { List } from "../../magma/api/collect/list/List";
import { Type } from "../../magma/app/compile/type/Type";
import { None } from "../../magma/api/option/None";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { DefiningCompiler } from "../../magma/app/DefiningCompiler";
import { Definition } from "../../magma/app/compile/define/Definition";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Platform } from "../../magma/app/Platform";
import { FieldCompiler } from "../../magma/app/FieldCompiler";
import { DivideState } from "../../magma/app/compile/DivideState";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { Value } from "../../magma/app/compile/value/Value";
import { Caller } from "../../magma/app/compile/value/Caller";
import { Argument } from "../../magma/app/compile/value/Argument";
import { Invokable } from "../../magma/app/compile/value/Invokable";
import { Iters } from "../../magma/api/collect/Iters";
import { ValueCompiler } from "../../magma/app/ValueCompiler";
import { Symbol } from "../../magma/app/compile/value/Symbol";
import { HeadedIter } from "../../magma/api/collect/head/HeadedIter";
import { RangeHead } from "../../magma/api/collect/head/RangeHead";
import { Characters } from "../../magma/api/text/Characters";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { Parameter } from "../../magma/app/compile/define/Parameter";
import { Placeholder } from "../../magma/app/compile/value/Placeholder";
import { VariadicType } from "../../magma/app/compile/type/VariadicType";
import { PrimitiveType } from "../../magma/app/compile/type/PrimitiveType";
import { TemplateType } from "../../magma/app/compile/type/TemplateType";
import { FunctionType } from "../../magma/app/compile/type/FunctionType";
import { ImmutableDivideState } from "../../magma/app/compile/ImmutableDivideState";
import { FunctionSegmentCompiler } from "../../magma/app/FunctionSegmentCompiler";
import { Location } from "../../magma/app/Location";
import { Context } from "../../magma/app/compile/Context";
import { Dependency } from "../../magma/app/compile/Dependency";
import { Registry } from "../../magma/app/compile/Registry";
import { Import } from "../../magma/app/compile/Import";
import { Source } from "../../magma/app/io/Source";
export class RootCompiler {
	static compileRootSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileOrPlaceholder(state, input, Lists.of(RootCompiler.compileWhitespace, RootCompiler.compileNamespaced, RootCompiler.createStructureRule("class ", "class "), RootCompiler.createStructureRule("interface ", "interface "), RootCompiler.createStructureRule("record ", "class "), RootCompiler.createStructureRule("enum ", "class ")))/*unknown*/;
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state: CompileState, input1: string) => RootCompiler.compileFirst(input1, sourceInfix, (beforeInfix: string, afterInfix: string) => RootCompiler.compileFirst(afterInfix, "{", (beforeContent: string, withEnd: string) => RootCompiler.compileSuffix(Strings.strip(withEnd), "}", (inputContent: string) => RootCompiler.compileLast(beforeInfix, "\n", (s: string, s2: string) => {
			let annotations = RootCompiler.parseAnnotations(s)/*unknown*/;
			if (annotations.contains("Actual")/*unknown*/){
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""))/*unknown*/;
			}
			return RootCompiler.compileStructureWithImplementing(state, annotations, RootCompiler.parseModifiers(s2), targetInfix, beforeContent, inputContent)/*unknown*/;
		}).or(() => {
			let modifiers = RootCompiler.parseModifiers(beforeContent)/*unknown*/;
			return RootCompiler.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent)/*unknown*/;
		})/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*//*unknown*/;
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileLast(beforeContent, " implements ", (s: string, s2: string) => RootCompiler.parseType(state, s2).flatMap((implementingTuple: Tuple2<CompileState, Type>) => RootCompiler.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content)/*unknown*/)/*unknown*/).or(() => RootCompiler.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content)/*unknown*/)/*unknown*/;
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileFirst(beforeContent, " extends ", (beforeExtends: string, afterExtends: string) => RootCompiler.parseValues(state, afterExtends, (inner0: CompileState, inner1: string) => RootCompiler.parseType(inner0, inner1)/*unknown*/).flatMap((compileStateListTuple2: Tuple2<CompileState, List<Type>>) => RootCompiler.compileStructureWithParameters(compileStateListTuple2.left(), annotations, modifiers, targetInfix, beforeExtends, compileStateListTuple2.right(), maybeImplementing, inputContent)/*unknown*/)/*unknown*/).or(() => RootCompiler.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, Lists.empty(), maybeImplementing, inputContent)/*unknown*/)/*unknown*/;
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: Iterable<Type>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileFirst(beforeContent, "(", (rawName: string, withParameters: string) => RootCompiler.compileFirst(withParameters, ")", (parametersString: string, _: string) => {
			let name = Strings.strip(rawName)/*unknown*/;
			let parametersTuple = DefiningCompiler.parseParameters(state, parametersString)/*unknown*/;
			let parameters = DefiningCompiler.retainDefinitionsFromParameters(parametersTuple.right())/*unknown*/;
			return RootCompiler.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType)/*unknown*/;
		})/*unknown*/).or(() => RootCompiler.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType)/*unknown*/)/*unknown*/;
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: Iterable<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: Iterable<Type>): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileSuffix(Strings.strip(beforeParams), ">", (withoutTypeParamEnd: string) => RootCompiler.compileFirst(withoutTypeParamEnd, "<", (name: string, typeParamsString: string) => {
			let typeParams = RootCompiler.divideValues(typeParamsString)/*unknown*/;
			return RootCompiler.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType)/*unknown*/;
		})/*unknown*/).or(() => RootCompiler.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType)/*unknown*/)/*unknown*/;
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: Iterable<string>, parameters: Iterable<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: Iterable<Type>): Option<Tuple2<CompileState, string>> {
		let name = Strings.strip(rawName)/*unknown*/;
		if (!RootCompiler/*unknown*/.isSymbol(name)/*unknown*/){
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
		let outputContentTuple = CompilerUtils.compileStatements(state.mapStack(stack -  > stack.pushStructureName(name)), content, RootCompiler.compileClassSegment)/*unknown*/;
		let outputContentState = outputContentTuple.left().mapStack(stack1 -  > stack1.popStructureName())/*unknown*/;
		let outputContent = outputContentTuple.right()/*unknown*/;
		let constructorString = RootCompiler.generateConstructorFromRecordParameters(parameters)/*unknown*/;
		let joinedTypeParams = RootCompiler.joinTypeParams(typeParams)/*unknown*/;
		let implementingString = RootCompiler.generateImplementing(maybeImplementing)/*unknown*/;
		let newModifiers = RootCompiler.modifyModifiers0(oldModifiers)/*unknown*/;
		let joinedModifiers = newModifiers.iter().map((value: string) => value + " "/*unknown*/).collect(Joiner.empty()).orElse("")/*unknown*/;
		if (outputContentState.context().hasPlatform(Platform.PlantUML)/*unknown*/){
			let joinedImplementing = maybeImplementing.map((type: Type) => type.generateSimple()/*unknown*/).map((generated: string) => name + " <|.. " + generated + "\n"/*unknown*/).orElse("")/*unknown*/;
			let joinedSuperTypes = maybeSuperType.iter().map((type: Type) => type.generateSimple()/*unknown*/).map((generated: string) => name + " <|-- " + generated + "\n"/*unknown*/).collect(new Joiner("")).orElse("")/*unknown*/;
			let generated = infix + name + joinedTypeParams + " {\n}\n" + joinedSuperTypes + joinedImplementing/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.mapRegistry(registry -  > registry.append(generated)), ""))/*unknown*/;
		}
		if (annotations.contains("Namespace")/*unknown*/){
			let actualInfix: string = "interface "/*unknown*/;
			let newName: string = name + "Instance"/*unknown*/;
			let generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + RootCompiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n"/*unknown*/;
			let compileState: CompileState = outputContentState.mapRegistry(registry -  > registry.append(generated))/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(compileState.mapRegistry(registry1 -  > registry1.append("export declare const " + name + ": " + newName + ";\n")), ""))/*unknown*/;
		}
		else {
			let extendsString = RootCompiler.joinExtends(maybeSuperType)/*unknown*/;
			let generated = joinedModifiers + infix + name + joinedTypeParams + extendsString + implementingString + " {" + RootCompiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n"/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.mapRegistry(registry -  > registry.append(generated)), ""))/*unknown*/;
		}
	}
	static joinExtends(maybeSuperType: Iterable<Type>): string {
		return maybeSuperType.iter().map((type: Type) => type.generate()/*unknown*/).collect(new Joiner(", ")).map((inner: string) => " extends " + inner/*unknown*/).orElse("")/*unknown*/;
	}
	static modifyModifiers0(oldModifiers: List<string>): Iterable<string> {
		if (oldModifiers.contains("public")/*unknown*/){
			return Lists.of("export")/*unknown*/;
		}
		return Lists.empty()/*unknown*/;
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing.map((type: Type) => type.generate()/*unknown*/).map((inner: string) => " implements " + inner/*unknown*/).orElse("")/*unknown*/;
	}
	static joinTypeParams(typeParams: Iterable<string>): string {
		return typeParams.iter().collect(new Joiner(", ")).map((inner: string) => "<" + inner + ">"/*unknown*/).orElse("")/*unknown*/;
	}
	static generateConstructorFromRecordParameters(parameters: Iterable<Definition>): string {
		return parameters.iter().map((definition: Definition) => definition.generate()/*unknown*/).collect(new Joiner(", ")).map((generatedParameters: string) => RootCompiler.generateConstructorWithParameterString(parameters, generatedParameters)/*unknown*/).orElse("")/*unknown*/;
	}
	static generateConstructorWithParameterString(parameters: Iterable<Definition>, parametersString: string): string {
		let constructorAssignments = RootCompiler.generateConstructorAssignments(parameters)/*unknown*/;
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}"/*unknown*/;
	}
	static generateConstructorAssignments(parameters: Iterable<Definition>): string {
		return parameters.iter().map((definition: Definition) => definition.toAssignment()/*unknown*/).collect(Joiner.empty()).orElse("")/*unknown*/;
	}
	static joinParameters(parameters: Iterable<Definition>): string {
		return parameters.iter().map((definition: Definition) => definition.generate()/*unknown*/).map((generated: string) => "\n\t" + generated + ";"/*unknown*/).collect(Joiner.empty()).orElse("")/*unknown*/;
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (stripped.startsWith("package ") || stripped.startsWith("import ")/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""))/*unknown*/;
		}
		return new None<Tuple2<CompileState, string>>()/*unknown*/;
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileOrPlaceholder(state1, input1, Lists.of(RootCompiler.compileWhitespace, RootCompiler.createStructureRule("class ", "class "), RootCompiler.createStructureRule("interface ", "interface "), RootCompiler.createStructureRule("record ", "class "), RootCompiler.createStructureRule("enum ", "class "), FieldCompiler.compileMethod, RootCompiler.compileFieldDefinition, RootCompiler.compileEnumValues))/*unknown*/;
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileStatements(state, input, CompilerUtils.compileFunctionSegment)/*unknown*/;
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return RootCompiler.splitFolded(input, folder, (divisions1: List<string>) => RootCompiler.selectLast(divisions1, delimiter)/*unknown*/)/*unknown*/;
	}
	static splitFolded(input: string, folder: (arg0 : DivideState, arg1 : string) => DivideState, selector: (arg0 : List<string>) => Option<Tuple2<string, string>>): Option<Tuple2<string, string>> {
		let divisions = CompilerUtils.divide(input, folder).collect(new ListCollector<string>())/*unknown*/;
		if (2 > divisions.size()/*unknown*/){
			return new None<Tuple2<string, string>>()/*unknown*/;
		}
		return selector(divisions)/*unknown*/;
	}
	static selectLast(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions)/*unknown*/;
		let last = divisions.findLast().orElse("")/*unknown*/;
		let joined = beforeLast.iter().collect(new Joiner(delimiter)).orElse("")/*unknown*/;
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
		return RootCompiler.parseValues(state, argsString, (state1: CompileState, s: string) => {
			return RootCompiler.parseArgument(state1, s)/*unknown*/;
		}).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState = argsTuple.left()/*unknown*/;
			let args = RootCompiler.retain(argsTuple.right(), (argument: Argument) => argument.toValue()/*unknown*/)/*unknown*/;
			let newCaller = RootCompiler.transformCaller(argsState, oldCaller)/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)))/*unknown*/;
		})/*unknown*/;
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller.findChild().flatMap((parent: Value) => {
			let parentType = RootCompiler.resolve0(state, parent)/*unknown*/;
			if (parentType.isFunctional()/*unknown*/){
				return new Some<Caller>(parent)/*unknown*/;
			}
			return new None<Caller>()/*unknown*/;
		}).orElse(oldCaller)/*unknown*/;
	}
	static retain<T, R>(args: Iterable<T>, mapper: (arg0 : T) => Option<R>): Iterable<R> {
		return args.iter().map(mapper).flatMap(Iters.fromOption).collect(new ListCollector<R>())/*unknown*/;
	}
	static parseArgument(state1: CompileState, input: string): Option<Tuple2<CompileState, Argument>> {
		return RootCompiler.parseValue(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right())/*unknown*/)/*unknown*/;
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileFirst(input, "=", (destination: string, source: string) => {
			let sourceTuple = RootCompiler.compileValueOrPlaceholder(state, source)/*unknown*/;
			let destinationTuple = RootCompiler.compileValue(sourceTuple.left(), destination).or(() => RootCompiler.parseDefinition(sourceTuple.left(), destination).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, string>(tuple.left(), "let " + tuple.right().generate())/*unknown*/)/*unknown*/).orElseGet(() => new Tuple2Impl<CompileState, string>(sourceTuple.left(), RootCompiler.generatePlaceholder(destination))/*unknown*/)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()))/*unknown*/;
		})/*unknown*/;
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return RootCompiler.compileValue(state, input).orElseGet(() => new Tuple2Impl<CompileState, string>(state, RootCompiler.generatePlaceholder(input))/*unknown*/)/*unknown*/;
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.parseValue(state, input).map((tuple: Tuple2<CompileState, Value>) => ValueCompiler.generateValue(tuple)/*unknown*/)/*unknown*/;
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return CompilerUtils.or(state, input, Lists.of(ValueCompiler.parseLambda, ValueCompiler.createOperatorRule("+"), ValueCompiler.createOperatorRule("-"), ValueCompiler.createOperatorRule("<="), ValueCompiler.createOperatorRule("<"), ValueCompiler.createOperatorRule("&&"), ValueCompiler.createOperatorRule("||"), ValueCompiler.createOperatorRule(">"), ValueCompiler.createOperatorRule(">="), ValueCompiler.parseInvokable, ValueCompiler.createAccessRule("."), ValueCompiler.createAccessRule("::"), RootCompiler.parseSymbol, ValueCompiler.parseNot, RootCompiler.parseNumber, ValueCompiler.createOperatorRuleWithDifferentInfix("==", "==="), ValueCompiler.createOperatorRuleWithDifferentInfix("!=", "!=="), ValueCompiler.createTextRule("\""), ValueCompiler.createTextRule("'")))/*unknown*/;
	}
	static selectFirst(divisions: List<string>, delimiter: string): Option<Tuple2<string, string>> {
		let first = divisions.findFirst().orElse("")/*unknown*/;
		let afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).iter().collect(new Joiner(delimiter)).orElse("")/*unknown*/;
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
					current/*unknown*/ = current.pop().map((tuple: Tuple2<DivideState, string>) => tuple.left()/*unknown*/).orElse(current)/*unknown*/;
				}
				return current.advance()/*unknown*/;
			}
			return state.append(c)/*unknown*/;
		}/*unknown*/;
	}
	static parseNumber(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (RootCompiler.isNumber(stripped)/*unknown*/){
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
		if (RootCompiler.isSymbol(stripped)/*unknown*/){
			let withImport = RootCompiler.addResolvedImportFromCache0(state, stripped)/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new Symbol(stripped)))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, Value>>()/*unknown*/;
		}
	}
	static isSymbol(input: string): boolean {
		let query = new HeadedIter<number>(new RangeHead(Strings.length(input)))/*unknown*/;
		return query.allMatch((index: number) => RootCompiler.isSymbolChar(index, input.charAt(index))/*unknown*/)/*unknown*/;
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
		return RootCompiler.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())/*unknown*/)/*unknown*/;
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings.isBlank(input)/*unknown*/){
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()))/*unknown*/;
		}
		return new None<Tuple2<CompileState, Whitespace>>()/*unknown*/;
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => RootCompiler.getTupleOption(state, withoutEnd).or(() => RootCompiler.compileEnumValues(state, withoutEnd)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.parseParameter(state, withoutEnd).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";"))/*unknown*/)/*unknown*/;
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.parseValues(state, withoutEnd, (state1: CompileState, segment: string) => {
			let stripped = segment.strip()/*unknown*/;
			if (RootCompiler.isSymbol(stripped)/*unknown*/){
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state1, "\n\t static " + stripped + " = \"" + stripped + "\";"))/*unknown*/;
			}
			return RootCompiler.getTuple2Option(state, state1, segment)/*unknown*/;
		}).map((tuple: Tuple2<CompileState, List<string>>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().iter().collect(new Joiner("")).orElse(""))/*unknown*/)/*unknown*/;
	}
	static getTuple2Option(state: CompileState, state1: CompileState, segment: string): Option<Tuple2<CompileState, string>> {
		return ValueCompiler.parseInvokable(state1, segment).flatMap((tuple: Tuple2<CompileState, Value>) => {
			let structureName = state.stack().findLastStructureName().orElse("")/*unknown*/;
			return tuple.right().generateAsEnumValue(structureName).map((stringOption: string) => new Tuple2Impl<CompileState, string>(tuple.left(), stringOption)/*unknown*/)/*unknown*/;
		})/*unknown*/;
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return RootCompiler.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input))/*unknown*/)/*unknown*/;
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return RootCompiler.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => RootCompiler.getCompileStateParameterTuple2(tuple)/*unknown*/).or(() => RootCompiler.parseDefinition(state, input).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/;
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return RootCompiler.compileLast(Strings.strip(input), " ", (beforeName: string, name: string) => RootCompiler.compileSplit(RootCompiler.splitFoldedLast(Strings.strip(beforeName), " ", RootCompiler.foldTypeSeparators), (beforeType: string, type: string) => RootCompiler.compileLast(Strings.strip(beforeType), "\n", (annotationsString: string, afterAnnotations: string) => {
			let annotations = RootCompiler.parseAnnotations(annotationsString)/*unknown*/;
			return RootCompiler.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name)/*unknown*/;
		}).or(() => RootCompiler.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name)/*unknown*/)/*unknown*/).or(() => RootCompiler.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static parseAnnotations(s: string): List<string> {
		return CompilerUtils.divide(s, (state1: DivideState, c: string) => RootCompiler.foldDelimited(state1, c, "\n")/*unknown*/).map((s2: string) => Strings.strip(s2)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).filter((value: string) => 1 <= Strings.length(value)/*unknown*/).map((value: string) => Strings.sliceFrom(value, 1)/*unknown*/).map((s1: string) => Strings.strip(s1)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return RootCompiler.compileSuffix(Strings.strip(beforeType), ">", (withoutTypeParamEnd: string) => RootCompiler.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams: string, typeParamsString: string) => {
			let typeParams = RootCompiler.divideValues(typeParamsString)/*unknown*/;
			return RootCompiler.parseDefinitionWithTypeParameters(state, annotations, typeParams, RootCompiler.parseModifiers(beforeTypeParams), type, name)/*unknown*/;
		})/*unknown*/).or(() => {
			let divided = RootCompiler.parseModifiers(beforeType)/*unknown*/;
			return RootCompiler.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name)/*unknown*/;
		})/*unknown*/;
	}
	static parseModifiers(beforeType: string): List<string> {
		return CompilerUtils.divide(Strings.strip(beforeType), (state1: DivideState, c: string) => RootCompiler.foldDelimited(state1, c, " ")/*unknown*/).map((s: string) => Strings.strip(s)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter === c/*unknown*/){
			return state1.advance()/*unknown*/;
		}
		return state1.append(c)/*unknown*/;
	}
	static divideValues(input: string): List<string> {
		return CompilerUtils.divide(input, RootCompiler.foldValues).map((input1: string) => Strings.strip(input1)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
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
		return RootCompiler.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = RootCompiler.modifyModifiers(oldModifiers)/*unknown*/;
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
		return RootCompiler.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())/*unknown*/).orElseGet(() => new Tuple2Impl<CompileState, Type>(state, new Placeholder(type))/*unknown*/)/*unknown*/;
	}
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())/*unknown*/)/*unknown*/;
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return CompilerUtils.or(state, type, Lists.of(RootCompiler.parseVarArgs, RootCompiler.parseGeneric, RootCompiler.parsePrimitive, RootCompiler.parseSymbolType))/*unknown*/;
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input)/*unknown*/;
		return RootCompiler.compileSuffix(stripped, "...", (s: string) => {
			let child = RootCompiler.parseTypeOrPlaceholder(state, s)/*unknown*/;
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())))/*unknown*/;
		})/*unknown*/;
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (RootCompiler.isSymbol(stripped)/*unknown*/){
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(RootCompiler.addResolvedImportFromCache0(state, stripped), new Symbol(stripped)))/*unknown*/;
		}
		return new None<Tuple2<CompileState, Type>>()/*unknown*/;
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return RootCompiler.findPrimitiveValue(Strings.strip(input)).map((result: Type) => new Tuple2Impl<CompileState, Type>(state, result)/*unknown*/)/*unknown*/;
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
		return RootCompiler.compileSuffix(Strings.strip(input), ">", (withoutEnd: string) => RootCompiler.compileFirst(withoutEnd, "<", (baseString: string, argsString: string) => {
			let argsTuple = RootCompiler.parseValuesOrEmpty(state, argsString, (state1: CompileState, s: string) => RootCompiler.compileTypeArgument(state1, s)/*unknown*/)/*unknown*/;
			let argsState = argsTuple.left()/*unknown*/;
			let args = argsTuple.right()/*unknown*/;
			let base = Strings.strip(baseString)/*unknown*/;
			return RootCompiler.assembleFunctionType(argsState, base, args).or(() => {
				let compileState = RootCompiler.addResolvedImportFromCache0(argsState, base)/*unknown*/;
				return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)))/*unknown*/;
			})/*unknown*/;
		})/*unknown*/)/*unknown*/;
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return RootCompiler.mapFunctionType(base, args).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state, generated)/*unknown*/)/*unknown*/;
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
		return CompilerUtils.or(state, input, Lists.of((state2: CompileState, input1: string) => RootCompiler.compileWhitespace(state2, input1)/*unknown*/, (state1: CompileState, type: string) => RootCompiler.compileType(state1, type)/*unknown*/))/*unknown*/;
	}
	static generateValueStrings(values: Iterable<string>): string {
		return CompilerUtils.generateAll(values, RootCompiler.mergeValues)/*unknown*/;
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return RootCompiler.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()))/*unknown*/;
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return CompilerUtils.parseAll(state, input, RootCompiler.foldValues, mapper)/*unknown*/;
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
		return RootCompiler.compileInfix(input, infix, RootCompiler.findLast, mapper)/*unknown*/;
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
		return RootCompiler.compileInfix(input, infix, RootCompiler.findFirst, mapper)/*unknown*/;
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return RootCompiler.compileSplit(RootCompiler.split(input, infix, locator), mapper)/*unknown*/;
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
	static createInitialDivideState(input: string): DivideState {
		return new ImmutableDivideState(Lists.empty(), "", 0, input, 0)/*unknown*/;
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple2<CompileState, string>> {
		return FunctionSegmentCompiler.compileReturn(input1, (withoutPrefix: string) => RootCompiler.compileValue(state1, withoutPrefix)/*unknown*/).map((tuple: Tuple2<CompileState, string>) => new Tuple2Impl<CompileState, string>(tuple.left(), state1.createIndent() + tuple.right())/*unknown*/)/*unknown*/;
	}
	static compileRoot(state: CompileState, input: string, location: Location): Tuple2<CompileState, string> {
		return CompilerUtils.compileStatements(state.mapContext((context2: Context) => context2.withLocation(location)/*unknown*/), input, RootCompiler.compileRootSegment)/*unknown*/;
	}
	static fixNamespace(requestedNamespace: List<string>, thisNamespace: List<string>): List<string> {
		if (thisNamespace.isEmpty()/*unknown*/){
			return requestedNamespace.addFirst(".")/*unknown*/;
		}
		return RootCompiler.addParentSeparator(requestedNamespace, thisNamespace.size())/*unknown*/;
	}
	static addParentSeparator(newNamespace: List<string>, count: number): List<string> {
		let index = 0/*unknown*/;
		let copy = newNamespace/*List<string>*/;
		while (index < count/*unknown*/){
			copy/*unknown*/ = copy.addFirst("..")/*unknown*/;
			index/*number*/++;
		}
		return copy/*unknown*/;
	}
	static getCompileState1(immutableCompileState: CompileState, location: Location): Option<CompileState> {
		if (!immutableCompileState/*CompileState*/.context().hasPlatform(Platform.PlantUML)/*unknown*/){
			return new None<>()/*unknown*/;
		}
		let name = immutableCompileState.context().findNameOrEmpty()/*unknown*/;
		let dependency = new Dependency(name, location.name())/*unknown*/;
		if (immutableCompileState.registry().containsDependency(dependency)/*unknown*/){
			return new None<>()/*unknown*/;
		}
		return new Some<>(immutableCompileState.mapRegistry((registry1: Registry) => registry1.addDependency(dependency)/*unknown*/))/*unknown*/;
	}
	static getState(immutableCompileState: CompileState, location: Location): CompileState {
		let requestedNamespace = location.namespace()/*unknown*/;
		let requestedChild = location.name()/*unknown*/;
		let namespace = RootCompiler.fixNamespace(requestedNamespace, immutableCompileState.context().findNamespaceOrEmpty())/*unknown*/;
		if (immutableCompileState.registry().doesImportExistAlready(requestedChild)/*unknown*/){
			return immutableCompileState/*CompileState*/;
		}
		let namespaceWithChild = namespace.addLast(requestedChild)/*unknown*/;
		let anImport = new Import(namespaceWithChild, requestedChild)/*unknown*/;
		return immutableCompileState.mapRegistry((registry: Registry) => registry.addImport(anImport)/*unknown*/)/*unknown*/;
	}
	static addResolvedImportFromCache0(state: CompileState, base: string): CompileState {
		if (state.stack().hasAnyStructureName(base)/*unknown*/){
			return state/*CompileState*/;
		}
		return state.context().findSource(base).map((source: Source) => {
			let location: Location = source.createLocation()/*unknown*/;
			return RootCompiler.getCompileState1(state, location).orElseGet(() => RootCompiler.getState(state, location)/*unknown*/)/*unknown*/;
		}).orElse(state)/*unknown*/;
	}
	static resolve0(state: CompileState, value: Value): Type {/*return switch (value) {
            case AccessValue accessValue -> accessValue.resolve(state);
            case Invokable invokable -> invokable.resolve(state);
            case Lambda lambda -> lambda.resolve(state);
            case Not not -> not.resolve(state);
            case Operation operation -> operation.resolve(state);
            case Placeholder placeholder -> placeholder.resolve(state);
            case StringValue stringValue -> stringValue.resolve(state);
            case Symbol symbol -> symbol.resolve(state);
        }*/;
	}
}

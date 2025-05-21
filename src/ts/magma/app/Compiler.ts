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
	Compiler: magma.app, 
	CompilerUtils: magma.app, 
	DefinitionCompiler: magma.app, 
	PathSource: magma.app.io, 
	Source: magma.app.io, 
	Location: magma.app, 
	Main: magma.app, 
	PathSources: magma.app, 
	PathTargets: magma.app, 
	Platform: magma.app, 
	Sources: magma.app, 
	Targets: magma.app
]*/
import { CompileState } from "../../magma/app/compile/CompileState";
import { Tuple2 } from "../../magma/api/Tuple2";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Option } from "../../magma/api/option/Option";
import { Strings } from "../../magma/api/text/Strings";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { List } from "../../magma/api/collect/list/List";
import { Type } from "../../magma/app/compile/type/Type";
import { None } from "../../magma/api/option/None";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Definition } from "../../magma/app/compile/define/Definition";
import { Parameter } from "../../magma/app/compile/define/Parameter";
import { Iters } from "../../magma/api/collect/Iters";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { CompilerUtils } from "../../magma/app/CompilerUtils";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Platform } from "../../magma/app/Platform";
import { ConstructorHeader } from "../../magma/app/compile/define/ConstructorHeader";
import { MethodHeader } from "../../magma/app/compile/define/MethodHeader";
import { DivideState } from "../../magma/app/compile/DivideState";
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
import { Symbol } from "../../magma/app/compile/value/Symbol";
import { HeadedIter } from "../../magma/api/collect/head/HeadedIter";
import { RangeHead } from "../../magma/api/collect/head/RangeHead";
import { Characters } from "../../magma/api/text/Characters";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { Placeholder } from "../../magma/app/compile/value/Placeholder";
import { VariadicType } from "../../magma/app/compile/type/VariadicType";
import { PrimitiveType } from "../../magma/app/compile/type/PrimitiveType";
import { TemplateType } from "../../magma/app/compile/type/TemplateType";
import { FunctionType } from "../../magma/app/compile/type/FunctionType";
import { ImmutableDivideState } from "../../magma/app/compile/ImmutableDivideState";
import { Location } from "../../magma/app/Location";
import { Context } from "../../magma/app/compile/Context";
import { Dependency } from "../../magma/app/compile/Dependency";
import { Registry } from "../../magma/app/compile/Registry";
import { Import } from "../../magma/app/compile/Import";
import { Source } from "../../magma/app/io/Source";
export class Compiler {
	static compileRootSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Compiler.compileOrPlaceholder(state, input, Lists.of(Compiler.compileWhitespace, Compiler.compileNamespaced, Compiler.createStructureRule("class ", "class "), Compiler.createStructureRule("interface ", "interface "), Compiler.createStructureRule("record ", "class "), Compiler.createStructureRule("enum ", "class ")))/*unknown*/;
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state: CompileState, input1: string) => Compiler.compileFirst(input1, sourceInfix, (beforeInfix: string, afterInfix: string) => Compiler.compileFirst(afterInfix, "{", (beforeContent: string, withEnd: string) => Compiler.compileSuffix(Strings.strip(withEnd), "}", (inputContent: string) => Compiler.compileLast(beforeInfix, "\n", (s: string, s2: string) => {
			let annotations = Compiler.parseAnnotations(s)/*unknown*/;
			if (annotations.contains("Actual")/*unknown*/){
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""))/*unknown*/;
			}
			return Compiler.compileStructureWithImplementing(state, annotations, Compiler.parseModifiers(s2), targetInfix, beforeContent, inputContent)/*unknown*/;
		}).or(() => {
			let modifiers = Compiler.parseModifiers(beforeContent)/*unknown*/;
			return Compiler.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent)/*unknown*/;
		})/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*//*unknown*/;
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileLast(beforeContent, " implements ", (s: string, s2: string) => Compiler.parseType(state, s2).flatMap((implementingTuple: Tuple2<CompileState, Type>) => Compiler.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content)/*unknown*/)/*unknown*/).or(() => Compiler.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content)/*unknown*/)/*unknown*/;
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileFirst(beforeContent, " extends ", (beforeExtends: string, afterExtends: string) => Compiler.parseValues(state, afterExtends, (inner0: CompileState, inner1: string) => Compiler.parseType(inner0, inner1)/*unknown*/).flatMap((compileStateListTuple2: Tuple2<CompileState, List<Type>>) => Compiler.compileStructureWithParameters(compileStateListTuple2.left(), annotations, modifiers, targetInfix, beforeExtends, compileStateListTuple2.right(), maybeImplementing, inputContent)/*unknown*/)/*unknown*/).or(() => Compiler.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, Lists.empty(), maybeImplementing, inputContent)/*unknown*/)/*unknown*/;
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: Iterable<Type>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileFirst(beforeContent, "(", (rawName: string, withParameters: string) => Compiler.compileFirst(withParameters, ")", (parametersString: string, _: string) => {
			let name = Strings.strip(rawName)/*unknown*/;
			let parametersTuple = Compiler.parseParameters(state, parametersString)/*unknown*/;
			let parameters = Compiler.retainDefinitionsFromParameters(parametersTuple.right())/*unknown*/;
			return Compiler.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType)/*unknown*/;
		})/*unknown*/).or(() => Compiler.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType)/*unknown*/)/*unknown*/;
	}
	static retainDefinitionsFromParameters(parameters: Iterable<Parameter>): Iterable<Definition> {
		return parameters.iter().map((parameter: Parameter) => parameter.asDefinition()/*unknown*/).flatMap(Iters.fromOption).collect(new ListCollector<Definition>())/*unknown*/;
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: Iterable<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: Iterable<Type>): Option<Tuple2<CompileState, string>> {
		return Compiler.compileSuffix(Strings.strip(beforeParams), ">", (withoutTypeParamEnd: string) => Compiler.compileFirst(withoutTypeParamEnd, "<", (name: string, typeParamsString: string) => {
			let typeParams = Compiler.divideValues(typeParamsString)/*unknown*/;
			return Compiler.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType)/*unknown*/;
		})/*unknown*/).or(() => Compiler.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType)/*unknown*/)/*unknown*/;
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: Iterable<string>, parameters: Iterable<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: Iterable<Type>): Option<Tuple2<CompileState, string>> {
		let name = Strings.strip(rawName)/*unknown*/;
		if (!Compiler/*unknown*/.isSymbol(name)/*unknown*/){
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
		let outputContentTuple = CompilerUtils.compileStatements(state.mapStack(stack -  > stack.pushStructureName(name)), content, Compiler.compileClassSegment)/*unknown*/;
		let outputContentState = outputContentTuple.left().mapStack(stack1 -  > stack1.popStructureName())/*unknown*/;
		let outputContent = outputContentTuple.right()/*unknown*/;
		let constructorString = Compiler.generateConstructorFromRecordParameters(parameters)/*unknown*/;
		let joinedTypeParams = Compiler.joinTypeParams(typeParams)/*unknown*/;
		let implementingString = Compiler.generateImplementing(maybeImplementing)/*unknown*/;
		let newModifiers = Compiler.modifyModifiers0(oldModifiers)/*unknown*/;
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
			let generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Compiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n"/*unknown*/;
			let compileState: CompileState = outputContentState.mapRegistry(registry -  > registry.append(generated))/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(compileState.mapRegistry(registry1 -  > registry1.append("export declare const " + name + ": " + newName + ";\n")), ""))/*unknown*/;
		}
		else {
			let extendsString = Compiler.joinExtends(maybeSuperType)/*unknown*/;
			let generated = joinedModifiers + infix + name + joinedTypeParams + extendsString + implementingString + " {" + Compiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n"/*unknown*/;
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
		return parameters.iter().map((definition: Definition) => definition.generate()/*unknown*/).collect(new Joiner(", ")).map((generatedParameters: string) => Compiler.generateConstructorWithParameterString(parameters, generatedParameters)/*unknown*/).orElse("")/*unknown*/;
	}
	static generateConstructorWithParameterString(parameters: Iterable<Definition>, parametersString: string): string {
		let constructorAssignments = Compiler.generateConstructorAssignments(parameters)/*unknown*/;
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
	static compileOrPlaceholder(state: CompileState, input: string, rules: Iterable<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>>>): Tuple2<CompileState, string> {
		return Compiler.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Compiler.generatePlaceholder(input))/*unknown*/)/*unknown*/;
	}
	static or<T>(state: CompileState, input: string, rules: Iterable<(arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules.iter().map((rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>) => Compiler.getApply(state, input, rule)/*unknown*/).flatMap(Iters.fromOption).next()/*unknown*/;
	}
	static getApply<T>(state: CompileState, input: string, rule: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule(state, input)/*unknown*/;
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return Compiler.compileOrPlaceholder(state1, input1, Lists.of(Compiler.compileWhitespace, Compiler.createStructureRule("class ", "class "), Compiler.createStructureRule("interface ", "interface "), Compiler.createStructureRule("record ", "class "), Compiler.createStructureRule("enum ", "class "), Compiler.compileMethod, Compiler.compileFieldDefinition, Compiler.compileEnumValues))/*unknown*/;
	}
	static compileMethod(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileFirst(input, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams = Strings.strip(beforeParams)/*unknown*/;
			return Compiler.compileLast(strippedBeforeParams, " ", (_: string, name: string) => {
				if (state.stack().isWithinLast(name)/*unknown*/){
					return Compiler.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams)/*unknown*/;
				}
				return new None<Tuple2<CompileState, string>>()/*unknown*/;
			}).or(() => {
				if (state.stack().findLastStructureName().filter((anObject: string) => Strings.equalsTo(strippedBeforeParams, anObject)/*unknown*/).isPresent()/*unknown*/){
					return Compiler.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams)/*unknown*/;
				}
				return new None<Tuple2<CompileState, string>>()/*unknown*/;
			}).or(() => Compiler.parseDefinition(state, beforeParams).flatMap((tuple: Tuple2<CompileState, Definition>) => Compiler.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)/*unknown*/)/*unknown*/)/*unknown*/;
		})/*unknown*/;
	}
	static compileMethodWithBeforeParams(state: CompileState, header: MethodHeader, withParams: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileFirst(withParams, ")", (params: string, afterParams: string) => {
			let parametersTuple = Compiler.parseParameters(state, params)/*unknown*/;
			let parametersState = parametersTuple.left()/*unknown*/;
			let parameters = parametersTuple.right()/*unknown*/;
			let definitions = Compiler.retainDefinitionsFromParameters(parameters)/*unknown*/;
			let joinedDefinitions = definitions.iter().map((definition: Definition) => definition.generate()/*unknown*/).collect(new Joiner(", ")).orElse("")/*unknown*/;
			if (header.hasAnnotation("Actual")/*unknown*/){
				let headerGenerated = header.removeModifier("static").generateWithAfterName("(" + joinedDefinitions + ")")/*unknown*/;
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";\n"))/*unknown*/;
			}
			let headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")")/*unknown*/;
			return Compiler.compilePrefix(Strings.strip(afterParams), "{", (withoutContentStart: string) => Compiler.compileSuffix(Strings.strip(withoutContentStart), "}", (withoutContentEnd: string) => {
				let compileState: CompileState = parametersState.enterDepth().enterDepth()/*unknown*/;
				let statementsTuple = Compiler.compileFunctionStatements(compileState.mapStack(stack1 -  > stack1.defineAll(definitions)), withoutContentEnd)/*unknown*/;
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
		return Compiler.parseValuesOrEmpty(state, params, (state1: CompileState, s: string) => {
			return new Some<Tuple2<CompileState, Parameter>>(Compiler.parseParameterOrPlaceholder(state1, s))/*unknown*/;
		})/*unknown*/;
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileStatements(state, input, Compiler.compileFunctionSegment)/*unknown*/;
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Compiler.compileOrPlaceholder(state, input, Lists.of(Compiler.compileWhitespace, Compiler.compileEmptySegment, Compiler.compileBlock, Compiler.compileFunctionStatement, Compiler.compileReturnWithoutSuffix))/*unknown*/;
	}
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo(";", Strings.strip(input))/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ";"))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileSuffix(Strings.strip(input), "}", (withoutEnd: string) => Compiler.compileSplit(Compiler.splitFoldedLast(withoutEnd, "", Compiler.foldBlockStarts), (beforeContentWithEnd: string, content: string) => Compiler.compileSuffix(beforeContentWithEnd, "{", (beforeContent: string) => Compiler.compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple2<CompileState, string>) => {
			let contentTuple = Compiler.compileFunctionStatements(headerTuple.left().enterDepth(), content)/*unknown*/;
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
		return Compiler.or(state, input, Lists.of(Compiler.createConditionalRule("if"), Compiler.createConditionalRule("while"), Compiler.compileElse))/*unknown*/;
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input1: string) => Compiler.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: string) => {
			let strippedCondition = Strings.strip(withoutPrefix)/*unknown*/;
			return Compiler.compilePrefix(strippedCondition, "(", (withoutConditionStart: string) => Compiler.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: string) => {
				let tuple = Compiler.compileValueOrPlaceholder(state1, withoutConditionEnd)/*unknown*/;
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
		return Compiler.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			let valueTuple = Compiler.compileFunctionStatementValue(state, withoutEnd)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"))/*unknown*/;
		})/*unknown*/;
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return Compiler.compileOrPlaceholder(state, withoutEnd, Lists.of(Compiler.compileReturnWithValue, Compiler.compileAssignment, (state1: CompileState, input: string) => Compiler.parseInvokable(state1, input).map((tuple: Tuple2<CompileState, Value>) => Compiler.generateValue(tuple)/*unknown*/)/*unknown*/, Compiler.createPostRule("++"), Compiler.createPostRule("--"), Compiler.compileBreak))/*unknown*/;
	}
	static generateValue(tuple: Tuple2<CompileState, Value>): Tuple2Impl<CompileState, string> {
		let state = tuple.left()/*unknown*/;
		let right = tuple.right()/*unknown*/;
		let generated = right.generate()/*unknown*/;
		let s = Compiler.generatePlaceholder(Compiler.resolve0(state, right).generate())/*unknown*/;
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
		return (state1: CompileState, input: string) => Compiler.compileSuffix(Strings.strip(input), suffix, (child: string) => {
			let tuple = Compiler.compileValueOrPlaceholder(state1, child)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right() + suffix))/*unknown*/;
		})/*unknown*//*unknown*/;
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileReturn(input, (value1: string) => Compiler.compileValue(state, value1)/*unknown*/)/*unknown*/;
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return Compiler.compilePrefix(Strings.strip(input), "return ", (value: string) => mapper(value).flatMap((tuple: Tuple2<CompileState, string>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), "return " + tuple.right()))/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Compiler.compileSuffix(Strings.strip(input), ")", (withoutEnd: string) => Compiler.compileSplit(Compiler.splitFoldedLast(withoutEnd, "", Compiler.foldInvocationStarts), (callerWithArgStart: string, args: string) => Compiler.compileSuffix(callerWithArgStart, "(", (callerString: string) => Compiler.compilePrefix(Strings.strip(callerString), "new ", (type: string) => Compiler.compileType(state, type).flatMap((callerTuple: Tuple2<CompileState, string>) => {
			let callerState = callerTuple.right()/*unknown*/;
			let caller = callerTuple.left()/*unknown*/;
			return Compiler.assembleInvokable(caller, new ConstructionCaller(callerState), args)/*unknown*/;
		})/*unknown*/).or(() => Compiler.parseValue(state, callerString).flatMap((callerTuple: Tuple2<CompileState, Value>) => Compiler.assembleInvokable(callerTuple.left(), callerTuple.right(), args)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static splitFoldedLast(input: string, delimiter: string, folder: (arg0 : DivideState, arg1 : string) => DivideState): Option<Tuple2<string, string>> {
		return Compiler.splitFolded(input, folder, (divisions1: List<string>) => Compiler.selectLast(divisions1, delimiter)/*unknown*/)/*unknown*/;
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
		return Compiler.parseValues(state, argsString, (state1: CompileState, s: string) => {
			return Compiler.parseArgument(state1, s)/*unknown*/;
		}).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState = argsTuple.left()/*unknown*/;
			let args = Compiler.retain(argsTuple.right(), (argument: Argument) => argument.toValue()/*unknown*/)/*unknown*/;
			let newCaller = Compiler.transformCaller(argsState, oldCaller)/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)))/*unknown*/;
		})/*unknown*/;
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller.findChild().flatMap((parent: Value) => {
			let parentType = Compiler.resolve0(state, parent)/*unknown*/;
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
		return Compiler.parseValue(state1, input).map((tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right())/*unknown*/)/*unknown*/;
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileFirst(input, "=", (destination: string, source: string) => {
			let sourceTuple = Compiler.compileValueOrPlaceholder(state, source)/*unknown*/;
			let destinationTuple = Compiler.compileValue(sourceTuple.left(), destination).or(() => Compiler.parseDefinition(sourceTuple.left(), destination).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, string>(tuple.left(), "let " + tuple.right().generate())/*unknown*/)/*unknown*/).orElseGet(() => new Tuple2Impl<CompileState, string>(sourceTuple.left(), Compiler.generatePlaceholder(destination))/*unknown*/)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()))/*unknown*/;
		})/*unknown*/;
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return Compiler.compileValue(state, input).orElseGet(() => new Tuple2Impl<CompileState, string>(state, Compiler.generatePlaceholder(input))/*unknown*/)/*unknown*/;
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Compiler.parseValue(state, input).map((tuple: Tuple2<CompileState, Value>) => Compiler.generateValue(tuple)/*unknown*/)/*unknown*/;
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Compiler.or(state, input, Lists.of(Compiler.parseLambda, Compiler.createOperatorRule("+"), Compiler.createOperatorRule("-"), Compiler.createOperatorRule("<="), Compiler.createOperatorRule("<"), Compiler.createOperatorRule("&&"), Compiler.createOperatorRule("||"), Compiler.createOperatorRule(">"), Compiler.createOperatorRule(">="), Compiler.parseInvokable, Compiler.createAccessRule("."), Compiler.createAccessRule("::"), Compiler.parseSymbol, Compiler.parseNot, Compiler.parseNumber, Compiler.createOperatorRuleWithDifferentInfix("==", "==="), Compiler.createOperatorRuleWithDifferentInfix("!=", "!=="), Compiler.createTextRule("\""), Compiler.createTextRule("'")))/*unknown*/;
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped = Strings.strip(input1)/*unknown*/;
			return Compiler.compilePrefix(stripped, slice, (s: string) => Compiler.compileSuffix(s, slice, (s1: string) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1)))/*unknown*/)/*unknown*/)/*unknown*/;
		}/*unknown*/;
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Compiler.compilePrefix(Strings.strip(input), "!", (withoutPrefix: string) => {
			let childTuple = Compiler.compileValueOrPlaceholder(state, withoutPrefix)/*unknown*/;
			let childState = childTuple.left()/*unknown*/;
			let child = "!" + childTuple.right()/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)))/*unknown*/;
		})/*unknown*/;
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return Compiler.compileFirst(input, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow = Strings.strip(beforeArrow)/*unknown*/;
			return Compiler.compilePrefix(strippedBeforeArrow, "(", (withoutStart: string) => {
				return Compiler.compileSuffix(withoutStart, ")", (withoutEnd: string) => {
					return Compiler.parseValues(state, withoutEnd, (state1: CompileState, s: string) => {
						return Compiler.parseParameter(state1, s)/*unknown*/;
					}).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => {
						return Compiler.compileLambdaWithParameterNames(paramNames.left(), Compiler.retainDefinitionsFromParameters(paramNames.right()), afterArrow)/*unknown*/;
					})/*unknown*/;
				})/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: Iterable<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow = Strings.strip(afterArrow)/*unknown*/;
		return Compiler.compilePrefix(strippedAfterArrow, "{", (withoutContentStart: string) => Compiler.compileSuffix(withoutContentStart, "}", (withoutContentEnd: string) => {
			let compileState: CompileState = state.enterDepth()/*unknown*/;
			let statementsTuple = Compiler.compileFunctionStatements(compileState.mapStack(stack1 -  > stack1.defineAll(paramNames)), withoutContentEnd)/*unknown*/;
			let statementsState = statementsTuple.left()/*unknown*/;
			let statements = statementsTuple.right()/*unknown*/;
			let exited = statementsState.exitDepth()/*unknown*/;
			return Compiler.assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}")/*unknown*/;
		})/*unknown*/).or(() => Compiler.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple2<CompileState, string>) => Compiler.assembleLambda(tuple.left(), paramNames, tuple.right())/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static assembleLambda(exited: CompileState, paramNames: Iterable<Definition>, content: string): Option<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)))/*unknown*/;
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return Compiler.createOperatorRuleWithDifferentInfix(infix, infix)/*unknown*/;
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state: CompileState, input: string) => Compiler.compileLast(input, infix, (childString: string, rawProperty: string) => {
			let property = Strings.strip(rawProperty)/*unknown*/;
			if (!Compiler/*unknown*/.isSymbol(property)/*unknown*/){
				return new None<Tuple2<CompileState, Value>>()/*unknown*/;
			}
			return Compiler.parseValue(state, childString).flatMap((childTuple: Tuple2<CompileState, Value>) => {
				let childState = childTuple.left()/*unknown*/;
				let child = childTuple.right()/*unknown*/;
				return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessValue(child, property)))/*unknown*/;
			})/*unknown*/;
		})/*unknown*//*unknown*/;
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => Compiler.compileSplit(Compiler.splitFolded(input1, Compiler.foldOperator(sourceInfix), (divisions: List<string>) => Compiler.selectFirst(divisions, sourceInfix)/*unknown*/), (leftString: string, rightString: string) => Compiler.parseValue(state1, leftString).flatMap((leftTuple: Tuple2<CompileState, Value>) => Compiler.parseValue(leftTuple.left(), rightString).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
			let left = leftTuple.right()/*unknown*/;
			let right = rightTuple.right()/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)))/*unknown*/;
		})/*unknown*/)/*unknown*/)/*unknown*//*unknown*/;
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
		if (Compiler.isNumber(stripped)/*unknown*/){
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
		if (Compiler.isSymbol(stripped)/*unknown*/){
			let withImport = Compiler.addResolvedImportFromCache0(state, stripped)/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new Symbol(stripped)))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, Value>>()/*unknown*/;
		}
	}
	static isSymbol(input: string): boolean {
		let query = new HeadedIter<number>(new RangeHead(Strings.length(input)))/*unknown*/;
		return query.allMatch((index: number) => Compiler.isSymbolChar(index, input.charAt(index))/*unknown*/)/*unknown*/;
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
		return Compiler.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())/*unknown*/)/*unknown*/;
	}
	static parseWhitespace(state: CompileState, input: string): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings.isBlank(input)/*unknown*/){
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()))/*unknown*/;
		}
		return new None<Tuple2<CompileState, Whitespace>>()/*unknown*/;
	}
	static compileFieldDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return Compiler.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => Compiler.getTupleOption(state, withoutEnd).or(() => Compiler.compileEnumValues(state, withoutEnd)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static getTupleOption(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Compiler.parseParameter(state, withoutEnd).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";"))/*unknown*/)/*unknown*/;
	}
	static compileEnumValues(state: CompileState, withoutEnd: string): Option<Tuple2<CompileState, string>> {
		return Compiler.parseValues(state, withoutEnd, (state1: CompileState, segment: string) => {
			let stripped = segment.strip()/*unknown*/;
			if (Compiler.isSymbol(stripped)/*unknown*/){
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state1, "\n\t static " + stripped + " = \"" + stripped + "\";"))/*unknown*/;
			}
			return Compiler.getTuple2Option(state, state1, segment)/*unknown*/;
		}).map((tuple: Tuple2<CompileState, List<string>>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().iter().collect(new Joiner("")).orElse(""))/*unknown*/)/*unknown*/;
	}
	static getTuple2Option(state: CompileState, state1: CompileState, segment: string): Option<Tuple2<CompileState, string>> {
		return Compiler.parseInvokable(state1, segment).flatMap((tuple: Tuple2<CompileState, Value>) => {
			let structureName = state.stack().findLastStructureName().orElse("")/*unknown*/;
			return tuple.right().generateAsEnumValue(structureName).map((stringOption: string) => new Tuple2Impl<CompileState, string>(tuple.left(), stringOption)/*unknown*/)/*unknown*/;
		})/*unknown*/;
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return Compiler.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input))/*unknown*/)/*unknown*/;
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return Compiler.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => Compiler.getCompileStateParameterTuple2(tuple)/*unknown*/).or(() => Compiler.parseDefinition(state, input).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/;
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return Compiler.compileLast(Strings.strip(input), " ", (beforeName: string, name: string) => Compiler.compileSplit(Compiler.splitFoldedLast(Strings.strip(beforeName), " ", Compiler.foldTypeSeparators), (beforeType: string, type: string) => Compiler.compileLast(Strings.strip(beforeType), "\n", (annotationsString: string, afterAnnotations: string) => {
			let annotations = Compiler.parseAnnotations(annotationsString)/*unknown*/;
			return Compiler.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name)/*unknown*/;
		}).or(() => Compiler.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name)/*unknown*/)/*unknown*/).or(() => Compiler.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static parseAnnotations(s: string): List<string> {
		return CompilerUtils.divide(s, (state1: DivideState, c: string) => Compiler.foldDelimited(state1, c, "\n")/*unknown*/).map((s2: string) => Strings.strip(s2)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).filter((value: string) => 1 <= Strings.length(value)/*unknown*/).map((value: string) => Strings.sliceFrom(value, 1)/*unknown*/).map((s1: string) => Strings.strip(s1)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return Compiler.compileSuffix(Strings.strip(beforeType), ">", (withoutTypeParamEnd: string) => Compiler.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams: string, typeParamsString: string) => {
			let typeParams = Compiler.divideValues(typeParamsString)/*unknown*/;
			return Compiler.parseDefinitionWithTypeParameters(state, annotations, typeParams, Compiler.parseModifiers(beforeTypeParams), type, name)/*unknown*/;
		})/*unknown*/).or(() => {
			let divided = Compiler.parseModifiers(beforeType)/*unknown*/;
			return Compiler.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name)/*unknown*/;
		})/*unknown*/;
	}
	static parseModifiers(beforeType: string): List<string> {
		return CompilerUtils.divide(Strings.strip(beforeType), (state1: DivideState, c: string) => Compiler.foldDelimited(state1, c, " ")/*unknown*/).map((s: string) => Strings.strip(s)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static foldDelimited(state1: DivideState, c: string, delimiter: string): DivideState {
		if (delimiter === c/*unknown*/){
			return state1.advance()/*unknown*/;
		}
		return state1.append(c)/*unknown*/;
	}
	static divideValues(input: string): List<string> {
		return CompilerUtils.divide(input, Compiler.foldValues).map((input1: string) => Strings.strip(input1)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
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
		return Compiler.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = Compiler.modifyModifiers(oldModifiers)/*unknown*/;
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
		return Compiler.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())/*unknown*/).orElseGet(() => new Tuple2Impl<CompileState, Type>(state, new Placeholder(type))/*unknown*/)/*unknown*/;
	}
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return Compiler.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())/*unknown*/)/*unknown*/;
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return Compiler.or(state, type, Lists.of(Compiler.parseVarArgs, Compiler.parseGeneric, Compiler.parsePrimitive, Compiler.parseSymbolType))/*unknown*/;
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input)/*unknown*/;
		return Compiler.compileSuffix(stripped, "...", (s: string) => {
			let child = Compiler.parseTypeOrPlaceholder(state, s)/*unknown*/;
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())))/*unknown*/;
		})/*unknown*/;
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (Compiler.isSymbol(stripped)/*unknown*/){
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(Compiler.addResolvedImportFromCache0(state, stripped), new Symbol(stripped)))/*unknown*/;
		}
		return new None<Tuple2<CompileState, Type>>()/*unknown*/;
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return Compiler.findPrimitiveValue(Strings.strip(input)).map((result: Type) => new Tuple2Impl<CompileState, Type>(state, result)/*unknown*/)/*unknown*/;
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
		return Compiler.compileSuffix(Strings.strip(input), ">", (withoutEnd: string) => Compiler.compileFirst(withoutEnd, "<", (baseString: string, argsString: string) => {
			let argsTuple = Compiler.parseValuesOrEmpty(state, argsString, (state1: CompileState, s: string) => Compiler.compileTypeArgument(state1, s)/*unknown*/)/*unknown*/;
			let argsState = argsTuple.left()/*unknown*/;
			let args = argsTuple.right()/*unknown*/;
			let base = Strings.strip(baseString)/*unknown*/;
			return Compiler.assembleFunctionType(argsState, base, args).or(() => {
				let compileState = Compiler.addResolvedImportFromCache0(argsState, base)/*unknown*/;
				return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)))/*unknown*/;
			})/*unknown*/;
		})/*unknown*/)/*unknown*/;
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return Compiler.mapFunctionType(base, args).map((generated: Type) => new Tuple2Impl<CompileState, Type>(state, generated)/*unknown*/)/*unknown*/;
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
		return Compiler.or(state, input, Lists.of((state2: CompileState, input1: string) => Compiler.compileWhitespace(state2, input1)/*unknown*/, (state1: CompileState, type: string) => Compiler.compileType(state1, type)/*unknown*/))/*unknown*/;
	}
	static generateValueStrings(values: Iterable<string>): string {
		return CompilerUtils.generateAll(values, Compiler.mergeValues)/*unknown*/;
	}
	static parseValuesOrEmpty<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return Compiler.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()))/*unknown*/;
	}
	static parseValues<T>(state: CompileState, input: string, mapper: (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return CompilerUtils.parseAll(state, input, Compiler.foldValues, mapper)/*unknown*/;
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
		return Compiler.compileInfix(input, infix, Compiler.findLast, mapper)/*unknown*/;
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
		return Compiler.compileInfix(input, infix, Compiler.findFirst, mapper)/*unknown*/;
	}
	static compileInfix<T>(input: string, infix: string, locator: (arg0 : string, arg1 : string) => number, mapper: (arg0 : string, arg1 : string) => Option<T>): Option<T> {
		return Compiler.compileSplit(Compiler.split(input, infix, locator), mapper)/*unknown*/;
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
		return Compiler.compileReturn(input1, (withoutPrefix: string) => Compiler.compileValue(state1, withoutPrefix)/*unknown*/).map((tuple: Tuple2<CompileState, string>) => new Tuple2Impl<CompileState, string>(tuple.left(), state1.createIndent() + tuple.right())/*unknown*/)/*unknown*/;
	}
	static compileRoot(state: CompileState, input: string, location: Location): Tuple2<CompileState, string> {
		return CompilerUtils.compileStatements(state.mapContext((context2: Context) => context2.withLocation(location)/*unknown*/), input, Compiler.compileRootSegment)/*unknown*/;
	}
	static fixNamespace(requestedNamespace: List<string>, thisNamespace: List<string>): List<string> {
		if (thisNamespace.isEmpty()/*unknown*/){
			return requestedNamespace.addFirst(".")/*unknown*/;
		}
		return Compiler.addParentSeparator(requestedNamespace, thisNamespace.size())/*unknown*/;
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
		let namespace = Compiler.fixNamespace(requestedNamespace, immutableCompileState.context().findNamespaceOrEmpty())/*unknown*/;
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
			return Compiler.getCompileState1(state, location).orElseGet(() => Compiler.getState(state, location)/*unknown*/)/*unknown*/;
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

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
	Folder: magma.app, 
	FunctionSegmentCompiler: magma.app, 
	PathSource: magma.app.io, 
	Source: magma.app.io, 
	Location: magma.app, 
	Locator: magma.app, 
	Main: magma.app, 
	PathSources: magma.app, 
	PathTargets: magma.app, 
	Platform: magma.app, 
	RootCompiler: magma.app, 
	Selector: magma.app, 
	Sources: magma.app, 
	Targets: magma.app, 
	TypeCompiler: magma.app, 
	ValueCompiler: magma.app
]*/
import { Definition } from "../../magma/app/compile/define/Definition";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Parameter } from "../../magma/app/compile/define/Parameter";
import { Iters } from "../../magma/api/collect/Iters";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { CompileState } from "../../magma/app/compile/CompileState";
import { List } from "../../magma/api/collect/list/List";
import { Tuple2 } from "../../magma/api/Tuple2";
import { CompilerUtils } from "../../magma/app/CompilerUtils";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Placeholder } from "../../magma/app/compile/value/Placeholder";
import { Option } from "../../magma/api/option/Option";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { Strings } from "../../magma/api/text/Strings";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { DivideState } from "../../magma/app/compile/DivideState";
import { TypeCompiler } from "../../magma/app/TypeCompiler";
import { Type } from "../../magma/app/compile/type/Type";
import { Joiner } from "../../magma/api/collect/Joiner";
class DefiningCompiler {
	static retainDefinitionsFromParameters(parameters: Iterable<Parameter>): Iterable<Definition> {
		return parameters.iter().map((parameter: Parameter) => parameter.asDefinition()/*unknown*/).flatMap(Iters.fromOption).collect(new ListCollector<Definition>())/*unknown*/;
	}
	static parseParameters(state: CompileState, params: string): Tuple2<CompileState, List<Parameter>> {
		return CompilerUtils.parseValuesOrEmpty(state, params, (state1: CompileState, s: string) => new Some<Tuple2<CompileState, Parameter>>(DefiningCompiler.parseParameterOrPlaceholder(state1, s))/*unknown*/)/*unknown*/;
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return DefiningCompiler.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input))/*unknown*/)/*unknown*/;
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return CompilerUtils.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => DefiningCompiler.getCompileStateParameterTuple2(tuple)/*unknown*/).or(() => DefiningCompiler.parseDefinition(state, input).map((tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/;
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return CompilerUtils.compileLast(Strings.strip(input), " ", (beforeName: string, name: string) => CompilerUtils.compileSplit(CompilerUtils.splitFoldedLast(Strings.strip(beforeName), " ", DefiningCompiler.foldTypeSeparators), (beforeType: string, type: string) => CompilerUtils.compileLast(Strings.strip(beforeType), "\n", (annotationsString: string, afterAnnotations: string) => {
			let annotations = DefiningCompiler.parseAnnotations(annotationsString)/*unknown*/;
			return DefiningCompiler.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name)/*unknown*/;
		}).or(() => DefiningCompiler.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name)/*unknown*/)/*unknown*/).or(() => DefiningCompiler.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static parseAnnotations(s: string): List<string> {
		return CompilerUtils.divide(s, (state1: DivideState, c: string) => CompilerUtils.foldDelimited(state1, c, "\n")/*unknown*/).map((s2: string) => Strings.strip(s2)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).filter((value: string) => 1 <= Strings.length(value)/*unknown*/).map((value: string) => Strings.sliceFrom(value, 1)/*unknown*/).map((s1: string) => Strings.strip(s1)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return CompilerUtils.compileSuffix(Strings.strip(beforeType), ">", (withoutTypeParamEnd: string) => CompilerUtils.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams: string, typeParamsString: string) => {
			let typeParams = CompilerUtils.divideValues(typeParamsString)/*unknown*/;
			return DefiningCompiler.parseDefinitionWithTypeParameters(state, annotations, typeParams, DefiningCompiler.parseModifiers(beforeTypeParams), type, name)/*unknown*/;
		})/*unknown*/).or(() => {
			let divided = DefiningCompiler.parseModifiers(beforeType)/*unknown*/;
			return DefiningCompiler.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name)/*unknown*/;
		})/*unknown*/;
	}
	static parseModifiers(beforeType: string): List<string> {
		return CompilerUtils.divide(Strings.strip(beforeType), (state1: DivideState, c: string) => CompilerUtils.foldDelimited(state1, c, " ")/*unknown*/).map((s: string) => Strings.strip(s)/*unknown*/).filter((value: string) => !Strings/*unknown*/.isEmpty(value)/*unknown*/).collect(new ListCollector<string>())/*unknown*/;
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
		return TypeCompiler.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = DefiningCompiler.modifyModifiers(oldModifiers)/*unknown*/;
			let generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name)/*unknown*/;
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated))/*unknown*/;
		})/*unknown*/;
	}
	static joinParameters(parameters: Iterable<Definition>): string {
		return parameters.iter().map((definition: Definition) => definition.generate()/*unknown*/).map((generated: string) => "\n\t" + generated + ";"/*unknown*/).collect(Joiner.empty()).orElse("")/*unknown*/;
	}
	static modifyModifiers(oldModifiers: List<string>): List<string> {
		if (oldModifiers.contains("static")/*unknown*/){
			return Lists.of("static")/*unknown*/;
		}
		return Lists.empty()/*unknown*/;
	}
}

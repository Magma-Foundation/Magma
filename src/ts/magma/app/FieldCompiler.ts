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
import { Option } from "../../magma/api/option/Option";
import { RootCompiler } from "../../magma/app/RootCompiler";
import { Strings } from "../../magma/api/text/Strings";
import { ConstructorHeader } from "../../magma/app/compile/define/ConstructorHeader";
import { None } from "../../magma/api/option/None";
import { Definition } from "../../magma/app/compile/define/Definition";
import { MethodHeader } from "../../magma/app/compile/define/MethodHeader";
import { DefiningCompiler } from "../../magma/app/DefiningCompiler";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
export class FieldCompiler {
	static compileMethod(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileFirst(input, "(", (beforeParams: string, withParams: string) => {
			let strippedBeforeParams = Strings.strip(beforeParams)/*unknown*/;
			return RootCompiler.compileLast(strippedBeforeParams, " ", (_: string, name: string) => {
				if (state.stack().isWithinLast(name)/*unknown*/){
					return FieldCompiler.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams)/*unknown*/;
				}
				return new None<Tuple2<CompileState, string>>()/*unknown*/;
			}).or(() => {
				if (state.stack().findLastStructureName().filter((anObject: string) => Strings.equalsTo(strippedBeforeParams, anObject)/*unknown*/).isPresent()/*unknown*/){
					return FieldCompiler.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams)/*unknown*/;
				}
				return new None<Tuple2<CompileState, string>>()/*unknown*/;
			}).or(() => RootCompiler.parseDefinition(state, beforeParams).flatMap((tuple: Tuple2<CompileState, Definition>) => FieldCompiler.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)/*unknown*/)/*unknown*/)/*unknown*/;
		})/*unknown*/;
	}
	static compileMethodWithBeforeParams(state: CompileState, header: MethodHeader, withParams: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileFirst(withParams, ")", (params: string, afterParams: string) => {
			let parametersTuple = DefiningCompiler.parseParameters(state, params)/*unknown*/;
			let parametersState = parametersTuple.left()/*unknown*/;
			let parameters = parametersTuple.right()/*unknown*/;
			let definitions = DefiningCompiler.retainDefinitionsFromParameters(parameters)/*unknown*/;
			let joinedDefinitions = definitions.iter().map((definition: Definition) => definition.generate()/*unknown*/).collect(new Joiner(", ")).orElse("")/*unknown*/;
			if (header.hasAnnotation("Actual")/*unknown*/){
				let headerGenerated = header.removeModifier("static").generateWithAfterName("(" + joinedDefinitions + ")")/*unknown*/;
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";\n"))/*unknown*/;
			}
			let headerGenerated = header.generateWithAfterName("(" + joinedDefinitions + ")")/*unknown*/;
			return RootCompiler.compilePrefix(Strings.strip(afterParams), "{", (withoutContentStart: string) => RootCompiler.compileSuffix(Strings.strip(withoutContentStart), "}", (withoutContentEnd: string) => {
				let compileState: CompileState = parametersState.enterDepth().enterDepth()/*unknown*/;
				let statementsTuple = RootCompiler.compileFunctionStatements(compileState.mapStack(stack1 -  > stack1.defineAll(definitions)), withoutContentEnd)/*unknown*/;
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(statementsTuple.left().exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right() + "\n\t}"))/*unknown*/;
			})/*unknown*/).or(() => {
				if (Strings.equalsTo(";", Strings.strip(afterParams))/*unknown*/){
					return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(parametersState, "\n\t" + headerGenerated + ";"))/*unknown*/;
				}
				return new None<Tuple2<CompileState, string>>()/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
}

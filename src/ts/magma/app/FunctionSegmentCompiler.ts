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
import { Strings } from "../../magma/api/text/Strings";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { None } from "../../magma/api/option/None";
import { RootCompiler } from "../../magma/app/RootCompiler";
import { DivideState } from "../../magma/app/compile/DivideState";
import { CompilerUtils } from "../../magma/app/CompilerUtils";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { ValueCompiler } from "../../magma/app/ValueCompiler";
import { Value } from "../../magma/app/compile/value/Value";
export class FunctionSegmentCompiler {
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo(";", Strings.strip(input))/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ";"))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compileSuffix(Strings.strip(input), "}", (withoutEnd: string) => RootCompiler.compileSplit(RootCompiler.splitFoldedLast(withoutEnd, "", FunctionSegmentCompiler.foldBlockStarts), (beforeContentWithEnd: string, content: string) => RootCompiler.compileSuffix(beforeContentWithEnd, "{", (beforeContent: string) => compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple2<CompileState, string>) => {
			let contentTuple = RootCompiler.compileFunctionStatements(headerTuple.left().enterDepth(), content)/*unknown*/;
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
		return CompilerUtils.or(state, input, Lists.of(createConditionalRule("if"), createConditionalRule("while"), FunctionSegmentCompiler.compileElse))/*unknown*/;
	}
	static createConditionalRule(prefix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, string>> {
		return (state1: CompileState, input1: string) => RootCompiler.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: string) => {
			let strippedCondition = Strings.strip(withoutPrefix)/*unknown*/;
			return RootCompiler.compilePrefix(strippedCondition, "(", (withoutConditionStart: string) => RootCompiler.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: string) => {
				let tuple = RootCompiler.compileValueOrPlaceholder(state1, withoutConditionEnd)/*unknown*/;
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
		return RootCompiler.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			let valueTuple = compileFunctionStatementValue(state, withoutEnd)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"))/*unknown*/;
		})/*unknown*/;
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileOrPlaceholder(state, withoutEnd, Lists.of(FunctionSegmentCompiler.compileReturnWithValue, RootCompiler.compileAssignment, (state1: CompileState, input: string) => ValueCompiler.parseInvokable(state1, input).map((tuple: Tuple2<CompileState, Value>) => ValueCompiler.generateValue(tuple)/*unknown*/)/*unknown*/, createPostRule("++"), createPostRule("--"), FunctionSegmentCompiler.compileBreak))/*unknown*/;
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
		return (state1: CompileState, input: string) => RootCompiler.compileSuffix(Strings.strip(input), suffix, (child: string) => {
			let tuple = RootCompiler.compileValueOrPlaceholder(state1, child)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right() + suffix))/*unknown*/;
		})/*unknown*//*unknown*/;
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return compileReturn(input, (value1: string) => RootCompiler.compileValue(state, value1)/*unknown*/)/*unknown*/;
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return RootCompiler.compilePrefix(Strings.strip(input), "return ", (value: string) => mapper(value).flatMap((tuple: Tuple2<CompileState, string>) => new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), "return " + tuple.right()))/*unknown*/)/*unknown*/)/*unknown*/;
	}
}

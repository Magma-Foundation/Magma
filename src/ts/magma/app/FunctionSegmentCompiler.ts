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
	Composable: magma.app.compile.compose, 
	Context: magma.app.compile, 
	ConstructionCaller: magma.app.compile.define, 
	ConstructorHeader: magma.app.compile.define, 
	Definition: magma.app.compile.define, 
	MethodHeader: magma.app.compile.define, 
	Parameter: magma.app.compile.define, 
	Dependency: magma.app.compile, 
	Divider: magma.app.compile.divide, 
	FoldedDivider: magma.app.compile.divide, 
	DivideState: magma.app.compile, 
	DecoratedFolder: magma.app.compile.fold, 
	DelimitedFolder: magma.app.compile.fold, 
	Folder: magma.app.compile.fold, 
	OperatorFolder: magma.app.compile.fold, 
	StatementsFolder: magma.app.compile.fold, 
	TypeSeparatorFolder: magma.app.compile.fold, 
	ValueFolder: magma.app.compile.fold, 
	ImmutableCompileState: magma.app.compile, 
	ImmutableContext: magma.app.compile, 
	ImmutableDivideState: magma.app.compile, 
	ImmutableRegistry: magma.app.compile, 
	ImmutableStack: magma.app.compile, 
	Import: magma.app.compile, 
	FirstLocator: magma.app.compile.locate, 
	LastLocator: magma.app.compile.locate, 
	Locator: magma.app.compile.locate, 
	Merger: magma.app.compile.merge, 
	StatementsMerger: magma.app.compile.merge, 
	Registry: magma.app.compile, 
	OrRule: magma.app.compile.rule, 
	Rule: magma.app.compile.rule, 
	FirstSelector: magma.app.compile.select, 
	LastSelector: magma.app.compile.select, 
	Selector: magma.app.compile.select, 
	FoldingSplitter: magma.app.compile.split, 
	LocatingSplitter: magma.app.compile.split, 
	Splitter: magma.app.compile.split, 
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
	SuffixComposable: magma.app, 
	Targets: magma.app, 
	TypeCompiler: magma.app, 
	ValueCompiler: magma.app
]*/
import { CompileState } from "../../magma/app/compile/CompileState";
import { Tuple2 } from "../../magma/api/Tuple2";
import { Option } from "../../magma/api/option/Option";
import { Strings } from "../../magma/api/text/Strings";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { None } from "../../magma/api/option/None";
import { CompilerUtils } from "../../magma/app/CompilerUtils";
import { LastSelector } from "../../magma/app/compile/select/LastSelector";
import { Selector } from "../../magma/app/compile/select/Selector";
import { FoldingSplitter } from "../../magma/app/compile/split/FoldingSplitter";
import { DivideState } from "../../magma/app/compile/DivideState";
import { OrRule } from "../../magma/app/compile/rule/OrRule";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Rule } from "../../magma/app/compile/rule/Rule";
import { ValueCompiler } from "../../magma/app/ValueCompiler";
import { Value } from "../../magma/app/compile/value/Value";
import { LocatingSplitter } from "../../magma/app/compile/split/LocatingSplitter";
import { FirstLocator } from "../../magma/app/compile/locate/FirstLocator";
import { DefiningCompiler } from "../../magma/app/DefiningCompiler";
import { Definition } from "../../magma/app/compile/define/Definition";
class FunctionSegmentCompiler {
	static compileEmptySegment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo(";", Strings.strip(input))/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ";"))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
	}
	static compileBlock(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return CompilerUtils.compileSuffix(Strings.strip(input), "}", (withoutEnd: string) => {
			return CompilerUtils.compileSplit(withoutEnd, (withoutEnd0: string) => {
				let selector: Selector = new LastSelector("")/*unknown*/;
				return new FoldingSplitter((state1: DivideState, c: string) => {
					return FunctionSegmentCompiler.foldBlockStarts(state1, c)/*unknown*/;
				}, selector).apply(withoutEnd0)/*unknown*/;
			}, (beforeContentWithEnd: string, content: string) => {
				return CompilerUtils.compileSuffix(beforeContentWithEnd, "{", (beforeContent: string) => {
					return FunctionSegmentCompiler.compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple2<CompileState, string>) => {
						let contentTuple = FunctionSegmentCompiler.compileFunctionStatements(headerTuple.left().enterDepth(), content)/*unknown*/;
						let indent = state.createIndent()/*unknown*/;
						return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"))/*unknown*/;
					})/*unknown*/;
				})/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
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
		return new OrRule<string>(Lists.of(FunctionSegmentCompiler.createConditionalRule("if"), FunctionSegmentCompiler.createConditionalRule("while"), FunctionSegmentCompiler.compileElse)).apply(state, input)/*unknown*/;
	}
	static createConditionalRule(prefix: string): Rule<string> {
		return (state1: CompileState, input1: string) => {
			return CompilerUtils.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: string) => {
				let strippedCondition = Strings.strip(withoutPrefix)/*unknown*/;
				return CompilerUtils.compilePrefix(strippedCondition, "(", (withoutConditionStart: string) => {
					return CompilerUtils.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: string) => {
						let tuple = ValueCompiler.compileValueOrPlaceholder(state1, withoutConditionEnd)/*unknown*/;
						return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), prefix + " (" + tuple.right() + ")"))/*unknown*/;
					})/*unknown*/;
				})/*unknown*/;
			})/*unknown*/;
		}/*unknown*/;
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
		return CompilerUtils.compileSuffix(Strings.strip(input), ";", (withoutEnd: string) => {
			let valueTuple = FunctionSegmentCompiler.compileFunctionStatementValue(state, withoutEnd)/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(valueTuple.left(), state.createIndent() + valueTuple.right() + ";"))/*unknown*/;
		})/*unknown*/;
	}
	static compileFunctionStatementValue(state: CompileState, withoutEnd: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileOrPlaceholder(state, withoutEnd, Lists.of(FunctionSegmentCompiler.compileReturnWithValue, FunctionSegmentCompiler.compileAssignment, (state1: CompileState, input: string) => {
			return ValueCompiler.parseInvokable(state1, input).map((tuple: Tuple2<CompileState, Value>) => {
				return ValueCompiler.generateValue(tuple)/*unknown*/;
			})/*unknown*/;
		}, FunctionSegmentCompiler.createPostRule("++"), FunctionSegmentCompiler.createPostRule("--"), FunctionSegmentCompiler.compileBreak))/*unknown*/;
	}
	static compileBreak(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		if (Strings.equalsTo("break", Strings.strip(input))/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, "break"))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
	}
	static createPostRule(suffix: string): Rule<string> {
		return (state1: CompileState, input: string) => {
			return CompilerUtils.compileSuffix(Strings.strip(input), suffix, (child: string) => {
				let tuple = ValueCompiler.compileValueOrPlaceholder(state1, child)/*unknown*/;
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right() + suffix))/*unknown*/;
			})/*unknown*/;
		}/*unknown*/;
	}
	static compileReturnWithValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return FunctionSegmentCompiler.compileReturn(input, (value1: string) => {
			return ValueCompiler.compileValue(state, value1)/*unknown*/;
		})/*unknown*/;
	}
	static compileReturn(input: string, mapper: (arg0 : string) => Option<Tuple2<CompileState, string>>): Option<Tuple2<CompileState, string>> {
		return CompilerUtils.compilePrefix(Strings.strip(input), "return ", (value: string) => {
			return mapper(value).flatMap((tuple: Tuple2<CompileState, string>) => {
				return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(tuple.left(), "return " + tuple.right()))/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static compileReturnWithoutSuffix(state1: CompileState, input1: string): Option<Tuple2<CompileState, string>> {
		return FunctionSegmentCompiler.compileReturn(input1, (withoutPrefix: string) => {
			return ValueCompiler.compileValue(state1, withoutPrefix)/*unknown*/;
		}).map((tuple: Tuple2<CompileState, string>) => {
			return new Tuple2Impl<CompileState, string>(tuple.left(), state1.createIndent() + tuple.right())/*unknown*/;
		})/*unknown*/;
	}
	static compileAssignment(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return CompilerUtils.compileSplit(input, new LocatingSplitter("=", new FirstLocator()), (destination: string, source: string) => {
			let sourceTuple = ValueCompiler.compileValueOrPlaceholder(state, source)/*unknown*/;
			let destinationTuple = ValueCompiler.compileValue(sourceTuple.left(), destination).or(() => {
				return DefiningCompiler.parseDefinition(sourceTuple.left(), destination).map((tuple: Tuple2<CompileState, Definition>) => {
					return new Tuple2Impl<CompileState, string>(tuple.left(), "let " + tuple.right().generate())/*unknown*/;
				})/*unknown*/;
			}).orElseGet(() => {
				return new Tuple2Impl<CompileState, string>(sourceTuple.left(), CompilerUtils.generatePlaceholder(destination))/*unknown*/;
			})/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()))/*unknown*/;
		})/*unknown*/;
	}
	static compileFunctionStatements(state: CompileState, input: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileStatements(state, input, FunctionSegmentCompiler.compileFunctionSegment)/*unknown*/;
	}
	static compileFunctionSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return CompilerUtils.compileOrPlaceholder(state, input, Lists.of(CompilerUtils.compileWhitespace, FunctionSegmentCompiler.compileEmptySegment, FunctionSegmentCompiler.compileBlock, FunctionSegmentCompiler.compileFunctionStatement, FunctionSegmentCompiler.compileReturnWithoutSuffix))/*unknown*/;
	}
}

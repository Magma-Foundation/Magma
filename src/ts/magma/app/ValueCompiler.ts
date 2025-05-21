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
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Value } from "../../magma/app/compile/value/Value";
import { Tuple2 } from "../../magma/api/Tuple2";
import { RootCompiler } from "../../magma/app/RootCompiler";
import { Option } from "../../magma/api/option/Option";
import { Strings } from "../../magma/api/text/Strings";
import { ConstructionCaller } from "../../magma/app/compile/define/ConstructionCaller";
import { Some } from "../../magma/api/option/Some";
import { StringValue } from "../../magma/app/compile/value/StringValue";
import { Not } from "../../magma/app/compile/value/Not";
import { Parameter } from "../../magma/app/compile/define/Parameter";
import { List } from "../../magma/api/collect/list/List";
import { DefiningCompiler } from "../../magma/app/DefiningCompiler";
import { Definition } from "../../magma/app/compile/define/Definition";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Lambda } from "../../magma/app/compile/value/Lambda";
import { None } from "../../magma/api/option/None";
import { AccessValue } from "../../magma/app/compile/value/AccessValue";
import { Operation } from "../../magma/app/compile/value/Operation";
export class ValueCompiler {
	static generateValue(tuple: Tuple2<CompileState, Value>): Tuple2Impl<CompileState, string> {
		let state = tuple.left()/*unknown*/;
		let right = tuple.right()/*unknown*/;
		let generated = right.generate()/*unknown*/;
		let s = RootCompiler.generatePlaceholder(RootCompiler.resolve0(state, right).generate())/*unknown*/;
		return new Tuple2Impl<CompileState, string>(state, generated + s)/*unknown*/;
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return RootCompiler.compileSuffix(Strings.strip(input), ")", (withoutEnd: string) => RootCompiler.compileSplit(RootCompiler.splitFoldedLast(withoutEnd, "", RootCompiler.foldInvocationStarts), (callerWithArgStart: string, args: string) => RootCompiler.compileSuffix(callerWithArgStart, "(", (callerString: string) => RootCompiler.compilePrefix(Strings.strip(callerString), "new ", (type: string) => RootCompiler.compileType(state, type).flatMap((callerTuple: Tuple2<CompileState, string>) => {
			let callerState = callerTuple.right()/*unknown*/;
			let caller = callerTuple.left()/*unknown*/;
			return RootCompiler.assembleInvokable(caller, new ConstructionCaller(callerState), args)/*unknown*/;
		})/*unknown*/).or(() => RootCompiler.parseValue(state, callerString).flatMap((callerTuple: Tuple2<CompileState, Value>) => RootCompiler.assembleInvokable(callerTuple.left(), callerTuple.right(), args)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static createTextRule(slice: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => {
			let stripped = Strings.strip(input1)/*unknown*/;
			return RootCompiler.compilePrefix(stripped, slice, (s: string) => RootCompiler.compileSuffix(s, slice, (s1: string) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1)))/*unknown*/)/*unknown*/)/*unknown*/;
		}/*unknown*/;
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return RootCompiler.compilePrefix(Strings.strip(input), "!", (withoutPrefix: string) => {
			let childTuple = RootCompiler.compileValueOrPlaceholder(state, withoutPrefix)/*unknown*/;
			let childState = childTuple.left()/*unknown*/;
			let child = "!" + childTuple.right()/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)))/*unknown*/;
		})/*unknown*/;
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return RootCompiler.compileFirst(input, "->", (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow = Strings.strip(beforeArrow)/*unknown*/;
			return RootCompiler.compilePrefix(strippedBeforeArrow, "(", (withoutStart: string) => {
				return RootCompiler.compileSuffix(withoutStart, ")", (withoutEnd: string) => {
					return RootCompiler.parseValues(state, withoutEnd, (state1: CompileState, s: string) => {
						return RootCompiler.parseParameter(state1, s)/*unknown*/;
					}).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => {
						return compileLambdaWithParameterNames(paramNames.left(), DefiningCompiler.retainDefinitionsFromParameters(paramNames.right()), afterArrow)/*unknown*/;
					})/*unknown*/;
				})/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: Iterable<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow = Strings.strip(afterArrow)/*unknown*/;
		return RootCompiler.compilePrefix(strippedAfterArrow, "{", (withoutContentStart: string) => RootCompiler.compileSuffix(withoutContentStart, "}", (withoutContentEnd: string) => {
			let compileState: CompileState = state.enterDepth()/*unknown*/;
			let statementsTuple = RootCompiler.compileFunctionStatements(compileState.mapStack(stack1 -  > stack1.defineAll(paramNames)), withoutContentEnd)/*unknown*/;
			let statementsState = statementsTuple.left()/*unknown*/;
			let statements = statementsTuple.right()/*unknown*/;
			let exited = statementsState.exitDepth()/*unknown*/;
			return assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}")/*unknown*/;
		})/*unknown*/).or(() => RootCompiler.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple2<CompileState, string>) => assembleLambda(tuple.left(), paramNames, tuple.right())/*unknown*/)/*unknown*/)/*unknown*/;
	}
	static assembleLambda(exited: CompileState, paramNames: Iterable<Definition>, content: string): Option<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)))/*unknown*/;
	}
	static createOperatorRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return createOperatorRuleWithDifferentInfix(infix, infix)/*unknown*/;
	}
	static createAccessRule(infix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state: CompileState, input: string) => RootCompiler.compileLast(input, infix, (childString: string, rawProperty: string) => {
			let property = Strings.strip(rawProperty)/*unknown*/;
			if (!RootCompiler/*unknown*/.isSymbol(property)/*unknown*/){
				return new None<Tuple2<CompileState, Value>>()/*unknown*/;
			}
			return RootCompiler.parseValue(state, childString).flatMap((childTuple: Tuple2<CompileState, Value>) => {
				let childState = childTuple.left()/*unknown*/;
				let child = childTuple.right()/*unknown*/;
				return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessValue(child, property)))/*unknown*/;
			})/*unknown*/;
		})/*unknown*//*unknown*/;
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): (arg0 : CompileState, arg1 : string) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: string) => RootCompiler.compileSplit(RootCompiler.splitFolded(input1, RootCompiler.foldOperator(sourceInfix), (divisions: List<string>) => RootCompiler.selectFirst(divisions, sourceInfix)/*unknown*/), (leftString: string, rightString: string) => RootCompiler.parseValue(state1, leftString).flatMap((leftTuple: Tuple2<CompileState, Value>) => RootCompiler.parseValue(leftTuple.left(), rightString).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
			let left = leftTuple.right()/*unknown*/;
			let right = rightTuple.right()/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)))/*unknown*/;
		})/*unknown*/)/*unknown*/)/*unknown*//*unknown*/;
	}
}

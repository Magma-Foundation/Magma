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
	PrefixComposable: magma.app.compile.compose, 
	SplitComposable: magma.app.compile.compose, 
	SuffixComposable: magma.app.compile.compose, 
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
	ValueMerger: magma.app.compile.merge, 
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
	ValueUtils: magma.app.compile, 
	CompilerUtils: magma.app, 
	DefiningCompiler: magma.app, 
	DefinitionCompiler: magma.app, 
	DivideRule: magma.app, 
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
	TypeCompiler: magma.app, 
	ValueCompiler: magma.app, 
	WhitespaceCompiler: magma.app
]*/
import { CompileState } from "../../magma/app/compile/CompileState";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Value } from "../../magma/app/compile/value/Value";
import { Tuple2 } from "../../magma/api/Tuple2";
import { CompilerUtils } from "../../magma/app/CompilerUtils";
import { Option } from "../../magma/api/option/Option";
import { SuffixComposable } from "../../magma/app/compile/compose/SuffixComposable";
import { SplitComposable } from "../../magma/app/compile/compose/SplitComposable";
import { LastSelector } from "../../magma/app/compile/select/LastSelector";
import { Selector } from "../../magma/app/compile/select/Selector";
import { FoldingSplitter } from "../../magma/app/compile/split/FoldingSplitter";
import { DivideState } from "../../magma/app/compile/DivideState";
import { PrefixComposable } from "../../magma/app/compile/compose/PrefixComposable";
import { TypeCompiler } from "../../magma/app/TypeCompiler";
import { ConstructionCaller } from "../../magma/app/compile/define/ConstructionCaller";
import { Strings } from "../../magma/api/text/Strings";
import { RootCompiler } from "../../magma/app/RootCompiler";
import { Rule } from "../../magma/app/compile/rule/Rule";
import { Some } from "../../magma/api/option/Some";
import { StringValue } from "../../magma/app/compile/value/StringValue";
import { Not } from "../../magma/app/compile/value/Not";
import { LocatingSplitter } from "../../magma/app/compile/split/LocatingSplitter";
import { FirstLocator } from "../../magma/app/compile/locate/FirstLocator";
import { ValueUtils } from "../../magma/app/compile/ValueUtils";
import { DefiningCompiler } from "../../magma/app/DefiningCompiler";
import { Parameter } from "../../magma/app/compile/define/Parameter";
import { List } from "../../magma/api/collect/list/List";
import { Definition } from "../../magma/app/compile/define/Definition";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { FunctionSegmentCompiler } from "../../magma/app/FunctionSegmentCompiler";
import { Stack } from "../../magma/app/compile/Stack";
import { Lambda } from "../../magma/app/compile/value/Lambda";
import { None } from "../../magma/api/option/None";
import { AccessValue } from "../../magma/app/compile/value/AccessValue";
import { OperatorFolder } from "../../magma/app/compile/fold/OperatorFolder";
import { FirstSelector } from "../../magma/app/compile/select/FirstSelector";
import { Operation } from "../../magma/app/compile/value/Operation";
import { Symbol } from "../../magma/app/compile/value/Symbol";
import { HeadedIter } from "../../magma/api/collect/head/HeadedIter";
import { RangeHead } from "../../magma/api/collect/head/RangeHead";
import { Characters } from "../../magma/api/text/Characters";
import { Type } from "../../magma/app/compile/type/Type";
import { Argument } from "../../magma/app/compile/value/Argument";
import { Caller } from "../../magma/app/compile/value/Caller";
import { Invokable } from "../../magma/app/compile/value/Invokable";
import { Iters } from "../../magma/api/collect/Iters";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
class ValueCompiler {
	static generateValue(tuple: Tuple2<CompileState, Value>): Tuple2Impl<CompileState, string> {
		let state = tuple.left()/*unknown*/;
		let right = tuple.right()/*unknown*/;
		let generated = right.generate()/*unknown*/;
		let s = CompilerUtils.generatePlaceholder(ValueCompiler.resolve(state, right).generate())/*unknown*/;
		return new Tuple2Impl<CompileState, string>(state, generated + s)/*unknown*/;
	}
	static parseInvokable(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return new SuffixComposable<Tuple2<CompileState, Value>>(")", (withoutEnd: string) => {
			return SplitComposable.compileSplit(withoutEnd, (withoutEnd0: string) => {
				let selector: Selector = new LastSelector("")/*unknown*/;
				return new FoldingSplitter((state1: DivideState, c: string) => {
					return ValueCompiler.foldInvocationStarts(state1, c)/*unknown*/;
				}, selector).apply(withoutEnd0)/*unknown*/;
			}, (callerWithArgStart: string, args: string) => {
				return new SuffixComposable<Tuple2<CompileState, Value>>("(", (callerString: string) => {
					return new PrefixComposable<Tuple2<CompileState, Value>>("new ", (type: string) => {
						return TypeCompiler.compileType(state, type).flatMap((callerTuple1: Tuple2<CompileState, string>) => {
							let callerState = callerTuple1.right()/*unknown*/;
							let caller = callerTuple1.left()/*unknown*/;
							return ValueCompiler.assembleInvokable(caller, new ConstructionCaller(callerState), args)/*unknown*/;
						})/*unknown*/;
					}).apply(Strings.strip(callerString)).or(() => {
						return RootCompiler.parseValue(state, callerString).flatMap((callerTuple: Tuple2<CompileState, Value>) => {
							return ValueCompiler.assembleInvokable(callerTuple.left(), callerTuple.right(), args)/*unknown*/;
						})/*unknown*/;
					})/*unknown*/;
				}).apply(callerWithArgStart)/*unknown*/;
			})/*unknown*/;
		}).apply(Strings.strip(input))/*unknown*/;
	}
	static createTextRule(slice: string): Rule<Value> {
		return (state1: CompileState, input1: string) => {
			let stripped = Strings.strip(input1)/*unknown*/;
			return new PrefixComposable<Tuple2<CompileState, Value>>(slice, (s: string) => {
				return new SuffixComposable<Tuple2<CompileState, Value>>(slice, (s1: string) => {
					return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1)))/*unknown*/;
				}).apply(s)/*unknown*/;
			}).apply(stripped)/*unknown*/;
		}/*unknown*/;
	}
	static parseNot(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return new PrefixComposable<Tuple2<CompileState, Value>>("!", (withoutPrefix: string) => {
			let childTuple = ValueCompiler.compileValueOrPlaceholder(state, withoutPrefix)/*unknown*/;
			let childState = childTuple.left()/*unknown*/;
			let child = "!" + childTuple.right()/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)))/*unknown*/;
		}).apply(Strings.strip(input))/*unknown*/;
	}
	static parseLambda(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return SplitComposable.compileSplit(input, new LocatingSplitter("->", new FirstLocator()), (beforeArrow: string, afterArrow: string) => {
			let strippedBeforeArrow = Strings.strip(beforeArrow)/*unknown*/;
			return new PrefixComposable<Tuple2<CompileState, Value>>("(", (withoutStart: string) => {
				return new SuffixComposable<Tuple2<CompileState, Value>>(")", (withoutEnd: string) => {
					return ValueUtils.parseValues(state, withoutEnd, (state1: CompileState, s: string) => {
						return DefiningCompiler.parseParameter(state1, s)/*unknown*/;
					}).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => {
						return ValueCompiler.compileLambdaWithParameterNames(paramNames.left(), DefiningCompiler.retainDefinitionsFromParameters(paramNames.right()), afterArrow)/*unknown*/;
					})/*unknown*/;
				}).apply(withoutStart)/*unknown*/;
			}).apply(strippedBeforeArrow)/*unknown*/;
		})/*unknown*/;
	}
	static compileLambdaWithParameterNames(state: CompileState, paramNames: Iterable<Definition>, afterArrow: string): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow = Strings.strip(afterArrow)/*unknown*/;
		return new PrefixComposable<Tuple2<CompileState, Value>>("{", (withoutContentStart: string) => {
			return new SuffixComposable<Tuple2<CompileState, Value>>("}", (withoutContentEnd: string) => {
				let compileState: CompileState = state.enterDepth()/*unknown*/;
				let statementsTuple = FunctionSegmentCompiler.compileFunctionStatements(compileState.mapStack((stack1: Stack) => {
					return stack1.defineAll(paramNames)/*unknown*/;
				}), withoutContentEnd)/*unknown*/;
				let statementsState = statementsTuple.left()/*unknown*/;
				let statements = statementsTuple.right()/*unknown*/;
				let exited = statementsState.exitDepth()/*unknown*/;
				return ValueCompiler.assembleLambda(exited, paramNames, "{" + statements + exited.createIndent() + "}")/*unknown*/;
			}).apply(withoutContentStart)/*unknown*/;
		}).apply(strippedAfterArrow).or(() => {
			return ValueCompiler.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple2<CompileState, string>) => {
				return ValueCompiler.assembleLambda(tuple.left(), paramNames, tuple.right())/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static assembleLambda(exited: CompileState, paramNames: Iterable<Definition>, content: string): Option<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)))/*unknown*/;
	}
	static createOperatorRule(infix: string): Rule<Value> {
		return ValueCompiler.createOperatorRuleWithDifferentInfix(infix, infix)/*unknown*/;
	}
	static createAccessRule(infix: string): Rule<Value> {
		return (state: CompileState, input: string) => {
			return SplitComposable.compileLast(input, infix, (childString: string, rawProperty: string) => {
				let property = Strings.strip(rawProperty)/*unknown*/;
				if (!ValueCompiler/*unknown*/.isSymbol(property)/*unknown*/){
					return new None<Tuple2<CompileState, Value>>()/*unknown*/;
				}
				return RootCompiler.parseValue(state, childString).flatMap((childTuple: Tuple2<CompileState, Value>) => {
					let childState = childTuple.left()/*unknown*/;
					let child = childTuple.right()/*unknown*/;
					return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new AccessValue(child, property)))/*unknown*/;
				})/*unknown*/;
			})/*unknown*/;
		}/*unknown*/;
	}
	static createOperatorRuleWithDifferentInfix(sourceInfix: string, targetInfix: string): Rule<Value> {
		return (state1: CompileState, input1: string) => {
			return SplitComposable.compileSplit(input1, (slice: string) => {
				return new FoldingSplitter(new OperatorFolder(sourceInfix), (divisions: List<string>) => {
					return new FirstSelector(sourceInfix).select(divisions)/*unknown*/;
				}).apply(slice)/*unknown*/;
			}, (leftString: string, rightString: string) => {
				return RootCompiler.parseValue(state1, leftString).flatMap((leftTuple: Tuple2<CompileState, Value>) => {
					return RootCompiler.parseValue(leftTuple.left(), rightString).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
						let left = leftTuple.right()/*unknown*/;
						let right = rightTuple.right()/*unknown*/;
						return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)))/*unknown*/;
					})/*unknown*/;
				})/*unknown*/;
			})/*unknown*/;
		}/*unknown*/;
	}
	static parseSymbol(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (ValueCompiler.isSymbol(stripped)/*unknown*/){
			let withImport = TypeCompiler.addResolvedImportFromCache0(state, stripped)/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new Symbol(stripped)))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, Value>>()/*unknown*/;
		}
	}
	static isSymbol(input: string): boolean {
		let query = new HeadedIter<number>(new RangeHead(Strings.length(input)))/*unknown*/;
		return query.allMatch((index: number) => {
			return ValueCompiler.isSymbolChar(index, input.charAt(index))/*unknown*/;
		})/*unknown*/;
	}
	static isSymbolChar(index: number, c: string): boolean {
		return "_" === c || Characters.isLetter(c) || (0 !== index && Characters.isDigit(c))/*unknown*/;
	}
	static isNumber(input: string): boolean {
		let query = new HeadedIter<number>(new RangeHead(Strings.length(input)))/*unknown*/;
		return query.map(input.charAt).allMatch((c: string) => {
			return Characters.isDigit(c)/*unknown*/;
		})/*unknown*/;
	}
	static resolve(state: CompileState, value: Value): Type {/*return switch (value) {
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
	static parseNumber(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (ValueCompiler.isNumber(stripped)/*unknown*/){
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new Symbol(stripped)))/*unknown*/;
		}
		else {
			return new None<Tuple2<CompileState, Value>>()/*unknown*/;
		}
	}
	static compileValueOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, string> {
		return ValueCompiler.compileValue(state, input).orElseGet(() => {
			return new Tuple2Impl<CompileState, string>(state, CompilerUtils.generatePlaceholder(input))/*unknown*/;
		})/*unknown*/;
	}
	static compileValue(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return RootCompiler.parseValue(state, input).map((tuple: Tuple2<CompileState, Value>) => {
			return ValueCompiler.generateValue(tuple)/*unknown*/;
		})/*unknown*/;
	}
	static parseArgument(state1: CompileState, input: string): Option<Tuple2<CompileState, Argument>> {
		return RootCompiler.parseValue(state1, input).map((tuple: Tuple2<CompileState, Value>) => {
			return new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right())/*unknown*/;
		})/*unknown*/;
	}
	static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller.findChild().flatMap((parent: Value) => {
			let parentType = ValueCompiler.resolve(state, parent)/*unknown*/;
			if (parentType.isFunctional()/*unknown*/){
				return new Some<Caller>(parent)/*unknown*/;
			}
			return new None<Caller>()/*unknown*/;
		}).orElse(oldCaller)/*unknown*/;
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
		return ValueUtils.parseValues(state, argsString, (state1: CompileState, s: string) => {
			return ValueCompiler.parseArgument(state1, s)/*unknown*/;
		}).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState = argsTuple.left()/*unknown*/;
			let args = retain(argsTuple.right(), (argument: Argument) => {
				return argument.toValue()/*unknown*/;
			})/*unknown*/;
			let newCaller = ValueCompiler.transformCaller(argsState, oldCaller)/*unknown*/;
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)))/*unknown*/;
		})/*unknown*/;
	}
	static retain<T, R>(args: Iterable<T>, mapper: (arg0 : T) => Option<R>): Iterable<R> {
		return args.iter().map(mapper).flatMap(Iters.fromOption).collect(new ListCollector<R>())/*unknown*/;
	}
}

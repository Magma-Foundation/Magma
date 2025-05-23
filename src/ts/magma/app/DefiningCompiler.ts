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
	Sequence: magma.api.collect.list, 
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
import { Definition } from "../../magma/app/compile/define/Definition";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Parameter } from "../../magma/app/compile/define/Parameter";
import { Iters } from "../../magma/api/collect/Iters";
import { ListCollector } from "../../magma/api/collect/list/ListCollector";
import { CompileState } from "../../magma/app/compile/CompileState";
import { List } from "../../magma/api/collect/list/List";
import { Tuple2 } from "../../magma/api/Tuple2";
import { ValueCompiler } from "../../magma/app/ValueCompiler";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Placeholder } from "../../magma/app/compile/value/Placeholder";
import { Option } from "../../magma/api/option/Option";
import { WhitespaceCompiler } from "../../magma/app/WhitespaceCompiler";
import { Whitespace } from "../../magma/app/compile/text/Whitespace";
import { SplitComposable } from "../../magma/app/compile/compose/SplitComposable";
import { Strings } from "../../magma/api/text/Strings";
import { LastSelector } from "../../magma/app/compile/select/LastSelector";
import { Selector } from "../../magma/app/compile/select/Selector";
import { FoldingSplitter } from "../../magma/app/compile/split/FoldingSplitter";
import { TypeSeparatorFolder } from "../../magma/app/compile/fold/TypeSeparatorFolder";
import { Composable } from "../../magma/app/compile/compose/Composable";
import { FoldedDivider } from "../../magma/app/compile/divide/FoldedDivider";
import { DecoratedFolder } from "../../magma/app/compile/fold/DecoratedFolder";
import { DivideState } from "../../magma/app/compile/DivideState";
import { DelimitedFolder } from "../../magma/app/compile/fold/DelimitedFolder";
import { SuffixComposable } from "../../magma/app/compile/compose/SuffixComposable";
import { LocatingSplitter } from "../../magma/app/compile/split/LocatingSplitter";
import { FirstLocator } from "../../magma/app/compile/locate/FirstLocator";
import { Splitter } from "../../magma/app/compile/split/Splitter";
import { TypeCompiler } from "../../magma/app/TypeCompiler";
import { Type } from "../../magma/app/compile/type/Type";
import { Joiner } from "../../magma/api/collect/Joiner";
import { ValueFolder } from "../../magma/app/compile/fold/ValueFolder";
class DefiningCompiler {
	static retainDefinitionsFromParameters(parameters: Iterable<Parameter>): Iterable<Definition> {
		return parameters.iter().map((parameter: Parameter) => {
			return parameter.asDefinition()/*unknown*/;
		}).flatMap(Iters.fromOption).collect(new ListCollector<Definition>())/*unknown*/;
	}
	static parseParameters(state: CompileState, params: string): Tuple2<CompileState, List<Parameter>> {
		return ValueCompiler.values((state1: CompileState, s: string) => {
			return new Some<Tuple2<CompileState, Parameter>>(DefiningCompiler.parseParameterOrPlaceholder(state1, s))/*unknown*/;
		}).apply(state, params).orElse(new Tuple2Impl<CompileState, List<Parameter>>(state, Lists.empty()))/*unknown*/;
	}
	static parseParameterOrPlaceholder(state: CompileState, input: string): Tuple2<CompileState, Parameter> {
		return DefiningCompiler.parseParameter(state, input).orElseGet(() => {
			return new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input))/*unknown*/;
		})/*unknown*/;
	}
	static parseParameter(state: CompileState, input: string): Option<Tuple2<CompileState, Parameter>> {
		return WhitespaceCompiler.parseWhitespace(state, input).map((tuple: Tuple2<CompileState, Whitespace>) => {
			return DefiningCompiler.getCompileStateParameterTuple2(tuple)/*unknown*/;
		}).or(() => {
			return DefiningCompiler.parseDefinition(state, input).map((tuple: Tuple2<CompileState, Definition>) => {
				return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())/*unknown*/;
	}
	static parseDefinition(state: CompileState, input: string): Option<Tuple2<CompileState, Definition>> {
		return SplitComposable.compileLast(Strings.strip(input), " ", (beforeName: string, name: string) => {
			return new SplitComposable<Tuple2<CompileState, Definition>>((beforeName0: string) => {
				let selector: Selector = new LastSelector(" ")/*unknown*/;
				return new FoldingSplitter(new TypeSeparatorFolder(), selector).apply(Strings.strip(beforeName0))/*unknown*/;
			}, Composable.toComposable((beforeType: string, type: string) => {
				return SplitComposable.compileLast(Strings.strip(beforeType), "\n", (annotationsString: string, afterAnnotations: string) => {
					let annotations = DefiningCompiler.parseAnnotations(annotationsString)/*unknown*/;
					return DefiningCompiler.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name)/*unknown*/;
				}).or(() => {
					return DefiningCompiler.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name)/*unknown*/;
				})/*unknown*/;
			})).apply(beforeName).or(() => {
				return DefiningCompiler.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static parseAnnotations(s: string): List<string> {
		return new FoldedDivider(new DecoratedFolder((state1: DivideState, c: string) => {
			return new DelimitedFolder("\n").apply(state1, c)/*unknown*/;
		})).divide(s).map((s2: string) => {
			return Strings.strip(s2)/*unknown*/;
		}).filter((value: string) => {
			return !Strings/*unknown*/.isEmpty(value)/*unknown*/;
		}).filter((value: string) => {
			return 1 <= Strings.length(value)/*unknown*/;
		}).map((value: string) => {
			return Strings.sliceFrom(value, 1)/*unknown*/;
		}).map((s1: string) => {
			return Strings.strip(s1)/*unknown*/;
		}).filter((value: string) => {
			return !Strings/*unknown*/.isEmpty(value)/*unknown*/;
		}).collect(new ListCollector<string>())/*unknown*/;
	}
	static parseDefinitionWithAnnotations(state: CompileState, annotations: List<string>, beforeType: string, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return new SuffixComposable<Tuple2<CompileState, Definition>>(">", (withoutTypeParamEnd: string) => {
			let splitter: Splitter = new LocatingSplitter("<", new FirstLocator())/*unknown*/;
			return new SplitComposable<Tuple2<CompileState, Definition>>(splitter, Composable.toComposable((beforeTypeParams: string, typeParamsString: string) => {
				let typeParams = DefiningCompiler.divideValues(typeParamsString)/*unknown*/;
				return DefiningCompiler.parseDefinitionWithTypeParameters(state, annotations, typeParams, DefiningCompiler.parseModifiers(beforeTypeParams), type, name)/*unknown*/;
			})).apply(withoutTypeParamEnd)/*unknown*/;
		}).apply(Strings.strip(beforeType)).or(() => {
			let divided = DefiningCompiler.parseModifiers(beforeType)/*unknown*/;
			return DefiningCompiler.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name)/*unknown*/;
		})/*unknown*/;
	}
	static parseModifiers(beforeType: string): List<string> {
		return new FoldedDivider(new DecoratedFolder((state1: DivideState, c: string) => {
			return new DelimitedFolder(" ").apply(state1, c)/*unknown*/;
		})).divide(Strings.strip(beforeType)).map((s: string) => {
			return Strings.strip(s)/*unknown*/;
		}).filter((value: string) => {
			return !Strings/*unknown*/.isEmpty(value)/*unknown*/;
		}).collect(new ListCollector<string>())/*unknown*/;
	}
	static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<string>, typeParams: List<string>, oldModifiers: List<string>, type: string, name: string): Option<Tuple2<CompileState, Definition>> {
		return TypeCompiler.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = DefiningCompiler.modifyModifiers(oldModifiers)/*unknown*/;
			let generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name)/*unknown*/;
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated))/*unknown*/;
		})/*unknown*/;
	}
	static joinParameters(parameters: Iterable<Definition>): string {
		return parameters.iter().map((definition: Definition) => {
			return definition.generate()/*unknown*/;
		}).map((generated: string) => {
			return "\n\t" + generated + ";"/*unknown*/;
		}).collect(Joiner.empty()).orElse("")/*unknown*/;
	}
	static modifyModifiers(oldModifiers: List<string>): List<string> {
		if (oldModifiers.contains("static")/*unknown*/){
			return Lists.of("static")/*unknown*/;
		}
		return Lists.empty()/*unknown*/;
	}
	static divideValues(input: string): List<string> {
		return new FoldedDivider(new DecoratedFolder(new ValueFolder())).divide(input).map((input1: string) => {
			return Strings.strip(input1)/*unknown*/;
		}).filter((value: string) => {
			return !Strings/*unknown*/.isEmpty(value)/*unknown*/;
		}).collect(new ListCollector<string>())/*unknown*/;
	}
}

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
import { Tuple2 } from "../../magma/api/Tuple2";
import { OrRule } from "../../magma/app/compile/rule/OrRule";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { WhitespaceCompiler } from "../../magma/app/WhitespaceCompiler";
import { Rule } from "../../magma/app/compile/rule/Rule";
import { SplitComposable } from "../../magma/app/compile/compose/SplitComposable";
import { LocatingSplitter } from "../../magma/app/compile/split/LocatingSplitter";
import { FirstLocator } from "../../magma/app/compile/locate/FirstLocator";
import { SuffixComposable } from "../../magma/app/compile/compose/SuffixComposable";
import { DefiningCompiler } from "../../magma/app/DefiningCompiler";
import { Some } from "../../magma/api/option/Some";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { Strings } from "../../magma/api/text/Strings";
import { Option } from "../../magma/api/option/Option";
import { List } from "../../magma/api/collect/list/List";
import { TypeCompiler } from "../../magma/app/TypeCompiler";
import { Type } from "../../magma/app/compile/type/Type";
import { None } from "../../magma/api/option/None";
import { ValueUtils } from "../../magma/app/compile/ValueUtils";
import { Iterable } from "../../magma/api/collect/list/Iterable";
import { Definition } from "../../magma/app/compile/define/Definition";
import { ValueCompiler } from "../../magma/app/ValueCompiler";
import { FunctionSegmentCompiler } from "../../magma/app/FunctionSegmentCompiler";
import { Stack } from "../../magma/app/compile/Stack";
import { Joiner } from "../../magma/api/collect/Joiner";
import { Platform } from "../../magma/app/Platform";
import { Registry } from "../../magma/app/compile/Registry";
import { FieldCompiler } from "../../magma/app/FieldCompiler";
import { Value } from "../../magma/app/compile/value/Value";
import { Location } from "../../magma/app/Location";
import { Context } from "../../magma/app/compile/Context";
export class RootCompiler {
	static compileRootSegment(state: CompileState, input: string): Tuple2<CompileState, string> {
		return OrRule.compileOrPlaceholder(state, input, Lists.of(WhitespaceCompiler.compileWhitespace, RootCompiler.compileNamespaced, RootCompiler.createStructureRule("class ", "class "), RootCompiler.createStructureRule("interface ", "interface "), RootCompiler.createStructureRule("record ", "class "), RootCompiler.createStructureRule("enum ", "class ")))/*unknown*/;
	}
	static createStructureRule(sourceInfix: string, targetInfix: string): Rule<string> {
		return (state: CompileState, input1: string) => {
			return SplitComposable.compileSplit(input1, new LocatingSplitter(sourceInfix, new FirstLocator()), (beforeInfix: string, afterInfix: string) => {
				return SplitComposable.compileSplit(afterInfix, new LocatingSplitter("{", new FirstLocator()), (beforeContent: string, withEnd: string) => {
					return new SuffixComposable<Tuple2<CompileState, string>>("}", (inputContent: string) => {
						return SplitComposable.compileLast(beforeInfix, "\n", (s: string, s2: string) => {
							let annotations = DefiningCompiler.parseAnnotations(s)/*unknown*/;
							if (annotations.contains("Actual")/*unknown*/){
								return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""))/*unknown*/;
							}
							return RootCompiler.compileStructureWithImplementing(state, annotations, DefiningCompiler.parseModifiers(s2), targetInfix, beforeContent, inputContent)/*unknown*/;
						}).or(() => {
							let modifiers = DefiningCompiler.parseModifiers(beforeContent)/*unknown*/;
							return RootCompiler.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent)/*unknown*/;
						})/*unknown*/;
					}).apply(Strings.strip(withEnd))/*unknown*/;
				})/*unknown*/;
			})/*unknown*/;
		}/*unknown*/;
	}
	static compileStructureWithImplementing(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, content: string): Option<Tuple2<CompileState, string>> {
		return SplitComposable.compileLast(beforeContent, " implements ", (s: string, s2: string) => {
			return TypeCompiler.parseType(state, s2).flatMap((implementingTuple: Tuple2<CompileState, Type>) => {
				return RootCompiler.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content)/*unknown*/;
			})/*unknown*/;
		}).or(() => {
			return RootCompiler.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content)/*unknown*/;
		})/*unknown*/;
	}
	static compileStructureWithExtends(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return SplitComposable.compileSplit(beforeContent, new LocatingSplitter(" extends ", new FirstLocator()), (beforeExtends: string, afterExtends: string) => {
			return ValueUtils.parseValues(state, afterExtends, (inner0: CompileState, inner1: string) => {
				return TypeCompiler.parseType(inner0, inner1)/*unknown*/;
			}).flatMap((compileStateListTuple2: Tuple2<CompileState, List<Type>>) => {
				return RootCompiler.compileStructureWithParameters(compileStateListTuple2.left(), annotations, modifiers, targetInfix, beforeExtends, compileStateListTuple2.right(), maybeImplementing, inputContent)/*unknown*/;
			})/*unknown*/;
		}).or(() => {
			return RootCompiler.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, Lists.empty(), maybeImplementing, inputContent)/*unknown*/;
		})/*unknown*/;
	}
	static compileStructureWithParameters(state: CompileState, annotations: List<string>, modifiers: List<string>, targetInfix: string, beforeContent: string, maybeSuperType: Iterable<Type>, maybeImplementing: Option<Type>, inputContent: string): Option<Tuple2<CompileState, string>> {
		return SplitComposable.compileSplit(beforeContent, new LocatingSplitter("(", new FirstLocator()), (rawName: string, withParameters: string) => {
			return SplitComposable.compileSplit(withParameters, new LocatingSplitter(")", new FirstLocator()), (parametersString: string, _: string) => {
				let name = Strings.strip(rawName)/*unknown*/;
				let parametersTuple = DefiningCompiler.parseParameters(state, parametersString)/*unknown*/;
				let parameters = DefiningCompiler.retainDefinitionsFromParameters(parametersTuple.right())/*unknown*/;
				return RootCompiler.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType)/*unknown*/;
			})/*unknown*/;
		}).or(() => {
			return RootCompiler.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType)/*unknown*/;
		})/*unknown*/;
	}
	static compileStructureWithTypeParams(state: CompileState, infix: string, content: string, beforeParams: string, parameters: Iterable<Definition>, maybeImplementing: Option<Type>, annotations: List<string>, modifiers: List<string>, maybeSuperType: Iterable<Type>): Option<Tuple2<CompileState, string>> {
		return new SuffixComposable<Tuple2<CompileState, string>>(">", (withoutTypeParamEnd: string) => {
			return SplitComposable.compileSplit(withoutTypeParamEnd, new LocatingSplitter("<", new FirstLocator()), (name: string, typeParamsString: string) => {
				let typeParams = DefiningCompiler.divideValues(typeParamsString)/*unknown*/;
				return RootCompiler.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType)/*unknown*/;
			})/*unknown*/;
		}).apply(Strings.strip(beforeParams)).or(() => {
			return RootCompiler.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType)/*unknown*/;
		})/*unknown*/;
	}
	static assembleStructure(state: CompileState, annotations: List<string>, oldModifiers: List<string>, infix: string, rawName: string, typeParams: Iterable<string>, parameters: Iterable<Definition>, maybeImplementing: Option<Type>, content: string, maybeSuperType: Iterable<Type>): Option<Tuple2<CompileState, string>> {
		let name = Strings.strip(rawName)/*unknown*/;
		if (!ValueCompiler/*unknown*/.isSymbol(name)/*unknown*/){
			return new None<Tuple2<CompileState, string>>()/*unknown*/;
		}
		let outputContentTuple = FunctionSegmentCompiler.compileStatements(state.mapStack((stack: Stack) => {
			return stack.pushStructureName(name)/*unknown*/;
		}), content, RootCompiler.compileClassSegment)/*unknown*/;
		let outputContentState = outputContentTuple.left().mapStack((stack1: Stack) => {
			return stack1.popStructureName()/*unknown*/;
		})/*unknown*/;
		let outputContent = outputContentTuple.right()/*unknown*/;
		let constructorString = RootCompiler.generateConstructorFromRecordParameters(parameters)/*unknown*/;
		let joinedTypeParams = RootCompiler.joinTypeParams(typeParams)/*unknown*/;
		let implementingString = RootCompiler.generateImplementing(maybeImplementing)/*unknown*/;
		let newModifiers = RootCompiler.modifyModifiers0(oldModifiers)/*unknown*/;
		let joinedModifiers = newModifiers.iter().map((value: string) => {
			return value + " "/*unknown*/;
		}).collect(Joiner.empty()).orElse("")/*unknown*/;
		if (outputContentState.context().hasPlatform(Platform.PlantUML)/*unknown*/){
			let joinedImplementing = maybeImplementing.map((type: Type) => {
				return type.generateSimple()/*unknown*/;
			}).map((generated: string) => {
				return name + " <|.. " + generated + "\n"/*unknown*/;
			}).orElse("")/*unknown*/;
			let joinedSuperTypes = maybeSuperType.iter().map((type: Type) => {
				return type.generateSimple()/*unknown*/;
			}).map((generated: string) => {
				return name + " <|-- " + generated + "\n"/*unknown*/;
			}).collect(new Joiner("")).orElse("")/*unknown*/;
			let generated = infix + name + joinedTypeParams + " {\n}\n" + joinedSuperTypes + joinedImplementing/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.mapRegistry((registry: Registry) => {
				return registry.append(generated)/*unknown*/;
			}), ""))/*unknown*/;
		}
		if (annotations.contains("Namespace")/*unknown*/){
			let actualInfix: string = "interface "/*unknown*/;
			let newName: string = name + "Instance"/*unknown*/;
			let generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + DefiningCompiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n"/*unknown*/;
			let compileState: CompileState = outputContentState.mapRegistry((registry: Registry) => {
				return registry.append(generated)/*unknown*/;
			})/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(compileState.mapRegistry((registry1: Registry) => {
				return registry1.append("export declare const " + name + ": " + newName + ";\n")/*unknown*/;
			}), ""))/*unknown*/;
		}
		else {
			let extendsString = RootCompiler.joinExtends(maybeSuperType)/*unknown*/;
			let generated = joinedModifiers + infix + name + joinedTypeParams + extendsString + implementingString + " {" + DefiningCompiler.joinParameters(parameters) + constructorString + outputContent + "\n}\n"/*unknown*/;
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(outputContentState.mapRegistry((registry: Registry) => {
				return registry.append(generated)/*unknown*/;
			}), ""))/*unknown*/;
		}
	}
	static joinExtends(maybeSuperType: Iterable<Type>): string {
		return maybeSuperType.iter().map((type: Type) => {
			return type.generate()/*unknown*/;
		}).collect(new Joiner(", ")).map((inner: string) => {
			return " extends " + inner/*unknown*/;
		}).orElse("")/*unknown*/;
	}
	static modifyModifiers0(oldModifiers: List<string>): Iterable<string> {
		if (oldModifiers.contains("public")/*unknown*/){
			return Lists.of("export")/*unknown*/;
		}
		return Lists.empty()/*unknown*/;
	}
	static generateImplementing(maybeImplementing: Option<Type>): string {
		return maybeImplementing.map((type: Type) => {
			return type.generate()/*unknown*/;
		}).map((inner: string) => {
			return " implements " + inner/*unknown*/;
		}).orElse("")/*unknown*/;
	}
	static joinTypeParams(typeParams: Iterable<string>): string {
		return typeParams.iter().collect(new Joiner(", ")).map((inner: string) => {
			return "<" + inner + ">"/*unknown*/;
		}).orElse("")/*unknown*/;
	}
	static generateConstructorFromRecordParameters(parameters: Iterable<Definition>): string {
		return parameters.iter().map((definition: Definition) => {
			return definition.generate()/*unknown*/;
		}).collect(new Joiner(", ")).map((generatedParameters: string) => {
			return RootCompiler.generateConstructorWithParameterString(parameters, generatedParameters)/*unknown*/;
		}).orElse("")/*unknown*/;
	}
	static generateConstructorWithParameterString(parameters: Iterable<Definition>, parametersString: string): string {
		let constructorAssignments = RootCompiler.generateConstructorAssignments(parameters)/*unknown*/;
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}"/*unknown*/;
	}
	static generateConstructorAssignments(parameters: Iterable<Definition>): string {
		return parameters.iter().map((definition: Definition) => {
			return definition.toAssignment()/*unknown*/;
		}).collect(Joiner.empty()).orElse("")/*unknown*/;
	}
	static compileNamespaced(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (stripped.startsWith("package ") || stripped.startsWith("import ")/*unknown*/){
			return new Some<Tuple2<CompileState, string>>(new Tuple2Impl<CompileState, string>(state, ""))/*unknown*/;
		}
		return new None<Tuple2<CompileState, string>>()/*unknown*/;
	}
	static compileClassSegment(state1: CompileState, input1: string): Tuple2<CompileState, string> {
		return OrRule.compileOrPlaceholder(state1, input1, Lists.of(WhitespaceCompiler.compileWhitespace, RootCompiler.createStructureRule("class ", "class "), RootCompiler.createStructureRule("interface ", "interface "), RootCompiler.createStructureRule("record ", "class "), RootCompiler.createStructureRule("enum ", "class "), FieldCompiler.compileMethod, FieldCompiler.compileFieldDefinition, FieldCompiler.compileEnumValues))/*unknown*/;
	}
	static parseValue(state: CompileState, input: string): Option<Tuple2<CompileState, Value>> {
		return new OrRule<Value>(Lists.of(ValueCompiler.parseLambda, ValueCompiler.createOperatorRule("+"), ValueCompiler.createOperatorRule("-"), ValueCompiler.createOperatorRule("<="), ValueCompiler.createOperatorRule("<"), ValueCompiler.createOperatorRule("&&"), ValueCompiler.createOperatorRule("||"), ValueCompiler.createOperatorRule(">"), ValueCompiler.createOperatorRule(">="), ValueCompiler.parseInvokable, ValueCompiler.createAccessRule("."), ValueCompiler.createAccessRule("::"), ValueCompiler.parseSymbol, ValueCompiler.parseNot, ValueCompiler.parseNumber, ValueCompiler.createOperatorRuleWithDifferentInfix("==", "==="), ValueCompiler.createOperatorRuleWithDifferentInfix("!=", "!=="), ValueCompiler.createTextRule("\""), ValueCompiler.createTextRule("'"))).apply(state, input)/*unknown*/;
	}
	static compileRoot(state: CompileState, input: string, location: Location): Tuple2<CompileState, string> {
		return FunctionSegmentCompiler.compileStatements(state.mapContext((context2: Context) => {
			return context2.withLocation(location)/*unknown*/;
		}), input, RootCompiler.compileRootSegment)/*unknown*/;
	}
}

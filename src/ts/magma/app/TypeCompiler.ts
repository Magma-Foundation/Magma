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
import { Type } from "../../magma/app/compile/type/Type";
import { Tuple2Impl } from "../../magma/api/Tuple2Impl";
import { CompilerUtils } from "../../magma/app/CompilerUtils";
import { OrRule } from "../../magma/app/compile/rule/OrRule";
import { Lists } from "../../jvm/api/collect/list/Lists";
import { Strings } from "../../magma/api/text/Strings";
import { Some } from "../../magma/api/option/Some";
import { VariadicType } from "../../magma/app/compile/type/VariadicType";
import { ValueCompiler } from "../../magma/app/ValueCompiler";
import { Symbol } from "../../magma/app/compile/value/Symbol";
import { None } from "../../magma/api/option/None";
import { PrimitiveType } from "../../magma/app/compile/type/PrimitiveType";
import { LocatingSplitter } from "../../magma/app/compile/split/LocatingSplitter";
import { FirstLocator } from "../../magma/app/compile/locate/FirstLocator";
import { TemplateType } from "../../magma/app/compile/type/TemplateType";
import { List } from "../../magma/api/collect/list/List";
import { FunctionType } from "../../magma/app/compile/type/FunctionType";
import { Placeholder } from "../../magma/app/compile/value/Placeholder";
import { Location } from "../../magma/app/Location";
import { Import } from "../../magma/app/compile/Import";
import { Registry } from "../../magma/app/compile/Registry";
import { Source } from "../../magma/app/io/Source";
import { Platform } from "../../magma/app/Platform";
import { Dependency } from "../../magma/app/compile/Dependency";
class TypeCompiler {
	static compileType(state: CompileState, type: string): Option<Tuple2<CompileState, string>> {
		return TypeCompiler.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => {
			return new Tuple2Impl<CompileState, string>(tuple.left(), tuple.right().generate())/*unknown*/;
		})/*unknown*/;
	}
	static parseType(state: CompileState, type: string): Option<Tuple2<CompileState, Type>> {
		return CompilerUtils.or(state, type, new OrRule<Type>(Lists.of(TypeCompiler.parseVarArgs, TypeCompiler.parseGeneric, TypeCompiler.parsePrimitive, TypeCompiler.parseSymbolType)))/*unknown*/;
	}
	static parseVarArgs(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input)/*unknown*/;
		return CompilerUtils.compileSuffix(stripped, "...", (s: string) => {
			let child = TypeCompiler.parseTypeOrPlaceholder(state, s)/*unknown*/;
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VariadicType(child.right())))/*unknown*/;
		})/*unknown*/;
	}
	static parseSymbolType(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input)/*unknown*/;
		if (ValueCompiler.isSymbol(stripped)/*unknown*/){
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(TypeCompiler.addResolvedImportFromCache0(state, stripped), new Symbol(stripped)))/*unknown*/;
		}
		return new None<Tuple2<CompileState, Type>>()/*unknown*/;
	}
	static parsePrimitive(state: CompileState, input: string): Option<Tuple2<CompileState, Type>> {
		return TypeCompiler.findPrimitiveValue(Strings.strip(input)).map((result: Type) => {
			return new Tuple2Impl<CompileState, Type>(state, result)/*unknown*/;
		})/*unknown*/;
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
		return CompilerUtils.compileSuffix(Strings.strip(input), ">", (withoutEnd: string) => {
			return CompilerUtils.compileSplit(withoutEnd, new LocatingSplitter("<", new FirstLocator()), (baseString: string, argsString: string) => {
				let argsTuple = CompilerUtils.parseValuesOrEmpty(state, argsString, (state1: CompileState, s: string) => {
					return TypeCompiler.compileTypeArgument(state1, s)/*unknown*/;
				})/*unknown*/;
				let argsState = argsTuple.left()/*unknown*/;
				let args = argsTuple.right()/*unknown*/;
				let base = Strings.strip(baseString)/*unknown*/;
				return TypeCompiler.assembleFunctionType(argsState, base, args).or(() => {
					let compileState = TypeCompiler.addResolvedImportFromCache0(argsState, base)/*unknown*/;
					return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new TemplateType(base, args)))/*unknown*/;
				})/*unknown*/;
			})/*unknown*/;
		})/*unknown*/;
	}
	static assembleFunctionType(state: CompileState, base: string, args: List<string>): Option<Tuple2<CompileState, Type>> {
		return TypeCompiler.mapFunctionType(base, args).map((generated: Type) => {
			return new Tuple2Impl<CompileState, Type>(state, generated)/*unknown*/;
		})/*unknown*/;
	}
	static mapFunctionType(base: string, args: List<string>): Option<Type> {
		if (Strings.equalsTo("Function", base)/*unknown*/){
			return args.findFirst().and(() => {
				return args.find(1)/*unknown*/;
			}).map((tuple: Tuple2<string, string>) => {
				return new FunctionType(Lists.of(tuple.left()), tuple.right())/*unknown*/;
			})/*unknown*/;
		}
		if (Strings.equalsTo("BiFunction", base)/*unknown*/){
			return args.find(0).and(() => {
				return args.find(1)/*unknown*/;
			}).and(() => {
				return args.find(2)/*unknown*/;
			}).map((tuple: Tuple2<Tuple2<string, string>, string>) => {
				return new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right())/*unknown*/;
			})/*unknown*/;
		}
		if (Strings.equalsTo("Supplier", base)/*unknown*/){
			return args.findFirst().map((first: string) => {
				return new FunctionType(Lists.empty(), first)/*unknown*/;
			})/*unknown*/;
		}
		if (Strings.equalsTo("Consumer", base)/*unknown*/){
			return args.findFirst().map((first: string) => {
				return new FunctionType(Lists.of(first), "void")/*unknown*/;
			})/*unknown*/;
		}
		if (Strings.equalsTo("Predicate", base)/*unknown*/){
			return args.findFirst().map((first: string) => {
				return new FunctionType(Lists.of(first), "boolean")/*unknown*/;
			})/*unknown*/;
		}
		return new None<Type>()/*unknown*/;
	}
	static compileTypeArgument(state: CompileState, input: string): Option<Tuple2<CompileState, string>> {
		return CompilerUtils.or(state, input, new OrRule<string>(Lists.of((state2: CompileState, input1: string) => {
			return CompilerUtils.compileWhitespace(state2, input1)/*unknown*/;
		}, (state1: CompileState, type: string) => {
			return TypeCompiler.compileType(state1, type)/*unknown*/;
		})))/*unknown*/;
	}
	static parseTypeOrPlaceholder(state: CompileState, type: string): Tuple2<CompileState, Type> {
		return TypeCompiler.parseType(state, type).map((tuple: Tuple2<CompileState, Type>) => {
			return new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())/*unknown*/;
		}).orElseGet(() => {
			return new Tuple2Impl<CompileState, Type>(state, new Placeholder(type))/*unknown*/;
		})/*unknown*/;
	}
	static getState(immutableCompileState: CompileState, location: Location): CompileState {
		let requestedNamespace = location.namespace()/*unknown*/;
		let requestedChild = location.name()/*unknown*/;
		let namespace = TypeCompiler.fixNamespace(requestedNamespace, immutableCompileState.context().findNamespaceOrEmpty())/*unknown*/;
		if (immutableCompileState.registry().doesImportExistAlready(requestedChild)/*unknown*/){
			return immutableCompileState/*CompileState*/;
		}
		let namespaceWithChild = namespace.addLast(requestedChild)/*unknown*/;
		let anImport = new Import(namespaceWithChild, requestedChild)/*unknown*/;
		return immutableCompileState.mapRegistry((registry: Registry) => {
			return registry.addImport(anImport)/*unknown*/;
		})/*unknown*/;
	}
	static addResolvedImportFromCache0(state: CompileState, base: string): CompileState {
		if (state.stack().hasAnyStructureName(base)/*unknown*/){
			return state/*CompileState*/;
		}
		return state.context().findSource(base).map((source: Source) => {
			let location: Location = source.createLocation()/*unknown*/;
			return TypeCompiler.getCompileState1(state, location).orElseGet(() => {
				return TypeCompiler.getState(state, location)/*unknown*/;
			})/*unknown*/;
		}).orElse(state)/*unknown*/;
	}
	static getCompileState1(immutableCompileState: CompileState, location: Location): Option<CompileState> {
		if (!immutableCompileState/*CompileState*/.context().hasPlatform(Platform.PlantUML)/*unknown*/){
			return new None<CompileState>()/*unknown*/;
		}
		let name = immutableCompileState.context().findNameOrEmpty()/*unknown*/;
		let dependency = new Dependency(name, location.name())/*unknown*/;
		if (immutableCompileState.registry().containsDependency(dependency)/*unknown*/){
			return new None<CompileState>()/*unknown*/;
		}
		return new Some<CompileState>(immutableCompileState.mapRegistry((registry1: Registry) => {
			return registry1.addDependency(dependency)/*unknown*/;
		}))/*unknown*/;
	}
	static fixNamespace(requestedNamespace: List<string>, thisNamespace: List<string>): List<string> {
		if (thisNamespace.isEmpty()/*unknown*/){
			return requestedNamespace.addFirst(".")/*unknown*/;
		}
		return TypeCompiler.addParentSeparator(requestedNamespace, thisNamespace.size())/*unknown*/;
	}
	static addParentSeparator(newNamespace: List<string>, count: number): List<string> {
		let index = 0/*unknown*/;
		let copy = newNamespace/*List<string>*/;
		while (index < count/*unknown*/){
			copy/*unknown*/ = copy.addFirst("..")/*unknown*/;
			index/*unknown*/++;
		}
		return copy/*unknown*/;
	}
}

/*[
	JVMList: jvm.api.collect.list, 
	Lists: jvm.api.collect.list, 
	Console: jvm.api.io, 
	Files: jvm.api.io, 
	JVMPath: jvm.api.io, 
	Characters: jvm.api.text, 
	Strings: jvm.api.text, 
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect.head, 
	FlatMapHead: magma.api.collect.head, 
	Head: magma.api.collect.head, 
	HeadedQuery: magma.api.collect.head, 
	MapHead: magma.api.collect.head, 
	RangeHead: magma.api.collect.head, 
	SingleHead: magma.api.collect.head, 
	ZipHead: magma.api.collect.head, 
	Joiner: magma.api.collect, 
	List: magma.api.collect.list, 
	ListCollector: magma.api.collect.list, 
	Queries: magma.api.collect, 
	Query: magma.api.collect, 
	IOError: magma.api.io, 
	Path: magma.api.io, 
	None: magma.api.option, 
	Option: magma.api.option, 
	Some: magma.api.option, 
	Err: magma.api.result, 
	Ok: magma.api.result, 
	Result: magma.api.result, 
	Tuple2: magma.api, 
	Tuple2Impl: magma.api, 
	Type: magma.api, 
	Definition: magma.app, 
	Main: magma.app, 
	MethodHeader: magma.app, 
	Parameter: magma.app
]*/
import { Type } from "magma/api/Type";
import { Option } from "magma/api/option/Option";
import { List } from "magma/api/collect/list/List";
import { Lists } from "jvm/api/collect/list/Lists";
import { Tuple2 } from "magma/api/Tuple2";
import { Strings } from "jvm/api/text/Strings";
import { None } from "magma/api/option/None";
import { Some } from "magma/api/option/Some";
import { Tuple2Impl } from "magma/api/Tuple2Impl";
import { Definition } from "magma/app/Definition";
import { MethodHeader } from "magma/app/MethodHeader";
import { Parameter } from "magma/app/Parameter";
import { Joiner } from "magma/api/collect/Joiner";
import { Path } from "magma/api/io/Path";
import { IOError } from "magma/api/io/IOError";
import { Result } from "magma/api/result/Result";
import { ListCollector } from "magma/api/collect/list/ListCollector";
import { Files } from "jvm/api/io/Files";
import { Console } from "jvm/api/io/Console";
import { Queries } from "magma/api/collect/Queries";
import { HeadedQuery } from "magma/api/collect/head/HeadedQuery";
import { RangeHead } from "magma/api/collect/head/RangeHead";
import { Characters } from "jvm/api/text/Characters";
interface Value extends Argument, Caller  {
	mut resolve(mut state: CompileState): Type;
	mut generateAsEnumValue(mut structureName: &[I8]): Option<&[I8]>;
}
interface Argument {
	mut toValue(): Option<Value>;
}
interface Caller {
	mut generate(): &[I8];
	mut findChild(): Option<Value>;
}
class DivideState {
	mut segments: List<&[I8]>;
	mut buffer: &[I8];
	mut depth: number;
	mut input: &[I8];
	mut index: number;
	constructor (mut segments: List<&[I8]>, mut buffer: &[I8], mut depth: number, mut input: &[I8], mut index: number) {
		this.segments = segments;
		this.buffer = buffer;
		this.depth = depth;
		this.input = input;
		this.index = index;
	}
	mut static createInitial(input: &[I8]): DivideState {
		return new DivideState(Lists.empty(), "", 0, input, 0);
	}
	mut advance(): DivideState {
		return new DivideState(this.segments.addLast(this.buffer), "", this.depth, this.input, this.index);
	}
	mut append(c: I8): DivideState {
		return new DivideState(this.segments, this.buffer + c, this.depth, this.input, this.index);
	}
	mut isLevel(): Bool {
		return 0 === this.depth;
	}
	mut enter(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth + 1, this.input, this.index);
	}
	mut exit(): DivideState {
		return new DivideState(this.segments, this.buffer, this.depth - 1, this.input, this.index);
	}
	mut isShallow(): Bool {
		return 1 === this.depth;
	}
	mut pop(): Option<Tuple2<DivideState, I8>> {
		if (this.index >= Strings.length(this.input)) {
			return new None<Tuple2<DivideState, I8>>();
		}
		let c = Strings.charAt(this.input, this.index);
		let nextState = new DivideState(this.segments, this.buffer, this.depth, this.input, this.index + 1);
		return new Some<Tuple2<DivideState, I8>>(new Tuple2Impl<DivideState, I8>(nextState, c));
	}
	mut popAndAppendToTuple(): Option<Tuple2<DivideState, I8>> {
		return this.pop().map((mut inner: Tuple2<DivideState, I8>) => new Tuple2Impl<DivideState, I8>(inner.left().append(inner.right()), inner.right()));
	}
	mut popAndAppendToOption(): Option<DivideState> {
		return this.popAndAppendToTuple().map((mut tuple: Tuple2<DivideState, I8>) => tuple.left());
	}
	mut peek(): I8 {
		return Strings.charAt(this.input, this.index);
	}
	mut startsWith(slice: &[I8]): Bool {
		return Strings.sliceFrom(this.input, this.index).startsWith(slice);
	}
}
class CompileState {
	mut imports: List<Import>;
	mut output: &[I8];
	mut structureNames: List<&[I8]>;
	mut depth: number;
	mut definitions: List<Definition>;
	mut maybeLocation: Option<Location>;
	mut sources: List<Source>;
	mut platform: Platform;
	constructor (mut imports: List<Import>, mut output: &[I8], mut structureNames: List<&[I8]>, mut depth: number, mut definitions: List<Definition>, mut maybeLocation: Option<Location>, mut sources: List<Source>, mut platform: Platform) {
		this.imports = imports;
		this.output = output;
		this.structureNames = structureNames;
		this.depth = depth;
		this.definitions = definitions;
		this.maybeLocation = maybeLocation;
		this.sources = sources;
		this.platform = platform;
	}
	mut static createInitial(): CompileState {
		return new CompileState(Lists.empty(), "", Lists.empty(), 0, Lists.empty(), new None<Location>(), Lists.empty(), Platform.Magma);
	}
	mut isLastWithin(name: &[I8]): Bool {
		return this.structureNames.findLast().filter((mut anObject: &[I8]) => Strings.equalsTo(name, anObject)).isPresent();
	}
	mut addResolvedImport(oldParent: List<&[I8]>, child: &[I8]): CompileState {
		let namespace = this.maybeLocation.map((mut location: Location) => location.namespace).orElse(Lists.empty());
		let mut newParent = oldParent;
		if (Platform.TypeScript === this.platform) {
			if (namespace.isEmpty()) {
				newParent = newParent.addFirst(".");
			}
			let mut i = 0;
			let size = namespace.size();
			while (i < size) {
				newParent = newParent.addFirst("..");
				i++;
			}
		}
		if (this.imports.query().filter((mut node: Import) => Strings.equalsTo(node.child, child)).next().isPresent()) {
			return this;
		}
		let importString = new Import(newParent, child);
		return new CompileState(this.imports.addLast(importString), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut withLocation(namespace: Location): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, new Some<Location>(namespace), this.sources, this.platform);
	}
	mut append(element: &[I8]): CompileState {
		return new CompileState(this.imports, this.output + element, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut pushStructureName(name: &[I8]): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames.addLast(name), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut enterDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth + 1, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut exitDepth(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth - 1, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut defineAll(definitions: List<Definition>): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions.addAll(definitions), this.maybeLocation, this.sources, this.platform);
	}
	mut resolve(name: &[I8]): Option<Type> {
		return this.definitions.queryReversed().filter((mut definition: Definition) => Strings.equalsTo(definition.name(), name)).map((mut definition1: Definition) => definition1.type()).next();
	}
	mut clearImports(): CompileState {
		return new CompileState(Lists.empty(), this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut clearOutput(): CompileState {
		return new CompileState(this.imports, "", this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut addSource(source: Source): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources.addLast(source), this.platform);
	}
	mut findSource(name: &[I8]): Option<Source> {
		return this.sources.query().filter((mut source: Source) => Strings.equalsTo(source.computeName(), name)).next();
	}
	mut addResolvedImportFromCache(base: &[I8]): CompileState {
		if (this.structureNames.query().anyMatch((mut inner: &[I8]) => Strings.equalsTo(inner, base))) {
			return this;
		}
		return this.findSource(base).map((mut source: Source) => this.addResolvedImport(source.computeNamespace(), source.computeName())).orElse(this);
	}
	mut popStructureName(): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames.removeLast().orElse(this.structureNames), this.depth, this.definitions, this.maybeLocation, this.sources, this.platform);
	}
	mut mapLocation(mapper: (arg0 : Location) => Location): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation.map(mapper), this.sources, this.platform);
	}
	mut withPlatform(platform: Platform): CompileState {
		return new CompileState(this.imports, this.output, this.structureNames, this.depth, this.definitions, this.maybeLocation, this.sources, platform);
	}
}
class ConstructorHeader implements MethodHeader<ConstructorHeader> {
	mut generateWithAfterName(afterName: &[I8]): &[I8] {
		return "constructor " + afterName;
	}
	mut hasAnnotation(annotation: &[I8]): Bool {
		return false;
	}
	mut removeModifier(modifier: &[I8]): ConstructorHeader {
		return this;
	}
	mut addModifier(modifier: &[I8]): ConstructorHeader {
		return this;
	}
}
class Placeholder {
	mut input: &[I8];
	constructor (mut input: &[I8]) {
		this.input = input;
	}
	mut generate(): &[I8] {
		return Main.generatePlaceholder(this.input);
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut asDefinition(): Option<Definition> {
		return new None<Definition>();
	}
	mut toValue(): Option<Value> {
		return new None<Value>();
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}
class Whitespace implements Parameter {
	mut generate(): &[I8] {
		return "";
	}
	mut asDefinition(): Option<Definition> {
		return new None<Definition>();
	}
}
class Access implements Value {
	mut child: Value;
	mut property: &[I8];
	constructor (mut child: Value, mut property: &[I8]) {
		this.child = child;
		this.property = property;
	}
	mut generate(): &[I8] {
		return this.child.generate() + "." + this.property;
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new Some<Value>(this.child);
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}
class SymbolNode {
	mut value: &[I8];
	constructor (mut value: &[I8]) {
		this.value = value;
	}
	mut generate(): &[I8] {
		return this.value;
	}
	mut resolve(state: CompileState): Type {
		return state.resolve(this.value).orElse(Primitive.Unknown);
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}
class StringValue implements Value {
	mut value: &[I8];
	constructor (mut value: &[I8]) {
		this.value = value;
	}
	mut generate(): &[I8] {
		return "\"" + this.value + "\"";
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}
class Not implements Value {
	mut child: &[I8];
	constructor (mut child: &[I8]) {
		this.child = child;
	}
	mut generate(): &[I8] {
		return this.child;
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}
class Lambda implements Value {
	mut paramNames: List<Definition>;
	mut content: &[I8];
	constructor (mut paramNames: List<Definition>, mut content: &[I8]) {
		this.paramNames = paramNames;
		this.content = content;
	}
	mut generate(): &[I8] {
		let joinedParamNames = this.paramNames.query().map((mut definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
		return "(" + joinedParamNames + ")" + " => " + this.content;
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}
class Invokable implements Value {
	mut caller: Caller;
	mut args: List<Value>;
	constructor (mut caller: Caller, mut args: List<Value>) {
		this.caller = caller;
		this.args = args;
	}
	mut generate(): &[I8] {
		let joinedArguments = this.joinArgs();
		return this.caller.generate() + "(" + joinedArguments + ")";
	}
	mut joinArgs(): &[I8] {
		return this.args.query().map((mut value: Value) => value.generate()).collect(new Joiner(", ")).orElse("");
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new Some<&[I8]>("\n\tstatic " + this.caller.generate() + ": " + structureName + " = new " + structureName + "(" + this.joinArgs() + ");");
	}
}
class Operation implements Value {
	mut left: Value;
	mut targetInfix: &[I8];
	mut right: Value;
	constructor (mut left: Value, mut targetInfix: &[I8], mut right: Value) {
		this.left = left;
		this.targetInfix = targetInfix;
		this.right = right;
	}
	mut generate(): &[I8] {
		return this.left.generate() + " " + this.targetInfix + " " + this.right.generate();
	}
	mut toValue(): Option<Value> {
		return new Some<Value>(this);
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
	mut resolve(state: CompileState): Type {
		return Primitive.Unknown;
	}
	mut generateAsEnumValue(structureName: &[I8]): Option<&[I8]> {
		return new None<&[I8]>();
	}
}
class ConstructionCaller implements Caller {
	mut right: &[I8];
	mut platform: Platform;
	constructor (mut right: &[I8], mut platform: Platform) {
		this.right = right;
		this.platform = platform;
	}
	mut generate(): &[I8] {
		if (Platform.Magma === this.platform) {
			return this.right;
		}
		return "new " + this.right;
	}
	mut findChild(): Option<Value> {
		return new None<Value>();
	}
}
class FunctionType implements Type {
	mut args: List<&[I8]>;
	mut returns: &[I8];
	constructor (mut args: List<&[I8]>, mut returns: &[I8]) {
		this.args = args;
		this.returns = returns;
	}
	mut generate(): &[I8] {
		let joinedArguments = this.args.queryWithIndices().map((mut tuple: Tuple2<number, &[I8]>) => "arg" + tuple.left() + " : " + tuple.right()).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments + ") => " + this.returns;
	}
	mut isFunctional(): Bool {
		return true;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
}
class Generic implements Type {
	mut base: &[I8];
	mut args: List<&[I8]>;
	constructor (mut base: &[I8], mut args: List<&[I8]>) {
		this.base = base;
		this.args = args;
	}
	mut static generateValueStrings(values: List<&[I8]>): &[I8] {
		return Main.generateAll(values, Generic.mergeValues);
	}
	mut static mergeValues(cache: &[I8], element: &[I8]): &[I8] {
		if (Strings.isEmpty(cache)) {
			return cache + element;
		}
		return cache + ", " + element;
	}
	mut generate(): &[I8] {
		return this.base + "<" + Generic.generateValueStrings(this.args) + ">";
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
}
class VarArgs implements Type {
	mut type: Type;
	constructor (mut type: Type) {
		this.type = type;
	}
	mut generate(): &[I8] {
		return this.type.generate() + "[]";
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "...";
	}
}
class Import {
	mut namespace: List<&[I8]>;
	mut child: &[I8];
	constructor (mut namespace: List<&[I8]>, mut child: &[I8]) {
		this.namespace = namespace;
		this.child = child;
	}
	mut generate(platform: Platform): &[I8] {
		if (Platform.Magma === platform) {
			let joinedNamespace = this.namespace.query().collect(new Joiner(".")).orElse("");
			return "import " + joinedNamespace + "." + this.child + ";\n";
		}
		let joinedNamespace = this.namespace.addLast(this.child).query().collect(new Joiner("/")).orElse("");
		return "import { " + this.child + " } from \"" + joinedNamespace + "\";\n";
	}
}
class Source {
	mut sourceDirectory: Path;
	mut source: Path;
	constructor (mut sourceDirectory: Path, mut source: Path) {
		this.sourceDirectory = sourceDirectory;
		this.source = source;
	}
	mut read(): Result<&[I8], IOError> {
		return this.source.readString();
	}
	mut computeName(): &[I8] {
		let fileName = this.source.findFileName();
		let separator = fileName.lastIndexOf(".");
		return fileName.substring(0, separator);
	}
	mut computeNamespace(): List<&[I8]> {
		return this.sourceDirectory.relativize(this.source).getParent().query().collect(new ListCollector<&[I8]>());
	}
	mut computeLocation(): Location {
		return new Location(this.computeNamespace(), this.computeName());
	}
}
class Location {
	mut namespace: List<&[I8]>;
	mut name: &[I8];
	constructor (mut namespace: List<&[I8]>, mut name: &[I8]) {
		this.namespace = namespace;
		this.name = name;
	}
}
class ArrayType implements Type {
	mut child: Type;
	constructor (mut child: Type) {
		this.child = child;
	}
	mut generate(): &[I8] {
		return this.child.generate() + "[]";
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
}
class Slice implements Type {
	mut type: Type;
	constructor (mut type: Type) {
		this.type = type;
	}
	mut generate(): &[I8] {
		return "&[" + this.type.generate() + "]";
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
}
class BooleanType implements Type {
	mut platform: Platform;
	constructor (mut platform: Platform) {
		this.platform = platform;
	}
	mut generate(): &[I8] {
		if (Platform.TypeScript === this.platform) {
			return "boolean";
		}
		return "Bool";
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return false;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
}
class Platform {
	static TypeScript: Platform = new Platform("node", "ts");
	static Magma: Platform = new Platform("magma", "mgs");
	static Windows: Platform = new Platform("windows", "h", "c");
	root: &[I8];
	extension: &[I8][];
	constructor (root: &[I8], ...extensions: &[I8][]) {
		this.root = root;
		this.extension = extensions;
	}
}
class Primitive implements Type {
	static String: Primitive = new Primitive("string");
	static Number: Primitive = new Primitive("number");
	static Var: Primitive = new Primitive("var");
	static Void: Primitive = new Primitive("void");
	static Unknown: Primitive = new Primitive("unknown");
	static I8: Primitive = new Primitive("I8");
	static I32: Primitive = new Primitive("I32");
	value: &[I8];
	constructor (value: &[I8]) {
		this.value = value;
	}
	mut generate(): &[I8] {
		return this.value;
	}
	mut isFunctional(): Bool {
		return false;
	}
	mut isVar(): Bool {
		return Primitive.Var === this;
	}
	mut generateBeforeName(): &[I8] {
		return "";
	}
}
export class Main {
	mut static main(): void {
		let sourceDirectory = Files.get(".", "src", "java");
		sourceDirectory.walk().match((mut children: List<Path>) => Main.runWithChildren(children, sourceDirectory), (mut value: IOError) => new Some<IOError>(value)).map((mut error: IOError) => error.display()).ifPresent((mut displayed: &[I8]) => Console.printErrLn(displayed));
	}
	mut static runWithChildren(children: List<Path>, sourceDirectory: Path): Option<IOError> {
		let sources = children.query().filter((mut source: Path) => source.endsWith(".java")).map((mut child: Path) => new Source(sourceDirectory, child)).collect(new ListCollector<Source>());
		let initial = sources.query().foldWithInitial(CompileState.createInitial(), (mut state: CompileState, mut source: Source) => state.addSource(source));
		return sources.query().foldWithInitial(Main.createInitialState(initial), (mut current: Tuple2<CompileState, Option<IOError>>, mut source1: Source) => Main.foldChild(current.left(), current.right(), source1)).right();
	}
	mut static createInitialState(state: CompileState): Tuple2<CompileState, Option<IOError>> {
		return new Tuple2Impl<CompileState, Option<IOError>>(state, new None<IOError>());
	}
	mut static foldChild(state: CompileState, maybeError: Option<IOError>, source: Source): Tuple2<CompileState, Option<IOError>> {
		if (maybeError.isPresent()) {
			return new Tuple2Impl<CompileState, Option<IOError>>(state, maybeError);
		}
		return Main.runWithSource(state, source);
	}
	mut static runWithSource(state: CompileState, source: Source): Tuple2<CompileState, Option<IOError>> {
		return source.read().match((mut input: &[I8]) => Main.getCompileStateOptionTuple2(state, source, input), (mut value: IOError) => new Tuple2Impl<CompileState, Option<IOError>>(state, new Some<IOError>(value)));
	}
	mut static getCompileStateOptionTuple2(state: CompileState, source: Source, input: &[I8]): Tuple2Impl<CompileState, Option<IOError>> {
		let typeScriptTuple = Main.compileAndWrite(state, source, input, Platform.TypeScript);
		let magmaTuple = Main.compileAndWrite(typeScriptTuple.left(), source, input, Platform.Magma);
		let windowsTuple = Main.compileAndWrite(magmaTuple.left(), source, input, Platform.Windows);
		return new Tuple2Impl<CompileState, Option<IOError>>(windowsTuple.left(), typeScriptTuple.right().or(() => magmaTuple.right()).or(() => windowsTuple.right()));
	}
	mut static compileAndWrite(state: CompileState, source: Source, input: &[I8], platform: Platform): Tuple2<CompileState, Option<IOError>> {
		let state1 = state.withLocation(source.computeLocation()).withPlatform(platform);
		let output = Main.compileRoot(state1, source, input);
		let location = output.left().maybeLocation.orElse(new Location(Lists.empty(), ""));
		let targetDirectory = Files.get(".", "src", platform.root);
		let targetParent = targetDirectory.resolveChildSegments(location.namespace);
		if (!targetParent.exists()) {
			let maybeError = targetParent.createDirectories();
			if (maybeError.isPresent()) {
				return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), maybeError);
			}
		}
		let initial: Option<IOError> = new None<IOError>();
		let ioErrorOption1 = Queries.fromArray(platform.extension).foldWithInitial(initial, (mut ioErrorOption: Option<IOError>, mut extension: &[I8]) => {
			let target = targetParent.resolveChild(location.name + "." + extension);
			return ioErrorOption.or(() => target.writeString(output.right()));
		});
		return new Tuple2Impl<CompileState, Option<IOError>>(output.left(), ioErrorOption1);
	}
	mut static compileRoot(state: CompileState, source: Source, input: &[I8]): Tuple2Impl<CompileState, &[I8]> {
		let statementsTuple = Main.compileStatements(state, input, Main.compileRootSegment);
		let statementsState = statementsTuple.left();
		let imports = statementsState.imports.query().map((mut anImport: Import) => anImport.generate(state.platform)).collect(new Joiner("")).orElse("");
		let compileState = statementsState.clearImports().clearOutput();
		let segment = compileState.sources.query().map((mut source1: Source) => Main.formatSource(source1)).collect(new Joiner(", ")).orElse("");
		let withMain = Main.createMain(source);
		let output = "/*[" + segment + "\n]*/\n" + imports + statementsState.output + statementsTuple.right() + withMain;
		return new Tuple2Impl<CompileState, &[I8]>(compileState, output);
	}
	mut static createMain(source: Source): &[I8] {
		if (Strings.equalsTo(source.computeName(), "Main")) {
			return "Main.main();";
		}
		return "";
	}
	mut static formatSource(source: Source): &[I8] {
		let joinedNamespace = source.computeNamespace().query().collect(new Joiner(".")).orElse("");
		return "\n\t" + source.computeName() + ": " + joinedNamespace;
	}
	mut static compileStatements(state: CompileState, input: &[I8], mapper: (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]>): Tuple2<CompileState, &[I8]> {
		return Main.compileAll(state, input, Main.foldStatements, mapper, Main.mergeStatements);
	}
	mut static compileAll(state: CompileState, input: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState, mapper: (arg0 : CompileState, arg1 : &[I8]) => Tuple2<CompileState, &[I8]>, merger: (arg0 : &[I8], arg1 : &[I8]) => &[I8]): Tuple2<CompileState, &[I8]> {
		let folded = Main.parseAll(state, input, folder, (mut state1: CompileState, mut s: &[I8]) => new Some<Tuple2<CompileState, &[I8]>>(mapper(state1, s))).orElse(new Tuple2Impl<CompileState, List<&[I8]>>(state, Lists.empty()));
		return new Tuple2Impl<CompileState, &[I8]>(folded.left(), Main.generateAll(folded.right(), merger));
	}
	mut static generateAll(elements: List<&[I8]>, merger: (arg0 : &[I8], arg1 : &[I8]) => &[I8]): &[I8] {
		return elements.query().foldWithInitial("", merger);
	}
	mut static parseAll<T>(state: CompileState, input: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState, biFunction: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main.divide(input, folder).query().foldWithInitial(new Some<Tuple2<CompileState, List<T>>>(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty())), (maybeCurrent: Option<Tuple2<CompileState, List<T>>>, segment: &[I8]) => maybeCurrent.flatMap((current: Tuple2<CompileState, List<T>>) => {
			let currentState = current.left();
			let currentElement = current.right();
			return biFunction(currentState, segment).map((mappedTuple: Tuple2<CompileState, T>) => {
				let mappedState = mappedTuple.left();
				let mappedElement = mappedTuple.right();
				return new Tuple2Impl<CompileState, List<T>>(mappedState, currentElement.addLast(mappedElement));
			});
		}));
	}
	mut static mergeStatements(cache: &[I8], element: &[I8]): &[I8] {
		return cache + element;
	}
	mut static divide(input: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState): List<&[I8]> {
		let mut current = DivideState.createInitial(input);
		while (true) {
			let poppedTuple0 = current.pop().toTuple(new Tuple2Impl<DivideState, I8>(current, "\0"));
			if (!poppedTuple0.left()) {
				break;
			}
			let poppedTuple = poppedTuple0.right();
			let poppedState = poppedTuple.left();
			let popped = poppedTuple.right();
			current = Main.foldSingleQuotes(poppedState, popped).or(() => Main.foldDoubleQuotes(poppedState, popped)).orElseGet(() => folder(poppedState, popped));
		}
		return current.advance().segments;
	}
	mut static foldDoubleQuotes(state: DivideState, c: I8): Option<DivideState> {
		if ("\"" !== c) {
			return new None<DivideState>();
		}
		let mut appended = state.append(c);
		while (true) {
			let maybeTuple = appended.popAndAppendToTuple().toTuple(new Tuple2Impl<DivideState, I8>(appended, "\0"));
			if (!maybeTuple.left()) {
				break;
			}
			let tuple = maybeTuple.right();
			appended = tuple.left();
			if ("\\" === tuple.right()) {
				appended = appended.popAndAppendToOption().orElse(appended);
			}
			if ("\"" === tuple.right()) {
				break;
			}
		}
		return new Some<DivideState>(appended);
	}
	mut static foldSingleQuotes(state: DivideState, c: I8): Option<DivideState> {
		if ("\'" !== c) {
			return new None<DivideState>();
		}
		return state.append(c).popAndAppendToTuple().flatMap(Main.foldEscaped).flatMap((mut state1: DivideState) => state1.popAndAppendToOption());
	}
	mut static foldEscaped(tuple: Tuple2<DivideState, I8>): Option<DivideState> {
		let state = tuple.left();
		let c = tuple.right();
		if ("\\" === c) {
			return state.popAndAppendToOption();
		}
		return new Some<DivideState>(state);
	}
	mut static foldStatements(state: DivideState, c: I8): DivideState {
		let appended = state.append(c);
		if (";" === c && appended.isLevel()) {
			return appended.advance();
		}
		if ("}" === c && appended.isShallow()) {
			return appended.advance().exit();
		}
		if ("{" === c || "(" === c) {
			return appended.enter();
		}
		if ("}" === c || ")" === c) {
			return appended.exit();
		}
		return appended;
	}
	mut static compileRootSegment(state: CompileState, input: &[I8]): Tuple2<CompileState, &[I8]> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileNamespaced, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class ")));
	}
	mut static createStructureRule(sourceInfix: &[I8], targetInfix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> {
		return (state: CompileState, input1: &[I8]) => Main.compileFirst(input1, sourceInfix, (beforeInfix: &[I8], afterInfix: &[I8]) => Main.compileFirst(afterInfix, "{", (beforeContent: &[I8], withEnd: &[I8]) => Main.compileSuffix(Strings.strip(withEnd), "}", (inputContent: &[I8]) => Main.compileLast(beforeInfix, "\n", (s: &[I8], s2: &[I8]) => {
			let annotations = Main.parseAnnotations(s);
			if (annotations.contains("Actual", Strings.equalsTo)) {
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
			}
			return Main.compileStructureWithImplementing(state, annotations, Main.parseModifiers(s2), targetInfix, beforeContent, inputContent);
		}).or(() => {
			let modifiers = Main.parseModifiers(beforeContent);
			return Main.compileStructureWithImplementing(state, Lists.empty(), modifiers, targetInfix, beforeContent, inputContent);
		}))));
	}
	mut static compileStructureWithImplementing(state: CompileState, annotations: List<&[I8]>, modifiers: List<&[I8]>, targetInfix: &[I8], beforeContent: &[I8], content: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileLast(beforeContent, " implements ", (s: &[I8], s2: &[I8]) => Main.parseType(state, s2).flatMap((implementingTuple: Tuple2<CompileState, Type>) => Main.compileStructureWithExtends(implementingTuple.left(), annotations, modifiers, targetInfix, s, new Some<Type>(implementingTuple.right()), content))).or(() => Main.compileStructureWithExtends(state, annotations, modifiers, targetInfix, beforeContent, new None<Type>(), content));
	}
	mut static compileStructureWithExtends(state: CompileState, annotations: List<&[I8]>, modifiers: List<&[I8]>, targetInfix: &[I8], beforeContent: &[I8], maybeImplementing: Option<Type>, inputContent: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileFirst(beforeContent, " extends ", (mut beforeExtends: &[I8], mut afterExtends: &[I8]) => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeExtends, new Some<&[I8]>(afterExtends), maybeImplementing, inputContent)).or(() => Main.compileStructureWithParameters(state, annotations, modifiers, targetInfix, beforeContent, new None<&[I8]>(), maybeImplementing, inputContent));
	}
	mut static compileStructureWithParameters(state: CompileState, annotations: List<&[I8]>, modifiers: List<&[I8]>, targetInfix: &[I8], beforeContent: &[I8], maybeSuperType: Option<&[I8]>, maybeImplementing: Option<Type>, inputContent: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileFirst(beforeContent, "(", (rawName: &[I8], withParameters: &[I8]) => Main.compileFirst(withParameters, ")", (parametersString: &[I8], _: &[I8]) => {
			let name = Strings.strip(rawName);
			let parametersTuple = Main.parseParameters(state, parametersString);
			let parameters = Main.retainDefinitionsFromParameters(parametersTuple.right());
			return Main.compileStructureWithTypeParams(parametersTuple.left(), targetInfix, inputContent, name, parameters, maybeImplementing, annotations, modifiers, maybeSuperType);
		})).or(() => Main.compileStructureWithTypeParams(state, targetInfix, inputContent, beforeContent, Lists.empty(), maybeImplementing, annotations, modifiers, maybeSuperType));
	}
	mut static retainDefinitionsFromParameters(parameters: List<Parameter>): List<Definition> {
		return parameters.query().map((mut parameter: Parameter) => parameter.asDefinition()).flatMap(Queries.fromOption).collect(new ListCollector<Definition>());
	}
	mut static compileStructureWithTypeParams(state: CompileState, infix: &[I8], content: &[I8], beforeParams: &[I8], parameters: List<Definition>, maybeImplementing: Option<Type>, annotations: List<&[I8]>, modifiers: List<&[I8]>, maybeSuperType: Option<&[I8]>): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileSuffix(Strings.strip(beforeParams), ">", (withoutTypeParamEnd: &[I8]) => Main.compileFirst(withoutTypeParamEnd, "<", (name: &[I8], typeParamsString: &[I8]) => {
			let typeParams = Main.divideValues(typeParamsString);
			return Main.assembleStructure(state, annotations, modifiers, infix, name, typeParams, parameters, maybeImplementing, content, maybeSuperType);
		})).or(() => Main.assembleStructure(state, annotations, modifiers, infix, beforeParams, Lists.empty(), parameters, maybeImplementing, content, maybeSuperType));
	}
	mut static assembleStructure(state: CompileState, annotations: List<&[I8]>, oldModifiers: List<&[I8]>, infix: &[I8], rawName: &[I8], typeParams: List<&[I8]>, parameters: List<Definition>, maybeImplementing: Option<Type>, content: &[I8], maybeSuperType: Option<&[I8]>): Option<Tuple2<CompileState, &[I8]>> {
		let name = Strings.strip(rawName);
		if (!Main.isSymbol(name)) {
			return new None<Tuple2<CompileState, &[I8]>>();
		}
		let outputContentTuple = Main.compileStatements(state.pushStructureName(name), content, Main.compileClassSegment);
		let outputContentState = outputContentTuple.left().popStructureName();
		let outputContent = outputContentTuple.right();
		let constructorString = Main.generateConstructorFromRecordParameters(parameters);
		let joinedTypeParams = Joiner.joinOrEmpty(typeParams, ", ", "<", ">");
		let implementingString = Main.generateImplementing(maybeImplementing);
		let newModifiers = Main.modifyModifiers0(oldModifiers);
		let joinedModifiers = newModifiers.query().map((mut value: &[I8]) => value + " ").collect(Joiner.empty()).orElse("");
		if (annotations.contains("Namespace", Strings.equalsTo)) {
			let actualInfix: &[I8] = "interface ";
			let newName: &[I8] = name + "Instance";
			let generated = joinedModifiers + actualInfix + newName + joinedTypeParams + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
			let withNewLocation = outputContentState.append(generated).mapLocation((mut location: Location) => new Location(location.namespace, location.name + "Instance"));
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(withNewLocation, ""));
		}
		else {
			let extendsString = maybeSuperType.map((mut inner: &[I8]) => " extends " + inner).orElse("");
			let infix1 = Main.retainStruct(infix, outputContentState);
			let generated = joinedModifiers + infix1 + name + joinedTypeParams + extendsString + implementingString + " {" + Main.joinParameters(parameters) + constructorString + outputContent + "\n}\n";
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(outputContentState.append(generated), ""));
		}
	}
	mut static retainStruct(infix: &[I8], outputContentState: CompileState): &[I8] {
		if (Platform.Magma === outputContentState.platform) {
			return "struct ";
		}
		return infix;
	}
	mut static modifyModifiers0(oldModifiers: List<&[I8]>): List<&[I8]> {
		if (oldModifiers.contains("public", Strings.equalsTo)) {
			return Lists.of("export");
		}
		return Lists.empty();
	}
	mut static generateImplementing(maybeImplementing: Option<Type>): &[I8] {
		return maybeImplementing.map((mut type: Type) => type.generate()).map((mut inner: &[I8]) => " implements " + inner).orElse("");
	}
	mut static generateConstructorFromRecordParameters(parameters: List<Definition>): &[I8] {
		return parameters.query().map((mut definition: Definition) => definition.generate()).collect(new Joiner(", ")).map((mut generatedParameters: &[I8]) => Main.generateConstructorWithParameterString(parameters, generatedParameters)).orElse("");
	}
	mut static generateConstructorWithParameterString(parameters: List<Definition>, parametersString: &[I8]): &[I8] {
		let constructorAssignments = Main.generateConstructorAssignments(parameters);
		return "\n\tconstructor (" + parametersString + ") {" + constructorAssignments + "\n\t}";
	}
	mut static generateConstructorAssignments(parameters: List<Definition>): &[I8] {
		return parameters.query().map((mut definition: Definition) => "\n\t\tthis." + definition.name() + " = " + definition.name() + ";").collect(Joiner.empty()).orElse("");
	}
	mut static joinParameters(parameters: List<Definition>): &[I8] {
		return parameters.query().map((mut definition: Definition) => definition.generate()).map((mut generated: &[I8]) => "\n\t" + generated + ";").collect(Joiner.empty()).orElse("");
	}
	mut static compileNamespaced(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		let stripped = Strings.strip(input);
		if (stripped.startsWith("package ")) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
		}
		return Main.compileImport(state, stripped);
	}
	mut static compileImport(state: CompileState, stripped: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compilePrefix(stripped, "import ", (s: &[I8]) => Main.compileSuffix(s, ";", (s1: &[I8]) => {
			let divisions = Main.divide(s1, (mut divideState: DivideState, mut c: I8) => Main.foldDelimited(divideState, c, "."));
			let child = Strings.strip(divisions.findLast().orElse(""));
			let parent = divisions.subList(0, divisions.size() - 1).orElse(Lists.empty());
			if (parent.equalsTo(Lists.of("java", "util", "function"), Strings.equalsTo)) {
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
			}
			let compileState = state.addResolvedImport(parent, child);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ""));
		}));
	}
	mut static compileOrPlaceholder(state: CompileState, input: &[I8], rules: List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>>): Tuple2<CompileState, &[I8]> {
		return Main.or(state, input, rules).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(state, Main.generatePlaceholder(input)));
	}
	mut static or<T>(state: CompileState, input: &[I8], rules: List<(arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>>): Option<Tuple2<CompileState, T>> {
		return rules.query().map((mut rule: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>) => Main.getApply(state, input, rule)).flatMap(Queries.fromOption).next();
	}
	mut static getApply<T>(state: CompileState, input: &[I8], rule: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		return rule(state, input);
	}
	mut static compileClassSegment(state1: CompileState, input1: &[I8]): Tuple2<CompileState, &[I8]> {
		return Main.compileOrPlaceholder(state1, input1, Lists.of(Main.compileWhitespace, Main.createStructureRule("class ", "class "), Main.createStructureRule("interface ", "interface "), Main.createStructureRule("record ", "class "), Main.createStructureRule("enum ", "class "), Main.compileMethod, Main.compileFieldDefinition));
	}
	mut static compileMethod(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileFirst(input, "(", (beforeParams: &[I8], withParams: &[I8]) => {
			let strippedBeforeParams = Strings.strip(beforeParams);
			return Main.compileLast(strippedBeforeParams, " ", (_: &[I8], name: &[I8]) => {
				if (state.isLastWithin(name)) {
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return new None<Tuple2<CompileState, &[I8]>>();
			}).or(() => {
				if (state.structureNames.findLast().filter((mut anObject: &[I8]) => Strings.equalsTo(strippedBeforeParams, anObject)).isPresent()) {
					return Main.compileMethodWithBeforeParams(state, new ConstructorHeader(), withParams);
				}
				return new None<Tuple2<CompileState, &[I8]>>();
			}).or(() => Main.parseDefinition(state, beforeParams).flatMap((mut tuple: Tuple2<CompileState, Definition>) => Main.compileMethodWithBeforeParams(tuple.left(), tuple.right(), withParams)));
		});
	}
	mut static compileMethodWithBeforeParams<S extends MethodHeader<S>>(state: CompileState, header: MethodHeader<S>, withParams: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileFirst(withParams, ")", (params: &[I8], afterParams: &[I8]) => {
			let parametersTuple = Main.parseParameters(state, params);
			let parametersState = parametersTuple.left();
			let parameters = parametersTuple.right();
			let definitions = Main.retainDefinitionsFromParameters(parameters);
			let joinedDefinitions = definitions.query().map((mut definition: Definition) => definition.generate()).collect(new Joiner(", ")).orElse("");
			let newHeader = Main.retainDef(header, parametersState);
			if (newHeader.hasAnnotation("Actual")) {
				let headerGenerated = newHeader.removeModifier("static").generateWithAfterName("(" + joinedDefinitions + ")");
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState, "\n\t" + headerGenerated + ";\n"));
			}
			let headerGenerated = newHeader.generateWithAfterName("(" + joinedDefinitions + ")");
			return Main.compilePrefix(Strings.strip(afterParams), "{", (withoutContentStart: &[I8]) => Main.compileSuffix(Strings.strip(withoutContentStart), "}", (withoutContentEnd: &[I8]) => {
				let statementsTuple = Main.compileFunctionStatements(parametersState.enterDepth().enterDepth().defineAll(definitions), withoutContentEnd);
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(statementsTuple.left().exitDepth().exitDepth(), "\n\t" + headerGenerated + " {" + statementsTuple.right() + "\n\t}"));
			})).or(() => {
				if (Strings.equalsTo(";", Strings.strip(afterParams))) {
					return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(parametersState, "\n\t" + headerGenerated + ";"));
				}
				return new None<Tuple2<CompileState, &[I8]>>();
			});
		});
	}
	mut static retainDef<S extends MethodHeader<S>>(header: MethodHeader<S>, parametersState: CompileState): MethodHeader<S> {
		if (Platform.Magma === parametersState.platform) {
			return header.addModifier("def").removeModifier("mut");
		}
		return header;
	}
	mut static parseParameters(state: CompileState, params: &[I8]): Tuple2<CompileState, List<Parameter>> {
		return Main.parseValuesOrEmpty(state, params, (mut state1: CompileState, mut s: &[I8]) => new Some<Tuple2<CompileState, Parameter>>(Main.parseParameterOrPlaceholder(state1, s)));
	}
	mut static compileFunctionStatements(state: CompileState, input: &[I8]): Tuple2<CompileState, &[I8]> {
		return Main.compileStatements(state, input, Main.compileFunctionSegment);
	}
	mut static compileFunctionSegment(state: CompileState, input: &[I8]): Tuple2<CompileState, &[I8]> {
		return Main.compileOrPlaceholder(state, input, Lists.of(Main.compileWhitespace, Main.compileEmptySegment, Main.compileBlock, Main.compileFunctionStatement, Main.compileReturnWithoutSuffix));
	}
	mut static compileEmptySegment(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		if (Strings.equalsTo(";", Strings.strip(input))) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, ";"));
		}
		else {
			return new None<Tuple2<CompileState, &[I8]>>();
		}
	}
	mut static compileReturnWithoutSuffix(state1: CompileState, input1: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileReturn(input1, (mut withoutPrefix: &[I8]) => Main.compileValue(state1, withoutPrefix)).map((mut tuple: Tuple2<CompileState, &[I8]>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), Main.generateIndent(state1.depth) + tuple.right()));
	}
	mut static compileBlock(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileSuffix(Strings.strip(input), "}", (withoutEnd: &[I8]) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldBlockStarts), (beforeContentWithEnd: &[I8], content: &[I8]) => Main.compileSuffix(beforeContentWithEnd, "{", (beforeContent: &[I8]) => Main.compileBlockHeader(state, beforeContent).flatMap((headerTuple: Tuple2<CompileState, &[I8]>) => {
			let contentTuple = Main.compileFunctionStatements(headerTuple.left().enterDepth(), content);
			let indent = Main.generateIndent(state.depth);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(contentTuple.left().exitDepth(), indent + headerTuple.right() + "{" + contentTuple.right() + indent + "}"));
		}))));
	}
	mut static foldBlockStarts(state: DivideState, c: I8): DivideState {
		let appended = state.append(c);
		if ("{" === c) {
			let entered = appended.enter();
			if (entered.isShallow()) {
				return entered.advance();
			}
			else {
				return entered;
			}
		}
		if ("}" === c) {
			return appended.exit();
		}
		return appended;
	}
	mut static compileBlockHeader(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.or(state, input, Lists.of(Main.createConditionalRule("if"), Main.createConditionalRule("while"), Main.compileElse));
	}
	mut static createConditionalRule(prefix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> {
		return (mut state1: CompileState, mut input1: &[I8]) => Main.compilePrefix(Strings.strip(input1), prefix, (withoutPrefix: &[I8]) => {
			let strippedCondition = Strings.strip(withoutPrefix);
			return Main.compilePrefix(strippedCondition, "(", (withoutConditionStart: &[I8]) => Main.compileSuffix(withoutConditionStart, ")", (withoutConditionEnd: &[I8]) => {
				let tuple = Main.compileValueOrPlaceholder(state1, withoutConditionEnd);
				return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), prefix + " (" + tuple.right() + ") "));
			}));
		});
	}
	mut static compileElse(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		if (Strings.equalsTo("else", Strings.strip(input))) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, "else "));
		}
		else {
			return new None<Tuple2<CompileState, &[I8]>>();
		}
	}
	mut static compileFunctionStatement(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: &[I8]) => {
			let valueTuple = Main.compileFunctionStatementValue(state, withoutEnd);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(valueTuple.left(), Main.generateIndent(state.depth) + valueTuple.right() + ";"));
		});
	}
	mut static generateIndent(indent: number): &[I8] {
		return "\n" + "\t".repeat(indent);
	}
	mut static compileFunctionStatementValue(state: CompileState, withoutEnd: &[I8]): Tuple2<CompileState, &[I8]> {
		return Main.compileOrPlaceholder(state, withoutEnd, Lists.of(Main.compileReturnWithValue, Main.compileAssignment, (mut state1: CompileState, mut input: &[I8]) => Main.parseInvokable(state1, input).map((mut tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate())), Main.createPostRule("++"), Main.createPostRule("--"), Main.compileBreak));
	}
	mut static compileBreak(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		if (Strings.equalsTo("break", Strings.strip(input))) {
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(state, "break"));
		}
		else {
			return new None<Tuple2<CompileState, &[I8]>>();
		}
	}
	mut static createPostRule(suffix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, &[I8]>> {
		return (mut state1: CompileState, mut input: &[I8]) => Main.compileSuffix(Strings.strip(input), suffix, (child: &[I8]) => {
			let tuple = Main.compileValueOrPlaceholder(state1, child);
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right() + suffix));
		});
	}
	mut static compileReturnWithValue(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileReturn(input, (mut value1: &[I8]) => Main.compileValue(state, value1));
	}
	mut static compileReturn(input: &[I8], mapper: (arg0 : &[I8]) => Option<Tuple2<CompileState, &[I8]>>): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compilePrefix(Strings.strip(input), "return ", (value: &[I8]) => mapper(value).flatMap((tuple: Tuple2<CompileState, &[I8]>) => new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(tuple.left(), "return " + tuple.right()))));
	}
	mut static parseInvokable(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
		return Main.compileSuffix(Strings.strip(input), ")", (withoutEnd: &[I8]) => Main.compileSplit(Main.splitFoldedLast(withoutEnd, "", Main.foldInvocationStarts), (callerWithArgStart: &[I8], args: &[I8]) => Main.compileSuffix(callerWithArgStart, "(", (callerString: &[I8]) => Main.compilePrefix(Strings.strip(callerString), "new ", (type: &[I8]) => Main.compileType(state, type).flatMap((callerTuple: Tuple2<CompileState, &[I8]>) => {
			let callerState = callerTuple.left();
			let caller = callerTuple.right();
			return Main.assembleInvokable(callerState, new ConstructionCaller(caller, callerState.platform), args);
		})).or(() => Main.parseValue(state, callerString).flatMap((callerTuple: Tuple2<CompileState, Value>) => Main.assembleInvokable(callerTuple.left(), callerTuple.right(), args))))));
	}
	mut static splitFoldedLast(input: &[I8], delimiter: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState): Option<Tuple2<&[I8], &[I8]>> {
		return Main.splitFolded(input, folder, (mut divisions1: List<&[I8]>) => Main.selectLast(divisions1, delimiter));
	}
	mut static splitFolded(input: &[I8], folder: (arg0 : DivideState, arg1 : I8) => DivideState, selector: (arg0 : List<&[I8]>) => Option<Tuple2<&[I8], &[I8]>>): Option<Tuple2<&[I8], &[I8]>> {
		let divisions = Main.divide(input, folder);
		if (2 > divisions.size()) {
			return new None<Tuple2<&[I8], &[I8]>>();
		}
		return selector(divisions);
	}
	mut static selectLast(divisions: List<&[I8]>, delimiter: &[I8]): Option<Tuple2<&[I8], &[I8]>> {
		let beforeLast = divisions.subList(0, divisions.size() - 1).orElse(divisions);
		let last = divisions.findLast().orElse("");
		let joined = beforeLast.query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(joined, last));
	}
	mut static foldInvocationStarts(state: DivideState, c: I8): DivideState {
		let appended = state.append(c);
		if ("(" === c) {
			let entered = appended.enter();
			if (entered.isShallow()) {
				return entered.advance();
			}
			else {
				return entered;
			}
		}
		if (")" === c) {
			return appended.exit();
		}
		return appended;
	}
	mut static assembleInvokable(state: CompileState, oldCaller: Caller, argsString: &[I8]): Option<Tuple2<CompileState, Value>> {
		return Main.parseValues(state, argsString, (mut state1: CompileState, mut s: &[I8]) => Main.parseArgument(state1, s)).flatMap((argsTuple: Tuple2<CompileState, List<Argument>>) => {
			let argsState = argsTuple.left();
			let args = Main.retain(argsTuple.right(), (mut argument: Argument) => argument.toValue());
			let newCaller = Main.transformCaller(argsState, oldCaller);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(argsState, new Invokable(newCaller, args)));
		});
	}
	mut static transformCaller(state: CompileState, oldCaller: Caller): Caller {
		return oldCaller.findChild().flatMap((parent: Value) => {
			let parentType = parent.resolve(state);
			if (parentType.isFunctional()) {
				return new Some<Caller>(parent);
			}
			return new None<Caller>();
		}).orElse(oldCaller);
	}
	mut static retain<T, R>(args: List<T>, mapper: (arg0 : T) => Option<R>): List<R> {
		return args.query().map(mapper).flatMap(Queries.fromOption).collect(new ListCollector<R>());
	}
	mut static parseArgumentOrPlaceholder(state1: CompileState, input: &[I8]): Tuple2<CompileState, Argument> {
		return Main.parseArgument(state1, input).orElseGet(() => new Tuple2Impl<CompileState, Argument>(state1, new Placeholder(input)));
	}
	mut static parseArgument(state1: CompileState, input: &[I8]): Option<Tuple2<CompileState, Argument>> {
		return Main.parseValue(state1, input).map((mut tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, Argument>(tuple.left(), tuple.right()));
	}
	mut static compileAssignment(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileFirst(input, "=", (destination: &[I8], source: &[I8]) => {
			let sourceTuple = Main.compileValueOrPlaceholder(state, source);
			let destinationTuple = Main.compileValue(sourceTuple.left(), destination).or(() => Main.parseDefinition(sourceTuple.left(), destination).map((tuple: Tuple2<CompileState, Definition>) => {
				return new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().addModifier("let").generate());
			})).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(sourceTuple.left(), Main.generatePlaceholder(destination)));
			return new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(destinationTuple.left(), destinationTuple.right() + " = " + sourceTuple.right()));
		});
	}
	mut static compileValueOrPlaceholder(state: CompileState, input: &[I8]): Tuple2<CompileState, &[I8]> {
		return Main.compileValue(state, input).orElseGet(() => new Tuple2Impl<CompileState, &[I8]>(state, Main.generatePlaceholder(input)));
	}
	mut static compileValue(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.parseValue(state, input).map((mut tuple: Tuple2<CompileState, Value>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate()));
	}
	mut static parseValue(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
		return Main.or(state, input, Lists.of(Main.parseLambda, Main.createOperatorRule("+"), Main.createOperatorRule("-"), Main.createOperatorRule("<="), Main.createOperatorRule("<"), Main.createOperatorRule("&&"), Main.createOperatorRule("||"), Main.createOperatorRule(">"), Main.createOperatorRule(">="), Main.parseInvokable, Main.createAccessRule("."), Main.createAccessRule("::"), Main.parseSymbol, Main.parseNot, Main.parseNumber, Main.createOperatorRuleWithDifferentInfix("==", "==="), Main.createOperatorRuleWithDifferentInfix("!=", "!=="), Main.createTextRule("\""), Main.createTextRule("'")));
	}
	mut static createTextRule(slice: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: &[I8]) => {
			let stripped = Strings.strip(input1);
			return Main.compilePrefix(stripped, slice, (s: &[I8]) => Main.compileSuffix(s, slice, (mut s1: &[I8]) => new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state1, new StringValue(s1)))));
		};
	}
	mut static parseNot(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
		return Main.compilePrefix(Strings.strip(input), "!", (withoutPrefix: &[I8]) => {
			let childTuple = Main.compileValueOrPlaceholder(state, withoutPrefix);
			let childState = childTuple.left();
			let child = "!" + childTuple.right();
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Not(child)));
		});
	}
	mut static parseLambda(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
		return Main.compileFirst(input, "->", (beforeArrow: &[I8], afterArrow: &[I8]) => {
			let strippedBeforeArrow = Strings.strip(beforeArrow);
			return Main.compilePrefix(strippedBeforeArrow, "(", (withoutStart: &[I8]) => Main.compileSuffix(withoutStart, ")", (withoutEnd: &[I8]) => Main.parseValues(state, withoutEnd, (mut state1: CompileState, mut s: &[I8]) => Main.parseParameter(state1, s)).flatMap((paramNames: Tuple2<CompileState, List<Parameter>>) => Main.compileLambdaWithParameterNames(paramNames.left(), Main.retainDefinitionsFromParameters(paramNames.right()), afterArrow))));
		});
	}
	mut static compileLambdaWithParameterNames(state: CompileState, paramNames: List<Definition>, afterArrow: &[I8]): Option<Tuple2<CompileState, Value>> {
		let strippedAfterArrow = Strings.strip(afterArrow);
		return Main.compilePrefix(strippedAfterArrow, "{", (withoutContentStart: &[I8]) => Main.compileSuffix(withoutContentStart, "}", (withoutContentEnd: &[I8]) => {
			let statementsTuple = Main.compileFunctionStatements(state.enterDepth().defineAll(paramNames), withoutContentEnd);
			let statementsState = statementsTuple.left();
			let statements = statementsTuple.right();
			let exited = statementsState.exitDepth();
			return Main.assembleLambda(exited, paramNames, "{" + statements + Main.generateIndent(exited.depth) + "}");
		})).or(() => Main.compileValue(state, strippedAfterArrow).flatMap((tuple: Tuple2<CompileState, &[I8]>) => Main.assembleLambda(tuple.left(), paramNames, tuple.right())));
	}
	mut static assembleLambda(exited: CompileState, paramNames: List<Definition>, content: &[I8]): Option<Tuple2<CompileState, Value>> {
		return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(exited, new Lambda(paramNames, content)));
	}
	mut static createOperatorRule(infix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> {
		return Main.createOperatorRuleWithDifferentInfix(infix, infix);
	}
	mut static createAccessRule(infix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> {
		return (mut state: CompileState, mut input: &[I8]) => Main.compileLast(input, infix, (childString: &[I8], rawProperty: &[I8]) => {
			let property = Strings.strip(rawProperty);
			if (!Main.isSymbol(property)) {
				return new None<Tuple2<CompileState, Value>>();
			}
			return Main.parseValue(state, childString).flatMap((childTuple: Tuple2<CompileState, Value>) => {
				let childState = childTuple.left();
				let child = childTuple.right();
				return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(childState, new Access(child, property)));
			});
		});
	}
	mut static createOperatorRuleWithDifferentInfix(sourceInfix: &[I8], targetInfix: &[I8]): (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, Value>> {
		return (state1: CompileState, input1: &[I8]) => Main.compileSplit(Main.splitFolded(input1, Main.foldOperator(sourceInfix), (mut divisions: List<&[I8]>) => Main.selectFirst(divisions, sourceInfix)), (leftString: &[I8], rightString: &[I8]) => Main.parseValue(state1, leftString).flatMap((leftTuple: Tuple2<CompileState, Value>) => Main.parseValue(leftTuple.left(), rightString).flatMap((rightTuple: Tuple2<CompileState, Value>) => {
			let left = leftTuple.right();
			let right = rightTuple.right();
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(rightTuple.left(), new Operation(left, targetInfix, right)));
		})));
	}
	mut static selectFirst(divisions: List<&[I8]>, delimiter: &[I8]): Option<Tuple2<&[I8], &[I8]>> {
		let first = divisions.findFirst().orElse("");
		let afterFirst = divisions.subList(1, divisions.size()).orElse(divisions).query().collect(new Joiner(delimiter)).orElse("");
		return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(first, afterFirst));
	}
	mut static foldOperator(infix: &[I8]): (arg0 : DivideState, arg1 : I8) => DivideState {
		return (state: DivideState, c: I8) => {
			if (c === Strings.charAt(infix, 0) && state.startsWith(Strings.sliceFrom(infix, 1))) {
				let length = Strings.length(infix) - 1;
				let mut counter = 0;
				let mut current = state;
				while (counter < length) {
					counter++;
					current = current.pop().map((mut tuple: Tuple2<DivideState, I8>) => tuple.left()).orElse(current);
				}
				return current.advance();
			}
			return state.append(c);
		};
	}
	mut static parseNumber(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input);
		if (Main.isNumber(stripped)) {
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(state, new SymbolNode(stripped)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>();
		}
	}
	mut static isNumber(input: &[I8]): Bool {
		let query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
		return query.map((mut index: number) => input.charAt(index)).allMatch((mut c: I8) => Characters.isDigit(c));
	}
	mut static parseSymbol(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Value>> {
		let stripped = Strings.strip(input);
		if (Main.isSymbol(stripped)) {
			let withImport = state.addResolvedImportFromCache(stripped);
			return new Some<Tuple2<CompileState, Value>>(new Tuple2Impl<CompileState, Value>(withImport, new SymbolNode(stripped)));
		}
		else {
			return new None<Tuple2<CompileState, Value>>();
		}
	}
	mut static isSymbol(input: &[I8]): Bool {
		let query = new HeadedQuery<number>(new RangeHead(Strings.length(input)));
		return query.allMatch((mut index: number) => Main.isSymbolChar(index, Strings.charAt(input, index)));
	}
	mut static isSymbolChar(index: number, c: I8): Bool {
		return "_" === c || Characters.isLetter(c) || (0 !== index && Characters.isDigit(c));
	}
	mut static compilePrefix<T>(input: &[I8], infix: &[I8], mapper: (arg0 : &[I8]) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, T>> {
		if (!input.startsWith(infix)) {
			return new None<Tuple2<CompileState, T>>();
		}
		let slice = Strings.sliceFrom(input, Strings.length(infix));
		return mapper(slice);
	}
	mut static compileWhitespace(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.parseWhitespace(state, input).map((mut tuple: Tuple2<CompileState, Whitespace>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate()));
	}
	mut static parseWhitespace(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Whitespace>> {
		if (Strings.isBlank(input)) {
			return new Some<Tuple2<CompileState, Whitespace>>(new Tuple2Impl<CompileState, Whitespace>(state, new Whitespace()));
		}
		return new None<Tuple2<CompileState, Whitespace>>();
	}
	mut static compileFieldDefinition(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.compileSuffix(Strings.strip(input), ";", (withoutEnd: &[I8]) => Main.getTupleOption(state, withoutEnd).or(() => Main.compileEnumValues(state, withoutEnd)));
	}
	mut static getTupleOption(state: CompileState, withoutEnd: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.parseParameter(state, withoutEnd).flatMap((definitionTuple: Tuple2<CompileState, Parameter>) => new Some<Tuple2<CompileState, &[I8]>>(new Tuple2Impl<CompileState, &[I8]>(definitionTuple.left(), "\n\t" + definitionTuple.right().generate() + ";")));
	}
	mut static compileEnumValues(state: CompileState, withoutEnd: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.parseValues(state, withoutEnd, (state1: CompileState, s: &[I8]) => Main.parseInvokable(state1, s).flatMap((tuple: Tuple2<CompileState, Value>) => {
			let structureName = state.structureNames.findLast().orElse("");
			return tuple.right().generateAsEnumValue(structureName).map((stringOption: &[I8]) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), stringOption));
		})).map((tuple: Tuple2<CompileState, List<&[I8]>>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().query().collect(new Joiner("")).orElse("")));
	}
	mut static parseParameterOrPlaceholder(state: CompileState, input: &[I8]): Tuple2<CompileState, Parameter> {
		return Main.parseParameter(state, input).orElseGet(() => new Tuple2Impl<CompileState, Parameter>(state, new Placeholder(input)));
	}
	mut static parseParameter(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Parameter>> {
		return Main.parseWhitespace(state, input).map((mut tuple: Tuple2<CompileState, Whitespace>) => Main.getCompileStateParameterTuple2(tuple)).or(() => Main.parseDefinition(state, input).map((mut tuple: Tuple2<CompileState, Definition>) => new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right())));
	}
	mut static getCompileStateParameterTuple2(tuple: Tuple2<CompileState, Whitespace>): Tuple2<CompileState, Parameter> {
		return new Tuple2Impl<CompileState, Parameter>(tuple.left(), tuple.right());
	}
	mut static parseDefinition(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Definition>> {
		return Main.compileLast(Strings.strip(input), " ", (beforeName: &[I8], name: &[I8]) => Main.compileSplit(Main.splitFoldedLast(Strings.strip(beforeName), " ", Main.foldTypeSeparators), (beforeType: &[I8], type: &[I8]) => Main.compileLast(Strings.strip(beforeType), "\n", (annotationsString: &[I8], afterAnnotations: &[I8]) => {
			let annotations = Main.parseAnnotations(annotationsString);
			return Main.parseDefinitionWithAnnotations(state, annotations, afterAnnotations, type, name);
		}).or(() => Main.parseDefinitionWithAnnotations(state, Lists.empty(), beforeType, type, name))).or(() => Main.parseDefinitionWithTypeParameters(state, Lists.empty(), Lists.empty(), Lists.empty(), beforeName, name)));
	}
	mut static parseAnnotations(s: &[I8]): List<&[I8]> {
		return Main.divide(s, (mut state1: DivideState, mut c: I8) => Main.foldDelimited(state1, c, "\n")).query().map((mut s2: &[I8]) => Strings.strip(s2)).filter((mut value: &[I8]) => !Strings.isEmpty(value)).filter((mut value: &[I8]) => 1 <= Strings.length(value)).map((mut value: &[I8]) => Strings.sliceFrom(value, 1)).map((mut s1: &[I8]) => Strings.strip(s1)).filter((mut value: &[I8]) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
	}
	mut static parseDefinitionWithAnnotations(state: CompileState, annotations: List<&[I8]>, beforeType: &[I8], type: &[I8], name: &[I8]): Option<Tuple2<CompileState, Definition>> {
		return Main.compileSuffix(Strings.strip(beforeType), ">", (withoutTypeParamEnd: &[I8]) => Main.compileFirst(withoutTypeParamEnd, "<", (beforeTypeParams: &[I8], typeParamsString: &[I8]) => {
			let typeParams = Main.divideValues(typeParamsString);
			return Main.parseDefinitionWithTypeParameters(state, annotations, typeParams, Main.parseModifiers(beforeTypeParams), type, name);
		})).or(() => {
			let divided = Main.parseModifiers(beforeType);
			return Main.parseDefinitionWithTypeParameters(state, annotations, Lists.empty(), divided, type, name);
		});
	}
	mut static parseModifiers(beforeType: &[I8]): List<&[I8]> {
		return Main.divide(Strings.strip(beforeType), (mut state1: DivideState, mut c: I8) => Main.foldDelimited(state1, c, " ")).query().map((mut s: &[I8]) => Strings.strip(s)).filter((mut value: &[I8]) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
	}
	mut static foldDelimited(state1: DivideState, c: I8, delimiter: I8): DivideState {
		if (delimiter === c) {
			return state1.advance();
		}
		return state1.append(c);
	}
	mut static divideValues(input: &[I8]): List<&[I8]> {
		return Main.divide(input, Main.foldValues).query().map((mut input1: &[I8]) => Strings.strip(input1)).filter((mut value: &[I8]) => !Strings.isEmpty(value)).collect(new ListCollector<&[I8]>());
	}
	mut static foldTypeSeparators(state: DivideState, c: I8): DivideState {
		if (" " === c && state.isLevel()) {
			return state.advance();
		}
		let appended = state.append(c);
		if ("<" === c) {
			return appended.enter();
		}
		if (">" === c) {
			return appended.exit();
		}
		return appended;
	}
	mut static parseDefinitionWithTypeParameters(state: CompileState, annotations: List<&[I8]>, typeParams: List<&[I8]>, oldModifiers: List<&[I8]>, type: &[I8], name: &[I8]): Option<Tuple2<CompileState, Definition>> {
		return Main.parseType(state, type).flatMap((typeTuple: Tuple2<CompileState, Type>) => {
			let newModifiers = Main.modifyModifiers(oldModifiers, state.platform);
			let generated = new Definition(annotations, newModifiers, typeParams, typeTuple.right(), name);
			return new Some<Tuple2<CompileState, Definition>>(new Tuple2Impl<CompileState, Definition>(typeTuple.left(), generated));
		});
	}
	mut static modifyModifiers(oldModifiers: List<&[I8]>, platform: Platform): List<&[I8]> {
		let list: List<&[I8]> = Main.retainFinal(oldModifiers, platform);
		if (oldModifiers.contains("static", Strings.equalsTo)) {
			return list.addLast("static");
		}
		return list;
	}
	mut static retainFinal(oldModifiers: List<&[I8]>, platform: Platform): List<&[I8]> {
		if (oldModifiers.contains("final", Strings.equalsTo) || Platform.TypeScript === platform) {
			return Lists.empty();
		}
		return Lists.of("mut");
	}
	mut static parseTypeOrPlaceholder(state: CompileState, type: &[I8]): Tuple2<CompileState, Type> {
		return Main.parseType(state, type).map((mut tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, Type>(tuple.left(), tuple.right())).orElseGet(() => new Tuple2Impl<CompileState, Type>(state, new Placeholder(type)));
	}
	mut static compileType(state: CompileState, type: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.parseType(state, type).map((mut tuple: Tuple2<CompileState, Type>) => new Tuple2Impl<CompileState, &[I8]>(tuple.left(), tuple.right().generate()));
	}
	mut static parseType(state: CompileState, type: &[I8]): Option<Tuple2<CompileState, Type>> {
		return Main.or(state, type, Lists.of(Main.parseArrayType, Main.parseVarArgs, Main.parseGeneric, Main.parsePrimitive, Main.parseSymbolType));
	}
	mut static parseArrayType(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input);
		return Main.compileSuffix(stripped, "[]", (s: &[I8]) => {
			let child = Main.parseTypeOrPlaceholder(state, s);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new ArrayType(child.right())));
		});
	}
	mut static parseVarArgs(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input);
		return Main.compileSuffix(stripped, "...", (s: &[I8]) => {
			let child = Main.parseTypeOrPlaceholder(state, s);
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(child.left(), new VarArgs(child.right())));
		});
	}
	mut static parseSymbolType(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
		let stripped = Strings.strip(input);
		if (Main.isSymbol(stripped)) {
			return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(state.addResolvedImportFromCache(stripped), new SymbolNode(stripped)));
		}
		return new None<Tuple2<CompileState, Type>>();
	}
	mut static parsePrimitive(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
		return Main.findPrimitiveValue(Strings.strip(input), state.platform).map((mut result: Type) => new Tuple2Impl<CompileState, Type>(state, result));
	}
	mut static findPrimitiveValue(input: &[I8], platform: Platform): Option<Type> {
		let stripped = Strings.strip(input);
		if (Strings.equalsTo("char", stripped) || Strings.equalsTo("Character", stripped)) {
			if (Platform.TypeScript === platform) {
				return new Some<Type>(Primitive.String);
			}
			else {
				return new Some<Type>(Primitive.I8);
			}
		}
		if (Strings.equalsTo("String", stripped)) {
			if (Platform.TypeScript === platform) {
				return new Some<Type>(Primitive.String);
			}
			else {
				return new Some<Type>(new Slice(Primitive.I8));
			}
		}
		if (Strings.equalsTo("int", stripped) || Strings.equalsTo("Integer", stripped)) {
			if (Platform.Magma === platform) {
				return new Some<Type>(Primitive.I32);
			}
			else {
				return new Some<Type>(Primitive.Number);
			}
		}
		if (Strings.equalsTo("boolean", stripped) || Strings.equalsTo("Boolean", stripped)) {
			return new Some<Type>(new BooleanType(platform));
		}
		if (Strings.equalsTo("var", stripped)) {
			return new Some<Type>(Primitive.Var);
		}
		if (Strings.equalsTo("void", stripped)) {
			return new Some<Type>(Primitive.Void);
		}
		return new None<Type>();
	}
	mut static parseGeneric(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, Type>> {
		return Main.compileSuffix(Strings.strip(input), ">", (withoutEnd: &[I8]) => Main.compileFirst(withoutEnd, "<", (baseString: &[I8], argsString: &[I8]) => {
			let argsTuple = Main.parseValuesOrEmpty(state, argsString, (mut state1: CompileState, mut s: &[I8]) => Main.compileTypeArgument(state1, s));
			let argsState = argsTuple.left();
			let args = argsTuple.right();
			let base = Strings.strip(baseString);
			return Main.assembleFunctionType(argsState, base, args).or(() => {
				let compileState = argsState.addResolvedImportFromCache(base);
				return new Some<Tuple2<CompileState, Type>>(new Tuple2Impl<CompileState, Type>(compileState, new Generic(base, args)));
			});
		}));
	}
	mut static assembleFunctionType(state: CompileState, base: &[I8], args: List<&[I8]>): Option<Tuple2<CompileState, Type>> {
		return Main.mapFunctionType(base, args).map((mut generated: Type) => new Tuple2Impl<CompileState, Type>(state, generated));
	}
	mut static mapFunctionType(base: &[I8], args: List<&[I8]>): Option<Type> {
		if (Strings.equalsTo("Function", base)) {
			return args.findFirst().and(() => args.find(1)).map((mut tuple: Tuple2<&[I8], &[I8]>) => new FunctionType(Lists.of(tuple.left()), tuple.right()));
		}
		if (Strings.equalsTo("BiFunction", base)) {
			return args.find(0).and(() => args.find(1)).and(() => args.find(2)).map((mut tuple: Tuple2<Tuple2<&[I8], &[I8]>, &[I8]>) => new FunctionType(Lists.of(tuple.left().left(), tuple.left().right()), tuple.right()));
		}
		if (Strings.equalsTo("Supplier", base)) {
			return args.findFirst().map((first: &[I8]) => new FunctionType(Lists.empty(), first));
		}
		if (Strings.equalsTo("Consumer", base)) {
			return args.findFirst().map((first: &[I8]) => new FunctionType(Lists.of(first), "void"));
		}
		if (Strings.equalsTo("Predicate", base)) {
			return args.findFirst().map((first: &[I8]) => new FunctionType(Lists.of(first), "boolean"));
		}
		return new None<Type>();
	}
	mut static compileTypeArgument(state: CompileState, input: &[I8]): Option<Tuple2<CompileState, &[I8]>> {
		return Main.or(state, input, Lists.of((mut state2: CompileState, mut input1: &[I8]) => Main.compileWhitespace(state2, input1), (mut state1: CompileState, mut type: &[I8]) => Main.compileType(state1, type)));
	}
	mut static parseValuesOrEmpty<T>(state: CompileState, input: &[I8], mapper: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>): Tuple2<CompileState, List<T>> {
		return Main.parseValues(state, input, mapper).orElse(new Tuple2Impl<CompileState, List<T>>(state, Lists.empty()));
	}
	mut static parseValues<T>(state: CompileState, input: &[I8], mapper: (arg0 : CompileState, arg1 : &[I8]) => Option<Tuple2<CompileState, T>>): Option<Tuple2<CompileState, List<T>>> {
		return Main.parseAll(state, input, Main.foldValues, mapper);
	}
	mut static foldValues(state: DivideState, c: I8): DivideState {
		if ("," === c && state.isLevel()) {
			return state.advance();
		}
		let appended = state.append(c);
		if ("-" === c) {
			let peeked = appended.peek();
			if (">" === peeked) {
				return appended.popAndAppendToOption().orElse(appended);
			}
			else {
				return appended;
			}
		}
		if ("<" === c || "(" === c) {
			return appended.enter();
		}
		if (">" === c || ")" === c) {
			return appended.exit();
		}
		return appended;
	}
	mut static compileLast<T>(input: &[I8], infix: &[I8], mapper: (arg0 : &[I8], arg1 : &[I8]) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findLast, mapper);
	}
	mut static findLast(input: &[I8], infix: &[I8]): number {
		return input.lastIndexOf(infix);
	}
	mut static compileSuffix<T>(input: &[I8], suffix: &[I8], mapper: (arg0 : &[I8]) => Option<T>): Option<T> {
		if (!input.endsWith(suffix)) {
			return new None<T>();
		}
		let length = Strings.length(input);
		let length1 = Strings.length(suffix);
		let content = Strings.sliceBetween(input, 0, length - length1);
		return mapper(content);
	}
	mut static compileFirst<T>(input: &[I8], infix: &[I8], mapper: (arg0 : &[I8], arg1 : &[I8]) => Option<T>): Option<T> {
		return Main.compileInfix(input, infix, Main.findFirst, mapper);
	}
	mut static compileInfix<T>(input: &[I8], infix: &[I8], locator: (arg0 : &[I8], arg1 : &[I8]) => number, mapper: (arg0 : &[I8], arg1 : &[I8]) => Option<T>): Option<T> {
		return Main.compileSplit(Main.split(input, infix, locator), mapper);
	}
	mut static compileSplit<T>(splitter: Option<Tuple2<&[I8], &[I8]>>, mapper: (arg0 : &[I8], arg1 : &[I8]) => Option<T>): Option<T> {
		return splitter.flatMap((mut tuple: Tuple2<&[I8], &[I8]>) => mapper(tuple.left(), tuple.right()));
	}
	mut static split(input: &[I8], infix: &[I8], locator: (arg0 : &[I8], arg1 : &[I8]) => number): Option<Tuple2<&[I8], &[I8]>> {
		let index = locator(input, infix);
		if (0 > index) {
			return new None<Tuple2<&[I8], &[I8]>>();
		}
		let left = Strings.sliceBetween(input, 0, index);
		let length = Strings.length(infix);
		let right = Strings.sliceFrom(input, index + length);
		return new Some<Tuple2<&[I8], &[I8]>>(new Tuple2Impl<&[I8], &[I8]>(left, right));
	}
	mut static findFirst(input: &[I8], infix: &[I8]): number {
		return input.indexOf(infix);
	}
	mut static generatePlaceholder(input: &[I8]): &[I8] {
		let replaced = input.replace("/*", "start").replace("*/", "end");
		return "/*" + replaced + "*/";
	}
}
Main.main();
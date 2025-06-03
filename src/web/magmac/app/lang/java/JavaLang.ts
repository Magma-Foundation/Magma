export interface JavaArgument {
}
export interface JavaCaller {
}
export interface Assignable {
}
export interface Value {
}
export interface BlockHeader {
}
export interface Base {
}
export interface JavaLambdaContent {
}
export interface JavaType {
}
export interface JavaLambdaHeader {
}
export interface JavaLambdaParameter {
}
export interface InstanceOfDefinition {
}
export class InstanceOfDefinitionWithParameters {
}
export class InstanceOfDefinitionWithName {
}
export class Invokable {
	 Invokable( caller : JavaCaller,  arguments : List<JavaArgument>) : public {super( caller, arguments);;}
}
export class Symbol {
	 Symbol( value : String) : public {super( value);;}
}
export class JavaArrayType {
	constructor( arrayType : JavaType) {this.inner=arrayType;;}
	public static createArrayRule( rule : Rule) : Rule { let child : var=new NodeRule( "child", rule);return new TypeRule( "array", new StripRule( new SuffixRule( child, "[]")));;}
	public static deserialize( node : Node) : Option<CompileResult<JavaType>> {return Destructors.destructWithType( "array", node).map( 0);;}
}
export class Root {
	public static getChildren( node : Node,  deserializer : Deserializer<JavaRootSegment>) : CompileResult<Root> {return Destructors.destruct( node).withNodeList( "children", deserializer).complete( Root.new);;}
}
export class Lambda {
	 Lambda( header : JavaLambdaHeader,  content : JavaLambdaContent) : public {super( header, content);;}
}
export class Access {
	 Access( type : JavaAccessType,  receiver : Value,  arguments : Option<List<JavaType>>,  property : String) : public {super( type, receiver, property, arguments);;}
}
export class JavaLambdaValueContent {
	 JavaLambdaValueContent( value : Value) : public {super( value);;}
}
export class JavaLambdaBlockContent {
	public static deserialize( node : Node) : Option<CompileResult<JavaLambdaBlockContent>> {return Destructors.destructWithType( "block", node).map( 0);;}
}
export class Conditional {
	 Conditional( type : ConditionalType,  condition : Value) : public {super( type, condition);;}
}
export class JavaTemplateType {
}
export class JavaStructureNodeDeserializer {
	private static deserializeHelper( type : JavaStructureType,  deserializer : InitialDestructor) : CompileResult<Structure> {return JavaStructureNodeDeserializer.attachOptionals( JavaStructureNodeDeserializer.attachRequired( deserializer)).complete( 0);;}
	private static attachRequired( deserializer : InitialDestructor) : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>> {return deserializer.withString( "name").withNodeList( "modifiers", Modifier.deserialize).withNodeList( "children", StructureMembers.deserialize);;}
	private static attachOptionals( attachRequired : CompoundDestructor<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>>) : CompoundDestructor<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>> {return attachRequired.withNodeListOptionally( "implemented", JavaDeserializers.deserializeType).withNodeListOptionally( "type-parameters", TypescriptLang.TypeParam.deserialize).withNodeListOptionally( "parameters", JavaDeserializers.deserializeParameter).withNodeListOptionally( "extended", JavaDeserializers.deserializeType).withNodeListOptionally( "variants", JavaDeserializers.deserializeType);;}
	private static from( type : JavaStructureType,  tuple : Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<Tuple2<String, List<Modifier>>, List<JavaStructureMember>>, Option<List<JavaType>>>, Option<List<TypescriptLang.TypeParam>>>, Option<List<JavaParameter>>>, Option<List<JavaType>>>, Option<List<JavaType>>>) : Structure {return new Structure( type, new StructureValue<JavaType, JavaStructureMember>( tuple.left( ).left( ).left( ).left( ).left( ).left( ).left( ), tuple.left( ).left( ).left( ).left( ).left( ).left( ).right( ), tuple.left( ).left( ).left( ).left( ).left( ).right( ), tuple.left( ).left( ).left( ).right( ), tuple.left( ).right( ), tuple.left( ).left( ).left( ).left( ).right( )), tuple.left( ).left( ).right( ), tuple.right( ));;}
	public deserialize( node : Node) : Option<CompileResult<Structure>> {return Destructors.destructWithType( this.type( ).name( ).toLowerCase( ), node).map( 0);;}
}
export class Try {
}
export class Else {
}
export class Catch {
	public static deserialize( node : Node) : Option<CompileResult<BlockHeader>> {return Destructors.destructWithType( "catch", node).map( 0);;}
}
export class Qualified {
	public static deserializeQualified( node : Node) : Option<CompileResult<Qualified>> {return Destructors.destructWithType( "qualified", node).map( 0);;}
	public static createQualifiedRule() : TypeRule {return new TypeRule( "qualified", Qualified.createSegmentsRule( ));;}
	private static createSegmentsRule() : Rule {return NodeListRule.createNodeListRule( "segments", new DelimitedFolder( '.'), CommonRules.createSymbolRule( "value"));;}
	public serialize() : Node {return new MapNode( "qualified").withNodeListAndSerializer( "segments", this.segments, Segment.serialize);;}
}
export class JavaVariadicType {
	public static deserialize( node : Node) : Option<CompileResult<JavaType>> {return Destructors.destructWithType( "variadic", node).map( 0);;}
	public static createVariadicRule( rule : Rule) : Rule { let child : var=new NodeRule( "child", rule);return new TypeRule( "variadic", new StripRule( new SuffixRule( child, "...")));;}
}
export class Number {
	 Number( value : String) : public {super( value);;}
}
export class StringValue {
	 StringValue( value : String) : public {super( value);;}
}
export class operation {
	public serialize() : Node {return new MapNode( this.operator.type( ));;}
}
export class Char {
}
export class Not {
}
export class Index {
}
export class SwitchNode {
}
export class Definition {
	 Definition( maybeAnnotations : Option<List<Annotation>>,  modifiers : List<Modifier>,  name : String,  maybeTypeParams : Option<List<TypescriptLang.TypeParam>>,  type : JavaType) : public {super( maybeAnnotations, modifiers, name, maybeTypeParams, type);;}
}
export class JavaMultipleHeader {
}
export class Whitespace {
}
export class FunctionStatement {
	 FunctionStatement( child : JavaFunctionSegmentValue) : public {super( child);;}
}
export class Return {
	 Return( child : Value) : public {super( child);;}
}
export class Case {
}
export class Block {
	 Block( header : BlockHeader,  segments : List<JavaFunctionSegment>) : public {super( header, segments);;}
}
export class Assignment {
}
export class InstanceOf {
}
export class Structure {
	constructor( type : JavaStructureType,  structureNode : StructureValue<JavaType, JavaStructureMember>,  parameters : Option<List<JavaParameter>>,  variants : Option<List<JavaType>>) {this.type=type;this.value=structureNode;;}
	public type() : JavaStructureType {return this.type;;}
	public name() : String {return this.value.name( );;}
	public implemented() : Option<List<JavaType>> {return this.value.maybeImplemented( );;}
	public extended() : Option<List<JavaType>> {return this.value.maybeExtended( );;}
}
export class Construction {
}
export class JavaLang {
}

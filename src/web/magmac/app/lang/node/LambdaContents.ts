export class LambdaContents {
	public static deserialize( node : Node) : CompileResult<JavaLang.JavaLambdaContent> {return Deserializers.orError( "lambda-content", node, Lists.of( Deserializers.wrap( JavaRules.deserializeLambdaValueContent), Deserializers.wrap( JavaLang.JavaLambdaBlockContent.deserialize)));;}
}

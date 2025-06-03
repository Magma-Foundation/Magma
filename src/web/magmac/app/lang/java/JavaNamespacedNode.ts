export class JavaNamespacedNode {
	private static deserialize( type : NamespacedType,  node : Node) : Option<CompileResult<JavaRootSegment>> {return Destructors.destructWithType( type.type( ), node).map( 0);;}
	public static deserialize( node : Node) : Option<CompileResult<JavaRootSegment>> {return Iters.fromValues( NamespacedType.values( )).map( 0).flatMap( Iters.fromOption).next( );;}
	public static createNamespacedRule( type : String) : Rule { let childRule : var=NodeListRule.createNodeListRule( "segments", new DelimitedFolder( '.'), new StringRule( "value"));return new TypeRule( type, new StripRule( new SuffixRule( new PrefixRule( type+" ", childRule), ";")));;}
}

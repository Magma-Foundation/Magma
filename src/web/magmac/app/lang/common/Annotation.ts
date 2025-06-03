export class Annotation {
	public static deserialize( node : Node) : CompileResult<Annotation> {return Destructors.destruct( node).withString( "value").complete( Annotation.new);;}
}

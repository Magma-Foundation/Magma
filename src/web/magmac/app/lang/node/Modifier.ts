export class Modifier {
	public static deserialize( node : Node) : CompileResult<Modifier> {return Destructors.destruct( node).withString( "value").complete( Modifier.new);;}
	public static createModifiersRule() : Rule {return new StripRule( NodeListRule.createNodeListRule( "modifiers", new DelimitedFolder( ' '), new StringRule( "value")));;}
	public serialize() : Node {return new MapNode( "modifier").withString( "value", this.value);;}
}

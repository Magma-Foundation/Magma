export class FlattenJava {
	private static getChildren( state : ParseState,  node : Node) : InlinePassResult { let parseStateNodeTuple2 : ParseUnit<Node>=new ParseUnitImpl<Node>( state, node);return new InlinePassResult( new Some<>( CompileResults.Ok( parseStateNodeTuple2)));;}
	public pass( state : ParseState,  node : Node) : ParseResult {if(true){  let values : NodeList=new InlineNodeList( node.findNodeList( "children").orElse( InlineNodeList.empty( )).iter( ).filter( 0).collect( new ListCollector<>( )));return FlattenJava.getChildren( state, node.withNodeList( "children", values));;}if(true){ return FlattenJava.getChildren( state, node.retype( "class"));;}return InlinePassResult.empty( );;}
}

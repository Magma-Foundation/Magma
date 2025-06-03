export class InlineNodeList {
	 InlineNodeList( elements : List<Node>) : public {this.elements=elements;;}
	public static empty() : NodeList {return new InlineNodeList( Lists.empty( ));;}
	public static of( ...elements : Node[]) : NodeList {return new InlineNodeList( Lists.of( elements));;}
	public iter() : Iter<Node> {return this.elements.iter( );;}
	public add( element : Node) : NodeList {return new InlineNodeList( this.elements.addLast( element));;}
	public addAll( others : NodeList) : NodeList {return others.iter( ).fold( this, NodeList.add);;}
	public findLast() : Option<Node> {return this.elements.findLast( );;}
	public join( delimiter : String,  generator : Function<Node, CompileResult<String>>) : CompileResult<String> {return this.iter( ).map( generator).collect( new CompileResultCollector<>( new Joiner( delimiter))).mapValue( 0);;}
}

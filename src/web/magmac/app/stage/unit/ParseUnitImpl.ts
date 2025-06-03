export class ParseUnitImpl<T> {
	 ParseUnitImpl( state : ParseState,  node : T) : public {this.state=state;this.node=node;;}
	public toLocationUnit() : Unit<T> {return this.merge( 0);;}
	public merge( merge : BiFunction<ParseState, T, R>) : R {return merge.apply( this.state, this.node);;}
	public retainWithList() : ParseUnit<NodeList> {return new ParseUnitImpl<>( this.state, InlineNodeList.empty( ));;}
	public left() : ParseState {return this.state;;}
	public right() : T {return this.node;;}
}

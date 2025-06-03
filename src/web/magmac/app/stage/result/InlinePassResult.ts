export class InlinePassResult {
	public static empty() : ParseResult {return new InlinePassResult( new None<>( ));;}
	public static from( state : ParseState,  node : Node) : ParseResult {return new InlinePassResult( new Some<>( CompileResults.Ok( new ParseUnitImpl<Node>( state, node))));;}
	public orElseGet( other : Supplier<ParseUnit<Node>>) : CompileResult<ParseUnit<Node>> {return this.option.orElseGet( ( )->CompileResults.fromResult( new Ok<>( other.get( ))));;}
}

export interface Passer {
	 pass( state : ParseState,  node : Node) : ParseResult;
}

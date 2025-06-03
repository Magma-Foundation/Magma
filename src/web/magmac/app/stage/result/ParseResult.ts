export interface ParseResult {
	 orElseGet( other : Supplier<ParseUnit<Node>>) : CompileResult<ParseUnit<Node>>;
}

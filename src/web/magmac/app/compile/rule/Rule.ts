export interface Rule {
	 lex( input : String) : CompileResult<Node>;
	 generate( node : Node) : CompileResult<String>;
}

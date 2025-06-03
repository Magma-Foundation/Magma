export class OrRule {
	private static foldElement( state : OrState<T>,  rule : Rule,  mapper : Function<Rule, CompileResult<T>>) : OrState<T> {return mapper.apply( rule).match( state.withValue, state.withError);;}
	private foldAll( mapper : Function<Rule, CompileResult<T>>,  context : Context) : CompileResult<T> { let ruleIter : var=this.rules.iter( ); let initial : var=new OrState<T>( );return ruleIter.fold( initial, 0).toResult( context);;}
	public lex( input : String) : CompileResult<Node> {return this.foldAll( 0, new StringContext( input));;}
	public generate( node : Node) : CompileResult<String> {return this.foldAll( 0, new NodeContext( node));;}
}

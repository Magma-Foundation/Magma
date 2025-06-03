export class RuleLexer {
	 RuleLexer( rootRule : Rule) : public {this.rootRule=rootRule;;}
	private foldEntry( unit : Unit<String>) : CompileResult<Unit<Node>> {System.out.println( unit.display( ));return unit.mapValue( this.rootRule.lex);;}
	public apply( initial : UnitSet<String>) : CompileResult<UnitSet<Node>> {return initial.iter( ).map( this.foldEntry).collect( new CompileResultCollector<>( new UnitSetCollector<>( )));;}
}

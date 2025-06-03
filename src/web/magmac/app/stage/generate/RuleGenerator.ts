export class RuleGenerator {
	 RuleGenerator( rootRule : Rule) : public {this.rootRule=rootRule;;}
	public apply( initial : UnitSet<Node>) : CompileResult<UnitSet<String>> {return initial.iter( ).map( this.generateEntry).collect( new CompileResultCollector<>( new UnitSetCollector<>( )));;}
	private generateEntry( entry : Unit<Node>) : CompileResult<Unit<String>> {return entry.mapValue( this.rootRule.generate).mapErr( 0);;}
	private getDestruct( entry : Unit<Node>,  err : CompileError) : CompileError {return entry.destruct( 0);;}
}

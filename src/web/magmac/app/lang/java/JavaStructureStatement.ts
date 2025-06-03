export class JavaStructureStatement {
	public static deserialize( node : Node) : Option<CompileResult<JavaStructureMember>> {return Destructors.destructWithType( "structure-statement", node).map( 0);;}
	public static createStructureStatementRule( definitionRule : Rule,  valueRule : LazyRule) : Rule { let definition : Rule=new NodeRule( "value", new OrRule( Lists.of( definitionRule, JavaRules.createAssignmentRule( definitionRule, valueRule))));return new TypeRule( "structure-statement", new StripRule( new SuffixRule( definition, ";")));;}
}

export class NodeListRule {
	 NodeListRule( key : String,  childRule : Rule,  divider : Divider) : public {this.key=key;this.childRule=childRule;this.divider=divider;;}
	public static Values( key : String,  childRule : Rule) : Rule {return NodeListRule.createNodeListRule( key, new ValueFolder( ), childRule);;}
	public static createNodeListRule( key : String,  folder : Folder,  childRule : Rule) : Rule {return new NodeListRule( key, childRule, new FoldingDivider( folder));;}
	public lex( input : String) : CompileResult<Node> {return this.divider.divide( input).map( this.childRule.lex).collect( new CompileResultCollector<>( new NodeListCollector( ))).mapValue( 0);;}
	public generate( node : Node) : CompileResult<String> {return node.findNodeListOrError( this.key).flatMapValue( 0);;}
}

export class OptionNodeListRule {
	public lex( input : String) : CompileResult<Node> {return new OrRule( Lists.of( this.ifPresent, this.ifEmpty)).lex( input);;}
	public generate( node : Node) : CompileResult<String> {if(true){ return this.ifPresent.generate( node);;}if(true){ return this.ifEmpty.generate( node);;};}
}

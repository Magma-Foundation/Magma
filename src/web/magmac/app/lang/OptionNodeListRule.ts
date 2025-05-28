import { Lists } from "../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../magmac/app/compile/node/Node";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
export class OptionNodeListRule {
	public lex( input : String) : CompileResult<Node> {
		return new OrRule( Lists.of( this.ifPresent, this.ifEmpty)).lex( input);
	}
	public generate( node : Node) : CompileResult<String> {
		if(node.hasNodeList( this.key)){ 
		return this.ifPresent.generate( node);}
		else{ 
		return this.ifEmpty.generate( node);}
	}
}

import { Lists } from "../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../magmac/app/compile/node/Node";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
export class OptionNodeListRule {
	public lex( input : String) : CompileResult<Node> {return new OrRule( Lists.of( this.ifPresent, this.ifEmpty)).lex( input);;}
	public generate( node : Node) : CompileResult<String> {if(true){ return this.ifPresent.generate( node);;}if(true){ return this.ifEmpty.generate( node);;};}
}

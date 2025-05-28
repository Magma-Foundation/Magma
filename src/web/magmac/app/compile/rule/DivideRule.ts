import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { NodeListCollector } from "../../../../magmac/app/compile/node/NodeListCollector";
import { FoldingDivider } from "../../../../magmac/app/compile/rule/divide/FoldingDivider";
import { Folder } from "../../../../magmac/app/compile/rule/fold/Folder";
export class DivideRule {
	public lex( input : String) : CompileResult<Node> {
		return new FoldingDivider( this.folder).divide( input).map( ( segment : String) => this.childRule.lex( segment)).collect( new CompileResultCollector<>( new NodeListCollector( ))).mapValue( ( children : NodeList) => new MapNode( ).withNodeList( this.key, children));
	}
	public generate( node : Node) : CompileResult<String> {
		return node.findNodeList( this.key).map( ( list : NodeList) => list.join( this.folder.createDelimiter( ), ( child : Node) => this.childRule.generate( child))).orElseGet( ( )->CompileErrors.createNodeError( "Node list '" + this.key + "' not present", node));
	}
}

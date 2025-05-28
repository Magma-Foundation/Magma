import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Joiner } from "../../../../magmac/api/iter/collect/Joiner";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { InlineNodeList } from "../../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { FoldingDivider } from "../../../../magmac/app/compile/rule/divide/FoldingDivider";
import { Folder } from "../../../../magmac/app/compile/rule/fold/Folder";
export class DivideRule {
	join(list : NodeList) : CompileResult<Option<String>> {
		return list.iter( ).map( (Node node)  => this.childRule.generate( node)).collect( new CompileResultCollector<>( new Joiner( this.folder.createDelimiter( ))));
	}
	lex(input : String) : CompileResult<Node> {
		return new FoldingDivider( this.folder).divide( input).map( (String segment)  => this.childRule.lex( segment)).collect( new CompileResultCollector<>( new ListCollector<>( ))).mapValue( (List<Node> children)  => new MapNode( ).withNodeList( this.key( ), new InlineNodeList( children)));
	}
	generate(node : Node) : CompileResult<String> {
		return node.findNodeList( this.key).map( (NodeList list)  => this.join( list)).orElseGet( ()  => CompileErrors.createNodeError( "Node list '" + this.key + "' not present", node)).mapValue( (Option<String> value)  => value.orElse( ""));
	}
}

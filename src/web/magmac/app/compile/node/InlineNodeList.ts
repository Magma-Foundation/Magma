import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { Joiner } from "../../../../magmac/api/iter/collect/Joiner";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { Function } from "../../../../java/util/function/Function";
export class InlineNodeList {
	InlineNodeList(elements : List<Node>) : public {this.elements=elements;;}
	empty() : NodeList {return new InlineNodeList( Lists.empty( ));;}
	of(...elements : Node[]) : NodeList {return new InlineNodeList( Lists.of( elements));;}
	iter() : Iter<Node> {return this.elements.iter( );;}
	add(element : Node) : NodeList {return new InlineNodeList( this.elements.addLast( element));;}
	addAll(others : NodeList) : NodeList {return others.iter( ).fold( this, NodeList.add);;}
	findLast() : Option<Node> {return this.elements.findLast( );;}
	join(delimiter : String, generator : Function<Node, CompileResult<String>>) : CompileResult<String> {return this.iter( ).map( generator).collect( new CompileResultCollector<>( new Joiner( delimiter))).mapValue( 0);;}
}

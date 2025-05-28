import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../magmac/api/iter/Iter";
export class InlineNodeList {
	temp : ?;
	InlineNodeList(elements : List<Node>) : public {this.elements=elements;}
	empty() : NodeList {return new InlineNodeList( Lists.empty( ));}
	iter() : Iter<Node> {return this.elements.iter( );}
	last() : Node {return this.elements.getLast( );}
	add(element : Node) : NodeList {return new InlineNodeList( this.elements.add( element));}
	addAll(others : NodeList) : NodeList {return others.iter( ).fold( this,  (NodeList nodeList, Node element) ->nodeList.add( element));}
	findLast() : Option<Node> {return new Some<>( this.last( ));}
}

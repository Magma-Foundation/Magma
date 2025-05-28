import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../magmac/api/iter/Iter";
export class InlineNodeList {private final elements() : List<Node>;
	 InlineNodeList( elements() : List<Node>) : public {
		this.elements=elements;
	}
	public static empty() : NodeList {
		return new InlineNodeList( Lists.empty( ));
	}
	public iter() : Iter<Node> {
		return this.elements.iter( );
	}
	private last() : Node {
		return this.elements.getLast( );
	}
	public add( element() : Node) : NodeList {
		return new InlineNodeList( this.elements.add( element));
	}
	public addAll( others() : NodeList) : NodeList {
		return others.iter( ).fold( this, ( nodeList() : NodeList,  element() : Node) => nodeList.add( element));
	}
	public findLast() : Option<Node> {
		return new Some<>( this.last( ));
	}
}

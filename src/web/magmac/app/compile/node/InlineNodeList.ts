import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { Joiner } from "../../../../magmac/api/iter/collect/Joiner";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { Function } from "../../../../java/util/function/Function";
export class InlineNodeList {private final elements : List<Node>;
	 InlineNodeList( elements : List<Node>) : public {
		this.elements=elements;
	}
	public static empty() : NodeList {
		return new InlineNodeList( Lists.empty( ));
	}
	public static of( elements : Node[]) : NodeList {
		return new InlineNodeList( Lists.of( elements));
	}
	public iter() : Iter<Node> {
		return this.elements.iter( );
	}
	private last() : Node {
		return this.elements.getLast( );
	}
	public add( element : Node) : NodeList {
		return new InlineNodeList( this.elements.add( element));
	}
	public addAll( others : NodeList) : NodeList {
		return others.iter( ).fold( this, ( nodeList : NodeList,  element : Node) => nodeList.add( element));
	}
	public findLast() : Option<Node> {
		return new Some<>( this.last( ));
	}
	public join( delimiter : String,  generator : Function<Node, CompileResult<String>>) : CompileResult<String> {
		return this.iter( ).map( generator).collect( new CompileResultCollector<>( new Joiner( delimiter))).mapValue( ( option : Option<String>) => option.orElse( ""));
	}
}

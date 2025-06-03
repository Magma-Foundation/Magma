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
	InlineNodeList(elements : List<Node>) : public {break;;}
	empty() : NodeList {break;;}
	of(...elements : Node[]) : NodeList {break;;}
	iter() : Iter<Node> {break;;}
	add(element : Node) : NodeList {break;;}
	addAll(others : NodeList) : NodeList {break;;}
	findLast() : Option<Node> {break;;}
	join(delimiter : String, generator : Function<Node, CompileResult<String>>) : CompileResult<String> {break;;}
}

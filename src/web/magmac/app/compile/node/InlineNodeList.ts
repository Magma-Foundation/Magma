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
	empty() : NodeList {return 0;;}
	of(...elements : Node[]) : NodeList {return 0;;}
	iter() : Iter<Node> {return 0;;}
	add(element : Node) : NodeList {return 0;;}
	addAll(others : NodeList) : NodeList {return 0;;}
	findLast() : Option<Node> {return 0;;}
	join(delimiter : String, generator : Function<Node, CompileResult<String>>) : CompileResult<String> {return 0;;}
}

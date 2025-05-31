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
	InlineNodeList(elements : List<Node>) : public;
	empty() : NodeList;
	of(...elements : ?[]) : NodeList;
	iter() : Iter<Node>;
	add(element : Node) : NodeList;
	addAll(others : NodeList) : NodeList;
	findLast() : Option<Node>;
	join(delimiter : String, generator : Function<Node, CompileResult<String>>) : CompileResult<String>;
}

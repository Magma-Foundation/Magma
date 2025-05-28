import { Option } from "../../../../magmac/api/Option";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Function } from "../../../../java/util/function/Function";
export interface NodeList { iter() : Iter<Node>; findLast() : Option<Node>;
	 add( element : Node) : NodeList;
	 addAll( others : NodeList) : NodeList;CompileResult<String> join(String delimiter, generator) : Function<Node, CompileResult<String>>;
}

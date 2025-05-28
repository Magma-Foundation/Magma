import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Iter } from "../../../../magmac/api/iter/Iter";
export interface Node { iterNodes()() : Iter<Tuple2<String, Node>>; display()() : String;
	 format( depth() : int) : String; iterNodeLists()() : Iter<Tuple2<String, NodeList>>;Node withString(String key, value)() : String;
	 findString( key() : String) : Option<String>;
	 merge( other() : Node) : Node;
	 is( type() : String) : boolean;
	 retype( type() : String) : Node;Node withNodeList(String key, values)() : NodeList;
	 findNodeList( key() : String) : Option<NodeList>;Node withNode(String key, value)() : Node;
	 findNode( key() : String) : Option<Node>; iterStrings()() : Iter<Tuple2<String, String>>;
	 hasNodeList( key() : String) : boolean;
}

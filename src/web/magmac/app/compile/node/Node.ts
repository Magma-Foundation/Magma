import { Option } from "../../../../magmac/api/Option";
import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Iter } from "../../../../magmac/api/iter/Iter";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
export interface Node {
	 findNodeOrError( key : String) : CompileResult<Node>;
	 iterNodes() : Iter<Tuple2<String, Node>>;
	 display() : String;
	 format( depth : int) : String;
	 iterNodeLists() : Iter<Tuple2<String, NodeList>>;
	 withString( key : String,  value : String) : Node;
	 findString( key : String) : Option<String>;
	 merge( other : Node) : Node;
	 is( type : String) : boolean;
	 retype( type : String) : Node;
	 withNodeList( key : String,  values : NodeList) : Node;
	 findNodeList( key : String) : Option<NodeList>;
	 withNode( key : String,  value : Node) : Node;
	 findNode( key : String) : Option<Node>;
	 iterStrings() : Iter<Tuple2<String, String>>;
	 hasNodeList( key : String) : boolean;
}

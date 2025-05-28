import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { MapCollector } from "../../../../magmac/api/collect/map/MapCollector";
import { Ok } from "../../../../magmac/api/result/Ok";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InlineNodeList } from "../../../../magmac/app/compile/node/InlineNodeList";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { Location } from "../../../../magmac/app/io/Location";
import { AfterAll } from "../../../../magmac/app/stage/AfterAll";
import { MapRoots } from "../../../../magmac/app/stage/MapRoots";
import { Passer } from "../../../../magmac/app/stage/Passer";
import { Roots } from "../../../../magmac/app/stage/Roots";
import { Map } from "../../../../magmac/api/collect/map/Map";
export class TreeParser {
	temp : ?;
	temp : ?;
	temp : ?;
	TreeParser(beforeChild : Passer, afterChild : Passer, afterAllChildren : AfterAll) : public {
	}
	parseNodeLists(state : ParseState, root : Node) : Tuple2<ParseState, Node> {
	}
	parseNodeList(current : Tuple2<ParseState, Node>, entry : Tuple2<String, NodeList>) : Tuple2<ParseState, Node> {
	}
	parse(location : Location, root : Node) : Tuple2<Location, Node> {
	}
	parseTree(state : ParseState, root : Node) : Tuple2<ParseState, Node> {
	}
	apply(initial : Roots) : CompileResult<Roots> {
	}
}

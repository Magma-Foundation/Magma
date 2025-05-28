import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { MapCollector } from "../../../../magmac/api/collect/map/MapCollector";
import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { CompileError } from "../../../../magmac/app/compile/error/error/CompileError";
import { InlineNodeList } from "../../../../magmac/app/compile/node/InlineNodeList";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { Location } from "../../../../magmac/app/io/Location";
import { AfterAll } from "../../../../magmac/app/stage/AfterAll";
import { MapRoots } from "../../../../magmac/app/stage/MapRoots";
import { Passer } from "../../../../magmac/app/stage/Passer";
import { Roots } from "../../../../magmac/app/stage/Roots";
export class TreeParser {
	temp : ?;
	temp : ?;
	temp : ?;
	TreeParser(beforeChild : Passer, afterChild : Passer, afterAllChildren : AfterAll) : public;
	parseNodeLists(state : ParseState, root : Node) : CompileResult<Tuple2<ParseState, Node>>;
	parseNodeList(current : Tuple2<ParseState, Node>, entry : Tuple2<String, NodeList>) : CompileResult<Tuple2<ParseState, Node>>;
	parse(location : Location, root : Node) : CompileResult<Tuple2<Location, Node>>;
	parseTree(state : ParseState, root : Node) : CompileResult<Tuple2<ParseState, Node>>;
	apply(initial : Roots) : CompileResult<Roots>;
}

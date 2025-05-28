import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { NodeListCollector } from "../../../magmac/app/compile/node/NodeListCollector";
import { InlinePassResult } from "../../../magmac/app/stage/InlinePassResult";
import { PassResult } from "../../../magmac/app/stage/PassResult";
import { Passer } from "../../../magmac/app/stage/Passer";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
export class TypeScriptAfterPasser {
	pass : PassResult{
	}
	passImport : Option<Node>{
	}
}

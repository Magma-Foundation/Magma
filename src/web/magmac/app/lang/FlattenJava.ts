import { Some } from "../../../magmac/api/Some";
import { ListCollector } from "../../../magmac/api/iter/collect/ListCollector";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { InlinePassResult } from "../../../magmac/app/stage/result/InlinePassResult";
import { ParseResult } from "../../../magmac/app/stage/result/ParseResult";
import { ParseUnit } from "../../../magmac/app/stage/unit/ParseUnit";
import { ParseUnitImpl } from "../../../magmac/app/stage/unit/ParseUnitImpl";
import { Passer } from "../../../magmac/app/stage/Passer";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
export class FlattenJava {
	getChildren(state : ParseState, node : Node) : InlinePassResult;
	pass(state : ParseState, node : Node) : ParseResult;
}

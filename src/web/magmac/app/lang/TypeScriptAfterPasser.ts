import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../magmac/app/compile/error/CompileResults";
import { InlineNodeList } from "../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../magmac/app/compile/node/NodeList";
import { NodeListCollector } from "../../../magmac/app/compile/node/NodeListCollector";
import { Passer } from "../../../magmac/app/stage/Passer";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
import { InlinePassResult } from "../../../magmac/app/stage/result/InlinePassResult";
import { ParseResult } from "../../../magmac/app/stage/result/ParseResult";
import { ParseUnit } from "../../../magmac/app/stage/unit/ParseUnit";
import { ParseUnitImpl } from "../../../magmac/app/stage/unit/ParseUnitImpl";
export class TypeScriptAfterPasser {
	passImport(state : ParseState, node : Node) : Option<ParseResult>;
	passMethod(state : ParseState, node : Node) : Option<ParseResult>;
	passVariadic(state : ParseState, node : Node) : Option<ParseResult>;
	format(state : ParseState, node : Node) : Option<ParseResult>;
	pass(state : ParseState, node : Node) : ParseResult;
	passTemp(state : ParseState, node : Node) : Option<ParseResult>;
	getObjectCompileResult(state : ParseState, value : Node) : CompileResult<ParseUnit<Node>>;
	getNode(destination : Node) : Node;
}

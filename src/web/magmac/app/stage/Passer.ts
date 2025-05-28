import { Node } from "../../../magmac/app/compile/node/Node";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
export interface Passer {ParseResult pass(ParseState state, node) : Node;
}

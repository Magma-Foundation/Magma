import { None } from "../../../../magmac/api/None";
import { Option } from "../../../../magmac/api/Option";
import { Some } from "../../../../magmac/api/Some";
import { Ok } from "../../../../magmac/api/result/Ok";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ParseState } from "../../../../magmac/app/stage/parse/ParseState";
import { ParseUnit } from "../../../../magmac/app/stage/unit/ParseUnit";
import { ParseUnitImpl } from "../../../../magmac/app/stage/unit/ParseUnitImpl";
import { Supplier } from "../../../../java/util/function/Supplier";
export class InlinePassResult {
	empty() : ParseResult {return new InlinePassResult( new None<>( ));;}
	from(state : ParseState, node : Node) : ParseResult {return new InlinePassResult( new Some<>( 0.Ok( new ParseUnitImpl<Node>( 0, 0))));;}
	orElseGet(other : Supplier<ParseUnit<Node>>) : CompileResult<ParseUnit<Node>> {return 0.option.orElseGet( 0);;}
}

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
	public static empty() : ParseResult {return new InlinePassResult( new None<>( ));;}
	public static from( state : ParseState,  node : Node) : ParseResult {return new InlinePassResult( new Some<>( CompileResults.Ok( new ParseUnitImpl<Node>( state, node))));;}
	public orElseGet( other : Supplier<ParseUnit<Node>>) : CompileResult<ParseUnit<Node>> {return this.option.orElseGet( ( )->CompileResults.fromResult( new Ok<>( other.get( ))));;}
}

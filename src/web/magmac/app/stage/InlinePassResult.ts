import { None } from "../../../magmac/api/None";
import { Option } from "../../../magmac/api/Option";
import { Some } from "../../../magmac/api/Some";
import { Tuple2 } from "../../../magmac/api/Tuple2";
import { Ok } from "../../../magmac/api/result/Ok";
import { CompileResult } from "../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../magmac/app/compile/error/InlineCompileResult";
import { Node } from "../../../magmac/app/compile/node/Node";
import { ParseState } from "../../../magmac/app/stage/parse/ParseState";
import { Supplier } from "../../../java/util/function/Supplier";
export class InlinePassResult {
	public static empty() : PassResult {
		return new InlinePassResult( new None<>( ));
	}
	public static from( state() : ParseState,  node() : Node) : PassResult {
		return new InlinePassResult( new Some<>( InlineCompileResult.fromOk( new Tuple2<>( state, node))));
	}
	public orElseGet( other() : Supplier<Tuple2<ParseState, Node>>) : CompileResult<Tuple2<ParseState, Node>> {
		return this.option.orElseGet( ( )->InlineCompileResult.fromResult( new Ok<>( other.get( ))));
	}
}

import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
import { Max } from "../../../magmac/api/iter/collect/Max";
import { Context } from "../../../magmac/app/compile/error/context/Context";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
export class ImmutableCompileError {
	ImmutableCompileError(message : String, context : Context) : public {this( message, context, Lists.empty( ));;}
	formatEntry(depth : int, display : String) : String {break;return 0;;}
	display() : String {return this.format( 0);;}
	format(depth : int) : String {break;break;return 0;;}
	joinSorted(depth : int, copy : List<CompileError>) : String {return copy.iter( ).map( 0).map( 0).collect( new Joiner( )).orElse( "");;}
	computeMaxDepth() : int {break;return 0.map( CompileError.computeMaxDepth).collect( new Max( )).orElse( 0);;}
}

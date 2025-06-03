import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
import { Max } from "../../../magmac/api/iter/collect/Max";
import { Context } from "../../../magmac/app/compile/error/context/Context";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
export class ImmutableCompileError {
	ImmutableCompileError(message : String, context : Context) : public {this( message, context, Lists.empty( ));;}
	formatEntry(depth : int, display : String) : String {repeated : String="| ".repeat( depth+1);return "\n"+repeated+display;;}
	display() : String {return this.format( 0);;}
	format(depth : int) : String {copy : List<CompileError>=this.errors.sort( 0);joined : String=ImmutableCompileError.joinSorted( depth, copy);return this.message+": "+this.context.display( )+joined;;}
	joinSorted(depth : int, copy : List<CompileError>) : String {return copy.iter( ).map( 0).map( 0).collect( new Joiner( )).orElse( "");;}
	computeMaxDepth() : int {compileErrorIter : Iter<CompileError>=this.errors.iter( );return 1+compileErrorIter.map( CompileError.computeMaxDepth).collect( new Max( )).orElse( 0);;}
}

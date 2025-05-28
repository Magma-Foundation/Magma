import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
import { Max } from "../../../magmac/api/iter/collect/Max";
import { Context } from "../../../magmac/app/compile/error/context/Context";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
export class ImmutableCompileError {
	ImmutableCompileError(message : String, context : Context) : public {
		this( message, context, Lists.empty( ));
	}
	formatEntry(depth : int, display : String) : String {
		 String repeated="| ".repeat( depth+1);
		return "\n"+repeated+display;
	}
	display() : String {
		return this.format( 0);
	}
	format(depth : int) : String {
		 List<CompileError> copy=this.errors.sort( (CompileError first, CompileError second) ->first.computeMaxDepth( )-second.computeMaxDepth( ));
		 String joined=ImmutableCompileError.joinSorted( depth, copy);
		return this.message+": "+this.context.display( )+joined;
	}
	joinSorted(depth : int, copy : List<CompileError>) : String {
		return copy.iter( ).map( (CompileError compileError) ->compileError.format( depth+1)).map( (String display) ->ImmutableCompileError.formatEntry( depth, display)).collect( new Joiner( )).orElse( "");
	}
	computeMaxDepth() : int {
		 Iter<CompileError> compileErrorIter=this.errors.iter( );
		return 1+compileErrorIter.map( (CompileError compileError) ->compileError.computeMaxDepth( )).collect( new Max( )).orElse( 0);
	}
}

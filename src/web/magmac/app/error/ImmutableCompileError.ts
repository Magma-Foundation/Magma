import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../magmac/api/iter/Iter";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
import { Max } from "../../../magmac/api/iter/collect/Max";
import { Context } from "../../../magmac/app/compile/error/context/Context";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
export class ImmutableCompileError {
	 ImmutableCompileError( message() : String,  context() : Context) : public {
		this( message, context, Lists.empty( ));
	}
	private static formatEntry( depth() : int,  display() : String) : String {
		 repeated() : String="| ".repeat( depth+1);
		return "\n"+repeated+display;
	}
	public display() : String {
		return this.format( 0);
	}
	public format( depth() : int) : String {
		 copy() : List<CompileError>=this.errors.sort( ( first() : CompileError,  second() : CompileError) => first.computeMaxDepth( )-second.computeMaxDepth( ));
		 joined() : String=ImmutableCompileError.joinSorted( depth, copy);
		return this.message+": "+this.context.display( )+joined;
	}
	private static joinSorted( depth() : int,  copy() : List<CompileError>) : String {
		return copy.iter( ).map( ( compileError() : CompileError) => compileError.format( depth+1)).map( ( display() : String) => ImmutableCompileError.formatEntry( depth, display)).collect( new Joiner( )).orElse( "");
	}
	public computeMaxDepth() : int {
		 compileErrorIter() : Iter<CompileError>=this.errors.iter( );
		return 1+compileErrorIter.map( ( compileError() : CompileError) => compileError.computeMaxDepth( )).collect( new Max( )).orElse( 0);
	}
}

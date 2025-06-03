import { Tuple2 } from "../../../magmac/api/Tuple2";
import { List } from "../../../magmac/api/collect/list/List";
import { Lists } from "../../../magmac/api/collect/list/Lists";
import { Joiner } from "../../../magmac/api/iter/collect/Joiner";
import { Max } from "../../../magmac/api/iter/collect/Max";
import { Context } from "../../../magmac/app/compile/error/context/Context";
import { CompileError } from "../../../magmac/app/compile/error/error/CompileError";
export class ImmutableCompileError {
	 ImmutableCompileError( message : String,  context : Context) : public {this( message, context, Lists.empty( ));;}
	private static formatEntry( depth : int,  display : String) : String { let repeated : var="| ".repeat( depth+1);return "\n"+repeated+display;;}
	private static joinSorted( depth : int,  copy : List<CompileError>,  indices : List<Integer>) : String {return copy.iterWithIndices( ).map( 0).map( 0).collect( new Joiner( )).orElse( "");;}
	private static getIntegerList( copy : List<CompileError>,  indices : List<Integer>,  tuple : Tuple2<Integer, CompileError>) : List<Integer> {if(true){ return indices;;}if(true){ return indices.addLast( tuple.left( ));;};}
	public display() : String {return this.format( 0, Lists.empty( ));;}
	public format( depth : int,  indices : List<Integer>) : String { let copy : var=this.errors.sort( 0); let joined : var=ImmutableCompileError.joinSorted( depth, copy, indices); let joinedIndices : var=indices.iter( ).map( String.valueOf).collect( new Joiner( ".")).orElse( "");return joinedIndices+") "+this.message+": "+this.context.display( )+joined;;}
	public computeMaxDepth() : int { let compileErrorIter : var=this.errors.iter( );return 1+compileErrorIter.map( CompileError.computeMaxDepth).collect( new Max( )).orElse( 0);;}
}

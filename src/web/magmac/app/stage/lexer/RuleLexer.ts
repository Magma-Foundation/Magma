import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { Map } from "../../../../magmac/api/collect/map/Map";
import { MapCollector } from "../../../../magmac/api/collect/map/MapCollector";
import { ResultCollector } from "../../../../magmac/api/iter/collect/ResultCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { Location } from "../../../../magmac/app/io/Location";
import { MapRoots } from "../../../../magmac/app/stage/MapRoots";
import { Roots } from "../../../../magmac/app/stage/Roots";
export class RuleLexer {private final rootRule() : Rule;
	 RuleLexer( rootRule() : Rule) : public {
		this.rootRule=rootRule;
	}
	private foldEntry( tuple() : Tuple2<Location, String>) : CompileResult<Tuple2<Location, Node>> {
		 location() : Location=tuple.left( );
		 input() : String=tuple.right( );
		System.out.println( "Lexing: "+location);
		return this.rootRule.lex( input).mapValue( ( root() : Node) => new Tuple2<>( location, root));
	}
	public apply( initial() : Map<Location, String>) : CompileResult<Roots> {
		return InlineCompileResult.fromResult( initial.iterEntries( ).map( ( entry() : Tuple2<Location, String>) => this.foldEntry( entry)).map( ( tuple2CompileResult() : CompileResult<Tuple2<Location, Node>>) => tuple2CompileResult.result( )).collect( new ResultCollector<>( new MapCollector<>( ))).mapValue( ( roots() : Map<Location, Node>) => new MapRoots( roots)));
	}
}

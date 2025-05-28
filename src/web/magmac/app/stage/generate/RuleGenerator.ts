import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { MapCollector } from "../../../../magmac/api/collect/map/MapCollector";
import { ResultCollector } from "../../../../magmac/api/iter/collect/ResultCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InlineCompileResult } from "../../../../magmac/app/compile/error/InlineCompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { Location } from "../../../../magmac/app/io/Location";
import { Roots } from "../../../../magmac/app/stage/Roots";
import { Map } from "../../../../magmac/api/collect/map/Map";
export class RuleGenerator {private final rootRule() : Rule;
	 RuleGenerator( rootRule() : Rule) : public {
		this.rootRule=rootRule;
	}
	public apply( initial() : Roots) : CompileResult<Map<Location, String>> {
		return InlineCompileResult.fromResult( initial.iter( ).map( ( entry() : Tuple2<Location, Node>) => this.rootRule.generate( entry.right( )).result( ).mapValue( ( generated() : String) => new Tuple2<>( entry.left( ), generated))).collect( new ResultCollector<>( new MapCollector<>( ))));
	}
}

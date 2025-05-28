import { ResultCollector } from "../../../../magmac/api/iter/collect/ResultCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { UnitSetCollector } from "../../../../magmac/app/io/sources/UnitSetCollector";
import { Unit } from "../../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export class RuleLexer {private final rootRule : Rule;
	 RuleLexer( rootRule : Rule) : public {
		this.rootRule=rootRule;
	}
	private foldEntry( unit : Unit<String>) : CompileResult<Unit<Node>> {
		return unit.mapValue( ( input : String) => this.rootRule.lex( input));
	}
	public apply( initial : UnitSet<String>) : CompileResult<UnitSet<Node>> {
		return CompileResults.fromResult( initial.iter( ).map( ( entry : Unit<String>) => this.foldEntry( entry)).map( ( tuple2CompileResult : CompileResult<Unit<Node>>) => tuple2CompileResult.result( )).collect( new ResultCollector<>( new UnitSetCollector<>( ))));
	}
}

import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { UnitSetCollector } from "../../../../magmac/app/io/sources/UnitSetCollector";
import { Unit } from "../../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export class RuleGenerator {private final rootRule : Rule;
	 RuleGenerator( rootRule : Rule) : public {
		this.rootRule=rootRule;
	}
	public apply( initial : UnitSet<Node>) : CompileResult<UnitSet<String>> {
		return initial.iter( ).map( ( entry : Unit<Node>) => this.generateEntry( entry)).collect( new CompileResultCollector<>( new UnitSetCollector<>( )));
	}
	private generateEntry( entry : Unit<Node>) : CompileResult<Unit<String>> {
		return entry.mapValue( ( node : Node) => this.rootRule.generate( node));
	}
}

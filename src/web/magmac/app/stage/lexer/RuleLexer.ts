import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { UnitSetCollector } from "../../../../magmac/app/io/sources/UnitSetCollector";
import { Unit } from "../../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export class RuleLexer {
	 RuleLexer( rootRule : Rule) : public {this.rootRule=rootRule;;}
	private foldEntry( unit : Unit<String>) : CompileResult<Unit<Node>> {System.out.println( unit.display( ));return unit.mapValue( this.rootRule.lex);;}
	public apply( initial : UnitSet<String>) : CompileResult<UnitSet<Node>> {return initial.iter( ).map( this.foldEntry).collect( new CompileResultCollector<>( new UnitSetCollector<>( )));;}
}

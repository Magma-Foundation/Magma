import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { UnitSetCollector } from "../../../../magmac/app/io/sources/UnitSetCollector";
import { Unit } from "../../../../magmac/app/stage/unit/Unit";
import { UnitSet } from "../../../../magmac/app/stage/unit/UnitSet";
export class RuleLexer {
	RuleLexer(rootRule : Rule) : public {break;;}
	foldEntry(unit : Unit<String>) : CompileResult<Unit<Node>> {0.out.println( 0.display( ));return 0.mapValue( 0.rootRule.lex);;}
	apply(initial : UnitSet<String>) : CompileResult<UnitSet<Node>> {return 0.iter( ).map( 0.foldEntry).collect( new CompileResultCollector<>( new UnitSetCollector<>( )));;}
}

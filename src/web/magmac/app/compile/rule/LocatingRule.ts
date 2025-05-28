import { Tuple2 } from "../../../../magmac/api/Tuple2";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FirstLocator } from "../../../../magmac/app/compile/rule/locate/FirstLocator";
import { LastLocator } from "../../../../magmac/app/compile/rule/locate/LastLocator";
import { Locator } from "../../../../magmac/app/compile/rule/locate/Locator";
export class LocatingRule {private final leftRule : Rule;private final splitter : Splitter;private final rightRule : Rule;
	 LocatingRule( leftRule : Rule,  splitter : Splitter,  rightRule : Rule) : public {
		this.leftRule=leftRule;
		this.rightRule=rightRule;
		this.splitter=splitter;
	}
	public static First( leftRule : Rule,  infix : String,  rightRule : Rule) : Rule {
		return LocatingRule.createLocatingRule( leftRule, infix, rightRule, new FirstLocator( ));
	}
	public static Last( leftRule : Rule,  infix : String,  rightRule : Rule) : Rule {
		return LocatingRule.createLocatingRule( leftRule, infix, rightRule, new LastLocator( ));
	}
	private static createLocatingRule( leftRule : Rule,  infix : String,  rightRule : Rule,  locator : Locator) : Rule {
		return new LocatingRule( leftRule, new LocatingSplitter( infix, locator), rightRule);
	}
	public lex( input : String) : CompileResult<Node> {
		return this.splitter.split( input).map( ( tuple : Tuple2<String, String>) => {
		 left : String=tuple.left( );
		 right : String=tuple.right( );
		return this.leftRule.lex( left).merge( ( )->this.rightRule.lex( right), ( node : Node,  other : Node) => node.merge( other));}).orElseGet( ( )->CompileErrors.createStringError( this.splitter.createMessage( ), input));
	}
	public generate( node : Node) : CompileResult<String> {
		return this.leftRule.generate( node).merge( ( )->this.rightRule.generate( node), ( leftString : String,  rightString : String) => this.splitter.merge( leftString, rightString));
	}
}

import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { Filter } from "../../../../magmac/app/compile/rule/filter/Filter";
import { NumberFilter } from "../../../../magmac/app/compile/rule/filter/NumberFilter";
import { SymbolFilter } from "../../../../magmac/app/compile/rule/filter/SymbolFilter";
export class FilterRule {private final childRule : Rule;private final filter : Filter;
	 FilterRule( filter : Filter,  childRule : Rule) : private {
		this.childRule=childRule;
		this.filter=filter;
	}
	public static Symbol( childRule : Rule) : Rule {
		return new FilterRule( new SymbolFilter( ), childRule);
	}
	public static Number( childRule : Rule) : Rule {
		return new FilterRule( new NumberFilter( ), childRule);
	}
	public lex( input : String) : CompileResult<Node> {
		if(this.filter.test( input)){ 
		return this.childRule.lex( input);}
		else{ 
		return CompileErrors.createStringError( this.filter.createMessage( ), input);}
	}
	public generate( node : Node) : CompileResult<String> {
		return this.childRule.generate( node);
	}
}

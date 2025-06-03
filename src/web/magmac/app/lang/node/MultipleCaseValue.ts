import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { StatementFolder } from "../../../../magmac/app/compile/rule/fold/StatementFolder";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaFunctionSegment } from "../../../../magmac/app/lang/java/JavaFunctionSegment";
export class MultipleCaseValue {
	public static deserialize( node : Node) : Option<CompileResult<CaseValue>> {return Destructors.destructWithType( "case-multiple", node).map( 0);;}
	public static createRule( segment : Rule) : TypeRule {return new TypeRule( "case-multiple", new StripRule( new PrefixRule( "{", new SuffixRule( NodeListRule.createNodeListRule( "children", new StatementFolder( ), segment), "}"))));;}
}

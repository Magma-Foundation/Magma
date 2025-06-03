import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FilterRule } from "../../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { CommonRules } from "../../../../magmac/app/lang/CommonRules";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
export class JavaEnumValue {
	public static deserialize( node : Node) : CompileResult<JavaEnumValue> {return Destructors.destruct( node).withString( "name").withNodeListOptionally( "arguments", JavaDeserializers.deserializeValueOrError).complete( 0);;}
	static createEnumValueRule( value : Rule) : Rule { let name : Rule=new StripRule( FilterRule.Symbol( new StringRule( "name"))); let rule : Rule=new SuffixRule( LocatingRule.First( name, "(", JavaRules.createArgumentsRule( value)), ")");return new StripRule( new OrRule( Lists.of( CommonRules.createSymbolRule( "name"), rule)));;}
}

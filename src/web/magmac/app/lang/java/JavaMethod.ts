import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FilterRule } from "../../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { OptionNodeListRule } from "../../../../magmac/app/lang/OptionNodeListRule";
import { FunctionSegments } from "../../../../magmac/app/lang/node/FunctionSegments";
export class JavaMethod {
	deserialize(node : Node) : Option<CompileResult<JavaStructureMember>> {return Destructors.destructWithType( "method", node).map( 0);;}
	createMethodRule(childRule : Rule) : Rule {header : NodeRule=new NodeRule( "header", new OrRule( Lists.of( JavaRules.createDefinitionRule( ), new TypeRule( "constructor", new StripRule( FilterRule.Symbol( new StringRule( "name")))))));parameters : Rule=JavaRules.createParametersRule( JavaRules.createDefinitionRule( ));content : Rule=CommonLang.Statements( "children", childRule);rightRule : Rule=new StripRule( new PrefixRule( "{", new SuffixRule( new StripRule( "", content, "after-children"), "}")));withParams : Rule=new OptionNodeListRule( "parameters", new SuffixRule( parameters, ");"), LocatingRule.First( parameters, ")", rightRule));return new TypeRule( "method", LocatingRule.First( header, "(", withParams));;}
}

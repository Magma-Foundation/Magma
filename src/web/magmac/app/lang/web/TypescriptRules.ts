import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { DelimitedDivider } from "../../../../magmac/app/compile/rule/divide/DelimitedDivider";
import { DelimitedFolder } from "../../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { CommonRules } from "../../../../magmac/app/lang/CommonRules";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { MutableLazyRule } from "../../../../magmac/app/lang/MutableLazyRule";
import { OptionNodeListRule } from "../../../../magmac/app/lang/OptionNodeListRule";
import { JavaStructureStatement } from "../../../../magmac/app/lang/java/JavaStructureStatement";
import { FunctionSegments } from "../../../../magmac/app/lang/node/FunctionSegments";
import { Modifier } from "../../../../magmac/app/lang/node/Modifier";
export class TypescriptRules {
	createRule() : Rule {return new TypeRule( "root", CommonLang.Statements( "children", new OrRule( Lists.of( JavaRules.createWhitespaceRule( ), createImportRule( ), createStructureRule( "class"), createStructureRule( "interface")))));;}
	createStructureMemberRule() : Rule {definitionRule : var=createDefinitionRule( );valueLazy : LazyRule=new MutableLazyRule( );return new OrRule( Lists.of( JavaRules.createWhitespaceRule( ), createMethodRule( definitionRule, valueLazy), JavaStructureStatement.createStructureStatementRule( definitionRule, valueLazy)));;}
	createMethodRule(definition : Rule, valueLazy : LazyRule) : Rule {header : Rule=new PrefixRule( "\n\t", new NodeRule( "header", new OrRule( Lists.of( definition, createConstructorRule( definition)))));functionSegmentRule : LazyRule=new MutableLazyRule( );value : var=JavaRules.initValueRule( functionSegmentRule, valueLazy, " => ", definition);children : var=CommonLang.Statements( "children", FunctionSegments.initFunctionSegmentRule( functionSegmentRule, value, definition));childRule : Rule=new SuffixRule( LocatingRule.First( header, " {", new StripRule( "", children, "after-children")), "}");return new TypeRule( "method", new OptionNodeListRule( "children", childRule, new SuffixRule( header, ";")));;}
	createConstructorRule(definition : Rule) : Rule {parametersRule : var=JavaRules.createParametersRule( definition);return new TypeRule( "constructor", new PrefixRule( "constructor(", new SuffixRule( parametersRule, ")")));;}
	createDefinitionRule() : Rule {definition : LazyRule=new MutableLazyRule( );modifiers : var=Modifier.createModifiersRule( );parameters : var=JavaRules.createParametersRule( definition);name : Rule=new StringRule( "name");leftRule : Rule=new OptionNodeListRule( "parameters", new SuffixRule( LocatingRule.First( name, "(", parameters), ")"), name);first : var=LocatingRule.First( leftRule, " : ", new NodeRule( "type", createTypeRule( )));return definition.set( new OptionNodeListRule( "modifiers", LocatingRule.Last( modifiers, " ", first), first));;}
	createTypeRule() : Rule {type : LazyRule=new MutableLazyRule( );return type.set( new OrRule( Lists.of( JavaRules.createTemplateRule( type), createArrayRule( type), CommonRules.createSymbolRule( ))));;}
	createStructureRule(type : String) : Rule {children : var=CommonLang.Statements( "members", createStructureMemberRule( ));name : Rule=new StringRule( "name");afterKeyword : var=LocatingRule.First( JavaRules.attachTypeParams( name), " {", new SuffixRule( children, "\n}\n"));return new TypeRule( type, new PrefixRule( "export " + type + " ", afterKeyword));;}
	createImportRule() : Rule {segments : Rule=new SuffixRule( NodeListRule.createNodeListRule( "segments", new DelimitedFolder( '/'), new StringRule( "value")), "\";\n");leftRule : Rule=new NodeListRule( "values", new StringRule( "value"), new DelimitedDivider( ", "));first : var=LocatingRule.First( leftRule, " } from \"", segments);return new TypeRule( "import", new PrefixRule( "import { ", first));;}
	createArrayRule(orRule : LazyRule) : TypeRule {return new TypeRule( "array", new SuffixRule( new NodeRule( "child", orRule), "[]"));;}
}

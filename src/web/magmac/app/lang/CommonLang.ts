import { Option } from "../../../magmac/api/Option";
import { List } from "../../../magmac/api/collect/list/List";
import { NodeListRule } from "../../../magmac/app/compile/rule/NodeListRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { StatementFolder } from "../../../magmac/app/compile/rule/fold/StatementFolder";
import { Annotation } from "../../../magmac/app/lang/common/Annotation";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { TypescriptLang } from "../../../magmac/app/lang/web/TypescriptLang";
export class AbstractDefinition<T> {
	AbstractDefinition(maybeAnnotations : Option<List<Annotation>>, modifiers : List<Modifier>, name : String, maybeTypeParams : Option<List<TypescriptLang.TypeParam>>, type : T) : public {this.maybeAnnotations=maybeAnnotations;this.modifiers=modifiers;this.name=name;this.maybeTypeParams=maybeTypeParams;this.type=type;;}
	maybeAnnotations() : Option<List<Annotation>> {return this.maybeAnnotations;;}
	modifiers() : List<Modifier> {return this.modifiers;;}
	name() : String {return this.name;;}
	maybeTypeParams() : Option<List<TypescriptLang.TypeParam>> {return this.maybeTypeParams;;}
	type() : T {return this.type;;}
}
export class CommonLang {
	Statements(key : String, childRule : Rule) : Rule {return NodeListRule.createNodeListRule( key, new StatementFolder( ), childRule);;}
}

import { Option } from "../../../magmac/api/Option";
import { List } from "../../../magmac/api/collect/list/List";
import { NodeListRule } from "../../../magmac/app/compile/rule/NodeListRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { StatementFolder } from "../../../magmac/app/compile/rule/fold/StatementFolder";
import { Annotation } from "../../../magmac/app/lang/common/Annotation";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { TypescriptLang } from "../../../magmac/app/lang/web/TypescriptLang";
export class Definition<T> {
	withType(newType : T) : Definition<T> {return 0;;}
	withName(name : String) : Definition<T> {return 0;;}
}
export class CommonLang {
	Statements(key : String, childRule : Rule) : Rule {return 0;;}
}

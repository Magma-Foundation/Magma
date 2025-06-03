import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResults } from "../../../../magmac/app/compile/error/CompileResults";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { JavaDeserializers } from "../../../../magmac/app/lang/JavaDeserializers";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
export class Values {
	deserializeOrError(node : Node) : CompileResult<JavaValue> {;;}
	deserialize(node : Node) : Option<CompileResult<JavaValue>> {;;;;}
	getWrap(operator : Operator) : TypedDeserializer<JavaValue> {;;}
	initValueRule(segment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : LazyRule {;;}
	getValueRules(functionSegment : Rule, value : LazyRule, lambdaInfix : String, definition : Rule) : List<Rule> {;;;;}
	createSwitchRule(functionSegmentRule : Rule, value : Rule) : TypeRule {;;;;}
}

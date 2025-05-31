import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { ExactRule } from "../../../../magmac/app/compile/rule/ExactRule";
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
import { StatementFolder } from "../../../../magmac/app/compile/rule/fold/StatementFolder";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
export interface FunctionSegment {
	deserialize(node : Node) : CompileResult<FunctionSegment>;
	initFunctionSegmentRule(functionSegmentRule : LazyRule, value : Rule, definition : Rule) : Rule;
	createCaseRule(value : Rule, segment : Rule) : Rule;
}

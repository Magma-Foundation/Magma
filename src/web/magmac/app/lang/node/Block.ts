import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { Splitter } from "../../../../magmac/app/compile/rule/Splitter";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { FoldingDivider } from "../../../../magmac/app/compile/rule/divide/FoldingDivider";
import { DividingSplitter } from "../../../../magmac/app/compile/rule/split/DividingSplitter";
import { BlockFolder } from "../../../../magmac/app/lang/BlockFolder";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
export class Block {
	deserialize(node : Node) : Option<CompileResult<Block>>;
	createBlockRule(functionSegmentRule : LazyRule, value : Rule, definition : Rule) : Rule;
	createBlockRule0(header : Rule, functionSegmentRule : Rule) : TypeRule;
}

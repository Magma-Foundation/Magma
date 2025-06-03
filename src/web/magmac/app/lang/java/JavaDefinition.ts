import { Option } from "../../../../magmac/api/Option";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FilterRule } from "../../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { Splitter } from "../../../../magmac/app/compile/rule/Splitter";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Divider } from "../../../../magmac/app/compile/rule/divide/Divider";
import { FoldingDivider } from "../../../../magmac/app/compile/rule/divide/FoldingDivider";
import { DelimitedFolder } from "../../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { DividingSplitter } from "../../../../magmac/app/compile/rule/split/DividingSplitter";
import { OptionNodeListRule } from "../../../../magmac/app/lang/OptionNodeListRule";
import { TypeSeparatorFolder } from "../../../../magmac/app/lang/TypeSeparatorFolder";
import { ValueFolder } from "../../../../magmac/app/lang/ValueFolder";
import { Assignable } from "../../../../magmac/app/lang/node/Assignable";
import { Definition } from "../../../../magmac/app/lang/node/Definition";
import { JavaType } from "../../../../magmac/app/lang/node/JavaType";
import { JavaTypes } from "../../../../magmac/app/lang/node/JavaTypes";
import { LambdaParameter } from "../../../../magmac/app/lang/node/LambdaParameter";
import { Modifier } from "../../../../magmac/app/lang/node/Modifier";
import { StructureStatementValue } from "../../../../magmac/app/lang/node/StructureStatementValue";
export class JavaDefinition {
	deserialize(node : Node) : CompileResult<JavaDefinition> {break;;}
	deserializeTyped(node : Node) : Option<CompileResult<JavaDefinition>> {break;;}
	createRule() : Rule {break;break;break;break;break;break;break;break;break;break;;}
	attachTypeParams(beforeTypeParams : Rule) : Rule {break;break;;}
	serialize() : Node {break;;}
}

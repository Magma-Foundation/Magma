import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { FilterRule } from "../../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Deserializers } from "../../../../magmac/app/lang/Deserializers";
import { ValueFolder } from "../../../../magmac/app/lang/ValueFolder";
export class EnumValues {
	createEnumValuesRule(value : Rule) : TypeRule;
	deserialize(node : Node) : Option<CompileResult<JavaStructureMember>>;
	serialize() : Node;
}

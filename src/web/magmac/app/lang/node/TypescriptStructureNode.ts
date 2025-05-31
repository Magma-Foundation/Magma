import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { JavaLang } from "../../../../magmac/app/lang/JavaLang";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { TypescriptLang } from "../../../../magmac/app/lang/TypescriptLang";
export class TypescriptStructureNode {
	TypescriptStructureNode(type : TypescriptStructureType, structureNode : StructureValue<TypeScriptType, TypescriptStructureMember>) : public;
	createStructureRule(type : String) : Rule;
	serializeImplementsParams() : Node;
	serializeExtendedParams() : Node;
	serializeTypeParams() : Node;
	serialize() : Node;
}

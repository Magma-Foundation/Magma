import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { LocatingRule } from "../../../../magmac/app/compile/rule/LocatingRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { CommonLang } from "../../../../magmac/app/lang/CommonLang";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
import { TypescriptLang } from "../../../../magmac/app/lang/TypescriptLang";
import { JavaDefinition } from "../../../../magmac/app/lang/java/JavaDefinition";
export class TypescriptStructureNode {
	TypescriptStructureNode(type : TypescriptStructureType, structureNode : StructureValue<TypeScriptType, TypescriptStructureMember>) : public {break;break;;}
	createStructureRule(type : String) : Rule {break;break;break;break;;}
	serializeImplementsParams() : Node {break;;}
	serializeExtendedParams() : Node {break;;}
	serializeTypeParams() : Node {break;;}
	serialize() : Node {break;;}
}

import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { DelimitedFolder } from "../../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { Serializable } from "../../../../magmac/app/lang/Serializable";
export class Modifier {
	deserialize(node : Node) : CompileResult<Modifier> {return 0.destruct( 0).withString( "value").complete( 0.new);;}
	createModifiersRule() : Rule {return new StripRule( 0.createNodeListRule( "modifiers", new DelimitedFolder( ' '), new StringRule( "value")));;}
	serialize() : Node {return new MapNode( "modifier").withString( "value", 0.value);;}
}

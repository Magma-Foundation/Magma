import { Option } from "../../../../magmac/api/Option";
import { List } from "../../../../magmac/api/collect/list/List";
import { Iters } from "../../../../magmac/api/iter/Iters";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { InitialDestructor } from "../../../../magmac/app/compile/node/InitialDestructor";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeListRule } from "../../../../magmac/app/compile/rule/NodeListRule";
import { PrefixRule } from "../../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StringRule } from "../../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { DelimitedFolder } from "../../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { NamespacedType } from "../../../../magmac/app/lang/node/NamespacedType";
import { Segment } from "../../../../magmac/app/lang/node/Segment";
export class JavaNamespacedNode {
	deserialize(type : NamespacedType, node : Node) : Option<CompileResult<JavaRootSegment>> {return Destructors.destructWithType( type.type( ), node).map( 0);;}
	deserialize(node : Node) : Option<CompileResult<JavaRootSegment>> {return Iters.fromValues( NamespacedType.values( )).map( 0).flatMap( Iters.fromOption).next( );;}
	createNamespacedRule(type : String) : Rule {childRule : Rule=NodeListRule.createNodeListRule( "segments", new DelimitedFolder( '.'), new StringRule( "value"));return new TypeRule( type, new StripRule( new SuffixRule( new PrefixRule( type+" ", childRule), ";")));;}
}

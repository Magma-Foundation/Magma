import { Option } from "../../../../magmac/api/Option";
import { Lists } from "../../../../magmac/api/collect/list/Lists";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeRule } from "../../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../../magmac/app/compile/rule/OrRule";
import { Rule } from "../../../../magmac/app/compile/rule/Rule";
import { StripRule } from "../../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../../magmac/app/compile/rule/TypeRule";
import { Destructors } from "../../../../magmac/app/lang/Destructors";
import { JavaRules } from "../../../../magmac/app/lang/JavaRules";
import { LazyRule } from "../../../../magmac/app/lang/LazyRule";
import { StructureStatementValue } from "../../../../magmac/app/lang/node/StructureStatementValue";
export class JavaStructureStatement {
	deserialize(node : Node) : Option<CompileResult<JavaStructureMember>> {return Destructors.destructWithType( "structure-statement", node).map( 0);;}
	createStructureStatementRule(definitionRule : Rule, valueRule : LazyRule) : Rule {break;return new TypeRule( "structure-statement", new StripRule( new SuffixRule( definition, ";")));;}
}

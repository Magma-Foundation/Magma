import { Option } from "../../../../magmac/api/Option";
import { Joiner } from "../../../../magmac/api/iter/collect/Joiner";
import { ListCollector } from "../../../../magmac/api/iter/collect/ListCollector";
import { CompileResult } from "../../../../magmac/app/compile/error/CompileResult";
import { CompileResultCollector } from "../../../../magmac/app/compile/error/CompileResultCollector";
import { CompileErrors } from "../../../../magmac/app/compile/error/error/CompileErrors";
import { InlineNodeList } from "../../../../magmac/app/compile/node/InlineNodeList";
import { MapNode } from "../../../../magmac/app/compile/node/MapNode";
import { Node } from "../../../../magmac/app/compile/node/Node";
import { NodeList } from "../../../../magmac/app/compile/node/NodeList";
import { FoldingDivider } from "../../../../magmac/app/compile/rule/divide/FoldingDivider";
import { Folder } from "../../../../magmac/app/compile/rule/fold/Folder";
import { StatementFolder } from "../../../../magmac/app/compile/rule/fold/StatementFolder";
export class DivideRule {
	Statements : DivideRule {
	}
	join : CompileResult<Option<String>> {
	}
	lex : CompileResult<Node> {
	}
	generate : CompileResult<String> {
	}
}

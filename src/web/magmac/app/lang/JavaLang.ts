import { Lists } from "../../../magmac/api/collect/list/Lists";
import { FilterRule } from "../../../magmac/app/compile/rule/FilterRule";
import { LocatingRule } from "../../../magmac/app/compile/rule/LocatingRule";
import { NodeListRule } from "../../../magmac/app/compile/rule/NodeListRule";
import { NodeRule } from "../../../magmac/app/compile/rule/NodeRule";
import { OrRule } from "../../../magmac/app/compile/rule/OrRule";
import { PrefixRule } from "../../../magmac/app/compile/rule/PrefixRule";
import { Rule } from "../../../magmac/app/compile/rule/Rule";
import { Splitter } from "../../../magmac/app/compile/rule/Splitter";
import { StringRule } from "../../../magmac/app/compile/rule/StringRule";
import { StripRule } from "../../../magmac/app/compile/rule/StripRule";
import { SuffixRule } from "../../../magmac/app/compile/rule/SuffixRule";
import { TypeRule } from "../../../magmac/app/compile/rule/TypeRule";
import { Divider } from "../../../magmac/app/compile/rule/divide/Divider";
import { FoldingDivider } from "../../../magmac/app/compile/rule/divide/FoldingDivider";
import { DelimitedFolder } from "../../../magmac/app/compile/rule/fold/DelimitedFolder";
import { DividingSplitter } from "../../../magmac/app/compile/rule/split/DividingSplitter";
import { JavaRootSegment } from "../../../magmac/app/lang/node/JavaRootSegment";
import { JavaRootSegments } from "../../../magmac/app/lang/node/JavaRootSegments";
import { Modifier } from "../../../magmac/app/lang/node/Modifier";
import { RootDeserializer } from "../../../magmac/app/lang/node/RootDeserializer";
import { Types } from "../../../magmac/app/lang/node/Types";
export class JavaLang {
	createDeserializer() : RootDeserializer<JavaRootSegment>;
	createRule() : Rule;
	attachTypeParams(beforeTypeParams : Rule) : Rule;
}

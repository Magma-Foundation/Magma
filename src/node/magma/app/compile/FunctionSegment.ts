import { FunctionHeader } from "../../../magma/app/compile/define/FunctionHeader";
import { Definition } from "../../../magma/app/compile/define/Definition";
import { List } from "../../../magma/api/collect/list/List";
import { Option } from "../../../magma/api/option/Option";
import { Platform } from "../../../magma/app/io/Platform";
export class FunctionSegment<S extends FunctionHeader<S>> {
	header: FunctionHeader<S>;
	definitions: List<Definition>;
	maybeContent: Option<string>;
	constructor (header: FunctionHeader<S>, definitions: List<Definition>, maybeContent: Option<string>) {
		this.header = header;
		this.definitions = definitions;
		this.maybeContent = maybeContent;
	}
	generate(platform: Platform, indent: string): string {
		let content = this/*auto*/.maybeContent(/*auto*/).map((inner: string) => " {" + inner/*auto*/ + indent/*string*/ + "}").orElse(";");
		return indent/*string*/ + this/*auto*/.header.generateWithDefinitions(platform/*Platform*/, this/*auto*/.definitions(/*auto*/)) + content/*auto*/;
	}
}

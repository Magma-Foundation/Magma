import { Type } from "../../../../magma/api/Type";
import { List } from "../../../../magma/api/collect/list/List";
import { Main } from "../../../../magma/app/Main";
import { Strings } from "../../../../jvm/api/text/Strings";
export class TemplateType implements Type {
	base: string;
	args: List<string>;
	constructor (base: string, args: List<string>) {
		this.base = base;
		this.args = args;
	}
	static generateValueStrings(values: List<string>): string {
		return Main/*auto*/.generateAll(values/*auto*/, TemplateType/*auto*/.mergeValues);
	}
	static mergeValues(cache: string, element: string): string {
		if (Strings/*auto*/.isEmpty(cache/*auto*/)) {
			return cache/*auto*/ + element/*auto*/;
		}
		return cache/*auto*/ + ", " + element/*auto*/;
	}
	generate(): string {
		return this/*auto*/.base + "<" + TemplateType/*auto*/.generateValueStrings(this/*auto*/.args) + ">";
	}
	isFunctional(): boolean {
		return false/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}

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
		return Main.generateAll(values, TemplateType.mergeValues);
	}
	static mergeValues(cache: string, element: string): string {
		if (Strings.isEmpty(cache)) {
			return cache + element;
		}
		return cache + ", " + element;
	}
	generate(): string {
		return this.base + "<" + TemplateType.generateValueStrings(this.args) + ">";
	}
	isFunctional(): boolean {
		return false;
	}
	isVar(): boolean {
		return false;
	}
	generateBeforeName(): string {
		return "";
	}
}

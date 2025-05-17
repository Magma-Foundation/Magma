import { Type } from "../../../../magma/api/Type";
import { List } from "../../../../magma/api/collect/list/List";
import { Tuple2 } from "../../../../magma/api/Tuple2";
import { Joiner } from "../../../../magma/api/collect/Joiner";
export class FunctionType implements Type {
	args: List<string>;
	returns: string;
	constructor (args: List<string>, returns: string) {
		this.args = args;
		this.returns = returns;
	}
	generate(): string {
		let joinedArguments: string = this/*auto*/.args.queryWithIndices(/*auto*/).map((tuple: Tuple2<number, string>) => "arg" + tuple/*auto*/.left(/*auto*/) + " : " + tuple/*auto*/.right(/*auto*/)).collect(new Joiner(", ")).orElse("");
		return "(" + joinedArguments/*auto*/ + ") => " + this/*auto*/.returns;
	}
	isFunctional(): boolean {
		return true/*auto*/;
	}
	isVar(): boolean {
		return false/*auto*/;
	}
	generateBeforeName(): string {
		return "";
	}
}

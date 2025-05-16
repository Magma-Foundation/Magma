import { Option } from "../../../magma/api/option/Option";
import { Main } from "../../../magma/app/Main";
import { Head } from "./Head";
import { Option } from "./Option";
import { Main.None } from "./Main.None";
import { Main.Some } from "./Main.Some";
export class RangeHead implements Head<number> {
	length: number;
	counter: number;
	constructor (length: number) {
		this.length = length;
		this.counter = 0;
	}
	next(): Option<number> {
		if (this.counter >= this.length){
			return new Main.None<number>();
		}
		let value = this.counter;
		this.counter++;
		return new Main.Some<number>(value);
	}
}

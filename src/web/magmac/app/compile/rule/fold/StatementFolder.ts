import { DivideState } from "../../../../../magmac/app/compile/rule/divide/DivideState";
export class StatementFolder {
	fold(state : DivideState, c : char) : DivideState;
	createDelimiter() : String;
}

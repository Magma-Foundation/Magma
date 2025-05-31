import { DivideState } from "../../../../../magmac/app/compile/rule/divide/DivideState";
export interface Folder {
	fold(state : DivideState, c : char) : DivideState;
	createDelimiter() : String;
}

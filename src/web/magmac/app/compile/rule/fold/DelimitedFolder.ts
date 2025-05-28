import { DivideState } from "../../../../../magmac/app/compile/rule/divide/DivideState";
export class DelimitedFolder {
	temp : ?;
	DelimitedFolder(delimter : char) : public;
	fold(state : DivideState, c : char) : DivideState;
	createDelimiter() : String;
}

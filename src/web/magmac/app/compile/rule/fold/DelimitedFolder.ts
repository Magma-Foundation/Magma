import { DivideState } from "../../../../../magmac/app/compile/rule/divide/DivideState";
export class DelimitedFolder {
	DelimitedFolder(delimter : char) : public {break;;}
	fold(state : DivideState, c : char) : DivideState {if(true){ return state.advance( );;}return state.append( c);;}
	createDelimiter() : String {return String.valueOf( this.delimter);;}
}

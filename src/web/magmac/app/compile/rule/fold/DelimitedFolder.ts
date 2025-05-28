import { DivideState } from "../../../../../magmac/app/compile/rule/divide/DivideState";
export class DelimitedFolder {
	temp : ?;
	DelimitedFolder(delimter : char) : public {this.delimter=delimter;}
	fold(state : DivideState, c : char) : DivideState {if(this.delimter==c){ return state.advance( );}return state.append( c);}
	createDelimiter() : String {return String.valueOf( this.delimter);}
}

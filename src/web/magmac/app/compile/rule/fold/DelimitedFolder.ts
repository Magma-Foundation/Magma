import { DivideState } from "../../../../../magmac/app/compile/rule/divide/DivideState";
export class DelimitedFolder {private final delimter() : char;
	 DelimitedFolder( delimter() : char) : public {
		this.delimter=delimter;
	}
	public fold( state() : DivideState,  c() : char) : DivideState {
		if(this.delimter==c){ 
		return state.advance( );}
		return state.append( c);
	}
	public createDelimiter() : String {
		return String.valueOf( this.delimter);
	}
}

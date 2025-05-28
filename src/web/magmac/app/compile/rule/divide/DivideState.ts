import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { Option } from "../../../../../magmac/api/Option";
import { Iter } from "../../../../../magmac/api/iter/Iter";
export interface DivideState {
	 append( c : char) : DivideState;
	 advance() : DivideState;
	 iter() : Iter<String>;
	 isLevel() : boolean;
	 enter() : DivideState;
	 exit() : DivideState;
	 isShallow() : boolean;
	 pop() : Option<Tuple2<DivideState, Character>>;
	 popAndAppendToTuple() : Option<Tuple2<DivideState, Character>>;
	 popAndAppendToOption() : Option<DivideState>;
	 peek() : char;
}

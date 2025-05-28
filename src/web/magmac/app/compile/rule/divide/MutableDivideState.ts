import { None } from "../../../../../magmac/api/None";
import { Option } from "../../../../../magmac/api/Option";
import { Some } from "../../../../../magmac/api/Some";
import { Tuple2 } from "../../../../../magmac/api/Tuple2";
import { List } from "../../../../../magmac/api/collect/list/List";
import { Lists } from "../../../../../magmac/api/collect/list/Lists";
import { Iter } from "../../../../../magmac/api/iter/Iter";
export class MutableDivideState {
	temp : ?;
	temp : ?;
	temp : ?;
	temp : ?;
	temp : ?;
	MutableDivideState : private{
	}
	MutableDivideState : public{
	}
	append : DivideState{
	}
	advance : DivideState{
	}
	iter : Iter<String>{
	}
	isLevel : boolean{
	}
	enter : DivideState{
	}
	exit : DivideState{
	}
	isShallow : boolean{
	}
	pop : Option<Tuple2<DivideState, Character>>{
	}
	popAndAppendToTuple : Option<Tuple2<DivideState, Character>>{
	}
	popAndAppendToOption : Option<DivideState>{
	}
}

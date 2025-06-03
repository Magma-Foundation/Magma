import { List } from "../../../../magmac/api/collect/list/List";
export class Invokable<C,  A> {
	Invokable(caller : C, arguments : List<A>) : protected {break;break;;}
	caller() : C {return 0.caller;;}
	arguments() : List<A> {return 0.arguments;;}
}

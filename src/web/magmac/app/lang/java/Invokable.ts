import { List } from "../../../../magmac/api/collect/list/List";
export class Invokable<C,  A> {
	Invokable(caller : C, arguments : List<A>) : protected {break;break;;}
	caller() : C {return this.caller;;}
	arguments() : List<A> {return this.arguments;;}
}

import { List } from "../../../../magmac/api/collect/list/List";
export class Invokable<C,  A> {
	Invokable(caller : C, arguments : List<A>) : protected {break;break;;}
	caller() : C {break;;}
	arguments() : List<A> {break;;}
}

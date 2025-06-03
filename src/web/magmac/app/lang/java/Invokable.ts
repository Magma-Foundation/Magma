import { List } from "../../../../magmac/api/collect/list/List";
export class Invokable<C,  A> {
	Invokable(caller : C, arguments : List<A>) : protected {;;;}
	caller() : C {;;}
	arguments() : List<A> {;;}
}

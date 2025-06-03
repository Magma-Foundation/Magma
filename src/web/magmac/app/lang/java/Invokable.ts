import { List } from "../../../../magmac/api/collect/list/List";
export class Invokable<C,  A> {
	Invokable(caller : C, arguments : List<A>) : protected {this.caller=caller;this.arguments=arguments;;}
	caller() : C {return this.caller;;}
	arguments() : List<A> {return this.arguments;;}
}

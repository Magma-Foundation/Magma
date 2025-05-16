/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect, 
	Head: magma.api.collect, 
	List: magma.api.collect, 
	ListCollector: magma.api.collect, 
	Lists: magma.api.collect, 
	HeadedQuery: magma.api.collect.query, 
	Query: magma.api.collect.query, 
	RangeHead: magma.api.collect, 
	SingleHead: magma.api.collect
]*/
import { Actual } from "../../magma/annotate/Actual";
import { Namespace } from "../../magma/annotate/Namespace";
export interface ConsoleInstance {
	printErrLn(message: string): void;

}
export declare const Console: ConsoleInstance;

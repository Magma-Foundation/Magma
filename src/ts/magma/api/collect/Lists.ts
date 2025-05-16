/*[
	Actual: magma.annotate, 
	Namespace: magma.annotate, 
	Collector: magma.api.collect, 
	EmptyHead: magma.api.collect, 
	Head: magma.api.collect, 
	List: magma.api.collect, 
	ListCollector: magma.api.collect
]*/
import { Actual } from "../../../magma/annotate/Actual";
import { Namespace } from "../../../magma/annotate/Namespace";
import { Tuple2 } from "../../../magma/api/Tuple2";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
import { HeadedQuery } from "../../../magma/api/collect/query/HeadedQuery";
import { Query } from "../../../magma/api/collect/query/Query";
import { Option } from "../../../magma/api/option/Option";
import { Some } from "../../../magma/api/option/Some";
import { ArrayList } from "../../../java/util/ArrayList";
import { Arrays } from "../../../java/util/Arrays";
import { List } from "./List";
export interface ListsInstance {
	empty<T>(): List<T>;

	of<T>(...elements: T[]): List<T>;

}
export declare const Lists: ListsInstance;

import { Actual } from "../../../magma/annotate/Actual";
import { Namespace } from "../../../magma/annotate/Namespace";
import { Tuple2 } from "../../../magma/api/Tuple2";
import { Tuple2Impl } from "../../../magma/api/Tuple2Impl";
import { HeadedQuery } from "../../../magma/api/collect/query/HeadedQuery";
import { Query } from "../../../magma/api/collect/query/Query";
import { Option } from "../../../magma/api/option/Option";
import { Main } from "../../../magma/app/Main";
import { ArrayList } from "../../../java/util/ArrayList";
import { Arrays } from "../../../java/util/Arrays";
import { List } from "./List";
import { java.util.List } from "./java.util.List";
export interface ListsInstance {
	empty<T>(): List<T>;

	of<T>(...elements: T[]): List<T>;

}
export declare const Lists: ListsInstance;

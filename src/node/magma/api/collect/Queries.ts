// [Lists, Lists, Lists, Console, Console, Console, Files, Files, Files, Characters, Characters, Characters, Strings, Strings, Strings, Actual, Actual, Actual, Namespace, Namespace, Namespace, Collector, Collector, Collector, EmptyHead, EmptyHead, EmptyHead, FlatMapHead, FlatMapHead, FlatMapHead, Head, Head, Head, HeadedQuery, HeadedQuery, HeadedQuery, MapHead, MapHead, MapHead, RangeHead, RangeHead, RangeHead, SingleHead, SingleHead, SingleHead, ZipHead, ZipHead, ZipHead, Joiner, Joiner, Joiner, List, List, List, ListCollector, ListCollector, ListCollector, Queries]
import { Query } from "../../../magma/api/collect/Query";
import { Option } from "../../../magma/api/option/Option";
import { HeadedQuery } from "../../../magma/api/collect/head/HeadedQuery";
import { EmptyHead } from "../../../magma/api/collect/head/EmptyHead";
import { Head } from "../../../magma/api/collect/head/Head";
import { SingleHead } from "../../../magma/api/collect/head/SingleHead";
export class Queries {
	static fromOption<T>(option: Option<T>): Query<T> {
		return new HeadedQuery<T>(option/*Option<T>*/.map((element: T) => Queries/*auto*/.getTSingleHead(element/*T*/)).orElseGet(() => new EmptyHead<T>(/*auto*/)));
	}
	static getTSingleHead<T>(element: T): Head<T> {
		return new SingleHead<T>(element/*T*/);
	}
	static fromArray<T>(elements: T[]): Query<T> {
		/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
	}
}

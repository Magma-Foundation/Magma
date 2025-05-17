import { Query } from "../../../magma/api/collect/Query";
import { Option } from "../../../magma/api/option/Option";
import { HeadedQuery } from "../../../magma/api/collect/head/HeadedQuery";
import { EmptyHead } from "../../../magma/api/collect/head/EmptyHead";
import { Head } from "../../../magma/api/collect/head/Head";
import { SingleHead } from "../../../magma/api/collect/head/SingleHead";
export class Queries {
	static fromOption<T>(option: Option<T>): Query<T> {
		return new HeadedQuery<T>(option.map((element: T) => Queries.getTSingleHead(element)).orElseGet(() => new EmptyHead<T>()));
	}
	static getTSingleHead<T>(element: T): Head<T> {
		return new SingleHead<T>(element);
	}
	static fromArray<T>(elements: T[]): Query<T> {
		/*return new HeadedQuery<Integer>(new RangeHead(elements.length)).map((Integer index) -> elements[index])*/;
	}
}

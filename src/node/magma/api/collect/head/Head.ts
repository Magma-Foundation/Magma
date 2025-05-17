// [Lists, Console, Files, Characters, Strings, Actual, Namespace, Collector, EmptyHead, FlatMapHead, Head]
import { Option } from "../../../../magma/api/option/Option";
export interface Head<T> {
	next(): Option<T>;
}

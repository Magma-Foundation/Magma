import { Option } from "../../../magmac/api/Option";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Result } from "../../../magmac/api/result/Result";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export interface Iter {folder) : BiFunction<R, T, Result<R, X>>;mapper) : map(Function<T, R>;folder) : BiFunction<R, T, R>;collector) : collect(Collector<T, C>;predicate) : filter(Predicate<T>;next() : Option<T>;mapper) : flatMap(Function<T, Iter<R>>;other) : concat(Iter<T>;
}

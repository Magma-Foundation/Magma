import { Option } from "../../../magmac/api/Option";
import { Collector } from "../../../magmac/api/iter/collect/Collector";
import { Result } from "../../../magmac/api/result/Result";
import { BiFunction } from "../../../java/util/function/BiFunction";
import { Function } from "../../../java/util/function/Function";
import { Predicate } from "../../../java/util/function/Predicate";
export interface Iter {<R, X> Result<R, X> foldToResult(R initial, folder)() : BiFunction<R, T, Result<R, X>>; mapper)() : map(Function<T, R>;<R> R fold(R initial, folder)() : BiFunction<R, T, R>;<C> C collector)() : collect(Collector<T, C>;Iter predicate)() : filter(Predicate<T>; next()() : Option<T>; mapper)() : flatMap(Function<T, Iter<R>>;Iter other)() : concat(Iter<T>;
}

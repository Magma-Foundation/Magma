import { Consumer } from "../../java/util/function/Consumer";
import { Function } from "../../java/util/function/Function";
import { Predicate } from "../../java/util/function/Predicate";
import { Supplier } from "../../java/util/function/Supplier";
export interface Option {mapper) : map(Function<T, R>;isPresent() : boolean;other) : orElseGet(Supplier<T>;isEmpty() : boolean;mapper) : flatMap(Function<T, Option<R>>;
	orElse(other : T) : T;predicate) : filter(Predicate<T>;other) : or(Supplier<Option<T>>;consumer) : ifPresent(Consumer<T>;
}

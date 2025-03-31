#include "HeadedStream.h"
magma.collect.stream.head.R foldWithInitial(magma.collect.stream.head.R initial, magma.collect.stream.head.R(*folder)(magma.collect.stream.head.R, T)){R current = initial;while (true) {
            R finalCurrent = current;
            Tuple<Boolean, R> tuple = head.next()
                    .map(inner -> folder.apply(finalCurrent, inner))
                    .toTuple(current);

            if (tuple.left()) {
                current = tuple.right();
            } else {
                break;
            }
        }return current;
}
magma.collect.stream.Stream<magma.collect.stream.head.R> map(magma.collect.stream.head.R(*mapper)(T)){return (__lambda0__.next().map(mapper));
}
magma.collect.stream.head.C collect(magma.collect.stream.Collector<T, magma.collect.stream.head.C> collector){return foldWithInitial(collector.createInitial(), collector.fold);
}
magma.option.Option<T> next(){return head.next();
}
int anyMatch(int(*predicate)(T)){return foldWithInitial(false, __lambda1__||predicate.test(t));
}
magma.option.Option<magma.collect.stream.head.R> foldMapping(magma.collect.stream.head.R(*mapper)(T), magma.collect.stream.head.R(*folder)(magma.collect.stream.head.R, T)){return head.next().map(mapper).map(__lambda2__(initial, folder));
}
magma.collect.stream.Stream<T> filter(int(*predicate)(T)){return flatMap(__lambda3__);
}
magma.collect.stream.Stream<magma.collect.stream.head.R> flatMap(magma.collect.stream.Stream<magma.collect.stream.head.R>(*mapper)(T)){return this.foldWithInitial((()), __lambda4__.concat(mapper.apply(t)));
}
magma.result.Result<magma.collect.stream.head.R, magma.collect.stream.head.X> foldToResult(magma.collect.stream.head.R initial, magma.result.Result<magma.collect.stream.head.R, magma.collect.stream.head.X>(*folder)(magma.collect.stream.head.R, T)){return this.foldWithInitial((initial), __lambda5__.flatMapValue(__lambda6__.apply(inner, t)));
}
magma.collect.stream.Stream<T> concat(magma.collect.stream.Stream<T> other){return (__lambda7__.next().or(other.next));
}
auto __lambda0__();
auto __lambda1__();
auto __lambda2__();
auto __lambda3__();
auto __lambda4__();
auto __lambda5__();
auto __lambda6__();
auto __lambda7__();

